package program;

public class ProgramHolder {
    public static String programName;
    public static String dateAndTime;
    public static Boolean addExercises;

    public static Boolean getAddExercises() {
        return addExercises;
    }

    public static void setAddExercises(Boolean addExercises) {
        ProgramHolder.addExercises = addExercises;
    }

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
