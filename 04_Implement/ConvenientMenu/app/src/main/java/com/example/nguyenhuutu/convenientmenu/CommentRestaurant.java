package com.example.nguyenhuutu.convenientmenu;

import java.util.HashMap;
import java.util.Map;

public class CommentRestaurant {
    /**
     * Properties
     */
    private String cmtRestid;
    private String cmtRestContent;
    private String cmtRestDate;
    private String restAccount;
    private String userAccount;
    private Float cmtRestStar;
    private String Avatar;

    /**
     * Constructor Methods
     */
    public CommentRestaurant(String _cmtRestId, String _cmtRestContent, String _cmtRestDate, String _restAccount, String _userAccount, Float _cmtRestStar,String _avatar) {
        this.cmtRestid = _cmtRestId;
        this.cmtRestContent = _cmtRestContent;
        this.cmtRestDate = _cmtRestDate;
        this.restAccount = _restAccount;
        this.userAccount = _userAccount;
        this.cmtRestStar = _cmtRestStar;
        this.Avatar = _avatar;
    }

    /**
     * Getter methods
     */
    public String getCmtRestid() {
        return cmtRestid;
    }

    public String getCmtRestContent() {
        return cmtRestContent;
    }

    public String getCmtRestDate() {
        return cmtRestDate;
    }

    public String getRestAccount() {
        return restAccount;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public Float getCmtRestStar() {
        return cmtRestStar;
    }
    public String getAvatar(){return Avatar;}

    /**
     * Other Methods
     */

    /**
     * createCommentRestId()
     *  - Create new id for CommentRestaurant
     * @param idNum
     * @return String
     */
    public static String createCommentRestId(Integer idNum) {
        String newId;

        if (idNum < 10) {
            newId = String.format("MENU0000%d", idNum);
        }
        else if (idNum < 100) {
            newId = String.format("MENU000%d", idNum);
        }
        else if (idNum < 1000) {
            newId = String.format("MENU00%d", idNum);
        }
        else if (idNum < 10000) {
            newId = String.format("MENU0%d", idNum);
        }
        else {
            newId = String.format("MENU%d", idNum);
        }

        return newId;
    }

    /**
     * loadCommentRestaurant()
     *  - Load information of a CommentRestaurant
     * @param document
     * @return CommentRestaurant
     */
    public static CommentRestaurant loadCommentRestaurant(Map<String, Object> document) {
        String _cmtRestId = document.get("cmt_rest_id").toString();
        String _cmtRestContent = document.get("cmt_rest_content").toString();
        String _cmtRestDate = document.get("cmt_rest_date").toString();
        String _restAccount = document.get("rest_account").toString();
        String _userAccount = document.get("user_account").toString();
        Float _cmtRestStar = ((Number)document.get("cmt_rest_star")).floatValue();
        String _avatar = (String)document.get("user_avatar");

        return new CommentRestaurant(_cmtRestId, _cmtRestContent, _cmtRestDate, _restAccount, _userAccount, _cmtRestStar,_avatar);
    }

    /**
     * createCommentRestaurantData()
     *  -   Create CommentRestaurant's data for query
     * @param _cmtRestId
     * @param _cmtRestContent
     * @param _cmtRestDate
     * @param _restAccount
     * @param _userAccount
     * @param _cmtRestStar
     * @return
     */
    public static Map<String, Object> createCommentRestaurantData(String _cmtRestId, String _cmtRestContent, String _cmtRestDate, String _restAccount, String _userAccount, Float _cmtRestStar,String _avatar) {
        Map<String, Object> document = new HashMap<>();

        document.put("cmt_rest_id", _cmtRestId);
        document.put("cmt_rest_content", _cmtRestContent);
        document.put("cmt_rest_date", _cmtRestDate);
        document.put("rest_account", _restAccount);
        document.put("user_account", _userAccount);
        document.put("cmt_rest_star", _cmtRestStar);
        document.put("user_avatar", _avatar);
        return document;
    }
}
