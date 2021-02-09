package com.manohar.notesapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        findViewById<Button>(R.id.addnote).setOnClickListener(View.OnClickListener { addnote() })

    }

    fun addnote()
    {
        var dbManager = DbManager(applicationContext)
        var values = ContentValues()
        values.put("title", findViewById<EditText>(R.id.notetitle).text.toString())
        values.put("content", findViewById<EditText>(R.id.notecontent).text.toString())
        val id = dbManager.insert(values)
        if (id>0)
        {
            Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show()
            finish()
        }
        else
        {
            Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show()
        }


    }

}