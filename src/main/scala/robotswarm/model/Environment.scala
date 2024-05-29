package robotswarm.model

trait Grid:
    val width: Int
    val height: Int
end Grid

case class Environment (val width: Int, val height: Int) extends Grid