package net.cavitos.workshop.model.repository;

import net.cavitos.workshop.model.entity.InvoiceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceEntity, String> {
}
