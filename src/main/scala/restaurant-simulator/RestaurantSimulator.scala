package restaurant.simulator

import core.simulator.{Event, BasicSimulator}

case class Customer(id: Int)
case class ArrivalEvent(time: Double, customer: Customer) extends Event
case class DepartureEvent(time: Double, customer: Customer) extends Event

class RestaurantSimulator extends BasicSimulator:
    var numCustomers = 0
    var currentCustomers: Set[Customer] = Set[Customer]()

    override def handleEvent(event: Event): Unit =
        event match
            case ArrivalEvent(_, customer) => {numCustomers += 1; currentCustomers += customer}
            case DepartureEvent(_, customer) => if currentCustomers.contains(customer) then {numCustomers -= 1; currentCustomers -= customer}

