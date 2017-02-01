package com.androidnerdcolony.idlefactorybusiness.datalayout;

/**
 * Created by tiger on 1/31/2017.
 */

public class UserState {

    private long date;
    private double balance;
    private double exp;
    private double prestige;
    private String token;
    private int level;

    private UserState(){}

    public UserState(long date, double balance, double exp, double prestige, String token, int level) {
        this.date = date;
        this.balance = balance;
        this.exp = exp;
        this.prestige = prestige;
        this.token = token;
        this.level = level;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public double getPrestige() {
        return prestige;
    }

    public void setPrestige(double prestige) {
        this.prestige = prestige;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
