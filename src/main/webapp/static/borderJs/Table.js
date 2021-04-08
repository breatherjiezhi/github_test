$.extend({
    //方法名有参数
    Table: function (option) {
        //默认方法
        var defaults = {
            dataId: "border_area",
            tableTop:"tableTop",
            company:"%",
            header: ["A", "B", "C", "D"],
            headerChange:true,
            headerLen: ["24", "24", "24", "24"],//表头字段
            headerColor:"#fff",
            headerBgcolor:"#0c2651",
            Num: 4,
            len:12,
            roll:true,//是否滚动
            even:"#fff",
            odd:"blue",
            ultId:"ult"

        }
        //覆盖
        var opt = $.extend(defaults, option)
        var str = $('<div class="tableArea">' +
            '<div class= "tableTop" id="'+opt.tableTop+'" style="background-color:'+opt.headerBgcolor+';color:'+opt.headerColor+'"></div >' +
            '<ul class="ult" id="'+opt.ultId+'" >' +
            '</ul>' +
            '</div > ');
        $("#" + opt.dataId).append(str);
        if(opt.headerChange==true){
            var len = opt.header.length;
            var lenTr = parseInt((100 / len));
            for (var i = 0; i < opt.header.length; i++) {
                $('<span style="width: ' + lenTr + ''+opt.company+';">' + opt.header[i] + '</span>').appendTo($("#"+opt.tableTop))
            };
        }else{
            for (var i = 0; i < opt.header.length; i++) {
                $('<span style="width: ' + opt.headerLen[i] + ''+opt.company+';">' + opt.header[i] + '</span>').appendTo($("#"+opt.tableTop))
            };
        }

        if(opt.roll==true){
            var mtop = 0;
            var speedhq = 40;
            var MyMarhq;
            var numTan=1;
            function moveData() {
                mtop--;
                document.getElementsByName("data")[0].style.marginTop = mtop + "px";
                //判断第一个元素是否已经滚到它自身的高度
                if (parseInt(document.getElementsByName("data")[0].style.marginTop) == -45) {
                    mtop = 0;
                    //重新获取一把第一个元素
                    var first = document.getElementsByName("data")[0];
                    //把这个时候获取到的第一个元素的margin-top设置为0
                    first.style.marginTop = "0px";
                    //把这个元素追加到父元素的末尾 （父元素.appendChild(子元素)）
                    numTan++
                    if(opt.len%2!=0){
                        if(numTan%2==0){
                            first.style.background = opt.odd;
                        }else{
                            first.style.background = opt.even;
                        }

                    }
                    document.getElementById(ultId).appendChild(first);
                }
            }
            //上浮滚动
            if (opt.len > opt.Num) {
                MyMarhq = setInterval(moveData, speedhq);
                $(".ult").hover(function () {
                    clearInterval(MyMarhq);

                }, function () {
                    clearInterval(MyMarhq);
                    MyMarhq = setInterval(moveData, speedhq);

                });
            }

        }else{
            $(".ult").css("overflow","auto")
            $(".ult").addClass("scrollBar");

        }

        $("#ult li span").livequery("mouseenter",function(){
            var x=event.clientX;
            var y=event.clientY;
            $("#showDetail").text($(this).text())
            $("#showDetail").css({
                "left":x,
                "top":y
            });
        }).livequery("mouseleave",function(){
            $("#showDetail").html("")

        })

    }



});