package com.example.p070;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// TODO Auto-generated method stub
        Intent intent =null;

        if (item.getItemId() == R.id.m1) {
            intent = new Intent(this, theatre.class);
        } else if (item.getItemId() == R.id.m2) {
            intent = new Intent(this, films.class);
        } else if (item.getItemId() == R.id.m3) {
            intent = new Intent(this, newtheatre.class);
        } else if (item.getItemId() == R.id.m4) {
            intent = new Intent(this, newfilm.class);
        } else if (item.getItemId() == R.id.m5) {
            intent = new Intent(this, updtheatres.class);
        } else if (item.getItemId()==R.id.m6){
            intent = new Intent(this, updfilms.class);
        } else {
            return super.onOptionsItemSelected(item);
        }
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

}