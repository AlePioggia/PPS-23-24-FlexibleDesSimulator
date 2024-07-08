package pos.model.utilities

import utils.Position
import pos.model.Particle
import pos.model.POSParams

object VelocityUtils:
    def calculateVelocity(
        particle: Particle,
        globalBest: Position,
        params: POSParams
    ): Position =
        val (w, c1, c2, r1, r2) = (params.w, params.c1, params.c2, params.r1, params.r2)
        
        val velocityX = (w * particle.velocity.x 
            + c1 * r1 * (particle.best.personalBest.x - particle.pos.x)
            + c2 * r2 * (globalBest.x - particle.pos.x)).toInt
        
        val velocityY = (w * particle.velocity.y 
            + c1 * r1 * (particle.best.personalBest.y - particle.pos.y) 
            + c2 * r2 * (globalBest.y - particle.pos.y)).toInt

        Position(velocityX, velocityY)
