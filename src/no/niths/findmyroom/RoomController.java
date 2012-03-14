package no.niths.findmyroom;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import config.ServerURL;

import domain.Room;

import android.content.Context;
import android.util.Log;

public class RoomController {

    private Context context;
    private RestTemplate rest; 

    public RoomController(Context context) {
        this.context = context;
        rest = new RestTemplate();
    }

    public Room[] fetchRooms() {
        Room[] rooms = null;

        try {
            rooms = rest.exchange(
                    ServerURL.LOCAL_URL.getURL(),
                    HttpMethod.GET,
                    null,
                    Room[].class).getBody();

        } catch (RestClientException e) {
            Log.e(context.getString(R.string.connection_error), e.getMessage());
        }

        return rooms;
    }
}