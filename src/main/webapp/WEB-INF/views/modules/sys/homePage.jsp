<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" href="${ctxStatic}/modules/sys/css/style-homePage.css" type="text/css"/>
<title>首页</title>
<style>
    #todoTable tbody {
        display:block;
        height:calc(100vh - 245px);
        overflow-y:scroll;
    }

    thead,
    #todoTable tbody tr {
        display:table;
        width:100%;
        table-layout:fixed;
    }

    #todoTable thead {
        width: 100%;
    }
    #todoTable tbody tr {
        margin-top: -2px;
    }
    .scrollbar::-webkit-scrollbar {
        /*滚动条整体样式*/
        width: 0.04rem;
        /*高宽分别对应横竖滚动条的尺寸*/
        height: 0.04rem;
    }

    .scrollbar::-webkit-scrollbar-thumb {
        /*滚动条里面小方块*/
        border-radius: 5px;
        -webkit-box-shadow: inset 0 0 5px rgba(70, 27, 27, 0.2);
        background: #4381e6;
    }

    .scrollbar::-webkit-scrollbar-track {
        /*滚动条里面轨道*/
        -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
        border-radius: 0;
        background: #e9e9e9;
    }
</style>
<div id="homePage">
		<div id="personCard">
			<div class="width-100">
				<%--<span class="fa fa-check-circle green_circle_icon"></span>--%>
				<%--<span class="fa fa-trash cardDelete delete_icon"></span>--%>
				<%--<span class="fa fa-gear changePwd settings_icon"></span>--%>
			</div>
			<div class="width-100" style="text-align: center;margin-top:0.05rem;">
				<%--<img class="head_portrait" src="${ctxStatic}/assets/avatars/avatar5.png" alt="">--%>
				<c:if test="${not empty user.photo }">
					<img id="avatar" data-pk="13" data-placement="center" class="editable img-responsive head_portrait" alt="${user.name}" src="${ctxUploads}/userAvatar/1_large.jpg" onerror="this.src='${ctxStatic}/assets/avatars/avatar5.png'"/>
				</c:if>
				<c:if test="${empty user.photo}">
					<img id="avatar" data-pk="13" data-placement="center" class="editable img-responsive head_portrait" alt="${user.name}" src="${ctxStatic}/assets/avatars/avatar5.png" />
				</c:if>
			</div>
			<div class="width-100" style="text-align: center;">
				<div style="font-size: 0.2rem;line-height: 0.36rem;">
					<strong>${user.name}</strong>
					<%--<span class="fa fa-mars" style="color:#4d72dd;"></span>--%>
				</div>
				<%--<div style="font-size:0.14rem;">XIN MING</div>--%>
			</div>
			<div class="change_Pwd">
				<span id="changePassword" class="fa fa-gear" style="letter-spacing: 0.02rem;"> <span data-locale='ChangePassword'>修改密码</span></span>
			</div>
		</div>
		<div id="personInfo">
			<div class="personInfo_title" style="height: 0.6rem">
				<span data-locale="PersonalInformation">个人信息</span>
			</div>
			<div class="infoList">
				<div class="info_label">
					<span><i class="fa fa-user" style="color:#e18e42"></i> <span data-locale='UserName'>用户名</span>：</span>
				</div>
				<div class="info_content">
					<span>${user.loginName}</span>
				</div>
			</div>
			<div class="infoList">
				<div class="info_label">
					<span><i class="fa fa-star" style="color:#fa5a5a"></i> <span data-locale='No'>工号</span>：</span>
				</div>
				<div class="info_content">
					<span>${user.no}</span>
				</div>
			</div>
			<div class="infoList">
				<div class="info_label">
					<span><i class="fa fa-user" style="color:#4e73e0"></i> <span data-locale='UserIntegral'>剩余转换次数</span>：</span>
				</div>
				<div class="info_content">
					<span>
						<c:if test="${!empty user.userIntegral }">
						<fmt:formatNumber type="number" value="${user.userIntegral }" pattern="#" />
						</c:if>
						<c:if test="${empty user.userIntegral }">
							0
						</c:if>
					</span>
				</div>
			</div>
			<%--<div class="infoList">
				<div class="info_label">
					<span><i class="fa fa-user" style="color:#e18e42"></i> <span data-locale='userIntegral'>用户积分</span>：</span>
				</div>
				<div class="info_content">
					<span>${user.userIntegral}</span>
				</div>
			</div>--%>
			<%--<div class="infoList">
				<div class="info_label">
					<span><i class="fa fa-cogs" style="color:#19c865"></i> <span data-locale='Department'>所属部门</span>：</span>
				</div>
				<div class="info_content">
					<span>${user.office.name}</span>
				</div>
			</div>--%>
			<%--<div class="infoList">--%>
				<%--<div class="info_label">--%>
					<%--<span><i class="fa fa-suitcase" style="color:#e1d53a"></i> <span data-locale='SubordinateProject'>所属项目</span>：</span>--%>
				<%--</div>--%>
				<%--<div class="info_content">--%>
					<%--<span>--%>
						<%--<c:forEach items="${pbdProjectList}" var="item" varStatus="status">--%>
							<%--${item.projectName},--%>
						<%--</c:forEach>--%>
					<%--</span>--%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="infoList">
				<div class="info_label">
					<span><i class="fa fa-users" style="color:#eeaee3"></i> <span data-locale='roleNames'>用户角色</span>：</span>
				</div>
				<div class="info_content">
					<span>${user.roleNames}</span>
				</div>

			</div>
			<%--<div class="infoList">
				<div class="info_label">
					<span><i class="fa fa-user" style="color:#e18e42"></i> <span data-locale='userIntegral'>用户积分</span>：</span>
				</div>
				<div class="info_content">
					<span>${user.userIntegral}</span>
				</div>
			</div>--%>

		</div>
		<div id="myTodoList">
			<table id="todoTable">
				<thead>
				<tr style="background-color:#fafafa;">
					<th style="width:0.5rem;"></th>
					<th style="width:14.98%" data-locale="Project">项目</th>
					<th style="width:14.98%" data-locale="Number">编号</th>
					<th style="width:24.96%" data-locale="ToDoContent">待办内容</th>
					<th style="width:14.98%" data-locale="ToDoList">待办事项</th>
					<th style="width:14.98%" data-locale="createDate">创建时间</th>
					<th data-locale="Type">类型</th>
				</tr>
				</thead>
				<tbody id="todoTbody" class="scrollbar">

				</tbody>
			</table>
		</div>
</div>
<div id="changePwdDivId" style="display: none;"></div>
<script type="text/javascript">
	var scripts = [ null, null ];
	$('.page-content-area').ace_ajax('loadScripts',scripts,function() {
		jQuery(function($) {
            var qCode = sessionStorage.getItem("qCode");
            typeChange(qCode);
            
            //rem设置
            var htmlWidth = document.documentElement.clientWidth || document.body.clientWidth;
			//得到html的Dom元素
            var htmlDom = document.getElementsByTagName('html')[0];
			//设置根元素字体大小
            htmlDom.style.fontSize = htmlWidth / 20 + 'px';


            // 我的待办-表格数据加载
            $.ajax({
                type: "post",
                url: '${ctx}/pbdtodo/viewTodo/searchPage',
                datatype: 'json',
                data: {},
                success: function (result) {
                    for(var i=0; i<result.rows.length; i++){
                        var tr = $('<tr>\n' +
                            '\t\t\t\t\t<td style="width:0.4rem">'+(i+1)+'</td>\n' +
                            '\t\t\t\t\t<td style="width:15%">'+result.rows[i].projectName+'</td>\n' +
                            '\t\t\t\t\t<td style="width:15%">'+result.rows[i].taskCode+'</td>\n' +
                            '\t\t\t\t\t<td style="width:25%">'+result.rows[i].taskName+'</td>\n' +
                            '\t\t\t\t\t<td style="width:15%">'+result.rows[i].projectName+'</td>\n' +
                            '\t\t\t\t\t<td style="width:15%">'+result.rows[i].createDate+'</td>\n' +
                            '\t\t\t\t\t<td>'+result.rows[i].typeName+'</td>\n' +
                            '\t\t\t\t</tr>')
						$("#todoTbody").append(tr);
                    }
                }
            });


            // 上传头像
            $.fn.editableform.loading = "<div class='editableform-loading'><i class='ace-icon fa fa-spinner fa-spin fa-2x light-blue'></i></div>";
            $.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="ace-icon fa fa-check"></i></button>'+
                '<button type="button" class="btn editable-cancel"><i class="ace-icon fa fa-times"></i></button>';
            try {//ie8 throws some harmless exceptions, so let's catch'em

                //first let's add a fake appendChild method for Image element for browsers that have a problem with this
                //because editable plugin calls appendChild, and it causes errors on IE
                try {
                    document.createElement('IMG').appendChild(document.createElement('B'));
                } catch(e) {
                    Image.prototype.appendChild = function(el){}
                }

                var last_gritter
                $('#avatar').editable({
                    type: 'image',
                    name: 'avatar',
                    value: null,
                    image: {
                        //specify ace file input plugin's options here
                        btn_choose: '修改头像',
                        droppable: true,
                        maxSize: 2048000,//~2MB

                        //and a few extra ones here
                        name: 'avatar',//put the field name here as well, will be used inside the custom plugin
                        on_error : function(error_type) {//on_error function will be called when the selected file has a problem
                            if(last_gritter) $.gritter.remove(last_gritter);
                            if(error_type == 1) {//file format error
                                last_gritter = $.gritter.add({
                                    title: '您选择的不是图片文件！',
                                    text: '请选择一张 jpg 或者 gif 或者 png 格式的图片!',
                                    class_name: 'gritter-error gritter-center'
                                });
                            } else if(error_type == 2) {//file size rror
                                last_gritter = $.gritter.add({
                                    title: '文件太大!',
                                    text: '文件大小不能超过 2MB!',
                                    class_name: 'gritter-error gritter-center'
                                });
                            }
                            else {//other error
                            }
                        },
                        on_success : function() {
                            $.gritter.removeAll();
                        }
                    },
                    url: function(params) {
                        // ***UPDATE AVATAR HERE*** //
                        var submit_url = '${ctx}/sys/user/saveAvatar';//please modify submit_url accordingly
                        var deferred = null;
                        var avatar = '#avatar';

                        //if value is empty (""), it means no valid files were selected
                        //but it may still be submitted by x-editable plugin
                        //because "" (empty string) is different from previous non-empty value whatever it was
                        //so we return just here to prevent problems
                        var value = $(avatar).next().find('input[type=hidden]:eq(0)').val();
                        if(!value || value.length == 0) {
                            deferred = new $.Deferred
                            deferred.resolve();
                            return deferred.promise();
                        }

                        var $form = $(avatar).next().find('.editableform:eq(0)')
                        var file_input = $form.find('input[type=file]:eq(0)');
                        var pk = $(avatar).attr('data-pk');//primary key to be sent to server

                        var ie_timeout = null


                        if( "FormData" in window ) {
                            var formData_object = new FormData();//create empty FormData object

                            //serialize our form (which excludes file inputs)
                            $.each($form.serializeArray(), function(i, item) {
                                //add them one by one to our FormData
                                formData_object.append(item.name, item.value);
                            });
                            //and then add files
                            $form.find('input[type=file]').each(function(){
                                var field_name = $(this).attr('name');
                                var files = $(this).data('ace_input_files');
                                if(files && files.length > 0) {
                                    formData_object.append(field_name, files[0]);
                                }
                            });

                            //append primary key to our formData
                            formData_object.append('pk', pk);

                            deferred = $.ajax({
                                url: submit_url,
                                type: 'POST',
                                processData: false,//important
                                contentType: false,//important
                                dataType: 'json',//server response type
                                data: formData_object
                            })
                        }
                        else {
                            deferred = new $.Deferred

                            var temporary_iframe_id = 'temporary-iframe-'+(new Date()).getTime()+'-'+(parseInt(Math.random()*1000));
                            var temp_iframe =
                                $('<iframe id="'+temporary_iframe_id+'" name="'+temporary_iframe_id+'" \
									frameborder="0" width="0" height="0" src="about:blank"\
									style="position:absolute; z-index:-1; visibility: hidden;"></iframe>')
                                    .insertAfter($form);

                            $form.append('<input type="hidden" name="temporary-iframe-id" value="'+temporary_iframe_id+'" />');

                            //append primary key (pk) to our form
                            $('<input type="hidden" name="pk" />').val(pk).appendTo($form);

                            temp_iframe.data('deferrer' , deferred);
                            //we save the deferred object to the iframe and in our server side response
                            //we use "temporary-iframe-id" to access iframe and its deferred object

                            $form.attr({
                                action: submit_url,
                                method: 'POST',
                                enctype: 'multipart/form-data',
                                target: temporary_iframe_id //important
                            });

                            $form.get(0).submit();

                            //if we don't receive any response after 30 seconds, declare it as failed!
                            ie_timeout = setTimeout(function(){
                                ie_timeout = null;
                                temp_iframe.attr('src', 'about:blank').remove();
                                deferred.reject({'status':'fail', 'message':'Timeout!'});
                            } , 30000);
                        }


                        //deferred callbacks, triggered by both ajax and iframe solution
                        deferred
                            .done(function(result) {//success
                                var res = result;//the `result` is formatted by your server side response and is arbitrary
                                if(res.status == '1') {
                                    $(avatar).get(0).src = res.url+"?t="+new Date().getTime();
                                    //var reg=new RegExp("large","g");
                                    //newUrl=res.url.replace(reg,"middle");
                                    $('img.nav-user-photo')[0].src=res.url+"?t="+new Date().getTime();
                                    $('#navbarToggleImg')[0].src=res.url+"?t="+new Date().getTime();
                                }
                                else alert(res.message);
                            })
                            .fail(function(result) {//failure
                                alert("上传错误！");
                            })
                            .always(function() {//called on both success and failure
                                if(ie_timeout) clearTimeout(ie_timeout)
                                ie_timeout = null;
                            });

                        return deferred.promise();
                        // ***END OF UPDATE AVATAR HERE*** //
                    },

                    success: function(response, newValue) {
                    }
                })
            }catch(e) {}

            // 修改密码
            $("#changePassword").click(function () {
                $.get("${ctx}/sys/user/TomodifyPwd", function (data, textStatus, object) {
                    $("#changePwdDivId").html(object.responseText).dialog({
                        modal: true,
                        width: 550,
                        height: 300,
                        title: "<div class='widget-header widget-header-small widget-header-flat'><h4 class='smaller' style='line-height:2'><i class='ace-icon fa fa-th-large'></i>&nbsp;<span data-locale='ChangePassword'>修改密码</span></h4></div>",
                        title_html: true,
                        buttons: [
                            {
                                text: "保存",
                                "class": "btn btn-primary btn-minier",
                                "data-locale":"doSave",
                                click: function () {
                                    $('#inputForm').bootstrapValidator('validate');
                                }
                            },
                            {
                                text: "取消",
                                "class": "btn btn-minier",
                                "data-locale":"cancel",
                                click: function () {
                                    $(this).dialog("close");
                                }
                            }
                        ],
						open:function () {
                            var qCode = sessionStorage.getItem("qCode");
                            typeChange(qCode);

                            $(".ui-dialog-title .widget-header").on('mouseenter',function(){
                                $(".ui-dialog-content input").blur();
                            })
                        }
                    });
                    //动作设备页面维护表单验证
                    $('#inputForm').bootstrapValidator({
                        message : '请录入一个有效值',
                        excluded:':disabled, :hidden, :not(:visible)',
                        feedbackIcons : {
                            valid : 'glyphicon glyphicon-ok',
                            invalid : 'glyphicon glyphicon-remove',
                            validating : 'glyphicon glyphicon-refresh'
                        },
                        fields : {
                            oldPassword : {
                                validators : {
                                    notEmpty : {
                                        message : '旧密码不能位空'
                                    }
                                }
                            },
                            newPassword : {
                                validators : {
                                    notEmpty : {
                                        message : '新密码不能位空'
                                    }
                                }
                            },
                            confirmNewPassword : {
                                validators : {
                                    notEmpty : {
                                        message : '确认密码不能为空'
                                    },
                                    identical : {
                                        field : 'newPassword',
                                        message : '两次密码不一致'
                                    }
                                }
                            }
                        },
                        submitButtons: '#submitBtn',
                        submitHandler: null,
                    }).on('success.form.bv',function(e) {
                        // Prevent form submission
                        e.preventDefault();
                        // Get the form instance
                        var $form = $(e.target);
                        // Get the BootstrapValidator instance
                        var bv = $form.data('bootstrapValidator');
                        // Use Ajax to submit form data
                        $.post($form.attr('action'),$form.serialize(), function(result) {
                            var status =result.status,type='success';
                            if(status == 'failure'){type='error';}
                            $.msg_show.Init({
                                'msg':result.message,
                                'type':type
                            });
                            if(status == 'success'){
                                $("#changePwdDivId").dialog("close");
                            }
                            $form.bootstrapValidator('disableSubmitButtons', false);
                        }, 'json');
                    }) ;
                });
            });
        });
	});
</script>