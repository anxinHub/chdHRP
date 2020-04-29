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
    
    var check_tree="";
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	loadHotkeys();
    	
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
    	
    });
    //查询
    function  query(){//根据表字段进行添加查询条件
    	
    	
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		var year_month1= liger.get("acc_year_month1").getValue();
            var year_month2= liger.get("acc_year_month2").getValue();
             
            var subj_code = liger.get("subj_code").getValue();
            if(year_month1==""||year_month2 ==""){
            	$.ligerDialog.error('会计期间为必填项，不能为空！');
            	return;
            }
            if(liger.get("rela_code").getValue()==""||liger.get("rela_code").getValue() ==""){
            	$.ligerDialog.error('账套为必填项，不能为空！');
            	return;
            }
            
        	grid.options.parms.push({name:'acc_year_b',value:year_month1.split(".")[0]}); 
            grid.options.parms.push({name:'acc_month_b',value:year_month1.split(".")[1]}); 
            grid.options.parms.push({name:'acc_year_e',value:year_month2.split(".")[0]}); 
            grid.options.parms.push({name:'acc_month_e',value:year_month2.split(".")[1]});
            
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
         			if(subj_code.split(".").length>1){
         				grid.options.parms.push({name:'subj_code',value:subj_code.split(".")[1]}); 
             	 		grid.options.parms.push({name:'subj_select_flag',value:"2"});
         			}else{
         				grid.options.parms.push({name:'subj_code',value:subj_code}); 
             	 		grid.options.parms.push({name:'subj_select_flag',value:"4"});
         			}
         		}
            }

            grid.options.parms.push({name:'cur_code',value:liger.get("cur_code").getValue()}); 
            grid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue()}); 
            grid.options.parms.push({name:'rela_code',value:liger.get("rela_code").getValue()}); 
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
                     { display: '科目编码', name:'obj_code', align: 'left',width:120,isSort:false,
						 render : function(rowdata, rowindex, value) { 
							 if(rowdata.is_check==1){
								 
								 return"<a href='#'>"+rowdata.obj_code+"</a>";
							 }else if(rowdata.is_check==0){
								 
								 return rowdata.obj_code;
							 }
							}
                     }, 
					 { display: '科目名称', name: 'obj_name', align: 'left',width:120,isSort:false,
						 render : function(rowdata, rowindex, value) {
								return formatSpace(rowdata.obj_name,rowdata.subj_level - 1);
							}
					 },
					 { display: '科目全称', name: 'obj_name_all', align: 'left',width:400,isSort:false
					 },
					 
					 { display: '年初余额', align: 'center', id:'01',formatter:'###,##0.00',
						 columns:[
						 	{display: '借方', name: 'nc_d', align: 'right',id:'01D',width:150,formatter:'###,##0.00',reg:'0.00= ,0= ',isSort:false,render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.nc_d, 2, 1);}}},
                     		{display: '贷方', name: 'nc_c', align: 'right',id:'01C',width:150,formatter:'###,##0.00',reg:'0.00= ,0= ',isSort:false,render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.nc_c, 2, 1);}}}
						 ]
					 },
					 
					 { display: '期初余额', align: 'center', id:'02',
						 columns:[
						 	{display: '借方', name: 'qc_d', align: 'right',id:'02D',width:150,formatter:'###,##0.00',reg:'0.00= ,0= ',isSort:false,render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.qc_d, 2, 1);}}},
                     		{display: '贷方', name: 'qc_c', align: 'right',id:'02C',width:150,formatter:'###,##0.00',reg:'0.00= ,0= ',isSort:false,render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.qc_c, 2, 1);}}}
						 ]
					 },  
					 
					 { display: '本期发生', align: 'center', id:'03',
						 columns:[
						 	{display: '借方', name: 'bq_d', align: 'right',id:'03D',width:150,formatter:'###,##0.00',reg:'0.00= ,0= ',isSort:false,render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.bq_d, 2, 1);}}},
                    	 	{display: '贷方', name: 'bq_c', align: 'right',id:'03C',width:150,formatter:'###,##0.00',reg:'0.00= ,0= ',isSort:false,render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.bq_c, 2, 1);}}}
						 ]
					 }, 
					 
					 { display: '累计发生', align: 'center', id:'04',
						 columns:[
							{display: '借方', name: 'sum_od', align: 'right',id:'04D',width:150,formatter:'###,##0.00',reg:'0.00= ,0= ',isSort:false,render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.sum_od, 2, 1);}}},
							{display: '贷方', name: 'sum_oc', align: 'right',id:'04C',width:150,formatter:'###,##0.00',reg:'0.00= ,0= ',isSort:false,render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.sum_oc, 2, 1);}}}
						]
					 },
					
					 { display: '期末余额', align: 'center', id:'05',
						 columns:[
						 	{display: '借方', name: 'end_d', align: 'right',id:'05D',width:150,formatter:'###,##0.00',reg:'0.00= ,0= ',isSort:false,render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.end_d, 2, 1);}}},
						 	{display: '贷方', name: 'end_c', align: 'right',id:'05C',width:150,formatter:'###,##0.00',reg:'0.00= ,0= ',isSort:false,render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.end_c, 2, 1);}}}
						]
					 }
				], 
				dataAction: 'server',dataType: 'server',usePager:false,url:'collectGroupBalanceLedgerDetail.do',
				width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,selectRowButtonOnly:true,
				//heightDiff: 25,
				toolbar: { items: [
									{ text: '查询',id:'search', click: queryBtn ,icon:'search'},
									{ line:true } , 
									/* { text: '明细',id:'view', click: queryDetail ,icon:'view'},
									{ line:true } , 
									{ text: '辅助核算',id:'initwage', click: queryCheck ,icon:'initwage'},
									{ line:true } , */ 
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
    
    function queryDetail(){
    	var row = gridManager.getSelectedRow();
        if (!row&&row.subj_name.indexOf("小计")>-1) { 
        	$.ligerDialog.error('请选择查询科目！');
        	return;
        }
        
        var state = 99;
        if($("#is_state").is(":checked")){
        	state=1;
        }
        var para="&subj_code="+row.obj_code+"&subj_name="+row.obj_name+"&acc_year="+liger.get("acc_year_month1").getValue()+"&year_month="+liger.get("acc_year_month2").getValue()+"&state="+state;
        parent.openFullDialog('hrp/acc/accSubjLederDetailPage.do?isCheck=fasle'+para,'明细',0,0,true,true);
        
    }
    
    function queryCheck(){
    	
		var row = gridManager.getSelectedRow();
        if (!row&&row.subj_name.indexOf("小计")>-1||row.is_check==0) { 
        	//$.ligerDialog.error('请选择辅助核算科目！');
        	return;
        }

		var state = 99;
        if($("#is_state").is(":checked")){
        	state=1;
        }

        var para="&subj_code="+row.obj_code+"&subj_name="+row.obj_name+"&acc_year="+liger.get("acc_year_month1").getValue()+"&year_month="+liger.get("acc_year_month2").getValue()+"&state="+state+'&subj_id='+row.obj_id;
        parent.openFullDialog('hrp/acc/accSubjLederCheckPage.do?isCheck=fasle'+para,'辅助核算',0,0,true,true);
        
    }
  
	function loadHotkeys(){//键盘事件
		hotkeys('Q',queryBtn);
		hotkeys('P',printBtn);
	}
  
    function queryBtn(){//查询
    	query();
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
 
    	selPara["template_code"]="01004";
       	selPara["class_name"]="com.chd.hrp.acc.service.books.subjaccount.GroupAccSubjLedgerService";
       	selPara["method_name"]="collectGroupBalanceLedgerDetailPrint";
       	selPara["bean_name"]="groupAccSubjLedgerService";
       	if(liger.get("acc_year_month1").getValue()!=""){
       		selPara["p_acc_year"]=liger.get("acc_year_month1").getValue().split(".")[0];	
       	}
       	selPara["p_year_month_b"]=liger.get("acc_year_month1").getValue();
       	selPara["p_year_month_e"]=liger.get("acc_year_month2").getValue();
       	selPara["p_subj_code"]=liger.get("subj_code").getText();
       	selPara["p_subj_level"]=liger.get("subj_level").getValue();
       	selPara["p_cur_code"]=liger.get("cur_code").getText();
       	var isAccStr="不含未记账";
       	if($("#is_state").is(":checked")){
       		isAccStr="包含未记账";
    	}
       	selPara["p_is_acc"]=isAccStr;
       	
       	officeTablePrint(selPara);
    	
   		/* //console.log(grid)
   		var printPara={
   			headCount:2,
   			title:'科目余额表',
   			type:3,
   			columns:grid.getColumns(1),
   			autoFile:true 
   		};

   		ajaxJsonObjectByUrl("collectBalanceLedgerDetail.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	}); */
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
    
	function loadDict(){//字典下拉框
		
    	autocomplete("#cur_code","../../queryCur.do?isCheck=false","id","text",true,true,'',true,false,'220');
    	//autocomplete("#subj_code","../../querySubjBylevel.do?isCheck=false","id","text",true,true,'',false,false,'300');
    	//$("#subj_code").ligerTextBox({width:160 });
    	autocomplete("#subj_level","../../querySubjLevel.do?isCheck=false","id","text",true,true,'',true,false,'200');
    	//autocomplete("#rela_code","../../../sys/queryHosCopyDict.do?isCheck=false","id","text",true,true,'',true,false,'300');  
    	//$("#rela_code").ligerTextBox({width:220 });
    	
    	autocompleteObj({
			id: '#rela_code',                   
			urlStr:	"../../../sys/queryHosCopyDict.do?isCheck=false",							
			valueField: 'id',            
			textField:   'text' ,            
			autocomplete:true,			
			highLight:true,
			initWidth: '220', 
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
    	
    	//-----------------会计期间设置-----------------
    	autocomplete("#acc_year_month1","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');
    	autocomplete("#acc_year_month2","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');
    	var year_Month = '${yearMonth}';
   	 	liger.get("acc_year_month1").setValue(year_Month);
   	 	liger.get("acc_year_month1").setText(year_Month);
   	 	liger.get("acc_year_month2").setValue(year_Month);
   	 	liger.get("acc_year_month2").setText(year_Month);
   	 	//------------------会计期间设置-----------------
		
		is_state_manager = $("#is_state").ligerCheckBox();
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

	}
	
	function subjSelector(){//科目选择器
    	$.ligerDialog.open({ url : '../../groupbookselector/groupAccBookSubjSelectorPage.do?isCheck=false&flag='+$("#subj_flag").val()+'&sign=0'+'&check_nodes='+check_tree+'&rela_code='+liger.get("rela_code").getValue(),
    			data:{listBoxData:subj_box_data}, height:$(window).height()-40,width: 600, title:'账簿科目选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			buttons: [{ text: '确定', onclick:  function (item, dialog) { 
    				
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
    				           },cls:'l-dialog-btn-highlight'}, 
    				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
    				    ] });
    }
	
	//打印维护
	 function myPrintSet(){
		 officeFormTemplate({template_code:"01004"});
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
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" >级　　次：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_level" type="text" id="subj_level" ltype="text"  /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">显示栏目：</td>
            <td align="left" class="l-table-edit-td">
				<input name="check_column1" type="checkbox" id="check_column1" ltype="text"  />年初余额
            	<input name="check_column2" type="checkbox" id="check_column2" ltype="text"  />期初余额
            	<input name="check_column3" type="checkbox" id="check_column3" ltype="text"  />本期发生
            	<input name="check_column4" type="checkbox" id="check_column4" ltype="text"  />累计发生
            	<input name="check_column5" type="checkbox" id="check_column5" ltype="text"  />期末余额
            </td>
            <td align="left"></td>
            
		</tr>
		 <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>账　　套：</td>
            <td align="left" class="l-table-edit-td"><input name="rela_code" type="text" id="rela_code" ltype="text" /></td>
            <!-- <td align="left"><input class="l-button l-button-test"  type="button" value="选择" onclick="subjSelector();"/></td> -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" colspan="2">科　　目：</td>
            <td align="left" class="l-table-edit-td" colspan="2"><input name="subj_code" type="text" id="subj_code" ltype="text" /></td>
            <td align="left"><input class="l-button l-button-test"  type="button" value="选择" onclick="subjSelector();"/></td>
            
        </tr>
		<tr>
			
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">币　　种：</td>
            <td align="left" class="l-table-edit-td" ><input name="cur_code" type="text" id="cur_code" ltype="text"  /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left" class="l-table-edit-td" colspan="3">
            	<input name="is_state" type="checkbox" id="is_state" ltype="text" checked="checked" />包含未记账
            </td>
            <td align="left"></td>
        </tr> 
       
    </table>
    
    <div id="maingrid"></div>

	
</body>
</html>
