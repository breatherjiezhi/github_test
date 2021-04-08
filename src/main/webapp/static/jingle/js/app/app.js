document.addEventListener('deviceready', onDeviceReady, false);
String.prototype.startsWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
		return false;
	if(this.substr(0,str.length)==str)
		return true;
	else
		return false;
	return true;
}
function onDeviceReady(){
    navigator.splashscreen.hide();
    //注册后退按钮
    document.addEventListener("backbutton", function (e) {
        if(J.hasMenuOpen){
            J.Menu.hide();
        }else if(J.hasPopupOpen){
            J.closePopup();
        }else{
            var sectionId = $('section.active').attr('id');
            if(sectionId == 'index_section'){
                J.confirm('提示','是否退出程序？',function(){
                    navigator.app.exitApp();
                });
            }else{
                window.history.go(-1);
            }
        }
    }, false);
}
var App = (function(){
    var pages = {};
    var run = function(){
        $.each(pages,function(k,v){
            var sectionId = '#'+k+'_section';
            $('body').delegate(sectionId,'pageinit',function(){
                v.init && v.init.call(v);
            });
            $('body').delegate(sectionId,'pageshow',function(e,isBack){
                //页面加载的时候都会执行
                v.show && v.show.call(v);
                //后退时不执行
                if( v.load){
                    v.load.call(v);
                }
            });
        });
		J.Transition.add('flip','slideLeftOut','flipOut','slideRightOut','flipIn');
        Jingle.launch({
            showWelcome : false,
            showPageLoading : true,
            remotePage : {
            	'#index_section': ctx,
            	'#user_section': ctx + '/sys/user',
            	'#workprocess_section': ctx + '/mobile/action/wp',
            	'#actiondetail_section': ctx + '/mobile/action/wp/actionDetail',
            	'#image_section': ctx + '/mobile/action/wp/actionImage',
            	'#avatar_section': ctx + '/mobile/action/wp/userDetail'
            }
        });
       
    };
    var page = function(id,factory){
        return ((id && factory)?_addPage:_getPage).call(this,id,factory);
    }
    var _addPage = function(id,factory){
        pages[id] = new factory();
    };
    var _getPage = function(id){
        return pages[id];
    }
    //动态计算chart canvas的高度，宽度，以适配终端界面
    var calcChartOffset = function(){
        return {
            height : $(document).height() - 44 - 30 -60,
            width : $(document).width()
        }

    }
    return {
        run : run,
        page : page,
        calcChartOffset : calcChartOffset
    }
}());
App.page('index',function(){
	this.load = function(){
		if(getCookie('login')==0){
			setTimeout(function(){
				J.Router.goTo('#login_section');
			},500)
	   	}
    }
});
App.page('workprocess',function(){
	this.load = function(){
		if(getCookie('login')==0){
			setTimeout(function(){
				J.Router.goTo('#login_section');
			},500)
	   	}
    }
	this.show = function(){
		if(getCookie('login')==0){
			setTimeout(function(){
				J.Router.goTo('#login_section');
			},500)
	   		
	   	}
    }
});
function getCookie(c_name){
	if(document.cookie.length>0){
		c_start=document.cookie.indexOf(c_name + "=")
		if (c_start!=-1){ 
			c_start=c_start + c_name.length+1 
			c_end=document.cookie.indexOf(";",c_start)
			if (c_end==-1) c_end=document.cookie.length
			return unescape(document.cookie.substring(c_start,c_end))
		} 
	}
	return "";
}
//function getCookie(name){
//	var re;
//	//获取cookie字符串 
//	var strCookie=document.cookie; 
//	//将多cookie切割为多个名/值对 
//	var arrCookie=strCookie.split(";"); 
//	//遍历cookie数组，处理每个cookie对 
//	for(var i=0;i<arrCookie.length;i++){ 
//		var arr=arrCookie[i].split("="); 
//		//找到名称为userId的cookie，并返回它的值 
//		if(name==arr[0]){ 
//			re=arr[1]; 
//			break; 
//		} 
//	} 
//	return re;
//}
App.page('actiondetail',function(){
    this.init = function(){
    	
    }
    this.load=function(){
    	if(getCookie('login')==0){
    		setTimeout(function(){
				J.Router.goTo('#login_section');
			},500)
	   	}
    }
    this.show=function(){
    	var wprId = $('#actiondetail_section').attr('data-id');
    	//检测进度条
        var processBar=$('#progressBar');
        var process=processBar.attr('data-progress');
        if(process==0){
        	processBar.text("0%");
        }
        //图片显示
        $('#myslider>div div').tap(function(){
        	var img=$(this).children().first();
        	var img_src=img.attr('src');
        	var content="<div style='text-align: center;'><img src='"+img_src+"' style='height:85%;'/></div>";
        	//console.log(content);
        	J.popup({
                html: content,
                pos : 'center'
            })
        })
        //内容中按钮
//        $('#actiondetail_section article div ol li button').tap(function(){
        $('.content button').tap(function(){
        	var buttonId=$(this).attr('id');
        	var arrayId=new Array();
        	arrayId=buttonId.split("_");
//        	console.log('id--'+arrayId[1]+'  type--'+arrayId[0]);
        	$.post(ctx + '/mobile/action/wp/resourceDetail', {'id':arrayId[1],'type':arrayId[0]},function(data){
        		if(data.status==1){
        			var content;//输出对应html信息
        			if(arrayId[0]=="action"){
        				content=getActionHtmlContent(arrayId[1],data.target,data.upload);
        			}else{
        				content=getHtmlContent(arrayId[0],data.target);
        			}
                	J.popup({
                        html: content,
                        pos : 'center'
                    })
        		}else{
        			J.Toast.show('error',data.msg,2000);
        			return;
        		}
        	});
        	
        });
        //弹出图纸
        $('div.file').tap(function(){
        	var wpr=$('#actiondetail_section').attr('data-id');
        	var date=new Date();
        	J.Router.goTo("#image_section?wpr="+wpr+"&t="+date.getTime());

        });
        //点击下一步
        $('#nextstep').tap(function(){
        	J.confirm('','<center>确定完成，开始下一步？</center>',function(){
	        	var oH1=$("#actiondetail_section>header h1").first();
	        	var groupName=oH1.text();
	        	var gid=parseInt(groupName);
	        	console.log('wprId:'+wprId+"----"+'gid:'+gid);
	        	$.post(ctx + '/mobile/action/wp/endData', {'wprId':wprId,'gid':gid},function(data){
	        		console.log(data);
	        		if(data.status==1){
	        			J.Toast.show('success',data.msg,1000);
	        			J.Router.goTo(data.url);
	        		}else{
	        			if(data.url != ''){
	        				var date=new Date();
	        				J.Router.goTo("#workprocess_section?t="+date.getTime());
	        			}
	        			J.Toast.show('error',data.msg,1000);
	        		}	
	        	})
        	})
        })
        //--最后--初始化图片滑动
        var slider;
        slider = new J.Slider('#myslider', false);
    }
    
});
App.page('image',function(){
	this.load = function(){
		if(getCookie('login')==0){
			setTimeout(function(){
				J.Router.goTo('#login_section');
			},500)
	   	}
    }
	this.init=function(){
		var slider;
        $('#image_section article').on('articleshow',function(){
            slider = new J.Slider('#imageslider', true);
        });
	}
	this.show=function(){
        //图片放大   使用pinchzoom插件
        $(function () {
            $('#imageslider>div div').each(function () {
                new RTP.PinchZoom($(this), {});
            });
        })    
		$('#image-back').tap(function(){
			var wpr=$('#image_section').attr('data-id');
			var date=new Date();
			J.Router.goTo('#actiondetail_section?wprId='+wpr+'&t='+date.getTime());
			console.log('#actiondetail_section?wprId='+wpr+'&t='+date.getTime());
		});
    }
});
App.page('avatar',function(){
	this.init = function(){
    	
    }
	this.show=function(){
		
	}
});
function getHtmlContent(type,target){
	var content;
	switch(type){
	case 'user':
		var worktype="";//工种
		for( var item in target.workTypeList){
			worktype+= target.workTypeList[item].workname+" ";
		}
		if(target.operDate!=null){
			var operDate= new Date(target.operDate);
			var date=operDate.getFullYear()+"-"+(operDate.getMonth()+1)+"-"+operDate.getDate();
		}else date='/';
	content=
		"<div class='grid mypopup-grid'>"+
            "<div class='col-1'>"+
            	"<div class='col-1'>姓名</div>"+
            	"<div class='col-2'>工号</div>"+
            	"<div class='col-3'>部门</div>"+
            	"<div class='col-4'>工种</div>"+
            	"<div class='col-5'>上岗证编号</div>"+
            	"<div class='col-6'>上岗证有效期 </div>"+
            	"<div class='col-7'>级别 </div>"+
            "</div>"+
            "<div class='col-2'>"+
	            "<div class='col-1'>"+target.userName+"</div>"+
	        	"<div class='col-2'>"+target.userNo+"</div>"+
	        	"<div class='col-3'>"+target.officeName+"</div>"+
	        	"<div class='col-4'>"+worktype+"</div>"+
	        	"<div class='col-5'>"+target.operNo+"</div>"+
	        	"<div class='col-6'>"+date+" </div>"+
	        	"<div class='col-7'>"+"/"+" </div>"+
            "</div>"+
        "</div>";
		break;
	case 'tool':
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>工具名称</div>"+
	            	"<div class='col-2'>工具编码 </div>"+
	            	"<div class='col-3'>型号</div>"+
	            	"<div class='col-4'>单位</div>"+
	            	"<div class='col-5'> 类别</div>"+
	            	"<div class='col-6'>级别 </div>"+
	            	"<div class='col-7'>测量范围 </div>"+
	            	"<div class='col-8'>精度 </div>"+
	            	"<div class='col-9'>使用说明书 </div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.model)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.unit)+"</div>"+
		        	"<div class='col-5'>"+judgePra(target.type)+"</div>"+
		        	"<div class='col-6'>"+judgePra(target.grade)+" </div>"+
		        	"<div class='col-7'>"+judgePra(target.range)+" </div>"+
		        	"<div class='col-8'>"+judgePra(target.precision)+" </div>"+
		        	"<div class='col-9'>"+judgePra(target.manual)+" </div>"+
	            "</div>"+
	        "</div>";
		break;
	case 'device':
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>设备名称</div>"+
	            	"<div class='col-2'>设备编码 </div>"+
	            	"<div class='col-3'>设备型号</div>"+
	            	"<div class='col-4'>设备用途</div>"+
	            	"<div class='col-5'> 单位</div>"+
	            	"<div class='col-6'>设备参数 </div>"+
	            	"<div class='col-7'>设备等级 </div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.model)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.purpose)+"</div>"+
		        	"<div class='col-5'>"+judgePra(target.unit)+"</div>"+
		        	"<div class='col-6'>"+judgePra(target.param)+" </div>"+
		        	"<div class='col-7'>"+judgePra(target.grade)+" </div>"+

	            "</div>"+
	        "</div>";
		break;
	case 'frock':
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>工装名称</div>"+
	            	"<div class='col-2'>工装编码 </div>"+
	            	"<div class='col-3'>工装型号</div>"+
	            	"<div class='col-4'>工装分类</div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.model)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.type)+"</div>"+
	            "</div>"+
	        "</div>";
		break;
//	case 'action':
//		content=
//			"<div class='grid mypopup-grid'>"+
//	            "<div class='col-1'>"+
//	            	"<div class='col-1'>工具名称</div>"+
//	            	"<div class='col-2'>工具编码 </div>"+
//	            	"<div class='col-3'>型号</div>"+
//	            	"<div class='col-4'>单位</div>"+
//	            	"<div class='col-5'> 类别</div>"+
//	            	"<div class='col-6'>级别 </div>"+
//	            	"<div class='col-7'>测量范围 </div>"+
//	            "</div>"+
//	            "<div class='col-2'>"+
//		            "<div class='col-1'>"+judgePra(target.name)+"</div>"+
//		        	"<div class='col-2'>"+judgePra(target.code)+"</div>"+
//		        	"<div class='col-3'>"+judgePra(target.model)+"</div>"+
//		        	"<div class='col-4'>"+judgePra(target.unit)+"</div>"+
//		        	"<div class='col-5'>"+judgePra(target.type)+"</div>"+
//		        	"<div class='col-6'>"+judgePra(target.grade)+" </div>"+
//		        	"<div class='col-7'>"+judgePra(target.range)+" </div>"+
//	            "</div>"+
//	        "</div>";
//		break;
	case 'm':
		if(target.measdoc!=null){
			var measdoc=target.measdoc.measname;
		}else measdoc="/";
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>物料名称</div>"+
	            	"<div class='col-2'>物料编码 </div>"+
	            	"<div class='col-3'>规格</div>"+
	            	"<div class='col-4'>型号</div>"+
	            	"<div class='col-5'> 单位</div>"+
	            	"<div class='col-6'>物料简称</div>"+
	            	"<div class='col-7'>物料分类 </div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+judgePra(target.invname)+"</div>"+
		        	"<div class='col-2'>"+judgePra(target.invcode)+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.invspec)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.invtype)+"</div>"+
		        	"<div class='col-5'>"+measdoc+"</div>"+
		        	"<div class='col-6'>"+judgePra(target.invshortname)+" </div>"+
		        	"<div class='col-7'>"+judgePra(target.forinvname)+" </div>"+
	            "</div>"+
	        "</div>";
		break;
	default:break;
	}
	return content;
}
//判断参数值是否存在
function judgePra(pra){
	if(pra!=null) return pra;
	else return "/";
}
//动作显示的信息
function getActionHtmlContent(id,target,ctx){
	console.log(ctx);
	var content;
	if(id.startsWith("ACT8001005")){//紧固
		content=
		"<div class='grid mypopup-grid'>"+
            "<div class='col-1'>"+
            	"<div class='col-1'>动作名称</div>"+
            	"<div class='col-2'>动作编码 </div>"+
            	"<div class='col-3'>动作描述</div>"+
            	"<div class='col-4'>紧固类型 </div>"+
            "</div>"+
            "<div class='col-2'>"+
	            "<div class='col-1'>"+target.name+"</div>"+
	        	"<div class='col-2'>"+target.code+"</div>"+
	        	"<div class='col-3'>"+judgePra(target.describe)+"</div>"+
	        	"<div class='col-4'>"+judgePra(target.type)+" </div>"+
            "</div>"+
        "</div>";
	}else if(id.startsWith("ACT8003")){//焊接
		var wpsId,fileTwo;
		//单独处理WPSid的文档
		if(target.file1==null){
			wpsId='/';
		}else{
			wpsId="<a class='wpsIdDoc' href='"+ctx+"/uploads/action/reference/"+target.file1+".pdf' target='_blank'style='color:green;text-decoration:underline'>"+target.file1+"</a>";
		}
		if(target.file2==null){
			fileTwo='/';
		}else{
			fileTwo="<a class='wpsIdDoc' href='"+ctx+"/uploads/action/reference/"+target.file2+".pdf' target='_blank'style='color:green;text-decoration:underline'>"+target.file2+"</a>";
		}
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>动作名称</div>"+
	            	"<div class='col-2'>动作编码 </div>"+
	            	"<div class='col-3'>动作描述</div>"+
	            	"<div class='col-4'>所用WPS编号 </div>"+
	            	"<div class='col-5'>理化报告 </div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.describe)+"</div>"+
		        	"<div class='col-4'>"+wpsId+"</div>"+
		        	"<div class='col-5'>"+fileTwo+"</div>"+
	            "</div>"+
	        "</div>";
	}else if(id.startsWith("ACT8002001")){//连接
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>动作名称</div>"+
	            	"<div class='col-2'>动作编码 </div>"+
	            	"<div class='col-3'>动作描述</div>"+
	            	"<div class='col-4'>紧固类型 </div>"+
	            	"<div class='col-5'>扭矩 </div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.describe)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.type)+" </div>"+
		        	"<div class='col-5'>"+judgePra(target.request)+" </div>"+
	            "</div>"+
	        "</div>";
	}else if(id.startsWith("ACT8007")){//打磨
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>动作名称</div>"+
	            	"<div class='col-2'>动作编码 </div>"+
	            	"<div class='col-3'>动作描述</div>"+
	            	"<div class='col-4'>有效时长</div>"+
	            	"<div class='col-5'>注意事项</div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.describe)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.noticeOther)+" </div>"+
		        	"<div class='col-5'>"+judgePra(target.notice)+" </div>"+
	            "</div>"+
	        "</div>";
	}else if(id.startsWith("ACT8001009")){//标注
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>动作名称</div>"+
	            	"<div class='col-2'>动作编码 </div>"+
	            	"<div class='col-3'>动作描述</div>"+
	            	"<div class='col-4'>参考标准或规范</div>"+
	            	"<div class='col-5'>类型</div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.describe)+"</div>"+
		        	"<div class='col-4'>"+"/"+" </div>"+
		        	"<div class='col-5'>"+judgePra(target.type)+" </div>"+
	            "</div>"+
	        "</div>";
		
	}else if(id.startsWith("ACT8001004")){//擦拭
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>动作名称</div>"+
	            	"<div class='col-2'>动作编码 </div>"+
	            	"<div class='col-3'>动作描述</div>"+
	            	"<div class='col-4'>被擦拭物体材质</div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.describe)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.meterial1)+" </div>"+
	            "</div>"+
	        "</div>";
	}else if(id.startsWith("ACT6001011")){//防护
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>动作名称</div>"+
	            	"<div class='col-2'>动作编码 </div>"+
	            	"<div class='col-3'>动作描述</div>"+
	            	"<div class='col-4'>方式</div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.describe)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.method)+" </div>"+
	            "</div>"+
	        "</div>";
	}else if(id.startsWith("ACT4001001")){//检查
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>动作名称</div>"+
	            	"<div class='col-2'>动作编码 </div>"+
	            	"<div class='col-3'>动作描述</div>"+
	            	"<div class='col-4'>检查方法</div>"+
	            	"<div class='col-5'>检查目的</div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.describe)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.method)+"</div>"+
		        	"<div class='col-5'>"+judgePra(target.purpose)+" </div>"+
	            "</div>"+
	        "</div>";
		
	}else if(id.startsWith("ACT6001002")){//测量
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>动作名称</div>"+
	            	"<div class='col-2'>动作编码 </div>"+
	            	"<div class='col-3'>动作描述</div>"+
	            	"<div class='col-4'>测量类型</div>"+
	            	"<div class='col-5'>测量精度</div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.describe)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.type)+"</div>"+
		        	"<div class='col-5'>"+judgePra(target.request)+" </div>"+
	            "</div>"+
	        "</div>";
	}else if(id.startsWith("ACT8001006")){//压紧
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>动作名称</div>"+
	            	"<div class='col-2'>动作编码 </div>"+
	            	"<div class='col-3'>动作描述</div>"+
	            	"<div class='col-4'>压紧装置接触面材质要求</div>"+
	            	"<div class='col-5'>被压物体材质</div>"+
	            	"<div class='col-6'>位置要求 </div>"+
	            	"<div class='col-7'>注意事项</div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.describe)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.requestOther)+"</div>"+
		        	"<div class='col-5'>"+judgePra(target.meterial1)+" </div>"+
		        	"<div class='col-6'>"+judgePra(target.request)+" </div>"+
		        	"<div class='col-7'>"+judgePra(target.notice)+" </div>"+
	            "</div>"+
	        "</div>";
	}else {
		content=
			"<div class='grid mypopup-grid'>"+
	            "<div class='col-1'>"+
	            	"<div class='col-1'>动作名称</div>"+
	            	"<div class='col-2'>动作编码 </div>"+
	            	"<div class='col-3'>动作分类 </div>"+
	            	"<div class='col-4'>动作描述</div>"+
	            	"<div class='col-5'>动作版本</div>"+
	            	"<div class='col-6'>参考规范</div>"+
	            	"<div class='col-7'>温度 </div>"+
	            	"<div class='col-8'>湿度</div>"+
	            "</div>"+
	            "<div class='col-2'>"+
		            "<div class='col-1'>"+target.name+"</div>"+
		        	"<div class='col-2'>"+target.code+"</div>"+
		        	"<div class='col-3'>"+judgePra(target.classify)+"</div>"+
		        	"<div class='col-4'>"+judgePra(target.describe)+"</div>"+
		        	"<div class='col-5'>"+judgePra(target.version)+" </div>"+
		        	"<div class='col-6'>"+'/'+" </div>"+
		        	"<div class='col-7'>"+judgePra(target.temperature)+" </div>"+
		        	"<div class='col-8'>"+judgePra(target.humidity)+" </div>"+
	            "</div>"+
	        "</div>";
	}
	return content;
}

/*
App.page('index',function(){
    this.init = function(){
        $('#btn_show_welcome').on('tap', J.Welcome.show);
    }
})
App.page('calendar',function(){
    this.init = function(){
        new J.Calendar('#calendar_demo',{
            onRenderDay : function(day,date){
                if(day == 5){
                    return '<div>'+day+'</div><div style="font-size: .8em;color: red">威武</div>'
                }
                return day;
            },
            onSelect:function(date){
                alert(date);
            }
        });
        $('#btn_popup_calendar').tap(function(){
            J.popup({
                html : '<div id="popup_calendar"></div>',
                pos : 'center',
                backgroundOpacity : 0.4,
                showCloseBtn : false,
                onShow : function(){
                    new J.Calendar('#popup_calendar',{
                        date : new Date(2013,7,1),
                        onSelect:function(date){
                            $('#btn_popup_calendar').text(date);
                            J.closePopup();
                        }
                    });
                }
            });
        });
    }
});
App.page('refresh',function(){
    this.init = function(){
        J.Refresh({
            selector : '#down_refresh_article',
            type : 'pullDown',
            pullText : '你敢往下拉么...',
            releaseText : '什么时候你才愿意放手？',
            refreshTip : '最后一次拉的人：<span style="color:#e222a5">骚年</span>',
            callback : function(){
                var scroll = this;
                setTimeout(function () {
                    $('#down_refresh_article ul.list li').text('嗯哈，长大后我就成了你~');
                    scroll.refresh();
                    J.showToast('更新成功','success');
                }, 2000);
            }
        });
//    最简约的调用方式
        J.Refresh( '#up_refresh_article','pullUp', function(){
            var scroll = this;
            setTimeout(function () {
                var html = '';
                for(var i=0;i<10;i++){
                    html += '<li style="color:#E74C3C">我是被拉出来的...</li>'
                }
                $('#up_refresh_article ul.list').append(html);
                scroll.refresh();
                J.showToast('加载成功','success');
            }, 2000);
        })
    }
});
App.page('scroll',function(){
    this.init = function(){
        $('#h_scroll_article').on('articleshow',function(){
            J.Scroll('#h_scroll_demo',{hScroll:true,hScrollbar : false});
        })
    }
});
App.page('menu',function(){
    this.init = function(){
        $.get('html/custom_aside.html',function(aside){
            $('#aside_container').append(aside);
        })
    }
});
App.page('layout',function(){
    this.init = function(){
        $('#layout_header_ctrl').on('change',function(event,el){
            J.alert('提示','你点了'+$(el).text());
        })
        $('#layout-btn-getmore').tap(function(){
            J.popup({
                html: '<div style="height: 100px;line-height: 100px;font-size: 20px;font-weight: 600;text-align: center;">这里展示更多功能</div>',
                pos : 'bottom-second',
                showCloseBtn : false
            });
        })
    }
});
App.page('popup',function(){
    this.init = function(){
        $('#btn_alert').tap(function(){
            J.alert('提示','这是一个Alert');
        })
        $('#btn_confirm').tap(function(){
            J.confirm('提示','这是一个Confirm！',function(){J.showToast('你选择了“确定”')},function(){J.showToast('你选择了“取消”')});
        })
        $('#btn_loading').tap(function(){
            J.showMask();
        })
        $('#btn_center').tap(function(){
            J.popup({
                html: '<div style="height: 100px;text-align: center;font-size: 20px;font-weight: 600;margin-top: 10px;color:#E74C3C ">随意设计你的弹出框吧</div>',
                pos : 'center'
            })
        })
        $('#btn_from_tpl').tap(function(){
            J.popup({
                tplId : 'tpl_popup_login',
                pos : 'center'
            })
        })
        $('#btn_t_top').tap(function(){
            J.popup({
                html: '这是一个来自顶部的弹出框',
                pos : 'top',
                showCloseBtn : false
            })
        })
        $('#btn_t_ts').tap(function(){
            J.popup({
                html: '这是一个在header之下的弹出框',
                pos : 'top-second',
                showCloseBtn : false
            })
        })
        $('#btn_t_bottom').tap(function(){
            J.popup({
                html: '这是一个来自底部弹出框',
                pos : 'bottom',
                showCloseBtn : false
            })
        })
        $('#btn_t_bs').tap(function(){
            J.popup({
                html: '这是一个在footer之上的弹出框',
                pos : 'bottom-second',
                showCloseBtn : false
            })
        })
        $('#btn_popover').tap(function(){
            J.popover('<ul class="list"><li style="color:#000;">Hello Jingle</li><li style="color:#000;">你好，Jingle</li></ul>',{top:'50px',left:'10%',right:'10%'},'top');
        });
        $('#btn_actionsheet').tap(function(){
            J.Popup.actionsheet([{
                text : '告诉QQ好友',
                handler : function(){
                    J.showToast('告诉QQ好友！');
                }
            },{
                text : '告诉MSN好友',
                handler : function(){
                    J.showToast('告诉MSN好友！');
                }
            }
            ]);
        });
    }
});

App.page('toast',function(){
    this.init = function(){
        $('#btn_t_default').tap(function(){
            J.showToast('这是默认的Toast,默认3s后小时');
        })
        $('#btn_t_success').tap(function(){
            J.showToast('恭喜，success,5s后消失','success',5000);
        })
        $('#btn_t_error').tap(function(){
            J.showToast('抱歉，error','error');
        })
        $('#btn_t_info').tap(function(){
            J.showToast('提示，info','info');
        })
        $('#btn_t_top').tap(function(){
            J.showToast('更新了50条数据','toast top');
        })
    }
});
App.page('chart_line',function(){
    var line,$chart;
    this.init = function(){
        //重新设置canvas大小
        $chart = $('#line_canvas');
        var wh = App.calcChartOffset();
        $chart.attr('width',wh.width).attr('height',wh.height-30);

        renderLine();
        $('#changeLineType').on('change',function(e,el){
            updateChart(el.data('type'));
        })
    }

    function renderLine(){
        var data = {
            labels : ["一月","二月","三月","四月","五月","六月","七月",'八月','九月','十月','十一月','十二月'],
            datasets : [
                {
                    name : 'A产品',
                    color : "#72caed",
                    pointColor : "#95A5A6",
                    pointBorderColor : "#fff",
                    data : [65,59,90,81,56,55,40,20,13,20,11,60]
                },
                {
                    name : 'B产品',
                    color : "#a6d854",
                    pointColor : "#95A5A6",
                    pointBorderColor : "#fff",
                    data : [28,48,40,19,96,27,100,40,40,70,11,89]
                }
            ]
        }
        line = new JChart.Line(data,{
            id : 'line_canvas',
            smooth : false,
            fill : false
        });
        line.on('tap.point',function(d,i,j){
            J.alert(data.labels[i],d);
        });
        line.draw();
    }
    function updateChart(type){
        if(type == 'smooth'){
            line.refresh({
                smooth : true,
                fill : false
            });
        }else if(type == 'area'){
            line.refresh({
                smooth : true,
                fill : true
            });
        }else{
            line.refresh({
                smooth : false,
                fill : false
            });
        }

    }
});
App.page('chart_bar',function(){
    var $chart;
    this.init = function(){
        //重新设置canvas大小
        $chart = $('#bar_canvas');
        var wh = App.calcChartOffset();
        $chart.attr('width',wh.width).attr('height',wh.height);

        var data = {
            labels : ["一月","二月","三月","四月","五月","六月","七月",'八月','九月','十月','十一月','十二月'],
            datasets : [
                {
                    name : 'A产品',
                    color : "#72caed",
                    pointColor : "#95A5A6",
                    pointBorderColor : "#fff",
                    data : [65,59,90,81,56,55,40,20,13,20,11,60]
                },
                {
                    name : 'B产品',
                    color : "#a6d854",
                    pointColor : "#95A5A6",
                    pointBorderColor : "#fff",
                    data : [28,48,40,19,96,27,100,40,40,70,11,89]
                }
            ]
        }
        var bar = new JChart.Bar(data,{
            id : 'bar_canvas'
        });
        bar.on('tap.bar',function(d,i,j){
            J.alert(data.labels[i],d);
        });
        bar.on('longTap.bar',function(d,i,j){
            J.alert('提示',d+' = 按住750ms会出现此提示');
        });
        bar.draw();
    }
});
App.page('chart_pie',function(){
    var pie,$chart;
    this.init = function(){
        //重新设置canvas大小
        $chart = $('#pie_canvas');
        var wh = App.calcChartOffset();
        $chart.attr('width',wh.width).attr('height',wh.height-100);
        renderPie();
        $('#changePieType').on('change',function(e,el){
            updateChart(el.data('type'));
        })
    }

    function renderPie(){
        var pie_data = [
            {
                name : '直接访问',
                value: 335,
                color:"#F38630"
            },{
                name : '联盟广告',
                value : 234,
                color : "#E0E4CC"
            },{
                name : '视频广告',
                value : 135,
                color : "#72caed"
            },{
                name : '搜索引擎',
                value : 1400,
                color : "#a6d854"
            }
        ];
        pie = new JChart.Pie(pie_data,{
            id : 'pie_canvas',
            clickType : 'rotate'
        });
        pie.on('rotate',function(d,i,j){
            $('#pie_segment_info').html(d.name + '    '+ d.value).show();
        });
        pie.draw();
    }
    function updateChart(type){
        $('#pie_segment_info').hide();
        if(type == 'pie'){
            pie.refresh({
                isDount : false
            });
        }else if(type == 'dount'){
            pie.refresh({
                isDount : true,
                dountText : '访问来源'
            });
        }

    }
});
App.page('chart_drag',function(){
    var $lineChart,$barChart;
    this.init = function(){
        //重新设置canvas大小
        $lineChart = $('#chart_drag_line_canvas');
        $barChart = $('#chart_drag_bar_canvas');
        var wh = App.calcChartOffset();
        $lineChart.attr('width',wh.width).attr('height',wh.height-30);
        $barChart.attr('width',wh.width).attr('height',wh.height-30);
        var data = {
            labels : ["2012","二月","三月","四月","五月","六月","七月",'八月','九月','十月','十一月','十二月','2013',"二月","三月","四月","五月","六月","七月",'八月','九月','十月','十一月','十二月','2014','一月','二月'],
            datasets : [
                {
                    name : 'A产品',
                    color : "#72caed",
                    pointColor : "#95A5A6",
                    pointBorderColor : "#fff",
                    data : [65,59,90,81,56,55,40,20,13,20,11,60,65,59,90,81,56,55,40,20,11,20,10,60,11,60,65]
                },
                {
                    name : 'B产品',
                    color : "#a6d854",
                    pointColor : "#95A5A6",
                    pointBorderColor : "#fff",
                    data : [28,48,40,19,96,27,100,40,40,70,11,89,28,48,40,19,96,27,100,40,40,70,10,89,28,48,40]
                }
            ]
        }
        $('#changeDragChartType').on('change',function(e,el){
            renderChart(el.data('type'),data);
        })
        renderChart('line',data);
    }
    var renderChart = function(type,data){
        if(type == 'line'){
            $lineChart.show();
            $barChart.hide();
            new JChart.Line(data,{
                id : 'chart_drag_line_canvas',
                datasetGesture : true,
                datasetOffsetNumber : 10
            }).draw(true);
        }else{
            $lineChart.hide();
            $barChart.show();
            new JChart.Bar(data,{
                id : 'chart_drag_bar_canvas',
                datasetGesture : true,
                datasetOffsetNumber : 10
            }).draw(true);
        }
    }
});
App.page('chart_dynamic',function(){
    var pause = false,$chart;
    var datasets = [[65,59,90,81,56,55,40,20,3,20,10,60],[28,48,40,19,96,27,100,40,40,70,10,89]];
    var data = {
        labels : ["一月","二月","三月","四月","五月","六月","七月",'八月','九月','十月','十一月','十二月'],
        datasets : [
            {
                name : 'A产品',
                color : "#72caed",
                pointColor : "#95A5A6",
                pointBorderColor : "#fff",
                data : datasets[0]
            },
            {
                name : 'B产品',
                color : "#a6d854",
                pointColor : "#95A5A6",
                pointBorderColor : "#fff",
                data : datasets[1]
            }
        ]
    }

    this.init = function(){
        //重新设置canvas大小
        $chart = $('#dynamic_line_canvas');
        var wh = App.calcChartOffset();
        $chart.attr('width',wh.width).attr('height',wh.height-30);
        var line = new JChart.Line(data,{
            id : 'dynamic_line_canvas'
        });
        line.draw();
        refreshChart(line);
        $('#pause_dynamic_chart').on('tap',function(){
            pause = !pause;
            $(this).text(pause?'继续':'暂停');
        })
    }

    function refreshChart(chart){
        setTimeout(function(){
            if(!pause){
                datasets[0].shift();
                datasets[0].push(Math.floor(Math.random()*100));
                datasets[1].shift();
                datasets[1].push(Math.floor(Math.random()*100));
                chart.load(data);
            }
            refreshChart(chart);
        },1000);
    }
});
App.page('form',function(){
    this.init = function(){
        alert('init');
        $('#checkbox_1').on('change',function(){
            alert($(this).data('checkbox'));
        })
    }
})*/
$(function(){
    App.run();
})