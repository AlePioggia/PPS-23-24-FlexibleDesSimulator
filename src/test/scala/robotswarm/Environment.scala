package robotswarm

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import robotswarm.model.Environment

class EnvironmentSpec extends AnyFlatSpec with Matchers {
    "An Environment" should "be created correctly" in {
        val env = new Environment(10, 10)
        env.width should be(10)
        env.height should be(10)
    } 
}