package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.medina.tacorda.four.GameObject

import kotlin.math.min

class BoardMultiActivity (ctx: Context) : View(ctx) {

    //Board Activity for Multiplayer Mode
    private companion object {

        const val unitRadius = 3
        const val unitHeight = 8
        const val unitWidth = 10
        const val boardWidth = MultiGameObject.boardCols * unitWidth + 2
        const val boardHeight = MultiGameObject.boardRows * unitWidth + 3
        const val startText = 5
        const val endText = unitHeight - 2
        const val cRed = 0xffAF1E13.toInt()
        const val cBlue = 0xff061556.toInt()
        const val cYellow = 0xffF2B80E.toInt()
        const val cWhite = 0xffffffff.toInt()
        const val cBlack = 0xFF000000.toInt()
        const val cGray = 0xff333333.toInt()
        const val cDGray = 0xff242424.toInt()
        const val cLblue = 0xFFAECCE4.toInt()
        //const val cLblue2 = 0xFFE5F3FD.toInt()
        const val cLblue2 = 0xFFBFD6F5.toInt()

        fun getChipColor(id: Int) = when (id) {
            1 -> cRed
            -1 -> cYellow
            else -> cLblue2
        }

    }


    private var block = min(width / boardWidth, height / boardHeight).toFloat()
    private var x0 = (width - boardWidth * block) / 2
    private var y0 = (height - boardHeight * block) / 2

    private fun getX(x: Int) = x0 + x * block
    private fun getY(y: Int) = y0 + y * block

    private fun getBlockX(j: Int) = getX(1 + j * unitWidth + unitWidth / 2)
    private fun getBlockY(i: Int) = getY(1 + (i + 1) * unitHeight + unitHeight / 2)

    private fun getField() = RectF(getX(1), getY(unitHeight),
        getX(boardWidth - 1), getY(boardHeight - 1))

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }
    //Canvas Implementation (Board and Logic)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = cLblue
        canvas.drawPaint(paint)
        paint.color = cGray
        paint.textSize = 50f

        canvas.drawRoundRect(15f,20f,1065f,200f,30f,30f,paint)
        paint.color = cWhite

        val win = "Wins: " + MultiGameObject.p1wins.toString()
        canvas.drawText(win, getX(boardWidth / 4), getY(unitHeight - 28), paint)
        canvas.drawText(MultiGameObject.p1name, getX(boardWidth / 4), getY(unitHeight - 23), paint)

        val compwin = "Wins: " + MultiGameObject.p2wins.toString()
        canvas.drawText(compwin, 800f, getY(unitHeight - 28), paint)
        canvas.drawText(MultiGameObject.p2name, 800f, getY(unitHeight - 23), paint)

        paint.color = cBlack
        paint.strokeWidth = 20f
        canvas.drawLine(boardWidth.toFloat()+470,20f, boardWidth.toFloat()+470,200f,paint)

        paint.color = cDGray
        paint.strokeWidth = 20f
        canvas.drawLine(boardWidth.toFloat()+470,20f, boardWidth.toFloat()+470,200f,paint)

        paint.color = cRed
        canvas.drawCircle(100f, 110f, 60f,paint)


        paint.color = cYellow
        canvas.drawCircle(985f, 110f, 60f,paint)

        if (MultiGameObject.state != MultiGameObject.State.START) {
            paint.color = cBlue
            canvas.drawRect(getField(), paint)

            if (MultiGameObject.state == MultiGameObject.State.TURN) {
                paint.color = getChipColor(MultiGameObject.currTurn)
                //draw top chip
                canvas.drawCircle(getBlockX(MultiGameObject.currCol), getY(unitHeight / 2),
                    unitRadius * block, paint)
            } else {
                paint.color = cBlue
                paint.textSize = endText * block
                val text = when (MultiGameObject.winPlayer) {
                    1 -> "Player 1 Wins!"
                    -1 -> "Player 2 Wins!"
                    else -> "Draw."
                }
                paint.textSize = 85f
                canvas.drawText("Tap screen to play again",
                    boardWidth.toFloat()+470,1500f,paint)
                if(MultiGameObject.winPlayer == 1)
                    MultiGameObject.p1wins +=1
                else
                    MultiGameObject.p2wins +=1
                canvas.drawText(text, getX(boardWidth / 2), getY(unitHeight - 2), paint)

                if (MultiGameObject.winPlayer != 0) {
                    for ((i, j) in MultiGameObject.winLine) {
                        paint.color = getChipColor(-MultiGameObject.field[i][j])
                        //draw circle around the winning 4
                        canvas.drawCircle(getBlockX(j), getBlockY(i), (unitRadius + .5f) * block, paint)
                    }
                }
            }

            for (i in 0 until MultiGameObject.boardRows) {
                for (j in 0 until MultiGameObject.boardCols) {
                    paint.color = getChipColor(MultiGameObject.field[i][j])
                    //white circle around the board
                    canvas.drawCircle(getBlockX(j), getBlockY(i), unitRadius * block, paint)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && event.action == MotionEvent.ACTION_DOWN) {
            val x = (event.x - x0) / block
            val y = (event.y - y0) / block
            if (MultiGameObject.state == MultiGameObject.State.TURN) {
                // Drop Chip
                if (x > 1 && x < boardWidth - 1 && y > MultiGameObject.boardRows * unitHeight && y < boardHeight - 1) {
                    MultiGameObject.dropChip()
                    invalidate()
                    // Move Chip left to right
                } else if (y > 0 && y < MultiGameObject.boardRows * unitHeight - 2)
                    for (i in 0 until MultiGameObject.boardCols)
                        if (x > i * unitWidth + 2 && x < (i + 1) * unitWidth) {
                            MultiGameObject.currCol = i
                            invalidate()
                            break
                        }
            } else if (x > 1 && x < boardWidth - 1 && y > 1 && y < boardHeight - 1) {
                MultiGameObject.start()
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        block = min(width / boardWidth, height / boardHeight).toFloat()
        x0 = (width - boardWidth * block) / 2
        y0 = (height - boardHeight * block) / 2
    }


}
