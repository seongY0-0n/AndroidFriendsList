package com.cookandroid.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    myDBHelper helper;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<String> listNames;
    ArrayList<String> listPhones;
    //myAdapter adapter;
    //*
    ArrayAdapter<String> adapter;
    //*
    static final int Add_Friends = 1001;
    static final int INFO_Friends = 1002;
    static final int FIX_Friends = 1003;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("친구 목록 관리 앱");
        helper = new myDBHelper(MainActivity.this);
        listView =  (ListView)findViewById(R.id.listView);
        listNames = new ArrayList<>();
        listPhones = new ArrayList<>();
        getName();
        //getPhone();
        Toast.makeText(this, "총 " + listNames.size() + "명의 친구가 등록되어 있음", Toast.LENGTH_SHORT).show();
        //*
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNames);
        listView.setAdapter(adapter);
        //*
//        adapter = new myAdapter(this, listNames, listPhones);
//        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addFriends:
                Intent intent = new Intent(getApplicationContext(),AddFriendsActivity.class);
                intent.putExtra("code", Add_Friends);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final String name = (String) adapter.getItem(info.position);
        switch (item.getItemId()){
            case R.id.info:
                Intent intent = new Intent(getApplicationContext(),AddFriendsActivity.class);
                intent.putExtra("code", INFO_Friends);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case R.id.fix:
                Intent intent1 = new Intent(getApplicationContext(),AddFriendsActivity.class);
                intent1.putExtra("code", FIX_Friends);
                intent1.putExtra("name", name);
                startActivity(intent1);
                break;
            case R.id.del:
                sqLiteDatabase = helper.getWritableDatabase();
                sqLiteDatabase.execSQL("DELETE FROM friends WHERE name = '" + name + "';" );
                sqLiteDatabase.close();
                getName();
                //getPhone();
                //*
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNames);
                listView.setAdapter(adapter);
                //*
//                adapter = new myAdapter(this, listNames, listPhones);
//                listView.setAdapter(adapter);
                Toast.makeText(this, "총 " + listNames.size() + "명의 친구가 등록되어 있음", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void getName(){
        listNames.clear();
        sqLiteDatabase = helper.getReadableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT name FROM friends;",null);
        while (c.moveToNext()){
            listNames.add(c.getString(0));
        }
        
        c.close();
        sqLiteDatabase.close();
    }
//    public void getPhone(){
//        listPhones.clear();
//        sqLiteDatabase = helper.getReadableDatabase();
//
//        Cursor c = sqLiteDatabase.rawQuery("SELECT phone FROM friends;",null);
//        while (c.moveToNext()){
//            listPhones.add(c.getString(0));
//        }
//        c.close();
//        sqLiteDatabase.close();
//    }

}