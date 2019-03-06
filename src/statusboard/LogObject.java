package statusboard;

public class LogObject {
    private String datetime;
    private String event;
    private String detail;

    public String getDatetime() {
        return datetime;
    }

    public LogObject setDatetime(String datetime) {
        this.datetime = datetime;
        return this;
    }

    public String getEvent() {
        return event;
    }

    public LogObject setEvent(String event) {
        this.event = event;
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
