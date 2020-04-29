<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
	<script>
    var grid;
    var is_state_manager;
    var gridManager = null;
    var userUpdateStr;
    var subj_box_data = "";
    var check_tree="";
    var vouch_no=""; 
    var vouch_num="";
    $(function ()
    {
		loadDict();
    	loadHead(null);	//加载数据
    	loadHotkeys();
    	
    	
    });
    //查询
    function  query(){//根据表字段进行添加查询条件
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	var year_month1= liger.get("create_date_b").getValue();
        var year_month2= liger.get("create_date_e").getValue();
        if(year_month1==""||year_month2 ==""){
       		$.ligerDialog.error('会计期间为必填项，不能为空！');
        	return;
        }
        if(liger.get("rela_code").getValue()=="" ){
       		$.ligerDialog.error('账套为必填项，不能为空！');
        	return;
        }
        grid.options.parms.push({name:'create_date_b',value : liger.get("create_date_b").getValue()}); 
        grid.options.parms.push({name:'create_date_e',value : liger.get("create_date_e").getValue()}); 
        grid.options.parms.push({name:'rela_code',value:liger.get("rela_code").getValue()}); 
        var subj_codes ;
   		var subj_select_flag;
    	var subj_code = liger.get("subj_code").getValue();
    	 
    	if(subj_code.split(",").length>1){
        	if($("#subj_flag").val() == "true"){
        		subj_codes = subj_code; 
        		subj_select_flag = "3";
        	}else{
        		subj_codes = subj_code;
        		subj_select_flag = "4";
        	}
        }else{
        	if(subj_code==""){
        		subj_codes = "";
        		subj_select_flag = "1";
         	}else{
         		if(subj_code.indexOf(".")!=-1){
         			subj_codes = subj_code.split(".")[1] ;
            		subj_select_flag = "2";
         		}else{ 
         			subj_codes = subj_code ;
            		subj_select_flag = "2";
         		}
         		 
         	}
        }
    	grid.options.parms.push({name:'subj_codes',value : subj_codes});
    	grid.options.parms.push({name:'subj_select_flag',value : subj_select_flag});
        grid.options.parms.push({name:'cur_code',value:liger.get("cur_code").getValue()}); 
        grid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue()}); 
	    var is_state = 99;
	    var is_state_alue = is_state_manager.getValue();
	    if(is_state_alue){
	    	is_state = 1;
	    }
	    grid.options.parms.push({name:'is_state',value:is_state});
	    grid.loadData(grid.where);//加载查询条件
     }

     function loadHead(){
    	 
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科目编码', name:'subj_code', align: 'left',width:'200',
						 render : function(rowdata, rowindex, value) {
								 return rowdata.subj_code;
							}
                     }, 
					 { display: '科目名称', name: 'subj_name', align: 'left',width:'200',
						 render : function(rowdata, rowindex, value) {
								return formatSpace(rowdata.subj_name,rowdata.subj_level - 1);
						 }
					 },
					 { display: '本期借方', name: 'debit', align: 'left',width:'200',formatter:'###,##0.00',
						 render : function(rowdata, rowindex, value) {
							 if(typeof(value) == 'undefined'){
								 return "";
							 }else{ 
								 return formatNumber(rowdata.debit, 2, 1);}
						 }
					 },
					 { display: '本期贷方', name: 'credit', align: 'left',width:'200',formatter:'###,##0.00',
						 render : function(rowdata, rowindex, value) {
							 if(typeof(value) == 'undefined'){
								 return "";
							 }else{ 
								 return formatNumber(rowdata.credit, 2, 1);}
						 }
					 }
				],
				dataAction: 'server',dataType: 'server',usePager:false,url:'collectGroupAccSubjLedgerSumMain.do',
				width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,selectRowButtonOnly:true,heightDiff: 25,
				toolbar: { items: [
									{ text: '查询',id:'search', click: queryBtn ,icon:'search'},
									{ line:true },
									{ text: '打印', id:'print', click: printDate,icon:'print' },
									{ line:true },
									{ text: '模板打印', id:'print', click: printBtn,icon:'print' },
									{ line:true },
									{ text: '模板设置', id:'settings', click: myPrintSet,icon:'settings' },
									{ line:true } 
		    				]}
		    	 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   //普通打印 
 	function printDate(){//打印
       	
 		if(grid.getData().length==0){
 			$.ligerDialog.error("请先查询数据！");
 			return;
 		}
     
 		var heads={
 		  		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
 		  		  "rows": [
 			          {"cell":0,"value":"会计期间："+$("#acc_year_month1").val()+"至"+$("#acc_year_month2").val(),"colSpan":"13","align":"center"} ,
 			         // {"cell":0,"value":"科目："+liger.get("acc_subj_code").getText(),"br":"true","colSpan":"7"} ,
 			         {"cell":0,"value":"账套："+liger.get("rela_code").getText(),"br":"true","colSpan":"5"} ,
 			         {"cell":9,"value":"币种："+liger.get("cur_code").getText(),"colSpan":"4"} 
 			          
 		  		  ] 
 		  	}; 
 	   		var printPara={
 	   			title: "科目余额表",//标题
 	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
 	   			class_name: "com.chd.hrp.acc.service.books.subjaccount.GroupAccSubjLedgerService",
 				method_name: "collectGroupBalanceLedgerDetailPrintDate",
 				bean_name: "groupAccSubjLedgerService",
 				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
 	   		};
 	    	
 	   		//执行方法的查询条件
 	   		$.each(grid.options.parms,function(i,obj){
 	   			printPara[obj.name]=obj.value;
 	    	});
 	   		
 	    	officeGridPrint(printPara);
     }
   
 	//打印维护
	 function myPrintSet(){
		 officeFormTemplate({template_code:"01038"});
	 }
	 function printBtn(){//打印
	      	
			if(grid.getData().length==0){
				$.ligerDialog.error("请先查询数据！");
				return;
			}
	    
	      	var selPara={};
	    	$.each(grid.options.parms,function(i,obj){
	    		selPara[obj.name]=obj.value;
	    	});
	 
	    	selPara["template_code"]="01038";
	       	selPara["class_name"]="com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService";
	       	selPara["method_name"]="collectAccSubjLedgerSumMainPrint";
	       	selPara["bean_name"]="accSubjLedgerService";
	       	selPara["p_year_month_b"]=$("#create_date_b").val();
	       	selPara["p_year_month_e"]=$("#create_date_e").val();
	       	selPara["p_subj_code"]=liger.get("subj_code").getText();
	       	selPara["p_subj_level"]=liger.get("subj_level").getValue();
	       	selPara["p_cur_code"]=liger.get("cur_code").getText();
	       	selPara["p_vouch_no"]= $("#vouch_no").val();
	       	selPara["p_vouch_num"]= $("#vouch_num").val();
	       	var isAccStr="不含未记账";
	       	if($("#is_state").is(":checked")){
	       		isAccStr="包含未记账";
	    	}
	       	selPara["p_is_acc"]=isAccStr;
	       	
	       	officeTablePrint(selPara);
	    }
 	
	function loadHotkeys(){//键盘事件
		hotkeys('Q',queryBtn);
		hotkeys('P',printBtn);
	}
  
    function queryBtn(){//查询
    	
    	var year_month1= liger.get("create_date_b").getValue();
        var year_month2= liger.get("create_date_e").getValue();
        if(year_month1==""||year_month2 ==""){
       		$.ligerDialog.error('会计期间为必填项，不能为空！');
        	return;
        }
        if(liger.get("rela_code").getValue()=="" ){
       		$.ligerDialog.error('账套为必填项，不能为空！');
        	return;
        }
        
    	var subj_codes ;
   		var subj_select_flag;
    	var subj_code = liger.get("subj_code").getValue();
    	if(subj_code.split(",").length>1){
        	if($("#subj_flag").val() == "true"){
        		subj_codes = subj_code;
        		subj_select_flag = "3";
        	}else{
        		subj_codes = subj_code;
        		subj_select_flag = "4"; 
        	}
        }else{
        	if(subj_code==""){
        		subj_codes = "";
        		subj_select_flag = "1";
         	}else{  
         		if(subj_code.indexOf(".")!=-1){
         			subj_codes = subj_code.split(".")[1] ;
            		subj_select_flag = "2";
         		}else{ 
         			subj_codes = subj_code ;
            		subj_select_flag = "2";
         		}
         		 
         	}
        }
    	var formPara={
        	acc_year : liger.get("create_date_b").getValue().split("-")[0],
        	acc_month_b : liger.get("create_date_b").getValue().split("-")[1],
        	acc_month_e : liger.get("create_date_e").getValue().split("-")[1],
        	is_state : $("#is_state").is(":checked") ? 1 : 99,
        	subj_codes : subj_codes,
        	subj_select_flag :subj_select_flag,
        	rela_code :liger.get("rela_code").getValue()
        	
        } 
    	console.log(formPara)
        ajaxJsonObjectByUrl("queryGroupSubjLedgerSum.do?isCheck=false",formPara,function(responseData){
        	vouch_no = responseData.vouch_no;
        	vouch_num = responseData.vouch_num;
            $("#vouch_no").val(responseData.vouch_no);
            $("#vouch_num").val(responseData.vouch_num);
        });
    	
    	query();
    } 
    
	function loadDict(){//字典下拉框

    	autocomplete("#cur_code","../../queryCur.do?isCheck=false","id","text",true,true,'',true,false,'235');
    	autocomplete("#subj_level","../../querySubjLevel.do?isCheck=false","id","text",true,true,'',false,false,'215');
    	//-----------------会计期间设置----------------- 
    	//设置凭证起止日期
		var acc_month=getMonthDate('${acc_year}','${acc_month}');
    	
		$("#create_date_b").val(acc_month.split(";")[0]);
		$("#create_date_e").val(acc_month.split(";")[1]);
	
		$("#create_date_b").ligerTextBox({width:100});
		$("#create_date_e").ligerTextBox({width:100});
   	 	//------------------会计期间设置-----------------
		is_state_manager = $("#is_state").ligerCheckBox();
		
		$("#vouch_no").ligerTextBox({width:215,disabled: true});
		$("#vouch_num").ligerTextBox({width:300,disabled: true});
		autocompleteObj({
			id: '#rela_code',                   
			urlStr:	"../../../sys/queryHosCopyDict.do?isCheck=false",							
			valueField: 'id',            
			textField:   'text' ,            
			autocomplete:true,			
			highLight:true,
			initWidth: '300', 
			defaultSelect:true,
			keySupport: true,
			selectEvent:function(value){
			var	para = {
         			rela_code : value
         		}; 
		 		autocomplete("#subj_code","../../querySubjByHosCopyRela.do?isCheck=false","id","text",true,true,para,true,false,'235');
		    }
		}); 
	}
    
	function subjSelector(){//科目选择器 
    	$.ligerDialog.open({ url : '../../groupbookselector/groupAccBookSubjSelectorPage.do?isCheck=false&flag='+$("#subj_flag").val()+'&sign=0'+'&check_nodes='+check_tree +'&rela_code='+liger.get("rela_code").getValue(),
    			data:{listBoxData :subj_box_data}, height:$(window).height()-40,width: 600, title:'账簿科目选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			buttons: [{ text: '确定', onclick: 
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
										subj_box_data = "";

										$.each(boxData, function(i, v) {

											if (boxData.length == (i + 1)) {
												subj_box_data = subj_box_data + "{'id':'" + v.id + "','text':'" + v.text + "'}";

												param = param + v.text;

												subj_param = subj_param + v.id.split(".")[1].toString();

											} else {
												subj_box_data = subj_box_data + "{'id':'" + v.id + "','text':'" + v.text + "'},";

												param = param + v.text + ",";

												subj_param = subj_param + v.id.split(".")[1].toString() + ",";
											}

										});

									}
    				        	   query_subj_code = param; 
     				        	   liger.get("subj_code").setValue(subj_param); 
     				        	   liger.get("subj_code").setText(param);
     				        	  
     				        	   dialog.close(); 
    				           },cls:'l-dialog-btn-highlight'}, 
    				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
    				      ] });
    	}
    </script>

</head>
<body style="padding: 0px; overflow: hidden;">

<input type="hidden" id="subj_flag" name="subj_flag"/> 
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>会计期间：</td>
            <td align="left" class="l-table-edit-td">
            	<table>
					<tr>
						<td>
							<input class="Wdate" name="create_date_b" type="text" id="create_date_b" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" style="width: 90px;" /></td>
						</td>
						<td align="right" class="l-table-edit-td"  >至：</td>
						<td>
							<input class="Wdate" name="create_date_e" type="text" id="create_date_e" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" style="width: 90px;" />
						</td>
            		</tr>
				</table>
            </td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">级次：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_level" type="text" id="subj_level" ltype="text"  /></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>账　　套：</td>
            <td align="left" class="l-table-edit-td"><input name="rela_code" type="text" id="rela_code" ltype="text" /></td>
            <!-- <td align="left"><input class="l-button l-button-test"  type="button" value="选择" onclick="subjSelector();"/></td> -->
            
		</tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科　　目：</td>
            <td align="left" class="l-table-edit-td"><input style="width:235px" name="subj_code" type="text" id="subj_code" ltype="text" /></td>
            <td align="left"><input class="l-button l-button-test"  type="button" value="选择" onclick="subjSelector();"/></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭证范围：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="vouch_no" type="text" id="vouch_no" ltype="text"  />
            	
            </td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">包括：</td>
            <td align="left" class="l-table-edit-td"  colspan="4">
            	<input name="vouch_num" type="text" id="vouch_num" ltype="text"  />
            </td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">币种：</td>
            <td align="left" class="l-table-edit-td" ><input name="cur_code" type="text" id="cur_code" ltype="text"  /></td>
            
            <td align="left" class="l-table-edit-td"  colspan="2">
            	<input name="is_state" type="checkbox" id="is_state" ltype="text" checked="checked" />包含未记账
            </td>
        </tr> 
    </table>
    
    <div id="maingrid"></div>

</body>
</html>
