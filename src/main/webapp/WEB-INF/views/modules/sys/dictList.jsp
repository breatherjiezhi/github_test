<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>字典管理</title>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class="widget-box widget-compact">
			<div class="widget-header widget-header-blue widget-header-flat">
				<h5 class="widget-title lighter" data-locale="queryCriteria">
					查询条件
				</h5>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse"> <i
						class="ace-icon fa fa-chevron-up"></i> </a>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<form:form id="searchForm" modelAttribute="dict"
						class="form-horizontal">
						<div class="form-group">
							<div class="col-xs-12 col-sm-4 no-padding">
								<div class="new-search clearfix">
									<label class=" col-xs-12 col-sm-3" for="type"  data-locale="Type">类型</label>
									<div class="col-xs-12 col-sm-9 no-padding ">
										<form:select  path="type" class="chosen-select form-control newsearchInput"
													  data-placeholder="点击选择...">
											<form:option value="" label="" />
											<form:options items="${typeList}" htmlEscape="false" />
										</form:select>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-4 no-padding">
								<div class="new-search clearfix">
									<label class="col-xs-12 col-sm-3" for="description" data-locale="Description">描述</label>
									<div class="col-xs-12 col-sm-9 no-padding">
										<input type="text" id="description" class="ace width-100 newsearchInput" />
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-4">
								<div style="float:right">
									<button class="btn btn-info btn-sm" type="button" id="query" style="color: orange !important;border-color: orange">
										<i class="fa fa-search" aria-hidden="true" style="margin-right: 5px"></i>
										<span data-locale='query'>查询</span>
									</button>
									<button class="btn btn-info btn-sm" type="reset" id="reset" style="color: #4cc74c !important;border-color: #4cc74c;margin-left: 5px">
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
		<table id="grid-table"></table>
		<div id="grid-pager"></div>
		<div class="widget-box" style="display:none" id="editDivId">
			
		</div>
	</div>
</div>
<script type="text/javascript">
			
	var scripts = [ null,'${ctxStatic}/assets/js/fuelux/fuelux.spinner.js', null ];
	$('.page-content-area').ace_ajax('loadScripts',scripts,function() {				
		jQuery(function($) {
            var qCode = sessionStorage.getItem("qCode");
            typeChange(qCode);
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
		        url:'${ctx}/sys/dict/searchPage', //这是数据的请求地址  
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
				colNames:[
				    '<span data-locale="KeyValue">键值</span>',
					'<span data-locale="Label">标签</span>',
					'<span data-locale="Type">类型</span>',
					'<span data-locale="Description">描述</span>',
					'<span data-locale="SequenceNumber">排序</span>',
					'<span data-locale="Operations">操作</span>'
				],
		        colModel:[    
		            {name:'value',index:'value', editable: true},    
		            {name:'label',index:'label', editable: true},    
		            {name:'type',index:'type', editable: true},
		            {name:'description',index:'description', editable: true},    
		            {name:'sort',index:'sort', editable: true},    
		            {name:'id',index:'id', width:60,hidden:true}       
		        ],    
		
				viewrecords : true,
				rowNum:20,
				rowList:[10,20,30],
				pager : pager_selector,
				altRows: true,
				toppager: true,
				
				multiselect: true,
				//multikey: "ctrlKey",
		        multiboxonly: true,
		
				loadComplete : function() {
					 $.changeGridTable.changeStyle(this);
					 $(grid_selector+"_toppager_center").remove();
					 $(grid_selector+"_toppager_right").remove();
					 $(pager_selector+"_left table").remove();
				},
		
				editurl: "/dummy.html",//nothing is saved
				caption: "<span data-locale='DictionanyList'>字典列表</span>",

				gridComplete: function () {
                    var qCode = sessionStorage.getItem("qCode");
                    typeChange(qCode);
        		}
		
			});
			
			//$("#t_grid-table").append("<input type='button' value='Click Me' style='height:20px;font-size:-3'/>"); 
			//$("input","#t_grid-table").click(function(){ alert("Hi! I'm added button at this toolbar"); });
			$.changeGridTable.changeSize([grid_selector,grid_selector+" ~ .widget-box"],reSizeHeight);
			
			//search list by condition
			$("#query").click(function(){ 
		        var type = $("#type").val(); 
		        var description =  $("#description").val(); 
		        $("#grid-table").jqGrid('setGridParam',{ 
		            url:"${ctx}/sys/dict/searchPage", 
		            mtype:"post",
		            postData:{'description':description,'type':type}, //发送数据 
		            page:1 
		        }).trigger("reloadGrid"); //重新载入 
		    }); 
			
			$("#reset").click(function(){ 
				$('.chosen-select').val('').trigger('chosen:updated');
		        $(grid_selector).jqGrid('setGridParam',{ 
		            url:"${ctx}/sys/dict/searchPage", 
		            mtype:"post",
		            postData:{'description':'','type':''}, 
		            page:1 
		        }).trigger("reloadGrid"); //重新载入 
		    }); 
			  
			//navButtons
			jQuery(grid_selector).jqGrid('navGrid',pager_selector,
				{ 	//navbar options
					edit: true,
					editicon : 'ace-icon fa fa-pencil blue',
					editfunc : openDialogEdit,
					edittext:"<span data-locale='edit'>编辑</span>",
					edittitle:'',
					add: true,
					addicon : 'ace-icon fa fa-plus',
					addfunc : openDialogAdd,
					addtext:"<span data-locale='add'>新增</span>",
					addtitle:'',
					del: true,
					delicon : 'ace-icon fa fa-trash-o',
					delfunc : doDelete,
					deltext:"<span data-locale='delete'>删除</span>",
					deltitle:'',
					search: true,
					searchicon : 'ace-icon fa fa-search',
					searchtext:"<span data-locale='query'>查询</span>",
					searchtitle:'',
					refresh: true,
					refreshicon : 'ace-icon fa fa-refresh',
					refreshtext:"<span data-locale='refresh'>刷新</span>",
					refreshtitle:'',
					view: true,
					viewicon : 'ace-icon fa fa-search-plus',
					viewtext:"<span data-locale='View'>查看</span>",
					viewtitle:'',
					cloneToTop:true,
				},
				{}, // use default settings for edit
				{}, // use default settings for add
				{},  // delete instead that del:false we need this
				{multipleSearch : true}, // enable the advanced searching
				{closeOnEscape:true} /* allow the view dialog to be closed when user press ESC key*/
			);
			
			//$(grid_selector).jqGrid('navGrid',pager_selector,{ cloneToTop:true });
			
			//$("#t_grid-table").append( "<input type='button' value='Click Me' style='height:20px;font-size:-3'/>");
			
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
			function doDelete(){
				var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
				
			 	if(selectedIds.length>1){	
			 		var arrayObj = "";
					for (var i=0;i<selectedIds.length ;i++ ){
						var rowData = $("#grid-table").getRowData(selectedIds[i]);	
						var id = rowData.id;
						arrayObj = (arrayObj + id) + (((i + 1)== selectedIds.length) ? '':',');  
					} 	 
			 		var params = {"ids":arrayObj};
					$.post("${ctx}/sys/dict/batchDelete", params,function(result){
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
			 	}else{
			 		var rowData = $("#grid-table").getRowData(selectedIds[0]);
			 		var params = {"id":rowData.id};
					$.post("${ctx}/sys/dict/delete", params,function(result){
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
			 
			}	
	
			function _edit(id){		
				var params = {"id":id};
	            $.get("${ctx}/sys/dict/form",params, function(data,textStatus,object){
	            	$(".ui-dialog").remove();
	                $("#editDivId").html(object.responseText).dialog({
						modal: true,
						width: 600,
						height:"auto",
						title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-book'></i>&nbsp;<span data-locale='DictionaryMaintenance'>字典维护</span></h4></div>",
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
						open: function(){
                            var qCode = sessionStorage.getItem("qCode");
                            typeChange(qCode);
						},
						create: function( event, ui ) {
                            $(".ui-dialog-title .widget-header").on('mouseenter',function(){
                                $(".ui-dialog-content input").blur();
                            })
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
							value : {
								validators : {
									notEmpty : {
										message : "键值不能为空"
									}
								}
							},
							label : {
								validators : {
									notEmpty : {
										message : "标签名称不能为空"
									}
								}
							},
							type : {
								validators : {
									notEmpty : {
										message : "类型不能为空"
									}
								}
							},
							description : {
								validators : {
									notEmpty : {
										message : "描述不能为空"
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
			});
		});
	});
</script>

