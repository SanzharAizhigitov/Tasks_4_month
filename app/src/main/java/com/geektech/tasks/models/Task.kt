package com.geektech.tasks.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Task(
 @PrimaryKey(autoGenerate = true)
 var id:Int? = null,
 var title:String?=null,
 var description:String?=null,
):Serializable
