package com.uis.groupadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GroupHolder<T : Any> : RecyclerView.ViewHolder{
    constructor(view: View):super(view)
    constructor(layoutRes :Int,parent :ViewGroup):super(LayoutInflater.from(parent.context).inflate(layoutRes,parent,false))
    open fun bindVH(item: T){}
}