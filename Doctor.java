import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Doctor extends Person {
    private int doctorId;
    private Date birthday;
    private String specialization;
    public ArrayList<Date> availability = new ArrayList<>();
    public HashMap<Date, ArrayList<Appointment>> allAppointments = new HashMap<>();

    public Doctor(int doctorId, String name, Date birthday, String specialization, String contactNumber) {
        super(name, contactNumber);
        this.doctorId = doctorId;
        this.birthday = birthday;
        this.specialization = specialization;
    }


    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public ArrayList<Date> getAvailability() {
        return availability;
    }

    public void setAvailability(ArrayList<Date> availability) {
        this.availability = availability;
    }

    public HashMap<Date, ArrayList<Appointment>> getAllAppointments() {
        return allAppointments;
    }

    public void setAllAppointments(HashMap<Date, ArrayList<Appointment>> allAppointments) {
        this.allAppointments = allAppointments;
    }

    //method to check doctor is a physician or not
    public boolean isPhysician() {
        return specialization.endsWith("physician");
    }

    public void setAppointments(Appointment appointment, Date date) {
        ArrayList<Appointment> currentAppointments = this.allAppointments.get(date);
        if (currentAppointments == null) {
            currentAppointments = new ArrayList<>();
            this.allAppointments.put(date, currentAppointments);
        }
        currentAppointments.add(appointment);
    }
}
