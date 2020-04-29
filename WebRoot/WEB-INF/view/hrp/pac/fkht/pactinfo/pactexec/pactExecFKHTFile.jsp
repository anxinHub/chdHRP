<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param
		value="hr,tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox,pageOffice"
		name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js"
	type="text/javascript"></script>
<!-- script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script-->

<script type="text/javascript">
	var grid;

	var initSelect = function() {
		pact_type_code = $("#pact_type_code").etSelect({
			url : '../../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',
			defaultValue : '${pact_type_code}'
		});
	}

	function query() {
		var params = [ 
			{name : 'pact_code',value : $("#pact_code").val()},
			{name : 'pact_type_code',value : $("#pact_type_code").val()} 
			]

		grid.loadData(params, '../../pactdoc/queryPactFileTypeFKHT.do?isCheck=false')
	}

	
	var initPactDocGrid = function() {
		var docColumns = [
				{display : '归档项目',name : 'type_name',width : '15%',editable : false},
				{display : '文件',name : 'doc_name',width : 260,editable : false,
					render : function(data) {
						var res = "";
						var obj = data.rowData;
						var doc_name = obj.doc_name;
						if(doc_name){
							var file_path = obj.file_path.split(",");
							var name = doc_name.split(",");
							for(var i = 0;i<name.length;i++){
								res += '<a class="toView" filePath = "'+file_path[i]+'">'+name[i]+'</a><br />'
							}
						}
						return res;
					}
				},
				{display : '选择',name : 'select',width : 120,editable : false,
					render : function(data) {
						return '<a class="getSouse" rowIndex = "'+data.rowIndx+'">文件选择</a>'
					}
				}, 
				{display : '备注',name : 'note',align : 'center',width : '20%',editable : true} 
				];
		var paramObj = {
			height: '100%',
            inWindowHeight: true,
			editable : true,
			checkbox : true,
			usePager : false,
			columns : docColumns,
			load: onAfterShowData,
			toolbar : {
				items : [ {
					type : 'button',
					label : '添加归档',
					listeners : [ {
						click : addFile
					} ],
					icon : 'add'
				}, {
					type : 'button',
					label : '删除',
					listeners : [ {
						click : remove
					} ],
					icon : 'close'
				},  {
					type : 'button',
					label : '保存',
					listeners : [ {
						click : save
					} ],
					icon : 'save'
				}, {
					type : 'button',
					label : '文档管理',
					listeners : [ {
						click : docFileManager
					} ],
					icon : 'add'
				} ]
			}
		};
		grid = $("#grid").etGrid(paramObj);

		$("#grid").on('click', '.getSouse', function() {
			var rowIndex = $(this).attr('rowIndex');
			var currentRowData = grid.getAllData()[rowIndex];
			toResource(currentRowData);
		})
		$("#grid").on('click', '.toView', function() {
			var filePath = $(this).attr('filePath');
			toView(filePath);
		})
	};
	
	 var onAfterShowData =  function(){
      	 $('#grid .l-grid-body-table tbody').rowspan();
      }
	
	 //合并单元格
    jQuery.fn.rowspan = function (){
    	if (!$('.pq-cont-inner').find('tbody')[1]) {
			return;
		}
    	tableObj = $('.pq-cont-inner').find('tbody')[1].rows;
    	
   		var that = tableObj[1];
    	for (var i = 1; i < tableObj.length; i++) {
    		var row = tableObj[i + 1];
    		if ($('td:eq(2)', row).html() == $('td:eq(2)', that).html()) {
    			rowspan = $('td:eq(2)', that).attr("rowSpan");
				if(rowspan == undefined){
					$('td:eq(2)', that).attr("rowSpan",1);
					rowspan = $('td:eq(2)', that).attr("rowSpan");
				}
				rowspan = Number(rowspan) + 1;
				$('td:eq(2)', that).attr("rowSpan",rowspan);
				$('td:eq(2)', row).hide();
				$('td:eq(3)', row).attr("height",'17');
				$('td:eq(4)', that).attr("rowSpan",rowspan);
				$('td:eq(4)', row).hide();
				$('td:eq(5)', that).attr("rowSpan",rowspan);
				$('td:eq(5)', row).hide();
			}else{
				that = row;
			}
		}
    } 
	 
	function save(){
		var data = grid.getAllData();
		var param = [];
		var err = "";
		$(data).each(function() {
			var rowdata = this;
			rowdata.group_id = ${group_id};
			rowdata.hos_id = ${hos_id};
			rowdata.copy_code = '${copy_code}';
			rowdata.pact_code = '${pact_code}';
			rowdata.file_type = rowdata.type_code;
			param.push(rowdata);
			if(typeof rowdata.doc_id != 'number' && isNaN(rowdata.doc_id)){
				err+="["+(rowdata._rowIndx+1)+"]";
			}
		});
		if(param.length<=0){$.etDialog.error("没有要保存的数据");return;}
		if(err!=""){$.etDialog.error("第"+err+"行合同文档为空，请先选择合同文档");return;}
		ajaxPostData({
			url : '../../pactdoc/saveFileDocFKHT.do?isCheck=false',
			data : {
				listVo : JSON.stringify(param)
			},
			success : function() {
				query();
			}
		})
	}
	
	 function toView(data){
		 showFile(data);
	 }

	function toResource(data) {
		parent.$.etDialog.open({
					url : 'hrp/pac/fkht/pactdoc/toPactFKHTSelectDocPage.do?isCheck=false&pact_code='
							+ $("#pact_code").val()
							+ '&pact_type_code=' + $("#pact_type_code").val()
							+ "&file_type=" + data.type_code +"&_rowIndx=" + data._rowIndx + "&type_code="+data.type_code,
					width : $(window).width(),
					height : '450px',
					title : '文件选择',
					modal : true,
					frameName: window.name
				});
	}

	function remove() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {
			var param = [];
			var err = 0;
			$(data).each(function() {
				var rowdata = this.rowData;
				if (rowdata._status != 'add') {
					rowdata.group_id = ${group_id};
					rowdata.hos_id = ${hos_id};
					rowdata.copy_code = '${copy_code}';
					rowdata.pact_code = '${pact_code}';
					rowdata.file_type = rowdata.type_code;
					param.push(rowdata);
				}
				if(typeof rowdata.doc_id != 'number' && isNaN(rowdata.doc_id)){
					err+=1;
				}
			});
			if(err!=0){grid.deleteRows(data);return;}
			$.etDialog.confirm('确定删除?', function() {
				ajaxPostData({
					url : '../../pactdoc/deletePactExecFKHTFile.do?isCheck=false',
					data : {
						listVo : JSON.stringify(param)
					},
					success : function() {
						grid.deleteRows(data);
						onAfterShowData();
					}
				})
			});
		}
	}

	function addFile() {
		parent.$.etDialog
				.open({
					url : 'hrp/pac/fkht/pactdoc/toPactFKHTFileManagerPage.do?isCheck=false&pact_code=${pact_code}&pact_type_code=${pact_type_code}&pact_name=${pact_name}',
					width : '450px',
					height : '250px',
					title : '归档管理',
					modal : true,
					frameName: window.name
				});
	}
	function docFileManager() {
		parent.$.etDialog
				.open({
					url : 'hrp/pac/fkht/pactdoc/toPactFKHTDocManagerPage.do?isCheck=false&pact_code=${pact_code}&pact_type_code=${pact_type_code}&pact_name=${pact_name}',
					width : $(window).width(),
					height : '450px',
					title : '文档管理',
					modal : true,
					frameName: window.name
				});
	}

	$(function() {
		initSelect();
		initPactDocGrid();
		query()
	})
</script>
</head>

<body style="overflow: scroll;">

	<table class="table-layout">
		<tr>
			<td class="label no-empty" style="width: 100px;">合同编号：</td>
			<td class="ipt"><input id="pact_code" type="text"
				disabled="disabled" value="${pact_code }" /></td>
			<td class="label no-empty" style="width: 100px;">合同类型：</td>
			<td class="ipt"><select id="pact_type_code"
				style="width: 180px;" disabled="disabled"></select></td>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt" colspan="3"><input id="pact_name" type="text"
				style="width: 94%;" value="${pact_name }" disabled="disabled" /></td>
		</tr>
	</table>
	<div id="grid"></div>
</body>

</html>

