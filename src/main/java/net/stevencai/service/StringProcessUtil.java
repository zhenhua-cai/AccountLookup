package net.stevencai.service;

import javafx.scene.control.TextField;

public class StringProcessUtil {
    /**
     * process input field strings. eliminate whitespaces at the beginning and the end.
     * @param text input text.
     * @return final trimmed string.
     */
    public static String processInputFieldString(String text){
        int start = 0;
        while(start < text.length() && Character.isWhitespace(text.charAt(start))) {
            start++;
        }
        int end = text.length() - 1;
        while(end >0 && Character.isWhitespace(text.charAt(end))){
            end--;
        }
        if(end < start){
            return "";
        }
        return text.substring(start, end + 1);
    }
}
