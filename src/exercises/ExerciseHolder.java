package exercises;

public class ExerciseHolder {
    public static String exerciseName;
    public static String programID;
    public static String type;
    public static String bodyPart;
    public static String description;

    public static void setExerciseName(String n) {
        exerciseName = n;
    }
    public static String getExerciseName() {
        return exerciseName;
    }

    public static void setProgramID(String p) {
        programID = p;
    }
    public static String getProgramID() {
        return programID;
    }

    public static void setType(String t) {
        type = t;
    }
    public static String getType() {
        return type;
    }

    public static void setBodyPart(String bp) {
        bodyPart = bp;
    }
    public static String getBodyPart() {
        return bodyPart;
    }

    public static void setDescription(String d) {
        description = d;
    }
    public static String getDescription() {
        return description;
    }

    public static void clear(){
        exerciseName = "";
        programID = "";
        type = "";
        bodyPart = "";
        description = "";
    }
}
