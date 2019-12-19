$(function () {
    $("#jqGrid").jqGrid({
        // 请求后台json数据的url
        url: '/admin/tags/list',
        datatype: "json",　//从后台返回的数据类型
        // 列表模型
        //常用到的属性：name：列显示的名称； index：传到服务器端用来排列用的列名称；width：列宽度；align：对齐方式；sortable：是否可以排序；
        colModel: [
            {label: 'id', name: 'tagId', index: 'tagId', width: 50, key: true, hidden: true},
            {label: '标签名称', name: 'tagName', index: 'tagName', width: 240},
            {label: '文章数', name: 'tagArticleCount', index: 'tagArticleCount', width: 240}

        ],
        height: 560,　　//表格的高度

        rowNum: 10,     //  一页显示的行记录数，这个参数要被传递到后台

        rowList: [10, 20, 50],　 // 一个下拉选择框，用来改变显示记录数，当选择时会覆盖rowNum参数传递到后台。翻页控制条中 每页显示记录数可选集合
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,   //不在添加左侧行号
        rownumWidth: 20,　
        autowidth: true,    //自动列宽
        multiselect: true,　　//定义是否可以多选
        pager: "#jqGridPager",　　//Grid显示在id为jqGridPager的标签里面
        //后台将列表的数据，当前的页数，总页数还有总记录数发送前端
        jsonReader: {
            root: "data.list",      //列表数据
            page: "data.currPage",　　//当前页数
            total: "data.totalPage",    //总页数
            records: "data.totalCount"　　//总记录数
        },
        // 发送一个map给后台：page(第几页) limit(每页个数)
        prmNames: {
            //设置初始的页码(默认值：1)
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });


});


/**
 * jqGrid重新加载
 */
function reload() {
    //设置page来表示获取到的数据，需要重新加载的数据
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page　　　//将数据重新加载
    }).trigger("reloadGrid");
}

//新增标签
function tagAdd() {
    reset();
    $('.modal-title').html('标签添加');
    $('#tagModal').modal('show');
}

//绑定modal上的保存按钮
//需求
//添加标签后点击确认按钮
//获取tagName的值并且判断是否符合标准，如果不符合提示信息，如果符合使用serialize将表单序列化，并且记录下url和id
//如果id不为null,则更新原有的tags
//如果id为null，则通过ajax将url,以及表单传给后端，如果成功则将tags添加框隐藏，并且弹出提示框，如果失败，弹出错误提示框
$('#saveButton').click(function () {
    //获取tagName的值
    var tagName = $("#tagName").val();
    //tagName不符合
    // 正则匹配2-18位的中英文字符串
    if (!validCN_ENString2_18(tagName)) {
        //错误信息栏展示
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的标签名称！");
    } else {
        var params = $("#tagForm").serialize();　　//序列化表单
        var url = '/admin/tags/save';
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = '/admin/tags/update';　　　//更新url
        }
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            data: params,
            success: function (result) {
                if (result.resultCode == 200) {
                    $('#tagModal').modal('hide');
                    swal("保存成功", {
                        icon: "success",  //重新加载
                    });
                    reload();
                }
                else {
                    $('#tagModal').modal('hide'); //隐藏添加框

                    swal(result.message, {　　//弹出错误提示
                        icon: "error",
                    });
                }
                ;
            },
            error: function () {
                swal("操作失败", {　　　//弹出操作失败
                    icon: "error",
                });
            }
        });
    }
});

function tagEdit() {
    reset();
    var id = getSelectedRow();  //获取被选中的Row
    if (id == null) {
        return;
    }
    $('.modal-title').html('标签编辑');
    $('#tagModal').modal('show');
    $("#tagId").val(id);
}

function deleteTag() {
    var ids = getSelectedRows();        //获取被选中的Row
    if (ids == null) {
        return;
    }
    //当选中的行的id不为空的时候，我们选择弹框，当用户点击删除按钮的时候，我们通过ajax函数将数据（被选中的要删除的row，以及他的路径）以post的方式发送给后台，
    //并且使用JSON.stringify() 方法将 JavaScript 对象转换为字符串
    //如果删除成功，使用swal弹框提示信息
    //如果删除失败，也使用swal弹框提示信息
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/tags/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),   //使用 JSON.stringify() 方法将 JavaScript 对象转换为字符串
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}
//
function reset() {
    $("#tagName").val('');
    $("#tagIcon option:first").prop("selected", 'selected');
}