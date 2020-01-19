package actor.filecount

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.RoundRobinPool

/*
有状态Actor
 */

class FilesCounter extends Actor {

  val start: Long = System.nanoTime
  var filesCount = 0L
  var pending = 0

  val fileExplorers: ActorRef = context.actorOf(RoundRobinPool(100).props(Props[FIleExporer]))

  override def receive: Receive = {
    case dirName: String =>
      pending = pending + 1
      fileExplorers ! dirName

    case count: Int =>
      filesCount = filesCount + count
      pending = pending - 1

      if (pending == 0) {
        val end = System.nanoTime()
        println(s"Files count: $filesCount")
        println(s"Time taken: ${(end - start) / 1.0e9} seconds")
        context.system.terminate()
      }
  }
}
