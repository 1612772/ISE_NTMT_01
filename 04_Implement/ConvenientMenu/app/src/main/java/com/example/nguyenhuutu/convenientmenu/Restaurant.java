package com.example.nguyenhuutu.convenientmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
    /**
     * Properties
     */

    private static final Number ZERO = 0;
    private String restAccount;
    private String restPassword;
    private String restName;
    private List<String> restAddresses;
    private String restDescription;
    private List<String> restMoreImages;
    private String restHomeImage;
    private Double maxStar;
    private Long viewedNumber;

    /**
     * Methods List
     */

    public Restaurant(){
        this.restAccount = "";
        this.restPassword = "";
        this.restName = "";
        this.restAddresses = new ArrayList<>();
        this.restDescription = "";
        this.restHomeImage = "";
        this.restMoreImages = new ArrayList<>();
        this.maxStar = ZERO.doubleValue();
        this.viewedNumber = ZERO.longValue();
    }

    public Restaurant(String _restAccount, String _restPassword, String _restName, String _restDescription, List<String> _restAddresses, String _restHomeImage, List<String> _restMoreImages, Double _maxStar, Long _viewedNumber) {

        this.restAccount = _restAccount;
        this.restPassword = _restPassword;
        this.restName = _restName;
        this.restAddresses = _restAddresses;
        this.restDescription = _restDescription;
        this.restHomeImage = _restHomeImage;
        this.restMoreImages = _restMoreImages;
        this.maxStar = _maxStar;
        this.viewedNumber = _viewedNumber;
    }

    /**
     * Getter methods for properties
     */
    public String getRestAccount() {
        return this.restAccount;
    }

    public String getRestPassword() {
        return this.restPassword;
    }

    public String getRestName() {
        return this.restName;
    }

    public List<String> getRestAddresses() {
        return this.restAddresses;
    }

    public String getRestDescription() {
        return this.restDescription;
    }

    public String getRestHomeImage() {
        return this.restHomeImage;
    }

    public List<String> getRestMoreImages() {
        return this.restMoreImages;
    }

    public Double getMaxStar() {
        return maxStar;
    }

    public Long getViewedNumber() {
        return viewedNumber;
    }

    /**
     * loadRestaurant()
     *  - Load data of a restaurant
     * @return Restaurant
     */
    public static Restaurant loadRestaurant(Map<String, Object> document) {
        String _restAccount = (String)document.get("rest_account");
        String _restPassword= (String)document.get("rest_password");
        String _restName = (String)document.get("rest_name");
        String _restDdescription = (String)document.get("rest_description");
        List<String> _restAddresses = (ArrayList)document.get("rest_addresses");
        String _restHomeImage = (String)document.get("rest_home_image");
        List<String> _restMoreImages = (ArrayList)document.get("rest_more_images");
        Double _maxStar = ((Number)document.get("max_star")).doubleValue();
        Long _viewedNumber = (Long)document.get("viewed_number");

        return new Restaurant(_restAccount, _restPassword, _restName, _restDdescription, _restAddresses, _restHomeImage, _restMoreImages, _maxStar, _viewedNumber);
    }

    /**
     * createRestaurantData()
     *  - Create restaurant's data for query
     * @param _restAccount
     * @param _restPassword
     * @param _restName
     * @param _restDescription
     * @param _restAddresses
     * @param _restHomeImage
     * @param _restMoreImages
     * @return Map<String, Object>
     */
    public static Map<String, Object> createRestaurantData(String _restAccount, String _restPassword, String _restName, String _restDescription, List<String> _restAddresses, String _restHomeImage, List<String> _restMoreImages, Double _maxStar, Long _viewedNumber) {
        Map<String, Object> restData = new HashMap<>(); // Save data of dish

        restData.put("rest_account", _restAccount);
        restData.put("rest_password", _restPassword);
        restData.put("rest_name", _restName);
        restData.put("rest_description", _restDescription);
        restData.put("rest_addresses", _restAddresses);
        restData.put("rest_home_image", _restHomeImage);
        restData.put("rest_more_images", _restMoreImages);
        restData.put("max_star", _maxStar);
        restData.put("viewed_number", _viewedNumber);

        return restData;
    }


}
