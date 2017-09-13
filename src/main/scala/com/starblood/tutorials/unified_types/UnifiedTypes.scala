package scala_tutorial.unified_types

/**
 * User: "john.shim"
 * Date: 15. 3. 9.
 */
object UnifiedTypes extends App {
  val set = new scala.collection.mutable.LinkedHashSet[Any]
  set += "This is a string"  // add a string
  set += 732                 // add a number
  set += 'c'                 // add a character
  set += true                // add a boolean value
  set += main _              // add the main function
  val iter: Iterator[Any] = set.iterator
  while (iter.hasNext) {
    println(iter.next.toString())
  }
}