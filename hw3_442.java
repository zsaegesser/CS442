import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.sql.*;
//Zach Saegesser
public class hw3_442 {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost/test";

   //  Database credentials
   static final String USER = "root";
   //the user name; You can change it to your username (by default it is root).
   static final String PASS = "saegesser";
   //the password; You can change it to your password (the one you used in MySQL server configuration).

   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 1: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 2: Open a connection to database
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);


      System.out.println("Creating database...");
      stmt = conn.createStatement();

      String sql = "DROP DATABASE IF EXISTS BoatRental";
      stmt.executeUpdate(sql);

      sql = "CREATE DATABASE BoatRental";
      stmt.executeUpdate(sql);
      System.out.println("Database created successfully...");

      //STEP 4: Use SQL to select the database
      sql = "use BoatRental";
      stmt.executeUpdate(sql);

      //STEP 5: Use SQL to create Tables;
      sql = "create table Sailor( sid integer not null PRIMARY KEY, " +
            "sname varchar(20) not null," +
            "rating real," +
            "age integer not null)";
      stmt.executeUpdate(sql);

      sql = "create table Boats(bid integer not null PRIMARY KEY," +
            "bdriver_name varchar(40) not null," +
            "color varchar(40))";
      stmt.executeUpdate(sql);

      sql = "create table Reserves(sid integer not null," +
            "bid integer not null," +
            "day date not null," +
            "FOREIGN KEY (sid) REFERENCES Sailor(sid)," +
            "FOREIGN KEY (bid) REFERENCES Boats(bid)," +
            "PRIMARY KEY (sid, bid, day))";
      stmt.executeUpdate(sql);

      //sailors inserts
      sql = "insert into Sailor values(22, 'Dustin', 7, 45)";
      stmt.executeUpdate(sql);

      sql = "insert into Sailor values(29, 'Brutus', 1, 33)";
      stmt.executeUpdate(sql);

      sql = "insert into Sailor values(31, 'Lubber', 8, 55)";
      stmt.executeUpdate(sql);

      sql = "insert into Sailor values(32, 'Andy', 8, 26)";
      stmt.executeUpdate(sql);

      sql = "insert into Sailor values(58, 'Rusty', 10, 35)";
      stmt.executeUpdate(sql);

      sql = "insert into Sailor values(64, 'Horatio', 7, 35)";
      stmt.executeUpdate(sql);

      sql = "insert into Sailor values(71, 'Zorba', 20, 18)";
      stmt.executeUpdate(sql);

      sql = "insert into Sailor values(74, 'Horatio', 9, 35)";
      stmt.executeUpdate(sql);

      //boats inserts
      sql = "insert into Boats values(101, 'Interlake', 'Blue')";
      stmt.executeUpdate(sql);

      sql = "insert into Boats values(102, 'Interlake', 'Red')";
      stmt.executeUpdate(sql);

      sql = "insert into Boats values(103, 'Clipper', 'Green')";
      stmt.executeUpdate(sql);

      sql = "insert into Boats values(104, 'Marine', 'Red')";
      stmt.executeUpdate(sql);

      //reserves inserts
      sql = "insert into Reserves values(22, 101, '2008-10-10')";
      stmt.executeUpdate(sql);

      sql = "insert into Reserves values(22, 102, '2008-10-10')";
      stmt.executeUpdate(sql);

      sql = "insert into Reserves values(22, 103, '2009-10-08')";
      stmt.executeUpdate(sql);

      sql = "insert into Reserves values(22, 104, '2009-10-09')";
      stmt.executeUpdate(sql);

      sql = "insert into Reserves values(31, 102, '2008-11-10')";
      stmt.executeUpdate(sql);

      sql = "insert into Reserves values(31, 103, '2008-11-06')";
      stmt.executeUpdate(sql);

      sql = "insert into Reserves values(31, 104, '2008-11-12')";
      stmt.executeUpdate(sql);

      sql = "insert into Reserves values(64, 101, '2008-09-05')";
      stmt.executeUpdate(sql);

      sql = "insert into Reserves values(64, 102, '2008-09-08')";
      stmt.executeUpdate(sql);

      sql = "insert into Reserves values(74, 103, '2008-09-08')";
      stmt.executeUpdate(sql);

      //Sailor(sid integer, sname varchar(20), rating real, age integer);
      //Boats(bid integer, bname varchar(40), color varchar(40));
      //Reserves(sid integer, bid integer, day date);

        //Q1: Find the sids of all sailors who have reserved red boats but not green boats

        Statement q1 = conn.createStatement ();
        q1.executeQuery ("SELECT S.sid FROM Sailor S, Reserves R, Boats B WHERE S.sid = R.sid AND B.bid = R.bid AND B.color = 'red' AND S.sid NOT IN (SELECT S.sid FROM Sailor S, Reserves R, Boats B WHERE S.sid = R.sid AND B.bid = R.bid AND B.color = 'green')");
        ResultSet rs1 = q1.getResultSet ();
        int count1 = 0;
        System.out.println("QUESTION 1:");
        while (rs1.next ()) {
            String nameVal = rs1.getString ("sid");
            System.out.println ("sid = " + nameVal);
            ++count1;
        }
        rs1.close ();
        q1.close ();
        //Q2: Find the name of the sailors that have NOT reserved a red boat
        Statement q2 = conn.createStatement ();
        q2.executeQuery("SELECT S1.sname FROM Sailor S1 WHERE S1.sid NOT IN (SELECT S2.sid FROM Reserves R, Boats B, Sailor S2 WHERE S2.sid = R.sid AND B.bid = R.bid AND B.color = 'Red')");
        ResultSet rs2 = q2.getResultSet ();
        int count2 = 0;
        System.out.println("QUESTION 2:");
        while (rs2.next ()) {
            String nameVal = rs2.getString ("sname");
            System.out.println("sname = " + nameVal);
            ++count2;
        }
        rs2.close ();
        q2.close ();

        //Q3: Find sailors whose rating is better than every sailor named Horatio
        Statement q3 = conn.createStatement ();
        q3.executeQuery("SELECT * FROM Sailor WHERE rating > ALL (SELECT rating FROM Sailor WHERE sname = 'Horatio');");
        ResultSet rs3 = q3.getResultSet ();
        int count3 = 0;
        System.out.println("QUESTION 3:");
        while (rs3.next ()) {
            String sidVal = rs3.getString("sid");
            String nameVal = rs3.getString("sname");
            String ratingVal = rs3.getString("rating");
            String ageVal = rs3.getString("age");
            System.out.println("sid = " + sidVal + ", sname = " + nameVal + ", rating = " + ratingVal + ", age = " + ageVal);
            ++count3;
        }
        rs3.close ();
        q3.close ();

        //Q4: Find the names of sailors who have reserved all boats

        Statement q4 = conn.createStatement ();
        q4.executeQuery("SELECT S.sname FROM Sailor S, Reserves R WHERE R.sid = S.sid GROUP BY S.sid HAVING COUNT(DISTINCT bid) = (SELECT COUNT(DISTINCT bid) as countVal FROM Boats);");
        ResultSet rs4 = q4.getResultSet ();
        int count4 = 0;
        System.out.println("Question 4:");
        while (rs4.next ()) {
            String nameVal = rs4.getString ("sname");
            System.out.println("sname = " + nameVal);
            ++count4;
        }
        rs4.close ();
        q4.close ();

        //Q5: For each red boat, find its number of reservations
        Statement q5 = conn.createStatement ();
        q5.executeQuery("SELECT B.bid, COUNT(*) as countval FROM Boats B, Reserves R WHERE R.bid = B.bid AND B.color = 'Red' GROUP BY B.bid");
        ResultSet rs5 = q5.getResultSet ();
        int count5 = 0;
        System.out.println("QUESTION 5:");
        while (rs5.next ()) {
            String bidVal = rs5.getString("bid");
            String countval = rs5.getString("countval");
            System.out.println("bid = "+ bidVal + ", countval = " + countval);
            ++count5;
        }
        rs5.close ();
        q5.close ();


      }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
}//end main
}//end JDBCExample
