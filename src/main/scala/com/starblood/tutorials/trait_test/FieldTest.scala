package scala_tutorial.trait_test

// test if trait has field, subclasses don't have to prepend override modifier to that field
trait Store {
  var boxes: Set[Int]

  def plus(box: Int) = {
    boxes += box
  }

  def printBox = {
    println (boxes.mkString(", "))
  }
}

class AppleStore extends Store {
  var boxes = Set(1,2,3)
}

class SamsungStore extends Store {
  var boxes = Set(5,6,7,8)
}

object FieldTest extends App {
  val appleStore = new AppleStore
  appleStore.plus(-1)
  appleStore.printBox

  val samsungStore = new SamsungStore
  samsungStore.plus(10)
  samsungStore.printBox

}
