package test.mydown

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import test.mydownm.getShapeDrawable
import test.mydownm.mydown

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        find<test.mydownm.mydown>(R.id.t2).apply {
            setmainback(getShapeDrawable(Color.parseColor("#ffffff"), dip(4).toFloat(), dip(1), Color.parseColor("#ff0000"), null, null))//设置主框背景
            setlistback(getShapeDrawable(Color.parseColor("#ffffff"), 0f, 0, null, null, null))//设置下拉框背景
            setitemonclick(null, listOf("今日", "昨日", "本周", "本月"), object : mydown.Func1<Any> {
                override fun itemclick(itemPostion: Int, bean: Any?) {
                    Toast.makeText(this@MainActivity, itemPostion.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }

        //TODO：待支持下拉框全宽和跟上框同宽，支持下拉框背景透明和半透明两种。
        var q = testbean()
        q.number = 666
        var q2 = testbean()
        q2.number = 888
        var q3 = linkedMapOf("a" to testbean(), "b" to q, "c" to testbean(), "d" to q2)
        find<test.mydownm.mydown>(R.id.t).apply {
            setmainback(getShapeDrawable(Color.parseColor("#ffffff"), dip(4).toFloat(), dip(1), Color.parseColor("#8bc44a"), null, null))//设置主框背景
            setlistback(getShapeDrawable(Color.parseColor("#ffffff"), dip(4).toFloat(), dip(1), Color.parseColor("#8bc44a"), null, null))//设置下拉框背景
            setitemonclick(q3, null, object : mydown.Func1<testbean> {
                override fun itemclick(itemPostion: Int, bean: testbean?) {
                    Toast.makeText(this@MainActivity, itemPostion.toString() + "____" + bean?.number, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
