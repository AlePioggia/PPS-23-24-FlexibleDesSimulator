package core

import scala.collection.mutable.PriorityQueue

trait Event:
    def time: Double

trait Simulator:
    def schedule(event: Event): Unit
    def run(): Unit
    def handleEvent(event: Event): Unit

class BasicSimulator extends Simulator:
    private var currentSimTime = 0.0
    private val eventQueue = PriorityQueue.empty[Event](Ordering.by(_.time))

    def schedule(event: Event): Unit =
        eventQueue.enqueue(event)
    
    def run(): Unit = 
        while eventQueue.nonEmpty do 
            val nextEvent = eventQueue.dequeue()
            currentSimTime = nextEvent.time
            handleEvent(nextEvent)
    
    def handleEvent(event: Event): Unit = ()
