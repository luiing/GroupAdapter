package com.uis.groupadater.demo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.uis.groupadapter.GroupEntity
import com.uis.groupadater.demo.holder.*
import com.uis.groupadater.demo.test.OnPinListener
import kotlinx.android.synthetic.main.ui_demo.*
import kotlinx.android.synthetic.main.ui_demo_main.*
import kotlinx.android.synthetic.main.ui_view_pin.view.*


class DemoUi: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_demo_main)

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