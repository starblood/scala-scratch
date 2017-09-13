package com.starblood.tutorials.reflection_test

object ReflectionTest {
  def main(args: Array[String]): Unit = {
    import scala.reflect.runtime.{universe => ru}
    val l = List(1,2,3)
    def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]
    val theType = getTypeTag(l).tpe

    val decls = theType.declarations.take(10)


    case class Person(name: String)

  }
}
