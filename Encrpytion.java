class Encrpytion {
    // A 64 character encrpyter that will use the values present in addition to
    // a passcode to randomize the text string
    private final static int[] encrpyter = {
        58, 50, 42, 34, 26, 18, 10, 2,  
        60, 52, 44, 36, 28, 20, 12, 4,  
        62, 54, 46, 38, 30, 22, 14, 6,  
        64, 56, 48, 40, 32, 24, 16, 8,  
        57, 49, 41, 33, 25, 17, 9,  1,  
        59, 51, 43, 35, 27, 19, 11, 3,  
        61, 53, 45, 37, 29, 21, 13, 5,  
        63, 55, 47, 39, 31, 23, 15, 7
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
        int indexPasscode = (msgtext.length()+1)/this.passcode.length;
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
        int indexPasscode = (encrptedText.length()+1)/this.passcode.length;
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

        String text = "Hello World My name is abemelec";
        
        Encrpytion userEncrpytion = new Encrpytion(1331);

        String encrpyted = userEncrpytion.encrpytText(text);
        System.out.println(encrpyted);
        System.out.println(userEncrpytion.decrpytText(encrpyted));
        
    }
}