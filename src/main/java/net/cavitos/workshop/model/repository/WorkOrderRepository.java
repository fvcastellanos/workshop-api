package net.cavitos.workshop.model.repository;

import net.cavitos.workshop.model.entity.WorkOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface WorkOrderRepository extends PagingAndSortingRepository<WorkOrderEntity, String> {

    Page<WorkOrderEntity> findByContactEntityNameContainsIgnoreCaseAndNumberContainsIgnoreCaseAndStatusAndTenant(String contactName,
                                                                                                                 String number,
                                                                                                                 String status,
                                                                                                                 String tenant,
                                                                                                                 Pageable pageable);

    Optional<WorkOrderEntity> findByNumberEqualsIgnoreCaseAndTenant(final String number, final String tenant);

    Optional<WorkOrderEntity> findByIdAndTenant(final String id, final String tenant);
}
