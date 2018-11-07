package com.starblood.libraries

import java.util.Date

object DateTimeTest {

  def main(args: Array[String]): Unit = {
    val t1 = System.currentTimeMillis()
    val t2 = System.currentTimeMillis()
    val t3 = System.currentTimeMillis()
    val t4 = System.currentTimeMillis()


    val t = (t1 + t2 + t3 + t4) / 4

    println(new Date(t))
  }
}
