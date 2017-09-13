package study.selftype.fbounded

import study.selftype.basic.Color

/**
  * 2016. 7. 27..
  * refer: https://tpolecat.github.io/2015/04/29/f-bounds.html
  */

trait Pet[A <: Pet[A]] {
  def name: String
  def renamed(newName: String): A
}

case class Fish(name: String, age: Int) extends Pet[Fish] {
  override def renamed(newName: String) = copy(name = newName)
}

case class Kitty(name: String, color: Color) extends Pet[Fish] { // oops
  override def renamed(newName: String): Fish = new Fish(newName, 42)
}

object FBoundedTypesTest {
  def esquire[A <: Pet[A]](a: A): A = a.renamed(a.name + ", Esq.")

  def main(args: Array[String]): Unit = {
    val a = Fish("moly", 2)
    val b = a.renamed("guppy")
    println(a, b)
    println(esquire(a))
  }
}
