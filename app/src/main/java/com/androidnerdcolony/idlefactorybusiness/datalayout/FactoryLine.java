package com.androidnerdcolony.idlefactorybusiness.datalayout;

/**
 * Created by tiger on 1/31/2017.
 */

public class FactoryLine {
    private int factoryId;
    private int factoryLineId;
    private double idleCash;
    private int level;
    private double lineCost;
    private double openCost;
    private double workCapacity;
    private boolean working;
    private boolean open;
    private int quality;
    private int time;
    private int value;

    private FactoryLine(){}
    public FactoryLine(int factoryId, int factoryLineId, double idleCash, int level, double lineCost, double openCost, double workCapacity, boolean working, boolean open, int quality, int time, int value) {
        this.factoryId = factoryId;
        this.factoryLineId = factoryLineId;
        this.idleCash = idleCash;
        this.level = level;
        this.lineCost = lineCost;
        this.openCost = openCost;
        this.workCapacity = workCapacity;
        this.working = working;
        this.open = open;
        this.quality = quality;
        this.time = time;
        this.value = value;
    }

    public int getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(int factoryId) {
        this.factoryId = factoryId;
    }

    public int getFactoryLineId() {
        return factoryLineId;
    }

    public void setFactoryLineId(int factoryLineId) {
        this.factoryLineId = factoryLineId;
    }

    public double getIdleCash() {
        return idleCash;
    }

    public void setIdleCash(double idleCash) {
        this.idleCash = idleCash;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getLineCost() {
        return lineCost;
    }

    public void setLineCost(double lineCost) {
        this.lineCost = lineCost;
    }

    public double getOpenCost() {
        return openCost;
    }

    public void setOpenCost(double openCost) {
        this.openCost = openCost;
    }

    public double getWorkCapacity() {
        return workCapacity;
    }

    public void setWorkCapacity(double workCapacity) {
        this.workCapacity = workCapacity;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
