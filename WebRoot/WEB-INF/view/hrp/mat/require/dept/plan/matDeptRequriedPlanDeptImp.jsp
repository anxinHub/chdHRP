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
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var is_check = 0;
	$(function() {
		
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		//query();
		$("#is_zero").bind('change', function (){
			if($('#is_zero').is(':checked')) {
	        	is_check = 1;
			}else{
				is_check = 0;
			} 
		}); 
	});
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'store_id',value : '${store_id}'});
		grid.options.parms.push({name : 'store_no',value : '${store_no}'});
		
		grid.options.parms.push({name : 'dept_id',value : '${dept_id}'});
		grid.options.parms.push({name : 'dept_no',value : '${dept_no}'});
		
		
    	grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val() });
    	
    	grid.options.parms.push({name : 'thisDateB',value : $("#thisDateB").val()}); 
		grid.options.parms.push({name : 'thisDateE',value : $("#thisDateE").val()}); 
		
    	grid.options.parms.push({name : 'lastDateB',value : $("#lastDateB").val()}); 
    	grid.options.parms.push({name : 'lastDateE',value : $("#lastDateE").val()});
    	
    	
    	grid.options.parms.push({name:'is_check',value:is_check}); 
    	//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                 { display: '材料编码', name: 'inv_code', align: 'left' , width:120 },
						 { display: '材料名称', name: 'inv_name', align: 'left' , width:160 },
						 { display: '规格型号', name: 'inv_model', align: 'left', width:160 },
						 { display: '计量单位', name: 'unit_name', align: 'left', width:100 },
						 
						 { display: '计量单价', name: 'price', align: 'right' , width:100,
							 render:function(rowdata){
				            		return formatNumber(rowdata.price ==null ? 0 : rowdata.price,'${p04006}',1);
				            } 	 
						 },
						 { display: '上期计划量', name: 'last_plan', align: 'right' , width:100,
							 render:function(rowdata){
				            		return formatNumber(rowdata.last_plan ==null ? 0 : rowdata.last_plan,2,1);
				            } 	 
						 },
						 { display: '上期耗用量', name: 'last_expend', align: 'right' , width:100,
							 render:function(rowdata){
				            		return formatNumber(rowdata.last_expend ==null ? 0 : rowdata.last_expend,2,1);
				            } 	 
						 },
						 { display: '本期已报数量', name: 'this_apply', align: 'right', width:100,
							 render:function(rowdata){
				            		return formatNumber(rowdata.this_apply ==null ? 0 : rowdata.this_apply,2,1);
				            } 	 
						 }
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,
		                 url:'queryMatDeptExpend.do?isCheck=false',
		                 width: '95%', height: '75%', checkbox: true, rownumbers:false,delayLoad:true,
		                 selectRowButtonOnly:true, isScroll :true 
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function getSelectRows() {
		var rows = grid.getCheckedRows();
		return rows;
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', addNew);
		hotkeys('C', this_close);
	}
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	//确定添加
	function addNew(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择材料！');
			return;
		}else{
			/* var detail_rows = new StringBuffer();
			detail_rows.append("[");
			$.each(data, function(index, data){
				if(index != 0){
					detail_rows.append(",");
				}
				detail_rows.append('{"inv_id":').append(data.inv_id).append(',');
				detail_rows.append('"inv_code":"').append(data.inv_code).append('",');
				detail_rows.append('"inv_name":"').append(data.inv_name).append('",');
				detail_rows.append('"inv_no":').append(data.inv_no).append(','); 
				detail_rows.append('"inv_model":"').append(data.inv_model).append('",');
				detail_rows.append('"unit_code":"').append(data.unit_code).append('",');
				detail_rows.append('"unit_name":"').append(data.unit_name).append('",');
				detail_rows.append('"pack_code":"').append(data.pack_code).append('",');
				detail_rows.append('"pack_name":"').append(data.pack_name).append('",');
				detail_rows.append('"fac_id":').append(data.fac_id).append(','); 
				detail_rows.append('"fac_no":').append(data.fac_no).append(','); 
				detail_rows.append('"fac_name":"').append(data.fac_name).append('",');
				detail_rows.append('"sup_id":').append(data.sup_id).append(','); 
				detail_rows.append('"sup_no":').append(data.sup_no).append(','); 
				detail_rows.append('"sup_name":"').append(data.sup_name).append('",');
				detail_rows.append('"amount":').append(data.last_expend).append(',');
				detail_rows.append('"price":').append(data.price).append(',');
				detail_rows.append('"sum_money":').append(data.price*data.last_expend).append('}');
			});
			detail_rows.append("]"); */
			//return;
		    //var p_data = parent.gridManager.rows;//获取父页面数据
    		//parent.deleteRange(p_data);//删除父页面数据 
    		//alert(detail_rows.toString());
			//添加材料
			//parent.add_rows(data);
    		//parent.is_addRow();
    		
    		// 将选择数据往表格的倒数第一行插入。update.wsj.2017.08.09  
    		var theRowDatas = parent.grid.getData(); 
    		for (var i = 0; i < theRowDatas.length; i++) {
    			
				if (theRowDatas[i]&&!theRowDatas[i].inv_id) {
					parent.grid.deleteRow(i);
					theRowDatas =parent.grid.getData();
					i--;
				}
			}
    		parent.grid.addRows(data);
    		parent.is_addRow();
			this_close();
			
		}
		
	}
	
	function getLastDate(){
		
		var currentYear;
		var currentMonth;
		var currentdate;
		
		var lastMonth;
		var lastFirstDay;
		var lastEndDay;

		var date = new Date();
		var seperator1 = "-";
		var seperator2 = ":";
		
		var month = date.getMonth() + 1;
		if(month == 1){
			lastMonth = '12';
			currentYear = date.getFullYear()-1;
		}else{
			lastMonth = month - 1;
			currentYear = date.getFullYear();
		}
		
		var thisEnd = getLastDay(currentYear, month);
		var lastEnd = getLastDay(currentYear, lastMonth);
		
		if (lastMonth >= 1 && lastMonth <= 9) {
			lastMonth = "0" + lastMonth;
		}
		
		lastFirstDay = currentYear + seperator1 + lastMonth + seperator1 + '01';
		lastEndDay = currentYear + seperator1 + lastMonth + seperator1 + lastEnd;
		
		$("#lastDateB").val(lastFirstDay);
		$("#lastDateE").val(lastEndDay);
	}
	function loadDict() {
		autocomplete("#store_code","../../../queryMatStore.do?isCheck=false","id","text",true,true);//仓库
        liger.get("store_code").setValue("${store_id}");
        liger.get("store_code").setText("${store_name}");
        $("#store_code").ligerComboBox({disabled:true,cancelable: false});
        autodate("#thisDateB", "yyyy-mm-dd", "month_first");
	    autodate("#thisDateE", "yyyy-mm-dd", "month_last");
		//返回当前年,当前月,当前日期,当前	月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		getLastDate();
		
   		$("#inv_code").ligerTextBox({width:150});
		$("#lastDateB").ligerTextBox({width:140});
		$("#lastDateE").ligerTextBox({width:140});
		
		//格式化按钮
		$("#query").ligerButton({click: query, width:70});
		$("#close").ligerButton({click: this_close, width:70});
		$("#addNew").ligerButton({click: addNew, width:70});
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
		<tr></tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">上期日期：</td>
	        <td align="left" class="l-table-edit-td"  >
	            <input class="Wdate" ="true"  name="lastDateB" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="lastDateB"   />
	        </td>
	        <td align="center" class="l-table-edit-td" style="width: 10px;">至：</td>
			<td align="right" class="l-table-edit-td">
				<input class="Wdate" name="lastDateE" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
				id="lastDateE" />
			</td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>响应库房：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="store_code" type="text" requried="true"  id="store_code"  />
	        </td>
	        <td align="left"></td>
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;">物资信息：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="inv_code" type="text" requried="true"  id="inv_code"  />
	        </td>
	        <td align="left"></td>
	    </tr>    	
		<tr>
			
	        
	        <td style="padding-left:20px;" align="right" class="l-table-edit-td"></td>
			<td  align="left" class="l-table-edit-td">
				 <input name="is_zero" type="checkbox" id="is_zero" />&nbsp;&nbsp;显示消耗量为0的材料
			</td>
	            
            <td align="left"></td>
			<td >
				<input name="thisDateB" type="hidden" id="thisDateB" requried="false"/>
				<input name="thisDateE" type="hidden" id="thisDateE" requried="false"/>
			</td>	
		</tr>
		
	</table>

	<div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<button id ="query" accessKey="Q"><b>查询（<u>Q</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="addNew" accessKey="A"><b>添加（<u>A</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>
