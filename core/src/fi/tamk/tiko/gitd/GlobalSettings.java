package fi.tamk.tiko.gitd;

/**
 * The HelloWorld program implements an application that
 * simply displays "Hello World!" to the standard output.
 *
 * @author Akio Ide
 * @version 1.0
 * @since 2017-05-12
 */
public class GlobalSettings {

    private static final GlobalSettings instance = new GlobalSettings();

    public static GlobalSettings getInstance (){
        return instance;
    }

    public static MyGdxGame myGdxGame;


}
