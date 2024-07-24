package HospitalMangementSystem;

import java.sql.*;
import java.sql.SQLException;
import java.util.Scanner;
public class HMSDriverClass {
    private static final String url="jdbc:mysql://localhost:3306/hospitaldb";
    private static final String username="root";
    private static final  String password="admin560";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection=DriverManager.getConnection(url,username,password);
            //Patient Class instance
            Patient patient=new Patient(connection,scanner);
            //Doctor Class instance
            Doctor doctor=new Doctor(connection);
            while(true){
                System.out.println("HealthHub");
                System.out.println("1. ADD Patient");
                System.out.println("2. VIEW Patients");
                System.out.println("3. VIEW Doctors");
                System.out.println("4. BOOK Appointment");
                System.out.println("5. EXIT");

                System.out.println("Enter your choice between 1 to 5 : ");
                int choice=scanner.nextInt();
                switch(choice){
                    case 1:
                        // Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        // View Patient
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        // View Doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // Book Appointment
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("THANK YOU! FOR USING HealthHub!!");
                        return;
                    default:
                        System.out.println("Enter valid choice!!!");
                        break;
                }
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
        System.out.println("Enter Patient pid:");
        int patientId=scanner.nextInt();
        System.out.println("Enter Doctor did:");
        int doctorId=scanner.nextInt();
        System.out.println("Enter appointment on date (YYYY-MM-DD):");
        String appointmentDate=scanner.next();

        if(patient.checkPatientById(patientId) && doctor.checkDoctorById(doctorId)){
            if(checkDoctorAvailability(doctorId, appointmentDate, connection)){
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appoinment_date) VALUES(?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected>0){
                        System.out.println("Appointment Booked!");
                    }else{
                        System.out.println("Failed to Book Appointment!");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available on this date!!");
            }
        }else{
            System.out.println("Either doctor or patient doesn't exist!!!");
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appoinment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
