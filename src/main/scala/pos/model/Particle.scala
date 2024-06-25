package pos.model

import core.model.Agent
import scala.util.Random
import _root_.utils.Position
import _root_.utils.Direction

case class Best(var personalBest: Position, var personalBestFitness: Double, var globalBest: Position)

case class Particle (val id: Int)(var pos: Position, var velocity: Position, var best: Best) extends Agent:
    var dir: Direction = _

    def moveTo(nextPos: Position): Unit = 
        if velocity != Position(0, 0) then
            pos = nextPos

class ParticleBuilder:
    private var id: Int = 0
    private var pos: Position = Position(0, 0)
    private var velocity: Position = Position(0, 0)
    private var personalBest: Position = Position(0, 0)
    private var personalBestFitness: Double = Int.MaxValue
    private var globalBest: Position = Position(0, 0)
    
    def withId(id: Int): ParticleBuilder = 
        this.id = id
        this

    def withPosition(pos: Position): ParticleBuilder = 
        this.pos = pos
        this

    def withVelocity(vel: Position): ParticleBuilder = 
        this.velocity = vel
        this
    
    def withPersonalBest(personalBest: Position): ParticleBuilder = 
        this.personalBest = personalBest
        this
    
    def withPersonalBestFitness(personalBestFitness: Double): ParticleBuilder = 
        this.personalBestFitness = personalBestFitness
        this

    def withGlobalBest(globalBest: Position): ParticleBuilder = 
        this.globalBest = globalBest
        this

    def build(): Particle = 
        val best = Best(personalBest, personalBestFitness, globalBest)
        Particle(id)(pos, velocity, best)