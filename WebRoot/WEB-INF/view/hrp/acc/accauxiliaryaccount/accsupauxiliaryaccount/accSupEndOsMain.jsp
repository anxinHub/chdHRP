<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script>
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr; 
    
	var query_obj_code;
	var obj_param="";
    var obj_box_data = "";
    var is_show = 0;
    var is_state = 99;

	var query_subj_code;
    
    var subj_box_data = "";
    var year_month1=""; 
    var yearMonth= '${yearMonth}';
    $(function ()
    {
    	//loadForm();
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	//注册事件check选中显示栏目 否则隐藏栏目
    	$("#check_column1").change(function () { 
    		
			if(this.checked){
				
				grid.toggleCol('01', true);grid.toggleCol('01D', true); grid.toggleCol('01C', true);
				
			}else{
				
				grid.toggleCol('01', false);grid.toggleCol('01D', false); grid.toggleCol('01C', false);
				
			}
		});
    	
		$("#check_column2").change(function () { 
    		
			if(this.checked){
				
				grid.toggleCol('02', true);grid.toggleCol('02D', true); grid.toggleCol('02C', true);
				
			}else{
				
				grid.toggleCol('02', false);grid.toggleCol('02D', false); grid.toggleCol('02C', false);
				
			}
		});
		
		$("#check_column3").change(function () { 
    		
			if(this.checked){
				
				grid.toggleCol('03', true);grid.toggleCol('03D', true); grid.toggleCol('03C', true);
				
			}else{
				
				grid.toggleCol('03', false);grid.toggleCol('03D', false); grid.toggleCol('03C', false);
				
			}
		});
		
		$("#check_column4").change(function () { 
    		
			if(this.checked){
				
				grid.toggleCol('04', true);grid.toggleCol('04D', true); grid.toggleCol('04C', true);
				
			}else{
				
				grid.toggleCol('04', false);grid.toggleCol('04D', false); grid.toggleCol('04C', false);
				
			}
		});
		
		$("#check_column5").change(function () { 
    		
			if(this.checked){
				
				grid.toggleCol('05', true);grid.toggleCol('05D', true); grid.toggleCol('05C', true);
				
			}else{
				
				grid.toggleCol('05', false);grid.toggleCol('05D', false); grid.toggleCol('05C', false);
				
			}
		});
		/* $("#sup_code").bind("change",function(){
			if(liger.get("sup_code").getValue()){
		    	var para = {
		    		checkid : liger.get("sup_code").getValue().split(".")[0],
		    		checkno : liger.get("sup_code").getValue().split(".")[1],
		    		checkType : 6
		    	};
		    	liger.get("subj_code").setValue("");
		    	liger.get("subj_code").setText("");
		    	autocomplete("#subj_code", "../querySubjByProjCheck.do?isCheck=false", "id", "text", true, true, para, false,false,'215');
			}
		}); */
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
		year_month1  = acc_year_month1.getValue();
    	
    	if(year_month1==""){
    		$.ligerDialog.error('起始年月为必填项');
    		return ;
    	} 
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acc_year_b',value : year_month1[0].split(".")[0]}); 
    	grid.options.parms.push({name:'acc_month_b',value : year_month1[0].split(".")[1]}); 
    	grid.options.parms.push({name:'acc_year_e',value : year_month1[1].split(".")[0]}); 
    	grid.options.parms.push({name:'acc_month_e',value : year_month1[1].split(".")[1]});
    	var subj_code = liger.get("subj_code").getValue();
    	var sch_id = liger.get("sch_id").getValue();
    	grid.options.parms.push({name:'sch_id',value: sch_id}); 
       	grid.options.parms.push({name:'subj_code',value: subj_code}); 
		
       	var check_item1_b = liger.get("check_item_code1_b") == null ? "" :  liger.get("check_item_code1_b").getValue();
   	    var check_item1_e = liger.get("check_item_code1_b") == null ? "" :  liger.get("check_item_code1_e").getValue();
   	    var is_show = $("#is_show").prop("checked");
   	    grid.options.parms.push({name:'show_zero', value: is_show ? 0 : 1});
       	grid.options.parms.push({name:'check_item_type', value: '${check_type_id}'});
       	grid.options.parms.push({name:'check_item_code1_b', value: check_item1_b});
       	grid.options.parms.push({name:'check_item_code1_e', value: check_item1_e});
       
    	//加载查询条件
    	grid.loadData(grid.where);
     }
  

    function loadHead(){

        grid = $("#maingrid").ligerGrid({
            columns: [ 
 					 { display: '供应商', isSort:false, name: 'check1_name', align: 'left'
 						 /* ,render : function(rowdata, rowindex, value) {
 								return formatSpace(rowdata.obj_name,rowdata.subj_level - 1);
 							} */
 					 },
 					 
 					 { display: '年初余额', align: 'center', id:'01',
 						 columns:[
 						 	{display: '借方', isSort:false, name: 'nc_d', align: 'right',id:'01D',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.nc_debit, 2, 1);}}},
                      		{display: '贷方', isSort:false, name: 'nc_c', align: 'right',id:'01C',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.nc_credit, 2, 1);}}}
 						 ]
 					 },
 					 
 					 { display: '期初余额', align: 'center', id:'02',
 						 columns:[
 						 	{display: '借方', isSort:false, name: 'qc_d', align: 'right',id:'02D',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.qc_debit, 2, 1);}}},
                      		{display: '贷方', isSort:false, name: 'qc_c', align: 'right',id:'02C',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.qc_credit, 2, 1);}}}
 						 ]
 					 },
 					 
 					 { display: '本期发生', align: 'center', id:'03',
 						 columns:[
 						 	{display: '借方', isSort:false, name: 'bq_d', align: 'right',id:'03D',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.bq_debit, 2, 1);}}},
                     	 	{display: '贷方', isSort:false, name: 'bq_c', align: 'right',id:'03C',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.bq_credit, 2, 1);}}}
 						 ]
 					 },
 					 
 					 { display: '累计发生', align: 'center', id:'04',
 						 columns:[
 							{display: '借方', isSort:false, name: 'sum_d', align: 'right',id:'04D',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.lj_debit, 2, 1);}}},
 							{display: '贷方', isSort:false, name: 'sum_c', align: 'right',id:'04C',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.lj_credit, 2, 1);}}}
 						]
 					 },
 					 
 					 { display: '期末余额', align: 'center', id:'05',
 						 columns:[
 						 	{display: '借方', isSort:false, name: 'end_d', align: 'right',id:'05D',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.end_debit, 2, 1);}}},
 						 	{display: '贷方', isSort:false, name: 'end_c', align: 'right',id:'05C',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.end_credit, 2, 1);}}}
 						]
 					 }
 				],
 				dataAction: 'server',dataType: 'server',usePager:true,url:'querySupEndOs.do',
 				pageSize: 100, pageSizeOptions:[100, 200, 500],
 				width: '100%', height: '97%', checkbox: false,rownumbers:true,delayLoad:true,selectRowButtonOnly:true,heightDiff: 25,
 				toolbar: { items: [
 									{ text: '查询',id:'search', click: queryBtn ,icon:'search'},
 									{ line:true } , 
 									{ text: '打印', id:'print', click: print,icon:'print' },
 									{ line:true } ,
 									{ text: '模板打印', id:'print', click: printDate,icon:'print' },
 									{ line:true } ,
 									{ text: '模板设置', id:'settings', click: myPrintSet,icon:'settings' },
 									{ line:true }
 		    				]} 
 			});

         gridManager = $("#maingrid").ligerGetGridManager();
    }
    
 function queryBtn(){//查询
    	
    	query();
    	
    }
    
	//普通打印数据
	function print(){//打印
    	
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}

		var heads={
		  		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  		  "rows": [
			          {"cell":0,"value":"会计期间："+year_month1[0]+"至"+year_month1[1],"colSpan":"5"} 
		  		  ]
		  	}; 
			 
		 		var printPara={
		 			title: "供应商余额表",//标题
		 			columns: JSON.stringify(grid.getPrintColumns()),//表头 //数据格式化
		 			class_name: "com.chd.hrp.acc.service.books.auxiliaryaccount.AccSupAuxiliaryAccountService",
					method_name: "collectSupEndOsPrint",
					bean_name: "accSupAuxiliaryAccountService",
					heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空

					/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
		 		};
		 		//执行方法的查询条件
		 		$.each(grid.options.parms,function(i,obj){
		 			printPara[obj.name]=obj.value;
		  	});
		 		
		  	officeGridPrint(printPara);
    	
    }
	
	//模板打印数据
	function printDate(){//打印
    	
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}

	  	var selPara={};
		$.each(grid.options.parms,function(i,obj){
			selPara[obj.name]=obj.value;
		});
		
		selPara["template_code"]="01027";
	   	selPara["class_name"]="com.chd.hrp.acc.service.books.auxiliaryaccount.AccSupAuxiliaryAccountService";
	   	selPara["method_name"]="collectSupEndOsPrintDate";
	   	selPara["bean_name"]="accSupAuxiliaryAccountService";
	   	if(year_month1!=""){
       		selPara["p_acc_year"]=year_month1[0].split(".")[0];	
       	}
       	selPara["p_year_month_b"]=year_month1[0];
       	selPara["p_year_month_e"]=year_month1[1];
	   	selPara["obj_code"]=liger.get("sup_code").getValue();
	   	selPara["p_subj_code"]=liger.get("subj_code").getText();
	   	selPara["p_cur_code"]=liger.get("cur_code").getText();
	   	var isAccStr="不含未记账";
	   	if($("#is_state").is(":checked")){
	   		isAccStr="包含未记账";
		}
	   	selPara["p_is_acc"]=isAccStr;
	   	
		selPara["source_id"]="0";
       	
       	selPara["table_flag"]="6";
	   	
	   	officeTablePrint(selPara);

    }
	
	//打印维护
	 function myPrintSet(){
		 officeFormTemplate({template_code:"01027"});
	 }

    function loadDict(){
    	 
		
    	 
    	 var check_column1_manager = $("#check_column1").ligerCheckBox();
  		
  		var check_column2_manager = $("#check_column2").ligerCheckBox();
  		
  		var check_column3_manager = $("#check_column3").ligerCheckBox();
  		
  		var check_column4_manager = $("#check_column4").ligerCheckBox();
  		
  		var check_column5_manager = $("#check_column5").ligerCheckBox();
  		
  		check_column1_manager.setValue(true);
  		
  		check_column2_manager.setValue(true);
  		
  		check_column3_manager.setValue(true);
  		
  		check_column4_manager.setValue(true);
  		
  		check_column5_manager.setValue(true);
    	 
  		//会计期间
     	acc_year_month1 = $("#acc_year_month1").etDatepicker({
     		 range: true,
             view: "months",
             minView: "months",
             dateFormat: "yyyy.mm",
             defaultDate: ['${yearMonth}', '${yearMonth}'],
             onChange:function(fd, d, picker){
            	 var minDate,maxDate;
            	 if(!d){
            		 minDate = null;
            		 maxDate = null;
            	 }else{
            		 var selectYear = d[0].getFullYear();
            		 minDate = new Date(selectYear + '-1-1');
                     maxDate = new Date(selectYear + '-12-31');
            	 }
                 picker.update({
                	 minDate,
                	 maxDate
                 })
             },
   		});
     	autocomplete("#sch_id", "../queryAccBookSch.do?isCheck=false", "id", "text", true, true, '', true);
    	autocomplete("#check_item_code1_b","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true,{check_type_id:'${check_type_id}'});
       	autocomplete("#check_item_code1_e","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true,{check_type_id:'${check_type_id}'});

			 
    	 $("#subj_code").ligerComboBox({
 	      	url: '../querySubjByAccount.do?isCheck=false&sign=6',
 	      	valueField: 'id',
 	       	textField: 'text', 
 	       	selectBoxWidth: 320,
 	      	autocomplete: true,
 	      	width: 180
 		 });
    	 /* $("#emp_code").ligerComboBox({
		      	url: '../../sys/queryEmpDict.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 180,
		      	autocomplete: true,
		      	width: 180
			 }); */
    	 
         }   
    
    function loadForm(){
    	 $("form").ligerForm();
        
     }  
function supSelector(){
    	
    	$.ligerDialog.open({ url : '../bookselector/accBookSupSelectorPage.do?isCheck=false&listBoxData='+obj_box_data+'&flag='+$("#flag").val() ,data:{}, height: 470,width: 480, title:'账簿供应商选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ 
 				           { text: '确定', onclick: 
 				        	   function (item, dialog) { 
	 				        	   var boxData = dialog.frame.getListBox();
	 				        	   var param = "";
	 				        	   
	 				        	   //alert($("#flag").val());
	 				        	   if($("#flag").val() == "true"){
	 				        		  obj_box_data = "";
	 				        		  $.each(boxData,function(i,v){
	 				        			  if(boxData.length == (i+1)){
	 				        				 obj_box_data = obj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'}";
	 				        				 param = param + v.text;
	 				        				obj_param=obj_param+v.id.toString().split(".")[1];
	 				        				
	 				        			  }else{
	 				        				 obj_box_data = obj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'},";
	 				        				 param = param + v.text + ",";
	 				        				obj_param=obj_param+v.id.toString().split(".")[1]+ ",";
	 				        				
	 				        			  }
	 				        		  });
	 				        	   }else{
	 				        		  obj_box_data = "";
									  $.each(boxData,function(i,v){
										  if(boxData.length == (i+1)){
											obj_box_data = obj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'}";
		 				        			param = param + v.text;
		 				        			obj_param=obj_param+v.id.toString().split(".")[1];
		 				        		  }else{
		 				        			obj_box_data = obj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'},";
		 				        			param = param + v.text + ",";
		 				        			obj_param=obj_param+v.id.toString().split(".")[1]+ ",";
		 				        		  }
	 				        		  });
	 				        	   }
	 				        	  query_obj_code = param;
								  liger.get("sup_code").setValue(obj_param);
   				        	      liger.get("sup_code").setText(param);
	 				        	   /* $("#dept_code").val(param); */
	 				        	   dialog.close();
 				        	   
 				        	   },cls:'l-dialog-btn-highlight' 
 				        	   }, 
 				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
 				         ] });
    }
function subjSelector(){
	
	$.ligerDialog.open({ url : '../bookselector/accBookSubjSelectorPage.do?isCheck=false&flag='+$("#subj_flag").val()+'&sign=6' ,
			data:{listBoxData:subj_box_data}, height: $(window).height()-40,width: 600, title:'账簿科目选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ 
				           { text: '确定', onclick: 
				        	   function (item, dialog) { 
				        	   var boxData = dialog.frame.getListBox();
 				        	   var param = "";
 				        	   var subj_param="";
				        	   var subj_box_data = "";
				        	   
				        	   if($("#subj_flag").val() == "true"){
				        			
				        			 $.each(boxData,function(i,v){
									  
									 	 if(boxData.length == (i+1)){

									 	  subj_box_data = subj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'}";
										  
										  param = param + v.text;
										   
										  subj_param=subj_param+v.id.split(".")[1].toString();
	 				        		  
									  }else{ 
										  subj_box_data = subj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'},";
	 				        			
										  param = param + v.text+ ",";
	 				        			 
										  subj_param=subj_param+v.id.split(".")[1].toString()+ ",";
	 				        		  
									  }
				        		  
				        		  });
				        			 
				        		 }else {
  				        			$.each(boxData,function(i,v){
    									  
    									  if(boxData.length == (i+1)){ 
    										  subj_box_data = subj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'}";
    										  
    										  param = param + v.text;
    										   
    										  subj_param=subj_param+v.id.split(".")[1].toString();
    	 				        		  
    									  }else{ 
    										  subj_box_data = subj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'},";
	    	 				        			
    										  param = param + v.text+ ",";
    	 				        			  
    										  subj_param=subj_param+v.id.split(".")[1].toString()+ ",";
    									  }
     				        		  
     				        		  }); 
 				        		 }
 				        	   query_subj_code = param;
 				        	  liger.get("subj_code").setValue(subj_param);
 				              
				        	   liger.get("subj_code").setText(param);
 				        	   dialog.close(); 
				        	   
				        	   },cls:'l-dialog-btn-highlight' 
				        	   }, 
				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
				         ] });
}
//方案设置
function subjIntercalate(){
	 parent.$.ligerDialog.open({
			title : '方案设置',
			width : $(window).width()-500,
			height : $(window).height(),
			url : 'hrp/acc/accbooksch/accBookSchMainPage.do?isCheck=false',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			parentframename : window.name,
			buttons : [ {
				text : '保存',
				onclick : function(item, dialog) {
					dialog.frame.saveSch(0);
				},
				cls : 'l-dialog-btn-highlight'
			},{
				text : '查询',
				onclick : function(item, dialog) {
					dialog.frame.saveSch(1);
				},
				cls : 'l-dialog-btn-highlight'
			},{
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
};
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
<input type="hidden" id="flag" name="flag"/>
<input type="hidden" id="query_code" name="query_code"/>
<input type="hidden" id="source_id" name="source_id"/>
<input type="hidden" id="subj_flag" name="subj_flag"/>
<input type="hidden" id="subj_query_code" name="subj_query_code"/>
	<div id="toptoolbar" ></div>
	<form name="form1" method="post"  id="form1" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit"  border="0"  width="100%">  
        <tr>
            <td align="right"><font size="2" color="red">*</font>会计期间：</td>
            <td align="left" class="l-table-edit-td" >
            	<input id="acc_year_month1" name="acc_year_month1" style="width: 180px;"/>
            </td>
            
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">方&nbsp;&nbsp;案：</td> 
			<td align="left" class="l-table-edit-td" >
				<table>
					<tr>
						<td>
							<input name="sch_id" type="text" id="sch_id" ltype="text" /></td>
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="l-button l-button-test"  type="button" value="设置" onclick="subjIntercalate();"/>
						</td>
					</tr>
				</table>
			</td>
            
        	<td align="right" class="l-table-edit-td"   style="padding-left:20px;">科目：</td>
            <td align="left" class="l-table-edit-td" ><input id="subj_code" name="subj_code" /></td>
            
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"   >供应商：</td>
            <td align="left" class="l-table-edit-td"  colspan="3">
            	<table>
            		<tr>
            			<td>
            				<input id="check_item_code1_b" name="check_item_code1_b" />
            			</td>
            			<td>&nbsp;至：</td>
            			<td>
            				<input id="check_item_code1_e" name="check_item_code1_e" /> 
            			</td>
            			<td>
	           				&nbsp;&nbsp;<input name="is_show" type="checkbox" id="is_show"  /> 显示有余额的供应商
            			</td>
            		</tr>
            	</table>
            </td>
			
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">显示栏目：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
				<input name="check_column1" type="checkbox" id="check_column1" ltype="text"  />年初余额
            	<input name="check_column2" type="checkbox" id="check_column2" ltype="text"  />期初余额
            	<input name="check_column3" type="checkbox" id="check_column3" ltype="text"  />本期发生
            	<input name="check_column4" type="checkbox" id="check_column4" ltype="text"  />累计发生
            	<input name="check_column5" type="checkbox" id="check_column5" ltype="text"  />期末余额
            </td>
        </tr>
    </table>
	</form>
	<center><h2>供应商余额表</h2></center>
	<div id="maingrid"></div>
</body>
</html>
