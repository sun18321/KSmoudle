package com.sun.ksmoudle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int mPhoneState;
    private int mWrite_storage;
    private int mRead_storage;
    private int mCoarse_location;
    private int mFine_location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mPhoneState = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        mWrite_storage = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        mRead_storage = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        mCoarse_location = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        mFine_location = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (mPhoneState != PackageManager.PERMISSION_GRANTED || mWrite_storage != PackageManager.PERMISSION_GRANTED || mRead_storage != PackageManager.PERMISSION_GRANTED || mCoarse_location != PackageManager.PERMISSION_GRANTED || mFine_location != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 2);
//         init();
        } else {
//         init();
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED || grantResults[2] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "没有电话权限,请打开再尝试", Toast.LENGTH_SHORT).show();
        } else {
            init();
        }
    }
    private void init() {
        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.push).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.push:
                startActivity(new Intent(this, PushActivity.class));
                break;
            case R.id.play:
                startActivity(new Intent(this, PlayActivity.class));
                break;
        }
    }
}
