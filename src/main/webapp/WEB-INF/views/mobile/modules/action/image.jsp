<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<section id="image_section" data-id="${wprId}" >
	<header>
		<h1 class="title">工序图纸</h1>
		<nav class="left">
            <a href="#" id="image-back" data-icon="previous" >返回</a>
        </nav>
	</header>
	<article class="active" data-scroll="true">
		<div>
			<div id="imageslider" class="slider">
			    <div>
				    <c:forEach items="${wpDrawingList}" var="wpd" >    
				    	<c:forEach items="${wpd.childFileList}" var="cf" >    
				    		<div style="text-align: center" data-name="${wpd.code}" ><img src="${ctxUploads}/action/drawing/${cf}" height="100%" width="100%"/></div>
				    	</c:forEach>
				    </c:forEach>
			    </div>
			</div>
		</div>
	</article>
	<script type="text/javascript">
		
	</script>
</section>