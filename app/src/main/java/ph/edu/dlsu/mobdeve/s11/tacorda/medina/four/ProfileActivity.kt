package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.databinding.ActivityProfileBinding

//Sets the Profile Information

class ProfileActivity : AppCompatActivity() {
    val TAG = "ProfileActivity"
    private lateinit var prof: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prof = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(prof.root)

        prof.btnsaveprofile.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("name", prof.textusername.toString())

            var resultIntent = Intent()
//            resultIntent.putExtra("username", prof.textusername.toString())
//            resultIntent.putExtra("username", prof.textfirstname.toString())
//            resultIntent.putExtra("username", prof.textlastname.toString())
//            resultIntent.putExtra("username", prof.textbirthdate.toString())
//            resultIntent.putExtra("email", prof.textemail.toString())
            resultIntent.putExtras(bundle)


            setResult(RESULT_OK, resultIntent)
            finish()


        }
    }
    override fun onResume() {
        super.onResume()
        Log.i(TAG,"App restarted from being minimized")
//        Snackbar.make(prof.root,"Profile Loaded!",
//            Snackbar.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG,"App minimized")
    }


}