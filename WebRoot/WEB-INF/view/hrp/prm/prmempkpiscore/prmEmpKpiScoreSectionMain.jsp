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
    
    var clicked = 0;
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        
        toobar();
        
        loadHotkeys();

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
                     { display: '区间', name: 'section', align: 'left',
                    	 render: function (rowdata) {
                    		 
                    		 if(rowdata.section !='' &&  rowdata.section !=undefined){

                    			 return '第'+parseInt(rowdata.section)+'区间';
                    			 
                    		 }else{
                    			 return '第'+parseInt(rowdata.__index+1)+'区间';
                    			 
                    		 }
                    		 
                         } 
                    },
					 { display: '指标值', columns:[
								{ display: '起始', name: 'kpi_beg_velue', align: 'left',
			                    	 render: function (rowdata) {
			                    		 if(rowdata.kpi_beg_velue == null || rowdata.kpi_beg_velue == ""){
			                    			 return "0";
			                    		 }else{
			                    			 return rowdata.kpi_beg_velue;
			                    		 }
			                    	 }},
								{ display: '终止', name: 'kpi_end_velue', align: 'left',
				                    	 render: function (rowdata) {
				                    		 if(rowdata.kpi_end_velue == null || rowdata.kpi_end_velue == ""){
				                    			 return "0";
				                    		 }else{
				                    			 return rowdata.kpi_end_velue;
				                    		 }
				                    	 }}
					 		]
					 },
					 { display: '指标得分', columns:[
								{ display: '起始', name: 'kpi_beg_score', align: 'left',
			                    	 render: function (rowdata) {
			                    		 if(rowdata.kpi_beg_score == null || rowdata.kpi_beg_score == ""){
			                    			 return "0";
			                    		 }else{
			                    			 return rowdata.kpi_beg_score;
			                    		 }
			                    	 }},
								{ display: '终止', name: 'kpi_end_score', align: 'left',
				                    	 render: function (rowdata) {
				                    		 if(rowdata.kpi_end_score == null || rowdata.kpi_end_score == ""){
				                    			 return "0";
				                    		 }else{
				                    			 return rowdata.kpi_end_score;
				                    		 }
				                    	 }}
							]
					}
                     ],
                     usePager:false,enabledEdit : false,url:'queryPrmEmpSectionScoreByEditerGrid.do?group_id=${group_id}&hos_id=${hos_id}&copy_code=${copy_code}&acc_year=${acc_year}&acc_month=${acc_month}&goal_code=${goal_code}&kpi_code=${kpi_code}&emp_id=${emp_id}&emp_no=${emp_no}',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
      
    }

  
  //键盘事件
	function loadHotkeys(){
		
 
	}
	
    function loadDict(){
        //字典下拉框

    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",false);
        
		liger.get("hos_id").setValue("${hos_id}");
        
        liger.get("hos_id").setText("${hos_code} ${hos_name}");
    	
    	autocomplete("#emp_id","../queryHosEmpDict.do?isCheck=false","id","text",true,true,"",false);
    	
		liger.get("emp_id").setValue("${emp_id}.${emp_no}");
        
        liger.get("emp_id").setText("${emp_code} ${emp_name}");
        
		$("#hos_id").ligerTextBox({width : 160,disabled: true});
    	
    	$("#emp_id").ligerTextBox({width : 160,disabled: true});
    	
    	$("#acc_year").ligerTextBox({width : 70,disabled: true});
    	
    	$("#acc_month").ligerTextBox({width : 70,disabled: true});
    	
    	$("#kpi_code").ligerTextBox({width : 160,disabled: true});
    	
    	$("#nature_code").ligerTextBox({width : 160,disabled: true});
    	
    	$("#goal_value").ligerTextBox({width : 70,disabled: true});
    	
    	$("#ratio").ligerTextBox({width : 70,disabled: true});
	}  

    function toobar(){
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	  <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">单位信息：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核单元：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核年度：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" disabled="disabled"  ltype="text"  value="${acc_year}"  validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_month" type="text" id="acc_month" disabled="disabled"  value="${acc_month}"   ltype="text" validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})"/></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" ltype="text"  value="${kpi_name}"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">指标性质：</td>
            <td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" ltype="text"  value="${nature_name}"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:5px;">目标值：</td>
            <td align="left" class="l-table-edit-td"><input name="goal_value" type="text" id="goal_value" value="${goal_value}"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">满分：</td>
            <td align="left" class="l-table-edit-td"><input name="ratio" type="text" id="ratio"  value="${full_score}" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
    </table>
	<div id="toptoolmod"></div>
	<div id="maingrid"></div>

</body>
</html>
