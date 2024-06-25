package pos.model

import core.model.Environment
import core.model.Agent
import _root_.utils.Position
import utilities.VelocityUtils
import utilities.PositionUtils
import pos.model.utilities.VelocityUtils.calculateVelocity

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
                particle.velocity = PositionUtils.boundPosition(calculateVelocity(particle, globalBest, params), width, height)
                PositionUtils.boundPosition(Position(particle.pos.x + particle.velocity.x, 
                    particle.pos.y + particle.velocity.y), width, height)
            
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