package com.uis.groupadapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlin.collections.ArrayList

private const val TAG = "GroupAdapter"

abstract class GroupAdapter :RecyclerView.Adapter<GroupHolder<out Any>>() {

    var data :MutableList<MutableList<GroupEntity>> = ArrayList(6)
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
     * 初始化组,组号范围[0,size-1]
     * @param group 组号
     */
    fun initGroup(size :Int){
        if(groupNo <= 0) {
            groupNo = size
            for (i in 0..(size - 1)) {
                data.add(ArrayList(8))
            }
        }else{
            Log.w(TAG,"$TAG has already init")
        }
    }

    /**
     * 重制组
     * @param size 组容量<=最大组号
     */
    fun resetGroup(size :Int){
        clearAllEntity()
        groupNo = 0
        initGroup(size)
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

    /** 更新全局position位置
     * @param position 全局position
     */
    fun changePositionEntity(position: Int,entity: GroupEntity){
        var size = 0
        for(item in data){
            size += item.size
            if(position < size ){
                item.set(position-(size-item.size),entity)
                notifyItemChanged(position)
                break
            }
        }
    }

    fun changeEntity(entity: GroupEntity)=changeEntity(0,entity)

    /** 更新组号下第0个位置数据，此组无数据不更新
     * @param group组号
     * @param entity
     */
    fun changeEntity(group: Int,entity: GroupEntity){
        if(isValid(group)){
            val gp = data[group]
            if(gp.size > 0) {
                var position = 0
                for(i in 0 until group){
                    position += data[i].size
                }
                gp.set(0,entity)
                notifyItemChanged(position)
            }
        }
    }

    fun changeEntity(entities :MutableList<GroupEntity>)=changeEntity(0,entities)

    /**
     * 替换组号下列表
     * @param group 组号
     * @param entities
     */
    fun changeEntity(group :Int,entities :MutableList<GroupEntity>){
        if(isValid(group)){
            var position = 0
            for(i in 0 until group){
                position += data[i].size
            }
            val groupItem = data[group]
            val lastSize = groupItem.size
            val newSize = entities.size
            groupItem.clear()
            groupItem.addAll(entities)
            if(lastSize == newSize){
                notifyItemRangeChanged(position,newSize)
            }else{
                total += newSize - lastSize
                notifyDataSetChanged()
            }
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
    fun removePositonEntity(position: Int){
        var size = 0
        for(item in data){//5,5,5 10
            size += item.size
            if(position < size){
                item.removeAt(position-(size-item.size))
                total -= 1
                notifyItemRemoved(position)
                break
            }
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

    private fun getEntity(position:Int):GroupEntity? {
        var size = 0
        for(item in data){
            val itemSize = item.size
            if(position < size + itemSize){
                return item[position - size]
            }
            size += itemSize
        }
        return null
    }
}