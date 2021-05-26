<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<title></title>
<link href="${ctxStatic}/bootstrap-treeview/css/bootstrap-treeview.css" rel="stylesheet" type="text/css" />
<style type="text/css" id="css-style">
    .popfi{margin-left: 0px;width: 220px;}
    .popfi table{font-size: 14px;}
    .invis{width: 100%;margin-bottom: 5px;}
    .invis table{width: 100%;}
    .invis table th{padding:3px 0px;}
    .invis table td{padding:3px 0px;}
    .qrcss{margin-top: 20px;margin-left: 35%;text-align: center;}
    .tab1{width: 100%;}
    .tab1 table{width: 100%;border:solid; border-width:1px 0px 0px 1px;text-align:center;}
    .tab1 th{border:solid; border-width:0px 1px 1px 0px;text-align:center;}
    .tab1 td{border:solid; border-width:0px 1px 1px 0px;text-align:center;}
    .tex-l{text-align: left;}
    .tex-r{text-align: right;}
    .dotted{width:280px;height:0px;border-top:1px black dashed; margin-top: 20px;margin-left: -30px;}
</style>
<div class="row">
    <button id="printTest" onclick="printTest();" style="width: 80px;margin-left: 115px;">打印</button>
    <div id="printArea" style="margin-left: 50px;/*display: none;*/">
        <%--<div class="popfi">
            <div id="qrCode0" class="qrcss">
            </div>
            <div class="invis">
                <table>
                    <tbody>
                    <tr>
                        <td>取餐点：</td>
                        <td class="tex-r">新区01号</td>
                    </tr>
                    <tr>
                        <td>电气研发1</td>
                        <td class="tex-r">绿泉</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="tab1">
                <table>
                    <tbody>
                    <tr>
                        <th style="text-align:center;">A</th>
                        <th style="text-align:center;">B</th>
                        <th style="text-align:center;">C</th>
                    </tr>
                    <tr>
                        <td style="text-align:center;">5</td>
                        <td style="text-align:center;">6</td>
                        <td style="text-align:center;">8</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="tab1">
                <table>
                    <tbody>
                    <tr>
                        <th>序号</th>
                        <th style="">姓名</th>
                        <th colspan="2">套餐类别</th>
                    </tr>
                    <tr>
                        <td style="text-align:center;">1</td>
                        <td style="text-align:center;">张三</td>
                        <td style="text-align:center;">A</td>
                        <td style="text-align:center;">胡萝卜</td>
                    </tr>
                    <tr>
                        <td style="text-align:center;">2</td>
                        <td style="text-align:center;">李四</td>
                        <td style="text-align:center;">B</td>
                        <td style="text-align:center;">黄焖鸡米饭</td>
                    </tr>
                    </tbody>
                </table>
                <div class="dotted"></div>
            </div>
        </div>--%>
    </div>
</div>
<script type="text/javascript">
    var scripts = [null, '${ctxStatic}/assets/js/fuelux/fuelux.spinner.js', '${ctxStatic}/assets/js/date-time/bootstrap-datepicker.js', '${ctxStatic}/assets/js/date-time/bootstrap-datepicker.zh-CN.min.js',
        '${ctxStatic}/bootstrap-treeview/js/bootstrap-treeview.js', '${ctxStatic}/jquery/jquery-1.8.3.js', '${ctxStatic}/qrcode/qrcode.js', '${ctxStatic}/lodop/LodopFuncs.js', null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var count = 0;
        <c:forEach items="${orderStatisticlist}" var="orderStatisticMap" varStatus="orderStatisticStatus">
            var menuStraticArea1 = "";
            var menuStraticArea2 = "";
            <c:forEach items="${orderStatisticMap.menuStatisticlist}" var="menuStatisticMap">
                menuStraticArea1 += "<th style='text-align:center;'>${menuStatisticMap.menuName}</th>";
                menuStraticArea2 += "<td style='text-align:center;'>${menuStatisticMap.menuNum}</td>";
            </c:forEach>
            var orderDetailArea = "";
            <c:forEach items="${orderStatisticMap.orderDetaillist}" var="orderDetailMap" varStatus="status">
                orderDetailArea += "<tr><td style='text-align:center;'>${status.index+1}</td><td style='text-align:center;'>${orderDetailMap.userName}</td><td style='text-align:center;'>${orderDetailMap.menuName}</td><td style='text-align:center;'>${orderDetailMap.menuDetail}</td></tr>"
            </c:forEach>
            var printAreaInfo = " <div class='popfi'>";
            printAreaInfo += "<div id='qrCode" + count + "' class='qrcss'></div>";
            printAreaInfo += "<div class='invis'><table>";
            printAreaInfo += "<tr><td>取餐点：</td><td class='tex-r'>${orderStatisticMap.areaLocation}</td></tr>";
            printAreaInfo += "<tr><td>${orderStatisticMap.serviceUnitName}</td><td class='tex-r'>${orderStatisticMap.restaurantName}</td></tr>";
            printAreaInfo += "</table></div>";
            printAreaInfo += "<div class='tab1'><table>";
            printAreaInfo += "<tr>" + menuStraticArea1 + "</tr>";
            printAreaInfo += "<tr>" + menuStraticArea2 + "</tr>";
            printAreaInfo += "</table></div>";
            printAreaInfo += "<div  class='tab1'><table>";
            printAreaInfo += "<tr><th>序号</th><th style=''>姓名</th><th colspan='2'>套餐类别</th></tr>";
            printAreaInfo += orderDetailArea;
            printAreaInfo += "</table></div>";
           // printAreaInfo += "<div>${orderStatisticStatus.index + 1}</div>";
            printAreaInfo += "<div class='dotted'/>";
            printAreaInfo += "</div>";
            $("#printArea").append(printAreaInfo);
            var qrCodeId = "qrCode" + count;
            // var qrcode = new QRCode(qrCodeId);
            var qrCodeObj = new QRCode(qrCodeId, {
                text: "${orderStatisticMap.qrCode}",
                width: 60,
                height: 60,
                colorDark : "#000000",
                colorLight : "#ffffff"
            });
            qrCodeObj.makeCode("${orderStatisticMap.qrCode}");
            count++;
        </c:forEach>

    });
    var LODOP;
    function printTest() {
        var printA = $("#printArea").find(".popfi").length;
        if (printA > 18) {
            alert("打印内容超长，请重新选择！");
            return;
        }
        var cssStyle = "<style>" + document.getElementById("css-style").innerHTML + "</style>";
        var textHtml = cssStyle + "<body>" + document.getElementById("printArea").innerHTML + "</body>";
        //$("#printArea").jqprint();
        LODOP=getLodop();
        LODOP.PRINT_INIT(); // 打印初始化
        LODOP.SET_PRINT_PAGESIZE(1,800,1750*printA,"");  // 设置纸张大小,纸张高度最大32500
        LODOP.ADD_PRINT_HTM(0,40,'100%','100%',textHtml); // 设置打印内容
        LODOP.PREVIEW();
    }



</script>