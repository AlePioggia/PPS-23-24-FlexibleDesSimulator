package robotswarm

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import robotswarm.model.Robot
import utils.Position
import utils.Direction
import robotswarm.model.Battery
import robotswarm.model.ai.TaskAllocator

class TaskAllocatorSpec extends AnyFlatSpec with Matchers {
    it should "assign a task to each robot to the closest object" in {
        val robots = Set(Robot(1)(Position(0, 0))(Direction.North), Robot(2)(Position(2, 2))(Direction.South))
        val objects = List(Position(1, 1), Position(3, 3))

        val assignments = TaskAllocator.assignTasks(robots, objects)

        assignments should have size 2

        val iterator: Iterator[Robot] = robots.iterator

        assignments(iterator.next()) shouldEqual Position(1, 1)
        assignments(iterator.next()) shouldEqual Position(3, 3)
    }

    it should "handle no robots" in {
        val robots = Set.empty[Robot]
        val objects = List(Position(1, 1), Position(3, 3))

        val assignments = TaskAllocator.assignTasks(robots, objects)

        assignments shouldEqual Map.empty
    }

    it should "handle no objects" in {
        val robots = Set(Robot(1)(Position(0, 0))(Direction.North), Robot(2)(Position(2, 2))(Direction.South))
        val objects = List.empty[Position]

        val assignments = TaskAllocator.assignTasks(robots, objects)

        assignments shouldEqual Map.empty
    }
}