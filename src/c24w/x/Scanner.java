package c24w.x;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Chris on 08/03/14.
 */
public class Scanner extends Activity {

    private CrosswordParser crosswordParser;
    private ScannerView scannerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner);

        crosswordParser = new CrosswordParser();

        scannerView = (ScannerView) findViewById(R.id.scanner_view);
        scannerView.setCallback(handlePixelData());
    }

    private ScannerView.Callback handlePixelData() {
        return new ScannerView.Callback() {
            @Override
            public void onData(byte[] data) {
                crosswordParser.parse(data, handleSuccessfulParse());
            }
        };
    }

    private CrosswordParser.Callback handleSuccessfulParse() {
        return new CrosswordParser.Callback() {
            @Override
            public void success(Crossword crossword) {
                Toast.makeText(Scanner.this, "Successful parse", Toast.LENGTH_SHORT).show();
            }
        };
    }
}