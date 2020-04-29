<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="grid,select,datepicker,ligerUI" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var addData ;// 记录添加数据用变量
    var state = '${state}' ;
    var isCount = false;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		
		if(${state}=='0'){
			$("#state").attr("state_name", "0")
			$("#state").val("未记账");
		}else if(${state}=='1'){
			$("#state").attr("state_name", "1")
			$("#state").val("记账");
		}
		
		$("#cash_begin_year").change(function(){
			count();
        });
    });
    //查询
    function  query(){
    	var parms = [];
    	//加载查询条件
    	grid.loadData(parms);
     }
    
    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
                { display: '现金流量项目', name: 'cash_item_name', align: 'left',width:'35%',editable: setEdit,
                 	editor : {
						type : 'select',
						keyField : 'cash_item_id',
						url : 'queryCashItem.do?isCheck=false',
						keySupport : true,
						autocomplete : true,
						change: function (rowData, cellData) {
							grid.updateRow(cellData.rowIndx, {cash_dire: cellData.selected.cash_dire})
						}
                 	}
		 		},
			 	{ display: '金额(元 )(E)', name: 'amount', align: 'right',dataType: 'float',width:'30%',editable: setEditByState,
		 			render:function(ui) {
						return formatNumber(ui.rowData.amount, 2, 1)
					}
		 		},
                { display: '备注(E)', name: 'remark', align: 'left',editor:{type:"string"},minWidth:'380', editable: setEditByState,}
           ],
           dataModel: {
			   method: 'POST',
			   location: 'remote',
			   url: 'queryBudgCashFlowBegin.do?isCheck=false',
			   recIndx: 'cash_item_name'
		   },
		   usePager: false, width: '100%', height: '100%', checkbox: true, editable: true,
		   addRowByKey: false,
           toolbar: { 
        	   items: [
		           { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
		           { type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
		           { type: "button", label: '删除', icon: 'minus', listeners: [{ click: remove }] },
		           { type: "button", label: '导入', icon: 'search', listeners: [{ click: impNew }] },
		           { type: "button", label: '记账', icon: 'check', listeners: [{ click: charge }] },
		           { type: "button", label: '反记账', icon: 'closethick', listeners: [{ click: unCharge }] },
		           { type: "button", label: '添加行', icon: 'plus', listeners: [{ click: addRow }] },
			   ]
		   },
		   load: function () {
				if (isCount) {
					changeCount()
   					isCount = false
				}
			}
        });
    }
    
 	// 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
	function setEdit(ui) {
		if (ui.rowData && ui.rowData.group_id) {
			return false;
		} else {
			return true;
		}
	}
    
 	// 根据 state 是否存在 返回 true 或 false  控制金额及说明单元格可否编辑 用 
	function setEditByState(ui) {
		if (ui.rowData && state == 1) {
			return false;
		} else {
			return true;
		}
	}
	
	function addRow(){
		 ajaxPostData({
             url: "queryBudgCashBeginState.do?isCheck=false",
             data: {},
             success: function(responseData) {
                 if (responseData.state == "true") {
                	 grid.addRow();
                 }
             }
         });
	}
	
  	function  save(){ 
	  	ajaxPostData({
	        url: "queryBudgCashBeginState.do?isCheck=false",
	        data: {},
	        success: function(responseData) {
	            if (responseData.state == "true") {
	            	var data = grid.getAllData();
	                if (data.length == 0){
	                   $.etDialog.error('请添加行数据');
	                }else{
	                  	if(!validateGrid(data)){
	                  		return false;
	                  	}
	          	    	var ParamVo =[];
	          	    	var changedData = changeCount(); 
	          	        $(data).each(function (){
	          	        	if(this.cash_item_name && (this.amount || this.amount == 0)){
	          	        		ParamVo.push(
	 	          					(this.cash_item_id ? this.cash_item_id : this.cash_item_name)  +"@"+ 
	 	          					this.amount   +"@"+ 
	 	          					(this.remark?this.remark:"-1")  
	          	     		    )
	          	        	}
	          	        });
	          	         
	          	        if(!ParamVo){
	          	        	$.etDialog.error('没有需要保存的数据,请确认添加数据后操作!'); 
	          	        }
		          	    ajaxPostData({
		                    url: "addOrUpdateBudgCashFlowBegin.do?isCheck=false",
		                    data: {ParamVo : ParamVo.toString(),changedData:JSON.stringify(changedData)},
		                    success: function(responseData) {
		                       if (responseData.state == "true") {
		                    	   query();
		 	       	               changeCount();
		                       }
		                    }
		                });
	                }
	            }
	        }
	    });
    }
  
    function validateGrid(data) {  
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		
 		$.each(data,function(i, v){
 			rowm = "";
 			if(!v.cash_item_name && !v.amount && !v.remark){
 				return true;
 			}else{
 				if (v.cash_item_name == "" || v.cash_item_name == null || v.cash_item_name == 'undefined') {
 					rowm+="[现金流量项目]、";
 				}  
 				if (v.amount == "" || v.amount == null || v.amount == 'undefined') {
 					rowm+="[金额]、";
 				}  
 				if(rowm != ""){
 					rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
 				}
 				msg += rowm;
 				var key=(v.cash_item_id ? v.cash_item_id : v.cash_item_name)
 				var value="第"+(i+1)+"行";
 				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
 					targetMap.put(key ,value);
 				}else{
 					msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
 				}
 			}
 		});
 		if(msg != ""){
 			$.etDialog.warn(msg);  
			return false;  
 		}else{
 			return true;  
 		} 	
 	}
    
   	//表格数据增删改时 重新计算
    function changeCount(){
    	if(state == '1'){
	     	$.etDialog.warn('当前期初货币资金为记账状态,无法执行该操作!如有必要,请反记账后执行!');
	     	return;
		 }
		 var data = grid.getAllData();
		  
		 if (data != null && data.length == 0){
	       	$.etDialog.error('没有需要计算的数据');
	     }else{
	         var Param; //方法 参数  携带计算值传入后台 保存到数据库
	         var cash_begin_year =Number($("#cash_begin_year").val().replace(/,/g, '')); //年初货币资金
	         var cash_in = 0;//累计流入资金
	         var cash_out = 0;//累计流出资金  
	         var cash_add = 0;//现金净增加额 = 累计流入资金 - 累计流出资金
	         var cash_begin = 0; // 期初现金存量  = 年初货币资金  + 现金净增加额
	         
	         if(!cash_begin_year){
	        	 cash_begin_year = 0; 
	         }
	         $(data).each(function (){
	        	 if(!this.cash_item_name && !this.amount && !this.remark){
	  				return true;
	  			 }else{
	  				if(this.cash_dire == '0'){
	  	         		cash_in += this.amount;
	  	         		
	  	         	}else{
	  	         		cash_out += this.amount
	  	         	}
	  			 }
	         });
	     	 //计算 现金净增加额
	     	 cash_add = cash_in - cash_out;
	     	 
	     	 //判断年初货币资金数值是否大于等于0
	     	 if(cash_begin_year < 0){
	     		 $.etDialog.warn('年初货币资金不得为负数!'); 
	        	 return;
	     	 }
	     	 //计算 期初现金存量 
	     	 cash_begin = cash_begin_year + cash_add;
	     	 //封装参数传入后台
	     	 Param = {
	     		cash_begin_year	: cash_begin_year,
	     		cash_in : cash_in,
	     		cash_out : cash_out,
	     		cash_add : cash_add,
	     		cash_begin : cash_begin,
	     		state : $("#state").attr("state_name")
	     	 }
	     	 if(cash_begin < 0){
	     		 $.etDialog.warn('期初货币资金小于零,请检查数据后执行操作!'); 
	        	 return;
	     	 }
	     	 //前台展示数据
	     	 $("#cash_in").val(formatNumber(cash_in,2,1));
	     	 $("#cash_out").val(formatNumber(cash_out,2,1));
	     	 $("#cash_add").val(formatNumber(cash_add,2,1));
	     	 $("#cash_begin").val(formatNumber(cash_begin,2,1));
	     	 return Param;
	     }
    }
 
	 function count(){
		 if(state == '1'){
	     	$.etDialog.warn('当前期初货币资金为记账状态,无法执行该操作!如有必要,请反记账后执行!');
	     	return;
		 }
		 var data = grid.getAllData();
		 if (data != null && data.length == 0){
	       	$.etDialog.error('没有需要计算的数据');
	     }else{
	         var Param; //方法 参数  携带计算值传入后台 保存到数据库
	         var cash_begin_year =Number($("#cash_begin_year").val().replace(/,/g, '')); //年初货币资金
	         var cash_in = 0;//累计流入资金
	         var cash_out = 0;//累计流出资金  
	         var cash_add = 0;//现金净增加额 = 累计流入资金 - 累计流出资金
	         var cash_begin = 0; // 期初现金存量  = 年初货币资金  + 现金净增加额
	         
	         if(!cash_begin_year){
	        	 $.etDialog.warn('年初货币资金为空,无法执行该操作!'); 
	        	 return;
	         }
	         $(data).each(function (){
	        	 if(!this.cash_item_name && !this.amount && !this.remark){
	  				return true;
	  			 }else{
	  				if(this.cash_dire== '0'){
	  	         		cash_in += this.amount;
	  	         		
	  	         	}else{
	  	         		cash_out += this.amount
	  	         	}
	  			 }
	         });
	     	 //计算 现金净增加额
	     	 cash_add = cash_in - cash_out;
	     	 
	     	 //判断年初货币资金数值是否大于等于0
	     	 if(cash_begin_year < 0){
	     		 $.etDialog.warn('年初货币资金不得为负数!'); 
	        	 return;
	     	 }
	     	 //计算 期初现金存量 
	     	 cash_begin = cash_begin_year + cash_add;
	     	 //封装参数传入后台
	     	 Param = {
	     		cash_begin_year	: cash_begin_year,
	     		cash_in : cash_in,
	     		cash_out : cash_out,
	     		cash_add : cash_add,
	     		cash_begin : cash_begin,
	     		state : $("#state").attr("state_name")
	     	 }
	     	 //前台展示数据
	     	 $("#cash_in").val(formatNumber(cash_in,2,1));
	     	 $("#cash_out").val(formatNumber(cash_out,2,1));
	     	 $("#cash_add").val(formatNumber(cash_add,2,1));
	     	 $("#cash_begin").val(formatNumber(cash_begin,2,1));
	     	 if(cash_begin < 0){
	     		 $.etDialog.warn('期初货币资金小于零,请检查数据后执行操作!'); 
	        	 return;
	     	 }
	     	 ajaxPostData({
                url: "saveBudgCashBegin.do?isCheck=false",
                data: Param,
                success: function(responseData) {
                }
             });
	     }
	 }
	    
	 function remove(){
		
		 if(state == '1'){
	     	$.etDialog.warn('当前期初货币资金为记账状态,无法执行该操作!如有必要,请反记账后执行!');
	     	return;
		 }	
		 var data = grid.selectGet();
		 
       	 if (data.length == 0){
        	$.etDialog.error('请选择行');
         }else{
           var ParamVo =[];
           var delData = [] ;//接受页面端删除数据
           var changedData = changeCount();
           $(data).each(function (){
             	if(this.rowData.group_id){
             		ParamVo.push(
						this.rowData.group_id   +"@"+ 
						this.rowData.hos_id   +"@"+ 
						this.rowData.copy_code   +"@"+ 
						this.rowData.cash_item_id +"@"+ 
						this.rowData.cash_dire +"@"+ 
						this.rowData.amount 
					) 
	            }else{
	              	delData.push(data)
	            }
            });
            if (ParamVo.length > 0) {
                $.etDialog.confirm('确定删除?', function() {
                    ajaxPostData({
                        url: "deleteBudgCashFlowBegin.do?isCheck=false",
                        data: { ParamVo: ParamVo.toString(),changedData:JSON.stringify(changedData) },
                        success: function(responseData) {
                        	isCount = true;
               				query();
                        }
                    });
                });
            } else if (delData.length > 0) {
            	grid.deleteRow(delData);
   				$.etDialog.success("删除成功.");
            }
	    }
	}
    
 	//记账
 	function charge(){
 		$.etDialog.confirm('记账后将无法再对数据进行更改,确定记账?', function() {
            ajaxPostData({
                url: "chargeBudgCashBeginState.do?isCheck=false",
                data: "",
                success: function(responseData) {
                	$("#state").attr("state_name", "1")
         		    $("#state").val("记账");
            		state = '1';
                }
            });
        });
 	}
 	
 	//反记账
 	function unCharge(){
   		$.etDialog.confirm('反记账后将可以对数据进行更改,确定反记账?', function() {
            ajaxPostData({
                url: "unChargeBudgCashBeginState.do?isCheck=false",
                data: {},
                success: function(responseData) {
                    $("#state").attr("state_name", "0")
         			$("#state").val("未记账");
            		state = '0';
                }
            });
        });
 	}
 
 	//导入
	 function impNew(){
		parent.$.ligerDialog.open({ url : 'hrp/budg/budgcash/budgcashbegin/importBudgCashFlowBeginPage.do?isCheck=false',
			data:{columns : grid.columns, grid : grid}, height: 300,width: 450,title:'期初现金流量累计导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
   		}); 
	}	
    
    function loadDict(){
        //字典下拉框
        $("#start_date").ligerTextBox({width:160,disabled:true,value:${start_date}});
        $("#cash_begin_year").ligerTextBox({width:160,value:formatNumber("${cash_begin_year}",2,1)});
        $("#cash_in").ligerTextBox({width:160,disabled:true,value:formatNumber("${cash_in}",2,1)});
        $("#cash_out").ligerTextBox({width:160,disabled:true,value:formatNumber("${cash_out}",2,1)});
        $("#cash_add").ligerTextBox({width:160,disabled:true,value:formatNumber("${cash_add}",2,1)});
        $("#cash_begin").ligerTextBox({width:160,disabled:true,value:formatNumber("${cash_begin}",2,1)});
        $("#state").ligerTextBox({width:100,disabled:true});
    }  
    //键盘事件
  	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('I', impNew);
	}

    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金预算启用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="start_date" type="text" id="start_date"  disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="state" type="text" id="state" ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>    
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年初货币资金：</td>
            <td align="left" class="l-table-edit-td"><input name="cash_begin_year" type="text" id="cash_begin_year"  value="${cash_begin_year}" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计流入：</td>
            <td align="left" class="l-table-edit-td"><input name="cash_in" type="text" id="cash_in"  value="${cash_in}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>        
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计流出：</td>
            <td align="left" class="l-table-edit-td"><input name="cash_out" type="text" id="cash_out" value="${cash_out}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>        
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">现金净增加额：</td>
            <td align="left" class="l-table-edit-td"><input name="cash_add" type="text" id="cash_add" value="${cash_add}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期初现金存量：</td>
            <td align="left" class="l-table-edit-td"><input name="cash_begin" type="text" id="cash_begin" value="${cash_begin}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
