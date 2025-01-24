package com.example.painty;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class DrawingView extends View {

    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private Canvas canvas;

    // Helper function to initialize the canvas
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // Initializing the canvas/bitmap
    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);

        path = new Path();

        bitmap = Bitmap.createBitmap(1100, 1500, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
    }

    // Creating the Canvas of which the user will draw on
    @Override
    protected void onDraw(Canvas drawCanvas) {
        super.onDraw(drawCanvas);
        drawCanvas.drawBitmap(bitmap, 0, 0, null);
        drawCanvas.drawPath(path, paint);
    }

    // On the event the user touches the canvas, it draws!
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(path, paint);
                path.reset();
                break;
        }

        invalidate();
        return true;
    }

    // Getter function to get the bitmap
    public Bitmap getBitmap() {
        return bitmap;
    }

    // Using Skydove's Android color picker to select our color
    public void showColorPicker() {
        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(getContext());
        builder.setTitle("Pick a Color");
        builder.setPreferenceName("ColorPickerDialog");
        builder.setPositiveButton("Select", (ColorEnvelopeListener) (envelope, fromUser) -> {
            paint.setColor(envelope.getColor());
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
