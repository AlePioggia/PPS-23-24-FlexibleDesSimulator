import alice.tuprolog._
import scala.language.implicitConversions
import utils.Position

object PrologEngine {
  val engine = Prolog()

  val filePath = "src/main/scala/pos/model/engine/PrologEngine.pl"

  val theoryString: String = {
    val file = new java.io.File(filePath)
    val source = scala.io.Source.fromFile(file)
    val content = source.getLines.mkString("\n")
    source.close()
    content
  }
  def testFindValidPosition(width: scala.Int, height: scala.Int, occupied: List[Position]): Unit = {
    // val occupiedTerms = occupied.map(pos => s"position(${pos.x}, ${pos.y})").mkString("[", ", ", "]")
    // val query = s"find_valid_position($width, $height, $occupiedTerms, Position)."
    val query = s"hello_world."
    val solution: SolveInfo = engine.solve(query)
    print("prova")
    print(solution.getSolution().toString())
  }
  // main to test
  def main(args: Array[String]): Unit = {
    val occupiedPositions = List(Position(2, 3), Position(5, 7))
    testFindValidPosition(10, 10, occupiedPositions)
  }
}
