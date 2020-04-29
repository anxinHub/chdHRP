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
         loadDict()//加载下拉框
         loadHead(null);
         f_gridcharge_loadData();
 		 f_gridtitle_loadData();
         $("#layout1").ligerLayout({
             leftWidth: 200,
             centerBottomHeight:275
         });

     });  
     
     function  saveAphiDeptMaping(){
    	
    	 var data = gridChargeManager.getCheckedRows();
    	 var data2 = gridtitleManager.getCheckedRows();
    	
    	
 
        ajaxJsonObjectByUrl("addAphiDeptMaping.do",{},function(responseData){
            
            if(responseData.state=="true"){
				 
				
				 
                parent.query();
            }
       });
    }
     function loadHead() {

    	 var ref_code = liger.get("ref_code").getValue();
    	 
    	 
    	 var is_dept_flag;
    	 var is_sys_dept_flag;
    	 if(ref_code == '01'){
    		 is_dept_flag = true;
    		 is_sys_dept_flag = true;
    	 }else if(ref_code == '02'){
    		 is_dept_flag = false;
    		 is_sys_dept_flag = true;
    	 }else if(ref_code == '03'){
    		 is_dept_flag = true;
    		 is_sys_dept_flag = false;
    	 }
    	 
    	 var colunmName=[];
    	  colunmName.push({display : '绩效科室编码',name : 'dept_code',align : 'left'});
    	  
    	  if(ref_code =='02'){
    		  colunmName.push({display : '绩效科室名称',name : 'dept_name',align : 'left'});
    		  colunmName.push({display : '比例',name : 'spilt_perc',align : 'left', editor: { type: 'float'}});
 		   }else{
 			  colunmName.push({display : '绩效科室名称',name : 'dept_name',align : 'left'});
 		   }
    	  
    	gridcharge = $('#maingrid1').ligerGrid({
    	  columns : colunmName ,
    	  dataAction : 'server',dataType : 'server',usePager : true,
   	 	  url : 'queryAphiDept.do?isCheck=false',
   	      width : '100%',height : '50%',
   	      enabledEdit: true,
   	      title : '绩效科室',
   	      checkbox : true,
		  isSingleCheck:is_dept_flag
  	});
   	 gridChargeManager = $('#maingrid1').ligerGetGridManager();
    	
    	 
 		gridtitle = $("#maingrid2").ligerGrid({

 			columns : [{
 				display : '平台科室编码',
 				name : 'sys_dept_code',
 				align : 'left',
 				editor: { type: 'text'}
 			},{
 				display : '平台科室名称',
 				name : 'sys_dept_name',
 				align : 'left'
 			}],
 			dataAction : 'server',
 			dataType : 'server',
 			usePager : true,
 			url : 'queryDeptDictByPrmDept.do?isCheck=false',
 			width : '100%',
 			height : '50%',
 			checkbox : true,
 			title : '平台科室',
 			isSingleCheck:is_sys_dept_flag
 		});
 		gridtitleManager = $("#maingrid2").ligerGetGridManager();
 		
 		
 		gridTotal = $("#maingrid3").ligerGrid({

 			columns : [{ display: '绩效科室编码', name: 'dept_code', align: 'left'
	 		},
            { display: '绩效科室编码', name: 'dept_name', align: 'left'
			 		},
            { display: '平台科室编码', name: 'sys_dept_code', align: 'left'
			 		},
            { display: '平台科室名称', name: 'sys_dept_name', align: 'left'
			 		},
            { display: '关系', name: 'ref_name', align: 'left'
			 		},
            { display: '拆分比例', name: 'spilt_perc', align: 'left'
			 		}],
 			dataAction : 'server',
 			dataType : 'server',
 			usePager : true,
 			url : 'queryAphiDeptMaping.do',
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
         	$.ligerDialog.error('请选择行');
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
														this.dept_id   +"@"+ 
														this.sys_dept_id
														);
					}									
					});
             if(ParamVo == ""){
					return;
				}
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteAphiDeptMapping.do",{ParamVo : ParamVo.toString()},function (responseData){
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
    	 if(totalData.length == 0){
    		 $.ligerDialog.error('对应关系表不能为空');
			 return;
    	 }
    	 
    	 ajaxJsonObjectByUrl("addAphiDeptMaping.do",{maping_data : JSON.stringify(totalData)},function (responseData){
     		if(responseData.state=="true"){
     			query();
     		}
     	});
     }
  
   function query() {
	    gridTotal.options.parms = [];
	    gridTotal.options.newPage = 1;
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
			value : liger.get("dept_id").getValue().split(".")[0]
		});
		if($("#is_dept").prop("checked")){
			gridcharge.options.parms.push({
				name : 'is_dept',
				value : "1"
			});
		}
		//加载查询条件
		gridcharge.loadData(gridcharge.where);

	}
   
   function f_gridtitle_loadData() {
		gridtitle.options.parms = [];
		gridtitle.options.newPage = 1;
		//根据表字段进行添加查询条件
		gridtitle.options.parms.push({
			name : 'dept_id',
			value : liger.get("sys_dept_id").getValue().split(".")[0]
		});
		if($("#is_sys_dept").prop("checked")){
			gridtitle.options.parms.push({
				name : 'is_sys_dept',
				value : "1"
			});
		}
		
		//加载查询条件
		gridtitle.loadData(gridtitle.where);
	}
   
 function changerefcode()
 {
	 
	 loadHead();
	 gridcharge='';
	 //query();
 }
    function saveDeptMaping(){
    	
    	save();
       
   }
    function loadDict(){
    	
    	$("#ref_code").ligerComboBox({
          	url: "../queryPrmDeptRefDict.do?isCheck=false",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	inputlabelAlign:'center'
 		 });
    	liger.get("ref_code").setValue("01");
    	liger.get("ref_code").setText("01 平行");
    	
    	autocomplete("#dept_id","../queryPrmDeptDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#sys_dept_id","../../sys/queryDeptDict.do?isCheck=false","id","text",true,true);
    	
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
	   	 var ref_code = liger.get("ref_code").getValue();
	   	 var ref_name = liger.get("ref_code").getText().split(" ")[1];
	   	 if(ref_code == ""){
			 $.ligerDialog.error('对应关系不能为空');
			 return;
		 }
	   	for(var i = 0; i < data.length;i++){
	   		for(var k = 0; k < data2.length;k++){
	   			for(var j = 0;j < totalData.length;j++){
					 if(data[i].dept_id == totalData[j].dept_id && data2[k].sys_dept_id == totalData[j].sys_dept_id ){
						 $.ligerDialog.error('对应关系已存在');
						 return;
					 }
				 }
	   			data3.push({
					dept_id:data[i].dept_id,
					dept_code:data[i].dept_code,
					dept_name:data[i].dept_name,
					sys_dept_id:data2[k].sys_dept_id,
					sys_dept_code:data2[k].sys_dept_code,
					sys_dept_name:data2[k].sys_dept_name,
					spilt_perc:data[i].spilt_perc,
					ref_code:ref_code,
					ref_name:ref_name
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
		                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">绩效科室：</td>
		                <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" o /></td>
		                <td align="left"></td>
		                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="checkbox" id="is_dept" checked="checked"/></td>
		                <td align="left" class="l-table-edit-td">不包含对应关系科室</td>
		                <td align="left"><input class="l-button l-button-test" style="float: right;" type="button" value="查询" onclick="f_gridcharge_loadData()"/></td>
		            </tr> 
        		</table>
				<div  id="maingrid1"  ></div>
			</div>
		<div style="margin-left: 30px;margin-top:150px;    padding: 0; float: left;">
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
		            	<td align="center"><h2>映射规则</h2></td>
		            </tr> 
		            <tr>
		                <td align="center" class="l-table-edit-td"><input name="ref_code" type="text" id="ref_code" ltype="text" validate="{required:true,maxlength:20}" onchange="changerefcode()" /></td>
		            </tr> 
		        </table>
        </div>
		<div style="margin-left: 30px;     padding: 0; float: left;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	            <tr>
	                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">平台科室科室：</td>
	                <td align="left" class="l-table-edit-td"><input name="sys_dept_id" type="text" id="sys_dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
	                <td align="left"></td>
	                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="checkbox" id="is_sys_dept" checked="checked"/></td>
	                <td align="left" class="l-table-edit-td">不包含对应关系科室</td>
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
