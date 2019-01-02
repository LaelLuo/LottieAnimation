package io.github.laelluo.lottieanimation

import android.animation.Animator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animationView.addAnimatorUpdateListener { animation ->
            if (isPlaying) seekBar.setProgress((animation.animatedValue as Float * 1000).toInt(), false)
        }
        switchView.setOnCheckedChangeListener { _, isChecked ->
            isPlaying = isChecked
            switchView.isClickable = false
            switchView.animate()
                .setInterpolator(LinearInterpolator())
                .setDuration(500)
                .rotationBy(360f)
                .start()
            seekBar.animate()
                .setInterpolator(LinearInterpolator())
                .setDuration(500)
                .rotationBy(-360f)
                .setListener(object :Animator.AnimatorListener{
                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {
                        switchView.isClickable = true
                    }
                })
                .start()
            if (isChecked) animationView.resumeAnimation() else animationView.pauseAnimation()
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!isPlaying) animationView.progress = progress / 1000f
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
