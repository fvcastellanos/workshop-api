@param int page
@param int size
@param long totalElements
@param int totalPages

<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-end">
        <li class="page-item ${page == 0 ? "disabled" : ""}">
            <a class="page-link" href="?page=${page - 1}&size=${size}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>

        @if (totalElements > 0)

            <li class="page-item ${page == 0 ? "active" : ""}">
                <a class="page-link" href="?page=0&size=${size}">1</a>
            </li>

            @if (page > 2)
                <li class="page-item disabled">
                    <span class="page-link">...</span>
                </li>
            @endif

            @for (int i = Math.max(1, Math.min(page - 2, totalPages - 5)); i < Math.min(totalPages - 1, Math.max(page + 3, 5)); i++)
                <li class="page-item ${page == i ? "active" : ""}">
                    <a class="page-link" href="?page=${i}&size=${size}">${i + 1}</a>
                </li>
            @endfor

            @if (page < totalPages - 3)
                <li class="page-item disabled">
                    <span class="page-link">...</span>
                </li>
            @endif

            @if (totalPages > 1)
                <li class="page-item ${page == totalPages - 1 ? "active" : ""}">
                    <a class="page-link" href="?page=${totalPages - 1}&size=${size}">${totalPages}</a>
                </li>
            @endif

        @endif

        <li class="page-item ${totalPages == 0 || page == totalPages - 1 ? "disabled" : ""}">
            <a class="page-link" href="?page=${page + 1}&size=${size}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>

    </ul>
<%--    <form method="get" action="" class="form-inline">--%>
<%--        <label for="size" class="mr-2">Elements per page:</label>--%>
<%--        <select id="size" name="size" class="form-control" onchange="this.form.submit()">--%>
<%--            <option value="10" >10</option>--%>
<%--            <option value="20" >20</option>--%>
<%--            <option value="50" >50</option>--%>
<%--            <option value="100" >100</option>--%>
<%--        </select>--%>
<%--        <input type="hidden" name="page" value="${page}">--%>
<%--    </form>--%>
</nav>