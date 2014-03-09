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

    public void stop() {
        autoFocusLoopHandler.removeCallbacks(autoFocusLoopTask);
    }

    public void start(final int loopInterval) {
        autoFocusLoopHandler = new Handler();
        autoFocusLoopTask = new Runnable() {
            @Override
            public void run() {
                autoFocus(camera, loopInterval);
            }
        };
        autoFocus(camera, loopInterval);
    }

    private void autoFocus(Camera camera, final int loopInterval) {
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, final Camera camera) {
                autoFocusLoopHandler.postDelayed(autoFocusLoopTask, loopInterval);
            }
        });
    }

}
