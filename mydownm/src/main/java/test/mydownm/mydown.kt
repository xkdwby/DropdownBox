package test.mydownm

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.jude.easyrecyclerview.EasyRecyclerView
import kotlinx.android.synthetic.main.main.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.onClick
import org.jetbrains.anko.wrapContent
import java.util.*

open class mydown : RelativeLayout {
    private var mContext: Context? = null
    private var pw: PopupWindow? = null
    private var w: Int = 0
    private var h: Int = 0
    private var q: recycler_adapter? = null
    private var viewPop: View? = null
    var viewmain: View? = null
    var styy: Int? = 0//多种上部布局样式。0：为默认，文字居中，箭头在最右|1：文字和箭头一起居中
    var styybottom: Int? = 0//多种列表下部样式。0：为默认，列表宽度与上部相，列表到屏幕底部的部分全透|1：列表宽度为屏幕宽度，列表下部到屏幕下部半透明

    constructor(context: Context, i: Int?) : super(context) {//代码创建控件
        mContext = context
        styy = i
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {//写在布局文件
        mContext = context
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.downstyle)
        styy = mTypedArray.getInt(R.styleable.downstyle_mydownstyle, 0)
        styybottom = mTypedArray.getInt(R.styleable.downstyle_mydownbottomstyle, 0)
        init()
    }

    // 动画
    private fun startPropertyAnim(a: Float, b: Float) {
        val anim = ObjectAnimator.ofFloat(rightimg, "rotation", a, b)
        anim.duration = 200
        anim.addUpdateListener { animation -> val value = animation.animatedValue as Float }
        anim.start()
    }

    fun init() {
        // 准备弹出视图
        if (styy == 0) {
            viewmain = View.inflate(mContext, R.layout.main, null)
        } else if (styy == 1) {
            viewmain = View.inflate(mContext, R.layout.main2, null)
        }
        viewPop = View.inflate(mContext, R.layout.easyrecyclerview_normal, null)
        viewPop!!.find<EasyRecyclerView>(R.id.mydownrec).apply {
            //            addItemDecoration(itemDecoration)
            setLayoutManager(LinearLayoutManager(mContext))
        }
        this@mydown.post {
            test.mydownm.heightt=this@mydown.height
            w = this@mydown.width
            h = getScreenHeight(context) - this@mydown.height - this@mydown.y.toInt() - getStatusHeight(context)
            if (styybottom == 0) {
                pw = PopupWindow(viewPop, w, wrapContent).apply {
                    //最正常没阴影的
                    isFocusable = true//设置返回键关闭
                    isOutsideTouchable = true//设置菜单以外的点击生效 并闭菜单
                    setOnDismissListener { startPropertyAnim(180f, 0f) }
                }
            } else {
                pw = PopupWindow(viewPop, matchParent, h).apply {
                    setBackgroundDrawable(ColorDrawable(0x55000000))
                    isFocusable = true//设置返回键关闭
                    isOutsideTouchable = true//设置菜单以外的点击生效 并闭菜单
                    setOnDismissListener { startPropertyAnim(180f, 0f) }
//                    onClick { dismiss() }
                }
            }
        }
        q = recycler_adapter(1, mContext)
//        val itemDecoration = DividerDecoration(Color.parseColor("#ffe1e1e1"), 1, dip(18), dip(18))
//        itemDecoration.setDrawLastItem(false)
        viewPop!!.find<EasyRecyclerView>(R.id.mydownrec).apply {
            //            addItemDecoration(itemDecoration)
            adapter = q
        }
        this@mydown?.onClick {
            startPropertyAnim(0f, 180f)
            pw?.showAsDropDown(this, 0, 0)
        }
        this@mydown.addView(viewmain)

    }

    fun setmainback(back: GradientDrawable) {
        this@mydown.background = back
        invalidate()
    }

    fun setlistback(backk: GradientDrawable) {
        viewPop!!.find<MyEasyRecyclerView>(R.id.mydownrec).apply {
            background = backk
        }
        invalidate()
    }

    private fun <T> mydownclick(position: Int, bean: T?, callBack: Func1<T>?) {
        if (callBack != null) {
            try {
                callBack.itemclick(position, bean)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    interface Func1<T> {
        fun itemclick(itemPostion: Int, bean: T?)
    }

    fun <T> setitemonclick(datamap: LinkedHashMap<String, T>?, data: List<String>?, callBack: Func1<T>) {
        if (data != null && datamap == null) {//仅带字符串，前端写定的
            var w1 = ArrayList<selected_bean>()
            data.forEach { var temp = selected_bean();temp.mytext = it;w1.add(temp) }
            w1[0].selected=true
            q?.addAll(w1)
            tmain.text = data[0]
            q?.setOnItemClickListener { position ->
                w1.forEach { it.selected = false }
                w1[position].selected = true
                q?.notifyDataSetChanged()
                tmain.text = data!![position]
                pw?.dismiss()
                mydownclick(position, null, callBack)
            }
        } else if (datamap != null && data == null) {//仅带对象，后台传list内容//key为列表显示的内容，value为对应的bean
            var w0 = ArrayList<selected_bean>()
            datamap.forEach { var temp = selected_bean();temp.mytext = it.key;w0.add(temp) }
            q?.addAll(w0)
            var w = ArrayList<String>()
            w0[0].selected=true
            w.addAll(datamap.keys)
            tmain.text = w[0]
            q?.setOnItemClickListener { position ->
                w0.forEach { it.selected = false }
                w0[position].selected = true
                q?.notifyDataSetChanged()
                tmain.text = w!![position]
                pw?.dismiss()
                var w2 = ArrayList<T>()
                w2.addAll(datamap?.values!!)
                mydownclick(position, w2[position], callBack)
            }
        } else {
            Log.i("wby", "下拉列表数据设置错误")
        }
    }

}
