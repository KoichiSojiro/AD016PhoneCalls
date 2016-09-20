package com.example.trannh08.ad016phonecalls;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String DEBUG_TAG = "DEBUG TAG";
    private Button button_call;
    private EditText editText_phoneNumber;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_call = (Button) findViewById(R.id.button_call);
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });

        boolean isGranted = checkPermission();
        if(!isGranted) {
            setPermission();
        }
    }

    private void call() {
        try {
            if(checkPermission()) {
                Log.d(DEBUG_TAG, "Starting action call().");
                editText_phoneNumber = (EditText) findViewById(R.id.editText_phoneNumber);
                String phoneNumber = editText_phoneNumber.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
                Log.d(DEBUG_TAG, "Complete action call().");
            } else {
                Log.d(DEBUG_TAG, "Starting action call().");
                Toast.makeText(this, "Lacking of permission.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Log.d(DEBUG_TAG, ex.getMessage());
            Log.d(DEBUG_TAG, ex.getStackTrace().toString());
            Toast.makeText(this, "Cannot perform your action right now.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Access CALL_PHONE granted.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Access CALL_PHONE granted.", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
    }

    private void setPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                Toast.makeText(this, "CALL_PHONE permission is needed.", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PackageManager.PERMISSION_GRANTED);
            }
        } else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                Toast.makeText(this, "CALL_PHONE permission is needed.", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PackageManager.PERMISSION_GRANTED);
            }
        }
    }
}
