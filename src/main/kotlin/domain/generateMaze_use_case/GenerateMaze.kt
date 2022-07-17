package domain.generateMaze_use_case

import algorithm.AlgorithmImpl
import callbacks.NewCellCreatedCallback
import domain.alogrithms.Algorithm
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import model.Cell

class GenerateMaze(
    private val algorithm:Algorithm = AlgorithmImpl()
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(cells:ArrayList<ArrayList<Cell>>, dimen:Int) = callbackFlow{
       var callback:Unit? =  algorithm.generateMaze(cells,dimen,object : NewCellCreatedCallback{
            override suspend fun onNewCellCreated(cells: ArrayList<ArrayList<Cell>>) {
               trySendBlocking(cells)
                delay(10)
            }
        })
        awaitClose { callback = null }
    }
}