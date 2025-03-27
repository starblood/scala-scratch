package scala_tutorial.variances

/**
 * User: "john.shim"
 * Date: 15. 3. 9.
 */
class Stack[+A] {
  def push[B >: A](elem: B): Stack[B] = new Stack[B] {
    override def top: B = elem
    override def pop: Stack[B] = Stack.this // equivalent to 'this'
    override def toString() = elem.toString() + " " +
      Stack.this.toString()
  }

  def top: A = sys.error("no element on stack")
  def pop: Stack[A] = sys.error("no element on stack")
  override def toString() = ""
}

object VariancesTest extends App {
  var s: Stack[Any] = new Stack().push("hello");
  s = s.push(new Object())
  s = s.push(7)
  println(s)

  println(s.top)
  println(s.pop)

  // explain error case
  val s2 = new Stack[Int]
  s2.top
  s2.toString()
  s2.pop
}