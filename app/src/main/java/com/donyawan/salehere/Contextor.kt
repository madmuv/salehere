package com.donyawan.salehere

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object Contextor {

    private var mContext : Context? = null

    fun setContext(context: Context) {
        mContext = context
    }

    fun getContext(): Context? {
        return mContext
    }

    fun clear() {
        mContext = null
    }
}