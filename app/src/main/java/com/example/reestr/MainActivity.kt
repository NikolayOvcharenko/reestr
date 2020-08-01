package com.example.reestr

import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reestr.adapter.ListReestrAdapter
import com.example.reestr.DBHelper.DBHelper
import com.example.reestr.data.ReestrDB
import kotlinx.android.synthetic.main.activity_main.*

var whers: String = ""

class MainActivity : AppCompatActivity() {
    val mm = "(01|02|03|04|05|06|07|08|09|10|11|12)"
    // val dd = "(01|..|31)"
    private var news = 0
    private lateinit var db: DBHelper
    private var lstReestr: List<ReestrDB> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        db = DBHelper(this)

        refreshData()

        button_edit.setOnClickListener {
            news = 1
            edit.visibility = VISIBLE
            button(true)
        }

        button_add.setOnClickListener {
            news = 0
            val edits: LinearLayout = findViewById(R.id.edit)
            // but_edit = findViewById(R.id.edit)
            // var edits: LinearLayout = findViewById(R.id.edit)
            // edits.visibility(View.VISIBLE)
            edits.visibility = VISIBLE
            button(true)
        }

        button_delete.setOnClickListener {
            if (edit_id.text.toString() != "") {
                val reestr = ReestrDB(Integer.parseInt(edit_id.text.toString()))
                db.deleteSMP(reestr)
                refreshData()
            }

        }

        button_ok.setOnClickListener {
            if (news == 2) find()
            else {
                if (edit_id.text.toString() == "" ||
                    edit_name.text.toString() == "" ||
                    edit_kontrol.text.toString() == "" ||
                    edit_length.text.toString() == "" ||
                    edit_end.text.toString() == "" ||
                    edit_start.text.toString() == "" ||
                    !edit_start.text.toString().matches("""[0-3]\d-$mm-20[2-9]\d""".toRegex()) ||
                    !edit_end.text.toString().matches("""[0-3]\d-$mm-20[2-9]\d""".toRegex())
                ) {
                    Toast.makeText(application, "Error Input", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else {
                    val reestr = ReestrDB(
                        Integer.parseInt(edit_id.text.toString()),
                        edit_name.text.toString(),
                        edit_kontrol.text.toString(),
                        edit_start.text.toString(),
                        edit_end.text.toString(),
                        edit_length.text.toString()
                    )
                    if (news == 0) db.addSMP(reestr) else db.updateSMP(reestr)
                    refreshData()
                }

                edit.visibility = GONE
                button(false)
            }
        }

        button_cancel.setOnClickListener {
            edit.visibility = GONE
            button(false)
            refreshData()
        }
        button_find.setOnClickListener {
            button(true)
            val edits: LinearLayout = findViewById(R.id.edit)
            edits.visibility = VISIBLE
            news = 2
        }

        button_clear.setOnClickListener {
            edit_id.setText("")
            edit_name.setText("")
            edit_kontrol.setText("")
            edit_start.setText("")
            edit_end.setText("")
            edit_length.setText("")
        }
    }

    private fun button(index: Boolean) {
        val add: Button = findViewById(R.id.button_add)
        add.visibility = if (index) GONE else VISIBLE
        val find: Button = findViewById(R.id.button_find)
        find.visibility = if (index) GONE else VISIBLE
        val del: Button = findViewById(R.id.button_delete)
        del.visibility = if (index) GONE else VISIBLE
        val edit: Button = findViewById(R.id.button_edit)
        edit.visibility = if (index) GONE else VISIBLE
        val ok: Button = findViewById(R.id.button_ok)
        ok.visibility = if (index) VISIBLE else GONE
        val clear: Button = findViewById(R.id.button_clear)
        clear.visibility = if (index) VISIBLE else GONE
        val cancel: Button = findViewById(R.id.button_cancel)
        cancel.visibility = if (index) VISIBLE else GONE
    }

    private fun find() {
        whers = " where"
        when {
            (edit_id.text.toString() != "") -> whers += " _id = ${edit_id.text}"
            (edit_name.text.toString() != "") -> whers += " name_smp = ${edit_name.text}"
            (edit_kontrol.text.toString() != "") -> whers += " kontrol = ${edit_kontrol.text}"
            (edit_start.text.toString() != "") -> whers += " data_start = ${edit_start.text}"
            (edit_end.text.toString() != "") -> whers += " data_end = ${edit_end.text}"
            (edit_length.text.toString() != "") -> whers += " length = ${edit_length.text}"
            else -> whers = ""
        }

        try {

            refreshData()

        } catch (e: SQLiteException) {
            whers = " where _id = 0"
            Toast.makeText(application, "Error Find", Toast.LENGTH_SHORT).show()
            refreshData()
        }
    }

    fun refreshData() {

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
        adapter.notifyDataSetChanged()
        // row_all.setBackgroundColor(Color.WHITE)
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
