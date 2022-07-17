package callbacks

import model.Cell

interface GenerateDataCallback {
    suspend fun onNewDataGenerate(cells:ArrayList<ArrayList<Cell>>)
}