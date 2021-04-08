/*!
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 * 
 * 通用公共方法
 * @author DHC
 * @version 2014-4-29
 */

// 获取URL地址参数
function getQueryString(name, url) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	if (!url || url == ""){
		url = window.location.search;
	}else{	
		url = url.substring(url.indexOf("?"));
	}
	r = url.substr(1).match(reg)
	if (r != null) return unescape(r[2]); return null;
}

//获取字典标签
function getDictLabel(data, value, defaultValue){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if (row.value == value){
			return row.label;
		}
	}
	return defaultValue;
}

// 打开一个窗体
function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
		"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	window.open(url ,name , options);
}

// cookie操作
function cookie(name, value, options) {
	if (typeof value != 'undefined') { // name and value given, set cookie
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
		}
		var path = options.path ? '; path=' + options.path : '';
		var domain = options.domain ? '; domain=' + options.domain : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
	} else { // only name given, get cookie
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for (var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				// Does this cookie string begin with the name we want?
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
}

// 数值前补零
function pad(num, n) {
	var len = num.toString().length;
	while(len < n) {
		num = "0" + num;
		len++;
	}
	return num;
}

// 转换为日期
function strToDate(date){
	return new Date(date.replace(/-/g,"/"));
}

// 日期加减
function addDate(date, dadd){
	date = date.valueOf();
	date = date + dadd * 24 * 60 * 60 * 1000;
	return new Date(date);
}

//截取字符串，区别汉字和英文
function abbr(name, maxLength){
	if(!maxLength){
		maxLength = 20;
	}
	if(name==null||name.length<1){
		return "";
	}
	var w = 0;//字符串长度，一个汉字长度为2
	var s = 0;//汉字个数
	var p = false;//判断字符串当前循环的前一个字符是否为汉字
	var b = false;//判断字符串当前循环的字符是否为汉字
	var nameSub;
	for (var i=0; i<name.length; i++) {
		if(i>1 && b==false){
			p = false;
		}
		if(i>1 && b==true){
			p = true;
		}
		var c = name.charCodeAt(i);
		//单字节加1
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
			w++;
			b = false;
		}else {
			w+=2;
			s++;
			b = true;
		}
		if(w>maxLength && i<=name.length-1){
			if(b==true && p==true){
				nameSub = name.substring(0,i-2)+"...";
			}
			if(b==false && p==false){
				nameSub = name.substring(0,i-3)+"...";
			}
			if(b==true && p==false){
				nameSub = name.substring(0,i-2)+"...";
			}
			if(p==true){
				nameSub = name.substring(0,i-2)+"...";
			}
			break;
		}
	}
	if(w<=maxLength){
		return name;
	}
	return nameSub;
}

/*******************************************************************************
 * 文件名称：msg_show.js
 * 文件说明：信息提示插件
 * 创建者：maliang
 * 创建时间：2015-11-3
 ******************************************************************************/
(function ($) {
	$.msg_show = {
		Init: function (options) {
			var t = this;
			t.options = $.extend({}, $.msg_show.defaults, options);
			t.options.msg_config = {
				title: '信息',
				text: '',
				class_name:'',
				time: 500
			};
			t.options.msg_config.text = t.options.msg;
			switch (t.options.type) {
				case 'success':
					t.options.msg_config.class_name = 'gritter-success gritter-center gritter-light';
					t._success(t.options.msg_config);
					break;
				case 'error':
					t.options.msg_config.class_name = 'gritter-error gritter-center gritter-light';
					t._error(t.options.msg_config);
					break;
				default:
					t.options.msg_config.class_name = 'gritter-error gritter-center gritter-light';
					t._error(t.options.msg_config);
					break;
			}
		},
		_success: function (config) {
			$.gritter.add(config);
		},
		_error: function (config) {
			$.gritter.add(config);
		}
	};
	$.msg_show.defaults = {
		'msg': '服务器忙！',
		'type': 'error'
	};
})(jQuery);
/*******************************************************************************
 * 文件名称：msg_confirm.js
 * 文件说明：信息确认插件
 * 创建者：maliang
 * 创建时间：2015-11-3
 ******************************************************************************/
(function ($) {
	$.msg_confirm = {
		Init: function (options) {
			var t = this;
			t.options = $.extend({}, $.msg_confirm.defaults, options);
			bootbox.confirm({
				message: t.options.msg,
				buttons: {
					confirm: {
						label: "确定",
						className: "btn-primary btn-sm",
					},
					cancel: {
						label: "取消",
						className: "btn-sm",
					}
				},
				callback: function(res) {
					if(res == true){
						t.options.confirm_fn();
					}else{
						t.options.cancel_fn();
					}
				}
			});
		}
	};
	$.msg_confirm.defaults = {
		'msg': '这是信息提示！',
		'confirm_fn':function(){},
		'cancel_fn':function(){}
	};
})(jQuery);
/*******************************************************************************
 * 文件名称：changeGridTable.js
 * 文件说明：更改表格
 * 创建者：maliang
 * 创建时间：2015-11-4
 ******************************************************************************/
(function ($) {
	function hasScrolled(el) {
		var direction = arguments.length <= 1 || arguments[1] === undefined ? 'vertical' : arguments[1];
		var overflow = el.currentStyle ? el.currentStyle.overflow : window.getComputedStyle(el).getPropertyValue('overflow');
		if (overflow === 'hidden')
			return false;
		if (direction === 'vertical') {
			return el.scrollHeight > el.clientHeight;
		} else if (direction === 'horizontal') {
			return el.scrollWidth > el.clientWidth;
		}
	}

	$.changeGridTable = {
		changeStyle: function (table) {
			var t = this;
			setTimeout(function(){
				t.styleCheckbox(table);
			//	t.updateActionIcons(table);
			//	t.updatePagerIcons(table);
			//	t.enableTooltips(table);
			}, 0);
		},
		changeSize:function(tableId,callback){
			$.each(tableId,function(i,n){
				try {
					var $table = $(n);
					var toppagerTmp = $table.getGridParam('toppager');
					if (toppagerTmp !== false) {
						var pagerTmp = $table.getGridParam('pager');
						$(toppagerTmp + '_center').remove();
						$(toppagerTmp + '_right').remove();
						$(pagerTmp + '_left table').remove();
						$(pagerTmp + '_left').css('width','auto');
					}
				} catch (e) {
					// TODO: handle exception
				}
			});
			$('.ui-jqgrid-labels th[id*="_cb"]').find('input.cbox[type=checkbox]').addClass('ace').wrap('<label />').after('<span class="lbl align-top" />');
			$('.navtable .ui-pg-button').tooltip({container:'body'});
			
			var changeGridWidth = function(gridWidth){
				$.each(tableId,function(i,n){
					$(n).jqGrid('setGridWidth',gridWidth);
				});
			};

			//resize to fit page size
			var parent_column = $(tableId[0]).closest('[class*="col-"]');
			$(window).off('resize.jqGrid').on('resize.jqGrid', function () {
				if (hasScrolled(document.body, 'horizontal')) {
					changeGridWidth(200);
				}
				if(callback != undefined){
					callback();
				}
				changeGridWidth(parent_column.width());
			});

			//resize on sidebar collapse/expand
			$(document).off('settings.ace.jqGrid').on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
				if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
					//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
					setTimeout(function() {
						if (hasScrolled(document.body, 'horizontal')) {
							changeGridWidth(200);
						}
						if(callback != undefined){
							callback();
						}
						changeGridWidth(parent_column.width());
					}, 0);
				}
			});

			//如果你的表格在其他元素（举例：tab pane）里，你需要使用父元素的宽度:
			/**
			$(window).on('resize.jqGrid', function () {
				var parent_width = $(grid_selector).closest('.tab-pane').width();
				$(grid_selector).jqGrid( 'setGridWidth', parent_width );
			})
			//and also set width when tab pane becomes visible
			$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
				if($(e.target).attr('href') == '#mygrid') {
					var parent_width = $(grid_selector).closest('.tab-pane').width();
					$(grid_selector).jqGrid( 'setGridWidth', parent_width );
				}
			})
			*/

			$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
		},

		//it causes some flicker when reloading or navigating grid
		//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
		//or go back to default browser checkbox styles for the grid
		styleCheckbox:function(table){
			$(table).find('input:checkbox:not(.ace)').addClass('ace').wrap('<label />').after('<span class="lbl align-top" />');
		},
		//unlike navButtons icons, action icons in rows seem to be hard-coded
		//you can change them like this in here if you want
		updateActionIcons:function(table) {
		/**
			var replacement = {
				'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
				'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
				'ui-icon-disk' : 'ace-icon fa fa-check green',
				'ui-icon-cancel' : 'ace-icon fa fa-times red'
			};
			$(table).find('.ui-pg-div span.ui-icon').each(function(){
				var icon = $(this);
				var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
				if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
			});
		 */
		},
		//replace icons with FontAwesome icons like above
		updatePagerIcons:function(table) {
		/**
			var replacement = {
				'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
				'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
				'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
				'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
			};
			$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
				var icon = $(this);
				var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
				if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
			});
		 */
		},
		enableTooltips:function(table) {
		//	$('.navtable .ui-pg-button').tooltip({container:'body'});
		//	$(table).find('.ui-pg-div').tooltip({container:'body'});
		}
	};
})(jQuery);

(function($){
	if(!Array.prototype.map)
		Array.prototype.map = function(fn,scope) {
		var result = [],ri = 0;
		for (var i = 0,n = this.length; i < n; i++){
			if(i in this){
				result[ri++] = fn.call(scope ,this[i],i,this);
			}
		}
		return result;
	};

	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val) return i;
		}
		return -1;
	};

	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};

	$.getWindowSize = function(){
		return ["Height","Width"].map(function(name){
			return window["inner"+name] ||
				document.compatMode === "CSS1Compat" && document.documentElement[ "client" + name ] || document.body[ "client" + name ];
		});
	};

})(jQuery);

/*******************************************************************************
 * 文件名称：jgrid公共属性设置
 * 文件说明：jgrid公共属性设置
 * 创建者：maliang
 * 创建时间：2016-10-28
 ******************************************************************************/
(function($){
	$.jgrid = $.jgrid || {};
	$.extend(true,$.jgrid,{
		defaults : {
			datatype: "json",//从服务器端返回的数据类型，默认xml。可选类型：xml，local，json，jsonnp，script，xmlstring，jsonstring，clientside
			mtype: "POST",//ajax提交方式。POST或者GET，默认GET
			altRows: true,//设置表格 zebra-striped 值
			jsonReader: {
				root: "rows", // json中代表实际模型数据的入口
				page: "page", // json中代表当前页码的数据
				total: "total", // json中代表页码总数的数据
				records: "records", // json中代表数据行总数的数据
				repeatitems: false
			},
			viewrecords: true, //定义是否要显示总记录数
			prmNames : {
				page:"pageNo", // 表示请求页码的参数名称
				rows:"rows", // 表示请求行数的参数名称
				sort: "sidx", // 表示用于排序的列名的参数名称
				order: "sord", // 表示采用的排序方式的参数名称
				search:"_search", // 表示是否是搜索请求的参数名称
				nd:"nd", // 表示已经发送请求的次数的参数名称
				id:"id", // 表示当在编辑数据模块中发送数据时，使用的id的名称
				oper:"oper", // operation参数名称（我暂时还没用到）
				editoper:"edit", // 当在edit模式中提交数据时，操作的名称
				addoper:"add", // 当在add模式中提交数据时，操作的名称
				deloper:"del", // 当在delete模式中提交数据时，操作的名称
				subgridid:"id", // 当点击以载入数据到子表时，传递的数据名称
				npage: null,
				totalrows:"totalrows" // 表示需从Server得到总共多少行数据的参数名称，参见jqGrid选项中的rowTotal
			},
			loadError: function(xhr, st, err) {
				$.msg_show.Init({
					'msg': '服务器发生错误!错误信息：<br>' + st + '<br>状态码：' + xhr.status + '<br>' + xhr.statusText,
					'type': 'error'
				});
			},//39
			autowidth: true,//89如果为ture时，则当表格在首次被创建时会根据父元素比例重新调整表格宽度。如果父元素宽度改变，为了使表格宽度能够自动调整则需要实现函数：setGridWidth
			toppager: true,//109复制菜单到顶部
		//	width: 200,//如果设置则按此设置为主，如果没有设置则按colModel中定义的宽度计算
			altclass: '',
		},
		nav : {
			edit: false,
			editicon: 'ace-icon fa fa-pencil blue',
			editfunc: null,
			edittext: "编辑",
			add: false,
			addicon: 'ace-icon fa fa-plus-circle purple',
			addfunc: null,
			addtext: "新增",
			del: false,
			delicon: 'ace-icon fa fa-trash-o red',
			delfunc: null,
			deltext: "删除",
			search: false,
			searchicon : 'ace-icon fa fa-search orange',
			searchfunc: null,
			searchtext: "查询",
			refresh: false,
			refreshicon: 'ace-icon fa fa-refresh green',
			refreshtext: "刷新",
			view: false,
			viewicon: 'ace-icon fa fa-search-plus grey',
			viewtext: "查看",
			cloneToTop: true,
		},
		styleUI: {
			jQueryUI: {
				base: {
					icon_first : "ace-icon fa fa-angle-double-left bigger-140",
					icon_prev : "ace-icon fa fa-angle-left bigger-140",
					icon_next: "ace-icon fa fa-angle-right bigger-140",
					icon_end: "ace-icon fa fa-angle-double-right bigger-140",
				},
			},
		},
	});
})(jQuery);
/*******************************************************************************
 * 文件名称：插件初始化
 * 文件说明：插件初始化
 * 创建者：maliang
 * 创建时间：2016-11-02
 ******************************************************************************/
(function($){
	if(location.protocol == 'file:'){
		alert("你应该使用“HTTP”协议访问这个页面");
	}
	
	//override dialog's title function to allow for HTML titles
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title: function(title) {
			var $title = this.options.title || '&nbsp;'
			if( ("title_html" in this.options) && this.options.title_html == true )
				title.html($title);
			else title.text($title);
		}
	}));
	
	$.plugInit = {
		'editable': function(mode){
			if (!!mode) {
				$.fn.editable.defaults.mode = mode;
			}
			$.fn.editableform.loading = "<div class='editableform-loading'><i class='ace-icon fa fa-spinner fa-spin fa-2x light-blue'></i></div>";
			$.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="ace-icon fa fa-check"></i></button>'+
										'<button type="button" class="btn editable-cancel"><i class="ace-icon fa fa-times"></i></button>';
		}	
	};
})(jQuery);
/*******************************************************************************
 * 文件名称：简化jGrid增删改
 * 文件说明：简化jGrid增删改
 * 创建者：maliang
 * 创建时间：2016-11-04 重写：2016-12-06
 ******************************************************************************/
(function($){
	var t = null;
	$.jGridCurdFn = {
		_lockFlag: false,//请求锁
		_$form: null,
		doDelete: function(options){
			t = this;
			if (t._lockFlag) {
				return false;
			}
			t.options = $.extend(true, {}, $.jGridCurdFn.defaults, options);
			var confirmOptions = t.options['confirm'];
			if(!$.isFunction(confirmOptions['confirm_fn'])) {
				var formParams = t.options['base']['formParams'];
				var confirmFn = function(){
					if (!formParams) {
						var tableObj = t.options['base']['tableObj'];
						var selectedIds = tableObj.jqGrid("getGridParam", "selarrrow");
						var lengthTmp = selectedIds.length;
						if(lengthTmp < 1){
							$.msg_show.Init({
								'msg':'请您至少选择一条记录',
								'type':'error'
							});
							return;
						}
						var ids = "";
						for (var i = 0;i < lengthTmp;i++){
							var id = selectedIds[i];
							ids = (ids + id) + (((i + 1) == lengthTmp) ? '':',');
						}
						formParams = {"ids":ids};
					}
					t._lockFlag = true;
					$.loadingFn.show();
					$.post(t.options['base']['formUrl'],formParams,function(data){
						$.loadingFn.hide();
						if(data.messageStatus === '1'){
							$.msg_show.Init({
								'msg':data.message,
								'type':'success'
							});	
							t.options.base.success();
						} else {
							$.msg_show.Init({
								'msg':data.message,
								'type':'error'
							});
						}
						t._lockFlag = false;
					});
				};
				confirmOptions['confirm_fn'] = confirmFn;
			}
			if(!$.isFunction(confirmOptions['cancel_fn'])) {
				confirmOptions['cancel_fn'] = function(){};
			}
			$.msg_confirm.Init(confirmOptions);
		},
		doAdd: function(options){
			t = this;
			if (t._lockFlag) {
				return false;
			}
			t.options = $.extend(true, {}, $.jGridCurdFn.defaults, options);
			if (!t.options['base']['formParams']) {
				t.options['base']['formParams'] = {};
			}
			t._edit();
		},
		doEdit: function(options){
			t = this;
			if (t._lockFlag) {
				return false;
			}
			t.options = $.extend(true, {}, $.jGridCurdFn.defaults, options);
			if (!t.options['base']['formParams']) {
				t.options['base']['formParams'] = {};
			}
			if (!t.options['base']['formParams']['id']) {
				var tableObj = t.options['base']['tableObj'];
				var selectedIds = tableObj.jqGrid("getGridParam", "selarrrow");
				if(!(selectedIds.length === 1)){
					//失败
					$.msg_show.Init({
						'msg':'请您选择一条记录修改',
						'type':'error'
					});
					return;
				}
				t.options['base']['formParams']['id'] = selectedIds[0];
			}
	 		t._edit();
		},
		_edit: function(){
			if (t._lockFlag) {
				return false;
			}
			var dialogOptions = t.options['dialog'];
			var validatorOptions = t.options['validator'];;
			dialogOptions['title'] = '<div class="widget-header widget-header-small widget-header-flat">\
				<h4 class="smaller">\
				<i class="' + dialogOptions['titleIcon'] + '"></i>\
				&nbsp;' + dialogOptions['title'] + '</h4></div>';
			delete dialogOptions['titleIcon'];
			t._lockFlag = true;
			$.post(t.options['base']['formUrl'],t.options['base']['formParams'], function(data,textStatus,object){
				var $dialog = $("#editDivId");
				if (!!$dialog.data('ui-dialog')) {
					$dialog.dialog('destroy');
				}
				$dialog.html(object.responseText).dialog(dialogOptions);
				t._$form = $dialog.find("#inputForm");
				t._$form.bootstrapValidator(validatorOptions);
				t._lockFlag = false;
			});
		}
	};
	$.jGridCurdFn.defaults = {
		base: {
			tableObj: null,
			formUrl: null,
			formParams: null,
			success: function(){
				t.options.base.tableObj.jqGrid('setGridParam',{datatype: 'json',page: 1}).jqGrid('clearGridData').trigger('reloadGrid');
			}
		},
		confirm: {
			msg: '这是信息提示！',
			confirm_fn: null,
			cancel_fn: null
		},
		dialog: {
			autoOpen: true,
			buttons: [ 
				{
					text: "保存",
					"class" : "btn btn-primary btn-minier",
					'id':'dialogSaveBtn',
					click: function() {
						t._$form.submit();
					}
				},
				{							
					text: "取消",
					"class" : "btn btn-minier",
					click: function() {
						if (t._lockFlag) {
							return false;
						}
						$(this).dialog("close"); 
					} 
				}
			],
			closeText: "关闭",
			height: "auto",
			modal: true,
			title: '编辑',
			titleIcon: null,
			title_html: true,
			width: "auto",
			
			// callbacks
			create: null,
			open: null,
		},
		validator: {
			//排除禁用，隐藏，不可显示的控件
			//excluded: [':disabled', ':hidden', ':not(:visible)'],
			excluded: [],
			feedbackIcons : {
				valid : "glyphicon glyphicon-ok",
				invalid : "glyphicon glyphicon-remove",
				validating : "glyphicon glyphicon-refresh"
			},
			//live: 'enabled',
			message : "请录入一个有效值",
//			submitButtons: '#dialogSaveBtn',
			onSuccess: function(e) {
				if (t._lockFlag) {
					return false;
				}
				
				var dialogSaveBtnObj = $('#dialogSaveBtn');
				dialogSaveBtnObj.data('oldText',dialogSaveBtnObj.html());
				dialogSaveBtnObj.attr('disabled','disabled');
				dialogSaveBtnObj.find('span').html('保存中..');
				
				t._lockFlag = true;
				$.loadingFn.show();
				$.post(t._$form.attr('action'), t._$form.serialize(), function(result) {
					$.loadingFn.hide();
					if(result.messageStatus == "1"){
						$.msg_show.Init({
							'msg':result.message,
							'type':'success'
						});		
						$("#editDivId").dialog("close");
						t.options.base.success();
					} else {
						$.msg_show.Init({
							'msg':result.message,
							'type':'error'
						});	
//						form.bootstrapValidator('disableSubmitButtons', false);
					}
					dialogSaveBtnObj.html(dialogSaveBtnObj.data('oldText'));
					dialogSaveBtnObj.removeAttr('disabled');
					t._lockFlag = false;
				}, 'json');
			},
			trigger: null,
			fields: null
		}
	};
})(jQuery);
/*******************************************************************************
 * 文件名称：文件上传
 * 文件说明：文件上传
 * 创建者：maliang
 * 创建时间：2016-11-17
 ******************************************************************************/
(function($){
	$.uploadFn = {
		_uploading: false,
		upload: function(options){
			var t = this;
			var optionsTmp = $.extend({}, $.uploadFn.defaults.upload, options);
			var $form = optionsTmp['formObj'];
			//you can have multiple files, or a file input with "multiple" attribute
			var file_input = $form.find('input[type=file]');
			var upload_in_progress = false;
			t._uploading = upload_in_progress;

			file_input.ace_file_input({
				style : 'well',
				btn_choose : '选择或者拖拽文件到这里',
				btn_change: null,
				droppable: true,
				thumbnail: 'large',
				
				maxSize: 110000,//bytes
				allowExt: optionsTmp['allowExt'],
				allowMime: optionsTmp['allowMime'],

				before_remove: function() {
					if(upload_in_progress)
						return false;//if we are in the middle of uploading a file, don't allow resetting file input
					return true;
				},

				preview_error: function(filename , code) {
					//code = 1 means file load error
					//code = 2 image load error (possibly file is not an image)
					//code = 3 preview failed
				}
			});
			file_input.on('file.error.ace', function(ev, info) {
				if(info.error_count['ext'] || info.error_count['mime']) {
					$.msg_show.Init({
						'msg':'无效的文件类型，请选择一个.xslx文件!',
						'type':'error'
					});
				};
				if(info.error_count['size']) {
					$.msg_show.Init({
						'msg':'文件大小最大为100KB!',
						'type':'error'
					});
				};
				
				//you can reset previous selection on error
				//ev.preventDefault();
				//file_input.ace_file_input('reset_input');
			});
				
			var ie_timeout = null;//a time for old browsers uploading via iframe
			
			$form.on('submit', function(e) {
				e.preventDefault();
			
				var files = file_input.data('ace_input_files');
				if( !files || files.length == 0 ) return false;//no files selected
									
				var deferred ;
				if( "FormData" in window ) {
					//for modern browsers that support FormData and uploading files via ajax
					//we can do >>> var formData_object = new FormData($form[0]);
					//but IE10 has a problem with that and throws an exception
					//and also browser adds and uploads all selected files, not the filtered ones.
					//and drag&dropped files won't be uploaded as well
					
					//so we change it to the following to upload only our filtered files
					//and to bypass IE10's error
					//and to include drag&dropped files as well
					formData_object = new FormData();//create empty FormData object
					
					//serialize our form (which excludes file inputs)
					$.each($form.serializeArray(), function(i, item) {
						//add them one by one to our FormData 
						formData_object.append(item.name, item.value);							
					});
					//and then add files
					$form.find('input[type=file]').each(function(){
						var field_name = $(this).attr('name');
						//for fields with "multiple" file support, field name should be something like `myfile[]`

						var files = $(this).data('ace_input_files');
						if(files && files.length > 0) {
							for(var f = 0; f < files.length; f++) {
								formData_object.append(field_name, files[f]);
							}
						}
					});

					upload_in_progress = true;
					t._uploading = upload_in_progress;
					file_input.ace_file_input('loading', true);
					
					deferred = $.ajax({
						url: $form.attr('action'),
						type: $form.attr('method'),
						processData: false,//important
						contentType: false,//important
						dataType: 'json',
						data: formData_object
						/**
						,
						xhr: function() {
							var req = $.ajaxSettings.xhr();
							if (req && req.upload) {
								req.upload.addEventListener('progress', function(e) {
									if(e.lengthComputable) {	
										var done = e.loaded || e.position, total = e.total || e.totalSize;
										var percent = parseInt((done/total)*100) + '%';
										//percentage of uploaded file
									}
								}, false);
							}
							return req;
						},
						beforeSend : function() {
						},
						success : function() {
						}*/
					})

				} else {
					//for older browsers that don't support FormData and uploading files via ajax
					//we use an iframe to upload the form(file) without leaving the page

					deferred = new $.Deferred //create a custom deferred object
					
					var temporary_iframe_id = 'temporary-iframe-'+(new Date()).getTime()+'-'+(parseInt(Math.random()*1000));
					var temp_iframe = 
							$('<iframe id="'+temporary_iframe_id+'" name="'+temporary_iframe_id+'" \
							frameborder="0" width="0" height="0" src="about:blank"\
							style="position:absolute; z-index:-1; visibility: hidden;"></iframe>')
							.insertAfter($form)

					$form.append('<input type="hidden" name="temporary-iframe-id" value="'+temporary_iframe_id+'" />');
					
					temp_iframe.data('deferrer' , deferred);
					//we save the deferred object to the iframe and in our server side response
					//we use "temporary-iframe-id" to access iframe and its deferred object
					
					$form.attr({
								method: 'POST',
								enctype: 'multipart/form-data',
								target: temporary_iframe_id //important
								});

					upload_in_progress = true;
					t._uploading = upload_in_progress;
					file_input.ace_file_input('loading', true);//display an overlay with loading icon
					$form.get(0).submit();
					
					//if we don't receive a response after 30 seconds, let's declare it as failed!
					ie_timeout = setTimeout(function(){
						ie_timeout = null;
						temp_iframe.attr('src', 'about:blank').remove();
						deferred.reject({'status':'fail', 'message':'超时!'});
					} , 30000);
				}

				////////////////////////////
				//deferred callbacks, triggered by both ajax and iframe solution
				deferred.done(function(result) {//success
					var doneFn = optionsTmp.doneFn;
					if (!!doneFn && $.isFunction(doneFn)) {
						var dialogObjTmp = optionsTmp.dialogObj;
						var resTmp = null;
						if (!!dialogObjTmp) {
							resTmp = doneFn(result, dialogObjTmp);
						} else {
							resTmp = doneFn(result);
						}
						if (resTmp === false) {
							return;
						}
					}
					//format of `result` is optional and sent by server
					//in this example, it's an array of multiple results for multiple uploaded files
					var message = '';
					var msgType = 'error';
					if (!!result) {
						if ($.isArray(result)) {
							var lengthTmp = result.length;
							for(var i = 0; i < lengthTmp; i++) {
								if(result[i].messageStatus == '1') {
									message += "文件上传成功." + result[i].message;// + result[i].url
									msgType = 'success';
								} else {
									message += "文件上传失败." + result[i].message;
								}
								message += "\n";
							}
						} else {
							if(result.messageStatus == '1') {
								message += "文件上传成功." + result.message;
								msgType = 'success';
							} else {
								message += "文件上传失败." + result.message;
							}
						}
					}
					$.msg_show.Init({
						'msg': message,
						'type': msgType
					});
				}).fail(function(result) {//failure
					$.msg_show.Init({
						'msg': '上传过程中有错误发生.',
						'type': 'error'
					});
				}).always(function() {//called on both success and failure
					if(ie_timeout) clearTimeout(ie_timeout)
					ie_timeout = null;
					upload_in_progress = false;
					t._uploading = upload_in_progress;
					file_input.ace_file_input('loading', false);
				});

				deferred.promise();
			});


			//when "reset" button of form is hit, file field will be reset, but the custom UI won't
			//so you should reset the ui on your own
			$form.on('reset', function() {
				$(this).find('input[type=file]').ace_file_input('reset_input_ui');
			});
		},
        dialogMode: function(options){
            var t = this;
            var optionsTmp = options['dialogMode'];
            var optionsTmp4Upload = options['upload'];
            var dialogHtml = '<div class="widget-body">\
				<div class="widget-main center">\
					<form id="myform" method="post" action="' + optionsTmp['uploadUrl'] + '">\
						<input type="file" name="file" />\
						<button type="submit" class="btn btn-sm btn-primary">上传</button>\
						<button type="reset" class="btn btn-sm">重置</button>\
					</form>\
				</div>\
			</div>';
            $('.ui-jqdialog').remove();
            var titleTmp = '<div class="widget-header widget-header-small widget-header-flat"><h4 class="smaller"><i class="\
				ace-icon fa fa-upload bigger-120"></i>&nbsp;文件上传</h4></div>';
            var $dialog = $("#editDivId");
            if (!!$dialog.data('ui-dialog')) {
                $dialog.dialog('destroy');
            }
            $dialog.html(dialogHtml).dialog({
                modal: true,
                //	autoOpen: false,
                width: 400,
                //	height: options['dialogHeight'],
                title: titleTmp,
                title_html: true,
                create: function(event){
                    optionsTmp4Upload['formObj'] = $('#myform');
                    optionsTmp4Upload['dialogObj'] = $(this);
                    t.upload(optionsTmp4Upload);
                },
                beforeClose: function(event){
                    if (t._uploading) {
                        $.msg_show.Init({
                            'msg': '正在上传中...',
                            'type': 'success'
                        });
                        return false;
                    }
                }
            });
        }
	};
	
	$.uploadFn.defaults = {
		upload: {
			formObj: null,
			allowExt: ["jpeg", "jpg", "png", "gif"],
			allowMime: ["image/jpg", "image/jpeg", "image/png", "image/gif"],
			doneFn: null,
		},
		dialogMode: {
			uploadUrl: null
		}
	};
})(jQuery);

/*******************************************************************************
 * 文件名称：chosenSuper.js
 * 文件说明：chosen-select增强版
 * 创建者：maliang
 * 创建时间：2016-06-23
 ******************************************************************************/
(function($){
	$.fn.chosenSuper = function(options){
		var self = this;
		if(!ace.vars['touch']) {
			self.chosen(options); 
			//resize the chosen on window resize
			$(window).off('resize.chosen').on('resize.chosen', function() {
				self.each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			}).trigger('resize.chosen');
			//resize chosen on sidebar collapse/expand
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				self.each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			});
		}
	};
})(jQuery);

/*******************************************************************************
 * 文件名称：loadingFn.js
 * 文件说明：等待提示
 * 创建者：maliang
 * 创建时间：2016-11-22
 ******************************************************************************/
(function($){
	$.loadingFn = {
		_dialogObj: null,
		show: function(){
			var t = this;
			t._dialogObj = bootbox.dialog({
				message: '<div class="text-center"><i class="fa fa-spin fa-spinner"></i> 正在提交，请稍等...</div>',
				closeButton: false
			});
		},
		hide: function(){
			var t = this;
			t._dialogObj.modal('hide');
		}
	};
	$.loadingFn.defaults = {};
})(jQuery);
