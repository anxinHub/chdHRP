<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var grid;
    
    var is_state_manager;
    
    var gridManager = null;
    
    var userUpdateStr; 
    
    var subj_box_data = "";
    var is_show = 0;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	$("#cur_code").ligerTextBox({width:215});
    });
    //查询
    function  query(){  
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
        var year_month1= liger.get("acc_year_month1").getValue();
        var year_month2= liger.get("acc_year_month2").getValue();
        var subj_code = liger.get("subj_code").getValue();
        if(year_month1==""||year_month2 ==""){
        	$.ligerDialog.error('会计期间为必填项，不能为空！');
        	return;
        }
        
		if(subj_code==""){
        	$.ligerDialog.error('科目为必填项，不能为空！');
        	return;
        }
		if(liger.get("rela_code").getValue()==""){
        	$.ligerDialog.error('账套为必填项，不能为空！');
        	return;
        }
		 
	    acc_year_b:year_month1.split(".")[0]
        acc_month_b:year_month1.split(".")[1]
		acc_year_e:year_month2.split(".")[0]
        acc_month_e:year_month2.split(".")[1]
	//	subj_code:subj_code.split(".")[1]
        cur_code:liger.get("cur_code").getValue()   
    	grid.options.parms.push({name:'acc_year_b',value:year_month1.split(".")[0]}); 
        grid.options.parms.push({name:'acc_month_b',value:year_month1.split(".")[1]}); 
        grid.options.parms.push({name:'acc_year_e',value:year_month2.split(".")[0]}); 
        grid.options.parms.push({name:'acc_month_e',value:year_month2.split(".")[1]}); 
       // grid.options.parms.push({name:'subj_code',value:subj_code.split(".")[1]}); 
      //  grid.options.parms.push({name:'subj_code',value:subj_code}); 
        grid.options.parms.push({name:'cur_code',value:liger.get("cur_code").getValue()}); 
        grid.options.parms.push({name:'rela_code',value:liger.get("rela_code").getValue()}); 
		var is_state = 99;
    	var is_state_alue = is_state_manager.getValue();
    	if(is_state_alue){
    		is_state = 1;
    	}
    	grid.options.parms.push({name:'state',value:is_state});
    	
    	if(subj_code.split(",").length>1){ 
    		 
    		 if($("#subj_flag").val() == "true"){
    			 
    			 grid.options.parms.push({name:'subj_code',value:subj_code}); 
              	
              	grid.options.parms.push({name:'subj_select_flag',value:"3"});
    		 }else {
    			
				grid.options.parms.push({name:'subj_code',value:subj_code}); 
             	
             	grid.options.parms.push({name:'subj_select_flag',value:"4"});
    		 }
    		 
        }else{
        	 
        	if(subj_code=="")
        		{
        		
        		grid.options.parms.push({name:'subj_code',value:""}); 
             	
           	 grid.options.parms.push({name:'subj_select_flag',value:"1"});
        		}
        	else
        		{ 
        	 grid.options.parms.push({name:'subj_code',value:$("#subj_code").val().split(" ")[0]}); 
         	
        	 grid.options.parms.push({name:'subj_select_flag',value:"2"});
        		}
        }
    	//账套多选查询
    	
    	/* var rela_code = liger.get("rela_code").getValue();
    	if(rela_code.split(",").length>1){ 
	   		 if($("#rela_flag").val() == "true"){
	   			 grid.options.parms.push({name:'rela_code',rela_code}); 
	             	grid.options.parms.push({name:'rela_select_flag',value:"3"});
	   		 }else {
				grid.options.parms.push({name:'rela_code',rela_code}); 
	            	grid.options.parms.push({name:'rela_select_flag',value:"4"});
	   		 }
        }else{
        	if(rela_code==""){
        		grid.options.parms.push({name:'rela_code',value:""}); 
           	 	grid.options.parms.push({name:'rela_select_flag',value:"1"});
        	}else{  
        	    grid.options.parms.push({name:'rela_code',value:liger.get("rela_code").getValue()}); 
        	    grid.options.parms.push({name:'rela_select_flag',value:"2"});
       		}
        } */
    	 
    	if($("#is_show").is(":checked")){
    		is_show = 1;
    	}else{
    		is_show = 0;
    	}
    	grid.options.parms.push({name:'is_show',value : is_show});
    	
        //加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '期间',  align: 'center',columns:[{
                    	 display: '年', name: 'acc_year', align: 'left',width:50,isSort:false
                     },{
                    	 display: '月', name: 'acc_month', align: 'left',width:50,isSort:false
                     }]
					 },{ display: '凭证号', name: 'vouch_no', align: 'left',width:150,isSort:false
					 },
					 { display: '摘要', name: 'summary', align: 'left',width:200,isSort:false
					 },
					 {display: '借方', name: 'debit', align: 'right',width:200,formatter:'###,##0.00',isSort:false,
                    	 render : function(rowdata, rowindex, value) {
                    		 if(rowdata.debit == 0){return "";}
                    		 else{ return formatNumber(rowdata.debit, 2, 1);}
          				}
					 },
					 {display: '贷方', name: 'credit', align: 'right',width:200,formatter:'###,##0.00',isSort:false,
                    	 render : function(rowdata, rowindex, value) {
                    		 if(rowdata.credit == 0){return "";}
                    		 else{ return formatNumber(rowdata.credit, 2, 1);}
          				}
					 },
					 { display: '方向', name: 'subj_dire', align: 'left',width:200,isSort:false
					 },
                     { display: '余额', name: 'end_os',  align: 'right',width:200,formatter:'###,##0.00',isSort:false,
						 render : function(rowdata, rowindex, value) {
							 if(rowdata.end_os==0){return "Q";}
							 else{return formatNumber(rowdata.end_os, 2, 1);}
	         				}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'collectGroupAccSubjLedger.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     groupColumnName:'subj_name',groupColumnDisplay:'科目',selectRowButtonOnly:true,
                     toolbar: { items: [
							{ text: '查询',id:'search',icon:'search', click: query },
							{ line:true } , 
							{ text: '打印', id:'print', click: printDate,icon:'print' },
							{ line:true },
							{ text: '模板打印', id:'print', click: print_btn,icon:'print' },
							{ line:true },
							{ text: '模板', id:'settings', click: myPrintSet,icon:'settings' },
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
			          {"cell":0,"value":"会计期间："+$("#acc_year_month1").val()+"至"+$("#acc_year_month2").val(),"colSpan":"8","align":"center"} ,
			         {"cell":0,"value":"科目："+liger.get("subj_code").getText(),"br":"true","colSpan":"5"} ,
			         {"cell":0,"value":"账套："+liger.get("rela_code").getText(),"br":"true","colSpan":"5"} ,
			         {"cell":6,"value":"币种："+liger.get("cur_code").getText(),"colSpan":"2"} 
			          
		  		  ] 
		  	}; 
	   		var printPara={
	   			title: "科目总账按月",//标题
	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
	   			class_name: "com.chd.hrp.acc.service.books.subjaccount.GroupAccSubjLedgerService",
				method_name: "collectGroupAccSubjLedgerPrintDate",
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
  
    function print_btn(){  
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var selPara={};
       	$.each(grid.options.parms,function(i,obj){
       		selPara[obj.name]=obj.value;
       	});
       	selPara["template_code"]="01006";
       	selPara["class_name"]="com.chd.hrp.acc.service.books.subjaccount.GroupAccSubjLedgerService";
       	selPara["method_name"]="collectGroupAccSubjLedgerPrint";
       	selPara["bean_name"]="groupAccSubjLedgerService";
       	if(liger.get("acc_year_month1").getValue()!=""){
       		selPara["p_acc_year"]=liger.get("acc_year_month1").getValue().split(".")[0];	
       	}
       	selPara["p_year_month_b"]=liger.get("acc_year_month1").getValue();
       	selPara["p_year_month_e"]=liger.get("acc_year_month2").getValue();
       	selPara["p_subj_code"]=liger.get("subj_code").getText().split(" ")[0];
       	selPara["p_subj_name"]=liger.get("subj_code").getText().split(" ")[1];
       	selPara["p_cur_code"]=liger.get("cur_code").getText();
       	var isAccStr="不含未记账";
       	if($("#is_state").is(":checked")){ 
       		isAccStr="包含未记账";
    	}
       	selPara["p_is_acc"]=isAccStr;
       	
       	officeTablePrint(selPara);
    	
    }
    
    function loadDict(){
            //字典下拉框
    	
    	autocomplete("#cur_code","../../queryCur.do?isCheck=false","id","text",true,true,'',true);
    	//autocomplete("#rela_code","../../../sys/queryHosCopyDict.do?isCheck=false","id","text",true,true,'',true,false,'300');
    	autocompleteObj({
			id: '#rela_code',                   
			urlStr:	"../../../sys/queryHosCopyDict.do?isCheck=false",							
			valueField: 'id',            
			textField:   'text' ,            
			autocomplete:true,			
			highLight:true,
			initWidth: '250', 
			defaultSelect:true,
			keySupport: true,
			selectEvent:function(value){
				//console.log(arguments)
			var	para = {
         			rela_code : value
         		}; 
		 		autocomplete("#subj_code","../../querySubjByHosCopyRela.do?isCheck=false","id","text",true,true,para,true,false,'200');
			 
			
		    }
		}); 
    	
    	autocomplete("#acc_year_month1","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');
    	
    	autocomplete("#acc_year_month2","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');
    	 
    	var year_Month = '${yearMonth}';

    	liger.get("acc_year_month1").setValue(year_Month);
		 
    	liger.get("acc_year_month1").setText(year_Month);
    	 
    	liger.get("acc_year_month2").setValue(year_Month);
		 
    	liger.get("acc_year_month2").setText(year_Month);
    	 
    	is_state_manager = $("#is_state").ligerCheckBox();
         
    } 
     
	
	 function subjSelector(){ 
	    	$.ligerDialog.open({ url : '../../groupbookselector/groupAccSubjSelectorPage.do?isCheck=false&flag='+$("#subj_flag").val()+'&rela_code='+liger.get("rela_code").getValue(),
	    			data:{listBoxData:subj_box_data/* ,
	    				  rela_code : liger.get("rela_code").getValue()	 */
	    			}, height:$(window).height()-40,width: 600, title:'账簿科目选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
	    			
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
	    				        	   
	    				        	   },cls:'l-dialog-btn-highlight' 
	    				        	   
	    				           }, 
	    				           
	    				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
	    				         
	    				           ] });
	    }

	 //打印维护
	 function myPrintSet(){
		 officeFormTemplate({template_code:"01006"});
	 }
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">

<input type="hidden" id="subj_flag" name="subj_flag"/> 
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>会计期间：</td>
            <td align="left" class="l-table-edit-td">
            	<table>
					<tr>
						<td>
						<input name="acc_year_month1" type="text" id="acc_year_month1"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td>
							<input name="acc_year_month2" type="text" id="acc_year_month2"/>
						</td>
            		</tr>
				</table>
            </td>
            <td align="left"></td> 
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>账套：</td>
            <td align="left" class="l-table-edit-td"><input name="rela_code" type="text" id="rela_code" ltype="text" /></td>
            <!-- <td align="left"><input class="l-button l-button-test"  type="button" value="选择" onclick="subjSelector();"/></td> -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>科　　目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" /></td>
            <td align="left"><input class="l-button l-button-test"  type="button" value="选择" onclick="subjSelector();"/></td>
          </tr>
          <tr>  
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">币　　种：</td>
            <td align="left" class="l-table-edit-td" ><input name="cur_code" type="text" id="cur_code" ltype="text"  /></td>
            <td align="left" style="padding-left:20px;" colspan="4">
           		<input name="is_state" type="checkbox" id="is_state" ltype="text" checked="checked" />包含未记账 
           		<input name="is_show" type="checkbox" id="is_show" ltype="text"  />显示本期无发生
            </td>
		</tr>

    </table>

	<div id="maingrid"></div>

</body>
</html>
