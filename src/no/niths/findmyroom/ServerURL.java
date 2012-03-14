package no.niths.findmyroom;

public enum ServerURL {

    LOCAL_URL("http://10.21.25.122:8080/niths/rooms"),

    NITHS_URL("http://ec2-46-137-44-111.eu-west-1.compute.amazonaws.com:8181/niths/rooms");

    private String url;

    ServerURL(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}