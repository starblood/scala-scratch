package study.selftype

import study.selftype.basic.Color

/**
  * 2016. 7. 27..
  */
trait Pet[A <: Pet[A]] {
  this: A =>
  def name: String
  def renamed(newName: String): A
}

case class Fish(name: String, age: Int) extends Pet[Fish] {
  def renamed(newName: String): Fish = copy(name = newName)
}

/* this is not permitted
case class Kitty(name: String, color: Color) extends Pet[Fish] { // Pet[Fish] should be Pet[Kitty]
  override def renamed(newName: String): Fish = copy(name = newName)
}
*/
case class Kitty(name: String, color: Color) extends Pet[Kitty] {
  override def renamed(newName: String): Kitty = copy(name = newName)
}

object SelfTypeTest {

}
