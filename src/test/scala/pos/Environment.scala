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
            .withPosition(Position(0, 0))
            .withVelocity(Position(1, 1))
            .withPersonalBest(Position(0, 0))
            .withGlobalBest(Position(0, 0))
            .build()

        env = new PosEnvironment(10, 10)(fitnessFunction)
        env.agentManager.placeAgentAt(particle, Position(0, 0))

    it should "be initialized correctly" in {
        initialize()
        env.width should be (10)
        env.height should be (10)
        env.globalBest should be (Position(Int.MaxValue, Int.MaxValue))
        env.globalBestFitness should be (Double.MaxValue)
    }

    it should "update personal best correctly in preMoveActions" in {
        initialize()
        env.preMoveActions(particle)
        particle.best.personalBest should be (particle.pos)
    }
}
