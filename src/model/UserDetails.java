package model;

import java.util.Random;

public class UserDetails {
    private String name;
    private String phone;
    private String address;
    private String email;
    private String userName;
    private String password;
    private String userId;
    private boolean isAdmin;
    private String adminId;

    public UserDetails(String name, String phone, String address, String email, String userName, String password) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.userId = (name.length() >= 3 ? name.substring(0, 3) : name) + setsId();
        this.isAdmin = false;
        this.adminId = null;
    }

    private int setsId() {
        Random rand = new Random();
        return rand.nextInt(9000) + 1000;
    }
    public String getUsername() {
        return userName;
    }

    public String getId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}