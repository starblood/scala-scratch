package study.selftype.basic

/**
  * 2016. 7. 27..
  */
case class Color(name: String)
trait Pet {
  def name: String
  def renamed(newName: String): Pet
}

case class Fish(name: String, age: Int) extends Pet {
  def renamed(newName: String): Fish = copy(name = newName)
}

case class Kitty(name: String, color: Color) extends Pet {
  def renamed(newName: String): Fish = new Fish(newName, 42)
}
object SelfTypeTest {
  def main(args: Array[String]): Unit = {
    val a = Fish("Jimmy", 2)
    val b = a.renamed("Bob")

    println(a, b)
  }
}
