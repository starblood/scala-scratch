package com.starblood.tutorials.trait_test

object TraitTest {
  def main(args: Array[String]): Unit = {
    trait Animal {
      def eat(x: Int): Unit
    }

    trait Bird extends Animal {
      override def eat(x: Int) = println(s"${x} by Bird")
    }

    trait Monkey extends Animal {
      override def eat(x: Int) = println(s"${x} by Monkey")
    }

    class HybridAnimal extends Monkey with Bird {

    }

  }
}
