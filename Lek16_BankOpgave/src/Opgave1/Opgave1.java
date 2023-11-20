package Opgave1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class Opgave1 {
    public static void main(String[] args) {

        try {
            BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Indtast fraKonto: ");
            int fraKonto = Integer.parseInt(inLine.readLine());
            System.out.println("Intast tilKonto: ");
            int tilKonto = Integer.parseInt(inLine.readLine());
            System.out.println("Indtast beløb: ");
            String beloeb = inLine.readLine();

            Connection minConnection;
            minConnection = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;encrypt=true;trustserverCertificate=true;databaseName=Lek16;user=sa;password=reallyStrongPwd123;");

            Statement stmt = minConnection.createStatement();

            ResultSet fraKontoRes = stmt.executeQuery("select * from konto where ktoNr = " + fraKonto);
            fraKontoRes.next();
            ResultSet tilKontoRes = stmt.executeQuery("select * from konto where ktoNr = " + tilKonto);
            tilKontoRes.next();

            if (fraKontoRes != null) {
                System.out.println("Her");
                double fraKontoSaldo = fraKontoRes.getDouble(1);
                System.out.println("Også her");
                if (fraKontoSaldo > Integer.parseInt(beloeb)) {
                    if (tilKontoRes != null) {
                        double tilKontoSaldo = tilKontoRes.getDouble(1);

                        //Opdater frakonto
                        double fraKontoNySaldo = fraKontoSaldo - Double.parseDouble(beloeb);
                        stmt.executeQuery("update konto set saldo = " + fraKontoNySaldo + " where ktoNr = " + fraKonto);

                        //Opdater tilKonto
                        double tilKontoNySaldo = tilKontoSaldo - Double.parseDouble(beloeb);
                        stmt.executeQuery("update konto set saldo = " + tilKontoNySaldo + " where ktoNr = " + tilKonto);
                    }
                }
            }


            if (stmt != null)
                stmt.close();
            if (minConnection != null)
                minConnection.close();
        } catch (Exception e) {
            System.out.println("fejl:  " + e.getMessage());
        }
    }
}
