package com.mylrc.mymusic.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SongDatabaseHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "song.db";

  private static final int DATABASE_VERSION = 1;

  private static final String TABLE_NAME = "song_list";

  private static final String CREATE_TABLE_SQL =
      "create table if not exists " + TABLE_NAME +
          " (id integer primary key, name text, path text)";

  private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;

  public SongDatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
    sQLiteDatabase.execSQL(CREATE_TABLE_SQL);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) throws SQLException {
    sQLiteDatabase.execSQL(DROP_TABLE_SQL);
    onCreate(sQLiteDatabase);
  }
}