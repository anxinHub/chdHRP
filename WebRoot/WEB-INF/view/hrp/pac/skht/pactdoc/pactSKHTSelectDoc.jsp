<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox,pageOffice" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<!-- script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script-->

<script type="text/javascript">
var grid,doc_ids,parentWindow;
var empSoues = [];    
var typeSoues = [];    
var stateSoues = [];    

	var close = function() {
		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		parent.$.etDialog.close(curIndex);
	};
	
	 var initPactDocGrid = function () {
         var docColumns = [
         	// { display: '阶段状态', name: 'state_name',width: '10%'},
              { display: '文档类别', name: 'doc_type_name', width: '15%'},
              { display: '文档名称', name: 'doc_name', width: '30%'},
              { display: '所在科室', name: 'dept_name', width: '15%'},
              { display: '责任人', name: 'emp_name', width: '10%'},
              { display: '存放位置', name: 'location', width: '27%'},
         ];
         var paramObj = {
         	editable: false,
         	height: '96%',
         	width:'98%',
         	checkbox: true,
         	usePager: false,
            dataModel: {
	         	url: 'queryPactDocSKHTForExec.do?isCheck=false&pact_code=${pact_code }&doc_ids='+doc_ids,
             },
             columns: docColumns,
             toolbar: {
                 items: [
                     { type: 'button', label: '保存',  listeners: [{ click: saveDoc }],  icon: 'save' },
                     { type: 'button', label: '关闭',  listeners: [{ click: close }],  icon: 'close' },
                 ]
             }
         };
         docGrid =  $("#pactdoc").etGrid(paramObj);
         
     };
     
     function saveDoc(){
 		var select = docGrid.selectGet();
 		var data = parentWindow.grid.getAllData();
 		
 		var maxIndex;
 		 $(select).each(function () {
 			 var newRow = false;
              var rowdata = this.rowData;
              $(data).each(function(){
 				if (${type_code} == this.type_code) {
 					rowdata.type_code = '${type_code}';
 					rowdata.type_name = this.type_name;
 					rowdata.note = this.note;
 					var index = this._rowIndx;
 					if (this.doc_id) {
 						newRow = true;
 						if (maxIndex) {
 							if (maxIndex < index) {
 								maxIndex = index;
 							}
 						}else{
 							maxIndex = index
 						}
 					}else{
 						maxIndex = index;
 					}
 				}            	 
              });
              if (newRow) {
 	             parentWindow.grid.insertRow(maxIndex + 1,rowdata,true);
 			 }else{
 				 parentWindow.grid.deleteRow(maxIndex);
 				 parentWindow.grid.insertRow(maxIndex,rowdata);
 			 }
          });
 		  
 		 parentWindow.onAfterShowData(); 
 		 var curIndex = parent.$.etDialog.getFrameIndex(window.name);
 	     parent.$.etDialog.close(curIndex); 
 	}

	$(function(){
		var parentFrameName = parent.$.etDialog.parentFrameName;
        parentWindow = parent.window[parentFrameName];
        obj = parentWindow.grid.getRowData('${_rowIndx}');
        var data = parentWindow.grid.getAllData();
        doc_ids = "";
        $(data).each(function(){
        	if (this.doc_id) {
	        	doc_ids += this.doc_id + ",";
			}
        });
		initPactDocGrid();
	})
</script>
</head>

<body style="overflow: scroll; ">
	<div id="pactdoc"></div>
</body>

</html>

