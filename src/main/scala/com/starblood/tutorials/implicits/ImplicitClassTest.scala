package com.starblood.tutorials.implicits;

/**
 * Created by john.shim on 2015. 3. 24..
 */

object ImplicitClassTest {


  def main(args: Array[String]): Unit = {
    implicit def abcdd(x: String) = x.toInt + 3

    object StringUtils {
      implicit class StringImprovements(val s: String) {
        def increment = s.map(c => (c + 1).toChar)
      }
    }

    import StringUtils.StringImprovements
    println("KOR".increment)

    val abc: Int = "123"
  }

}
