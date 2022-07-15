// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import algorithm.GraphAlgo
import algorithm.MazeSolveAlgo
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import model.Cell
import screens.DrawCells

@OptIn(DelicateCoroutinesApi::class, FlowPreview::class)
@Composable
@Preview
fun App() {
    val dimen by remember { mutableStateOf(20) }
    var cells by remember { initCells(dimen) }
    val graph = GraphAlgo(cells, dimen)
    var job: Job? = null
    val start: () -> Unit = {
        job = graph.generate().onEach {
            cells = arrayListOf()
            cells.addAll(it)
        }.launchIn(GlobalScope)
    }
    val solver: () -> Unit = {
        val temp = MazeSolveAlgo(cells, dimen)
        temp.solve().onEach {
            cells = arrayListOf()
            cells.addAll(it)
        }.launchIn(GlobalScope)
        cells = arrayListOf()
        println(cells.size)
        cells.addAll(temp.getSolved())
    }
    LazyColumn(
        modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                DrawCells(dimen, cells, Modifier.align(Alignment.Center))
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
                    start()
                }
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
                    solver()
                }
            )
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        resizable = false,
        state = WindowState(width = 1050.dp, height = 1050.dp)
    ) {
        App()
    }
}

fun reset(state: MutableState<ArrayList<ArrayList<Cell>>>) {
    for (i in 0 until state.value.size) {
        for (j in 0 until state.value.size) {
            state.value[i][j].reset()
        }
    }
}

fun initCells(size: Int): MutableState<ArrayList<ArrayList<Cell>>> {
    val list = arrayListOf<ArrayList<Cell>>()
    for (i in 0 until size) {
        list.add(arrayListOf<Cell>())
        for (j in 0 until size) {
            list[i].add(Cell())
        }
    }
    return mutableStateOf(list)
}
