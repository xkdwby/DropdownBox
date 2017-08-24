package test.mydownm

import android.content.Context
import android.util.AttributeSet
import android.view.View

import com.jude.easyrecyclerview.EasyRecyclerView

class MyEasyRecyclerView : EasyRecyclerView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun getHeighth(): Int {
        var dm = context.resources.displayMetrics
        val screenHeight = dm.heightPixels
        return screenHeight / 3
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightSpec = MeasureSpec.makeMeasureSpec(getHeighth(), MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, heightSpec)
    }
}