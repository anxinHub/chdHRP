<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
        $("#contract_no").ligerTextBox({width:160});
        $("#contract_type").ligerTextBox({width:160});
        $("#contract_ori_no").ligerTextBox({width:160});
        $("#contract_name").ligerTextBox({width:243});
        $("#ass_year").ligerTextBox({width:160});
        $("#ass_month1").ligerTextBox({width:100});
        $("#ass_month2").ligerTextBox({width:100});
        $("#ven_id").ligerTextBox({width:160});
        $("#sign_date1").ligerTextBox({width:100});
        $("#sign_date2").ligerTextBox({width:100});
        $("#buy_type").ligerTextBox({width:160});
        $("#emp_main").ligerTextBox({width:160});
        $("#provider").ligerTextBox({width:160});
        $("#is_bid").ligerTextBox({width:160});
        $("#create_date1").ligerTextBox({width:100});
        $("#create_date2").ligerTextBox({width:100});
        $("#check_date1").ligerTextBox({width:100});
        $("#check_date2").ligerTextBox({width:100});
        $("#state").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'contract_id',value:$("#contract_id").val()}); 
    	  grid.options.parms.push({name:'contract_no',value:$("#contract_no").val()}); 
    	  grid.options.parms.push({name:'contract_type',value:$("#contract_type").val()}); 
    	  grid.options.parms.push({name:'contract_ori_no',value:$("#contract_ori_no").val()}); 
    	  grid.options.parms.push({name:'contract_name',value:$("#contract_name").val()}); 
    	  grid.options.parms.push({name:'ass_year',value:$("#ass_year").val()}); 
    	  grid.options.parms.push({name:'ass_month1',value:$("#ass_month1").val()}); 
    	  grid.options.parms.push({name:'ass_month2',value:$("#ass_month2").val()}); 
    	  grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]});
    	  grid.options.parms.push({name:'sign_date1',value:$("#sign_date1").val()}); 
    	  grid.options.parms.push({name:'sign_date2',value:$("#sign_date2").val()}); 
    	  grid.options.parms.push({name:'buy_type',value:$("#buy_type").val()}); 
    	  grid.options.parms.push({name:'emp_main',value:liger.get("emp_main").getValue()}); 
    	  grid.options.parms.push({name:'provider',value:$("#provider").val()}); 
    	  grid.options.parms.push({name:'is_bid',value:$("#is_bid").val()}); 
    	  grid.options.parms.push({name:'create_date1',value:$("#create_date1").val()}); 
    	  grid.options.parms.push({name:'create_date2',value:$("#create_date2").val()}); 
    	  grid.options.parms.push({name:'check_date1',value:$("#check_date1").val()}); 
    	  grid.options.parms.push({name:'check_date2',value:$("#check_date2").val()}); 
    	  grid.options.parms.push({name:'state',value:$("#state").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [
                     { display: '合同号', name: 'contract_no', align: 'left',width: '110',frozen: true,
							render : function(rowdata, rowindex, value) {
								return "<a href=javascript:openUpdate('"
										+ rowdata.group_id   + "|" + 
										rowdata.hos_id   + "|" + 
										rowdata.copy_code   + "|" + 
										rowdata.contract_id   + "')>"+rowdata.contract_no+"</a>";
							}
					 		},
					 { display: '统计年度', name: 'ass_year', align: 'left',width: '60',frozen: true
						 		},
	                 { display: '统计月份', name: 'ass_month', align: 'left',width: '60',frozen: true
						 		},
                     { display: '合同类别', name: 'contract_type', align: 'left',width: '70',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.contract_type == 0){
										return "买卖合同";
									}else if(rowdata.contract_type == 1){
										return "赠与合同";
									}else if(rowdata.contract_type == 2){
										return "借款合同";
									}else if(rowdata.contract_type == 3){
										return "租赁合同";
									}else if(rowdata.contract_type == 4){
										return "融资租赁合同";
									}else if(rowdata.contract_type == 5){
										return "承揽合同";
									}else if(rowdata.contract_type == 6){
										return "建设工程合同";
									}
					 			}
					 		},
                     { display: '合同原始编号', name: 'contract_ori_no', align: 'left',width: '120'
					 		},
                     { display: '合同名称', name: 'contract_name', align: 'left',width: '120'
					 		},
					 { display: '付款管理', name: 'money_manage', align: 'left',width: '120',
					 			render : function(rowdata, rowindex,
										value) {
										return "<a href=javascript:openManage('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|" + rowdata.contract_id  + "|" + rowdata.contract_no+ "|" + rowdata.contract_name +"')>"+"付款信息"+"</a>";
								   }
					 		},
                     { display: '供应商', name: 'ven_no', align: 'left',width: '100',
					 			render : function(rowdata, rowindex,
										value) {
									return rowdata.ven_name;
								}
					 		},
                     { display: '签订日期', name: 'sign_date', align: 'left',width: '90'
					 		},
                     { display: '采购方式', name: 'buy_type', align: 'left',width: '70',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.buy_type == 0){
										return "自主采购";
									}
									return "集中采购";
					 			}
					 		},
                     { display: '开始日期', name: 'start_date', align: 'left',width: '90'
					 		},
                     { display: '结束日期', name: 'end_date', align: 'left',width: '90'
					 		},
                     { display: '合同描述', name: 'contract_detail', align: 'left',width: '150'
					 		},
                     { display: '售后服务说明', name: 'service_detail', align: 'left',width: '150'
					 		},
                     { display: '我方负责人', name: 'emp_main', align: 'left',width: '70',
					 			render : function(rowdata, rowindex,
										value) {
									return rowdata.emp_main_name;
								}
					 		},
                     { display: '对方负责人', name: 'provider', align: 'left',width: '70'
					 		},
                     { display: '是否经过招标', name: 'is_bid', align: 'left',width: '80',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_bid == 0){
										return "否";
									}
									return "是";
								}
					 		},
                     { display: '合同金额', name: 'contract_money', align: 'right', width: '120',
					 			render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.contract_money,'${ass_05005}',1);
								},totalSummary:{type: 'Sum'}
					 		},
                     { display: '制单人', name: 'create_emp', align: 'left',width: '70',
					 			render : function(rowdata, rowindex,
										value) {
									return rowdata.create_emp_name;
								}
					 		},
                     { display: '制单日期', name: 'create_date', align: 'left',width: '90'
					 		},
                     { display: '审核人', name: 'check_emp', align: 'left',width: '70',
					 			render : function(rowdata, rowindex,
										value) {
									return rowdata.check_emp_name;
								}
					 		},
                     { display: '审核日期', name: 'check_date', align: 'left',width: '90'
					 		},
                     { display: '状态', name: 'state', align: 'left',width: '60',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.state == 0){
										return "新建";
									}else if(rowdata.state == 1){
										return "审核";
									}else if(rowdata.state == 2){
										return "履行";
									}else if(rowdata.state == 3){
										return "归档";
									}
									
								}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssFileContractMain.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//
                     delayLoad :true,//初始化数据
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
										{text : '归档（<u>S</u>）',id : 'toExamine',click : toExamine,icon : 'right'},
										{line : true},
										{text : '取消归档（<u>X</u>）',id : 'notToExamine',click :notToExamine ,icon : 'delete'}, 
										{line : true},
						                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' }
				    				]},
				    				onDblClickRow : function (rowdata, rowindex, value)
				    				{
										openUpdate(
												rowdata.group_id   + "|" + 
												rowdata.hos_id   + "|" + 
												rowdata.copy_code   + "|" + 
												rowdata.contract_id +"|"+
												rowdata.state
											);
				    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function printFile(){
    	
    }
    
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			" copy_code="+vo[2] +"&"+ 
			"contract_id="+vo[3] +"&"+
			"state="+vo[4];
				parent.$.ligerDialog.open({
					title: '合同查看',
					height: $(window).height(),
					width: $(window).width(),
					url: 'hrp/ass/assfilecontractmain/assFileContractMainUpdatePage.do?isCheck=false&'+parm,
					modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				});
    
    }
    function openManage(obj){
    	var vo = obj.split("|");
		var parm = "group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			" copy_code="+vo[2] +"&"+ 
			"contract_id="+vo[3] +"&"+
			"contract_no="+vo[4]+"&"+
			"contract_name="+vo[5];
		
		parent.$.ligerDialog.open({
			title: '付款分期信息',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assfilecontractmain/assFileContractMainManagePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
    function loadDict(){
    	
    	var param = {query_key:''};
    	
       //字典下拉框
        //默认年
		autodate("#ass_year","YYYY");
		//默认月
		autodate("#ass_month1","mm");
		//默认月
		autodate("#ass_month2","mm");
        //供应商
    	autocomplete("#ven_id","../queryHosSupDict.do?isCheck=false","id","text",true,true, param, true,null,"400");
    	//我方负责人
    	autocomplete("#emp_main","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true, param, true);
//     	//制单人
//     	autocomplete("#create_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true);
//     	//审核人
//     	autocomplete("#check_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true);
    	//是否经过招标
    	$("#is_bid").ligerComboBox({
			width : 160
		});
    	//状态
    	$("#state").ligerComboBox({
			width : 160
		});
    	//采购方式
    	$("#buy_type").ligerComboBox({
			width : 160
		});
    	//合同类别
    	$("#contract_type").ligerComboBox({
			width : 160
		});
    }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('P', printDate);

	 }
    
	  function toExamine(){
			var ParamVo = [];
			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				$(data).each(
						function() {
								ParamVo.push(this.group_id + "@" + this.hos_id + "@"
										+ this.copy_code + "@" + this.contract_id );
							
						});
				$.ligerDialog.confirm('确定归档?', function(yes) {

					if (yes) {
						ajaxJsonObjectByUrl("updateToExamine.do?isCheck=false", {
							ParamVo : ParamVo.toString()
						}, function(responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
					}
				});
			}
		}
		function notToExamine(){
			var ParamVo = [];
			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				$(data).each(
						function() {
							ParamVo.push(this.group_id + "@" + this.hos_id + "@"
									+ this.copy_code + "@" + this.contract_id  );
						});
				$.ligerDialog.confirm('确定取消归档?', function(yes) {

					if (yes) {
						ajaxJsonObjectByUrl("updateNotToExamine.do?isCheck=false", {
							ParamVo : ParamVo.toString()
						}, function(responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
					}
				});
			}
		}
	  function printDate(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var selPara={};
	    	
	    	$.each(grid.options.parms,function(i,obj){
	    		
	    		selPara[obj.name]=obj.value;
	    		
	    	});
	   		
			var dates = getCurrentDate();
	    	
	    	var cur_date = dates.split(";")[2];
	    	//跨所有列:计算列数
	    	var colspan_num = grid.getColumns(1).length-1;
	   		
	    	var printPara={
	       			title:'合同归档',
	       			head:[
	    				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true}
	       			],
	       			foot:[
	    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
						{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
						{"cell":0,"value":"制单人： ${user_name}","colspan":2,"br":false},
						{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
	       			],
	       			columns:grid.getColumns(1),
	       			headCount:2,//列头行数
	       			autoFile:true,
	       			type:3
	       	};
	   		ajaxJsonObjectByUrl("queryAssFileContractMain.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","050501 资产合同主表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           contract_id:$("#contract_id").val(),
           contract_no:$("#contract_no").val(),
           contract_type:$("#contract_type").val(),
           contract_ori_no:$("#contract_ori_no").val(),
           contract_name:$("#contract_name").val(),
           ass_year:$("#ass_year").val(),
           ass_month:$("#ass_month").val(),
           ven_id:$("#ven_id").val(),
           ven_no:$("#ven_no").val(),
           sign_date:$("#sign_date").val(),
           buy_type:$("#buy_type").val(),
           start_date:$("#start_date").val(),
           end_date:$("#end_date").val(),
           contract_detail:$("#contract_detail").val(),
           service_detail:$("#service_detail").val(),
           emp_main:$("#emp_main").val(),
           provider:$("#provider").val(),
           is_bid:$("#is_bid").val(),
           contract_money:$("#contract_money").val(),
           create_emp:$("#create_emp").val(),
           create_date:$("#create_date").val(),
           check_emp:$("#check_emp").val(),
           check_date:$("#check_date").val(),
           state:$("#state").val(),
           is_group:$("#is_group").val()
         };
		ajaxJsonObjectByUrl("queryAssFileContractMain.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.contract_id+"</td>"; 
					 trHtml+="<td>"+item.contract_no+"</td>"; 
					 trHtml+="<td>"+item.contract_type+"</td>"; 
					 trHtml+="<td>"+item.contract_ori_no+"</td>"; 
					 trHtml+="<td>"+item.contract_name+"</td>"; 
					 trHtml+="<td>"+item.ass_year+"</td>"; 
					 trHtml+="<td>"+item.ass_month+"</td>"; 
					 trHtml+="<td>"+item.ven_id+"</td>"; 
					 trHtml+="<td>"+item.ven_no+"</td>"; 
					 trHtml+="<td>"+item.sign_date+"</td>"; 
					 trHtml+="<td>"+item.buy_type+"</td>"; 
					 trHtml+="<td>"+item.start_date+"</td>"; 
					 trHtml+="<td>"+item.end_date+"</td>"; 
					 trHtml+="<td>"+item.contract_detail+"</td>"; 
					 trHtml+="<td>"+item.service_detail+"</td>"; 
					 trHtml+="<td>"+item.emp_main+"</td>"; 
					 trHtml+="<td>"+item.provider+"</td>"; 
					 trHtml+="<td>"+item.is_bid+"</td>"; 
					 trHtml+="<td>"+item.contract_money+"</td>"; 
					 trHtml+="<td>"+item.create_emp+"</td>"; 
					 trHtml+="<td>"+item.create_date+"</td>"; 
					 trHtml+="<td>"+item.check_emp+"</td>"; 
					 trHtml+="<td>"+item.check_date+"</td>"; 
					 trHtml+="<td>"+item.state+"</td>"; 
					 trHtml+="<td>"+item.is_group+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","050501 资产合同主表.xls",true);
	    },true,manager);
		return;
	 }		  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
<!-- 	<div class="l-layout-top" style="left: 0px; width: 100%; height: 300px;"> -->
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同名称：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="contract_name" type="text" id="contract_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同号：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_no" type="text" id="contract_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同原始编号：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_ori_no" type="text" id="contract_ori_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计月份：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_month1" type="text" id="ass_month1" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'ass_month2\')}',isShowClear:true,readOnly:false,dateFmt:'MM'})"/></td>
            <td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_month2" type="text" id="ass_month2" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'ass_month1\')}',isShowClear:true,readOnly:false,dateFmt:'MM'})"/></td>
            <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同类别：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="contract_type" name="contract_type">
            		<option value="">全部</option>
            		<option value="0">买卖合同</option>
            		<option value="1">赠与合同</option>
            		<option value="2">借款合同</option>
            		<option value="3">租赁合同</option>
            		<option value="4">融资租赁合同</option>
            		<option value="5">承揽合同</option>
            		<option value="6">建设工程合同</option>
            	</select>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年度：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_year" type="text" id="ass_year" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">签订日期：</td>
            <td align="left" class="l-table-edit-td"><input name="sign_date1" type="text" id="sign_date1" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'sign_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="sign_date2" type="text" id="sign_date2" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'sign_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">采购方式：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="buy_type" name="buy_type">
            		<option value="">全部</option>
            		<option value="0">自主采购</option>
            		<option value="1">集中采购</option>
            	</select>
            </td>
            <td align="left"></td>
        </tr> 
       
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date1" type="text" id="create_date1" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'create_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date2" type="text" id="create_date2" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">我方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_main" type="text" id="emp_main" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">对方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="provider" type="text" id="provider" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核日期：</td>
            <td align="left" class="l-table-edit-td"><input name="check_date1" type="text" id="check_date1" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'check_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
           	<td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="check_date2" type="text" id="check_date2" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'check_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否经过招标：</td>
            <td align="left" class="l-table-edit-td">
				<select id="is_bid" name="is_bid">
					<option value="">全部</option>
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
             <td align="left" class="l-table-edit-td">
            	<select id="state" name="state">
            		    <option value="">全部</option>
                		<option value="1">审核</option>
                		<option value="2">履行</option>
                		<option value="3">归档</option>
                </select>
            </td>
            <td align="left"></td>
        </tr> 
        
    </table>
<!-- </div> -->
<!-- <div class="l-layout-bottom" style="left: 0px; width: 100%; top: 120px; height: 100%;"> -->
	<div id="maingrid"  ></div>
<!-- 	</div> -->
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">合同ID</th>	
                <th width="200">合同号</th>	
                <th width="200">合同类别</th>	
                <th width="200">合同原始编号</th>	
                <th width="200">合同名称</th>	
                <th width="200">统计年度</th>	
                <th width="200">统计月份</th>	
                <th width="200">供应商ID</th>	
                <th width="200">供应商变更ID</th>	
                <th width="200">签订日期</th>	
                <th width="200">采购方式</th>	
                <th width="200">开始日期</th>	
                <th width="200">结束日期</th>	
                <th width="200">合同描述</th>	
                <th width="200">售后服务说明</th>	
                <th width="200">我方负责人</th>	
                <th width="200">对方负责人</th>	
                <th width="200">是否经过招标</th>	
                <th width="200">合同金额</th>	
                <th width="200">制单人</th>	
                <th width="200">制单日期</th>	
                <th width="200">审核人</th>	
                <th width="200">审核日期</th>	
                <th width="200">状态</th>	
                <th width="200">集团合同</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
