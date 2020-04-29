<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
    var ForT = [{ id: 0, text: '否' }, { id: 1, text: '是'}];
    var dataFormat;
    $(function (){
        loadDict();
        loadHead(null);
        query();
        
        toobarmanage=gridManager.toolbarManager;
        toobarmanage.setDisabled('saveDetail');
        toobarmanage.setDisabled('save');
        toobarmanage.setDisabled('delete');
        
    });  
     
    function save(){
    	if($("#contract_type").val() == ""){
			$.ligerDialog.error('合同类别不能为空');
			return;	
		}
		if($("#contract_ori_no").val() == ""){
			$.ligerDialog.error('合同原始编号不能为空');
			return;	
		}
		if($("#contract_name").val() == ""){
			$.ligerDialog.error('合同名称不能为空');
			return;	
		}
		if($("#ass_year").val() == ""){
			$.ligerDialog.error('购置年度不能为空');
			return;	
		}
		if($("#ass_month").val() == ""){
			$.ligerDialog.error('购置月份不能为空');
			return;	
		}
		if($("#ven_id").val() == ""){
			$.ligerDialog.error('供应商不能为空');
			return;	
		}
		if($("#sign_date").val() == ""){
			$.ligerDialog.error('签订日期不能为空');
			return;	
		}
		if($("#buy_type").val() == ""){
			$.ligerDialog.error('采购方式不能为空');
			return;	
		}
		if($("#start_date").val() == ""){
			$.ligerDialog.error('开始日期不能为空');
			return;	
		}
		if($("#end_date").val() == ""){
			$.ligerDialog.error('结束日期不能为空');
			return;	
		}
		if($("#emp_main").val() == ""){
			$.ligerDialog.error('我方负责人不能为空');
			return;	
		}
		if($("#provider").val() == ""){
			$.ligerDialog.error('对方负责人不能为空');
			return;	
		}
		if($("#is_bid").val() == ""){
			$.ligerDialog.error('是否经过招标不能为空');
			return;	
		}
        var formPara={
        contract_id:$("#contract_id").val(),
        contract_no:$("#contract_no").val(),
        contract_type:$("#contract_type").val(),
        contract_ori_no:$("#contract_ori_no").val(),
        contract_name:$("#contract_name").val(),
        ass_year:$("#ass_year").val(),
        ass_month:$("#ass_month").val(),
        ven_id:liger.get("ven_id").getValue(),
//         ven_no:$("#ven_no").val(),
        sign_date:$("#sign_date").val(),
        buy_type:liger.get("buy_type").getValue(),
        start_date:$("#start_date").val(),
        end_date:$("#end_date").val(),
        contract_detail:$("#contract_detail").val(),
        service_detail:$("#service_detail").val(),
        emp_main:liger.get("emp_main").getValue(),
        provider:$("#provider").val(),
        is_bid:liger.get("is_bid").getValue(),
        contract_money:$("#contract_money").val(),
//         create_emp:liger.get("create_emp").getValue(),
//         create_date:$("#create_date").val(),
//         check_emp:liger.get("check_emp").getValue(),
//         check_date:$("#check_date").val(),
//         state:liger.get("state").getValue(),
//         is_group:$("#is_group").val()
        };
        ajaxJsonObjectByUrl("addAssPerformContractMain.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	$("#contract_id").val(responseData.contract_id);
				$("#contract_no").val(responseData.contract_no);
                parent.query();
                query();
            }
        });
    }
    
	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '资产编码',
										name : 'ass_code',
										align : 'left'
									},
									{
										display : '资产名称',
										name : 'ass_id',
										align : 'left',
										textField : 'ass_name',
										editor : {
											type : 'select',
											valueField : 'ass_id_no',
											textField : 'ass_name',
											selectBoxWidth : 500,
											selectBoxHeight : 240,
											grid : {
												columns : [ {
													display : '编码',
													name : 'ass_code',
													align : 'left'
												}, {
													display : '名称',
													name : 'ass_name',
													align : 'left'
												}, {
													display : '分类名称',
													name : 'ass_type_name',
													align : 'left'
												}, {
													display : '是否停用',
													name : 'is_stop',
													align : 'left'
												} ],
												switchPageSizeApplyComboBox : false,
												onSelectRow : f_onSelectRow_detail,
												url : '../queryAssNoDictTable.do?isCheck=false',
												pageSize : 30
											},
											alwayShowInDown : true,
											keySupport : true,
											autocomplete : true,
											onSuccess : function() {
												this.parent("tr").next(
														".l-grid-row").find(
														"td:first").focus();
											}
										//
										}

									},
									{
										display : '型号',
										name : 'ass_model',
										editor : {
											type : 'text'
										},
										align : 'left'
									},
									{
										display : '规格',
										name : 'ass_spec',
										editor : {
											type : 'text'
										},
										align : 'left'
									},
									{
										display : '品牌',
										name : 'ass_brand',
										editor : {
											type : 'text'
										},
										align : 'left'
									},
									{
			    						display : '生产厂家',
			    						name : 'fac_id',
			    						textField : 'fac_name',
			    						editor : {
			    							type : 'select',
			    							valueField : 'id',
			    							textField : 'text',
			    							url : '../queryHosFacDict.do?isCheck=false',
			    							keySupport : true,
			    							autocomplete : true,
			    							onSuccess : function (data){
			    								if(initvalue != undefined && initvalue != ""){
			    									this.setValue(initvalue);
			    									initvalue="";
			    								}
			    					       }
			    						},
			    						align : 'left'
			    					}, {
										display : '合同数量',
										name : 'contract_amount',
										align : 'left',
										type: 'int',
										editor : {
											type : 'int'
										},
										align : 'left',totalSummary:{type: 'Sum'}
									}, {
										display : '合同单价',
										name : 'contract_price',
										editor : {
											type : 'text'
										},
										align : 'left',
										render : function(rowdata, rowindex,
												value) {
											 return formatNumber(rowdata.contract_price,'${ass_05006}',1);
										}
									},{
										display : '总额',
										name : 'sum_price',
										align : 'left',
										render:function(rowdata){
											return formatNumber(rowdata.contract_price*rowdata.contract_amount,'${ass_05005}',1);
										},
										totalSummary:{type: 'Sum'}
									}, {
										display : '交货日期',
										name : 'send_date',
										type: 'date', 
										format: 'yyyy-MM-dd',
										editor : {
											type : 'date'
										},
										align : 'left'
									}, {
										display : '保修期',
										name : 'keep_repair_times',
										editor : {
											type : 'text'
										},
										align : 'left'
									}, {
										display : '保修期单位',
										name : 'times_unit',
										editor : {
											type : 'text'
										},
										align : 'left'
									}, {
										display : '是否安装',
										name : 'is_fix',
										editor : {
											type : 'select',data:ForT,valueField: 'id'
										},
										render: function (item)
					                    {
					                        if (parseInt(item.is_fix) == 0) return '否';
					                        return '是';
					                    },
										align : 'left'
									}, {
										display : '是否验收',
										name : 'is_accept',
										editor : {
											type : 'select',data:ForT,valueField: 'id'
										},
										render: function (item)
					                    {
					                        if (parseInt(item.is_accept) == 0) return '否';
					                        return '是';
					                    },
										align : 'left'
									}, {
										display : '是否招标',
										name : 'is_bid',
										editor : {
											type : 'select',data:ForT,valueField: 'id'
										},
										render: function (item)
					                    {
					                        if (parseInt(item.is_bid) == 0) return '否';
					                        return '是';
					                    },
										align : 'left'
									}, {
										display : '备注',
										name : 'note',
										editor : {
											type : 'text'
										},
										align : 'left'
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : '../assperformcontractdetail/queryAssPerformContractDetail.do?isCheck=false',
							width : '100%',
							height : '100%',
							checkbox : true,
							enabledEdit : false,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							isScroll : true,
							rownumbers : true,
							delayLoad : true,//初始化明细数据
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ 
									{
										text : '关闭',
										id : 'close',
										click : this_close,
										icon : 'candle'
										}
								]
							}

						});

		gridManager = $("#maingrid").ligerGetGridManager();
		
	}
	
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(
							function() {
								ParamVo.push(this.group_id + "@" + this.hos_id
										+ "@" + this.copy_code + "@"
										+ this.contract_id + "@"
										+ this.contract_detail_id)
							});
					$.ligerDialog
							.confirm(
									'确定删除?',
									function(yes) {
										if (yes) {
											ajaxJsonObjectByUrl(
													"../assperformcontractdetail/deleteAssPerformContractDetail.do?isCheck=false",
													{
														ParamVo : ParamVo
																.toString()
													},
													function(responseData) {
														if (responseData.state == "true") {
															query();
															$("#contract_money").val(responseData.contract_money);
															is_addRow();
														}
													});
										}
									});
				}
				return;
			case "saveDetail":
				if('${state}' == 1){
		    		$.ligerDialog.warn('数据已审核,不能保存');
					return;
				}
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var is_flag = true;
					var is_flag1 = true;
					var str = "";
					var str1= "";
					var targetMap = new HashMap();
					$.each(data,function(i,v){
						if (v.contract_amount == "" || v.contract_amount == null || v.contract_amount == 'undefined') {  
							str += "第"+(i+1)+"行[数量]不能为空";  
						       is_flag = false; 
						  }  
						  if (v.contract_price == "" || v.contract_price == null  || v.contract_price == 'undefined') {  
							  str += "第"+(i+1)+"行[单价]不能为空";  
						       is_flag = false;  
						  } 
						  if (v.send_date == "" || v.send_date == null  || v.send_date == 'undefined') {  
							  str += "第"+(i+1)+"行[交货日期]不能为空";  
						       is_flag = false; 
						  } 
						 
						  var key=v.ass_code +"|"+v.ass_model+"|"+v.ass_spec+"|"+v.fac_id;
						  var value="第"+(i+1)+"行";
						  if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
							  targetMap.put(key ,value);
						  }else{
							  str1 += targetMap.get(key)+"与"+value+"重复";
							  is_flag1 = false;
						  }

					});
					if(is_flag == false){
						$.ligerDialog.error(str);
						return;
					}
					if(is_flag1 == false){
						$.ligerDialog.error(str1);
						return;
					}
					$.ligerDialog
							.confirm(
									'确定保存?',
									function(yes) {
										var contract_id = $("#contract_id").val();
										var contract_no = $("#contract_no").val();

										if (yes) {
											ajaxJsonObjectByUrl(
													"../assperformcontractdetail/addOrUpdateAssPerformContractDetail.do?isCheck=false",
													{
														ParamVo : JSON
																.stringify(data),
														contract_id : contract_id,
														contract_no : contract_no

													},
													function(responseData) {
														if (responseData.state == "true") {
															layer.msg('保存成功', {
																icon : 1
															});
															query();
															$("#contract_money").val(responseData.contract_money);
															is_addRow();
														}
													});
										}
									});
				}
				return;
		
			}
		}
	}

	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;

		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					ass_usage_code : data.usage_name,
					ass_model : data.ass_model,
					ass_spec : data.ass_spec
				});

			}
		}
		return true;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if(e.column.name=="contract_price" || e.column.name=="contract_amount"){
			
			var price= (e.record.contract_price== null || e.record.contract_price == 'undefined' || e.record.contract_price == "") ? 0 :e.record.contract_price ;
			var num= (e.record.contract_amount== null || e.record.contract_amount == 'undefined' || e.record.contract_amount == "") ? 0 :e.record.contract_amount ;
			
			grid.updateCell('sum_price', price * num, e.record); 
		}
		return true;
	}
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'contract_id',
			value : $("#contract_id").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
// 		$("#resultPrint > table > tbody").empty();
	}
   
    function saveAssContractMain(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框

    	//供应商
    	autocomplete("#ven_id","../queryHosSupDict.do?isCheck=false","id","text",true,true,"",false,"${ven_id}@${ven_no}");
        
    	liger.get("ven_id").setValue("${ven_id}");
    	
        liger.get("ven_id").setText("${ven_name}");
     
		//我方负责人
    	autocomplete("#emp_main","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,"",false,"${emp_main}");
		
    	liger.get("emp_main").setValue("${emp_main}");
    	
        liger.get("emp_main").setText("${emp_main_name}");
 
		//是否经过招标
    	$("#is_bid").ligerComboBox({
    		
			width : 160
			
		});
		
		liger.get("is_bid").setValue("${is_bid}");
		
    	//合同类别
    	$("#contract_type").ligerComboBox({
    		
			width : 160
			
		});
    	
    	liger.get("contract_type").setValue("${contract_type}");
    	
    	//采购方式
    	$("#buy_type").ligerComboBox({
    		
			width : 160
			
		});
    	
    	liger.get("buy_type").setValue("${buy_type}");
    	
        $("#contract_no").ligerTextBox({disabled:true,cancelable: false,width:160});
        
		$("#contract_money").ligerTextBox({disabled:true,cancelable: false,width:160});
		
    	$("#contract_ori_no").ligerTextBox({width:160});
        
        $("#contract_name").ligerTextBox({width:160});
        
        $("#ass_year").ligerTextBox({width:160});
        
        $("#ass_month").ligerTextBox({width:160});
        
        $("#sign_date").ligerTextBox({width:160});
        
        $("#start_date").ligerTextBox({width:160});
        
        $("#end_date").ligerTextBox({width:160});
        
        $("#provider").ligerTextBox({width:160});
     }  
    function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
    function this_close(){
		frameElement.dialog.close();
	}
    </script>
  
  </head>
  
   <body style="padding: 0px; overflow: hidden;" onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <input type="hidden" name="contract_id" id="contract_id" ltype="text" value="${contract_id}"  />
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>合同号：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_no" type="text" id="contract_no" ltype="text" disabled="disabled" value="${contract_no}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>合同原始编号：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_ori_no" type="text" id="contract_ori_no" ltype="text" value="${contract_ori_no}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>合同名称：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_name" type="text" id="contract_name" ltype="text" value="${contract_name}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>合同类别：</td>
            	<td align="left" class="l-table-edit-td">
            	<select id="contract_type" name="contract_type" value="${contract_type} ">
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计年度：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_year" type="text" id="ass_year" ltype="text" value="${ass_year}" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计月份：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_month" type="text" id="ass_month" ltype="text" value="${ass_month}" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            
           	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>签订日期：</td>
            <td align="left" class="l-table-edit-td"><input name="sign_date" type="text" id="sign_date" ltype="text" value="${sign_date}" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>开始日期：</td>
            <td align="left" class="l-table-edit-td"><input name="start_date" type="text" id="start_date" ltype="text" value="${start_date}" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>结束日期：</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" ltype="text" value="${end_date}" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text" value="${ven_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>采购方式：</td>
            	<td align="left" class="l-table-edit-td">
            	<select id="buy_type" name="buy_type" value="${buy_type}" >
            		<option value=""></option>
            		<option value="0">自主采购</option>
            		<option value="1">集中采购</option>
            	</select>
            	</td>
            	<td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>是否经过招标：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="is_bid" name="is_bid" value="${is_bid}">
					<option value=""></option>
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>我方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_main" type="text" id="emp_main" ltype="text" value="${emp_main}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>对方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="provider" type="text" id="provider" ltype="text" value="${provider}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同金额：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_money" type="text" id="contract_money" ltype="text" value="${contract_money}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同描述：</td>
            <td align="left" class="l-table-edit-td" colspan="9"> 
            	<textarea rows="2" cols="100" name="contract_detail" id="contract_detail" >${contract_detail}</textarea>
            </td>
            <td align="left"></td>
        	</tr>
        	<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">售后服务说明：</td>
            <td align="left" class="l-table-edit-td" colspan="9"> 
            	<textarea rows="2" cols="100" name="service_detail" id="service_detail" >${service_detail}</textarea>
            </td>
            <td align="left"></td>
        	</tr>
        
        </table>
    </form>
    
    <div id="maingrid"></div>
	<div id="resultPrint" style="display: none">
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
