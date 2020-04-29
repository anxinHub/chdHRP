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
    	
    	if('${accPara043}'==1){
			
			 grid.set("groupColumnName", "subj_name");
			
			 grid.set("groupColumnDisplay", "科目");
			 
		}else {
			
			 grid.set("groupColumnName", "");
			
			 grid.set("groupColumnDisplay", "");
			 
		}
    	
    });

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '年度', align: 'left',
						columns:[
					 	{display: '月', name: 'acc_month', align: 'left',width:60,isSort:false,
					 		render : function(rowdata, rowindex, value) {
					 		return rowdata.acc_month;
					 	}
					 	},
                    	{display: '日', name: 'acc_day', align: 'left',width:60,isSort:false,
					 		render : function(rowdata, rowindex, value) {
					 			
					 			return rowdata.acc_day;
					 			
					 		} 
					 	}
					 	
					 ]}, 
					 { display: '凭证号', name: 'vouch_no', align: 'left',width:150,isSort:false,
						 render : function(rowdata, rowindex, value) {
							 if(rowdata.vouch_id !=null){
								 return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"</div></a>"; 
							 }
							 
          				}
					 },
					 
					 { display: '摘要', align: 'center', name:'summary',align: 'left',width:300,isSort:false},
					 { display: '对方科目', align: 'left', name:'subj_name',width:550,isSort:false},
					 {display: '借方', name: 'debit', align: 'right',formatter:'###,##0.00',width:120,isSort:false,
						 render : function(rowdata, rowindex, value) {
							 if(typeof(value) == 'undefined'){
								 return "";
							 } else if (rowdata.debit ==0){
								 return "";
							 } else{ 
								 return formatNumber(rowdata.debit, 2, 1);
								 }
							 }
					 }, 
					 {display: '贷方', name: 'credit', align: 'right',formatter:'###,##0.00',width:120,isSort:false,
						 render : function(rowdata, rowindex, value) {
							 if(typeof(value) == 'undefined'){
								 return "";
							 }else if (rowdata.credit == 0){
								 return "";
							 } else{
								 return formatNumber(rowdata.credit, 2, 1);}
							 }
					 },
					 {display: '方向', name: 'subj_dire', align: 'right',width:60,isSort:false
					 },
					 {display: '余额', name: 'end_os', align: 'right',formatter:'###,##0.00',width:120,isSort:false,render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.end_os, 2, 1);}}},
					 
				],
				dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccSubjLederDetail.do?isCheck=fasle&subj_id='+'${subj_code}'+'&acc_year='+'${acc_year}'+'&acc_month='+'${acc_month}'+'&year_month='+'${year_month}'+'&state='+'${state}',
				width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:false,selectRowButtonOnly:true,heightDiff: 25,enabledSort:false,
				toolbar: { items: [
									{ text: '打印', id:'print', click: printBtn,icon:'print' },
									{ line:true },
									{ text: '打印维护', id:'settings', click: myPrintSet,icon:'settings' },
									{ line:true },
									{ text: '关闭', id:'close', click: this_close,icon:'close' }
		    				]}
			});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function this_close(){
    	frameElement.dialog.close();
    }
    
	function loadHotkeys(){//键盘事件
		
		//hotkeys('Q',queryBtn);

		hotkeys('P',printBtn);
		 
	}
  
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
	
    function queryBtn(){//查询
    	
    	query();
    	
    }
    
	function printBtn(){//打印
      	
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    
		var yearMonthB='${year_month_b}';
		var state='${state}';
      	var selPara={
      			subj_id:'${subj_code}',
      			acc_year:'${acc_year}',
      			acc_month:'${acc_month}',
      			year_month:'${year_month}',
      			state:state
      	};
    	
    	selPara["template_code"]="01005";
       	selPara["class_name"]="com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService";
       	selPara["method_name"]="queryAccSubjLederDetailPrint";
       	selPara["bean_name"]="accSubjLedgerService";
       
       	if(yearMonthB!=""){
       		selPara["p_acc_year"]=yearMonthB.split(".")[0];	
       	}
       	selPara["p_year_month_b"]=yearMonthB;
       	selPara["p_year_month_e"]='${year_month_e}';
       	selPara["p_subj_code"]='${subj_code}';
       	selPara["p_subj_name"]=$("#subj_code").val();
       	selPara["p_cur_code"]="";
       	var isAccStr="不含未记账";
       	if(state==1){
       		isAccStr="包含未记账";
    	}
       	selPara["p_is_acc"]=isAccStr;
       	
       	officeTablePrint(selPara);
    	
   		//console.log(grid)
   	/* 	var printPara={
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
    
	function loadDict(){//字典下拉框

    	autocomplete("#cur_code","../acc/queryCur.do?isCheck=false","id","text",true,true,'',true);
    
    	$("#subj_code").ligerTextBox({width:200,disabled: true});   
    	
   	 	$("#subj_code").val('${subj_code}'+" "+'${subj_name}');
   	 
    	
	}
    
	//打印维护
	 function myPrintSet(){
		 officeFormTemplate({template_code:"01005"});
	 }
    </script>

</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科　　目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code"  type="text" id="subj_code" ltype="text" disabled="disabled"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">币种：</td>
            <td align="left" class="l-table-edit-td" ><input name="cur_code" type="text" id="cur_code" ltype="text"  /></td>
            <td align="left"></td>
            
            <!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>会计期间：</td>
            <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text"  /></td>
            <td align="left"></td> -->
        </tr> 
    </table>
    
    <div id="maingrid"></div>

	
</body>
</html>
