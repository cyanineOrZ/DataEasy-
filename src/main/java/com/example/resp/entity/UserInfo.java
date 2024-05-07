package com.example.resp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userinfo")
public class UserInfo {

    @Id
    private Long userid;
    private String username;
    private String userage;
    private String usersex;
    private String useremail;
    private String userphone;
    private String userwechart;
    private String usersignature;
    private String userword;
    private String useraddress;
    private String usereducate;
    private String userintro;
    private String userbackgroundcolor;
    private Boolean userinfolayout;
    private String userblogheadpicture;

    private String useravatarpicture;

    public UserInfo() {}

    public UserInfo(Long userid, String username, String userage, String usersex, String useremail, String userphone, String userwechart, String usersignature, String userword, String useraddress, String usereducate, String userintro, String userbackgroundcolor, Boolean userinfolayout, String userblogheadpicture, String useravatarpicture) {
        this.userid = userid;
        this.username = username;
        this.userage = userage;
        this.usersex = usersex;
        this.useremail = useremail;
        this.userphone = userphone;
        this.userwechart = userwechart;
        this.usersignature = usersignature;
        this.userword = userword;
        this.useraddress = useraddress;
        this.usereducate = usereducate;
        this.userintro = userintro;
        this.userbackgroundcolor = userbackgroundcolor;
        this.userinfolayout = userinfolayout;
        this.userblogheadpicture = userblogheadpicture;
        this.useravatarpicture = useravatarpicture;
    }

    public Long getUserid() {return userid;}public void setUserid(Long userid) {this.userid = userid;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserage() {
        return userage;
    }

    public void setUserage(String userage) {
        this.userage = userage;
    }

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUserwechart() {
        return userwechart;
    }

    public void setUserwechart(String userwechart) {
        this.userwechart = userwechart;
    }

    public String getUsersignature() {
        return usersignature;
    }

    public void setUsersignature(String usersignature) {
        this.usersignature = usersignature;
    }

    public String getUserword() {
        return userword;
    }

    public void setUserword(String userword) {
        this.userword = userword;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public String getUsereducate() {
        return usereducate;
    }

    public void setUsereducate(String usereducate) {
        this.usereducate = usereducate;
    }

    public String getUserintro() {
        return userintro;
    }

    public void setUserintro(String userintro) {
        this.userintro = userintro;
    }

    public String getUserbackgroundcolor() {
        return userbackgroundcolor;
    }

    public void setUserbackgroundcolor(String userbackgroundcolor) {
        this.userbackgroundcolor = userbackgroundcolor;
    }

    public Boolean getUserinfolayout() {
        return userinfolayout;
    }

    public void setUserinfolayout(Boolean userinfolayout) {
        this.userinfolayout = userinfolayout;
    }

    public String getUserblogheadpicture() {return userblogheadpicture;}public void setUserblogheadpicture(String userblogheadpicture) {this.userblogheadpicture = userblogheadpicture;}
    public String getUseravatarpicture() {return useravatarpicture;}public void setUseravatarpicture(String useravatarpicture) {this.useravatarpicture = useravatarpicture;}

    @Override
    public String toString() {
        return "UserInfo{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", userage='" + userage + '\'' +
                ", usersex='" + usersex + '\'' +
                ", useremail='" + useremail + '\'' +
                ", userphone='" + userphone + '\'' +
                ", userwechart='" + userwechart + '\'' +
                ", usersignature='" + usersignature + '\'' +
                ", userword='" + userword + '\'' +
                ", useraddress='" + useraddress + '\'' +
                ", usereducate='" + usereducate + '\'' +
                ", userintro='" + userintro + '\'' +
                ", userbackgroundcolor='" + userbackgroundcolor + '\'' +
                ", userinfolayout='" + userinfolayout + '\'' +
                ", userblogheadpicture='" + userblogheadpicture + '\'' +
                ", useravatarpicture='" + useravatarpicture + '\'' +
                '}';
    }
}
