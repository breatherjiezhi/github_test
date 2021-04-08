
//$(document).ready(function(){

var documentW = $(document).width() - 80; //padding：40 上下 -80
var documentH = $(document).height() - 80;
//编辑逻辑
// $("#editImgs").click(function() {
// 	// $(this).hide();
// 	$("#uploadImgs").show();
// 	$("#showImgs").hide();
// })
var imgSrc;
var gbOption = null;
var filesArr=[];
var fileClass = 'input-file';
var imgMaxNum=5;
//上传时调用的主方法
function imgsUpload(imgObj, obj,imgNum) {
    var lastInput = null;

    lastInput = $('.' + fileClass).last(); //第一次指向默认的input
    $(".uploadImgs-add").click(function () {
        lastInput = $('.' + fileClass).last(); //得到最后的input,如果是IE，会生成多个, 这个input是工具人性质
        $('.' + fileClass).each(function (k, v) {
            $(this).attr('id', '');
        })
        lastInput.attr('id', 'fileUpIpt'); //只给最后一个input正确的ID值和label联动，不用label联动  IE9行不通，提交会失败
        lastInput.unbind('change');
        lastInput.bind("change", function () {
            if (lastInput[0].files) { //谷歌 IE10以上          
                var win = window.URL || window.webkitURL;
                imgSrc = win.createObjectURL(lastInput[0].files[0]);
                imgObj.push(imgSrc);            
                filesArr.push(lastInput[0].files[0]);
                var upImgs = "<div class='show-img-div2'>" +
                    // "<span><img src='./upload-img/close.png'/></span>" +
                    "<span style='font-size:18px;color:#ff6162;cursor:pointer;'><i class='fa fa-times-circle' style='background-color:#fff;border-radius:50%;padding-left:1.5px;'></i></span>" +
                    "<img src='" + imgSrc + "'/>" +
                    "</div>";
                $("#labelId").before(upImgs);
                //$(".type-file-div").append(lastInput.clone()); //如果需要和IE方式一样的方式添加多个 input 去上传 打开这个注释

            } else { //IE9 IE8兼容
                var upImgs = "<div class='show-img-div2'><span style='font-size:18px;color:#ff6162;cursor:pointer;'><i style='background-color:#fff;border-radius:50%;padding-left:1.5px;' class='fa fa-times-circle'></i></span><img class='ie-imgshow' src='./upload-img/null.png' /></div>";
                $("#labelId").before(upImgs);
                lastInput[0].select();
                imgSrc = lastInput[0].value;
                imgObj.push(imgSrc);
                $(".ie-imgshow").last()[0].innerHTML = "";
                $(".ie-imgshow").last()[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src='" + imgSrc + "')";
                //添加input file 结构
                $(".type-file-div").append(lastInput.clone());

            }
            lastInput.val(''); //解决同图片删后 不能上传的 BUG
            if(imgObj.length>=imgNum || lastInput.parent().siblings(".show-img-div2").length>=imgNum){
                $("#labelId").hide()
            }
            //添加新图片后需要new imgPre  重置 预览函数
            obj = new imgPre(imgObj);
            gbOption = obj;
            createPreDiv();
            //删除图标绑定删除事件
            $(".show-img-div2").each(function (k, v) {
                $(this).find('span').unbind('click');
                $(this).find('span').bind("click", function () {
                    deleteImg(imgObj, k, obj,lastInput)
                })
                //和预览图片机制联动
                $(this).unbind('click');
                $(this).bind('click', function () {
                    gbOption = obj;
                    createPreDiv.imgMiddleShow(k)
                });
            })
        })
    })
}
//点击关闭图标删除图片数组
function deleteImg(imgObj, k, obj,lastInput) {
    console.log(k)
    imgObj.splice(k, 1);
    filesArr.splice(k, 1);

    // if(imgObj.length<imgMaxNum){
    //     $("#labelId").show()
    // }
    if(imgObj.length<imgMaxNum || lastInput.parent().siblings(".show-img-div2").length>=imgMaxNum){
        $("#labelId").show()
    }
    //删除点击的自己
    $(".show-img-div2:eq(" + k + ")").remove();
    //删除后重新绑定事件
    $(".show-img-div2").each(function (k, v) {
        $(this).find('span').unbind('click');
        $(this).find('span').bind("click", function () {
            deleteImg(imgObj, k)
        })
        //和预览图片机制联动
        $(this).unbind('click');
        $(this).bind('click', function () {
            gbOption = obj;
            createPreDiv.imgMiddleShow(k)
        });
    })

    //点击图片删除对应的input type=file
    if (!$('.' + fileClass).first()[0].files) {
        $('.' + fileClass).eq(k).remove(); //兼容IE，谷歌等如果需要和IE方式一样的方式，取消判断，input就不会删除
    }

    // $("#ifCanSave").text("0");  // 使得不可直接保存/提交
    // if(imgObj.length == 0){
    //     //如果删除的是最后一个，清空picture，使得可以直接保存/提交
    //     $("#picture").val('')
    // }else{
    //     debugger
    //     var picture = $("#picture").val().substring(1,$("#picture").val().length);
    //     var picArr = picture.split(',');
    //     picArr.splice(k,1);
    //     $("#picture").val(','+picArr.join(','));
    //
    // }
}

//图片返显预览专用， 上传时也会联动用到
function imgPre(imgObj, flag) {
    this.imgKey = 0;
    this.imgObj = imgObj;

    var _this = this;
    //flag 真说明只是纯预览功能，图片返显，   上传功能时不给flag 即可
    if (flag) {
        //动态在showImgs里添加图片
        var showImgs = '';
        this.imgObj.forEach(function (v, k) {
            showImgs += "<div class='show-img-div'><img src='" + v + "'/></div>";
        })
        $('#showImgs').append(showImgs);

        //预览图片绑定事件
        $('.show-img-div').each(function (k, v) {
            $(this).bind('click', function () {
                if (ispre) gbOption = ispre;
                createPreDiv.imgMiddleShow(k)
            });
        })
    }

}

//预览图片的DIV结构
function createPreDiv() {
    var divStr = "<div class='imgs-bg'>\n " +
        "<div class='middle'>\n " +
        "<div class='middle-img'>\n " +
        "<img src=''>\n" +
        "</div>\n" +
        "<div class='left'>\n" +
        // "<img src='./upload-img/left.png'>\n" +
        "<i class='fa fa-chevron-circle-left'></i>\n" +
        "</div>\n<div class='right'>\n " +
        "<i class='fa fa-chevron-circle-right'></i>\n" +
        // "<img src='./upload-img/right.png'>\n" +
        "</div>\n" +
        "<p>\xD7" +
        "</p>\n" +
        "</div>\n " +
        "</div>";
    $('body').append(divStr);
    var maxKey = gbOption.imgObj.length;
    //预览图左右箭头点击事件
    $(".imgs-bg .right").click(function () {
        if (gbOption.imgKey >= maxKey) return
        gbOption.imgKey++;
        createPreDiv.imgMiddleShow(gbOption.imgKey)
    })
    $(".imgs-bg .left").click(function () {
        if (gbOption.imgKey == 0) return
        gbOption.imgKey--;
        createPreDiv.imgMiddleShow(gbOption.imgKey)
    })
    $(".imgs-bg p").click(function () {
        $(".imgs-bg").hide();
    })
}
//预览图  宽度样式动态调整，为了响应式
createPreDiv.imgW = function () {
    var imgW = $(".middle-img img")[0].clientWidth;
    var imgH = $(".middle-img img")[0].clientHeight + 20;
    // console.log(imgW+'---'+documentW)
    // console.log(imgH+'---'+documentH)
    if (imgW <= documentW) {
        $('.imgs-bg .middle').css("width", imgW + 'px')
    } else {
        $('.imgs-bg .middle').css("width", documentW-80 + 'px')
    }
    if (imgH <= documentH) {
        $('.imgs-bg .middle').css("height", imgH + 'px')
    } else {
        $('.imgs-bg .middle').css("height", '100%')
    }
}
//预览图 动态改变SRC
createPreDiv.imgMiddleShow = function (key) {
    gbOption.imgKey = key;
    $(".middle-img img").attr("src", gbOption.imgObj[key]);
    $(".imgs-bg").show();
    setTimeout(function () {
        createPreDiv.imgW();
        $(".middle-img img").css("opacity", 1)
    },30)
}


//})
