package com.mycompany.quick.chat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


class Loginn {

    String Username;
    String Password;
    String Firstname;
    String Lastname;
    String CellPhoneNumber;

    boolean checkUserName(String u) {

        if (u == null) {
            System.out.println("Username cannot be empty");
            return false;
        }

        boolean hasUnderscore = u.contains("_");
        int letterCount = 0;

        for (int i = 0; i < u.length(); i++) {
            if (Character.isLetter(u.charAt(i))) {
                letterCount++;
            }
        }

        if (hasUnderscore && letterCount >= 5) {
            System.out.println("Username captured successfully");
            return true;
        } else {
            System.out.println("Username is not correctly formatted");
            System.out.println("- Must contain '_' ");
            System.out.println("- Must contain at least 5 letters");
            return false;
        }
    }

    boolean checkLastName(String u) {

        if (u != null && u.length() > 0) {
            System.out.println("Lastname captured");
            return true;
        } else {
            System.out.println("Lastname cannot be empty");
            return false;
        }
    }

    boolean checkPassword(String p) {

        boolean longEnough = p.length() >= 8;
        boolean hasUpper = false;
        boolean hasNum = false;
        boolean hasSpecial = false;

        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasNum = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        if (longEnough && hasUpper && hasNum && hasSpecial) {
            System.out.println("Password is successful");
            return true;
        } else {
            System.out.println("Password is unsuccessful");
            if (!longEnough) System.out.println("At least 8 characters");
            if (!hasUpper) System.out.println("At least one capital letter");
            if (!hasNum) System.out.println("At least one number");
            if (!hasSpecial) System.out.println("At least one special character");
            return false;
        }
    }

    boolean checkCellPhoneNumber(String cell) {

        boolean good = cell != null && cell.matches("^\\+27[0-9]{9}$");

        if (good) {
            System.out.println("Cellphone number captured successfully");
            return true;
        } else {
            System.out.println("Cellphone number must start with +27 and contain 9 digits");
            return false;
        }
    }

    boolean loginUser(String loginU, String loginP,
                      String savedU, String savedP) {

        if (loginU.equals(savedU) && loginP.equals(savedP)) {
            System.out.println("Login successful");
            return true;
        } else {
            System.out.println("Username or password incorrect");
            return false;
        }
    }
}


class Message {

    String MessageID;
    String MessageHash;
    String Recipient;
    String MessageText;

    public Message(String MessageID, String MessageHash,
                    String Recipient, String MessageText) {

        this.MessageID = MessageID;
        this.MessageHash = MessageHash;
        this.Recipient = Recipient;
        this.MessageText = MessageText;
    }

    public String toJSONString() {

        return "{"
                + "\"MessageID\":\"" + MessageID + "\","
                + "\"MessageHash\":\"" + MessageHash + "\","
                + "\"Recipient\":\"" + Recipient + "\","
                + "\"MessageText\":\"" + MessageText + "\""
                + "}";
    }
}


class MessageProcessor {

    public Message[] storedMessages;
    public int storedCount;

    public MessageProcessor(int maxCapacity) {
        storedMessages = new Message[maxCapacity];
        storedCount = 0;
    }

    public void storeMessage(String MessageID, String MessageHash,
                             String Recipient, String MessageText) {

        if (storedCount >= storedMessages.length) {
            System.out.println("Storage is full!");
            return;
        }

        storedMessages[storedCount++] =
                new Message(MessageID, MessageHash, Recipient, MessageText);

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        for (int i = 0; i < storedCount; i++) {
            jsonBuilder.append(storedMessages[i].toJSONString());
            if (i < storedCount - 1) jsonBuilder.append(",\n");
        }

        jsonBuilder.append("\n]");

        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(jsonBuilder.toString());
            System.out.println("Message successfully stored");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public String checkRecipientCell(String RecipientNumber) {

        if (RecipientNumber == null) {
            return "Cell phone number is incorrectly formatted.";
        }

        if (RecipientNumber.startsWith("+") && RecipientNumber.length() >= 10) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted.";
        }
    }

    public String createMessageHash(String MessageID, int messageIndex, String message) {

        String firstTwoId =
                (MessageID != null && MessageID.length() >= 2)
                        ? MessageID.substring(0, 2)
                        : "00";

        String cleanMessage =
                message.trim().replaceAll("[\\.\\?\\!]", "");

        String[] words = cleanMessage.split("\\s+");

        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";

        return (firstTwoId + ":" + messageIndex + ":" + firstWord + lastWord)
                .toUpperCase();
    }

    public String SentMessage(int choice) {

        return switch (choice) {
            case 1 -> "Message successfully sent.";
            case 2 -> "Message disregarded.";
            case 3 -> "Message successfully stored.";
            default -> "Invalid option selected.";
        };
    }

    public String checkMessageLength(String message) {

        if (message == null || message.length() <= 250) {
            return "Message ready to send.";
        } else {
            return "Message exceeds 250 characters by "
                    + (message.length() - 250) + " characters.";
        }
    }
}


public class QuickchatApp {

    public static int totalMessagesSent = 0;

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            Loginn user = new Loginn();

          
            System.out.println("\n=== REGISTRATION ===");

            System.out.print("Enter firstname: ");
            String Firstname = scanner.nextLine();

            System.out.print("Enter lastname: ");
            String Lastname = scanner.nextLine();

            if (!user.checkLastName(Lastname)) return;

            String Username;
            while (true) {
                System.out.print("Enter username: ");
                Username = scanner.nextLine();

                if (user.checkUserName(Username)) break;
            }

            String Password;
            while (true) {
                System.out.print("Enter password: ");
                Password = scanner.nextLine();

                if (user.checkPassword(Password)) break;
            }

            String Cell;
            while (true) {
                System.out.print("Enter cellphone number: ");
                Cell = scanner.nextLine();

                if (user.checkCellPhoneNumber(Cell)) break;
            }

           
            System.out.println("\n=== QUICKCHAT LOGIN ===");

            System.out.print("Enter username: ");
            String LoginU = scanner.nextLine();

            System.out.print("Enter password: ");
            String LoginP = scanner.nextLine();

            boolean loggedIn =
                    user.loginUser(LoginU, LoginP, Username, Password);

            if (!loggedIn) {
                System.out.println("Access denied.");
                return;
            }

            System.out.println("\nHi " + Firstname + " " + Lastname + ", welcome back!");

            /* ===== QUICKCHAT MENU ===== */
            MessageProcessor processor = new MessageProcessor(100);

            boolean exitApp = false;

            while (!exitApp) {

                System.out.println("\n--- MAIN MENU ---");
                System.out.println("1. Send Messages");
                System.out.println("2. Show messages");
                System.out.println("3. Quit");
                System.out.print("Select option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1 -> {
                        System.out.print("How many messages? ");
                        int count = scanner.nextInt();
                        scanner.nextLine();

                        for (int i = 0; i < count; i++) {

                            System.out.println("\n--- Message " + (i + 1) + " ---");

                            String messageID =
                                    String.valueOf((long) (Math.random() * 9000000000L) + 1000000000L);

                            System.out.print("Recipient (+27...): ");
                            String recipient = scanner.nextLine();
                            System.out.println(processor.checkRecipientCell(recipient));

                            System.out.print("Message: ");
                            String text = scanner.nextLine();

                            String validation = processor.checkMessageLength(text);
                            System.out.println(validation);

                            if (validation.startsWith("Message exceeds")) continue;

                            String hash = processor.createMessageHash(messageID, i, text);

                            System.out.println("1. Send Message");
                            System.out.println("2. Discard Message");
                            System.out.println("3. Store Message");
                            
                            int action = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println(processor.SentMessage(action));

                            if (action == 1) totalMessagesSent++;
                            else if (action == 3)
                                processor.storeMessage(messageID, hash, recipient, text);

                            System.out.println("Hash: " + hash);
                        }
                    }

                    case 2 -> System.out.println("Coming soon");

                    case 3 -> {
                        System.out.println("Total sent: " + totalMessagesSent);
                        exitApp = true;
                    }

                    default -> System.out.println("Invalid option");
                }
            }
        }
    }
} 