<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<title>投料点管理</title>
	<link href="${ctxStatic}/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet" type="text/css" />
	<link href="${ctxStatic}/bootstrap-treeview/css/bootstrap-treeview.css" rel="stylesheet" type="text/css" />	
	<div class="row">
		<div class="col-xs-12">
			<table id="treeTable" class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th data-locale="AreaName">投料点名称</th>
					<th data-locale="AreaCode">投料点编码</th>
					<th data-locale="AreaLocation">投料点地址</th>
					<shiro:hasPermission name="sys:area:edit"><th data-locale="Operations">操作</th></shiro:hasPermission>
				</tr>
				</thead>
				<tbody><c:forEach items="${list}" var="area">
					<tr id="${area.id}" pId="${area.parent.id}">
						<td nowrap><a href="javascript:void(0);" data-action="view">${area.name}</a></td>
						<td title="${area.code}">${area.code}</td>
						<td >${area.areaLocation}</td>
						<shiro:hasPermission name="sys:area:edit"><td nowrap>
							<div class="action-buttons">
							<a data-action="edit" href="javascript:void(0);" class="tooltip-success green" data-rel="tooltip" title="编辑"  style="border-color:#69aa46 "><i class="ace-icon fa fa-pencil bigger-130"></i></a>
							<%--<a data-action="delete"  href="javascript:void(0);" class="tooltip-error red" data-rel="tooltip" title="删除" style="border-color:#dd5a43"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>--%>
							<a data-action="add" href="javascript:void(0);" class="tooltip-info" data-rel="tooltip" title="添加下级区域"><i class="ace-icon fa fa-bars bigger-130"></i></a> 
							</div>
						</td></shiro:hasPermission>
					</tr>
				</c:forEach></tbody>
			</table>
			<div class="widget-box" style="display:none" id="editDivId"></div>
		</div><!-- /.col -->
	</div><!-- /.row -->
	 <!-- page specific plugin scripts -->
	<script type="text/javascript">	
		var scripts = [null,'${ctxStatic}/treeTable/jquery.treeTable.min.js',
							'${ctxStatic}/bootstrap-treeview/js/bootstrap-treeview.js',
		               		'${ctxStatic}/assets/js/x-editable/bootstrap-editable.js',
							'${ctxStatic}/assets/js/x-editable/ace-editable.js',
							'${ctxStatic}/assets/js/fuelux/fuelux.spinner.js', null];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
			
			jQuery(function($) {
                var qCode = sessionStorage.getItem("qCode");
                typeChange(qCode);

				$("#treeTable").treeTable({expandLevel : 4}).show();
				$("#treeTable").find('a[data-action=delete]').on('click', function(event) {
					var id = $(this).closest('tr').attr('id');
					deleteRow(id);
				});
				$("#treeTable").find('a[data-action=edit]').on('click', function(event) {
					var id = $(this).closest('tr').attr('id');
		    		var params = {"id":id};
					editRow(params,'编辑区域','Editarea');
				});
				$("#treeTable").find('a[data-action=add]').on('click', function(event) {
					var id = $(this).closest('tr').attr('id');
		    		var params = {"parent.id":id};
					editRow(params,'新增区域','Addarea');
				});
				$("#treeTable").find('a[data-action=view]').on('click', function(event) {
					var id = $(this).closest('tr').attr('id');
		    		var params = {"id":id};
					editRow(params,'编辑区域','Editarea');
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
	            $.get("${ctx}/sys/area/form",params, function(data,textStatus,object){
	            	$(".ui-dialog").remove();
	                $("#editDivId").html(object.responseText).dialog({
						modal: true,
						width:472,
						height:470,
						title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-delicious'>&nbsp;<span data-locale='"+en_title+"'>"+title+"</span></i></h4></div>",
						title_html: true,
						buttons: [ 
							{
								text: "保存",
								'class' : "btn btn-primary btn-minier",
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
						open:function(event,ui){
                            var qCode = sessionStorage.getItem("qCode");
                            typeChange(qCode);

                            $(".ui-dialog-title .widget-header").on('mouseenter',function(){
                                $(".ui-dialog-content input").blur();
                            });

                            var select2 = $('.select2');
                            var select2width = select2.parent().width();
                            $('.select2').css('width',select2width).select2({allowClear:true});

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
							}
						},
						create: function( event, ui ) {							
							$( "#selectParentarea" ).on('click', function(e) {
								e.preventDefault();
						
								var dialog = $( "#selectParentareaDiv" ).removeClass('hide').dialog({
									modal: true,
									width:300,
									height:400,
									title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-delicious' data-locale='ChooseSuperiorarea'>&nbsp;选择上级区域</i></h4></div>",
									title_html: true,
									buttons: [ 
										{
											text: "确定",
											"class" : "btn btn-primary btn-minier",
                                            "data-locale":"define",
											click: function() {
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
										$.getJSON( "${ctx}/sys/area/getAreatree",[],function(data) {
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
						}
					});  
										
					//字典页面维护表单验证
					$("#areaDivId #inputForm").bootstrapValidator({
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
										message : "区域名称不能为空"
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
	    		    'msg': '要删除该区域及所有子区域项吗？',//这个参数可选，默认值：'这是信息提示！'
	    		    'confirm_fn':function(){
	    		    	 var url = "${ctx}/sys/area/doDelete?id="+id;	  
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