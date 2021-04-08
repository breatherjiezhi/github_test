<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/xcConfirm.jsp"%>
<title>任务清单管理</title>
<script type="text/javascript">
function confirmModal(){
}
//恢复暂停的任务
function start(id){
	var rowData = $("#grid-table").getRowData(id);	
 	var jobId = rowData.jobId;
 	if(rowData.currentStatus == '00' || rowData.currentStatus == '02'){
	    $.msg_show.Init({
	        'msg':'该任务已经在执行中或者已删除，无法恢复！',
	        'type':'error'
	    });
	    return false;
	}
 	var params = {"jobId":jobId};
	$.post("${ctx}/system/scheduleJob/startTask", params,function(result){
		
	    if(result.messageStatus=="1"){
	    
		  	$.msg_show.Init({
		  		
		        'msg':result.message,
		        'type':'success'
		    });	
			$("#grid-table").trigger("reloadGrid");
			
		}if(result.messageStatus=="0"){
		  	$.msg_show.Init({
		        'msg':result.message,
		        'type':'error'
		    });								
		}
		
	});	
}
//暂停任务
function stop(id){
	var rowData = $("#grid-table").getRowData(id);	
 	var jobId = rowData.jobId;
 	if(rowData.currentStatus == '01' || rowData.currentStatus == '02'){
	    $.msg_show.Init({
	        'msg':'该任务已经暂停或者已删除，无法暂停状态！',
	        'type':'error'
	    });
	    return false;
	}
 	var params = {"jobId":jobId};
	$.post("${ctx}/system/scheduleJob/stopTask", params,function(result){
		
	    if(result.messageStatus=="1"){
	    
		  	$.msg_show.Init({
		  		
		        'msg':result.message,
		        'type':'success'
		    });	
			$("#grid-table").trigger("reloadGrid");
			
		}if(result.messageStatus=="0"){
		  	$.msg_show.Init({
		        'msg':result.message,
		        'type':'error'
		    });								
		}
		
	});	
}
//删除当前任务
function deletejob(id){
	var rowData = $("#grid-table").getRowData(id);	
 	var jobId = rowData.jobId;
 	if(rowData.currentStatus == '02'){
	    $.msg_show.Init({
	        'msg':'该任务已删除，请勿重复操作！',
	        'type':'error'
	    });
	    return false;
	}
 	var txt=  "任务删除后将不在继续执行,确定删除？";
	window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.confirm,
	{onOk:function(){
		var params = {"jobId":jobId};
		$.post("${ctx}/system/scheduleJob/deleteTask", params,function(result){
			
		    if(result.messageStatus=="1"){
			  	$.msg_show.Init({
			        'msg':result.message,
			        'type':'success'
			    });	
				$("#grid-table").trigger("reloadGrid");
				
			}if(result.messageStatus=="0"){
			  	$.msg_show.Init({
			        'msg':result.message,
			        'type':'error'
			    });								
			}
			
		});	
		
	}});
  	
}
</script>
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
				<div class="widget-main">
					<form:form id="searchForm" modelAttribute="scheduleJob" class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-xs-12 col-sm-1 no-padding-right"for="positionStatus">任务状态:</label>
							<div class="col-xs-12 col-sm-2">
								<select id="_positionStatus" name="positionStatus" data-placeholder="点击选择..." class="chosen-select form-control">
									<option value="">请选择……</option>
									 <c:forEach items="${fns:getDictList('taskStatus')}"
										var="dict">
										<option value="${dict.value}">${dict.label}
										</option>
									 </c:forEach>
								</select>
							</div>	
							
<!-- 							<label class="control-label col-xs-12 col-sm-1 no-padding-right"for="houseCode">库房名称:</label> -->
<!-- 							<div class="col-xs-12 col-sm-2"> -->
<!-- 								<select id="houseCode" name="houseCode" data-placeholder="点击选择..." class="chosen-select form-control"> -->
<!-- 									<option value="">请选择……</option> -->
<%-- 									<c:forEach items="${storeHouseList}" var="r"> --%>
<%-- 										<option value="${r.value}">${r.value}</option> --%>
<%-- 									</c:forEach> --%>
<!-- 								</select> -->
<!-- 							</div>	 -->
							
							<div class="col-xs-12 col-sm-3 no-padding-right" style="float:right">
									<button class="btn btn-info btn-sm" type="button" id="query">
									查询
								</button>
								<button class="btn btn-info btn-sm" type="reset" id="reset">
									重置
								</button>
							</div>						
						</div>
					</form:form>
				</div>
			</div>
		</div>
		
				<table id="grid-table"></table>
				<div id="grid-pager"></div>
				<div class="widget-box" style="display:none" id="editDivId"></div>
	
</div>
</div>
	<script type="text/javascript">
	var scripts = [null,
		'${ctxStatic}/bootstrap-treeview/js/bootstrap-treeview.js',
		null];
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		jQuery(function($) {
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
			var toolbarTopChild = grid_selector + '_toppager';
			
			var reSizeHeight = function(){
				var strs = $.getWindowSize().toString().split(",");
				var tree_height = strs[0]-146;
				var jqgrid_height = strs[0]-337;
				$(grid_selector).jqGrid('setGridHeight',jqgrid_height);
			};
			
			var jqgridData = function(param){
				$("#grid-table").jqGrid('setGridParam',{ 
					url:"${ctx}/system/scheduleJob/searchPage", 
					mtype:"post",
					postData:param, //发送数据 
					page:1 
				}).trigger("reloadGrid"); //重新载入 
			}
			
			jQuery(grid_selector).jqGrid({
				datatype: "json", //将这里改为使用JSON数据
				url:'${ctx}/system/scheduleJob/searchPage', //这是数据的请求地址
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
// 				colNames:['任务id','任务名称 ', '任务别名','任务分组','触发器','任务状态','任务运行时间表达式','是否异步 ','任务描述 ','创建时间','修改时间'],
// 				colModel:[
// 					{name:'jobId',hidden: true,key:true},
// 					{name:'jobName', width:120},
// 					{name:'description',width:140},
// 					{name:'jobGroup',width:100},
// 					{name:'jobTrigger', width:100},
// 					{name:'jobStatus',width:80},
// 					{name:'cronExpression',width:80},
// 					{name:'isSync', width:80},
// 					{name:'description', width:140},
// 					{name:'createTime',width:130},
// 					{name:'updateTime',width:130},
// 				],

				colModel: [
					{name: 'jobId', label: '任务id', fixed: true, key: true,hidden:true},
					{name: 'jobName', label: '任务名称',  fixed: true,width:120},
					{name: 'description', label: '任务描述',  fixed: true,width:140},
					{name: 'jobGroup', label: '任务分组',  fixed: true,width:100},
					{name: 'jobTrigger', label: '触发器',  fixed: true,width:100,hidden:true},
					{name: 'jobStatus', label: '触发器状态',  fixed: true,width:80},
					{name: 'taskStatus', label: '任务状态',  fixed: true,width:80,hidden:true},
					{name: 'currentStatus', label: '当前状态',  fixed: true,hidden:true},
                    {name: 'currentStatusName', label: '当前状态',  fixed: true,width:80},
					{name: 'cronExpression', label: '任务运行时间表达式',  fixed: true,width:120},
					{name: 'isSync', label: '是否异步',  fixed: true,width:80,hidden: true},
					{name: 'createTime', label: '创建时间',  fixed: true,width:130},
					{name: 'updateTime', label: '修改时间',  fixed: true,width:130},
					{name: 'operate', label: '操作', fixed: true, width: 86}],

				
				viewrecords : true,
				rowNum:10,
				rowList:[10,20,30],
				pager : pager_selector,
				altRows: true,
				toppager: true,
				
				multiselect: true,
				//multikey: "ctrlKey",
				multiboxonly: true,
				shrinkToFit: false,   //滚动条
				autoScroll: false, //滚动条
				
				loadComplete : function() {
					 $.changeGridTable.changeStyle(this);	
					 $(grid_selector+"_toppager_center").remove();
					 $(grid_selector+"_toppager_right").remove();
					 $(pager_selector+"_left table").remove();
				},
		
				editurl: "/dummy.html",//nothing is saved
 				caption: "任务配置列表",
				//toolbar: [true,"top"],
				gridComplete: function () {
					var ids = $(grid_selector).jqGrid('getDataIDs');
			         for (var i = 0; i < ids.length; i++) {
						var id = ids[i];
						var rowData = $("#grid-table").getRowData(id);
						var viewBtn = null;
						if(rowData.jobStatus == '1')
						{
		 	        		viewBtn = "<div class='hidden-sm hidden-xs btn-group'><a class='btn btn-xs btn-info' title='暂停任务' onclick='javascript:stop("+id+")'><i class='ace-icon fa fa-pause' title='暂停任务'></i></a><a class='btn btn-xs btn-success' title='恢复任务' onclick='start("+id+");'><i class='ace-icon fa fa-play' title='恢复任务'></i></a><a class='btn btn-xs btn-danger' title='删除任务' onclick='deletejob("+id+");'><i class='ace-icon fa fa-trash-o' title='删除任务'></i></a></div>";
						}
			         	var jobStatus = getDictLabel(${fns:toJson(fns:getDictList("jobStatus"))}, rowData.jobStatus);
						var currentStatus = getDictLabel(${fns:toJson(fns:getDictList("jobCurrentStatus"))}, rowData.currentStatus);
 			         	$(grid_selector).jqGrid('setRowData', ids[i], {jobStatus: jobStatus,currentStatusName: currentStatus,operate: viewBtn});
			         }
        		}
		
			});				
			
			//$("#t_grid-table").append("<input type='button' value='Click Me' style='height:20px;font-size:-3'/>"); 
			//$("input","#t_grid-table").click(function(){ alert("Hi! I'm added button at this toolbar"); });
			reSizeHeight(true);		
			$.changeGridTable.changeSize([grid_selector,grid_selector+" ~ .widget-box"],reSizeHeight);
			//search list by condition
			$("#query").click(function(){ 
		        var mdsItemCode = $("#mdsItemCode").val(); 
		        var houseCode = $("#houseCode").val();
		        var containerCode = $("#containerCode").val();
		        $("#grid-table").jqGrid('setGridParam',{
		            url:"${ctx}/basisdata/Borrowed/searchPage",
		            mtype:"post",
		            postData:{'mdsItemCode':mdsItemCode,'houseCode':houseCode,'containerCode':containerCode}, //发送数据 
		            page:1
		        },true).trigger("reloadGrid"); //重新载入 		        
		    }); 
			$("#reset").click(function(){ 
				$('.chosen-select').val('').trigger('chosen:updated');
		        $(grid_selector).jqGrid('setGridParam',{ 
		            url:"${ctx}/basisdata/Borrowed/searchPage", 
		            mtype:"post",
		            postData:{'mdsItemCode':'','houseCode':'','containerCode':''}, 
		            page:1 
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
					add: true,
					addicon : 'ace-icon fa fa-plus-circle purple',
					addfunc : openDialogAdd,
					addtext:"新增",
					addtitle:'',
					del: false,
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
					view: true,
					viewicon : 'ace-icon fa fa-play green',
					viewfunc:doLaunch,
					viewtext:"立即运行一次",
					viewtitle:'',
					cloneToTop:true,
				},
				{}, // use default settings for edit
				{}, // use default settings for add
				{},  // delete instead that del:false we need this
				{multipleSearch : true}, // enable the advanced searching
				{closeOnEscape:true} /* allow the view dialog to be closed when user press ESC key*/
			).jqGrid("navButtonAdd",toolbarTopChild,{  
				   caption:"启动任务",   
	               buttonicon:"ace-icon fa fa-play-circle red",   
	               title:"启动任务",  
	               onClickButton: startjob, 
	               position:"last" 
			});
			//override dialog's title function to allow for HTML titles
			$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
				_title: function(title) {
					var $title = this.options.title || '&nbsp;'
					if( ("title_html" in this.options) && this.options.title_html == true )
						title.html($title);
					else title.text($title);
				}
			}));
			//启动任务
			function startjob(){
				//信息确认插件
				var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
				
				if(selectedIds.length<1){
				    //失败
				    $.msg_show.Init({
				        'msg':'请您至少选择一条记录',
				        'type':'error'
				    });	
			 	}
				else{
			 		for(var i = 0; i<selectedIds.length; i++){
			 			var row = $(grid_selector).jqGrid('getRowData',selectedIds[i]);
			 			if(row.taskStatus == '是'){
						    $.msg_show.Init({
						        'msg':'任务已启动，正在执行中，请勿重复启动！',
						        'type':'error'
						    });
						    return false;
			 			}
		 			}			 		
			 	}
				var arrayObj = "";
				for (var i=0;i<selectedIds.length ;i++ ){
					var rowData = $("#grid-table").getRowData(selectedIds[i]);
					var id = rowData.jobId;
					arrayObj = (arrayObj + id) + (((i + 1)== selectedIds.length) ? '':',');  
				} 	 
				// alert(arrayObj)
		 		var params = {"ids":arrayObj};
				$.post("${ctx}/system/scheduleJob/addJob", params,function(result){
					
				    if(result.messageStatus=="1"){
					  	$.msg_show.Init({
					        'msg':result.message,
					        'type':'success'
					    });	
						$(grid_selector).trigger("reloadGrid");
					
				    }if(result.messageStatus=="0"){
					  	$.msg_show.Init({
					        'msg':result.message,
					        'type':'error'
					    });								
					}
					
				});	
			}	
			
		    function openDialogAdd(){
		       _edit();		       
		    }
			
			function openDialogEdit(){
				var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
			 	if(selectedIds.length>1){
				    //失败
				    $.msg_show.Init({
				        'msg':'请您选择一条记录修改',
				        'type':'error'
				    });				
 
			 	}else{
			 		for(var i = 0; i<selectedIds.length; i++){
			 			var row = $(grid_selector).jqGrid('getRowData',selectedIds[i]);
			 			_edit(selectedIds[0]);
			 			//alert(row.planStatus);
// 			 			if(row.isEnabled == '否'){
// 						   _edit(selectedIds[0]);
// 			 			}
// 			 			else{
// 						    $.msg_show.Init({
// 						        'msg':'已启动的库房不能进行编辑操作！',
// 						        'type':'error'
// 						    });
// 						    return false;
// 			 			}
		 			}	
			 	}
			 
			}
			function doDelete(){
				//信息确认插件
				var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
				
				if(selectedIds.length<1){
				    //失败
				    $.msg_show.Init({
				        'msg':'请您至少选择一条记录',
				        'type':'error'
				    });	
			 	}else{
			 		for(var i = 0; i<selectedIds.length; i++){
			 			var row = $(grid_selector).jqGrid('getRowData',selectedIds[i]);
			 			if(row.isEnabled == '是'){
						    $.msg_show.Init({
						        'msg':'已启动的库房不能进行删除操作！',
						        'type':'error'
						    });
						    return false;
			 			}
		 			}			 		
			 	}
				var arrayObj = "";
				for (var i=0;i<selectedIds.length ;i++ ){
					var rowData = $("#grid-table").getRowData(selectedIds[i]);
					var id = rowData.houseCode;
					arrayObj = (arrayObj + id) + (((i + 1)== selectedIds.length) ? '':',');  
				} 	 
		 		var params = {"ids":arrayObj};
				$.post("${ctx}/basisdata/storeHouse/batchDelete", params,function(result){
					
				    if(result.messageStatus=="1"){
				    
					  	$.msg_show.Init({
					  		
					        'msg':result.message,
					        'type':'success'
					    });	
						$(grid_selector).trigger("reloadGrid");
						
					}if(result.messageStatus=="2"){
					    
					  	$.msg_show.Init({
					  		
					        'msg':result.message,
					        'type':'success'
					    });	
						
						
					}if(result.messageStatus=="0"){
					  	$.msg_show.Init({
					        'msg':result.message,
					        'type':'error'
					    });								
					}
					
				});	
			}	
			
			
			// 立即运行一次
			function doLaunch(){
				//信息确认插件
				var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
				if(selectedIds.length<1){
				    //失败
				    $.msg_show.Init({
				        'msg':'请您选择一条记录',
				        'type':'error'
				    });	
			 	}
				if(selectedIds.length>1){
				    //失败
				    $.msg_show.Init({
				        'msg':'至多选择一条记录',
				        'type':'error'
				    });	
			 	}
				var arrayObj = "";
				
				var rowData = $("#grid-table").getRowData(selectedIds[0]);	
				var id = rowData.jobId;
				
		 		var params = {"jobId":id};
				$.post("${ctx}/system/scheduleJob/runJobNow", params,function(result){
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
	
			function _edit(id){		
				var params = {};
				if(id == null || id == undefined || id ==""){
					params.jobId="";					
					params.operType="new";
				}else{
					params.jobId=id;	
					params.operType="mod";
				}
				$.post("${ctx}/system/scheduleJob/form",params, function(data,textStatus,object){
					$(".ui-dialog").remove();
					$("#editDivId").html(object.responseText).dialog({
						modal: true,
						width:800,
						height:560,
						title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller'><i class='ace-icon fa fa-users'></i>&nbsp;定时器配置信息</h4></div>",
						title_html: true,
						buttons: [ 
							{
								text: "保存",
								"class" : "btn btn-primary btn-minier",
								click: function() {
									if(confirmModal()!=false){
									if(id==null){
										$.post("${ctx}/system/scheduleJob/doSave", $("#inputForm").serialize(),function(result) {
											if(result.messageStatus=="1"){
												$.msg_show.Init({
													'msg':result.message,
													'type':'success'
												});		
												$("#editDivId").dialog("close");
												$(grid_selector).trigger("reloadGrid");
												
											}else if(result.messageStatus=="2"){
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
									}else{
										$.post("${ctx}/system/scheduleJob/doSave", $("#inputForm").serialize(),function(result) {
											if(result.messageStatus=="1"){
												$.msg_show.Init({
													'msg':result.message,
													'type':'success'
												});		
												$("#editDivId").dialog("close");
												$(grid_selector).trigger("reloadGrid");
												
											}else if(result.messageStatus=="2"){
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
									}
									}
								} 
							},
							{
								text: "保存&启动",
								"class" : "btn btn-primary btn-minier",
								click: function() {
									if(confirmModal()!=false){
									if(id==null){
										$.post("${ctx}/system/scheduleJob/doSaveAndLaunch", $("#inputForm").serialize(),function(result) {
											if(result.messageStatus=="1"){
												$.msg_show.Init({
													'msg':result.message,
													'type':'success'
												});		
												$("#editDivId").dialog("close");
												$(grid_selector).trigger("reloadGrid");
												
											}else if(result.messageStatus=="2"){
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
									}else{
										$.post("${ctx}/basisdata/B030401/doUpdateAndLaunch", $("#inputForm").serialize(),function(result) {
											if(result.messageStatus=="1"){
												$.msg_show.Init({
													'msg':result.message,
													'type':'success'
												});		
												$("#editDivId").dialog("close");
												$(grid_selector).trigger("reloadGrid");
												
											}else if(result.messageStatus=="2"){
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
									}
									}
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
							cronExpression : {
								validators : {
									notEmpty : {
										message : "cronExpression不能为空"
									}
								}
							},
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
									
								}else if(result.messageStatus=="2"){
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
			
			
			
			$(document).one('ajaxloadstart.page', function(e) {
				$.jgrid.gridUnload(grid_selector);
				$('.ui-jqdialog').remove();
				$('[class*=select2]').remove();
			});			
		});	
	});
</script>