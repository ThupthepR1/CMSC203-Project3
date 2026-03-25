/* 
 * Class: CMSC203  
 * Instructor: Khandan Vahabzadeh Monshi
 * Description: Create a encryption and decryption methods 
 * Due: 03/25/2026 
 * Platform/compiler: EclipseIDE Java 2025-12
 * I pledge that I have completed the programming  
 * assignment independently. I have not copied the code  
 * from a student or any source. I have not given my code  
 * to any student. 
   Print your Name here: Thupthep Ruthirakanok 
*/ 


package Project3;
import  java.util.ArrayList;
public class CryptoManager {
	private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // Use 64-character matrix (8X8) for Playfair cipher  
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_ ";
    private static final char spacePlaceholder = ' ';

    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Vigenere Cipher is a method of encrypting alphabetic text 
	 * based on the letters of a keyword. It works as below:
	 * 		Choose a keyword (e.g., KEY).
	 * 		Repeat the keyword to match the length of the plaintext.
	 * 		Each letter in the plaintext is shifted by the position of the 
	 * 		corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
	 */   

    public static String vigenereEncryption(String plainText, String key) {
    	 //String noSpaces = plainText.replaceAll("\\s+", "");
         String upperText = plainText.toUpperCase();
         String upperKey  = key.toUpperCase();
         char[] encrypChar = new char[upperText.length()];
         
         if(isStringInBounds(upperText) == false) {
        	 return "The selected string is not in bounds, Try again.";
         }
         
         int[] indexText = new int[plainText.length()];
         for(int i =0;i <plainText.length(); i++ ) {
        	 for(int j = 0; j < ALPHABET64.length(); j++ ) {
        		 if(upperText.charAt(i) == ALPHABET64.charAt(j)) {
        			 indexText[i] = j;
        		 }
        	 }
         }
         
         int[] indexKey = new int[key.length()];
         for(int i =0;i < key.length(); i++ ) {
        	 for(int j = 0; j < ALPHABET64.length(); j++ ) {
        		 if(upperKey.charAt(i) == ALPHABET64.charAt(j)) {
        			 indexKey[i] = j;
        		 }
        	 }
         }
         
         
         int j = 0;
         int[] position = new int[plainText.length()];
         for(int i = 0; i < plainText.length(); i++) {
        	 if(j == key.length()) {
        		 j=0;
        	 }
        	 position[i] = (indexText[i] + indexKey[j]) % ALPHABET64.length();
        	 j++;
        	 
        	 encrypChar[i] = ALPHABET64.charAt(position[i]);
         }
         
         String encrypString = new String(encrypChar);
         return encrypString;
    }

    // Vigenere Decryption
    public static String vigenereDecryption(String encryptedText, String key) {
    	String upperText = encryptedText.toUpperCase();
        String upperKey  = key.toUpperCase();
        char[] decrypChar = new char[encryptedText.length()];
        
        if(isStringInBounds(upperText) == false) {
       	 	return "The selected string is not in bounds, Try again.";
        }
        
        int[] indexText = new int[encryptedText.length()];
        for(int i =0;i <encryptedText.length(); i++ ) {
       	 	for(int j = 0; j < ALPHABET64.length(); j++ ) {
       		 	if(upperText.charAt(i) == ALPHABET64.charAt(j)) {
       			 indexText[i] = j;
       		 	}
       	 	}
        }
        
        int[] indexKey = new int[key.length()];
        for(int i =0;i < key.length(); i++ ) {
       	 	for(int j = 0; j < ALPHABET64.length(); j++ ) {
       	 		if(upperKey.charAt(i) == ALPHABET64.charAt(j)) {
       			 indexKey[i] = j;
       	 		}
       	 	}
        }
        
        int j = 0;
        int[] position = new int[encryptedText.length()];
        for(int i = 0; i < encryptedText.length(); i++) {
       	 if(j == key.length()) {
       		 j=0;
       	 }
       	 position[i] = (indexText[i] - indexKey[j] + ALPHABET64.length()) % ALPHABET64.length();
       	 j++;
       	 
       	decrypChar[i] = ALPHABET64.charAt(position[i]);
        }
        
        String decrypString = new String(decrypChar);
        return decrypString;
        
    }


	/**
	 * Playfair Cipher encrypts two letters at a time instead of just one.
	 * It works as follows:
	 * A matrix (8X8 in our case) is built using a keyword
	 * Plaintext is split into letter pairs (e.g., ME ET YO UR).
	 * Encryption rules depend on the positions of the letters in the matrix:
	 *     Same row: replace each letter with the one to its right.
	 *     Same column: replace each with the one below.
	 *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
	 */    

    public static String playfairEncryption(String plainText, String key) {
         String upperKey = key.toUpperCase();
         String upperText = plainText.toUpperCase();
         
        if(isStringInBounds(upperKey) == false) {
        	return "The selected string is not in bounds, Try again.";
        }
         
        ArrayList<Character> ALPHABETNOT64 = new ArrayList<>();
        for (int i = 0; i < upperKey.length(); i++) {        	
        	char c = upperKey.charAt(i);
        	if (!ALPHABETNOT64.contains(c)) {
        		ALPHABETNOT64.add(c);
         	}
        }
         
        int insertIndex = 0;
        for (int i = 0; i < ALPHABET64.length(); i++) {
        	char c = ALPHABET64.charAt(i);
        	if (!ALPHABETNOT64.contains(c)) {
        		ALPHABETNOT64.add(c);
        	}
        }
        
        if (ALPHABETNOT64.size() != 64) {
            return "Error: Key + alphabet does not produce 64 characters!";
        }
            
         char[][] keyMatrix = new char[8][8];
         int q =0;
         for (int i = 0; i < 8; i++) {
        	 for (int j = 0; j < 8; j++) {
        		 keyMatrix[i][j] = ALPHABETNOT64.get(q);
        		 q++;
        	 }
         }
         
         boolean extra = false;
         if (upperText.length() % 2 != 0) {
        	    upperText += 'X'; 
        	    extra = true;
        	}
         
         int size = upperText.length() /2;
         char[][] pairChar = new char[size][2];
         int p =0;
         for (int i = 0; i < size; i++) {
        	pairChar[i][0] = upperText.charAt(p++);
        	pairChar[i][1] = upperText.charAt(p++);
         }
         
         
         char[] rowCase = rowCaseCheck(keyMatrix, pairChar);
         char[] columnCase = columnCaseCheck(keyMatrix, pairChar);
         char[] encrypChar = new char[upperText.length()];
         int index =0;
         for (int i = 0; i < pairChar.length; i++) {
        	 char target1 =pairChar[i][0], 
        		  target2 =pairChar[i][1];
        	 
        	 int r1 = 0, r2 =0; 
        	 int c1 = 0, c2 = 0;
        	 
        	for(int r = 0; r < keyMatrix.length; r++) {
        		for(int c = 0; c < keyMatrix[r].length; c++) {
            		if(target1 == keyMatrix[r][c]) {
            			r1 = r;
            			c1 = c;
            		}
            		if(target2 == keyMatrix[r][c]) {
            			r2 = r;
            			c2 = c;
            		}
            	}
	 
	 
        	}
        	//same row
        	if(rowCase[i] == 'y') {
        		c1 = (c1+1) %8;
        		c2 = (c2+1) %8;
        	}
        	//same column
        	else if(columnCase[i] == 'y') {
        		r1 = (r1+1) %8;
        		r2 = (r2+1) %8;
        	}
        	//rectangle 
        	else {
        		int cTemp = c1;
        		c1 = c2;
        		c2 = cTemp;
        	}
        	 
        	encrypChar[index++] = keyMatrix[r1][c1];
 			encrypChar[index++] = keyMatrix[r2][c2];	
        	 
         }
        

         String  encrypString = new String(encrypChar);
         return encrypString;
         
    }
    
    
    

    // Vigenere Decryption
    public static String playfairDecryption(String encryptedText, String key) {
    	String upperKey = key.toUpperCase();
        String upperText = encryptedText.toUpperCase();
        
       if(isStringInBounds(upperKey) == false) {
       	return "The selected string is not in bounds, Try again.";
       }
        
       ArrayList<Character> ALPHABETNOT64 = new ArrayList<>();
       for (int i = 0; i < upperKey.length(); i++) {        	
       	char c = upperKey.charAt(i);
       	if (!ALPHABETNOT64.contains(c)) {
       		ALPHABETNOT64.add(c);
        	}
       }
        
       int insertIndex = 0;
       for (int i = 0; i < ALPHABET64.length(); i++) {
       	char c = ALPHABET64.charAt(i);
       	if (!ALPHABETNOT64.contains(c)) {
       		ALPHABETNOT64.add(c);
       	}
       	
       }

        char[][] keyMatrix = new char[8][8];
        int q =0;
        for (int i = 0; i < 8; i++) {
       	 for (int j = 0; j < 8; j++) {
       		 keyMatrix[i][j] = ALPHABETNOT64.get(q);
       		 q++;
       	 }
        }
        
        int size = (upperText.length() /2) + (upperText.length() %2);
        char[][] pairChar = new char[size][2];
        int p =0;
        for (int i = 0; i < size; i++) {
       	 	pairChar[i][0] = upperText.charAt(p++);
       	 	pairChar[i][1] = upperText.charAt(p++);
       	 	
        }      
        
        char[] rowCase = rowCaseCheck(keyMatrix, pairChar);
        char[] columnCase = columnCaseCheck(keyMatrix, pairChar);
        char[] decrypChar = new char[encryptedText.length()];
        int index =0;
        for (int i = 0; i < pairChar.length; i++) {
       	 	char target1 =pairChar[i][0], 
       	 		 target2 =pairChar[i][1];
       	 	
       	 	int r1 = 0, r2 =0; 
       	 	int c1 = 0, c2 = 0;
       	 
       	 	for(int r = 0; r < keyMatrix.length; r++) {
       	 		for(int c = 0; c < keyMatrix[r].length; c++) {
       	 			if(target1 == keyMatrix[r][c]) {
           			r1 = r;
           			c1 = c;
       	 			}
       	 			if(target2 == keyMatrix[r][c]) {
           			r2 = r;
           			c2 = c;
       	 			}
       	 		}
       	 	}
       	 	
       	 	//same row
       	 	if(r1 == r2) {
       	 		c1 = (c1+7) %8;
       	 		c2 = (c2+7) %8;
       	 	}
       	 	//same column
       	 	else if(c1 == c2) {
       			r1 = (r1+7) %8;
       			r2 = (r2+7) %8;
       	 	}
       	 	//rectangle
       	 	else {
       	 		int cTemp = c1;
       	 		c1 = c2;
       	 		c2 = cTemp;
       	 	}
       	 
       	 	decrypChar[index++] = keyMatrix[r1][c1];
       	 	decrypChar[index++] = keyMatrix[r2][c2];	
       	 
        	}
        
        	String decrypString = new String(decrypChar);
        	if (decrypString.endsWith("X")) {
        		decrypString = decrypString.substring(0, decrypString.length() - 1);
        	}
        	return decrypString;
    }

    
    public static char[] rowCaseCheck(char[][] keyMatrix, char[][] pairChar) {
    	
    	char[] cases = new char[pairChar.length];
    	for (int i = 0; i < pairChar.length; i++) {
    		char target1 =  pairChar[i][0], target2 =  pairChar[i][1];
    		boolean sameRow = false;
    		
    		for (int r = 0; r < keyMatrix.length; r++) {
	    		boolean found1 = false, found2 = false;
	       	 		for (int c = 0; c < keyMatrix[r].length; c++) {
	       	 			if(keyMatrix[r][c] == target1) {
	       	 				found1 = true;
	       	 			}  	 			
	       	 			if(keyMatrix[r][c] == target2) {
	       	 				found2 = true;
	       	 			}
	       	 		}
	       	 		
	       	 		if(found1 && found2) {
	       	 			sameRow = true;
	       	 			break;
	       	 		}
    		}
    		
    		cases[i] = sameRow? 'y' : 'n';
        }
    	
    	return cases;
    }
    
   public static char[] columnCaseCheck(char[][] keyMatrix, char[][] pairChar) {
   	
   		char[] cases = new char[pairChar.length];
   		for (int i = 0; i < pairChar.length; i++) {
	   		char target1 =  pairChar[i][0], target2 =  pairChar[i][1];
	   		boolean sameColumn = false;
	   		
	   		for (int c = 0; c < keyMatrix[0].length; c++) {
		    		boolean found1 = false, found2 = false;
		       	 		for (int r = 0; r < keyMatrix.length; r++) {
		       	 			if(keyMatrix[r][c] == target1) {
		       	 				found1 = true;
		       	 			}  	 			
		       	 			if(keyMatrix[r][c] == target2) {
		       	 				found2 = true;
		       	 			}
		       	 		}
		       	 		
		       	 		if(found1 && found2) {
		       	 			sameColumn = true;
		       	 			break;
		       	 		}
	   		}
	   		
	   		cases[i] = sameColumn? 'y' : 'n';
	      }
	   	
   	return cases;
   }
    
    /**
     * Caesar Cipher is a simple substitution cipher that replaces each letter in a message 
     * with a letter some fixed number of positions down the alphabet. 
     * For example, with a shift of 3, 'A' would become 'D', 'B' would become 'E', and so on.
     */    
 
    public static String caesarEncryption(String plainText, int key) {
    	String upperText = plainText.toUpperCase();
        char[] encrypChar = new char[upperText.length()];
        
        if(isStringInBounds(upperText) == false) {
       	 	return "The selected string is not in bounds, Try again.";
        }
        
        int[] indexText = new int[upperText.length()];
        for(int i =0;i <plainText.length(); i++ ) {
       	 	for(int j = 0; j < ALPHABET64.length(); j++ ) {
       		 	if(upperText.charAt(i) == ALPHABET64.charAt(j)) {
       			 indexText[i] = j;
       		 	}
       	 	}
        }
         
        int j = 0;
        int[] position = new int[upperText.length()];
        for(int i = 0; i < upperText.length(); i++) {
       	 position[i] = (indexText[i] + key + ALPHABET64.length()) % ALPHABET64.length();
       	 encrypChar[i] = ALPHABET64.charAt(position[i]);
        }
        
        String encrypString = new String(encrypChar);
        return encrypString;
    }

    // Caesar Decryption
    public static String caesarDecryption(String encryptedText, int key) {
    	String upperText = encryptedText.toUpperCase();
        char[] decrypChar = new char[encryptedText.length()];
        
        if(isStringInBounds(upperText) == false) {
       	 	return "The selected string is not in bounds, Try again.";
        }
        
        int[] indexText = new int[upperText.length()];
        for(int i =0;i <upperText.length(); i++ ) {
       	 	for(int j = 0; j < ALPHABET64.length(); j++ ) {
       		 	if(upperText.charAt(i) == ALPHABET64.charAt(j)) {
       			 indexText[i] = j;
       		 	}
       	 	}
        }
        
        int j = 0;
        int[] position = new int[upperText.length()];
        for(int i = 0; i < upperText.length(); i++) {
        	position[i] = (indexText[i] - key + ALPHABET64.length()) % ALPHABET64.length(); 
        	decrypChar[i] = ALPHABET64.charAt(position[i]);
        }
        
        String decrypString = new String(decrypChar);
        return decrypString;
    }    
}
