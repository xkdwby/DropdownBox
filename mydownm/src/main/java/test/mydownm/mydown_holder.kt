package test.mydownm

import android.view.ViewGroup
import android.widget.TextView
import com.jude.easyrecyclerview.adapter.BaseViewHolder

/**
 * Created by wby on 2017/8/21.
 */
class mydown_holder(parent: ViewGroup?) : BaseViewHolder<String>(parent, R.layout.item) {
    internal var det: TextView = `$`(R.id.tv)

    override fun setData(data: String) {
        det.text = data
    }
}