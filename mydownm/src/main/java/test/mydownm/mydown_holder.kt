package test.mydownm

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
class mydown_holder(parent: ViewGroup?, hei: Float) : BaseViewHolder<selected_bean>(parent, R.layout.item) {
    var heii = hei
    internal var det: TextView = `$`(R.id.tv)
    internal var under: TextView = `$`(R.id.under)
    internal var dui: ImageView = `$`(R.id.duigou)

    override fun setData(data: selected_bean) {
        if (heii == 0f) {
            itemView.layoutParams = RelativeLayout.LayoutParams(wrapContent, heightt)
        } else {
            itemView.layoutParams = RelativeLayout.LayoutParams(wrapContent, heii.toInt())
        }
        det.text = data.mytext
        det.textSize = fontsize
        if (data.selected) {
            det.setTextColor(textcolor)
            under.setBackgroundColor(Color.parseColor("#333531"))
            dui.visibility = View.VISIBLE
        } else {
            det.setTextColor(textcolor)
            under.setBackgroundColor(Color.parseColor("#f2f2f2"))
            dui.visibility = View.INVISIBLE
        }

    }
}