package com.example.reestr.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import com.example.reestr.R
import com.example.reestr.data.ReestrDB
import kotlinx.android.synthetic.main.fragment_second.view.*
import kotlinx.android.synthetic.main.row_layout.view.*

class ListReestrAdapter (internal var activity: Activity,
                   internal var lstReestr: List<ReestrDB>,
                   internal var edit_id: EditText,
                   internal var edit_name: EditText,
                   internal var edit_kontrol: EditText,
                   internal var edit_start: EditText,
                   internal var edit_end: EditText,
                   internal var edit_length: EditText): BaseAdapter() {

    internal var inflater:LayoutInflater =
        activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View = inflater.inflate(R.layout.row_layout, null)

        rowView.txt_id.text = lstReestr[position].id.toString()
        rowView.txt_name.text = lstReestr[position].name.toString()
        rowView.txt_kontrol.text = lstReestr[position].kontrol.toString()
        rowView.txt_data_start.text = lstReestr[position].start.toString()
        rowView.txt_data_end.text = lstReestr[position].end.toString()
        rowView.txt_length.text = lstReestr[position].length.toString()

        // cобытие
        rowView.setOnClickListener {
            edit_id.setText(rowView.txt_id.text.toString())
            edit_name.setText(rowView.txt_name.text.toString())
            edit_kontrol.setText(rowView.txt_kontrol.text.toString())
            edit_start.setText(rowView.txt_data_start.text.toString())
            edit_end.setText(rowView.txt_data_end.text.toString())
            edit_length.setText(rowView.txt_length.text.toString())
        }
        return rowView
    }

    override fun getItem(position: Int): Any = lstReestr[position]

    override fun getItemId(position: Int): Long = lstReestr[position].id.toLong()

    override fun getCount(): Int = lstReestr.size

}