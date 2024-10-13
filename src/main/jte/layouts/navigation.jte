
@param String basePath = null

    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"> </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${basePath}">Inicio</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Catálogos
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="${basePath}/car-brands">Marcas de Vehículos</a></li>
                            <li><a class="dropdown-item" href="#">Contactos</a></li>
                            <li><a class="dropdown-item" href="#">Productos</a></li>
                            <li><a class="dropdown-item" href="#">Categorías de Productos</a></li>
                            <li><a class="dropdown-item" href="#">Tipos de Movimientos</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/">Órdenes</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Movimientos
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Facturas Proveedores</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Inventario
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Inicial</a></li>
                            <li><a class="dropdown-item" href="#">Movimientos</a></li>
                            <li><a class="dropdown-item" href="#">Existencias</a></li>
                        </ul>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="#">Link</a>
                    </li>
                    <%--                    <li class="nav-item dropdown">--%>
                    <%--                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">--%>
                    <%--                            Dropdown--%>
                    <%--                        </a>--%>
                    <%--                        <ul class="dropdown-menu">--%>
                    <%--                            <li><a class="dropdown-item" href="#">Action</a></li>--%>
                    <%--                            <li><a class="dropdown-item" href="#">Another action</a></li>--%>
                    <%--                            <li><hr class="dropdown-divider"></li>--%>
                    <%--                            <li><a class="dropdown-item" href="#">Something else here</a></li>--%>
                    <%--                        </ul>--%>
                    <%--                    </li>--%>
                    <%--                    <li class="nav-item">--%>
                    <%--                        <a class="nav-link disabled" aria-disabled="true">Disabled</a>--%>
                    <%--                    </li>--%>
                </ul>
                <%--                <form class="d-flex" role="search">--%>
                <%--                    <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">--%>
                <%--                    <button class="btn btn-outline-success" type="submit">Search</button>--%>
                <%--                </form>--%>
            </div>
        </div>
        <div class='justify-content-end'>
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Logged User
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
