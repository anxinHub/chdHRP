<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
    var para;
    
    $(function ()
    {
    	
    	$("#wage_tax_tab").ligerTab();
    	
    });
    //查询
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	$("#iframe").attr("src","../accwagetaxset/accWageTaxSetMainPage.do");
              		return;
                case "delete":
                	$("#iframe").attr("src","../accwagetaxcal/accWageTaxCalMainPage.do");
                    return;
                case"update":
                	$("#iframe").attr("src","accWageTaxIndexMainPage.do");
                    return;
            }   
        }
        
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
           
	<div id="wage_tax_tab" style="width: 100%;overflow:hidden; border:1px solid #A3C0E8; "> 
		
		<div tabid ="wage_tax_set" title="个人所得税设置" style="height:100%" >
  			
  			<iframe frameborder="0" name="wage_tax_set" src="../accwagetaxset/accWageTaxSetMainPage.do"></iframe>
  			
	    </div>
	    
	    <div tabid ="wage_tax_cal" title="应税项设置" style="height:100%">
  			
  			<iframe frameborder="0" name="wage_tax_cal" src="../accwagetaxcal/accWageTaxCalMainPage.do"></iframe>
  			
	    </div>
	    
	</div>

</body>
</html>
