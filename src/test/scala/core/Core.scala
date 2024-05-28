package core

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import restaurant.simulator.{ArrivalEvent, Customer, DepartureEvent, RestaurantSimulator}

class RestaurantSimulatorSpec extends AnyFlatSpec with Matchers {
  "A RestaurantSimulator" should "correctly handle arrival and departure events" in {
    val simulator = new RestaurantSimulator
    val customer1 = Customer(1)
    val customer2 = Customer(2)

    simulator.schedule(ArrivalEvent(1.0, customer1))
    simulator.schedule(ArrivalEvent(2.0, customer2))
    simulator.run()

    simulator.numCustomers should be(2)

    simulator.schedule(DepartureEvent(3.0, customer1))
    simulator.schedule(DepartureEvent(4.0, customer2))
    simulator.run()

    simulator.numCustomers should be(0)
  }

  it should "correctly handle departure events before arrival events" in {
    val simulator = new RestaurantSimulator
    val customer = Customer(1)

    simulator.schedule(DepartureEvent(1.0, customer))
    simulator.run()

    simulator.numCustomers should be(0)

    simulator.schedule(ArrivalEvent(2.0, customer))
    simulator.run()

    simulator.numCustomers should be(1)
  }

  it should "correctly handle events at the same time" in {
    val simulator = new RestaurantSimulator
    val customer1 = Customer(1)
    val customer2 = Customer(2)

    simulator.schedule(ArrivalEvent(1.0, customer1))
    simulator.schedule(ArrivalEvent(1.0, customer2))
    simulator.run()

    simulator.numCustomers should be(2)
  }
}
