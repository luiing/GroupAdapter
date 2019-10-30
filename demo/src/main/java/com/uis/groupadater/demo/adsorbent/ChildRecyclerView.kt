package com.uis.groupadater.demo.adsorbent

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class ChildRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        /** true child在顶部*/
        val isChildTop = !canScrollVertically(-1)
        var pv = parent
        while (pv != null) {
            if (pv is OnInterceptListener) {
                pv.onTopChild(isChildTop)
                break
            }
            pv = pv.parent
        }
        return super.dispatchTouchEvent(ev)
    }
}