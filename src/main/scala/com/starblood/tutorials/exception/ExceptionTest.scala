package study.exception

import java.io.{FileNotFoundException, FileReader, IOException, InputStream}
import java.net.{MalformedURLException, URL}

import scala.io.Source
import scala.util.{Failure, Success, Try}


object ExceptionTest {
  def main(args: Array[String]) {
    val f = new FileReader("input.txt")
    f.read()

    try {
      val f = new FileReader("input.txt")
    } catch {
      case ex: FileNotFoundException => {
        println("Missing file exception")
      }
      case ex: IOException => {
        println("IO Exception")
      }
    }

    case class Customer(age: Int)

    class Cigarettes

    case class UnderAgeException(message: String) extends Exception(message)

    def buyCigarettes(customer: Customer): Cigarettes =
      if (customer.age < 16)
        throw UnderAgeException(s"Customer must be older than 16 but was ${customer.age}")
      else new Cigarettes

    val youngCustomer = Customer(15)
    try {
      buyCigarettes(youngCustomer)
      "Yo, here are your cancer sticks! Happy smokin'!"
    } catch {
      case UnderAgeException(msg) => msg
    }

    def parseURL(url: String): Try[URL] = Try(new URL(url))

    val url1 = "http://www.daum.net"
    val url2 = "htta:/asdf.kkk"
    parseURL(url1)
    parseURL(url2)

    val url = parseURL(Console.readLine("URL: ")) getOrElse new URL("http://duckduckgo.com")

    parseURL("http://danielwestheide.com").map(_.getProtocol)
    // results in Success("http")
    parseURL("garbage").map(_.getProtocol)
    // results in Failure(java.net.MalformedURLException: no protocol: garbage)



    def inputStreamForURL(url: String): Try[Try[Try[InputStream]]] = parseURL(url).map { u =>
      Try(u.openConnection()).map(conn => Try(conn.getInputStream))
    }

    def inputStreamForURLByFlatMap(url: String): Try[InputStream] = parseURL(url).flatMap { u =>
      Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream))
    }

    def parseHttpURL(url: String) = parseURL(url).filter(_.getProtocol == "http")
    parseHttpURL("http://apache.openmirror.de") // results in a Success[URL]
    parseHttpURL("ftp://mirror.netcologne.de/apache.org") // results in a Failure[URL]
    parseHttpURL("http://danielwestheide.com").foreach(println)



    def getURLContent(url: String): Try[Iterator[String]] =
      for {
        url <- parseURL(url)
        connection <- Try(url.openConnection())
        is <- Try(connection.getInputStream)
        source = Source.fromInputStream(is)
      } yield source.getLines()

    val result = getURLContent("http://www.daum.net")
    result match {
      case Success(lines) => lines.foreach(println)
      case _ => println("failed")
    }

    getURLContent("http://danielwestheide.com/foobar") match {
      case Success(lines) => lines.foreach(println)
      case Failure(ex) => println(s"Problem rendering URL content: ${ex.getMessage}")
    }

    val content = getURLContent("garbage") recover {
      case e: FileNotFoundException => Iterator("Requested page does not exist")
      case e: MalformedURLException => Iterator("Please make sure to enter a valid URL")
      case _ => Iterator("An unexpected error has occurred. We are so sorry!")
    }
  }
}
