package net.cavitos.workshop.configuration;

import net.cavitos.workshop.event.observers.WorkOrderInvoiceDetailObserver;
import net.cavitos.workshop.event.subject.InvoiceDetailObservable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    @Bean
    public InvoiceDetailObservable invoiceDetailObservable(final WorkOrderInvoiceDetailObserver workOrderInvoiceDetailObserver) {

        final var observable = new InvoiceDetailObservable();
        observable.addObserver(workOrderInvoiceDetailObserver);

        return observable;
    }
}
