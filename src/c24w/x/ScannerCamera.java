package c24w.x;

import android.hardware.Camera;

public class ScannerCamera {

    private Callback callback;
    private Camera camera;
    private AutoFocusLooper autoFocusLooper;

    interface Callback {
        void autoFocus(boolean success);
        void previewFrame(byte[] data);
    }

    public Camera configureCamera() {
        camera = Camera.open();

        if (camera != null) {
            Camera.Parameters params = camera.getParameters();
            camera.setDisplayOrientation(90);
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            camera.setParameters(params);
        }

        return camera;
    }

    public void init() {
        autoFocusLooper = new AutoFocusLooper(camera);
        autoFocusLooper.start(R.integer.auto_focus_interval, new AutoFocusLooper.Callback() {
            @Override
            public void autoFocused(boolean success) {
                if (callback != null) {
                    callback.autoFocus(success);
                }
            }
        });
    }

    public void release() {
        if (autoFocusLooper != null) {
            autoFocusLooper.stop();
        }
    }

    public void on(Callback callback) {
        this.callback = callback;
    }

    public void triggerPreviewFrame(byte[] data) {
        if (callback != null) {
            callback.previewFrame(data);
        }
    }
}
