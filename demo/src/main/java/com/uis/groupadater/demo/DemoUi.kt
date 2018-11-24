package com.uis.groupadater.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.uis.groupadapter.GroupAdapter
import com.uis.groupadapter.GroupEntity
import com.uis.groupadapter.GroupHolder
import kotlinx.android.synthetic.main.ui_demo.*
import kotlinx.android.synthetic.main.ui_item_txt.view.*
import kotlinx.android.synthetic.main.ui_item_txt_large.view.*

const val VT_TXT        = 0
const val VT_TXT_LARGE  = 1
const val VT_TXT_BLUE   = 2

open class DemoUi: AppCompatActivity() {

    lateinit var adapter: DemoAdapter
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_demo)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DemoAdapter()
        //groupNo from 0 to 2
        adapter.initGroup(3)
        recyclerView.adapter = adapter
        bt_add.setOnClickListener {
            val entity = GroupEntity((position % 3).let{
                    if(1 == it) VT_TXT_LARGE
                    else if(2 == it) VT_TXT_BLUE
                    else VT_TXT }, "item $position")
            adapter.addEntity(position % 3,entity)
            position++
        }
        bt_adds.setOnClickListener {
            val entities = ArrayList<GroupEntity>()
            entities.add(GroupEntity(VT_TXT_LARGE,"large"))
            entities.add(GroupEntity(VT_TXT_LARGE,"larger"))
            entities.add(GroupEntity(VT_TXT_LARGE,"largest"))
            adapter.addEntity(1,entities)
        }
        bt_remove.setOnClickListener {
            adapter.removeEntity(1)
        }
        bt_clear.setOnClickListener {
            adapter.clearAllEntity()
            position = 0
        }
    }

    class TxtVH(parent:ViewGroup) :GroupHolder<String>(R.layout.ui_item_txt,parent){
        override fun bindVH(item: String) {
            itemView.tv_txt.text = item
        }
    }

    class TxtBlueVH(parent:ViewGroup) :GroupHolder<String>(R.layout.ui_item_txt_blue,parent){
        override fun bindVH(item: String) {
            itemView.tv_txt.text = item
        }
    }

    class TxtLargeVH(parent:ViewGroup) :GroupHolder<String>(R.layout.ui_item_txt_large,parent){
        override fun bindVH(item: String) {
            itemView.tv_txt_large.text = item
        }
    }

    class DemoAdapter :GroupAdapter(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder<out Any>{
            return when(viewType){
                VT_TXT_LARGE -> TxtLargeVH(parent)
                VT_TXT_BLUE  -> TxtBlueVH(parent)
                else         -> TxtVH(parent)
            }
        }
    }
}