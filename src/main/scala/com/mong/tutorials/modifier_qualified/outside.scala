package study.modifier_qualified

/**
  * Created by starblood on 2017. 1. 25..
  * 참고: http://www.jesperdj.com/2016/01/08/scala-access-modifiers-and-qualifiers-in-detail/
  */
package outside {
  package inside {
    object Messages {
      // Accessible up to package 'inside'
      private[inside] val Insiders = "Hello Friends"

      // Accessible up to package 'outside'
      private[outside] val Outsiders = "Hello People"
    }

    object InsideGreeter {
      def sayHello(): Unit =
      // Can access both messages
        println(Messages.Insiders + " and " + Messages.Outsiders)
    }
  }

  object OutsideGreeter {
    def sayHello(): Unit =
    // Can only access the 'Outsiders' message
      println(inside.Messages.Outsiders)
  }
}

class Counter {
  // Normal private member variable
  private var total = 0

  // Object-private member variable
  private[this] var lastAdded = 0

  def add(n: Int): Unit = {
    total += n
    lastAdded = n
  }

  def copyFrom(other: Counter): Unit = {
    // OK, private member from other instance is accessible
    total = other.total

    // ERROR, object-private member from other instance is not accessible
//    lastAdded = other.lastAdded
  }
}