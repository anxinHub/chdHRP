<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<link href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
	<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js" type="text/javascript"></script>
	<script type="text/javascript">
		var grid;
		var gridManager = null;
		var userUpdateStr;
		//打印 单元格格式化 用
		var renderFunc = {

			is_pre: function (value) {
				if (value == 0) {
					return "否";
				} else {
					return "是"
				}
			},
			is_rec: function (value) {
				if (value == 0) {
					return "否";
				} else {
					return "是"
				}
			},
			is_stop: function (value) {
				if (value == 0) {
					return "否";
				} else {
					return "是"
				}
			}
		};
		$(function () {
			loadDict()//加载下拉框
			//加载数据
			loadHead();
			loadHotkeys();
			layout();
			loadTree();
		});
		//查询
		function query() {
			grid.options.parms = [];
			grid.options.newPage = 1;
			//根据表字段进行添加查询条件
			//加载查询条件
			grid.loadData(grid.where);
		}

		function loadHead() {
			grid = $("#maingrid").ligerGrid({
				columns: [
					{
						display: '函数编码', name: 'fun_code', align: 'left', width: 80,
						render: function (rowdata, rowindex, value) {
							return "<a href=javascript:openUpdate('" + rowdata.group_id + "|" + rowdata.hos_id + "|" + rowdata.copy_code + "|" + rowdata.fun_code + "')>" + rowdata.fun_code + "</a>";
						}
					},
					{
						display: '函数名称', name: 'fun_name', align: 'left', width: 80
					},
					{
						display: '函数分类编码', name: 'type_code', align: 'left', width: 100
					},
					{
						display: '是否执行', name: 'is_pre', align: 'left', width: 80,
						render: function (rowdata, rowindex,
							value) {
							if (rowdata.is_rec == 0) {
								return "否";
							} else {
								return "是"
							}
						}
					},
					{
						display: '是否递归', name: 'is_rec', align: 'left', width: 80,
						render: function (rowdata, rowindex,
							value) {
							if (rowdata.is_rec == 0) {
								return "否";
							} else {
								return "是"
							}
						}
					},
					{
						display: '是否停用', name: 'is_stop', align: 'left', width: 80,
						render: function (rowdata, rowindex,
							value) {
							if (rowdata.is_stop == 0) {
								return "否";
							} else {
								return "是"
							}
						}
					},
					{
						display: '取值函数(中文)', name: 'fun_method_chs', align: 'left'
					}

				],
				dataAction: 'server', dataType: 'server', usePager: true, url: 'queryBudgFun.do',
				height: '100%', width: "100%", checkbox: true, rownumbers: true,
				selectRowButtonOnly: true,//heightDiff: -10,
				toolbar: {
					items: [
						{ text: '查询（<u>Q</u>）', id: 'search', click: query, icon: 'search' },
						{ line: true },
						{ text: '添加（<u>A</u>）', id: 'add', click: add_open, icon: 'add' },
						{ line: true },
						{ text: '重新加载存储过程', id: 'initProc', click: initProc, icon: 'add' },
						{ line: true },
						{ text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' },
						{ line: true }/*, 
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true },
		                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' } */
					]
				},
				onDblClickRow: function (rowdata, rowindex, value) {
					openUpdate(
						rowdata.group_id + "|" +
						rowdata.hos_id + "|" +
						rowdata.copy_code + "|" +
						rowdata.fun_code
					);
				},
				/*  onSelectRow :function (rowdata, rowindex, rowobj){
					 loadComType(rowdata.fun_code);
					 setFunNote(rowdata) ;
				 } */
			});

			gridManager = $("#maingrid").ligerGetGridManager();
		}


		//打印回调方法
		function lodopPrint() {
			var head = "";
			grid.options.lodop.head = head;
			grid.options.lodop.fn = renderFunc;
			grid.options.lodop.title = "取值函数";
		}


		/* function setFunNote(rowdata){
			
			$("#fun_note").val(rowdata.fun_note);
		} */
		function add_open() {

			parent.$.ligerDialog.open({
				url: 'hrp/budg/common/budgfun/budgFunAddPage.do?isCheck=false',
				height: $(window).height(), width: $(window).width(), title: '预算相关函数添加', modal: true, showToggle: false,
				showMax: true, showMin: false, isResize: true,
				parentframename: window.name,
			});
		}

		function remove() {

			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				var ParamVo = [];
				$(data).each(function () {
					ParamVo.push(
						this.group_id + "@" +
						this.hos_id + "@" +
						this.copy_code + "@" +
						this.fun_code
					)
				});
				$.ligerDialog.confirm('确定删除?', function (yes) {
					if (yes) {
						ajaxJsonObjectByUrl("deleteBudgFun.do", { ParamVo: ParamVo.toString() }, function (responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
					}
				});
			}
		}
		function imp() {

			var index = layer.open({
				type: 2,
				title: '取值函数',
				shadeClose: false,
				shade: false,
				maxmin: true, //开启最大化最小化按钮
				area: ['893px', '500px'],
				content: 'budgFunImportPage.do?isCheck=false'
			});
			layer.full(index);
		}
		function downTemplate() {

			location.href = "downTemplate.do?isCheck=false";
		}

		function openUpdate(obj) {

			var vo = obj.split("|");
			var parm =
				"group_id=" + vo[0] + "&" +
				"hos_id=" + vo[1] + "&" +
				"copy_code=" + vo[2] + "&" +
				"fun_code=" + vo[3]
			parent.$.ligerDialog.open({
				url: 'hrp/budg/common/budgfun/budgFunUpdatePage.do?isCheck=false&' + parm, data: {},
				height: 500, width: 800, title: '预算相关函数修改', modal: true, showToggle: false, showMax: true,
				showMin: false, isResize: true,
				parentframename: window.name,
			})
		}
		function layout() {
			$("#layout1").ligerLayout({ topHeight: 30, leftWidth: 260 });
		}
		//重新加载存储过程
		function initProc() {
			$.ligerDialog.confirm('确定重新加载?', function (yes) {
				if (yes) {
					ajaxJsonObjectByUrl("initBudgFunProc.do?isCheck=false", null, function (responseData) {
						if (responseData.state == "true") {
						}
					});
				}
			});
		}

		function loadDict() {
			//字典下拉框

			autocomplete("#type_code", "../../qureyBudgFunType.do?isCheck=false", "id", "text", true, true);
		}
		//键盘事件
		function loadHotkeys() {

			hotkeys('Q', query);

			hotkeys('A', add);
			//hotkeys('D', remove);

			//hotkeys('B', downTemplate);

			//hotkeys('P', printDate);
			//hotkeys('I', imp);


		}

		function onSelect(event, treeid, treeNode) {
			grid.options.parms = [];
			//根据表字段进行添加查询条件
			grid.options.parms.push({ name: 'type_code', value: treeNode.id });

			//加载查询条件
			grid.loadData(grid.where);
		}
		// 函数类型树
		function loadTree() {
			ajaxJsonObjectByUrl("queryFunTypeTree.do?isCheck=false", {},
				function (responseData) {
					if (responseData.Rows.length != 0) {
						tree = $.fn.zTree.init($('#typeTree'), {
							checkbox: false,
							data: {
								key: {
									name: 'text'
								}
							},
							simpleData: {
								enable: true,
								idKey: "id",
								pIdKey: "pId"
							},
							callback: {
								onClick: onSelect
							}
						}, responseData.Rows);

					}
				}
			);
		}

/* 	 function loadComType(obj){ 
		 var data = gridManager.getCheckedRows();
         if (data.length == 1){
        	 ajaxJsonObjectByUrl("../budgfunpara/queryComTypePara.do?isCheck=false&fun_code="+obj, {},
 	    			
 	    			function(responseData) {
 	    			
 	    					if (responseData != null) {

 	    						var obj = eval(responseData.Rows);
 	    						
 	    						fun_para_code = obj;

 	    						var iw = $("#iw");
 	    						
 	    						iw.find("tr").remove();
 	    						
 	    						for(var i=0;i<obj.length;i++){ 

 	    							if(obj[i].com_type_nature == "text"){

 	    								iw.append('<tr class="l-table-edit-tr"><td align="right" class="l-table-edit-td">'+obj[i].para_name+'：</td>'+
 	    		    		            		'<td align="right" class="l-table-edit-td">'+
 	    		    		            		'<input name="'+obj[i].para_code+'" type="text" id="'+obj[i].para_code+'" ltype="text" validate="{required:true,maxlength:20}" />'+
 	    		    		            		'</td></tr>');
 	    		    		        	
 	    		    		        	loadTextStyle(obj[i].para_code);
 	    		    		        	
 	    		    		        }else if(obj[i].com_type_nature == "input"){
 	    		    		        	
 	    		    		        	iw.append('<tr class="l-table-edit-tr"><td align="right" class="l-table-edit-td">'+obj[i].para_name+'：</td>'+
 	    		    		            		'<td align="right" class="l-table-edit-td">'+
 	    		    		            		'<input name="'+obj[i].para_code+'" type="text" id="'+obj[i].para_code+'" ltype="text" validate="{required:true,maxlength:20}" />'+
 	    		    		            		'</td></tr>');   
 	    		    		        	loadTextStyle(obj[i].para_code);
 	    		    		        	loadComBoBox(obj[i].para_code);
 	    		    		        	
 	    		    		        }else if(obj[i].com_type_nature == "date"){
 	    		    		        	
 	    		    		        	iw.append('<tr class="l-table-edit-tr"><td align="right" class="l-table-edit-td">'+obj[i].para_name+'：</td>'+
 	    		    		            		'<td align="right" class="l-table-edit-td">'+
 	    		    		            		'<input name="'+obj[i].para_code+'" type="text" id="'+obj[i].para_code+'" ltype="text" validate="{required:true,maxlength:20}" />'+
 	    		    		            		'</td></tr>'); 
 	    		    		        	loadTextStyle(obj[i].para_code);
 	    		    		        	loadDate(obj[i].para_code);
 	    		    		        	
 	    		    		        }
 	    							
 	    						} 

 	    					}
 	    					
 	    				});
         }
	 }
	function loadTextStyle(fc){
		
		$("#"+fc).ligerTextBox({width:160 });
		
	}
	function loadDate(fc) {
		autoCompleteByData("#"+fc,para_date.Rows,"para_data_code", "para_data_name", true, true, "");
			
	}
	function loadComBoBox(fc) {
		
		//--指标编码
    	$("#"+fc).ligerComboBox({
    		parms : {"para_code":fc},
         	url: '../funparamethod/queryFunParaByDict.do?isCheck=false',
         	valueField: 'ID',
          	textField: 'TEXT', 
          	selectBoxWidth: 160,
         	autocomplete: true,
         	width: 160,
		});
	} */
	</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout1" style="width:100%; margin:40px;  height:400px;margin:0; padding:0;">
		<div position="left" title="">
			<div>
				<table>
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
							<b>函数类型:</b>
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="type_code" type="text" id="type_code" ltype="text" />
						</td>
					</tr>
				</table>
			</div>
			<div id="typeTree" class="ztree"></div>
		</div>
		<div position="center">
			<div id="toptoolbar"></div>
			<div id="maingrid"></div>
		</div>

		<!-- <div position="centerbottom">
          		<h3>函数说明:</h3><input name="fun_note" type="text" id="fun_note" ltype="text" style="border: 0" />
          		<form name="form1" method="post" id="form1">
          		<input type="hidden" id="target_code" name="target_code" value="" />
					<table cellpadding="0" cellspacing="0" class="l-table-edit" id="iw">
						
					</table>
				</form>
				
          	</div>   -->
	</div>
</body>

</html>