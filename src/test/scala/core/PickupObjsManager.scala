package core

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PickupObjsManager extends AnyFlatSpec with Matchers {
    it should "add an object correctly" in {
        val manager = new core.model.PickupObjsManager(10, 10)
        manager.addObject(utils.Position(0, 0))
        manager.isObjectAt(utils.Position(0, 0)) should be(true)
    }
}
