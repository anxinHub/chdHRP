<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
     var dataFormat;
     var gridcharge;
 	var gridtitle;
 	var gridTotal;
 	var gridChargeManager = null;
	var gridtitleManager = null;
	var gridTatalManager = null;
     $(function (){
    	 $("#acct_yearm").ligerTextBox({ width:160 });
    	 	autodate("#acct_yearm","yyyymm");
         loadDict()//加载下拉框
         loadHead(null);
         f_gridcharge_loadData();
 		 f_gridtitle_loadData();
         $("#layout1").ligerLayout({
             leftWidth: 200,
             centerBottomHeight:275
         });

     });  
     
     function loadHead() {

    	 var colunmName=[];
    	  colunmName.push({display : '绩效科室编码',name : 'dept_code',align : 'left'});
    	  
    	  colunmName.push({display : '绩效科室名称',name : 'dept_name',align : 'left'});
    	  
    	gridcharge = $('#maingrid1').ligerGrid({
    	  columns : colunmName ,
    	  dataAction : 'server',dataType : 'server',usePager : true,
   	 	  url : 'queryDeptCost.do?isCheck=false',
   	      width : '100%',height : '48%',
   	      enabledEdit: true,
   	      title : '科室',
   	      checkbox : true,
		  isSingleCheck:true
  	});
   	 gridChargeManager = $('#maingrid1').ligerGetGridManager();
    	
    	 
 		gridtitle = $("#maingrid2").ligerGrid({

 			columns : [{
 				display : '支出项目编码',
 				name : 'cost_item_code',
 				align : 'left',
 				editor: { type: 'text'}
 			},{
 				display : '支出项目名称',
 				name : 'cost_iitem_name',
 				align : 'left'
 			}],
 			dataAction : 'server',
 			dataType : 'server',
 			usePager : true,
 			url : 'queryDeptItemAdd.do?isCheck=false',
 			width : '100%',
 			height : '48%',
 			checkbox : true,
 			title : '支出项目',
 			isSingleCheck:false
 		});
 		gridtitleManager = $("#maingrid2").ligerGetGridManager();
 		
 		
 		gridTotal = $("#maingrid3").ligerGrid({

 			columns : [
			{ display: '年份', name: 'acct_year', align: 'left'
					},
			{ display: '月份', name: 'acct_month', align: 'left'
					},  
 			{ display: '绩效科室编码', name: 'dept_code', align: 'left'
	 				},
            { display: '绩效科室编码', name: 'dept_name', align: 'left'
			 		},
            { display: '支出项目编码', name: 'cost_item_code', align: 'left'
			 		},
            { display: '支出项目名称', name: 'cost_iitem_name', align: 'left'
			 		}],
 			dataAction : 'server',
 			dataType : 'server',
 			usePager : true,
 			url : 'queryHpmDeptCostItemMaping.do',
 			width : '98%',
 			height : '44%',
 			enabledEdit : true,
 			alternatingRow : true,
 			rownumbers:true,
 			checkbox : true,//heightDiff: -10,
            toolbar: { items: [
            	{ text: '删除', id:'delete', click: deleteDeptMaping,icon:'delete' },
		    	{ line:true },
		    	{ text: '保存', id:'add', click: addDeptMaping, icon:'add' },
		    	{ line:true },
		    	{ text: '关闭', id:'close', click: this_close,icon:'delete' }
		    	               
		    				]}
 		});
 		gridTotalManager = $("#maingrid3").ligerGetGridManager();
 		
 	}
     
     function deleteDeptMaping(){
    	 var data = gridTotalManager.getCheckedRows();

	     if (data.length == 0){
         	$.ligerDialog.warn('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
            	 if(isnull(this.group_id)){
            		 gridTotalManager.deleteSelectedRow();
					}else{
            	 
														ParamVo.push(
														this.group_id   +"@"+ 
														this.hos_id   +"@"+ 
														this.copy_code   +"@"+
														this.acct_year +"@"+
														this.acct_month +"@"+
														this.dept_id   +"@"+ 
														this.cost_item_code
														);
					}									
					});
             if(ParamVo == ""){
					return;
				}
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteHpmDeptCostItemMapping.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
     }
     
     function addDeptMaping(){
    	 var totalData =  gridTotalManager.getData();
    	 
    	 //过滤空行
    	 var totalDataArr = new Array();
    	 $.each(totalData,function(){
    		 if(this.dept_code != null){
    			 totalDataArr.push(this);
    		 }
    	 });
    	 
    	 if(totalData.length == 0){
    		 $.ligerDialog.warn('对应关系表不能为空');
			 return;
    	 }
    	 
    	 ajaxJsonObjectByUrl("addHpmDeptCostItemMaping.do",{maping_data : JSON.stringify(totalDataArr)},function (responseData){
     		if(responseData.state=="true"){
     			query();
     		}
     	});
     }
  
   function query() {
	    gridTotal.options.parms = [];
	    gridTotal.options.newPage = 1;
	    gridTotal.options.parms.push({name : 'acct_yearm',value : $("#acct_yearm").val()});
		gridTotal.loadData(gridTotal.where);
		f_gridcharge_loadData();
		f_gridtitle_loadData();
 	}  
   
   function f_gridcharge_loadData() {
		gridcharge.options.parms = [];
		gridcharge.options.newPage = 1;
		//根据表字段进行添加查询条件
		gridcharge.options.parms.push({ 
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split(",")[0]
		});
		//加载查询条件
		gridcharge.loadData(gridcharge.where);

	}
   
   function f_gridtitle_loadData() {
		gridtitle.options.parms = [];
		gridtitle.options.newPage = 1;
		//根据表字段进行添加查询条件
		gridtitle.options.parms.push({
			name : 'cost_item_code',
			value : liger.get("cost_item_code").getValue()
		});
		
		//加载查询条件
		gridtitle.loadData(gridtitle.where);
	}
   
    function loadDict(){
    	
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	autocomplete("#cost_item_code","../../hpm/queryAphiCostItem.do?isCheck=false", "id", "text",true, true);
    	
    	$("#addData").ligerButton({click: addData, width:90});
		$("#close").ligerButton({click: this_close, width:90});
    	
     } 
    function this_close(){
		frameElement.dialog.close();
	}    
    function addData(){
    	 var data3 = [];
    	 
    	 var data = gridChargeManager.getCheckedRows();
   	 	 var data2 = gridtitleManager.getCheckedRows();
   	 	 var totalData =  gridTotalManager.getData();
   	 	 var acct_year = $("#acct_yearm").val().substr(0,4);
	   	 var acct_month = $("#acct_yearm").val().substr(4,7);
	   	for(var i = 0; i < data.length;i++){
	   		for(var k = 0; k < data2.length;k++){
	   			for(var j = 0;j < totalData.length;j++){
					 if(data[i].dept_id == totalData[j].dept_id && data2[k].cost_item_code == totalData[j].cost_item_code ){
						 $.ligerDialog.warn('对应关系已存在');
						 return;
					 }
				 }
	   			data3.push({
	   				acct_year : acct_year,
	   				acct_month : acct_month,
					dept_id:data[i].dept_id,
					dept_no:data[i].dept_no,
					dept_code:data[i].dept_code,
					dept_name:data[i].dept_name,
					cost_item_code:data2[k].cost_item_code,
					cost_iitem_name:data2[k].cost_iitem_name
				 });
	   		}
		 }
	   	 
	   
		 //var str = "[{\"Rows\":"+JSON.stringify(data3)+",\"Total\":"+total+"}]";
   	 	 gridTotal.addRows(data3);
   	 	
    }
    </script>
  
  </head>
  
<body>
<div id="layout1">
       <div position="center" >
	        <div style="margin-left: 30px;    padding: 0; float: left;">
	        		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	           <tr>
	               <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
	               <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}"  /></td>
	               <td align="left"></td>
	               <td align="left"><input class="l-button l-button-test" style="float: right;" type="button" value="查询" onclick="f_gridcharge_loadData()"/></td>
	           </tr> 
	     		</table>
			<div  id="maingrid1"  ></div>
		</div>
		<div style="margin-left: 30px;margin-top:150px;    padding: 0; float: left;">
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>	
						<td align="center">核算年月：</td>
					</tr>
					<tr>	
						<td align="center" class="l-table-edit-td"><input
						name="acct_yearm" class="Wdate" type="text" id="acct_yearm"
						ltype="text" validate="{required:true,maxlength:20}"
						onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" onchange="query()"/></td>
		      		</tr>
		        </table>
        </div>
		<div style="margin-left: 30px;     padding: 0; float: left;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	            <tr>
	                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出项目：</td>
	                <td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
	                <td align="left"></td>
	                <td align="left"><input class="l-button l-button-test" style="float: right;" type="button" value="查询" onclick="f_gridtitle_loadData();"/></td>
	            </tr> 
        	</table>
			<div  id="maingrid2"></div>
		</div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<button id ="addData"><b>添加</b></button>
				</td>
			</tr>
		</table>
     </div>  
     <div position="centerbottom" >
        <div style="margin-left: 30px;padding: 0; ">
			<div id="maingrid3"></div>
		</div>
     </div>  
</div> 
    </body>
</html>
