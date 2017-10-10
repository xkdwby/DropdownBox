package test.myDropDownBox

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import org.jetbrains.anko.wrapContent

/**
 * Created by wby on 2017/8/21.
 */
class mydown_holder(parent: ViewGroup?, hei: Int) : BaseViewHolder<selected_bean>(parent, R.layout.item) {
    var heii = hei
    internal var det: TextView = `$`(R.id.tv)
    internal var under: TextView = `$`(R.id.under)
    internal var dui: ImageView = `$`(R.id.duigou)

    var selectedColor:Int= Color.parseColor("#333531")
    var unSelectedColor:Int= Color.parseColor("#848583")
    var fontSize:Float?=14f

    fun setSelectedColor(a:Int?){
        selectedColor=a?:selectedColor
    }

    fun setUnSelectedColor(b:Int?){
        selectedColor=b?:unSelectedColor
    }

    fun fontSize(f:Float?){
        fontSize=f?:fontSize
    }
    override fun setData(data: selected_bean) {
        itemView.layoutParams = RelativeLayout.LayoutParams(wrapContent, heii)
        det.text = data.mytext
        det.textSize = fontSize!!
        if (data.selected) {
            det.setTextColor(selectedColor!!)
            under.setBackgroundColor(Color.parseColor("#333531"))
            dui.visibility = View.VISIBLE
        } else {
            det.setTextColor(unSelectedColor!!)
            under.setBackgroundColor(Color.parseColor("#f2f2f2"))
            dui.visibility = View.INVISIBLE
        }

    }
}