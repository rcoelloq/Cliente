/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author yuno
 */
public class Test {
    public static void main(String arg[])
    {
        String someString = "1@ricardo|5@sofia|6@sebas|7@ss";
	char someChar = '@';
	int count = 0;
	 
	for (int i = 0; i < someString.length(); i++) {
	    if (someString.charAt(i) == someChar) {
	        count++;
	    }
	}
        
        System.out.println(count);
        
        String text = "MSGilobato@canales@aaaa";
        System.out.println(text);
    
    }
    
    
    
}
