package pos

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import utils.Position
import utils.Direction
import pos.model.ParticleBuilder

class ParticleSpec extends AnyFlatSpec with Matchers {
    "A Particle" should "be created correctly" in {
        val particle = ParticleBuilder()
            .withId(1)
            .withPosition(Position(0, 0))
            .withDirection(Direction.North)
            .build()
        particle.pos should be(Position(0, 0))
        particle.dir should be(Direction.North)
    }
}
