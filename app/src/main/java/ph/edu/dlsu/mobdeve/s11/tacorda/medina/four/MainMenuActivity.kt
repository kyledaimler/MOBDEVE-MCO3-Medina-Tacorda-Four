package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.SeekBar
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.databinding.ActivityHomeBinding
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.databinding.ActivityMainMenuBinding
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener


class MainMenuActivity : AppCompatActivity() {

    //Main Menu of Four
    //Contains the navigation around the application
    private var backButton: ImageButton? = null
    private var volumeSeekbar: SeekBar? = null


    private var mediaMusic: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var beep: MediaPlayer? = null
    private var boop: MediaPlayer? = null
    private var cheer: MediaPlayer? = null
    private var draw: MediaPlayer? = null

    companion object {
        var leftvol = 0.6f
        var rightvol = 0.6f

    }


    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startMusic()


        binding.btnsettings.setOnClickListener {
            startActivity(
                Intent(this@MainMenuActivity,SettingsActivity::class.java)
            )
        }


        binding.btnsingleplayer.setOnClickListener{
            startActivity(
                Intent(this@MainMenuActivity,MultiInputActivity::class.java)
            )
        }
        binding.btnmultiplayer.setOnClickListener{
            startActivity(
                Intent(this@MainMenuActivity,InputActivity::class.java)
            )
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