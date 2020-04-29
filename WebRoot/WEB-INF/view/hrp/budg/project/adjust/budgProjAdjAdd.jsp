<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script>
	
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var adj_amount;
	var proj_id;
	var proj_no;
	var budg_year;
	var source_id;
	var payment_item_id;
	$(function () {
			loadDict();
			loadHead();
			// loadForm();
			$("body").keydown(function() {
		  	   if (event.keyCode == "9") {//keyCode=9是Tab
		           grid.addRowEdited({
			          	source_id: '' , //资金来源
			          	payment_item_id: '' , //支出项目ID
			          	bef_remain_amount: '0' ,//调整前预算余额
			          	adj_amount: '0' , //调整金额
			          	aft_remain_amount : '0'  //调整后预算余额
			          	
		       		});
		       }
		    })
		 budg_year = liger.get("budg_year").getValue();

		 $("#budg_year").change(function(){
			 budg_year = liger.get("budg_year").getValue();
			 loadHead();
		 });
			
	     $("#proj_id").change(function(){
	    	proj_id = liger.get("proj_id").getValue().split(",")[0]; // 项目id
    		proj_no = liger.get("proj_id").getValue().split(",")[1]; // 项目变更号
	    	
	    	autocomplete("#emp_id","../../../budg/queryConEmpId.do?isCheck=false?&proj_id="+proj_id,"id","text",true,true,'',true,'',200);
	    	loadHead();
	     });
	    
	     
	})

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '资金来源', name: 'source_id', align: 'left',
							 valueField:"source_id",textField : "source_name",
					 			editor : {
									type : 'select',
									valueField : 'source_id',
									textField : 'source_name',
									url : '../balanceadjust/queryBudgSourceByProj.do?isCheck=false&proj_id='+proj_id,
									keySupport : true,
									autocomplete : true,
									onChanged: getSourceId
								},
								totalSummary: {
									align: 'left',
									render: function (suminf, column, cell) {
										return '<div>' + '合计' + '</div>';
									}
		               			 }
						 	},
                     { display: '支出项目', name: 'payment_item_id', align: 'left',
						 		valueField:"id",textField : 'text',
					 			editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									url : '../../queryPaymentItem.do?isCheck=false',
									keySupport : true,
									autocomplete : true,
									onChanged: loadBalance
								} 
                     
					 		},
                     { display: '调整前预算余额', name: 'bef_remain_amount', align: 'left',
					 			totalSummary: {
	 								align: 'left',
	 								render: function (suminf, column, cell) {
	 									return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum,2,1) + '</div>';
	 								}
	 							}
					 		},
                     { display: '预算调整(E)', name: 'adj_amount', align: 'left',editor : {type : 'float',onChanged : updateCell},
					 			totalSummary:{
					 				
	 								align: 'left',
	 								render: function (suminf, column, cell) {
	 									adj_amount = suminf.sum;
	 									return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum,2,1) + '</div>';
	 								}
	 							}
					 		},						
                     { display: '调整后预算余额', name: 'aft_remain_amount', align: 'left',
					 			render : function(item) {
					 				if(item.adj_amount == 0 ){
					 					return formatNumber(item.aft_remain_amount,
												2, 1);
					 				}else{
										return formatNumber(item.aft_remain_amount,
												2, 1);
					 				}
					 					
					 			},
					 				
					 			totalSummary: {
	 								align: 'left',
	 								render: function (suminf, column, cell) {
	 									return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum,2,1) + '</div>';
	 								}
	 							}
                            }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,
                     width: '100%', height: '100%', enabledEdit :true ,checkbox: true, rownumbers:true,
                     onAfterEdit: f_onAfterEdit, delayLoad:true,isAddRow:false,
                     onAfterShowData:initSum,
                     
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
    					{ text: '保存（<u>S</u>）', id:'add', click: save, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: removeRows,icon:'delete' },
		                { line:true },
		               /*  { text: '提交（<u>S</u>）', id:'submit', click: submit,icon:'settle' },
		                { line:true },
		                { text: '撤回（<u>C</u>）', id:'recall', click: recall,icon:'greenwarn' },
		                { line:true }, */
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true },
		                { text: '关闭（<u>R</u>）', id:'close', click: close,icon:'close' },
		                { line:true }
    				]}
                 });
    	
    	 function getSourceId(rowdata){
   	     	source_id = rowdata.value;
   	     	loadHead();
   	     	/* value.record.source_name = "";
   	     	value.record.source_id = ""; 
   	     	grid.updateRow(value.record,value.record); */
    	 }
    		
		 //当选定项目和资金来源后   调用方法查询调整资金
	    function loadBalance (value) {
	 		if(!value.record.source_id){
	 			$.ligerDialog.error("该行资金来源不能为空!")
	 			
	 		}
	 		
	    	var theRecord = value.record
	    	payment_item_id = theRecord.payment_item_id.split(",")[1];
	        //项目ID和资金来源ID必须都被选中后  触发方法
	        if (source_id && payment_item_id) { 
		            var data = {
		            	budg_year: budg_year,
		            	proj_id: proj_id,
		            	source_id: source_id,
		            	payment_item_id: payment_item_id
		            }
		            var url = "queryBudgProjAdjAdd.do?isCheck=false";
		            
		           	$.ajax({
		               	type:"POST",
		               	url: url,
		               	data: data,
		               	dataType: "json",
		               	success: function (result) {
		               		if(result){
								theRecord.bef_remain_amount = result.bef_remain_amount== null ? "0" :result.bef_remain_amount;
								theRecord.adj_amount = result.adj_amount== null ? "0" :result.adj_amount;
								theRecord.aft_remain_amount = result.aft_remain_amount== null ? "0" :result.aft_remain_amount;
								//重新渲染
		                   		grid.reRender(); 
		               		}else {
		               			theRecord.bef_remain_amount = "0";
								theRecord.adj_amount = "0";
								theRecord.aft_remain_amount = "0";
								//重新渲染
		                   		grid.reRender(); 
		               		}
		               	},
		           })
		        }
		        
		    }
        gridManager = $("#maingrid").ligerGetGridManager();
    }
   
	
	//编辑前记录  rowindex 、column_name
    var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;
	}
	
	//即时计算调整前后余额值
    function f_onAfterEdit(e) {
			grid.updateTotalSummary();
	}
    
	function updateCell(e){
		
		if(e.record.adj_amount){
			e.record.aft_remain_amount =  Number(e.record.adj_amount) + Number(e.record.bef_remain_amount)
			//重新渲染表格
			grid.updateCell("aft_remain_amount",e.record.aft_remain_amount,e.record);
		}
	}
	
    function initSum(){
    	grid.updateTotalSummary();
    }
	
  	//校验数据
	function validateGrid(data) {  
     	var msg="";
  		var rowm = "";
  		//判断grid 中的数据是否重复或者为空
  		var targetMap = new HashMap();
  		$.each(data,function(i, v){
  			rowm = "";
 			if (v.source_name == "" || v.source_name == null || v.source_name == 'undefined') {
 				rowm+="[资金来源]、";
 			}
 			if (v.adj_amount == "" || v.adj_amount == null || v.adj_amount == 'undefined') {
 				rowm+="[调整金额]、";
 			}
 			if(rowm != ""){
 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
 			}
 			msg += rowm;
 			var key=v.source_id + v.payment_item_id ;
 			var value="第"+(i+1)+"行";
 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
 				targetMap.put(key ,value);
 			}else{
 				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
 			}
  		});
  		if(msg != ""){
  			$.ligerDialog.warn(msg);  
 			return false;  
  		}else{
  			return true;  
  		} 	
	}
  	
    
    //删除行
    function removeRows(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	gridManager.deleteRange(data);
        }
    }
    
    //保存
    function  save () {
    	var data = grid.getData();
    	if(data.length>0){
    		if(!validateGrid(data)){
    			return false;
    		}
    		var budg_year = liger.get("budg_year").getValue();
        	var formPara={
           		budg_year:budg_year,
           		adj_code:$("#adj_code").val(),
                proj_id:proj_id,
                proj_no:proj_no,
                remark:$("#remark").val(),
                file_url:$("#file_url").val(),
                adjData : JSON.stringify(data) 
     	           
            };
            ajaxJsonObjectByUrl("addBudgProjAdj.do?isCheck=false",formPara,function(responseData){		            
                if(responseData.state=="true"){
                	parentFrameUse().query();
                }
            });
    	}else{
    		$.ligerDialog.error('没有需要保存的数据!');
    	}
       
    }
    
    //提交
    function submit(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }
        var ParamVo =[];
        $(data).each(function (){					
			ParamVo.push(
			this.group_id   +"@"+ 
			this.hos_id   +"@"+ 
			this.copy_code   +"@"+ 
			this.adj_code   +"@"+ 
			this.proj_id   +"@"+ 
			this.proj_no   +"@"+ 
			this.remark   +"@"+
			this.file_url  +"@"+
			this.state
			) });
        $.ligerDialog.confirm('确定提交?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("submitbudgProjAdj.do",{ParamVo : ParamVo.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
    }
    
    //撤回
    function recall(){
    	var data = gridManager.getCheckedRows();
        if (data.length === 0){
        	$.ligerDialog.error("请选择行");
        }
        var ParamVo =[];
        $(data).each(function (){					
			ParamVo.push(
			this.group_id   +"@"+ 
			this.hos_id   +"@"+ 
			this.copy_code   +"@"+ 
			this.adj_code   +"@"+ 
			this.proj_id   +"@"+ 
			this.proj_no   +"@"+ 
			this.remark   +"@"+
			this.file_url  +"@"+
			this.state
			) });
        $.ligerDialog.confirm('确定撤回?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("recallbudgProjAdj.do",{ParamVo : ParamVo.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        });
    }
    
    //打印
    function printDate(){
    	if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
    	grid.options.newPage=1;
		
    	grid.options.parms.push({name : 'proj_id',value:liger.get("#proj_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name : 'emp_id',value:liger.get("emp_id").getValue()}); 
   	    grid.options.parms.push({name : 'state',value:liger.get("state").getValue()});
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"项目预算调整添加明细",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryCertenHYInBudgUp.do?isCheck=false&edit_method=03", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
    }
    
    //关闭
    function close(){
    	frameElement.dialog.close();
    }	
   
    function loadDict(){
      //字典下拉框
      	 //预算年度下拉框
    	 autocompleteAsync("#budg_year","../../../budg/queryBudgYear.do?isCheck=false","id","text",true,true,"",true);
       
         //项目名称下拉框
         autocompleteAsync("#proj_id","../../../budg/queryProjName.do?isCheck=false","id","text",true,true,'',false,'',450);   
         
       	 
       	 //初始化项目负责人下拉框（预算指标下拉框 与 科室下拉框下拉框 联动）
   		 $("#emp_id").ligerComboBox({});
       	 
         $("#adj_code").ligerTextBox({width:200,disabled:true});  
         $("#budg_year").ligerTextBox({width:200});  
         $("#proj_id").ligerTextBox({width:300});   
         $("#emp_id").ligerTextBox({width:200,disabled:true});  
         $("#remark").ligerTextBox({width: 585});
         $("#file_url").ligerTextBox({width: 870})
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		/* hotkeys('B', downTemplate);
		   hotkeys('E', exportExcel); 
		hotkeys('P', printDate);
		hotkeys('I', imp); */
		

	 }
 	  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td">调整单号<span style="color:red">*</span>:</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="adj_code" type="text" id="adj_code" ltype="text" disabled="disabled" value="系统生成" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left" class="l-table-edit-td"></td>
            <td align="right" class="l-table-edit-td">预算年度<span style="color:red">*</span>:</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left" class="l-table-edit-td"></td>
            <td align="right" class="l-table-edit-td"  >项目名称：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left" class="l-table-edit-td"></td>
        </tr>
	    <tr> 
            <td align="right" class="l-table-edit-td"  >项目负责人：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="emp_id" type="text" id="emp_id" ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left" class="l-table-edit-td"></td>
	    	<td align="right" class="l-table-edit-td">调整说明：</td>
            <td align="left" class="l-table-edit-td" colspan="4">
            	<input name="remark" type="text" id="remark" ltype="text" validate="{required:true,maxlength:20}" />
            </td>   
            <td align="left" class="l-table-edit-td"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  >相关文件：</td>
            <td align="left" class="l-table-edit-td" colspan="7">
            	<input name="file_url" type="text" id="file_url" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left" >
            	<button id ="upFile" accessKey="A"><b>上传文件（<u>A</u>）</b></button>
            </td>
            <td align="left" class="l-table-edit-td"></td>
        </tr>
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
