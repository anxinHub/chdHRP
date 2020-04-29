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
    
    var check_type_id1="";
    
    var check_type_id2="";
    
    var check_type_id3="";
    
    var check_type_id4="";
    
    $(function ()
    {
    	loadColumn();
		loadDict();
		
    	loadHead(null);	//加载数据
    	
    	loadHotkeys();
    	
    });

    var columns;
    function loadColumn(){
    	columns= "{ display: '摘要', name: 'summary', align: 'left',hide:true},";
    	ajaxJsonObjectByUrl("accleder/getSubjItemTitle.do?isCheck=false",{'subj_id':'${subj_id}','subj_code':'${subj_code}'},function(data){
	 		if( data != "" && data!=null){
	 	 		if(data[0].check_type_name1 != null && data[0].check_type_name1 != ""){
	 	 			check_type_id1=data[0].check_type_id1;
	 	 			columns = columns + "{ display: '"+data[0].check_type_name1+"', name: 'check1',align: 'left',minWidth :300"
	 				+"},";
	 	 		}
	 	 			
	 	 		if(data[0].check_type_name2 != null && data[0].check_type_name2 != ""){
	 	 			check_type_id2=data[0].check_type_id2;
	 	 			columns = columns + "{ display: '"+data[0].check_type_name2+"', name: 'check2', align: 'left',minWidth :300"
	 				+"},";
	 	 		}
	 	 			
	 	 		if(data[0].check_type_name3 != null && data[0].check_type_name3 != ""){
	 	 			check_type_id3=data[0].check_type_id3;
	 	 			columns = columns + "{ display: '"+data[0].check_type_name3+"', name: 'check3', align: 'left',minWidth :300"
	 				+"},";
	 	 		}
	 	 			
	 	 		if(data[0].check_type_name4 != null && data[0].check_type_name4 != ""){
	 	 			check_type_id4=data[0].check_type_id4;
	 	 			columns = columns + "{ display: '"+data[0].check_type_name4+"', name: 'check4',align: 'left',minWidth :300"
	 				+"},";
	 	 		}
	 		}
	 			
	 		columns = columns + "{ display: '方向', name: 'subj_dire', width:80,align: 'right',hide:true,render:function(rowdata){if(rowdata.subj_dire==0) {return '借'} else if(rowdata.subj_dire==1) {'贷'}}},"
	 			+"{ display: '期初余额', name: 'bal_os', width:150,align: 'right',formatter:'###,##0.00',render:function(rowdata){return formatNumber(rowdata.bal_os, 2, 1)}},"
	 			+"{ display: '本期借方', name: 'lj_debit', width:150,align: 'right',formatter:'###,##0.00',render:function(rowdata){return formatNumber(rowdata.lj_debit, 2, 1)}},"
	 			+"{ display: '本期贷方', name: 'lj_credit', width:150,align: 'right',formatter:'###,##0.00',render:function(rowdata){return formatNumber(rowdata.lj_credit, 2, 1)}},"
	 			+"{ display: '期末余额', name: 'end_os', width:150,align: 'right',formatter:'###,##0.00',render:function(rowdata){ return formatNumber(rowdata.end_os, 2, 1)}}";
	 			
			
       
	 	 },false); 
    }
    
    function loadHead(){
    	
 			grid = $("#maingrid").ligerGrid({
 	        	columns: eval("["+columns+"]"),
				dataAction: 'server',dataType: 'server',usePager:false,
				url:'queryAccSubjLederCkeck.do?isCheck=fasle&subj_id='+'${subj_id}'+'&subj_code='+'${subj_code}'+'&acc_month='+'${acc_month}'+'&year_month='+'${year_month}'+'&state='+'${state}'+'&acc_year='+'${acc_year}'
				+'&check_type_id1='+check_type_id1+'&check_type_id2='+check_type_id2+'&check_type_id3='+check_type_id3+'&check_type_id4='+check_type_id4,
				width: '100%', height: '100%', checkbox: false,
				rownumbers:true,delayLoad:false,
				selectRowButtonOnly:true,heightDiff: 25,
				toolbar: { items: [
								{ text: '打印', id:'print', click: printBtn,icon:'print' },
								{ line:true },
								{ text: '明细', id:'queryDetail', click: queryDetail,icon:'search' },
								{ line:true }, 
								{ text: '关闭', id:'close', click: this_close,icon:'close' },
								{ line:true }
	    		]}
			});

        	gridManager = $("#maingrid").ligerGetGridManager();
	 	
    }
    
    function queryDetail(){
    	var row = gridManager.getSelectedRow();
    	
        var para="&subj_id="+'${subj_id}'+"&subj_code="+'${subj_code}'+"&subj_name="+'${subj_name}'+"&acc_year="+'${acc_year}'+"&acc_month="+'${acc_month}'+"&year_month="+'${year_month}'+"&state="+'${state}'+'&value1='+row.value1+'&value2='+row.value2+'&value3='+row.value3+'&value4='+row.value4;
        parent.openFullDialog('hrp/acc/accSubjLederCheckDetailPage.do?isCheck=fasle'+para,'辅助核算明细',0,0,true,true);
        
    } 
    
    
    function this_close(){
    	frameElement.dialog.close();
    }
	function loadHotkeys(){//键盘事件
		
		hotkeys('P',printBtn);
		hotkeys('L',this_close); 
	}
	
	
  
	function printBtn(){//打印
      	
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    
		var heads={
		  		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  		  "rows": [
			          {"cell":0,"value":"科目："+"${subj_code}${subj_name}"} 
		  		  ]
		  	};  
			 
		 		var printPara={
		 			title: "辅助核算",//标题
		 			columns: JSON.stringify(grid.getPrintColumns()),//表头 //数据格式化
		 			class_name: "com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService",
					method_name: "queryAccSubjLederCheckPrint",
					bean_name: "accSubjLedgerService",
					subj_id:"${subj_id}",
					subj_code:"${subj_code}", 
					acc_month:"${acc_month}",
					year_month:"${year_month}",
					state:"${state}",
					acc_year:"${acc_year}",
					check_type_id1:check_type_id1,
					check_type_id2:check_type_id2,
					check_type_id3:check_type_id3,
					check_type_id4:check_type_id4,
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

    	autocomplete("#cur_code","../acc/queryCur.do?isCheck=false","id","text",true,true,'',true);
    
		$("#subj_code").ligerTextBox({width:200,disabled: true});   
    	
   	 	//liger.get("subj_code").setText('${subj_code}'+" "+'${subj_name}');
   	 	
   	 	$("#subj_code").val('${subj_code}'+" "+'${subj_name}');
	
    	//autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true,'',false,false,'215');
    	
    	//autocomplete("#subj_level","../querySubjLevel.do?isCheck=false","id","text",true,true);
    	
    	//-----------------会计期间设置-----------------
    	
    	/* autocomplete("#acc_year_month1","../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');
    	
    	autocomplete("#acc_year_month2","../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,'90');
    	
    	var year_Month = '${yearMonth}';

   	 	liger.get("acc_year_month1").setValue(year_Month);
		 
   	 	liger.get("acc_year_month1").setText(year_Month);
   	 
   	 	liger.get("acc_year_month2").setValue(year_Month);
		 
   	 	liger.get("acc_year_month2").setText(year_Month);
   	 	 */
	}
    
	
    </script>

</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
           
    <table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科　　目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" disabled="disabled" /></td>
            <td align="left"></td>
            
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">币种：</td>
            <td align="left" class="l-table-edit-td" ><input name="cur_code" type="text" id="cur_code" ltype="text"  /></td>
            <td align="left"></td>
            
           <!--  <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>会计期间：</td>
            <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text"  /></td>
            <td align="left"></td> -->
        </tr> 
    </table>
    
    <div id="maingrid"></div>

	
</body>
</html>
