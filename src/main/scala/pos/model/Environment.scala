package pos.model

import core.model.Environment
import core.model.Agent
import _root_.utils.Position
import utilities.VelocityUtils
import utilities.PositionUtils

class PosEnvironment(width: Int, height: Int)(fitnessFunction: Position => Double)(params: POSParams) extends Environment(width, height):
    var globalBest: Position = Position(Int.MaxValue, Int.MaxValue)
    var globalBestFitness: Double = Double.MaxValue

    override def preMoveActions(agent: Agent): Unit = agent match
        case particle: Particle =>
            particle.moveTo(nextPosition(particle))
    
    override def postMoveActions(agent: Agent): Unit =
        agent match
            case particle: Particle =>
                val fitness = fitnessFunction(particle.pos)
                if fitness < particle.best.personalBestFitness then
                    particle.best.personalBest = particle.pos
                    particle.best.personalBestFitness = fitness
                
                if fitness < globalBestFitness then
                    globalBest = particle.pos
                    globalBestFitness = fitness

    override def nextPosition(agent: Agent) =
        agent match
            case particle: Particle =>
                val (w, c1, c2, r1, r2) = (0.5, 1.5, 1.5, 0.5, 0.5)
                
                val x = (w * particle.velocity.x 
                    + c1 * r1 * (particle.best.personalBest.x - particle.pos.x) 
                    + c2 * r2 * (globalBest.x - particle.pos.x)).toInt
                
                val y = (w * particle.velocity.y 
                    + c1 * r1 * (particle.best.personalBest.y - particle.pos.y) 
                    + c2 * r2 * (globalBest.y - particle.pos.y)).toInt

                particle.velocity = boundPosition(Position(x, y))
                particle.velocity = boundPosition(Position(x, y))
                boundPosition(Position(particle.pos.x + particle.velocity.x, 
                    particle.pos.y + particle.velocity.y))
            
    def boundPosition(pos: Position): Position =
        val x = Math.max(0, Math.min(width - 1, pos.x))
        val y = Math.max(0, Math.min(height - 1, pos.y))
        Position(x, y)   
    
    def setup(n: Int): Unit =
        val random = scala.util.Random
        var occupiedPositions: Set[Position] = Set()

        for i <- 0 until n do
            var posX = random.nextInt(width)
            var posY = random.nextInt(height)
            var position = Position(posX, posY)

            while occupiedPositions.contains(position) do
                posX = random.nextInt(width)
                posY = random.nextInt(height)
                position = Position(posX, posY)

            val velocityX = random.nextInt(5) - 2
            val velocityY = random.nextInt(5) - 2
            
            val particle = ParticleBuilder()
                .withId(i)
                .withPosition(position)
                .withVelocity(Position(velocityX, velocityY))
                .withPersonalBest(position)
                .withPersonalBestFitness(Double.MaxValue)
                .withGlobalBest(globalBest)
                .build()
            
            agentManager.addAgentByPosition(posX, posY, particle)
            occupiedPositions += position