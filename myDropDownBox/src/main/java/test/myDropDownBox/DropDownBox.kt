package test.myDropDownBox

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.jude.easyrecyclerview.EasyRecyclerView
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import kotlinx.android.synthetic.main.main.view.*
import org.jetbrains.anko.*
import java.util.*

open class DropDownBox : RelativeLayout {
    private var mContext: Context? = null
    private var pw: PopupWindow? = null
    private var h: Int = 0
    private var q: RecyclerArrayAdapter<Any>? = null
    private var viewPop: View? = null
    private var recycle: EasyRecyclerView? = null
    private var holder: mydown_holder? = null
    var viewmain: View? = null
    var styy: Int? = 0//多种上部布局样式。0：为默认，文字居中，箭头在最右|1：文字和箭头一起居中
    var styybottom: Int? = 0//多种列表下部样式。0：为默认，列表宽度与上部相，列表到屏幕底部的部分全透|1：列表宽度为屏幕宽度，列表下部到屏幕下部半透明
    var itemheight: Int? = dip(0)//下拉列表的item高度

    constructor(context: Context, i: Int? = 0, j: Int? = 0, h: Int? = 0) : super(context) {//代码创建控件
        mContext = context
        styy = i
        styybottom = j
        itemheight = h
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {//写在布局文件
        mContext = context
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.downstyle)
        styy = mTypedArray.getInt(R.styleable.downstyle_mydownstyle, 0)
        styybottom = mTypedArray.getInt(R.styleable.downstyle_mydownbottomstyle, 0)
        itemheight = mTypedArray.getDimension(R.styleable.downstyle_itemheight, dip(0).toFloat()).toInt()
        init()
    }

    // 动画
    private fun startPropertyAnim(a: Float, b: Float) {//箭头的动画
        val anim = ObjectAnimator.ofFloat(rightimg, "rotation", a, b)
        anim.duration = 200
        anim.addUpdateListener { animation -> val value = animation.animatedValue as Float }
        anim.start()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun init() {
        // 准备弹出视图
        if (styy == 0) {//不同的顶部视图样式
            viewmain = View.inflate(mContext, R.layout.main, null)
        } else if (styy == 1) {
            viewmain = View.inflate(mContext, R.layout.main2, null)
        }
        viewPop = View.inflate(mContext, R.layout.easyrecyclerview_halftrans, null)

        viewPop!!.find<RelativeLayout>(R.id.halfback).apply {
            onClick {
                pw?.dismiss()
            }
        }
        onClick {
            startPropertyAnim(0f, 180f)
            pw?.showAsDropDown(this, 0, 0)
        }
        recycle = viewPop!!.find<EasyRecyclerView>(R.id.mydownrec).apply {
            setLayoutManager(LinearLayoutManager(mContext))
            //列表适配器
            q = object : RecyclerArrayAdapter<Any>(mContext) {
                override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> {
                    holder = mydown_holder(parent, if (itemheight == 0) {
                        dip(50)
                    } else {
                        itemheight!!
                    })
                    return holder!!
                }
            }

            adapter = q
        }
        addView(viewmain)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initPop(w, h)
    }

    fun initPop(w: Int, H: Int) {
        if (styybottom == 0) {//不同的列表高度，即显示不同的底部视图，透明和半透明
            viewPop!!.find<RelativeLayout>(R.id.halfback).apply {
                background = ColorDrawable(0x00000000)
            }
            pw = PopupWindow(viewPop, w, wrapContent).apply {
                //最正常没阴影的
                setBackgroundDrawable(ColorDrawable(0x00000000))
                isFocusable = true//设置返回键关闭
                isOutsideTouchable = true//设置菜单以外的点击生效 并闭菜单
                setOnDismissListener { startPropertyAnim(180f, 0f) }
            }
        } else {
            h = getScreenHeight(context) - H - (getScreenHeight(context) / 13.5).toInt()- this@DropDownBox.y.toInt() - getStatusHeight(context)//由于上面有include布局获取不到正确的y轴坐标
            //        h = getScreenHeight(context) - H - this@DropDownBox.y.toInt() - getStatusHeight(context)
            pw = PopupWindow(viewPop, matchParent, h).apply {
                setBackgroundDrawable(ColorDrawable(0x50000000))
                isFocusable = true//设置返回键关闭
                isOutsideTouchable = true//设置菜单以外的点击生效 并闭菜单
                setOnDismissListener { startPropertyAnim(180f, 0f) }
            }
        }
    }

    fun setmainback(back: GradientDrawable) {//设置头部的背景
        this@DropDownBox.background = back
        invalidate()
    }

    fun setSelectedTextcolorandFontsize(scolor: Int?, ucolor: Int?, size: Float) {//设置选中的文字的颜色和文字大小
        holder?.setSelectedColor(scolor)
        holder?.setSelectedColor(ucolor)
        holder?.fontSize = size
        invalidate()
    }

    fun setlistback(backk: GradientDrawable) {//设置列表的大背景
        viewPop!!.find<MyEasyRecyclerView>(R.id.mydownrec).apply {
            background = backk
        }
        invalidate()
    }

    //列表点击的事件处理
    private fun <T> mydownclick(position: Int, bean: T?, callBack: Func1<T>?) {
        if (callBack != null) {
            try {
                callBack.itemClick(position, bean)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    interface Func1<in T> {
        fun itemClick(position: Int, bean: T?)
    }

    fun <T> setitemonclick(data: List<String>?, databean: List<T>?, datamap: LinkedHashMap<String, T>?, callBack: Func1<T>) {
        //data为头部字符串数组|databean为对应的bean数组|datamap为key为显示的字符，value为其对应的bean的map
        if (data != null) {
            if (databean == null) {//仅带字符串，前端写定的
                val w1 = ArrayList<selected_bean>()
                data.forEach { val temp = selected_bean();temp.mytext = it;w1.add(temp) }
                w1[0].selected = true
                q?.addAll(w1)
                tmain.text = data[0]
                q?.setOnItemClickListener { position ->
                    w1.forEach { it.selected = false }
                    w1[position].selected = true
                    q?.notifyDataSetChanged()
                    tmain.text = data[position]
                    pw?.dismiss()
                    mydownclick(position, data[position] as T, callBack)
                }
            } else if (databean.size == data.size) {//带对应的对象，后台传list内容
                val w0 = ArrayList<selected_bean>()
                data.forEach { val temp = selected_bean();temp.mytext = it;w0.add(temp) }
                w0[0].selected = true
                q?.addAll(w0)
                tmain.text = data[0]
                q?.setOnItemClickListener { position ->
                    w0.forEach { it.selected = false }
                    w0[position].selected = true
                    q?.notifyDataSetChanged()
                    tmain.text = data[position]
                    pw?.dismiss()
                    mydownclick(position, databean[position], callBack)
                }
            }
        } else {
            if (datamap != null) {
                val w0 = ArrayList<selected_bean>()
                datamap.forEach { val temp = selected_bean();temp.mytext = it.key;w0.add(temp) }
                q?.addAll(w0)
                val w = ArrayList<String>()
                w0[0].selected = true
                w.addAll(datamap.keys)
                tmain.text = w[0]
                q?.setOnItemClickListener { position ->
                    w0.forEach { it.selected = false }
                    w0[position].selected = true
                    q?.notifyDataSetChanged()
                    tmain.text = w[position]
                    pw?.dismiss()
                    val w2 = ArrayList<T>()
                    w2.addAll(datamap.values)
                    mydownclick(position, w2[position], callBack)
                }
            } else {
                Log.i("wby", "下拉列表数据设置错误")
            }
        }
    }
}
