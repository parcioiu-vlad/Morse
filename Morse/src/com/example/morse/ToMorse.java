package com.example.morse;

/**
 * Created by vlad on 11/2/13.
 */

public class ToMorse {
    public String alphabet[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
            "O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "0","1","2","3","4","5","6","7","8","9"};
    public String morse[] = {".-","-...","-.-.","-..",".","..-.",
            "--.","....","..",".---","-.-",".-..","--","-.",
            "---",".--.","--.-",".-.","...","-",
            "..-","...-",".--","-..-","-.--","--..",
            "-----",".----","..---","...--","....-",
            ".....","-....","--...","---..","----."};
    String morse_msg; //variable to store the morse code (debugging purpose)

    public ToMorse(){
        morse_msg = "";
    }

    private void send_msg(int j,String c) throws InterruptedException {
       System.out.println(morse[j]);
        if(c != null){ // test for space in message
            morse_msg = morse_msg + " ";
            //TODO send space
            MainActivity.led_off();
            Thread.sleep(Delays.wordSpace);
            return;
        }
        for(int i = 0;i<morse[j].length();i++){
            MainActivity.led_off(); // send letter space
            Thread.sleep(Delays.letterSpace);

            morse_msg = morse_msg + morse[j] +" ";
            if(String.valueOf(morse[j].charAt(i)) == "."){
                //TODO send .
                MainActivity.led_on();
                Thread.sleep(Delays.point);

            } else{
                //TODO send -
                MainActivity.led_on();
                Thread.sleep(Delays.line);

            }
        }
    }

    public void transform(String msg) throws InterruptedException {
        //TODO improve search
        System.out.println("in ToMorse - transform");
        msg = msg.toUpperCase();
        System.out.println(msg);
        for(int i = 0;i<msg.length();i++){
            for(int j = 0;j<alphabet.length;j++){
                System.out.println(alphabet[j]);
                if(String.valueOf(msg.charAt(i)).equals(" "))
                    send_msg(j," ");
                if( alphabet[j].equals(String.valueOf(msg.charAt(i))) )
                    send_msg(j,null);
            }
        }

    }
}
