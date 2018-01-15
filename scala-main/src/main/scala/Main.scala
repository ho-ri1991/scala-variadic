import com.hori1991.variadic._

object Main {
  @MapGenerator(5) class Mapper

  def main(args: Array[String]): Unit = {
    val mapper = Mapper[Option]
    println(mapper.map(Some(1), Some("a"), Some(false), Some(1.5)){ (x, y, z, w) => (x, y, z, w)})
  }
}
