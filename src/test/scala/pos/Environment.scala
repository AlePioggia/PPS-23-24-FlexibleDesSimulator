package pos

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import pos.model.{Particle, ParticleBuilder, State, Best}
import utils.Position
import pos.model.PosEnvironment

class PosEnvironmentSpec extends AnyFlatSpec with Matchers {
    val fitnessFunction: Position => Double = pos => 
        math.sqrt(math.pow(pos.x - 5, 2) + math.pow(pos.y - 5, 2))
    var env: PosEnvironment = _
    var particle: Particle = _

    def initialize(): Unit =
        particle = ParticleBuilder()
            .withId(1)
            .withPosition(Position(2, 2))
            .withVelocity(Position(1, 1))
            .withPersonalBest(Position(3, 3))
            .withPersonalBestFitness(fitnessFunction(Position(3, 3)))
            .withGlobalBest(Position(4, 4))
            .build()

        env = new PosEnvironment(10, 10)(fitnessFunction)
        env.globalBest = Position(4, 4)
        env.globalBestFitness = fitnessFunction(Position(4, 4))
        env.agentManager.placeAgentAt(particle, Position(2, 2))

    it should "be initialized correctly" in {
        initialize()
        env.width should be (10)
        env.height should be (10)
        env.globalBest should be (Position(4, 4))
        env.globalBestFitness should be (fitnessFunction(Position(4, 4)))
    }

    it should "update personal best correctly in preMoveActions" in {
        initialize()
        val oldPersonalBest = particle.best.personalBest
        val oldPersonalBestFitness = particle.best.personalBestFitness

        env.preMoveActions(particle)
        particle.state.pos should be (Position(4, 4))
    }


    it should "update global best correctly in postMoveActions" in {
        initialize()
        val newPosition = Position(1, 1)
        particle.state.pos = newPosition
        env.postMoveActions(particle)

        val newFitness = fitnessFunction(newPosition)
        if (newFitness < env.globalBestFitness) {
            env.globalBest should be (newPosition)
            env.globalBestFitness should be (newFitness)
        } else {
            env.globalBest should be (Position(4, 4))
            env.globalBestFitness should be (fitnessFunction(Position(4, 4)))
        }
    }
    
    it should "bound position correctly" in {
        initialize()
        val pos = Position(11, 11)
        val expectedPos = Position(9, 9)

        env.boundPosition(pos) should be (expectedPos)
    }

    it should "move agent correctly using moveAgent" in {
        initialize()
        env.moveAgent(particle)

        val expectedPos = Position(5, 5)
        particle.state.pos should be (expectedPos)
        env.agentManager.isPositionValid(expectedPos) should be (true)
    }

    it should "not move agent out of bounds" in {
        initialize()
        val initialPos = Position(9, 9)
        particle.state.pos = initialPos
        particle.state.velocity = Position(1, 1)

        env.moveAgent(particle)

        val expectedPos = Position(9, 9) 
        particle.state.pos should be (expectedPos)
    }
}
