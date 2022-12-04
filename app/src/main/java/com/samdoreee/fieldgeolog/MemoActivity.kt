package com.samdoreee.fieldgeolog

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.Window
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        with(window){
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            // set an slide transition
            enterTransition = Slide().also {
                it.slideEdge = Gravity.BOTTOM
            }
            exitTransition = Slide().also {
                it.slideEdge = Gravity.TOP
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        val fabButton : FloatingActionButton = this.findViewById(R.id.Main_FabButton_fab)
        fabButton.setOnClickListener { v: View? ->
            val intent = Intent(this, WriteActivity::class.java)
            //애니메이션 옵션 추가
            val options = ActivityOptions.makeSceneTransitionAnimation(this)
            startActivity(intent, options.toBundle())
        }
    }
}