package net.cavitos.workshop.configuration;

import net.cavitos.workshop.event.observers.WorkOrderInvoiceDetailObserver;
import net.cavitos.workshop.event.subject.InvoiceDetailObservable;
import net.cavitos.workshop.model.repository.WorkOrderDetailRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    @Bean
    public WorkOrderInvoiceDetailObserver workOrderInvoiceDetailObserver(final WorkOrderDetailRepository workOrderDetailRepository) {

        return new WorkOrderInvoiceDetailObserver(workOrderDetailRepository);
    }

    @Bean
    public InvoiceDetailObservable invoiceDetailObservable(final WorkOrderInvoiceDetailObserver workOrderInvoiceDetailObserver) {

        final var observable = new InvoiceDetailObservable();
        observable.addObserver(workOrderInvoiceDetailObserver);

        return observable;
    }
}
