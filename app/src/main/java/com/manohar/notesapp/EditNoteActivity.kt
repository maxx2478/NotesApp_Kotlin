package com.manohar.notesapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class EditNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)


        var bundle = intent.extras!!
        var id = bundle.getInt("id")
        var title = bundle.getString("title")
        var content = bundle.getString("content")

        var editText1 = findViewById<EditText>(R.id.notetitle)
        editText1.setText(title)
        var editText2 =  findViewById<EditText>(R.id.notecontent)
        editText2.setText(content)



        findViewById<Button>(R.id.addnote).setOnClickListener(View.OnClickListener {
            var values = ContentValues()
            values.put("title", editText1.text.toString())
            values.put("content", editText2.text.toString())
            var dbManager = DbManager(this)
            val selectionArguments = arrayOf(id.toString())
            val id =  dbManager.update(values, "id=?", selectionArguments)
            if (id>0)
            {
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                finish()
            }
            else
            {
                Toast.makeText(this, "Error stop", Toast.LENGTH_SHORT).show()
            }

        })


    }
}