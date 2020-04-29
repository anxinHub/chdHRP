
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
    
    var gridData;
    
    var query_subj_code;
    
    var subj_box_data = "";
    
    $(function ()
    {
    	//loadForm();
    	
    	
		loadDict();
	
    	loadHead(null);	//加载数据
    	
		$("#acc_year_month1").ligerTextBox({ width:80 });
    	
    	$("#acc_year_month2").ligerTextBox({ width:80 });
    	
    });
    //查询
    function  query(){
    	
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    		if(liger.get("acc_year_month1").getValue() == '' || liger.get("acc_year_month1").getValue() == undefined){
            	$.ligerDialog.error('起始年月为必填项');
            	return ;
            }
            	
            if(liger.get("acc_year_month2").getValue() == '' || liger.get("acc_year_month2").getValue() == undefined){
            	$.ligerDialog.error('截止年月为必填项');
            	return ;
            } 
        
    		var check_type = liger.get("check_item_type").getValue();
    	       
    	    var check_item = liger.get("check_item_code").getValue();
    	    
    	    var check_itemn = liger.get("check_item_code").getText();
    	       
    	    if(check_type == ""){
    	    	   
    	    	 $.ligerDialog.error('核算类为必填项,不能为空');
    	    	   
    	    	 return;
    	    	   
    	     }
    	       
    		
    		
    		var subj_code = liger.get("subj_code").getValue();
        	
    		if(subj_code.split(",").length>1){
        	            	
    			if($("#subj_flag").val() == "true"){
        			grid.options.parms.push({name:'subj_code',value:subj_code}); 
                	
                	grid.options.parms.push({name:'subj_select_flag',value:"3"});
        		}
        		else
        			{
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
        		var code = subj_code.split(".").length > 1 ? subj_code.split(".")[1] : subj_code;
        		grid.options.parms.push({name:'subj_code',value:code}); 
         	
        	 grid.options.parms.push({name:'subj_select_flag',value:"2"});
        		}
            }
		grid.options.parms.push({name:'acc_year_b',value:liger.get("acc_year_month1").getValue().split(".")[0]}); 
		
       	grid.options.parms.push({name:'acc_month_b',value:liger.get("acc_year_month1").getValue().split(".")[1]}); 
		
       	grid.options.parms.push({name:'acc_year_e',value:liger.get("acc_year_month2").getValue().split(".")[0]}); 
		
       	grid.options.parms.push({name:'acc_month_e',value:liger.get("acc_year_month2").getValue().split(".")[1]}); 
    	
        grid.options.parms.push({name:'check_item_type',value:check_type}); 
       	
       	grid.options.parms.push({name:'check_item_code',value:check_item}); 
       	
		var is_state = 99;
    	
		if($("#is_state").get(0).checked == true){
    		is_state = 1;
    	}
    	grid.options.parms.push({name:'is_state',value:is_state});
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    function loadHead(){
    	
    	 grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '期间', align: 'left',columns:[
             							{ display: '年', isSort:false, name: 'acc_year', align: 'left', width: '4%'},
            					        { display: '月', isSort:false, name: 'acc_month', align: 'left', width: '4%'},
            					        { display: '日', isSort:false, name: 'acc_day', align: 'left', width: '4%'}
            						]
					 },
					 { display: '凭证号', isSort:false, name: 'vouch_no', align: 'left',width: '8%',
						 render : function(rowdata, rowindex, value) {
						  if(rowdata.vouch_no  !=null){
								  return "<a href=javascript:openSuperVouch('"+rowdata.vouch_no.split('-')[2]+"')><div>"+rowdata.vouch_no.split('-')[0]+"-"+rowdata.vouch_no.split('-')[1]+"</div></a>"; 
							 }  
							 
						 }
               },
					 { display: '核算项', isSort:false, name: 'obj_name', align: 'left'
					 },
					 
					 { display: '科目', isSort:false, name: 'subj_name', align: 'left'
					 },
					 { display: '摘要', isSort:false, name: 'summary', align: 'left'
					 },
					 { display: '借方', isSort:false, name: 'debit',align: 'right',width: '10%',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {return formatNumber(rowdata.debit, 2, 1);}
					 },
					 { display: '贷方', isSort:false, name: 'credit', align: 'right',width: '10%',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {return formatNumber(rowdata.credit, 2, 1);}
					 },
                     { display: '方向', isSort:false, name: 'subj_dire', align: 'left', width: '2%'
					 },
                     { display: '余额', isSort:false, name: 'end_os', align: 'right',width: '10%',formatter:'###,##0.00',reg:'0.00=Q,0=Q',render : 
                    	 function(rowdata, rowindex, value) 
                    	 {
                    	 if(rowdata.end_os==0)
                			 return "Q";
                		 else
       					    return formatNumber(rowdata.end_os, 2, 1);
                    	 }
					 }
                     ],       
                     dataAction: 'server',dataType: 'server',usePager:false,url:'collectGroupAccZcheckDetailLedger.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     delayLoad:true,
                     groupColumnName:'subj_name',groupColumnDisplay:'科目',selectRowButtonOnly:true,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '打印', id:'print', click: printDate,icon:'print' },
						{ line:true }
    				]}, 
                   });

        gridManager = $("#maingrid").ligerGetGridManager(); 
    }
    
    
    function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}

    

    function loadDict(){
    	para="";//清空参数
		para = {
				is_sys : '0'
		};
    	
// 		$("#check_item_type").ligerComboBox({
// 			parms:para,
//           	url: '../queryCheckType.do?isCheck=false',
//           	valueField: 'id',
//            	textField: 'text', 
//            	selectBoxWidth: 80,
//           	autocomplete: true,
//           	width: 180,
//           	selectBoxWidth: 180,
//           	onSuccess: function (data) {

//     				if (data.length > 0) {
//     					if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
    					
//     							this.setValue(data[0].id);
//     				}
//     			}

//     		},
//           	onSelected:function(data){
//           		para="";//清空参数
//         		para = {
//         			check_type_id : liger.get("check_item_type").getValue()
//         		};
            	
//         		//$("#check_item_code").val('');
//         		//方案
//         		autocomplete("#check_item_code",
//         				"../queryCheckItem.do?isCheck=false", "id", "text",
//         				true, true, para);
//         		$("#subj_code").val('');
//         		$("#subj_code").ligerComboBox({
//         			parms:para,
//         	      	url: '../querySubj.do?isCheck=false',
//         	      	valueField: 'id',
//         	       	textField: 'text', 
//         	       	selectBoxWidth: 160,
//         	      	autocomplete: true,
//         	      	width: 160,
        	      	
//         		 });
//           	}
//  		  });
    	
    	//$("#check_item_code").ligerComboBox({ isShowCheckBox: true, isMultiSelect: true});
    	
    	para="";//清空参数
		para = {
			check_type_id : '0',
// 			is_sys : 0
		};
		autocompleteObj({
			id: '#check_item_type',                   
			urlStr:	"../../queryCheckType.do?isCheck=false",							
			valueField: 'id',            
			textField:   'text' ,            
			autocomplete:true,			
			highLight:true,
			parmsStr:para,
			defaultSelect:true,
			selectEvent:function(value){
				//console.log(arguments)
			var	para = {
         			check_type_id : value
         		};
				
				liger.get('check_item_code').setValue('');
		 		autocomplete("#check_item_code","../../queryCheckItemByType.do?isCheck=false","id","text",true,true,para,true);
		 		$("#subj_code").val('');
		 		$("#subj_code").ligerComboBox({
        			parms:para,
        	      	url: '../../querySubjCode.do?isCheck=false',
         	      	valueField: 'id',
         	       	textField: 'text', 
         	       	selectBoxWidth: 160,
         	      	autocomplete: true,
         	      	width: 160,
        	      	
        		 });
		    }
		}); 
    	autocomplete("#check_item_code","../../queryCheckItem.do?isCheck=false","id","text",true,true,para,true);
    	
		autocomplete("#acc_year_month1","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');
    	
    	autocomplete("#acc_year_month2","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');

    	var year_Month = '${yearMonth}';

    	liger.get("acc_year_month1").setValue(year_Month);
		 
    	liger.get("acc_year_month1").setText(year_Month);
    	 
    	liger.get("acc_year_month2").setValue(year_Month);
		 
    	liger.get("acc_year_month2").setText(year_Month);

    	 
    	 $("#subj_code").ligerComboBox({
    		 	parms:para,
    	      	url: '../../querySubj.do?isCheck=false&sign=11',
    	      	valueField: 'id',
    	       	textField: 'text', 
    	       	selectBoxWidth: 320,
    	      	autocomplete: true,
    	      	width: 180
    		 });
    	 
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
    
    function loadForm(){
    	 $("form").ligerForm();
        
     }  
    
 function showWindow(){
    	
    	var check_type_id = liger.get("check_item_type").getValue();

 	    if(check_type_id == ""){
 	    	   
 	    	 $.ligerDialog.error('核算类为必填项,不能为空');
 	    	   
 	    	 return;
 	    	   
 	     }
    	
    	$.ligerDialog.open({
    		url: 'groupAccZCheckItemCheckPage.do?isCheck=false&check_type_id='+check_type_id+'&check_type_name='+liger.get("check_item_type").getText(),
    		id :'accZCheckItemCheck',
    		height: 450,
    		width: 720,
    		title:'',
    		modal:true,
    		showToggle:false,
    		showMax:false,
    		showMin: false,
    		isResize:true,
    		buttons: [ 
    		    { text: '确定', onclick: function (item, dialog) { 
    		    	dialog.frame.saveSelectData();//dialog.close(); 
    		    },cls:'l-dialog-btn-highlight' }, 
    			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    
function subjSelector(){
    	
    	$.ligerDialog.open({ url : '../../bookselector/accBookSubjSelectorPage.do?isCheck=false&flag='+$("#subj_flag").val()+'&sign=11' ,
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
   
	function onCheckTypeChange(){
    	
    	para="";//清空参数
		para = {
			check_type_id : liger.get("check_item_type").getValue()
		};
    	
		$("#check_item_code").val('');
		//方案
		autocomplete("#check_item_code",
				"../../queryCheckItem.do?isCheck=false", "id", "text",
				true, true, para);
		$("#subj_code").val('');
		$("#subj_code").ligerComboBox({
			parms:para,
	      	url: '../../querySubj.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 180,
	      	autocomplete: true,
	      	width: 180,
	      	
		 });
    }
   
	
	//打印数据
	function printDate(){
		 
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		   
		var heads={
	    		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	    		  "rows": [
		          {"cell":0,"value":"会计日期："+$("#acc_year_month1").val()+"至"+$("#acc_year_month2").val(),"colSpan":"5"},
		          {"cell":3,"value":"核算类："+liger.get("check_item_type").getText(),"from":"right","align":"right","colSpan":"4"}
	    		  ]
	    	};
	     
	   		var printPara={
	   			title: "核算项科目明细账",//标题
	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
	   			class_name: "com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccZcheckAuxiliaryAccountService",
				method_name: "collectGroupAccZcheckDetailLedgerPrint",
				bean_name: "groupAccZcheckAuxiliaryAccountService",
				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
	   		};
	    	
	   		//执行方法的查询条件
	   		
	   		$.each(grid.options.parms,function(i,obj){
	   			
	   			printPara[obj.name]=obj.value;
	    	});
	   		
	    	officeGridPrint(printPara);
		 
	}
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
<input type="hidden" id="subj_flag" name="subj_flag"/>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<form name="form1" method="post"  id="form1" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit"  border="0">
	
        <tr>
            <td align="left"><font size="2" color="red">*</font>会计期间：</td>
            <td align="left" class="l-table-edit-td" style="padding-left:20px;"><input id="acc_year_month1" name="acc_year_month1" /></td>
            <td align="center" >-</td>
            <td align="right" class="l-table-edit-td"><input id="acc_year_month2" name="acc_year_month2" /></td>
         	<td align="right" class="l-table-edit-td"  ><font size="2" color="red">*</font>核算类：</td>
            <td align="left" class="l-table-edit-td" ><input id="check_item_type" name="check_item_type" /></td>
            <td align="right" class="l-table-edit-td"  >核算项：</td>
            <td align="left" class="l-table-edit-td" ><input id="check_item_code" name="check_item_code"  /></td>
        	 <td colspan="2"><input class="l-button l-button-test"  type="button" value="选择" onclick="showWindow()"/></td> 
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  >科&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
            <td align="left" class="l-table-edit-td" style="padding-left:20px;" colspan="3" ><input id="subj_code" name="subj_code"  /></td>
            <td colspan="2"><input class="l-button l-button-test"  type="button" value="选择" onclick="subjSelector();"/></td>
        	<td align="right" class="l-table-edit-td">币种：</td>
            <td align="left" class="l-table-edit-td" ><input id="cur_code" name="cur_code"  /></td>
            <td align="right" class="l-table-edit-td" ><input name="is_state" type="checkbox" id="is_state" checked="checked" /></td>
            <td align="left" class="l-table-edit-td">包含未记账</td>
        </tr>
	 
    </table>
	</form>
	<div id="maingrid"></div>
</body>
</html>
