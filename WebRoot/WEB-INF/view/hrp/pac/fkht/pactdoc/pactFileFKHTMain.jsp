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
	var grid, pact_type,pact;

	function query(pact_type_code,pact_code) {
		var params = [ 
			{name : 'pact_code',value : pact_code},
			{name : 'pact_type_code',value : pact_type_code} 
			]

		grid.loadData(params, 'queryPactFileTypeFKHT.do?isCheck=false')
	}

	var initSelect = function(){
    	var change = true;
		pact_type = $("#pact_type_code").etSelect({
			url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',
			defaultValue: "none",
			 onChange:function(value, $item){
				 if (change) {
					 pact.setValue(0);
				}else{
					change = true
				}
				 pact.reload({url: '../../basicset/select/queryPactFKHTSelect.do?isCheck=false&pact_type_code=' + pact_type.getValue(),})
			 }, 
		 });
    	pact = $("#pact_code").etSelect({
      		url: '../../basicset/select/queryPactFKHTSelect.do?isCheck=false&pact_type_code=' + pact_type.getValue(),
      		defaultValue: "none",
      		onChange:function(value, $item){
      			query(pact_type.getValue(),value);
      			if (!pact_type.getValue()) {
	      			if (value == 0) {return;}
	           		ajaxPostData({
	          		 	url: 'queryPactFKHTType.do?isCheck=false',
	          		 	data:{ pact_code:value },
	          			success: function (result) {
      						change = false;
      						pact_type.setValue(result.pact_type_code);
	          			}
	          		});
				}
      		}
     	});
	}

	var initPactDocGrid = function() {
		var docColumns = [
				{display : '归档 项目',name : 'type_name',width : '15%',editable : false,},
				{display : '文件',name : 'doc_name',width : 260,editable : false,
					render : function(data) {
						var res = "";
						var obj = data.rowData;
						var doc_name = obj.doc_name;
						if(doc_name){
							var file_path = obj.file_path;
							res += '<a class="toView" filePath = "'+file_path+'">'+doc_name+'</a>'
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
					label : '添加归档项目',
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
				$('td:eq(3)', row).attr("height",'20');
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
		var err="";
		$(data).each(function() {
			var rowdata = this;
			rowdata.group_id = ${group_id};
			rowdata.hos_id = ${hos_id};
			rowdata.copy_code = '${copy_code}';
			rowdata.pact_code = pact.getValue();
			rowdata.file_type = rowdata.type_code;
			rowdata.sort_id = rowdata.type_code;
			if(typeof rowdata.doc_id === 'number' && !isNaN(rowdata.doc_id)){
				param.push(rowdata);
			}else{
				err+="["+(rowdata._rowIndx+1)+"]";
			}
		});
		if(err!=""){$.etDialog.error("第"+err+"行合同文档为空，请先选择合同文档");return;}
		if(param.length<=0){ $.etDialog.error("没有要保存的数据");return; }
		ajaxPostData({
			url : 'saveFileDocFKHT.do?isCheck=false',
			data : {
				listVo : JSON.stringify(param)
			},
			success : function() {
				query(pact_type.getValue(),pact.getValue());
			},
			delayCallback : true
		})
	}
	
	 function toView(data){
		 showFile(data);
	 }

	function toResource(data) {
		var obj = grid.getCell(data._rowIndx,data.type_name);
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
				rowdata.group_id = ${group_id};
				rowdata.hos_id = ${hos_id};
				rowdata.copy_code = '${copy_code}';
				rowdata.pact_code = pact.getValue();
				rowdata.file_type = rowdata.type_code;
				param.push(rowdata);
				if((typeof rowdata.doc_id != 'number' && isNaN(rowdata.doc_id)) || rowdata.doc_id=="" ){
 					err+=1;
 				}
			});
			if(err!=0){grid.deleteRows(data);return;}
			$.etDialog.confirm('确定删除?', function() {
				ajaxPostData({
					url : 'deletePactExecFKHTFile.do?isCheck=false',
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
		var pact_type_code = pact_type.getValue();
		if (!pact_type_code) {
			$.etDialog.error("请选择合同类型");return;
		}
		var pact_code = pact.getValue();
		if (!pact_code) {
			$.etDialog.error("请选择合同");return;
		}
		parent.$.etDialog
				.open({
					url : 'hrp/pac/fkht/pactdoc/toPactFKHTFileManagerPage.do?isCheck=false&pact_code='+pact_code+'&pact_type_code='+pact_type_code+'&pact_name='+pact.getText(),
					width : '700px',
					height : '550px',
					title : '归档管理',
					modal : true,
					frameName: window.name
				});
	}
	function docFileManager() {
		var pact_type_code = pact_type.getValue();
		if (!pact_type_code) {
			$.etDialog.error("请选择合同类型");return;
		}
		var pact_code = pact.getValue();
		if (!pact_code) {
			$.etDialog.error("请选择合同");return;
		}
		parent.$.etDialog
				.open({
					url : 'hrp/pac/fkht/pactdoc/toPactFKHTDocManagerPage.do?isCheck=false&pact_code='+pact_code+'&pact_type_code='+pact_type_code+'&pact_name='+pact.getText(),
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
			<td class="label no-empty" style="width: 100px;">合同类型：</td>
			<td class="ipt"><select id="pact_type_code" style="width: 180px;"></select> </td>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt"><select id="pact_code" style="width: 180px;"/></select> </td>
		</tr>
	</table>
	<div id="grid"></div>
</body>

</html>

