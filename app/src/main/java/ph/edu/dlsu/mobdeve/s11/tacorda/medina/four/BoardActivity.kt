package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.view.MotionEvent
import android.view.View
import com.mobdeve.s11.medina.tacorda.four.GameObject
import kotlin.math.min
import android.content.res.Resources
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi


class BoardActivity(ctx: Context) : View(ctx) {

    //Units used to be used in the board canvas
    private companion object {

        const val unitRadius = 3
        const val unitHeight = 8
        const val unitWidth = 10
        const val boardWidth = GameObject.boardCols * unitWidth + 2
        const val boardHeight = GameObject.boardRows * unitWidth + 3
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
     //   const val cLblue2 = 0xFFE5F3FD.toInt()
     const val cLblue2 = 0xFFBFD6F5.toInt()

        fun getChipColor(id: Int) = when (id) {
            1 -> cRed
            -1 -> cYellow
            else -> cLblue2
        }
    }

    //Initialization of important variables for the game board
    private var block = min(width / boardWidth, height / boardHeight).toFloat()
    private var x0 = (width - boardWidth * block) / 2
    private var y0 = (height - boardHeight * block) / 2

    private fun getX(x: Int) = x0 + x * block
    private fun getY(y: Int) = y0 + y * block

    private fun getBlockX(j: Int) = getX(1 + j * unitWidth + unitWidth / 2)
    private fun getBlockY(i: Int) = getY(1 + (i + 1) * unitHeight + unitHeight / 2)

    private fun getField() = RectF(getX(1), getY(unitHeight),
        getX(boardWidth - 1), getY(boardHeight - 1))

    //Application of color and style
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        typeface = Typeface.createFromAsset(context.assets,"fredoka.ttf")
    }

    //Canvas Implementation (Logic and Board)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = cLblue
        canvas.drawPaint(paint)
        paint.color = cGray
        paint.textSize = 50f

        canvas.drawRoundRect(15f,20f,1065f,200f,30f,30f,paint)
        paint.color = cWhite

        val win = "Wins: " + GameObject.wins.toString()
        canvas.drawText(win, getX(boardWidth / 4), getY(unitHeight - 28), paint)
            canvas.drawText(GameObject.p1name, getX(boardWidth / 4), getY(unitHeight - 23), paint)

        val compwin = "Wins: " + GameObject.compwins.toString()
        canvas.drawText(compwin, 800f, getY(unitHeight - 28), paint)
        canvas.drawText("Computer", 800f, getY(unitHeight - 23), paint)

        paint.color = cDGray
        paint.strokeWidth = 20f
        canvas.drawLine(boardWidth.toFloat()+470,20f, boardWidth.toFloat()+470,200f,paint)

        paint.color = cRed
        canvas.drawCircle(100f, 110f, 60f,paint)


        paint.color = cYellow
        canvas.drawCircle(985f, 110f, 60f,paint)


//        val bg = BitmapFactory.decodeResource(resources,R.drawable.bg)
//        canvas.drawBitmap(bg,0f,0f,null)

        if (GameObject.state != GameObject.State.START) {
            paint.color = cBlue
            canvas.drawRect(getField(), paint)

            if (GameObject.state == GameObject.State.TURN) {
                paint.color = getChipColor(GameObject.currTurn)
                //draw top chip
                canvas.drawCircle(getBlockX(GameObject.currCol), getY(unitHeight / 2),
                    unitRadius * block, paint)
            } else {
                paint.color = cBlue
                paint.textSize = endText * block
                val text = when (GameObject.winPlayer) {
                    1 -> "Player 1 Wins!"
                    -1 -> "Computer Wins!"
                    else -> "Draw."
                }
                paint.textSize = 85f
                canvas.drawText("Tap screen to play again",boardWidth.toFloat()+470,1500f,paint)
                if(GameObject.winPlayer == 1)
                    GameObject.wins +=1
                else
                    GameObject.compwins+=1

                canvas.drawText(text, getX(boardWidth / 2), getY(unitHeight - 2), paint)

                if (GameObject.winPlayer != 0) {
                    for ((i, j) in GameObject.winLine) {
                        paint.color = getChipColor(-GameObject.field[i][j])
                        //draw circle around the winning 4
                        canvas.drawCircle(getBlockX(j), getBlockY(i), (unitRadius + .5f) * block, paint)
                    }
                }
            }

            for (i in 0 until GameObject.boardRows) {
                for (j in 0 until GameObject.boardCols) {
                    paint.color = getChipColor(GameObject.field[i][j])
                    //white circle around the board
                    canvas.drawCircle(getBlockX(j), getBlockY(i), unitRadius * block, paint)
                }
            }

        }
    }
    //Function for touch gestures within the game
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && event.action == MotionEvent.ACTION_DOWN) {
            val x = (event.x - x0) / block
            val y = (event.y - y0) / block
            if (GameObject.state == GameObject.State.TURN) {
                // Drop Chip
                if (x > 1 && x < boardWidth - 1 && y > GameObject.boardRows * unitHeight && y < boardHeight - 1) {
                    GameObject.dropChip()
                    invalidate()
                // Move Chip left to right
                } else if (y > 0 && y < GameObject.boardRows * unitHeight - 2)
                    for (i in 0 until GameObject.boardCols)
                        if (x > i * unitWidth + 2 && x < (i + 1) * unitWidth) {
                            GameObject.currCol = i
                            invalidate()
                            break
                        }
            } else if (x > 1 && x < boardWidth - 1 && y > 1 && y < boardHeight - 1) {
                GameObject.start()
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
