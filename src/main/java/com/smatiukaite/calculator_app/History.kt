package com.smatiukaite.calculator_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

const val NUMBER_FROM_HISTORY = "csc244.switchingactivity.NUMBER_FROM_HISTORY"

class History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        title = "History"

        val list = intent.getStringArrayListExtra(HISTORY_EXTRA)
        val noContent = findViewById<TextView>(R.id.no_content_text)

        noContent.visibility = if(list == null || list.isEmpty()) View.VISIBLE else View.INVISIBLE

        val listView = findViewById<ListView>(R.id.list_view)
        listView.visibility = if(list == null || list.isEmpty()) View.INVISIBLE else View.VISIBLE
        if(list != null && !list.isEmpty()){
            val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, R.layout.list_view_item,
                list.toTypedArray())
            listView.adapter = adapter

            listView.onItemClickListener = AdapterView.OnItemClickListener{
                adapterView, view, i, l -> val item = list[i]
                val intent = Intent (this, MainActivity::class.java).apply{
                    putExtra(NUMBER_FROM_HISTORY, item.toString().toString())
                }
                startActivity(intent)
            }
        }
    }

}