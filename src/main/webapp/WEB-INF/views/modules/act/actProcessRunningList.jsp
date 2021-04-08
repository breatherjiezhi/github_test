<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<title>运行中的流程</title>

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
					<form id="searchForm" action="${ctx}/act/process/running/" method="post"
						class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-xs-12 col-sm-2 no-padding-right" for="category">
								流程实例ID：
							</label>

							<div class="col-xs-12 col-sm-3">
								<div class="clearfix input-group">
									<input type="text" id="procInsId" name="procInsId" class="ace width-100" />
								</div>
							</div>
							<label class="control-label col-xs-12 col-sm-2 no-padding-right" for="category">
								流程定义Key：
							</label>

							<div class="col-xs-12 col-sm-3">
								<div class="clearfix input-group">
									<input type="text" id="procDefKey" name="procDefKey" class="ace width-100" />
								</div>
							</div>
							<div class="col-xs-12 col-sm-2 no-padding-right">
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
		        url:'${ctx}/act/process/runningList', //这是数据的请求地址  
				height: 300,
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
				colNames:['执行ID','流程实例ID', '流程定义ID','当前环节','是否挂起','操作'],    
		        colModel:[    
		            {name:'id',index:'id'},    
		            {name:'processInstanceId',index:'processInstanceId'},    
		            {name:'processDefinitionId',index:'processDefinitionId'},
		            {name:'activityId',index:'activityId'},    
		            {name:'suspended',index:'suspended'},    
		            {name:'delete',index:'delete'}, 

		             
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
				caption: "运行中的流程列表",
				//toolbar: [true,"top"],
				gridComplete: function () {
			         var ids = jQuery("#grid-table").jqGrid('getDataIDs');
			         for (var i = 0; i < ids.length; i++) {
						var id = ids[i];			
			         	var rowData = $("#grid-table").getRowData(id);	
			         	var processInstanceId = rowData.processInstanceId;

						var deleted = "<a data-action='delete' data-id='"+rowData.processInstanceId+"' href='javascript:void(0);'>删除流程</a>";

									
			         	jQuery("#grid-table").jqGrid('setRowData', ids[i], { delete:deleted});	

			         }
			         
        		}
		
			});
					         	
         	//删除
         	$("#grid-table").on('click','a[data-action=delete]', function(event) {
				var processInstanceId = $(this).attr('data-id');
				$.msg_confirm.Init({
				    'msg': '确认要删除该流程吗？',//这个参数可选，默认值：'这是信息提示！'
				    'confirm_fn':function(){
						// Use Ajax to submit form data
						$.post("${ctx}/act/process/deleteProcIns", {processInstanceId:processInstanceId},function(result) {
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
			
											
			//search list by condition
			$("#query").click(function(){ 
		        var procInsId =  $("#procInsId").val(); 
		        var procDefKey =  $("#procDefKey").val(); 
		        $("#grid-table").jqGrid('setGridParam',{ 
		            url:"${ctx}/act/process/runningList", 
		            mtype:"post",
		            postData:{'procInsId':procInsId,'procDefKey':procDefKey}, //发送数据 
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