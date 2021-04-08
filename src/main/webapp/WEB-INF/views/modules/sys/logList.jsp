<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>日志管理</title>
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
					<form:form id="searchForm" modelAttribute="log" class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-xs-12 col-sm-1 no-padding-right"for="title">标题:</label>
							<div class="col-xs-12 col-sm-3">
								<form:input path="title" htmlEscape="false" maxlength="50" class="ace width-100"/>
							</div>
							
							<label class="control-label col-xs-12 col-sm-1 no-padding-right"for="createBy.loginName">用户登录名:</label>
							<div class="col-xs-12 col-sm-3">
								<form:input path="createBy.loginName" htmlEscape="false" maxlength="50" class="ace width-100"/>
							</div>
							
							<label class="control-label col-xs-12 col-sm-1 no-padding-right"for="requestUri">URI:</label>
							<div class="col-xs-12 col-sm-3">
								<form:input path="requestUri" htmlEscape="false" maxlength="50" class="ace width-100"/>
							</div>							
						</div>
							
						<div class="form-group">
							<label class="control-label col-xs-12 col-sm-1 no-padding-right" for="type">日期范围:</label>

							<div class="col-xs-12 col-sm-3">
								<div class="input-daterange input-group">
									<input type="text" class="input-sm form-control" id="beginDate" name="beginDate" value="<fmt:formatDate value="${beginDate}" type="date"/>"/>
									<span class="input-group-addon"><i class="fa fa-exchange"></i></span>
									<input type="text" class="input-sm form-control" id="endDate" name="endDate"  value="<fmt:formatDate value="${endDate}" type="date"/>"/>
								</div>
							</div>
							
							<label class="control-label col-xs-12 col-sm-1 no-padding-right" for="type">查询范围:</label>
							<div class="col-xs-12 col-sm-3">
								<select id="type" name="type" class="chosen-select form-control" data-placeholder="点击选择...">
									<option value="">全部</option>
									<option value="1">接入日志</option>	
									<option value="2">异常日志</option>									
								</select>
							</div>
							<div class="col-xs-12 col-sm-1 no-padding-right">
							&nbsp;
							</div>
							<div class="col-xs-12 col-sm-3 no-padding-right">
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
		<div class="widget-box" style="display:none" id="editDivId">
			
		</div>
	</div>
</div>
	<script type="text/javascript">
	var scripts = [null,'${ctxStatic}/assets/js/date-time/bootstrap-datepicker.js','${ctxStatic}/assets/js/date-time/bootstrap-datepicker.zh-CN.min.js', null];
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		jQuery(function($){
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

			$('.input-daterange').datepicker({autoclose:true,zIndexOffset:100,format: "yyyy-mm-dd",language:"zh-CN"});
  
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";			

			var reSizeHeight = function(){
				var strs = $.getWindowSize().toString().split(",");
				var jqgrid_height = strs[0]-337-48;
                $(grid_selector).jqGrid('setGridHeight',jqgrid_height);
			};
						
			jQuery(grid_selector).jqGrid({			
		        datatype: "json", //将这里改为使用JSON数据    
		        url:'${ctx}/sys/log/searchPage', //这是数据的请求地址  
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
				colNames:['操作菜单','操作用户', '所在部门','URI','日志类型','操作者IP','操作时间','提交方式','提交参数','用户代理','异常信息'],    
		        colModel:[    
		            {name:'title',index:'title'},    
		            {name:'createBy.name',index:'create_by'},    
		            {name:'createBy.office.name',index:'create_by'},
		            {name:'requestUri',index:'request_uri'},
		            {name:'type',index:'type'},
		            {name:'remoteAddr',index:'remote_addr'},    
		            {name:'createDate',index:'create_date'},    
		            {name:'method',index:'method',hidden:true,title:false},    
		            {name:'params',index:'params',hidden:true,title:false},    
		            {name:'userAgent',index:'user_agent',hidden:true,title:false},   
		            {name:'exception',index:'exception',hidden:true,title:false},  
		        ],    		
				viewrecords : true,
				rowNum:20,
				rowList:[10,20,30],
				pager : pager_selector,
				altRows: true,
				//toppager: true,
				subGrid: true, // set the subGrid property to true to show expand buttons for each row
	            //subGridRowExpanded: showChildGrid, // javascript function that will take care of showing the child grid
	            subGridOptions : {
					plusicon : "ace-icon fa fa-plus center bigger-110 blue",
					minusicon  : "ace-icon fa fa-minus center bigger-110 blue",
					openicon : "ace-icon fa fa-chevron-right center orange"
				},
				//for this example we are using local data
				subGridRowExpanded: function (subgridDivId, rowId) {
					var rowData = $(grid_selector).getRowData(rowId);
					var template = '<div class="profile-user-info profile-user-info-striped">\
						<div class="profile-info-row">\
							<div class="profile-info-name">用户代理</div>\
							<div class="profile-info-value">'+rowData.userAgent+'</div>\
						</div>\
						<div class="profile-info-row">\
							<div class="profile-info-name">方法</div>\
							<div class="profile-info-value">'+rowData.method+'</div>\
						</div>\
						<div class="profile-info-row">\
							<div class="profile-info-name">提交参数</div>\
							<div class="profile-info-value">'+rowData.params+'</div>\
							</div>\
							<div class="profile-info-row">\
								<div class="profile-info-name">异常信息</div>\
								<div class="profile-info-value">'+rowData.exception+'</div>\
							</div>\
						</div>';
					$("#" + subgridDivId).html(template);
				},
		
				loadComplete : function() {
					 $.changeGridTable.changeStyle(this);
				},
				
				caption: "日志列表",

				gridComplete: function () {
					var ids = $(grid_selector).jqGrid('getDataIDs');
			         for (var i = 0; i < ids.length; i++) {
						var id = ids[i];			
			         	var rowData = $(grid_selector).getRowData(id);				         	
			         	var type = getDictLabel(${fns:toJson(fns:getDictList("sys_log_type"))}, rowData.type);
			         	$(grid_selector).jqGrid('setRowData', ids[i], { type:type});		
			         }
        		}
		
			});			
			
			$.changeGridTable.changeSize([grid_selector,grid_selector+" ~ .widget-box"],reSizeHeight);
			
			//search list by condition
			$("#query").click(function(){ 
		        var loginName = $("#createBy\\.loginName").val(); 
		        var title =  $("#title").val(); 
		        var uri =  $("#requestUri").val(); 
		        var beginDate =  $("#beginDate").val(); 
		        var endDate =  $("#endDate").val(); 
		        var type =  $("#type").val(); 
		        $("#grid-table").jqGrid('setGridParam',{ 
		            url:"${ctx}/sys/log/searchPage", 
		            mtype:"post",
		            postData:{'createBy.loginName':loginName,'title':title,'uri':uri,'beginDate':beginDate,'endDate':endDate,'type':type}, //发送数据 
		            page:1 
		        }).trigger("reloadGrid"); //重新载入 
		    }); 			

			$("#reset").click(function(){ 
				$('.chosen-select').val('').trigger('chosen:updated');
		        $("#grid-table").jqGrid('setGridParam',{ 
		            url:"${ctx}/sys/log/searchPage", 
		            mtype:"post",
		            postData:{'createBy.loginName':'','title':'','uri':'','beginDate':'','endDate':'','type':''}, 
		            page:1 
		        }).trigger("reloadGrid"); //重新载入 
		    }); 
			
			$(document).one('ajaxloadstart.page', function(e) {
				$.jgrid.gridUnload(grid_selector);
				$('.ui-jqdialog').remove();
			});
		});
	});
</script>