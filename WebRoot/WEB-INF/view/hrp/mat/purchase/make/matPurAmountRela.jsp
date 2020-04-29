<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var pur_amount = 0;
    var pur_id = ${pur_id}; 
    var pur_detail_id = ${pur_detail_id};
    var state = ${state};
    var useAudit = '${p04046}'; 
    
    $(function ()
    {
    	loadDict();
    	loadHead(null);	//加载数据
    	query();
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name: 'pur_id', value: pur_id}); 
    	grid.options.parms.push({name: 'pur_detail_id', value: pur_detail_id});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '来源', name: 'rela_type', align: 'left', width: 120, 
				render: function(rowdata, rowindex, value){
					if(value == 'reqDept'){
						
						return "科室需求计划";
					}else if(value == 'reqStore'){
						
						return "仓库需求计划";
					}else if(value == 'app'){
						
						return "科室申请";
					}else{
						
						return "未知来源";
					}
				},
        		totalSummary: {
					type: 'sum',
					render: function (suminf, column, cell) {
						return '<div>合计</div>';
					}
				}
			}, { 
				display: '单据号', name: 'rela_no', align: 'left', width: 120, 
			}, { 
				display: '需求数量', name: 'rela_amount', align: 'left', width: 120, 
				render: function(rowdata, rowindex, value){
					return formatNumber(value ==null ? 0 : value, 2, 1);
				},
			}, { 
				display: '采购数量(E)', name: 'pur_amount', align: 'left', width: 120, 
				editor: {type : 'float'}, 
				render: function(rowdata, rowindex, value){
					return formatNumber(value ==null ? 0 : value, 2, 1);
				},
				totalSummary: {
					type: 'sum',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,2,1)+ '</div>';
					}
				}
			} ],
			dataAction: 'server', dataType: 'server', usePager: false, delayLoad: true, rownumbers: true, 
			enabledEdit : ((state == 2 && useAudit== 0)|| (state==1 && useAudit==1)) ? true : false, isAddRow : false, onAfterEdit: f_onAfterEdit,
			width: '100%', height: '97%', url: 'queryMatPurAmountRela.do?isCheck=false', 
		});
		
        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
	function f_onAfterEdit(e) {
		
		grid.updateTotalSummary();
		return true;
	}

	function loadDict() {
		$("#inv_code").ligerTextBox({width: 160, disabled: true});
		$("#inv_name").ligerTextBox({width: 160, disabled: true});

        //格式化按钮
        if(useAudit==0){
        	if(state != 2){
        		$("#save").ligerButton({click: save, width: 90, disabled: true});
            }else{
        		$("#save").ligerButton({click: save, width: 90});
            }
        }else{
        	if(state != 1){
        		$("#save").ligerButton({click: save, width: 90, disabled: true});
            }else{
        		$("#save").ligerButton({click: save, width: 90});
            }
        }
        
		$("#close").ligerButton({click: this_close, width: 90});
	}
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
	
	function save() {
		grid.endEdit();
		var relaData = gridManager.getData();
		//alert(JSON.stringify(allData)); 
		var rows = 0;
		var msg = '';
		
		$(relaData).each(function(d_index, d_content) {
			var row_index = d_index + 1;
			if(this.pur_detail_id != undefined && this.pur_detail_id != '' && this.pur_detail_id != null){
				rows += 1;
				if (this.pur_amount == '' || this.pur_amount == undefined || parseFloat(this.pur_amount) < 0) {	
					msg += '第' + row_index +'行采购数量不能为空且不能为负数<br>';						
					return false;
				} 
				pur_amount += parseFloat(this.pur_amount);
			}
		});
		
		if(rows == 0){
	    	$.ligerDialog.warn('无对应关系');
	    	return false;
	    }
	    	
	    if(msg != ''){
	    	$.ligerDialog.warn(msg);
	    	return false;
	    }

		var formPara = {
				pur_id: pur_id, 
				pur_detail_id: pur_detail_id, 
				pur_amount: pur_amount, 
				relaData: JSON.stringify(relaData)  //获取所有数据
		};

		ajaxJsonObjectByUrl("updateMatPurAmountRela.do?isCheck=false", formPara, function(responseData) {
			if (responseData.state == "true") {
				parent.updateAmount(${rowindex}, pur_amount);
			}
		});
	}
    
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				材料编码：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_code" type="text" id="inv_code" ltype="text" value="${inv_code}" disabled="disabled" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				材料名称：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_name" type="text" id="inv_name" ltype="text" value="${inv_name}" disabled="disabled" />
			</td>
		</tr>
	</table>
	 <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td">
					<button id ="save" accessKey="S"><b>保存（<u>S</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
