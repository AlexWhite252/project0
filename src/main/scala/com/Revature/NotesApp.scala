package com.Revature

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet, SQLException, Statement}
import scala.io.StdIn._
import scala.util.matching.Regex.Match

object NotesApp {

  def main(args: Array[String]) {
    var menu = true

    // connect to the database named "test" on the localhost
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/project0"
    val username = "root"
    val password = "Luni2323cK!"

    var connection:Connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()

    println("Welcome to the Note DB App")
    //Add create account option?
    val noteUser = readLine("Please enter your username: ")
    val notePass = readLine("Please enter your password: ")
    val credentials = statement.executeQuery(s"SELECT userID FROM users WHERE username = '${noteUser}' AND password = '${notePass}';")
    credentials.next()
    val userID = credentials.getString(1)
    //Check if credentials are valid

    //Menu system
    while(menu){
      println(
        """
          |Please select an option from the menu
          |1. Compose new note
          |2. Read previous note
          |3. Log out
          |""".stripMargin)
      var menuSelect = readInt()
      //Put menu options into methods
      menuSelect match {
        case 1 =>
          try{
            writeToDB(statement, userID)
          }
          catch{
            case _: Throwable => println("Exception in write method")
          }
        case 2 =>
          try{
            readFromDB(statement, userID)
          }
          catch{
            case _: Throwable => println("Exception in read method")
          }
        case 3 =>
          println("Logging out")
          menu = false
        case _ =>
          println("Default case")
      }
    }

    //Testing statements
    val resultSet = statement.executeQuery("SELECT * FROM contIndex;")
    printResults(resultSet, 4)

    connection.close()
  }

  //Print tables from a ResultSet
  def printResults(resultSet: ResultSet, colNum: Int): Unit={
    while ( resultSet.next() ) {
      for(i <- 1 to colNum) {
        print(resultSet.getString(i))
        if(i != colNum) print(", ")
      }
      println("")
    }
  }

  //Write to database functions, called from root menu option 1
  def writeToDB(statement: Statement, userID: String): Unit ={
    //Add ability to read from JSON file
    val importance = readLine("Enter your note's importance (Priority, Reminder, Misc, Secret): ")
    val noteText = readLine("Enter your note's text: ")
    statement.executeUpdate(s"INSERT INTO contIndex (userID, importance, entryDate) VALUES (${userID}, '${importance}', CAST( CURDATE() AS Date ));")
    statement.executeUpdate(s"INSERT INTO content (entryText) VALUES ('${noteText}')")
  }

  //Read from database functions, called from root menu option 2
  def readFromDB(statement: Statement, userID: String): Unit={
    println(
      """Please select an option from the menu
        |1. Open all previous notes
        |2. Open notes from today
        |3. Exit
        |""".stripMargin)
    val readSelect = readInt()
    readSelect match{
      case 1 =>
        println("Opening all previous notes: ")
        val readSet = statement.executeQuery(s"SELECT i.entryDate, c.entryText " +
          s"FROM content c INNER JOIN contIndex i " +
          s"ON " + s"c.entryID = i.entryID WHERE i.userID = ${userID};")
        printResults(readSet,2)
      case 2 =>
        println("Opening all entries from today: ")
        val readSet = statement.executeQuery(s"SELECT i.entryDate, c.entryText " +
          s"FROM content c INNER JOIN contIndex i " +
          s"ON c.entryID = i.entryID WHERE i.userID = ${userID} AND i.entryDate = CAST( CURDATE() AS Date );")
        printResults(readSet, 2)
      case 3 =>
        println("Exiting read menu.")
      case _ =>
        println("Invalid entry")
    }
  }
}