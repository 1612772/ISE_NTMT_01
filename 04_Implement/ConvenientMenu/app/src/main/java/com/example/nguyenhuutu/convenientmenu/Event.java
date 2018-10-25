package com.example.nguyenhuutu.convenientmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {
    /**
     * Properties
     */
    private String eventId;
    private String eventContent;
    private String eventHomeImage;
    private List<String> eventMoreImages;
    private String beginDate;
    private String endDate;
    private String restId;

    /**
     * Constructor methods
     */
    public Event(String _eventId, String _eventContent, String _eventhomeImage, List<String> _eventMoreImages, String _beginDate, String _endDate, String _restId) {
        this.eventId = _eventId;
        this.eventContent = _eventContent;
        this.eventHomeImage = _eventhomeImage;
        this.eventMoreImages = _eventMoreImages;
        this.beginDate = _beginDate;
        this.endDate = _endDate;
        this.restId = _restId;
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

    public String getEventHomeImage() {
        return eventHomeImage;
    }

    public List<String> getEventMoreImages() {
        return eventMoreImages;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getRestId() {
        return restId;
    }

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
        String _eventHomeImage = document.get("event_home_image").toString();
        List<String> _eventMoreImages = (ArrayList)document.get("event_more_images");
        String _beginDate = document.get("begin_date").toString();
        String _endDate = document.get("end_date").toString();
        String _restId = document.get("rest_id").toString();

        return new Event(_eventId, _eventContent, _eventHomeImage, _eventMoreImages, _beginDate, _endDate, _restId);
    }

    /**
     * createEventData()
     *  - Create Event's data for query
     * @param _eventId
     * @param _eventContent
     * @param _eventhomeImage
     * @param _eventMoreImages
     * @param _beginDate
     * @param _endDate
     * @param _restId
     * @return Map<String, Object>
     */
    public static Map<String, Object> createEventData(String _eventId, String _eventContent, String _eventhomeImage, List<String> _eventMoreImages, String _beginDate, String _endDate, String _restId) {
        Map<String, Object> document = new HashMap<>();

        document.put("event_id",_eventId);
        document.put("event_content", _eventContent);
        document.put("event_home_image", _eventhomeImage);
        document.put("event_more_images", _eventMoreImages);
        document.put("begin_date", _beginDate);
        document.put("end_date", _endDate);
        document.put("rest_id", _restId);

        return document;
    }
}
