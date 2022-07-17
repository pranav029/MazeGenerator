package callbacks

import model.Cell

interface NewCellCreatedCallback {
    suspend fun onNewCellCreated(cells:ArrayList<ArrayList<Cell>>)
}