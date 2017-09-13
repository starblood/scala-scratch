package com.starblood.tutorials.collection.list

object ListTest {
  def main(args: Array[String]): Unit = {
    val list = List("a", "a", "a", "b", "c", "c", "a")

    def pack[T](list: List[T]): List[List[T]] = list match
    {
      case Nil => Nil
      case x :: xs => {
        val (first, rest) = list span (y => y == x)
        first :: pack(rest)
      }
    }

    pack(list)

    def encode[T](list: List[T]): List[(T, Int)] =
      pack(list) map (xs => (xs.head, xs.length))

    encode(list)

    val abcde = List("a", "b", "c", "d", "e")
    abcde.indices
    abcde.indices zip abcde

    val tmp = abcde.indices zip abcde

    val result = tmp.foldLeft(Map.empty[Int,String]){(acc, e: (Int, String)) =>
      acc + (e._1 -> e._2)
    }

  }
}
