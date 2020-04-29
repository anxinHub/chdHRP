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
    });  
    
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
							url : '../assfilecontractdetail/queryAssFileContractDetail.do?isCheck=false',
							width : '100%',
							height : '90%',
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
	}
   
  
    function loadDict(){
        //字典下拉框

    	//供应商
    	autocomplete("#ven_id","../queryHosSupDict.do?isCheck=false","id","text",true,true,"",false,"${ven_id}@${ven_no}","400");
        
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
    function this_close(){
		frameElement.dialog.close();
	}
    </script>
  
  </head>
  
   <body style="padding: 0px; overflow: hidden;" >
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <input type="hidden" name="contract_id" id="contract_id" ltype="text" value="${contract_id}"  />
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
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
    </body>
</html>
