package com.example.a1031002.lab7;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.MenuItemHoverListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private String[] dinoNames, dinoInfos;
    private int[] dinoPics,dinoIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.dinoList);

        dinoNames = getResources().getStringArray(R.array.dinoNames);
        dinoInfos = getResources().getStringArray(R.array.dinoInfos);
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
        dinoIcon= new int[]{
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

        lv.setAdapter(new DinoAdapter(this,dinoNames,dinoPics,dinoInfos,dinoIcon));
    }

    public boolean onOptionItemSelected(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSekected(MenuItem item){

        switch (item.getItemId()){
            case R.id.dinoWWW:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sciencekids.co.nz/pictures/dinosaurs.html")));
                break;
            case R.id.dinoAbout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.appAbout).setTitle(R.string.me);

                builder.create().show();
        }
    }
}
