package actor

import java.io.File

object CountFilesSeqential extends App {

  def getChildren(file: File) = {
    val children = file.listFiles()
    if (children != null) children.toList else List()
  }
  val start = System.nanoTime
  val exploreFrom = new File(args(0))

  var count = 0L
  var filesToVisit = List(exploreFrom)
  while (filesToVisit.nonEmpty) {
    val head = filesToVisit.head
    filesToVisit = filesToVisit.tail

    val children = getChildren(head)
    count = count + children.count {!_.isDirectory}
    filesToVisit = filesToVisit ::: children.filter {_.isDirectory}
  }
  val end = System.nanoTime()
  println(s"files count: $count")
  println(s"time taken: ${(end - start) / 1.0e9} seconds")
}
