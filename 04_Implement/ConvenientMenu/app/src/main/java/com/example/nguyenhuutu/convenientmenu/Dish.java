package com.example.nguyenhuutu.convenientmenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Dish implements Comparable {
    /**
     * Properties
     */
    public static String NEW = "mới";
    public static String HOT = "hot";
    public static int colorNew = R.color.yellow;
    public  static int colorHot = R.color.red;
    private String dishId;
    private String dishName;
    private Integer dishPrice;
    private String dishDescription;
    private String dishHomeImage;
    private Bitmap dishImage;
    private List<String> dishMoreImages;
    private String dishTypeId;
    private Date createDate;
    private int eventType; // <0:New, >0:Hot

    private String restAccount;
    private float maxStar;

    public static int compareProperty;
    public final static int STAR = 0;

    /**
     * constructor methods
     */

    public Dish(String _dishId,
                String _dishName,
                Integer _dishPrice,
                String _dishDescription,
                String _dishHomeImage,
                List<String> _dishMoreImages,
                String _dishTypeId,
                float _maxStar,
                String _restAccount,
                Date _createdate) {

        this.dishId = _dishId;
        this.dishName = _dishName;
        this.dishPrice = _dishPrice;
        this.dishDescription = _dishDescription;
        this.dishHomeImage = _dishHomeImage;
        this.dishMoreImages = _dishMoreImages;
        this.dishTypeId = _dishTypeId;
        this.maxStar = _maxStar;
        this.restAccount = _restAccount;
        this.createDate=_createdate;
        //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(); // lấy thời gian hệ thống
        long getDiff = date.getTime() - _createdate.getTime();
        // using TimeUnit class from java.util.concurrent package
        long getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);
        Log.d("THOI GIAN KHOI TAO", String.valueOf(getDaysDiff));
        if(getDaysDiff<=7)
        {
            this.eventType = -1;
        }else
        {
            this.eventType=0;
        }
    }

    public Dish() {
        this.dishId = "";
        this.dishName = "";
        this.dishPrice = 0;
        this.dishDescription = "";
        this.dishHomeImage = "";
        this.dishMoreImages = new ArrayList<>();
        this.dishTypeId = "";
        this.maxStar = 0;
        this.restAccount = "";
    }

    public Dish(String _dishId) {
        this.dishId = _dishId;
        this.dishName = "";
        this.dishPrice = 0;
        this.dishDescription = "";
        this.dishHomeImage = "";
        this.dishMoreImages = new ArrayList<>();
        this.dishTypeId = "";
        this.maxStar = 0;
        this.restAccount = "";
    }

    /**
     * Getter methods for properties
     */

    public String getCreateDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return simpleDateFormat.format(createDate);
    }

    public void setCreateDate(Date createDate) {
        createDate = createDate;
    }

    public void setDishImage(Bitmap dishImage) {
        try{
        this.dishImage = dishImage;}catch (Exception ex){}
    }
    public String getDishId() {
        return this.dishId;
    }

    public String getDishName() {
        return this.dishName;
    }

    public String getDishDescription() {
        return this.dishDescription;
    }

    public Integer getDishPrice() {
        return this.dishPrice;
    }

    public String getDishHomeImage() {
        return this.dishHomeImage;
    }

    public List<String> getDishMoreImages() {
        return this.dishMoreImages;
    }

    public float getMaxStar() {
        return this.maxStar;
    }

    public String getDishTypeId() {
        return this.dishTypeId;
    }

    public String getRestAccount() {
        return this.restAccount;
    }

    public int getEventType() {
        return eventType;
    }

    public Bitmap getDishImage(Context context){
        if (dishImage != null) {
            return dishImage;
        }else
        {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.app_logo);
        }
    }

    /**
     * createDishId()
     * - generate a new id for new dish
     *
     * @return new id for new dish
     */
    public static String createDishId(Integer idNumber) {
        String newId;

        if (idNumber < 10) {
            newId = String.format("DISH0000%d", idNumber);
        } else if (idNumber < 100) {
            newId = String.format("DISH000%d", idNumber);
        } else if (idNumber < 1000) {
            newId = String.format("DISH00%d", idNumber);
        } else if (idNumber < 10000) {
            newId = String.format("DISH0%d", idNumber);
        } else {
            newId = String.format("DISH%d", idNumber);
        }

        return newId;
    }

    /**
     * loadDish()
     * - Load data of a dish
     *
     * @return Dish
     */
    public static Dish loadDish(Map<String, Object> document) {
        String _id = (String) document.get("dish_id");
        String _name = (String) document.get("dish_name");
        Number _price = (Number) document.get("dish_price");
        String _description = (String) document.get("dish_description");
        String _homeImage = (String) document.get("dish_home_image_file");
        List<String> _moreImages = (ArrayList) document.get("dish_more_image_files");
        float _maxStar = ((Number) document.get("max_star")).floatValue();
        String _dishTypeId = (String) document.get("dish_type_id");
        String _restAccount = (String) document.get("rest_account");
        //Number _event_type = (Number)document.get("event_type");
        Date _createdate = (Date) document.get("create_date");
        return new Dish(_id, _name, _price.intValue(), _description, _homeImage, _moreImages, _dishTypeId, _maxStar, _restAccount,_createdate);
    }

    /**
     * Create dish's data for insert to database
     *
     * @param _dishId
     * @param _dishName
     * @param _dishPrice
     * @param _dishDescription
     * @param _dishHomeImage
     * @param _dishMoreImages
     * @param _dishTypeId
     * @param _maxStar
     * @param _restAccount
     * @return
     */
    public static Map<String, Object> createDishData(String _dishId, String _dishName, Integer _dishPrice, String _dishDescription, String _dishHomeImage, List<String> _dishMoreImages, String _dishTypeId, double _maxStar, String _restAccount) {
        Map<String, Object> dishData = new HashMap<>(); // Save data of dish

        dishData.put("dish_id", _dishId);
        dishData.put("dish_name", _dishName);
        dishData.put("dish_price", _dishPrice);
        dishData.put("dish_description", _dishDescription);
        dishData.put("dish_home_image_file", _dishHomeImage);
        dishData.put("dish_more_image_files", _dishMoreImages);
        dishData.put("max_star", _maxStar);
        dishData.put("dish_type_id", _dishTypeId);
        dishData.put("rest_account", _restAccount);

        return dishData;
    }

    public int compareTo(@NonNull Object o) {
        Dish dishCmp = (Dish) o;
        int result = 0;

        if (compareProperty == STAR) {
            if (this.getMaxStar() > dishCmp.getMaxStar()) {
                result = -1;
            } else {
                result = 1;
            }
        }

        return result;
    }
}
