package lucene.pojo;

import java.util.Date;

/**
 * Created by zengbin on 2018/3/30.
 */
public class Product {
    private int pid;
    private String name;
    private int catalog;
    private String catalog_name;
    private double price;
    private int number;
    private String description;
    private String picture;
    private Date release_time;

    public int getPid(){
        return pid;
    }

    public void setPid(int pid){
        this.pid = pid;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getCatalog(){
        return catalog;
    }

    public void setCatalog(int catalog){
        this.catalog = catalog;
    }

    public String getCatalog_name(){
        return catalog_name;
    }

    public void setCatalog_name(String catalog_name){
        this.catalog_name = catalog_name;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getPicture(){
        return picture;
    }

    public void setPicture(String picture){
        this.picture = picture;
    }

    public Date getRelease_time(){
        return release_time;
    }

    public void setRelease_time(Date release_time){
        this.release_time = release_time;
    }

    @Override
    public String toString(){
        return "Product{" +
                       "pid=" + pid +
                       ", name='" + name + '\'' +
                       ", catalog=" + catalog +
                       ", catalog_name='" + catalog_name + '\'' +
                       ", price=" + price +
                       ", number=" + number +
                       ", description='" + description + '\'' +
                       ", picture='" + picture + '\'' +
                       ", release_time=" + release_time +
                       '}';
    }
}
