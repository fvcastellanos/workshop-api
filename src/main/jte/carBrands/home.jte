
@param String basePath

@template.contentWithNavigation(basePath = basePath, content = @`

    <div class="container-fluid top-separator">
        <div class="row justify-content-center">
            <div class="col-10">
                <h3>Marca de Vehículos</h3>
                <div class="top-separator">
                    <div class="card">
                        <div class="card-body">
                            <form>
                                <div class="row md-6">
                                    <div class="col-8">
                                        <label class="form-label">Texto</label>
                                        <input class="form-control" type="text" placeholder="Texto" />
                                    </div>
                                    <div class="col-2">
                                        <label class="form-label">Activo</label>
                                        <select class="form-select">
                                            <option value="1">Si</option>
                                            <option value="0">No</option>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-secondary">Buscar</button>
                            &nbsp;
                            <button type="button" class="btn btn-primary">Agregar Fabricante</button>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    
    @template.test(text=basePath)
`)
