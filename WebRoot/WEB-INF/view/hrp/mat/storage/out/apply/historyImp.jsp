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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var dept_id = "${dept_id}";
    var store_id = "${store_id}";
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_app_date',
			value : $("#begin_app_date").val()
		});
		grid.options.parms.push({
			name : 'end_app_date',
			value : $("#end_app_date").val()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'inv_code',
			value : $("#inv_code").val()
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
	 				display: '材料ID', name: 'inv_id', align: 'left', hide: true, width: 100
	 			},{ 
		 			display: '交易编码', name: 'bid_code', align: 'left', width: 100
		 		},  { 
		 			display: '材料编码', name: 'inv_code', align: 'left', width: 120
		 		}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left', width: 200
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left', width: 120
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left', width: 70
		 		}, { 
		 			display: '价格', name: 'price', align: 'right', width: 90,
		 			render : function(rowdata, rowindex, value) {
						return rowdata.price == null ? "" : formatNumber(value, '${p04006 }', 1);
					}
		 		}, { 
		 			display: '历史申请数量', name: 'history_amount', align: 'right', width: 100, 
		 			render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '数量', name: 'app_amount', align: 'right', width: 90,
		 			type: 'number', editor: { type: 'number'},
		 			render : function(rowdata, rowindex, value) {
						return rowdata.app_amount == null ? 0 : formatNumber(value, 2, 1);
					}
		 		}, { 
     				display : '库存数量', name : 'cur_amount', width : 90, align : 'right'
     			} ,{ 
		 			display: '生产厂家', name: 'fac_name', align: 'left', width: 300
		 		} ],
		 	enabledEdit: true, isAddRow: false, 
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryMatCommonOutApplyHistory.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true, rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
				{ line : true},
				{ text: '生成申请单（<u>A</u>）', id:'add', click: imp, icon:'add' },
				{ line : true},
				{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' },
				{ line : true},
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', imp);
		hotkeys('C', this_close);
	}
    
    function imp(){
    	//var data = gridManager.getData();
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			//$.ligerDialog.error('请配置！');
			$.ligerDialog.warn('请选择材料！');
		}else{
			var num = $("#num").val();
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
				detail_rows.append('"unit_name":"').append(data.unit_name).append('",');
				detail_rows.append('"fac_name":"').append(data.fac_name).append('",');
				detail_rows.append('"app_amount":').append(data.app_amount).append(',');
				detail_rows.append('"price":').append(data.price).append(',');
				detail_rows.append('"is_com":').append(data.is_com).append(',');
				detail_rows.append('"cur_amount":').append(data.cur_amount).append(',');
				detail_rows.append('"amount_money":').append(parseFloat(data.price)*parseFloat(data.app_amount)).append('}');
			});
			detail_rows.append("]");
			//console.log(detail_rows.toString());
			//alert(detail_rows);
			//添加材料
			parent.add_rows(eval(detail_rows.toString()));
			parent.grid.addRow();
			this_close();
		}
    }
    
	function this_close(){
		frameElement.dialog.close();
	}
   
    function loadDict(){
		//字典下拉框
		$("#store_code").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("store_code").setValue("${store_id}");
		liger.get("store_code").setText("${store_text}");
		$("#dept_code").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("dept_code").setValue("${dept_id}");
		liger.get("dept_code").setText("${dept_text}");
		$("#begin_app_date").ligerTextBox({width:100});
    	autodate("#begin_app_date", "yyyy-mm-dd", "before_month");
        $("#end_app_date").ligerTextBox({width:100});
        autodate("#end_app_date", "yyyy-mm-dd", "new");
        $("#inv_code").ligerTextBox({width:237}); 
        
		
        //格式化按钮
		$("#query").ligerButton({click: query, width:70});
		$("#close").ligerButton({click: this_close, width:70});
		$("#add").ligerButton({click: imp, width:70});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
			<td align="right" class="l-table-edit-td"  width="10%">
            	申请日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="begin_app_date" id="begin_app_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_app_date" id="end_app_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				科室：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="dept_code" type="text" id="dept_code" ltype="text" required="true" validate="{required:true}" />
            </td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
				材料信息：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false}" />
            </td>
		</tr>
    </table>
    
	<div id="maingrid"></div>
</body>
</html>
