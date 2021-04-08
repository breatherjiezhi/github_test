<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/bootstrap-treeview/css/bootstrap-treeview.css" rel="stylesheet" type="text/css" />	
<title>角色管理</title>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<table id="grid-table"></table>
		<div id="grid-pager"></div>
		<div class="widget-box" style="display:none" id="editDivId">
			
		</div>
		<div id="assignFunctionDiv" class="hide widget-body">
			<div class="widget-main padding-8">
				<div id="treeview" class="" data-id="" data-text=""></div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var scripts = [ null, '${ctxStatic}/bootstrap-treeview/js/bootstrap-treeview.js',null ];
	$('.page-content-area').ace_ajax('loadScripts',scripts,function() {	
		jQuery(function($) {
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";
			//resize to fit page size
			var reSizeHeight = function(){
				var strs = $.getWindowSize().toString().split(",");
				var jqgrid_height = strs[0]-186;
				$(grid_selector).jqGrid('setGridHeight',jqgrid_height);
			};
            var qCode = sessionStorage.getItem("qCode");
            typeChange(qCode);
			jQuery(grid_selector).jqGrid({
				datatype: "json", //将这里改为使用JSON数据
				url:'${ctx}/sys/role/searchPage', //这是数据的请求地址
				height: 'auto',
				autowidth:true,

				jsonReader: {
					root: "rows",		// json中代表实际模型数据的入口  
					page: "page",		// json中代表当前页码的数据  
					total: "total",		// json中代表页码总数的数据  
					records: "records",	// json中代表数据行总数的数据 
					repeatitems: false
				},
				prmNames : {  
					page:"pageNo",			// 表示请求页码的参数名称  
					rows:"rows",			// 表示请求行数的参数名称  
					sort: "sidx",			// 表示用于排序的列名的参数名称  
					order: "sord",			// 表示采用的排序方式的参数名称  
					search:"_search",		// 表示是否是搜索请求的参数名称  
					nd:"nd",				// 表示已经发送请求的次数的参数名称  
					id:"id",				// 表示当在编辑数据模块中发送数据时，使用的id的名称  
					oper:"oper",			// operation参数名称（我暂时还没用到）  
					editoper:"edit",		// 当在edit模式中提交数据时，操作的名称  
					addoper:"add",			// 当在add模式中提交数据时，操作的名称  
					deloper:"del",			// 当在delete模式中提交数据时，操作的名称  
					subgridid:"id",			// 当点击以载入数据到子表时，传递的数据名称  
					npage: null,
					totalrows:"totalrows"	// 表示需从Server得到总共多少行数据的参数名称，参见jqGrid选项中的rowTotal  
				},
				colNames:[
				    '<span data-locale="RoleName">角色名称</span>',
					'<span data-locale="EnglishName">英文名称</span>',
					'<span data-locale="officeName">归属机构</span>',
					'<span data-locale="DataRange">数据范围</span>',
					'<span data-locale="Operations">操作</span>',
					'值'
				],
				colModel:[
					{name:'name',index:'name', editable: true},
					{name:'enname',index:'enname', editable: true},
					{name:'office.name',index:'name', editable: true},
					{name:'dataScope',index:'dataScope', editable: true},
					{name:'Edit',index:'Edit', width:100},
					{name:'id',index:'id', width:60,hidden:true}
				],
                viewrecords: true,
                rowNum: 10,
                rowList: [10, 20, 30],
				pager : pager_selector,
				altRows: true,
				toppager: true,
				//multiselect: true,
				//multiboxonly: true,
				loadComplete : function() {
					$.changeGridTable.changeStyle(this);
					$(grid_selector+"_toppager_center").remove();
					$(grid_selector+"_toppager_right").remove();
					$(pager_selector+"_left table").remove();
				},
				editurl: "/dummy.html",
				caption: "<span data-locale=\"RoleManagement\">角色管理</span>",
				gridComplete: function () {
                    var qCode = sessionStorage.getItem("qCode");
                    typeChange(qCode);
					var ids = jQuery(grid_selector).jqGrid('getDataIDs');
					for (var i = 0; i < ids.length; i++) {
						var id = ids[i];
						var rowData = $(grid_selector).getRowData(id);

						var dataScopeVal = getDictLabel(${fns:toJson(fns:getDictList('sys_data_scope'))}, rowData.dataScope);
						jQuery(grid_selector).jqGrid('setRowData', ids[i], { dataScope:dataScopeVal});	
						var btn = '<div class="action-buttons" style="white-space:normal">\
							<a data-action="assign" data-id="'+rowData.id+'"href="javascript:void(0);" class="tooltip-info" data-rel="tooltip" title="分配功能点">\
							<i class="ace-icon fa fa-bars bigger-130"></i></a>\
							<a data-action="edit" data-id="'+rowData.id+'"href="javascript:void(0);" class="tooltip-success green" data-rel="tooltip" title="修改" style="border-color: #69aa46">\
							<i class="ace-icon fa fa-pencil bigger-130"></i></a>\
							<a data-action="delete" data-id="'+rowData.id+'"href="javascript:void(0);" class="tooltip-error red" data-rel="tooltip"  title="删除" style="border-color: #dd5a43">\
							<i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
						jQuery(grid_selector).jqGrid('setRowData', ids[i], {Edit:btn});
					}
					$(grid_selector).find('a[data-action=assign]').on('click', function(event) {
						var id = $(this).attr('data-id');
						var params = {"id":id};
						assignFunction(params);
					});
					$(grid_selector).find('a[data-action=edit]').on('click', function(event) {
						var id = $(this).attr('data-id');
						_edit(id);
					});
					$(grid_selector).find('a[data-action=delete]').on('click', function(event) {
						var id = $(this).attr('data-id');
						deleteRow(id);
					});
				}
			});
			
			$.changeGridTable.changeSize([grid_selector,grid_selector+" ~ .widget-box"],reSizeHeight);

			//navButtons
			jQuery(grid_selector).jqGrid('navGrid',pager_selector,
				{	//navbar options
					edit: false,
					add: true,
					addicon : 'ace-icon fa fa-plus ',
					addfunc : openDialogAdd,
					addtext:"<span  data-locale=\"add\">新增</span>",
					del: false,
					delicon : 'ace-icon fa fa-trash-o ',
					search: false,
					searchicon : 'ace-icon fa fa-search ',
					refresh: true,
					refreshicon : 'ace-icon fa fa-refresh ',
					refreshtext:"<span  data-locale=\"refresh\">刷新</span>",
					refreshtitle:'',
					view: false,
					viewicon : 'ace-icon fa fa-search-plus ',
					cloneToTop:true,
				}
			)
			
			//override dialog's title function to allow for HTML titles
			$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
				_title: function(title) {
					var $title = this.options.title || '&nbsp;'
					if( ("title_html" in this.options) && this.options.title_html == true )
						title.html($title);
					else title.text($title);
				}
			}));
			
			function assignFunction(params){
				var functions = new Array();
                $(".ui-dialog").remove();
				 $( "#assignFunctionDiv" ).removeClass('hide').dialog({
					modal: true,
					width:350,
					height:600,
					title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-bars'></i>&nbsp;<span data-locale='AllocateFunctions'>分配功能点</span></h4></div>",
					title_html: true,
					buttons: [ 
						{
							text: "确定",
							"class" : "btn btn-primary btn-minier",
                            "data-locale":"define",
							click: function() {
								var menus = functions.join(',');
								params.menus = menus;
								var that =$(this);
								$.post("${ctx}/sys/role/saveMenus",params, function(result){
									if(result.messageStatus=="1"){
										$.msg_show.Init({
											'msg':result.message,
											'type':'success'
										});
										that.dialog( "close" );
										// $( "#assignFunctionDiv" ).hide();
									}else if(result.messageStatus=="0"){
										$.msg_show.Init({
											'msg':result.message,
											'type':'error'
										});
									}	
								});
                                $( "#assignFunctionDiv" ).addClass('hide')
							} 
						},
						{
							text: "取消",
							"class" : "btn btn-minier",
                            "data-locale":"cancel",
							click: function() {
								$( this ).dialog( "close" );
                                $( "#assignFunctionDiv" ).addClass('hide')

                            }
						}
					],
					open: function( event, ui ) {
                        var qCode = sessionStorage.getItem("qCode");
                        typeChange(qCode);
						$.getJSON( "${ctx}/sys/role/getMenutree",params,function(data) {
							functions=data.selectList;
							functions.remove("");
							$checkableTree = $('#treeview').treeview({
								data: data.data,
								color: "#428bca",
								levels: 0,
								showBorder:true,
								showIcon: true,
								showCheckbox: true,
                                onNodeChecked: function(event, node) {
									functions.push(node.id);
									setParentNodeCheck(node);
								},
								onNodeUnchecked: function (event, node) {
									functions.remove(node.id);
									/* if(node.nodes != undefined){
										$checkableTree.treeview('uncheckNode',[ node.nodes, { silent: true }]);
										setChildrenChecked(node,'uncheckNode');
									} */
								} 
							});
							/* function setChildrenChecked(node,status){
								$.each(node.nodes,function(i,n){
									if(n.nodes != undefined){
										$checkableTree.treeview(status,[ n.nodes, { silent: true }]);
										setChildrenChecked(n,status);
									}
								});
							} */

							// 节点选中时，祖节点也选中
                            function setParentNodeCheck(node) {
                                if (node.parentId) {
                                    var parentNode = $("#treeview").treeview("getNode", node.parentId);
                                    $("#treeview").treeview("checkNode", [parentNode.nodeId, { silent: true }]);
                                    functions.push(parentNode.id);
                                    setParentNodeCheck(parentNode);
                                }
                            }
						});
					}
				});
			}
			
			function deleteRow(id){
				//信息确认插件
				$.msg_confirm.Init({
					'msg': '确认删除该角色吗？',//这个参数可选，默认值：'这是信息提示！'
					'confirm_fn':function(){
						var url = "${ctx}/sys/role/doDelete?id="+id;
						$.get(url, function(result) {
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
						}, 'json'); 
					},//这个参数可选，默认值：function(){} 空的方法体
					'cancel_fn':function(){
					//点击取消后要执行的操作
					}//这个参数可选，默认值：function(){} 空的方法体
				});
			}
			
			function openDialogAdd(){
				_edit();
			}
			
			function _edit(id){
				var params = {"id":id};
				$.get("${ctx}/sys/role/form",params, function(data,textStatus,object){
					$(".ui-dialog").remove();
					$("#editDivId").html(object.responseText).dialog({
						modal: true,
						width: 600,
						height:600,
						title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-cogs'></i>&nbsp;<span data-locale='RoleMaintenance'>角色维护</span></h4></div>",
						title_html: true,
						buttons: [ 
							{
								text: "保存",
								"class" : "btn btn-primary btn-minier",
                                "data-locale":"doSave",
								click: function() {
									$("#inputForm").bootstrapValidator('validate');
								} 
							},
							{
								text: "取消",
								"class" : "btn btn-minier",
                                "data-locale":"cancel",
								click: function() {
									$(this).dialog("close"); 
								} 
							}
						],
						open:function(event,ui){
                            var qCode = sessionStorage.getItem("qCode");
                            typeChange(qCode);
							$('.select2').select2({allowClear:true});
							/* $("#inputForm #name").off("change.name").on("change.name",function(event){
								 ;
								var bootstrapValidator = $('#inputForm').data('bootstrapValidator');
								bootstrapValidator.enableFieldValidators('name', true);
							});
							$("#inputForm #enname").off("focus.enname").on("focus.enname",function(event){
								var bootstrapValidator = $('#inputForm').data('bootstrapValidator');
								bootstrapValidator.enableFieldValidators('enname', true);
							}); */
						},
						create: function( event, ui ) {
                            $(".ui-dialog-title .widget-header").on('mouseenter',function(){
                                $(".ui-dialog-content input").blur();
                            })
                            $("#selectOfficeMenu").on('click', function(e) {
								e.preventDefault();
						
								 $( "#selectOfficeTreeDiv" ).removeClass('hide').dialog({
									modal: true,
									width:300,
									height:400,
									title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-university'></i>&nbsp;<span data-locale='Selectorganization'>选择机构</span></h4></div>",
									title_html: true,
									buttons: [ 
										{
											text: "确定",
											"class" : "btn btn-primary btn-minier",
                                            "data-locale":"define",
											click: function() {
												$("#inputForm input#office\\.name").val($('#popuptreeview').attr('data-text'));
												$("#inputForm input#office\\.id").val($('#popuptreeview').attr('data-id'));
												$( this ).dialog( "close" ); 
												$( "#selectOfficeTreeDiv" ).hide();
											}
										},
										{
											text: "取消",
											"class" : "btn btn-minier",
                                            "data-locale":"cancel",
											click: function() {
												$( this ).dialog( "close" ); 
												$( "#selectOfficeTreeDiv" ).hide();
											}
										}
									],
									open: function( event, ui ) {
										$.getJSON( "${ctx}/sys/office/getOfficetree",{"treetype":1},function(data) {
                                            var qCode = sessionStorage.getItem("qCode");
                                            typeChange(qCode);
											$('#popuptreeview').treeview({
												data: data,
												levels: 3,
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
					//角色页面维护表单验证
					$("#inputForm").bootstrapValidator({
						message : "请录入一个有效值",
						feedbackIcons : {
							valid : "glyphicon glyphicon-ok",
							invalid : "glyphicon glyphicon-remove",
							validating : "glyphicon glyphicon-refresh"
						},
						fields : {
							name : {
								//enabled:false,
								validators : {
									notEmpty : {
										message : "角色名称不能为空"
									},
									remote : {
										message : "角色名称不能重复",
										url:'${ctx}/sys/role/checkNameAjax',
										data: function(validator){
											return {id : validator.getFieldElements('id').val()	};
										}
									}
								}
							},
							enname : {
								//enabled:false,
								validators : {
									notEmpty : {
										message : "英文名称不能为空"
									},
									remote : {
										message : "英文名称不能重复",
										url:'${ctx}/sys/role/checkEnnameAjax',
										data: function(validator){
											return {id : validator.getFieldElements('id').val()	};
										}
									}
								}
							}
						}
					}).on("success.form.bv",function(e) {
						// Prevent form submission
						e.preventDefault();
						// Get the form instance
						var $form = $(e.target);
						var name = $("#roleDivId #name").val();
						if(name == null || name==""){
							$.msg_show.Init({
								'msg':"角色名称不能为空",
								'type':'error'
							});
							return;
						}
						var enname = $("#roleDivId #enname").val();
						if(enname == null || enname==""){
							$.msg_show.Init({
								'msg':"英文名称不能为空",
								'type':'error'
							});
							return;
						}
						// Get the BootstrapValidator instance
						var bv = $form.data("bootstrapValidator");
						// Use Ajax to submit form data
						$.post($form.attr("action"), $form.serialize(),function(result) {
							if(result.messageStatus=="1"){
								$("#editDivId").dialog("close");
								$(grid_selector).trigger("reloadGrid");
								$.msg_show.Init({
									'msg':result.message,
									'type':'success'
								});
							}else if(result.messageStatus=="0"){
								$.msg_show.Init({
									'msg':result.message,
									'type':'error'
								});
							}
						}, "json");
					});
				});
			}
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
