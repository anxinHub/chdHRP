<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,datepicker,grid,ligerUI,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    
    $(function (){
    	loadHead(null);	//加载数据
    });
    //查询
    function  query(){
    	var parms=[];
        //根据表字段进行添加查询条件
    	parms.push({name:'rule_message',value:$("#rule_message").val()}); 
    	//加载查询条件
    	grid.loadData(parms,'queryBudgNoRules.do?isCheck=false');
     }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
	          { display: '单据名称', name: 'table_name', align: 'center',width:130
				 },
	          { display: '前缀', name: 'prefixe', align: 'center',width:130
				 },
	          { display: '是否包含年', name: 'is_year_text', align: 'center',width:110,
					 editor : {
							type : 'select',
							keyField : 'is_year',
							source:[ 
								{id : '0',label:'否'},{id : '1',label:'是'}
							],
							keySupport : true,
							autocomplete : true,
							change: function (rowData, cellData) {
								if (rowData.is_year == 0) {
									grid.updateRow(cellData.rowIndx, { is_budg_year: "" })
								}
							}
             	 	 },
             	 	 render:function(ui){
             	 		 var value = ui.rowData.is_year;
             	 		 if(value == 0){
             	 			return "否"; 
             	 		 }else{
             	 			return "是";
             	 		 }
					 }
				 },
	          { display: '年度取值', name: 'is_budg_year_text', align: 'center',width:150,editable:setEdit,
					 editor : {
							type : 'select',
							keyField : 'is_budg_year',
							source:[ 
								{id : '0',label:'当前年度'},{id : '1',label:'预算年度'}
							],
							keySupport : true,
							autocomplete : true,
          	 		 },
             	 	 render:function(ui){
             	 		 var value = ui.rowData.is_budg_year;
             	 		 if(ui.rowData.is_year == 0){
             	 			 return "";
             	 		 }
             	 		 if(value == 0){
             	 			return "当前年度"; 
             	 		 }else if(value == 1){
             	 			return "预算年度";
             	 		 }
					 }
				 },
			  { display: '是否包含月', name: 'is_month_text', align: 'center',width:110,
					 editor : {
							type : 'select',
							keyField : 'is_month',
							source:[ 
								{id : '0',label:'否'},{id : '1',label:'是'}
							],
							keySupport : true,
							autocomplete : true,
          	 	 	},
          	 	 	render:function(ui){
          	 	 		 var value = ui.rowData.is_month;
            	 		 if(value == 0){
            	 			return "否"; 
            	 		 }else{
            	 			return "是";
            	 		 }
					 }
				 },
	          { display: '流水号位数',width:80, name: 'seq_no', align: 'center',minWidth:130
				 }
           ],
           dataModel:{
 	           	 method:'POST',
 	           	 location:'remote',
 	           	 url:'',
 	           	 recIndx: 'table_name'
           }, 
           usePager:false,width: '100%', height: '100%',checkbox: false ,editable: true,
	       toolbar: {
               items: [
		           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] }
           	   ]
           }
        });
	 }
	 
 // 根据 是否包含年  返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.is_year == 0){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
    
	function save(rowindex,proj_code){
		var data = grid.getChanges();
    	var formPara =[];
    	if(data.updateList.length <= 0){
    		$.etDialog.warn('没有需要保存的数据!');
    		return;
    	}
    	var updateData = data.updateList ;
        $(updateData).each(function (){	
        	formPara.push(
				this.table_code   +"@"+ 
				this.table_name   +"@"+ 
				this.prefixe  +"@"+
				this.is_year +"@"+ 
				this.is_budg_year +"@"+ 
				this.is_month +"@"+ 
				this.seq_no 
			) 
		});
	    ajaxPostData({url: 'saveRules.do?isCheck=false',
		    data: {formPara : formPara.toString()},
		    success: function (responseData) {
		    	query();
		    }
		})
	}
    </script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none;"></div>
	<div id="toptoolbar" ></div>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">业务单据：</td>
				<td class="ipt">
					<input type="text" id="rule_message">
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>
