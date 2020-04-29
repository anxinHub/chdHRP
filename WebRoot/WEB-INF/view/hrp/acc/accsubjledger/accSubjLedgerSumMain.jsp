<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js"></script>
<link rel="stylesheet" href="<%=path%>/lib/et_components/etCheck/css/icheck.css">
<script src="<%=path%>/lib/et_components/etCheck/js/icheck.js"></script>
<script src="<%=path%>/lib/et_components/etCheck/etCheck.js"></script>
<script type="text/javascript">

    var grid, acc_year_month1, acc_year_month2;
    
    var is_state_manager;
    
    var gridManager = null;
    var userUpdateStr;
    var subj_box_data = "";
    var check_tree="";
    var vouch_no=""; 
    var vouch_num="";
    
    var year_month1, year_month2;
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
    	 
    	var subj_code = liger.get("subj_code").getValue().split(".")[1];
		var sch_id = liger.get("sch_id").getValue();
    	
        year_month1= acc_year_month1.getValue();
        year_month2 = acc_year_month2.getValue();
        if(year_month1=="" || year_month2 == ""){
			$.ligerDialog.error('会计期间为必填项，不能为空！');
			return;
		}
    	grid.options.parms.push({name:'acc_year_month_b',value: year_month1}); 
        grid.options.parms.push({name:'acc_year_month_e',value: year_month2}); 
        grid.options.parms.push({name:'acc_year_b',value: year_month1.split(".")[0]}); 
        grid.options.parms.push({name:'acc_year_e',value: year_month2.split(".")[0]}); 
        grid.options.parms.push({name:'sch_id',value: sch_id}); 
       	grid.options.parms.push({name:'subj_code',value: subj_code}); 
       	
       	grid.options.parms.push({ name : 'vouch_no_b', value : $("#vouch_no_b").val() });
		grid.options.parms.push({ name : 'vouch_no_e', value : $("#vouch_no_e").val() });
		
		grid.options.parms.push({name: 'isLastChk', value: $("#isLastChk").prop("checked") ? 1 : 0});

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
					 { display: '本期借方', name: 'debit', align: 'right',width:'200',formatter:'###,##0.00',
						 render : function(rowdata, rowindex, value) {
							 if(typeof(value) == 'undefined'){
								 return "";
							 }else{ 
								 return formatNumber(rowdata.debit, 2, 1);}
						 }
					 },
					 { display: '本期贷方', name: 'credit', align: 'right',width:'200',formatter:'###,##0.00',
						 render : function(rowdata, rowindex, value) {
							 if(typeof(value) == 'undefined'){
								 return "";
							 }else{ 
								 return formatNumber(rowdata.credit, 2, 1);}
						 }
					 }
				],
				dataAction: 'server',dataType: 'server',usePager:true,url:'collectAccSubjLedgerSumMain.do',
				width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,selectRowButtonOnly:true,heightDiff: -4,
				pageSizeOptions:[100, 200, 500], pageSize: 100,
				toolbar: { items: [
									{ text: '查询',id:'search', click: queryBtn ,icon:'search'},
									{ line:true },
									{ text: '打印', id:'print', click: printBtn,icon:'print' },
									{ line:true },
									{ text: '模板打印', id:'print', click: printModBtn,icon:'print' },
									{ line:true },
									{ text: '打印维护', id:'settings', click: myPrintSet,icon:'settings' },
									{ line:true }
		    				]} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
 	//打印维护
	function myPrintSet(){
		officeFormTemplate({template_code:"01038"});
	}
	
	//模板打印
	function printModBtn(){
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
	    selPara["p_year_month_b"]=year_month1.split(".")[0]+"年"+year_month1.split(".")[1]+"月";
	    selPara["p_year_month_e"]=year_month2.split(".")[0]+"年"+year_month2.split(".")[1]+"月";;
	    selPara["p_subj_code"]=liger.get("subj_code").getText();
	    selPara["p_vouch_no"]= $("#vouch_no").val();
	    selPara["p_vouch_num"]= $("#vouch_num").val();
	       	
	    officeTablePrint(selPara);
	}
	
	//打印
	function printBtn(){
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
		
		var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"会计期间: "+year_month1+"至"+year_month2,"colspan":2,"br":true},
    	          {"cell":0,"value":"凭证范围: "+$("#vouch_no").val(),"colspan":3,"br":true},
    	          {"cell":0,"value":"凭证数: "+$("#vouch_num").val(),"colspan":4,"br":true} 
        	]};
		
		var foots={"rows": [
		    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":2,"br":false},
		    				{"cell":1,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":false},
		    				{"cell":2,"value":"打印日期: " + cur_date,"colspan":2,"br":false}
		     			]}
        	
        	var printPara={
          		title: "科目汇总表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService",
       			method_name: "queryAccVouchCountSumDetailPrint",
       			bean_name: "accSubjLedgerService" ,
       			heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots)
           	};
	    
	    $.each(grid.options.parms,function(i,obj){
	    	printPara[obj.name]=obj.value;
	    });
	 
	    officeGridPrint(printPara);
	}
	
 	
 
	function loadHotkeys(){//键盘事件
		hotkeys('Q',queryBtn);
		hotkeys('P',printBtn);
	}
  
    function queryBtn(){//查询
    	query();
    	var formPara={
        	acc_year : year_month1.split(".")[0], 
        	acc_month_b : year_month1,
        	acc_month_e : year_month2,
        	vouch_no_b : $("#vouch_no_b").val(),
        	vouch_no_e : $("#vouch_no_e").val(),
        	subj_codes : liger.get("subj_code").getValue().split(".")[1],
        	sch_id : liger.get("sch_id").getValue() 
        } 
        ajaxJsonObjectByUrl("querySubjLedgerSum.do?isCheck=false",formPara,function(responseData){
        	vouch_no = responseData.vouch_no;
        	vouch_num = responseData.vouch_num;
            $("#vouch_no").val(responseData.vouch_no);
            $("#vouch_num").val(responseData.vouch_num);
        });
    }
    
 
	function loadDict(){//字典下拉框

		$("#vouch_no_b").ligerTextBox({width:95} );
		
		$("#vouch_no_e").ligerTextBox({width:95} ); 

    	//autocomplete("#subj_code","../querySubjBylevel.do?isCheck=false","id","text",true,true,'',false,false,'215');
		autocompleteObj({
			id:  '#subj_code', 
			urlStr: '../querySubjBylevel.do?isCheck=false', 
			valueField:  'id', 
			textField:    'text', 
			autocomplete: true, 
			highLight: true, 
			defaultSelect:  false, 
			initWidth: 216, 
			boxwidth: subjWidth, 
			textBoxKeySpace: function(value){
				showSubjTree({
					ligerId: "subj_code", 
					idStr: "subj_id", 
					splitStr: ".",
					acc_year: acc_year_month1.getValue().split(".")[0], 
					windowName: window.name
				});
			}
		});
		//------------------会计期间设置----begin-------------
    	//会计期间
		acc_year_month1 = $("#acc_year_month1").etDatepicker({
			range: false,
			view: "days",
			minView: "days",
			dateFormat: "yyyy.mm.dd",
			defaultDate: ['${yearMonth}']
		});
		acc_year_month2 = $("#acc_year_month2").etDatepicker({
			range: false,
			view: "days",
			minView: "days",
			dateFormat: "yyyy.mm.dd",
			defaultDate: ['${yearMonth}']
		});
		 
   	 	//------------------会计期间设置----end-------------
		$("#vouch_no").ligerTextBox({width:215,disabled: true});
		$("#vouch_num").ligerTextBox({width:216,disabled: true});
		
		//方案下拉框
		autocomplete("#sch_id", "../queryAccBookSch.do?isCheck=false", "id", "text", true, true, {page: 'KMHZB'}, false, false, "", "", subjWidth);

     	
		var isLastChk=Local.get("acc[accsubjledger[accSubjLedgerSumMain[isLastChk");
		if(isLastChk==null || isLastChk=="true"){
			isLastChk=true;
		}else{
			isLastChk=false;
		}
		
		$("#isLastChk").etCheck({
			checked :isLastChk,
			ifChanged: function (status, checked, disabled) {
				Local.set("acc[accsubjledger[accSubjLedgerSumMain[isLastChk",checked);
			}
		});
	}
    
     //方案设置
	 function subjIntercalate(){
		 parent.$.ligerDialog.open({
				title : '方案设置',
				width : $(window).width()-100,
				height : $(window).height(),
				url : 'hrp/acc/accbooksch/accBookSchMainPage.do?isCheck=false&page=KMHZB',
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

<input type="hidden" id="subj_flag" name="subj_flag"/> 
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>会计期间：</td>
            <td><table><tr>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="acc_year_month1" type="text" 
						id="acc_year_month1" ltype="text" style="width: 95px;" />
				</td>
				<td align="left" class="l-table-edit-td">至</td>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="acc_year_month2" type="text" 
						id="acc_year_month2" ltype="text" style="width: 95px;" />
				</td>
				<td align="left"></td>
			</tr></table></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科&nbsp;&nbsp;目：</td>
            <td align="left" class="l-table-edit-td">
            	<table>
					<tr>
						<td>
							<input name="subj_code" type="text" id="subj_code" ltype="text" />
						</td>
						<td align="left" class="l-table-edit-td"><input style="vertical-align: middle" name="isLastChk" type="checkbox" id="isLastChk" ltype="text" />&nbsp;含非末级</td>
					</tr>
				</table>
            </td>
            <td align="left" class="l-table-edit-td"></td>
        </tr>
        <tr>  
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">方&nbsp;&nbsp;案：</td> 
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input name="sch_id" type="text" id="sch_id" ltype="text" /></td>
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="l-button l-button-test"  type="button" value="设置" onclick="subjIntercalate();" style="width: 48px;"/>
						</td>
					</tr>
				</table>
			</td>
			
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">凭证号：</td>
            <td align="left" class="l-table-edit-td">
			 <table><tr>
				<td align="left">
					<input name="vouch_no_b" type="text"  id="vouch_no_b" ltype="text" style="width: 95px;" />
				</td>
				<td align="left" class="l-table-edit-td">至</td>
				<td align="left" class="l-table-edit-td">
					<input  name="vouch_no_e" type="text" id="vouch_no_e" ltype="text" style="width: 95px;" />
				</td>
				<td align="left"></td>
			</tr></table></td>
		</tr>
		<tr>
			
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭证范围：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="vouch_no" type="text" id="vouch_no" ltype="text"  />
            </td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">包&nbsp;&nbsp;括：</td>
            <td align="left" class="l-table-edit-td"   colspan="4">
            	<input name="vouch_num" type="text" id="vouch_num" ltype="text"  />
            </td>
        </tr>
    </table>
    
    <div id="maingrid"></div>

</body>
</html>
