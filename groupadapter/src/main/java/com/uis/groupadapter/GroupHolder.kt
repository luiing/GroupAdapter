package com.uis.groupadapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class GroupHolder<T : Any> :RecyclerView.ViewHolder{
    constructor(view: View):super(view)
    constructor(layoutRes :Int,parent :ViewGroup):super(LayoutInflater.from(parent.context).inflate(layoutRes,parent,false))
    abstract fun bindVH(item: T)
}