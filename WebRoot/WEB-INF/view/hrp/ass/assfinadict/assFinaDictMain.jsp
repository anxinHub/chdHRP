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
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<style type="text/css">
.tree {
	width: auto;
	height: 800px;
	/* border: 1px solid #ccc; */
	overflow: auto;
	position: relative;
}
</style>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	 var menu; 
	var tree;
	var note;
	var actionNodeID;
	var setting = {
    		view: {
    			dblClickExpand: false
    		},
    		data: {
    			simpleData: {
    				enable: true
    			}
    		},
    		callback: {
    			/*beforeClick: beforeClick,*/
    			onClick: onSelect,
    			onDblClick: onSelect
    		}
    	};
	$(function() {
		$("#layout1").ligerLayout({ leftWidth: 235,
    		//heightDiff: -8,
    		//每展开左边树即刷新一次表格，以免结构混乱
    		onLeftToggle:function(){			
        		grid._onResize();
            },
        //每调整左边树宽度大小即刷新一次表格，以免结构混乱
        	onEndResize:function(a,b){    
				grid._onResize();
            }	
 		}); 
		
		
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadTree();
		 $('.tree').height($('.l-layout-left').height()-100);
		$("#fina_type_code").ligerTextBox({
			width : 160
		});
		$("#is_last").ligerComboBox({
			width : 160
		});
		$("#type_level").ligerComboBox({
			width : 160
		});
		$("#is_budg").ligerComboBox({
			width : 160
		});
		$("#super_code").ligerTextBox({
			width : 160
		});
		$("#is_stop").ligerComboBox({
			width : 160
		});
		
		 menu = $.ligerMenu({
			top : 100,
			left : 100,
			width : 120,
			items : [ {
				text : '增加',
				click : itemclick,
				id : 'add',
				icon : 'add'
			}, {
				text : '修改',
				click : update
			}, {
				line : true
			}, {
				text : '删除',
				id : 'delete',
				click : delete_tree
			} ]
		}); 
		 var comboBox = $("#fina_type_code_id").ligerTextBox({
				width:160,
				onSuccess:function(event){
					if (event.keyCode == 38 || event.keyCode == 40 || event.keyCode == 13) //up 、down、enter
					{
					    return;
					} 
		    		comboBox.selectBox.table.find("td.l-over").removeClass("l-over");
		  			comboBox.selectBox.table.find("td[index=1]").addClass("l-over");
		  			comboBox._scrollAdjust(1);
	    	    }
	      });
	      
	      $("#fina_type_code_id").change(function(){
	    	  	
	    	     var fina_type_code = liger.get("fina_type_code_id").getValue(); 
	    	    var treeObj = $.fn.zTree.getZTreeObj("tree");
	    	    
	    	    if(fina_type_code == -1){
	    	    	 liger.get("fina_type_code_id").setText("");
	    	    	 treeObj.expandAll(false);
	    	    }else{
	    	    	var fina_type_name = liger.get("fina_type_code_id").getText();
	        	    var str = fina_type_name.replaceAll("　","");
	        	    liger.get("fina_type_code_id").setText(str);
	    	    }
	    	    $("#fina_type_code_id").focus(function(e){
	    	    	this.select();
	    	    }).blur(function(){
	    	    	window.getSelection().removeAllRanges();
	    	    });
	    	  
	    		var node = treeObj.getNodesByParam("id",fina_type_code);
	    		treeObj.getNodesByParam("fina_type_code",fina_type_code);
	    	
	    		treeObj.selectNode(node[0]);//选择节点
	    		
	    		treeObj.expandNode(node[0], true, true, true,false);//展开所选中的节点
	    		
	  	   }); 
	  	 
	});
	 String.prototype.replaceAll = function(s1,s2){ 
	    	return this.replace(new RegExp(s1,"gm"),s2); 
	 }
	/* 设置树形菜单 */
	function onSelect(event, treeId, treeNode) {
		query(treeNode.id);
	}
	function loadTree() {
		ajaxJsonObjectByUrl("queryAssFinaDictByTree.do?isCheck=false",{},function (responseData){
		      tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
		},false);
	}
	//是否存在指定变量 
	function isExitsVariable(variableName) {
		try {
			if (typeof (variableName) == "object") {
				return false;
			} else if (typeof (variableName) == "undefined") {
				return false;
			} else {
				return true;
			}
		} catch (e) {
		}
		return false;
	}
	//查询
	function query(code) {
		console.log(code)
		grid.options.parms = [];
		grid.options.newPage = 1;
		if (isExitsVariable(code)) {
			grid.options.parms.push({
				name : 'fina_type_code',
				value : code
			});
		} else {
			
			grid.options.parms.push({
				name : 'fina_type_code',
				value : $("#fina_type_code").val()
			});
		}
		
		//根据表字段进行添加查询条件

		grid.options.parms.push({
			name : 'is_last',
			value : liger.get('is_last').getValue()
		});
		grid.options.parms.push({
			name : 'type_level',
			value : liger.get('type_level').getValue()
		});
		
		grid.options.parms.push({
			name : 'super_code',
			value : liger.get("super_code").getValue()
		});
		
		grid.options.parms.push({
			name : 'is_stop',
			value : liger.get('is_stop').getValue()
		});
		
		grid.options.parms.push({
			name : 'is_budg',
			value : liger.get('is_budg').getValue()
		});
		
		//加载查询条件
		grid.loadData(grid.where);
		loadDict();
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '分类编码',
								name : 'fina_type_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.fina_type_code + "')>"
											+ rowdata.fina_type_code + "</a>";
								}
							}, {
								display : '分类名称',
								name : 'fina_type_name',
								align : 'left'
							}, {
								display : '上级分类',
								name : 'super_code',
								align : 'left'
							}, {
								display : '是否末级分类',
								name : 'is_last',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_last == 0) {
										return "否";
									} else {
										return "是"
									}
								}
							}, {
								display : '分类级别',
								name : 'type_level',
								align : 'left'
							}, {
								display : '是否停用',
								name : 'is_stop',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_stop == 0) {
										return "否";
									} else {
										return "是"
									}
								}
							}, {
								display : '是否预算',
								name : 'is_budg',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_budg == 0) {
										return "否";
									} else {
										return "是"
									}
								}
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssFinaDict.do',
					width : '100%',
					height : '100%',
					delayLoad :true,
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						}, {
							text : '添加',
							id : 'add',
							click : itemclick,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : itemclick,
							icon : 'delete'
						}, {
							line : true
						}, {
							text : '打印',
							id : 'print',
							click : printDate,
							icon : 'print'
						}, {
							line : true
						}, {
							text : '导入',
							id : 'import',
							click : itemclick,
							icon : 'up'
						}, {
							line : true
						}]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				$.ligerDialog.open({
					url : 'assFinaDictAddPage.do?isCheck=false',
					height : 300,
					width : 500,
					title : '<h2>编码规则：<font color="red">' + "${rules_view}"
							+ '</font></h2>',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.saveAssFinaDict();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});
				return;
			case "modify":
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(
							function() {
								ParamVo.push(this.group_id + "@" + this.hos_id
										+ "@" + this.copy_code + "@"
										+ this.fina_type_code)
							});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAssFinaDict.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
									loadTree();
								}
							});
						}
					});
				}
				return;
			case "import":
				//公用import.jsp页面使用
				var para={
				    "column": [
				        {
				            "name": "fina_type_code",
				            "display": "财务类别编码",
				            "width": "200",
				            "require":true
				        },
				        {
				            "name": "fina_type_name",
				            "display": "财务类别名称",
				            "width": "200",
				            "require":true
				        },
				        {
				            "name": "is_last",
				            "display": "是否末级",
				            "width": "200",
				            "require":true
				        },
				        {
				            "name": "is_stop",
				            "display": "是否停用",
				            "width": "200",
				            "require":true
				        },
				        {
				            "name": "is_budg",
				            "display": "是否做预算",
				            "width": "200",
				            "require":true
				        }
				       
				        
				    ]/* ,
				    isUpdate:true */
				};
				importSpreadView("hrp/ass/assfinadict/assFinaDictImportPage.do?isCheck=false",para); 
			
			  /* 单独导入页面使用方法 */
				/* parent.$.ligerDialog.open({
					url : 'hrp/ass/assfinadict/assFinaDictImportPage.do?isCheck=false',
					data:{columns : grid.columns.slice(2), grid : grid}, height: 300,width: 450,title:'财务分类数据采集',modal:true,showToggle:false,showMax:true,
					showMin: false,isResize:true,parentframename: window.name,  	});  */
					
			
			case "export":
				return;
			case "downTemplate":
				location.href = "downTemplate.do?isCheck=false";
				return;
			}
		}

	}
	function update(item) {
		openUpdate(actionNodeID);
	}
	function delete_tree(item) {
		var vo = actionNodeID.split("|");
		var ParamVo = [];
		ParamVo.push(vo[0] + "@" + vo[1]
				+ "@" + vo[2] + "@"
				+ vo[3])
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteAssFinaDict.do", {
					ParamVo : ParamVo.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
						loadTree();
					}
				});
			}
		});
	}
	function openUpdate(obj) {

		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "&group_id=" + vo[0] + "&hos_id=" + vo[1] + "&copy_code="
				+ vo[2] + "&fina_type_code=" + vo[3];
		$.ligerDialog.open({
			url : 'assFinaDictUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 300,
			width : 500,
			title : '<h2>编码规则：<font color="red">' + "${rules_view}"
					+ '</font></h2>',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAssFinaDict();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
	function loadDict() {
		var param = {
            	query_key:''
        };
		//字典下拉框
		autocomplete("#fina_type_code_id", "../queryAssFinaDictTree.do?isCheck=false", "id",
				"text", true, false, null,null,null,"160");
		autocomplete("#super_code",
				"../queryMatFinaTypes.do?isCheck=false", "id", "text",
				true, true, param, true);
		
		$('#is_last,#is_stop,#is_budg').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		})
		
		$('#type_level').ligerComboBox({
			data:[{id:1,text:'1'},{id:2,text:'2'},{id:3,text:'3'},{id:4,text:'4'},{id:5,text:'5'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		})
		
	}

       function printDate(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var selPara={};
	    	
	    	$.each(grid.options.parms,function(i,obj){
	    		
	    		selPara[obj.name]=obj.value;
	    		
	    	});
	   		
			var dates = getCurrentDate();
	    	
	    	var cur_date = dates.split(";")[2];
	    	//跨所有列:计算列数
	    	var colspan_num = grid.getColumns(1).length-1;
	   		
	    	var printPara={
	       			title:'财务分类字典',
	       			head:[
	    				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true}
	       			],
	       			foot:[
	    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
						{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
						{"cell":0,"value":"制单人： ${user_name}","colspan":2,"br":false},
						{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
	       			],
	       			columns:grid.getColumns(1),
	       			headCount:2,//列头行数
	       			autoFile:true,
	       			type:3
	       	};
	   		ajaxJsonObjectByUrl("queryAssFinaDict.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout1">
		<div position="left" title="资产类别">
			<h2>
				<font id="font1">编码规则：<font id="font2" color="red">"${rules_view}"</font></font>
			</h2>
			<div>
				<table style="width:100%;height: 50px;text-align: center;">
					<tr>
						<td>&nbsp;快速定位：</td>
						<td><input type="text" id="fina_type_code_id" name="fina_type_code_id"/></td>
					</tr>
				</table>
			</div>
			<div class="tree">
				<ul class="ztree" id="tree"></ul>
			</div>
		</div>
		<div position="center">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">

				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">分类编码：</td>
					<td align="left" class="l-table-edit-td"><input
						name="fina_type_code" type="text" id="fina_type_code" ltype="text"
						validate="{required:true,maxlength:20}"
						onchange="charge_disabled()" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">上级分类：</td>
					<td align="left" class="l-table-edit-td"><input
						name="super_code" type="text" id="super_code" ltype="text"
						validate="{maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">分类级别：</td>
					<td align="left" class="l-table-edit-td">
						<input name="type_level" type="text" id="type_level"  />
					<!-- <select	id="type_level" name="type_level">
							<option value=""></option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
					</select> --></td>
					<td align="left"></td>
				</tr>
				<tr>
					
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">是否末级：</td>
					<td align="left" class="l-table-edit-td">
					<input name="is_last" type="text" id="is_last"  />
					<!-- <select id="is_last"
						name="is_last">
							<option value=""></option>
							<option value="0">否</option>
							<option value="1">是</option>
					</select> --></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">是否停用：</td>
					<td align="left" class="l-table-edit-td">
						<input name="is_stop" type="text" id="is_stop"  />
						<!-- <select id="is_stop" name="is_stop">
							<option value=""></option>
							<option value="0">否</option>
							<option value="1">是</option>
					</select> --></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">是否预算：</td>
					<td align="left" class="l-table-edit-td">
						<input name="is_budg" type="text" id="is_budg"  />
					<!-- <select id="is_budg" name="is_budg">
							<option value=""></option>
							<option value="0">否</option>
							<option value="1">是</option>
					</select> -->
					</td>
					<td align="left"></td>
				</tr>

			</table>

			<div id="maingrid"></div>
		</div>

	</div>
</body>
</html>
