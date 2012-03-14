package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import no.niths.domain.signaling.AccessField;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Room implements Serializable {

	private static final long serialVersionUID = -664567726655902624L;

    private Long id;

    private String roomName;

    private List<AccessField> accessFields = new ArrayList<AccessField>();

    public Room(String roomName) {
		setRoomName(roomName);
	}

	public Room() {
		this(null);
	}

	public void setId(Long id) {
        this.id = id;
    }

    public Long getId() { 
        return id;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setAccessFields(List<AccessField> accessFields) {
        this.accessFields = accessFields;
    }

    public List<AccessField> getAccessFields() {
        return accessFields;
    }
    
    @Override
    public String toString() {
    	return String.format("[%s][%s]", id, roomName);
    }
}