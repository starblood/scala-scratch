package com.starblood.tutorials.implicits;

/**
 * Created by john.shim on 2015. 3. 24..
 */
object StringUtils {
  implicit class StringImprovements(val s: String) {
    def increment = s.map(c => (c + 1).toChar)
  }
}
