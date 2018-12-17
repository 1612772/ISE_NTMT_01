package com.example.nguyenhuutu.convenientmenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nguyenhuutu.convenientmenu.add_dish.AddDish;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private int eventTypeId; // <0:New, >0:Hot

    private String restAccount;
    private float maxStar;

    protected Integer dishNumber = 0;

    public static int compareProperty;
    public final static int STAR = 0;

    /**
     * constructor methods
     */

    public Dish(String _dishId, String _dishName, Integer _dishPrice, String _dishDescription, String _dishHomeImage, List<String> _dishMoreImages, String _dishTypeId, float _maxStar, String _restAccount) {
        this.dishId = _dishId;
        this.dishName = _dishName;
        this.dishPrice = _dishPrice;
        this.dishDescription = _dishDescription;
        this.dishHomeImage = _dishHomeImage;
        this.dishMoreImages = _dishMoreImages;
        this.dishTypeId = _dishTypeId;
        this.maxStar = _maxStar;
        this.restAccount = _restAccount;
    }

    public Dish(String _dishName, Integer _dishPrice, String _dishDescription, String _dishHomeImage, List<String> _dishMoreImages, String _dishTypeId, String _restAccount) {
        this.dishId = "";
        this.dishName = _dishName;
        this.dishPrice = _dishPrice;
        this.dishDescription = _dishDescription;
        this.dishHomeImage = _dishHomeImage;
        this.dishMoreImages = _dishMoreImages;
        this.dishTypeId = _dishTypeId;
        this.maxStar = 0;
        this.restAccount = _restAccount;
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

    public int getEventTypeId() {
        return eventTypeId;
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

        return new Dish(_id, _name, _price.intValue(), _description, _homeImage, _moreImages, _dishTypeId, _maxStar, _restAccount);
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

    public Map<String, Object> createDishData() {
        Map<String, Object> dishData = new HashMap<>(); // Save data of dish

        dishData.put("dish_id", this.dishId);
        dishData.put("dish_name", this.dishName);
        dishData.put("dish_price", this.dishPrice);
        dishData.put("dish_description", this.dishDescription);
        dishData.put("dish_home_image_file", this.dishHomeImage);
        dishData.put("dish_more_image_files", this.dishMoreImages);
        dishData.put("max_star", this.maxStar);
        dishData.put("dish_type_id", this.dishTypeId);
        dishData.put("rest_account", this.restAccount);

        return dishData;
    }

    private String getExtensionOfFile(String fileName){
        String extension = "";

        extension = fileName.substring(fileName.lastIndexOf('.'));

        return extension;
    }

    private String createImageFileName(String oldFileName, Integer position) {
        return dishId + "_" + position.toString() + getExtensionOfFile(oldFileName);
    }

    private String uploadImageFile(Uri file, Integer position) {
        String fileName = createImageFileName(file.getLastPathSegment(), position);

        CMStorage.storage
                .child("images/dish/" + dishId + "/" + fileName)
                .putFile(file)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("Upload file failed: ", exception.toString());
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

        return fileName;
    }

    public void addDish(final Activity activity) {
        CMDB.db
                .collection("dish")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dishNumber = task.getResult().size();
                            dishId = createDishId(dishNumber);

                            Integer imageNumber = 1;

                            Uri file = Uri.fromFile(new File(dishHomeImage));
                            dishHomeImage = uploadImageFile(file, imageNumber);

                            ArrayList<String> dishImagesTemp = new ArrayList<String>();
                            int count = dishMoreImages.size();
                            for (int index = 0; index < count; index++) {
                                imageNumber++;
                                file = Uri.fromFile(new File(dishMoreImages.get(index).toString()));
                                dishImagesTemp.add(uploadImageFile(file, imageNumber));
                            }

                            dishMoreImages.clear();
                            dishMoreImages = dishImagesTemp;

                            CMDB.db
                                    .collection("dish")
                                    .document(dishId)
                                    .set(createDishData())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            ((AddDish)activity).notifyAddDishSuccess();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            ((AddDish)activity).notifyAddDishFailed();
                                        }
                                    });

                        } else {

                        }
                    }
                });
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
