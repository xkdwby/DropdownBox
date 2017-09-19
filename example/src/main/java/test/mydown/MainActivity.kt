package test.mydown

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import test.mydownm.dip2px
import test.mydownm.getShapeDrawable
import test.mydownm.mydown

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //TODO：文字大小设置，被选中文字颜色

        //简单传一个字符串数组，返回其点击位置
        find<test.mydownm.mydown>(R.id.t2).apply {
            setmainback(getShapeDrawable(Color.parseColor("#ffffff"), dip(4).toFloat(), dip(1), Color.parseColor("#ff0000"), null, null))//设置主框背景
            setlistback(getShapeDrawable(Color.parseColor("#ffffff"), 0f, 0, null, null, null))//设置下拉框背景
            setSelectedTextcolorandFontsize(Color.parseColor("#333531"), dip2px(this@MainActivity, 3.5f).toFloat())
            setitemonclick(listOf("今日", "昨日", "本周", "本月"), null, null, object : mydown.Func1<Any> {
                override fun itemclick(itemPostion: Int, bean: Any?) {
                    Toast.makeText(this@MainActivity, itemPostion.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }

        //第一个参数传要显示的字符串数组，第二个是对应的bean
        var q = testbean()
        q.number = 666
        var q2 = testbean()
        q2.number = 888
        find<test.mydownm.mydown>(R.id.t1).apply {
            setmainback(getShapeDrawable(Color.parseColor("#ffffff"), dip(4).toFloat(), dip(1), Color.parseColor("#8bc44a"), null, null))//设置主框背景
            setlistback(getShapeDrawable(Color.parseColor("#ffffff"), dip(4).toFloat(), dip(1), Color.parseColor("#8bc44a"), null, null))//设置下拉框背景
            setitemonclick(listOf("今日", "昨日", "本周", "本月"), listOf(testbean(), q, testbean(), q2), null, object : mydown.Func1<testbean> {
                override fun itemclick(itemPostion: Int, bean: testbean?) {
                    Toast.makeText(this@MainActivity, itemPostion.toString() + "____" + bean?.number, Toast.LENGTH_SHORT).show()
                }
            })
        }

        //第三个参数传一个map
        var q3 = linkedMapOf("a" to testbean(), "b" to q, "c" to testbean(), "d" to q2)
        find<test.mydownm.mydown>(R.id.t3).apply {
            setmainback(getShapeDrawable(Color.parseColor("#ffffff"), dip(4).toFloat(), dip(1), Color.parseColor("#8bc44a"), null, null))//设置主框背景
            setlistback(getShapeDrawable(Color.parseColor("#ffffff"), dip(4).toFloat(), dip(1), Color.parseColor("#8bc44a"), null, null))//设置下拉框背景
            setitemonclick(null, null, q3, object : mydown.Func1<testbean> {
                override fun itemclick(itemPostion: Int, bean: testbean?) {
                    Toast.makeText(this@MainActivity, itemPostion.toString() + "____" + bean?.number, Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}
