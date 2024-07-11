package core.simulator

import scala.collection.mutable.PriorityQueue

enum State:
    case Running, Stopped, Ended

trait Event:
    def time: Double

trait Simulator:
    var currentStep: Int = 0
    var startTime: Long = System.currentTimeMillis()
    var state: State = State.Stopped
    def schedule(event: Event): Unit
    def run(): Unit
    def step(): Unit
    def handleEvent(event: Event): Unit
    def getElapsedTime: Long

class BasicSimulator extends Simulator:
    private var currentSimTime = 0.0
    private val eventQueue = PriorityQueue.empty[Event](Ordering.by(_.time))

    def schedule(event: Event): Unit =
        eventQueue.enqueue(event)
    
    def run(): Unit = 
        while eventQueue.nonEmpty do
            step()

    def step(): Unit = 
        if eventQueue.nonEmpty && !shouldStop then
            val nextEvent = eventQueue.dequeue()
            currentSimTime = nextEvent.time
            handleEvent(nextEvent)
            updateStats()
        else 
            state = State.Ended
    
    def handleEvent(event: Event): Unit = ()

    def shouldStop: Boolean = false

    def updateStats(): Unit =
        currentStep += 1

    def getElapsedTime: Long =
        System.currentTimeMillis() - startTime
