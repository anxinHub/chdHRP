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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	$(function() {
		
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		query();
	});
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
		grid.options.parms.push({name : 'end_date',value : $("#end_date").val()});
		
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'sup_no',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1]}); 
		
		
		grid.options.parms.push({name : 'stocker',value : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue()}); 
		
    	grid.options.parms.push({name : 'order_code',value : $("#order_code").val()});
		grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()});
    	grid.options.parms.push({name : 'brif',value : $("#brif").val()}); 
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                 { display: '订单号', name: 'order_code', align: 'left' ,width : '120', 
		                	 render : function(rowdata, rowindex, value) {
		 						return '<a href=javascript:update_open("' 
		 							+ rowdata.group_id 
		 							+ ',' + rowdata.hos_id 
		 							+ ',' + rowdata.copy_code 
		 							+ ',' + rowdata.order_id
		 							+ '")>'+rowdata.order_code+'</a>';
		 					}	 
		                 },
						 { display: '摘要', name: 'brif', align: 'left'  },
						 { display: '供应商', name: 'sup_name', align: 'left' }, 
						 { display: '采购单位', name: 'pur_hos_name', align: 'left' }, 
						 { display: '收货单位', name: 'take_hos_name', align: 'left' }, 
						 { display: '付款单位', name: 'pay_hos_name', align: 'left' },
						 { display: '制单人', name: 'maker_name', align: 'left' },
						 { display: '编制日期', name: 'order_date', align: 'left' },
						 { display: '状态', name: 'state', align: 'left',
							 render : function(rowdata, rowindex,value) {
									if(rowdata.state == 2){
										return "已审核";
									}
								}		 
						 } 
					    ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAffiInOrder.do?isCheck=false',
		                 width: '95%', height: '95%', checkbox: true, rownumbers:true,delayLoad:true,
		                 selectRowButtonOnly:true, isScroll :true,
		                 toolbar: { items: [
		                 		{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
		                    	{ line : true},
		                    	{ text: '生成入库单（<u>I</u>）', id:'add', click: imp, icon:'add' },
		                    	{ line : true},
		                    	{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' }
		                 ]}
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//获取选中行
	function getSelectRows() {
		var rows = grid.getCheckedRows();
		return rows;
	}
	
	//生成入库单
	function imp(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行!');
			return;
		}else{
			var ParamVo =[];
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.order_id 
				) 
			});
			$.ligerDialog.confirm('确定生成入库单?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("queryAffiInOrderDetailImp.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.Rows.length > 0){
							//订单药品
							parent.add_rows(responseData.Rows);
							/* var p_data = parent.gridManager.rows;//获取父页面数据
				    		parent.deleteRange(p_data);//删除父页面数据
							parent.add_rows(responseData.Rows);
							parent.is_addRow(); */
						}
						this_close();
					});
				}
			}); 
		}
	}
	
	//查看入库单明细
	function update_open(obj){
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"order_id="+vo[3] ;
		$.ligerDialog.open({
			title: '订单明细查看',
			height: 550,
			width: 1000,
			url: 'medAffiInCommonOrderImpDetail..do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 1
		});   
	}
	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	//快捷键
	function loadHotkeys(){
		hotkeys('Q', query);
		hotkeys('I', imp);
		hotkeys('C', this_close);
	}
	
	//字典加载
	function loadDict() {
		
		$("#sup_code").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("sup_code").setValue("${sup_id}");
		liger.get("sup_code").setText("${sup_text}");
   		autocomplete("#stocker", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true);
   		
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        
        $("#begin_date").ligerTextBox({width:160});
        $("#end_date").ligerTextBox({width:160});
		$("#order_code").ligerTextBox({width:160});
		$("#inv_code").ligerTextBox({width:160});
		$("#stocker").ligerTextBox({width:160});
		$("#brif").ligerTextBox({width:160});
		
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr></tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">订单日期：</td>
	        <td align="left" class="l-table-edit-td"  >
	            <input class="Wdate" requried="true"  name="begin_date" type="text" id="begin_date"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"    />
	        </td>
	        <td align="center" class="l-table-edit-td" style="width: 10px;">至：</td>
			<td align="right" class="l-table-edit-td">
				<input class="Wdate" requried="true" name="end_date" type="text" id="end_date" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>供货单位：</td>
	        <td align="left" class="l-table-edit-td">
	            <input name="sup_code" type="text" id="sup_code" requried="true" ltype="text" validate="{required:true}" />
	        </td>
	        <td align="left"></td>
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;">采购员：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="stocker" type="text" requried="false"  id="stocker"  />
	        </td>
	        <td align="left"></td>
	        
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">订单号：</td>
	        <td align="left" class="l-table-edit-td"  colspan="3">
	           <input name="order_code" type="text" requried="false"  id="order_code"  />
	        </td>
	       
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;">药品信息：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="inv_code" type="text" requried="false"  id="inv_code"  />
	        </td>
	        <td align="left"></td>
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;">摘要：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="brif" type="text" requried="false"  id="brif"  />
	        </td>
	        <td align="left"></td>
		</tr>
	</table>

	
		<div id="maingrid"></div>
		<!-- <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<button id ="query" accessKey="Q"><b>查询（<u>Q</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="genAdd" accessKey="G"><b>生成入库单（<u>G</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table> -->
</body>
</html>
