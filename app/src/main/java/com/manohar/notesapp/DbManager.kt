package com.manohar.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.media.projection.MediaProjection
import android.text.Selection
import android.widget.Toast

class DbManager
{

    var dbname:String="Mynotes"
    var dbtable = "notes"
    val collid = "id"
    val coltitle="title"
    val colDes="content"
    val dbversion =1
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS "+ dbtable+
            " ("+ collid+" INTEGER PRIMARY KEY,"+ coltitle + " TEXT,"+colDes+
            " TEXT);"
    var sqlDB:SQLiteDatabase?=null
    constructor(context: Context)
    {
      val db = DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperNotes:SQLiteOpenHelper
    {
        var context:Context?=null
        constructor(context:Context):super(context, dbname, null, dbversion)
        {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(context, "Database created", Toast.LENGTH_SHORT)!!.show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("drop table if exists "+dbtable)
        }

    }

    fun insert(values: ContentValues):Long
    {
        val id = sqlDB!!.insert(dbtable,"", values)
        return id
    }

    fun delete(selection: String,
               selectionargs: Array<String>):Int
    {
       val countcurrentitem = sqlDB!!.delete(dbtable, selection, selectionargs)
        return countcurrentitem
    }

    fun update(values: ContentValues, selection: String, selectionargs: Array<String>):Int
    {
        val count = sqlDB!!.update(dbtable, values, selection, selectionargs)
        return count
    }

    fun Query(projection: Array<String>, selection: String,
              selectionargs: Array<String>,
              sortOrder:String):Cursor
    {
        val qb = SQLiteQueryBuilder()
        qb.tables = dbtable
        val cursor = qb.query(sqlDB, projection, selection,selectionargs, null,
        null, sortOrder)
        return cursor
    }



}