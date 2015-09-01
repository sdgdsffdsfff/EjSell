package com.ejsell.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Test;

import com.opencsv.CSVReader;

public class readandwrite {
	
	@Test
    public void test1() { 
        File inFile = new File("C:\\csvtest\\sell_return.csv"); 
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(inFile));
            CSVReader creader = new CSVReader(reader, ',');
            List<String[]> listData=creader.readAll();
            
            for(String[] strs:listData){
            	System.out.println(strs[11]);
            }
            
            creader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}