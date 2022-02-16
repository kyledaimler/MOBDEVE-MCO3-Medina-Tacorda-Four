package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.databinding.ActivitySettingsBinding
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.MainMenuActivity.Companion.leftvol
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.MainMenuActivity.Companion.rightvol
import kotlin.system.exitProcess
//Settings Activity where the audio and quitting game mechanics can be found

class SettingsActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySettingsBinding
    private var volumeSeekbar: SeekBar? = null

    private var mediaMusic: MediaPlayer? = null

    private var beep: MediaPlayer? = null
    private var boop: MediaPlayer? = null
    private var cheer: MediaPlayer? = null
    private var draw: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startMusic()

        //Initializing of the Audio Manager
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        binding.volume.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        binding.volume.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        println(binding.volume.progress)

        binding.volume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(arg0: SeekBar) {}

            override fun onStartTrackingTouch(arg0: SeekBar) {}

            override fun onProgressChanged(arg0: SeekBar, progress: Int, arg2: Boolean) {
                val mediaVol = progress.toFloat() / 15
                mediaMusic!!.setVolume(mediaVol, mediaVol)
                leftvol = mediaVol
                rightvol = mediaVol

            }
        })

        //Returns to MainMenu Activity
        binding.backButton.setOnClickListener{
            startActivity(
                Intent(this@SettingsActivity,MainMenuActivity::class.java)
            )
        }

        //Allows to exit the game properly
        binding.imageButton.setOnClickListener{
            val builder = AlertDialog.Builder(this@SettingsActivity)
            builder.setTitle("Confirm Exit.")
            builder.setMessage("Do you want to Exit Four?")
            builder.setPositiveButton("YES") { _, _ ->
                // Do something when user press the positive button
                moveTaskToBack(true)
                exitProcess(-1)
            }
            builder.setNegativeButton("No") { _, _ -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
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