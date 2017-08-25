package test.mydown

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import test.mydownm.getShapeDrawable
import test.mydownm.mydown
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        find<test.mydownm.mydown>(R.id.t).apply {
            setData(listOf("a", "b", "c", "z"))//添加下拉列表数据，默认初始显示第一个
            setmainback(getShapeDrawable(Color.parseColor("#00000000"),dip(4).toFloat(),dip(1),Color.parseColor("#8bc44a"),null,null))//设置主框背景
            setlistback(getShapeDrawable(Color.parseColor("#00000000"),dip(4).toFloat(),dip(1),Color.parseColor("#8bc44a"),null,null))//设置下拉框背景
//            setData(listOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"))//默认最大高度为屏幕的三分之一
            setitemonclick(object : mydown.Func1 {//下拉框条目点击事件处理
                override fun itemclick(result: Int) {
                    Toast.makeText(this@MainActivity, result.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
