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
    
    var gridManager = null;
    
    var userUpdateStr;
    
	var query_obj_code;
    
    var obj_box_data = "";
    
	var query_subj_code;
    
    var subj_box_data = "";
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	if(liger.get("acc_year_month1").getValue() == '' || liger.get("acc_year_month1").getValue() == undefined){
        	$.ligerDialog.error('起始年月为必填项');
        	return ;
        }
        	
        if(liger.get("acc_year_month2").getValue() == '' || liger.get("acc_year_month2").getValue() == undefined){
        	$.ligerDialog.error('截止年月为必填项');
        	return ;
        }     	
        if(liger.get("rela_code").getValue() == '' || liger.get("rela_code").getValue() == undefined){
        	$.ligerDialog.error('账套为必填项');
        	return ;
        }     	
        if(liger.get("cus_code").getValue() == '' || liger.get("cus_code").getValue() == undefined){
        	$.ligerDialog.error('客户为必填项');
        	return ;
        }   	
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acc_year_b',value:liger.get("acc_year_month1").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'acc_month_b',value:liger.get("acc_year_month1").getValue().split(".")[1]}); 
    	grid.options.parms.push({name:'acc_year_e',value:liger.get("acc_year_month2").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'acc_month_e',value:liger.get("acc_year_month2").getValue().split(".")[1]}); 
    	grid.options.parms.push({name:'cur_code',value:liger.get("cur_code").getValue()}); 
    	grid.options.parms.push({name:'rela_code',value:liger.get("rela_code").getValue()}); 
    	grid.options.parms.push({name:'obj_code',value:liger.get("cus_code").getValue()}); 
    	
    	var subj_code = liger.get("subj_code").getValue();
    	
    	var is_state = 99;
    	
    	if($("#is_state").get(0).checked == true){
    		is_state = 1;
    	}
    	grid.options.parms.push({name:'state',value:is_state});
    	
    	if(subj_code.split(",").length>1){
    		if($("#subj_flag").val() == "true"){
    			grid.options.parms.push({name:'subj_code',value:subj_code}); 
            	grid.options.parms.push({name:'subj_select_flag',value:"3"});
    		}else{
    			grid.options.parms.push({name:'subj_code',value:subj_code}); 
            	grid.options.parms.push({name:'subj_select_flag',value:"4"});
   			}
        }else{
        	if(subj_code==""){
    			grid.options.parms.push({name:'subj_code',value:""}); 
       	 		grid.options.parms.push({name:'subj_select_flag',value:"1"});
    		}else{
    			var code = subj_code.split(".").length > 1 ? subj_code.split(".")[1] : subj_code;
				grid.options.parms.push({name:'subj_code',value:code});      	
    	 		grid.options.parms.push({name:'subj_select_flag',value:"2"});
    		}
       }
    	
		/* var cus_code = liger.get("cus_code").getValue();
		if(cus_code.split(",").length>1){
			 if($("#flag").val() == "true"){
				 grid.options.parms.push({name:'obj_code',value:cus_code}); 
		         grid.options.parms.push({name:'obj_select_flag',value:"3"});
			 }else{
        		grid.options.parms.push({name:'obj_code',value:cus_code}); 
        		grid.options.parms.push({name:'obj_select_flag',value:"4"});
			 }
        }else{
        	if(cus_code==""){
        		 grid.options.parms.push({name:'obj_code',value:""}); 
            	 grid.options.parms.push({name:'obj_select_flag',value:"1"});
       		}else{
        	 	grid.options.parms.push({name:'obj_code',value:cus_code.split(".")[2]}); 
        	 	grid.options.parms.push({name:'obj_select_flag',value:"2"});
    		}
       } */
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     }
  

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '期间', align: 'left',columns:[
             							{ display: '年', isSort:false, name: 'acc_year', align: 'left', width: '4%'},
            					        { display: '月', isSort:false, name: 'acc_month', align: 'left', width: '4%'}
            						]
					 },
					 { display: '凭证号', isSort:false, name: 'vouch_no', align: 'left'
					 },
					 { display: '科目', isSort:false, name: 'subj_name', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								return rowdata.subj_name;
							}
					 },
					 { display: '摘要', isSort:false, name: 'summary', align: 'left'
					 },
					 { display: '借方', isSort:false, name: 'debit', align: 'right',width: '10%',formatter:'###,##0.00',
                    	 render : function(rowdata, rowindex, value) {
           					return formatNumber(rowdata.debit, 2, 1);
           				}
					 },
					 { display: '贷方', isSort:false, name: 'credit', align: 'right',width: '10%',formatter:'###,##0.00',
                    	 render : function(rowdata, rowindex, value) {
           					return formatNumber(rowdata.credit, 2, 1);
           				}
					 },
                     { display: '方向', isSort:false, name: 'subj_dire', align: 'left', width: '4%'
					 },
                     { display: '余额', isSort:false, name: 'end_os', align: 'right',width: '10%',formatter:'###,##0.00',reg:'0.00=Q,0=Q',
                    	 render : function(rowdata, rowindex, value) {
                    		 if(rowdata.end_os==0)
                    			 return "Q";
                    		 else
           					    return formatNumber(rowdata.end_os, 2, 1);
           				}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryGroupAccCusSubjGeneralLedger.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     delayLoad:true,
                     groupColumnName:'subj_name',groupColumnDisplay:'科目',selectRowButtonOnly:true,
                     toolbar: { items: [
                     		{ text: '查询',id:'search',icon:'search', click: query },
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
    
    function loadDict(){
    	 
		autocomplete("#acc_year_month1","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');
    	
    	autocomplete("#acc_year_month2","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');
    	 
    	 var year_Month = '${yearMonth}';

    	 liger.get("acc_year_month1").setValue(year_Month);
		 
    	 liger.get("acc_year_month1").setText(year_Month);
    	 
    	 liger.get("acc_year_month2").setValue(year_Month);
		 
    	 liger.get("acc_year_month2").setText(year_Month);
    	 
    	 autocompleteObj({
   			id: '#rela_code',                   
   			urlStr:	"../../../sys/queryHosCopyDict.do?isCheck=false",							
   			valueField: 'id',            
   			textField:   'text' ,            
   			autocomplete:true,			
   			highLight:true,
   			initWidth: '330', 
   			defaultSelect:true,
   			keySupport: true,
   			selectEvent:function(value){
   				//console.log(arguments)
			var	para = {
	        			rela_code : value,
	        			sign : "5",
	        		}; 
			autocomplete("#subj_code","../../querySubjByAccountRela.do?isCheck=false","id","text",true,true,para,true,false,'250');
	 		autocomplete("#cus_code","../../../sys/queryCusDictByRela.do?isCheck=false","id","text",true,true,para,true,false,'180');
   			 
   			
   		    }
   		}); 
    	 
    	/*  $("#cus_code").ligerComboBox({
		      	url: '../../../sys/queryCusDict.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 180,
		      	autocomplete: true,
		      	width: 180,
    	      	keySupport:true
			 });
    	  */
    	 $("#cur_code").ligerComboBox({
		      	url: '../../queryCur.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 180,
		      	autocomplete: true,
		      	width: 180,
	           	onSuccess:function(data){
    				if(data.length >0 ){
    					if(data[0].id != undefined && data[0].id != "" && data[0].id != null){
               				liger.get("cur_code").setValue(data[0].id);
               				liger.get("cur_code").setText(data[0].text);
    					}
    				}
           	}
			 });
    	 
         }   
      
function subjSelector(){
	
	$.ligerDialog.open({ url : '../../groupbookselector/groupAccBookSubjSelectorPage.do?isCheck=false&flag='+$("#subj_flag").val()+'&sign=5'+'&rela_code='+liger.get("rela_code").getValue(),
			data:{listBoxData:subj_box_data}, height:$(window).height()-40,width: 600, title:'账簿科目选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
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
 
	//普通打印数据
	function print(){
		   
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
	
		var heads={
		  		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  		  "rows": [
			          {"cell":0,"value":"会计期间："+$("#acc_year_month1").val()+"至"+$("#acc_year_month2").val(),"colSpan":"5"} 
		  		  ]
		  	}; 
			 
		 		var printPara={
		 			title: "客户科目总账",//标题
		 			columns: JSON.stringify(grid.getPrintColumns()),//表头 //数据格式化
		 			class_name: "com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccCusAuxiliaryAccountService",
					method_name: "collectGroupAccCusSubjGeneralLedgerPrint",
					bean_name: "groupAccCusAuxiliaryAccountService",
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
	function printDate(){
		   
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
	
	  	var selPara={};
		$.each(grid.options.parms,function(i,obj){
			selPara[obj.name]=obj.value;
		});
	
		selPara["template_code"]="01028";
	   	selPara["class_name"]="com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccCusAuxiliaryAccountService";
	   	selPara["method_name"]="collectGroupAccCusSubjGeneralLedgerPrintDate";
	   	selPara["bean_name"]="groupAccCusAuxiliaryAccountService";
	   	if(liger.get("acc_year_month1").getValue()!=""){
	   		selPara["p_acc_year"]=liger.get("acc_year_month1").getValue().split(".")[0];	
	   	}
	   	selPara["p_year_month_b"]=liger.get("acc_year_month1").getValue();
	   	selPara["p_year_month_e"]=liger.get("acc_year_month2").getValue();
	   	selPara["obj_code"]=liger.get("Cus_code").getValue();
	   	selPara["p_subj_code"]=liger.get("subj_code").getText();
	   	selPara["p_cur_code"]=liger.get("cur_code").getText();
	   	var isAccStr="不含未记账";
	   	if($("#is_state").is(":checked")){
	   		isAccStr="包含未记账";
		}
	   	selPara["p_is_acc"]=isAccStr;
	   	
		selPara["source_id"]="0";
	   	
	   	selPara["table_flag"]="5";
	   	
	   	officeTablePrint(selPara);
	
	}
	
	//打印维护
	function myPrintSet(){
		 officeFormTemplate({template_code:"01028"});
	}

    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
<input type="hidden" id="flag" name="flag"/>
<input type="hidden" id="query_code" name="query_code"/>
<input type="hidden" id="subj_flag" name="subj_flag"/>
<input type="hidden" id="subj_query_code" name="subj_query_code"/>
	<div id="toptoolbar" ></div>
	<form name="form1" method="post"  id="form1" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit"  border="0">
	
        <tr>
            <td align="left"><font size="2" color="red">*</font>会计期间：</td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input id="acc_year_month1" name="acc_year_month1" /></td>
            <td align="center" >-</td>
            <td align="right" class="l-table-edit-td"  ><input id="acc_year_month2" name="acc_year_month2" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>账套：</td>
            <td align="left" class="l-table-edit-td" colspan="2" ><input name="rela_code" type="text" id="rela_code" ltype="text" /></td>
            <!-- <td align="left"><input class="l-button l-button-test"  type="button" value="选择" onclick="subjSelector();"/></td> -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" ><font size="2" color="red">*</font>客户：</td>
            <td align="left" class="l-table-edit-td" ><input id="cus_code" name="cus_code" /></td>
            
            <td align="left"></td>
        </tr> 
        <tr>
        	 <td align="right" class="l-table-edit-td"  >币种：</td>
            <td align="left" class="l-table-edit-td" style="padding-left:20px;" colspan="3"><input id="cur_code" name="cur_code"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目：</td>
            <td align="left" class="l-table-edit-td" >
            	<input id="subj_code" name="subj_code"  />
            </td>
            <td>
            	<input class="l-button l-button-test" style="float: right;" type="button" value="选择" onclick="subjSelector();"/>
            </td>
            <td align="right" class="l-table-edit-td"  ><input name="is_state" type="checkbox" id="is_state" checked="checked" /></td>
            <td align="left" class="l-table-edit-td">包含未记账</td>
        </tr>
	 
    </table>
	</form>
	<div id="maingrid"></div>
</body>
</html>
