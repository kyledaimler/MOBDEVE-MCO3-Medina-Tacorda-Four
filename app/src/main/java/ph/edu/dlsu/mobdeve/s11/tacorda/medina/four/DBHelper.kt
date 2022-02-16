package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION){
    companion object{
        private val DATABASE_NAME = "playerpofile.db"
        private val DATABASE_VERSION = 3
        val TABLE_NAME = "PLAYER"
        val KEY_WIN = "win"
        val KEY_NAME = "name"
        val KEY_GENDER = "gender"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + "WIN" + " INTEGER PRIMARY KEY," +
                "NAME" + " TEXT," +
                "GENDER" + " TEXT)")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME)
        onCreate(db)
    }

    fun addPlayer(player: Player):Boolean{
        var db = this.writableDatabase
        var values = ContentValues()
        values.put(KEY_WIN,player.wins)
        values.put(KEY_NAME,player.name)
        values.put(KEY_GENDER,player.gender)
        val success = db.insert(TABLE_NAME,null,values)
        db.close()
        if (success.toInt() == -1){
            Toast.makeText(context,"Failed to create", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            Toast.makeText(context,"Successfully created", Toast.LENGTH_SHORT).show()
            return true
        }
    }

    fun deletePlayer(player: Player){
        var db = this.writableDatabase
        val selectionArgs = arrayOf(player.wins.toString())
        db.delete(TABLE_NAME,KEY_WIN +" = ? ",selectionArgs)
    }

    fun getAllPlayer():ArrayList<Player>{
        var db = this.writableDatabase
        var playerList : ArrayList<Player> = ArrayList()
        val selectAll = "SELECT * FROM "+ TABLE_NAME
        val cursor = db.rawQuery(selectAll,null)
        if (cursor.moveToFirst()){
            do{
                val player = Player()
                player.wins = cursor.getInt(0)
                player.name = cursor.getString(1)
                player.gender = cursor.getString(2)
                playerList.add(player)
            } while (cursor.moveToNext())
        }
        return playerList
    }

}