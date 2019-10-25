package com.weiwei.ww.base

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import java.lang.RuntimeException

/**
 * @Author weiwei
 * create by 2019-10-25
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
abstract class BaseInitializer :ContentProvider(){

    abstract fun init()

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw RuntimeException("no imp func")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        throw RuntimeException("no imp func")
    }

    override fun onCreate() = true.apply {
        init()
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw RuntimeException("no imp func")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw RuntimeException("no imp func")
    }

    override fun getType(uri: Uri): String? {
        throw RuntimeException("no imp func")
    }

}