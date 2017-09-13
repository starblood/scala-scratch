package study.actor

import akka.actor.{Actor, Props}

/**
  * Created by starblood on 2017. 3. 22..
  * As you can see the creator parameter is passed by-name and not by-value. As we recall,
  * passing a parameter by name means that it is only evaluated when it is accessed inside the method body.
  * So instead of calling the constructor of ChildActor and passing the result to Props.apply,
  * the constructor call is passed and evaluated later.
  * Why is this a problem? You can think of the creator parameter as a function with zero arity,
  * which returns as value of type T. Inside this closure, we defined a reference to the mutable variable counter.
  * The closure will be evaluated by Akka asynchronously and it is not specified whether ParentActor has already
  * received new Increment messages in the meantime, changing the value of counter.
  *
  * refer: https://blog.codecentric.de/en/2017/03/akka-best-practices-defining-actor-props/
  */
object AkkaBestPractice {

}

case object Increment
case object CreateChild

class ParentActor extends Actor {
  var counter = 0

  override def receive = {
    case Increment => counter = counter + 1
    case CreateChild => context.actorOf(ChildActor(counter), ChildActor.Name)
  }
}

class ChildActor(v: Int) extends Actor {
  println(s"v: $v")

  override def receive = {
    Actor.emptyBehavior
  }
}
/*  he Solution: Props Factory in the Companion Object
    So what’s the solution to our problem? We need to make sure that we’re not closing over the ParentActor's state
    when creating Props for ChildActor. How can we achieve this? By making sure all constructor values are accessed by-value,
    before passing them to Props.apply. Since Props always belong to a specific actor implementation,
    it makes sense to define a factory method for them in the companion object of our actors: */
object ChildActor {
  /*  Note that I also added a constant for the actor name. When defining constants final and starting with an upper case letter,
      the Scala compiler will inline them. So this is another small best practice when implementing actors. */
  final val Name = "child-actor"
  /*  This way, when we call ChildActor.apply the current value of counter is passed by-value.
      This makes sure the new ChildActor instance is created with counter at the time the CreateChild message was received. */
  def apply(v: Int): Props = Props(new ChildActor(v))

  /*  When implementing actors we need to make sure that we don’t close over some other actor’s state.
      Only if that is the case, our code will work reliably. The best way to achieve this is to define a factory method for
      Props in the actor’s companion object. By passing all actor constructor parameters to that factory method by-name,
      we can make sure that they reflect the state of the enclosing actor at the time we created the new child actor. */
}