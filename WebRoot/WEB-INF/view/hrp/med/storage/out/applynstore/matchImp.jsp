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
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'dept_match_id',
			value : liger.get("dept_match").getValue() == null ? "" : liger.get("dept_match").getValue()
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
	 				display: '药品ID', name: 'inv_id', align: 'left'
	 			}, { 
		 			display: '药品编码', name: 'inv_code', align: 'left'
		 		}, { 
		 			display: '药品名称', name: 'inv_name', align: 'left'
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left'
		 		}, { 
		 			display: '价格', name: 'price', align: 'right',
		 			render : function(rowdata, rowindex, value) {
						return rowdata.price == null ? "" : formatNumber(rowdata.price, '${p08006 }', 1);
					}
		 		}, { 
		 			display: '数量', name: 'app_amount', align: 'right',
		 			render : function(rowdata, rowindex, value) {
						return rowdata.app_amount == null ? "" : formatNumber(rowdata.app_amount, 2, 1);
					}
		 		} ],
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryMedCommonOutApplyMatch.do?isCheck=false',
			width: '100%', height: '100%', checkbox: false, rownumbers:true,
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
    	var data = gridManager.getData();
		if (data.length == 0){
			$.ligerDialog.error('请配置！');
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
				detail_rows.append('"app_amount":').append(data.app_amount*num).append(',');
				detail_rows.append('"price":').append(data.price).append(',');
				detail_rows.append('"is_com":').append(data.is_com).append(',');
				detail_rows.append('"amount_money":').append(data.price*data.app_amount*num).append('}');
			});
			detail_rows.append("]");
			console.log(detail_rows.toString());
			//alert(detail_rows);
			//添加药品
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
		autocompleteAsync("#dept_match", "../../../queryMedDeptMatch.do?isCheck=false", "id", "text", true, true, {dept_id:dept_id.split(",")[0],store_id:store_id.split(",")[0]}, true);
        $("#num").ligerTextBox({width:160});
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
			<td align="right" class="l-table-edit-td"  width="10%">
				配套表：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="dept_match" type="text" id="dept_match" ltype="text" required="true" validate="{required:true,maxlength:100}" />
            </td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" >
				添加套数：
			</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="num" type="text" id="num" ltype="text" value="1" required="true" validate="{required:true}" />
            </td>
        </tr> 
    </table>
    
	<div id="maingrid"></div>
</body>
</html>
