<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

	<title>模型管理</title>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class="widget-box widget-compact">
			<div class="widget-header widget-header-blue widget-header-flat">
				<h5 class="widget-title lighter">
					查询条件
				</h5>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse"> <i
						class="ace-icon fa fa-chevron-up"></i> </a>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<form id="searchForm" action="${ctx}/act/model/" method="post"
						class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-xs-12 col-sm-2 no-padding-right" for="category">
								类型:
							</label>

							<div class="col-xs-12 col-sm-5">
								<div class="clearfix input-group">
									<select id="category" name="category" data-placeholder="点击选择..." class="chosen-select form-control">
										<option value="">
											全部分类
										</option>
										<c:forEach items="${fns:getDictList('act_category')}"
											var="dict">
											<option value="${dict.value}" ${dict.value==category?'selected':''}>
												${dict.label}
											</option>
										</c:forEach>
									</select>

								</div>
							</div>

							<div class="col-xs-12 col-sm-5 no-padding-right">
								<div class="clearfix input-group">
									<button class="btn btn-info btn-sm" type="button" id="query">
										查询
									</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<table id="grid-table"></table>
		<div id="grid-pager"></div>
		<div class="widget-box" style="display:none" id="editDivId"></div>
	</div>
</div> 
<script type="text/javascript">

	var scripts = [ null, null ];
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		jQuery(function($) {
			
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				//resize the chosen on window resize
		
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				}).trigger('resize.chosen');
				//resize chosen on sidebar collapse/expand
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				});
			}
  
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";
		
			jQuery(grid_selector).jqGrid({			
		        datatype: "json", //将这里改为使用JSON数据    
		        url:'${ctx}/act/model/searchPage', //这是数据的请求地址  
				height: 300,
				autowidth:false,

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
				colNames:['流程分类','模型ID', '模型标识','模型名称','版本号','创建时间','最后更新时间','操作'],    
		        colModel:[    
		        	{name:'id',index:'id', editable: true},
		            {name:'id',index:'id', editable: true},    
		            {name:'key',index:'key', editable: true},    
		            {name:'name',index:'name', editable: true},
		            {name:'version',index:'version', editable: true},    
		            {name:'createTime',index:'createTime', editable: true},    
		            {name:'lastUpdateTime',index:'lastUpdateTime'}, 
		            {name:'oparation',index:'oparation', editable: true}

		             
		        ],    
				viewrecords : true,
				rowNum:20,
				rowList:[10,20,30],
				pager : pager_selector,
				altRows: true,
				//toppager: true,
				
				multiselect: true,
				//multikey: "ctrlKey",
		        multiboxonly: true,
		
				loadComplete : function() {
					 $.changeGridTable.changeStyle(this);				
				},
		
				editurl: "/dummy.html",//nothing is saved
				caption: "模型列表",
				//toolbar: [true,"top"],
				gridComplete: function () {
				     var ids = jQuery("#grid-table").jqGrid('getDataIDs');
			         for (var i = 0; i < ids.length; i++) {
						var id = ids[i];			
			         	var rowData = $("#grid-table").getRowData(id);	
			         	var edit = "";
			         	var deploy = "";
			         	var exporter = "";
			         	var deleter = "";
			         	edit = "<a href='${pageContext.request.contextPath}/act/rest/service/editor?id="+rowData.id+"' target='_blank'>编辑</a>|";
						deploy = "<a data-action='deploy' data-id='"+rowData.id+"' href='javascript:void(0);'>部署</a>|";
						exporter = "<a href='${ctx}/act/model/export?id="+rowData.id+"' target='_blank'>导出</a>|"
						deleter = "<a data-action='delete' data-id='"+rowData.id+"' href='javascript:void(0);'>删除</a>";
															
			         	jQuery("#grid-table").jqGrid('setRowData', ids[i], { oparation:edit+deploy+exporter+deleter});	

			         }
			         
        		}
		
			});
					         	
      		$.changeGridTable.changeSize([grid_selector]);
      		
      		//override dialog's title function to allow for HTML titles
			$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
				_title: function(title) {
					var $title = this.options.title || '&nbsp;'
					if( ("title_html" in this.options) && this.options.title_html == true )
						title.html($title);
					else title.text($title);
				}
			}));
			
				  
			//navButtons
			jQuery(grid_selector).jqGrid('navGrid',pager_selector,
				{ 	//navbar options
					add: true,
					addicon : 'ace-icon fa fa-plus-circle purple',
					addfunc : openDialogAdd,
					edit: false,
					refresh: true,
					refreshicon : 'ace-icon fa fa-refresh green',
					view: true,
					viewicon : 'ace-icon fa fa-search-plus grey',
					del:false,
					search: false
				}
			);
			
			function openDialogAdd(){
	            $.get("${ctx}/act/model/toCreate",{}, function(data,textStatus,object){
	            	$(".ui-dialog").remove();
	                $("#editDivId").html(object.responseText).dialog({
						modal: true,
						width:"auto",
						height:"auto",
						title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller'><i class='ace-icon fa fa-indent'></i>&nbsp;新建模型</h4></div>",
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
						create: function( event, ui ) {
							$('#dictDivId #sort').ace_spinner({value:0,min:0,max:10000,step:10, on_sides: true, icon_up:'ace-icon fa fa-plus bigger-110', icon_down:'ace-icon fa fa-minus bigger-110', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});							
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
							category : {
								validators : {
									notEmpty : {
										message : "流程分类不能为空"
									}
								}
							},
							name : {
								validators : {
									notEmpty : {
										message : "模块名称不能为空"
									}
								}
							},
							key : {
								validators : {
									notEmpty : {
										message : "模块标识不能为空"
									}
								}
							},
							description : {
								validators : {
									notEmpty : {
										message : "模块描述不能为空"
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
							//$("#inputForm").submit();
							// Use Ajax to submit form data
							$.post($form.attr("action"), $form.serialize(),function(result) {
								if(result.messageStatus=="1"){
									window.open("${pageContext.request.contextPath}/act/rest/service/editor?id="+result.id); 
								  	$.msg_show.Init({
								        'msg':result.message,
								        'type':'success'
								    });		
									$("#editDivId").dialog("close");
									$(grid_selector).trigger("reloadGrid");
									
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
		    
		    
			//部署
         	$("#grid-table").on('click', 'a[data-action=deploy]',function(event) {
				var id = $(this).attr('data-id');
				$.msg_confirm.Init({
				    'msg': '确认要部署该模型？',//这个参数可选，默认值：'这是信息提示！'
				    'confirm_fn':function(){
						// Use Ajax to submit form data
						$.post("${ctx}/act/model/deploy", {id:id},function(result) {
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
						}, "json");							         
				    },//这个参数可选，默认值：function(){} 空的方法体
				    'cancel_fn':function(){
				        //点击取消后要执行的操作
				    }//这个参数可选，默认值：function(){} 空的方法体
				});
				
			});
			
			
			//删除
         	$("#grid-table").on('click','a[data-action=delete]', function(event) {
				var id = $(this).attr('data-id');
				$.msg_confirm.Init({
				    'msg': '确认要删除该模型吗？',//这个参数可选，默认值：'这是信息提示！'
				    'confirm_fn':function(){
						// Use Ajax to submit form data
						$.post("${ctx}/act/model/delete", {id:id},function(result) {
						  	$.msg_show.Init({
						        'msg':result.message,
						        'type':'success'
						    });		
							$(grid_selector).trigger("reloadGrid");
											
						}, "json");							         
				    },//这个参数可选，默认值：function(){} 空的方法体
				    'cancel_fn':function(){
				        //点击取消后要执行的操作
				    }//这个参数可选，默认值：function(){} 空的方法体
				});
				
			});
											
			//search list by condition
			$("#query").click(function(){ 
		        var category =  $("#category").val(); 
		        $("#grid-table").jqGrid('setGridParam',{ 
		            url:"${ctx}/act/model/searchPage", 
		            mtype:"post",
		            postData:{'category':category}, //发送数据 
		            page:1 
		        }).trigger("reloadGrid"); //重新载入 
		    }); 	
			//$("#t_grid-table").append("<input type='button' value='Click Me' style='height:20px;font-size:-3'/>"); 
			//$("input","#t_grid-table").click(function(){ alert("Hi! I'm added button at this toolbar"); });
			//$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size	

			$.changeGridTable.changeSize([grid_selector]);
		});
	});
</script>
