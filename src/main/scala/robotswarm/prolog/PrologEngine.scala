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

  def matrixToPrologString(matrix: Array[Array[scala.Int]]): String = {
    matrix.map(row => "[" + row.mkString(",") + "]").mkString(",")
  }

  def testAStar(matrix: Array[Array[scala.Int]], startPos: Position, goal: Position): Unit =
    var matrixString = matrixToPrologString(matrix)
    println(matrixString)
    val query = s"find_path((0,0), (4,4), Path)."
    val solution: SolveInfo = engine.solve(query)

  // main to test
  def main(args: Array[String]): Unit = {
    val matrix: Array[Array[scala.Int]] = Array(
      Array(0, 0, 0, 0, 0),
      Array(0, 1, 1, 1, 0),
      Array(0, 1, 0, 1, 0),
      Array(0, 1, 1, 1, 0),
      Array(0, 0, 0, 0, 0)
    )

    var mString = "matrix([" + matrixToPrologString(matrix) + "])."
    var newTheoryString = mString + "\n" + theoryString
    
    engine.addTheory(new Theory(mString + "\n" + theoryString))

    val startPos = Position(0, 0)
    val goal = Position(4, 4)

    testAStar(matrix, startPos, goal)
  }
}

