package screens

import algorithm.CompleteCallback
import algorithm.MazeSolveAlgo
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import domain.generateMaze_use_case.GenerateMaze
import domain.solveMaze_use_case.SolveMaze
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import model.Cell

class MainScreenViewModel(
    val generateUseCase: GenerateMaze = GenerateMaze(),
    val solveUseCase: SolveMaze = SolveMaze()
) {
    val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    var generateButton by mutableStateOf(true)
    val dimen by mutableStateOf(20)
    var cells by mutableStateOf(initCells())
    private var generateJob: Job? = null

    private fun reset() {
        cells = arrayListOf()
        cells.addAll(initCells())
    }
    private fun initCells(): ArrayList<ArrayList<Cell>> {
        val list = arrayListOf<ArrayList<Cell>>()
        for (i in 0 until dimen) {
            list.add(arrayListOf<Cell>())
            for (j in 0 until dimen) {
                list[i].add(Cell())
            }
        }
        return list
    }

    fun generate() {
        reset()
        generateButton = false
        generateJob = generateUseCase(cells, dimen).onEach {
            cells = arrayListOf()
            cells.addAll(it)
        }.launchIn(viewModelScope)
    }
    fun cancel() {
        generateJob?.cancel()
        reset()
        generateButton = true
    }

    fun solver() {
        val temp = MazeSolveAlgo(cells, dimen, object : CompleteCallback {
            override fun onSolveComplete(list: ArrayList<ArrayList<Cell>>, context: MazeSolveAlgo) {
                cells = arrayListOf()
                cells.addAll(list)
                context.showPath().onEach {
                    cells = arrayListOf()
                    cells.addAll(it)
                }.launchIn(viewModelScope)
            }
        })
        temp.solve().onEach {
            cells = arrayListOf()
            cells.addAll(it)
        }.launchIn(viewModelScope)
    }
}