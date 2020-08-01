package com.example.reestr.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import com.example.reestr.R
import com.example.reestr.R.*
import com.example.reestr.data.ReestrDB
import kotlinx.android.synthetic.main.row_layout.view.*


class ListReestrAdapter (activity: Activity,
                         private var lstReestr: List<ReestrDB>,
                         private var edit_id: EditText,
                         private var edit_name: EditText,
                         private var edit_kontrol: EditText,
                         private var edit_start: EditText,
                         private var edit_end: EditText,
                         private var edit_length: EditText): BaseAdapter() {

    private var inflater:LayoutInflater =
        activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var rowLast: View =  inflater.inflate(layout.row_layout, null)


    @SuppressLint("ViewHolder", "InflateParams", "ResourceType")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View = inflater.inflate(layout.row_layout, null)
        rowView.txt_id.text = lstReestr[position].id.toString()
        rowView.txt_name.text = lstReestr[position].name.toString()
        rowView.txt_kontrol.text = lstReestr[position].kontrol.toString()
        rowView.txt_data_start.text = lstReestr[position].start.toString()
        rowView.txt_data_end.text = lstReestr[position].end.toString()
        rowView.txt_length.text = lstReestr[position].length.toString()
        // rowView.setBackgroundColor(Color.WHITE)

        // cобытие
        rowView.setOnClickListener {

            //rowLast.setBackgroundResource(drawable.edit_text_style)
            //val rowAll: View = this.getView(R.layout.activity_main)
            // rowView.background
            //rowView.setBackgroundColor(Color.MAGENTA)

            edit_id.setText(rowView.txt_id.text.toString())
            edit_name.setText(rowView.txt_name.text.toString())
            edit_kontrol.setText(rowView.txt_kontrol.text.toString())
            edit_start.setText(rowView.txt_data_start.text.toString())
            edit_end.setText(rowView.txt_data_end.text.toString())
            edit_length.setText(rowView.txt_length.text.toString())

            rowLast = rowView

        }
        return rowView
    }

    override fun getItem(position: Int): Any = lstReestr[position]

    override fun getItemId(position: Int): Long = lstReestr[position].id.toLong()

    override fun getCount(): Int = lstReestr.size

}