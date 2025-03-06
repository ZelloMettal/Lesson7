package com.example.lesson7;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class},version = 2)
public abstract class DBContext extends RoomDatabase {
    public abstract BookDao BookDao();
    private static DBContext DBcontext;
    public static synchronized DBContext getInstance(Context context){
        if (DBcontext == null)
        {
            DBcontext = Room.databaseBuilder(context.getApplicationContext(),
                            DBContext.class,"muDb")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return DBcontext;
    }
}
