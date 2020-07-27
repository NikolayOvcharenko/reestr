package com.example.reestr

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.reestr.Adapter.ListReestrAdapter
import com.example.reestr.DBHelper.DBHelper
import com.example.reestr.data.ReestrDB

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*

class MainActivity : AppCompatActivity() {

    internal lateinit var db: DBHelper
    internal var lstReestr: List<ReestrDB> = ArrayList<ReestrDB>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        db = DBHelper(this)

        refreshData()

        button_add.setOnClickListener {
            val reestr = ReestrDB (
                Integer.parseInt(_id.text.toString()),
                name.text.toString(),
                kontrol.text.toString(),
                data_start.text.toString(),
                data_end.text.toString(),
                length.text.toString()
            )
            db.addSMP(reestr)
            refreshData()
        }

    }

    private fun refreshData() {
        lstReestr = db.allReestr
        val adapter = ListReestrAdapter(this@MainActivity, lstReestr, _id, name, kontrol, data_start, data_end, length)
        list_tabl.adapter = adapter
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
