package exercises;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExerciseData {
    private final StringProperty exerciseName;
    private final StringProperty bodyPart;
    private final StringProperty type;
    private final StringProperty description;

    public ExerciseData(String exerciseName, String type, String bodyPart, String description) {
        this.exerciseName = new SimpleStringProperty(exerciseName);
        this.type = new SimpleStringProperty(type);
        this.bodyPart = new SimpleStringProperty(bodyPart);
        this.description = new SimpleStringProperty(description);
    }

    public String getExerciseName() {
        return exerciseName.get();
    }

    public StringProperty exerciseNameProperty() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName.set(exerciseName);
    }

    public String getBodyPart() {
        return bodyPart.get();
    }

    public StringProperty bodyPartProperty() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart.set(bodyPart);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    /* public String getExerciseEdit() {
        return exerciseEdit.get();
    }

    public StringProperty exerciseEditProperty() {
        return exerciseEdit;
    }

    public void setExerciseEdit(String exEdit) {
        this.exerciseEdit.set(exEdit);
    }

    public String getExerciseDelete() {
        return exerciseDelete.get();
    }

    public StringProperty exerciseDeleteProperty() {
        return exerciseDelete;
    }

    public void setExerciseDelete(String exDel) {
        this.exerciseDelete.set(exDel);
    }
    */
}
