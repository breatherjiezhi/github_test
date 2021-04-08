<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<title>菜单管理</title>
	<link href="${ctxStatic}/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet" type="text/css" />
	<link href="${ctxStatic}/bootstrap-treeview/css/bootstrap-treeview.css" rel="stylesheet" type="text/css" />	
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/menu/">菜单列表</a></li>
		<shiro:hasPermission name="sys:menu:edit"><li><a href="${ctx}/sys/menu/form">菜单添加</a></li></shiro:hasPermission>
	</ul> --%>
	<%-- <sys:message content="${message}"/> --%>
	<div class="row">
		<div class="col-xs-12">
			<table id="treeTable" class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th data-locale="MenuName">名称</th>
					<th data-locale="Link">链接</th>
					<th data-locale="EnglishName">英文名称</th>
					<th data-locale="SequenceNumber" style="text-align:center;">排序</th>
					<th data-locale="Show/Hide">是否可见</th>
					<th data-locale="Permissionsidentification">权限标识</th>
					<shiro:hasPermission name="sys:menu:edit"><th data-locale="Operations">操作</th></shiro:hasPermission>
				</tr>
				</thead>
				<tbody><c:forEach items="${list}" var="menu">
					<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
						<td nowrap><i class="menu-icon fa ${not empty menu.icon?menu.icon:' hide'}"></i><a href="javascript:void(0);" data-action="view">${menu.name}</a></td>
						<td title="${menu.href}">${fns:abbr(menu.href,50)}</td>
						<td title="${menu.signs}">${fns:abbr(menu.signs,50)}</td>
						<td style="text-align:center;">
							<shiro:hasPermission name="sys:menu:edit">
							<span class="editable" id="sort-${menu.id}">${menu.sort}</span>
							</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">
								${menu.sort}
							</shiro:lacksPermission>
						</td>
						<td>${menu.isShow eq '1'?'显示':'隐藏'}</td>
						<td title="${menu.permission}">${fns:abbr(menu.permission,50)}</td>
						<shiro:hasPermission name="sys:menu:edit"><td nowrap>
							<div class="action-buttons">
							<a data-action="edit" href="javascript:void(0);" class="tooltip-success green" data-rel="tooltip" title="编辑" style="border-color:#69aa46 "><i class="ace-icon fa fa-pencil bigger-130"></i></a>
							<a data-action="delete" href="javascript:void(0);" class="tooltip-error red" data-rel="tooltip" title="删除" style="border-color:#dd5a43"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>
							<a data-action="add" href="javascript:void(0);" class="tooltip-info" data-rel="tooltip" title="添加下级菜单"><i class="ace-icon fa fa-bars bigger-130"></i></a> 
							</div>
						</td></shiro:hasPermission>
					</tr>
				</c:forEach></tbody>
			</table>
			<div class="widget-box" style="display:none" id="editDivId">
			</di>
		</div><!-- /.col -->
	</div><!-- /.col -->
	</div><!-- /.row -->
	 <!-- page specific plugin scripts -->
	<script type="text/javascript">	
		var scripts = [null, '${ctxStatic}/treeTable/jquery.treeTable.min.js','${ctxStatic}/bootstrap-treeview/js/bootstrap-treeview.js',
		               '${ctxStatic}/assets/js/x-editable/bootstrap-editable.js','${ctxStatic}/assets/js/x-editable/ace-editable.js','${ctxStatic}/assets/js/fuelux/fuelux.spinner.js',null];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
			
			jQuery(function($) {
                var qCode = sessionStorage.getItem("qCode");
                typeChange(qCode);

				$("#treeTable").treeTable({expandLevel : 3}).show();
				$("#treeTable").find('a[data-action=delete]').on('click', function(event) {
					var id = $(this).closest('tr').attr('id');
					deleteRow(id);
				});
				$("#treeTable").find('a[data-action=edit]').on('click', function(event) {
					var id = $(this).closest('tr').attr('id');
		    		var params = {"id":id};
					editRow(params,'编辑菜单','Editmenu');
				});
				$("#treeTable").find('a[data-action=add]').on('click', function(event) {
					var id = $(this).closest('tr').attr('id');
		    		var params = {"parent.id":id};
					editRow(params,'新增菜单','Addmenu');
				});
				$("#treeTable").find('a[data-action=view]').on('click', function(event) {
					var id = $(this).closest('tr').attr('id');
		    		var params = {"id":id};
					editRow(params,'编辑菜单','Editmenu');
				});
				
				$.fn.editable.defaults.mode = 'popup';
				$.fn.editableform.loading = "<div class='editableform-loading'><i class='ace-icon fa fa-spinner fa-spin fa-2x light-blue'></i></div>";
			    $.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="ace-icon fa fa-check"></i></button>'+
			                                '<button type="button" class="btn editable-cancel"><i class="ace-icon fa fa-times"></i></button>';
			                                
				$("span[id^=sort-]").each(function(index,sortDom){
					var id = $(sortDom).attr('id').	slice(5);				
					$(sortDom).editable({
				        type: 'spinner',
						name : 'sort',
						pk:id,
						url :"${ctx}/sys/menu/updateSort",
						spinner : {
							min : 0,
							max : 1000,
							step: 10,
							on_sides: true
						},
						success:function(response,newValue){
							if(response.messageStatus=="1"){
							  	$.msg_show.Init({
							        'msg':response.message,
							        'type':'success'
							    });
								
							}else if(response.messageStatus=="0"){
							  	$.msg_show.Init({
							        'msg':response.message,
							        'type':'error'
							    });	
							}		
						}
					});
				});
				
				
				$(document).one('ajaxloadstart.page', function(e) {
					$('.ui-dialog').remove();
				});
				
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
	    	
	    	function editRow(params,title,en_title){
	            $.get("${ctx}/sys/menu/form",params, function(data,textStatus,object){
	            	$(".ui-dialog").remove();
	                $("#editDivId").html(object.responseText).dialog({
						modal: true,
						width:680,
						height:740,
						title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-bars'></i>&nbsp;<span data-locale='"+en_title+"'>"+title+"</span></h4></div>",
						title_html: true,
						buttons: [ 
							{
								text: "保存",
								class : "btn btn-primary btn-minier",
                                "data-locale":"doSave",
								click: function() {
									$("#inputForm").bootstrapValidator('validate');
								} 
							},
							{							
								text: "取消",
								"class" : "btn  btn-minier",
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
 							$('#menuDivId #sort').ace_spinner({value:0,min:0,max:10000,step:10, on_sides: true, icon_up:'ace-icon fa fa-plus bigger-110', icon_down:'ace-icon fa fa-minus bigger-110', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});
							$( "#selectParentMenu" ).on('click', function(e) {
								e.preventDefault();
						
								var dialog = $( "#selectParentMenuDiv" ).removeClass('hide').dialog({
									modal: true,
									width:300,
									height:400,
									title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-bars'></i>&nbsp;<span data-locale='ChooseSuperiprmenu'>选择上级菜单</span></h4></div>",
									title_html: true,
									buttons: [ 
										{
											text: "确定",
											"class" : "btn btn-primary btn-minier",
                                            "data-locale":"define",
											click: function() {
												//$('#treeview').remove();
												$("#inputForm input#parent\\.name").val($('#treeview').attr('data-text'));
												$("#inputForm input#parent\\.id").val($('#treeview').attr('data-id'));
												$( this ).dialog( "close" ); 
											} 
										},
										{
											text: "取消",
											"class" : "btn btn-minier",
                                            "data-locale":"cancel",
											click: function() {
												//$('#treeview').remove();
												$( this ).dialog( "close" ); 
											} 
										}
									],
									open: function( event, ui ) {
                                        var qCode = sessionStorage.getItem("qCode");
                                        typeChange(qCode);
										$.getJSON( "${ctx}/sys/menu/getMenutree",[],function(data) {
												$('#treeview').treeview({
													data: data,
													levels: 2,
													showBorder:true,
													emptyIcon:'fa fa-file-o',
													collapseIcon:'fa fa-folder-open-o',
													expandIcon:'fa fa-folder-o',
													onNodeSelected: function(event, node) {
											           $('#treeview').attr('data-id',node.id);
											           $('#treeview').attr('data-text',node.text);
											        },
												});
										});										
									}
								});
							});
                            $(".ui-dialog-title .widget-header").on('mouseenter',function(){
                                $(".ui-dialog-content input").blur();
                            })
						}
					});  
										
					//字典页面维护表单验证
					$("#menuDivId #inputForm").bootstrapValidator({
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
										message : "菜单名称不能为空"
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
									$(window).trigger('reload.ace_ajax');
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
	    	
	    	function deleteRow(id){
	    		//信息确认插件
	    		$.msg_confirm.Init({
	    		    'msg': '要删除该菜单及所有子菜单项吗？',//这个参数可选，默认值：'这是信息提示！'
	    		    'confirm_fn':function(){
	    		    	 var url = "${ctx}/sys/menu/doDelete?id="+id;	  
	   		    		 $.get(url, function(result) {
	   		    			if(result.messageStatus=="1"){
							  	$.msg_show.Init({
							        'msg':result.message,
							        'type':'success'
							    });		
							  	var depth = $('tr#'+id).attr('depth');
			            		var result =new Array();
			            		result.push($('tr#'+id));
			            		$('tr#'+id+' ~ tr').each(function(index,dom){
			            		    var trdepth = $(dom).attr('depth');
			            		    if(trdepth <= depth){
			            		      return false;
			            		    }else{
			            		      result.push($(dom));
			            		    }
			            		});
			            		$.each(result,function(n,value){
			            			value.remove();
			            		});
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
		});
	</script>
</body>
</html>