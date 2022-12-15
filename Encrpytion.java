class Encrpytion {
    // A 64 character encrpyter that will use the values present in addition to
    // a passcode to randomize the text string
    private final static int[] encrpyter = {
        8, 0, 2, 3, 6, 8, 0, 2, 
        7, 4, 1, 4, 5, 7, 9, 1, 
        0, 2, 9, 6, 8, 0, 2, 4,
        9, 1, 3, 0, 7, 9, 1, 3,  
        2, 4, 6, 8, 5, 2, 4, 6,  
        4, 6, 8, 7, 2, 3, 6, 8,    
        1, 3, 5, 0, 9, 1, 7, 5,  
        3, 5, 7, 9, 1, 3, 5, 4
    };
    // Initialize
    private int[] passcode = new int[4];
    // May not need to store the message - DELETE
    // private String msgtext;

    public Encrpytion(int code) {
        // Argument: passcode - must be integer
        for (int i = 0; i < passcode.length; i++) {
            this.passcode[i] = code % 10;
            code /= 10;
        }
    }

    public String encrpytText(String msgtext) {
        // Argument: msgtext - Message getting encrpyted

        // Create a char array list of the message
        char[] encrptedStrings = msgtext.toCharArray();
        // Integer to equally spread the the passcode over the char array
        int indexPasscode = (int) Math.ceil(((double) msgtext.length()+1)/this.passcode.length);
        // If msgtext is less than 4 = indexPasscode will be 0
        if (indexPasscode == 0) {indexPasscode = 1;}
        // Loop through and use passcode and encrpyter to encrpyt text
        for (int i = 0; i < msgtext.length(); i++) {
            // Change the value of char by shifting the ascii value of each character by
            // the encrpyter and passcode combined
            // Due to the 64 character limit of the encrpyter if string size is 
            // Longer than 64 then:
            // Aftre 64th character it will reset to 0 and so forth
            int indexEncrpyter = i - 64 * (i/64);
            encrptedStrings[i] = (char)(encrpyter[indexEncrpyter] + this.passcode[i/indexPasscode] + encrptedStrings[i]) ;
        }

        String encrpytedText = new String(encrptedStrings);

        return encrpytedText;
    }

    public String decrpytText(String encrptedText) {
        // Argument:  msgtext - Message getting encrpyted

        // Create a char array list of the message
        char[] decrpytedStrings = encrptedText.toCharArray();

        // Integer to equally spread the the passcode over the char array
        int indexPasscode = (int) Math.ceil(((double) encrptedText.length()+1)/this.passcode.length);
        // If msgtext is less than 4 = indexPasscode will be 0
        if (indexPasscode == 0) {indexPasscode = 1;}
        // Loop through and use passcode and encrpyter to decrpyt the text
        for (int i = 0; i < encrptedText.length(); i++) {
            // Change the value of char back by shifting the ascii value backward to
            // get the original text
            // Due to the 64 character limit of the encrpyter if string size is 
            // Longer than 64 then:
            // Aftre 64th character it will reset to 0 and so forth
            int indexEncrpyter = i - 64 * (i/64);
            
            decrpytedStrings[i] = (char)(decrpytedStrings[i] - encrpyter[indexEncrpyter] - this.passcode[i/indexPasscode]) ;
        }

        String decrpytedText = new String(decrpytedStrings);

        return decrpytedText;
    }

}

class EncrpytTest {
    public static void main(String[] args) {

        String text = "I am here to help";
        
        Encrpytion userEncrpytion = new Encrpytion(1331);

        String encrpyted = userEncrpytion.encrpytText(text);
        System.out.println(encrpyted);
        System.out.println(userEncrpytion.decrpytText(encrpyted));
        
    }
}