package net.cavitos.workshop.model.repository;

import net.cavitos.workshop.model.entity.ProductEntity;
import net.cavitos.workshop.model.entity.WorkOrderDetailEntity;
import net.cavitos.workshop.model.entity.WorkOrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface WorkOrderDetailRepository extends PagingAndSortingRepository<WorkOrderDetailEntity, String> {

    Optional<WorkOrderDetailEntity> findByWorkOrderEntityAndProductEntityAndTenant(WorkOrderEntity workOrderEntity,
                                                                                   ProductEntity productEntity,
                                                                                   String tenant);
}
