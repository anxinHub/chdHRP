<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/hr/hr.js"></script> 
<script type="text/javascript">
    var grid;
    var gridManager = null;
   
     
        
        $(function () {
        	  loadDict()//加载下拉框
          	//加载数据
          	loadHead(null);	
          	 $("#emp_id").ligerTextBox({width:160});
          	$("#dept_id").ligerTextBox({width:160});
        })
        //表格加载
            function loadHead(){
        	var columns=getGridColumns({ui:'liger',group_id:'${group_id}',hos_id:'${hos_id}',gridTables:['HR_EMP_CONTRACT_DICT'],design:'queryContractDict.do'});
    		
            	grid = $("#maingrid").ligerGrid({
            		columns: columns,
                              dataAction: 'server',dataType: 'server',url:'queryContractDict.do',delayLoad :true,
                              width: '100%', height: '100%', checkbox: true,rownumbers:true,usePager :true,
                              enabledEdit : true,
  						//onBeforeEdit : f_onBeforeEdit,
  					    //onChangeRow:onChangeRowSeve,
                        toolbar: {
                            items: [
                                {text: '查询', id:'search', click: query,icon:'search' },
            
                            ]
                        },
                    
                            });

                 gridManager = $("#maingrid").ligerGetGridManager();
          
        }
      	//查询方法
        function query() {
     			grid.options.parms=[];
     	  		grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue()}); 
     	  		grid.options.parms.push({name:'tab_code',value:'HR_EMP_CONTRACT_DICT'}); 
     	  		grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split('@')[0]}); 
     	  		grid.options.parms.push({name:'rjt',value:"grid"}); 
     	  		grid.options.parms.push({name:'design_code',value:"queryContractDict.do"}); 
     	  
         		grid.loadData(grid.where);
     	
     		}
        function loadDict(){
    		autocomplete("#emp_id","../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP","id","text",true,true,'',false,'',175);
    		autocomplete("#dept_id","../../baseSelect.do?isCheck=false&&field_tab_code=SYS_DEPT","id","text",true,true,'','false','',175);		
    	}
    </script>
</head>

<body>
  
<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
	</table>
        
            <div id="maingrid"></div>
     
</body>

</html>