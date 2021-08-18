package program;

public class ProgramData {
    private String programName;
    private String information;
    private String dateOfCreation;

    public ProgramData(String name, String info, String date) {
        this.programName = name;
        this.information = info;
        this.dateOfCreation = date;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
