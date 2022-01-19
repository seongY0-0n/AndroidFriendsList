package com.cookandroid.test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddFriendsActivity extends AppCompatActivity {
    EditText edtName, edtPhone, edtBirth;
     Button btnSave;
     myDBHelper helper;
     SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends);
        helper = new myDBHelper(AddFriendsActivity.this);
        edtName = (EditText)findViewById(R.id.edtName);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtBirth = (EditText)findViewById(R.id.edtBirth);
        btnSave = (Button)findViewById(R.id.btnSave);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String rxName = extras.getString("name");
        int rxCode = extras.getInt("code");
        if(rxCode == 1001){
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sqLiteDatabase = helper.getWritableDatabase();
                    sqLiteDatabase.execSQL("INSERT INTO friends VALUES ('" + edtName.getText().toString() + "' , '" + edtPhone.getText().toString() + "', '" +edtBirth.getText().toString() + "');" );
                    sqLiteDatabase.close();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else if(rxCode == 1002){
            btnSave.setVisibility(View.GONE);
            getInfo(rxName);
        }else{
            getInfo(rxName);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sqLiteDatabase = helper.getWritableDatabase();
                    sqLiteDatabase.execSQL("UPDATE friends SET name = '" +edtName.getText().toString() + "', phone = '" + edtPhone.getText().toString() +"', birthday = '" + edtBirth.getText().toString() + "' WHERE name = '" + rxName + "';");
                    sqLiteDatabase.close();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
    public void getInfo(String rxName){
        edtName.setText(rxName);
        sqLiteDatabase = helper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM friends WHERE name = '" + rxName + "';",null);
        String Phone = "";
        String Birth = "";
        while (c.moveToNext()){
            Phone += c.getString(1);
            Birth += c.getString(2);
        }

        edtPhone.setText(Phone);
        edtBirth.setText(Birth);
        c.close();
        sqLiteDatabase.close();
    }
}
