<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<title>箱子编码管理</title>
<link href="${ctxStatic}/bootstrap-treeview/css/bootstrap-treeview.css" rel="stylesheet" type="text/css" />
<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="widget-box widget-compact">
            <div class="widget-header widget-header-blue widget-header-flat">
                <h5 class="widget-title lighter" data-locale="queryCriteria">
                    查询条件
                </h5>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse"> <i class="ace-icon fa fa-chevron-up"></i> </a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <form:form id="searchForm" modelAttribute="pzBoxCode" class="form-horizontal">
                        <div class="form-group">
                            <div class="col-xs-12 col-sm-4 no-padding">
                                <div class="new-search clearfix">
                                    <label class=" col-xs-12 col-sm-4" for="boxCode"
                                           data-locale="boxCode">箱子编码</label>
                                    <div class="col-xs-12 col-sm-8 no-padding">
                                        <input type="text" id="boxCode" class="ace width-100 newsearchInput"/>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xs-12 col-sm-4 no-padding">
                                <div class="new-search clearfix">
                                    <label class=" col-xs-12 col-sm-4" for="boxCnName"
                                           data-locale="boxCnName">箱子中文名称</label>
                                    <div class="col-xs-12 col-sm-8 no-padding">
                                        <input type="text" id="boxCnName" class="ace width-100 newsearchInput"/>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="form-group">
                            <div class="col-xs-12 col-sm-4">
                                <div style="float:right;">
                                    <button class="btn btn-info btn-sm" type="button" id="query"
                                            style="color: orange !important;border-color: orange">
                                        <i class="fa fa-search" aria-hidden="true" style="margin-right: 5px"></i>
                                        <span data-locale='query'>查询</span>
                                    </button>
                                    <button class="btn btn-info btn-sm" type="reset" id="reset"
                                            style="color: #4cc74c !important;border-color: #4cc74c;margin-left: 5px">
                                        <i class="fa fa-refresh" aria-hidden="true" style="margin-right: 5px"></i>
                                        <span data-locale='reset'>重置</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
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
    var scripts = [null, '${ctxStatic}/assets/js/fuelux/fuelux.spinner.js', '${ctxStatic}/assets/js/date-time/bootstrap-datepicker.js', '${ctxStatic}/assets/js/date-time/bootstrap-datepicker.zh-CN.min.js','${ctxStatic}/bootstrap-treeview/js/bootstrap-treeview.js', null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        jQuery(function ($) {
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
            var isAdd = true;
            var isShow = "";
            var isShow2 = "";

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
                url: '${ctx}/pzBoxCode/searchPage', //这是数据的请求地址
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
                    '<span data-locale="restaurantId">餐厅名称</span>',
                    '<span data-locale="boxCode">箱子编码</span>',
                    '<span data-locale="boxCnName">箱子中文名称</span>',
                    '<span data-locale="view">操作</span>'
                ],
                colModel: [
                    {name: 'id', index: 'id', hidden: true},
                    {name: 'restaurantName', index: 'restaurant_id', sortable: false},
                    {name: 'boxCode', index: 'box_code'},
                    {name: 'boxCnName', index: 'box_cn_name'},
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
                caption: "<span data-locale='pzBoxCodeList'>箱子编码列表</span>",
                gridComplete: function () {
                    var ids = $(grid_selector).jqGrid('getDataIDs');
                    for (var i = 0; i < ids.length; i++) {
                        var id = ids[i];
                        var rowData = $("#grid-table").getRowData(id);
                        var viewBtn = '';
                            viewBtn = '<div class="action-buttons" style="white-space:normal">'+
                                '<a data-action="edit" data-id="'+rowData.id+'" href="javascript:void(0);" class="tooltip-success green" data-rel="tooltip" title="编辑"  style="border-color:#69aa46 "><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
                                '<a data-action="print" data-id="'+rowData.id+'" href="javascript:void(0);" class="tooltip-success green" data-rel="tooltip" title="打印" style="border-color:#69aa46"><i class="ace-icon fa fa fa-print bigger-130"></i></a>'+
                                '</div>';

                        $(grid_selector).jqGrid('setRowData', ids[i], {
                            view: viewBtn
                        });
                    }


                    //编辑
                    $(grid_selector).find('a[data-action=edit]').on('click', function(event) {
                        $(grid_selector).jqGrid('resetSelection');
                        var id = $(this).attr('data-id');
                        _edita(id);
                    });

                    //打印条码
                    $(grid_selector).find('a[data-action=print]').on('click', function(event) {
                        $(grid_selector).jqGrid('resetSelection');
                        var id = $(this).attr('data-id');
                        _boxCode(id);
                    });
                }
            });

            jQuery(grid_selector).jqGrid('navGrid', pager_selector,
                {//navbar options
                    edit: true,
                    editicon: 'ace-icon fa fa-pencil',
                    editfunc: openDialogEdit,
                    edittext: "<span data-locale='edit'>编辑</span>",
                    edittitle: '',
                    add: isAdd,
                    addicon: 'ace-icon fa fa-plus',
                    addfunc: openDialogAdd,
                    addtext: "<span data-locale='add'>新增</span>",
                    addtitle: '',
                    del: true,
                    delicon: 'ace-icon fa fa-trash-o',
                    delfunc: doDelete,
                    deltext: "<span data-locale='BatchDelete'>删除</span>",
                    deltitle: '',
                    search: false,
                    refresh: true,
                    refreshicon: 'ace-icon fa fa-refresh',
                    refreshtext: "<span data-locale='refresh'>刷新</span>",
                    refreshtitle: '',
                    view: false,
                    cloneToTop: true
                },
                {}, // use default settings for edit
                {}, // use default settings for add
                {},  // delete instead that del:false we need this
                {multipleSearch: true}, // enable the advanced searching
                {closeOnEscape: true} /* allow the view dialog to be closed when user press ESC key*/
            ).jqGrid("navButtonAdd",toolbarTop,{
                caption:"<span data-locale='BarcodePrinting'>条形码打印</span>",
                buttonicon:"ace-icon fa fa-download",
                title:"条形码打印",
                onClickButton: printBoxCode,
                position:"first" ,
                id:"printBoxCode"
            });



            function printBoxCode() {
                var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
                if (selectedIds.length ==0) {
                    $.msg_confirm.Init({
                        'msg': '确认要打印所有的记录吗？',//这个参数可选，默认值：'这是信息提示！'
                        'confirm_fn': function () {
                            _boxCode(selectedIds)
                        },//这个参数可选，默认值：function(){} 空的方法体
                        'cancel_fn': function () {
                            $(grid_selector).jqGrid('resetSelection');
                        }//这个参数可选，默认值：function(){} 空的方法体
                    });
                } else {
                    _boxCode(selectedIds);
                }
            }

            function _boxCode(ids){
                window.open("${ctxReport}/HtmReport/doPrintBoxCode.htm?param="+ids);
            }


            function openDialogAdd() {
                _edita();
            }

            function _edita(id) {
                var id = id;
                var params = {"id": id};
                $.get("${ctx}/pzBoxCode/form", params, function (data, textStatus, object) {
                    $(".ui-dialog").remove();
                    $("#editDivId").closest(".ui-dialog").remove();
                    $("#editDivId").html(object.responseText).dialog({
                        modal: true,
                        width: 600,
                        height: 400,
                        title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-th-large'></i>&nbsp;<span data-locale='PzBoxCodeInfo.'>箱子编码信息</span></h4></div>",
                        title_html: true,
                        buttons: [
                            {
                                text: "保存",
                                "class": "btn btn-primary btn-minier",
                                "data-locale": "doSave",
                                click: function () {
                                    $("#inputForm").bootstrapValidator('validate');
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

                            var select2 = $('.select2');
                            var select2width = select2.parent().width();
                            $('.select2').css('width',select2width).select2({allowClear:true});
                            //选择框
                            if(!ace.vars['touch']) {
                                $('.chosen-select').chosen({allow_single_deselect:true});
                                $(window).off('resize.chosen').on('resize.chosen', function() {
                                    $('.chosen-select,.select2').each(function() {
                                        var $this = $(this);
                                        $this.next().css({'width': $this.parent().width()});
                                    });
                                }).trigger('resize.chosen');
                            }
                        },
                        create: function( event, ui ) {
                            $(".ui-dialog-title .widget-header").on('mouseenter',function(){
                                $(".ui-dialog-content input").blur();
                            })

                            $("#selectOfficeMenu").on('click', function(e) {
                                e.preventDefault();
                                var dialog = $( "#selectOfficeTreeDiv" ).removeClass('hide').dialog({
                                    modal: true,
                                    width:300,
                                    height:400,
                                    title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-university'></i>&nbsp;<span data-locale='Selectdepartment'>选择服务单元</span></h4></div>",
                                    title_html: true,
                                    buttons: [
                                        {
                                            text: "确定",
                                            "class" : "btn btn-primary btn-minier",
                                            "data-locale":"define",
                                            click: function() {
                                                $("#inputForm input#serviceUnit").val($('#popuptreeview').attr('data-text'));
                                                $("#inputForm input#serviceUnitId").val($('#popuptreeview').attr('data-id'));
                                                $( this ).dialog( "close" );
                                            }
                                        },
                                        {
                                            text: "取消",
                                            "class" : "btn btn-minier",
                                            "data-locale":"cancel",
                                            click: function() {
                                                $( this ).dialog( "close" );
                                            }
                                        }
                                    ],
                                    open: function( event, ui ) {
                                        $.getJSON( "${ctx}/sys/office/getOfficetree",{"treetype":1},function(data) {
                                            $('#popuptreeview').treeview({
                                                data: data,
                                                levels: 4,
                                                showBorder:true,
                                                emptyIcon:'fa fa-file-o',
                                                collapseIcon:'fa fa-folder-open-o',
                                                expandIcon:'fa fa-folder-o',
                                                onNodeSelected: function(event, node) {
                                                    $('#popuptreeview').attr('data-id',node.id);
                                                    $('#popuptreeview').attr('data-text',node.text);
                                                },
                                            });
                                        });
                                    }
                                });
                            });
                        }
                    });
                    //动作设备页面维护表单验证
                    $("#inputForm").bootstrapValidator({
                        message: "请录入一个有效值",
                        excluded: ':disabled',
                        feedbackIcons: {
                            valid: "glyphicon glyphicon-ok",
                            invalid: "glyphicon glyphicon-remove",
                            validating: "glyphicon glyphicon-refresh"
                        },
                        fields: {
                            boxCode: {
                                validators: {
                                    notEmpty: {
                                        message: "箱子编码不能为空"
                                    }
                                }
                            },
                            boxCnName: {
                                validators: {
                                    notEmpty: {
                                        message: "箱子中文名称不能为空"
                                    }
                                }
                            }
                        }
                    }).on("success.form.bv", function (e) {
                        // Prevent form submission
                        e.preventDefault();

                        // Get the form instance
                        var $form = $(e.target);

                        // Get the BootstrapValidator instance
                        var bv = $form.data("bootstrapValidator");

                        // Use Ajax to submit form data
                        $.post($form.attr("action"), $form.serialize(), function (result) {
                            if (result.messageStatus == "1") {
                                $("#editDivId").dialog("close");
                                $(grid_selector).trigger("reloadGrid");
                                $.msg_show.Init({
                                    'msg': result.message,
                                    'type': 'success'
                                });
                            } else if (result.messageStatus == "0") {
                                $.msg_show.Init({
                                    'msg': result.message,
                                    'type': 'error'
                                });
                            }
                        }, "json");
                    });
                });
            }

            function doDelete(id) {
                //信息确认插件
                $.msg_confirm.Init({
                    'msg': '要删除当前所选的记录吗？',//这个参数可选，默认值：'这是信息提示！'
                    'confirm_fn': function () {
                        var ids = id+"";
                        var params = {"ids":ids};
                        $.post("${ctx}/pzBoxCode/deleteByIds", params, function (result) {
                            if (result.messageStatus == "1") {
                                $.msg_show.Init({
                                    'msg': result.message,
                                    'type': 'success'
                                });
                            } else if (result.messageStatus == "0") {
                                $.msg_show.Init({
                                    'msg': result.message,
                                    'type': 'error'
                                });
                            }
                            $(grid_selector).trigger("reloadGrid");
                        });
                    },//这个参数可选，默认值：function(){} 空的方法体
                    'cancel_fn': function () {
                        $(grid_selector).jqGrid('resetSelection');
                    }//这个参数可选，默认值：function(){} 空的方法体
                });
            }





            function openDialogEdit() {
                var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
                if (selectedIds.length > 1) {
                    $.msg_show.Init({
                        'msg': "请选择一条记录",
                        'type': 'error',
                        'time': 600
                    });
                } else {
                    _edita(selectedIds[0]);
                }
            }


            //search list by condition
            $("#query").click(function () {
                var boxCode = $("#boxCode").val();
                var boxCnName = $("#boxCnName").val();
                $(grid_selector).jqGrid('setGridParam', {
                    url: "${ctx}/pzBoxCode/searchPage",
                    mtype: "post",
                    postData: {'boxCode': boxCode, 'boxCnName': boxCnName}, //发送数据
                    page: 1
                }).trigger("reloadGrid"); //重新载入
            });
            /*$("#name").keydown(function (e) {
                if (e.keyCode == 13)
                    e.preventDefault();
                $("#query").click();
            });*/
            $("#reset").click(function () {
                $('.chosen-select').val('').trigger('chosen:updated');
                $(grid_selector).jqGrid('setGridParam', {
                    url: "${ctx}/pzBoxCode/searchPage",
                    mtype: "post",
                    postData: {'boxCode': '', 'boxCnName': ''},
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