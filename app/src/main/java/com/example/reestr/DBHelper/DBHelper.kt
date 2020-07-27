package com.example.reestr.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.reestr.data.ReestrDB
import java.io.*
import java.util.*


class DBHelper (context: Context) : SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VER) {
    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "reestr.db"

        // Table
        private const val TABLE_NAME = "reestr"
        // const val TABLE_NAME = "reestr"
        private val COLUMN_ID = "_id"
        private val COLUMN_NAME = "name_smp"
        private val COLUMN_KONTROL = "kontrol"
        private val COLUMN_DATA_START = "data_start"
        private val COLUMN_DATA_END = "data_end"
        private val COLUMN_LENGTH = "length"

        var DB_PATH: String? = null
        // private val DB_NAME = "extenalDB"
        private var myDataBase: SQLiteDatabase? = null
        private var myContext: Context? = null
    }


    //



    fun DBHelper(context: Context) {
        // super(context, DB_NAME, null, 10)
        myContext = context
        DB_PATH = "/data/" + context.packageName + "/" + "data/"
        Log.e("Path 1", DB_PATH)
    }


    @Throws(IOException::class)
    fun createDataBase() {
        val dbExist = checkDataBase()
        if (dbExist) {
        } else {
            this.readableDatabase
            try {
                copyDataBase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    private fun checkDataBase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            val myPath = DB_PATH + DATABASE_NAME
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
        }
        checkDB?.close()
        return if (checkDB != null) true else false
    }

    @Throws(IOException::class)
    private fun copyDataBase() {
        val myInput: InputStream = myContext!!.assets.open(DATABASE_NAME)
        val outFileName = DB_PATH + DATABASE_NAME
        val myOutput: OutputStream = FileOutputStream(outFileName)
        val buffer = ByteArray(10)
        var length = 0
        while (myInput.read(buffer).also({ length = it }) > 0) {
            myOutput.write(buffer, 0, length)
        }
        myOutput.flush()
        myOutput.close()
        myInput.close()
    }

    @Throws(SQLException::class)
    fun openDataBase() {
        val myPath = DB_PATH + DATABASE_NAME
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    @Synchronized
    override fun close() {
        if (myDataBase != null) myDataBase!!.close()
        super.close()
    }

    //


    override fun onCreate(db: SQLiteDatabase?) {
        openDataBase()

        val CREATE_TABLE_QUERY: String = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_KONTROL INTEGER," +
                "$COLUMN_DATA_START INTEGER," +
                "$COLUMN_DATA_END INTEGER," +
                "$COLUMN_LENGTH INTEGER);"
        db!!.execSQL(CREATE_TABLE_QUERY);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    val allReestr:List<ReestrDB>
        get() {
            val lstReestr = ArrayList<ReestrDB>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db: SQLiteDatabase = this.writableDatabase
            val cursor: Cursor = db.rawQuery(selectQuery,null)
            if (cursor.moveToFirst()) {
                do {
                    val reestr = ReestrDB()
                    reestr.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                    reestr.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    reestr.kontrol = cursor.getString(cursor.getColumnIndex(COLUMN_KONTROL))
                    reestr.start = cursor.getString(cursor.getColumnIndex(COLUMN_DATA_START))
                    reestr.end = cursor.getString(cursor.getColumnIndex(COLUMN_DATA_END))
                    reestr.length = cursor.getString(cursor.getColumnIndex(COLUMN_LENGTH))

                    lstReestr.add(reestr)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstReestr
        }

    fun addSMP (reestrDB: ReestrDB) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID.toString(),        reestrDB.id)
        values.put(COLUMN_NAME,                 reestrDB.name)
        values.put(COLUMN_KONTROL.toString(),   reestrDB.kontrol)
        values.put(COLUMN_DATA_START.toString(),reestrDB.start)
        values.put(COLUMN_DATA_END.toString(),  reestrDB.end)
        values.put(COLUMN_LENGTH.toString(),    reestrDB.length)

        db.insert(TABLE_NAME, null, values)
        db.close()

    }

    fun updateSMP (reestrDB: ReestrDB): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID.toString(),        reestrDB.id)
        values.put(COLUMN_NAME,                 reestrDB.name)
        values.put(COLUMN_KONTROL.toString(),   reestrDB.kontrol)
        values.put(COLUMN_DATA_START.toString(),reestrDB.start)
        values.put(COLUMN_DATA_END.toString(),  reestrDB.end)
        values.put(COLUMN_LENGTH.toString(),    reestrDB.length)

        return db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(reestrDB.id.toString()))

    }

    fun deleteSMP (reestrDB: ReestrDB) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(reestrDB.id.toString()))
        db.close()

    }
}