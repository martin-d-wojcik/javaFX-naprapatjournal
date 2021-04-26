package patients;

public class PatientHolder {
    public static String personNr;
    public static String firstName;
    public static String lastName;

    public static void setPersonNr(String p) {
        personNr = p;
    }
    public static String getPersonNr() {
        return personNr;
    }

    public static void setFirstName(String f) {
        firstName = f;
    }
    public static String getFirstName() {
        return firstName;
    }

    public static void setLastName(String l) {
        lastName = l;
    }
    public static String getLastName() {
        return lastName;
    }
}
