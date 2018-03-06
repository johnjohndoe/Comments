package com.example.store

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.store.dao.CommentDao
import com.example.store.models.Comment

@Database(
        entities = [
            (Comment::class)
        ],
        version = 1,
        exportSchema = false
)
abstract class TypicodeDatabase : RoomDatabase() {

    abstract fun commentDao(): CommentDao

    companion object {

        private const val NAME = "typicode"

        @Volatile
        private var INSTANCE: TypicodeDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): TypicodeDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also {
                        INSTANCE = it
                    }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        TypicodeDatabase::class.java,
                        NAME)
                        .addCallback(CALLBACK)
                        .build()

        private val CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val tableName = Comment.TABLE_NAME
                val triggerName = "trigger_${tableName}_insert"
                db.execSQL("""
                    CREATE TRIGGER '$triggerName'
                    AFTER INSERT ON '$tableName'
                    WHEN ( SELECT count(*) FROM  '$tableName' ) > 500
                      BEGIN
                        DELETE FROM '$tableName' WHERE 'clientId' NOT IN
                        (
                          SELECT clientId FROM '$tableName' ORDER BY 'clientId' DESC LIMIT 500
                        );
                      END
                    """)
            }
        }
    }
}
