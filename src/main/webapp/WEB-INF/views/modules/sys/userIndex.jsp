<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>�û�����</title>
<style>
	.chosen-container, [class*="chosen-container"] {
		vertical-align: middle;
		border: 1px solid #d5d5d5;
	}
</style>
<div class="row">
	<div class="col-xs-12">
		<div class="row">
			<div class="col-sm-3 no-padding-right">
				<div class="widget-box widget-compact">
					<div class="widget-header widget-header-blue widget-header-flat">
						<h5 class="widget-title lighter" data-locale="organization">��֯����</h5>
					</div>
					<div class="widget-body">
						<div class="widget-main no-padding">
							<div id="treeview" class="" data-id="" data-text=""></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-9 user-pane">
				<div class="widget-box widget-compact">
					<div class="widget-header widget-header-blue widget-header-flat">
						<h5 class="widget-title lighter" data-locale="queryCriteria">��ѯ����</h5>
						<div class="widget-toolbar">
							<a href="#" data-action="collapse"> <i
								class="ace-icon fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="widget-body">
						<div class="widget-main no-padding">
							<form:form id="searchForm" modelAttribute="user"
								class="form-horizontal">
								<div class="form-group">
                                    <div class="col-xs-12 col-sm-4 no-padding">
										<div class="new-search clearfix">
											<label class=" col-xs-12 col-sm-4" for="name" data-locale="name">����</label>
											<div class="col-xs-12 col-sm-8 no-padding">
												<input type="text" id="name" class="ace width-100 newsearchInput"/>
											</div>
										</div>
                                    </div>
                                    <div class="col-xs-12 col-sm-4 no-padding">
										<div class="new-search clearfix">
											<label class=" col-xs-12 col-sm-4" for="loginName" data-locale="loginName">��¼��</label>
											<div class="col-xs-12 col-sm-8 no-padding">
												<input type="text" id="loginName" class="ace width-100 newsearchInput"/>
											</div>
										</div>
                                    </div>
									<div class="col-xs-12 col-sm-4">
										<div style="float:right">
											<button class="btn btn-info btn-sm" type="button" id="query" style="color: orange !important;border-color: orange">
												<i class="fa fa-search" aria-hidden="true" style="margin-right: 5px" ></i><span data-locale="query">��ѯ</span>
											</button>
											<button class="btn btn-info btn-sm" type="reset" id="reset" style="color: #4cc74c !important;border-color: #4cc74c;margin-left: 5px">
												<i class="fa fa-refresh" aria-hidden="true" style="margin-right: 5px" ></i><span data-locale="reset">����</span>
											</button>
										</div>
									</div>
								</div>
							</form:form>
						</div>
					</div>
				</div>
				<div id="fileDivId"></div>

				<table id="grid-table"></table>
				<div id="grid-pager"></div>
				<div class="widget-box" style="display:none" id="editDivId"></div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var scripts = [null,'${ctxStatic}/bootstrap-treeview/js/bootstrap-treeview.js',null];
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		jQuery(function($) {
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";
			var toolbarTop = grid_selector + '_toppager';
			var officeId = '';
			var officeName ='';
            var qCode='${qCode}'
			var reSizeHeight = function(first){
				var strs = $.getWindowSize().toString().split(",");
				var tree_height = strs[0]-146;
				var jqgrid_height = strs[0]-285;
				if(first){
					$('#treeview').closest('.widget-main').ace_scroll({size:tree_height});
				}else{
					$('#treeview').closest('.widget-main').ace_scroll('update',{size:tree_height});
				}
				$(grid_selector).jqGrid('setGridHeight',jqgrid_height);
			};
            var qCode = sessionStorage.getItem("qCode");
			typeChange(qCode);

			var jqgridData = function(param){
				$("#grid-table").jqGrid('setGridParam',{ 
					url:"${ctx}/sys/user/searchPage", 
					mtype:"post",
					postData:param, //�������� 
					page:1 
				}).trigger("reloadGrid"); //�������� 
			}
			
			$.getJSON( "${ctx}/sys/office/getOfficetree",{treetype:'1'},function(data) {
				officeId =data[0].id;
				officeName = data[0].text;
				$('#treeview').treeview({
					data: data,
					levels: 3,
					showBorder:true,
					selectedBackColor: "skyblue",
					emptyIcon:'fa fa-file-o red',
					collapseIcon:'fa fa-folder-open-o red',
					expandIcon:'fa fa-folder-o red',
					onNodeSelected: function(event, node) {
						$('#treeview').attr('data-id',node.id);
						$('#treeview').attr('data-text',node.text);
						officeId = node.id;
						officeName = node.text;
						var param = {'office.id':node.id};
						jqgridData(param);
					},
					onNodeCollapsed: function(event, node) {
						reSizeHeight();
					},
					onNodeExpanded: function(event, node) {
						reSizeHeight();
					},
				});
			});	

			jQuery(grid_selector).jqGrid({
				datatype: "json", //�������Ϊʹ��JSON����
				url:'${ctx}/sys/user/searchPage', //�������ݵ������ַ
				height: 'auto',
				autowidth:true,

				jsonReader: {
					root: "rows",   // json�д���ʵ��ģ�����ݵ����  
					page: "page",   // json�д���ǰҳ�������  
					total: "total", // json�д���ҳ������������  
					records: "records", // json�д������������������� 
					repeatitems: false
				},  
				prmNames : {  
					page:"pageNo",    // ��ʾ����ҳ��Ĳ�������  
					rows:"rows",    // ��ʾ���������Ĳ�������  
					sort: "sidx", // ��ʾ��������������Ĳ�������  
					order: "sord", // ��ʾ���õ�����ʽ�Ĳ�������  
					search:"_search", // ��ʾ�Ƿ�����������Ĳ�������  
					nd:"nd", // ��ʾ�Ѿ���������Ĵ����Ĳ�������  
					id:"id", // ��ʾ���ڱ༭����ģ���з�������ʱ��ʹ�õ�id������  
					oper:"oper",    // operation�������ƣ�����ʱ��û�õ���  
					editoper:"edit", // ����editģʽ���ύ����ʱ������������  
					addoper:"add", // ����addģʽ���ύ����ʱ������������  
					deloper:"del", // ����deleteģʽ���ύ����ʱ������������  
					subgridid:"id", // ��������������ݵ��ӱ�ʱ�����ݵ���������  
					npage: null,
					totalrows:"totalrows" // ��ʾ���Server�õ��ܹ����������ݵĲ������ƣ��μ�jqGridѡ���е�rowTotal
				},
				colNames:[
				    '<span data-locale="name">����</span>',
					'<span data-locale="loginName">��¼��</span>',
					'<span data-locale="No">����</span>',
                    '<span data-locale="UserIntegral">ʣ��Ͳʹ���</span>',
					'<span data-locale="officeName">��������</span>',
					'<span data-locale="mobile">�ֻ�����</span>',
					'<span data-locale="userType">�û�����</span>',
					'<span data-locale="roleNames">�û���ɫ</span>',
					'<span data-locale="loginFlag">�Ƿ������¼</span>',
					'<span data-locale="view">����</span>',
					''
				],
				colModel:[
					{name:'name',index:'name', editable: true,width:45},
					{name:'loginName',index:'login_name', editable: true,width:45},
                    {name:'no',index:'no', editable: true,width:45},
                    {name:'userIntegral',index:'USER_INTEGRAL', editable: true,width:60},
					{name:'office.name',index:'office_id', editable: true,width:60},
					{name:'mobile',index:'mobile', editable: true,width:60},
					{name:'userType',index:'userType', editable: true,width:60,hidden:true},
                    {name:'role.name',index:'role.name', editable: true,width:80},
					{name:'loginFlag',index:'loginFlag', editable: true,width:55},
					{name:'View',index:'View',width:30},
					{name:'id',index:'id',hidden:true}
				],
		
				viewrecords : true,
				rowNum:15,
				rowList:[15,20,30],
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
				caption: "<span data-locale=\"userlist\">�û��б�</span>",
				//toolbar: [true,"top"],
				gridComplete: function () {
                    var qCode = sessionStorage.getItem("qCode");
                    typeChange(qCode);
					var ids = $(grid_selector).jqGrid('getDataIDs');
			         for (var i = 0; i < ids.length; i++) {
						var id = ids[i];			
			         	var rowData = $("#grid-table").getRowData(id);	
			         	
			         	var type = getDictLabel(${fns:toJson(fns:getDictList("sys_user_type"))}, rowData.userType);
			         	var flag = getDictLabel(${fns:toJson(fns:getDictList("yes_no"))}, rowData.loginFlag);
			         	var viewBtn = '<div class="action-buttons" style="white-space:normal">\
			         		<a data-action="resetPwd" data-id="'+rowData.id+'"href="javascript:void(0);" class="tooltip-success green" data-rel="tooltip" title="��������"  style="border-color:#69aa46">\
			         		<i class="ace-icon fa fa-key bigger-130"></i></a></div>';
			         	$(grid_selector).jqGrid('setRowData', ids[i], { userType:type,loginFlag:flag,View:viewBtn});		
			         }
			         $(grid_selector).find('a[data-action=resetPwd]').on('click', function(event) {
							var id = $(this).attr('data-id');
							var rowData = $(grid_selector).getRowData(id);
				    		var params = {"id":id,"loginName":rowData.loginName};
							resetPwd(params);
						});
        		}
		
			});				
			
			//$("#t_grid-table").append("<input type='button' value='Click Me' style='height:20px;font-size:-3'/>"); 
			//$("input","#t_grid-table").click(function(){ alert("Hi! I'm added button at this toolbar"); });
			reSizeHeight(true);		
			$.changeGridTable.changeSize([grid_selector,grid_selector+" ~ .widget-box"],reSizeHeight);
			//search list by condition
			$("#query").click(function(){ 
		        var param ={'name':$("#name").val(),'loginName':$("#loginName").val()};
		        jqgridData(param);			        
		    });

            $("#reset").click(function(){
                var param ={'name':'','loginName':''};
                jqgridData(param);
            });

            //navButtons
			jQuery(grid_selector).jqGrid('navGrid',pager_selector,
				{ 	//navbar options
					edit: true,
					editicon : 'ace-icon fa fa-pencil ',
					editfunc : openDialogEdit,
					edittext:"<span  data-locale=\"edit\">�༭</span>",
					edittitle:'',
					add: true,
					addicon : 'ace-icon fa fa-plus ',
					addfunc : openDialogAdd,
					addtext:"<span  data-locale=\"add\">����</span>",
					addtitle:'',
					del: true,
					delicon : 'ace-icon fa fa-trash-o ',
					delfunc : doDelete,
					deltext:"<span  data-locale=\"delete\">ɾ��</span>",
					deltitle:'',
					search: true,
					searchicon : 'ace-icon fa fa-search ',
					searchtext:"��ѯ",
					searchtitle:'',
					refresh: true,
					refreshicon : 'ace-icon fa fa-refresh ',
					refreshtext:"<span  data-locale=\"refresh\">ˢ��</span>",
					refreshtitle:'',
					view: false,
					viewicon : 'ace-icon fa fa-search ',
					viewtext:"�鿴",
					viewtitle:'',
					cloneToTop:true,
				},
				{}, // use default settings for edit
				{}, // use default settings for add
				{},  // delete instead that del:false we need this
				{multipleSearch : true}, // enable the advanced searching
				{closeOnEscape:true} /* allow the view dialog to be closed when user press ESC key*/
			).jqGrid("navButtonAdd",toolbarTop,{
                caption:"<span data-locale='RechargeTemplateDownload'>��ֵģ������</span>",
                buttonicon:"ace-icon fa fa-download",
                title:"��ֵģ������",
                onClickButton: download,
                position:"first" ,
                id:"download"
            }).jqGrid("navButtonAdd",toolbarTop,{
                caption:"<span data-locale='BatchRechargeImport'>������ֵ����</span>",
                buttonicon:"ace-icon fa fa-upload",
                title:"������ֵ����",
                onClickButton: upload,
                position:"first" ,
                id:"upload"
            });



            function download() {
                window.location.href="${ctx}/sys/user/downloadexcel";
            }

            function upload() {
                $.post("${ctx}/holiday/uploadFileForm", function (data, textStatus, object) {
                    $(".ui-dialog").remove();
                    $("#fileDivId").html(object.responseText).dialog({
                        modal: true,
                        width: 472,
                        height: 350,
                        title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-users' id='flags'>&nbsp;<span data-locale='BatchRechargeImport'>������ֵ����</span></h4></div>",
                        title_html: true,
                        buttons: [{
                            text: "����",
                            "class": "btn btn-primary btn-minier",
                            "data-locale":"doSave",
                            click: function () {
                                $("#serFileForm1").bootstrapValidator('validate');
                            }
                        },
                            {
                                text: "ȡ��",
                                "class": "btn btn-minier",
                                "data-locale":"cancel",
                                click: function () {
                                    $(this).dialog("close");
                                }
                            }
                        ],
                        open: function (event, ui) {
                            var qCode = sessionStorage.getItem("qCode");
                            typeChange(qCode);

                            $(".ui-dialog-title .widget-header").on('mouseenter',function(){
                                $(".ui-dialog-content input").blur();
                            })

                            //�ϴ�ģ̬��ʼ��
                            var $form = $('#serFileForm1');
                            //you can have multiple files, or a file input with "multiple" attribute
                            var file_input = $form.find('input[type=file]');
                            var upload_in_progress = false;

                            file_input.ace_file_input({
                                style : 'well',
                                btn_choose : '����˴����ļ��������',
                                btn_change: null,
                                droppable: true,
                                thumbnail: 'large',

                                maxSize: 51200000,//bytes
                                allowExt: ["xlsx","xls"],
                                allowMime: ["application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","application/vnd.ms-excel"],

                                before_remove: function() {
                                    if(upload_in_progress)
                                        return false;//if we are in the middle of uploading a file, don't allow resetting file input
                                    return true;
                                },

                                preview_error: function(filename , code) {
                                    //code = 1 means file load error
                                    //code = 2 image load error (possibly file is not an image)
                                    //code = 3 preview failed
                                }
                            })
                            file_input.on('file.error.ace', function(ev, info) {
                                if(info.error_count['ext'] || info.error_count['mime']){
                                    $.msg_show.Init({
                                        'msg':'��Ч���ļ����ͣ���ѡ��һ��Excel�ļ�!',
                                        'type':'error'
                                    });
                                }
                                if(info.error_count['size']){
                                    $.msg_show.Init({
                                        'msg':'�ļ���С���Ϊ500KB!',
                                        'type':'error'
                                    });
                                }

                            });
                        }
                    });

                    //�ֵ�ҳ��ά������֤
                    $("#serFileForm1").bootstrapValidator({
                        message: "��¼��һ����Чֵ",
                        excluded: ':disabled',
                        feedbackIcons: {
                            valid: "glyphicon glyphicon-ok",
                            invalid: "glyphicon glyphicon-remove",
                            validating: "glyphicon glyphicon-refresh"
                        }
                    }).on("success.form.bv", function (e) {
                        // Prevent form submission
                        e.preventDefault();

                        // Get the form instance
                        var $form = $(e.target);
                        // Get the BootstrapValidator instance
                        var bv = $form.data("bootstrapValidator");
                        //�ϴ��ļ�
                        // Use Ajax to submit form data
                        $("#serFileForm1").ajaxSubmit({
                            url: '${ctx}/sys/user/importRecharge',
                            type: 'post',
                            success: function (result) {
                                if (result.messageStatus == "1") {
                                    $.msg_show.Init({
                                        'msg': result.message,
                                        'type': 'success'
                                    });
                                    $("#fileDivId").dialog("close");
                                    $(grid_selector).trigger("reloadGrid");
                                }
                                if (result.messageStatus == "0") {
                                    $.msg_show.Init({
                                        'msg':result.message,
                                        'type': 'error'
                                    });
                                    $("#fileDivId").dialog("close");
                                    $(grid_selector).trigger("reloadGrid");
                                }
                            }
                        })
                    });
                })
            }
			
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
				    //ʧ��
				    $.msg_show.Init({
				        'msg':'����ѡ��һ����¼�޸�',
				        'type':'error'
				    });				
 
			 	}else{
			 		_edit(selectedIds[0]);
			 	}
			 
			}
			function doDelete(){
				//��Ϣȷ�ϲ��
	    		$.msg_confirm.Init({
	    		    'msg': 'Ҫɾ����ǰ��ѡ���û���',//���������ѡ��Ĭ��ֵ��'������Ϣ��ʾ��'
	    		    'confirm_fn':function(){
	    		    	var selectedIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
	    				
	    			 	if(selectedIds.length>1){	
	    			 		var arrayObj = "";
	    					for (var i=0;i<selectedIds.length ;i++ ){
	    						var rowData = $("#grid-table").getRowData(selectedIds[i]);	
	    						var id = rowData.id;
	    						arrayObj = (arrayObj + id) + (((i + 1)== selectedIds.length) ? '':',');  
	    					} 	 
	    			 		var params = {"ids":arrayObj};
	    					$.post("${ctx}/sys/user/batchDelete", params,function(result){
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
	    					$.post("${ctx}/sys/user/delete", params,function(result){
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
	    		    },//���������ѡ��Ĭ��ֵ��function(){} �յķ�����
	    		    'cancel_fn':function(){
	    		        //���ȡ����Ҫִ�еĲ���
	    		    }//���������ѡ��Ĭ��ֵ��function(){} �յķ�����
	    		});  	
				
			 
			}	
	
			function _edit(id){
				var params = {"id":id,"pageOfficeId":officeId,"pageOfficeName":officeName};
				$.post("${ctx}/sys/user/form",params, function(data,textStatus,object){
					$(".ui-dialog").remove();
					$("#editDivId").html(object.responseText).dialog({
						modal: true,
						width:800,
						height:600,
						title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-users'></i>&nbsp;<span data-locale='AddUser'>�����û�</span></h4></div>",
						title_html: true,
						buttons: [ 
							{
								text:"����",
								"class" : "btn btn-primary btn-minier",
                                "data-locale":"doSave",
								click: function() {
									$("#inputForm").bootstrapValidator('validate');
								} 
							},
							{							
								text: "ȡ��",
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
							var select2 = $('.select2');
							var select2width = select2.parent().width(); 
							$('.select2').css('width',select2width).select2({allowClear:true});
							//ѡ��� 
							if(!ace.vars['touch']) {
								$('.chosen-select').chosen({allow_single_deselect:true}); 
								$(window).off('resize.chosen').on('resize.chosen', function() {
									$('.chosen-select,.select2').each(function() {
										 var $this = $(this);
										 $this.next().css({'width': $this.parent().width()});
									});
								}).trigger('resize.chosen');
							}
							$("#userDivId #loginName").off("focus.userloginName").on("focus.userloginName",function(event){
								 var bootstrapValidator = $('#inputForm').data('bootstrapValidator');
								bootstrapValidator.enableFieldValidators('loginName', true);
							});
						},
						create: function( event, ui ) {
                            $(".ui-dialog-title .widget-header").on('mouseenter',function(){
                                $(".ui-dialog-content input").blur();
                            })

                            $("#selectOfficeMenu").on('click', function(e) {
								e.preventDefault();
						
								var dialog = $( "#selectOfficeTreeDiv" ).removeClass('hide').dialog({
									modal: true,
									width:300,
									height:400,
									title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-university'></i>&nbsp;<span data-locale='Selectdepartment'>ѡ����</span></h4></div>",
									title_html: true,
									buttons: [ 
										{
											text: "ȷ��",
											"class" : "btn btn-primary btn-minier",
                                            "data-locale":"define",
											click: function() {
												$("#inputForm input#office\\.name").val($('#popuptreeview').attr('data-text'));
												$("#inputForm input#office\\.id").val($('#popuptreeview').attr('data-id'));
												$( this ).dialog( "close" ); 
											} 
										},
										{
											text: "ȡ��",
											"class" : "btn btn-minier",
                                            "data-locale":"cancel",
											click: function() {
												$( this ).dialog( "close" ); 
											} 
										}
									],
									open: function( event, ui ) {
                                        var qCode = sessionStorage.getItem("qCode");
                                        typeChange(qCode);
										$.getJSON( "${ctx}/sys/office/getOfficetree",{"treetype":1},function(data) {
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
					//�ֵ�ҳ��ά������֤
					$("#inputForm").bootstrapValidator({
						message : "��¼��һ����Чֵ",
						feedbackIcons : {
							valid : "glyphicon glyphicon-ok",
							invalid : "glyphicon glyphicon-remove",
							validating : "glyphicon glyphicon-refresh"
						},
						fields : {
							name : {
								validators : {
									notEmpty : {
										message : "��������Ϊ��"
									}
								}
							},
							loginName : {
								enabled:false,
								validators : {
									remote : {
										message : "��¼�������ظ�",
										url:'${ctx}/sys/user/checkLoginNameAjax',
										data: function(validator){
											return {id : validator.getFieldElements('id').val()	};
										}
									}
								}
							},
							no : {
								validators : {
									notEmpty : {
										message : "���Ų���Ϊ��"
									}
								}
							},
							email: {
				                validators: {
				                    emailAddress: {
				                        message: '�����ַ����ȷ'
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
			
			function resetPwd(params){
				//��Ϣȷ�ϲ��
				$.msg_confirm.Init({
					'msg': 'ȷ��Ҫ���õ�ǰ�û���������',//���������ѡ��Ĭ��ֵ��'������Ϣ��ʾ��'
					'confirm_fn':function(){
						$.post("${ctx}/sys/user/resetPwd", params,function(result){
							if(result.messageStatus=="1"){
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
						});
				    },//���������ѡ��Ĭ��ֵ��function(){} �յķ�����
				    'cancel_fn':function(){
				        //���ȡ����Ҫִ�еĲ���
				    },
					open:function () {
                        typeChange(qCode);
                    }//���������ѡ��Ĭ��ֵ��function(){} �յķ�����
				});
			} 	
			
			$(document).one('ajaxloadstart.page', function(e) {
                $.jgrid.gridUnload(grid_selector);
                $('.ui-dialog').remove();
                $('.ui-jqdialog').remove();
                $('[class*=select2]').remove();
                $('.ui-helper-hidden-accessible').remove();
			});			
		});	
	});
	</script>