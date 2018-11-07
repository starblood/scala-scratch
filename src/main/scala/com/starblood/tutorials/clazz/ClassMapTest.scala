package com.starblood.tutorials.clazz

object ClassMapTest {

  class Foo(val map: scala.collection.mutable.Map[String, Int]) {
    def update(key: String, value: Int): Unit = {
      map += (key -> value)
    }
    override def toString(): String = {
      map.toString()
    }
  }
  def updateCount(foo: Foo, key: String, count: Int): Foo = {
    val prevCount = foo.map.getOrElse(key, 0)
    foo.map += (key -> (prevCount + count))
    foo
  }

  class Quota(val hostCount: scala.collection.mutable.Map[String, Long]) {
    override def toString: String = {
      hostCount.toString()
    }
  }
  case class QuotaResult(key: String, quota: Quota)

  class Bar {
    val key = "bar"
    val result: QuotaResult = QuotaResult(key, new Quota(scala.collection.mutable.Map[String, Long](("host1", 0L))))

    def updateHostCount(host: String, count: Long) = {
      val prevCount = result.quota.hostCount.getOrElse(host, 0L)
      result.quota.hostCount += (host -> (prevCount + count))
    }
    override def toString(): String = {
      result.toString
    }
  }
  sealed abstract class Request {
    var count: Long
    var startTime: Long
    var curTime: Long
    var reqPerSec: Double
  }
  class ReadRequest(var count: Long, var startTime: Long, var curTime: Long, var reqPerSec: Double) extends Request {
    override def toString: String = s"count: ${count}"
  }
  class WriteRequest(var count: Long, var startTime: Long, var curTime: Long, var reqPerSec: Double) extends Request
  case class Zoo(req: Request)

  def main(args: Array[String]): Unit = {
    test1()

    val bar = new Bar
    bar.updateHostCount("host1", 1L)
    println(bar)
    bar.updateHostCount("host2", 2L)
    println(bar)

    val zoo = Zoo(new ReadRequest(0, 0, 0, 0))
    val request = zoo.req
    request.count = 2
    println(zoo)
    request.count += 3
    println(zoo)
  }

  private def test1() = {
    val foo = new Foo(scala.collection.mutable.Map.empty[String, Int])
    foo.update("a", 1)
    foo.update("b", 2)
    println(foo)

    foo.map += ("c" -> 3)
    println(foo)

    val map = scala.collection.mutable.Map[String, Long](("a", 1), ("b", 2), ("c", 3))
    println(s"map: ${map}")

    updateCount(foo, "a", 2)
    println(foo)
  }
}
