<%--  2017/7/29 xjx create  --%>
<%--  静态资源按需加载页面  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.lang.*"%> 
<% String path = request.getContextPath(); %>
<% String plugins = request.getParameter("plugins");%>
<% String version = "2.0.0";%> 

<!--  默认jquery1.9.0 以及全局css  -->
<link rel="stylesheet" href="<%=path%>/lib/et_assets/common.css">
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>

<!-- 表格控件 -->
<!-- <% if(plugins.indexOf("grid") != -1) { %> 
    <link rel="stylesheet" href="<%=path%>/lib/et_components/etGrid/css/pqgird-theme-old.min.css" />
    <script src="<%=path%>/lib/et_components/etGrid/js/jquery-ui-1.9.2.min.js"></script>
    <script src="<%=path%>/lib/et_components/etGrid/js/pqgrid.dev.js"></script>
    <script src='<%=path%>/lib/et_components/etGrid/etGrid.js'></script>
<% }%> -->
<% if(plugins.indexOf("grid") != -1) { %> 
    <script src="<%=path%>/lib/et_components/etGrid/js/jquery-ui-1.9.2.min.js"></script>
    <script src="<%=path%>/lib/et_components/et_plugins/etGrid.min.js"></script>
 <% }%>

<!--  日期控件  -->
<% if(plugins.indexOf("datepicker") != -1) { %> 
    <link rel="stylesheet" href="<%=path%>/lib/et_components/etDatepicker/css/datepicker.css">
    <script src="<%=path%>/lib/et_components/etDatepicker/js/datepicker.js"></script>
   <%--  <script src="<%=path%>/lib/et_components/etDatepicker/etDatepicker.js"></script> --%>
    
    <script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
    
<% }%>

<!--  下拉框控件  -->
<% if(plugins.indexOf("select") != -1) { %> 
    <%-- <link rel="stylesheet" href="<%=path%>/lib/et_components/etSelect/css/selectize.default.css">
	<script src="<%=path%>/lib/et_components/etSelect/js/selectize.js"></script>
	<script src="<%=path%>/lib/et_components/etSelect/etSelect.js"></script> --%>
	<script src="<%=path%>/lib/et_components/et_plugins/etSelect.min.js"></script>
<% }%> 

<!--  复选框控件  -->
<% if(plugins.indexOf("checkbox") != -1) { %> 
    <link rel="stylesheet" href="<%=path%>/lib/et_components/etCheck/css/icheck.css">
	<script src="<%=path%>/lib/et_components/etCheck/js/icheck.js"></script>
	<script src="<%=path%>/lib/et_components/etCheck/etCheck.js"></script>
<% }%>

<!-- 弹出层组件 -->

    <!-- <script src="<%=path%>/lib/et_components/etDialog/layer/layer.js"></script>
    <script src="<%=path%>/lib/et_components/etDialog/etDialog.js"></script> -->
    <script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>


<!-- tab组件 -->
<% if (plugins.indexOf("tab") != -1) { %>
    <link rel="stylesheet" href="<%=path%>/lib/et_components/etTab/etTab.css">
    <script src="<%=path%>/lib/et_components/etTab/etTab.js"></script>
<% }%>

<!--  ligerUI所有控件  -->
<% if(plugins.indexOf("ligerUI") != -1) { %> 
    <link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<% } %>

<!--  自封装，简单 表单验证  -->
<% if(plugins.indexOf("etValidate") != -1) { %> 
    <script src="<%=path%>/lib/et_components/etValidate/etValidate.js" type="text/javascript"></script>
<% } %> 

<!--  表单验证插件  -->
<% if(plugins.indexOf("validate") != -1) { %> 
    <script src="<%=path%>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<% } %> 

<!--  打印插件  -->
<% if(plugins.indexOf("lodop") != -1) { %> 
    <script src="<%=path%>/lib/Lodop/ssf.js"></script>
    <script src="<%=path%>/lib/Lodop/barcode.js"></script>  
    <script src="<%=path%>/lib/Lodop/LodopFuncs.js"></script>
    <script src="<%=path%>/lib/Lodop/printThis.js" language="javascript"></script>
    <script src="<%=path%>/lib/Lodop/webPrint.js" language="javascript"></script>
<% } %> 

<!--  spread插件  -->
<% if(plugins.indexOf("spread") != -1) { %> 
    <script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/gc.spread.sheets.all.10.1.0.min.js' type='text/javascript'></script>
    <script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/pluggable/gc.spread.sheets.print.10.1.0.min.js' type='text/javascript'></script>
    <script src="<%=path%>/lib/SpreadJS10/license.js" type="text/javascript"></script>
<% } %> 

<!--  pageOffice插件  -->
<% if(plugins.indexOf("pageOffice") != -1) { %> 
    <script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<% } %> 

<!--  树形菜单插件  -->
<% if(plugins.indexOf("tree") != -1) { %> 
    <link href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
	<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js" type="text/javascript"></script>
<% } %> 

<!--  上传插件  -->
<% if(plugins.indexOf("upload") != -1) { %> 
   <script src="<%=path%>/lib/et_components/et_plugins/etUpload.min.js"></script>
<% } %> 


<%--  其他资源  --%>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery.cookie.js"></script>
<script src="<%=path%>/lib/hotkeys/jquery.hotkeys.js" type="text/javascript"></script>
<script src="<%=path%>/lib/map.js"></script>
<!-- <script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script> -->

<!-- 公共资源 -->
<script src="<%=path%>/lib/et_assets/common.js"></script>


<!--  上传组件  -->
<% if(plugins.indexOf("plupload") != -1) { %> 
<!-- 配置界面上的css -->  
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/plupload-2.1.3/js/jquery.plupload.queue/css/jquery.plupload.queue.css">  
<script type="text/javascript" src="<%=path%>/lib/plupload-2.1.3/js/plupload.full.min.js"></script>  
<script type="text/javascript" src="<%=path%>/lib/plupload-2.1.3/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>  
   <!-- 国际化中文支持 -->  
<script type="text/javascript" src="<%=path%>/lib/plupload-2.1.3/js/i18n/zh_CN.js"></script>
<script src="<%=path%>/lib/Lodop/ssf.js"></script> 
<script language="javascript" src="<%=path%>/lib/Lodop/LodopFuncs.js"></script>
<script src="<%=path%>/lib/Lodop/printThis.js" language="javascript"></script>
<script src="<%=path%>/lib/Lodop/webPrint.js" language="javascript"></script>
<% } %> 

<%-- 
<script src="<%=path%>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/conn/arithmetic.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/cost/cost.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>

<script src="<%=path%>/lib/hrp/ass/ligerNumberbox.js" type="text/javascript"></script>  --%>




