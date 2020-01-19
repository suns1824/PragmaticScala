package actor.filecount

import akka.actor.{ActorSystem, Props}

object CountFIles extends App {
  val system = ActorSystem("sample")
  val filesCounter = system.actorOf(Props[FilesCounter])
  filesCounter ! "/Applications"
}
