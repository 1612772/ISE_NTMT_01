package com.example.nguyenhuutu.convenientmenu;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event implements Comparable{
    /**
     * Properties
     */
    private String eventId;
    private String eventContent;
    private List<String> eventImageFiles;
    private Date beginDate;
    private Date endDate;
    private String restAccount;
    private Date datePublish;

    public static int compareProperty;
    public final static int DATE = 0;

    /**
     * Constructor methods
     */
    public Event(String _eventId, String _eventContent, List<String> _eventImageFiles, Date _beginDate, Date _endDate, String _restAccount, Date _datePublish) {
        this.eventId = _eventId;
        this.eventContent = _eventContent;
        this.eventImageFiles = _eventImageFiles;
        this.beginDate = _beginDate;
        this.endDate = _endDate;
        this.restAccount = _restAccount;
        this.datePublish = _datePublish;
    }

    /**
     * Getter methods
     */
    public String getEventId() {
        return eventId;
    }

    public String getEventContent() {
        return eventContent;
    }

    public List<String> getEventImageFiles() {
        return eventImageFiles;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getRestAccount() {
        return restAccount;
    }

    public Date getDatePublish() { return datePublish; }

    /**
     * Other methods
     */

    /**
     * createEventId()
     *  - Create new id for event
     * @param idNum
     * @return String
     */
    public String createEventId(Integer idNum) {
        String newId;

        if (idNum < 10) {
            newId = String.format("EVENT000%d", idNum);
        }
        else if (idNum < 100) {
            newId = String.format("EVENT00%d", idNum);
        }
        else if (idNum < 1000) {
            newId = String.format("EVENT0%d", idNum);
        }
        else {
            newId = String.format("EVENT%d", idNum);
        }

        return newId;
    }

    /**
     * loadEvent()
     *  - Load Event data from document
     * @param document
     * @return Event
     */
    public static Event loadEvent(Map<String, Object> document) {
        String _eventId = document.get("event_id").toString();
        String _eventContent = document.get("event_content").toString();
        List<String> _eventImageFiles = (ArrayList)document.get("event_image_files");
        Date _beginDate = (Date)document.get("begin_date");
        Date _endDate = (Date)document.get("end_date");
        String _restAccount= document.get("rest_account").toString();
        Date _datePublish = (Date)document.get("date_publish");

        return new Event(_eventId, _eventContent, _eventImageFiles, _beginDate, _endDate, _restAccount, _datePublish);
    }

    /**
     * Create event's data for insert to database
     * @param _eventId
     * @param _eventContent
     * @param _eventImageFiles
     * @param _beginDate
     * @param _endDate
     * @param _restAccount
     * @return
     */
    public static Map<String, Object> createEventData(String _eventId, String _eventContent, List<String> _eventImageFiles, Date _beginDate, Date _endDate, String _restAccount) {
        Map<String, Object> document = new HashMap<>();

        document.put("event_id",_eventId);
        document.put("event_content", _eventContent);
        document.put("event_image_files", _eventImageFiles);
        document.put("begin_date", new Timestamp(_beginDate.getTime()));
        document.put("end_date", new Timestamp(_endDate.getTime()));
        document.put("rest_account", _restAccount);
        document.put("date_publish", new Timestamp((new Date()).getTime()));

        return document;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Event eventCmp = (Event)o;
        int result = 0;

        if (compareProperty == DATE) {
            if (this.getDatePublish().compareTo(eventCmp.getDatePublish()) > 1) {
                result = -1;
            } else {
                result = 1;
            }
        }

        return result;
    }
}
