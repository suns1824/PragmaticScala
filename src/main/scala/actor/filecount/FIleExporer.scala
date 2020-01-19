package actor.filecount

import java.io.File

import akka.actor.Actor

/*
无状态actor
 */

class FIleExporer extends Actor{
  override def receive: Receive = {
    case dirName: String =>
      val file = new File(dirName)
      val children = file.listFiles()
      var filesCount = 0

      if (children != null) {
        children.filter{_.isDirectory}
          .foreach {
            sender ! _.getAbsolutePath
          }
        filesCount = children.count{ !_.isDirectory}
      }
      sender ! filesCount
  }
}
