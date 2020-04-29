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
    var is_show = 0;
    
    $(function (){
		loadDict();
    	loadHead(null);	//加载数据
    	if('${accPara043}'==1){
			
			 grid.set("groupColumnName", "subj_name");
			 grid.set("groupColumnDisplay", "科目");
		}else {
			
			 grid.set("groupColumnName", "");
			 grid.set("groupColumnDisplay", "");
		}
    });
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	var year_month1 = acc_year_month1.getValue();
		var year_month2 = acc_year_month2.getValue();
        
		var subj_code = liger.get("subj_code").getValue().split(".")[1];
		var sch_id = liger.get("sch_id").getValue();
		if(year_month1=="" || year_month2 == ""){
			$.ligerDialog.error('会计期间为必填项，不能为空！');
			return;
		}
		
		if(subj_code==""){
			$.ligerDialog.error('科目为必填项，不能为空！');
			return;
		}
		
		grid.options.parms.push({name:'acc_year_b',value: year_month1.split(".")[0]}); 
        grid.options.parms.push({name:'acc_month_b',value: year_month1.split(".")[1]}); 
        grid.options.parms.push({name:'acc_year_e',value: year_month1.split(".")[0]}); 
        grid.options.parms.push({name:'acc_month_e',value: year_month2.split(".")[1]}); 
       	grid.options.parms.push({name:'sch_id',value: sch_id}); 
       	grid.options.parms.push({name:'subj_code',value: subj_code}); 

    	grid.options.parms.push({name: 'isLastChk', value: $("#isLastChk").prop("checked") ? 1 : 0});
		
        //加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '期间',  align: 'center',columns:[{
                    	 display: '年', name: 'acc_year', align: 'left',width:'4%',isSort:false
                     },{
                    	 display: '月', name: 'acc_month', align: 'left',width:'2%',isSort:false
                     }]
					 },
					 { display: '科目', name: 'subj_name', align: 'left',width:'20%',isSort:false
					 },
					 { display: '摘要', name: 'summary', align: 'left',width:'20%',isSort:false,
						 render:function(rowdata, rowindex, value){
						 return rowdata.summary=='累计'?'累&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计':rowdata.summary;
					 }
					 },
					 {display: '借方', name: 'debit', align: 'right',width:'10%',formatter:'###,##0.00',isSort:false,
                    	 render : function(rowdata, rowindex, value) {
                    		 if(rowdata.debit == 0){return formatNumber(rowdata.debit, 2, 1);}
                    		 else{ return formatNumber(rowdata.debit, 2, 1);}
          				}
					 },
					 {display: '贷方', name: 'credit', align: 'right',width:'10%',formatter:'###,##0.00',isSort:false,
                    	 render : function(rowdata, rowindex, value) {
                    		 if(rowdata.credit == 0){return formatNumber(rowdata.credit, 2, 1);}
                    		 else{ return formatNumber(rowdata.credit, 2, 1);}
          				}
					 },
					 { display: '方向', name: 'subj_dire', align: 'left',width:'2%',isSort:false
					 },
                     { display: '余额', name: 'end_os',  align: 'right',width:'10%',formatter:'###,##0.00',isSort:false,
						 render : function(rowdata, rowindex, value) {
							 if(rowdata.end_os==0){return "Q";}
							 else{return formatNumber(rowdata.end_os, 2, 1);}
	         				}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'collectAccSubjLedger.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,pageSizeOptions:[100, 200, 500], pageSize: 100,
                     groupColumnName:'subj_name',groupColumnDisplay:'科目',
                     toolbar: { items: [
							{ text: '查询',id:'search',icon:'search', click: query },
							{ line:true } , 
							{ text: '打印', id:'print', click: print_btn,icon:'print' },
							{ line:true },
							{ text: '打印维护', id:'settings', click: myPrintSet,icon:'settings' },
							{ line:true }
					]},
					lodop:{
    		    		title:"科目总账",
    					fn:{
        	    			debit:function(value){//借方
        	    				if(value == 0){return "";}
        	           			else{return formatNumber(value, 2, 1);}
        	    			},
        	    			credit:function(value){//贷方
        	    				if(value == 0){return "";}
        	          			else{return formatNumber(value, 2, 1);}
        	   				},
        	   				end_os:function(value){//余额
        		   				 if(value==0){return "Q";}
        						 else{return formatNumber(value, 2, 1);}
        	  				}
        	    		}
    				}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

  	//打印回调方法
    function lodopPrint(){
    	var accStr="不包含未记账"
       	/* if($("#is_state").prop("checked")){
       		accStr="包含未记账"
       	} */
    	var head="<table class='head' width='100%'><tr><td>会计期间："+liger.get("acc_year_month1").getText()+"至"+liger.get("acc_year_month2").getText()+"</td>";
 		head=head+"<td align='right'>"+liger.get("cur_code").getText()+"</td></tr>";
 		head=head+"<tr><td>科目："+liger.get("subj_code").getText()+"</td><td align='right'>"+accStr+"</td></tr></table>";
 		grid.options.lodop.head=head;
    }
    
    function print_btn(){  
    	var year_month1 = acc_year_month1.getValue();
    	var year_month2 = acc_year_month2.getValue();
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var selPara={};
       	$.each(grid.options.parms,function(i,obj){
       		selPara[obj.name] = obj.value;
       	});
       	selPara["template_code"] = "01006";
       	selPara["class_name"] = "com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService";
       	selPara["method_name"] = "collectAccSubjLedgerPrint";
       	selPara["bean_name"] = "accSubjLedgerService";
       	if( acc_year_month1.getValue()!=""){
       		selPara["p_acc_year"] = year_month1.split(".")[0];
       	}
       	selPara["p_year_month_b"] = year_month1;
       	selPara["p_year_month_e"] = year_month2;
       	selPara["p_subj_code"]=liger.get("subj_code").getText().split(" ")[0];
       	selPara["p_subj_name"]=liger.get("subj_code").getText().split(" ")[1];
       	/* selPara["p_cur_code"]=liger.get("cur_code").getText();
       	var isAccStr="不含未记账";
       	if($("#is_state").is(":checked")){ 
       		isAccStr="包含未记账";
    	}
       	selPara["p_is_acc"]=isAccStr; */
       	
       	officeTablePrint(selPara);
    	
    }
    
    function loadDict(){
        //字典下拉框
        autocomplete("#sch_id", "../queryAccBookSch.do?isCheck=false", "id", "text", true, true, {page: "KMZZAY"}, false, false, "", "", subjWidth);
    	var count = 0;
    	$("#subj_code").ligerComboBox({
    		url: "../querySubjBylevel.do?isCheck=false",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: '400',
    		selectBoxHeight:'260', 
    		setTextBySource: true,
    		width: '180',
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: true,
    		alwayShowInDown: true, /*
    	    onSuccess: function (data) {
    			if (count == 0) {this.setValue(data[0].id);}
    			count++;
    		}   */
			onTextBoxKeySpace: function(value){
				showSubjTree({
					ligerId: "subj_code", 
					idStr: "subj_id", 
					splitStr: ".",
					acc_year: acc_year_month1.getValue().split(".")[0], 
					windowName: window.name
				});
			}
		});
    	
    	//会计期间
     	acc_year_month1 = $("#acc_year_month1").etDatepicker({
    		range: false,
            view: "months",
            minView: "months",
            dateFormat: "yyyy.mm",
            defaultDate: ['${yearMonth}']
   		});
     	acc_year_month2 = $("#acc_year_month2").etDatepicker({
    		range: false,
            view: "months",
            minView: "months",
            dateFormat: "yyyy.mm",
            defaultDate: ['${yearMonth}']
   		});
     	
		var isLastChk=Local.get("acc[accsubjledger[accSubjLedgerMain[isLastChk");
		if(isLastChk==null || isLastChk=="true"){
			isLastChk=true;
		}else{
			isLastChk=false;
		}
		
		$("#isLastChk").etCheck({
			checked :isLastChk,
			ifChanged: function (status, checked, disabled) {
				Local.set("acc[accsubjledger[accSubjLedgerMain[isLastChk",checked);
			}
		});
    } 
    
  	//打印数据
	 function printDate(){
  		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterAccTable("resultPrint","开始打印","科目总账(按月)",true);
			return;
		}
		
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
		
		var is_state = 1;
    	if($("#is_state").attr("checked") == true){
    		is_state = 99;
    	}
    	
    	//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		var printPara={
			usePager:false,
			acc_year_b:year_month1.split(".")[0],
			acc_month_b:year_month1.split(".")[1],
			acc_year_e:year_month2.split(".")[0],
	        acc_month_e:year_month2.split(".")[1],
	        subj_code:subj_code.split(".")[1],
	       // cur_code:liger.get("cur_code").getValue(),
	        state:is_state
        };
		
		ajaxJsonObjectByUrl("collectAccSubjLedger.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 if(item.acc_year==null){
					 trHtml+="<td> </td>"; 
				 }else{
					 trHtml+="<td>"+item.acc_year+"</td>"; 
				 }
				 if(item.acc_month==null){
					 trHtml+="<td> </td>"; 
				 }else{
					 trHtml+="<td>"+item.acc_month+"</td>";  
				 }
				 trHtml+="<td>"+item.summary+"</td>"; 
				 trHtml+="<td align='right'>"+formatNumber(item.debit, 2, 1)+"</td>"; 
				 trHtml+="<td align='right'>"+formatNumber(item.credit, 2, 1)+"</td>"; 
				 if(item.subj_dire==0){
					 trHtml+="<td>借</td>"; 
				 }else{
					 trHtml+="<td>贷</td>"; 
				 }
				 trHtml+="<td align='right'>"+formatNumber(item.end_os, 2, 1)+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			
			var subj_name = $("#subj_code").val();
			
			lodopPrinterAccTable("resultPrint","开始打印","科目总账(按月)",true,year_month1+","+year_month1,subj_name);
			
	    },true,manager);
		return;
	 }
	
	//方案设置
	 function subjIntercalate(){
		 parent.$.ligerDialog.open({
				title : '方案设置',
				width : $(window).width()-100,
				height : $(window).height(),
				url : 'hrp/acc/accbooksch/accBookSchMainPage.do?isCheck=false&page=KMZZAY',
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
	 
	 //打印维护
	 function myPrintSet(){
		 officeFormTemplate({template_code:"01006"});
	 }
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">

<input type="hidden" id="subj_flag" name="subj_flag"/> 
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>会计期间：</td>
            <td><table><tr>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="acc_year_month1" type="text" 
						id="acc_year_month1" ltype="text" style="width: 75px;" />
				</td>
				<td align="left" class="l-table-edit-td">至</td>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="acc_year_month2" type="text" 
						id="acc_year_month2" ltype="text" style="width: 75px;" />
				</td>
				<td align="left"></td>
			</tr></table></td>
			<td align="right" class="l-table-edit-td" style="padding-left:20px;"><!-- <font size="2" color="red">*</font> -->科目：</td>
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
            
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">方案：</td> 
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input name="sch_id" type="text" id="sch_id" ltype="text" /></td>
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="l-button l-button-test"  type="button" value="设置" onclick="subjIntercalate();" style="width: 45px;"/>
						</td>
					</tr>
				</table>
			</td>
		</tr> 
		
    </table>

	<div id="maingrid"></div>

</body>
</html>
