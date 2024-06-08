package core.model

import utils.Position

class PickupObjsManager(val width: Int, val height: Int):
    private val pickupObjs = Array.fill[Boolean](width, height)(false)

    def addObject(pos: Position): Unit = pickupObjs(pos.x)(pos.y) = true
    def removeObject(pos: Position): Unit = pickupObjs(pos.x)(pos.y) = false
    def isObjectAt(pos: Position): Boolean = pickupObjs(pos.x)(pos.y)
