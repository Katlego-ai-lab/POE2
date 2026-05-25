package com.mycompany.test.unit1;

public class TestUnit1 {

    
    public String checkMessageLength(String message) {
        if (message == null || message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }



    public void testMessageLengthFailure() {
        
        String failingMessage = "a".repeat(255);
        String expected = "Message exceeds 250 characters by 5; please reduce the size.";
        
        assertEquals(expected, checkMessageLength(failingMessage));
    }

    public void assertEquals(String expected, String checkMessageLength) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}