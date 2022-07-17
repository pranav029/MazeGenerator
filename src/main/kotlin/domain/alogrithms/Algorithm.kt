package domain.alogrithms

import algorithm.CompleteCallback
import callbacks.NewCellCreatedCallback
import model.Cell

interface Algorithm {
    suspend fun generateMaze(cells:ArrayList<ArrayList<Cell>>, dimen:Int, mListener: NewCellCreatedCallback)
    fun solveMaze(  cells: ArrayList<ArrayList<Cell>>, size: Int, mListener: CompleteCallback)
}