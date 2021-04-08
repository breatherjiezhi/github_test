<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>通知管理</title>
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
					<form:form id="searchForm" modelAttribute="oaNotify" class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-xs-12 col-sm-1 no-padding-right"for="title">标题:</label>
							<div class="col-xs-12 col-sm-2">
								<form:input path="title" htmlEscape="false" maxlength="200" class="ace width-100"/>
							</div>
							
							<label class="control-label col-xs-12 col-sm-1 no-padding-right" for="type">类型:</label>

							<div class="col-xs-12 col-sm-2">
								<form:select  path="type" class="chosen-select form-control" data-placeholder="点击选择...">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</div>
							
							<label class="control-label col-xs-12 col-sm-1 no-padding-right" for="status">状态:</label>

							<div class="col-xs-12 col-sm-2">
								<form:select  path="status" class="chosen-select form-control" data-placeholder="点击选择...">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
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
	var scripts = [null, null];
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
  
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";			

			var reSizeHeight = function(){
				var strs = $.getWindowSize().toString().split(",");
				var jqgrid_height = strs[0]-337;
                $(grid_selector).jqGrid('setGridHeight',jqgrid_height);
			};
						
			jQuery(grid_selector).jqGrid({			
		        datatype: "json", //将这里改为使用JSON数据    
		        url:'${ctx}/oa/oaNotify/searchPage', //这是数据的请求地址  
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
				colNames:['标题','类型', '状态','查阅状态','通知时间','操作'],    
		        colModel:[    
		            {name:'title',index:'title', editable: true},    
		            {name:'type',index:'type', editable: true},    
		            {name:'status',index:'status', editable: true},
		            {name:'readFlag',index:'readFlag', editable: true},
		            {name:'updateDate',index:'updateDate', editable: true},    
		            {name:'id',index:'id', width:60,hidden:true}       
		        ],    
		
				viewrecords : true,
				rowNum:20,
				rowList:[10,20,30],
				pager : pager_selector,
				altRows: true,
				
				multiselect: true,
		        multiboxonly: true,
		
				loadComplete : function() {
					 $.changeGridTable.changeStyle(this);
					 $(grid_selector+"_toppager_center").remove();
					 $(grid_selector+"_toppager_right").remove();
					 $(pager_selector+"_left table").remove();
				},
				
				caption: "通知列表",

				gridComplete: function () {
			         
        		}
		
			});
			
			//$("#t_grid-table").append("<input type='button' value='Click Me' style='height:20px;font-size:-3'/>"); 
			//$("input","#t_grid-table").click(function(){ alert("Hi! I'm added button at this toolbar"); });
			$.changeGridTable.changeSize([grid_selector,grid_selector+" ~ .widget-box"],reSizeHeight);
			
			//search list by condition
			$("#query").click(function(){ 
		        var type = $("#type").val(); 
		        var title =  $("#title").val(); 
		        var status =  $("#status").val(); 
		        $("#grid-table").jqGrid('setGridParam',{ 
		            url:"${ctx}/oa/oaNotify/searchPage", 
		            mtype:"post",
		            postData:{'title':title,'type':type,'status':status}, //发送数据 
		            page:1 
		        }).trigger("reloadGrid"); //重新载入 
		    }); 			

			$("#reset").click(function(){ 
				$('.chosen-select').val('').trigger('chosen:updated');
		        $("#grid-table").jqGrid('setGridParam',{ 
		            url:"${ctx}/oa/oaNotify/searchPage", 
		            mtype:"post",
		            postData:{'title':'','type':'','status':''}, 
		            page:1 
		        }).trigger("reloadGrid"); //重新载入 
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
			 		_edit(selectedIds[0]);
			 	}
		  
			}
			
			$(document).one('ajaxloadstart.page', function(e) {
				$.jgrid.gridUnload(grid_selector);
				//$(grid_selector).jqGrid('gridUnload');
				$('.ui-jqdialog').remove();
			});
		});
	});
	</script>