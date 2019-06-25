$(function () {
    $(document).ready(function () {
        // 日本語化
        // $.extend( $.fn.dataTable.defaults, {
        //     language: {
        //         url: "http://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Japanese.json"
        //     }
        // });

        var table = $('#table').DataTable({
            lengthChange: false,
            // searching: true,
            // // ordering: true,
            // info: true,
            // paging: true,
            // columnDefs: [
            //     {targets: [0, 1], width: 50}
            // ],
            // jQueryUI: true,
            // autoWidth: true,
        });

        $('#table tbody').on('click', 'tr', function () {
            var data = table.row(this).data();

            $('input:hidden[name="login_id"]').val(data[0]);
            $('#form').submit();
        });
    });
});

$(window).on("load resize", function () {
    console.log(window.innerWidth);
    if (window.innerWidth <= 1280) {
        $("#table").addClass("table-sm");
    }else {
        $("#table").removeClass("table-sm");
    }
});