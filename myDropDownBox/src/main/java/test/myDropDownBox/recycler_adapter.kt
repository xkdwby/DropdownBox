package test.myDropDownBox

import android.content.Context
import android.view.ViewGroup
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter

/**
 * Created by wby on 2017/8/21.
 */
internal class recycler_adapter(internal var type: Int, con: Context?,height:Int) : RecyclerArrayAdapter<Any>(con) {
    var he=height
    override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*>? {
        var base: BaseViewHolder<*>? = null
        when (type) {
            1 -> base = mydown_holder(parent,he)//item
        }
        return base
    }

}