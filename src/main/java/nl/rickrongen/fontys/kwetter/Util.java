package nl.rickrongen.fontys.kwetter;

/**
 * Created by rick on 3/31/17.
 */
public class Util {
    /**
     * Try to parse an integer, if it fails return the fallback
     * @param str The String containing an integer
     * @param fallback The fallback integer
     * @return An integer value representing the String or the fallback if the parsing fails
     */
    public static int tryParseInt(String str, int fallback){
        try{
            return Integer.parseInt(str);
        }catch (NumberFormatException nfe){
            return fallback;
        }
    }
}
