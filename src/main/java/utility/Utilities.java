package utility;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Slot;

public class Utilities {
	private static final Logger logger = LoggerFactory.getLogger(Utilities.class);

	
	public static String formatMovieTitle(String movieInput){
		//logger.info("Entered");
		movieInput = movieInput.toLowerCase();
		String formattedMovieTitle;

		int lastWordPosStart = movieInput.lastIndexOf(" ") + 1; 
		int lastWordPosEnd = lastWordPosStart + movieInput.substring(lastWordPosStart).length();
		String movieNumberWord = movieInput.substring(lastWordPosStart);
		//logger.debug("movieNumberWord = " + movieNumberWord);
		
		if (movieNumberWord.equals(movieInput)) return movieInput; //if there is only 1 word, this will not allow the formatting to continue as there is no need.
		
		StringBuffer sBuffer = new StringBuffer(movieInput);
	  sBuffer.delete(lastWordPosStart-1, lastWordPosEnd);
	  formattedMovieTitle = sBuffer.toString();
	  //logger.debug("sBuffer = " + sBuffer.toString());
		
		if (movieNumberWord.equals("two")){
			formattedMovieTitle += " " + 2;
		} else if (movieNumberWord.equals("three")){
			formattedMovieTitle += " " + 3;
		}else if (movieNumberWord.equals("four")){
			formattedMovieTitle += " " + 4;
		}else if (movieNumberWord.equals("five")){
			formattedMovieTitle += " " + 5;
		}else {
			formattedMovieTitle = movieInput;
		}
		logger.debug("formattedMovieTitle = " + formattedMovieTitle);
		//logger.info("Exited");
		return formattedMovieTitle;
	}
	
	public static boolean isPastDate(String date){
		
		try {
			if (new SimpleDateFormat(Constants.TMDB_RESPONSE_DATE_FORMAT).parse(date).before(new Date())){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return true;
		} 
		return false;
	}


	public static int getDistance(String string0, String string1){
	
		//Levenshtine Distances
		
		string0 = string0.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
		string1 = string1.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
		
		//logger.debug("string0 = {}", string0);
		//logger.debug("string1 = {}", string1);

		
		int len0 = string0.length() + 1;                                                     
    int len1 = string1.length() + 1;                                                     
                                                                                    
    // the array of distances                                                       
    int[] cost = new int[len0];                                                     
    int[] newcost = new int[len0];                                                  
                                                                                    
    // initial cost of skipping prefix in String s0                                 
    for (int i = 0; i < len0; i++) cost[i] = i;                                     
                                                                                    
    // dynamically computing the array of distances                                  
                                                                                    
    // transformation cost for each letter in s1                                    
    for (int j = 1; j < len1; j++) {                                                
        // initial cost of skipping prefix in String s1                             
        newcost[0] = j;                                                             
                                                                                    
        // transformation cost for each letter in s0                                
        for(int i = 1; i < len0; i++) {                                             
            // matching current letters in both strings                             
            int match = (string0.charAt(i - 1) == string1.charAt(j - 1)) ? 0 : 1;             
                                                                                    
            // computing cost for each transformation                               
            int cost_replace = cost[i - 1] + match;                                 
            int cost_insert  = cost[i] + 1;                                         
            int cost_delete  = newcost[i - 1] + 1;                                  
                                                                                    
            // keep minimum cost                                                    
            newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
        }                                                                           
                                                                                    
        // swap cost/newcost arrays                                                 
        int[] swap = cost; cost = newcost; newcost = swap;                          
    }                                                    
		
		return cost[len0 - 1];
	}
	
	public static HashMap<String, String> retriveSlotValues(Map<String, Slot> slots){
		HashMap<String, String> slotValueMap = new HashMap<String, String>();
		
		for(HashMap.Entry<String, Slot> entry : slots.entrySet()){ 
			Slot s = entry.getValue();
			slotValueMap.put(entry.getKey(), s.getValue());
		}

		//logger.debug("slotValueMap: {}", slotValueMap);
		return slotValueMap;
	
	}
	
	public static String convertListToString(List<String> list){
		String listText = list.toString();
		listText = listText.replace("[", "").replace("]", "");
		int index = listText.lastIndexOf(",");
		if (index >=0) listText = new StringBuilder(listText).replace(index, index+1, ", and").toString();
		return listText;
	}
	
	public static boolean doesContainNumber(String input){
		 List<String> allowedStrings = Arrays.asList
				    (
				    "oh","one","two","three","four","five","six","seven",
				    "eight","nine","ten","eleven","twelve","thirteen","fourteen",
				    "fifteen","sixteen","seventeen","eighteen","nineteen","twenty",
				    "thirty","forty","fifty","sixty","seventy","eighty","ninety",
				    "hundred","thousand","million","billion","trillion"
				    );
		 String[] splittedParts = input.trim().split("\\s+");
		 
		 for(String str : splittedParts) {
				//System.out.println("str: " + str);
		    if(allowedStrings.contains(str)) return true;
		 }
		 return false;
	}
	
	public static String reformatTitle(String input){
		//boolean isValidInput = true;
    long result = 0;
    long tempResult = 0;
    String finalResult = "";
    
    List<String> allowedStrings = Arrays.asList
    (
    "oh","one","two","three","four","five","six","seven",
    "eight","nine","ten","eleven","twelve","thirteen","fourteen",
    "fifteen","sixteen","seventeen","eighteen","nineteen","twenty",
    "thirty","forty","fifty","sixty","seventy","eighty","ninety",
    "hundred","thousand","million","billion","trillion"
    );

    //String input="One hundred two thousand and thirty four";

    //System.out.println(allowedStrings.indexOf(input));
    
    if(input != null && input.length()> 0)
    {
        input = input.replaceAll("-", " ");
        input = input.toLowerCase().replaceAll(" and", " ");
        String[] splittedParts = input.trim().split("\\s+");
        //System.out.println("splittedParts: " + Arrays.toString(splittedParts));

        for(String str : splittedParts) {
        		//System.out.println("str: " + str);
            if(!allowedStrings.contains(str)) {
                if (tempResult !=0 && result !=0){
                	tempResult += result;
                	result = 0;
                }
            		if (tempResult != 0) {
                	finalResult += tempResult + " ";
                	tempResult = 0;
                } else if(result != 0) {
                	finalResult += result + " ";
                	result = 0;
                }
                finalResult += str + " ";
                

            } else {
	            if(str.equalsIgnoreCase("zero")) {
	                result += 0;
	            }
	            else if(str.equalsIgnoreCase("one")) {
	                result += 1;
	            }
	            else if(str.equalsIgnoreCase("two")) {
	                result += 2;
	            }
	            else if(str.equalsIgnoreCase("three")) {
	                result += 3;
	            }
	            else if(str.equalsIgnoreCase("four")) {
	                result += 4;
	            }
	            else if(str.equalsIgnoreCase("five")) {
	                result += 5;
	            }
	            else if(str.equalsIgnoreCase("six")) {
	                result += 6;
	            }
	            else if(str.equalsIgnoreCase("seven")) {
	                result += 7;
	            }
	            else if(str.equalsIgnoreCase("eight")) {
	                result += 8;
	            }
	            else if(str.equalsIgnoreCase("nine")) {
	                result += 9;
	            }
	            else if(str.equalsIgnoreCase("ten")) {
	                result += 10;
	            }
	            else if(str.equalsIgnoreCase("eleven")) {
	                result += 11;
	            }
	            else if(str.equalsIgnoreCase("twelve")) {
	                result += 12;
	            }
	            else if(str.equalsIgnoreCase("thirteen")) {
	                result += 13;
	            }
	            else if(str.equalsIgnoreCase("fourteen")) {
	                result += 14;
	            }
	            else if(str.equalsIgnoreCase("fifteen")) {
	                result += 15;
	            }
	            else if(str.equalsIgnoreCase("sixteen")) {
	                result += 16;
	            }
	            else if(str.equalsIgnoreCase("seventeen")) {
	                result += 17;
	            }
	            else if(str.equalsIgnoreCase("eighteen")) {
	                result += 18;
	            }
	            else if(str.equalsIgnoreCase("nineteen")) {
	                result += 19;
	            }
	            else if(str.equalsIgnoreCase("twenty")) {
	                result += 20;
	            }
	            else if(str.equalsIgnoreCase("thirty")) {
	                result += 30;
	            }
	            else if(str.equalsIgnoreCase("forty")) {
	                result += 40;
	            }
	            else if(str.equalsIgnoreCase("fifty")) {
	                result += 50;
	            }
	            else if(str.equalsIgnoreCase("sixty")) {
	                result += 60;
	            }
	            else if(str.equalsIgnoreCase("seventy")) {
	                result += 70;
	            }
	            else if(str.equalsIgnoreCase("eighty")) {
	                result += 80;
	            }
	            else if(str.equalsIgnoreCase("ninety")) {
	                result += 90;
	            }
	            else if(str.equalsIgnoreCase("hundred")) {
	                result *= 100;
	            }
	            else if(str.equalsIgnoreCase("thousand")) {
	                result *= 1000;
	                tempResult += result;
	                //finalResult += result;
	                result=0;
	            }
	            else if(str.equalsIgnoreCase("million")) {
	                result *= 1000000;
	                tempResult += result;
	                //finalResult += result;
	                result=0;
	            }
	            else if(str.equalsIgnoreCase("billion")) {
	                result *= 1000000000;
	                tempResult += result;
	                //finalResult += result;	               
	                result=0;
	            }
	            else if(str.equalsIgnoreCase("trillion")) {
	                result *= 1000000000000L;
	                tempResult += result;
	                //finalResult += result;
	                result=0;
	            }
            	
            }
        }

    }
    if (tempResult !=0 && result !=0){
    	tempResult += result;
    	result = 0;
    }
		if (tempResult != 0) {
    	finalResult += tempResult + " ";
    	tempResult = 0;
    } else if(result != 0) {
    	finalResult += result + " ";
    	result = 0;
    }
    return finalResult;
	}
	
}
