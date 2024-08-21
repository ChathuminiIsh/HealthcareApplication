public class Patient extends Person {
    private String patientId;


    public Patient(String patientId, String name, String contactNumber) {
        super(name, contactNumber);
        this.patientId = patientId;
    }


    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    // method to get patient type
    public char getPatientType() {
        return patientId.startsWith("T") ? 'T' : 'D';
    }
}
