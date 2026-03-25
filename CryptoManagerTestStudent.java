/**
 * 
 */
package Project3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 */
class CryptoManagerTestStudent {

	@Test
	public void testPlayfairEncryptionDecryption_Duplicates() {
        String text = "JAVA FUUN $_$";
        String key = "OPTION";

        assertEquals(text.toUpperCase(),
            CryptoManager.playfairDecryption(
                CryptoManager.playfairEncryption(text, key), key));
    }
	
	@Test
	public void testVigenereEncryption_outOfBounds() {
	    String result = CryptoManager.vigenereEncryption("HELLO~", "KEY");
	    assertEquals("The selected string is not in bounds, Try again.", result);
	}
	
	@Test
	public void testPlayfairSingleCharacter() {
	     String text = "$";
	     String key = "s";
	     assertEquals(text,
	             CryptoManager.playfairDecryption(
	                     CryptoManager.playfairEncryption(text, key), key));
	 }
	
	@Test
	public void testPlayfairEncryption_oddLength() {
	    String result = CryptoManager.playfairEncryption("HELLO", "KEY");
	    assertEquals(6, result.length()); 
	}
	
	@Test
	public void testCaesarEncryptionDecryption() {
        String text = "HELLO";
        int key = 3;
        String encrypted = CryptoManager.caesarEncryption(text, key);
        String decrypted = CryptoManager.caesarDecryption(encrypted, key);
        assertEquals(text, decrypted);
    }

}
