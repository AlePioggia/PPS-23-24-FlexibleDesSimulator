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
        particle.velocity should be(Position(1, 1))
        particle.best.personalBest should be(Position(2, 2))
        particle.best.globalBest should be(Position(3, 3))
    }

    it should "not move if velocity is zero" in {
        particle.velocity = Position(0, 0)
        particle.moveTo(Position(1, 1))
        particle.pos shouldEqual Position(0, 0)
    }

}
