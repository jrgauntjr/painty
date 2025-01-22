package com.example.painty;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


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
            savePainting();
        });
    }

    private void savePainting() {
        Bitmap bitmap = drawingView.getBitmap();
        FileUtils.saveBitmapToStorage(bitmap, this); // Utility function to save the bitmap
    }

}