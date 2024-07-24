package HospitalMangementSystem;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Doctor{
    //data members
    private Connection connection; //instance
    //constructor
    public Doctor(Connection connection) {
        this.connection=connection;
    }

    //view doctors method
    public void viewDoctors(){
        String viewQuery="SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(viewQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
//            System.out.println("+-----------+---------------------+----------------------------+");
//            System.out.println("|  doc_id   |        name         |       specialization       |");
//            System.out.println("+-----------+---------------------+----------------------------+");
//            while (resultSet.next()) {
//                System.out.println("doc_id :"+resultSet.getInt("did"));
//                System.out.println("name :"+resultSet.getInt("name"));
//                System.out.println("specialization :"+resultSet.getString("specialization"));
//            }
//            System.out.println("+-----------+---------------------+----------------------------+");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("did");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-10s | %-18s | %-16s |\n", id, name, specialization);
                System.out.println("+------------+--------------------+------------------+");
            }
        }
        catch(SQLException se) {
            se.printStackTrace();
        }
    }

    //check doctor by id  (getDoctorById)
    public boolean checkDoctorById(int did){
        String checkDoctorQuery="SELECT * FROM doctors WHERE did=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(checkDoctorQuery);
            preparedStatement.setInt(1,did);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return false;
    }
}

