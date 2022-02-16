package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import android.widget.TextView

//Splash activity that enables the splash art intro of the App

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.logo)
        val logoText = findViewById<TextView>(R.id.logo_text)
        val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val fade = AnimationUtils.loadAnimation(this, R.anim.fade)
        bounce.interpolator = BounceInterpolator()
        logo.startAnimation(bounce)
        Handler().postDelayed({
            logoText.startAnimation(fade)
            logoText.visibility = View.VISIBLE
        }, 1000)
        val staticTimeOut = 3000
        Handler().postDelayed({
            val homeIntent = Intent(this@SplashActivity, CreateViewActivity::class.java)
            startActivity(homeIntent)
            finish()
        }, staticTimeOut.toLong())
    }
}