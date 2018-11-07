package com.starblood.algorithms.recursive

object CountNestedConnect {
  case class ScanFilter(field: String)
  case class ConnectTarget(id: String, filter: Option[ScanFilter] = None)
  case class Connect(id: String, target: ConnectTarget, connects: Option[List[Connect]] = None)

  def countConnectSize2(conn: Connect): Long = {
    def countConnects(sum: Long = 0, conns: Option[List[Connect]]): Long = {
      if (conns.isEmpty) sum
      else {
        val children = conns.get
        sum + children.size + children.foldLeft(0L){ (count, connect) =>
          count + countConnects(0, connect.connects)
        }
      }
    }
    countConnects(1, conn.connects)
  }

  def countConnectSize(conn: Connect): Long = {
    def countConnects(sum: Long = 0, conns: Option[List[Connect]]): Long = {
      if (conns.isEmpty) sum
      else {
        val children = conns.get
        sum + children.size + children.foldLeft(0L){ (count, connect) =>
          val scanCost = if (connect.target.filter.isDefined) 5 else 0
          count + scanCost + countConnects(0, connect.connects)
        }
      }
    }
    val initialCost = if (conn.target.filter.isDefined) 5 else 0
    countConnects(1 + initialCost, conn.connects)
  }

  def main(args: Array[String]): Unit = {
    // 1 -> (2 -> (3, 4), 5, 6) => 1 + (1 + 2) + 2
    val connect1 =
      Connect(id = "1", ConnectTarget(id = "1"), Some(
        List(
          Connect(id = "2", ConnectTarget(id = "2"), Some(
            List(
              Connect(id = "3", ConnectTarget(id = "3")),
              Connect(id = "4", ConnectTarget(id = "4"))
            )
          )),
          Connect(id = "5", ConnectTarget(id = "5")),
          Connect(id = "6", ConnectTarget(id = "6"))
        )
      )
    )

    val connect2 =
      Connect(id = "1", ConnectTarget(id = "1", filter = Some(ScanFilter(field = "foo"))), Some(
        List(
          Connect(id = "2", ConnectTarget(id = "2"), Some(
            List(
              Connect(id = "3", ConnectTarget(id = "3")),
              Connect(id = "4", ConnectTarget(id = "4")),
              Connect(id = "5", ConnectTarget(id = "5", filter = Some(ScanFilter(field = "bar"))), Some(
                List(
                  Connect(id = "6", ConnectTarget(id = "6")),
                  Connect(id = "7", ConnectTarget(id = "7"))
                ))
              )
            )
          )),
          Connect(id = "8", ConnectTarget(id = "8")),
          Connect(id = "9", ConnectTarget(id = "9"))
        )
      ))

    val count1 = countConnectSize(connect1)
    println(s"expected: 6, actual: ${count1}")

    val count2 = countConnectSize(connect2)
    println(s"expected: 19, actual: ${count2}")
  }
}
