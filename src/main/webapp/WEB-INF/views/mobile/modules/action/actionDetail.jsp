<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<section id="actiondetail_section" data-id="${wprId}" >
	<header>
		<h1 class="title">${group.name }</h1>
		<nav class="left">
            <!-- <div class="icon home-2" data-target="section" href="#index_section">首页</div> -->
            <a data-target="section" data-icon="home-2" href="#index_section?index">首页</a>
        </nav>
		<nav class="right">
            <div class="icon file ">图纸</div>&nbsp;
            <div class="icon file-2 ">附表</div>&nbsp;&nbsp;
        </nav>
	</header>
	<article class="active" data-scroll="true">
		<div>
			<div class="grid middle" style="padding: 2px;">
		        <div class="col-0" style="width: 200px;margin:5px;">
		            <div id="myslider" class="slider">
					    <div>
						    <c:forEach items="${photo}" var="po" >    
						    	<div style="text-align: center" data-name="${po.name}"><img src="${ctxUploads}/action/photo/${po.location}" height="260px" width="200px"/></div>
						    </c:forEach>
					    </div>
				    </div>
		        </div>
		        <div class="col-1" style="margin-left:20px;padding-right:6px">
			        <ol>
			        	<c:forEach items="${content}" var="ct" >    
						    <li class="content">${ct}
			        		</li>
						</c:forEach>
			        	
			        </ol>
		        </div>
			</div>
			<div>
				<c:if test="${video != null}">
					<c:forEach items="${video}" var="vo" > 
						<a class="button video" data-icon="icon camera-2" data="${vo.location}"  style="margin-left:5px">${vo.name}</a>
					</c:forEach>
				</c:if>
			</div>	
			<div class="grid" style="padding: 2px;">
				<div class="col-3">
					
					<div id="progressBar" data-progress="${progressBar}" style="width:500px;margin-top:6px;margin-left:2px"><div class="bar" style="width: ${progressBar}%;">${progressBar}%</div></div>
				</div>
				<div class="col-1">
					<a id="nextstep" class="button right" data-icon="next right"><i class="icon next right"></i>下一步</a>
				</div>
			</div>
		</div>
	</article>
	<script>
		//下载附表
        $('div.file-2').tap(function(){
        	J.confirm("","<center>你确定要下载本工序的BOM清单附表吗?</center>",function(){
	        	var list=${fns:toJson(wpBomList)};
	        	//console.log(list);
	        	for(var key in list){
	        		window.location.href="${ctxUploads}/action/bom/"+list[key];
	        	}
        	},function(){
        		return false;
        	});
        });
        var video=${fns:toJson(video)};
        console.log(video);
        //视频显示
        $("a.button.video").tap(function(){
        	var src="${ctxUploads}/action/photo/"+$(this).attr('data');
        	console.log(src);
        	var content=
        		"<video style='height:260px;width:100%' controls='controls' autoplay='autoplay'>"+
        			"<source src="+src+" />"+
        			"您的浏览器不支持 video 标签"+
        		"</video>";
        	J.popup({
                html: content,
                pos : 'center'
            })
        })
	</script>
</section>