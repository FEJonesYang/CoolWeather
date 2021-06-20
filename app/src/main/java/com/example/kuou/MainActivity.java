package com.example.kuou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.kuou.module.weather.WeatherActivity;

import org.jetbrains.annotations.NotNull;


/**
 * 做一个 Flash 界面，用于初始化系统配置，获取定位服务之类的额，
 * 为后面启动天气展示的界面做一个准备
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /// 定位权限的获取
        int hasPermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            //已获取权限
            Toast.makeText(this, "定位权限已获取.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                    startActivity(intent);
                }
            }, 5000);
        } else {
            //未获取权限
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("申请定位权限");
            builder.setMessage("您同意该应用获取定位吗？");
            builder.setCancelable(false);
            builder.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "没有定位您将无法使用，请退出应用！", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "正在申请定位权限...", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]
                                    {Manifest.permission.ACCESS_FINE_LOCATION},
                            MainActivity.RequestPermissionCode);
                }
            });
            builder.show();
        }
    }

    /**
     * 权限申请的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RequestPermissionCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                    startActivity(intent);
                }
            }, 5000);
        }
    }
}
