package com.example.a1031002.lab7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private String[] dinoNames, dinoInfos;
    private int[] dinoPics, dinoIcon;
    private DinoDAO dinoDAO;
    private DinoAdapter dinoAdapter;
    private DataLoader dataLoader;
    Cursor cursor;
    int ctr, size;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dinoDAO = DinoDAO.getDao(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.dinoList);

        dataLoader = new DataLoader();
        dataLoader.execute();



        /*
        cursor = dinoDAO.getDinos();
        ctr = 0;



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
        dinoAdapter = new DinoAdapter(this, dinoNames, dinoPics, dinoInfos, dinoIcon);
        lv.setAdapter(dinoAdapter);*/
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

    /**
     * AsyncTask that will call in the background database actions.
     */
    public class DataLoader extends AsyncTask<Void, Integer, DinoAdapter> {
        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        /**
         * This probably "won't show up" as it is so short.
         */
        protected void onPreExecute() {
            this.dialog.setTitle(getString(R.string.loading));
            this.dialog.setMessage(getString(R.string.loadMsg));
            this.dialog.show();
        }

        /**
         * Gets the data from SQLite and load them into arrays
         * to pass to the custom dino adapter. All done in the bkg.
         *
         * @param params
         * @return
         */
        @Override
        protected DinoAdapter doInBackground(Void... params) {
            Cursor asyncCursor = dinoDAO.getDinos();

            size = asyncCursor.getCount();

            dinoNames = new String[size];
            dinoInfos = new String[size];
            dinoPics = new int[size];
            dinoIcon = new int[size];

            while (asyncCursor.moveToNext()) {
                dinoNames[ctr] = asyncCursor.getString(asyncCursor.getColumnIndex(dinoDAO.COL_NAME));
                dinoInfos[ctr] = asyncCursor.getString(asyncCursor.getColumnIndex(dinoDAO.COL_INFO));
                dinoPics[ctr] = asyncCursor.getInt(asyncCursor.getColumnIndex(dinoDAO.COL_IMG_ID));
                dinoIcon[ctr] = asyncCursor.getInt(asyncCursor.getColumnIndex(dinoDAO.COL_ICON_ID));
                ctr++;

                /**
                 * This is only here so I can see what it actually does as my db is too small.
                 */
                try {
                    Thread.sleep(500);
                } catch (Exception e) {

                }

                int percent = ctr * 100 / size;
                publishProgress(percent);

            }

            asyncCursor.close();

            return new DinoAdapter(MainActivity.this, dinoNames, dinoPics, dinoInfos, dinoIcon);
        }

        /**
         * Every 2% of data added to the arrays, update the progress dialog.
         *
         * @param progress
         */
        protected void onProgressUpdate(Integer... progress) {
            this.dialog.setMessage(getString(R.string.loadMsg) + "\t" + progress[0] + "%");
        }

        protected void onPostExecute(DinoAdapter asyncDA) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            dinoAdapter = asyncDA;
            dinoAdapter.notifyDataSetChanged();
            lv.setAdapter(dinoAdapter);
        }
    }
}

