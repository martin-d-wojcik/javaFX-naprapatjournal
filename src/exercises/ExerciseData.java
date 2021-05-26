package exercises;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExerciseData {
    private final StringProperty exerciseName;
    private final StringProperty type;
    private final StringProperty bodyPart;
    private final StringProperty description;
    private final StringProperty exerciseEdit;

    public ExerciseData(String exerciseName, String type, String bodyPart, String description, String exercEdit) {
        this.exerciseName = new SimpleStringProperty(exerciseName);
        this.type = new SimpleStringProperty(type);
        this.bodyPart = new SimpleStringProperty(bodyPart);
        this.description = new SimpleStringProperty(description);
        this.exerciseEdit = new SimpleStringProperty(exercEdit);
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

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getExerciseEdit() {
        return exerciseEdit.get();
    }

    public StringProperty exerciseEditProperty() {
        return exerciseEdit;
    }

    public void setExerciseEdit(String exEdit) {
        this.exerciseEdit.set(exEdit);
    }

}
