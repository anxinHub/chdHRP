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
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
	
		$("#location_code").ligerTextBox({ width : 160 });
		$("#location_name").ligerTextBox({width : 160});
		
		$("#store_id").ligerTextBox({width : 160});
		$("#location_type_id").ligerTextBox({width : 160});
		$("#location_nature").ligerTextBox({width : 160	});
		

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		grid.options.parms.push({name : 'location_code',value : $("#location_code").val()});
		grid.options.parms.push({name : 'location_name',value : $("#location_name").val()});
		
		grid.options.parms.push({name : 'location_type_id',value : liger.get("location_type_id").getValue().split(",")[0]});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_id").getValue().split(",")[0]});
		grid.options.parms.push({name : 'location_nature',value : $("#location_nature").val()});
		
		
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ {display : '货位ID',name : 'location_id',hide : true	}, 
					            {display : '货位编码',	name : 'location_code',	align : 'left',width : '10%',
									render : function(rowdata, rowindex,value) {
										return "<a href=javascript:openUpdate('"+rowdata.location_code   + "|" + 
										rowdata.location_store_id   + "|" + 
										rowdata.group_id   + "|" + 
										rowdata.hos_id   + "|" + 
										rowdata.copy_code   + "|" + 
										rowdata.acc_year  +"')>"+rowdata.location_code+"</a>"
									}
					            }, 
					            {display : '货位名称',	name : 'location_name',	align : 'left',width : '10%'},
					            {display : '所属库房',name : 'store_name',	align : 'left',width : '15%'},
					            {display : '排位编号',	name : 'grid_no',	align : 'left',width : '10%' }, 
					            {display : '货位分类',name : 'location_type_code',	align : 'left',width : '15%'} , 				             
					            {display : '货位性质', name : 'location_nature',align : 'left',width : '10%'},  
								{display : '是否停用',name : 'is_stop',align : 'left',width : '10%',
					            	render : function(rowdata, rowindex,value) {
										if(value == 0){
											return "否";
										}else{
											return "是";
										}
									}
					            }, 
								{display : '备注',name : 'note',align : 'left',width : '10%'} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryMedLocationDict.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad: true,
					selectRowButtonOnly : true,heightDiff: -30,
					toolbar : { items: [{text : '查询（<u>Q</u>）',	id : 'search',click : query,icon : 'search'	}, 
						          {	line : true	}, 
						          {	text : '添加（<u>A</u>）',	id : 'add',	click : itemclick,icon : 'add'}, 
						          {line : true}, 
						          {text : '删除（<u>D</u>）',id : 'delete',	click : itemclick,icon : 'delete'	}, 
						          {line : true}/* , 
						          {text : '导出Excel（<u>E</u>）',id : 'export',click : exportExcel,icon : 'pager'}, 
								  {line : true}, 
								  {text : '打印（<u>P</u>）',id : 'print',click : printDate,icon : 'print'}, 
								  {line : true}, 
								  {text : '下载导入模板（<u>B</u>）',id : 'downTemplate',click : downTemplate,icon : 'down'}, 
								  {line : true},  
								  {text : '导入（<u>I</u>）',id : 'import',click : imp,icon : 'up'},
						          { line:true }*/
						]
					} ,
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.location_code+ "|" +rowdata.location_store_id + "|"+ "|" +rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.acc_year);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function itemclick(item){ 
        if(item.id){
            switch (item.id){
               
                case "add":
                	$.ligerDialog.open({url: 'medLocationDictAddPage.do?isCheck=false', height: 400,width: 600, 
          				title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
          				buttons: [ 
          				           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedLocationDict(); },cls:'l-dialog-btn-highlight' }, 
          				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
          				         ] 
          			});
                	return;
                case "delete":
                	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){	
                        	
							ParamVo.push(
							//表的主键
									this.location_code   +"@"+ 
									this.location_name   +"@"+ 
									this.is_stop   +"@"+
									this.location_id 
							)
							
                        });
                       
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMedLocationDict.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                	return;
                case "export":
                	return;
                case "print":
                	return;
                case "downTemplate":
                	return;
                case "import":
                	return;
            }
        }
	}
	
	

	
	function imp() {

		parent.$.ligerDialog.open({
			url : 'hrp/med/info/basic/location/dict/medLocationDictImportPage.do?isCheck=false',
			data : {
				columns : grid.columns,
				grid : grid
			},
			height : 300,
			width : 450,
			title : '货位字典导入',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
		
	}
	
	
	
	function downTemplate() {

		location.href = "downTemplate.do?isCheck=false";
	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "location_code="+vo[0] +"&location_store_id="+ 
		vo[1];


		$.ligerDialog.open({ url : 'medLocationDictUpdatePage.do?isCheck=false&' + parm,data:{}, height: 400,width: 600, 
			title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ 
			           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedLocationDict(); },cls:'l-dialog-btn-highlight' }, 
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			         ] });


	}
	function loadDict() {
		//字典下拉框
		$("#location_type_id").ligerComboBox({
           	url: '../../../../queryMedLocationType.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 160,
           	autocomplete: true,
           	initValue : 0,
           	width: 160
  		  });
		
		$("#store_id").ligerComboBox({
           	url: '../../../../queryMedStore.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 160,
           	autocomplete: true,
           	initValue : 0,
           	width: 160
  		  });
		
		
		
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('E', exportExcel);
		hotkeys('P', printDate);
		hotkeys('I', imp);

	}
	//打印数据
	function printDate() {
		//有数据直接打印
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopPrinterTable(
					"resultPrint",
					"开始打印",
					"货位性质 LOCATION_NATURE 1、固定货位 2、自由货位 货位控制方式： 1、不控制 2、提示控制 3、强制控制（入库不可以修改货位）",
					true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara = {
			usePager : false,
			location_id : $("#location_id").val(),
			location_code : $("#location_code").val(),
			location_name : $("#location_name").val(),
			grid_no : $("#grid_no").val(),
			location_type_id : $("#location_type_id").val(),
			store_id : $("#store_id").val(),
			location_nature : $("#location_nature").val(),
			ctrl_type : $("#ctrl_type").val(),
			picker : $("#picker").val(),
			is_stop : $("#is_stop").val(),
			note : $("#note").val()
		};
		ajaxJsonObjectByUrl(
				"queryMedLocationDict.do",
				printPara,
				function(responseData) {
					$.each(responseData.Rows, function(idx, item) {
						var trHtml = "<tr>";
						trHtml += "<td>" + item.location_id + "</td>";
						trHtml += "<td>" + item.location_code + "</td>";
						trHtml += "<td>" + item.location_name + "</td>";
						trHtml += "<td>" + item.grid_no + "</td>";
						trHtml += "<td>" + item.location_type_id + "</td>";
						trHtml += "<td>" + item.store_id + "</td>";
						trHtml += "<td>" + item.location_nature + "</td>";
						trHtml += "<td>" + item.ctrl_type + "</td>";
						trHtml += "<td>" + item.picker + "</td>";
						trHtml += "<td>" + item.is_stop + "</td>";
						trHtml += "<td>" + item.note + "</td>";
						trHtml += "</tr>";
						$("#resultPrint > table > tbody").empty();
						$("#resultPrint > table > tbody").append(trHtml);
					});
					manager.close();
					//alert($("#resultPrint").html())
					lodopPrinterTable(
							"resultPrint",
							"开始打印",
							"货位性质 LOCATION_NATURE 1、固定货位 2、自由货位 货位控制方式： 1、不控制 2、提示控制 3、强制控制（入库不可以修改货位）",
							true);
				}, true, manager);
		return;
	}

	//导出数据
	function exportExcel() {
		//有数据直接导出
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopExportExcel(
					"resultPrint",
					"导出Excel",
					"货位性质 LOCATION_NATURE 1、固定货位 2、自由货位 货位控制方式： 1、不控制 2、提示控制 3、强制控制（入库不可以修改货位）.xls",
					true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara = {
			usePager : false,
			
			location_code : $("#location_code").val(),
			location_name : $("#location_name").val(),
			grid_no : $("#grid_no").val(),
			location_type_id : $("#location_type_id").val(),
			store_id : $("#store_id").val(),
			location_nature : $("#location_nature").val(),
			ctrl_type : $("#ctrl_type").val(),
			picker : $("#picker").val(),
			is_stop : $("#is_stop").val(),
			note : $("#note").val()
		};
		ajaxJsonObjectByUrl(
				"queryMedLocationDict.do",
				exportPara,
				function(responseData) {
					$.each(responseData.Rows, function(idx, item) {
						var trHtml = "<tr>";
						trHtml += "<td>" + item.location_id + "</td>";
						trHtml += "<td>" + item.location_code + "</td>";
						trHtml += "<td>" + item.location_name + "</td>";
						trHtml += "<td>" + item.grid_no + "</td>";
						trHtml += "<td>" + item.location_type_id + "</td>";
						trHtml += "<td>" + item.store_id + "</td>";
						trHtml += "<td>" + item.location_nature + "</td>";
						trHtml += "<td>" + item.ctrl_type + "</td>";
						trHtml += "<td>" + item.picker + "</td>";
						trHtml += "<td>" + item.is_stop + "</td>";
						trHtml += "<td>" + item.note + "</td>";
						trHtml += "</tr>";
						$("#resultPrint > table > tbody").empty();
						$("#resultPrint > table > tbody").append(trHtml);
					});
					manager.close();
					lodopExportExcel(
							"resultPrint",
							"导出Excel",
							"货位性质 LOCATION_NATURE 1、固定货位 2、自由货位 货位控制方式： 1、不控制 2、提示控制 3、强制控制（入库不可以修改货位）.xls",
							true);
				}, true, manager);
		return;
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr></tr>
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">货位编码：</td>
			<td align="left" class="l-table-edit-td">
				<input name="location_code" type="text" id="location_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">货位名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="location_name" type="text" id="location_name" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">所属库房：</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_id" type="text" id="store_id" ltype="text"  />
			</td>
			<td align="left"></td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">货位分类：</td>
			<td align="left" class="l-table-edit-td">
				<input name="location_type_id" type="text" id="location_type_id" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">货位性质：</td>
			<td align="left" class="l-table-edit-td">
				<select id="location_nature" name="location_nature" >
	            		<option value="0">0 固定货位</option>
	            		<option value="1">1 自由货位</option>
	            </select>
			</td>
			<td align="left"></td>
			<td align="left"></td>
			<td align="left"></td>
			<td align="left"></td>
		</tr>
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
