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
                {val fitness = fitnessFunction(particle.pos); if fitness < particle.best.personalBestFitness then updatePersonalBests(particle: Particle); if fitness < globalBestFitness then updateGlobalBests(particle: Particle)}

    override def nextPosition(agent: Agent) =
        agent match
            case particle: Particle =>
                particle.velocity = PositionUtils.boundPosition(calculateVelocity(particle, globalBest, params), width, height)

                var targetPos = Position(particle.pos.x + particle.velocity.x, particle.pos.y + particle.velocity.y)

                while !agentManager.isPositionValid(targetPos) do
                    targetPos = PositionUtils.boundPosition(Position(
                        particle.pos.x + scala.util.Random.nextInt(3) - 1, 
                        particle.pos.y + scala.util.Random.nextInt(3) - 1
                    ), width, height)

                targetPos

    def setup(n: Int): Unit = setupParticles(n, Set.empty, 0)

    private def setupParticles(n: Int, occupiedPositions: Set[Position], id: Int): Unit =
        if n > 0 then
            val (position, velocity) = (PositionUtils.findValidPosition(width, height, occupiedPositions), PositionUtils.randomVelocity())
            
            val particle = ParticleBuilder()
                .withId(id).withPosition(position).withVelocity(Position(velocity.x, velocity.y))
                .withPersonalBest(position).withPersonalBestFitness(Double.MaxValue).withGlobalBest(globalBest)
                .build()
            
            agentManager.addAgentByPosition(position.x, position.y, particle)
            setupParticles(n - 1, occupiedPositions + position, id + 1)

    private def updatePersonalBests(particle: Particle): Unit = {particle.best.personalBest = particle.pos; particle.best.personalBestFitness = fitnessFunction(particle.pos)}

    private def updateGlobalBests(particle: Particle): Unit = {globalBest = particle.pos; globalBestFitness = fitnessFunction(particle.pos)}