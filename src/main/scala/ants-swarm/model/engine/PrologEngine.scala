import alice.tuprolog._
import scala.language.implicitConversions
import utils.Position

object PrologEngine {
  val engine = Prolog()

  val filePath = "src/main/scala/ants-swarm/model/engine/PrologEngine.pl"

  val theoryString: String = {
    val file = new java.io.File(filePath)
    val source = scala.io.Source.fromFile(file)
    val content = source.getLines.mkString("\n")
    source.close()
    content
  }
  def testDoubleArray(array: Array[scala.Double]): Unit = {
    val prologArray = array.map(d => Term.createTerm(d.toString())).toList
    val arrayTerm = Term.createTerm(prologArray.mkString("[", ",", "]"))

    val query = s"append($arrayTerm, [4, 5, 6], R)."
    
    val solution: SolveInfo = engine.solve(query)

    println("Risultato della query Prolog:")
    println(solution)
  }
}
