import java.util.*;

public class Controller {
    // ArrayList to add new doctors
    static ArrayList<Doctor> doctors = new ArrayList<>();

    // ArrayList to add new patients
    static ArrayList<Patient> patients = new ArrayList<>();

    public static int NumberOfSlots = 5;

    // Adding new doctors method
    public static void addDoctor() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Doctor ID ");
        int doctorId = scan.nextInt();
        scan.nextLine();

        System.out.println("Enter Doctor Name ");
        String name = scan.nextLine();

        System.out.println("Enter Doctor BirthDay (yyyy-mm-dd) ");
        String birthday = scan.nextLine();
        Date birthdayInput = java.sql.Date.valueOf(birthday);

        System.out.println("Enter Doctor Specialization ");
        String specialization = scan.nextLine();

        System.out.println("Enter Doctor Contact Number ");
        String contactNumber = scan.nextLine();

        Doctor newDoctor = new Doctor(doctorId, name, birthdayInput, specialization, contactNumber);
        doctors.add(newDoctor);

        System.out.println("Doctor added Successfully!\n");
    }

    // View all doctors
    public static void viewAllDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("No available Doctors");
        } else {
            System.out.println("All available Doctors:");
            for (Doctor doctor : doctors) {
                System.out.println("Name: " + doctor.getName() + ", Specialization: " + doctor.getSpecialization());
            }
        }
        System.out.println();
    }

    // Add availability of the doctors
    public static void addAvailability() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Doctor ID ");
        int doctorId = scan.nextInt();
        scan.nextLine();

        Doctor selectedDoctor = null;
        for (Doctor doc : doctors) {
            if (doc.getDoctorId() == doctorId) {
                selectedDoctor = doc;
            }
        }
        if (selectedDoctor == null) {
            System.out.println("Doctor not found!");
            return;
        }

        System.out.println("Enter available date (yyyy-mm-dd)");
        String availableDate = scan.nextLine();
        Date availableDateInput = java.sql.Date.valueOf(availableDate);

        // Add the available date to the doctor's availability list
        selectedDoctor.getAvailability().add(availableDateInput);
        System.out.println("Availability added successfully!\n");
    }

    // Adding new patient method
    public static void addPatient() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Patient ID ");
        String patientId = scan.nextLine();

        System.out.println("Enter Patient Name ");
        String name = scan.nextLine();

        System.out.println("Enter Patient Contact Number ");
        String contactNumber = scan.nextLine();

        Patient newPatient = new Patient(patientId, name, contactNumber);
        patients.add(newPatient);

        System.out.println("Patient added Successfully!\n");
    }

    // Book an appointment
    public static void bookAppointment() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter doctor's ID you want to make an appointment with ");
        int doctorId = scan.nextInt();
        scan.nextLine();

        Doctor selectedDoctor = null;
        for (Doctor doc : doctors) {
            if (doc.getDoctorId() == doctorId) {
                selectedDoctor = doc;
            }
        }
        if (selectedDoctor == null) {
            System.out.println("Doctor not found!");
            return;
        }

        System.out.println("Enter Patient's ID: ");
        String patientId = scan.nextLine();

        Patient selectedPatient = null;
        for (Patient patient : patients) {
            if (Objects.equals(patient.getPatientId(), patientId)) {
                selectedPatient = patient;
            }
        }
        if (selectedPatient == null) {
            System.out.println("Patient not found!");
            return;
        }

        System.out.println("Is this a General or Referral Appointment? (Press G for General, R for Referral): ");
        char appointmentType = scan.next().charAt(0);
        scan.nextLine();

        System.out.println("Enter appointment date (yyyy-mm-dd)");
        String appointmentDate = scan.nextLine();
        Date availableDate = java.sql.Date.valueOf(appointmentDate);

        Boolean isAvailable = checkAvailability(selectedDoctor, availableDate);
        if (!isAvailable) {
            System.out.println("Doctor is not available on the selected Date.. Select another Date");
            return;
        }

        String slotTime = getTimeForBooking(selectedDoctor, availableDate);
        if (slotTime == null) {
            System.out.println("No slots are available now!");
            return;
        }

        Appointment appointment;
        if (appointmentType == 'G' || appointmentType == 'g') {
            System.out.println("Enter additional note: ");
            String note = scan.nextLine();

            appointment = new GeneralAppointment(selectedDoctor, selectedPatient, availableDate, slotTime, note);
        } else if (appointmentType == 'R' || appointmentType == 'r') {
            System.out.println("Enter referral Doctor's ID: ");
            int referralDoctorId = scan.nextInt();
            scan.nextLine();

            Doctor referralDoctor = null;
            for (Doctor doc : doctors) {
                if (doc.getDoctorId() == referralDoctorId) {
                    referralDoctor = doc;
                }
            }
            if (referralDoctor == null) {
                System.out.println("Referral Doctor not found!");
                return;
            }

            System.out.println("Enter additional note: ");
            String note = scan.nextLine();

            appointment = new ReferralAppointment(selectedDoctor, selectedPatient, availableDate, slotTime, referralDoctor, note);

            System.out.println("Enter referral doctor's note: ");
            String referralNote = scan.nextLine();
            ((ReferralAppointment) appointment).setReferralDoctorNotes(referralNote);
        } else {
            System.out.println("Invalid appointment type.");
            return;
        }

        selectedDoctor.setAppointments(appointment, availableDate);
        System.out.println("Appointment added successfully!\n");
        System.out.println("Appointment Date: " + appointmentDate + "  Appointment Time: " + slotTime);
    }


    private static String getTimeForBooking(Doctor selectedDoctor, Date dateOfBooking) {
        for (Map.Entry<Date, ArrayList<Appointment>> appointment : selectedDoctor.getAllAppointments().entrySet()) {
            if (appointment.getKey().equals(dateOfBooking)) {
                int numberOfSlots = appointment.getValue().size();
                if (numberOfSlots > NumberOfSlots - 1) {
                    return null;
                }
                int time = 17 + appointment.getValue().size();
                return time + ":00";
            }
        }
        return "17:00";
    }

    private static Boolean checkAvailability(Doctor selectedDoctor, Date dateOfBooking) {
        for (Date day : selectedDoctor.getAvailability()) {
            if (day.equals(dateOfBooking)) {
                return true;
            }
        }
        return false;
    }

    // Method to view a doctor's bookings
    public static void viewDoctorBookings(int doctorId) {
        Doctor selectedDoctor = null;
        for (Doctor doc : doctors) {
            if (doc.getDoctorId() == doctorId) {
                selectedDoctor = doc;
                break;
            }
        }

        if (selectedDoctor != null) {
            HashMap<Date, ArrayList<Appointment>> allAppointments = selectedDoctor.getAllAppointments();
            if (allAppointments.isEmpty()) {
                System.out.println("No bookings found for this doctor.");
            }
            else {
                System.out.println("Appointments for Doctor ID " + doctorId + ":");
                for (Map.Entry<Date, ArrayList<Appointment>> entry : allAppointments.entrySet()) {
                    Date date = entry.getKey();
                    ArrayList<Appointment> appointments = entry.getValue();
                    for (Appointment appointment : appointments) {
                        System.out.println("Patient: " + appointment.getPatient().getName() +
                                ", Date: " + date +
                                ", Time: " + appointment.getTime());
                    }
                }
            }
        }
        else {
            System.out.println("Doctor not found!");
        }
    }


}
