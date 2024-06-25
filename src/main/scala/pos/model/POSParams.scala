package pos.model

class POSParams(val w: Double, val c1: Double, val c2: Double, val r1: Double, val r2: Double)

object POSParams:
  val Default: POSParams = POSParams(0.5, 1.5, 1.5, 0.5, 0.5)

  def builder() = new Builder

  class Builder:
    private var w: Double = 0.5
    private var c1: Double = 1.5
    private var c2: Double = 1.5
    private var r1: Double = 0.5
    private var r2: Double = 0.5

    def withW(w: Double): Builder = { this.w = w; this }
    
    def withC1(c1: Double): Builder = { this.c1 = c1; this }
    
    def withC2(c2: Double): Builder = { this.c2 = c2; this }
    
    def withR1(r1: Double): Builder = { this.r1 = r1; this }
    
    def withRandomR1(): Builder = { this.r1 = scala.util.Random.nextDouble(); this }

    def withR2(r2: Double): Builder = { this.r2 = r2; this }

    def withRandomR2(): Builder = { this.r2 = scala.util.Random.nextDouble(); this }  
    
    def build(): POSParams = POSParams(w, c1, c2, r1, r2)
