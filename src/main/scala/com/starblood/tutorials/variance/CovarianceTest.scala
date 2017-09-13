package com.starblood.tutorials.variance

class Company[T](val company:T)

class BigCompany
class SmallCompany extends BigCompany
class Investor(val company: Company[BigCompany])

object CovarianceTest extends App {
  val littleCompany: Company[SmallCompany] = new Company[SmallCompany](new SmallCompany)
  val bigCompany: Company[BigCompany] = new Company[BigCompany](new BigCompany)
  val bigInvestor:Investor = new Investor(bigCompany)

  // compile error, need to define class Company[+T](val company:T)
//  val smallInvestor:Investor = new Investor(littleCompany)
}
