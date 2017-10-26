/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionnary;

import java.util.List;



/**
 *
 * @author nthan
 */

public class Meanings {
    
    private String pronunciation = null;
    private String attribute = null;
    private String listWordtrans = null;
    private String KeyWord = null;
    
    /*
        Contructor
    */
    public  Meanings(){}
    public Meanings(String _pronunciation, String _attribute, String _listWordtrans, String _Key){
        this.pronunciation = _pronunciation;
        this.attribute = _attribute;
        this.listWordtrans = _listWordtrans;
        this.KeyWord = _Key;
        
    }
    
    public String getPronunciation(){
        return this.pronunciation;
    }
    
    public String getAttribute(){
        return this.attribute;
    }
    
    public String getlistWordtrans(){
        return this.listWordtrans;
    }
    
    public String getKeyWord(){
        return this.KeyWord;
    }
    
    public void setPronunciation(String _pronunciation){
        this.pronunciation = _pronunciation;
    }
    
    public void setAttribute(String _attribute){
        this.attribute= _attribute;
    }
    
    public void setListWord(String _listword){
        this.listWordtrans = _listword;
    }
    
    public void setKeyWord(String _Key){
        this.KeyWord = _Key;
    }

            
}
