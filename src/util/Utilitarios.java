
package util;

import bo.Item;
import java.util.ArrayList;

/**
 *
 * @author yuno
 */
public class Utilitarios {
    
    public static String[] parsingString(String value)
    {
      String[] arrayParts = null ;
      if(!value.equals(""))   
      {
          arrayParts = value.split("\\|");
      }
      
      return arrayParts;
    } 
    
    
    public static String[] parsingStringNodes(String value)
    {
      String[] arrayParts = null ;
      if(!value.equals(""))   
      {
          arrayParts = value.split("\\@");
      }
      
      return arrayParts;
    } 
    
    public static ArrayList<String> getUserAssociatedGroups(String[] listaGrupos)
    {
        String itemGrupo = "";
        ArrayList<String> listaItems =  new ArrayList<String>();
        String[] listaNodoItems      =  null;
        
        for (int i = 0 ; i< listaGrupos.length; i++){
                 itemGrupo = listaGrupos[i];
                 listaNodoItems = parsingStringNodes(itemGrupo);
                 
                 listaItems.add(listaNodoItems[1]);
        }
        
        return listaItems;
    }
    
    public static int countPlotLetters(String plot, char letter)
    {
	char someChar = letter;
	int count = 0;
	 
	for (int i = 0; i < plot.length(); i++) {
	    if (plot.charAt(i) == letter) {
	        count++;
	    }
	}
        return count;
    }
}
