package com.starblood.tutorials.collection.vector

object VectorTest {
  def main(args: Array[String]): Unit = {
    val vector = Vector(1,2,3)

    vector :+ -1
    -1 +: vector

  }
}
