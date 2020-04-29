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
var docGrid;
var deptSoues = [];   
var empSoues = [];    
var typeSoues = [];    
var stateSoues = [];    
var pact_type_code;
var pact_code;

	 var query = function (id) {
         params = [
             { name: 'pact_code', value: id }
         ];
         docGrid.loadData(params,'queryPactDocSKXY.do?isCheck=false');
     };
     
    var initSelect=  function(){
      	ajaxPostData({
   		 url: '../../basicset/select/queryPactStateSelect.do?isCheck=false',
   		async:false,
			  success: function (result) {
				  for(var i = 0;i<result.length;i++){
					  var obj = result[i];
					  if(obj.id <= 11){
						  stateSoues.push(obj);
					  }
				  }
			  },
		});
    	ajaxPostData({url: '../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',async:false,success: function (result) {empSoues = result;}});
    	ajaxPostData({url: '../../basicset/select/queryDeptSelect.do?isCheck=false',async:false,success: function (result) {deptSoues = result;}});
    	ajaxPostData({url: '../../basicset/select/queryPactDocTypeSelect.do?isCheck=false',async:false,success: function (result) {typeSoues= result;}});
    	
    	var change = true;
    	var pact = '${pact_type_code}';if (!pact) {pact = "none";}
       	pact_type_code = $("#pact_type_code").etSelect({
   			url: '../../basicset/select/queryPactTypeSKXYSelect.do?isCheck=false',
   			defaultValue: pact,
   			 onChange:function(value, $item){
   				if (change) {
   					 pact_code.setValue(0);
   				}else{
   					change = true
   				}
   				pact_code.reload({
   					 url: '../../basicset/select/queryPactSKXYSelect.do?isCheck=false&pact_type_code=' + pact_type_code.getValue(),
   	    		})
   			 }, 
   		 });
       	var pact = '${pact_code}';if (!pact) {pact = "none";}
       	pact_code = $("#pact_code").etSelect({
         		url: '../../basicset/select/queryPactSKXYSelect.do?isCheck=false&pact_type_code=' + pact_type_code.getValue(),
         		defaultValue: pact,
         		onChange:function(value, $item){
         			query(value);
         			if (!pact_type_code.getValue()) {
	   	      			if (value == 0) {return;}
	   	           		ajaxPostData({
	   	          		 	url: 'queryPactSKXYType.do?isCheck=false',
	   	          		 	data:{ pact_code:value },
	   	          			success: function (result) {
	         					change = false;
	   	          				pact_type_code.setValue(result.pact_type_code);
	   	          			}
	   	          		});
	   				}
         		}
       		});
    	
      }
    
	 var initPactDocGrid = function () {
         var docColumns = [
         	 { display: '阶段状态', name: 'state_name',width: '10%',
         		editor: {
       		     	type: 'select',
       		     	keyField: 'pact_state',
       		     	source: stateSoues
       		 	}	 
         	 },
             { display: '文档类别', name: 'doc_type_name', align: 'center' ,width: '15%',
           	  editor: {
        		     type: 'select',
        		     keyField: 'doc_type',
        		     source: typeSoues
        		}	  	  
           	},
            { display: '文档名称', name: 'doc_name', width: '15%',editor: { type: 'textbox' }},
              { display: '所在科室', name: 'dept_name', width: '15%',
            	  editor: {
           		     type: 'select', 
           		  	 keyField: 'dept_no',
           		     source: deptSoues
           		 }	  	  
              },
              { display: '责任人', name: 'emp_name', width: '10%',
            	  editor: {
            		     type: 'select', 
            		     keyField: 'emp_id',
            		     source: empSoues
            	}	
              },
              { display: '存放位置', name: 'location', width: '15%'},
              { display: '上传', name: 'file_path', width: '15%',editable:false,
            	  fileModel: {
                      keyField: 'file',
                      url: 'addFile.do?isCheck=false'
                  }
              },
              { display: '下载', name: 'file_path', width: '7%',editable:false,
            	  render:function(data) {
            		  var path = data.rowData.file_path;
            		  if(path=="" || !path){
            			  return '<a>下载</a>';
            		  }
            		  var array = path.split('/');
            		  var name = array[array.length-1];
                      return '<a href='+path+' download='+name+'>下载</a>';
                  }
              },
              { display: '预览', name: 'file_path', width: '7%',editable:false,
            	  render:function(data) {
                      return '<a class="toView" rowIndex = "'+data.rowIndx+'">预览</a>'
                  }
              },
         ];
         var paramObj = {
         	editable: true,
         	height: '100%',
         	inWindowHeight: true,
         	checkbox: true,
         	usePager: false,
             columns: docColumns,
             toolbar: {
                 items: [
                	 { type: 'button', label: '增加', listeners: [{ click: addDoc }], icon: 'add' },
                     { type: 'button', label: '保存',  listeners: [{ click: saveDoc }],  icon: 'save' },
                     { type: 'button', label: '删除',  listeners: [{ click:  delDoc }],  icon: 'del' }
                 ]
             }
         };
         docGrid =  $("#pactdoc").etGrid(paramObj);
         
     	$("#pactdoc").on('click','.toView',function(){
          	 var rowIndex = $(this).attr('rowIndex');
             var currentRowData = docGrid.getAllData()[rowIndex];
             toView(currentRowData);
        })
     };
     
     function toView(data){
     	if(!data.file_path){
     		return;
     	}
     	showFile(data.file_path);
     }
     
	function addDoc() {
		var pact_code = $("#pact_code").val();
		if(pact_code == null || pact_code == ""){
			 $.etDialog.error("请添加协议");
			return;
		}
		docGrid.addRow();
    }
	
	function saveDoc(){
		var pact_code = $("#pact_code").val();
		if(pact_code == null || pact_code == ""){
			 $.etDialog.error("请添加协议");
			return;
		}
		var param = [];
		var data = docGrid.getAllData();
		if(data == null || data.length == 0){
			 $.etDialog.error('请添加数据');
			 return;
		}
		
		var error ;
		 $(data).each(function () {
            var rowdata = this;
            var row = rowdata._rowIndx +1 ;
            if(!rowdata.pact_state){error = "第"+row + "行状态不能为空";return;}
            if(!rowdata.doc_type){error = "第"+row + "行文档类别不能为空";return;}
            if(!rowdata.dept_no){error = "第"+row + "行科室不能为空";return;}
            if(!rowdata.emp_id){error = "第"+row + "行责任人不能为空";return;}
            if(!rowdata.doc_name){error = "第"+row + "行文档名称不能为空";return;}
            if(!rowdata.location){error = "第"+row + "行存放位置不能为空";return;}
            if(!rowdata.file_path){error = "第"+row + "行文件不能为空";return;}
            rowdata.group_id = ${group_id};
            rowdata.hos_id = ${hos_id};
            rowdata.copy_code = '${copy_code}';
            rowdata.pact_code = pact_code;
            rowdata.dept_id = rowdata.dept_no;
            param.push(rowdata);
        });
		 if(error){$.etDialog.error(error);return; }
		 
		ajaxPostData({
		 	url: 'addPactDocSKXY.do?isCheck=false',
		 	 data: {
            	 mapVo: JSON.stringify(param)
             },
			  success: function (result) {
				  query($("#pact_code").val());
			  },
		});
	}
	
	function delDoc(){
		 var data = docGrid.selectGet();
         if (data.length == 0) {
             $.etDialog.error('请选择行');
         } else {
             var param = [];
             $(data).each(function () {
                 var rowdata = this.rowData;
                 rowdata.group_id = ${group_id};
                 rowdata.hos_id = ${hos_id};
                 rowdata.copy_code = '${copy_code}';
                 param.push(rowdata);
             });
             $.etDialog.confirm('确定删除?', function () {
                 ajaxPostData({
                     url: 'deletePactDocSKXY.do',
                     data: {
                    	 mapVo: JSON.stringify(param)
                     },
                     success: function () {
                    	 docGrid.deleteRows(data);
                     }
                 })
             });
         }
	}

	$(function(){
    	initSelect();
    	initPactDocGrid();
    	
    	query($("#pact_code").val());
    	if ('${pact_type_code}' && '${pact_code}') {
    		pact_type_code.disabled();
    		pact_code.disabled();
		}
	})
</script>
</head>

<body style="overflow: scroll; ">

	<table class="table-layout">
		<tr>
			<td class="label no-empty" style="width: 100px;">协议类型：</td>
			<td class="ipt"><select id="pact_type_code" style="width: 180px;"></select> </td>
			<td class="label no-empty" style="width: 100px;">协议名称：</td>
			<td class="ipt"><select id="pact_code" style="width: 180px;"/></select> </td>
		</tr>
	</table>
	<div id="pactdoc"></div>
</body>

</html>

