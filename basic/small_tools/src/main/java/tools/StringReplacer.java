package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Replace the appointed string that matches a given Regular Expression
public class StringReplacer {
    /*
     * 
     */
    public enum ReplaceType {
        FIRST, LAST, ALL,
    }

    public static String replace(String oldContent, String newString,
            String regx, ReplaceType type) {
        if (oldContent == null || newString == null || regx == null) {
            return null;
        }

        String newContent = null;
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(oldContent);

        if (matcher.find()) {
            String oldString = matcher.group();
            switch (type) {
            case FIRST:
                newContent = oldContent.replace(oldString, newString);
                break;
            case LAST:
                // TODO
                break;
            case ALL:
                newContent = oldContent.replaceAll(regx, newString);
                break;
            default:
                break;
            }

        }

        System.out.println("Old content: " + oldContent);
        System.out.println("New content: " + newContent);
        return newContent;
    }
}
