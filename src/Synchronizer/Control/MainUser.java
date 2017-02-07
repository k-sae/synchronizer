package Synchronizer.Control;

/**
 * Created by kemo on 07/02/2017.
 */
//TODO #later
    //1-add more info
    //2-load name from database

public class MainUser {
    private static MainUser mainUser;
    private String name;
    private MainUser()
    {
        name = "App User";
    }
    public static MainUser getMainUser()
    {
       return mainUser == null ? new MainUser() : mainUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
