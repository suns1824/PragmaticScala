package actor

import akka.actor.Actor

import scala.collection.mutable

case class Play(role: String)
case class ReportCount(role: String)

class HolloywoodActor extends Actor{
  val messageCount: mutable.Map[String, Int] = mutable.Map()
  override def receive: Receive = {
    case message => println(s"playing the role of $message - ${Thread.currentThread()}")
    case Play(role) =>
      val currentCount = messageCount.getOrElse(role, 0)
      messageCount.update(role, currentCount + 1)
      println(s"Playing $role")
    case ReportCount(role) =>
      sender ! messageCount.getOrElse(role, 0)
  }
}

