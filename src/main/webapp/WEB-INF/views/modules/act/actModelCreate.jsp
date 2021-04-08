<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>新建模型 - 模型管理</title>
<div class="widget-body" id="dictDivId">
	<div class="widget-main">
		<form id="inputForm" action="${ctx}/act/model/create" target="_blank"
			method="post" class="form-horizontal">
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-4 no-padding"
					for="category">
					流程分类：
				</label>
				<div class="col-xs-12 col-sm-6">
					<div class="clearfix input-group">
						<select id="category" name="category"
							class="chosen-select form-control">
							<c:forEach items="${fns:getDictList('act_category')}" var="dict">
								<option value="${dict.value}">
									${dict.label}
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-4 no-padding"
					for="name">
					模块名称：
				</label>
				<div class="col-xs-12 col-sm-6">
					<div class="clearfix">
						<input id="name" name="name" type="text" class="form-control width-100" />
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-4 no-padding" for="key">
					模块标识：
				</label>
				<div class="col-xs-12 col-sm-6">
					<div class="clearfix">
						<input id="key" name="key" type="text" class="form-control width-100" />
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-4 no-padding"
					for="description">
					模块描述：
				</label>
				<div class="col-xs-12 col-sm-6">
					<div class="clearfix">
						<textarea id="description" name="description" class="form-control width-100"></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>