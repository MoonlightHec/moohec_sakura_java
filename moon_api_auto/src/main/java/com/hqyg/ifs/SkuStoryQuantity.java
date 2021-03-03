package com.hqyg.ifs;

/**
 * @Author lijun
 * @Date 2020年6月28日 20:22:36
 * @Description //TODO
 **/
public class SkuStoryQuantity {
    private String sku;
    private int aStoryQuantity;
    private int bStoryQuantity;
    private int cStoryQuantity;
    private int MOQ;
    private int stockQuantity;
    private int orderQuantity;
    private int weekSales;
    private int finalQuantity;
    private float firstCutQuantity;


    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getAStoryQuantity() {
        return aStoryQuantity;
    }

    public void setAStoryQuantity(int aStoryQuantity) {
        this.aStoryQuantity = aStoryQuantity;
    }

    public int getBStoryQuantity() {
        return bStoryQuantity;
    }

    public void setBStoryQuantity(int bStoryQuantity) {
        this.bStoryQuantity = bStoryQuantity;
    }

    public int getCStoryQuantity() {
        return cStoryQuantity;
    }

    public void setCStoryQuantity(int cStoryQuantity) {
        this.cStoryQuantity = cStoryQuantity;
    }

    public int getMOQ() {
        return MOQ;
    }

    public void setMOQ(int MOQ) {
        this.MOQ = MOQ;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getWeekSales() {
        return weekSales;
    }

    public void setWeekSales(int weekSales) {
        this.weekSales = weekSales;
    }

    public int getFinalQuantity() {
        return finalQuantity;
    }

    public void setFinalQuantity(int finalQuantity) {
        this.finalQuantity = finalQuantity;
    }

    public float getFirstCutQuantity() {
        return firstCutQuantity;
    }

    public void setFirstCutQuantity(float firstCutQuantity) {
        this.firstCutQuantity = firstCutQuantity;
    }
}
