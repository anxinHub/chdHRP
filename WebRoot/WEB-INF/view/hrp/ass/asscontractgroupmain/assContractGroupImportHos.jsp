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
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
    	loadForm();
        $("#contract_no").ligerTextBox({width:160});
        $("#contract_type").ligerTextBox({width:160});
        $("#contract_ori_no").ligerTextBox({width:160});
        $("#contract_name").ligerTextBox({width:160});
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
    	  grid.options.parms.push({name:'is_hos_exists',value:'true'});
    	//加载查询条件
    	grid.loadData(grid.where);
     }
   

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [
                     { display: '合同号', name: 'contract_no', align: 'left',width: '110',frozen: true
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
                     { display: '合同金额', name: 'contract_money', align: 'left', width: '120',
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
									}
									return "审核";
								}
					 		},
                     { display: '集团合同', name: 'is_group', align: 'left',width: '100'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../asscontractmain/queryAssContractMain.do?state=1',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    	{ text: '保存（<u>A</u>）', id:'add', click: save, icon:'add' }
				    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function save(){
    	if($("#contract_type_hos").val() == ""){
    		$.ligerDialog.error('请选择合同类别');
    	}
		if($("#contract_ori_no_hos").val() == ""){
			$.ligerDialog.error('请选输入同原始编号');	
	    }
		if($("#contract_name_hos").val() == ""){
			$.ligerDialog.error('请输入合同名称');
		}
		if(liger.get("ven_id_hos").getValue() == ""){
			$.ligerDialog.error('请选择供应商');
		}
		if($("#sign_date_hos").val() == ""){
			$.ligerDialog.error('请选择签订日期');
		}
		if($("#start_date_hos").val() == ""){
			$.ligerDialog.error('请选择开始日期');
		}
		if($("#end_date_hos").val() == ""){
			$.ligerDialog.error('请选择结束日期');
		}
		if($("#buy_type_hos").val() == ""){
			$.ligerDialog.error('请选择采购方式');
		}
		if(liger.get("emp_main_hos").getValue() == ""){
			$.ligerDialog.error('请选择我方负责人');
		}
		if($("#provider_hos").val() == ""){
			$.ligerDialog.error('请对方对方负责人');
		}
		if($("#is_bid_hos").val()  == ""){
			$.ligerDialog.error('请选择是否经过招标');
		}
		if(liger.get("cur_code_hos").getValue() == ""){
			$.ligerDialog.error('请选择币种');
		}
    	var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.contract_id);
					});
			$.ligerDialog.confirm('确定要导入招标单?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("importHosContractGroup.do?isCheck=false", {
						contract_ids : ParamVo.toString(),
						contract_type:$("#contract_type_hos").val(), 
		    			contract_ori_no:$("#contract_ori_no_hos").val(), 
		    			contract_name:$("#contract_name_hos").val(), 
		    			ven_id:liger.get("ven_id_hos").getValue().split("@")[0],
		    			ven_no:liger.get("ven_id_hos").getValue().split("@")[1],
		    			sign_date:$("#sign_date_hos").val(),
		    			start_date:$("#start_date_hos").val(),
		    			end_date:$("#end_date_hos").val(),
		    			buy_type:$("#buy_type_hos").val(),  
		    			contract_detai:$("#contract_detai_hos").val(),
		    			service_detail:$("#service_detail_hos").val(), 
		    			emp_main:liger.get("emp_main_hos").getValue(), 
		    			provider:$("#provider_hos").val(), 
		    			is_bid:$("#is_bid_hos").val(), 
		    			cur_code:liger.get("cur_code_hos").getValue()
					}, function(responseData) {
							parent.query();
					});
				}
			});	
		}
    }
    function loadDict(){
       //字典下拉框
        //默认年
		autodate("#ass_year","YYYY");
		//默认月
		autodate("#ass_month1","mm");
		//默认月
		autodate("#ass_month2","mm");
        //供应商
    	autocomplete("#ven_id","../queryHosSupDict.do?isCheck=false","id","text",true,true);
    	//我方负责人
    	autocomplete("#emp_main","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true);
    	
    	autocomplete("#emp_main_hos","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true);
    	
    	autocomplete("#ven_id_hos","../queryHosSupDict.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#cur_code_hos","../../../hrp/acc/queryCur.do?isCheck=false", "id","text", true, true);
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
    	$("#create_date").ligerTextBox({
			width : 160
		});
    	autocomplete("#ass_purpose",
				"../queryAssUsageDict.do?isCheck=false","id",
			    "text",true,true);
    }  
    function loadForm() {

		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		$("form").ligerForm();
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
<!-- 	<div class="l-layout-top" style="left: 0px; width: 100%; height: 300px;"> -->
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同类别：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="contract_type" name="contract_type">
            		<option value=""></option>
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计月份：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_month1" type="text" id="ass_month1" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'ass_month2\')}',isShowClear:true,readOnly:false,dateFmt:'MM'})"/></td>
            <td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_month2" type="text" id="ass_month2" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'ass_month1\')}',isShowClear:true,readOnly:false,dateFmt:'MM'})"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">采购方式：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="buy_type" name="buy_type">
            		<option value=""></option>
            		<option value="0">自主采购</option>
            		<option value="1">集中采购</option>
            	</select>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">签订日期：</td>
            <td align="left" class="l-table-edit-td"><input name="sign_date1" type="text" id="sign_date1" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'sign_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="sign_date2" type="text" id="sign_date2" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'sign_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
        </tr> 
       
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">我方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_main" type="text" id="emp_main" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">对方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="provider" type="text" id="provider" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date1" type="text" id="create_date1" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'create_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date2" type="text" id="create_date2" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            
        </tr> 
        
        <tr>
         <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否经过招标：</td>
            <td align="left" class="l-table-edit-td">
				<select id="is_bid" name="is_bid">
					<option value=""></option>
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同名称：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_name" type="text" id="contract_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核日期：</td>
            <td align="left" class="l-table-edit-td"><input name="check_date1" type="text" id="check_date1" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'check_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
           	<td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="check_date2" type="text" id="check_date2" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'check_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
        </tr> 
    </table>
    <hr size="1" width="1400" color="#A3COE8" align="left" style=""/>
    <form name="form1" method="post" id="form1">
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
				<td align="right" class="l-table-edit-td"
					><b><font color="red">*</font></b>合同原始编号：</td>
				<td align="left" class="l-table-edit-td"><input
					name="contract_ori_no_hos" type="text" id="contract_ori_no_hos"
					ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="right" class="l-table-edit-td"
					><b><font color="red">*</font></b>合同名称：</td>
				<td align="left" class="l-table-edit-td"><input
					name="contract_name_hos" type="text" id="contract_name_hos" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>合同类别：</td>
            	<td align="left" class="l-table-edit-td">
            	<select id="contract_type_hos" name="contract_type_hos">
            		<option value=""></option>
            		<option value="0">买卖合同</option>
            		<option value="1">赠与合同</option>
            		<option value="2">借款合同</option>
            		<option value="3">租赁合同</option>
            		<option value="4">融资租赁合同</option>
            		<option value="5">承揽合同</option>
            		<option value="6">建设工程合同</option>
            	</select>
            	</td>
            	<td align="right" class="l-table-edit-td"
					><b><font color="red">*</font></b>签订日期：</td>
				<td align="left" class="l-table-edit-td"><input
					name="sign_date_hos" type="text" id="sign_date_hos" ltype="text"
					validate="{required:true,maxlength:20}" class="Wdate"
					onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					><b><font color="red">*</font></b>供应商：</td>
				<td align="left" class="l-table-edit-td"><input name="ven_id_hos"
					type="text" id="ven_id_hos" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>采购方式：</td>
            	<td align="left" class="l-table-edit-td">
            	<select id="buy_type_hos" name="buy_type_hos">
            		<option value=""></option>
            		<option value="0">自主采购</option>
            		<option value="1">集中采购</option>
            	</select>
            	</td>
            	<td align="right" class="l-table-edit-td"
					><b><font color="red">*</font></b>是否经过招标：</td>
				<td align="left" class="l-table-edit-td">
					<select id="is_bid_hos" name="is_bid_hos">
					<option value=""></option>
					<option value="0">否</option>
					<option value="1">是</option>
					</select>
				</td>
				<td align="right" class="l-table-edit-td"
					><b><font color="red">*</font></b>币种：</td>
				<td align="left" class="l-table-edit-td"><input
					name="cur_code_hos" type="text" id="cur_code_hos" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					><b><font color="red">*</font></b>我方负责人：</td>
				<td align="left" class="l-table-edit-td"><input name="emp_main_hos"
					type="text" id="emp_main_hos" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="right" class="l-table-edit-td"
					><b><font color="red">*</font></b>对方负责人：</td>
				<td align="left" class="l-table-edit-td"><input name="provider_hos"
					type="text" id="provider_hos" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
					<td align="right" class="l-table-edit-td"
					><b><font color="red">*</font></b>开始日期：</td>
				<td align="left" class="l-table-edit-td"><input
					name="start_date_hos" type="text" id="start_date_hos" ltype="text"
					validate="{required:true,maxlength:20}" class="Wdate"
					onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="right" class="l-table-edit-td"
					><b><font color="red">*</font></b>结束日期：</td>
				<td align="left" class="l-table-edit-td"><input name="end_date_hos"
					type="text" id="end_date_hos" ltype="text"
					validate="{required:true,maxlength:20}" class="Wdate"
					onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			</tr>
			<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同描述：</td>
            <td align="left" class="l-table-edit-td" colspan="9"> 
            	<textarea rows="2" cols="100" name="contract_detail_hos" id="contract_detail_hos" ></textarea>
            </td>
            <td align="left"></td>
        	</tr>
        	<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">售后服务说明：</td>
            <td align="left" class="l-table-edit-td" colspan="9"> 
            	<textarea rows="2" cols="100" name="service_detail_hos" id="service_detail_hos" ></textarea>
            </td>
            <td align="left"></td>
        	</tr>
		</table>
		</form>
    <div id="maingrid"></div>
</body>
</html>
