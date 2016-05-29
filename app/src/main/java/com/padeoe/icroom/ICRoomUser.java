package com.padeoe.icroom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 表示ICRoom系统{@link ICRoom}的用户，包含用户名，院系，邮箱等信息
 * Date: 2016/3/7.
 */
public class ICRoomUser {
    @JSONField(name = "id")
    private String id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "label")
    private String label_major;
    @JSONField(name = "szLogonName")
    private String student_id;
    @JSONField(name = "szHandPhone")
    private String phone;
    @JSONField(name = "szTel")
    private String tel;
    @JSONField(name = "szEmail")
    private String email;

    /**
     * 默认构造函数,因为fastjson解析对象的需要，不可删除
     */
    public ICRoomUser(){}

    public ICRoomUser(String id, String name, String label_major, String student_id, String phone, String tel, String email) {
        this.id = id;
        this.name = name;
        this.label_major = label_major;
        this.student_id = student_id;
        this.phone = phone;
        this.tel = tel;
        this.email = email;
    }

    public static ICRoomUser getFromJson(String json){
        try {
            return JSON.parseObject(json, ICRoomUser.class);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel_major() {
        return label_major;
    }

    public void setLabel_major(String label_major) {
        this.label_major = label_major;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
