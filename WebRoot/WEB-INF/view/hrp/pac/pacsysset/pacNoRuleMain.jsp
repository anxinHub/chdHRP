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
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function (){
    	loadHead(null);	//加载数据
    });
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '单据编号', name: 'table_code', align: 'center'},
                     { display: '单据名称', name: 'table_name', align: 'center'},
                     { display: '单据号前缀', name: 'prefixe', align: 'center',editor: { type: 'string' }},
                     { display: '含类型代码', name: 'is_type', align: 'center',
                    	 render : function(rowdata, rowindex,value) {
                    		 if(value == 0){
								return "<input type='checkbox' style='margin-top:7px;' id='is_type' name='is_type"+rowindex+"' checked='checked'/>";
                    		 }else{
								return "<input type='checkbox' style='margin-top:7px;' id='is_type' name='is_type"+rowindex+"'/>";
                    		 }
							}	 
                     },
                     { display: '含年度', name: 'is_year', align: 'center',
                    	 render : function(rowdata, rowindex,value) {
                    		 if(value == 0){
 								return "<input type='checkbox' style='margin-top:7px;' id='is_year' name='is_year"+rowindex+"' checked='checked'/>";
                     		 }else{
 								return "<input type='checkbox' style='margin-top:7px;' id='is_year' name='is_year"+rowindex+"'/>";
                     		 }
							}	 
                     },
                     { display: '含月份', name: 'is_month', align: 'center',
                    	 render : function(rowdata, rowindex,value) {
                    		 if(value == 0){
  								return "<input type='checkbox' style='margin-top:7px;' id='is_month' name='is_month"+rowindex+"' checked='checked'/>";
                      		 }else{
  								return "<input type='checkbox' style='margin-top:7px;' id='is_month' name='is_month"+rowindex+"'/>";
                      		 }
						}	 
                     },
                     { display: '流水号位数', name: 'seq_no', align: 'center',editor: { type: 'int' }},
					 { display: '操作',name: 'edit',width:90, align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<div class='l-button' style='width: 60px;margin-top:1px;margin-left: 18px;' ligeruiid='Button1004' onclick=save('"+rowindex+"','"+rowdata.proj_code+"');>"
		       					+"<span>保存</span></div>";
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryPacNoRule.do',
                     width: '100%', height: '100%', rownumbers:true,
                     selectRowButtonOnly:true,enabledEdit: true,
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function save(rowindex,proj_code){
    	var is_type = 1;
    	if($("input[name='is_type"+rowindex+"']").attr("checked") == true){
    		is_type = 0;
    	}
    	var is_year = 1;
    	if($("input[name='is_year"+rowindex+"']").attr("checked") == true){
    		is_year = 0;
    	}
    	var is_month = 1;
    	if($("input[name='is_month"+rowindex+"']").attr("checked") == true){
    		is_month = 0;
    	}
    	var obj = grid.data.Rows[rowindex];
    	var formPara = {
    			is_type:is_type,
    			is_year:is_year,
    			is_month:is_month,
    			table_code:obj.table_code,
    			table_name:obj.table_name,
    			prefixe:obj.prefixe,
    			seq_no:obj.seq_no
    			
    	};
		ajaxJsonObjectByUrl("updatePacNoRule.do",formPara,function(responseData){
                query();
        });
    }
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none;"></div>
	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>

</body>
</html>
