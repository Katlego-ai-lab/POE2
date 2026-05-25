
package com.mycompany.testunit2;



public class TestUnit2 {
    
public class RecipientCellTest {

    
    public String checkRecipientCell(String cellNumber) {
        
        if (cellNumber != null && cellNumber.startsWith("+") && cellNumber.length() <= 11) { 
           
            return "Cell phone number successfully captured.";
        } else if (cellNumber != null && !cellNumber.startsWith("+") && cellNumber.length() <= 10) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

   
    public void testRecipientNumberSuccess() {
        
        String validCell = "+271234567"; 
        String expected = "Cell phone number successfully captured.";
        
        assertEquals(expected, checkRecipientCell(validCell));
    }

    public void testRecipientNumberFailure() {
        
        String invalidCell = "08575975889"; 
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        
        assertEquals(expected, checkRecipientCell(invalidCell));
    }

        public void assertEquals(String expected, String checkRecipientCell) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
    
}

   
