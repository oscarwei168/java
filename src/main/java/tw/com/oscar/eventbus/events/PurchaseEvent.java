package tw.com.oscar.eventbus.events;

/**
 * @author oscarwei
 * @since 2015/1/15
 */
public abstract class PurchaseEvent {

    private String item;

    public PurchaseEvent(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
