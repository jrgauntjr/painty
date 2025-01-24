package com.example.painty;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing our drawing view and buttons
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

    // Helper function to call the save process in the FileUtils class
    private void savePainting() {
        Bitmap bitmap = drawingView.getBitmap();
        FileUtils.saveBitmapToStorage(bitmap, this); // Utility function to save the bitmap
    }

}