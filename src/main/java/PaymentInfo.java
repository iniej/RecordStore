/**
 * Created by Iniebiyo Joshua on 5/8/2017.
 */
public class PaymentInfo {
    String name;
    double share;
    double paid;
    double due;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getShare() {
        return share;
    }

    public void setShare(double share) {
        this.share = share;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getDue() {
        return due;
    }

    public void setDue(double due) {
        this.due = due;
    }

    PaymentInfo(String conName, double sAmount, double pAmount, double dAmount){
        name = conName;
        share = sAmount;

        paid = pAmount;
        due = dAmount;
    }

    @Override
    public String toString(){
        return "Consignor: " + this.name + " Share % : "+ share + " Paid amount: " + paid + " Amount Due: " + due;
    }
}
