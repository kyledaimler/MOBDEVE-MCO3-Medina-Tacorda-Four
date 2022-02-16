package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.databinding.ActivityInputBinding
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.MainMenuActivity.Companion.leftvol
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.MainMenuActivity.Companion.rightvol

class InputActivity : AppCompatActivity() {

    //Player Input Activity
    private lateinit var binding: ActivityInputBinding
    private var mediaMusic: MediaPlayer? = null
    private var beep: MediaPlayer? = null
    private var boop: MediaPlayer? = null
    private var cheer: MediaPlayer? = null
    private var draw: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startMusic()
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val p1 = binding.textplayer1
        val p2 = binding.textplayer2


        binding.btnstart.setOnClickListener{
            MultiGameObject.p1name = p1.editableText.toString()
            MultiGameObject.p2name = p2.editableText.toString()
            startActivity(Intent(this@InputActivity,MainMultiActivity::class.java))
        }


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