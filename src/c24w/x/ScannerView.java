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

    public ScannerView(Context context) {
        super(context);
    }

    public ScannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private SurfaceHolder.Callback bindToCamera(final ScannerCamera scannerCamera) {
        return new SurfaceHolder.Callback() {

            private Camera camera;

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                camera = scannerCamera.configureCamera();
                attachCamera(surfaceHolder);
                scannerCamera.init();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                detachCamera();
            }

            private void attachCamera(SurfaceHolder surfaceHolder) {
                try {
                    camera.setPreviewDisplay(surfaceHolder);
                } catch (IOException e) {
                    Toast.makeText(getContext(), (CharSequence) e, Toast.LENGTH_LONG).show();
                }
                camera.startPreview();
                camera.setPreviewCallback(new Camera.PreviewCallback() {
                    @Override
                    public void onPreviewFrame(byte[] data, Camera camera) {
                        scannerCamera.triggerPreviewFrame(data);
                    }
                });
            }

            private void detachCamera() {
                scannerCamera.release();
                camera.stopPreview();
                camera.setPreviewCallback(null);
                camera.cancelAutoFocus();
                camera.autoFocus(null);
                camera.release();
            }
        };
    }

    public void attachCamera(ScannerCamera camera) {
        if (getHolder() != null) {
            getHolder().addCallback(bindToCamera(camera));
        }
    }
}
