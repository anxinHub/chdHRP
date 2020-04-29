<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
<jsp:param value="grid,select,datepicker,ligerUI" name="plugins" />
</jsp:include>

<script type="text/javascript">
var grid=null;
var formManager=null;
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	$(function (){
		loadHead();
		query();
    });  
    function query(){
		var parms = [
			{ name: 'report_code', value: '${report_code}' },
			{ name: 'ds_code', value: '${ds_code}' }
		]
		//加载查询条件
		grid.loadData(parms,"getReportQueryData.do?isCheck=false");
	}
	
    function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{display: '查询编码', name: 'para_code', align: 'left', width: "15%"},
				{display: '查询名称', name: 'para_name', align: 'left', width: "20%"},
				{display: '数据集', name: 'ds_code', align: 'left', width: "20%"},
				{display: '数据来源', name: 'dict_code', align: 'right', width: "15%"},
				{display: '位置', name: 'location', align: 'right', width: "15%",
					editor:{
						type:"string"
						}
				}
			],
			dataModel:{
	           	method:'POST',
	           	location:'remote',
	           	url:'',
	           	recIndx: 'para_code'
            },
            usePager:false,width: '100%', height: '80%',checkbox: true,editable: true,
			toolbar: {
              
			},
			load: function(event, ui){
			     checkGrid();
			}
		});
	}
    function checkGrid(){
    	debugger;
    	 var gridData = grid.getAllData();
    	 $.each(gridData, function(index, rowData){
    	    if(rowData.rep_code!=null){
    	     grid.setSelection(index);
    	    }
    	 }) 	   
   }
    //保存
    function mySave(){
    	debugger;
    	var data = grid.selectGetChecked();
//    	if(data.length==0){
//    		$.etDialog.error('请至少选择一个条件');
//    		return;
//   	}
		$.ligerDialog.confirm('是否保存？', function (yes) {
			if(yes){
				mySaveConfirm();
			}
		});
    }
    
    function mySaveConfirm(){
    	var data = grid.selectGetChecked();
    	 var params=JSON.stringify(data);
    	 var para={
     			report_code: '${report_code}',
     			params:params
     	};
    	 ajaxJsonObjectByUrl("../saveReportQuery.do?isCheck=false",para,function(data){
     		
 		});
    	
    	 
      
    }
   
   
   function myClose(){
   	dialog.close();
   }
  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<h2 align=center>查询条件选择</h2><br/>
	<div id="maingrid"></div>
	<table align=center>
			<td><br><br></br>
				<input type="button" value="  保存  " accessKey="S" onclick="mySave();"/>
				&nbsp;&nbsp;<input type="button" value="  关闭  " accessKey="C" onclick="myClose();"/>
			</td>
			</tr>
		</table>
</body>
</html>
