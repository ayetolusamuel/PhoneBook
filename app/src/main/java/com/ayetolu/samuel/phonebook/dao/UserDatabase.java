package com.ayetolu.samuel.phonebook.dao;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.ayetolu.samuel.phonebook.model.User;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase INSTANCE;
    private static final String DATABASE_NAME = "user_database";
    private static Object lock = new Object();
    public abstract UserDao mDao();

    public static UserDatabase getInstance(Context context){
        if (INSTANCE == null) {
            synchronized (lock) {
                if (INSTANCE == null) {
                    //create database Here......
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            //.addMigrations(MIGRATION_1_2)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }
    };
}










//
//
//
//
//
//public abstract class ProductDatabase extends RoomDatabase {
//    private static ProductDatabase INSTANCE;
//    private static final String DATABASE_NAME = "akwe_database";
//    private static Object lock = new Object();
//
//    public abstract ProductDao productDao();
//
//
//
//
//
//    public static ProductDatabase getInstance(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (lock) {
//                if (INSTANCE == null) {
//                    //create database Here......
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            ProductDatabase.class, DATABASE_NAME)
//                            .fallbackToDestructiveMigration()
//                            // .addCallback(MIGRATION_1_2)
//                            //.addMigrations(MIGRATION_1_2)
//                            .build();
//
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//
//
//}
