package c24w.x;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Chris on 08/03/14.
 */
public class Scanner extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner);

        ScannerView scannerView = (ScannerView) findViewById(R.id.scanner_view);

        ScannerCamera scannerCamera = new ScannerCamera();

        scannerCamera.on(new ScannerCamera.Callback() {
            private CrosswordParser crosswordParser = new CrosswordParser();
            private byte[] currentPreviewData;

            @Override
            public void autoFocus(boolean success) {
                if (success) {
                    Log.v("___", "successful auto focus");
                    crosswordParser.parse(currentPreviewData, handleParsedCrossword());
                }
            }

            @Override
            public void previewFrame(byte[] data) {
                currentPreviewData = data;
            }

            private CrosswordParser.Callback handleParsedCrossword() {
                return new CrosswordParser.Callback() {
                    @Override
                    public void success(Crossword crossword) {
                        Log.v("___", "parsed crossword");
                    }
                };
            }
        });

        scannerView.attachCamera(scannerCamera);
    }
}

