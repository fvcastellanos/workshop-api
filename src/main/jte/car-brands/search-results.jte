@import net.cavitos.workshop.model.entity.CarBrandEntity
@import net.cavitos.workshop.web.controller.ui.model.SearchRequest
@import java.util.List

@param List<CarBrandEntity> entities
@param SearchRequest searchRequest

@if (entities != null)

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
                    &nbsp;
                    <a href="${entity.getId()}/lines">
                        <i class="bi bi-view-stacked"></i>
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
        @template.layouts.pagination(page = searchRequest.getPage(), size = searchRequest.getSize())
    </div>

@else
    <table class="table table-bordered">
        <tr>
            <td class="text-center">
                No data
            </td>
        </tr>
    </table>
@endif

