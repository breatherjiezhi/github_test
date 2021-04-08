<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')}登录</title>
			<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript" src="${ctxStatic}/assets/js/1.js"></script>
	<style>
		.display{display: none;}
		a{text-decoration: NONE} 
		.denglu>h3>b{cursor: pointer;color:  #CA8EC2}
		.denglu>h3>b.active{color: #00c957
		}
body{
	overflow: hidden}
		.light-login {
			background: url(${pageContext.request.contextPath}/static/assets/images/bjWh.png) no-repeat;
			background-size: 100% 100%;
		}
		.login-container {
			float: right;
			margin: 20% 0 0 0;
		}
	</style>
</head>
<body class="login-layout light-login">
<div class="main-container">
	<%--<img src="${pageContext.request.contextPath}/static/assets/images/lolo.png" alt="" style="height:38px">--%>
<div class="main-content">
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
			<div class="space1"></div>
				<div class="space1"></div>
					<div class="login-container">
						<div class="center">
						<!-- 	<h1 class="header blue lighter bigger"> -->
							<h1>
								<%--<img alt="" src="${pageContext.request.contextPath}/static/images/login.png"/>	--%>
							</h1>
						</div>
						<div class="space1"></div>
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<div class="denglu" align="center">
												<h3  class="header blue lighter bigger" >
													<b class="1 active">用户登录</b>		
														<%--&nbsp;&nbsp;--%>
                                                    <%--<b class="1 ">刷卡登录</b>--%>
												</h3>
											</div>
											<div class="space-6"></div>
											<br>
											<form id="loginForm" action="${ctx}/login" method="post">
												<div class="change">
													<fieldset>						
															<label class="block clearfix">
																<span class="block input-icon input-icon-right">
																	<input type="text" id="username" name="username" class="form-control" placeholder="用户名"  />
																	<i class="ace-icon fa fa-user"></i>
																</span>
															</label>
													
															<label class="block clearfix">
																<span class="block input-icon input-icon-right">
<!-- 																	<input type="password" id="password" name="password" class="form-control" placeholder="密码" /> -->
																	
																	<input type="text" id="password" name="password" class="form-control" onfocus="this.type='password'" placeholder="密码" autocomplete="off"  onpaste="return false" oncopy="return false"/>
																	<i class="ace-icon fa fa-lock"></i>
										     					</span>
															</label>
															<br>
															<div  class="clearfix" align="center">
																<button  type="button" id="submitLogin" class="width-50 pull-center btn btn-sm btn-primary" data-loading-text="登录中...">
																	<i class="ace-icon fa fa-key"></i>
																	<span class="bigger-130">登&nbsp;&nbsp;录</span>
																</button>
															</div>
															<br> 
														</fieldset>												
											 		<%--<div class="display">	--%>
														<%--<div>	--%>
														<%--<label class="block clearfix">--%>
															<%--<span class="block input-icon input-icon-right" >--%>
																<%--<input type="text" id="cardID" name="cardID" class="form-control" placeholder="请输入您的卡号" onkeydown="fnKeyDown(event);" onfocus="this.select()" onclick="this.select()" onpaste="return false" ondrop="return false" ondragenter="return false;" ondragstart="return false" onkeypress="return keyDigt(event)" />--%>
																<%--<i class="ace-icon fa fa-user"></i>--%>
															<%--</span>--%>
														<%--</label>--%>
														<%--<br><br>--%>
													</div>
												</form>
											</div>
											<input id="massage" value="${message}" hidden ="true"/>
										</div>
									</div>
								</div>
							</div>
						</div>		
					</div>
				</div>	
			</div>
		</div>	
	</div>	
</div>	
<script>
		$(function(){
				jcPublic.register();
				jcPublic.Tab();
				
			});		
			var jcPublic = {
				register:function(){//注册
					var currentThis = this;//当前对象	
				},
				Tab:function(){//切换注册和登录
					$(".denglu>h3>b").on("click",function(){
						$(this).addClass("active").siblings().removeClass("active");
						var $index = $(this).index();//索引
					$(".change").children().eq($index).stop(true,false).show().siblings().hide();
					});
				},
				
			};
		setInterval("clickTuo2()",1000);
			function clickTuo2(){
			$("#cardID").val("");
			};
//		window.onload=function(){
//			$(".denglu>h3>b").click();
//		}
		//$(".denglu>h3>b").click;
		</script>
	
	<!--[if !IE]> -->
	<!-- <script type="text/javascript">
		window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery.js'>"+"<"+"/script>");
	</script> -->
	    <!-- <![endif]-->

	<!--[if IE]>
	<script type="text/javascript">
	 window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery1x.js'>"+"<"+"/script>");
	</script>
	<![endif]-->
	
	<script type="text/javascript">
	 window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery1x.js'>"+"<"+"/script>");
	</script>
   
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='${ctxStatic}/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
	</script>
	<script src="${ctxStatic}/assets/js/bootstrap.js"></script>
	
	<!--[if lte IE 8]>
	  <script src="${ctxStatic}/assets/js/excanvas.js"></script>
	<![endif]-->

	
	
	<script src="${ctxStatic}/assets/js/jqGrid/jquery.jqGrid.src.js"></script>
	<script src="${ctxStatic}/assets/js/jqGrid/i18n/grid.locale-cn.js"></script>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/assets/js/jquery.validate.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$(function(){
			$('body').attr('class', 'login-layout light-login');

			
	 });
		
			var lockFlag =false;
		$("input").focus();
		
			$('#btn-login-dark').on('click', function(e) {
				$('body').attr('class', 'login-layout');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-light').on('click', function(e) {
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-blur').on('click', function(e) {
				$('body').attr('class', 'login-layout blur-login');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'light-blue');
				
				e.preventDefault();
			 });
			
				
			$("#loginForm").validate({
						
					rules: {
							username: {
								required: true
							},
							password: {
								required: true
							}
					},
					messages: {
							username: {required: "请填写用户名."},password: {required: "请填写密码."}
					},
					errorClass: 'help-block',
					errorElement: 'div',
					highlight: function( element, errorClass, validClass ) {
						$(element).parent('span').removeClass('has-info').addClass('has-error');
					},
					success: function ( errElement, element ) {
						$(errElement).parent('span').removeClass('has-error');
						$(errElement).remove();
					},
				
				});
			$('#submitLogin').click(function(){
					
				$("#loginForm").submit();
				
			});
			
			var massage = $("#massage").val();
			if(massage != ""){
				alert(massage);
			}
			$("body").keydown(function(event) {
    			if (event.keyCode == "13") {//keyCode=13是回车键
        		$("#loginForm").submit();
			    }
			});   
		
			
		});
		
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</body>
</html>