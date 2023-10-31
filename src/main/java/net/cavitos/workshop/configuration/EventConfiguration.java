package net.cavitos.workshop.configuration;

import com.google.common.eventbus.EventBus;
import net.cavitos.workshop.event.listener.InvoiceDetailInventoryListener;
import net.cavitos.workshop.event.observers.InventoryInvoiceDetailObserver;
import net.cavitos.workshop.event.observers.WorkOrderInvoiceDetailObserver;
import net.cavitos.workshop.event.subject.InvoiceDetailObservable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    @Bean
    public InvoiceDetailObservable invoiceDetailObservable(final WorkOrderInvoiceDetailObserver workOrderInvoiceDetailObserver,
                                                           final InventoryInvoiceDetailObserver inventoryInvoiceDetailObserver) {

        final var observable = new InvoiceDetailObservable();
        observable.addObserver(workOrderInvoiceDetailObserver);
        observable.addObserver(inventoryInvoiceDetailObserver);

        return observable;
    }

    @Bean
    public EventBus eventBus(final InvoiceDetailInventoryListener invoiceDetailInventoryListener) {

        final var eventBus = new EventBus("Application Event Bus");

        eventBus.register(invoiceDetailInventoryListener);

        return eventBus;
    }
}
