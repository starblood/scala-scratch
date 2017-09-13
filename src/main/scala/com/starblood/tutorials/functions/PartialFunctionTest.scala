package com.starblood.tutorials.functions

object PartialFunctionTest {
  def main(args: Array[String]): Unit = {


    def addUmm(x: String) = x + " Umm"
    def addAhem(x: String) = x + " Ahem"

    // f compose g를 하면 두 함수를 f(g(x))과 같이 합성한다.
    val ummAndAhem = addUmm _ compose addAhem _
    ummAndAhem("hello")

    val one: PartialFunction[Int, String] = { case 1 => "one" }
    one(1)
    // compile error
    //one(2)

    // compile error
    // List(41, "cat") map { case i: Int ⇒ i + 1 }

    List(41, "cat") collect { case i: Int ⇒ i + 1 }

    val fraction = new PartialFunction[Int, Int] {
      def apply(d: Int) = 42 / d
      def isDefinedAt(d: Int) = d != 0
    }


    val list = List(1, 2, 3, 0, 5)
    // collect 는 PartialFunction 을 요구한다. 실행중에 isDefinedAt 을 이용하여 element 를 확인
    list.collect(fraction)

    val incAny: PartialFunction[Any, Int] = { case i: Int ⇒ i + 1 }
    incAny(41)
    // compile error
    //incAny("scala")


    val root: PartialFunction[Double,Double] = {
      case d if (d >= 0) => math.sqrt(d)
    }

    root.isDefinedAt(-1)
    root.isDefinedAt(3)

    val doubleEvens: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
      def isDefinedAt(x: Int) = x % 2 == 0
      def apply(x: Int) = x * 2
    }

    val tripleOdds: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
      def isDefinedAt(x: Int) = x % 2 != 0
      def apply(x: Int) = x * 3
    }

    val whatToDo = doubleEvens orElse tripleOdds
    whatToDo(3)
    whatToDo(2)

    val doubleEvens2: PartialFunction[Int, Int] = {
      case x: Int if x % 2 == 0 => x * 2
    }

    val tripleOdds2: PartialFunction[Int, Int] = {
      case x: Int if x % 2 != 0 => x * 3
    }

    def addFive(x:Int) = x + 5
    val whatToDo2 = doubleEvens2 orElse tripleOdds2 andThen addFive
    whatToDo2(3)
    whatToDo2(2)

    val div: (Double, Double) => Double = {
      case (x, y) if y != 0 => x / y
    }

    div(2,3)
    //div(2,0) error

    // no need to catch MatchError Exception
    val div2: PartialFunction[(Double, Double), Double] = {
      case (x, y) if y != 0 => x /y
    }

    div2.isDefinedAt(2, 0)

    def add(i: Int, j: Int) = i + j
    val add5 = add(_: Int,5)
    add5(2)

  }
}
