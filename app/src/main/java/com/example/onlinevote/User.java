package com.example.onlinevote;

public class User {
    private String Name, Password, Nid, UserKey;
    private int Usertype, Voted;

    public User(){

    }


    public User(String name, String nid, String password, String userkey, int usertype, int voted) {
        Name = name;
        Nid = nid;
        Password = password;
        UserKey = userkey;
        Usertype = usertype;
        Voted = voted;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNid() {
        return Nid;
    }

    public void setNid(String nid) {
        Nid = nid;
    }

//    public String getPhone() {
//        return Phone;
//    }
//
//    public void setPhone(String phone) {
//        Phone = phone;
//    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public int getUsertype() {
        return Usertype;
    }

    public void setUsertype(int usertype) {
        Usertype = usertype;
    }

    public int getVoted() {
        return Voted;
    }

    public void setVoted(int voted) {
        Voted = voted;
    }

    //    public String getPhone() {
//        return Phone;
//    }
//
//    public void setPhone(String phone) {
//        Phone = phone;
//    }
//
//    public String getUserKey() {
//        return UserKey;
//    }
//
//    public void setUserKey(String userkey) {
//        UserKey = userkey;
//    }
//
//    public int getVoted() { return Voted; }
//
//    public void setVoted(int voted) { Voted = voted; }
//
//    public int getUsertype(){return Usertype;}
//
//    public void setUsertype(int usertype){ Usertype = usertype;}
//
//    public String getName() {
//        return Name;
//    }
//
//    public void setName(String name) {
//        Name = name;
//    }
//
//    public String getPassword() {
//        return Password;
//    }
//
//    public void setPassword(String password) {
//        Password = password;
//    }
//
//    public String getNid() {
//        return Nid;
//    }
//
//    public void setNid(String nid) {
//        Nid = nid;
//    }
}
