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
    var searchType = {id:'sum'};
    
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据

    	$("#in_dept_id").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#out_dept_id").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
        
    });
    
    //查询
    function query(item){
    	searchType.id = item.id;
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
    	
    	grid.options.parms.push({name:'ass_id',value:liger.get("ass_id").getValue().split("@")[0]});
    	
    	grid.options.parms.push({name : 'out_dept_id',value : '${out_dept_id}'});
		grid.options.parms.push({name : 'out_dept_no',value : '${out_dept_no}'});

		
    	grid.options.parms.push({name:'ass_card_no',value:$('#ass_card_no').val()});
    	
    	
     	 
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
		
	}
    
 
    function loadHead(){
    	var dept_id = liger.get("out_dept_id").getValue().split("@")[0];
		var dept_no = liger.get("out_dept_id").getValue().split("@")[1];
		var ass_id = liger.get("ass_id").getValue().split("@")[0];
		
		if (dept_no == null || dept_id == null || dept_id == ""
			|| dept_no == "") {
			dept_no = "";
			dept_id = "";
		}
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                      {
					display : '卡片编码',
					name : 'ass_card_no',
					align : 'left',
					width : 120
				},{
					display : '原始卡片编码',
					name : 'ass_ori_card_no',
					align : 'left',
					width : 120
				}, {
					display : '资产编码',
					name : 'ass_code',
					align : 'left',
					width : 120
				}, {
					display : '资产名称',
					name : 'ass_name',
					align : 'left',
					width : 120
				}, {
					display : '资产规格',
					name : 'ass_spec',
					align : 'left',
					width : 120
				}, {
					display : '资产型号',
					name : 'ass_model',
					align : 'left',
					width : 120
				}, {
					display : '资产品牌',
					name : 'ass_brand',
					align : 'left',
					width : 120
				}, {
					display : '计量单位',
					name : 'ass_unit_name',
					align : 'left',
					width : 80
				}, {
					display : '生产厂商',
					name : 'fac_name',
					align : 'left',
					width : 120
				}, {
					display : '供应商',
					name : 'ven_name',
					align : 'left',
					width : 120
				}, {
					display : '资产原值',
					name : 'price',
					align : 'left',
					width : 120,
					render : function(rowdata, rowindex,value) {
						return formatNumber(
						rowdata.price == null ? 0: rowdata.price,'${ass_05006}',1);
						}

				}, {
					display : '累计折旧',
					name : 'depre_money',
					align : 'left',
					width : 120,
					render : function(rowdata, rowindex,value) {
						return formatNumber(
						rowdata.depre_money == null ? 0: rowdata.depre_money,'${ass_05005}',1);
						}

				}, {
					display : '累计分摊',
					name : 'manage_depre_money',
					align : 'left',
					width : 120,
					render : function(rowdata, rowindex,value) {
						return formatNumber(
						rowdata.manage_depre_money == null ? 0: rowdata.manage_depre_money,'${ass_05005}',1);
						}

				}, {
					display : '资产净值',
					name : 'cur_money',
					align : 'left',
					width : 120,
					render : function(rowdata, rowindex,value) {
						return formatNumber(
						rowdata.cur_money == null ? 0: rowdata.cur_money,'${ass_05006}',1);
						}

				}, {
					display : '预留残值',
					name : 'fore_money',
					align : 'left',
					width : 120,
					render : function(rowdata, rowindex,value) {
						return formatNumber(
						rowdata.fore_money == null ? 0: rowdata.fore_money,'${ass_05006}',1);
						}

				}, {
					display : '累计折旧月份',
					name : 'add_depre_month',
					align : 'left',
					width : 80
				}, {
					display : '累计分摊月份',
					name : 'add_manage_month',
					align : 'left',
						width : 80
				}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../../../queryAssCardTable.do?isCheck=false&ass_nature=05&use_state=1,2,3,4,5'
 						+'&is_dept=1&dept_id='+dept_id+'&dept_no='+dept_no,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { 
                    	 items: [
                            { text: '查询', id:'sum', click: query,icon:'search' },
                            { line:true },
                            { text: '生成', id:'detail', click: save,icon:'add' },
                            { line : true },
                            { text : '关闭',id : 'close',click : this_close,icon : 'candle'}
                         ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    function save(){
		var data = gridManager.getCheckedRows();
		if (data.length === 0) {
			$.ligerDialog.error('请选择行')
			return;
		}

		// 删除父级表格空行
		var emptyRowIndexArr = [];
		var parentGridData = parent.grid.getData();
		parentGridData.forEach(function (item, index) {
			if (!item.ass_card_no) {
				emptyRowIndexArr.push(index)
			}
		})
		for (var i = emptyRowIndexArr.length - 1; i >= 0; i--) {
			parent.grid.deleteRow(emptyRowIndexArr[i])
		}
		parent.grid.appendRow(data);
		parent.grid.addRow();
		this_close();
		
	}
	function this_close() {
		frameElement.dialog.close();
	}
    //字典下拉框
    function loadDict(){
    
    	$("#in_dept_id").ligerComboBox({
			url : '../../../queryDeptDict.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			autocomplete : true,
			width : 160,
			onSelected : function(id, text) {
			}
		});
		
		liger.get("in_dept_id").setValue("${in_dept_id}@${in_dept_no}");
		liger.get("in_dept_id").setText("${in_dept_code} ${in_dept_name}");
		
		$("#out_dept_id").ligerComboBox({
			url : '../../../queryDeptDict.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			autocomplete : true,
			width : 160,
			onSelected : function(id, text) {
			}
		});
		
		liger.get("out_dept_id").setValue("${out_dept_id}@${out_dept_no}");
		liger.get("out_dept_id").setText("${out_dept_code} ${out_dept_name}");
   
		$("#ass_card_no").ligerTextBox({width:180});
		
		autocomplete("#ass_id","../../../queryAssNoDict.do?isCheck=false","id","text",true,true);
    
    } 
    //键盘事件
	function loadHotkeys() {
	}
 	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_id" type="text" id="ass_id"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">卡片号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_card_no" type="text" id="ass_card_no"  /></td>
            <td align="left"></td>
        </tr>
        <tr>
		  
		  	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">移出科室：</td>
			<td align="left" class="l-table-edit-td"><input
				name="out_store_id" type="text" id="out_dept_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">移入科室：</td>
			<td align="left" class="l-table-edit-td"><input
				name="in_store_id" type="text" id="in_dept_id" /></td>
        </tr> 
     
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
