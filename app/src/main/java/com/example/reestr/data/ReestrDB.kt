package com.example.reestr.data

import android.provider.BaseColumns




public class ReestrDB (
    var id: Int = 1,
    var name: String? = null,
    var kontrol: String? = null,
    var start: String? = null,
    var end: String? = null,
    var length: String? = null )
/*
    private fun ReestrDB() {}

    object GuestEntry : BaseColumns {
        const val TABLE_NAME = "reestr"
        const val _ID = BaseColumns._ID
        const val COLUMN_NAME = "name"
        const val COLUMN_KONTROL = "kontrol"
        const val COLUMN_DATA_START = "data_start"
        const val COLUMN_DATA_END = "data_end"
        const val COLUMN_LENGTH = "length"
*/

        // const val GENDER_FEMALE = 0
        // const val GENDER_MALE = 1
        // const val GENDER_UNKNOWN = 2
//    }
//}