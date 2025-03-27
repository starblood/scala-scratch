package scala_tutorial.variances

/**
 * User: "john.shim"
 * Date: 15. 3. 9.
 */

class Person(val name: String) {
  override def toString = name
}

class Employee(override val name: String) extends Person(name)

class SomeCollection[T]
class SomeCollection2[+T]

object VarianceTest {
  def main(args: Array[String]): Unit = {
    def sayHi[T](people: List[T]) = people.map {
      println _
    }

    def sayHi2[T <: Person](people: List[Person]) = people map {println}

    def appendToPerson[T, D <: T](employees: List[T], personList: List[D]) = personList ++ employees

    val empList = List(new Employee("Joao"), new Employee("Andre"))
    val peopleList = List(new Person("Martin"), new Person("Jonas"))
    println("----")
    sayHi(empList)
    sayHi(peopleList) // compilation ERROR

    //  println("----")
    //  sayHi2(empList)
    //
    //  println("----")
    //  sayHi2(peopleList)
    //
    //  val list1 = new SomeCollection[Employee]
    ////  val list2: SomeCollection[Person] = list1 // compile error
    //
    //  val list3 = new SomeCollection2[Employee]
    //  val list4: SomeCollection2[Person] = list3
    //
    //  val list5 = appendToPerson(empList, peopleList)
    //  println("----")
    //  sayHi2(list5)

    class Company[T](val company:T)
    class BigCompany
    class SmallCompany extends BigCompany
    class Investor(val company: Company[BigCompany])

    val littleCompany: Company[SmallCompany] = new Company[SmallCompany](new SmallCompany)
    val bigCompany: Company[BigCompany] = new Company[BigCompany](new BigCompany)
    val bigInvestor:Investor = new Investor(bigCompany)

//    val smallInvestor:Investor = new Investor(littleCompany)
  }
}
