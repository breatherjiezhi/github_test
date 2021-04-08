<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<section id="workprocess_section">
	<header>
		<h1 class="title">今日工作</h1>
		<nav class="left">
            <a id="back2index" data-icon="previous" >返回</a>
        </nav>
		<nav class="right">
			<div class="icon ellipsis"></div>
		</nav>
	</header>
	<article class="active" data-scroll="true">
		<div style="padding: 10px 0 20px;">
			<ul class="list">
				<c:forEach items="${list}" var="wp" >  
				<li data-icon="next" data="${wp.wp_runtime_id}">
					<strong>${wp.name}</strong><br>
					<div>
					<div class="grid ">
						<div class="col-1"><span>项目：${wp.work_target}</span></div>
						<div class="col-1"><span>车型：${wp.work_type_info}</span></div>
					</div>
					</div>
				</li>
				</c:forEach>
			</ul>
		</div>
	</article>
	<script type="text/javascript">	
		$('#workprocess_section>article li').tap(function(){
			$('#workprocess_section>article li').removeClass("active");
			$(this).addClass("active");
			J.confirm('','<center>是否开始该任务？</center>',function(){
				var wpr=$('#workprocess_section>article li.active').attr('data');
				console.log(wpr);
				var date=new Date();
				J.Router.goTo('#actiondetail_section?wprId='+wpr+'&t='+date.getTime());
			},function(){
				return false;
			});
		})
		$("#back2index").tap(function(){
			var date=new Date();
			J.Router.goTo('#index_section?index='+date.getTime());
		})
	</script>
</section>