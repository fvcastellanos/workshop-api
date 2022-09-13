package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.web.WorkOrderDetail;
import net.cavitos.workshop.domain.model.web.common.CommonInvoice;
import net.cavitos.workshop.domain.model.web.common.CommonProduct;
import net.cavitos.workshop.model.entity.WorkOrderDetailEntity;

public class WorkOrderDetailTransformer {

    private WorkOrderDetailTransformer() {
    }

    public static WorkOrderDetail toWeb(final WorkOrderDetailEntity entity) {

        final var workOrderEntity = entity.getWorkOrderEntity();
        final var productEntity = entity.getProductEntity();
        final var invoiceDetailEntity = entity.getInvoiceDetailEntity();

        final var product = new CommonProduct();
        product.setCode(productEntity.getCode());
        product.setType(productEntity.getType());
        product.setName(productEntity.getType());

        final var detail = new WorkOrderDetail();
        detail.setId(entity.getId());
        detail.setOrderId(workOrderEntity.getId());
        detail.setQuantity(entity.getQuantity());
        detail.setUnitPrice(entity.getUnitPrice());
        detail.setProduct(product);
        detail.setInvoiceDetailId(invoiceDetailEntity.getId());

        return detail;
    }
}
