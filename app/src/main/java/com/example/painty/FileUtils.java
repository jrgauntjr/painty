package com.example.painty;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.OutputStream;
import java.util.Objects;

public class FileUtils {

    public static void saveBitmapToStorage(Bitmap bitmap, Context context) {
        OutputStream outputStream = null;
        try {
            // Set metadata for the image
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "Painting_" + System.currentTimeMillis() + ".png");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

            // For Android 10 and above, specify a relative path
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Painty");
            }

            // Insert the image into MediaStore
            outputStream = context.getContentResolver().openOutputStream(
                    Objects.requireNonNull(context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values))
            );

            // Write a message to the user confirming or denying saved image
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                Toast.makeText(context, "Painting saved successfully.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Failed to access storage.", Toast.LENGTH_SHORT).show();
            }
        }
        // Catching any other errors that may arise
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to save painting.", Toast.LENGTH_SHORT).show();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
