package com.uis.groupadater.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ui_demo_main.*

class DemoUi: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_demo_main)
        //dv_image.setImageURI("http://www.baidu.com/img/bd_logo1.png")
        //dv_image.setImageURI("https://img22.st.iblimg.com/market-2/images/activity/1688657991.jpg")
        bt_single.setOnClickListener{
            val intent = Intent(this,SingleRecyclerUi::class.java)
            startActivity(intent)
        }

        bt_double.setOnClickListener{
            val intent = Intent(this,DoubleRecyclerUi::class.java)
            startActivity(intent)
        }

        bt_viewpager.setOnClickListener{
            val intent = Intent(this,ViewpagerRecyclerUi::class.java)
            startActivity(intent)
        }
    }
}