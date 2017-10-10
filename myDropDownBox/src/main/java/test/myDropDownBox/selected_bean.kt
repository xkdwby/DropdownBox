package test.myDropDownBox

import java.io.Serializable

/**
 * Created by wby on 2017/9/18.
 */
data class selected_bean(var selected: Boolean = false,
                         var mytext: String? = null) : Serializable