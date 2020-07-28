package com.example.reestr

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.reestr.Adapter.ListReestrAdapter
import com.example.reestr.DBHelper.DBHelper
import com.example.reestr.data.ReestrDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_layout.*
import kotlinx.android.synthetic.main.row_layout.view.*

class MainActivity : AppCompatActivity() {
    var news: Boolean = true
    lateinit var but_edit: LinearLayout
    internal lateinit var db: DBHelper
    internal var lstReestr: List<ReestrDB> = ArrayList<ReestrDB>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        db = DBHelper(this)

        refreshData()

        button_edit.setOnClickListener {
            news = false
            edit.visibility = VISIBLE
        }

        button_add.setOnClickListener {
            news = true

            // but_edit = findViewById(R.id.edit)
            var edits: LinearLayout = findViewById(R.id.edit)
            // edits.visibility(View.VISIBLE)
            edits.visibility = VISIBLE
        }

        button_delete.setOnClickListener{
            if (edit_id.text.toString() != "") {
                val reestr = ReestrDB(Integer.parseInt(edit_id.text.toString()))
                db.deleteSMP(reestr)
                refreshData()
            }
        }

        button_ok.setOnClickListener {
            if (edit_id.text.toString() != "") {
                val reestr = ReestrDB(
                    Integer.parseInt(edit_id.text.toString()),
                    edit_name.text.toString(),
                    edit_kontrol.text.toString(),
                    edit_start.text.toString(),
                    edit_end.text.toString(),
                    edit_length.text.toString()
                )
                if (news) db.addSMP(reestr) else db.updateSMP(reestr)
                refreshData()
            }
            edit.visibility = GONE

        }

    }

    private fun refreshData() {
        lstReestr = db.allReestr
        val adapter = ListReestrAdapter(
            this@MainActivity,
            lstReestr,
            edit_id,
            edit_name,
            edit_kontrol,
            edit_start,
            edit_end,
            edit_length
        )
        list_reestr.adapter = adapter
        row_all.setBackgroundColor(Color.WHITE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
