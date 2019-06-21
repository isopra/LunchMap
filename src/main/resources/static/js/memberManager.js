$(function () {
    $(document).ready(function () {
        var table = $('#table').DataTable({
            lengthChange: false,
            searching: true,
            ordering: true,
            info: false,
            paging: true,
            columnDefs: [
                {targets: [0, 1], width: 50}
            ]

        });

        $('#table tbody').on('click', 'tr', function () {
            var data = table.row(this).data();

            $('input:hidden[name="login_id"]').val(data[0]);
            $('#form').submit();

            // var token = $("meta[name='_csrf']").attr("content");
            // var header = $("meta[name='_csrf_header']").attr("content");
            // $(document).ajaxSend(function(e, xhr, options) {
            //     xhr.setRequestHeader(header, token);
            // });
            //
            // $.ajax({
            //     type: "POST",
            //     dataType: "json",
            //     url: "/member/edit_by_manager",
            //     data: {"id": data[0]}
            // })
        })
    });
});