package com.starblood.tutorials.phantom_types


object PhantomTypesTest {

  case class Food(ingredients: Seq[String])

  class Chef {
    def hasDoughCheeseAndToppings(ingredients: Seq[String]): Boolean = true

    def build(ingredients: Seq[String]): Food =
      if (hasDoughCheeseAndToppings(ingredients)) Food(ingredients)
      else throw new FoodBuildingException("You tried to build a pizza without enough ingredients")
  }

  case class FoodBuildingException(str: String) extends Exception

  class DoorStateException(str: String) extends Exception


  def main(args: Array[String]): Unit = {
    val ingredients = Seq("dough", "cheese", "topping")
    (new Chef()).build(ingredients) // may fail in runtime, is there better solution?

    // we need phantom types for this case
    def need_phantomtypes() = {
      trait DoorState
      case class Open() extends DoorState
      case class Closed() extends DoorState
      case class Door(state: DoorState) {
        def open = state match {
          case _: Open => throw new DoorStateException("You cannot open a door that's already open")
          case _ => Door(Open())
        }

        def close = state match {
          case _: Closed => throw new DoorStateException("You cannot close a door that's already closed")
          case _ => Door(Closed())
        }
      }
    }

    /*  A phantom type is a manifestation of abstract type that has no effect on the runtime.
        These are useful to prove static properties of the code using type evidences.
        As they have no effect on the runtime they can be erased from the resulting code by the compiler
        once it has shown the constraints hold. */
    // use phantom types
    trait DoorState
    case class Open() extends DoorState
    case class Closed() extends DoorState
    case class Door[State <: DoorState]() {
      // method which depends on structure are restricted by an implicit evidence which binds them to a specific DoorState.
      def open(implicit ev: State =:= Closed) = Door[Open]()
      def close(implicit ev: State =:= Open) = Door[Closed]()
    }

//    Door[Open]().open // won't compile
    Door[Closed]().open

  }

}
