package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four
// Game Object for Multiplayer Mode
object MultiGameObject {
    const val boardCols = 7
    const val boardRows = 6
    private const val midCol = 3
    private const val winNum = 4
    var currCol = midCol
    var currTurn = 1
    var winLine = ArrayList<Pair<Int, Int>>()
    val field = Array(boardRows) { Array(boardCols) { 0 } }
    var p1wins = 0
    var p2wins = 0

    var p1name = ""
    var p2name = ""



    val winPlayer get() = if (winLine.size < winNum) 0 else {
        val (x, y) = winLine[0]
        field[x][y]
    }

    enum class State { START, TURN, END }
    var state = State.TURN

    fun start() {
        if (state == State.END) {
            currTurn = 1
            currCol = midCol
            winLine = ArrayList()
            field.forEach { it.fill(0) }
        }
        state = State.TURN
    }


    fun dropChip() {
        val colDepth = getLastEmptyUnit(currCol)
        val rnds = (0..6).random()

        if (colDepth >= 0) {
            //what color to drop
            field[colDepth][currCol] = currTurn

            updateWinLine(currCol, colDepth)
            if (winLine.size < winNum && field[0].contains(0)) {
                currTurn = -currTurn
                currCol = midCol


            } else
                state = State.END
        }


    }

    private fun getLastEmptyUnit(col: Int): Int {
        var count = 0
        while (count < boardRows && field[count][col] == 0)
            count = count + 1
        return count-1
    }

    private fun updateLine(x0: Int, y0: Int, dx: Int, dy: Int): ArrayList<Pair<Int, Int>> {
        val line = ArrayList<Pair<Int, Int>>()
        line.add(Pair(y0, x0))
        var x = x0 - dx
        var y = y0 - dy
        while (x in 0 until boardCols && y in 0 until boardRows && field[y][x] == field[y0][x0]) {
            line.add(Pair(y, x))
            x -= dx
            y -= dy
        }
        x = x0 + dx
        y = y0 + dy
        while (x in 0 until boardCols && y in 0 until boardRows && field[y][x] == field[y0][x0]) {
            line.add(Pair(y, x))
            x += dx
            y += dy
        }
        return line
    }

    private fun updateWinLine(x0: Int, y0: Int) {
        for ((dx, dy) in arrayOf(Pair(1, -1), Pair(1, 0), Pair(1, 1), Pair(0, 1)))
            winLine = maxOf(winLine, updateLine(x0, y0, dx, dy), compareBy { it.size })
    }
}