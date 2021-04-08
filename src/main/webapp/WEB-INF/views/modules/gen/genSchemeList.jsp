<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>生成方案列表</title>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class="widget-box widget-compact search-box">
			<div class="widget-header widget-header-blue widget-header-flat">
				<h5 class="widget-title lighter">查询条件</h5>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse"><i class="ace-icon fa fa-chevron-up"></i></a>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-xs-12 col-sm-1 no-padding-right" for="nameQuery">方案名称:</label>
							<div class="col-xs-12 col-sm-2">
								<select id="nameQuery" class="chosen-select form-control" data-placeholder="点击选择...">
									<option value="" selected="selected"></option>
									<c:forEach items="${nameList}" var="item" varStatus="status">
										<option value="${item}">${item}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-xs-12 col-sm-2 no-padding-right">
								<button class="btn btn-info btn-sm" type="button" id="query">查询</button>
								<button class="btn btn-info btn-sm" type="reset" id="reset">重置</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<table id="grid-table"></table>
		<div id="grid-pager"></div>
		<div class="widget-box" style="display:none" id="editDivId"></div>
		<div id="load" vertical-align="middle" style="z-index:9999;" >
			<span style="width:100%;position:absolute;top:40%;" align="center">
				<img src="${ctxStatic}/images/loading.gif" width="50" height="50" align="absmiddle" style="vertical-align:middle;"/>
			</span>
		</div>
	</div>
</div>
<script type="text/javascript">

    var scripts = [ null,'${ctxStatic}/assets/js/fuelux/fuelux.spinner.js', null ];
    $('.page-content-area').ace_ajax('loadScripts',scripts,function() {
        jQuery(function($) {

            //选择框
            if(!ace.vars['touch']) {
                $('.chosen-select').chosen({allow_single_deselect:true});
                //resize the chosen on window resize

                $(window).off('resize.chosen').on('resize.chosen', function() {
                    $('.chosen-select').each(function() {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    });
                }).trigger('resize.chosen');
                //resize chosen on sidebar collapse/expand
                $(document).on('settings.ace.chosen', function(e, event_name, event_val) {
                    if(event_name != 'sidebar_collapsed') return;
                    $('.chosen-select').each(function() {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    });
                });
            }

            var grid_selector = "#grid-table";
            var pager_selector = "#grid-pager";

            var reSizeHeight = function(){
                var strs = $.getWindowSize().toString().split(",");
                var jqgrid_height = strs[0]-337;
                $(grid_selector).jqGrid('setGridHeight',jqgrid_height);
            };

            jQuery(grid_selector).jqGrid({
                datatype: "json", //将这里改为使用JSON数据
                url: '${ctx}/gen/genScheme/list',
                height: 'auto',
                autowidth:true,
                jsonReader: {
                    root: "rows",   // json中代表实际模型数据的入口
                    page: "page",   // json中代表当前页码的数据
                    total: "total", // json中代表页码总数的数据
                    records: "records", // json中代表数据行总数的数据
                    repeatitems: false
                },
                prmNames : {
                    page:"pageNo",    // 表示请求页码的参数名称
                    rows:"rows",    // 表示请求行数的参数名称
                    sort: "sidx", // 表示用于排序的列名的参数名称
                    order: "sord", // 表示采用的排序方式的参数名称
                    search:"_search", // 表示是否是搜索请求的参数名称
                    nd:"nd", // 表示已经发送请求的次数的参数名称
                    id:"id", // 表示当在编辑数据模块中发送数据时，使用的id的名称
                    oper:"oper",    // operation参数名称（我暂时还没用到）
                    editoper:"edit", // 当在edit模式中提交数据时，操作的名称
                    addoper:"add", // 当在add模式中提交数据时，操作的名称
                    deloper:"del", // 当在delete模式中提交数据时，操作的名称
                    subgridid:"id", // 当点击以载入数据到子表时，传递的数据名称
                    npage: null,
                    totalrows:"totalrows" // 表示需从Server得到总共多少行数据的参数名称，参见jqGrid选项中的rowTotal
                },
                colModel: [
                    {name: 'name'},
                    {name: 'packageName'},
                    {name: 'moduleName'},
                    {name: 'functionName'},
                    {name: 'functionAuthor'},
                    {name: 'id', hidden: true}
                ],
                rowList: [10, 20, 30],
                colNames: ['方案名称', '生成模块', '模块名', '功能名','功能作者', '操作'],
                viewrecords : true,
                rowNum:20,
                rowList:[10,20,30],
                pager : pager_selector,
                altRows: true,
                toppager: true,
                multiselect: true,
                multiboxonly: true,
                rownumbers: true,
                loadComplete : function() {
                    $.changeGridTable.changeStyle(this);
                    $(grid_selector+"_toppager_center").remove();
                    $(grid_selector+"_toppager_right").remove();
                    $(pager_selector+"_left table").remove();
                    $("#load").hide();
                },

                editurl: "/dummy.html",//nothing is saved
                caption: "业务列表",

                gridComplete: function () {

                }

            });

            //$("#t_grid-table").append("<input type='button' value='Click Me' style='height:20px;font-size:-3'/>");
            //$("input","#t_grid-table").click(function(){ alert("Hi! I'm added button at this toolbar"); });
            $.changeGridTable.changeSize([grid_selector,grid_selector+" ~ .widget-box"],reSizeHeight);

            //search list by condition

            $("#query").click(function () {
                var name = $("#nameQuery").val();
                $(grid_selector).jqGrid('setGridParam', {
                    postData: {'name':name}, //发送数据
                    page: 1
                }).trigger("reloadGrid"); //重新载入
            });

            $("#reset").click(function () {
                $('.chosen-select').val('').trigger('chosen:updated');
                $(grid_selector).jqGrid('setGridParam', {
                    postData: {'name': ''},
                    page: 1
                }).trigger("reloadGrid"); //重新载入
            });

            //navButtons
            jQuery(grid_selector).jqGrid('navGrid',pager_selector,
                { 	//navbar options
                    edit: true,
                    editicon : 'ace-icon fa fa-pencil blue',
                    editfunc : openDialogEdit,
                    edittext:"编辑",
                    edittitle:'',
                    add:true,
                    addicon : 'ace-icon fa fa-plus-circle purple',
					addfunc : openDialogAdd,
                    addtext:"新增",
                    addtitle:'',
                    del: true,
                    delicon : 'ace-icon fa fa-trash-o red',
                    delfunc : doDelete,
                    deltext:"删除",
                    deltitle:'',
                    search: false,
                    searchicon : 'ace-icon fa fa-search orange',
                    searchtext:"查询",
                    searchtitle:'',
                    refresh: false,
                    refreshicon : 'ace-icon fa fa-refresh green',
                    refreshtext:"刷新",
                    refreshtitle:'',
                    view: false,
                    viewicon : 'ace-icon fa fa-search-plus grey',
                    viewtext:"查看",
                    viewtitle:'',
                    cloneToTop:true,
                },
                {}, // use default settings for edit
                // use default settings for add
                {},  // delete instead that del:false we need this
                {multipleSearch : true}, // enable the advanced searching
                {closeOnEscape:true} /* allow the view dialog to be closed when user press ESC key*/
            );

            //$(grid_selector).jqGrid('navGrid',pager_selector,{ cloneToTop:true });

            //$("#t_grid-table").append( "<input type='button' value='Click Me' style='height:20px;font-size:-3'/>");

            //override dialog's title function to allow for HTML titles
            $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
                _title: function(title) {
                    var $title = this.options.title || '&nbsp;';
                    if( ("title_html" in this.options) && this.options.title_html == true )
                        title.html($title);
                    else title.text($title);
                }
            }));

            function openDialogAdd(){
                _edit();
            }

            function openDialogEdit(){
                var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
                if(selectedIds.length>1){
                    $.msg_show.Init({
                        'msg':'请您选择一条记录进行编辑',
                        'type':'error'
                    });
                }else{
                    _edit(selectedIds[0]);
                }

            }

            function _edit(id){
                var params = {"id":id};
                $.post("${ctx}/gen/genScheme/form",params, function(data,textStatus,object){
                    $(".ui-dialog").remove();
                    $("#editDivId").html(object.responseText).dialog({
                        modal: true,
                        width:800,
                        height:'auto',
                        title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller'><i class='ace-icon fa fa-users'></i>&nbsp;方案配置</h4></div>",
                        title_html: true,
                        buttons: [
                            {
                                text: "保存",
                                "class" : "btn btn-primary btn-minier",
                                click: function() {
                                    $("#inputForm").bootstrapValidator('validate');
                                }
                            },
                            {
                                text: "取消",
                                "class" : "btn btn-minier",
                                click: function() {
                                    $(this).dialog("close");
                                }
                            }
                        ],
                        open:function(event,ui){
                            $('.chosen-select').chosen({allow_single_deselect:true});
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
                        }
                    });

                    //字典页面维护表单验证
                    $("#inputForm").bootstrapValidator({
                        message : "请录入一个有效值",
                        feedbackIcons : {
                            valid : "glyphicon glyphicon-ok",
                            invalid : "glyphicon glyphicon-remove",
                            validating : "glyphicon glyphicon-refresh"
                        },
                        fields : {
                            name : {
                                validators : {
                                    notEmpty : {
                                        message : "方案名称不能为空"
                                    }
                                }
                            },
                            packageName : {
                                validators : {
                                    notEmpty : {
                                        message : "包路径不能为空"
                                    }
                                }
                            },
                            moduleName : {
                                validators : {
                                    notEmpty : {
                                        message : "模块名不能为空"
                                    }
                                }
                            },
                            functionNameSimple : {
                                validators : {
                                    notEmpty : {
                                        message : "功能名不能为空"
                                    }
                                }
                            }
                        }
                    }).on("success.form.bv",function(e) {
                        // Prevent form submission
                        e.preventDefault();

                        // Get the form instance
                        var $form = $(e.target);

                        // Get the BootstrapValidator instance
                        var bv = $form.data("bootstrapValidator");
                        // Use Ajax to submit form data
                        $.post($form.attr("action"), $form.serialize(),function(result) {
                            if(result.messageStatus=="1"){
                                $.msg_show.Init({
                                    'msg':result.message,
                                    'type':'success'
                                });
                                $("#editDivId").dialog("close");
                                $(grid_selector).trigger("reloadGrid");
                                hideResult();
                            }else if(result.messageStatus=="0"){
                                $.msg_show.Init({
                                    'msg':result.message,
                                    'type':'error'
                                });
                                hideResult();
                            }
                        }, "json");
                    });
                });

            }

            function doDelete(){
                var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
                var arrayObj="";
                for (var i=0;i<selectedIds.length;i++){
                    var rowData=$("#grid-table").getRowData(selectedIds[i]);
                    var id=rowData.id;
                    arrayObj = (arrayObj + id) + (((i + 1) == selectedIds.length) ? '' : ',');


                }
                console.log(arrayObj);


                var params = {"ids": arrayObj};

                $.post("${ctx}/gen/genScheme/delete",params,function(result){
                    if(result.messageStatus=="1"){
                        $.msg_show.Init({
                            'msg':result.message,
                            'type':'success'
                        });
                        $(grid_selector).trigger("reloadGrid");
                    }else if(result.messageStatus=="0"){
                        $.msg_show.Init({
                            'msg':result.message,
                            'type':'error'
                        });
                    }

                });
            }
            <%--function _edit(id){--%>
                <%--location.href = "${ctx}/gen/genTable/form?id="+id;--%>
            <%--}--%>

            $(document).one('ajaxloadstart.page', function(e) {
                $.jgrid.gridUnload(grid_selector);
                $('.ui-jqdialog').remove();
            });
        });
    });
</script>