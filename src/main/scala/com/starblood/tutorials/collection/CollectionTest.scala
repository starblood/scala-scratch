package com.starblood.tutorials.collection

object CollectionTest {
  def main(args: Array[String]): Unit = {
    val strs = List("1", "two", "3", "four", "five")
    val nums = strs.flatMap { s =>
      try {
        List(s.toInt)
      } catch {
        case _ => Nil
      }
    }


    def f(x: Option[Int]): Option[Int] = if (x.isDefined) Some(x.get * 2) else None

    val list = List(Some(1), Some(2), None, Some(3), Some(4), None)

    list.flatMap(x => f(x))


    val engToDeu = Map(("dog","Hund"), ("cat","Katze"), ("rhinoceros","Nashorn"))

    engToDeu("dog")

    // engToDeu("bird") error


    engToDeu.get("dog")
    engToDeu.get("bird")


    val words = List("1", "2", "three", "4", "one hundred seventy five")

    def toInt(in: String): Option[Int] = {
      try {
        Some(Integer.parseInt(in.trim))
      } catch {
        case e: Exception => None
      }
    }

    val convertedWords = words.flatMap(toInt)
    convertedWords.foreach(println)
    val flattedList = list.flatMap(x => x)
    flattedList.foreach(println)


  }
}
