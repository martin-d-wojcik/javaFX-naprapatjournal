package journal;

public class JournalData {
    private String dateOfCreation;
    private String information;
    private int notesId;

    public JournalData(String date, String info) {
        this.dateOfCreation = date;
        this.information = info;
    }

    public JournalData() {}

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

    public int getNotesId() {
        return notesId;
    }

    public void setNotesId(int notesId) {
        this.notesId = notesId;
    }
}
