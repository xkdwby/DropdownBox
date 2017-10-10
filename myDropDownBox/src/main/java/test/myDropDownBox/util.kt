package test.myDropDownBox

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Created by wby on 2017/8/23.
 */
/**
 * 画边框方法

 * @param color      背景色
 * *
 * @param radius     圆角
 * *
 * @param stockWidth 边框宽度
 * *
 * @param stockColor 边框颜色
 * *
 * @param dashWidth  边框线间隔
 * *
 * @param dashGap    边框线长度
 * *
 * @return
 */
fun getShapeDrawable(color: Int, radius: Float, stockWidth: Int? = null, stockColor: Int? = null, dashWidth: Float? = null, dashGap: Float? = null): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    val stockWidth = stockWidth ?: 0
    val stockColor = stockColor ?: Color.parseColor("#ffffff")
    val dashWidth = dashWidth ?: 0f
    val dashGap = dashGap ?: 1f
    gradientDrawable.cornerRadius = radius
    gradientDrawable.setColor(color)
    gradientDrawable.setStroke(stockWidth, stockColor, dashWidth, dashGap)
    return gradientDrawable
}

/**
 * 获取屏幕高度
 */
fun getScreenWidth(context: Context): Int {
    val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.widthPixels
}

/**
 * 获取屏幕宽度
 */
fun getScreenHeight(context: Context): Int {
    val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.heightPixels
}

/**
 * 获得状态栏的高度
 *
 * @param context
 * @return
 */
fun getStatusHeight(context: Context): Int {

    var statusHeight = -1
    try {
        val clazz = Class.forName("com.android.internal.R\$dimen")
        val `object` = clazz.newInstance()
        val height = Integer.parseInt(clazz.getField("status_bar_height")
                .get(`object`).toString())
        statusHeight = context.resources.getDimensionPixelSize(height)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return statusHeight
}

fun dip2px(context: Context, dpValue: Float): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}