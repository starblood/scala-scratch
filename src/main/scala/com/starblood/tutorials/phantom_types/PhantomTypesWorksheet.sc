import Chef.Pizza.EmptyPizza

trait DoorState
case class Open() extends DoorState
case class Closed() extends DoorState

// typical state validation
//case class Door(state: DoorState) {
//  def open() = state match {
//    case _: Open => throw new RuntimeException
//    case _ => Door(Open())
//  }
//
//  def close() = state match {
//    case _: Closed => throw new RuntimeException
//    case _ => Door(Closed())
//  }
//}

// using phantom types
case class Door[State <: DoorState]() {
  def open(implicit ev: State =:= Closed) = Door[Open]()
  def close(implicit ev: State =:= Open) = Door[Closed]()
}

//Door[Open].open









case class Food(ingredients: Seq[String])
object Chef {
  sealed trait Pizza
  object Pizza {
    sealed trait EmptyPizza extends Pizza
    sealed trait Dough extends Pizza
    sealed trait Cheese extends Pizza
    sealed trait Topping extends Pizza
    type FullPizza = EmptyPizza with Dough with Cheese with Topping
  }
  def apply[T <: Pizza](ingredients: Seq[String]): Chef[T] = new Chef[T](ingredients)
  def apply(): Chef[EmptyPizza] = apply[EmptyPizza](Seq())
}

class Chef[Pizza <: Chef.Pizza](ingredients: Seq[String]) {
  import Chef.Pizza._
  def addCheese(cheese: String): Chef[Pizza with Cheese] = Chef(ingredients :+ cheese)
  def addDough: Chef[Pizza with Dough] = Chef(ingredients :+ "dough")
  def addTopping(topping: String): Chef[Pizza with Topping] = Chef(ingredients :+ topping)
  def build(implicit ev: Pizza =:= FullPizza): Food = {
    Food(ingredients)
  }
}

Chef().addTopping("tomato").addDough.addCheese("cheddar").build