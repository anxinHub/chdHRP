<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path=request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,datepicker,grid,ligerUI,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var grid;
	
	$(function(){
		loadHead(null); //界面加载，初始化
	});
	//初始化渲染界面，加载数据
	function loadHead(){
		grid=$("#maingrid").etGrid({
			columns: [
				{ display: '单据规则编码', name: 'table_code', align: 'center', width: 130 },
				{ display: '单据名称', name: 'table_name', align: 'center', width: 130 },
				{ display: '前缀', name: 'prefixe', align: 'center', width: 130 },
				{ display: '是否包含年', name: 'is_year_text', align: 'center', width: 110,
					editor: {
						type: 'select',
						keyField: 'is_year',
						source: [
							{ id: '0', label: '否' },{ id: '1', label: '是'}
						],
						keySupport: true,
						autocomplete: true
						//联动变更某一列的值
// 						change: function(rowData, cellData){
// 							if (rowData.is_year == 0){
// 								grid.updateRow(cellData.rowIndx,{ is_year: ""})
// 							}
// 						}
					},
					render: function(ui){
						var value = ui.rowData.is_year;
						if(value == 0){
							return "否";
						}else{
							return "是";
						}
					}
				},
				{ display: '是否包含月', name: 'is_month', align: 'center', width: 110,
					editor: {
						type: 'select',
						keyField: 'is_month',
						source: [
							{ id: '0', label: '否' },{ id: '1', label: '是'}
						],
						keySupport: true,
						autocomplete: true
					},
					render: function(ui){
						var value = ui.rowData.is_month;
						if(value == 0){
							return "否";
						}else{
							return "是";
						}
					}
				},
				{ display: '是否包含日', name: 'is_day_text', align: 'center', width: 110,
					editor: {
						type: 'select',
						keyField: 'is_day',
						source: [
							{ id: '0', label: '否' },{ id: '1', label: '是'}
						],
						keySupport: true,
						autocomplete: true
					},
					render: function(ui){
						var value = ui.rowData.is_day;
						if(value == 0){
							return "否";
						}else{
							return "是";
						}
					}
				},
				{ display: '单据号位数', name: 'seq_no', align: 'center', width: 80 }
			],
			dataModel:{
				method: 'post',
				location: 'remote',
				url:'queryHrNoRule.do?isCheck=false',
				recIndx: 'table_name'
			},
			//加载的界面控制，是否分页，大小，是否有复选框，是否可编辑
			usePage: false, width: '100%', height: '100%', checkbox: true, editable: true,
			toolbar: {
				items: [
		      		{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
		      		{ type: "button", label: '新增', icon: 'add', listeners: [{ click: add }] },
		      		{ type: "button", label: '删除', icon: 'delete', listeners: [{ click: deleteDate }] },
		      		{ type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] }
		      	]
			}
		});
	}
	
	//查询
	function query(){
		var params=[];
		// 获取查询条件 及 值
		params.push({ name: 'rule_message', value: $("#rule_message").val() });
		// 加载查询条件 的 结果
		grid.loadData(params, 'queryHrNoRule.do?isCheck=false');
	}
	
	// 保存
	function save(rowindex,proj_code){
		var data = grid.getChanges(); //获取所有改变
		var formPara=[];
		if(data.updateList.length <= 0){
			$.etDialog.warn('没有需要保存的数据!');
			return;
		}
		var updateData = data.updateList; //获取所有改变列表
		$(updateData).each(function(){   //遍历所有的改变,数组存储
			formPara.push(
				this.table_code + "@" +
				this.table_name + "@" +
				this.prefixe + "@" +
				this.is_year + "@" +
				this.is_month + "@" +
				this.is_day + "@" +
				this.seq_no
			)
		});
		ajaxPostData({
			url: 'savaHrNoRule.do?isCheck=false',
			data: { formPara: formPara.toString()},
			success: function (responseData){
				if(responseData.state == "true"){
					$.etDialog.success('修改保存成功!');
					query();
				}
			}
		});
	}
	
	//新增
	function add(){
		$.etDialog.open({
			url: 'hrNoRuleAddPage.do?isCheck=false',
			width: 600,
			height: 300,
			title: '新增单据号规则',
			btn: ['确定', '取消'],
			btn1:function(index,el){
				var paramVo=[];
				var childWindow=window[el.find('iframe')[0].name] //获取弹窗的 window 对象
				childWindow.addHrNoRule(); //调转到 弹窗对应的界面，执行对应的方法。
				// 不用下面的了，（也是一种方法）。  去 弹窗对应界面，执行 add 请求
				/* paramVo.push({ name: 'table_code', value: childWindow.table_code.value });
				paramVo.push({ name: 'table_name', value: childWindow.table_name.value });
				paramVo.push({ name: 'prefixe', value: childWindow.prefixe.value });
				paramVo.push({ name: 'is_year', value: childWindow.is_year.value });
				paramVo.push({ name: 'is_month', value: childWindow.is_month.value });
				paramVo.push({ name: 'is_day', value: childWindow.is_day.value });
				paramVo.push({ name: 'seq_no', value: childWindow.seq_no.value }); */
			}
		});
	}
	
	//删除
	function deleteDate(){
		var data=grid.selectGet();
		if (data.length == 0){
			$.etDialog.error("请选择需要输出的行!");
			return;
		}
		var paramVo=[];
		//遍历,将选中的行，依次放入 数组
		$(data).each(function (){
			paramVo.push(this.rowData.table_code);
		});
		$.etDialog.confirm('确认要删除记录？',
			function (index, el){
				$.etDialog.close(index);
				ajaxPostData({
					url: 'deleteHrNoRule.do?isCheck=false',
					data: { paramVo: paramVo.toString() }, //stringify() 将 JavaScript 对象转换为 JSON 字符串
					success: function(responseData){
						if(responseData.state == "true"){
							$.etDialog.success('删除成功!');
							query(); //删除成功后刷新
						}
					}
				});
			},
			function (index, el){
				//取消后的代码，  弹窗会自动关闭。
			}	
		);
		
	}
</script>

</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="1-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">业务单据：</td>
				<td class="ipt">
					<input type="text" id="rule_message">
				</td>		
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>