package com.example.a1031002.lab7;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.MenuItemHoverListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private String[] dinoNames, dinoInfos;
    private int[] dinoPics, dinoIcon;
    private DinoDAO dinoDAO;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dinoDAO = DinoDAO.getDao(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = dinoDAO.getDinos();
        int ctr = 0;

        lv = (ListView) findViewById(R.id.dinoList);

        int size = cursor.getCount();

        dinoNames = new String[size];
        dinoInfos = new String[size];
        dinoPics = new int[size];
        dinoIcon = new int[size];

        while (cursor.moveToNext()) {
            dinoNames[ctr] = cursor.getString(cursor.getColumnIndex(dinoDAO.COL_NAME));
            dinoInfos[ctr] = cursor.getString(cursor.getColumnIndex(dinoDAO.COL_INFO));
            dinoPics[ctr] = cursor.getInt(cursor.getColumnIndex(dinoDAO.COL_IMG_ID));
            dinoIcon[ctr] = cursor.getInt(cursor.getColumnIndex(dinoDAO.COL_ICON_ID));
            ctr++;
        }

        cursor.close();

        lv.setAdapter(new DinoAdapter(this, dinoNames, dinoPics, dinoInfos, dinoIcon));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.dinoWWW:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sciencekids.co.nz/pictures/dinosaurs.html")));
                return true;
            case R.id.dinoAbout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.appAbout).setTitle(R.string.me);

                builder.create().show();
                return true;
            default:
                return false;
        }
    }
}
