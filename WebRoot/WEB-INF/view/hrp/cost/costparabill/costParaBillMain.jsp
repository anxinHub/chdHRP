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
	var leftgrid;
	var leftgridManager = null;
	var rightgrid;
	var rightgridManager = null;
	var userUpdateStr;
	var menu;
	var actionNodeID;
	var treeAccYear;
	var treeAccMonth;
	var treeBillCode='${bill_code}';
	var treeBillType='${bill_type}';
	$(function() {
		$("#layout1").ligerLayout({
			leftWidth : 180
		});

		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#acc_year").ligerTextBox({
			width : 160
		});
		$("#acc_month").ligerTextBox({
			width : 160
		});
		$("#bill_code").ligerTextBox({
			width : 160
		});
		$("#bill_name").ligerTextBox({
			width : 160
		});
		$("#cost_type_code").ligerTextBox({
			width : 160
		});
		$("#p_code").ligerTextBox({
			width : 160
		});
		loadTree();
		menu = $.ligerMenu({
			top : 100,
			left : 100,
			width : 120,
			items : [ {
				text : '添加',
				click : add_open,
				icon : 'add'
			}, {
				line : true
			}, {
				text : '修改',
				click : update,
				icon : 'edit'
			}, {
				line : true
			}, {
				text : '删除',
				click : delete_tree,
				icon : 'del'
			}, {
				line : true
			} ]
		});
		$("#toptoolbar").ligerToolBar({
			items : [ {
				text : '查询（<u>Q</u>）',
				id : 'search',
				click : query,
				icon : 'search'
			}, {
				line : true
			}, 
// 			{
// 				text : '打印（<u>P</u>）',
// 				id : 'print',
// 				click : print,
// 				icon : 'add'
// 			}, 
			{
				text : '生成（<u>G</u>）',
				id : 'add',
				click : generate,
				icon : 'add'
			},
			{
				text : '继承（<u>G</u>）',
				id : 'add',
				click : inheritance,
				icon : 'add'
			}, {
				line : true
			} ]
		});
		queryDept();
		queryServerDept();
	});
	function delete_tree(item) {
		var vo = actionNodeID.split("|");
		var ParamVo = [];
		ParamVo.push(vo[0] + "@" + vo[1] + "@" + vo[2])
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteCostParaBill.do", {
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
	function update(item) {
		var vo = actionNodeID.split("|");
		
		if(vo[2]==0){
			$.ligerDialog.warn('请选择叶子节点');
			return false;
		}
		openUpdate(actionNodeID);
	}

	/* 设置树形菜单 */
	function onSelect(note) {
		treeAccYear = note.data.acc_year;
		treeAccMonth = note.data.acc_month;
		treeBillCode = note.data.bill_code;
		treeBillType = note.data.pcode;
		queryDeptTree(note);
		queryServerDeptTree(note);
		//还是有漏洞 平级分摊的无法选择 后期优化
		if(treeBillType=='01'){
			//字典下拉框
			var dept_para = {
				type_code : "('04')"
			};
			
			var dept_param = {
					type_code : "('01','02','03')"
				};
				//字典下拉框
				autocomplete("#dept_id", "../queryDeptDictCode.do?isCheck=false", "id",
						"text", true, true, dept_para);

				autocomplete("#server_dept_id", "../queryDeptDictCode.do?isCheck=false",
						"id", "text", true, true, dept_param);
		}
		if(treeBillType=='02'){
			//字典下拉框
			var dept_para = {
				type_code : "('03')"
			};
			
			var dept_param = {
					type_code : "('01','02')"
				};
				//字典下拉框
				autocomplete("#dept_id", "../queryDeptDictCode.do?isCheck=false", "id",
						"text", true, true, dept_para);

				autocomplete("#server_dept_id", "../queryDeptDictCode.do?isCheck=false",
						"id", "text", true, true, dept_param);
		}
		if(treeBillType=='03'){
			//字典下拉框
			var dept_para = {
				type_code : "('02')"
			};
			
			var dept_param = {
					type_code : "('01')"
				};
				//字典下拉框
				autocomplete("#dept_id", "../queryDeptDictCode.do?isCheck=false", "id",
						"text", true, true, dept_para);

				autocomplete("#server_dept_id", "../queryDeptDictCode.do?isCheck=false",
						"id", "text", true, true, dept_param);
		}
		

		
		
	}
	/* 查询 */
	function query() {
		treeAccYear = $("#acc_year").val();
		treeAccMonth = $("#acc_month").val();
		loadTree();
		queryDept();
		queryServerDept();
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
	function loadTree() {

		var formPara = {

			acc_year : $("#acc_year").val(),

			acc_month : $("#acc_month").val()
		};

		ajaxJsonObjectByUrl("queryByTree.do?isCheck=false", formPara, function(
				responseData) {
			if (responseData != null) {
				tree = $("#tree").height($(window).height()-60).ligerTree(
						{
							data : responseData.Rows,
							parentIcon : null,
							childIcon : null,
							checkbox : false,
							idFieldName : 'code',
							parentIDFieldName : 'pcode',
							textFieldName : 'text',
							onSelect : onSelect,
							isExpand : true,
							nodeWidth : 250,
							slide : false,
							
							onContextmenu : function(node, e) {
								actionNodeID = "" + node.data.acc_year + "|"
										+ node.data.acc_month + "|"
										+ node.data.bill_code+"|"
										+ node.data.pcode;
								menu.show({
									top : e.pageY,
									left : e.pageX
								});
								return false;
							}
						});
				treeManager = $("#tree").ligerGetTreeManager();
				//treeManager.collapseAll(); //全部收起
			}
		});
	}
	//查询
	function queryDeptTree(node) {
		leftgrid.options.parms = [];
		leftgrid.options.newPage = 1;
        var type_code="";
		if(treeBillType=='01'){
			type_code="04";
		}
		if(treeBillType=='02'){
			type_code="03";
		}
		if(treeBillType=='03'){
			type_code="02";
		}
		
		//根据表字段进行添加查询条件
		leftgrid.options.parms.push({
			name : 'acc_year',
			value : node.data.acc_year
		});
		leftgrid.options.parms.push({
			name : 'acc_month',
			value : node.data.acc_month
		});
		leftgrid.options.parms.push({
			name : 'bill_code',
			value : node.data.bill_code
		});
		leftgrid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue()
		});
		leftgrid.options.parms.push({
			name : 'type_code',
			value : type_code
		});
		//加载查询条件
		leftgrid.loadData();
	}
	//查询
	function queryServerDeptTree(node) {
		rightgrid.options.parms = [];
		rightgrid.options.newPage = 1;
		
		//根据表字段进行添加查询条件
		rightgrid.options.parms.push({
			name : 'acc_year',
			value : node.data.acc_year
		});
		rightgrid.options.parms.push({
			name : 'acc_month',
			value : node.data.acc_month
		});
		rightgrid.options.parms.push({
			name : 'bill_code',
			value : node.data.bill_code
		});
		rightgrid.options.parms.push({
			name : 'srever_dept_id',
			value : liger.get("server_dept_id").getValue()
		});
		//加载查询条件
		rightgrid.loadData();
	}
	//查询
	function queryDept() {
		leftgrid.options.parms = [];
		leftgrid.options.newPage = 1;
		treeAccYear = $("#acc_year").val();
		treeAccMonth = $("#acc_month").val();
		var dept_dict = liger.get("dept_id").getValue();
		
		//根据表字段进行添加查询条件
		leftgrid.options.parms.push({
			name : 'acc_year',
			value : treeAccYear
		});
		leftgrid.options.parms.push({
			name : 'acc_month',
			value : treeAccMonth
		});
		leftgrid.options.parms.push({
			name : 'bill_code',
			value : treeBillCode
		});
		leftgrid.options.parms.push({
			name : 'dept_code',
			value : dept_dict.split(".")[2]
		});
		//加载查询条件
		leftgrid.loadData();
	}
	//查询
	function queryServerDept() {
		rightgrid.options.parms = [];
		rightgrid.options.newPage = 1;
		treeAccYear = $("#acc_year").val();
		treeAccMonth = $("#acc_month").val();
		var dept_dict = liger.get("server_dept_id").getValue();
		
		//根据表字段进行添加查询条件
		rightgrid.options.parms.push({
			name : 'acc_year',
			value : treeAccYear
		});
		rightgrid.options.parms.push({
			name : 'acc_month',
			value : treeAccMonth
		});
		rightgrid.options.parms.push({
			name : 'bill_code',
			value : treeBillCode
		});
		rightgrid.options.parms.push({
			name : 'dept_code',
			value : dept_dict.split(".")[2]
		});
		//加载查询条件
		rightgrid.loadData();
	}

	function loadHead() {
		leftgrid = $("#maingridleft").ligerGrid({
			columns : [ {
				display : '年度',
				name : 'acc_year',
				width : '10%',
				align : 'left'
			}, {
				display : '月份',
				name : 'acc_month',
				width : '10%',
				align : 'left'
			}, {
				display : '科室编码',
				name : 'dept_code',
				width : '25%',
				align : 'left'
			}, {
				display : '科室名称',
				name : 'dept_name',
				width : '35%',
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryCostParaDept.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			//scrollToPage : true,
			//scrollToAppend : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询（<u>E</u>）',
					id : 'search',
					click : queryDept,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '添加（<u>A</u>）',
					id : 'add',
					click : add_dept_open,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '删除（<u>D</u>）',
					id : 'delete',
					click : remove_dept,
					icon : 'delete'
				}, {
					line : true
				}, {
					text : '打印（<u>P</u>）',
					id : 'print',
					click : printDate,
					icon : 'print'
				}, {
					line : true
				} ]
			}
		});
		rightgrid = $("#maingridright").ligerGrid({
			columns : [ {
				display : '年度',
				name : 'acc_year',
				width : '10%',
				align : 'left'
			}, {
				display : '月份',
				name : 'acc_month',
				width : '10%',
				align : 'left'
			}, {
				display : '科室编码',
				name : 'dept_code',
				width : '25%',
				align : 'left'
			}, {
				display : '科室名称',
				name : 'dept_name',
				width : '35%',
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryCostParaSetverDept.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			//scrollToPage : true,
			//scrollToAppend : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询（<u>E</u>）',
					id : 'search',
					click : queryServerDept,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '添加（<u>A</u>）',
					id : 'add',
					click : add_server_dept_open,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '删除（<u>D</u>）',
					id : 'delete',
					click : remove_server_dept,
					icon : 'delete'
				}, {
					line : true
				}, {
					text : '打印（<u>P</u>）',
					id : 'print',
					click : printRight,
					icon : 'print'
				}, {
					line : true
				} ]
			}
		});

		leftgridManager = $("#maingridleft").ligerGetGridManager();
		rightgridManager = $("#maingridright").ligerGetGridManager();
	}

	function add_open() {
		$.ligerDialog.open({
			url : 'costParaBillAddPage.do?isCheck=false',
			height : 300,
			width : 500,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostParaBill();
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
	function add_server_dept_open() {
		if(treeBillCode==0){
			$.ligerDialog.warn('请选择科室定向分摊设置');
			return false;
		}
		var parm =  "acc_year=" + treeAccYear + "&"
		+ "acc_month=" + treeAccMonth + "&" + "bill_code=" + treeBillCode+ "&" + "type_code=" + treeBillType;
		$.ligerDialog.open({
			url : 'costParaSetverDeptMainPage.do?isCheck=false&' + parm,
			height : 400,
			width : 800,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveServerDeptDict();
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
	function add_dept_open() {
		if(treeBillCode==0){
			$.ligerDialog.warn('请选择科室定向分摊设置');
			return false;
		}
		
		var parm =  "acc_year=" + treeAccYear + "&"
		+ "acc_month=" + treeAccMonth + "&" + "bill_code=" + treeBillCode+ "&" + "type_code=" + treeBillType;
		$.ligerDialog.open({
			url : 'costParaDeptMainPage.do?isCheck=false&' + parm,
			height : 400,
			width : 800,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveDeptDict();
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

	function remove_dept() {

		var data = leftgridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.acc_year + "@"
								+ this.acc_month + "@" + this.bill_code+ "@" + this.dept_id+ "@" + this.dept_no)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteCostParaDept.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							//query();
						}
					});
				}
			});
		}
	}
	function remove_server_dept() {

		var data = rightgridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.acc_year + "@"
								+ this.acc_month + "@" + this.bill_code +"@"+ this.dept_id+ "@" + this.dept_no)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteCostParaServerDept.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							//query();
						}
					});
				}
			});
		}
	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "acc_year=" + vo[0] + "&"
				+ "acc_month=" + vo[1] + "&" + "bill_code=" + vo[2]
		$.ligerDialog.open({
			url : 'costParaBillUpdatePage.do?isCheck=false&' + parm,
			height : 300,
			width : 500,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostParaBill();
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
	function generate() {
		if ($("#acc_year").val() == "") {

			$.ligerDialog.error('统计年份不能为空');

			return;
		}
		if ($("#acc_month").val() == "") {

			$.ligerDialog.error('统计月份不能为空');

			return;
		}
		var formPara = {

			acc_year : $("#acc_year").val(),

			acc_month : $("#acc_month").val()
		};

		$.ligerDialog.confirm('确定生成后将初始化当前月数据?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("generate.do?isCheck=false", formPara,
						function(responseData) {
							if (responseData.state == "true") {
								query();
								loadTree();
							}
						});
			}
		});
	}
	function inheritance() {
	 $.ligerDialog.open({url: 'costParaBillSetExtendPage.do?isCheck=false', height: 270,width: 500, title:'继承数据',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostWageCostRela(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	}
	function loadDict() {
		//默认年
		autodate("#acc_year", "yyyy");
		//默认月
		autodate("#acc_month", "MM");

		//字典下拉框
		var dept_para = {
			type_code : "('04')"
		};

		var dept_param = {
			type_code : "('01','02','03')"
		};
		//字典下拉框
		autocomplete("#dept_id", "../queryDeptDictCode.do?isCheck=false", "id",
				"text", true, true, dept_para);

		autocomplete("#server_dept_id", "../queryDeptDictCode.do?isCheck=false",
				"id", "text", true, true, dept_param);

	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('G', add);
		hotkeys('D', remove);

		hotkeys('P', printDate);
		hotkeys('O', printDate);
		hotkeys('L', printDate);

	}
	

	//导出数据
	function exportExcel() {
		//有数据直接导出
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopExportExcel("resultPrint", "导出Excel", "科室定向分摊设置.xls", true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara = {
			usePager : false,
			acc_year : $("#acc_year").val(),
			acc_month : $("#acc_month").val(),
			bill_code : $("#bill_code").val(),
			bill_name : $("#bill_name").val(),
			cost_type_code : $("#cost_type_code").val(),
			p_code : $("#p_code").val()
		};
		ajaxJsonObjectByUrl("queryCostParaBill.do", exportPara, function(
				responseData) {
			$.each(responseData.Rows, function(idx, item) {
				var trHtml = "<tr>";
				trHtml += "<td>" + item.acc_year + "</td>";
				trHtml += "<td>" + item.acc_month + "</td>";
				trHtml += "<td>" + item.bill_code + "</td>";
				trHtml += "<td>" + item.bill_name + "</td>";
				trHtml += "<td>" + item.cost_type_code + "</td>";
				trHtml += "<td>" + item.p_code + "</td>";
				trHtml += "</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint", "导出Excel", "科室定向分摊设置.xls", true);
		}, true, manager);
		return;
	}

// 	function printDate(){
    	
// // 		if(grid.getData().length==0){
// // 			$.ligerDialog.error("请先查询数据！");
// // 			return;
// // 		}
           	
//         	var selPara={};
//         	$.each(leftgrid.options.parms,function(i,obj){
//         		selPara[obj.name]=obj.value;
//         	});
     
//        		//console.log(grid)
//        		var printPara={
//        			headCount:2,
//        			title:'科室分摊设置',
//        			head:[
// 	    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":4,"br":true},
// 	    				{"cell":0,"value":
// 	    					"期间: " + $("#acc_year").val()+
// 	    					" 年  "+ $("#acc_month").val()+"月",
// 	    					"colspan":4,"br":true}
// 	       			],
// 	       			foot:[
	       			
// 	    				{"cell":0,"value":"主管:","colspan":4,"br":true} ,
// 						//{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
// 						{"cell":0,"value":"制单人： ${sessionScope.user_name}","colspan":4,"br":true},
// 						//{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
// 	       			],
//        			type:3,
//        			columns:leftgrid.getColumns(1),
//        			autoFile:true
//        		};
       		
//        		ajaxJsonObjectByUrl("queryCostParaDept.do?isCheck=false", selPara, function(responseData) {
//        			printGridView(responseData,printPara);
//         	});

   		
//     }
           function printDate(){
        	   
        	   if(leftgrid.getData().length==0){
   	    		
   				$.ligerDialog.error("请先查询数据！");
   				
   				return;
   			}
   	    	
   	    	var heads={
   	 	    		//"isAuto":true,//系统默认，页眉显示页码
   	 	    		"rows": [
   	 		          {"cell":0,"value":"统计年份："+$("#acc_year").val()+"月份："+$("#acc_month").val(),"colSpan":"5"}
   	 	    	]};
   	 	       var printPara={
   	 	      		title: "科室分摊设置（服务）",//标题
   	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
   	 	      		class_name: "com.chd.hrp.cost.service.CostParaDeptService",
   	 	   			method_name: "queryCostParaDeptPrint",
   	 	   			bean_name: "costParaDeptService",
   	 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
   	 	   			
   	 	       	};
   	 	      //执行方法的查询条件
   	 		  $.each(leftgrid.options.parms,function(i,obj){
   	 			printPara[obj.name]=obj.value;
   	  	      });
   	 		
   	  	     officeGridPrint(printPara);	
           }
           function printRight(){
        	   if(rightgrid.getData().length==0){
      	    		
      				$.ligerDialog.error("请先查询数据！");
      				
      				return;
      			}
        	   var heads={
      	 	    		//"isAuto":true,//系统默认，页眉显示页码
      	 	    		"rows": [
      	 		          {"cell":0,"value":"统计年份："+$("#acc_year").val()+"月份："+$("#acc_month").val(),"colSpan":"5"}
      	 	    	]};
      	 	       var printPara={
      	 	      		title: "科室分摊设置（受益）",//标题
      	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      	 	      		class_name: "com.chd.hrp.cost.service.CostParaSetverDeptService",
      	 	   			method_name: "queryCostParaSetverDeptPrint",
      	 	   			bean_name: "costParaSetverDeptService",
      	 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
      	 	   			
      	 	       	};
      	 	      //执行方法的查询条件
      	 		  $.each(rightgrid.options.parms,function(i,obj){
      	 			printPara[obj.name]=obj.value;
      	  	      });
      	 		
      	  	     officeGridPrint(printPara);	
           }
// 	       function printRight(){
	    	
// 	    		if(rightgrid.getData().length==0){
// 	 			$.ligerDialog.error("请先查询数据！");
// 	 			return;
// 	 		}
	           	
// 	        	var selPara={};
// 	        	$.each(rightgrid.options.parms,function(i,obj){
// 	        		selPara[obj.name]=obj.value;
// 	        	});
	     
// 	       		//console.log(grid)
// 	       		var printPara={
// 	       			headCount:2,
// 	       			title:'科室分摊设置',
// 	       			head:[
// 		    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":4,"br":true},
// 		    				{"cell":0,"value":
// 		    					"期间: " + $("#acc_year").val()+
// 		    					" 年  "+ $("#acc_month").val()+"月",
// 		    					"colspan":4,"br":true}
// 		       			],
// 		       			foot:[
		       			
// 		    				{"cell":0,"value":"主管:","colspan":4,"br":true} ,
// 							//{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
// 							{"cell":0,"value":"制单人： ${sessionScope.user_name}","colspan":4,"br":true},
// 							//{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
// 		       			],
// 	       			type:3,
// 	       			columns:leftgrid.getColumns(1),
// 	       			autoFile:true
// 	       		};
	       		
// 	       		ajaxJsonObjectByUrl("queryCostParaSetverDept.do?isCheck=false", selPara, function(responseData) {
// 	       			printGridView(responseData,printPara);
// 	        	});

	   		
// 	    }
	       
	       function print(){
		    	
//	    		if(grid.getData().length==0){
//	 			$.ligerDialog.error("请先查询数据！");
//	 			return;
//	 		}
	           	
	        	var selPara={};
	        	$.each(rightgrid.options.parms,function(i,obj){
	        		selPara[obj.name]=obj.value;
	        	});
	     
	       		//console.log(grid)
	       		var printPara={
	       			headCount:2,
	       			title:'科室分摊设置',
	       			head:[
		    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":4,"br":true},
		    				{"cell":0,"value":
		    					"期间: " + $("#acc_year").val()+
		    					" 年  "+ $("#acc_month").val()+"月",
		    					"colspan":4,"br":true}
		       			],
		       			foot:[
		       			
		    				{"cell":0,"value":"主管:","colspan":4,"br":true} ,
							//{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
							{"cell":0,"value":"制单人： ${sessionScope.user_name}","colspan":4,"br":true},
							//{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
		       			],
	       			type:3,
	       			columns:leftgrid.getColumns(1),
	       			autoFile:true
	       		};
	       		
	       		ajaxJsonObjectByUrl("queryCostParaSetverDept.do?isCheck=false", selPara, function(responseData) {
	       			printGridView(responseData,printPara);
	        	});

	   		
	    }
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="layout1">
		<div position="left" title="科室定向分摊设置">
			<div class="tree">
				<ul class="ztree" id="tree"></ul>
			</div>
		</div>

		<div position="top">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">统计年份：</td>
					<td align="left" class="l-table-edit-td"><input
						name="acc_year" type="text" id="acc_year" class="Wdate"
						onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">统计月份：</td>
					<td align="left" class="l-table-edit-td"><input
						name="acc_month" type="text" id="acc_month" class="Wdate"
						onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>

				</tr>

			</table>
			<div id="toptoolbar"></div>
		</div>
		<div position="center">
				<div  style="float: left; width: 50%;">
				
					<table>
						<tr>
							<td align="right" class="l-table-edit-td"
								style="padding-left: 20px;">服务科室：</td>
							<td align="left" class="l-table-edit-td"><input
								name="dept_id" type="text" id="dept_id" /></td>
						</tr>
					</table>
					<div id="maingridleft"
						style="margin: 4px; padding: 0; float: left;"></div>
				</div>
				<div  style="float: left; width: 50%;">
				
					<table>
						<tr>
							<td align="right" class="l-table-edit-td"
								style="padding-left: 20px;">受益科室：</td>
							<td align="left" class="l-table-edit-td"><input
								name="server_dept_id" type="text" id="server_dept_id" /></td>
						</tr>
					</table>
					<div id="maingridright"
						style="margin: 4px; padding: 0; margin-left: 10px; float: left;">
					</div>
				</div>
		</div>
	</div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>

				<tr>
					<th width="200">统计年份</th>
					<th width="200">统计月份</th>
					<th width="200">序号</th>
					<th width="200">摘要</th>
					<th width="200">科室类型</th>
					<th width="200">单据编码</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
