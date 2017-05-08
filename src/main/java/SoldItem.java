/**
 * Created by Iniebiyo Joshua on 5/6/2017.
 */
public class SoldItem {

    String Consignor;
    double SoldPrice;
    String title;

    public double getSoldPrice() {
        return SoldPrice;
    }

    public String getConsignor() {
        return Consignor;
    }

    public void setConsignor(String consignor) {
        Consignor = consignor;
    }

    public void setSoldPrice(double soldPrice) {
        SoldPrice = soldPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    SoldItem(String consignor, double sPrice, String t) {
        Consignor = consignor;
        SoldPrice = sPrice;
        title = t;

    }
    @Override
    public String toString(){
        return "Consignor: "+ this.Consignor+ "  Title: "+ this.title + "  Sold Price: "+this.SoldPrice;
    }
}


