package pos.model

import core.model.Environment
import utils.Position
import core.model.Agent

class PosEnvironment(width: Int, height: Int)(fitnessFunction: Position => Double) extends Environment(width, height):
    var globalBest: Position = Position(Int.MaxValue, Int.MaxValue)
    var globalBestFitness: Double = Double.MaxValue

    override def preMoveActions(agent: Agent): Unit = agent match
        case particle: Particle =>
            particle.moveTo(nextPosition(particle))
    

    override def postMoveActions(agent: Agent): Unit =
        agent match
            case particle: Particle =>
                val fitness = fitnessFunction(particle.state.pos)
                if fitness < particle.best.personalBestFitness then
                    particle.best.personalBest = particle.state.pos
                    particle.best.personalBestFitness = fitness
                
                if fitness < globalBestFitness then
                    globalBest = particle.state.pos
                    globalBestFitness = fitness

    override def nextPosition(agent: Agent) =
        agent match
            case particle: Particle =>
                val (w, c1, c2, r1, r2) = (0.5, 1.5, 1.5, 0.5, 0.5)
                
                val x = (w * particle.state.velocity.x 
                    + c1 * r1 * (particle.best.personalBest.x - particle.state.pos.x) 
                    + c2 * r2 * (globalBest.x - particle.state.pos.x)).toInt
                
                val y = (w * particle.state.velocity.y 
                    + c1 * r1 * (particle.best.personalBest.y - particle.state.pos.y) 
                    + c2 * r2 * (globalBest.y - particle.state.pos.y)).toInt

                particle.state.velocity = boundPosition(Position(x, y))
                particle.state.velocity = boundPosition(Position(x, y))
                boundPosition(Position(particle.state.pos.x + particle.state.velocity.x, 
                    particle.state.pos.y + particle.state.velocity.y))
            
    def boundPosition(pos: Position): Position =
        val x = Math.max(0, Math.min(width - 1, pos.x))
        val y = Math.max(0, Math.min(height - 1, pos.y))
        Position(x, y)   