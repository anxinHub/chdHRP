<%--  2017/10/17 xjx create  --%>
<%--  静态资源按需加载页面  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.lang.*"%> 
<% String path = request.getContextPath(); %>
<% String plugins = request.getParameter("plugins");%>
 

<!--    公共资源    --> 
<link rel="stylesheet" href="<%=path%>/lib/et_assets/common.css">
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/et_assets/common.js"></script>

<!-- 日期库  -->
<script src="<%=path%>/lib/et_assets/moment.min.js"></script>

<%-- hr系统公共资源 --%>
<% if(plugins.indexOf("hr") != -1) { %> 
    <link rel="stylesheet" href="<%=path%>/lib/et_assets/hr/common.css">
    <script src="<%=path%>/lib/et_assets/hr/common.js"></script>
    <script src="<%=path%>/lib/hrp/hr/hr.js"></script>
<% } %> 

<!-- 表格控件 -->
<% if(plugins.indexOf("grid") != -1) { %> 
   <script src="<%=path%>/lib/et_components/etGrid/js/jquery-ui-1.9.2.min.js"></script>
   <script src="<%=path%>/lib/et_components/et_plugins/etGrid.min.js"></script>
<% }%>

<!--  日期控件  -->
<% if(plugins.indexOf("datepicker") != -1) { %> 
   <script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js"></script>
<% }%>

<!--  日期滚动 react版-->
<% if(plugins.indexOf("dateSlider") != -1) { %> 
   <script src="<%=path%>/lib/et_components/et_plugins/etDateSlider.min.js"></script>
<% }%>

<!--  下拉框控件  -->
<% if(plugins.indexOf("select") != -1) { %> 
    <script src="<%=path%>/lib/et_components/et_plugins/etSelect.min.js"></script>
<% }%> 

<!--  复选框控件  -->
<% if(plugins.indexOf("checkbox") != -1) { %> 
    <script src="<%=path%>/lib/et_components/et_plugins/etCheck.min.js"></script>
<% }%>

<!-- 弹出层组件 -->
<% if (plugins.indexOf("dialog") != -1) { %>
   <script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<% }%>

<!-- tab组件 -->
<% if (plugins.indexOf("tab") != -1) { %>
   <script src="<%=path%>/lib/et_components/et_plugins/etTab.min.js"></script>
<% }%>

<!-- 表单验证  -->
<% if(plugins.indexOf("validate") != -1) { %> 
    <script src="<%=path%>/lib/et_components/et_plugins/etValidate.min.js"></script>
<% } %> 

<!--  树形菜单插件  -->
<% if(plugins.indexOf("tree") != -1) { %> 
   <script src="<%=path%>/lib/et_components/et_plugins/etTree.min.js"></script>
<% } %> 

<!--  上传插件  -->
<% if(plugins.indexOf("upload") != -1) { %> 
   <script src="<%=path%>/lib/et_components/et_plugins/etUpload.min.js"></script>
<% } %> 

<!--  表单插件  -->
<% if(plugins.indexOf("form") != -1) { %> 
   <script src="<%=path%>/lib/et_components/et_plugins/etForm.min.js"></script>
<% } %> 

<!--  可拖拽插件  -->
<% if(plugins.indexOf("sortable") != -1) { %> 
   <script src="<%=path%>/lib/et_components/et_plugins/etSortable.min.js"></script>
<% } %> 

<!--  pageOffice插件  -->
<% if(plugins.indexOf("pageOffice") != -1) { %> 
    <script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<% } %>

<!--  人资 人物信息列表  -->
<% if(plugins.indexOf("characterList") != -1) { %> 
    <!-- <script src="<%=path%>/lib/et_components/et_plugins_test/etCharacterList.min.js"></script> -->
    <script src="<%=path%>/lib/et_components/et_plugins/etCharacterList.min.js"></script>
<% } %> 

<!--  图片展示器(放大缩小)  -->
<% if(plugins.indexOf("magnify") != -1) { %>
    <link rel="stylesheet" href="<%=path%>/lib/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=path%>/lib/magnify/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path%>/lib/magnify/magnifycss.css">
    <script src="<%=path%>/lib/magnify/magnify.js"></script>
<% } %>

<!--  打印  -->
<% if(plugins.indexOf("jquery_print") != -1) { %>
    <script src="<%=path%>/lib/jquery/jquery.print.js"></script>
    <% } %>
    
<!-- 时间控件 -->
<% if(plugins.indexOf("time") != -1) { %>
    <script src="<%=path%>/lib/laydate/laydate.js"></script>
    <% } %>

<%--  其他资源  --%>
 <script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<%--<script src="<%=path%>/lib/jquery.cookie.js"></script>
<script src="<%=path%>/lib/hotkeys/jquery.hotkeys.js" type="text/javascript"></script>
<script src="<%=path%>/lib/map.js"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script> --%>








