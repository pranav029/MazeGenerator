package algorithm

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import model.Cell

class MazeSolveAlgo(
    private val cells: ArrayList<ArrayList<Cell>>,
    private val size: Int,
    private val mListener: CompleteCallback
) {

    private val n = size * size
    private val dist = arrayListOf<Int>()
    private val sptSet = arrayListOf<Boolean>()
    private val parent = arrayListOf<Int>()

    init {
        for (i in 0 until n) {
            dist.add(Integer.MAX_VALUE)
            sptSet.add(false)
            parent.add(0)
        }
        parent[0] = -1
    }

    //toCount required for some future addition of features  to project
    private fun Pair<Int, Int>.toCount(): Int = this.first * size + this.second
    private fun Int.toIndices(): Pair<Int, Int> = Pair(this / size, this % size)
    fun solve() = flow<ArrayList<ArrayList<Cell>>> {
        dist[0] = 0
        for (i in 0 until n - 1) {
            val minVertex = minDistance()
            sptSet[minVertex] = true
            val (x: Int, y: Int) = minVertex.toIndices()
            cells[x][y].shortPath = true
            cells[x][y].current = true
            emit(cells)
            cells[x][y].current = false
            for (v in 0 until n) {
                if (!sptSet[v] && isConnected(
                        minVertex.toIndices(),
                        v.toIndices()
                    ) && dist[minVertex] != Integer.MAX_VALUE && dist[minVertex] + 1 < dist[v]
                ) {
                    parent[v] = minVertex
                    val (x: Int, y: Int) = v.toIndices()
                    cells[x][y].current = true
                    emit(cells)
                    delay(100)
                    dist[v] = dist[minVertex] + 1
                    cells[x][y].current = false
                    cells[x][y].shortPath = true
                    emit(cells)
                    cells[x][y].shortPath = false
                }
            }
            cells[x][y].shortPath = false
        }
        for (i in 0 until size) {
            for (j in 0 until size) {
                cells[i][j].shortPath = false
                cells[i][j].current = false
            }
        }
        cells[0][0].current = true
        mListener.onSolveComplete(cells,this@MazeSolveAlgo)
    }

    private fun minDistance(): Int {
        var mini = Integer.MAX_VALUE
        var minIndex = -1
        for (i in 0 until n) {
            if (!sptSet[i] && dist[i] <= mini) {
                mini = dist[i]
                minIndex = i
            }
        }
        return minIndex
    }

    private fun isConnected(u: Pair<Int, Int>, v: Pair<Int, Int>): Boolean {
        if (u.first + 1 == v.first && v.second == u.second) {
            return cells[u.first][u.second].bottomWall == cells[v.first][v.second].topWall && !cells[v.first][v.second].topWall
        }
        if (u.first == v.first + 1 && v.second == u.second) {
            return cells[u.first][u.second].topWall == cells[v.first][v.second].bottomWall && !cells[v.first][v.second].bottomWall
        }
        if (u.first == v.first && u.second + 1 == v.second) {
            return cells[u.first][u.second].rightWall == cells[v.first][v.second].leftWall && !cells[v.first][v.second].leftWall
        }
        if (u.first == v.first && u.second == v.second + 1) {
            return cells[u.first][u.second].leftWall == cells[v.first][v.second].rightWall && !cells[v.first][v.second].rightWall
        }
        return false
    }

    private suspend fun getPath(index: Int,lis:()->Unit) {
        if (parent[index] == -1) return
        println(index.toIndices())
        val (x: Int, y: Int) = index.toIndices()
        cells[x][y].shortPath = true
        lis()
        delay(100)
        getPath(parent[index],lis)
    }

    fun showPath(): Flow<ArrayList<ArrayList<Cell>>> = callbackFlow {
        var callback:Unit? = getPath(n-1){
            trySendBlocking(cells)
        }
        awaitClose { callback = null }
    }

}

interface CompleteCallback {
    fun onSolveComplete(cells: ArrayList<ArrayList<Cell>>,context:MazeSolveAlgo)
}

