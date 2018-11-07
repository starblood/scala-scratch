package com.starblood.types




import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

object TypeEraserTest {

  // JVM 의 type eraser 때문에 재대로된 type 을 추출할 수 없다.
  object Extractor {
    def extract[T](list: List[Any]) = list.flatMap {
      case element: T => Some(element)
      case _ => None
    }
  }

  /*  ClassTag 의 implicit value 가 필요할 경우 compiler 가 자동으로 생성하여 넣어준다.
      Compiler tries to turn unchecked type tests in pattern matches into checked ones by wrapping a (_: T)
      type pattern as ct(_: T), where ct is the ClassTag[T] instance.

      컴파일러에 의해 아래 코드가
      case element: T => Some(element)
      아래 코드로 변환된다.
      case (element @ tag(_: T)) => Some(element)
   */
  object Extractor2 {
    def extract[T](list: List[Any])(implicit classTag: ClassTag[T]): List[T] = {
      list.flatMap {
        case element: T => Some(element)
        case _ => None
      }
    }
  }

  /*  TypeTag 로는 having more information about the type, such as knowing about the higher types
      (that is, being able to differentiate List[X] from List[Y]), but their downside is that
      they cannot be used on objects at runtime.
   */
  object Recognizer {
    def recognize[T](x: T)(implicit tag: TypeTag[T]): String = {
      tag.tpe match {
        case TypeRef(utype, usymbol, args) => List(utype, usymbol, args).mkString("\n")
      }
    }
  }

  /*  type parameter 가 추상 클래스에 사용될 경우에는 WeakTypeTag 가 필요하다.
      WeakTypeTag 를 TypeTag 의 super set 으로 생각하면 된다.
   */
  abstract class SomeClass[T] {

    object Recognizer {
      def recognize[T](x: T)(implicit tag: WeakTypeTag[T]): String =
        tag.tpe match {
          case TypeRef(utype, usymbol, args) =>
            List(utype, usymbol, args).mkString("\n")
        }
    }

    val list: List[T]
    val result = Recognizer.recognize(list)
    println(result)
  }

  def test1(): Unit = {
    import Extractor._

    val list = List(1, "string1", List(), "string2")
    val result = extract[String](list)
    println(result)
  }

  def test2_1(): Unit = {
    import Extractor2._
    val list = List(1, "string1", List(), "string2")
    val result = extract[String](list)
    println(result)
  }

  // Class tags cannot differentiate on a higher level.
  def test2_2(): Unit = {
    import Extractor2._
    val list: List[List[Any]] = List(List(1, 2), List("a", "b"))
    val result = extract[List[Int]](list)
    println(result) // List(List(1, 2), List(a, b))
  }

  def test3() = {
    import Recognizer._
    val list: List[Int] = List(1,2)
    println(recognize(list))
  }

  /*  아래 println 으로 나타나는 type 은 T 로 찍히게 된다.
      Resulting type is a List[T]. If we had used a TypeTag instead of a WeakTypeTag,
      compiler would have complained that there is “no TypeTag available for List[T]”
   */
  def test4(): Unit = {
    new SomeClass[Int] { val list = List(1) }
    // prints:
    //   scala.type
    //   type List
    //   List(T)
  }

  /*  Note that using tags (which is basically reflection under the hood) can slow things down and make the generated code
      significantly bigger, so don’t go around adding implicit type tags all over your library to make the compiler
      smarter “just in case” and for no practical reason. Save them for when you really need them.
      And when you do need them, they will provide a powerful weapon against JVM’s type erasure.  */
  def test5(): Unit = {
    import scala.reflect.classTag
    import scala.reflect.runtime.universe._

    val ct = classTag[String]
    val tt = typeTag[List[Int]]
    val wtt = weakTypeTag[List[Int]]

    val array = ct.newArray(3)
    array.update(2, "Third")

    println(array.mkString(","))
    println(tt.tpe)
    println(wtt.equals(tt))

    //  prints:
    //    null,null,Third
    //    List[Int]
    //    true
  }

  /*  here are the two main differences between ClassTags and TypeTags in one place:

      ClassTag doesn’t know about “higher type”; given a List[T], a ClassTag only knows that the value is a List and
      knows nothing about T.
      TypeTag knows about “higher type” and has a much richer type information,
      but cannot be used for getting type information about values at runtime.
      In other words, TypeTag provides runtime information about the type while ClassTag provides runtime information
      about the value
      (more specifically, information that tells us what is the actual type of the value in question at runtime).
   */
  def main(args: Array[String]): Unit = {
    test1()
    test2_1()
    test2_2()
    test3()
    test4()
  }
}
