<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<title>部署流程 - 流程管理</title>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class="widget-box">
			<div class="widget-header widget-header-blue widget-header-flat">
				<h4 class="widget-title lighter">
					部署流程
				</h4>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<form id="inputForm" action="${ctx}/act/process/deploy"
						method="post" enctype="multipart/form-data"
						class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-xs-12 col-sm-3 no-padding-right"
								for="category">
								流程分类：
							</label>
							<div class="col-xs-12 col-sm-7">
								<div class="clearfix input-group">

									<select id="category" name="category"
										 class="chosen-select form-control">
										<c:forEach items="${fns:getDictList('act_category')}"
											var="dict">
											<option value="${dict.value}">
												${dict.label}
											</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-xs-12 col-sm-3 no-padding-right"
								for="file">
								流程文件：
							</label>
							<div class="col-xs-12 col-sm-7">
								<div class="clearfix input-group">
									<input type="file" id="file" name="file" class="required" />
									<span class="help-inline">支持文件格式：zip、bar、bpmn、bpmn20.xml</span>
								</div>
							</div>
						</div>

						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9 no-padding-left">
								<button class="btn btn-info btn-sm" type="button" id="submitBtn">保存</button>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

	var scripts = [ null, null ];
	$('.page-content-area').ace_ajax('loadScripts',scripts,
			function() {
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
					$('#inputForm').bootstrapValidator({
						message : '请录入一个有效值',
						excluded:':disabled, :hidden, :not(:visible)',
						feedbackIcons : {
							valid : 'glyphicon glyphicon-ok',
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							category : {
								validators : {
									notEmpty : {
										message : '请选择一个流程分类'
									}
								}
							},
							file : {
								validators : {
									notEmpty : {
										message : '新密码不能位空'
									}
								}
							}
						},
						submitButtons: '#submitBtn',
				        submitHandler: null,
					}).on('success.form.bv',function(e) {
						// Prevent form submission
						e.preventDefault();
						// Get the form instance
						var $form = $(e.target);
						// Get the BootstrapValidator instance
						var bv = $form.data('bootstrapValidator');
						// Use Ajax to submit form data
						
						$.ajaxFileUpload({
					         url:$form,

					         fileElementId:'file',
					         success: function (data, status){
					         	alert(data.message);
					         },
					         error: function (data, status){
					            alert(456);
					         }
					    });
/*						 $.post($form.attr('action'),$form.serialize(), function(result) {
							var status =result.status,type='success';
							if(status == 'failure'){type='error';}
			            	$.msg_show.Init({
			                   'msg':result.message,
			                   'type':type
			              	});
			            	$form.bootstrapValidator('disableSubmitButtons', false);
			            }, 'json');*/ 
					}) ;					

					$("#submitBtn").click(function() {
						$('#inputForm').bootstrapValidator('validate');
					});

					$('#resetBtn').click(function() {
						$('#inputForm').data('bootstrapValidator').resetForm(true);
					});
				});

			});
</script>