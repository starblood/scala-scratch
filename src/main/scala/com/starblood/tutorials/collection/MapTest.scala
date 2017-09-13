package com.starblood.tutorials.collection;

object MapTest {

  def main(args: Array[String]): Unit = {
    println("hello")

    val list = List(List(1, 2, 3, 4, 5, 6), List(1,2, 3, 4, 5,6))

    val map = list.flatten.groupBy(k => k)
    println(map)
  }
}
