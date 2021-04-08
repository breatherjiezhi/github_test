<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@page import="com.dhc.rad.common.config.Global"%>
<c:set var="ctx" value="${pageContext.request.contextPath}${fns:getAdminPath()}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxUploads" value="${pageContext.request.contextPath}/uploads"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<link rel="icon" href="${ctxStatic}/assets/images/favicon.ico" type="image/x-icon"/>
<!--[if !IE]> -->
	<link rel="stylesheet" href="${ctxStatic}/assets/css/pace.css" />
	<script data-pace-options='{ "ajax": true, "document": true, "eventLag": false, "elements": false }' src="${ctxStatic}/assets/js/pace.js"></script>
	<!-- <![endif]-->
	
	<!-- bootstrap & fontawesome -->
	<link rel="stylesheet" href="${ctxStatic}/assets/css/bootstrap.css" />
	<link rel="stylesheet" href="${ctxStatic}/assets/css/font-awesome.css" />

	<!-- text fonts -->
	<link rel="stylesheet" href="${ctxStatic}/assets/css/ace-fonts.css" />
	<link rel="stylesheet" href="${ctxStatic}/assets/css/ui.jqgrid.5.0.1.css" />
	<link rel="stylesheet" href="${ctxStatic}/assets/css/jquery-ui.css" />
	<link rel="stylesheet" href="${ctxStatic}/assets/css/jquery-ui.custom.css" />
	<link rel="stylesheet" href="${ctxStatic}/assets/css/chosen.css" />
	<link rel="stylesheet" href="${ctxStatic}/assets/css/select2.css" />
	<link rel="stylesheet" href="${ctxStatic}/assets/css/jquery.gritter.css"/>
	<link rel="stylesheet" href="${ctxStatic}/assets/css/bootstrapValidator.css" />
	
	<!-- ace styles -->
	<link rel="stylesheet" href="${ctxStatic}/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />


	<link rel="stylesheet" href="${ctxStatic}/common/dhc.css" />

	<script src="${ctxStatic}/assets/js/jquery.js"></script>
	<!-- ace settings handler -->
	<script src="${ctxStatic}/assets/js/ace-extra.js"></script>

	
	<script src="${ctxStatic}/assets/js/bootstrap.js"></script>
	
	<!-- ace scripts -->
<%-- 	<script src="${ctxStatic}/assets/js/ace-elements.js"></script> --%>
	<script src="${ctxStatic}/assets/js/jquery.livejquery.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.scroller.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.colorpicker.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.fileinput.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.typeahead.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.wysiwyg.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.spinner.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.treeview.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.wizard.js"></script>
	<script src="${ctxStatic}/assets/js/ace/elements.aside.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.ajax-content.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.touch-drag.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.sidebar.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.sidebar-scroll-1.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.submenu-hover.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.widget-box.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.settings.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.settings-rtl.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.settings-skin.js"></script>	
	<script src="${ctxStatic}/assets/js/ace/ace.widget-on-reload.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.searchbox-autocomplete.js"></script>
	
	<script src="${ctxStatic}/assets/js/jquery-ui.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.ui.touch-punch.js"></script>
	<script src="${ctxStatic}/assets/js/chosen.jquery.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.autosize.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.inputlimiter.1.3.1.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.maskedinput.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.gritter.js"></script>
	<script src="${ctxStatic}/assets/js/bootbox.js"></script>
	
	<script src="${ctxStatic}/assets/js/jquery.validate.js"></script>
	<script src="${ctxStatic}/assets/js/jqGrid/jquery.jqGrid.js"></script>
	<script src="${ctxStatic}/assets/js/jqGrid/i18n/grid.locale-cn.js"></script>
	
	<script src="${ctxStatic}/assets/js/select2.js" type="text/javascript"></script>
	<script src="${ctxStatic}/assets/js/bootstrapValidator.js"></script>
	
	<script src="${ctxStatic}/common/dhc.js" type="text/javascript"></script>
	
<%-- 	<script src="${ctxStatic}/assets/js/form-elements.js"></script> --%>
<%-- 	<script src="${ctxStatic}/assets/js/elements.js"></script> --%>

</head>
<body>
	<div class="page-content-area" style="display: none;"></div>
</body>
</html>