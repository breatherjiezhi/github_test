<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
			<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title></title>

		<meta name="description" content="Draggabble Widget Boxes &amp; Containers" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${ctxStatic}/assets/css/bootstrap.css" />
		<link rel="stylesheet" href="${ctxStatic}/assets/css/font-awesome.css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${ctxStatic}/assets/css/jquery-ui.custom.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="${ctxStatic}/assets/css/ace-fonts.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="${ctxStatic}/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${ctxStatic}/assets/css/ace-part2.css" class="ace-main-stylesheet" />
		<![endif]-->

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${ctxStatic}/assets/css/ace-ie.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->
		<script src="${ctxStatic}/assets/js/ace-extra.js"></script>

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="${ctxStatic}/assets/js/html5shiv.js"></script>
		<script src="${ctxStatic}/assets/js/respond.js"></script>
		<![endif]-->
	</head>
	<body>
	<div class="main-content">
				<div class="main-content-inner">
		<div class="page-content">
			<!-- #section:settings.box -->
	 
			<!-- /.ace-settings-container -->

			<!-- /section:settings.box -->
			<div class="page-header">
				<h1>
					我的工作台
					
				</h1>
			</div>
			<!-- /.page-header -->

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<div class="row">
						<div class="col-xs-12 col-sm-6 widget-container-col">
							<!-- #section:custom/widget-box -->
							<div class="widget-box">
								<div class="widget-header">
									<h5 class="widget-title">
										Default Widget Box
									</h5>

									<!-- #section:custom/widget-box.toolbar -->
									<div class="widget-toolbar">
										<div class="widget-menu">
											<a href="#" data-action="settings" data-toggle="dropdown">
												<i class="ace-icon fa fa-bars"></i> </a>

											<ul
												class="dropdown-menu dropdown-menu-right dropdown-light-blue dropdown-caret dropdown-closer">
												<li>
													<a data-toggle="tab" href="#dropdown1">Option#1</a>
												</li>

												<li>
													<a data-toggle="tab" href="#dropdown2">Option#2</a>
												</li>
											</ul>
										</div>

										<a href="#" data-action="fullscreen" class="orange2"> <i
											class="ace-icon fa fa-expand"></i> </a>

										<a href="#" data-action="reload"> <i
											class="ace-icon fa fa-refresh"></i> </a>

										<a href="#" data-action="collapse"> <i
											class="ace-icon fa fa-chevron-up"></i> </a>

										<a href="#" data-action="close"> <i
											class="ace-icon fa fa-times"></i> </a>
									</div>

									<!-- /section:custom/widget-box.toolbar -->
								</div>

								<div class="widget-body">
									<div class="widget-main">
										<p class="alert alert-info">
											Nunc aliquam enim ut arcu aliquet adipiscing. Fusce dignissim
											volutpat justo non consectetur. Nulla fringilla eleifend
											consectetur.
										</p>
										<p class="alert alert-success">
											Raw denim you probably haven't heard of them jean shorts
											Austin.
										</p>
									</div>
								</div>
							</div>

							<!-- /section:custom/widget-box -->
						</div>

						<div class="col-xs-12 col-sm-6 widget-container-col">
							<div class="widget-box widget-color-blue">
								<!-- #section:custom/widget-box.options -->
								<div class="widget-header">
									<h5 class="widget-title bigger lighter">
										<i class="ace-icon fa fa-table"></i> Tables & Colors
									</h5>

									<div class="widget-toolbar widget-toolbar-light no-border">
										<select id="simple-colorpicker-1" class="hide">
											<option selected="" data-class="blue" value="#307ECC">
												#307ECC
											</option>
											<option data-class="blue2" value="#5090C1">
												#5090C1
											</option>
											<option data-class="blue3" value="#6379AA">
												#6379AA
											</option>
											<option data-class="green" value="#82AF6F">
												#82AF6F
											</option>
											<option data-class="green2" value="#2E8965">
												#2E8965
											</option>
											<option data-class="green3" value="#5FBC47">
												#5FBC47
											</option>
											<option data-class="red" value="#E2755F">
												#E2755F
											</option>
											<option data-class="red2" value="#E04141">
												#E04141
											</option>
											<option data-class="red3" value="#D15B47">
												#D15B47
											</option>
											<option data-class="orange" value="#FFC657">
												#FFC657
											</option>
											<option data-class="purple" value="#7E6EB0">
												#7E6EB0
											</option>
											<option data-class="pink" value="#CE6F9E">
												#CE6F9E
											</option>
											<option data-class="dark" value="#404040">
												#404040
											</option>
											<option data-class="grey" value="#848484">
												#848484
											</option>
											<option data-class="default" value="#EEE">
												#EEE
											</option>
										</select>
									</div>
								</div>

								<!-- /section:custom/widget-box.options -->
								<div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-striped table-bordered table-hover">
											<thead class="thin-border-bottom">
												<tr>
													<th>
														<i class="ace-icon fa fa-user"></i> User
													</th>

													<th>
														<i>@</i> Email
													</th>
													<th class="hidden-480">
														Status
													</th>
												</tr>
											</thead>

											<tbody>
												<tr>
													<td class="">
														Alex
													</td>

													<td>
														<a href="#">alex@email.com</a>
													</td>

													<td class="hidden-480">
														<span class="label label-warning">Pending</span>
													</td>
												</tr>

												<tr>
													<td class="">
														Fred
													</td>

													<td>
														<a href="#">fred@email.com</a>
													</td>

													<td class="hidden-480">
														<span
															class="label label-success arrowed-in arrowed-in-right">Approved</span>
													</td>
												</tr>

												<tr>
													<td class="">
														Jack
													</td>

													<td>
														<a href="#">jack@email.com</a>
													</td>

													<td class="hidden-480">
														<span class="label label-warning">Pending</span>
													</td>
												</tr>

												<tr>
													<td class="">
														John
													</td>

													<td>
														<a href="#">john@email.com</a>
													</td>

													<td class="hidden-480">
														<span class="label label-inverse arrowed">Blocked</span>
													</td>
												</tr>

												<tr>
													<td class="">
														James
													</td>

													<td>
														<a href="#">james@email.com</a>
													</td>

													<td class="hidden-480">
														<span class="label label-info arrowed-in arrowed-in-right">Online</span>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<!-- /.span -->
					</div>
					<!-- /.row -->

					<div class="space-24"></div>

					<div class="row">
						<div class="col-xs-12 col-sm-6 widget-container-col">
							<div class="widget-box widget-color-orange collapsed">
								<!-- #section:custom/widget-box.options.collapsed -->
								<div class="widget-header widget-header-small">
									<h6 class="widget-title">
										<i class="ace-icon fa fa-sort"></i> Small Header & Collapsed
									</h6>

									<div class="widget-toolbar">
										<a href="#" data-action="settings"> <i
											class="ace-icon fa fa-cog"></i> </a>

										<a href="#" data-action="reload"> <i
											class="ace-icon fa fa-refresh"></i> </a>

										<a href="#" data-action="collapse"> <i
											class="ace-icon fa fa-plus" data-icon-show="fa-plus"
											data-icon-hide="fa-minus"></i> </a>

										<a href="#" data-action="close"> <i
											class="ace-icon fa fa-times"></i> </a>
									</div>
								</div>

								<!-- /section:custom/widget-box.options.collapsed -->
								<div class="widget-body">
									<div class="widget-main">
										<p class="alert alert-info">
											Lorem ipsum dolor sit amet, consectetur adipiscing elit.
											Quisque commodo massa sed ipsum porttitor facilisis.
										</p>
									</div>
								</div>
							</div>
						</div>

						<div class="col-xs-12 col-sm-6 widget-container-col">
							<div class="widget-box">
								<!-- #section:custom/widget-box.header.options -->
								<div class="widget-header widget-header-large">
									<h4 class="widget-title">
										Big Header
									</h4>

									<div class="widget-toolbar">
										<a href="#" data-action="settings"> <i
											class="ace-icon fa fa-cog"></i> </a>

										<a href="#" data-action="reload"> <i
											class="ace-icon fa fa-refresh"></i> </a>

										<a href="#" data-action="collapse"> <i
											class="ace-icon fa fa-chevron-up"></i> </a>

										<a href="#" data-action="close"> <i
											class="ace-icon fa fa-times"></i> </a>
									</div>
								</div>

								<!-- /section:custom/widget-box.header.options -->
								<div class="widget-body">
									<div class="widget-main">
										<p class="alert alert-info">
											Lorem ipsum dolor sit amet, consectetur adipiscing elit.
											Quisque commodo massa sed ipsum porttitor facilisis.
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="space-24"></div>

					<div class="row">
						<div class="col-sm-6 widget-container-col">
							<!-- #section:custom/widget-box.options.transparent -->
							<div class="widget-box transparent">
								<div class="widget-header">
									<h4 class="widget-title lighter">
										Transparent Box
									</h4>

									<div class="widget-toolbar no-border">
										<a href="#" data-action="settings"> <i
											class="ace-icon fa fa-cog"></i> </a>

										<a href="#" data-action="reload"> <i
											class="ace-icon fa fa-refresh"></i> </a>

										<a href="#" data-action="collapse"> <i
											class="ace-icon fa fa-chevron-up"></i> </a>

										<a href="#" data-action="close"> <i
											class="ace-icon fa fa-times"></i> </a>
									</div>
								</div>

								<div class="widget-body">
									<div
										class="widget-main padding-6 no-padding-left no-padding-right">
										Lorem ipsum dolor sit amet, consectetur adipiscing elit.
										Quisque commodo massa sed ipsum porttitor facilisis. Nullam
										interdum massa vel nisl fringilla sed viverra erat tincidunt.
										Phasellus in ipsum velit. Maecenas id erat vel sem convallis
										blandit. Nunc aliquam enim ut arcu aliquet adipiscing. Fusce
										dignissim volutpat justo non consectetur.
									</div>
								</div>
							</div>

							<!-- /section:custom/widget-box.options.transparent -->
						</div>

						<div class="col-sm-6 widget-container-col">
							<div class="widget-box transparent">
								<div class="widget-header">
									<h4 class="widget-title lighter">
										Tabs With Scroll
									</h4>

									<div class="widget-toolbar no-border">
										<ul class="nav nav-tabs" id="myTab2">
											<li class="active">
												<a data-toggle="tab" href="#home2">Home</a>
											</li>

											<li>
												<a data-toggle="tab" href="#profile2">Profile</a>
											</li>

											<li>
												<a data-toggle="tab" href="#info2">Info</a>
											</li>
										</ul>
									</div>
								</div>

								<div class="widget-body">
									<div
										class="widget-main padding-12 no-padding-left no-padding-right">
										<div class="tab-content padding-4">
											<div id="home2" class="tab-pane in active">
												<!-- #section:custom/scrollbar.horizontal -->
												<div class="scrollable-horizontal" data-size="800">
													<b>Horizontal Scroll</b> Lorem ipsum dolor sit amet,
													consectetur adipiscing elit. Quisque commodo massa sed
													ipsum porttitor facilisis. Nullam interdum massa vel nisl
													fringilla sed viverra erat tincidunt. Phasellus in ipsum
													velit. Maecenas id erat vel sem convallis blandit. Nunc
													aliquam enim ut arcu aliquet adipiscing. Fusce dignissim
													volutpat justo non consectetur. Lorem ipsum dolor sit amet,
													consectetur adipiscing elit. Quisque commodo massa sed
													ipsum porttitor facilisis. Nullam interdum massa vel nisl
													fringilla sed viverra erat tincidunt. Phasellus in ipsum
													velit. Maecenas id erat vel sem convallis blandit. Nunc
													aliquam enim ut arcu aliquet adipiscing. Fusce dignissim
													volutpat justo non consectetur. Lorem ipsum dolor sit amet,
													consectetur adipiscing elit. Quisque commodo massa sed
													ipsum porttitor facilisis. Nullam interdum massa vel nisl
													fringilla sed viverra erat tincidunt. Phasellus in ipsum
													velit. Maecenas id erat vel sem convallis blandit. Nunc
													aliquam enim ut arcu aliquet adipiscing. Fusce dignissim
													volutpat justo non consectetur.
												</div>

												<!-- /section:custom/scrollbar.horizontal -->
											</div>

											<div id="profile2" class="tab-pane">
												<div class="scrollable" data-size="100" data-position="left">
													<b>Scroll on Left</b> Lorem ipsum dolor sit amet,
													consectetur adipiscing elit. Quisque commodo massa sed
													ipsum porttitor facilisis. Nullam interdum massa vel nisl
													fringilla sed viverra erat tincidunt. Phasellus in ipsum
													velit. Maecenas id erat vel sem convallis blandit. Nunc
													aliquam enim ut arcu aliquet adipiscing. Fusce dignissim
													volutpat justo non consectetur. Lorem ipsum dolor sit amet,
													consectetur adipiscing elit. Quisque commodo massa sed
													ipsum porttitor facilisis. Nullam interdum massa vel nisl
													fringilla sed viverra erat tincidunt. Phasellus in ipsum
													velit. Maecenas id erat vel sem convallis blandit. Nunc
													aliquam enim ut arcu aliquet adipiscing. Fusce dignissim
													volutpat justo non consectetur. Lorem ipsum dolor sit amet,
													consectetur adipiscing elit. Quisque commodo massa sed
													ipsum porttitor facilisis. Nullam interdum massa vel nisl
													fringilla sed viverra erat tincidunt. Phasellus in ipsum
													velit. Maecenas id erat vel sem convallis blandit. Nunc
													aliquam enim ut arcu aliquet adipiscing. Fusce dignissim
													volutpat justo non consectetur.
												</div>
											</div>

											<div id="info2" class="tab-pane">
												<div class="scrollable" data-size="100">
													<b>Scroll # 3</b> Lorem ipsum dolor sit amet, consectetur
													adipiscing elit. Quisque commodo massa sed ipsum porttitor
													facilisis. Nullam interdum massa vel nisl fringilla sed
													viverra erat tincidunt. Phasellus in ipsum velit. Maecenas
													id erat vel sem convallis blandit. Nunc aliquam enim ut
													arcu aliquet adipiscing. Fusce dignissim volutpat justo non
													consectetur. Lorem ipsum dolor sit amet, consectetur
													adipiscing elit. Quisque commodo massa sed ipsum porttitor
													facilisis. Nullam interdum massa vel nisl fringilla sed
													viverra erat tincidunt. Phasellus in ipsum velit. Maecenas
													id erat vel sem convallis blandit. Nunc aliquam enim ut
													arcu aliquet adipiscing. Fusce dignissim volutpat justo non
													consectetur. Lorem ipsum dolor sit amet, consectetur
													adipiscing elit. Quisque commodo massa sed ipsum porttitor
													facilisis. Nullam interdum massa vel nisl fringilla sed
													viverra erat tincidunt. Phasellus in ipsum velit. Maecenas
													id erat vel sem convallis blandit. Nunc aliquam enim ut
													arcu aliquet adipiscing. Fusce dignissim volutpat justo non
													consectetur.
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content -->
		</div>
		</div>


			<!-- basic scripts -->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${ctxStatic}/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${ctxStatic}/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		<script src="${ctxStatic}/assets/js/bootstrap.js"></script>

		<!-- page specific plugin scripts -->
		<script src="${ctxStatic}/assets/js/jquery-ui.custom.js"></script>
		<script src="${ctxStatic}/assets/js/jquery.ui.touch-punch.js"></script>

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

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
			
				$('#simple-colorpicker-1').ace_colorpicker({pull_right:true}).on('change', function(){
					var color_class = $(this).find('option:selected').data('class');
					var new_class = 'widget-box';
					if(color_class != 'default')  new_class += ' widget-color-'+color_class;
					$(this).closest('.widget-box').attr('class', new_class);
				});
			
			
				// scrollables
				$('.scrollable').each(function () {
					var $this = $(this);
					$(this).ace_scroll({
						size: $this.attr('data-size') || 100,
						//styleClass: 'scroll-left scroll-margin scroll-thin scroll-dark scroll-light no-track scroll-visible'
					});
				});
				$('.scrollable-horizontal').each(function () {
					var $this = $(this);
					$(this).ace_scroll(
					  {
						horizontal: true,
						styleClass: 'scroll-top',//show the scrollbars on top(default is bottom)
						size: $this.attr('data-size') || 500,
						mouseWheelLock: true
					  }
					).css({'padding-top': 12});
				});
				
				$(window).on('resize.scroll_reset', function() {
					$('.scrollable-horizontal').ace_scroll('reset');
				});
			
			
				/**
				//or use slimScroll plugin
				$('.slim-scrollable').each(function () {
					var $this = $(this);
					$this.slimScroll({
						height: $this.data('height') || 100,
						railVisible:true
					});
				});
				*/
				
			
				/**$('.widget-box').on('setting.ace.widget' , function(e) {
					e.preventDefault();
				});*/
			
				/**
				$('.widget-box').on('show.ace.widget', function(e) {
					//e.preventDefault();
					//this = the widget-box
				});
				$('.widget-box').on('reload.ace.widget', function(e) {
					//this = the widget-box
				});
				*/
			
				//$('#my-widget-box').widget_box('hide');
			
				
			
				// widget boxes
				// widget box drag & drop example
			    $('.widget-container-col').sortable({
			        connectWith: '.widget-container-col',
					items:'> .widget-box',
					handle: ace.vars['touch'] ? '.widget-header' : false,
					cancel: '.fullscreen',
					opacity:0.8,
					revert:true,
					forceHelperSize:true,
					placeholder: 'widget-placeholder',
					forcePlaceholderSize:true,
					tolerance:'pointer',
					start: function(event, ui) {
						//when an element is moved, it's parent becomes empty with almost zero height.
						//we set a min-height for it to be large enough so that later we can easily drop elements back onto it
						ui.item.parent().css({'min-height':ui.item.height()})
						//ui.sender.css({'min-height':ui.item.height() , 'background-color' : '#F5F5F5'})
					},
					update: function(event, ui) {
						ui.item.parent({'min-height':''})
						//p.style.removeProperty('background-color');
					}
			    });
				
			
			
			});
		</script>

		<!-- the following scripts are used in demo only for onpage help and you don't need them -->
		<link rel="stylesheet" href="${ctxStatic}/assets/css/ace.onpage-help.css" />

		<script type="text/javascript"> ace.vars['base'] = '${ctxStatic}'; </script>
		<script src="${ctxStatic}/assets/js/ace/elements.onpage-help.js"></script>
		<script src="${ctxStatic}/assets/js/ace/ace.onpage-help.js"></script>

	</body>
</html>