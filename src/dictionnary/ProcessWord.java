/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionnary;


import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.reflect.Array.set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Set;


/**
 *
 * @author nthan
 */
public class ProcessWord  {
    public static HashMap <String, Meanings> listEn_Vn = new HashMap<String, Meanings>();
    public static HashMap <String, Meanings> listVn_En = new HashMap<String, Meanings>();
    public static List<String> listKey_EN_VN = new ArrayList<String>();
    public static List<String> listKey_VN_EN = new ArrayList<String>();
    public static List<WordOther> listWordOther = new ArrayList<WordOther>();
    public static List<WordOther> listWordOther_VN = new ArrayList<WordOther>();
    public static List<History> historyEN = new ArrayList<History>();
    public static List<History> historyVN = new ArrayList<History>();
    public static final int MAX = 20;
    
    /*
        ReadFile Viet_Anh.xml
    */
    public void ReadFile_EN_VN(){
        File En_Vn_XML = null;
        try{
            En_Vn_XML = new File("data\\Anh_Viet.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc  = dBuilder.parse(En_Vn_XML);
            
            doc.getDocumentElement().normalize();
            
            NodeList list = doc.getElementsByTagName("record");
            for(int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element elm = (Element)node;
                    String keyword;
                    // get word ENGLISH
                    keyword = elm.getElementsByTagName("word").item(0).getTextContent().toString();
                    listKey_EN_VN.add(keyword);
                    String waitpro;
                    Meanings temp;
                    waitpro = elm.getElementsByTagName("meaning").item(0).getTextContent().toString();
                    /*
                        String processing into 3 components
                            +   pronunciation
                            +   attrubute
                            +   listword
                            +   Key
                    */
                    String[] edit_1 = waitpro.split("\\*");
                     //Has attribute
                    if(edit_1.length > 1){
                        String[] edit_2 = edit_1[1].split("(?=-)", 2);
                        if(edit_2.length > 1){
                            temp = new Meanings(edit_1[0],edit_2[0], edit_2[1], keyword);  
                        }
                        else{
                            temp = new Meanings(edit_1[0],edit_2[0], "No define!!!", keyword);
                            
                        }
                        if(temp!= null)
                            listEn_Vn.put(keyword, temp);
                         
                    }
                     //No attribute
                    else{
                        String[] edit_3 = waitpro.split("(?=-)", 2);
                        if(edit_3.length > 1)
                        {
                            temp = new Meanings(edit_3[0], "Not defined!!!", edit_3[1], keyword);
                            
                        }
                        else{
                            temp = new Meanings(edit_3[0], "Not defined!!!", "Not defined!!!", keyword); 
                        }
                        if(temp != null)
                            listEn_Vn.put(keyword,temp);
                    } 
                }    
            }
        }
        catch(Exception e){
            System.out.println("ReadFile Error!!!");
        }
    }
    
    /*
        ReadFile Anh_Viet.xml
    */
    
    public void ReadFileVN_EN()
    {
        File Vn_En_XML = null;
        try{
            Vn_En_XML = new File("data\\Viet_Anh.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc  = dBuilder.parse(Vn_En_XML);
            
            doc.getDocumentElement().normalize();
            
            NodeList list = doc.getElementsByTagName("record");
            for(int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element elm = (Element)node;
                    String keyword;
                    // get word ENGLISH
                    keyword = elm.getElementsByTagName("word").item(0).getTextContent().toString();
                    listKey_VN_EN.add(keyword);
                    String waitpro;
                    Meanings temp;
                    waitpro = elm.getElementsByTagName("meaning").item(0).getTextContent().toString();
                    /*
                        String processing into 3 components
                            +   pronunciation
                            +   attrubute
                            +   listword
                            +   Key
                    */
                    String[] edit_1 = waitpro.split("\\*");
                     //Has attribute
                    if(edit_1.length > 1){
                        String[] edit_2 = edit_1[1].split("(?=-)", 2);
                        if(edit_2.length > 1){
                            temp = new Meanings(edit_1[0],edit_2[0], edit_2[1], keyword);  
                        }
                        else{
                            temp = new Meanings(edit_1[0],edit_2[0], "No define!!!", keyword);
                            
                        }
                        if(temp!= null)
                            listVn_En.put(keyword, temp);
                         
                    }
                     //No attribute
                    else{
                        String[] edit_3 = waitpro.split("(?=-)", 2);
                        if(edit_3.length > 1)
                        {
                            temp = new Meanings(edit_3[0], "Not defined!!!", edit_3[1], keyword);
                            
                        }
                        else{
                            temp = new Meanings(edit_3[0], "Not defined!!!", "Not defined!!!", keyword); 
                        }
                        if(temp != null)
                            listVn_En.put(keyword,temp);
                    } 
                    
                }
            }
        }
        catch(Exception e){
            System.out.println("ReadFile error!!!");
        }
    }
    /*
        Search word in lists
        if(Definition) -> word
    if(noDefine) -> 20 word similar
    */
    
    public List<String> SearchSimilar(String Key){
        List<String> temp = new ArrayList<String>();
        int count = 0;
        for(int i = 0; i < temp.size(); i++){
            if(listKey_EN_VN.get(i).startsWith(Key) == true){                
                count++;
                temp.add(listKey_EN_VN.get(i));      
            }
             if(count >= MAX){
                 break;
            }
        }
        return temp;
    }
    // Lookup KeyWord EN -> VN
    public Meanings LookUpEN_VN(String Key){
        return listEn_Vn.get(Key);
    }
    // Lookup KeyWord vN -> EN
    public Meanings lookUpVn_EN(String Key){
        return listVn_En.get(Key);
    }   
    
    
        // check like word
    public void CheckLike(String Key){
        if(Dictionnary.Translation == 1){ // EN_VN
            // Not in list OtherWord
             if(checkExist(Key, 1) == -1){
                 WordOther temp = new WordOther();
                 temp.SetKey(Key);
                 temp.setLike(true);
                 temp.setDisLike(false);
                 listWordOther.add(temp);
             }
             // Have in list OtherWord
             else{
                 listWordOther.get(checkExist(Key, 1)).setLike(true);
                 listWordOther.get(checkExist(Key, 1)).setDisLike(false);
             }
          }
        if(Dictionnary.Translation == 2){// VN_EN{
             // Not in list OtherWord
             if(checkExist(Key, 2) == -1){
                 WordOther temp = new WordOther();
                 temp.SetKey(Key);
                 temp.setLike(true);
                 temp.setDisLike(false);
                 listWordOther_VN.add(temp);
             }
             // Have in list OtherWord
             else{
                 listWordOther_VN.get(checkExist(Key, 2)).setLike(true);
                 listWordOther_VN.get(checkExist(Key, 2)).setDisLike(false);
             }
        }
    }
    // check dislike word
    public void CheckDisLike(String Key){
        if(Dictionnary.Translation == 1){ // EN_VN
            // Not in list OtherWord
            if(checkExist(Key, 1) == -1){
                WordOther temp = new WordOther();
                temp.SetKey(Key);
                temp.setLike(false);
                temp.setDisLike(true);
                listWordOther.add(temp);
             }
            // Have in list OtherWord
            else{
                listWordOther.get(checkExist(Key,1)).setLike(false);
                listWordOther.get(checkExist(Key,1)).setDisLike(true);
            }      
        }
        if(Dictionnary.Translation == 2) {//VN_EN    
        }
                        // Not in list OtherWord
            if(checkExist(Key, 2) == -1){
                WordOther temp = new WordOther();
                temp.SetKey(Key);
                temp.setLike(false);
                temp.setDisLike(true);
                listWordOther_VN.add(temp);
             }
            // Have in list OtherWord
            else{
                listWordOther_VN.get(checkExist(Key,2)).setLike(false);
                listWordOther_VN.get(checkExist(Key,2)).setDisLike(true);
            } 
        }

    //check popular
    // return index key in list
    public void checkPopular(String Key, boolean check){
        if(Dictionnary.Translation == 1){
            if(checkExist(Key,1) == -1){
                WordOther temp = new WordOther();
                temp.SetKey(Key);
                temp.setPopular(check);
                listWordOther.add(temp);
            }
            else{
                listWordOther.get(checkExist(Key,1)).setPopular(check); 
            }
        }
        if(Dictionnary.Translation == 2){
            if(checkExist(Key,2) == -1){
                WordOther temp = new WordOther();
                temp.SetKey(Key);
                temp.setPopular(check);
                listWordOther_VN.add(temp);
            }
            else{
                listWordOther_VN.get(checkExist(Key,2)).setPopular(check); 
            }
        }   
    }   
    // check exist of word in list
    public int checkExist(String Key, int Trans){
        //EN_VN
        if(Trans == 1){
            for(int i = 0; i< listWordOther.size(); i++)
                if(Key.equals(listWordOther.get(i).getKey())){
                    return i;
                }
        }
        //VN_EN
        if(Trans == 2){
           for(int i = 0; i< listWordOther_VN.size(); i++)
               if(Key.equals(listWordOther_VN.get(i).getKey())){
                   return i;
                }
        }
        return -1;
    }
    
    // SaveFile List Word Love 
    public void SaveFileListWordLove(){
        ObjectOutputStream out = null;
        try{
            // save word love EN
           out = new ObjectOutputStream(new FileOutputStream("data\\wordloveEN.txt"));
           out.writeObject(listWordOther.size());
           for(int i = 0; i < listWordOther.size(); i++)
           {
               out.writeObject(listWordOther.get(i));
           }
           out.close();
           // save word love VN
           out = new ObjectOutputStream(new FileOutputStream("data\\wordloveVN.txt"));
           out.writeObject(listWordOther_VN.size());
           for(int i = 0; i < listWordOther_VN.size(); i++)
           {
               out.writeObject(listWordOther_VN.get(i));
           }
        }
        catch(IOException e) {
            System.out.println("SaveFile error!!!" + e.toString());
        }
         finally{   
            try{
            if(out != null)
            {
                out.close();
            }
            }
            catch(Exception e)
            {
            }          
        }
    }
    
    // ReadFile wordlove
    public void ReadFileListWordLove(){
        ObjectInputStream in = null;
        listWordOther.clear();
        listWordOther_VN.clear();
        // READ FILE WORD LOVE EN
        try{
            in = new ObjectInputStream(new FileInputStream("data\\wordloveEN.txt"));

        }
        catch(IOException e){
            System.out.println("Notfound!!!");
        }
        
        try {
            int size = (int)in.readObject();
            for(int i = 0; i < size; i++ )
                listWordOther.add((WordOther)in.readObject());
            in.close();
        }
        catch(Exception e){
            System.out.println("ReadFile Error!!!");
        }
        
        //----------------------------------------------------------------------
        //READ FILE WORD LOVE VN
        try{
            in = new ObjectInputStream(new FileInputStream("data\\wordloveVN.txt"));
        }
        catch(IOException e){
        }
        
        try {
            int size = (int)in.readObject();
            for(int i = 0; i < size; i++ )
                listWordOther_VN.add((WordOther)in.readObject());
        }
        catch(Exception e){
            System.out.println("ReadFile Error!!!");
        }
        //----------------------------------------------------------------------
        finally
        {
            if(in != null)
            {
                try
                {
                    in.close();
                }
                catch(Exception e)
                {
                }
            }
        }
    }
    
    // SAVE FILE HISTORY SEARCH EN AND VN
    public void SaveHistory(){
        ObjectOutputStream out = null;
        try{
            // save word love EN
           out = new ObjectOutputStream(new FileOutputStream("data\\historyEN.txt"));
           out.writeObject(historyEN.size());
           for(int i = 0; i < historyEN.size(); i++)
           {
               out.writeObject(historyEN.get(i));
           }
           out.close();
           // save word love VN
           out = new ObjectOutputStream(new FileOutputStream("data\\historyVN.txt"));
           out.writeObject(historyVN.size());
           for(int i = 0; i < historyVN.size(); i++)
           {
               out.writeObject(historyVN.get(i));
           }
        }
        catch(IOException e) {
            System.out.println("SaveFile error!!!" + e.toString());
        }
         finally{   
            try{
            if(out != null)
            {
                out.close();
            }
            }
            catch(Exception e)
            {
            }          
        }
    }
    
    //READ FILE HISTORY EN - VN
    public void ReadFileHistory(){
        ObjectInputStream in = null;
        historyEN.clear();
        historyVN.clear();
        // READ FILE HISTORY EN
        try{
            in = new ObjectInputStream(new FileInputStream("data\\historyEN.txt"));

        }
        catch(IOException e){
            System.out.println("Notfound!!!");
        }
        
        try {
            int size = (int)in.readObject();
            for(int i = 0; i < size; i++ )
                historyEN.add((History)in.readObject());
            in.close();
        }
        catch(Exception e){
            System.out.println("ReadFile Error!!!");
        }
        
        //----------------------------------------------------------------------
        //READ FILE HISTORY VN
        try{
            in = new ObjectInputStream(new FileInputStream("data\\historyVN.txt"));
        }
        catch(IOException e){
        }
        
        try {
            int size = (int)in.readObject();
            for(int i = 0; i < size; i++ )
                historyVN.add((History)in.readObject());
        }
        catch(Exception e){
            System.out.println("ReadFile Error!!!");
        }
        //----------------------------------------------------------------------
        finally
        {
            if(in != null)
            {
                try
                {
                    in.close();
                }
                catch(Exception e)
                {
                }
            }
        }
    }
}
