package study.monad

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * 2016. 9. 28..
  */
object FutureMonadExamples {
  trait Order
  trait Item
  trait PurchaseResult
  trait LogResult

  object OrderService {
    def loadOrder(userName: String): Future[Order] = Future { new Order {}}
  }
  object ItemService {
    def loadItem(order: Order): Future[Item] = Future { new Item {} }
  }
  object PurchasingService {
    def purchaseItem(item: Item): Future[PurchaseResult] = Future { new PurchaseResult {} }
    def logPurchase(purchaseResult: PurchaseResult): Future[LogResult] = Future { new LogResult {} }
  }


  def main(args: Array[String]): Unit = {
    val loadItem: Order => Future[Item] = {
      order => ItemService.loadItem(order)
    }

    val purchaseItem: Item => Future[PurchaseResult] = {
      item => PurchasingService.purchaseItem(item)
    }

    val logPurchase: PurchaseResult => Future[LogResult] = {
      purchaseResult => PurchasingService.logPurchase(purchaseResult)
    }

    val result =
      OrderService.loadOrder("john")
      .flatMap(loadItem)
      .flatMap(purchaseItem)
      .flatMap(logPurchase)
  }
}
