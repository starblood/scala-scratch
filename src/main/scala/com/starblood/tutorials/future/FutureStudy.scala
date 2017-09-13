package study.future

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FutureStudy {

  // Scala Option[Future[T]] to Future[Option[T]]
  def swap[M](x: Option[Future[M]]): Future[Option[M]] =
    Future.sequence(Option.option2Iterable(x)).map(_.headOption)
}
