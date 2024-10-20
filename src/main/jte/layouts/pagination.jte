
@param int page
@param int size

<ul class="pagination justify-content-end">
    <select name="size" class="form-select" style="width: 5em">
        <option value="10" selected="${size == 10}">10</option>
        <option value="25" selected="${size == 25}">25</option>
        <option value="50" selected="${size == 50}">50</option>
        <option value="200" selected="${size == 200}">200</option>
    </select>
    &nbsp;
    <li class="page-item">
        <a class="page-link" href="#" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
        </a>
    </li>
<%--        <li class="page-item"><a class="page-link" href="#">1</a></li>--%>
<%--        <li class="page-item"><a class="page-link" href="#">2</a></li>--%>
<%--        <li class="page-item"><a class="page-link" href="#">3</a></li>--%>
    <li class="page-item">
        <a class="page-link" href="#" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
        </a>
    </li>
</ul>
