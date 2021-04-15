
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
</style>
    <div class="widget-body" id="userDivId">
    <div class="widget-main" >
        <form id="serFileForm1" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding" data-locale="Selectfile">选择文件</label>
                <div class="col-xs-12 col-sm-8">
                    <div class="clearfix">
                        <input type="file" name="file"/>
                        <input type="submit" style="visibility:hidden" id='upLoad' value="上传" />
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

