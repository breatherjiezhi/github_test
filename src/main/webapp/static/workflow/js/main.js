
$('.deleteinput').livequery('click',function(){
    $(this).parent().parent().remove()

})
$( "#addstaticinput" ).draggable({
    helper: "clone",
    revert: "invalid",
});


var inputnum=0;

var outputnum=0;
$('.middle').droppable({
    accept:'.menu-item ',
    hoverClass: 'zidhighlight1',
    drop:function(event,ui){
        if(event.originalEvent.target.innerText=="静态信息读取"){

            $(this).append('<div class="static-input static-input-in" id="input'+inputnum+'" style="top:'+(ui.offset.top-200)+'px;left:'+(ui.offset.left-186)+'px" >\n' +
                '              \n' +
                '                <div class="input-icon">\n' +
                '                    <img src="img/输入.png" alt="">\n' +
                '                </div>\n' +
                '                <span style="position:relative;left:20px">  静态输入控件'+inputnum+'</span>\n' +
                '            <ul class="input-li">\n' +
                '              \n' +
                '                <li class="deleteinput"><img src="img/删 除.png" alt=""></li>\n' +
                '            </ul>\n' +
                '            </div>\n');
            inputnum++
        }
        else{
            $(this).append(' <div class="static-input static-output" id="output'+outputnum+'" style="top:'+(ui.offset.top-200)+'px;left:'+(ui.offset.left-186)+'px">\n' +
                '              \n' +
                '                <div class="input-icon" style="background-color: #ffd54e;">\n' +
                '                    <img src="img/输出.png" alt="" style="width: 50%;">\n' +
                '                </div>\n' +
                '                <span style="position:relative;left:20px" class="inputname">表格输出控件'+outputnum+'</span>\n' +
                '            <ul class="input-li">\n' +
                '               \n' +
                '\n' +
                '                <li class="deleteinput"><img src="img/删 除.png" alt=""></li>\n' +
                '            </ul>\n' +
                '            </div>')
            outputnum++
        }
    }
})
var inputform;

$('.static-input-in').livequery('click',function () {

    inputform = $(this);
    var text = inputform.find('span').text()
    $('#workshop_name div input').prop('value',text);
    $('#workshop_out div select option').remove();
    for(var i=0;i<$('.static-output').length;i++){
        var txt =  $('.static-output').eq(i).find('span').text();
        $('#workshop_out div select').append('<option value="'+txt+'">'+txt+'</option>')
    }
})
$('#workshop_name div input').change(function(){
    inputform.find('span').text( $('#workshop_name div input').val())
});

$('#confirmbutton').click(function(){

    if($('#workshop_select div select').val()=='人员信息'){
        // $.ajax({
        //     url:"http://172.16.5.143:8181/rest/testmodule/testModule/list?iInt=2",
        //     async:false,
        //     success:function(result){
        //   console.log(result)
        //     }
        // });
        var hums ={"MESSAGE":"成功","CODE":"0","COLUMN":{"COLUMN":["姓名","工作","项目个数"],"CONTENT":["employeeName","employeeJob","projectNumber"]},"RESULT":{"pageNo":1,"pageSize":10,"count":2,"list":[{"id":"9a27703683e14d1ea4a983b10b6ccc10","orderBy":"","groupBy":"","pageNo":1,"pageSize":10,"isNewRecord":false,"remarks":"","createDate":"2020-03-31 09:54:12","updateDate":"2020-03-31 09:54:12","extendMap":null,"totalCount":null,"totalDate":null,"totalType":null,"employeeName":"王三","employeeJob":"设计","projectNumber":"5","columnList":null},{"id":"d0e8abd9a682445d86b63956f2a6d55f","orderBy":"","groupBy":"","pageNo":1,"pageSize":10,"isNewRecord":false,"remarks":"","createDate":"2020-03-31 09:53:58","updateDate":"2020-03-31 09:53:58","extendMap":null,"totalCount":null,"totalDate":null,"totalType":null,"employeeName":"李天柱","employeeJob":"测试","projectNumber":"3","columnList":null}],"firstResult":0,"maxResults":10,"pageHtml":"<ul>\n<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>\n<li class=\"active\"><a href=\"javascript:\">1</a></li>\n<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>\n<li class=\"disabled controls\"><a href=\"javascript:\">当前 <input type=\"text\" value=\"1\" onkeypress=\"var e=window.event||event;var c=e.keyCode||e.which;if(c==13)page(this.value,10,'');\" onclick=\"this.select();\"/> / <input type=\"text\" value=\"10\" onkeypress=\"var e=window.event||event;var c=e.keyCode||e.which;if(c==13)page(1,this.value,'');\" onclick=\"this.select();\"/> 条，共 2 条</a></li>\n</ul>\n<div style=\"clear:both;\"></div>","html":"<div class=\"fixed-table-pagination\" style=\"display: block;\"><div class=\"pull-left pagination-detail\"><span class=\"pagination-info\">显示第 1 到第 2 条记录，总共 2 条记录</span><span class=\"page-list\">每页显示 <span class=\"btn-group dropup\"><button type=\"button\" class=\"btn btn-sm btn-primary dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"page-size\">10</span> <span class=\"caret\"></span></button><ul class=\"dropdown-menu\" role=\"menu\"><li class=\"active\"><a href=\"javascript:page(1,10,'');\">10</a></li><li class=\"\"><a href=\"javascript:page(1,25,'');\">25</a></li><li class=\"\"><a href=\"javascript:page(1,50,'');\">50</a></li><li class=\"\"><a href=\"javascript:page(1,100,'');\">100</a></li><li class=\"\"><a href=\"javascript:page(1,1000,'');\">1000</a></li><li class=\"\"><a href=\"javascript:page(1,100000000,'');\">全部</a></li></ul></span> 条记录</span></div><div class=\"pull-right pagination-roll\"><ul class=\"pagination pagination-outline\"><li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"></i></a></li>\n<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"></i></a></li>\n<li class=\"paginate_button active\"><a href=\"javascript:\">1</a></li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"></i></a></li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-right\"></i></a></li>\n</ul></div></div>"}}
        console.log(hums);
        var id1 =inputform.attr('id')
        for(var i=0;i<$('.static-output').length;i++){
            var txt =  $('.static-output').eq(i).find('span').text();
            var id2 = $('.static-output').eq(i).attr('id');
            if(txt == $('#workshop_out div select').val()){
                jsPlumb.connect({
                    source: id1,
                    target: id2,
                    endpoint: 'Rectangle',
                    endpointStyle: { fill: 'lightgray', outlineStroke: 'darkgray', outlineWidth: 1 },
                    overlays: [ ['Arrow', { width: 12, length: 12, location: 0.5 }] ],
                    connector: ['Flowchart'],
                    anchor: "Continuous"

                })
                jsPlumb.draggable(id1)
                jsPlumb.draggable(id2)
                $('.static-output').eq(i).find('table').remove()
                $('.static-output').eq(i).append('    <table style="width: 290px;color:#ccc">\n' +
                    '                <thead>\n' +
                    '                <th>姓名</th>\n' +
                    '                <th>工作</th>\n' +
                    '                <th>项目个数</th>\n' +
                    '                </thead>\n' +
                    '                <tbody>\n' +
                    '                <tr>\n' +
                    '                    <td>王三</td>\n' +
                    '                    <td>设计</td>\n' +
                    '                    <td>5</td>\n' +
                    '                </tr>\n' +
                    '                <tr>\n' +
                    '                    <td>李天柱</td>\n' +
                    '                    <td>测试</td>\n' +
                    '                    <td>3</td>\n' +
                    '                </tr>\n' +
                    '\n' +
                    '                </tbody>\n' +
                    '\n' +
                    '            </table>\n')
            }

        }

    }

    else if($('#workshop_select div select').val()=='项目信息'){
        // $.ajax({
        //     url:"http://172.16.5.143:8181/rest/testmodule/testModule/list?iInt=2",
        //     async:false,
        //     success:function(result){
        //   console.log(result)
        //     }
        // });
        var hums ={"MESSAGE":"成功","CODE":"0","COLUMN":{"COLUMN":["姓名","工作","项目个数"],"CONTENT":["employeeName","employeeJob","projectNumber"]},"RESULT":{"pageNo":1,"pageSize":10,"count":2,"list":[{"id":"9a27703683e14d1ea4a983b10b6ccc10","orderBy":"","groupBy":"","pageNo":1,"pageSize":10,"isNewRecord":false,"remarks":"","createDate":"2020-03-31 09:54:12","updateDate":"2020-03-31 09:54:12","extendMap":null,"totalCount":null,"totalDate":null,"totalType":null,"employeeName":"王三","employeeJob":"设计","projectNumber":"5","columnList":null},{"id":"d0e8abd9a682445d86b63956f2a6d55f","orderBy":"","groupBy":"","pageNo":1,"pageSize":10,"isNewRecord":false,"remarks":"","createDate":"2020-03-31 09:53:58","updateDate":"2020-03-31 09:53:58","extendMap":null,"totalCount":null,"totalDate":null,"totalType":null,"employeeName":"李天柱","employeeJob":"测试","projectNumber":"3","columnList":null}],"firstResult":0,"maxResults":10,"pageHtml":"<ul>\n<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>\n<li class=\"active\"><a href=\"javascript:\">1</a></li>\n<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>\n<li class=\"disabled controls\"><a href=\"javascript:\">当前 <input type=\"text\" value=\"1\" onkeypress=\"var e=window.event||event;var c=e.keyCode||e.which;if(c==13)page(this.value,10,'');\" onclick=\"this.select();\"/> / <input type=\"text\" value=\"10\" onkeypress=\"var e=window.event||event;var c=e.keyCode||e.which;if(c==13)page(1,this.value,'');\" onclick=\"this.select();\"/> 条，共 2 条</a></li>\n</ul>\n<div style=\"clear:both;\"></div>","html":"<div class=\"fixed-table-pagination\" style=\"display: block;\"><div class=\"pull-left pagination-detail\"><span class=\"pagination-info\">显示第 1 到第 2 条记录，总共 2 条记录</span><span class=\"page-list\">每页显示 <span class=\"btn-group dropup\"><button type=\"button\" class=\"btn btn-sm btn-primary dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"page-size\">10</span> <span class=\"caret\"></span></button><ul class=\"dropdown-menu\" role=\"menu\"><li class=\"active\"><a href=\"javascript:page(1,10,'');\">10</a></li><li class=\"\"><a href=\"javascript:page(1,25,'');\">25</a></li><li class=\"\"><a href=\"javascript:page(1,50,'');\">50</a></li><li class=\"\"><a href=\"javascript:page(1,100,'');\">100</a></li><li class=\"\"><a href=\"javascript:page(1,1000,'');\">1000</a></li><li class=\"\"><a href=\"javascript:page(1,100000000,'');\">全部</a></li></ul></span> 条记录</span></div><div class=\"pull-right pagination-roll\"><ul class=\"pagination pagination-outline\"><li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"></i></a></li>\n<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"></i></a></li>\n<li class=\"paginate_button active\"><a href=\"javascript:\">1</a></li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-right\"></i></a></li>\n<li class=\"paginate_button next disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-right\"></i></a></li>\n</ul></div></div>"}}
        console.log(hums);
        var id1 =inputform.attr('id')
        for(var i=0;i<$('.static-output').length;i++){
            var txt =  $('.static-output').eq(i).find('span').text();
            if(txt == $('#workshop_out div select').val()){
                var id2 = $('.static-output').eq(i).attr('id');
                jsPlumb.connect({
                    source: id1,
                    target: id2,
                    endpoint: 'Rectangle',
                    endpointStyle: { fill: 'lightgray', outlineStroke: 'darkgray', outlineWidth: 1 },
                    overlays: [ ['Arrow', { width: 12, length: 12, location: 0.5 }] ],
                    connector: ['Flowchart'],
                    anchor: "Continuous"
                })
                jsPlumb.draggable(id1)
                jsPlumb.draggable(id2)
                $('.static-output').eq(i).find('table').remove()
                $('.static-output').eq(i).append('    <table style="width: 290px;color:#ccc">\n' +
                    '                <thead>\n' +
                    '                <th>项目名</th>\n' +
                    '                <th>负责人</th>\n' +
                    '                </thead>\n' +
                    '                <tbody>\n' +
                    '                <tr>\n' +
                    '                    <td>圣菲LCU机箱产线项目</td>\n' +
                    '                    <td>竺青</td>\n' +
                    '                </tr>\n' +
                    '                <tr>\n' +
                    '                    <td>二期集中仓储及科研项目</td>\n' +
                    '                    <td>张仁义</td>\n' +
                    '                </tr>\n' +
                    '                <tr>\n' +
                    '                    <td>航天五院智能物流项目</td>\n' +
                    '                    <td>倪宇鹏</td>\n' +
                    '                </tr>\n' +
                    '                <tr>\n' +
                    '                    <td>上海地铁WMS系统项目</td>\n' +
                    '                    <td>倪宇鹏</td>\n' +
                    '                </tr>\n' +
                    '                <tr>\n' +
                    '                    <td>上飞线边库智能工具箱项目</td>\n' +
                    '                    <td>倪宇鹏</td>\n' +
                    '                </tr>\n' +
                    '                <tr>\n' +
                    '                    <td>瑞肯耐特智能物流项目</td>\n' +
                    '                    <td>倪宇鹏</td>\n' +
                    '                </tr>\n' +
                    '\n' +
                    '                </tbody>\n' +
                    '\n' +
                    '            </table>\n')
            }

        }

    }

})

