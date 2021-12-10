package project.st991532818.org

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * Name: Rishabh Bajaj
 * Student Id: 991532818
 * Date: 2021-12-04
 * Description: Splash Screen Activity
 */
class SplashScreenActivity : AppCompatActivity() {
    var animation: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)
        animation = AnimationUtils.loadAnimation(this, R.anim.animation)
        var imageView: ImageView? = findViewById(R.id.imageView)
        var appName: TextView? = findViewById(R.id.appName)

        imageView?.animation = animation
        appName?.animation = (animation)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH.toLong())
//        FirebaseAuth.getInstance().signOut()

    }

    companion object {
        private const val SPLASH = 3000
    }
}