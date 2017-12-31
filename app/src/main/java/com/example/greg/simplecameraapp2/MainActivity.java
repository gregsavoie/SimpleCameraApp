package com.example.greg.simplecameraapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private CameraView cameraView;
    private Button takePic;
    private Button flip;
    private Button cancel;
    private ImageView capture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = findViewById(R.id.camera);
        takePic = findViewById(R.id.takePic);
        flip = findViewById(R.id.flip);
        cancel = findViewById(R.id.cancel);
        capture = findViewById(R.id.capture);

        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Take Picture");
                cameraView.captureImage();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Cancel");
                cancel.setVisibility(View.INVISIBLE);
                capture.setAlpha(0F);
                takePic.setVisibility(View.VISIBLE);
                flip.setVisibility(View.VISIBLE);
                cameraView.start();
            }
        });

        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Flip Camera");
                int facing = cameraView.getFacing();
                switch(facing) {
                    case CameraKit.Constants.FACING_BACK:
                        cameraView.setFacing(CameraKit.Constants.FACING_FRONT);
                        break;
                    case CameraKit.Constants.FACING_FRONT:
                        cameraView.setFacing(CameraKit.Constants.FACING_BACK);
                        break;
                    default:
                        cameraView.setFacing(CameraKit.Constants.FACING_FRONT);
                }
            }
        });

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {
            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Log.e(TAG, "OnImage");
                cameraView.stop();
                cancel.setVisibility(View.VISIBLE);
                takePic.setVisibility(View.INVISIBLE);
                flip.setVisibility(View.INVISIBLE);
                capture.setImageBitmap(cameraKitImage.getBitmap());
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }
}
