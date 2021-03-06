package JavaQueries;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Executable list of SQL queries
 */
public class Queries {
    /**
     * Connects to a given database
     * @return a connection
     */
    public Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/Users/alexandrekhien/Google Drive/0 - Computer Science"
                + "/Auburn/CPSC5133 Database 2/Hospital Project/Database/database.sl3";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);

            //This will turn on foreign keys
            //by default SQLite turns them off
            conn.createStatement().executeUpdate("PRAGMA foreign_keys = ON;");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    /**
     * 1.1 Room Utilization.
     */
    public void roomUtilization() {
        String sql = "SELECT roomNumber, firstName, lastName, admissionDate"
            + " FROM currentInPatient;";
        System.out.println("Room Utilization");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("roomNumber") +  "\t"
                        + rs.getString("firstName") + "\t"
                        + rs.getString("lastName") + "\t"
                        + rs.getString("admissionDate"));
            }
        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 1.2 List the rooms that are currently unoccupied
     */
    public void roomOccupation() {
        String sql = "SELECT roomNumber " +
                " FROM Rooms " +
                " WHERE roomOcc = 0;";
        System.out.println("List the rooms that are currently unoccupied");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("roomNumber") +  "\t");
            }

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 1.3 List all rooms, rooms occupied, by whom and admission rates for those rooms occupied
     */
    public void allRoomsOccupation() {
        String sql = "SELECT Rooms.roomNumber, currentInPatient.firstName, currentInPatient.lastName, "
                + "currentInPatient.admissionDate "
                + "FROM Rooms "
                + "LEFT JOIN currentInPatient "
                + "ON Rooms.roomNumber = currentInPatient.roomNumber;";
        System.out.println("List all rooms, occupied, by whom and admission rates for those rooms occupied");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("roomNumber") +  "\t"
                        + rs.getString("firstName") + "\t"
                        + rs.getString("lastName") + "\t"
                        + rs.getString("admissionDate"));
            }

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 2.1 List all patients in the database with all personal information
     */
    public void allPatients() {
        String sql = "SELECT * "
                + " FROM Patient;";
        System.out.println("List all patients in the database with all personal information");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("patientID") +  "\t"
                        + rs.getString("firstName") + "\t"
                        + rs.getString("lastName") + "\t"
                        + rs.getString("primaryDoctorLastName"));
                }
        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 2.2 List all currently admitted inPatients at the hospital
     */
    public void allCurrentPatients() {
        String sql = "SELECT patientID, firstName, lastName "
                + " FROM currentInPatient;";
        System.out.println("List all currently admitted patients at the hospital");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("patientID") +  "\t"
                        + rs.getString("firstName") + "\t"
                        + rs.getString("lastName"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 2.3 List patients discharged within a given date range
     * @param startDateIn beginning date
     * @param endDateIn end date
     */
    public void patientsWithinDischargeRange(String startDateIn, String endDateIn) {
        String sql = "SELECT patientID, firstName, lastName "
                + " FROM InPatient "
                + " WHERE dischargeDate "
                + " BETWEEN '" + startDateIn + "' AND '" + endDateIn + "';";
        System.out.println("List all patients discharged between " + startDateIn + " and "
                + endDateIn);

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("patientID") +  "\t"
                        + rs.getString("firstName") + "\t"
                        + rs.getString("lastName"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 2.4 List all patients admitted within a given date range
     * @param startDateIn beginning date
     * @param endDateIn end date
     */
    public void patientsWithinAdmitRange(String startDateIn, String endDateIn) {
        String sql = "SELECT patientID, firstName, lastName "
                + " FROM InPatient "
                + " WHERE admissionDate "
                + " BETWEEN '" + startDateIn + "' AND '" + endDateIn + "';";
        System.out.println("List all patients admitted between " + startDateIn + " and "
                + endDateIn);

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("patientID") +  "\t"
                        + rs.getString("firstName") + "\t"
                        + rs.getString("lastName"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 2.5 For a given patient, list all admissions to the hospital with the diagnosis
     * @param patientLastNameIn patient last name
     */
    public void givenPatient(String patientLastNameIn) {
        String sql = "SELECT firstName, lastName, admissionDate, iniDiagnosis "
                + " FROM inPatient "
                + " WHERE lastName = '" + patientLastNameIn + "';";
        System.out.println("For patient " + patientLastNameIn + ", list all admissions to the hospital with the diagnosis");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("firstName") +  "\t"
                        + rs.getString("lastName") + "\t"
                        + rs.getString("admissionDate") + "\t"
                        + rs.getString("iniDiagnosis"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 2.6 For a given patient, list all treatments and treatment dates
     * @param patientLastNameIn patient last name
     */
    public void givenPatientTreatment(String patientLastNameIn) {
        String sql = "SELECT ptLastName, treatment, treatmentDate "
                + " FROM Treatment "
                + " WHERE ptLastName = '" + patientLastNameIn + "';";
        System.out.println("For patient " + patientLastNameIn + ", list all treatments and treatment dates");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("ptLastName") +  "\t"
                        + rs.getString("treatment") + "\t"
                        + rs.getString("treatmentDate"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 2.7 List patients who were admitted to the hospital 30 days within their last discharge date
     */
    public void dischargeWithinThirty() {
        String sql = "SELECT patientID, firstName, currentInPatient.lastName, primaryDoctorLastName "
                + " FROM currentInPatient "
                + " JOIN "
                + " (SELECT lastName, MAX(dischargeDate) AS dischargeDate "
                + " FROM inPatient "
                + " GROUP BY lastName) AS MXDIS "
                + " ON currentInPatient.lastName = MXDIS.lastName "
                + " WHERE (admissionDate) < DATE(MXDIS.dischargeDate,'+30 day')";
        System.out.println("List patients who were admitted within 30 days of their last discharge");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("patientID") +  "\t"
                        + rs.getString("firstName") + "\t"
                        + rs.getString("lastName") + "\t"
                        + rs.getString("primaryDoctorLastName"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 2.8 For all patients ever admitted, list total admissions, avg duration of each admission
     * longest span between, shortest span between, and average span between admissions
     */
    public void patientHistory() {
        String sql = "SELECT a.lastName, Total, Average, Maximum, Minimum, BetweenAVG "
                + " FROM (SELECT x.lastName, Total, Average "
                + " FROM (SELECT lastName, COUNT(lastName) AS Total "
                + " FROM inpatient "
                + " GROUP BY patientID "
                + " ORDER BY lastName DESC) as x "
                + " JOIN (SELECT lastName, AVG(Duration) AS Average "
                + " FROM (SELECT lastName, Cast ((JulianDay(dischargeDate) - JulianDay(admissionDate)) AS Duration) As Duration "
                + " FROM inpatient "
                + " WHERE dischargeDate > 0) "
                + " GROUP BY lastName "
                + " ORDER BY lastName DESC) as y"
                + " ON x.lastName = y.lastName) AS a "
                + " LEFT OUTER JOIN (SELECT lastName, MAX(Span) AS Maximum, MIN(Span) AS Minimum, AVG(Span) AS BetweenAVG "
                + " FROM (SELECT lastName, Cast ((JulianDay(admissionDate) - JulianDay(lastAdmissionDate)) AS Span) As Span "
                + " FROM (SELECT lastName, admissionDate, LAG(admissionDate,1,0) OVER (PARTITION BY lastName) lastAdmissionDate "
                + " FROM inPatient "
                + " ORDER BY lastName DESC) "
                + " WHERE Span < 1000000) "
                + " GROUP BY lastName "
                + " ORDER BY lastName DESC) AS b "
                + " ON a.lastName = b.lastName;";
        System.out.println("For all patients ever admitted, list total admissions, average duration "
            + "of each admission, longest span between, and average span between admissions");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("lastName") +  "\t"
                        + rs.getString("Total") + "\t"
                        + rs.getString("Average") + "\t"
                        + rs.getString("Maximum") + "\t"
                        + rs.getString("Minimum") + "\t"
                        + rs.getString("BetweenAVG"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 3.1 List the diagnosis' given to admitted patients
     */
    public void inPatientDiagnosis () {
        String sql = "SELECT diagnosisID, diagnosisName, COUNT(diagnosis.diagnosisName) AS counter "
                + " FROM currentInPatient "
                + " JOIN Diagnosis "
                + " ON currentInPatient.iniDiagnosis = Diagnosis.diagnosisName"
                + " GROUP BY diagnosisName "
                + " ORDER BY COUNT(diagnosisName) DESC;";
        System.out.println("List the diagnosis' given to admitted patients");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("diagnosisID") +  "\t"
                        + rs.getString("diagnosisName") + "\t"
                        + rs.getString("counter"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 3.2 List the diagnosis' given to all patients ever to visit the hospital
     */
    public void allPatientDiagnosis () {
        String sql = "SELECT diagnosisID, diagnosisName, COUNT(diagnosis.diagnosisName) AS counter "
                + " FROM inPatient "
                + " JOIN Diagnosis "
                + " ON inPatient.iniDiagnosis = Diagnosis.diagnosisName"
                + " GROUP BY diagnosisName "
                + " ORDER BY COUNT(diagnosisName) DESC;";
        System.out.println("List the diagnosis' given to all patients ever to visit the hospital");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("diagnosisID") +  "\t"
                        + rs.getString("diagnosisName") + "\t"
                        + rs.getString("counter"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 3.3 List treatments performed at the hospital
     */
    public void listTreatments () {
        String sql = "SELECT treatmentID, treatment, COUNT(treatmentID) AS counter "
                + " FROM treatment "
                + " GROUP BY treatmentID "
                + " ORDER BY COUNT(treatmentID) DESC;";
        System.out.println("List treatments performed at the hospital");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("treatmentID") +  "\t"
                        + rs.getString("treatment") + "\t"
                        + rs.getString("counter"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 3.4 List treatments performed on admitted patients
     */
    public void listAdmittedTreatments () {
        String sql = "SELECT treatmentID, treatment, COUNT(treatmentID) AS counter "
                + " FROM treatment "
                + " JOIN currentInPatient "
                + " ON Treatment.ptLastName = currentInPatient.lastName"
                + " GROUP BY treatmentID;";
        System.out.println("List treatments performed on admitted patients");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("treatmentID") +  "\t"
                        + rs.getString("treatment") + "\t"
                        + rs.getString("counter"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 3.5 List the top 5 administered medications
     */
    public void topMedications () {
        String sql = "SELECT treatment, COUNT(treatment) AS counter "
                + " FROM treatment "
                + " WHERE treatmentType = 'M' "
                + " GROUP BY treatment "
                + " LIMIT 5;";
        System.out.println("List the top 5 administered medications");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("treatment") +  "\t"
                        + rs.getString("counter"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 3.6 List most common procedures and operating doctors
     */
    public void topProcedure() {
        String sql = "SELECT treatment, docLastName "
                + " FROM Treatment "
                + " WHERE treatment = (SELECT treatment "
                + " FROM Treatment "
                + " WHERE treatmentType = 'P' "
                + " GROUP BY treatment "
                + " ORDER BY COUNT(treatment) DESC "
                + " LIMIT 1) "
                + " GROUP BY docLastName;";
        System.out.println("List most common procedures and operating doctors");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("treatment") +  "\t"
                        + rs.getString("docLastName"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 3.7 List most recent procedure at the hospital
     */
    public void mostRecentProcedure() {
        String sql = "SELECT treatment, docLastName "
                + " FROM Treatment "
                + " WHERE treatment = (SELECT treatment "
                + " FROM Treatment "
                + " WHERE treatmentDate = (SELECT MAX(treatmentDate) "
                + " FROM Treatment "
                + " WHERE treatmentType = 'P')) "
                + " AND treatmentType = 'P';";
        System.out.println("List most recent procedure at the hospital");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("treatment") +  "\t"
                        + rs.getString("docLastName"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 3.8 List the top 5 most frequent patients and their diagnosis'
     */
    public void topFiveDiagnosis() {
        String sql = "SELECT iniDiagnosis, inPatient.firstName "
                + " FROM inPatient "
                + " JOIN (SELECT firstName "
                + " FROM inpatient "
                + " GROUP BY firstName "
                + " ORDER BY COUNT(firstName) DESC "
                + " LIMIT 5) AS TOP "
                + " ON inpatient.firstName = TOP.firstName;";
        System.out.println("List the top 5 most frequent patients and their diagnosis'");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("iniDiagnosis") +  "\t"
                        + rs.getString("firstName"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 4.1 List all employees
     */
    public void listEmployees() {
        String sql = "SELECT firstName, lastName, jobCategory "
                + " FROM Employee "
                + " ORDER BY firstName, lastName ASC;";
        System.out.println("List all employees");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("firstname") +  "\t"
                        + rs.getString("lastName") + "\t"
                        + rs.getString("jobCategory"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 4.2 List primary doctors with high admission rates (over 4 in past year)
     */
    public void listFrequentDoctors() {
        String sql = "SELECT primaryDoctorLastName, COUNT(primaryDoctorLastName) AS counter"
                + " FROM (SELECT primaryDoctorLastName, admissionDate "
                + " FROM inpatient "
                + " WHERE admissionDate BETWEEN (SELECT date('now','-1 years')) AND (SELECT date('now'))) "
                + " GROUP by primaryDoctorLastName"
                + " HAVING COUNT(primaryDoctorLastName) > 3;";
        System.out.println("List primary doctors with high admission rates (over 4 in the past year)");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("primaryDoctorLastName") +  "\t"
                        + rs.getString("counter"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 4.3 For a given doctor, list all associated diagnosis'
     * @param doctorNameIn doctor's name
     */
    public void listDoctorDiagnosis(String doctorNameIn) {
        String sql = "SELECT iniDiagnosis, COUNT(iniDiagnosis) as counter "
                + " FROM(SELECT iniDiagnosis "
                + " FROM outpatient "
                + " WHERE primaryDoctorLastName = '" + doctorNameIn + "' "
                + " UNION ALL "
                + " SELECT iniDiagnosis "
                + " FROM inpatient "
                + " WHERE primaryDoctorLastName = 'Lowry') "
                + " GROUP BY iniDiagnosis "
                + " ORDER BY COUNT(iniDiagnosis) DESC;";
        System.out.println("For doctor " + doctorNameIn + ", list all associated diagnosis'");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("iniDiagnosis") +  "\t"
                        + rs.getString("counter"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 4.4 For a given doctor, list all treatments that they ordered
     * @param doctorNameIn doctor's name
     */
    public void listDoctorTreatment(String doctorNameIn) {
        String sql = "SELECT treatment, COUNT(treatment) as counter "
                + " FROM Treatment "
                + " WHERE docLastName = '" + doctorNameIn + "' "
                + " GROUP BY treatment "
                + " ORDER BY COUNT(treatment) DESC;";
        System.out.println("For doctor " + doctorNameIn + ", list all treatments that they ordered");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("treatment") +  "\t"
                        + rs.getString("counter"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }

    /**
     * 4.5 List doctors who have been involved in the treatment of every admitted patient
     */
    public void listDoctorsAllInPatient() {
        String sql = "SELECT DISTINCT Treatment.docLastName "
                + " FROM Treatment "
                + " JOIN currentInPatient "
                + " ON Treatment.docLastName = currentInPatient.primaryDoctorLastName;";
        System.out.println("List doctors who have been involved in the treatment of every admitted patient ");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("docLastName"));}

        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }
    }
}