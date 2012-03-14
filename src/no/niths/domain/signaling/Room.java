package no.niths.domain.signaling;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String roomName;

    private List<AccessField> accessFields;

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
}