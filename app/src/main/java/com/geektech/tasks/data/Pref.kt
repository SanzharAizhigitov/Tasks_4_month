package com.geektech.tasks.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.EditText

class Pref(private val context: Context){
    private val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
     fun isUserSeen(): Boolean{
        return pref.getBoolean(IS_SEEN_KEY,false)}
     fun saveSeen(){
             pref.edit().putBoolean(IS_SEEN_KEY, true).apply()
         }
    fun putString(key: String, data:String){
        pref.edit().putString(key, data).apply()
    }
    fun getString(key: String): String{
      return pref.getString(key, "").toString()
    }
    companion object{
        const val PREF_NAME = "Task.pref"
        const val IS_SEEN_KEY = "seen_key"
        const val IMAGE_KEY = "image_key"
    }
}