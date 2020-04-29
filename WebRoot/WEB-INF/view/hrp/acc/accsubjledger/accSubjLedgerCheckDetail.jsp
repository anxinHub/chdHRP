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
		loadDict();
    	loadHead(null);	//加载数据
    	loadHotkeys();
    });

    function loadHead(){
    	var columns = "{display:'年度',align:'left',columns:[{display:'月',name:'acc_month',width:'80',align:'left',render:function(rowdata,rowindex,value){return rowdata.acc_month;}},{display:'日',name:'acc_day',width:'80',align:'left',render:function(rowdata,rowindex,value){return rowdata.acc_day;}}]},"
	 	 $.post("accleder/getSubjItemTitle.do?isCheck=false",{'subj_id':'${subj_id}','subj_code':'${subj_code}'},function(data){
	 		var url;
	 		if( data != "" && data!=null){
	 	 		if(data[0].check_type_name1 != null && data[0].check_type_name1 != ""){
	 	 			url = getRequestURL(data[0].column_check1.toLowerCase());
	 	 			check_type_id1=data[0].check_type_id1;
	 	 			columns = columns + "{ display: '"+data[0].check_type_name1+"', name: 'value1',width:200,align: 'left',valueField : '"+data[0].check1.toLowerCase()+"',textField: 'check1',editor: { type: 'select',keySupport : true, autocomplete : true, url:'"+url+"',valueField : '"+data[0].check1.toLowerCase()+"',textField: '"+data[0].column_check1.toLowerCase()+"' }"
	 				+"},";
	 	 			para = data[0].column_item1.toLowerCase();
	 	 		}
	 	 			
	 	 		if(data[0].check_type_name2 != null && data[0].check_type_name2 != ""){
	 	 			url = getRequestURL(data[0].column_check2.toLowerCase());
	 	 			check_type_id2=data[0].check_type_id2;
	 	 			columns = columns + "{ display: '"+data[0].check_type_name2+"', name: 'value2', width:200,align: 'left',valueField : '"+data[0].check2.toLowerCase()+"',textField:  'check2',editor: { type: 'select',keySupport : true, autocomplete : true, url:'"+url+"',valueField : '"+data[0].check2.toLowerCase()+"',textField: '"+data[0].column_check2.toLowerCase()+"' }"
	 				+"},";
	 	 			para =para+";"+data[0].column_item2.toLowerCase();
	 	 		}
	 	 			
	 	 		if(data[0].check_type_name3 != null && data[0].check_type_name3 != ""){
	 	 			url = getRequestURL(data[0].column_check3.toLowerCase());
	 	 			check_type_id3=data[0].check_type_id3;
	 	 			columns = columns + "{ display: '"+data[0].check_type_name3+"', name: 'value3', width:200,align: 'left',valueField : '"+data[0].check3.toLowerCase()+"',textField: 'check3',editor: { type: 'select',keySupport : true, autocomplete : true, url:'"+url+"',valueField : '"+data[0].check3.toLowerCase()+"',textField: '"+data[0].column_check3.toLowerCase()+"' }"
	 				+"},";
	 	 			para =para+";"+data[0].column_item3.toLowerCase();
	 	 		}
	 	 			
	 	 		if(data[0].check_type_name4 != null && data[0].check_type_name4 != ""){
	 	 			url = getRequestURL(data[0].column_check4.toLowerCase());
	 	 			check_type_id4=data[0].check_type_id4;
	 	 			columns = columns + "{ display: '"+data[0].check_type_name4+"', name: 'value4',width:200,align: 'left',valueField : '"+data[0].check4.toLowerCase()+"',textField: 'check4',editor: { type: 'select',keySupport : true, autocomplete : true, url:'"+url+"',valueField : '"+data[0].check4.toLowerCase()+"',textField: '"+data[0].column_check4.toLowerCase()+"' }"
	 				+"},";
	 	 			para =para+";"+data[0].column_item4.toLowerCase();
	 	 		}
	 		}
	 		
	 		columns = columns 
	 			+"{ display: '凭证号', name: 'vouch_no', align: 'left',width:'100',render : function(rowdata, rowindex, value) {if(rowdata.vouch_no !=null && rowdata.vouch_no !='-'){return '<a href=javascript:openSuperVouch('+rowdata.vouch_id+')>'+rowdata.vouch_no+'</a>'; }}},"
	 			+"{ display: '摘要', name: 'summary', align: 'left',width:'350'},"
	 			+"{ display: '对方科目', name: 'subj_name', align: 'left',width:'250' },"
	 			+"{ display: '借方', name: 'debit',width:'120', align: 'right',render:function(rowdata){return formatNumber(rowdata.debit, 2, 1)}},"
	 			+"{ display: '贷方', name: 'credit',width:'120', align: 'right',render:function(rowdata){return formatNumber(rowdata.credit, 2, 1)}},"
	 			+"{ display: '方向', name: 'subj_dire', width:'60',align: 'right',render:function(rowdata){if(rowdata.subj_dire==0) {return '借'} else if(rowdata.subj_dire==1) {'贷'}}},"
	 			+"{ display: '期末余额', name: 'end_os', width:'120',align: 'right',render:function(rowdata){return formatNumber(rowdata.end_os, 2, 1)}}";
	 			grid = $("#maingrid").ligerGrid({
	 	        	columns: eval("["+columns+"]"),
					dataAction: 'server',dataType: 'server',usePager:false,
					url:'queryAccSubjLedgerCheckDetail.do?isCheck=fasle&subj_id='+'${subj_id}'+'&subj_code='+'${subj_code}'+'&subj_name='+'${subj_name}'+'&acc_year='+'${acc_year}'+'&acc_month='+'${acc_month}'+'&year_month='+'${year_month}'+'&state='+'${state}'+'&value1='+'${value1}'+'&value2='+'${value2}'+'&value3='+'${value3}'+'&value4='+'${value4}'
					+'&check_type_id1='+ check_type_id1 +'&check_type_id2='+ check_type_id2 +'&check_type_id3='+ check_type_id3 +'&check_type_id4='+ check_type_id4,
					width: '100%', height: '100%', checkbox: false,
					rownumbers:true,delayLoad:false,
					selectRowButtonOnly:true,heightDiff: 25,
					toolbar: { items: [
									{ text: '打印', id:'print', click: printBtn,icon:'print' },
									{ line:true },
									{ text: '关闭', id:'close', click: this_close,icon:'close' },
									{ line:true }
		    		]}
			});
        	gridManager = $("#maingrid").ligerGetGridManager();
	 	 },"json"); 
    }
    
    function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
    
    function this_close(){
    	frameElement.dialog.close();
    }
    
	function loadHotkeys(){//键盘事件
		hotkeys('P',printBtn);
		hotkeys('L',this_close); 
	}
	
	function getRequestURL(check_code){
		
		var url;
		if (check_code == "dept_name"){
			url = "queryDeptDict.do?isCheck=false";
		}else if(check_code == "emp_name"){
			url = "queryEmp.do?isCheck=false";
		}else if(check_code == "proj_name"){
			url = "queryProjDictDict.do?isCheck=false";
		}else if(check_code == "store_name"){
			url = "queryStoreDictDict.do?isCheck=false";
		}else if(check_code == "cus_name"){
			url = "queryCusDict.do?isCheck=false";
		}else if(check_code == "sup_name"){
			url = "querySupDictDict.do?isCheck=false";
		}else if(check_code == "source_name"){
			url = "querySourceDict.do?isCheck=false";
		}else if(check_code == "hos_name"){
			url = "queryAccHosInfo.do?isCheck=false";
		}else{
			url = "queryCheckItem.do?isCheck=false";
		}
		
		return url;
	 }
  
	function printBtn(){//打印
      	
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    
		var heads={
		  		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  		  "rows": [
			          {"cell":0,"value":"科目："+"${subj_code}${subj_name}","colSpan":"2"} 
		  		  ]
		  	};  
			 
		 		var printPara={
		 			title: "辅助核算明细",//标题
		 			columns: JSON.stringify(grid.getPrintColumns()),//表头 //数据格式化
		 			class_name: "com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService",
					method_name: "queryAccSubjLedgerCheckDetailPrint",
					bean_name: "accSubjLedgerService",
					subj_id:"${subj_id}",
					subj_code:"${subj_code}", 
					subj_name:"${subj_name}",
					acc_year:"${acc_year}",
					acc_month:"${acc_month}",
					year_month:"${year_month}",
					state:"${state}",
					value1:"${value1}",
					value2:"${value2}",
					value3:"${value3}",
					value4:"${value4}",
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
   	 	$("#subj_code").val('${subj_code}'+" "+'${subj_name}');
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
           
        </tr> 
    </table>
    <div id="maingrid"></div>
	
</body>
</html>
