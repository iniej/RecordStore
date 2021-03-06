import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Iniebiyo Joshua on 4/25/2017.
 */
public class RecordStoreAppForm extends JFrame {
    private JPanel rootPanel;
    private JTextField consignorNameTextField;
    private JTextField phoneTextField;
    private JTextField artistName;
    private JTextField titleTextField;
    private JButton deleteRecordButton;
    private JButton showInfoButton;
    private JButton addRecordButton;
    private JButton quitButton;
    private JTextField priceTextField;
    private JList <RecordStoreAppData>inventoryJList;
    private JList<SoldItem> soldItemJList;
    private JComboBox selectItemComboBox;
    private JComboBox displayItemComboBox;
    private JComboBox ReturnToComboBox;
    private JButton daysInInventoryButton;
    private JLabel displayDaysJLabel;
    private JLabel notifyConsignorJLabel;
    private JList paymentJList;
    private DefaultListModel<RecordStoreAppData>inventoryListModel;
    private DefaultListModel<SoldItem>soldItemListModel;
    private DefaultListModel<PaymentInfo> paymentListModel;
    private RecordStoreAppManager recordStoreAppManager;

    RecordStoreAppForm(RecordStoreAppManager recordStoreAppManager) {
        this.recordStoreAppManager = recordStoreAppManager;
        inventoryListModel = new DefaultListModel<RecordStoreAppData>();
        soldItemListModel = new DefaultListModel<SoldItem>();
        paymentListModel = new DefaultListModel<PaymentInfo>();
        inventoryJList.setModel(inventoryListModel);
        soldItemJList.setModel(soldItemListModel);
        paymentJList.setModel(paymentListModel);
        setContentPane(rootPanel);
        selectItemComboBox();
        pack();

        addListeners();
        setTitle("Record Store Application");

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }
    // Create combo boxes for dropdown menu(for adding info into tables
    // and for showing info in a table.
    private void selectItemComboBox(){
        selectItemComboBox.addItem("Inventory");
        selectItemComboBox.addItem("Sold Item");
        selectItemComboBox.addItem("Payment Information");

        displayItemComboBox.addItem("All Inventory Records");
        displayItemComboBox.addItem("Sold Items");
        displayItemComboBox.addItem("Payment Info");

        ReturnToComboBox.addItem("Yes");
        ReturnToComboBox.addItem("No");
    }

    private final double rate = .4;
    private void addListeners() {
        addRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Read data, send message to database via controller
                String cName = consignorNameTextField.getText();
                String phone = phoneTextField.getText();
                String aName = artistName.getText();
                String title = titleTextField.getText();
                java.util.Date recordDate = new java.util.Date();
                java.sql.Date sqlrecordDate = new java.sql.Date(recordDate.getTime());
                if (selectItemComboBox.getSelectedItem().equals("Inventory")) {
                    if (cName.isEmpty()) {
                        JOptionPane.showMessageDialog(RecordStoreAppForm.this, "Enter Consignor's name");
                        return;
                    } else if (phone.isEmpty()) {
                        JOptionPane.showMessageDialog(RecordStoreAppForm.this, "Enter the phone number");
                        return;
//
                    }
                    double price;

                    try {
                        price = Double.parseDouble(priceTextField.getText());
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(RecordStoreAppForm.this, "Enter the price");
                        return;
                    }
                    RecordStoreAppData recordStoreAppData = new RecordStoreAppData(cName, phone, aName, title, price, sqlrecordDate);
                    recordStoreAppManager.addRecordToDatabase(recordStoreAppData);
                    //Clear JTextFields
                    consignorNameTextField.setText("");
                    phoneTextField.setText("");
                    artistName.setText("");
                    titleTextField.setText("");
                    priceTextField.setText("");
                    JOptionPane.showMessageDialog(RecordStoreAppForm.this, "Inventory list has been updated");
                }
                else if(selectItemComboBox.getSelectedItem().equals("Sold Item")) {
                        String sPriceString = JOptionPane.showInputDialog(RecordStoreAppForm.this, "Please enter the selling price");
                        double sPrice = Double.parseDouble(sPriceString);
                        double consignorShare = sPrice * rate;
                        RecordStoreAppData recordSold = inventoryJList.getSelectedValue();
                        String sTitle = recordSold.getTitle();
                        String consignorName = recordSold.getConsignorName();
                        SoldItem soldItem = new SoldItem(consignorName, sPrice, sTitle);
                        recordStoreAppManager.addRecordToSoldTable(soldItem);
                        recordStoreAppManager.delete(recordSold);//deletes selected object from Inventory list after it is added to sold item list.
                        JOptionPane.showMessageDialog(RecordStoreAppForm.this, "The selected item has been moved to sold item list");

                }
                else if(selectItemComboBox.getSelectedItem().equals("Payment Information")){
                    SoldItem recordToPay = soldItemJList.getSelectedValue();
                    String name = recordToPay.getConsignor();
                    double soldPrice = recordToPay.getSoldPrice();
                    double conShare = soldPrice * rate;
                    String amountPaidString = JOptionPane.showInputDialog(RecordStoreAppForm.this, "Enter pay amount");
                    double amountPaid = Double.parseDouble(amountPaidString);
                    double amountDue = conShare - amountPaid;
                    PaymentInfo paymentInfo = new PaymentInfo(name,conShare,amountPaid,amountDue);
                    recordStoreAppManager.addRecordToPayInfoTable(paymentInfo);
                }

            }
        });
        showInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (displayItemComboBox.getSelectedItem().equals("All Inventory Records")) {
                    //request all data from database to update list
                    ArrayList<RecordStoreAppData> allData = recordStoreAppManager.getAllData();
                    setListData(allData);
                }
                if (displayItemComboBox.getSelectedItem().equals("Sold Items")) {
                    ArrayList<SoldItem> allSoldData = recordStoreAppManager.getAllSoldData();
                    setListOfSoldData(allSoldData);
                }
                if (displayItemComboBox.getSelectedItem().equals("Payment Info")){
                    ArrayList<PaymentInfo> allPaymentData = recordStoreAppManager.getAllPaymentData();
                    setListOfPaymentData(allPaymentData);
                }
            }
        });
        deleteRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RecordStoreAppData recordStoreAppData = inventoryJList.getSelectedValue();
                if (recordStoreAppData == null) {
                    JOptionPane.showMessageDialog(RecordStoreAppForm.this, "Please select the record to delete");
                } else if (!ReturnToComboBox.getSelectedItem().equals("Yes")) {
                    JOptionPane.showMessageDialog(RecordStoreAppForm.this, "Please select 'Yes' from the 'Return to Consignor?' combox box");
                }
                if (recordStoreAppData != null && ReturnToComboBox.getSelectedItem().equals("Yes")) {
                    int delete = JOptionPane.showConfirmDialog(RecordStoreAppForm.this, "Are you sure you want to delete this item?",
                            "Delete", JOptionPane.OK_CANCEL_OPTION);
                    if (delete == JOptionPane.OK_OPTION) {
                        recordStoreAppManager.delete(recordStoreAppData);
                        ArrayList<RecordStoreAppData> recordObject1 = recordStoreAppManager.getAllData();
                        setListData(recordObject1);
                        JOptionPane.showMessageDialog(RecordStoreAppForm.this, "The selected item has been deleted from the list");
                    }
                }
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quit = JOptionPane.showConfirmDialog(
                        RecordStoreAppForm.this, "Are you sure you want to quit?",
                        "Quit", JOptionPane.OK_CANCEL_OPTION);
                if (quit == JOptionPane.OK_OPTION) {

                    System.exit(0);
                }
            }
        });

        daysInInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayNumberOfDays();
            }
        });
        showInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });



    }
    private void displayNumberOfDays(){
        java.sql.Date displayDays = inventoryJList.getSelectedValue().getDate();
        Date now = new Date();
        long date = now.getTime() - displayDays.getTime();
        long msInDay = 1000*60*60*24;
        long days = date/msInDay;
        String StringDays = Long.toString(days);
        int intDays = Integer.parseInt(StringDays);
        displayDaysJLabel.setText(StringDays);
        if(intDays>30){
            notifyConsignorJLabel.setText("Notify consignor to pick item");
        }else{
            int daysDiff = 30-intDays;
            String daysDiffString = Integer.toString(daysDiff);
            notifyConsignorJLabel.setText(daysDiffString+" days to stay in this list");
        }
    }
    void setListData(ArrayList<RecordStoreAppData> data) {
        inventoryListModel.clear();
        //add cubes object to display list model.
        for (RecordStoreAppData rob : data) {
            inventoryListModel.addElement(rob);
        }
    }
    void setListOfSoldData(ArrayList<SoldItem> soldData){
        soldItemListModel.clear();
        for(SoldItem robs : soldData){
            soldItemListModel.addElement(robs);
        }
    }
    void setListOfPaymentData(ArrayList<PaymentInfo> paymentData){
        paymentListModel.clear();
        for(PaymentInfo pob: paymentData){
            paymentListModel.addElement(pob);
        }
    }

}


