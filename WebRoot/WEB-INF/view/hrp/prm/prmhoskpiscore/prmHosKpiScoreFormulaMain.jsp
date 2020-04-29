<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        loadHotkeys();
        toobar();
		
    });
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		grid.options.parms.push({name:'acc_year',value:'${acc_year}'}); 
		grid.loadData(grid.where);
   }

    function loadHead(){

    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '指标编码', name: 'kpi_code', align: 'left'
					 		},
                     { display: '指标名称', name: 'kpi_name', align: 'left'
					 		},
                     { display: '指标性质', name: 'nature_name', align: 'left'
					 		},
		             { display: '取值方法', name: 'method_code', align: 'left',
		                  		render : function(rowdata, rowindex,
										value) {
		                  			if(rowdata.method_code == "02"){
		                  				return rowdata.formula_method_chs;
		                  			}else if(rowdata.method_code == "03"){
		                  				return rowdata.fun_method_chs;
		                  			}
		                  			return rowdata.kpi_value;
		   							
		                    	}
						 	},
					  { display: '指标值', name: 'kpi_value', align: 'right',width:80,
		                  		render : function(rowdata, rowindex,
										value) {
		                  			return "<a href=javascript:openKpiValue()>"+rowdata.kpi_value+"</a>";
		   							
		                    	}
							 }	 	
                     ],
                     usePager:false,url:'queryPrmHosScoreFormula.do?group_id=${group_id}&hos_id=${hos_id}&copy_code=${copy_code}&acc_year=${acc_year}&acc_month=${acc_month}&formula_code=${formula_code}&kpi_code=${kpi_code}&check_hos_id=${check_hos_id}',
                     width: '100%', height: '100%', checkbox: false,rownumbers:false,enabledEdit : false,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
    function openKpiValue(){
    	
    }
 

    function loadDict(){
    	autocompleteAsync("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",true,"${hos_id}",false);
        
        
        autocompleteAsync("#check_hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",false,"${check_hos_id}");
        
        
		$("#hos_id").ligerTextBox({width : 160,disabled: true});
    	
    	$("#check_hos_id").ligerTextBox({width : 160,disabled: true});
        
    	
    	$("#formula_method_chs").ligerTextBox({width : 400,disabled: true});
    	
    	$("#kpi_value").ligerTextBox({width : 160,disabled: true});
    	
    	$("#kpi_code").ligerTextBox({width : 160,disabled: true});
    	
  	}  
  
    //键盘事件
	function loadHotkeys(){
 
	}
    
	
    function toobar(){
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	  <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">医院名称：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id"  ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核单元：</td>
            <td align="left" class="l-table-edit-td"><input name="check_hos_id" type="text" id="check_hos_id"  ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">KPI指标：</td>
            <td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" value="${kpi_name}" disabled="disabled"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">计算公式：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="formula_method_chs" type="text" id="formula_method_chs" ltype="text" disabled="disabled" value="${formula_method_chs}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">KPI指标值：</td>
            <td align="left" class="l-table-edit-td" ><input name="kpi_value" type="text" id="kpi_value"  value="${kpi_value}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
    </table>
	<div id="toptoolmod"></div>
	<div id="maingrid"></div>
</body>
</html>
