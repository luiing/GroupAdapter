package com.uis.groupadapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlin.collections.ArrayList

private const val TAG = "GroupAdapter"

abstract class GroupAdapter :RecyclerView.Adapter<GroupHolder<out Any>>() {

    var data :MutableList<MutableList<GroupEntity>> = ArrayList()
    var total = 0
    var groupNo = 0

    override fun getItemCount():Int = total

    override fun getItemViewType(position: Int): Int {
        return getEntity(position)?.viewType ?: 0
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: GroupHolder<out Any>, positon: Int){
        getEntity(positon)?.data?.let{data->
            (holder as? GroupHolder<Any>)?.bindVH(data)
        }
    }

    /**
     * 初始化组
     * @param group 组号
     */
    fun initGroup(groupSize :Int){
        if(groupNo <= 0) {
            groupNo = groupSize
            for (i in 0..(groupSize - 1)) {
                data.add(ArrayList())
            }
        }else{
            Log.w(TAG,"$TAG has already init")
        }
    }

    /**
     * 重制组
     * @param group 组号
     */
    fun resetGroup(groupSize :Int){
        clearAllEntity()
        groupNo = 0
        initGroup(groupSize)
    }

    fun addEntity(entity :GroupEntity) =addEntity(0,entity)

    fun addEntity(entities :MutableList<GroupEntity>) =addEntity(0,entities)

    fun addEntity(group :Int,entity :GroupEntity){
        if(isValid(group)){
            var position = 0
            for(i in 0..group){
                position += data[i].size
            }
            data[group].add(entity)
            total += 1
            notifyItemInserted(position)
        }
    }

    fun addEntity(group :Int,entities :MutableList<GroupEntity>){
        if(isValid(group)){
            var position = 0
            for(i in 0..group){
                position += data[i].size
            }
            data[group].addAll(entities)
            total += entities.size
            notifyItemRangeInserted(position,entities.size)
        }
    }

    fun removeEntity(group :Int){
        if(isValid(group)){
            var position = 0
            for(i in 0..(group-1)){
                position += data[i].size
            }
            val count = data[group].size
            data[group].clear()
            total -= count
            notifyItemRangeRemoved(position,count)
        }
    }

    /**
     * 移出全局的位置
     * @param positon 全局位置
     */
    fun removePositonEntity(positon: Int){
        var size = 0
        for(item in data){
            val itemSize = item.size
            if(positon < size + itemSize){
                item.removeAt(positon-size)
                total -= 1
                notifyItemRemoved(positon)
                break
            }
            size += itemSize
        }
    }

    fun clearAllEntity(){
        total = 0
        for (item in data){
            item.clear()
        }
        notifyDataSetChanged()
    }

    /**
     * 获取组开使的位置
     * @param group 组号
     */
    fun getPositon(group: Int) :Int{
        var position = 0
        if(isValid(group)){
            for(i in 0..(group-1)){
                position += data[i].size
            }
        }
        return position
    }

    /**
     * 获取组大小
     * @param group 组号
     */
    fun getSize(group: Int):Int{
        var size = 0
        if(isValid(group)){
            size = data[group].size
        }
        return size
    }

    private fun isValid(group :Int):Boolean{
        if(groupNo > 0 && group < groupNo && group >= 0){
            return true
        }else{
            Log.w(TAG,"$group outof groupSize $groupNo")
            return false
        }
    }

    private fun getEntity(positon:Int):GroupEntity? {
        var size = 0
        for(item in data){
            val itemSize = item.size
            if(positon < size + itemSize){
                return item[positon - size]
            }
            size += itemSize
        }
        return null
    }
}