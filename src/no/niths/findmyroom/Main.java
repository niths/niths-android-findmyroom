package no.niths.findmyroom;

import java.util.List;

import no.niths.domain.signaling.AccessField;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity {

    private WifiManager wifiManager; 
    private Room[] rooms;
    private String currentRoom;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpRooms();
        configureWifiListener();
        configureButton();
    }

    private void setUpRooms() {
        rooms = new RoomController(this).fetchRooms();
    }

    private void configureWifiListener() {
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
            WifiInfo info = wifiManager.getConnectionInfo();
            String bssid = info.getBSSID();
            int speed = info.getLinkSpeed(); 

            for (Room room : rooms) {
                List<AccessField> accessFields = room.getAccessFields();

                for (AccessField accessField : accessFields) {
                    Log.e("m", "match: " + accessField.getAccessPoint().getAddress() + ", " + bssid);
                    if (accessField.getAccessPoint().getAddress().equals(bssid)
                            && speed > accessField.getMinRange()
                            && speed < accessField.getMaxRange()) {
                        Log.e("match", "room: " + room.getRoomName());
                    }
                }
            }

            setCurrentRoom(wifiManager.getConnectionInfo().getBSSID());
        }
    };

    private void setCurrentRoom(String currentBSSID) {
    }
}