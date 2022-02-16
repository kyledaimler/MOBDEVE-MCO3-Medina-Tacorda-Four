package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.s11.medina.tacorda.four.GameObject
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.databinding.ActivityInputBinding
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.databinding.ActivityMultiInputBinding
//Input Activity for Multiplayer Mode
class MultiInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMultiInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMultiInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val p1 = binding.textplayer1

        binding.btnstart.setOnClickListener{
            GameObject.p1name = p1.editableText.toString()
            startActivity(Intent(this@MultiInputActivity,MainActivity::class.java))
        }


    }
}