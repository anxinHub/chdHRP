<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	var parentData = frameElement.dialog.options.data;
	
	$(function(){
		$("#cert_text").ligerTextBox({width: 160});
		loadGrid();
		query();
	})
	
	//加载证件材料表格
	function loadGrid(){
		grid = $("#grid").ligerGrid({
			columns: [ {
				display: '证件类型', name: 'cert_type_name', align: 'left', width: 140
			},{
				display: '证件编号', name: 'cert_code', align: 'left', width: 140, 
				render: function(rowdata, index, value){
					return "<a href=javascript:edit('"+rowdata.cert_id+"','"+index+"')>"+value+"</a>"
				}
			},{
				display: '产品名称', name: 'prod_name', align: 'left', width: 160
			},{
				display: '生产厂商', name: 'fac_id', textField: "fac_name", align: 'left', width: 140
			},{
				display: '证件日期', name: 'cert_date', align: 'left', width: 160
			},{
				display: '是否新证', name: 'is_new', textField: "is_new_name", align: 'left', width: 70
			},{
				display: '效期状态', name: 'date_state', align: 'left', width: 70,
				render: function(rowdata, index, value){
					if(value == "已过期"){
						return "<span style='color: red'>已过期</span>"
					}
					return value;
				}
			}],
			dataAction: 'server', dataType: 'server', usePager: true, width: '100%', height: '100%', 
			url:'queryMatProdCertChooseList.do?isCheck=false', delayLoad: true, 
			checkbox: true, rownumbers: true, selectRowButtonOnly: false, //heightDiff: 30, 
			toolbar: { items: [{
				text: '查询', id: 'query', icon: 'search', click: query
			},{ line:true },{
				text: '选择', id: 'add', icon: 'save', click: add
			},{ line:true },{
				text: '关闭', id: 'close', icon: 'close', click: thisClose
			}] }
		});
	}
	
	//查询
	function query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name: 'cert_id', value: parentData.cert_id});
		grid.options.parms.push({name: 'old_cert_id', value: parentData.old_cert_id});
		grid.options.parms.push({name: 'new_cert_id', value: parentData.new_cert_id});
		grid.options.parms.push({name: 'fac_id', value: parentData.fac_id});
		grid.options.parms.push({name: 'cert_text', value: $("#cert_text").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//保存证件材料
	function add(){
		var data = grid.getCheckedRows();
		if (data.length != 1){
			$.ligerDialog.error('必须勾选1个证件！');
			return false;
		}
		
		$.ligerDialog.confirm('确定建立新老证关联?', function (yes){
			if(yes){
				var paras = {
					cert_id: parentData.cert_id, 
					cert_info: parentData.cert_info, 
					new_cert_id: data[0].cert_id, 
					new_cert_info: data[0].cert_code + " " + data[0].cert_date
				}
				ajaxJsonObjectByUrl("updateMatProdCertToNewCert.do?isCheck=false", paras, function (responseData){
					if(responseData.state=="true"){
						parentFrameUse().changeNewCert(data[0].cert_id, data[0].cert_code + " " + data[0].cert_date);
						thisClose();
					}
				});
			}
		}); 
	}
	
	//关闭页面
	function thisClose(){
 		frameElement.dialog.close();
	}
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table class="table-layout" >
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				材料信息：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="cert_text" />
			</td>
		</tr>
	</table>
	<div id="grid"></div>
</body>
</html>