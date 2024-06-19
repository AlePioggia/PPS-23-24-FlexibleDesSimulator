package pos.model

import utils.Position
import utils.Direction

class ParticleBuilder:
    def withId(id: Int): ParticleBuilder = ???
    def withPosition(pos: Position): ParticleBuilder = ???
    def withDirection(dir: Direction): ParticleBuilder = ???
    def withVelocity(vel: Position): ParticleBuilder = ???
    def withPersonalBest(personalBest: Position): ParticleBuilder = ???
    def withGlobalBest(globalBest: Position): ParticleBuilder = ???
    def build(): Particle = ???
