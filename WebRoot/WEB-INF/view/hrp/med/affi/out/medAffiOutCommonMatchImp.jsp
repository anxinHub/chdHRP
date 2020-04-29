<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	$(function() {
		
		loadDict()//加载下拉框
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
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]});
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]});
		
    	grid.options.parms.push({name : 'store_match_id',value : liger.get("store_match").getValue()}); 
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ {display: '药品ID', name: 'inv_id', align: 'left'},
		                 { display: '药品编码', name: 'inv_code', align: 'left'  },
						 { display: '药品名称', name: 'inv_name', align: 'left'  },
						 { display: '规格型号', name: 'inv_model', align: 'left'},
						 { display: '计量单位', name: 'unit_name', align: 'left'},
						 
						 { display: '价格', name: 'price', align: 'right' , 
							 render:function(rowdata){
				            		return formatNumber(rowdata.price ==null ? 0 : rowdata.price,'${p08006}',1);
				            } 	 
						 },
						 { display: '数量', name: 'amount', align: 'right' ,
							 render:function(rowdata){
				            		return formatNumber(rowdata.amount ==null ? 0 : rowdata.amount,2,1);
				            } 	 
						 }
					
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,
		                 url:'queryMedAffiOutMatchDetail.do?isCheck=false',
		                 width: '95%', height: '80%', checkbox: false, rownumbers:true,delayLoad:true,
		                 selectRowButtonOnly:true, isScroll :true 
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function getSelectRows() {
		var rows = grid.getCheckedRows();
		return rows;
	}
	//获得选中数据
	function addNew(){
		var detail_data = gridManager.rows;
    	var para={
			store_id : liger.get("store_code").getValue().split(",")[0], 
			num : $("#num").val(), 
			detail_data : JSON.stringify(detail_data)
        };
 		
        ajaxJsonObjectByUrl("queryMedAffiOutDetailByMatch.do?isCheck=false",para,function(responseData){
        	if(responseData.Rows.length > 0){
				//订单药品
				parent.add_Rows(responseData.Rows);
	        	parent.$("#is_dir").val('0');
			}
        	this_close();
        });
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
		$("#store_code").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("store_code").setValue("${store_id}");
		liger.get("store_code").setText("${store_text}");
		autocomplete("#store_match", "../../queryMedDeptMatch.do?isCheck=false", "id", "text", true, true,"",true);
		
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
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				配套表：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_match" type="text" id="store_match" ltype="text" required="true" validate="{required:true,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" width="10%">
				添加套数：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="num" type="text" id="num" ltype="text" value="1" required="true" validate="{required:true}" />
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
					<button id ="addNew" accessKey="A"><b>生出出库单（<u>A</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
