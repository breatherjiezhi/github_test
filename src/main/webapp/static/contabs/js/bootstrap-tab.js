var addTabs = function (options) {
    //�����ڴ˴���֤session
    //var rand = Math.random().toString();
    //var id = rand.substring(rand.indexOf('.') + 1);
    var url = window.location.protocol + '//' + window.location.host;
    options.url = url + options.url;
    id = "tab_" + options.id;
    var active_flag = false;
    if($("#" + id)){
        active_flag = $("#" + id).hasClass('active');
    }
    $(".active").removeClass("active");
    //���TAB�����ڣ�����һ���µ�TAB
    if (!$("#" + id)[0]) {
        //�̶�TAB��IFRAME�߶�
        mainHeight = $(document.body).height();
        //������TAB��title
        title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab"><i class="'+options.icon+'"></i>' + options.title;
        //�Ƿ�����ر�
        if (options.close) {
            title += ' <i class="glyphicon glyphicon-remove-sign" tabclose="' + id + '"></i>';
        }
        title += '</a></li>';
        //�Ƿ�ָ��TAB����
        if (options.content) {
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + options.content + '</div>';
        } else {//û�����ݣ�ʹ��IFRAME������
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe id="iframe_'+id+'" src="' + options.url + 
                '" width="100%" height="100%" onload="changeFrameHeight(this)" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
        }
        //����TABS
        $(".nav-tabs").append(title);
        $(".tab-content").append(content);
    }else{
        if(active_flag){
            $("#iframe_" + id).attr('src', $("#iframe_" + id).attr('src'));
        }
    }
    //����TAB
    $("#tab_" + id).addClass('active');
    $("#" + id).addClass("active");
};
var changeFrameHeight = function (that) {
    $(that).height(document.documentElement.clientHeight - 115);
    $(that).parent(".tab-pane").height(document.documentElement.clientHeight - 130);
}
var closeTab = function (id) {
    //����رյ��ǵ�ǰ�����TAB����������ǰһ��TAB
    if ($("li.active").attr('id') == "tab_" + id) {
        $("#tab_" + id).prev().addClass('active');
        $("#" + id).prev().addClass('active');
    }
    //�ر�TAB
    $("#tab_" + id).remove();
    $("#" + id).remove();
};
$(function () {
    $("[addtabs]").click(function () {
        addTabs({ id: $(this).attr("id"), title: $(this).attr('title'), close: true });
    });

    $(".nav-tabs").on("click", "[tabclose]", function (e) {
        id = $(this).attr("tabclose");
        closeTab(id);
    });

    window.onresize = function () {
        var target = $(".tab-content .active iframe");
        changeFrameHeight(target);
    }
});

