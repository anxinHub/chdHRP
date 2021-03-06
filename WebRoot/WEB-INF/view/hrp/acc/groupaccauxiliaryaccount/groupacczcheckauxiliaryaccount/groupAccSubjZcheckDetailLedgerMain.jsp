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
    	
		$("#acc_year_month1").ligerTextBox({ width:105 });
    	
    	$("#acc_year_month2").ligerTextBox({ width:105 });
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
    		var check_type = liger.get("check_item_type").getValue();
    	       
    	    var check_item = liger.get("check_item_code").getValue();
    	    
    	    var check_itemn = liger.get("check_item_code").getText();
    	    
    		if(liger.get("acc_year_month1").getValue() == '' || liger.get("acc_year_month1").getValue() == undefined){
            	$.ligerDialog.error('起始年月为必填项');
            	return ;
            }
            	
            if(liger.get("acc_year_month2").getValue() == '' || liger.get("acc_year_month2").getValue() == undefined){
            	$.ligerDialog.error('截止年月为必填项');
            	return ;
            } 
            
            var subj_code = liger.get("acc_subj_code").getValue(); 
            if(subj_code == ""){
 	    	   
     	       $.ligerDialog.error('科目为必填项,不能为空');
     	    	   
     	    	return;
     	    	   
     	     }
        	
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
          	 grid.options.parms.push({name:'subj_code',value:subj_code}); 
           	
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
    	
    	var is_happend = 0;
		if($("#is_happend").is(":checked")){
			is_happend = 1;
    	}else{
    		is_happend = 0;
    	}
		grid.options.parms.push({name:'is_happend',value:is_happend});
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }
  

    function loadHead(){
    	
    	gridData =spliceJson();
    	
		var columns = "";
    	
    	columns = columns + "{ display: '期间', align: 'left',columns:["
			+"{ display: '年', isSort:false, name: 'ACC_YEAR', align: 'left', width: '4%'},"
	        +"{ display: '月', isSort:false, name: 'ACC_MONTH', align: 'left', width: '4%'},"
	        +"{ display: '日', isSort:false, name: 'ACC_DAY', align: 'left', width: '4%'}"
			+"]},"
			+"{ display: '凭证号', isSort:false, name: 'VOUCH_NO', align: 'left', width: '8%',render : function(rowdata, rowindex, value) {if(rowdata.VOUCH_NO !=null && rowdata.VOUCH_NO !='-'){return '<a href=javascript:openSuperVouch('+rowdata.VOUCH_ID+')>'+rowdata.VOUCH_NO+'</a>'; }}},";
			
			if(gridData!=""&& gridData.length>0){
				
				for(var i=0;i<gridData.length;i++)
			   		 
			   	{

			   	 columns = columns + "{ display: '"+gridData[i]+"', isSort:false, name: 'check"+i+"', align: 'center'},";
			   	    
			   	}	
				
			}
			columns = columns +"{ display: '核算项', isSort:false, name: 'OBJ_NAME', align: 'left'},"
			+"{ display: '摘要', isSort:false, name: 'SUMMARY', align: 'left'},"
			+"{ display: '借方', isSort:false, name: 'DEBIT', align: 'right',width: '10%',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {return formatNumber(rowdata.DEBIT, 2, 1);}},"
			+"{ display: '贷方', isSort:false, name: 'CREDIT', align: 'right',width: '10%',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {return formatNumber(rowdata.CREDIT, 2, 1);}},"
			+"{ display: '方向', isSort:false, name: 'SUBJ_DIRE', align: 'left', width: '2%'},"
			+"{ display: '余额', isSort:false, name: 'END_OS', align: 'right',width: '10%',formatter:'###,##0.00',reg:'0.00=Q,0=Q',render : function(rowdata, rowindex, value) {if(rowdata.END_OS==0)  return 'Q'; else  return formatNumber(rowdata.END_OS, 2, 1);}}";
		
    	 grid = $("#maingrid").ligerGrid({
	           columns: eval("["+columns+"]"),
	                     dataAction: 'server',dataType: 'server',usePager:false,url:'collectGroupAccSubjZcheckDetailLedger.do',
	                     width: '100%', height: '95%', checkbox: false,rownumbers:true,delayLoad:true,
	                     selectRowButtonOnly:true,
	                     toolbar: { items: [
	                                     	{ text: '查询', id:'search', click: query,icon:'search' },
	                                     	{ line:true },
	                                     	{ text: '打印', id:'print', click: printDate,icon:'print' },
	                						{ line:true }
	                    				]}
	                   });
	        gridManager = $("#maingrid").ligerGetGridManager();
	        
    }

     
    function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}

    function loadDict(){
    	 
    	para="";//清空参数
		para = {
			is_sys : 0
		};
		autocompleteObj({
			id: '#acc_subj_code',                   
			urlStr:	"../../querySubjCode.do?isCheck=false&is_check="+1,							
			valueField: 'id',            
			textField:   'text' ,            
			autocomplete:true,			
			highLight:true,
			parmsStr:para,
			defaultSelect:true,
			selectEvent:function(value){
				//console.log(arguments)
		    var acc_subj_code = liger.get("acc_subj_code").getValue();
			var	para = {
					subj_code : value
         		};
				
			autocompleteObj({
				id: '#check_item_type',                   
				urlStr:	"../../queryCheckTypeBySubjCode.do?isCheck=false",							
				valueField: 'id',            
				textField:   'text' ,            
				autocomplete:true,			
				highLight:true,
				parmsStr:para,
				defaultSelect:true,
				selectEvent:function(value){
					 
				 var param = {
						 check_type_id: value
	         		}; 
		 		autocomplete("#check_item_code","../../queryCheckItemByType.do?isCheck=false","id","text",true,true,param,false);
			 			
			    }
			 })  
		    	
		  }
		}); 
		 para="";
		para ={subj_code:0};
    	
    	autocomplete("#check_item_code","../../queryCheckItemByType.do?isCheck=false","id","text",true,true,'',false); 
    	//autocomplete("#check_item_type",".../queryCheckTypeBySubjCode.do?isCheck=false","id","text",true,true,'',true);  
		autocomplete("#acc_year_month1","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');
    	
    	autocomplete("#acc_year_month2","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');

    	var year_Month = '${yearMonth}';

    	liger.get("acc_year_month1").setValue(year_Month);
		 
    	liger.get("acc_year_month1").setText(year_Month);
    	 
    	liger.get("acc_year_month2").setValue(year_Month);
		 
    	liger.get("acc_year_month2").setText(year_Month);

    	 $("#cur_code").ligerComboBox({
		      	url: '../../queryCur.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 160,
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
     				        	  liger.get("acc_subj_code").setValue(subj_param);
     				              
   				        	   liger.get("acc_subj_code").setText(param);
     				        	   dialog.close(); 
    				        	   
    				        	   },cls:'l-dialog-btn-highlight' 
    				        	   }, 
    				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
    				         ] });
    }
  //根据选择核算类构建动态列
 	function spliceJson(){
    	
   	var resColumn=$("#check_item_type").val();
   	
   	var array = resColumn.split(";");
   	
   	return array;
   	
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
 	       	selectBoxWidth: 160,
 	      	autocomplete: true,
 	      	width: 160,
 	      	
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
		          {"cell":0,"value":"会计日期："+$("#acc_year_month1").val()+"至"+$("#acc_year_month2").val(),"colSpan":"4"} ,
		          {"cell":4,"value":"科目："+liger.get("acc_subj_code").getText().split(" ")[1],"from":"right","align":"right","colSpan":"5"}
	    		  ]
	    	}; 
	   		var printPara={
	   			title: "科目核算项明细账",//标题
	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
	   			class_name: "com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccZcheckAuxiliaryAccountService",
				method_name: "collectGroupAccSubjZcheckDetailLedgerPrint",
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
    <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
	
       <tr>
            <td align="left"><font size="2" color="red">*</font>会计期间：</td>
            <td>
            	<table>
            		<tr>
            			 <td align="left" class="l-table-edit-td"  ><input id="acc_year_month1" name="acc_year_month1" /></td>
			            <td align="center" >-</td>
			            <td align="right" class="l-table-edit-td"><input id="acc_year_month2" name="acc_year_month2" /></td>
            		</tr>
            	</table>
            </td>
           
            <td align="right" class="l-table-edit-td" style="padding-left:40px;"   ><font size="2" color="red">*</font>&nbsp;&nbsp;科目：</td>
            <td align="left" class="l-table-edit-td"  ><input id="acc_subj_code" name="acc_subj_code"  /></td>
            <td  align="right" ><input class="l-button l-button-test"  type="button" value="选择" onclick="subjSelector()"/></td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:40px;"  >核算类：</td>
            <td align="left" class="l-table-edit-td" ><input id="check_item_type" name="check_item_type" /></td>
            
         	
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  >&nbsp;核算项：</td>
        	<td>
        		<table>
        			<tr>
        				 <td align="left" class="l-table-edit-td" ><input id="check_item_code" name="check_item_code"  /></td>
        	 			 <td ><input class="l-button l-button-test"  type="button" value="选择" onclick="showWindow()"/></td>
        			</tr>
        		</table>
        	</td> 
        	<td align="right" class="l-table-edit-td" style="padding-left:40px;" >币种：</td>
            <td align="left" class="l-table-edit-td" colspan="2" ><input id="cur_code" name="cur_code"  /></td>
            <td align="right" class="l-table-edit-td" colspan="2" style="padding-left:40px;"  >
	            <input name="is_state" type="checkbox" id="is_state" checked="checked" /> 包含未记账 
	            <input name="is_happend" type="checkbox" id="is_happend"  /> 无发生不显示期初余额
            </td>
        </tr>
	 
    </table>
	</form>
	<div id="maingrid"></div>

</body>
</html>
