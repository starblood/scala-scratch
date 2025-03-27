package study.monad

/**
  * 2016. 9. 27..
  */
object MonadExamples {
  trait User {
    val child: Option[User]
  }

  object UserService {
    val db = Map.empty[String, User]

    def loadUser(name: String): Option[User] = {
      println(s"load user: $name")
      db.get(name)
    }
  }

  val getChild = (user: User) => user.child

  def main(args: Array[String]): Unit = {
    val f = (i: Int) => List("pred=" + (i - 1), "succ=" + (i + 1))

    val list = List(5,6,7)
    println(list.flatMap(f))

    val result = UserService.loadUser("mike")
      .flatMap(getChild)
      .flatMap(getChild)
  }
}

