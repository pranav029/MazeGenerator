package application

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

abstract class Application {
    private var HEIGHT = 800
    private var WIDTH = 800

     fun createWindow() = application {
        Window(
            onCloseRequest = ::exitApplication,
            resizable = false,
            state = WindowState(width = WIDTH.dp, height = HEIGHT.dp)
        ) {
            onDraw()
        }
    }
    fun setWindowDimension(height:Int,width:Int){
        HEIGHT = height
        WIDTH = width
    }
    abstract fun onCreate()
    @Composable
    abstract fun onDraw()
}