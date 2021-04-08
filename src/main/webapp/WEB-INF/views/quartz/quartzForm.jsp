<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="widget-body" id="editDivId">
	<div class="widget-main" > 
		<form:form id="inputForm" modelAttribute="scheduleJob"  class="form-horizontal">

<table width="100%">
			
	<tr>
		<td>

		<fieldset style="padding: 5px"><legend>生成CRON表达式</legend>
		<input type="hidden" id="nowdisplay" value="Daily">
		<table style="width: 100%" cellpadding="0" cellspacing="0">

			<tr>
				<td height="125" valign="top">

				<div class="tabpanel4" style="width: 450px" id="id3">
				<div class="tab-row" >
				<div class="tabbable tabs-left">
				<ul class="nav nav-tabs" id="myTab">
					  <li id ="MinutesTab" class="tab0">
						<a href="#" onClick="display('Minutes')" id="id16" >
							<span>按分钟</span>
						</a>
					  </li>
					  
					  <li id ="HourlyTab" class="tab1">
						<a href="#" onClick="display('Hourly')" id="id17">
							<span>按小时</span></a>
					  </li>
					  
					  <li id ="DailyTab" class="tab2"><a
						href="#" onClick="display('Daily')"
						id="id18"
						><span>按天</span></a>
					  </li>
					  
					  <li id ="WeeklyTab" class="tab3"><a
						href="#" onClick="display('Weekly')"
						id="id19"
						><span>按周</span></a>
					  </li>
					  
					  <li id ="MonthlyTab" class="tab4"><a
						href="#" onClick="display('Monthly')"
						id="id1a"
						><span>按月</span></a>
					  </li>
					  
					</ul>
				</div>
				</div>
				
				<div class="tab-panel"  id="Daily">
				<table>
				    <tr></br></br></tr>
					<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>每 <input type="text" style="width: 40px" value="1"
							name="txtDaily" id="txtDaily" /> 天
							
							</td>
					</tr>
				</table>
<!-- 				<table> -->
<!-- 					<tr> -->
<!-- 						<td>每 <input type="text" style="width: 40px" value="1" -->
<!-- 							name="txtDaily" id="txtDaily" /> 天</td> -->
<!-- 					</tr> -->
<!-- 				</table> -->
<!-- 				<label class="control-label col-xs-12 col-sm-2 no-padding"></label> -->
<!-- 				<div class="col-xs-12 col-sm-4"> -->
<!-- 					<div class="input-prepend input-append"> -->
<!-- 					<span class="add-on">每</span> -->
<!-- 					<input id="txtDaily" htmlEscape="false" value="1" class="form-control width-100" /> -->
<!-- 					<span class="add-on">天</span> -->
<!-- 					</div>	 -->
<!-- 				</div>	 -->
				</div>
				
				<div class="tab-panel"  id="Yearly" style="display:none">
				<table>
				    <tr></br></br></tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input type="radio" id="id6b"
							name="tabbedPanel:panel:radioGroup" value="radio93"
							checked="checked" class="wicket-id6a" /></td>
						<td>Every <select
							name="tabbedPanel:panel:radioGroup:firstChoiceMonth" id="id6c">
							<option selected="selected" value="January">January</option>
							<option value="February">February</option>
							<option value="March">March</option>
							<option value="April">April</option>
							<option value="May">May</option>
							<option value="June">June</option>
							<option value="July">July</option>
							<option value="August">August</option>
							<option value="September">September</option>
							<option value="October">October</option>
							<option value="November">November</option>
							<option value="December">December</option>
						</select> <input type="text" class="dayEntry" value="1"
							name="tabbedPanel:panel:radioGroup:txtFirstChoiceDay" id="id6d" /></td>
					</tr>
					<tr>
						<td><input type="radio" id="id6e"
							name="tabbedPanel:panel:radioGroup" value="radio96"
							class="wicket-id6a" /></td>
						<td>The <select
							name="tabbedPanel:panel:radioGroup:secondChoiceRank" id="id6f">
							<option selected="selected" value="First">First</option>
							<option value="Second">Second</option>
							<option value="Third">Third</option>
							<option value="Fourth">Fourth</option>
						</select><select name="tabbedPanel:panel:radioGroup:secondChoiceDay"
							id="id70">
							<option selected="selected" value="Monday">Monday</option>
							<option value="Tuesday">Tuesday</option>
							<option value="Wednesday">Wednesday</option>
							<option value="Thursday">Thursday</option>
							<option value="Friday">Friday</option>
							<option value="Saturday">Saturday</option>
							<option value="Sunday">Sunday</option>
						</select> of<select name="tabbedPanel:panel:radioGroup:secondChoiceMonth"
							id="id71">
							<option selected="selected" value="January">January</option>
							<option value="February">February</option>
							<option value="March">March</option>
							<option value="April">April</option>
							<option value="May">May</option>
							<option value="June">June</option>
							<option value="July">July</option>
							<option value="August">August</option>
							<option value="September">September</option>
							<option value="October">October</option>
							<option value="November">November</option>
							<option value="December">December</option>
						</select></td>
					</tr>
					

				</table>
				</div>

				<div class="tab-panel"  id="Monthly" style="display:none">
				<table>
				    <tr></br></br></tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>每 <input type="text" style="width: 40px" value="1"
							name="ChoiceMonth" id="ChoiceMonth" />
							个月的第
							<input type="text" style="width: 40px" value="1"
							name="ChoiceDay" id="ChoiceDay" />
							天</td>
					</tr>
					
				</table>

				</div>

				<div class="tab-panel"  id="Hourly" style="display:none">

				<table>
				    <tr></br></br></tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>每 <input type="text" style="width: 40px" value="1"
							name="txtHourly" id="txtHourly" /> 小时</td>
					</tr>
					
				</table>
				</div>

				<div class="tab-panel"  id="Weekly" style="display:none">

				<table>
				 	<tr></br></br></tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>
						<table>
							<tr>
								<td><input type="checkbox" id="id42"
									name="txtWeekly" value="2"
									class="wicket-id41"> </input></td>
								<td>&nbsp;&nbsp;周一</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>

						</table>
						</td>
						<td>
						<table>
							<tr>
								<td><input type="checkbox" id="id43"
									name="txtWeekly" value="3"
									class="wicket-id41" /></td>
								<td>&nbsp;&nbsp;周二</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</table>

						</td>
						<td>
						<table>
							<tr>
								<td><input type="checkbox" id="id44"
									name="txtWeekly" value="4"
									class="wicket-id41" /></td>
								<td>&nbsp;&nbsp;周三</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</table>
						</td>
						<td>

						<table>
							<tr>
								<td><input type="checkbox" id="id45"
									name="txtWeekly" value="5"
									class="wicket-id41" /></td>
								<td>&nbsp;&nbsp;周四</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>
						<table>
							<tr>
								<td><input type="checkbox" id="id46"
									name="txtWeekly" value="6"
									class="wicket-id41" /></td>
								<td>&nbsp;&nbsp;周五</td>
							</tr>
						</table>
						</td>
						<td>
						<table>

							<tr>
								<td><input type="checkbox" id="id47"
									name="txtWeekly" value="7"
									class="wicket-id41" /></td>

								<td>&nbsp;&nbsp;周六</td>
							</tr>
						</table>
						</td>
						<td>
						<table>
							<tr>
								<td><input type="checkbox" id="id48"
									name="txtWeekly" value="1"
									class="wicket-id41" /></td>
								<td>&nbsp;&nbsp;周日</td>

							</tr>
						</table>
						</td>
						<td></td>
					</tr>
					
				</table>
				</div>

				<div class="tab-panel"  id="Minutes" style="display:none">
					
				<table>
					<tr></br></br></tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>每 <input type="text" style="width: 40px" value="1"
							name="txtMinutes" id="txtMinutes" />分钟</td>
					</tr>
				</table>
				</div>
				
				
				
				<!-- 
				<input type="checkbox" id="firstRun" name="firstRun" onClick="checkFirstRun()" />-->
				<table cellpadding="0" cellspacing="0" id="firstRunTable">
							<tr></br></tr>
							<tr>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td>第一次运行时间:</td>
								<td><span id="id54">
								<table>
									<tr>
										<td><select
											id="hourPart">
											<option value="0">00</option>
											<option value="1">01</option>
											<option value="2">02</option>

											<option value="3">03</option>
											<option value="4">04</option>
											<option value="5">05</option>
											<option value="6">06</option>
											<option value="7">07</option>
											<option value="8">08</option>
											<option value="9">09</option>
											<option value="10">10</option>
											<option value="11">11</option>

											<option selected="selected" value="12" onchange="getExp()">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>

											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
										</select>点</td>

										<td><select
											id="minutePart">
											<option selected="selected" value="0" onchange="getExp()">00</option>
											<option value="1">01</option>
											<option value="2">02</option>
											<option value="3">03</option>
											<option value="4">04</option>
											<option value="5">05</option>

											<option value="6">06</option>
											<option value="7">07</option>
											<option value="8">08</option>
											<option value="9">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>

											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>

											<option value="24">24</option>
											<option value="25">25</option>
											<option value="26">26</option>
											<option value="27">27</option>
											<option value="28">28</option>
											<option value="29">29</option>
											<option value="30">30</option>
											<option value="31">31</option>
											<option value="32">32</option>

											<option value="33">33</option>
											<option value="34">34</option>
											<option value="35">35</option>
											<option value="36">36</option>
											<option value="37">37</option>
											<option value="38">38</option>
											<option value="39">39</option>
											<option value="40">40</option>
											<option value="41">41</option>

											<option value="42">42</option>
											<option value="43">43</option>
											<option value="44">44</option>
											<option value="45">45</option>
											<option value="46">46</option>
											<option value="47">47</option>
											<option value="48">48</option>
											<option value="49">49</option>
											<option value="50">50</option>

											<option value="51">51</option>
											<option value="52">52</option>
											<option value="53">53</option>
											<option value="54">54</option>
											<option value="55">55</option>
											<option value="56">56</option>
											<option value="57">57</option>
											<option value="58">58</option>
											<option value="59">59</option>

										</select>分</td>
									</tr>
								</table>
								</span></td>
							</tr>
						</table>

				</td>
			</tr>

			<tr>
				<td height="40">
				<br><br>
				 CRON表达式：
				 <input type="text" style="width: 200px" name="cronExpression" id="cronExpression" />
				</td>
				
				
			</tr>
			
			<tr>
				<td height="40">
				<br>
				    <label class="pull-left inline" style="padding-left: 18px">是否禁用：</label>
					<input id="jobStatus" value = "No" checked="checked" type="checkbox" class="ace ace-switch ace-switch-5" />
					<span class="lbl"></span>
				</td>
				
			</tr>
			<tr>
				<td height="40" style="padding-left: 100px">
				<button class="button" name="btnGenerate" id="idf" onClick="getExp()">
				 生成</button>&nbsp;&nbsp;
				 <button class="button" name="btnGenerate" id="idf" onClick="ret()">
				 确认返回</button>
				 </td>
				 </tr>

		</table>

		</fieldset>

		</td>

	</tr>

	<tr>

		<td></td>
	</tr>

</table>


			
		</form:form>
 	</div>
</div>
<div id="selectOfficeTreeDiv" class="hide widget-body">
	<div class="widget-main padding-8">
		<div id="popuptreeview" class="" data-id="" data-text=""></div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$("#houseType").val('${storeHouse.houseType}');	
		var houseCode="${storeHouse.houseCode}";
		if(houseCode!=null && houseCode!=""){
			$("#inputForm #houseCode").attr("readonly", true);
		}
	});

	function test()
	{
		alert("123");
	}
	
	function getExp()
	{
		var cronExpression = "-1";
		nowdisplay = document.getElementById("nowdisplay").value;
		if(nowdisplay == "Minutes")
		{
			txtMinutes = document.getElementById("txtMinutes").value;
			hourPart = document.getElementById("hourPart").value;
			minutePart = document.getElementById("minutePart").value;
			cronExpression = "0 "+minutePart+"/"+txtMinutes+" "+hourPart+"/1 * * ?";
		}
		else if(nowdisplay == "Hourly")
		{
			
			
			txtHourly = document.getElementById("txtHourly").value;
			hourPart = document.getElementById("hourPart").value;
			minutePart = document.getElementById("minutePart").value;
			cronExpression = "0 "+minutePart+" "+hourPart+"/"+txtHourly+" * * ?";
			
		}
		else if(nowdisplay == "Daily")
		{
			
			
			txtDaily = document.getElementById("txtDaily").value;
			hourPart = document.getElementById("hourPart").value;
			minutePart = document.getElementById("minutePart").value;
			cronExpression = "0 "+minutePart+" "+hourPart+" 1/"+txtDaily+" * ?";
		}
		else if(nowdisplay == "Weekly")
		{
			var week = "";
			var huang = document.all['txtWeekly'];
			for(i = 0;i < huang.length;i++){
				if(huang[i].checked == true)
				{
				   week = week+huang[i].value+",";
				}
  		  	}
			
			if(week.length==0)
			{
			    alert(" 必须选择日期!");
				return;
			}
			week = week.substring(0,week.length-1);
			
			hourPart = document.getElementById("hourPart").value;
			minutePart = document.getElementById("minutePart").value;
			cronExpression = "0 "+minutePart+" "+hourPart+" ? * "+week+" * ";

				
		}
		else if(nowdisplay == "Monthly")
		{
			ChoiceMonth=document.getElementById("ChoiceMonth").value;
			ChoiceDay=document.getElementById("ChoiceDay").value;
			hourPart = document.getElementById("hourPart").value;
			minutePart = document.getElementById("minutePart").value;
			cronExpression = "0 "+minutePart+" "+hourPart+" "+ChoiceDay+" 1/"+ChoiceMonth+" ? * ";
		}
		if(cronExpression != "-1")
		{
			document.getElementById("cronExpression").value =cronExpression ;
		}
	
	}
	
	function ret()
	{
		cronExpression=	document.getElementById("cronExpression").value;
		var isSure = confirm('确认将表达式: \"'+cronExpression+"\" 返回给父窗口?");
		if(isSure)
		{
		alert("success");
		}
	}
	
	function checkFirstRun()
	{
		testCheck = document.getElementById("firstRun").checked;
		if(testCheck)
		{
			document.getElementById("firstRunTable").disabled = false;
		}
		else
		{
			document.getElementById("firstRunTable").disabled = "disabled";
		}
	}
	
	function display(nowdisplay)
	{
		cronThis = document.getElementById("nowdisplay").value;
		if(nowdisplay!=cronThis)
		{
			document.getElementById(cronThis).style.display = "none";
			//将老Tab置灰
			//document.getElementById(cronThis+"Tab").className = document.getElementById(nowdisplay+"Tab").className.substring(0,document.getElementById(nowdisplay+"Tab").className.length-10);
			//将新Tab亮起
			//document.getElementById(nowdisplay+"Tab").className = 	document.getElementById(nowdisplay+"Tab").className +" selected ";
			
			document.getElementById(nowdisplay).style.display = "block";
			document.getElementById("nowdisplay").value = nowdisplay;
			if(nowdisplay == "Weekly")
			{
				$("#cronExpression").val('');	
				
			}else{
				getExp();
			}
		}
			
	}
	
	
	var scripts = [ null,'${ctxStatic}/assets/js/fuelux/fuelux.spinner.js',null ];
	$('.page-content-area').ace_ajax('loadScripts',scripts,function() {				
		jQuery(function($) {	
	//选择框
				if(!ace.vars['touch']) {
// 					$('.chosen-select').chosen({allow_single_deselect:true}); 
					//resize the chosen on window resize
			
					$(window).off('resize.chosen').on('resize.chosen', function() {
						$('.chosen-select').each(function() {
							 var $this = $(this);
							 $this.next().css({'width': $this.parent().width()});
						});
					}).trigger('resize.chosen');
					//resize chosen on sidebar collapse/expand
					$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
						if(event_name != 'sidebar_collapsed') return;
						$('.chosen-select').each(function() {
							 var $this = $(this);
							 $this.next().css({'width': $this.parent().width()});
						});
					});
				}
		});
	})

</script>

<script>
  $(function () {
    $('#myTab a:first').tab('show');
    getExp();
  })
  $('#myTab a').click(function (e) {
  e.preventDefault();
  $(this).tab('show');
  });
</script>