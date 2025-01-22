package com.example.painty;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;
    private static final int REQUEST_WRITE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawing_view);
        Button colorButton = findViewById(R.id.color_button);
        Button saveButton = findViewById(R.id.save_button);

        // Change the color of the paint
        colorButton.setOnClickListener(v -> drawingView.showColorPicker());

        // Save the painting
        saveButton.setOnClickListener(v -> {
            if (checkPermission()) {
                savePainting();
            } else {
                requestPermission();
            }
        });
        Log.d("PermissionStatus", "Write Permission: " + checkPermission());
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                savePainting();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            }
        } else {
            savePainting(); // Permissions are automatically granted on devices below API 23
        }
    }

    private void savePainting() {
        Bitmap bitmap = drawingView.getBitmap();
        FileUtils.saveBitmapToStorage(bitmap, this); // Utility function to save the bitmap
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                savePainting();
            } else {
                Toast.makeText(this, "Permission denied. Cannot save painting.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}