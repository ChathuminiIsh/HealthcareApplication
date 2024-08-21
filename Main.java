import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("If you are a hospital administrator please press 1, If you are a patient please press 2, Press 3 to exit");
            int choice = scan.nextInt();

            if (choice == 1) {
                administrator();
            }
            else if (choice == 2) {
                patient();
            }
            else if (choice == 3) {
                exit= true;
            }
            else {
                System.out.println("Invalid input..");
            }
        }

      /*  Doctor doc=new Doctor();
        System.out.println(doc.isPhysician("Cardiologist"));
        System.out.println(doc.isPhysician("Neurophysician"));

        Patient shashikala=new Patient();
        System.out.println(shashikala.getPatientType("T-0001"));
*/
    }

    //Administrator panel
    public static void administrator(){
        Scanner scan = new Scanner(System.in);
        boolean exitFirst = true;

        while (exitFirst) {
            System.out.println("Press 1 to add a doctor, Press 2 to add a doctor availability, and Press 3 to exit");
            int num = scan.nextInt();

            switch (num) {
                case 1:
                    Controller.addDoctor();
                    break;
                case 2:
                    Controller.addAvailability();
                    break;
                case 3:
                    exitFirst=false;
                    break;
                default:
                    System.out.println("invalid inputs..try again");
            }
        }
    }
    // patient menu
    public static void patient(){
        Scanner scan = new Scanner(System.in);
        boolean exitSec = true;

        while (exitSec) {
            System.out.println("Press 1 to view doctors,Press 2 to book an appoinment,Press 3 to view selected doctor's booking, Press 4 to register patient and Press 5 to exit");

            int num1 = scan.nextInt();
            switch (num1) {
                case 1:
                    Controller.viewAllDoctors();
                    break;
                case 2:
                    Controller.bookAppointment();
                    break;
                case 3:
                    System.out.println("Enter the doctor's ID to view bookings: ");
                    int doctorId = scan.nextInt();
                    scan.nextLine();

                    Controller.viewDoctorBookings(doctorId);
                    break;
                case 4:
                    Controller.addPatient();
                    break;
                case 5:
                    exitSec=false;
                    break;

                default:
                    System.out.println("invalid input..");
            }
        }
    }
}
