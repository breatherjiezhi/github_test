<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>${fns:getConfig('productName')}</title>
	
	<meta http-equiv="progma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@include file="/WEB-INF/views/include/head.jsp" %>

	<script type="text/javascript">
	    var ctx = '${ctx}';
	</script>
	<style>
		.navTab {
			width: 98.5%;
			height: 35px;
			background: #fff;
			margin: 10px auto 0;
		}
		.navSpan{
			width: auto;
			padding: 4px 20px;
			background: #e8ebf3;
			margin: 4px 10px;
			display: inline-block;
			border-radius: 5px;
			cursor: pointer;
		}
        .navSpanfocus{
        background:#5b7cf0 !important;
        color:#fff !important;
        }
        .navSpanfocus a{
            color:#fff !important;
        }
        .navSpanSY{
            width: auto;
            padding: 4px 20px;
            background: #e8ebf3;
            margin: 4px 10px;
            display: inline-block;
            border-radius: 5px;
            cursor: pointer;
            text-decoration:none;
        }
		#sidebar{
			background-color:#5075e4;
		}

	</style>
</head>
<body class="no-skin">
	<div class="navbar navbar-default navbar-collapse" id="navbar">
		<script type="text/javascript">
			try{ace.settings.check('navbar' , 'fixed')}catch(e){};
		</script>
		<div class="navbar-container" id="navbar-container">
			<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>

			<button class="pull-right navbar-toggle navbar-toggle-img collapsed" type="button" data-toggle="collapse" data-target=".navbar-buttons,.navbar-menu">
				<span class="sr-only">Toggle user menu</span>
				<c:if test="${empty fns:getUser().photo}">
					<img id="navbarToggleImg" src="${ctxStatic}/assets/avatars/avatar5.png" alt="${fns:getUser().name}" >
				</c:if>
				<c:if test="${not empty fns:getUser().photo}">
			    	<img id="navbarToggleImg"  src="${ctxUploads}/userAvatar/${fns:getUser().id}_large${fns:getUser().photo}" alt="${fns:getUser().name}"onerror="this.src='${ctxStatic}/assets/avatars/avatar5.png'">
			    </c:if>	
			<!-- 	<img src="${ctxStatic}/assets/avatars/user.jpg" alt="${fns:getUser().name}" />   -->
			</button>
					
			<div class="navbar-header pull-left"><div class="brand">
				<%--<img alt="" src="${pageContext.request.contextPath}/static/images/logo.png" />--%>
			</div></div>
			
			<div class="navbar-buttons navbar-header pull-right collapse navbar-collapse " role="navigation">
					<ul class="nav ace-nav">
						<li class="transparent" id="fullScreen" style="display: none">
							<a href="#" id='J_tabFullScreen' class="J_tabFullScreen"  title="全屏"><i class="fa fa-arrows-alt" style="font-size:18px;text-align: center"></i><span></span></a>
						</li>
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
									<img class="nav-user-photo" src="${ctxStatic}/assets/avatars/avatar5.png" alt="${fns:getUser().name}" >
								</c:if>
								<c:if test="${not empty fns:getUser().photo}">
							    	<img class="nav-user-photo" src="${ctxUploads}/userAvatar/${fns:getUser().id}_large${fns:getUser().photo}" alt="${fns:getUser().name}"onerror="this.src='${ctxStatic}/assets/avatars/avatar5.png'">
							    </c:if>	
								<span class="user-info">
									<small>欢迎您,</small>
									${fns:getUser().name}
								</span>

								<i class="ace-icon fa fa-caret-down"></i>
							</a>
	
							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<%--<li>--%>
									<%--<a href="#page/sys/user/info">--%>
										<%--<i class="ace-icon fa fa-user"></i>--%>
										<%--个人信息--%>
									<%--</a>--%>
								<%--</li>--%>
								<%--<li>--%>
									<%--<a href="#page/sys/user/modifyPwd">--%>
										<%--<i class="ace-icon fa fa-lock"></i>--%>
										<%--修改密码--%>
									<%--</a>--%>
								<%--</li>--%>
								<li>
									<a href="#page/sys/user/homePage">
										<i class="ace-icon fa fa-home"></i>
                                        <span data-locale="HomePage">首页</span>
									</a>
								</li>
								<li>
									<a href="#page/sys/notify">
										<i class="ace-icon fa fa-bell"></i>
                                        <span data-locale="Notifications">我的通知</span>
									</a>
								</li>
								<li class="divider"></li>
								<li class="toChinese" style="display:none;">
									<a title="Chinese" onclick="zw()">
										<i class="ace-icon fa fa-exchange"></i>
										切换为中文
									</a>
								</li>
								<li class="toEnglish" style="display:none;">
									<a  title="English" onclick="yw()">
										<i class="ace-icon fa fa-exchange"></i>
										English
									</a>
								</li>
								<li>
									<a href="${ctx}/logout" title="退出登录">
										<i class="ace-icon fa fa-power-off"></i>
                                        <span data-locale="Exit">退出</span>
									</a>
								</li>

							</ul>
						</li>
						

						<!-- /section:basics/navbar.user_menu -->
					</ul>
				</div>
			
			<nav role="navigation" class="navbar-menu pull-left collapse navbar-collapse">
					<!-- #section:basics/navbar.nav -->
					<ul id="menu" class="nav navbar-nav" style="margin-left: 180px;">
						<li  class="sidebar-toggle sidebar-collapse" data-target="#sidebar"id="sidebar-collapse"><a style="padding: 14px 20px;font-size: 16px;"><i class="fa fa-outdent" aria-hidden="true"></i></a></li>
					<c:set var="menuList" value="${fns:getTreeMenuList(false)}"/>
					<c:set var="firstMenu" value="true"/>
					<c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
						<c:if test="${menu.parent.id eq '1'&& menu.isShow eq '1'}">
							<li class="${not empty firstMenu && firstMenu ? 'active' : ''}">
								<c:if test="${empty menu.href}">
									<a class="menu" href="javascript:;" data-href="${ctx}/sys/menu/treeData4ajax?parentId=${menu.id}" data-id="${menu.id}">
									<i class="ace-icon fa ${not empty menu.icon ? menu.icon : 'fa-envelope'}"></i>
										<c:if test="${empty menu.signs}"><span  >&nbsp;${menu.name}</span> </c:if>

										<c:if test="${not empty menu.signs}"> <span data-locale="${menu.signs}" >&nbsp;${menu.name}</span> </c:if>

									</a>
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
		</div><!-- /.container -->
	</div>
	
	
	
	<div class="main-container" id="main-container">

		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>
	
		<div class="sidebar responsive" id="sidebar">
			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
			</script>
			<div class="sidebar-shortcuts" id="sidebar-shortcuts" style="display: none">
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
			<ul class="nav nav-list" id="sidebar-container" style="margin-top: -31.5%">
				<li style="height: 60px;
    background-color: #3f67e7;
    color: #fff;
    font-size: 15px;
    text-align: center;
    line-height: 60px;
    font-weight: 600;">智能点餐系统</li>

				<c:forEach items="${menuList}" var="parent" varStatus="idxStatus">
					<c:if test="${parent.parent.id eq '1'&&  parent.isShow eq '1'}">
							<c:forEach items="${parent.subMenu}" var="child" varStatus="childStatus">
							<c:if test="${child.isShow eq '1'}">
							<li data-id="${child.parent.id}" style="display:none;" class="liNav">
								<c:choose>	
								<c:when test="${fn:length(child.subMenu) eq 0}">
								<a id="menuitem_${child.id}" data-url="${fn:indexOf(child.href, '://') eq -1 ? 'page' : ''}${not empty child.href ? child.href : '/404'}" 
							
								href="${fn:indexOf(child.href, '://') eq -1 ?'#page' : ''}${not empty child.href ? child.href : '/404'}" target="${child.target}">
								<i class="menu-icon fa ${not empty child.icon ? child.icon : 'fa-list-alt'}"></i>
                                    <c:if test="${empty child.signs}"> <span class="menu-text"  >${child.name}</span>  </c:if>

                                    <c:if test="${not empty child.signs}">  <span class="menu-text"  data-locale="${child.signs}">${child.name}</span> </c:if>

								</a>
								<b class="arrow"></b>
								</c:when>
								<c:otherwise>
								<a id="menuitem_${child.id}" href="#" class="dropdown-toggle">
								<i class="menu-icon fa ${not empty child.icon ? child.icon : 'fa-list-alt'}"></i>
                                    <c:if test="${empty child.signs}"> <span class="menu-text"  >${child.name}</span>  </c:if>

                                    <c:if test="${not empty child.signs}">  <span class="menu-text"  data-locale="${child.signs}">${child.name}</span> </c:if>
								<b class="arrow fa fa-angle-down"></b></a>
								<b class="arrow"></b>
								<ul class="submenu">
									<c:forEach items="${child.subMenu}" var="subchild" varStatus="subchildStatus">
									<c:if test="${subchild.isShow eq '1'}">
									<li class="liNavChild">
										<a id="menuitem_${subchild.id}" data-url="${fn:indexOf(subchild.href, '://') eq -1 ? 'page' : ''}${not empty subchild.href ? subchild.href : '/404'}" 
										
										href="${fn:indexOf(subchild.href, '://') eq -1 ? '#page' : ''}${not empty subchild.href ? subchild.href : '/404'}" target="${subchild.target}">
										<i class="menu-icon fa ${not empty subchild.icon ? subchild.icon : ''}"></i>
                                            <c:if test="${empty subchild.signs}"><span >${subchild.name}</span> </c:if>
                                            <c:if test="${not empty subchild.signs}"> <span data-locale="${subchild.signs}">${subchild.name}</span> </c:if>

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
		    </ul>
			 
			<%--<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse" data-target="#sidebar" style="display: none">--%>
	   			<%--<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>--%>
			<%--</div>--%>
			<!-- /section:basics/sidebar.layout.minimize -->
			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'collapsed')}catch(e){};
			</script>
		</div><!-- /.sidebar -->	
					
		<div class="main-content">
			<div class="navTab">
                <span class="navSpanSY" ><a href="#page/sys/user/homePage"><i class="fa fa-home" style="margin-right: 5px;"></i>首页</a></span>
			</div>

			<div class="main-content-inner">

                    

			    <div class="page-content l2m-no-header no-padding-bottom">
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
<!-- 									<div class="ace-settings-item"> -->
<!-- 										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" /> -->
<!-- 										<label class="lbl" for="ace-settings-breadcrumbs"> 固定面包屑</label> -->
<!-- 									</div> -->

									<!-- /section:settings.breadcrumbs -->
									
									<!-- #section:settings.container -->
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
										<label class="lbl" for="ace-settings-add-container">
											切换窄屏
										</label>
									</div>

									<!-- /section:settings.container -->

								</div><!-- /.pull-left -->

								<div class="pull-left width-50">
									<!-- #section:basics/sidebar.options -->
									<div class="ace-settings-item">
										<input type="hidden" />
										<label class="lbl" for="ace-settings-hover"></label>
									</div>
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

									
									<!-- /section:basics/sidebar.options -->
								</div><!-- /.pull-left -->
							</div><!-- /.ace-settings-box -->
						</div><!-- /.ace-settings-container -->

						<!-- /section:settings.box -->
						<div class="page-content-area" data-ajax-content="true">
							<!-- ajax content goes here -->
						</div><!-- /.page-content-area -->
					
			      <!-- <div class="row">
			        <div class="col-xs-12">
			          <iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="600"></iframe>
			        </div>
			      </div> -->
			
			    </div><!-- /.page-content -->
		  	</div>
	  	</div><!-- /.main-content -->
	 
	  	
	
		
		
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
		
	</div>
	
</div>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7d36Hmxbp9YlQZ69mb6730X6nWYaA12S"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/TrafficControl/1.4/src/TrafficControl_min.js"></script>
  	<!--[if gte IE 9]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
	<!-- <![endif]-->
	
	<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
	<!-- <![endif]-->

	<!--[if lt IE 9]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
	<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${ctxStatic}/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		<script src="${ctxStatic}/assets/js/bootstrap.js"></script>

		<!--[if lte IE 8]>
		  <script src="${ctxStatic}/assets/js/excanvas.js"></script>
		<![endif]-->
		
		
		<script src="${ctxStatic}/jquery-validation/1.11.0/lib/jquery.form.js"></script>
		<script src="${ctxStatic}/assets/js/jqGrid/jquery.jqGrid.js"></script>
		<script src="${ctxStatic}/assets/js/jqGrid/i18n/grid.locale-cn.js"></script>
		
				
<script src="${ctxStatic}/assets/js/select2.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/assets/js/bootstrapValidator.js"></script>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${ctxStatic}/layui/css/layui.css">
	<link rel="stylesheet" href="${ctxStatic}/layui/css/layer.css" >
	<link rel="stylesheet" href="${ctxStatic}/layui/css/laydate.css" >
	<link rel="stylesheet" href="${ctxStatic}/layui/css/code.css">
	<link rel="stylesheet" href="${ctxStatic}/layui/css/mul-select.css">
	<%--<link rel="stylesheet" href="${ctxStatic}/assets/font/iconfont.ttf">--%>
	<%--<link rel="stylesheet" href="${ctxStatic}/assets/font/iconfont.woff">--%>
	<%--<link rel="stylesheet" href="${ctxStatic}/assets/font/iconfont.woff2">--%>
<!-- <link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet" /> -->
<script src="${ctxStatic}/common/jeesite.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/i18n/js/jquery.i18n.properties.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctxStatic}/layui/js/layui.all.js"></script>
	<script type="text/javascript" src="${ctxStatic}/layui/js/form.js"></script>
	<script type="text/javascript" src="${ctxStatic}/layui/js/layer.js"></script>
<%-- <script src="${ctxStatic}/modules/sys/js/plug-in.js" type="text/javascript"></script> --%>
	<!-- ace scripts -->
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
	<script src="${ctxStatic}/assets/js/jquery-ui.custom.js"></script>
	<script src="${ctxStatic}/assets/js/jquery-ui.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.ui.touch-punch.js"></script>
	<script src="${ctxStatic}/assets/js/chosen.jquery.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.autosize.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.inputlimiter.1.3.1.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.maskedinput.js"></script>
	<script src="${ctxStatic}/assets/js/bootstrap-tag.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.easypiechart.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.sparkline.js"></script>
	<script src="${ctxStatic}/assets/js/flot/jquery.flot.js"></script>
	<script src="${ctxStatic}/assets/js/flot/jquery.flot.pie.js"></script>
	<script src="${ctxStatic}/assets/js/flot/jquery.flot.resize.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.gritter.js"></script>
	<script src="${ctxStatic}/assets/js/bootbox.js"></script>
	<script src="${ctxStatic}/assets/js/jquery.livejquery.js"></script>
    <script src="${ctxStatic}/assets/js/Print.js"></script>
	<script src="${ctxStatic}/assets/js/x-editable/bootstrap-editable.js"></script>
	<script src="${ctxStatic}/assets/js/x-editable/ace-editable.js"></script>
	<script>


        if($("#sidebar").css("width").split("p")[0]==43){
            $("#menu").css("margin-left","43px");
            $("#sidebar-container").css("margin-top","-60px")
        }else{
            $("#menu").css("margin-left","180px");
        }
        $("#sidebar-collapse").click(function(){
            var marLeft=$("#menu").css("margin-left").split("p")[0];
            if(marLeft=="200"){
                $("#menu").css("margin-left","43px");
                $("#sidebar-container").css("margin-top","-60px")
            }else{
                $("#menu").css("margin-left","180px");
            }
        });





        var hasSession=JSON.parse(sessionStorage.getItem("tabHistory"));
        if(hasSession==""||hasSession==null){
            var tabArr=[];
        }else{
            //sessionStorage存了值就回显
            var tabArr=hasSession;
            var locaHref=window.location.href;
            locaHref=locaHref.split("#")[1];
            locaHref="#"+locaHref
            $(".navSpan").remove();
            if(locaHref=="#page/sys/user/homePage"){
                $(".navSpanSY").addClass("navSpanfocus")
			}
            for(var i=0;i<tabArr.length;i++){
                if(locaHref==tabArr[i].url){
                    if (null==tabArr[i].datalocale){
                        $('<span class="navSpan navSpanfocus">' +
                            '<a href="'+tabArr[i].url+'"><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i>'+tabArr[i].text+'</a>' +
                            '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
					}else {
                        $('<span class="navSpan navSpanfocus">' +
                            '<a href="'+tabArr[i].url+'"><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i><span  data-locale="'+tabArr[i].datalocale+'">'+tabArr[i].text+'</span></a>' +
                            '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
					}

                }else{
                    if (null==tabArr[i].datalocale){
                        $('<span class="navSpan">' +
                            '<a href="'+tabArr[i].url+'" ><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i>'+tabArr[i].text+'</a>' +
                            '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
                    }else {
                        $('<span class="navSpan">' +
                            '<a href="'+tabArr[i].url+'" ><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i><span  data-locale="'+tabArr[i].datalocale+'">'+tabArr[i].text+'</span></a>' +
                            '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
					}

                }
            };
        }
        $(".liNav").click(function(){
            var tabObj=new Object();
		    if($(this).children().hasClass("submenu")){
				return;
			}else if($(this).children().find("span").text()=="首页") {
                $(".navSpan").removeClass("navSpanfocus");
				 $(".navSpanSY").addClass("navSpanfocus")

            }else{
                $(".navSpanSY").removeClass("navSpanfocus")
                var str=$(this).children().find("i").attr("class").split(" ")[2];
				var textValue=$(this).children().find("span").text();
				var dataLocale=$(this).children().find("span").attr("data-locale");
				var url=$(this).children().attr("href");
                tabObj.icon=str;
                tabObj.text=textValue;
                tabObj.datalocale=dataLocale;
                tabObj.url=url;
                if(tabArr==null||tabArr==""){
                    tabArr.push(tabObj);
                    sessionStorage.setItem("tabHistory", JSON.stringify(tabArr));
                    $(".navSpan").remove();

                    for(var i=0;i<tabArr.length;i++){
                        if (null==tabArr[i].datalocale){
                            $('<span class="navSpan">' +
                                '<a href="'+tabArr[i].url+'"><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i>'+tabArr[i].text+'</a>' +
                                '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
                        }else {
                            $('<span class="navSpan">' +
                                '<a href="'+tabArr[i].url+'"><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i><span  data-locale="'+tabArr[i].datalocale+'">'+tabArr[i].text+'</span></a>' +
                                '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
						}

                    };
                    $(".navTab").children("span:last-child").addClass("navSpanfocus");
                }else{

                    var a = 0 ;
                    var childNum;
                    for(var i=0;i<tabArr.length;i++) {
                        if (url==tabArr[i].url ) {
                            a++;
                            childNum=i+2
                             location.href=url;
                            $(".navSpan").removeClass("navSpanfocus");
                            $(".navTab").children(":nth-child("+childNum+")").addClass("navSpanfocus");
                        }
                    }
                    if(a==0){
                        if(tabArr.length<=3){
                            tabArr.push(tabObj);
                            sessionStorage.setItem("tabHistory", JSON.stringify(tabArr));

                            $(".navSpan").remove();
                            for(var i=0;i<tabArr.length;i++){
                                if (null==tabArr[i].datalocale){
                                    $('<span class="navSpan">' +
                                        '<a href="'+tabArr[i].url+'"><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i>'+tabArr[i].text+'</a>' +
                                        '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
                                }else {
                                    $('<span class="navSpan">' +
                                        '<a href="'+tabArr[i].url+'"><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i><span  data-locale="'+tabArr[i].datalocale+'">'+tabArr[i].text+'</span></a>' +
                                        '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
								}

                            };
                            $(".navTab").children("span:last-child").addClass("navSpanfocus");
                        }else if(tabArr.length>3){
                            tabArr.push(tabObj);
                            tabArr.remove(tabArr[0])
                            sessionStorage.setItem("tabHistory", JSON.stringify(tabArr));
                            $(".navSpan").remove();
                            for(var i=0;i<tabArr.length;i++){
                                if (null==tabArr[i].datalocale){
                                    $('<span class="navSpan">' +
                                        '<a href="'+tabArr[i].url+'" ><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i>'+tabArr[i].text+'</a>' +
                                        '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));

                                }else {
                                    $('<span class="navSpan">' +
                                        '<a href="'+tabArr[i].url+'" ><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i><span  data-locale="'+tabArr[i].datalocale+'">'+tabArr[i].text+'</span></a>' +
                                        '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));

                                }

                            };
                            $(".navTab").children("span:last-child").addClass("navSpanfocus");

                        }


                    }
                }


			}

        });
        $(".navSpanSY a").click(function(){
			$(".navSpan").removeClass("navSpanfocus");
			$(".navSpanSY").addClass("navSpanfocus")
		})

        $(".liNavChild").click(function(){
            var tabObj1=new Object();
            var str1=$(this).children().find("i").attr("class").split(" ")[2];
			var textValue1=$(this).children().text().trim().replace(/\s/g,"");
            var  datalocale1=$(this).children().find("span").attr("data-locale");
            var url1=$(this).children().attr("href");
            tabObj1.icon=str1;
            tabObj1.text=textValue1;
            tabObj1.datalocale=datalocale1;
            tabObj1.url=url1;
            if(tabArr==null||tabArr==""){
                tabArr.push(tabObj1);
                sessionStorage.setItem("tabHistory", JSON.stringify(tabArr));
                $(".navSpan").remove();
                for(var i=0;i<tabArr.length;i++){
                    if (null==tabArr[i].datalocale){
                        $('<span class="navSpan">' +
                            '<a href="'+tabArr[i].url+'" ><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i>'+tabArr[i].text+'</a>' +
                            '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));

                    }else {
                        $('<span class="navSpan">' +
                            '<a href="'+tabArr[i].url+'" ><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i><span  data-locale="'+tabArr[i].datalocale+'">'+tabArr[i].text+'</span></a>' +
                            '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));

                    }

                };
                // typeChange(qCode);
                $(".navTab").children("span:last-child").addClass("navSpanfocus");
            }else{
                var a2 = 0 ;
                var childNum2;
                for(var i=0;i<tabArr.length;i++) {
                    if (url1==tabArr[i].url ) {
                        a2++;
                        childNum2=i+2
                        location.href=tabArr[i].url;
                        $(".navSpan").removeClass("navSpanfocus");
                        $(".navTab").children(":nth-child("+childNum2+")").addClass("navSpanfocus");
                    }
                }
                if(a2==0){
                    if(tabArr.length<=3){
                        tabArr.push(tabObj1);
                        sessionStorage.setItem("tabHistory", JSON.stringify(tabArr));
                        $(".navSpan").remove();
                        for(var i=0;i<tabArr.length;i++){
                            if (null==tabArr[i].datalocale){
                                $('<span class="navSpan">' +
                                    '<a href="'+tabArr[i].url+'" ><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i>'+tabArr[i].text+'</a>' +
                                    '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
                            }else {
                                $('<span class="navSpan">' +
                                    '<a href="'+tabArr[i].url+'" ><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i><span  data-locale="'+tabArr[i].datalocale+'">'+tabArr[i].text+'</span></a>' +
                                    '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
							}

                        }
                        $(".navTab").children("span:last-child").addClass("navSpanfocus");
                    }else if(tabArr.length>3){
                        tabArr.push(tabObj1);
                        tabArr.remove(tabArr[0])
                        sessionStorage.setItem("tabHistory", JSON.stringify(tabArr));
                        $(".navSpan").remove();
                        for(var i=0;i<tabArr.length;i++){
                            if (null==tabArr[i].datalocale){
                                $('<span class="navSpan">' +
                                    '<a href="'+tabArr[i].url+'" ><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i>'+tabArr[i].text+'</a>' +
                                    '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
                            }else {
                                $('<span class="navSpan">' +
                                    '<a href="'+tabArr[i].url+'" ><i class="fa '+tabArr[i].icon+'" style="margin-right: 5px;"></i><span  data-locale="'+tabArr[i].datalocale+'">'+tabArr[i].text+'</span></a>' +
                                    '<i class="fa fa-close closeIcon" style="display: none;margin-left: 5px;width: 5px"></i></span>\n').appendTo($(".navTab"));
							}

                        }
                        $(".navTab").children("span:last-child").addClass("navSpanfocus");

                    }


                }
            }



        })
        $(".navSpan").livequery("mouseenter",function(){
		    $(this).find(".closeIcon").show();
		    $(this).find("a").css({
                "text-decoration":"none"
			})
        }).livequery("mouseleave",function () {
            $(this).find(".closeIcon").hide();
            $(this).removeAttr("style")
        });
    //删除标签
        $(".closeIcon").livequery("click",function(){
            $(this).parent().remove();
            var urlSpan=$(this).prev().attr("href");

            for(var i=0;i<tabArr.length;i++){
                if(tabArr[i].url==urlSpan){
                    tabArr.remove(tabArr[i]);
                    if(i==tabArr.length){
                        $(".navSpan").removeClass("navSpanfocus");
                        $(".navTab").children("span:last-child").addClass("navSpanfocus");
                        var hrefValue=$(".navTab").children("span:last-child").find("a").attr("href");
                         location.href=hrefValue;

                    }else{
                        $(".navSpan").removeClass("navSpanfocus");
                        $(".navTab").children(":nth-child("+(i+2)+")").addClass("navSpanfocus");
                        var hrefValue=$(".navTab").children(":nth-child("+(i+2)+")").find("a").attr("href");
                        location.href=hrefValue;
                    }

                }
            }
            sessionStorage.setItem("tabHistory", JSON.stringify(tabArr));
        })
        $(".navSpan").find("a").livequery("click",function(){
           $(".navSpan").removeClass("navSpanfocus");
           $(this).parent().addClass("navSpanfocus");
           $(".navSpanSY").removeClass("navSpanfocus");

        })



	</script>
	

	
<%-- 	<link href="${ctxStatic}/contabs/style.css" rel="stylesheet" />
	<script src="${ctxStatic}/contabs/contabs.js"></script> --%>
	
	
<%-- 	<script src="${ctxStatic}/assets/js/bootstrap-tab.js"></script> --%>
<%-- 	<script src="${ctxStatic}/contabs/js/bootstrap-tab.js"></script> --%>
<%-- 	<script src="${ctxStatic}/contabs/js/sidebar-menu.js"></script> --%>
<%-- 	<link href="${ctxStatic}/contabs/js/bootstrap-tab.css" rel="stylesheet" />  --%>
	
<%--     <script src="${ctxStatic}/content/sidebar-menu/sidebar-menu.js"></script> --%>
<%--     <script src="${ctxStatic}/content/bootstrap/js/bootstrap-tab.js"></script> --%>
<%--     <link href="${ctxStatic}/content/sidebar-menu/sidebar-menu.css" rel="stylesheet" /> --%>

<%--      <link href="${ctxStatic}/content/bootstrap/css/bootstrap-tab.css" rel="stylesheet"/> --%>
<%--       <script src="${ctxStatic}/content/jquery-1.9.1.min.js"></script> --%>
<%--     <script src="${ctxStatic}/content/bootstrap/js/bootstrap.min.js"></script> --%>
<%--      <link href="${ctxStatic}/content/bootstrap/css/bootstrap.min.css" rel="stylesheet" /> --%>
     	
	<%-- <script src="${ctxStatic}/assets/js/ajaxfileupload.js"></script> --%>
	
	<script type="text/javascript">
		$(document).ready(function() {
            var qCode='${qCode}';
            if(qCode == ""){
                qCode = '0'
            }
            changeLanguageKey(qCode);
            typeChange(qCode);

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
            var isFull = false;
			$(".J_tabFullScreen").on("click",function(){
				var el = document.documentElement;

				if(!isFull)
				{
                    fullScreen(el);
                    isFull = true;
                    $('.J_tabFullScreen').attr('title','退出全屏');
				}else{
                    exitFullScreen();
                    isFull = false;
                    $('.J_tabFullScreen').attr('title','全屏');

				}

			});

			// ESC 按键监听
			window.onresize = function(){
				if(!IsFull()){
					exitFullScreen();
                    isFull = false;
                    $('.J_tabFullScreen').attr('title','全屏');
				}
			}
			
			/* 全屏启动方法 */
			function fullScreen(el) {
				var rfs = el.requestFullScreen || el.webkitRequestFullScreen ||
	            el.mozRequestFullScreen || el.msRequestFullScreen;
		        if (rfs) { //typeof rfs != "undefined" && rfs
		            rfs.call(el);
		        } else if (typeof window.ActiveXObject != "undefined") {
		            //for IE，这里其实就是模拟了按下键盘的F11，使浏览器全屏
		            var wscript = new ActiveXObject("WScript.Shell");
		            if (wscript != null) {
		                wscript.SendKeys("{F11}");
		            }
		        }

			  }

			// 退出全屏功能
			  function exitFullScreen() {
				    var el = document;
			        var cfs = el.cancelFullScreen || el.webkitCancelFullScreen ||
			            el.mozCancelFullScreen || el.exitFullScreen;
			        if (cfs) { //typeof cfs != "undefined" && cfs
			            cfs.call(el);
			        } else if (typeof window.ActiveXObject != "undefined") {
			            //for IE，这里和fullScreen相同，模拟按下F11键退出全屏
			            var wscript = new ActiveXObject("WScript.Shell");
			            if (wscript != null) {
			                wscript.SendKeys("{F11}");
			            }
			        }
			  } 
			
			  function IsFull() {
				    var fullscreenElement =
				        document.fullscreenEnabled
				        || document.mozFullscreenElement
				        || document.webkitFullscreenElement;
				    var fullscreenEnabled =
				        document.fullscreenEnabled
				        || document.mozFullscreenEnabled
				        || document.webkitFullscreenEnabled;
				    if (fullscreenElement == null)
				    {
				        return false;
				    } else {
				        return true;
				    }
				}
        });
        function httpPostLocationUrl (url,params) {
            var form = $("<form method='post' style='display:none'></form>");
            if(!params)params ={};
            if(url.indexOf('?')!=-1){
                var paramsStr = url.split("?")[1].split('&');
                for (var i = 0 ;i<paramsStr.length;i++){
                    if(paramsStr[i]&&paramsStr[i].indexOf("=")!=-1){
                        var data =  paramsStr[i].split('=');
                        params[data[0]] = data[1];
                    }
                }
                url = url.split("?")[0];
            }
            form.attr({"action": url});
            if(!$.isEmptyObject(params)){
                for (arg in params ) {
                    var input = $("<input type='hidden'>");
                    input.attr({"name": arg});
                    input.val(params[arg]);
                    form.append(input);
                }
            }
            $("html").append(form); //兼容火狐
            form.submit();
        }

		function  changeLanguageKey(key) {
            sessionStorage.setItem("qCode", key);
            if(key === '0'){
                $('.toEnglish').show();
                $('.toChinese').hide();
            }else if(key === '1'){
                $('.toEnglish').hide();
                $('.toChinese').show();
            }
        }
        var typeChange=function (qCode) {
            if (qCode=='1'){
                loadProperties('en');
            }else if (qCode=='0'){
                loadProperties('zh');
            }
        };
        function loadProperties(lang) {

            $.i18n.properties({
                name: 'strings',    //属性文件名     命名格式： 文件名_国家代号.properties
                path: '${ctxStatic}/i18n/properties/',   //注意这里路径是你属性文件的所在文件夹
                mode: 'map',
                language: lang,     //这就是国家代号 name+language刚好组成属性文件名：strings+zh -> strings_zh.properties
                callback: function () {
                    $("[data-locale]").each(function () {
                        // console.log($(this).data("locale"))
                        $(this).html($.i18n.prop($(this).data("locale")));

                    });
                }
            });
        }
        function yw() {
            //
            $.post("${ctx}/sys/user/updateLanguage", {"qCode":"1"}, function (result) {
                if (result.messageStatus == "1") {
                    loadProperties('en');
                } else if (result.messageStatus == "0") {
                    $.msg_show.Init({
                        'msg': result.message,
                        'type': 'error'
                    });
                }
            });
            changeLanguageKey('1')
        }
        function zw() {
            $.post("${ctx}/sys/user/updateLanguage", {"qCode":"0"}, function (result) {
                if (result.messageStatus == "1") {
                    loadProperties('zh');
                } else if (result.messageStatus == "0") {
                    $.msg_show.Init({
                        'msg': result.message,
                        'type': 'error'
                    });
                }
            });
            changeLanguageKey('0')
        }
	</script>	
		
</body>
</html>