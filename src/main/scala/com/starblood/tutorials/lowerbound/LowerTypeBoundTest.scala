package com.starblood.tutorials.lowerbound

object LowerTypeBoundTest {
  def main(args: Array[String]): Unit = {
    case class ListNode[+T](h: T, t: ListNode[T]) {
      def head: T = h
      def tail: ListNode[T] = t
      def prepend[U >: T](elem: U): ListNode[U] =
        ListNode(elem, this)
    }



    val empty: ListNode[Null] = ListNode(null, null)
    val strList: ListNode[String] = empty.prepend("hello")
      .prepend("world")
    val anyList: ListNode[Any] = strList.prepend(12345)


  }
}
