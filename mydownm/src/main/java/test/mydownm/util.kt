package test.mydownm

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

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
