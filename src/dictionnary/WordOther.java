package dictionnary;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nthan
 */
public class WordOther implements java.io.Serializable {
    private  boolean LIKE = false;
    private  boolean  DISLIKE = false;
    private  boolean POPULAR = false;
    private  String Key = null;
    
    public boolean getLike(){
        return this.LIKE;   
    }
    
    public boolean  getDislike(){
        return this.DISLIKE;
    }
    
    public boolean  getPopular(){
        return this.POPULAR;
    }
    
    public String getKey(){
        return this.Key;
    }
    public void setLike(boolean set){
        this.LIKE = set;
    }
    
    public void setDisLike(boolean  set){
        this.DISLIKE = set;
    }
    
    public void setPopular(boolean set){
        this.POPULAR = set;
    }
    public void SetKey(String _Key){
        this.Key = _Key;
    }
    
}
