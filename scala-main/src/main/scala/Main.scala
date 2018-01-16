import com.hori1991.variadic._

object Main {
  @MapGenerator(5) class Mapper

  @SequenceGenerator(5) class Sequencer

  def main(args: Array[String]): Unit = {
    val mapper = Mapper[Option]
    println(mapper.map(Some(1), Some("a"), Some(false), Some(1.5)){ (x, y, z, w) => (x, y, z, w)})
    println(mapper.map(Some(1), Some("a"), Some(false), None){ (x, y, z, w) => (x, y, z, w)})

    val sequencer = Sequencer[Option]
    println(sequencer.sequence(Some(1), Some("a"), Some(false), Some(1.5)))
    println(sequencer.sequence(Some(1), None, Some(false), Some(1.5)))
  }
}
