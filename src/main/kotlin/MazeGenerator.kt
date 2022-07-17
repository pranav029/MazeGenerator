import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import application.Application
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.FlowPreview
import screens.DrawCells
import screens.MainScreenViewModel

class MazeGenerator: Application() {
    val viewModel: MainScreenViewModel = MainScreenViewModel()
    override fun onCreate() {
        super.setWindowDimension(1050,1050)
        super.createWindow()
    }

    @Composable
    override fun onDraw() {
        MainScreen()
    }

    @OptIn(DelicateCoroutinesApi::class, FlowPreview::class)
    @Composable
    @Preview
    fun MainScreen() {
        LazyColumn(
            modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    DrawCells(viewModel.dimen, viewModel.cells, Modifier.align(Alignment.Center))
                }
            }

            item {
                Button(
                    content = {
                        Text(
                            text = "Generate"
                        )
                    },
                    onClick = {
//                    reset(cells)
//                    graph.reset()
//                    job?.cancel()
                        viewModel.generate()
                    },
                    enabled = viewModel.generateButton
                )
            }
            item {
                Button(
                    content = {
                        Text(
                            text = "Solve"
                        )
                    },
                    onClick = {
                        viewModel.cancel()
                    }
                )
            }
        }
    }
}