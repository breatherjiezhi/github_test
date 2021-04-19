<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<title>导入</title>
<div class="widget-body">
    <div class="widget-main">
        <form id="impInputForm" class="form-horizontal" enctype="multipart/form-data">
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding" >选择导入文件:</label>

                <div class="col-xs-12 col-sm-9">
                    <div class="clearfix">
                        <input name ="impExcelData" id ="impExcelData" htmlEscape="false" class="form-control width-100"
                               type="file"   placeholder="选择文件"/>
                    </div>
                </div>
            </div>
        </form>
        <div id="progressbar" class="progress-label">等待导入....</div>
    </div>
</div>
