package study.implicits

import java.io.File

/**
  * 2017. 1. 2..
  */
object ImplicitsTests extends App {
  sealed trait Json
  object Json {
    case class Str(s: String) extends Json
    case class Num(v: Double) extends Json
  }

  /* 아래의 주석 처리된 예제는 implicit 을 사용하지 않았을 경우 실수가 발생할 여지를 남겨둔다
  def convertToJson(x: Any): Json = {
    x match {
      case s: String => Json.Str(s)
      case d: Double => Json.Num(d)
      case i: Int => Json.Num(i.toDouble)
    }
  }

  convertToJson("Hello")
  convertToJson(1234)
  convertToJson(new File("io")) // convertToJson 이 parameter 로 Any type 을 받을 수 있기 때문에 실행시 에러 발생
  */

  trait Jsonable[T] {
    def serialize(t: T): Json
  }
  object Jsonable {
    implicit object StringJsonable extends Jsonable[String] {
      override def serialize(t: String) = Json.Str(t)
    }
    implicit object DoubleJsonable extends Jsonable[Double] {
      override def serialize(t: Double) = Json.Num(t)
    }
    implicit object IntJsonable extends Jsonable[Int] {
      override def serialize(t: Int) = Json.Num(t.toDouble)
    }
  }

  def convertToJson[T](x: T)(implicit converter: Jsonable[T]): Json = {
    converter.serialize(x)
  }

  def convertToJson2[T: Jsonable](x: T): Json = {
    implicitly[Jsonable[T]].serialize(x)
  }

  convertToJson("hello")
  convertToJson(1234)
//  convertToJson(new File("test.txt")) // implicit conversion 이 없어서 compiler 가 빌드시 에러를 보고 한다. type safe
}
