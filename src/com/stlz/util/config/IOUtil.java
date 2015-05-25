package com.stlz.util.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {

	 /** 
     * 
     * 读取txt文件 . 
     * 
     */ 
    public static List<String> readTxtFile(String dirs){ 
        
    	List<String> lines=new ArrayList<String>();
    	String line;
        FileReader fileread;
        BufferedReader bufread;
        try { 
            fileread = new FileReader(dirs); 
            bufread = new BufferedReader(fileread);
            
            try { 
                while ((line = bufread.readLine()) != null) { 
                	lines.add(line); 
                } 
            } catch (IOException e) { 
                // TODO Auto-generated catch block 
                e.printStackTrace(); 
            }  finally {
                if(bufread!=null){
            	   try {
					    bufread.close();
				        } catch (IOException e) {
					      // TODO Auto-generated catch block
					      e.printStackTrace();
				}
            }

       }


        } catch (FileNotFoundException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 

//        System.out.println("文件内容是 :"+ "/r/n" + readStr); 
        return lines; 
    } 
    
    /**
     * 写txt文件 . 
     * 
     */ 
    public static void writeTxtFile(List<String> lines,String dirs){ 
    	 
      try{
   
    	   FileWriter fw=new FileWriter(dirs);
    	   BufferedWriter bw=new BufferedWriter(fw); 
    	   for(String line:lines){   		 
    	          bw.write(line); 
    	          bw.newLine();//断行    
    	          bw.flush();//
    	   }
    	   fw.close();
       }catch(Exception e){}

    } 

}
