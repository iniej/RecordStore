import java.util.ArrayList;

/**
 * Created by Iniebiyo Joshua on 5/5/2017.
 */
public class RecordStoreAppManager {

    static RecordStoreAppForm recordStoreAppForm;
    static RecordStoreAppDataBase recordStoreAppDataBase;

    public static void main(String[] args) {
        RecordStoreAppManager recordStoreAppManager = new RecordStoreAppManager();
        recordStoreAppManager.startApp();
    }
    private void startApp(){
        recordStoreAppDataBase = new RecordStoreAppDataBase();
        recordStoreAppDataBase.createTable();

        recordStoreAppForm = new RecordStoreAppForm(this);
        //record.setListData(allData);
    }
    void delete(RecordStoreAppData recordStoreAppData){
        recordStoreAppDataBase.delete(recordStoreAppData);
    }


    ArrayList<RecordStoreAppData> getAllData() {
        return recordStoreAppDataBase.fetchAllRecords();
    }
    ArrayList<SoldItem> getAllSoldData() {
        return recordStoreAppDataBase.fetchAllSoldRecords();
    }
    ArrayList<PaymentInfo> getAllPaymentData(){
        return recordStoreAppDataBase.fetchPaymentRecords();
    }

    void addRecordToDatabase(RecordStoreAppData recordStoreAppData) {
        recordStoreAppDataBase.addDataInvetory(recordStoreAppData);
    }
    void addRecordToSoldTable(SoldItem soldItem){
        recordStoreAppDataBase.addDataSoldItemTable(soldItem );
    }
    void addRecordToPayInfoTable(PaymentInfo paymentInfo){
        recordStoreAppDataBase.addDataToPayInfoTable(paymentInfo);
    }


}


