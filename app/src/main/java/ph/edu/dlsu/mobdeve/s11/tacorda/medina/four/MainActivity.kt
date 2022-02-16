package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.MainMenuActivity.Companion.leftvol
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.MainMenuActivity.Companion.rightvol


class MainActivity : AppCompatActivity() {
    //Main Activity that calls the Single Player Board Game
    lateinit var boardActivity: BoardActivity
    private var mediaMusic: MediaPlayer? = null
    private var beep: MediaPlayer? = null
    private var boop: MediaPlayer? = null
    private var cheer: MediaPlayer? = null
    private var draw: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startMusic()
        boardActivity = BoardActivity(this)
        setContentView(boardActivity)

    }

    private fun startMusic() {
        mediaMusic = MediaPlayer.create(this, R.raw.bg_raw)
        beep = MediaPlayer.create(this, R.raw.beep)
        boop = MediaPlayer.create(this, R.raw.boop)
        cheer = MediaPlayer.create(this, R.raw.cheer)
        draw = MediaPlayer.create(this, R.raw.draw)
        mediaMusic!!.setOnPreparedListener {
            playbackNow()
        }
    }

    private fun playbackNow() {
        mediaMusic!!.setVolume(leftvol, rightvol)
        mediaMusic!!.start()
        mediaMusic!!.isLooping = true
    }

    private fun pausePlayback() {
        mediaMusic!!.pause()
    }



    override fun onPause() {
        super.onPause()
        pausePlayback()
    }


    override fun onResume() {
        super.onResume()
        playbackNow()
    }

}