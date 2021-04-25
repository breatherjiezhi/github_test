<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<title>套餐审核管理</title>
<div class="row">
    <div class="col-xs-12 col-sm-12">

        <div id="fileDivId"></div>

        <table id="grid-table"></table>
        <div id="grid-pager"></div>
        <div class="widget-box" style="display:none" id="editDivId">
            <div class="widget-box" style="display:none" id="rejectInfoDivId"></div>
        </div>
        <div class="widget-box" style="display:none" id="setStationDivId"></div>
    </div>
</div>
<script type="text/javascript">

    var scripts = [null, '${ctxStatic}/assets/js/fuelux/fuelux.spinner.js', '${ctxStatic}/assets/js/date-time/bootstrap-datepicker.js', '${ctxStatic}/assets/js/date-time/bootstrap-datepicker.zh-CN.min.js', null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        jQuery(function ($) {
            var qCode = sessionStorage.getItem("qCode");
            typeChange(qCode);

            var select2 = $('.select2');
            var select2width = select2.parent().width();
            $('.select2').css('width', select2width).select2({allowClear: true});
            //选择框
            if (!ace.vars['touch']) {
                $('.chosen-select').chosen({allow_single_deselect: true});
                $(window).off('resize.chosen').on('resize.chosen', function () {
                    $('.chosen-select,.select2').each(function () {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    });
                }).trigger('resize.chosen');
            }

            var grid_selector = "#grid-table";
            var pager_selector = "#grid-pager";
            var toolbarTop = grid_selector + '_toppager';

            $('.input-daterange').datepicker({
                autoclose: true,
                zIndexOffset: 100,
                format: "yyyy-mm-dd",
                language: "zh-CN"
            });



            var reSizeHeight = function () {
                var strs = $.getWindowSize().toString().split(",");
                var jqgrid_height = strs[0] - 340;  //随着搜索部分的高度变化，设置这里的高度，保持page条在底部
                $(grid_selector).jqGrid('setGridHeight', jqgrid_height);
            };
            jQuery(grid_selector).jqGrid({
                datatype: "json", //将这里改为使用JSON数据
                url: '${ctx}/pzMenu/findMenuListByNoExamine', //这是数据的请求地址
                height: 'auto',
                autowidth: true,
                jsonReader: {
                    root: "rows",   // json中代表实际模型数据的入口
                    page: "page",   // json中代表当前页码的数据
                    total: "total", // json中代表页码总数的数据
                    records: "records", // json中代表数据行总数的数据
                    repeatitems: false
                },
                prmNames: {
                    page: "pageNo",    // 表示请求页码的参数名称
                    rows: "rows",    // 表示请求行数的参数名称
                    sort: "sidx", // 表示用于排序的列名的参数名称
                    order: "sord", // 表示采用的排序方式的参数名称
                    search: "_search", // 表示是否是搜索请求的参数名称
                    nd: "nd", // 表示已经发送请求的次数的参数名称
                    id: "id", // 表示当在编辑数据模块中发送数据时，使用的id的名称
                    oper: "oper",    // operation参数名称（我暂时还没用到）
                    editoper: "edit", // 当在edit模式中提交数据时，操作的名称
                    addoper: "add", // 当在add模式中提交数据时，操作的名称
                    deloper: "del", // 当在delete模式中提交数据时，操作的名称
                    subgridid: "id", // 当点击以载入数据到子表时，传递的数据名称
                    npage: null,
                    totalrows: "totalrows" // 表示需从Server得到总共多少行数据的参数名称，参见jqGrid选项中的rowTotal
                },
                colNames: [
                    'id',
                    '<span data-locale="menuName">菜单名称</span>',
                    '<span data-locale="menuDescription">菜单描述</span>',
                    '<span data-locale="menuLimited">套餐是否限量</span>',
                    '<span data-locale="menuCount">套餐余量</span>',
                    '<span data-locale="menuType">套餐规格</span>',
                    '<span data-locale="menuStatus">菜单状态</span>',
                    '<span data-locale="menuUp">是否上架</span>',
                    '<span data-locale="examineInfo">审核原因</span>',
                    '<span data-locale="view">操作</span>'
                ],
                colModel: [
                    {name: 'id', index: 'id', hidden: true},
                    {name: 'menuName', index: 'menu_name'},
                    {name: 'menuDescription', index: 'menu_description'},
                    {name: 'menuLimited', index: 'menu_limited'},
                    {name: 'menuCount', index: 'menu_count'},
                    {name: 'menuType', index: 'menu_type'},
                    {name: 'menuStatus', index: 'menu_status'},
                    {name: 'menuUp', index: 'menu_up'},
                    {name: 'examineInfo', index: 'examine_info'},
                    {name: 'view', index: 'view' ,sortable: false}

                ],
                viewrecords: true,
                rowNum: 20,
                rowList: [10, 20, 30],
                pager: pager_selector,
                altRows: true,
                toppager: true,
                multiselect: true,
                loadComplete: function () {
                    $.changeGridTable.changeStyle(this);  //改变复选框的样式
                    $(grid_selector + "_toppager_center").remove();
                    $(grid_selector + "_toppager_right").remove();
                    $(pager_selector + "_left table").remove();
                },
                editurl: "/dummy.html",//nothing is saved
                caption: "<span data-locale='PzMenuList'>菜单列表</span>",
                gridComplete: function () {
                    var qCode = sessionStorage.getItem("qCode");
                    typeChange(qCode);
                    $(grid_selector).closest(".ui-jqgrid-bdiv").css({'overflow-x': 'hidden'});
                    var rowData = jQuery(grid_selector).jqGrid('getRowData');
                    var ids = $(grid_selector).jqGrid('getDataIDs');

                    for (var i = 0; i < ids.length; i++) {
                        var id = ids[i];

                        var rowData = $("#grid-table").getRowData(id);
                        var menuLimited = getDictLabel(${fns:toJson(fns:getDictList("pz_menu_limited"))}, rowData.menuLimited);
                        var menuType = getDictLabel(${fns:toJson(fns:getDictList("pz_menu_type"))}, rowData.menuType);
                        var menuStatus = getDictLabel(${fns:toJson(fns:getDictList("pz_menu_status"))}, rowData.menuStatus);
                        var menuUp = getDictLabel(${fns:toJson(fns:getDictList("pz_menu_up"))}, rowData.menuUp);


                        var  viewBtn = '<div class="action-buttons" style="white-space:normal">'+
                            '<a data-action="examine" data-id="'+rowData.id+'" href="javascript:void(0);" class="tooltip-success green" data-rel="tooltip" title="审核"  style="border-color:#69aa46 "><i class="ace-icon fa fa-check bigger-130"></i></a>'+
                            '</div>';

                        $(grid_selector).jqGrid('setRowData', ids[i], {
                            menuLimited: menuLimited,
                            menuType: menuType,
                            menuStatus: menuStatus,
                            menuUp: menuUp,
                            view: viewBtn
                        });

                        //删除按钮
                        $(grid_selector).find('a[data-action=examine]').on('click', function(event) {
                            $(grid_selector).jqGrid('resetSelection');
                            var id = $(this).attr('data-id');
                            _view(id);
                        });
                    }

                }
            });

            jQuery(grid_selector).jqGrid('navGrid', pager_selector,
                {//navbar options
                    edit: false,
                    editicon: 'ace-icon fa fa-pencil',
                    edittext: "<span data-locale='edit'>编辑</span>",
                    edittitle: '',
                    add:false,
                    addicon: 'ace-icon fa fa-plus',
                    addtext: "<span data-locale='add'>新增</span>",
                    addtitle: '',
                    del:false,
                    delicon: 'ace-icon fa fa-trash-o',
                    deltext: "<span data-locale='BatchDelete'>批量删除</span>",
                    deltitle: '',
                    search: false,
                    refresh: true,
                    refreshicon: 'ace-icon fa fa-refresh',
                    refreshtext: "<span data-locale='refresh'>刷新</span>",
                    refreshtitle: '',
                    view: false,
                    viewicon : 'ace-icon fa fa-search-plus grey',
                    viewfunc : openDialogView,
                    viewtext:"<span data-locale='examine'>审核</span>",
                    viewtitle:'',
                    cloneToTop: true
                },
                {}, // use default settings for edit
                {}, // use default settings for add
                {},  // delete instead that del:false we need this
                {multipleSearch: true}, // enable the advanced searching
                {closeOnEscape: true} /* allow the view dialog to be closed when user press ESC key*/
            )


            function openDialogView() {
                var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
                if(selectedIds.length>1){
                    //失败
                    $.msg_show.Init({
                        'msg':'请您选择一条记录',
                        'type':'error'
                    });

                }else{
                    _view(selectedIds[0]);
                }
            }


            function _view(id) {
                var id = id;
                var params = {"id": id};
                $.get("${ctx}/pzMenu/noExamineForm", params, function (data, textStatus, object) {
                    $("#editDivId").closest(".ui-dialog").remove();
                    $("#editDivId").html(object.responseText).dialog({
                        modal: true,
                        width: 600,
                        height: 500,
                        title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-th-large'></i>&nbsp;<span data-locale='PzMenuExamineInfo.'>菜单审核信息</span></h4></div>",
                        title_html: true,
                        buttons: [
                            {
                                text: "通过",
                                "class": "btn btn-primary btn-minier",
                                "data-locale": "pass",
                                click: function () {
                                   var params = {"id": id,"menuStatus":"3","examineInfo":"审核通过"};
                                    $.post("${ctx}/pzMenu/updateStatus", params, function (result) {
                                        if (result.messageStatus == "1") {
                                            $.msg_show.Init({
                                                'msg': result.message,
                                                'type': 'success'
                                            });
                                            $("#editDivId").dialog("close");
                                            $(grid_selector).trigger("reloadGrid");
                                        } else if (result.messageStatus == "0") {
                                            $.msg_show.Init({
                                                'msg': result.message,
                                                'type': 'error'
                                            });
                                        }
                                    });
                                }
                            },
                            {
                                text: "不通过",
                                "class": "btn btn-primary btn-minier",
                                "data-locale": "noPass",
                                click: function () {
                                    var examineInfo=prompt("请输入审核不通过原因","");
                                    if(examineInfo!=''){
                                        var params = {"id": id,"menuStatus":"2","examineInfo":examineInfo};
                                        $.post("${ctx}/pzMenu/updateStatus", params, function (result) {
                                            if (result.messageStatus == "1") {
                                                $.msg_show.Init({
                                                    'msg': result.message,
                                                    'type': 'success'
                                                });
                                                $("#editDivId").dialog("close");
                                                $(grid_selector).trigger("reloadGrid");
                                            } else if (result.messageStatus == "0") {
                                                $.msg_show.Init({
                                                    'msg': result.message,
                                                    'type': 'error'
                                                });
                                            }
                                        });
                                    }else{
                                        alert("请输入审核不通过原因!");
                                    }
                                }
                            },
                            {
                                text: "取消",
                                "class": "btn btn-minier",
                                "data-locale": "cancel",
                                click: function () {
                                    $(this).dialog("close");
                                }
                            }
                        ],
                        open: function (event, ui) {
                            var qCode = sessionStorage.getItem("qCode");
                            typeChange(qCode);

                            $(".ui-dialog-title .widget-header").on('mouseenter', function () {
                                $(".ui-dialog-content input").blur();
                            })
                        }
                    });
                });
            }
            //search list by condition
            $("#query").click(function () {
                var menuStatus = $("#menuStatus").val();
                $(grid_selector).jqGrid('setGridParam', {
                    url: "${ctx}/pzMenu/findMenuListByNoExamine",
                    mtype: "post",
                    postData: { 'menuStatus': menuStatus}, //发送数据
                    page: 1
                }).trigger("reloadGrid"); //重新载入
            });

            $("#reset").click(function () {
                $('.chosen-select').val('').trigger('chosen:updated');
                $(grid_selector).jqGrid('setGridParam', {
                    url: "${ctx}/pzMenu/findMenuListByNoExamine",
                    mtype: "post",
                    postData: {'menuStatus': ''},
                    page: 1
                }).trigger("reloadGrid"); //重新载入
            });


            //============================== end ==============================
            $.changeGridTable.changeSize([grid_selector, grid_selector + " ~ .widget-box"], reSizeHeight);
            //override dialog's title function to allow for HTML titles
            $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
                _title: function (title) {
                    var $title = this.options.title || '&nbsp;';
                    if (("title_html" in this.options) && this.options.title_html == true)
                        title.html($title);
                    else title.text($title);
                }
            }));
            $(document).one('ajaxloadstart.page', function (e) {
                $.jgrid.gridUnload(grid_selector);
                $('.ui-dialog').remove();
                $('.ui-jqdialog').remove();
                $('[class*=select2]').remove();
                $('.ui-helper-hidden-accessible').remove();
            });
        });
    });
</script>