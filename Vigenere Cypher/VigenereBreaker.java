package Cypher;

import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    CaesarCipher[] ciphers;
    
    public String[] sliceString(String message,  int totalSlices) {
        //REPLACE WITH YOUR CODE
        String[] slice = new String[totalSlices];
        char C;
        for (int i = 0; i < totalSlices; i++){
            slice[i] = "";
        }
        for (int i = 0; i < message.length(); i++){
            C = Character.toLowerCase(message.charAt(i));
            slice[i%totalSlices] = slice[i%totalSlices]+C;
        }
        return slice;
    }

    public int getKey(String msg, char MC) {
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        Collection<Integer> val;
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        for (int i = 0; i<26; i++){
            hm.put(String.valueOf(alpha.charAt(i)), 0);
        }
        for (int i = 0; i < msg.length(); i++){
            String test = String.valueOf(msg.charAt(i)).toLowerCase();
            if (alpha.contains(test)){
                hm.put(test,hm.get(test)+1);
            }
        }
        val = new ArrayList<Integer>(hm.values());
        int count = 0;
        int max = 0;
        int maxI = 0;
        for (Integer i : val) {
            if (i > max){
                maxI=count;
                max = i;
            }
            count++;
            //System.out.println(alpha.toUpperCase().charAt(count-1) + "'s: " + i.toString());
        }
        int key = alpha.indexOf(String.valueOf(alpha.charAt(maxI)).toLowerCase()) - alpha.indexOf(String.valueOf(MC));
        if (key < 0){
            key = 26+key;
        }
        return key;
        //WRITE YOUR CODE HERE
        //return key;
    }

    public String Breaker(String msg, char k){
       String alphabet = "abcdefghijklmnopqrstuvwxyz";
       int key =  alphabet.indexOf(k);
       if (key != -1){
           String shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0,key);
           String newStr = "";
           
           for (int i = 0; i < msg.length(); i++){
               if (alphabet.indexOf(msg.charAt(i)) != -1){
                   newStr = newStr + (alphabet.charAt(shiftedAlphabet.indexOf(msg.charAt(i))));
               }
               else{
                   newStr = newStr + (msg.charAt(i));
               }
           }
           return newStr;
        }
       return "";
   }
   
   public int WordCount(String msg, String dict){
       int words = 0;
       for (String word: msg.split("\\W")){
           if (checker(word, dict)){
               words++;
           }
       }
       return words;
   }
   
   public void breakVigenere () {
       //WRITE YOUR CODE HERE
       String alpha = "abcdefghijklmnopqrstuvwxyz";
       String[] sliced;
       FileResource fr = new FileResource();
       FileResource dictionary = new FileResource();
       ArrayList keys = new ArrayList();
       String sent = "";
       int val;
       //String msg = "Hhdiu LVXNEW uxh WKWVCEW, krg k wbbsqa si Mmwcjiqm";
       String msg = fr.asString();
       String dict = dictionary.asString().toLowerCase();
       
       //System.out.println(dict);
       //String test = "";
       //keys = getKeys2(word(msg), dict);
       sent = sentence(msg);
       System.out.println(sent+"\n\n");
       //keys = checkKeys(keys, sent/*.get(1).toString()*/, dict);
       //val = getKey(msg, 'e');
       //System.out.println(val);
       String key;
       int realWords;
       for (int i = 1; i < 10; i++){
           realWords = 0;
           sliced = sliceString(msg, i);
           key = "";
           for (int j = 0; j < i; j++){
               val = getKey(sliced[j], 'e');
               key = key + String.valueOf(alpha.charAt(val));
           }
           for (int k = 0; k < i; k++){
               sliced[k] = Breaker(sliced[k], key.charAt(k));
           }
           int counter = -1;
           String tst2 = "";
           for( int l=0; l<sent.length(); l++ ) {
               if( l%sliced.length == 0 ) {
                   counter++;
               }
               tst2 = tst2 + (sliced[l%sliced.length].charAt(counter));
           }
           realWords = WordCount(tst2, dict);
           System.out.println(i-1);
           System.out.println(key);
           System.out.println(realWords + " real words");
           System.out.println(tst2+"\n");
       }
       /**
       sliced = sliceString(msg, 4);
       String key = "duke";
       //System.out.println(msg);
       for (int i = 0; i < 4; i++){
           sliced[i] = Breaker(sliced[i], key.charAt(i));
       }
       int count = -1;
       for (int i = 0; i < msg.length(); i++){
           if (i%sliced.length == 0){
               count++;
           }
           //System.out.print(sliced[i%sliced.length].charAt(count));
       }
       //System.out.println();
       **/
    }
    public ArrayList checkKeys (ArrayList keys, String msg, String dict){
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        String key = "";
        String[] sliced;
        ArrayList keyL = new ArrayList();
        String tst = msg;
        //sliced = sliceString(tst, 4);
        //System.out.println(tst);
        //System.out.println(Arrays.toString(sliced));
        int tester = 0;
        for (int j = 0; j < keys.size(); j++){
            tst = msg;
            sliced = sliceString(tst, keys.get(j).toString().length());
            //System.out.println(tst);
            //System.out.println(Arrays.toString(sliced));
            for (int i = 0; i < keys.get(j).toString().length(); i++){
                sliced[i] = Breaker(sliced[i], keys.get(j).toString().charAt(i));
            }
            int count = -1;
            for (int i = 0; i < tst.length(); i++){
                if (i%sliced.length == 0){
                    count++;
                }
                //System.out.print(sliced[i%sliced.length].charAt(count));
            }
            int counter = -1;
            String tst2 = "";
            for( int i=0; i<tst.length(); i++ ) {
                if( i%sliced.length == 0 ) {
                    counter++;
                }
                //System.out.println(tst2);
                //System.out.println(Arrays.toString(sliced));
                //System.out.println(counter);
                tst2 = tst2 + (sliced[i%sliced.length].charAt(counter));
                //System.out.print(tst2);
            }
            //System.out.println();
            //System.out.println(keys.get(j).toString()+ " - " + tst2);
            tst2 = tst2.substring(tst2.indexOf(" ")+1, tst2.length());
            //System.out.println(word(tst2));
            //System.out.println(checker(word(tst2), dict));
            if (checker(word(tst2), dict)){
                //System.out.println(keys.get(j).toString()+ " - " + tst2 );
                //keyL.add(test);
                keyL.add(keys.get(j).toString());
            }        
        }
        System.out.println("Remaining keys: " + keyL.size());
        System.out.println(keyL);
        return keyL;
    }
    
    public ArrayList getKeys2(String msg, String dict){
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        String key = "";
        String[] sliced;
        ArrayList keyL = new ArrayList();
        System.out.println("Possible keys: " + 26*26);
        for (int c = 0; c < alpha.length(); c++){
            for (int d = 0; d < alpha.length(); d++){
                key=""+alpha.charAt(c)+alpha.charAt(d);
                //System.out.println(msg);
                sliced = sliceString(msg, 2);
                for (int i = 0; i < 2; i++){
                    sliced[i] = Breaker(sliced[i], key.charAt(i));
                }
                String tst = "";
                int count = -1;
                for (int i = 0; i < msg.length(); i++){
                    if (i%sliced.length == 0){
                        count++;
                    }
                    //System.out.print(sliced[i%sliced.length].charAt(count));
                    tst = tst + (sliced[i%sliced.length].charAt(count));
                }
                if (checker(tst, dict)){
                    //System.out.println(key+ " - " + tst );
                    keyL.add(key);
                }
                //status(key);
            }
        }
        System.out.println("Remaining keys: " + keyL.size());
        return keyL;
    }
    
    public ArrayList getKeys3(String msg, String dict){
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        String key = "";
        String[] sliced;
        ArrayList keyL = new ArrayList();
        System.out.println("Possible keys: " + 26*26*26);
        for (int b = 0; b < alpha.length(); b++){
            for (int c = 0; c < alpha.length(); c++){
                for (int d = 0; d < alpha.length(); d++){
                    key=""+alpha.charAt(b)+alpha.charAt(c)+alpha.charAt(d);
                    //System.out.println(msg);
                    sliced = sliceString(msg, 3);
                    for (int i = 0; i < 3; i++){
                        sliced[i] = Breaker(sliced[i], key.charAt(i));
                    }
                    String tst = "";
                    int count = -1;
                    for (int i = 0; i < msg.length(); i++){
                        if (i%sliced.length == 0){
                            count++;
                        }
                        //System.out.print(sliced[i%sliced.length].charAt(count));
                        tst = tst + (sliced[i%sliced.length].charAt(count));
                    }
                    if (checker(tst, dict)){
                        //System.out.println(key+ " - " + tst );
                        keyL.add(key);
                    }
                    //status(key);
                    }
                }
            }
        System.out.println("Remaining keys: " + keyL.size());
        return keyL;
    }
    
    public ArrayList getKeys4(String msg, String dict){
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        String key = "";
        String[] sliced;
        ArrayList keyL = new ArrayList();
        System.out.println("Possible keys: " + 26*26*26*26);
        for (int a = 0; a < alpha.length(); a++){
            for (int b = 0; b < alpha.length(); b++){
                for (int c = 0; c < alpha.length(); c++){
                    for (int d = 0; d < alpha.length(); d++){
                        key=""+alpha.charAt(a)+alpha.charAt(b)+alpha.charAt(c)+alpha.charAt(d);
                        //System.out.println(msg);
                        sliced = sliceString(msg, 4);
                        for (int i = 0; i < 4; i++){
                            sliced[i] = Breaker(sliced[i], key.charAt(i));
                        }
                        String tst = "";
                        int count = -1;
                        for (int i = 0; i < msg.length(); i++){
                            if (i%sliced.length == 0){
                                count++;
                            }
                            //System.out.print(sliced[i%sliced.length].charAt(count));
                            tst = tst + (sliced[i%sliced.length].charAt(count));
                        }
                        if (checker(tst, dict)){
                            //System.out.println(key+ " - " + tst );
                            keyL.add(key);
                        }
                        status(key);
                    }
                }
            }
        }
        System.out.println("Remaining keys: " + keyL.size());
        return keyL;
    }
    
    public ArrayList getKeys5(String msg, String dict){
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        String key = "";
        String[] sliced;
        ArrayList keyL = new ArrayList();
        System.out.println("Possible keys: " + 26*26*26*26*26);
        for (int a = 0; a < alpha.length(); a++){
            for (int b = 0; b < alpha.length(); b++){
                for (int c = 0; c < alpha.length(); c++){
                    for (int d = 0; d < alpha.length(); d++){
                        for (int e = 0; e < alpha.length(); e++){
                            key=""+alpha.charAt(a)+alpha.charAt(b)+alpha.charAt(c)+alpha.charAt(d)+alpha.charAt(e);
                            //System.out.println(msg);
                            sliced = sliceString(msg, 5);
                            for (int i = 0; i < 5; i++){
                                sliced[i] = Breaker(sliced[i], key.charAt(i));
                            }
                            String tst = "";
                            int count = -1;
                            for (int i = 0; i < msg.length(); i++){
                                if (i%sliced.length == 0){
                                    count++;
                                }
                                //System.out.print(sliced[i%sliced.length].charAt(count));
                                tst = tst + (sliced[i%sliced.length].charAt(count));
                            }
                            if (checker(tst, dict)){
                                //System.out.println(key+ " - " + tst );
                                keyL.add(key);
                            }
                            //status(key);
                        }
                    }
                }
            }
        }
        System.out.println("Remaining keys: " + keyL.size());
        return keyL;
    }
    
    public boolean checker(String msg, String dict){
        
        if (dict.contains("\n"+msg+"\n")){
            return true;
        }
        else{
            return false;
        }
        //System.out.println(msg);
    }
    
    public void status(String key){
        if (key.contains("bbbb")){
            System.out.println("5% complete");
        }
        if (key.contains("czzz")){
            System.out.println("10% complete");
        }
        if (key.contains("eeee")){
            System.out.println("15% complete");
        }
        if (key.contains("faaa")){
            System.out.println("20% complete");
        }
        if (key.contains("fzzz")){
            System.out.println("25% complete");
        }
        if (key.contains("mzzz")){
            System.out.println("50% complete");
        }
        if (key.contains("rzzz")){
            System.out.println("75% complete");
        }
        if (key.contains("wzzz")){
            System.out.println("90% complete");
        }
        if (key.contains("xxxx")){
            System.out.println("95% complete");
        }
        if (key.contains("yzzz")){
            System.out.println("99% complete");
        }
    }
    
    public String word(String msg){
        return msg.substring(0,msg.indexOf(" "));
    }
    
    public String sentence(String msg){
        /**
        String sent = msg.substring(0,msg.indexOf("\n"));
        ArrayList X = new ArrayList();
        int counter = 0;
        for( int i=0; i<sent.length(); i++ ) {
            if( sent.charAt(i) == ' ' ) {
                counter++;
            } 
        }
        
        for( int i=0; i<counter-1; i++ ) {
            if (sent.indexOf(" ") != -1){ 
                X.add(word(sent));
            }
            sent = sent.substring(sent.indexOf(" ")+1,sent.length());

        }
        X.add(word(sent));
        //System.out.println(X);
        **/
        return msg.substring(0,msg.indexOf("\n"));
    }
    
    public void test(){
        FileResource dictionary = new FileResource();
        String dict = dictionary.asString().toLowerCase();;
        if (dict.contains("\n"+"brutus"+"\n")){
            System.out.println(true);
        }
        else{
            System.out.println(false);
        }
    } 
        /**
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        String key = "";
        for (int a = 0; a < alpha.length(); a++){
            for (int b = 0; b < alpha.length(); b++){
                for (int c = 0; c < alpha.length(); c++){
                    for (int d = 0; d < alpha.length(); d++){
                        key=""+alpha.charAt(a)+alpha.charAt(b)+alpha.charAt(c)+alpha.charAt(d);
                        //System.out.println(msg);
                    }
                }
            }
        }
    }
    **/
    
}
