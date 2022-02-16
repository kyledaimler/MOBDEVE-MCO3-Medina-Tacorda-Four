package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.google.android.material.snackbar.Snackbar
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    val TAG = "HomeActivity"
    //Home Activity (Initial Screen)
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        Log.i(TAG, "App First Created")

        binding.btnnewprofile.setOnClickListener {
            var gotoProfileActivity = Intent(applicationContext, ProfileActivity::class.java)
        }

        binding.btnexistingprofile.setOnClickListener{
            startActivity(
                Intent(this@HomeActivity,MainMenuActivity::class.java))
        }
    }



    override fun onResume() {
        super.onResume()
        Log.i(TAG,"App restarted from being minimized")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG,"App minimized")
    }




}
