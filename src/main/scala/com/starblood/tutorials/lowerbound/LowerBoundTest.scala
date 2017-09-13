package com.starblood.tutorials.lowerbound

/**
 * User: "john.shim"
 * Date: 15. 3. 9.
 */
case class ListNode[+T](h: T, t: ListNode[T]) {
  def head: T = h
  def tail: ListNode[T] = t
  def prepend[U >: T](elem: U): ListNode[U] =
    ListNode(elem, this)
}

object LowerBoundTest extends App {
  // refer to Unified Types
  val empty: ListNode[Null] = ListNode(null, null)
  val strList: ListNode[String] = empty.prepend("hello").prepend("world")
  val anyList: ListNode[Any] = strList.prepend(12345)
  val intList = anyList.prepend(1)
}

