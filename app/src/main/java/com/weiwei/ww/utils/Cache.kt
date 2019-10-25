package com.weiwei.ww.utils

import com.tencent.mmkv.MMKV
import java.io.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @Author weiwei
 * create by 2019-10-25
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
class Cache<T>(val name:String,private val default:T,private val cacheId:String = "weiwei_cache_id") : ReadWriteProperty<Any?, T>{

    companion object {
        const val TEST = "test"
        const val IS_LOGIN = "is_login"
        const val BANNERS = "banners"
    }

    private val mmkv by lazy {
        MMKV.mmkvWithID(cacheId,MMKV.MULTI_PROCESS_MODE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>) = getValue(name,default)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putValue(name, value)
    }


    fun <T> getValue(name: String, default: T):T = with(mmkv){
        val res: Any = when (default) {
            is Long -> decodeLong(name, default)
            is String -> decodeString(name, default)
            is Int -> decodeInt(name, default)
            is Boolean -> decodeBool(name, default)
            is Float -> decodeFloat(name, default)
            is Double -> decodeDouble(name,default)
            else -> deSerialization(decodeString(name, serialize(default)))
        }
        return res as T
    }

    private fun <T> putValue(name: String, value: T) = with(mmkv){
        when (value) {
            is Long -> encode(name, value)
            is String -> encode(name, value)
            is Int -> encode(name, value)
            is Boolean -> encode(name, value)
            is Float -> encode(name, value)
            is Double -> encode(name,value)
            else -> encode(name, serialize(value))
        }
    }


    /**
     * 序列化对象
     * @param person
     * *
     * @return
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun <A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
            byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 反序列化对象
     * @param str
     * *
     * @return
     * *
     * @throws IOException
     * *
     * @throws ClassNotFoundException
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun <A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
            redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(
            byteArrayInputStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }

    /**
     * 根据key删除存储数据
     */
    fun removeCache(key: String) {
        mmkv.removeValueForKey(key)
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    fun contains(key: String): Boolean {
        return mmkv.containsKey(key)
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    fun getAll(): Map<String, *> {
        return mmkv.all
    }

    /**
     * 清除所有缓存
     */
    fun clearCache(){
        mmkv.clearAll()
    }

}