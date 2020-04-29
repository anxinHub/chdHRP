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
<%-- <script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script> --%>
<!-- script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script-->

<script type="text/javascript">
var grid;
var SYSFromSources= [];   
var pact_type_code;
var pact_code;
var PIT_SYSFrom;
var initSelect=  function(){
	PIT_SYSFrom = $("#PIT_SYSFrom").etSelect({  url: 'COSTBUSISOURECDICT.do?isCheck=false', defaultValue: ""});
}



var query = function () {
	
	var param = [];
    params = [
        { name: 'PIT_TypeCode', value: $("#CodeResource").val() },
        { name: 'PIT_SYSFrom', value: $("#PIT_SYSFrom").val() }    
    ];
    grid.loadData(params);
};
     
	function initPactDocGrid(){
		
         var docColumns = [
        	 { display: '编码', name: 'PIT_TypeCode', width: '15%',editable:true},
        	 { display: '名称', name: 'PIT_TypeName', width: '15%',editable:true},
        	 { display: '方法名', name: 'PI_MethodName', width: '15%',editable:true},
        	 { display: '类名', name: 'PI_ClassName', width: '15%',editable:true},
        	 { display: '类描述', name: 'PI_ClassDesc', width: '15%',editable:true},
        	 { display: 'bean名', name: 'PI_BEANNAME', width: '15%',editable:true},      	
        	 { display: '来源系统', name: 'pit_sysfrom_name', align: 'center' ,width: '15%',editor:{
        		     type: 'select',
        		     keyField: 'PIT_SYSFrom',
        		     url:"COSTBUSISOURECDICT.do?isCheck=false"
        		},	  	  
           	},
        	 { display: '是否有效', name: 'PIT_IsActive', width: '15%',hidden:true}    
        	
         ];      
        
         var paramObj = {
        	dataModel: {
        		url : 'queryPACTInterfaceType.do?isCheck=false&PIT_IsActive=1',
        	},
         	editable: true,
         	height: '100%',
         	inWindowHeight: true,
         	checkbox: true,
         	usePager: false,
             columns: docColumns,
             toolbar: {
               items: [
                	 { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                	 { type: 'button', label: '增加', listeners: [{ click: add_click }], icon: 'add' },
                     { type: 'button', label: '保存',  listeners: [{ click: save_click }],  icon: 'save' },
                     { type: 'button', label: '删除',  listeners: [{ click:  del_click }],  icon: 'del' }
                 ]
             }
         };       
        grid =  $("#PACTInterfaceType").etGrid(paramObj);   
     };
     
   
     

	function add_click() {
		grid.addRow();
    }
	

	
	
	
	function save_click(){
		 var data = grid.selectGet();
         if (data.length == 0) {
             $.etDialog.error('请选择行');
             return;
         }
		var error="";
		var param = [];
		if(data!=null && data.length != 0){
			 $(data).each(function () {
	            var rowdata = this.rowData;
	            var row = rowdata._rowIndx +1 ;		       
	            if(!rowdata.PIT_TypeCode){error = "第"+row + "编码不能为空";return;}
	            if(!rowdata.PIT_TypeName){error = "第"+row + "名称不能为空";return;}
	            if(!rowdata.PI_MethodName){error = "第"+row + "方法名不能为空";return;}
	            if(!rowdata.PI_ClassName){error = "第"+row + "类名不能为空";return;}
	            if(!rowdata.PI_ClassDesc){error = "第"+row + "类描述不能为空";return;}
	            if(!rowdata.PI_BEANNAME){error = "第"+row + "bean名不能为空";return;}
	            if(!rowdata.PIT_SYSFrom){error = "第"+row + "来源系统不能为空";return;}
	            var status=rowdata.PIT_IsActive	          	                      
	            rowdata.IFB_GROUPID = ${IFB_GROUPID};
	            rowdata.IFB_PrjName = ${IFB_PrjName};
	            rowdata.COPY_CODE = '${COPY_CODE}';	          
	            if(rowdata.PIT_IsActive==1){
	            	param.push(rowdata);
	            	updateByStatus(param);	            	
	            	}else{
	            	  rowdata.PIT_IsActive=1
	            	  param.push(rowdata);
	            	  addByStatus(param);	            		
	            	}
	            //alert( rowdata.group_id+","+rowdata.hos_id+","+rowdata.copy_code+"+[]+"+CodeResource+rowdata.PIT_TypeCode+rowdata.PIT_TypeName+rowdata.PI_MethodName+rowdata.PI_ClassName+rowdata.PI_ClassDesc+rowdata.PI_BEANNAME+rowdata.PIT_SYSFrom)            
	        });
	        if(error.length!=0){$.etDialog.error(error);return;}
		}
		
		
	}
	
	
	function addByStatus(param){
		console.log(param);
		
		ajaxPostData({
		 	url: 'addPACTInterfaceType.do?isCheck=false',
		 	 data: {
            	 mapVo: JSON.stringify(param)
             },
			  success: function (result) {
				  query();
			  },
		});
	}
	
	function updateByStatus(param){
		
		console.log(param);
		ajaxPostData({
		 	url: 'updatePACTInterfaceType.do?isCheck=false',
		 	 data: {
            	 mapVo: JSON.stringify(param)
             },
			  success: function (result) {
				  query();
			  },
		});
	}
	
	
	
	
	function del_click(){
		 var data = grid.selectGet();
         if (data.length == 0) {
             $.etDialog.error('请选择行');
             return;
         } else {
             var param = [];
             $(data).each(function () {
                 var rowdata = this.rowData;
                 rowdata.IFB_GROUPID = ${IFB_GROUPID};
 	             rowdata.IFB_PrjName = ${IFB_PrjName};
 	             rowdata.COPY_CODE = '${COPY_CODE}';
 	             rowdata.PIT_IsActive=0;
                 param.push(rowdata);
             });
            //console.log(param);
             $.etDialog.confirm('确定删除?', function () {
                 ajaxPostData({
                     url: 'deletePACTInterfaceType.do',
                     data: {
                    	 mapVo: JSON.stringify(param)
                     },
                     success: function () {
                    	 grid.deleteRows(data);
                    	 query();
                     }
                 })
             });
         }
	}

      
	
	$(function(){
		initSelect();
    	initPactDocGrid();	
    	
	})
	
	
</script>
</head>

<body>
	<table class="table-layout">
		<tr>
			<td class="label no-empty" style="width: 100px;">接口来源</td>
			<td class="ipt"><input id="CodeResource" style="width: 180px;"></input> </td>
			<td class="label no-empty" style="width: 100px;">来源系统</td>
			<td class="ipt"> <select id="PIT_SYSFrom" style="width: 180px"></select> </td>
		</tr>
	</table>
	<div id="PACTInterfaceType"></div>
</body>

</html>

