package no.niths.findmyroom;

import java.util.List;

import no.niths.domain.signaling.AccessField;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import domain.Room;

public class Main extends Activity {

    private TextView tevRoom;
    private RoomController controller;
    private Room[] rooms;
    private WifiManager wifiManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configure();
    }

    private void configure() {
        setContentView(R.layout.main);
        tevRoom = (TextView) findViewById(R.id.tevRoom);

        controller = new RoomController(this);

        populateRooms();

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        registerReceiver(
                broadcastReceiver,
                new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
    }

    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            WifiInfo info = wifiManager.getConnectionInfo();
            findMatch(info.getBSSID(), info.getLinkSpeed());
        }
    };

    // Fetches all rooms if the user is connected to the Internet
    private void populateRooms() {
        if (((ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null) {
            rooms = controller.fetchRooms();
        }
    }

    // Tries to find a match based on the AP and the connection speed
    private void findMatch(String bssid, int linkSpeed) {
        if (rooms != null) {
            for (Room room : rooms) {
                List<AccessField> accessFields = room.getAccessFields();

                for (AccessField accessField : accessFields) {
                    if (accessField.getAccessPoint().getAddress().equals(bssid)
                            && linkSpeed > accessField.getMinRange()
                            && linkSpeed < accessField.getMaxRange()) {

                        // Update the TextView accordingly
                        tevRoom.setText(room.getRoomName());
                        return;
                    }
                }
            }
        }

        // When there is no match
        tevRoom.setText(getString(R.string.unknown_room));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fetch_rooms:
                populateRooms();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}