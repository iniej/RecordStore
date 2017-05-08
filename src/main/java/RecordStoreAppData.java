/**
 * Created by Iniebiyo Joshua on 4/25/2017.
 */

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
public class RecordStoreAppData {

    int ID;
    String ConsignorName;
    String phone;
    String artistName;
    String title;
    double price;
    java.sql.Date date;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getConsignorName() {
        return ConsignorName;
    }

    public void setConsignorName(String consignorName) {
        this.ConsignorName = consignorName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public java.sql.Date getDate() {
        return date;
    }

    //constructor
    RecordStoreAppData(String cName, String pn, String aName, String t, double pr, java.sql.Date d){
        //ID = id;
        ConsignorName = cName;
        phone = pn;
        artistName = aName;
        title = t;
        price = pr;
        date = d;
    }
    @Override
    public String toString(){
        return "Consignor: "+ConsignorName + "   Phone: "+phone + "   Artist: "+artistName + "   Title: "+title + "   Price: "+price;
    }
}






