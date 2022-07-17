package algorithm.dfs

import callbacks.GenerateDataCallback
import kotlinx.coroutines.flow.flow
import model.Cell

class GraphAlgo(
    private var cells: ArrayList<ArrayList<Cell>>,
    private val size: Int,
    private val mListener: GenerateDataCallback
) {
    private lateinit var mStack: ArrayList<Pair<Int, Int>>

    init {
        initStack()
    }

    private fun initStack() {
        mStack = arrayListOf()
    }


    suspend fun generate()  {
        cells[0][0].visited = true
        push(0, 0)
        while (mStack.isNotEmpty()) {
            val (x: Int, y: Int) = peek()
            cells[x][y].current = false
            cells[x][y].done = true
            val neighbours = peek().getNeighbours()
            pop()
            neighbours.nextUnvisitedCell()?.let {
                push(x, y)
                push(it.first, it.second)
                it.removeWall(x, y)
                cells[it.first][it.second].visited = true
                cells[it.first][it.second].current = true
                mListener.onNewDataGenerate(cells)
            }
            if (mStack.isEmpty()) {
                cells[x][y].current = true
                mListener.onNewDataGenerate(cells)
            }
        }
    }

    private fun ArrayList<Pair<Int, Int>>.nextUnvisitedCell(): Pair<Int, Int>? = this.randomOrNull()
    private fun peek(): Pair<Int, Int> = mStack[mStack.lastIndex]
    private fun pop(): Pair<Int, Int> = mStack.removeLast()
    private fun push(x: Int, y: Int) = mStack.add(Pair(x, y))
    private fun hasLeftNeighbour(x: Int, y: Int): Boolean =
        y - 1 >= 0 && !cells[x][y - 1].visited

    private fun hasRightNeighbour(x: Int, y: Int): Boolean =
        y + 1 < size && !cells[x][y + 1].visited

    private fun hasTopNeighbour(x: Int, y: Int): Boolean =
        x - 1 >= 0 && !cells[x - 1][y].visited

    private fun hasBottomNeighbour(x: Int, y: Int): Boolean =
        x + 1 < size && !cells[x + 1][y].visited

    private fun Pair<Int, Int>.getNeighbours(): ArrayList<Pair<Int, Int>> {
        val (x: Int, y: Int) = this
        val neighbours = arrayListOf<Pair<Int, Int>>()
        if (hasLeftNeighbour(x, y)) {
            neighbours.add(Pair(x, y - 1))
        }
        if (hasRightNeighbour(x, y)) {
            neighbours.add(Pair(x, y + 1))
        }
        if (hasTopNeighbour(x, y)) {
            neighbours.add(Pair(x - 1, y))
        }
        if (hasBottomNeighbour(x, y)) {
            neighbours.add(Pair(x + 1, y))
        }
        return neighbours
    }

    private fun Pair<Int, Int>.removeWall(x: Int, y: Int) {
        if (y > this.second) {
            cells[x][y].leftWall = false
            cells[this.first][this.second].rightWall = false
        }
        if (y < this.second) {
            cells[x][y].rightWall = false
            cells[this.first][this.second].leftWall = false
        }
        if (x > this.first) {
            cells[x][y].topWall = false
            cells[this.first][this.second].bottomWall = false
        }
        if (x < this.first) {
            cells[x][y].bottomWall = false
            cells[this.first][this.second].topWall = false
        }
    }
}