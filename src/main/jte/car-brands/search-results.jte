@import net.cavitos.workshop.model.entity.CarBrandEntity
@import java.util.List

@param List<CarBrandEntity> entities

<table class="table table-striped">
    <tr>
        <th>#</th>
        <th>Nombre</th>
        <th>Activo</th>
    </tr>
    @for (var entity : entities)
        <tr>
            <td>
                ${entity.getId()}
            </td>
            <td>
                ${entity.getName()}
            </td>
            <td>
                ${entity.getActive()}
            </td>
        </tr>
    @endfor
</table>