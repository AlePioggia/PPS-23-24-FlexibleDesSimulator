package robotswarm.model

import utils.Position
import utils.Direction

case class Robot(id: Int)(var position: Position)(var direction: Direction)