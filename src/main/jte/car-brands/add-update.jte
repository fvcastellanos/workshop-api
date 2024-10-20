@import net.cavitos.workshop.web.controller.ui.model.CarBrandModel
@import org.springframework.security.web.csrf.CsrfToken

@param String uiHome
@param CsrfToken csrfToken
@param boolean isUpdate = false
@param CarBrandModel carBrandModel

@template.layouts.main(basePath = uiHome, content = @`

    <div class="container-fluid top-separator">
        <div class="row justify-content-center">
            <div class="col-10">
                <h3>${isUpdate ? "Modificar Vehículo" : "Agregar Vehículo"}</h3>
                <form method="post" modelAttribute="carBrandModel">
                    <div class="top-separator">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-12">
                                        <label class="form-label">Nombre</label>
                                        <input class="form-control" name="name" type="text" placeholder="Texto" value="${carBrandModel.getName()}" />
                                    </div>
                                    <div class="col-12">
                                        <label class="form-label">Descripción</label>
                                        <textarea class="form-control" name="description" rows="3" >${carBrandModel.getDescription()}</textarea>
                                    </div>
                                    @if (isUpdate)
                                        <div class="col-12">
                                            <label class="form-label">Activo</label>
                                            <select class="form-select" name="active">
                                                <option value="1" selected="${carBrandModel.getActive() == 1}">Si</option>
                                                <option value="0" selected="${carBrandModel.getActive() == 0}">No</option>
                                            </select>
                                        </div>
                                    @endif
                                </div>
                            </div>
                            <div class="card-footer">
                                <a href="${uiHome}/car-brands" class="btn btn-secondary">Regresar</a>
                                <button type="submit" class="btn btn-primary">Guardar</button>
                            </div>
                            <input type="hidden" name="${csrfToken.getParameterName()}" value="${csrfToken.getToken()}"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
`)