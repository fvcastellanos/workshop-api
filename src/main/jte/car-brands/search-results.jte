@import net.cavitos.workshop.model.entity.CarBrandEntity
@import net.cavitos.workshop.web.controller.ui.model.SearchModel
@import java.util.List

@param List<CarBrandEntity> entities
@param SearchModel searchModel

<table class="table table-bordered table-striped table-hover">
    <thead>
        <tr>
            <th class="text-center">#</th>
            <th class="text-center">Nombre</th>
            <th class="text-center">Estado</th>
        </tr>
    </thead>
    <tbody>
        @for (var entity : entities)
            <tr>
                <td class="text-center">
                    <a href="${entity.getId()}">
                        <i class="bi bi-pencil"></i>
                    </a>
                </td>
                <td>
                    ${entity.getName()}
                </td>
                <td>
                    ${entity.getActive() == 1 ? "Activo" : "Inactivo"}
                </td>
            </tr>
        @endfor
    </tbody>
</table>

<div>
    @template.layouts.pagination(
        page = searchModel.getPage(),
        size = searchModel.getSize(),
        totalElements = searchModel.getTotalElements(),
        totalPages = searchModel.getTotalPages()
    )
</div>