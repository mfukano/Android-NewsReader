package com.fukano.mat.wv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickN1 (View v) {
        Intent intent = new Intent(context, ReaderActivity.class);
        String s = "http://www.sfgate.com";
        intent.putExtra("site", s);
        startActivity(intent);
    }

    public void onClickN2(View v) {
        Intent intent = new Intent(context, ReaderActivity.class);
        String s = "http://www.mercurynews.com";
        intent.putExtra("site", s);
        startActivity(intent);
    }

    public void onClickN3 (View v) {
        Intent intent = new Intent(context, ReaderActivity.class);
        String s = "http://www.santacruzsentinel.com";
        intent.putExtra("site", s);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
