<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<title>套餐统计</title>
<link href="${ctxStatic}/bootstrap-treeview/css/bootstrap-treeview.css" rel="stylesheet" type="text/css"/>
<style type="text/css" id="css-style">
    .popfi{margin-left: 0px;width: 220px;}
    .popfi table{font-size: 14px;}
    .invis{width: 100%;margin-bottom: 5px;}
    .invis table{width: 100%;}
    .invis table th{padding:3px 0px;}
    .invis table td{padding:3px 0px;}
    .qrcss{margin-top: 20px;margin-left: 25%;text-align: center;}
    .tab1{width: 100%;}
    .tab1 table{width: 100%;border:solid; border-width:1px 0px 0px 1px;text-align:center;}
    .tab1 th{border:solid; border-width:0px 1px 1px 0px;text-align:center;}
    .tab1 td{border:solid; border-width:0px 1px 1px 0px;text-align:center;}
    .tex-l{text-align: left;}
    .tex-r{text-align: right;}
    .tex-s{font-size: 28px;font-weight: bolder;text-align: center;}
    .dotted{width:280px;height:0px;border-top:1px black dashed; margin-top: 20px;margin-left: -30px;}
</style>
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
                    <form:form id="searchForm" modelAttribute="pzCensus" class="form-horizontal">
                        <div class="form-group">
                            <div class="col-xs-12 col-sm-4 no-padding">
                                <div class="new-search clearfix">
                                    <label class=" col-xs-12 col-sm-4" for="serviceUnitName" data-locale="holidayType">服务单元</label>
                                    <div class="col-xs-12 col-sm-8 no-padding">
                                        <input type="text" id="serviceUnitName" name="serviceUnitName"
                                               class="ace width-100 newsearchInput"/>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xs-12 col-sm-4 no-padding">
                                <div class="new-search clearfix">
                                    <label class=" col-xs-12 col-sm-4" for="eatDate" data-locale="DateRange">日期</label>
                                    <div class="input-daterange input-group ">
                                        <input autocomplete="off" type="text"
                                               class="input-sm form-control newsearchInput  width-100" id="eatDate"
                                               name="eatDate" value="${pzCensus.eatDate}"/>
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

    var scripts = [null, '${ctxStatic}/assets/js/fuelux/fuelux.spinner.js', '${ctxStatic}/assets/js/date-time/bootstrap-datepicker.js',
        '${ctxStatic}/assets/js/date-time/bootstrap-datepicker.zh-CN.min.js', '${ctxStatic}/qrcode/qrcode.js', '${ctxStatic}/lodop/LodopFuncs.js', null];
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
                url: '${ctx}/pzCensus/searchPage', //这是数据的请求地址
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
                    '<span data-locale="Date">日期</span>',
                    '<span data-locale="Date">日期</span>',
                    'restaurantId',
                    '<span data-locale="restaurantId">餐厅名称</span>',
                    'serviceUnitId',
                    '<span data-locale="serviceUnitId">服务单元名称</span>',
                    '<span data-locale="areaName">投料点名称</span>',
                    'A',
                    'B',
                    'C',
                    'D',
                    'E',
                    'F'

                ],
                colModel: [
                    {name: 'eatDate', index: 'eat_date'},
                    {name: 'eatWeek', index: 'eat_week'},
                    {name: 'restaurantId', hidden: true},
                    {name: 'restaurantName', index: 'restaurant_name'},
                    {name: 'serviceUnitId', hidden: true},
                    {name: 'serviceUnitName', index: 'service_unit_name'},
                    {name: 'areaName', index: 'area_name'},
                    {name: 'countA', sortable: false},
                    {name: 'countB', sortable: false},
                    {name: 'countC', sortable: false},
                    {name: 'countD', sortable: false},
                    {name: 'countE', sortable: false},
                    {name: 'countF', sortable: false},
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
                caption: "<span data-locale='StatisticsList'>统计列表</span>&nbsp;&nbsp;&nbsp;&nbsp;<span id='menuTotal' style='color:#3f67e7;'></span>",
                gridComplete: function () {
                    getMenuTotal();
                }
            });


            jQuery(grid_selector).jqGrid('navGrid', pager_selector,
                {
                    edit: false,
                    editicon: 'ace-icon fa fa-pencil',
                    editfunc: '',
                    edittext: "<span data-locale='edit'>编辑</span>",
                    edittitle: '',
                    add: false,
                    addicon: 'ace-icon fa fa-plus',
                    addfunc: '',
                    addtext: "<span data-locale='add'>新增</span>",
                    addtitle: '',
                    del: false,
                    delicon: 'ace-icon fa fa-trash-o',
                    delfunc: '',
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
                caption:"<span data-locale='BarcodePrinting'>订单打印</span>",
                buttonicon:"ace-icon fa fa-download",
                title:"订单打印",
                onClickButton: printOrderCode,
                position:"first" ,
                id:"printOrderCode"
            });



            function printOrderCode() {
                var eatDate = $("#eatDate").val();
                var serviceUnitName = $("#serviceUnitName").val();
                window.open("${ctxReport}/HtmReport/doPrintOrderCode.htm?param=''&restaurantId=${officeId}&roleFlag=${roleFlag}&serviceUnitName="+serviceUnitName+"&eatDate="+eatDate);
            }

            $("#query").click(function () {
                var eatDate = $("#eatDate").val();
                var serviceUnitName = $("#serviceUnitName").val();
                $(grid_selector).jqGrid('setGridParam', {
                    url: "${ctx}/pzCensus/searchPage",
                    mtype: "post",
                    postData: {'eatDate': eatDate, 'serviceUnitName': serviceUnitName}, //发送数据
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
                    url: "${ctx}/pzCensus/searchPage",
                    mtype: "post",
                    postData: {'eatDate': '','serviceUnitName': ''},
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


        function getMenuTotal() {
            var eatDate = $("#eatDate").val();
            var serviceUnitName = $("#serviceUnitName").val();
            $.post("${ctx}/pzCensus/menuTotal", {
                'eatDate': eatDate,
                'serviceUnitName': serviceUnitName
            }, function (result) {
                var tempVar = "套餐A:"+result.totalA+"份,套餐B:"+result.totalB+"份,套餐C:"+result.totalC+"份,套餐D:"+result.totalD+"份,套餐E:"+result.totalE+"份,套餐F:"+result.totalF+"份";
                $("#menuTotal").html(tempVar)
            });
        }

    });



</script>