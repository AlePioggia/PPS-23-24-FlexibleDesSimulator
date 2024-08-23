package robotswarm.prolog

import alice.tuprolog._
import scala.language.implicitConversions
import utils.Position

object PrologEngine {
  val engine = Prolog()

  val filePath = "src/main/scala/robotswarm/prolog/PrologEngine.pl"

  val theoryString: String = {
    val file = new java.io.File(filePath)
    val source = scala.io.Source.fromFile(file)
    val content = source.getLines.mkString("\n")
    source.close()
    content
  }

  def testAStar(startPos: Position, goal: Position): Unit =
    val query = s"find_path((0,0), (4,4), Path)."
    val solution: SolveInfo = engine.solve(query)
    print(solution)

  // main to test
  def main(args: Array[String]): Unit = {
    engine.addTheory(new Theory(theoryString))
    val startPos = Position(0, 0)
    val goal = Position(4, 4)
    testAStar(startPos, goal)
  }
}

