package c24w.x;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Chris on 08/03/14.
 */
public class ScannerView extends SurfaceView {

    private Callback scanDataCallback;

    public ScannerView(Context context) {
        super(context);
        init();
    }

    public ScannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        if (getHolder() != null) {
            getHolder().addCallback(bindToCamera());
        }
    }

    private Camera configureCamera() {
        Camera camera = Camera.open();
        if (camera != null) {
            Camera.Parameters params = camera.getParameters();
            camera.setDisplayOrientation(90);

            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

            camera.setParameters(params);
        }
        return camera;
    }

    private SurfaceHolder.Callback bindToCamera() {
        return new SurfaceHolder.Callback() {
            private Camera camera;
            private AutoFocusLooper autoFocusLooper;

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                camera = configureCamera();
                autoFocusLooper = new AutoFocusLooper(camera);
                attachCamera(camera, surfaceHolder);
                autoFocusLooper.start(2000);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                autoFocusLooper.stop();
                detachCamera();
            }

            private void attachCamera(Camera camera, SurfaceHolder surfaceHolder) {
                try {
                    camera.setPreviewDisplay(surfaceHolder);
                } catch (IOException e) {
                    Toast.makeText(getContext(), (CharSequence) e, Toast.LENGTH_LONG).show();
                }
                camera.startPreview();
                camera.setPreviewCallback(new Camera.PreviewCallback() {
                    @Override
                    public void onPreviewFrame(byte[] bytes, Camera camera) {
                        scanDataCallback.onData(bytes);
                    }
                });
            }

            private void detachCamera() {
                camera.stopPreview();
                camera.setPreviewCallback(null);
                camera.cancelAutoFocus();
                camera.autoFocus(null);
                camera.release();
            }
        };
    }

    interface Callback {
        void onData(byte[] data);
    }

    public void setCallback(Callback callback) {
        this.scanDataCallback = callback;
    }

    private Camera.PreviewCallback handleCameraData() {
        return new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                if (scanDataCallback != null) {
                    scanDataCallback.onData(data);
                }
            }
        };
    }

}
