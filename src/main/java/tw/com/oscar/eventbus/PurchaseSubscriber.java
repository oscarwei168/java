package tw.com.oscar.eventbus;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import tw.com.oscar.eventbus.events.CashPurchaseEvent;
import tw.com.oscar.eventbus.events.CreditCardPurchaseEvent;
import tw.com.oscar.eventbus.events.PurchaseEvent;

/**
 * @author oscarwei
 * @since 2015/1/15
 */
public class PurchaseSubscriber {

    @Subscribe
    @AllowConcurrentEvents
    public void handlePurchaseEvent(PurchaseEvent event) {
        System.out.println("Item : " + event.getItem());
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleCashPurchaseEvent(CashPurchaseEvent event) {
        System.out.printf("Item : %s, Cash amount : %d", event.getItem(), event.getAmount());
        System.out.println();
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleCreditPurchaseEvent(CreditCardPurchaseEvent event) {
        System.out.printf("Item : %s, Credit NO : %s, Amount : %d", event.getItem(), event
                .getCreditNumber(), event.getAmount());
        System.out.println();
    }

}
