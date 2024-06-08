package core

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import utils.Position

class PickupObjsManager extends AnyFlatSpec with Matchers {
    it should "add an object correctly" in {
        val manager = new core.model.PickupObjsManager(10, 10)
        manager.addObject(utils.Position(0, 0))
        manager.isObjectAt(utils.Position(0, 0)) should be(true)
    }

    it should "remove an object accordingly" in {
        val manager = new core.model.PickupObjsManager(10, 10)
        manager.addObject(utils.Position(0, 0))
        manager.isObjectAt(utils.Position(0, 0)) should be(true)
        manager.removeObject(utils.Position(0, 0))
        manager.isObjectAt(utils.Position(0, 0)) should be(false)
    }

    it should "return objects as a list of positions" in {
        val manager = new core.model.PickupObjsManager(10, 10)
        manager.addObject(Position(0, 0))
        manager.addObject(Position(0, 1))
        manager.addObject(Position(1, 0))
        manager.addObject(Position(1, 1))
        manager.objsPosList should contain theSameElementsAs 
            List(Position(0, 0), Position(0, 1), Position(1, 0), Position(1, 1))
    }
}
