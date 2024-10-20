@import net.cavitos.workshop.model.entity.CarBrandEntity
@import net.cavitos.workshop.web.controller.ui.model.SearchModel
@import org.springframework.security.web.csrf.CsrfToken
@import java.util.List

@param String uiHome
@param SearchModel searchModel
@param CsrfToken csrfToken
@param List<CarBrandEntity> items

@template.layouts.main(basePath = uiHome, content = @`

    <div class="container-fluid top-separator">
        <div class="row justify-content-center">
            <div class="col-10">
                <h3>Marca de Vehículos</h3>
                <form method="post" modelAttribute="searchModel">
                    <div class="top-separator">
                        <div class="card">
                                <div class="card-body">
                                        <div class="row md-6">
                                            <div class="col-8">
                                                <label class="form-label">Texto</label>
                                                <input class="form-control" name="text" type="text" placeholder="Texto" value="${searchModel.getText()}" />
                                            </div>
                                            <div class="col-2">
                                                <label class="form-label">Activo</label>
                                                <select class="form-select" name="active">
                                                    <option value="1" selected="${searchModel.getActive() == 1}">Si</option>
                                                    <option value="0" selected="${searchModel.getActive() == 0}">No</option>
                                                </select>
                                            </div>
                                        </div>
                                </div>
                                <div class="card-footer">
                                    <button type="submit" class="btn btn-secondary">Buscar</button>
                                    <a href="${uiHome}/car-brands/new" class="btn btn-primary">Agregar Fabricante</a>
                                </div>
                                <input type="hidden" name="${csrfToken.getParameterName()}" value="${csrfToken.getToken()}"/>

                        </div>
                    </div>
                    <div class="top-separator">
                        @template.car-brands.search-results(searchModel = searchModel, entities = items)
                    </div>
                </form>
            </div>
        </div>
    </div>
`)

