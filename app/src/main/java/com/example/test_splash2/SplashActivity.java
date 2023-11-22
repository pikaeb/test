package com.example.test_splash2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class SplashActivity extends AppCompatActivity {

    public int width, height;   // 가로, 세로
    public RelativeLayout.LayoutParams rp;   // rp의 속성을 가져옴, getLayoutParams : 현재 레이아웃 요소의 속성객체를 얻어옴
    public LinearLayout.LayoutParams lp;     // lp의 속성을 가져옴

    public static SharedPreferences pref;

    private static final int REQUEST_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Display display = getWindowManager().getDefaultDisplay();   // 상단바를 제외한 폰 크기를 반환함
//        Point size = new Point();   // Point 메소드
//        display.getSize(size);
//        width = size.x;     // 가로(화면의 폭 얻어오기)
//        height = size.y;    // 세로(화면의 높이 얻어오기)
//
//        ImageView splash = findViewById(R.id.splash);   // 앱 시작 이미지
//        lp = lp(splash);
//        lp.width = cp(337);
//        lp.height = cp(124);
//        Glide.with(this).load(R.drawable.splash_img).
//                skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(splash);    // '첫화면' 이미지 적용(OutOfMemoryError 때문에 Glide 사용)
//
//        pref = getSharedPreferences("pref", MODE_PRIVATE);  // SharedPreferences 객체를 받아옴(이름, MODE_PRIVATE: 여기서만 읽고 쓰기)

        // Check and request necessary permissions
        if (checkPermissions()) {
            proceedToNextScreen();
        } else {
            requestPermissions();
        }
    }


    private boolean checkPermissions() {
        // Check if the necessary permissions are granted
        int cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int notificationsPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);

        return cameraPermission == PackageManager.PERMISSION_GRANTED &&
                notificationsPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        // Request necessary permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_PERMISSIONS
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            // Check if all requested permissions are granted
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                proceedToNextScreen();
            } else {
                // Handle the case where permissions are not granted
                Toast.makeText(this, "Permissions not granted. Exiting...", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void proceedToNextScreen() {
        Log.d("chkchk", "Permissions granted. Proceeding to the next screen.");
        // Continue with your logic for a ready state...
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected RelativeLayout.LayoutParams rp(View v) {
        return (RelativeLayout.LayoutParams) v.getLayoutParams();   // rp의 속성을 가져옴, getLayoutParams : 현재 레이아웃 요소의 속성객체를 얻어옴
    }

    protected LinearLayout.LayoutParams lp(View v) {
        return (LinearLayout.LayoutParams) v.getLayoutParams();     // lp의 속성을 가져옴
    }

    public int cp(int cp) {     // 폰 크기에 맞추기
        float pixel = width * cp / 1080.0f;     // 폰 크기에 맞는 픽셀 값을 구함
        return (int) pixel;     // 픽셀을 반환함(폰 크기에 따라 정확하게 비율을 조정할 수 있음)
    }
}

