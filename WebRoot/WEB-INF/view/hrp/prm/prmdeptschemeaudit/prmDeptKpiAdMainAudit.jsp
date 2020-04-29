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
       
        $("#acc_year").ligerTextBox({width:60});
        $("#acc_month").ligerTextBox({width:60});
        $("#goal_code").ligerTextBox({width:160});
        $("#kpi_code").ligerTextBox({width:160});
        $("#nature_code").ligerTextBox({width:160});
        $("#ratio").ligerTextBox({width:160});
        $("#check_hos_id").ligerTextBox({width:160});
		
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
                     { display: '期间', name: 'acc_month', align: 'left'
					 		},
                     { display: '值距', name: 'kpi_range_velue', align: 'left',
		                    	 render: function (rowdata) {
		                    		 if(rowdata.kpi_range_velue == null || rowdata.kpi_range_velue == ""){
		                    			 return "0";
		                    		 }else{
		                    			 return rowdata.kpi_range_velue;
		                    		 }
		                    	 }
					 		},
                     { display: '分距', name: 'kpi_range_score', align: 'left',
		                    	 render: function (rowdata) {
		                    		 if(rowdata.kpi_range_value == null || rowdata.kpi_range_score == ""){
		                    			 return "0";
		                    		 }else{
		                    			 return rowdata.kpi_range_score;
		                    		 }
		                    	 }
					 		}
                     ],
                     usePager:false,url:'queryPrmDeptKpiAdByEditerGridAudit.do?group_id=${group_id}&hos_id=${hos_id}&copy_code=${copy_code}&acc_year=${acc_year}&acc_month=${acc_month}&goal_code=${goal_code}&kpi_code=${kpi_code}&dept_id=${dept_id}&dept_no=${dept_no}',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit : false,
                     selectRowButtonOnly:true//heightDiff: -10,
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

 

    function loadDict(){

        
    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",false);
    	
		liger.get("hos_id").setValue("${hos_id}");
        
        liger.get("hos_id").setText("${hos_code} ${hos_name}");
    	
    	autocomplete("#dept_no","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",false);
    	
		liger.get("dept_no").setValue("${dept_id}.${dept_no}");
        
        liger.get("dept_no").setText("${dept_code} ${dept_name}");
        
        
		$("#hos_id").ligerTextBox({width : 160,disabled: true});
    	
    	$("#dept_no").ligerTextBox({width : 160,disabled: true});
        
    	
    	$("#acc_year").ligerTextBox({width : 160,disabled: true});
    	
    	$("#acc_month").ligerTextBox({width : 160,disabled: true});
    	
		$("#kpi_code").ligerTextBox({width : 160,disabled: true});
    	
    	$("#nature_code").ligerTextBox({width : 160,disabled: true});
    	$("#ratio").ligerTextBox({width : 160,disabled: true});
    	
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
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">单位信息：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id"  ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核单元：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_no" type="text" id="dept_no"  ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核年度：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" value="${acc_year}" disabled="disabled"   ltype="text" validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_month" type="text" id="acc_month" value="${acc_month}" disabled="disabled"  ltype="text" validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" value="${kpi_name}" disabled="disabled"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">指标性质：</td>
            <td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" ltype="text" disabled="disabled" value="${nature_name}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">满分：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="ratio" type="text" id="ratio"  value="${full_score}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
    </table>
	<div id="toptoolmod"></div>
	<div id="maingrid"></div>
</body>
</html>
