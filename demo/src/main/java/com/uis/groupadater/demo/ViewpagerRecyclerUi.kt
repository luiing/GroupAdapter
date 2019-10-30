package com.uis.groupadater.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.uis.groupadapter.GroupEntity
import com.uis.groupadater.demo.holder.*
import kotlinx.android.synthetic.main.ui_demo.*
import kotlinx.android.synthetic.main.ui_view_pin.view.*


class ViewpagerRecyclerUi: AppCompatActivity() {

    val adapter = DemoGroupAdapter()
    lateinit var pin: View
    lateinit var manager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        //Fresco.initialize(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_demo)
        pin = layoutInflater.inflate(R.layout.ui_view_pin,null)
        pin.bt_add.setOnClickListener{

        }
        pin.bt_clear.setOnClickListener{

        }
        for(i in 0 until 5) {
            adapter.addEntity(GroupEntity(VT_TXT, "txt $i"))
        }
        for(i in 0 until 5) {
            adapter.addEntity(GroupEntity(VT_TXT_BLUE, "txt blue $i"))
        }
        for(i in 0 until 5) {
            adapter.addEntity(GroupEntity(VT_TXT, "txt position $i"))
        }
        adapter.addEntity(GroupEntity(VT_PIN,pin))
        adapter.addEntity(GroupEntity(VT_VIEWPAGER,""))

        manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
    }



}