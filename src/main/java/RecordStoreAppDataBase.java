import java.sql.*;
import java.util.*;

/**
 * Created by Iniebiyo Joshua on 4/25/2017.
 */
public class RecordStoreAppDataBase {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        //Configure the driver needed
    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/RecordStore";     //Connection to the database
    private static final String USER = "javaUser";   //MYSQL username
    private static final String PASSWORD = "itec2545";   //MYSQL password
    private static final String TABLE_NAME = "Inventory_info",PK_COLUMN = "ID",CONSIGNOR_NAME_COLUMN = "ConsignorName",PHONE_COLUMN = "Phone",
            ARTIST_NAME_COLUMN = "Artist",TITLE_COLUMN = "Title",PRICE_COLUMN = "Price",DATE_COLUMN = "RecordDate";

    private static final String SOLD_TABLE_NAME = "SoldItem",SOLD_PK_COLUMN = "SoldItemID",SOLD_ITEM_CONSIGNOR_COLUMN = "Consignor",
            SOLD_PRICE_COLUMN = "SoldPrice",SOLD_TITLE_COLUMN = "Title";

    private static final String PAYMENT_INFO_TABLE = "Payment_Info", PAYMENT_INFO_CONSIGNOR_COLUMN = "Name",PAYMENT_INFO_SHARE_COLUMN =
            "Share",PAYEMENT_INFO_PAID_COLUMN = "AmountPaid",PAYEMENT_INFO_DUE_COLUMN = "AmountDue";


    //constructor
    RecordStoreAppDataBase() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have drives and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //quit the program
        }
    }
    //defines method
    void createTable() {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)) {

            String createInventoryinfoTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + PK_COLUMN + " int NOT NULL AUTO_INCREMENT,"
                    + CONSIGNOR_NAME_COLUMN + " VARCHAR(50)," + PHONE_COLUMN + " VARCHAR(12)," + ARTIST_NAME_COLUMN + " VARCHAR(50),"
                    + TITLE_COLUMN + " VARCHAR(50)," + PRICE_COLUMN + " DOUBLE,"+ DATE_COLUMN +" TIMESTAMP, PRIMARY KEY(" + PK_COLUMN + "))";
            statement.executeUpdate(createInventoryinfoTableSQL);

            String createSoldItemTableSQL = "CREATE TABLE IF NOT EXISTS " + SOLD_TABLE_NAME + "(" + SOLD_PK_COLUMN + " INT NOT NULL AUTO_INCREMENT,"
                    + SOLD_ITEM_CONSIGNOR_COLUMN + " VARCHAR(20)," + SOLD_PRICE_COLUMN + " DOUBLE," + SOLD_TITLE_COLUMN +" VARCHAR(50), PRIMARY KEY(" + SOLD_PK_COLUMN + "))";
            statement.executeUpdate(createSoldItemTableSQL);

            String createPaymentInfoTableSQL = "CREATE TABLE IF NOT EXISTS " + PAYMENT_INFO_TABLE + "(" + PAYMENT_INFO_CONSIGNOR_COLUMN + " VARCHAR(20) NOT NULL,"
                    + PAYMENT_INFO_SHARE_COLUMN + " DOUBLE NOT NULL," + PAYEMENT_INFO_PAID_COLUMN + " DOUBLE NOT NULL," + PAYEMENT_INFO_DUE_COLUMN + " DOUBLE NOT NULL"+")";
            statement.executeUpdate(createPaymentInfoTableSQL);


            statement.close();
            connection.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    //defines method that add records to the database
    void addDataInvetory(RecordStoreAppData recordStoreAppData)  {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            String addSQLrecordObject = "INSERT INTO " + TABLE_NAME + "(" + CONSIGNOR_NAME_COLUMN + ","+ PHONE_COLUMN + "," +
                    ARTIST_NAME_COLUMN + "," + TITLE_COLUMN + "," + PRICE_COLUMN + "," + DATE_COLUMN + ")" +
                    " VALUES ('" + recordStoreAppData.ConsignorName + "' , '"
                    + recordStoreAppData.phone + "','" + recordStoreAppData.artistName + "','" + recordStoreAppData.title + "','"
                    + recordStoreAppData.price +"','" + recordStoreAppData.date + "')" ;

            statement.executeUpdate(addSQLrecordObject);

            //TO DO add a message dialog box with "Added cube solver record for 'cubesolver name'" message.

            statement.close();
            connection.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }

    }

    void addDataSoldItemTable(SoldItem soldItem){
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL,USER,PASSWORD)){
            Statement statement = connection.createStatement();
            String addSQLDataToSoldTable = "INSERT INTO " + SOLD_TABLE_NAME + "(" + SOLD_ITEM_CONSIGNOR_COLUMN + ","+
                    SOLD_PRICE_COLUMN+ ","+ SOLD_TITLE_COLUMN+")" + " VALUES ('"+soldItem.Consignor + "','"
                    + soldItem.SoldPrice +"','"+soldItem.title+ "')";
            statement.executeUpdate(addSQLDataToSoldTable);
            statement.close();
            connection.close();
        }catch (SQLException sql){
            sql.printStackTrace();
        }
    }
    void addDataToPayInfoTable(PaymentInfo paymentInfo){
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL,USER,PASSWORD)){
            Statement statement = connection.createStatement();
            String addSQLDataToPaymentInfoTable = "INSERT INTO " + PAYMENT_INFO_TABLE + "(" + PAYMENT_INFO_CONSIGNOR_COLUMN + ","+
                    PAYMENT_INFO_SHARE_COLUMN + "," + PAYEMENT_INFO_PAID_COLUMN + "," + PAYEMENT_INFO_DUE_COLUMN + ") VALUES ('" +
                    paymentInfo.name + "','" + paymentInfo.share + "','" + paymentInfo.paid + "','" + paymentInfo.due + "')";
            statement.executeUpdate(addSQLDataToPaymentInfoTable);
        }catch (SQLException SQL){
            SQL.printStackTrace();
        }
    }

    // defines method that deletes data from the database
    void delete(RecordStoreAppData recordStoreAppData){
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)){
            Statement statement = connection.createStatement();
            String deleteSQLrecordObject = "DELETE FROM %s WHERE %s = ? AND %s = ? AND %s = ? AND %s = ? AND %s = ? AND %s = ?";
            String deleteSQLrecordObjectRecord = String.format(deleteSQLrecordObject,TABLE_NAME,CONSIGNOR_NAME_COLUMN,PHONE_COLUMN,ARTIST_NAME_COLUMN,TITLE_COLUMN,PRICE_COLUMN,DATE_COLUMN);
            PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteSQLrecordObjectRecord);
            deletePreparedStatement.setString(1,recordStoreAppData.ConsignorName);
            deletePreparedStatement.setString(2,recordStoreAppData.phone);
            deletePreparedStatement.setString(3,recordStoreAppData.artistName);
            deletePreparedStatement.setString(4,recordStoreAppData.title);
            deletePreparedStatement.setDouble(5,recordStoreAppData.price);
            deletePreparedStatement.setDate(6,recordStoreAppData.date);
            deletePreparedStatement.execute();
            deletePreparedStatement.close();
            connection.close();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }


    //Defines method that displays record from the database
    ArrayList<RecordStoreAppData> fetchAllRecords() {
        ArrayList<RecordStoreAppData> allRecords = new ArrayList();
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String selectSQLtable = "SELECT * FROM " + TABLE_NAME;
            ResultSet rs = statement.executeQuery(selectSQLtable);
            while (rs.next()) {
                int id = rs.getInt(PK_COLUMN);
                String cName = rs.getString(CONSIGNOR_NAME_COLUMN);
                String phone = rs.getString(PHONE_COLUMN);
                String aName = rs.getString(ARTIST_NAME_COLUMN);
                String title = rs.getString(TITLE_COLUMN);
                double price = rs.getDouble(PRICE_COLUMN);
                java.util.Date date = rs.getDate(DATE_COLUMN);
                RecordStoreAppData recordStoreAppData = new RecordStoreAppData(cName,phone,aName,title,price, (java.sql.Date) date);
                allRecords.add(recordStoreAppData);}
            rs.close();
            statement.close();
            connection.close();
            return allRecords;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
    }
    ArrayList<SoldItem> fetchAllSoldRecords(){
        ArrayList<SoldItem> allSoldRecords = new ArrayList();
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL,USER,PASSWORD);
            Statement statement = connection.createStatement()){
            String selectSQLSoldTable = "SELECT * FROM " + SOLD_TABLE_NAME;
            ResultSet rs = statement.executeQuery(selectSQLSoldTable);
            while (rs.next()){
                String cName = rs.getString(SOLD_ITEM_CONSIGNOR_COLUMN);
                double sPrice = rs.getDouble(SOLD_PRICE_COLUMN);
                String Title = rs.getString(SOLD_TITLE_COLUMN);
                SoldItem soldItem = new SoldItem(cName,sPrice,Title);
                allSoldRecords.add(soldItem);}
            rs.close();
            statement.close();
            connection.close();
            return allSoldRecords;
        }catch (SQLException sql){
            sql.printStackTrace();
            return null;
        }
    }
    ArrayList<PaymentInfo> fetchPaymentRecords(){
        ArrayList<PaymentInfo> allPaymentRecords = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL,USER,PASSWORD);
        Statement statement = connection.createStatement()){
            String selectSQLPaymentTalble = "SELECT * FROM " + PAYMENT_INFO_TABLE;
            ResultSet rs  = statement.executeQuery(selectSQLPaymentTalble);
            while (rs.next()){
                String conName = rs.getString(PAYMENT_INFO_CONSIGNOR_COLUMN);
                double share = rs.getDouble(PAYMENT_INFO_SHARE_COLUMN);
                double paid = rs.getDouble(PAYEMENT_INFO_PAID_COLUMN);
                double due = rs.getDouble(PAYEMENT_INFO_DUE_COLUMN);
                PaymentInfo paymentInfo = new PaymentInfo(conName,share,paid,due);
                allPaymentRecords.add(paymentInfo);
            }
            rs.close();
            statement.close();
            connection.close();
            return allPaymentRecords;
        }catch (SQLException sql){
            sql.printStackTrace();
            return null;
        }
    }

}


