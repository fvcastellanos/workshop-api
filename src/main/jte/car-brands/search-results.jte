@import net.cavitos.workshop.model.entity.CarBrandEntity
@import java.util.List

@param List<CarBrandEntity> entities

<table class="table table-bordered table-striped table-hover">
    <thead>
        <tr>
            <th class="text-center">#</th>
            <th class="text-center">Nombre</th>
            <th class="text-center">Activo</th>
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
                    ${entity.getActive()}
                </td>
            </tr>
        @endfor
    </tbody>
</table>

<div>
    @template.layouts.pagination()
</div>