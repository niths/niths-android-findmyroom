package no.niths.findmyroom;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity {

    private WifiManager wifiManager;
    private String currentRoom;

    // In-memory map of rooms (key) and AP addresses (value)
    private final Map<String, String> roomMap = new HashMap<String, String>() {{
        put("00:26:cb:d1:2e:10", "45");
        put("00:24:97:f2:92:e0", "39");
        put("00:21:55:60:e7:d0", "39");
        put("00:26:cb:d1:2d:a0", "77");
        put("00:21:55:60:e6:10", "79 / 80");
        put("00:21:55:60:e6:10", "81");
        put("00:17:0f:e7:2d:60", "78");
        put("00:23:04:88:d4:c0", "Vrimle");
        put("00:24:97:f2:93:60", "Vrimle");
    }};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configure();
    }

    private void configure() {
        setContentView(R.layout.main);
        configureButton();

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        registerReceiver(
                broadcastReceiver,
                new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
    }

    private void configureButton() {
        Button btnFindMyRoom = (Button) findViewById(R.id.btnFindMyRoom);
        btnFindMyRoom.setOnClickListener(new OnClickListener() {
            
            public void onClick(View view) {
                showToast(currentRoom);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            setCurrentRoom(wifiManager.getConnectionInfo().getBSSID());
        }
    };

    private void setCurrentRoom(String currentBSSID) {
        currentRoom = roomMap.get(currentBSSID);
    }
}