package com.devkings.prometheusac;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

import com.devkings.prometheusac.R;
import com.devkings.prometheusac.root.RootUtils;

public class AirCommandLaunch extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (RootUtils.rootAccess()) {
            launchMii();
            finish();
            return;
        }

        Toast t = Toast.makeText(getApplicationContext(), getString(R.string.no_root), Toast.LENGTH_SHORT);
		t.show();
        finish();

    }

    protected boolean launchMii() {
        String shell = "su";
        String cmd = getString(R.string.devkings_cmd);
        int exitCode = 255;
        Process p;
	
        try {
            p = Runtime.getRuntime().exec(shell);
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
            exitCode = p.waitFor();
        } catch (IOException e1) {
            Toast t = Toast.makeText(getApplicationContext(), e1.toString(), Toast.LENGTH_SHORT);
            t.show();
        } catch (InterruptedException e) {
            Toast t = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
            t.show();
        }
        return (exitCode != 255);
    }

}


