import java.util.Date;

public class ReferralAppointment extends Appointment {
    private Doctor referralDoctor;
    private String notes;
    private String referralDoctorNotes;

    public ReferralAppointment(Doctor doctor, Patient patient, Date date, String time, Doctor referralDoctor, String notes) {
        super(doctor, patient, date, time);
        this.referralDoctor = referralDoctor;
        this.notes = notes;
    }

    // Getters and Setters for referralDoctor
    public Doctor getReferralDoctor() {
        return referralDoctor;
    }

    public void setReferralDoctor(Doctor referralDoctor) {
        this.referralDoctor = referralDoctor;
    }

    // Getters and Setters for notes
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Getters and Setters for referralDoctorNotes
    public String getReferralDoctorNotes() {
        return referralDoctorNotes;
    }

    public void setReferralDoctorNotes(String referralDoctorNotes) {
        this.referralDoctorNotes = referralDoctorNotes;
    }

    // Overloaded method for setting referralDoctorNotes as an array of strings
    public void setReferralDoctorNotes(String[] referralDoctorNotesArray) {
        this.referralDoctorNotes = String.join(", ", referralDoctorNotesArray);
    }
}
