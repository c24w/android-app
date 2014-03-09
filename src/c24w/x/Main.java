package c24w.x;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {
    public final int SCANNER_REQUEST_CODE = 1;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button scanButton = (Button) findViewById(R.id.scan_button);
        scanButton.setOnClickListener(handleScanButtonClick());

    }

    private View.OnClickListener handleScanButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchScanner();
            }
        };
    }

    private void launchScanner() {
        startActivityForResult(new Intent(this, Scanner.class), SCANNER_REQUEST_CODE);
    }
}