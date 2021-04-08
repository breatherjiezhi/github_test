<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>分配角色</title>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class="widget-box">
			<div class="widget-body">
				<div class="widget-main no-padding">
				<form action="" class="form-horizontal">
					<div class="form-group">
						<div class="control-label col-xs-12 col-sm-2 no-padding">
							<div class="clearfix">
								<span class="span1">角色名称: <b>${role.name}</b>
								</span>
							</div>
						</div>

						<div class="control-label col-xs-12 col-sm-2 no-padding">
							<div class="clearfix">
								<span class="span2">归属机构: ${role.office.name}</span>
							</div>
						</div>

						<div class="control-label col-xs-12 col-sm-2 no-padding">
							<div class="clearfix">
								<span class="span3">英文名称: ${role.enname}</span>
							</div>
						</div>

						<div class="control-label col-xs-12 col-sm-2 no-padding">
							<div class="clearfix">
								<span class="span4">角色类型: ${role.roleType}</span>
							</div>
						</div>

						<div class="control-label col-xs-12 col-sm-2 no-padding">
							<div class="clearfix">
								<span class="span5">数据范围:
									${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</span>
							</div>
						</div>

						<div class="control-label col-xs-12 col-sm-1 no-padding">
							<div class="clearfix">
								<button class="btn btn-minier btn-purple"id="query">分配角色</button>
							</div>
						</div>

					</div>
				</form>	
				</div>
			</div>
		</div>

		<table id="grid-tabletmp"></table>
		<div id="grid-pagertmp" style="display: none"></div>
		<div class="widget-box" id="editDivIdtmp" style="display: none">

		</div>
	</div>
</div>

