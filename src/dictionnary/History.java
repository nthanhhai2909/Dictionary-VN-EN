/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionnary;

/**
 *
 * @author nthan
 */
public class History implements java.io.Serializable{
    private String Key;
    private int Day;
    private int Month;
    private int Year;
    
    public String getKey(){
        return this.Key;
    }
    public int getDay(){
        return this.Day;
    }
    public int getMonth(){
        return this.Month;
    }
    public int getYear(){
        return this.Year;
    }
    public void setKey(String _key){
        this.Key = _key;
    }
    public void setDay(int _date){
        this.Day = _date;
    }
    public void setMonth(int _month){
        this.Month = _month;
    }
    public void setYear(int _year){
        this.Year = _year;
    }
}
