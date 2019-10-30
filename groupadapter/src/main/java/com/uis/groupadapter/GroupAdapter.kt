package com.uis.groupadapter

import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList


abstract class GroupAdapter: RecyclerView.Adapter<GroupHolder<out Any>>(){

    val data :MutableList<MutableList<GroupEntity>> = ArrayList(6)
    var total = 0

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
     * 初始化或重置组,组号范围[0,size-1]
     * @param group 组号
     */
    @Deprecated("not need method,it support auto increase capacity")
    fun initGroup(size :Int){
        clearAllEntity()
    }

    fun addEntity(entity :GroupEntity) =addEntity(0,entity)

    fun addEntity(entities :MutableList<GroupEntity>) =addEntity(0,entities)

    fun addEntity(group :Int,entity :GroupEntity){
        addEntity(group, mutableListOf(entity))
    }

    fun addEntity(group :Int,entities :MutableList<GroupEntity>){
        increaseCapacity(group)
        var position = 0
        for(i in 0..group){
            position += data[i].size
        }
        data[group].addAll(entities)
        total += entities.size
        notifyItemRangeInserted(position,entities.size)
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
        increaseCapacity(group)
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

    fun changeEntity(entities :MutableList<GroupEntity>)=changeEntity(0,entities)

    /**
     * 替换组号下列表
     * @param group 组号
     * @param entities
     */
    fun changeEntity(group :Int,entities :MutableList<GroupEntity>){
        increaseCapacity(group)
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

    fun removeEntity(group :Int){
        increaseCapacity(group)
        var position = 0
        for(i in 0..(group-1)){
            position += data[i].size
        }
        val count = data[group].size
        data[group].clear()
        total -= count
        notifyItemRangeRemoved(position,count)
    }

    /**
     * 移出全局的位置
     * @param positon 全局位置
     */
    fun removePositonEntity(position: Int){
        var size = 0
        for(item in data){
            size += item.size
            if(position < size){
                item.removeAt(position-(size-item.size))
                total -= 1
                notifyItemRemoved(position)
                break
            }
        }
    }

    /**
     * 清除所有数据
     */
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
        increaseCapacity(group)
        var position = 0
        for(i in 0..(group-1)){
            position += data[i].size
        }
        return position
    }

    /**
     * 获取组大小
     * @param group 组号
     */
    fun getSize(group: Int):Int{
        increaseCapacity(group)
        return data[group].size
    }

    private fun increaseCapacity(group :Int){
        val start = data.size
        for(i in start .. group step 1){
            data.add(ArrayList(8))
        }
    }

    /** 获取Entity
     * @param positon adapterPosition
     */
    private fun getEntity(positon:Int):GroupEntity? {
        var size = 0
        for(item in data){
            size += item.size
            if(positon < size){
                return item[positon - (size-item.size)]
            }
        }
        return null
    }
}