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
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>${fns:getConfig('productName')}</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<meta http-equiv="progma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!-- bootstrap & fontawesome -->
	<link rel="stylesheet" href="${ctxStatic}/assets/css/bootstrap.css" />
	<link rel="stylesheet" href="${ctxStatic}/assets/css/font-awesome.css" />

	
	<!-- ace styles -->
	<link rel="stylesheet" href="${ctxStatic}/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
	<!-- 页签css -->
	<link rel="stylesheet" href="${ctxStatic}/assets/css/jquery.contextmenu.css" />	

	<link rel="stylesheet" href="${ctxStatic}/common/dhc.css" />

	<!--[if lte IE 9]>
		<link rel="stylesheet" href="${ctxStatic}/assets/css/ace-part2.css" class="ace-main-stylesheet" />
	<![endif]-->

	<!--[if lte IE 9]>
		<link rel="stylesheet" href="${ctxStatic}/assets/css/ace-ie.css" />
	<![endif]-->

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

	<!--[if lte IE 8]>
	<script src="${ctxStatic}/assets/js/html5shiv.js"></script>
	<script src="${ctxStatic}/assets/js/respond.js"></script>
	<![endif]-->
	<link rel="stylesheet" href="${ctxStatic}/layui/css/layui.css">
	<link rel="stylesheet" href="${ctxStatic}/layui/css/layer.css" >
	<link rel="stylesheet" href="${ctxStatic}/layui/css/laydate.css" >
	<link rel="stylesheet" href="${ctxStatic}/layui/css/code.css">
	<link rel="stylesheet" href="${ctxStatic}/layui/css/mul-select.css">
	<%--<link rel="stylesheet" href="${ctxStatic}/assets/font/iconfont.ttf">--%>
	<%--<link rel="stylesheet" href="${ctxStatic}/assets/font/iconfont.woff">--%>
	<%--<link rel="stylesheet" href="${ctxStatic}/assets/font/iconfont.woff2">--%>
	<script src="${ctxStatic}/assets/js/jquery.js"></script>
	<script type="text/javascript" src="${ctxStatic}/layui/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctxStatic}/layui/js/form.js"></script>
	<script type="text/javascript" src="${ctxStatic}/layui/js/layer.js"></script>
	<script src="${ctxStatic}/assets/js/bootstrap.js"></script>
	
	<!-- ace settings handler -->
	<script src="${ctxStatic}/assets/js/ace-extra.js"></script>
		<!-- 页签功能js -->
	<script src="${ctxStatic}/assets/js/bootstrap-tab.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.contextmenu.js"></script>
 	<script src="${ctxStatic}/assets/js/jquery.cookie.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.storageapi.min.js"></script>

		<!-- ace scripts -->
	<script src="${ctxStatic}/assets/js/ace/ace.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.sidebar.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.sidebar-scroll-1.js"></script>
	<script src="${ctxStatic}/assets/js/ace/ace.submenu-hover.js"></script>
	
	
	
<%-- 	<link rel="stylesheet" href="${ctxStatic}/contabs/js/bootstrap-tab.css" /> --%>
<%-- 	<script src="${ctxStatic}/contabs/js/bootstrap-tab.js"></script> --%>
<%-- 	<script src="${ctxStatic}/contabs/js/sidebar-menu.js"></script> --%>
	
</head>

<body class="no-skin">
	<!-- #section:basics/navbar.layout -->
	<div id="navbar" class="navbar navbar-default">
		<script type="text/javascript">
			try{ace.settings.check('navbar' , 'fixed');}catch(e){}
		</script>
		
		<div class="navbar-container" id="navbar-container">
			<!-- #section:basics/sidebar.mobile.toggle -->
			<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<!-- /section:basics/sidebar.mobile.toggle -->
			
			<div class="navbar-header pull-left">
				<!-- #section:basics/navbar.layout.brand -->
			<%-- 
				<a href="#" class="navbar-brand">
					<small>
						<i class="fa fa-leaf"></i>
						DHC
					</small>
				</a>
			--%>
				<div class="brand">
					<img alt="" src="${pageContext.request.contextPath}/static/images/logo.png"/>
				</div>
				<!-- /section:basics/navbar.layout.brand -->
				<!-- #section:basics/navbar.toggle -->
				<!-- /section:basics/navbar.toggle -->
			</div>

			<!-- #section:basics/navbar.dropdown -->
			<div class="navbar-buttons navbar-header pull-right" role="navigation">
				<ul class="nav ace-nav">
					<li class="transparent" id="notifyLi">
						<a href="#page/sys/notify">
							<i class="ace-icon fa fa-bell icon-animated-bell"></i>
							<span class="badge badge-important" id="unReadNotifys"></span>
						</a>
					</li>
					
					<!-- #section:basics/navbar.user_menu -->
					<li class="light-blue user-min">
						<a data-toggle="dropdown" href="javascript:;" class="">
							<c:if test="${empty fns:getUser().photo}">
								<img class="nav-user-photo" src="${ctxStatic}/assets/avatars/user.jpg" alt="${fns:getUser().name}" >
							</c:if>
							<c:if test="${not empty fns:getUser().photo}">
								<img class="nav-user-photo" src="${ctxUploads}/userAvatar/${fns:getUser().id}_large${fns:getUser().photo}" alt="${fns:getUser().name}"onerror="this.src='${ctxStatic}/assets/avatars/user.jpg'">
							</c:if>	
							<span class="user-info">
								<small>欢迎,</small>
								${fns:getUser().name}
							</span>

							<i class="ace-icon fa fa-caret-down"></i>
						</a>
	
						<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
							<li>
								<a href="#page/sys/user/info">
									<i class="ace-icon fa fa-user"></i>
									个人信息
								</a>
							</li>
							<li>
								<a href="#page/sys/user/modifyPwd">
									<i class="ace-icon fa fa-lock"></i>
									修改密码
								</a>
							</li>
							<li>
								<a href="#page/sys/notify">
									<i class="ace-icon fa fa-bell"></i>
									我的通知
								</a>
							</li>
							<li class="divider"></li>	
							<li>
								<a href="${ctx}/logout" title="退出登录">
									<i class="ace-icon fa fa-power-off"></i>
									退出
								</a>
							</li>
						</ul>
					</li>
					<!-- /section:basics/navbar.user_menu -->
				</ul>
			</div>
			<!-- /section:basics/navbar.dropdown -->
			
			<nav role="navigation" class="navbar-menu pull-right collapse navbar-collapse">
				<!-- #section:basics/navbar.nav -->
				<ul id="menu" class="nav navbar-nav">
				<c:set var="menuList" value="${fns:getTreeMenuList(false)}"/>
				<c:set var="firstMenu" value="true"/>
				<c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
					<c:if test="${menu.parent.id eq '1'&& menu.isShow eq '1'}">
						<li class="${not empty firstMenu && firstMenu ? 'active' : ''}">
							<c:if test="${empty menu.href}">
								<a class="menu" href="javascript:;" data-href="${ctx}/sys/menu/treeData4ajax?parentId=${menu.id}" data-id="${menu.id}">
								<i class="ace-icon fa ${not empty menu.icon ? menu.icon : 'fa-envelope'}"></i>&nbsp;${menu.name}</a>
							</c:if>
							<c:if test="${not empty menu.href}">
								<a class="menu" href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" data-id="${menu.id}" target="mainFrame">${menu.name}</a>
							</c:if>
						</li>
						<c:if test="${firstMenu}">
							<c:set var="firstMenuId" value="${menu.id}"/>
						</c:if>
						<c:set var="firstMenu" value="false"/>
					</c:if>
				</c:forEach>
				</ul>

				<!-- /section:basics/navbar.nav -->
			</nav>
			
		</div><!-- /.navbar-container -->
	</div>

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed');}catch(e){}
		</script>
	
		<!-- #section:basics/sidebar -->
		<div class="sidebar responsive" id="sidebar">
			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'fixed');}catch(e){}
			</script>
			
			<div class="sidebar-shortcuts" id="sidebar-shortcuts">
				<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
					<button class="btn btn-success">
						<i class="ace-icon fa fa-signal"></i>
					</button>

					<button class="btn btn-info">
						<i class="ace-icon fa fa-pencil"></i>
					</button>

					<!-- #section:basics/sidebar.layout.shortcuts -->
					<button class="btn btn-warning">
						<i class="ace-icon fa fa-users"></i>
					</button>

					<button class="btn btn-danger">
						<i class="ace-icon fa fa-cogs"></i>
					</button>

					<!-- /section:basics/sidebar.layout.shortcuts -->
				</div>

				<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
					<span class="btn btn-success"></span>

					<span class="btn btn-info"></span>

					<span class="btn btn-warning"></span>

					<span class="btn btn-danger"></span>
				</div>
			</div><!-- /.sidebar-shortcuts -->
			
			<ul class="nav nav-list" id="sidebar-container">
				<c:forEach items="${menuList}" var="parent" varStatus="idxStatus">
					<c:if test="${parent.parent.id eq '1'&& parent.isShow eq '1'}">
						<c:forEach items="${parent.subMenu}" var="child" varStatus="childStatus">
							<c:if test="${child.isShow eq '1'}">
							<li data-id="${child.parent.id}" style="display:none;">
								<c:choose>	
									<c:when test="${fn:length(child.subMenu) eq 0}">
									<a id="menuitem_${child.id}" data-url="${fn:indexOf(child.href, '://') eq -1 ? 'a' : ''}${not empty child.href ? child.href : '/404'}" 
									href="javascript:addTabs({id:'${child.id}',title:'${child.name}',close: true,url:'${fn:indexOf(child.href, '://') eq -1 ? 'a' : ''}${not empty child.href ? child.href : '/404'}'})" target="${child.target}">
								<i class="menu-icon fa ${not empty child.icon ? child.icon : 'fa-list-alt'}"></i>
								<span class="menu-text">${child.name}</span>
								</a>
								<b class="arrow"></b>
								</c:when>
								<c:otherwise>
								<a id="menuitem_${child.id}" href="#" class="dropdown-toggle">
								<i class="menu-icon fa ${not empty child.icon ? child.icon : 'fa-list-alt'}"></i>
								<span class="menu-text">${child.name}</span>
								<b class="arrow fa fa-angle-down"></b></a>
								<b class="arrow"></b>
								<ul class="submenu">
									<c:forEach items="${child.subMenu}" var="subchild" varStatus="subchildStatus">
									<c:if test="${subchild.isShow eq '1'}">
									<li>
										<a id="menuitem_${subchild.id}" data-url="${fn:indexOf(subchild.href, '://') eq -1 ? 'a' : ''}${not empty subchild.href ? subchild.href : '/404'}" 
										href="javascript:addTabs({id:'${subchild.id}',title:'${subchild.name}',close: true,url:'${fn:indexOf(subchild.href, '://') eq -1 ? 'a' : ''}${not empty subchild.href ? subchild.href : '/404'}'})" target="${subchild.target}">
										<i class="menu-icon fa ${not empty subchild.icon ? subchild.icon : ''}"></i>
										${subchild.name}
										</a>
										<b class="arrow"></b>
									</li>
									</c:if>
									</c:forEach>
								</ul>	
								</c:otherwise>
								</c:choose>
							</li>
							</c:if>
							</c:forEach>	
					</c:if>
				</c:forEach>
			</ul><!-- /.nav-list -->
			
			<!-- #section:basics/sidebar.layout.minimize -->
			<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
				<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
			</div>
			<!-- /section:basics/sidebar.layout.minimize -->
			
			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'collapsed');}catch(e){};
			</script>
		</div>
		<!-- /section:basics/sidebar -->
					
		<div class="main-content">
			
			<div class="page-content">
			
				<!-- #section:settings.box -->
				<div class="ace-settings-container" id="ace-settings-container">
					<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
						<i class="ace-icon fa fa-cog bigger-130"></i>
					</div>

					<div class="ace-settings-box clearfix" id="ace-settings-box">
						<div class="pull-left width-50">
							<!-- #section:settings.skins -->
							<div class="ace-settings-item">
								<div class="pull-left">
									<select id="skin-colorpicker" class="hide">
										<option data-skin="no-skin" value="#438EB9">#438EB9</option>
										<option data-skin="skin-1" value="#222A2D">#222A2D</option>
										<option data-skin="skin-2" value="#C6487E">#C6487E</option>
										<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
									</select>
								</div>
								<span>&nbsp; 主题选择</span>
							</div>
							<!-- /section:settings.skins -->

							<!-- #section:settings.navbar -->
							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
								<label class="lbl" for="ace-settings-navbar"> 固定顶部导航</label>
							</div>
							<!-- /section:settings.navbar -->

							<!-- #section:settings.sidebar -->
							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
								<label class="lbl" for="ace-settings-sidebar"> 固定左侧菜单</label>
							</div>
							<!-- /section:settings.sidebar -->

							<!-- #section:settings.breadcrumbs -->
							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
								<label class="lbl" for="ace-settings-breadcrumbs"> 固定面包屑</label>
							</div>
							<!-- /section:settings.breadcrumbs -->

						</div><!-- /.pull-left -->

						<div class="pull-left width-50">
							<!-- #section:basics/sidebar.options -->
							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" />
								<label class="lbl" for="ace-settings-hover"> 子菜单浮层显示</label>
							</div>

							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" />
								<label class="lbl" for="ace-settings-compact"> 窄左侧菜单</label>
							</div>

							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" />
								<label class="lbl" for="ace-settings-highlight"> 切换选中效果</label>
							</div>

							<!-- #section:settings.container -->
							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
								<label class="lbl" for="ace-settings-add-container">
									切换窄屏
								</label>
							</div>

							<!-- /section:settings.container -->
							<!-- /section:basics/sidebar.options -->
						</div><!-- /.pull-left -->
					</div><!-- /.ace-settings-box -->
				</div><!-- /.ace-settings-container -->


				
				<div class="row">
						<div class="col-xs-12" style="width: 99%;padding-left:2px;padding-right: 2px;" id="tabs">
	                            <ul class="nav nav-tabs" role="tablist">
	                               
	                            </ul>

<!-- 							<ul class="nav-my-tab"> -->
<!--                                 <li class="leftbackward"><a href="#" > -->
<!--                                     <i class="icon-backward"></i></a> -->
<!--                                 </li> -->
<!--                                 <li class="middletab"> -->
<!--                                     <ul class="nav nav-tabs" role="tablist"> -->
<!--                                         <li class="active"><a href="#Index" role="tab" data-toggle="tab"> -->
<!--                                             <i class="icon-home"></i>系统首页</a> -->
<!--                                         </li> -->
<!--                                     </ul> -->
<!--                                 </li> -->
<!--                                 <li class="rightforward"><a href="#"> -->
<!--                                         <i class="icon-forward"></i></a> -->
<!--                                  </li> -->
<!--                             </ul> -->
                            
	                            <div  class="tab-content">
	                                <div role="tabpanel" class="tab-pane active" id="Index">
	                            	</div>
	                     		</div>
	               		</div>
				</div><!-- /.row -->
			</div><!-- /.page-content -->
		</div><!-- /.main-content -->

		<!-- 去页面顶部的导航按钮 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div><!-- /.main-container -->
	
	<!-- basic scripts -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery.js'>"+"<"+"script>");
		</script>



	<script type="text/javascript">
		$(document).ready(function() {	
	
			// 绑定菜单单击事件
			$("#menu a.menu").click(function(){
				// 一级菜单焦点
				$("#menu li").removeClass("active");
				$(this).parent().addClass("active");
				var id = $(this).attr('data-id');
				if($("div#sidebar ul.nav li[data-id='"+id+"']").length !=0){
					$("div#sidebar ul.nav li[data-id]").hide();
					$("div#sidebar ul.nav li[data-id='"+id+"']").show();
				}
			});

			
			// 获取通知数目
			function getNotifyNum(){
				$.get("${ctx}/sys/notify/count?updateSession=0&t="+new Date().getTime(),function(data){
					if(data.length>5){
						alert('未登录或登录超时。请重新登录，谢谢！');
						top.location = "${ctx}";
						return;
					}else if (data != '0'){
						if($("#notifyLi:hidden").length!=0){
							$("#notifyLi").show();
						}
						$("#unReadNotifys").html(data);
					}else{
						$("#notifyLi").hide();
					}
				});
			}
			getNotifyNum(); 
			setInterval(getNotifyNum, 60000*3); 
			
            $("#menu a:first-child").get(0).click();
		});
	</script>
<!-- 页签功能js -->		
<script>
		jQuery(function($) {
			addTabs({id:'home',title:'首页',close: false,url: 'a/sys/user/homePage'});
			$('.theme-poptit .close').click(function(){
	    		$('.theme-popover-mask').fadeOut(100);
	    		$('.theme-popover').slideUp(200);
	    	});
	    	$('#closeBtn').click(function(){
	    		$('.theme-popover-mask').fadeOut(100);
	    		$('.theme-popover').slideUp(200);
	    	});
		});
</script> 
		
</body>
</html>