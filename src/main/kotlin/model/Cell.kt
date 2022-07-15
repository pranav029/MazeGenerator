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
        leftWall = true
        rightWall = true
        topWall = true
        bottomWall = true
        current = false
        done = false
        visited = false
        shortPath = false
    }
}
