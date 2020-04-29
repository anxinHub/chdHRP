<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	loadHead("");	//加载数据
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	if($("#dept_code").val() != null && $("#dept_code").val() != ''){
    		grid.options.parms.push({name:'dept_code',value:$("#dept_code").val()}); 
        } 
    	if($("#cost_custom_dept_code").val() != null && $("#cost_custom_dept_code").val() != ''){
    		grid.options.parms.push({name:'cost_custom_dept_code',value:$("#cost_custom_dept_code").val()}); 
        } 
   	    /* if(liger.get("is_stop").getValue() != null && liger.get("is_stop").getValue() != ''){
   	    	grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue()}); 
        } */
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    function loadHead(dept_id){
   		grid = $("#maingrid").ligerGrid({
	           columns: [ 
						 { display: 'ref_id', name: 'ref_id', align: 'left',width:50,hide:true},
						 { display: 'ID', name: 'dept_id', align: 'left',width:50,hide:true},
						 { display: 'ZDYID', name: 'cost_custom_dept_id', align: 'left',width:50,hide:true},
	                     { display: '系统科室编码', name: 'dept_code', align: 'left',width:'15%'},
	                     { display: '系统科室名称', name: 'dept_name', align: 'left',width:'30%'},
	                     { display: '自定义科室编码', name: 'cost_custom_dept_code', align: 'left',width:'15%'},
	                     { display: '自定义科室名称', name: 'cost_custom_dept_name', align: 'left',width:'30%'}, 
						 { display: '是否停用', name: 'is_stop', align: 'left',
								render : function(rowdata, rowindex,value) {
									if(rowdata.is_stop == 0){
										return "否";
									}else{
										return "是"
									}
								},width:'8%'
					 	 }
						 ],
					 dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostHrpDeptExecRef.do?isCheck=false',
                     height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,
                     toolbar:{ items: [
									   { text: '查询', id:'search', click: query,icon:'search' },
									   { line:true },
                                       {text: '添加', icon:'add', click:addDeptExecRef },
									   { line:true },
                                       //{text: '删除',icon:'delete', click:deleteDeptExecRef  },
                                       {text: '停用',icon:'delete', click:endDeptExecRef  },
									   { line:true },
                                       {text: '导入',icon:'up', click:importDeptExecRef  },
									   { line:true },
                                       {text: '模板', icon:'down', click:downTemp }
                    ]},
                    checkbox:true,
                   /*  onDblClickRow : function (rowdata, rowindex, value)
    				{
                    	updateDeptRef(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.ref_id 
							);
    				}  */
   		});
    	gridManager = $("#maingrid").ligerGetGridManager();
    }

    function loadDict(){
	       /* 	$("#is_stop").ligerComboBox({
	       		 data:[{id:1,text:'是'},{id:0,text:'否'}],
	       		 valueField: 'id',
	                textField: 'text',
	    			 cancelable:true
	       	 ,width:160}); */
	       	$("#dept_code").ligerTextBox({width:160});
	       	
	    	$("#cost_custom_dept_code").ligerTextBox({width:160});
         }

    </script>

	<script type="text/javascript">
       
       $(function ()
       {
		   $("#btn_saveColumn").click(function(){
			   var path = window.location.pathname+"/maingrid";
			   var url = '../addBatchSysTableStyle.do?isCheck=false';
			   saveColHeader({
				   grid:grid,
				   path:path,
				   url:url
			   });
		   })
       });
       function downTemp(){
    	   location.href = "downTemplate.do?isCheck=false";
       }
        
       function addDeptExecRef(){
    	   $.ligerDialog.open({url: 'addDeptExecRefPage.do?isCheck=false', height: 400,width: 400, title:'新增自定义科室对应关系',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true ,
    		   buttons: [  
				           { text: '保存', onclick: function (item, dialog) { dialog.frame.saveDept(); },cls:'l-dialog-btn-highlight' }, 
				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
				         ]});
    	   query();
       }
       function updateDeptRef(rowStr){
    	   var rowArr=rowStr.split("|");
    	   $.ligerDialog.open({url : 'updateDeptExecRefPage.do?isCheck=false&ref_id=' + rowArr[2]+'&hos_id='+rowArr[1]+'&group_id='+rowArr[0], 
    			   height: 400,width: 400, title:'自定义科室对应关系修改',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true ,
    		   buttons: [  
				           { text: '保存', onclick: function (item, dialog) { dialog.frame.saveGroup();},cls:'l-dialog-btn-highlight' }, 
				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
				         ]});
       }
       function importDeptExecRef(){
    	   $.ligerDialog.open({url: 'importDeptExecRefPage.do?isCheck=false', height: 430,width: 1000, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
       }
       function deleteDeptRef(){
   		var data = grid.getSelectedRows();
   		if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }
   		var ParamVo =[];
   	 	$(data).each(function (){	
   			ParamVo.push(this.group_id+"@"+this.hos_id+"@"+this.ref_id);
   		});
   		
         $.ligerDialog.confirm('确定删除?', function (yes){
          	if(yes){
              	ajaxJsonObjectByUrl("deleteCostHrpDeptExecRef.do",{ParamVo : ParamVo.toString()},function (responseData){
              		if(responseData.state=="true"){
              			query();
              		}
              	});
          	}
          }); 
   		
   	}
       
    function endDeptExecRef(){
   		var data = grid.getSelectedRows();
   		if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }
   		var ParamVo =[];
   	 	$(data).each(function (){	
   			ParamVo.push(this.group_id+"@"+this.hos_id+"@"+this.ref_id);
   		});
   		
         $.ligerDialog.confirm('确定停用吗?', function (yes){
          	if(yes){ 
              	ajaxJsonObjectByUrl("endCostHrpDeptExecRef.do",{ParamVo : ParamVo.toString()},function (responseData){
              		if(responseData.state=="true"){
              			query();
              		}
              	});
          	}
          }); 
   		
   	}
     //打印
   	function print(){
       	
       	if(grid.getData().length==0){
       		
   			$.ligerDialog.error("请先查询数据！");
   			
   			return;
   		}
       	
       	var selPara={};
       	console.log(grid.options.parms)
       	$.each(grid.options.parms,function(i,obj){
       	
       		selPara[obj.name]=obj.value;
       	  
       	});
      		
     
   		var dates = getCurrentDate();
   		
       	var cur_date = dates.split(";")[2];
       	//跨所有列:计算列数
       	var colspan_num = grid.getColumns(1).length-1;
       	
      		var printPara={
      			title:'成本自定义科室',
      			head:[
   				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
   			/* 	{"cell":0,"value":"统计日期: " + $("#begin_in_date").val() +" 至  "+ $("#end_in_date").val(),"colspan":colspan_num,"br":true} */
      			],
      			columns:grid.getColumns(1),
      			headCount:1,//列头行数
      			autoFile:true,
      			type:3
      		};
      		ajaxJsonObjectByUrl("queryCostHrpDeptExecRef.do?isCheck=false", selPara, function (responseData) {
      			printGridView(responseData,printPara);
   		});

      		
       }

   </script>
</head>

 <body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading"></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">系统科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text"  /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">自定义科室：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_custom_dept_code" type="text" id="cost_custom_dept_code" ltype="text"  /></td>
            <td align="left"></td>
            <!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text"  /></td>
            <td align="left"></td> -->
           
       </tr>
      </table>
      <div id="maingrid"></div>
	
</body>
</html>
