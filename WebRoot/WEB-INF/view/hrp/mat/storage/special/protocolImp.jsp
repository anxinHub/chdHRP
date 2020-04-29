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
			name : 'protocol_id',
			value : liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue()
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
	 				display: '材料ID', name: 'inv_id', align: 'left'
	 			}, { 
		 			display: '材料编码', name: 'inv_code', align: 'left'
		 		}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left'
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left'
		 		}, { 
		 			display: '协议价格', name: 'price', align: 'right',
		 			render : function(rowdata, rowindex, value) {
						return rowdata.price == null ? "" : formatNumber(rowdata.price, '${p04006}', 1);
					}
		 		}, { 
		 			display: '数量', name: 'amount', align: 'right',
		 			render : function(rowdata, rowindex, value) {
						return rowdata.amount == null ? "" : formatNumber(rowdata.amount, 2, 1);
					}
		 		},
				{ 
		 			
		 			display : '是否个体码', name : 'is_per_bar', textField : 'text', width : 0, align : 'left'
		 			
		 		} 
				, { 
		 			
		 			display : '是否高值', name : 'is_highvalue', textField : 'text', width : 0, align : 'left'
		 			
		 		} 
		 		],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatCommonInProtocol.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,heightDiff: -30,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
				{ line : true},
				{ text: '生成入库单（<u>A</u>）', id:'add', click: imp, icon:'add' },
				{ line : true},
				{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' },
				{ line : true},
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
        grid.toggleCol("is_per_bar", false);
        grid.toggleCol("is_highvalue", false);
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
			$.ligerDialog.error('该协议没有明细记录！');
		}else{
			//添加材料
			parent.add_rows(data);
			this_close();
		}
    }
    
	function this_close(){
		frameElement.dialog.close();
	}
   
    function loadDict(){
		//字典下拉框
		$("#sup_code").ligerComboBox({width:300,disabled:true,cancelable: false});
        liger.get("sup_code").setValue("${sup_id}");
		liger.get("sup_code").setText("${sup_text}");
        $("#protocol_code").ligerTextBox({width:320,disabled:true,cancelable: false});
        
		$("#sup_code").ligerTextBox({width:300});
       
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
			<td align="right" class="l-table-edit-td"  width="10%">供应商：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">协议：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="protocol_code" type="text" id="protocol_code" value="${protocol_id}" ltype="text" required="true" validate="{required:true}" />
            </td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
