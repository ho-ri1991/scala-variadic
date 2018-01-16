package com.hori1991.variadic

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta._

@compileTimeOnly("SequenceGenerator not expanded")
class SequenceGenerator(generateNum: Int) extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    val q"class $className" = defn
    val q"new $_(${num: Term.Arg})" = this

    val sequenceDefs =
      for(i <- 2 to num.toString().toInt) yield {
        val genericsParams = (1 to i).map(j => s"A${j}").mkString(", ")
        val arguments = (1 to i).map(j => s"fa${j}: F[A${j}]").mkString(", ")
        val params = (1 to i).map(j => s"a${j}").mkString(", ")
        val functionEntity = (1 to i).map(j => s"a${j} <- fa${j}").mkString("for{\n", "\n", s"\n} yield ($params)")
        s"def sequence[${genericsParams}](${arguments}): F[(${genericsParams})] = ${functionEntity}".parse[Stat].get
      }

    q"""
        case class ${className}[F[X] <: {def flatMap[Y](g: X => F[Y]): F[Y]; def map[Y](g: X => Y): F[Y]}]() {
          ..${sequenceDefs}
        }
     """
  }
}
