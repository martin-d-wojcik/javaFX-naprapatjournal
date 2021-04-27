package journal;

public class JournalData {
    private String dateOfCreation;
    private String information;

    public JournalData(String date, String info) {
        this.dateOfCreation = date;
        this.information = info;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
