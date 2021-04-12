package login;

public class UserHolder {

    /*
     * Variables and methods are
     * public static.
     * This means you can use them
     * WITHOUT creating new instance of UserHolder.
     */

    /*
     * Call it from anywhere in your project like:
     *  login.UserHolder.loggedInUser = aName;
     * or
     *  String name = login.UserHolder.loggedInUser
     */
    public static String loggedInUser = "";

    /*
     * Call it from anywhere in your project like:
     *  login.UserHolder.setLoggedInUser(name);
     */
    public static void setLoggedInUser(String u) {
        loggedInUser = u;
    }

    /*
     * Call it from anywhere in your project like:
     *  String name = login.UserHolder.getLoggedInUser();
     */
    public static String getLoggedInUser() {
        return loggedInUser;
    }
}

