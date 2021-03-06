package c24w.x;

import android.hardware.Camera;
import android.os.Handler;

/**
 * Created by Chris on 09/03/14.
 */
public class AutoFocusLooper {
    private Handler autoFocusLoopHandler;
    private Runnable autoFocusLoopTask;
    private final Camera camera;

    public AutoFocusLooper(Camera camera) {
        this.camera = camera;
    }

    interface Callback {
        void autoFocused(boolean success);
    }

    public void stop() {
        autoFocusLoopHandler.removeCallbacks(autoFocusLoopTask);
    }

    public void start(final int loopInterval, final Callback callback) {
        autoFocusLoopHandler = new Handler();
        autoFocusLoopTask = new Runnable() {
            @Override
            public void run() {
                autoFocus(camera, loopInterval, callback);
            }
        };
        autoFocus(camera, loopInterval, callback);
    }

    private void autoFocus(Camera camera, final int loopInterval, final Callback callback) {
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, final Camera camera) {
                callback.autoFocused(success);

                // Schedule next auto-autoFocus
                autoFocusLoopHandler.postDelayed(autoFocusLoopTask, loopInterval);
            }
        });
    }

}
