package screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Cell

@Composable
fun DrawCells(
    mazeSize: Int,
    list: ArrayList<ArrayList<Cell>>,
    modifier: Modifier = Modifier
) {
    val height = 20.toFloat()
    Canvas(
        modifier = modifier.width((mazeSize * height).dp).height((mazeSize * height).dp),
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        for (i in 0 until mazeSize) {
            for (j in 0 until mazeSize) {
                //Left
                if (list[i][j].leftWall ) {
                    drawLine(
                        start = Offset(x = (j * height), y = (i * height)),
                        end = Offset(x = (j * height), y = (i * height) + height),
                        color = Color.Blue,
                        strokeWidth = 2f
                    )
                }
//                //Top
                if (list[i][j].topWall) {
                    drawLine(
                        start = Offset(x = (j * height), y = (i * height)),
                        end = Offset(x = (j * height) + height, y = (i * height)),
                        color = Color.Blue,
                        strokeWidth = 2f
                    )
                }
//                //Right
                if (list[i][j].rightWall) {
                    drawLine(
                        start = Offset(x = (j * height) + height, y = (i * height)),
                        end = Offset(x = (j * height) + height, y = (i * height) + height),
                        color = Color.Blue,
                        strokeWidth = 2f
                    )
                }
//                //Bottom
                if (list[i][j].bottomWall ) {
                    drawLine(
                        start = Offset(x = (j * height), y = (i * height) + height),
                        end = Offset(x = (j * height) + height, y = (i * height) + height),
                        color = Color.Blue,
                        strokeWidth = 2f
                    )
                }

                //Rect Fill
//                val t = list[i][j].leftWall && list[i][j].rightWall && list[i][j].topWall && list[i][j].bottomWall
//               drawRect(
//                    color = if (list[i][j].current) Color.Red else if (list[i][j].done) Color.Cyan else Color.White,
//                    topLeft = Offset(x = (j * height), y = (i * height)),
//                    size = Size(height, height)
//                )
                if(list[i][j].shortPath){
                    drawRect(
                        color = Color.Yellow,
                        topLeft = Offset(x = (j * height), y = (i * height)),
                        size = Size(height, height)
                    )
                }else{
                    drawRect(
                        color = if (list[i][j].current) Color.Red else if (list[i][j].done) Color.Cyan else Color.White,
                        topLeft = Offset(x = (j * height), y = (i * height)),
                        size = Size(height, height)
                    )
                }

            }
        }
    }
}