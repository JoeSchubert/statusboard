package statusboard;

public class LogObject {

    private String datetime;
    private String username;
    private String event;
    private String source;
    private String detail;

    public String getDatetime() {
        return datetime;
    }

    public LogObject setDatetime(String datetime) {
        this.datetime = datetime;
        return this;
    }
    
    public String getUsername() {
        return username;
    }
    
    public LogObject setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEvent() {
        return event;
    }

    public LogObject setEvent(String event) {
        this.event = event;
        return this;
    }
    
    public String getSource() {
        return source;
    }
    
    public LogObject setSource(String source) {
        this.source = source;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public LogObject setDetail(String detail) {
        this.detail = detail;
        return this;
    }

}
