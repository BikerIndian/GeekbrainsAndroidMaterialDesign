package net.svishch.android.pictureoftheday.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import net.svishch.android.pictureoftheday.R
import net.svishch.android.pictureoftheday.ui.viewPager.ApiActivity


class SplashActivity : AppCompatActivity() {

    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val a = AnimationUtils.loadAnimation(this, R.anim.scalerotate);
        image_view.clearAnimation();
        image_view.startAnimation(a);




        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, ApiActivity::class.java))
            finish()
        }, 2000)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
