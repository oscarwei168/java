package tw.com.oscar.eventbus.events;

/**
 * @author oscarwei
 * @since 2015/1/15
 */
public class CashPurchaseEvent extends PurchaseEvent {

    private int amount;

    public CashPurchaseEvent(String item, int amount) {
        super(item);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
