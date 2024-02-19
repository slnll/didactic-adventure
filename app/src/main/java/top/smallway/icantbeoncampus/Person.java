package top.smallway.icantbeoncampus;

public class Person {
    private String title, type, time, status, id, logId, schoolId, mode, latitude, longitude;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Person(String title, String type, String time, String status, String id, String logId, String schoolId, String mode, String latitude, String longitude) {
        this.title = title;
        this.type = type;
        this.time = time;
        this.status = status;
        this.id = id;
        this.logId = logId;
        this.schoolId = schoolId;
        this.mode = mode;
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
