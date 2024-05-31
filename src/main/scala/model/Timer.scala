package model

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

trait Timer {

    def start(): Unit
    def stop(): Unit
    def reset(): Unit
    def getElapsedTime: Double
    def measureTime(time: Double): Future[Boolean]
  
}

class TimerAddon extends Timer {

    private var startTime: Option[Long] = None
    private var elapsedTime: Long = 0

    override def start(): Unit = {

        if(startTime.isEmpty) startTime = Some(System.nanoTime())

    }

    override def stop(): Unit = {

        startTime match {

            case Some(start) => elapsedTime += System.nanoTime() - start
            startTime = None
            case _ => //Timer aus

        }

    }

    override def reset(): Unit = {

        startTime = None
        elapsedTime = 0

    }

    override def getElapsedTime: Double = {
        
        val elapsedNanos = startTime match {
            case Some(start) => elapsedTime + (System.nanoTime() - start)
            case None => elapsedTime
        }
        elapsedNanos / 1e9
    }

    override def measureTime(time: Double): Future[Boolean] = {

        Future{

            start()
            val targetTime = (time * 1e9).toLong
            while (getElapsedTime < targetTime) {

                Thread.sleep(10)

            }

            stop()
            true

        }

    }


}
