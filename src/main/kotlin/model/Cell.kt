package model

data class Cell(
    var leftWall: Boolean = true,
    var rightWall: Boolean = true,
    var topWall: Boolean = true,
    var bottomWall: Boolean = true,
    var current: Boolean = false,
    var done: Boolean = false,
    var visited: Boolean = false,
    var shortPath: Boolean = false
) {
    fun reset() {
        this.leftWall = true
        this.rightWall = true
        this.topWall = true
        this.bottomWall = true
        this.current = false
        this.done = false
        this.visited = false
        this.shortPath = false
    }
}
