package HospitalMangementSystem;

import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Patient  {
  //data members
    private Connection connection; //instance
    private Scanner scanner;  //instance
  //constructor
    public Patient(Connection connection,Scanner scanner) {
        this.connection=connection;
        this.scanner=scanner;
    }
  //add patient method
    public void addPatient(){
     try{
        System.out.println("Enter Patient Name : ");
        String name=scanner.next();
        System.out.println("Enter Patient Age : ");
        int age=scanner.nextInt();
        //scanner.nextLine();   // Consume newline character left by nextInt()
        System.out.println("Enter Patient Gender :");
        String gender=scanner.next();

         String insertionQuery="INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
         PreparedStatement preparedStatement=connection.prepareStatement(insertionQuery);
         preparedStatement.setString(1,name);
         preparedStatement.setInt(2,age);
         preparedStatement.setString(3,gender);

         int affectedRows=preparedStatement.executeUpdate();
         if(affectedRows>0){
             System.out.println("Patient ADDED successfully...");
         }
         else{
             System.out.println("Failed to ADD Patient...");
         }
     }catch(SQLException e){
         e.printStackTrace();
     }
    }


  //view patient method
     public void viewPatients(){
     String viewQuery="SELECT * FROM patients";
     try{
         PreparedStatement preparedStatement=connection.prepareStatement(viewQuery);
         ResultSet resultSet=preparedStatement.executeQuery();

         System.out.println("patient: ");
//         System.out.println("+--------+---------------------+----------+------------+");
//         System.out.println("|  pid   |        name         |   age    |   gender   |");
//         System.out.println("+--------+---------------------+----------+------------+");
//        while (resultSet.next()) {
//            System.out.println("pid :"+resultSet.getInt("pid"));
//            System.out.println("name :"+resultSet.getString("name"));
//            System.out.println("age :"+resultSet.getInt("age"));
//            System.out.println("gender :"+resultSet.getString("gender"));
//        }
//        System.out.println("+--------+---------------------+----------+------------+");
         System.out.println("+------------+--------------------+----------+------------+");
         System.out.println("| Patient Id | Name               | Age      | Gender     |");
         System.out.println("+------------+--------------------+----------+------------+");
         while(resultSet.next()){
             int id = resultSet.getInt("pid");
             String name = resultSet.getString("name");
             int age = resultSet.getInt("age");
             String gender = resultSet.getString("gender");
             System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
             System.out.println("+------------+--------------------+----------+------------+");
         }
       }
     catch(SQLException se) {
         se.printStackTrace();
       }
     }
  //check patient by id (getPatientById)
     public boolean checkPatientById(int pid){
         String checkPatientQuery="SELECT * FROM patients WHERE pid=?";
         try{
             PreparedStatement preparedStatement=connection.prepareStatement(checkPatientQuery);
             preparedStatement.setInt(1,pid);
             ResultSet resultSet=preparedStatement.executeQuery();
//             if(resultSet.next()){
//                 return true;
//             }
//             else{
//                 return false;
//             }
             return resultSet.next();  // If there's a result, return true; otherwise, false
         }
         catch (SQLException se) {
             se.printStackTrace();
         }
      return false;
    }
}
