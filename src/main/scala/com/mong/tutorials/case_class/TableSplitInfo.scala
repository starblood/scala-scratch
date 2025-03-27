package study.case_class

case class TableSplitInfo private (allowPreSplit: Boolean, preSplitSize: Option[Int])
object TableSplitInfo {
  def create(allowPreSplit: Boolean, preSplitSize: Option[Int]): TableSplitInfo = {
    TableSplitInfo(allowPreSplit, Some(10))
  }
}

object MainApp {
  def main(args: Array[String]): Unit = {
    val tableSplitInfo = TableSplitInfo.create(true, Some(2))

    println(tableSplitInfo)
    println(tableSplitInfo.allowPreSplit, tableSplitInfo.preSplitSize)
  }
}