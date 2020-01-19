package actor

import akka.actor.{ActorSystem, Props}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object CreateActors extends App {
  /*
  Akka的Actor托管在一个Actor System中，它管理了线程、消息队列以及Actor的
  生命周期。
  Actor System管理了一个线程池，只要系统保持活跃，这个线程池就会一直保持活跃。
  Akka提供了很多工具啦配置线程池大小，消息队列大小和其它许多参数，包括于远程Actor交互。
  */
  val system = ActorSystem("sample")
  val depp = system.actorOf(Props[HolloywoodActor])
  val hanks = system.actorOf(Props[HolloywoodActor])
  depp ! "Wonka"
  hanks ! "Jinx"
  //Thread.sleep(100)
  depp ! "Yangyh"
  hanks ! "Hadoop"
  println(s"Calling from ${Thread.currentThread()}")  //这里可以看出不会阻塞调用者
  val terminateFuture = system.terminate()
  Await.ready(terminateFuture, Duration.Inf)
  //Actor实际上并不会持有自己的线程，这段代码看不出。线程之于Actor类似于客服经理之于消费者。
}

/*
  ......
 */