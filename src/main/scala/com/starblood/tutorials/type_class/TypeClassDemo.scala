package study.type_class

import study.type_class.ShoppingCart.{ProductId, Quantity}

/*  refer:
    type class example: http://blog.leifbattermann.de/2017/03/25/what-are-scala-type-classes/
    monoid: http://www.bench87.com/content/22
 */
trait Combinable[A] {
  def empty: A
  def combine(a: A, b: A): A
  def combineAll(list: List[A]) = list.fold(empty)(combine)
}

object Combinable {
  implicit val intCombinableInstance: Combinable[Int] = Combinable.instance(0, _ + _)
  implicit val stringCombinableInstance: Combinable[String] = Combinable.instance("", _ + _)
  implicit val booleanOrCombinableInstance: Combinable[Boolean] = Combinable.instance(false, _ || _)
  implicit val booleanAndCombinableInstance: Combinable[Boolean] = Combinable.instance(true, _ && _)

  // type parameter A, B 를 받기 위해서 def key 로 만들어야 한다. scala compiler 가 인식할 수 있도록
  implicit def mapCombinableInstance[A,B](implicit combinable: Combinable[B]): Combinable[Map[A,B]] = {
    def merge(map1: Map[A,B], map2: Map[A,B]): Map[A,B] = {
      (map1.keys ++ map2.keys).toList.distinct.map { key =>
        (key, combinable.combine(map1.getOrElse(key, combinable.empty), map2.getOrElse(key, combinable.empty)))
      }.toMap
    }
    instance(Map.empty[A,B], merge)
  }
  def apply[A](implicit comb: Combinable[A]): Combinable[A] = comb
  def instance[A](emptyValue: A, combineFunc: (A, A) => A): Combinable[A] = {
    new Combinable[A] {
      override def empty: A = emptyValue
      override def combine(a: A, b: A): A = combineFunc(a, b)
    }
  }
}

case class ShoppingCart(items: Map[ProductId, Quantity])
object ShoppingCart {
  type ProductId = String
  type Quantity = Int
  implicit val shoppingCartCombinableInstance: Combinable[ShoppingCart] = Combinable.instance(
    ShoppingCart(Map()), (cart1, cart2) => ShoppingCart(Combinable[Map[ProductId, Quantity]].combine(cart1.items, cart2.items))
  )
}

object TypeClassDemo {
  def main(args: Array[String]): Unit = {
    println(Combinable[Int].empty)
    println(Combinable[Map[String,Int]].empty)
    println(Combinable[Map[Int,Map[Int,Map[Int,Int]]]].empty)

    val carts = List(
      ShoppingCart(Map("p0001" -> 1, "p0002" -> 3)),
      ShoppingCart(Map("p0001" -> 4, "p0004" -> 6))
    )

    println(Combinable[ShoppingCart].combineAll(carts))
  }
}