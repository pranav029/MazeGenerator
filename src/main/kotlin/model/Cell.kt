package model

data class Cell(
    var leftWall: Boolean = true,
    var rightWall: Boolean = true,
    var topWall: Boolean = true,
    var bottomWall: Boolean = true,
    var current: Boolean = false,
    var done: Boolean = false,
    var visited: Boolean = false,
)
