<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>配送计划列表</title>
<link rel="stylesheet" href="${ctxStatic}/assets/css/bootstrap-datetimepicker.css" />
<style type="text/css">
<!--
	.table-col-edit{
		background-color: #6fb3e0;
	}
	.table-row-no-deal{
		background-color: #dddddd;
	}
	.table-row-exception{
		background-color: #fab5b5;
	}
	.table-cell-on-time{
		background-color: #2BF30A;
	}
	.table-cell-overtime{
		background-color: #ffb752;
	}
	.table-cell-overtime2{
		background-color: #F13414;
	}
	.titleShow{
		line-height: 28px;
		margin-bottom: 16px;
		margin-top: 18px;
		padding-bottom: 4px;
	}
-->
</style>

<div class="row" id="fullScreenWidget">
	<div class="col-xs-12">
		<div class="widget-box" id="fullScreenWidgetBox">
			<div class="widget-header">
				<div class="widget-toolbar">
					<a href="#" data-action="fullscreen" class="orange2">
						<i class="ace-icon fa fa-expand"></i>
					</a>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main" id="fullScreenWidgetMain">
					<div class="row" id="pageBox">
						<div class="col-xs-12" id="titleShow">
							<div class="widget-box widget-compact">
								<div class="widget-body">
									<div class="widget-main no-padding">
										<h4 class="text-center titleShow blue bigger">实时任务&nbsp;&nbsp;<span id="curDateTime"></span></h4>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-12">
							<table id="grid-table"></table>
							<div id="grid-pager"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var scripts = [ null, null ];
	$('.page-content-area').ace_ajax('loadScripts',scripts,function() {
		jQuery(function($) {
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";
			var scrollTimer = null;
			var timerID = null;
			var timeBaseData = 3;
			var heightBaseData = 300;
			var heightBaseDataTmp = 300;
			var listLength;
			var listCount = 0;
				
			heightBaseData = 380;//调节距离底部的高度
			//search list by condition
			//时间显示
			var timerRunning = false;
			var showtime = function () {
				var now = new Date();
				var year = now.getFullYear();
				var month = now.getMonth() + 1;
				var date = now.getDate();
				var hours = now.getHours();
				var minutes = now.getMinutes();
				var seconds = now.getSeconds();
				minutes = ((minutes < 10) ? '0' : '') + minutes;
				seconds = ((seconds < 10) ? '0' : '') + seconds;
				$('#curDateTime').html(year + '-' + month + '-' + date + ' ' + hours + ':' + minutes + ':' + seconds);
				timerID = setTimeout(showtime,1000);//1.秒滚动一下
				timerRunning = true;
			};
			function stopclock(){
				if(timerRunning){
					clearTimeout(timerID);
				}
				timerRunning = false;
			}
			function startclock() {
				stopclock();
				showtime();
			}
			startclock();
			//滚屏效果
			var scrollBox = $(grid_selector);
			scrollTimer = setInterval(function(){
				if (!listLength || listLength * timeBaseData < 60) {
					listLength = 60 / timeBaseData;
				}
				if (!listCount) {
					listCount = 0;
				}
				if (listCount < listLength) {
					var height = scrollBox.find('tr').eq(1).height();
					scrollBox.animate({'marginTop':-height + 'px'},600,function(){
						scrollBox.css('marginTop',0).find('tr').eq(1).appendTo(scrollBox);
					});
					listCount++;
				}else{
					listCount = 0;
					jQuery(grid_selector).jqGrid('clearGridData').jqGrid('setGridParam', {
						'datatype':'json',
						//'postData':gridPostData['tab'],
						'search':false
					},true).trigger('reloadGrid');
				}
			},timeBaseData * 1000);
			//全屏效果
			$('#fullScreenWidgetBox').off('fullscreened.ace.widget').on('fullscreened.ace.widget',function(){
				var self = $(this);
				if (self.hasClass('fullscreen')) {
					heightBaseDataTmp = heightBaseData;
					heightBaseData = 230;
				}else{
					heightBaseData = heightBaseDataTmp;
				}
				$(window).triggerHandler('resize.jqGrid');
			});
			
			var reSizeHeight = function(){
				var strs = $.getWindowSize().toString().split(",");
				var jqgrid_height = strs[0] - heightBaseData;
				if (jqgrid_height < 150) {
					jqgrid_height = 150;
				}
                $(grid_selector).jqGrid('setGridHeight',jqgrid_height);
			};
			
			//获取后台数据
				jQuery(grid_selector).jqGrid({			
				datatype: "json", //将这里改为使用JSON数据    
				url:'${ctx}/sys/notify/showNotifyList', //这是数据的请求地址  
				height: '150',
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
				colNames:['标题','类型', '紧急度','查阅状态','消息时间','发送人','接受人','内容','主键'],    
		        colModel:[    
		            {name:'title',sortable:false},    
		            {name:'type',sortable:false},    
		            {name:'urgentFlag',sortable:false},
		            {name:'readFlag',sortable:false},
		            {name:'createDate',sortable:false}, 
		            {name:'sender.name',sortable:false,hidden:true},    
		            {name:'receiver.name',sortable:false}, 
		            {name:'content',index:'content',title:false,hidden:true},
		            {name:'id',index:'id', width:30,hidden:true}        
		        ],    
				viewrecords : false,
				rowNum:'all',
				pager : pager_selector,
		        pgbuttons: false,//是否显示翻页按钮
				pginput: false,//是否显示跳转页面的输入框
				
				altRows: true,
//				rownumbers:true,//显示行顺序号
				multiselect: false,
		        multiboxonly: false,
		
				loadComplete : function(data) {
					 $.changeGridTable.changeStyle(this);
					 var listTmp = data['rows'];
					 if (!!listTmp && $.isArray(listTmp) && listTmp.length > 0) {
					 	listLength = listTmp.length;
					 }
					 
				},
				
				caption: "实时任务",

				gridComplete: function () {
					 var ids = $(grid_selector).jqGrid('getDataIDs');
					for (var i = 0; i < ids.length; i++){
						var id = ids[i];
						var rowData = $(grid_selector).getRowData(id);
						var type = getDictLabel(${fns:toJson(fns:getDictList("sys_notify_type"))}, rowData.type);
						var readFlag = getDictLabel(${fns:toJson(fns:getDictList("sys_notify_read"))}, rowData.readFlag);
						var urgentFlag = getDictLabel(${fns:toJson(fns:getDictList("sys_notify_urgent"))}, rowData.urgentFlag);
			         	$(grid_selector).jqGrid('setRowData', ids[i], { type:type,readFlag:readFlag,urgentFlag:urgentFlag});
/*			         	//修改颜色
			         	if(urgentFlag == "紧急" && readFlag == "未读"){
				         	$(grid_selector).jqGrid('setRowData', ids[i],null,{background:"red"});
			         	}			        */ 	
			         	var viewBtn = '<div class="action-buttons" style="white-space:normal">\
			         		<a data-action="view" data-id="'+rowData.id+'"href="javascript:void(0);" class="tooltip-success green" data-rel="tooltip" title="查看">\
			         		<i class="ace-icon fa fa-file-text-o bigger-130"></i></a></div>';
			         	$(grid_selector).jqGrid('setRowData', ids[i], { View:viewBtn});	         		
			        } 
					/*$(grid_selector).find('a[data-action=view]').on('click', function(event) {
						var id = $(this).attr('data-id');
						var rowData = $(grid_selector).getRowData(id);
			    		var params = {"id":id,"title":rowData.title,"sender":rowData['sender.name'],"createDate":rowData.createDate,"content":rowData.content,"readFlag":rowData.readFlag};
						viewNotify(params);
					}); */
        		}
			});

			//---------------------------------resize------------------------------
			$.changeGridTable.changeSize([grid_selector],reSizeHeight);
			$(document).one('ajaxloadstart.page', function(e) {
				if (!!timerID) {
					clearTimeout(timerID);
				}
				if (!!scrollTimer) {
					clearInterval(scrollTimer);
				}
				if (!!$.jgrid.gridUnload) {
					$.jgrid.gridUnload(grid_selector);
				}
				$('.ui-jqdialog').remove();
			});
			//------------------------------end-----------------------------------	
		});
	});
</script>