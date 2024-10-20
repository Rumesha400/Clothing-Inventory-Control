package eMEDi;

import java.sql.*;
import java.time.LocalDate;

public class medicine {
    static LocalDate h;

    public static void main(String[] args) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/medical", "root", "12345678");
        if (con != null) {
            System.out.println("Database is connected");
        } else {
            System.out.println("Database is disconnected");
        }
        Statement st = con.createStatement();
        // st.execute(
        // "CREATE TABLE medicine ( name varchar(255) ,Contains varchar(255) , Therapy
        // varchar(255) , Uses varchar(255) , Side_Effect varchar(255) , price
        // decimal(10,0) ,Type varchar(255) , Quantity int(11) ) ");
        // st.executeUpdate(
        // "INSERT INTO `medicine` (`name`, `Contains`, `Therapy`, `Uses`,
        // `Side_Effect`, `price`, `Type`, `Quantity`) VALUES('Atorva\r\n',
        // 'Atorvastatin(20.0 Mg)\r\n', 'HYPOLIPIDEMIC DRUGS\r\n', 'High cholesterol and
        // heart disease\r\n', 'Headache, nausea, diarrhoea, joint pain, back pain\r\n',
        // 179, '15 Tablet(s) in Strip\r\n', 40),('Azithral\r\n', 'Azithromycin(500.0
        // Mg)\r\n', 'ANTIBIOTIC', 'Bacterial infections\r\n', 'Dizziness, breathing
        // difficulty, headache, irregular heart beat\r\n', 112, '5 Tablet(s) in
        // Strip\r\n', 60),('Benadryl Cough Syrup\r\n', 'Ammonium Chloride(138.0 Mg) +
        // Diphenhydramine(14.08 Mg) + Sodium Citrate(57.03 Mg) + Ethanol(0.2625
        // Ml)\r\n\r\n', 'COUGH COLD PREPARATION\r\n', 'Common cold and cough\r\n',
        // 'Drowsiness, dizziness, fatigue, nausea, vomiting\r\n', 119, '150ml Syrup in
        // Bottle\r\n', 15), ('Dolo', 'Paracetamol / Acetaminophen(500.0 Mg)',
        // 'ANALGESIC/ANTIPYRETIC\r\n', 'Fever and pain\r\n', 'Skin rash, itching,
        // blisters\r\n', 14, '15 Tablet in Strip\r\n', 30), ('Glycomet \r\n',
        // 'Metformin(500.0 Mg)\r\n', 'ANTI-DIABETIC\r\n', 'Diabetes mellitus\r\n',
        // 'Nausea, vomiting, diarrhoea, stomach pain\r\n', 36, '20 Tablet in
        // Strip\r\n', 25),('Pediatric Drops', 'Paracetamol / Acetaminophen(100.0
        // Mg/Ml)\r\n', 'ANALGESIC/ANTIPYRETIC\r\n', 'Vitamin D ,Multivitamin Drops',
        // 'Digestive Issues', 26, '15ml Oral Drop in Bottle\r\n', 10),('Praome ',
        // 'Omeprazole(20.0 Mg)', 'ANTACID', 'Acidity, heartburn, stomach ulcers\r\n',
        // 'Headache, nausea, vomiting\r\n', 34, '15 Capsule in Strip\r\n', 45);");
        // String sql = "INSERT INTO medicine (name, Contains, Therapy, Uses,
        // Side_Effect, price, Type, Quantity)VALUES ('Aspirin', 'Acetylsalicylic Acid',
        // 'Pain Relief', 'Headache, Fever', 'Stomach Irritation', 5.99, '10 tablets in
        // one strip', 100);";
        // String sql2 = "INSERT INTO medicine (name, Contains, Therapy, Uses,
        // Side_Effect, price, Type, Quantity)\n" + //
        // "VALUES ('Amoxicillin', 'Amoxicillin', 'Antibiotic', 'Bacterial Infections',
        // 'Nausea, Diarrhea', 12.49, 'Bottle of 50 capsules', 50);";
        // String sql3 = "INSERT INTO medicine (name, Contains, Therapy, Uses,
        // Side_Effect, price, Type, Quantity)\n" + //
        // "VALUES ('Lisinopril', 'Lisinopril', 'Blood Pressure Control',
        // 'Hypertension', 'Dizziness, Cough', 7.99, '30 tablets in one box', 75);";
        // String sql4 = "INSERT INTO medicine (name, Contains, Therapy, Uses,
        // Side_Effect, price, Type, Quantity)\n" + //
        // "VALUES ('Ibuprofen', 'Ibuprofen', 'Pain Relief', 'Muscle Pain,
        // Inflammation', 'Stomach Upset', 4.29, '20 tablets in one bottle', 200);";
        // String sql5 = "INSERT INTO medicine (name, Contains, Therapy, Uses,
        // Side_Effect, price, Type, Quantity)\n" + //
        // "VALUES ('Atorvastatin', 'Atorvastatin', 'Cholesterol Management', 'High
        // Cholesterol', 'Muscle Pain, Liver Issues', 15.99, 'Blister pack of 30
        // tablets', 30);";

    }

}
