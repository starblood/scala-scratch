package scala_tutorial.reflection_test

import scala.reflect.runtime._
import scala.tools.reflect.ToolBox
import scala.reflect.api.Trees

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.reflect.runtime.universe._

/**
 * Created by john.shim on 2015. 3. 30..
 */
object ToolBoxTest extends App {
  def eval(code: String) = {
    val toolbox = currentMirror.mkToolBox()
    toolbox.eval(toolbox.parse(code))
  }

  val cm = universe.runtimeMirror(getClass.getClassLoader)
  val tb = cm.mkToolBox()
  val clazz = tb.eval(tb.parse("class C; scala.reflect.classTag[C].runtimeClass"))
  println(clazz)

  val build = scala.reflect.runtime.universe.internal.reificationSupport
  val x = build.setInfo(build.newFreeTerm("x", 2), typeOf[Int])
  println(x)

  val plus2 = tb.eval(Apply(Select(Ident(x), TermName("$plus")), List(Literal(Constant(2)))))
  println(plus2)
}
