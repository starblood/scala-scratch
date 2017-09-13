package com.starblood.tutorials.functions

object DefVsValTest {
  def main(args: Array[String]): Unit = {
    def even: Int => Boolean = _ % 2 == 0
    even eq even // false

    val even2: Int => Boolean = _ % 2 == 0
    even2 eq even2 // true

    val test: () => Int = {
      val r = util.Random.nextInt()
      () => r
    }

    test() // same
    test() // same

    def test2: () => Int = {
      val r = util.Random.nextInt()
      () => r
    }

    test2() // not same
    test2() // not same

    def odd2: Int => Boolean = ???
    //val odd: Int => Boolean = ??? // error

    odd2
  }
}
