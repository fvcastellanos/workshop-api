package net.cavitos.workshop.event.listener;

import com.google.common.eventbus.Subscribe;
import net.cavitos.workshop.event.model.InvoiceDetailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InventoryInvoiceDetailListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryInvoiceDetailListener.class);

    @Subscribe
    public void handleEvent(InvoiceDetailEvent invoiceDetailEvent) {

        LOGGER.info("Invoice Detail Event of type={} with invoice_detail_id={}",
                invoiceDetailEvent.getEventType(), invoiceDetailEvent.getInvoiceDetailEntity().getId());
    }
}
