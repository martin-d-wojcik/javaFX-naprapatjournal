package program;

public class ProgramHolder {
    public static String programName;
    public static String dateAndTime;

    public static String getProgramName() {
        return programName;
    }

    public static void setProgramName(String programName) {
        ProgramHolder.programName = programName;
    }

    public static String getDateAndTime() {
        return dateAndTime;
    }

    public static void setDateAndTime(String dateTime) {
        ProgramHolder.dateAndTime = dateTime;
    }
}
