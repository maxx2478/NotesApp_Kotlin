package com.manohar.notesapp

import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.ref.WeakReference
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    var notelist = ArrayList<Note>()
    var ListView:ListView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ////notelist.add(Note(1, "Hello", "Kotlin programming is fun"))
        //notelist.add(Note(1, "Hello", "Kotlin programming is very fun. Android developers should learn it to improve skills."))

        ListView = findViewById(R.id.listview)

        var fab = findViewById<FloatingActionButton>(R.id.floating)
        fab.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)

        })

        //load from database

        LoadQuery("%")

    }

    fun LoadQuery(title:String)
    {
        var dbManager = DbManager(this)
        val selectionArguments = arrayOf(title)
        var projection = arrayOf("id", "title", "content")
        val cursor = dbManager.Query(projection, "title like ?", selectionArguments,
        "title")
        notelist.clear()
        if (cursor.moveToFirst())
        {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("title"))
                val content = cursor.getString(cursor.getColumnIndex("content"))
                notelist.add(Note(id, name, content))
            }while (cursor.moveToNext())
        }

        var Adapter = Adapter(notelist, this)
        ListView!!.adapter = Adapter

    }

    class Adapter:BaseAdapter {
        var noteslist = ArrayList<Note>()
        var weakReference:WeakReference<MainActivity>?=null
        constructor(list:ArrayList<Note>, activity: MainActivity):super()
        {
            this.noteslist = list
            this.weakReference = WeakReference(activity)
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var note = noteslist.get(position)
            var Inflator = weakReference!!.get()!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var view = Inflator.inflate(R.layout.notes_row, null)
            var notetitle:TextView = view.findViewById(R.id.notetitle)
            var notecontent:TextView = view.findViewById(R.id.notecontent)
            var delete:ImageButton = view.findViewById(R.id.remove)
            var edit:ImageButton = view.findViewById(R.id.edit)

            notetitle.setText(note.notetitle)
            notecontent.setText(note.notecontent)
            delete.setOnClickListener(View.OnClickListener {
                var dbManager = DbManager(weakReference!!.get()!!)
                val selectionArguments = arrayOf(note.noteid.toString())
                dbManager.delete("id=?", selectionArguments)
                weakReference!!.get()!!.LoadQuery("%")
            })
            edit.setOnClickListener(View.OnClickListener {

                var intent = Intent(weakReference!!.get(), EditNoteActivity::class.java)
                intent.putExtra("id", note.noteid)
                intent.putExtra("title", note.notetitle)
                intent.putExtra("content", note.notecontent)
                weakReference!!.get()!!.startActivity(intent)



            })
            return view

        }

        override fun getItem(position: Int): Any {
            return noteslist.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return noteslist.size
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView:SearchView = menu!!.findItem(R.id.search).actionView as SearchView
        val searchmanager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchmanager.getSearchableInfo(componentName))


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               LoadQuery("%" + query + "%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
           R.id.search -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        LoadQuery("%")

    }

}