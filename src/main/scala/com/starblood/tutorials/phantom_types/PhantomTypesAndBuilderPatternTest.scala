package com.starblood.tutorials.phantom_types


/*  phantom type for builder
    https://medium.com/@maximilianofelice/builder-pattern-in-scala-with-phantom-types-3e29a167e863 */
case class Food(ingredients: Seq[String])

object Chef {
  sealed trait Pizza
  object Pizza {
    sealed trait EmptyPizza extends Pizza
    sealed trait Cheese extends Pizza
    sealed trait Dough extends Pizza
    sealed trait Topping extends Pizza

    type FullPizza = EmptyPizza with Cheese with Dough with Topping
  }
  def apply[T <: Pizza](ingredients: Seq[String]): Chef[T] = new Chef[T](ingredients)

  def apply(): Chef[Pizza.EmptyPizza] = apply[Pizza.EmptyPizza](Seq())
}

class Chef[Pizza <: Chef.Pizza](ingredients: Seq[String]) {
  import Chef.Pizza._
  def addCheese(cheeseType: String): Chef[Pizza with Cheese] = new Chef(ingredients :+ cheeseType)
  def addTopping(toppingType: String): Chef[Pizza with Topping] = new Chef(ingredients :+ toppingType)
  def addDough: Chef[Pizza with Dough] = new Chef(ingredients :+ "dough")

  def build(implicit ev: Pizza =:= FullPizza): Food = {
    Food(ingredients)
  }
}


object PhantomTypesAndBuilderPatternTest {
  def main(args: Array[String]): Unit = {
//    Chef().addTopping("olives").build // won't compile

    Chef().addDough.addCheese("mozzarella").addTopping("tomatoes").build
  }
}
