package com.example.nguyenhuutu.convenientmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dish {
    /**
     * Properties
     */

    private String dishId;
    private String dishName;
    private Integer dishPrice;
    private String dishDescription;
    private String dishHomeImage;
    private List<String> dishMoreImages;
    private String menuId;
    private String dishTypeId;

    /**
     * constructor methods
     */

    public Dish(String _dishId, String _dishName, Integer _dishPrice, String _dishDescription, String _dishHomeImage, List<String> _dishMoreImages, String _menuId, String _dishTypeId) {
        this.dishId = _dishId;
        this.dishName = _dishName;
        this.dishPrice = _dishPrice;
        this.dishDescription = _dishDescription;
        this.dishHomeImage = _dishHomeImage;
        this.dishMoreImages = _dishMoreImages;
        this.dishTypeId = _dishTypeId;
        this.menuId = _menuId;
    }

    public Dish(){
        this.dishId = "";
        this.dishName = "";
        this.dishPrice = 0;
        this.dishDescription = "";
        this.dishHomeImage = "";
        this.dishMoreImages = new ArrayList<>();
        this.dishTypeId = "";
        this.menuId = "";
    }

    public Dish(String _dishId) {
        this.dishId = _dishId;
        this.dishName = "";
        this.dishPrice = 0;
        this.dishDescription = "";
        this.dishHomeImage = "";
        this.dishMoreImages = new ArrayList<>();
        this.dishTypeId = "";
        this.menuId = "";
    }

    /**
     * Getter methods for properties
     */
    protected String getDishId() {
        return this.dishId;
    }
    protected String getDishName() {
        return this.dishName;
    }
    protected String getDishDescription() {
        return this.dishDescription;
    }
    protected Integer getDishPrice() {
        return this.dishPrice;
    }
    protected String getDishHomeImage() {
        return this.dishHomeImage;
    }
    protected List<String> getDishMoreImages() {
        return this.dishMoreImages;
    }
    protected String getMenuId() {
        return this.menuId;
    }
    protected String getDishTypeId() {
        return this.dishTypeId;
    }

    /**
     * createDishId()
     *  - generate a new id for new dish
     * @return new id for new dish
     */
    public static String createDishId(Integer idNumber) {
        String newId;

        if (idNumber < 10) {
            newId = String.format("DISH0000%d", idNumber);
        }
        else if (idNumber < 100) {
            newId = String.format("DISH000%d", idNumber);
        }
        else if (idNumber < 1000) {
            newId = String.format("DISH00%d", idNumber);
        }
        else if (idNumber < 10000) {
            newId = String.format("DISH0%d", idNumber);
        }
        else {
            newId = String.format("DISH%d", idNumber);
        }

        return newId;
    }

    /**
     * loadDish()
     *  - Load data of a dish
     * @return Dish
     */
    public static Dish loadDish(Map<String, Object> document) {
        String _id = (String)document.get("dish_id");
        String _name = (String)document.get("dish_name");
        Number _price = (Number)document.get("dish_price");
        String _description = (String)document.get("dish_description");
        String _homeImage = (String)document.get("dish_home_image");
        List<String> _moreImages = (ArrayList)document.get("dish_more_images");
        String _menuId = (String)document.get("menu_id");
        String _dishTypeId = (String)document.get("dish_type_id");

        return new Dish(_id, _name, _price.intValue(), _description, _homeImage, _moreImages, _menuId, _dishTypeId);
    }

    /**
     * createDishData()
     *  - Create data for query
     * @param _dishId
     * @param _dishName
     * @param _dishPrice
     * @param _dishDescription
     * @param _dishHomeImage
     * @param _dishMoreImages
     * @param _menuId
     * @param _dishTypeId
     * @return Map<String, Object>
     */
    public static Map<String, Object> createDishData(String _dishId, String _dishName, Integer _dishPrice, String _dishDescription, String _dishHomeImage, List<String> _dishMoreImages, String _menuId, String _dishTypeId) {
        Map<String, Object> dishData = new HashMap<>(); // Save data of dish

        dishData.put("dish_id", _dishId);
        dishData.put("dish_name", _dishName);
        dishData.put("dish_price", _dishPrice);
        dishData.put("dish_description", _dishDescription);
        dishData.put("dish_home_image", _dishHomeImage);
        dishData.put("dish_more_images", _dishMoreImages);
        dishData.put("menu_id", _menuId);
        dishData.put("dish_type_id", _dishTypeId);

        return dishData;
    }
}
