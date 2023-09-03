package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.model.status.WorkOrderStatus;
import net.cavitos.workshop.domain.model.web.WorkOrderPartial;
import net.cavitos.workshop.model.entity.WorkOrderEntity;
import net.cavitos.workshop.model.repository.CarLineRepository;
import net.cavitos.workshop.model.repository.ContactRepository;
import net.cavitos.workshop.model.repository.WorkOrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static net.cavitos.workshop.common.assertion.CommonAssertions.assertBusinessException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkOrderServiceTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private CarLineRepository carLineRepository;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private WorkOrderService workOrderService;

    @AfterEach
    void tearDown() {

        verifyNoMoreInteractions(workOrderRepository, carLineRepository, contactRepository);
    }

    @Test
    void testFindById() {

        when(workOrderRepository.findById("id"))
                .thenReturn(Optional.of(buildWorkOrderEntity()));

        final var entity = workOrderService.findById("tenant", "id");

        assertThat(entity)
                .isNotNull()
                .hasFieldOrPropertyWithValue("status", WorkOrderStatus.IN_PROGRESS.value());

        verify(workOrderRepository).findById("id");
    }

    @Test
    void testFindByIdWhenNotFound() {

        when(workOrderRepository.findById("id"))
                .thenReturn(Optional.empty());

        assertBusinessException(() -> workOrderService.findById("tenant", "id"),
                "Work Order not found",
                HttpStatus.NOT_FOUND);

        verify(workOrderRepository).findById("id");
    }

    @Test
    void testFindByIdWhenTenantIsDifferent() {

        final var entity = buildWorkOrderEntity();
        entity.setTenant("another tenant");

        when(workOrderRepository.findById("id"))
                .thenReturn(Optional.of(entity));

        assertBusinessException(() -> workOrderService.findById("tenant", "id"),
                "Work Order not found",
                HttpStatus.NOT_FOUND);

        verify(workOrderRepository).findById("id");
    }

    @Test
    void testUpdateStatus() {

        final var entity = buildWorkOrderEntity();

        final var workOrderPartial = new WorkOrderPartial();
        workOrderPartial.setStatus(WorkOrderStatus.CLOSED.name());

        when(workOrderRepository.findById("id"))
                .thenReturn(Optional.of(entity));

        when(workOrderRepository.save(any(WorkOrderEntity.class)))
                .thenReturn(entity);

        final var result = workOrderService.updateStatus("tenant", "id", workOrderPartial);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("status", WorkOrderStatus.CLOSED.value());

        verify(workOrderRepository).findById("id");
        verify(workOrderRepository).save(any(WorkOrderEntity.class));
    }

    // --------------------------------------------------------------------------------

    private WorkOrderEntity buildWorkOrderEntity() {

        return WorkOrderEntity.builder()
                .status(WorkOrderStatus.IN_PROGRESS.value())
                .id("id")
                .tenant("tenant")
                .build();
    }
}
