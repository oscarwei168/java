package tw.com.oscar.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import tw.com.oscar.eventbus.events.CashPurchaseEvent;
import tw.com.oscar.eventbus.events.CreditCardPurchaseEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author oscarwei
 * @since 2015/1/15
 */
public class Sample {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        try {
            AsyncEventBus eventBus = new AsyncEventBus(pool);

            eventBus.register(new PurchaseSubscriber());

            CashPurchaseEvent event = new CashPurchaseEvent("Notebook", 10);
            eventBus.post(event);

            CreditCardPurchaseEvent creditCardPurchaseEvent = new CreditCardPurchaseEvent("PC",
                    "XX-XXXXX-XXXXXXX-XXX", 100);
            eventBus.post(creditCardPurchaseEvent);
        } finally {
            pool.shutdown();
        }

    }
}
