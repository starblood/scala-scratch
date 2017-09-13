package com.starblood.algorithms


// fibonacci algorithm recursive algorithm
object Fibonacci {

  def fib(n: Int): Int = {
    if (n == 0 || n == 1) 1
    else fib(n - 2) + fib(n - 1)
  }

  def main(args: Array[String]): Unit = {
    println(fib(args(0).toInt))
  }
}
