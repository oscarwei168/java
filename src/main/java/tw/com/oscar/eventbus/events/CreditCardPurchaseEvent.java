package tw.com.oscar.eventbus.events;

/**
 * @author oscarwei
 * @since 2015/1/15
 */
public class CreditCardPurchaseEvent extends PurchaseEvent {

    private int amount;
    private String creditNumber;

    public CreditCardPurchaseEvent(String item, String creditNumber, int amount) {
        super(item);
        this.creditNumber = creditNumber;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreditNumber() {
        return creditNumber;
    }

    public void setCreditNumber(String creditNumber) {
        this.creditNumber = creditNumber;
    }
}
