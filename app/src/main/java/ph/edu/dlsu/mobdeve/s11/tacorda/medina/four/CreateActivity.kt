package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.databinding.ActivityCreateBinding

class CreateActivity : AppCompatActivity(){

    private lateinit var binding: ActivityCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val dataHelper = DBHelper(this)

        binding.bInsert.setOnClickListener{
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Confirm Create Player")
                .setMessage("Are you sure create profile?")
                .setCancelable(true)
                .setPositiveButton("No"){dialog,which->
                }
                .setNegativeButton("Yes"){dialog,which->
                    val wins = 0
                    val name = binding.etName.text.toString()
                    val gender = findViewById<RadioButton>(binding.rgGender.checkedRadioButtonId)
                    dataHelper.addPlayer(Player(wins,name,gender.text.toString()))
                    binding.etName.setText("")
                }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}