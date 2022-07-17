package algorithm

import algorithm.dfs.GraphAlgo
import callbacks.GenerateDataCallback
import callbacks.NewCellCreatedCallback
import domain.alogrithms.Algorithm
import model.Cell

class AlgorithmImpl: Algorithm {
    override suspend fun generateMaze(cells: ArrayList<ArrayList<Cell>>, dimen: Int, mListener: NewCellCreatedCallback) {
        val graph = GraphAlgo(cells,dimen,object :GenerateDataCallback{
            override suspend fun onNewDataGenerate(cells: ArrayList<ArrayList<Cell>>) {
               mListener.onNewCellCreated(cells)
            }
        })
        graph.generate()
    }

    override fun solveMaze(cells: ArrayList<ArrayList<Cell>>, size: Int, mListener: CompleteCallback) {
        TODO("Not yet implemented")
    }
}