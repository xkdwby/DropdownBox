package test.mydownm

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.jude.easyrecyclerview.EasyRecyclerView
import com.jude.easyrecyclerview.decoration.DividerDecoration
import kotlinx.android.synthetic.main.main.view.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import org.jetbrains.anko.wrapContent

open class mydown : RelativeLayout {
    private var mContext: Context? = null
    private var pw: PopupWindow? = null
    private var w: Int = 0
    private var q: recycler_adapter? = null
    private var viewPop: View? = null
    var viewmain: View? = null
    var styy: Int? = 0

    constructor(context: Context, i: Int?) : super(context) {//代码创建控件
        mContext = context
        styy = i
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {//写在布局文件
        mContext = context
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.downstyle)
        styy = mTypedArray.getInt(R.styleable.downstyle_mydownstyle, 0)
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
        } else {
            viewmain = View.inflate(mContext, R.layout.main2, null)
        }
        viewPop = View.inflate(mContext, R.layout.easyrecyclerview_normal, null)
        this@mydown.post {
            w = this@mydown.width
            pw = PopupWindow(viewPop, w, wrapContent).apply {
                setBackgroundDrawable(ColorDrawable(0x00000000))
                isFocusable = true//设置返回键关闭
                isOutsideTouchable = true//设置菜单以外的点击生效 并闭菜单
                setOnDismissListener { startPropertyAnim(180f, 0f) }
            }
        }
        q = recycler_adapter(1, mContext)
        val itemDecoration = DividerDecoration(Color.parseColor("#ffe1e1e1"), 1, dip(18), dip(18))
        itemDecoration.setDrawLastItem(false)
        viewPop!!.find<EasyRecyclerView>(R.id.mydownrec).apply {
            addItemDecoration(itemDecoration)
            setLayoutManager(LinearLayoutManager(mContext))
            adapter = q
        }
        this@mydown?.onClick {
            startPropertyAnim(0f, 180f)
            pw?.showAsDropDown(this, 0, 0)
        }
        this@mydown.addView(viewmain)
    }

    var data: List<String>? = null
    fun setData(data: List<String>): mydown {
        if (data != null) {
            this.data = data
            q?.addAll(data)
            tmain.text = data[0]
        }
        return this
    }

    fun setmainback(back: GradientDrawable) {
        this@mydown.background = back
        invalidate()
    }

    fun setlistback(back: GradientDrawable) {
        viewPop!!.find<EasyRecyclerView>(R.id.mydownrec).apply {
            background = back
        }
        invalidate()
    }

    private fun mydownclick(position: Int, callBack: Func1?) {
        if (callBack != null) {
            try {
                callBack.itemclick(position)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    interface Func1 {
        fun itemclick(result: Int)
    }

    fun setitemonclick(callBack: Func1): mydown {
        q?.setOnItemClickListener { position ->
            tmain.text = data!![position]
            pw?.dismiss()
            mydownclick(position, callBack)
        }
        return this
    }

}
