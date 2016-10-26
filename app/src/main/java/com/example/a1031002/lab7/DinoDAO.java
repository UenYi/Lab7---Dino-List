package com.example.a1031002.lab7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 1031002 on 10/26/2016.
 */
public class DinoDAO extends SQLiteOpenHelper {

    private static final String TAG = "DinoDAO";

    private static final String TABLE_DINO = "dino";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "Name";
    public static final String COL_INFO = "Info";
    public static final String COL_IMG_ID = "Image id";
    public static final String COL_ICON_ID = "Icon id";

    private static final String DATABASE_NAME = "dino.db";
    private static final int DATABASE_VERSION = 1;
    private static DinoDAO dao = null;

    private String[] dinoNames, dinoInfos;
    private int[] dinoPics, dinoIcons;
    private Context context;

    private SQLiteDatabase readDB;
    private SQLiteDatabase writeDB;

    private static final String DB_CREATE = "create table " +
            TABLE_DINO + "(" + COL_ID + " integer primary key autoincrement, "
            + COL_NAME + " text, " + COL_INFO + " text, " + COL_IMG_ID + " integer, " + COL_ICON_ID + " integer);";

    public DinoDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.readDB = getReadableDatabase();
        this.writeDB = getWritableDatabase();
    }

    public static DinoDAO getDao(Context context) {
        Log.i(TAG, "getDao");

        if (dao == null) {
            dao = new DinoDAO(context.getApplicationContext());
            Log.i(TAG, "getDao, dao == null");
        }

        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "DinoDAO - onCreate");
        sqLiteDatabase.execSQL(DB_CREATE);

        dinoNames = this.context.getResources().getStringArray(R.array.dinoNames);
        dinoInfos = this.context.getResources().getStringArray(R.array.dinoInfos);

        dinoPics = new int[]{
                R.drawable.alamosaurus,
                R.drawable.albertosaurus,
                R.drawable.allosaurus,
                R.drawable.anchiceratops,
                R.drawable.ankylosaurus,
                R.drawable.apatosaurussketch,
                R.drawable.aucasaurus,
                R.drawable.avaceratops,
                R.drawable.baryonyx,
                R.drawable.brachiosaurusdrawing
        };

        dinoIcons = new int[]{
                R.drawable.alamosaurus_icon,
                R.drawable.albertosaurus_icon,
                R.drawable.allosaurus_icon,
                R.drawable.anchiceratops_icon,
                R.drawable.ankylosaurus_icon,
                R.drawable.apatosaurussketch_icon,
                R.drawable.aucasaurus_icon,
                R.drawable.avaceratops_icon,
                R.drawable.baryonyx_icon,
                R.drawable.brachiosaurusdrawing_icon
        };

        for (int i = 0; i < dinoNames.length; i++) {
            insertNewDino(dinoNames[i], dinoInfos[i], dinoPics[i], dinoIcons[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG, DinoDAO.class.getName() + "Upgrading DB from version " +
                oldVersion + " to " + newVersion + ", which will destroy all old data");

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DINO);
        Log.i(TAG, "DinoDAO - onUpgrade");
        onCreate(sqLiteDatabase);
    }

    public long insertNewDino(String name, String info, int imgID, int iconID) {
        Log.i(TAG, "insertNewDino");

        ContentValues cv = new ContentValues();

        cv.put(COL_NAME, name);
        cv.put(COL_INFO, info);
        cv.put(COL_IMG_ID, imgID);
        cv.put(COL_ICON_ID, iconID);

        long result = this.writeDB.insert(TABLE_DINO, null, cv);

        return result;
    }

    public Cursor getDinos() {
        return this.readDB.query(TABLE_DINO, null, null, null, null, null, null);
    }


}
