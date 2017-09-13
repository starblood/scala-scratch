package com.starblood.tutorials.byname_parameter

object BynameParameterTest {
  def main(args: Array[String]): Unit = {
    def calc_by_value(x: () => Int): Either[Throwable, Int] = {
      try {
        Right(x())
      } catch {
        case b: Throwable => Left(b)
      }
    }

    val y = calc_by_value(() =>
      14 + 15
    )

    y == Right(29)

    def calc(x: => Int): Either[Throwable, Int] = {
      try {
        Right(x)
      } catch {
        case b: Throwable => Left(b)
      }
    }

    val x = calc({
      println ("huh?")
      val z = List(1,2,3)
      49 + 20
    })

    x == Right(69)

    object PigLatinizer {
      def apply (x: => String) = x.tail + x.head + "ay"
    }

    val result = PigLatinizer {
      val x = "pret"
      val y = "zel"
      x ++ y
    }

    result == "retzelpay"
  }
}
