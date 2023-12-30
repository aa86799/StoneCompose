package com.stone.stonecompose.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment


// context 扩展，实际上还是 在 Activity 中 回调
inline fun <reified T : Activity> Context.openActivity(block: Intent.() -> Unit = {}) {
//    startActivity(Intent(this, T::class.java).apply {block(this)})
    startActivity(Intent(this, T::class.java).apply(block))
}

inline fun <reified T : Activity> Activity.openActivityForResult(requestCode: Int, block: Intent.() -> Unit = {}) {
    this.startActivityForResult(Intent(this, T::class.java).apply(block), requestCode)
}

inline fun <reified T : Activity> Fragment.openActivityForResult(requestCode: Int, block: Intent.() -> Unit = {}) {
    this.startActivityForResult(Intent(requireActivity(), T::class.java).apply(block), requestCode)
}