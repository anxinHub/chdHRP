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
	var store_id = '${store_id}';
	var dept_id ='${dept_id}';
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
	});
	//查询
	function query() {
		var allData = parent.gridManager.getData();
    	var str = [];
    	if(allData.length != 0){
	    	$.each(allData,function(i, v){
	    		if(v.inv_id == null){
	    			return;
	    		}
	    		str.push(v.inv_id);
	    	});
    	}
    	
		grid.options.parms = [];
		grid.options.newPage = 1; 
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'store_id',value : '${store_id}'});
		grid.options.parms.push({name : 'store_no',value : '${store_no}'});
		
		
    	grid.options.parms.push({name : 'store_match',value : liger.get("store_match").getValue()}); 
    	grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val() });
    	
    	grid.options.parms.push({name : 'thisDateB',value : $("#thisDateB").val()});
		grid.options.parms.push({name : 'thisDateE',value : $("#thisDateE").val()});
    	grid.options.parms.push({name : 'lastDateB',value : $("#last_dateB").val()}); 
    	grid.options.parms.push({name : 'lastDateE',value : $("#last_dateE").val()});
    	
    	grid.options.parms.push({
    		name:'inv_ids',
    		value: str
    	});
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns: [{ 
 				/* display: '材料ID', name: 'inv_id', align: 'left'
			    }, {  */
	 			display: '交易编码', name: 'bid_code', align: 'left'
	 			}, { 
		 			display: '材料编码', name: 'inv_code', align: 'left'
		 		}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left'
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left'
		 		}, { 
		 			display: '价格', name: 'plan_price', align: 'right',
		 			render : function(rowdata, rowindex, value) {
						rowdata.plan_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
					}
		 		}, { 
		 			display: '数量', name: 'amount', align: 'right', type: 'float'
		 		}, {  
		 			display: '生产厂商', name: 'fac_name', align: 'left',width:'200'
		 		} , { 
		 			display: '供应商', name: 'sup_name', align: 'left',width:'200'
		 		}  
		    ],
		    dataAction: 'server',dataType: 'server',rownumbers:true,usePager:true,pageSize:500,
		    url:'queryMatStoreSupport.do?isCheck=false',
		    width: '100%', height: '85%', checkbox: true, delayLoad:true,
		    selectRowButtonOnly:true, isScroll :true 
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function getSelectRows() {
		var rows = grid.getCheckedRows();
		return rows;
	}
	
	//确定添加
	function addNew(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请配置！');
			return;
		}else{
			/* var num = $("#num").val();
			var detail_rows = new StringBuffer();
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
				detail_rows.append('"amount":').append(data.amount*num).append(',');
				detail_rows.append('"price":').append(data.plan_price).append(',');
				detail_rows.append('"sup_id":"').append(data.sup_id).append('",');
				detail_rows.append('"sup_no":"').append(data.sup_no).append('",');
				detail_rows.append('"sup_code":"').append(data.sup_code).append('",');
				detail_rows.append('"sup_name":"').append(data.sup_name).append('",');
				detail_rows.append('"fac_name":"').append(data.fac_name).append('",');
				detail_rows.append('"sum_money":').append(data.plan_price*data.amount*num).append('}');
			});
			detail_rows.append("]"); */
    		//alert(detail_rows.toString());
			//添加材料
			//parent.add_rows(eval(detail_rows.toString()));
				
			var detail_rows = new StringBuffer();
			detail_rows.append("[");
			$.each(data, function(index, data){
				if(index != 0){
					detail_rows.append(",");
				}
				detail_rows.append('{"inv_id":').append(data.inv_id).append(',');
				detail_rows.append('"inv_no":').append(data.inv_no).append('}');
			});
			detail_rows.append("]");
			
			var formPara = { 
				num : $("#num").val(),
				store_id : 	store_id,
				store_match_id : liger.get("store_match").getValue(),
				allData : detail_rows.toString()
			};
			//alert(liger.get("store_match").getValue())
			
			ajaxJsonObjectByUrl("queryMatStoreSupportData.do?isCheck=false", formPara, function(responseData) {
				if(responseData.Rows.length > 0){
					parent.add_rows(responseData.Rows);
				}
				this_close();
			});
			
    		// 将选择数据往表格的倒数第一行插入。update.wsj.2017.08.09  
    		/* var theRowDatas = parent.grid.getData(); 
    		for (var i = 0; i < theRowDatas.length; i++) {
    			
				if (theRowDatas[i]&&!theRowDatas[i].inv_id) {
					parent.grid.deleteRow(i);
					theRowDatas =parent.grid.getData();
					i--;
				}
			}
    		parent.grid.addRows(data);
    		parent.is_addRow(); 
			this_close(); */
		}
    }
	
    //关闭
	function this_close(){
		frameElement.dialog.close();
	}
    
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', addNew);
		hotkeys('C', this_close);
	}
	
	function loadDict() {
		
		autocomplete("#store_code","../../../queryMatStore.do?isCheck=false","id","text",true,true);//仓库
        liger.get("store_code").setValue("${store_id}");
        liger.get("store_code").setText("${store_name}");
        $("#store_code").ligerComboBox({disabled:true,cancelable: false});
   		
   		var param = {
        	store_id : store_id
        };
   		autocomplete("#store_match", "../../../queryMatStoreMatch.do?isCheck=false", "id", "text", true, true,param,true);
   		
   		$("#store_match").ligerTextBox({width:160});
   		
		$("#num").ligerTextBox({width:160});
        //格式化按钮
		$("#query").ligerButton({click: query, width:70});
		$("#close").ligerButton({click: this_close, width:70});
		$("#addNew").ligerButton({click: addNew, width:70});
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr></tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>配套表：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="store_match" type="text" requried="true"  id="store_match"  />
	        </td>
	        <td align="left"></td>
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>仓库：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="store_code" type="text" requried="true"  id="store_code"  />
	        </td>
	        <td align="left"></td>
	       
			<td align="right" class="l-table-edit-td" width="10%">
				添加套数：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="num" type="text" id="num" ltype="text" value="1" required="true" validate="{required:true}" />
            </td>
            
           <td>
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
