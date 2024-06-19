package pos

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import utils.Position
import utils.Direction
import pos.model.ParticleBuilder

class ParticleSpec extends AnyFlatSpec with Matchers {

    val particle = ParticleBuilder()
        .withId(1)
        .withPosition(Position(0, 0))
        .withVelocity(Position(1, 1))
        .withPersonalBest(Position(2, 2))
        .withGlobalBest(Position(3, 3))
        .build()

    "A Particle" should "be created correctly" in {
        particle.pos should be(Position(0, 0))
        particle.state.velocity should be(Position(1, 1))
        particle.best.personalBest should be(Position(2, 2))
        particle.best.globalBest should be(Position(3, 3))
    }

    it should "be able to update velocity based on PSO equations" in {
        val w = 0.5
        val c1 = 1.5
        val c2 = 1.5
        val r1 = 0.5 
        val r2 = 0.5

        val expectedNewVelocity = Position(
            (w * particle.state.velocity.x + c1 * r1 * (particle.best.personalBest.x - particle.state.pos.x) + c2 * r2 * (particle.best.globalBest.x - particle.state.pos.x)).toInt,
            (w * particle.state.velocity.y + c1 * r1 * (particle.best.personalBest.y - particle.state.pos.y) + c2 * r2 * (particle.best.globalBest.y - particle.state.pos.y)).toInt
        )

        particle.move()

        particle.state.velocity shouldEqual expectedNewVelocity
    }

    it should "not move if velocity is zero" in {
        particle.state = particle.state.copy(velocity = Position(0, 0))
        particle.move()
        particle.state.pos shouldEqual Position(0, 0)
    }

    it should "handle extreme position values correctly" in {
        particle.state = particle.state.copy(pos = Position(Int.MaxValue, Int.MaxValue), velocity = Position(1, 1))
        particle.best = particle.best.copy(personalBest = Position(Int.MaxValue, Int.MaxValue), globalBest = Position(Int.MaxValue, Int.MaxValue))

        particle.move()

        val expectedNewPos = Position(Int.MaxValue, Int.MaxValue)
        particle.state.pos shouldEqual expectedNewPos
    }
}
