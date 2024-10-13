@import net.cavitos.workshop.model.entity.CarBrandEntity
@import net.cavitos.workshop.web.controller.ui.model.SearchRequest
@import org.springframework.security.web.csrf.CsrfToken
@import java.util.List

@param String uiHome
@param SearchRequest searchRequest
@param CsrfToken csrfToken
@param List<CarBrandEntity> items

@template.layouts.main(basePath = uiHome, content = @`

    <div class="container-fluid top-separator">
        <div class="row justify-content-center">
            <div class="col-10">
                <h3>Marca de Vehículos</h3>
                <div class="top-separator">
                    <div class="card">
                        <form method="post">
                            <div class="card-body">
                                    <div class="row md-6">
                                        <div class="col-8">
                                            <label class="form-label">Texto</label>
                                            <input class="form-control" name="text" type="text" placeholder="Texto" value="${searchRequest.getText()}" />
                                        </div>
                                        <div class="col-2">
                                            <label class="form-label">Activo</label>
                                            <select class="form-select" name="active">
                                                <option value="1" selected="${searchRequest.getActive() == 1}">Si</option>
                                                <option value="0" selected="${searchRequest.getActive() == 0}">No</option>
                                            </select>
                                        </div>
                                    </div>
                            </div>
                            <div class="card-footer">
                                <button type="submit" class="btn btn-secondary">Buscar</button>
                                &nbsp;
                                <button type="button" class="btn btn-primary">Agregar Fabricante</button>
                            </div>
                            <input type="hidden" name="${csrfToken.getParameterName()}" value="${csrfToken.getToken()}"/>
                        </form>
                    </div>

                </div>
                <div class="top-separator">
                    @template.car-brands.search-results(entities = items)
                </div>
            </div>
        </div>
    </div>
`)

