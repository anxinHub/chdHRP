<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		<html style="overflow:hidden;">

		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<title></title>
			<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
			<script type="text/javascript">
				var grid;

				var gridManager = null;
				
				var isSubj = '${98001}' == 1 ? true : false;
				
				var userUpdateStr,
					subj_type,
					subj_type_name = '',
					dynamicTab = "",
					newpage,                           // 存入当前page页的变量
					selectArr = [];                    // 存入所选择数据的数组

				$(function () {

					loadDict();

					loadHead("tabitem1");	//加载数据

					$("#subj_code").ligerTextBox({ width: 160 });

					$("#subj_name").ligerTextBox({ width: 160 });

					$("#subj_level").ligerTextBox({ width: 80 });

					$(':button').ligerButton({ width: 80 });

					ajaxJsonObjectByUrl("../accsubjtype/queryAccSubjType.do?isCheck=false", {}, function (responseData) {

						$(responseData.Rows).each(function (i, v) {

							dynamicTab += "<div tabid='" + v.subj_type_code + "' title='" + v.subj_type_name + "' style='height:100%' >"
								+ "<div id='" + v.subj_type_code + "'></div>"
								+ "</div>";

						})

						$("#all").after(dynamicTab);

						$("#subj_tab").ligerTab();

						$("#subj_tab").ligerTab({
							onAfterSelectTabItem: function (tabid) {
								if (tabid != "tabitem1") {
									subj_type_name = $("#subj_tab").ligerTab().getTabItemTitle(tabid);
								} else {
									subj_type_name = '';
								}

								loadHead(tabid);

								query();
							}
						});

					});

				});
				//查询
				function query() {

					grid.options.parms = [];

					grid.options.newPage = 1;
					//根据表字段进行添加查询条件
					grid.options.parms.push({ name: 'subj_code', value: $("#subj_code").val() });

					grid.options.parms.push({ name: 'subj_name', value: $("#subj_name").val() });

					grid.options.parms.push({ name: 'subj_level', value: liger.get("subj_level").getValue() });

					if (liger.get("is_last").getValue() != "") {

						grid.options.parms.push({ name: 'is_last', value: liger.get("is_last").getValue() });
					}

					if (subj_type != null) {

						grid.options.parms.push({ name: 'subj_type_code', value: subj_type });
					}


					//加载查询条件
					grid.loadData(grid.where);
				}

				function loadHead(tabid) {

					var gridId = "";
					var oldPageSize = 50;
					var scroll_onoff = true;
					if (tabid != "tabitem1") {
						subj_type = tabid;

						gridId = "#" + tabid;

					} else {

						subj_type = "";

						gridId = "#maingrid";
					}

					grid = $(gridId).ligerGrid({
						columns: [
							{
								display: '科目编码', name: 'subj_code', align: 'left', width: '15%',
								render: function (rowdata, rowindex,
									value) {                                         // 传入所选择的rowdata的id值
									return "<a href=javascript:openUpdate('" + rowdata.subj_id + "|" + rowdata.group_id + "|" + rowdata.hos_id + "|" + rowdata.copy_code + "|" + rowdata.acc_year + "|" + rowdata.subj_code + "|" + rowdata.__id + "')>" + rowdata.subj_code + "</a>";
								}                                                
							},
							{
								display: '科目名称', name: 'subj_name', align: 'left', width: '20%'
							},
							{
								display: '科目全称', name: 'subj_name_all', align: 'left', width: '28%'
							},
							{
								display: '级次', name: 'subj_level', align: 'left', width: '2%'
							},
							{
								display: '性质', name: 'subj_nature_name', align: 'left'
							},
							{
								display: '方向', name: 'subj_dire', align: 'left', width: '2%',
								render: function (rowdata, rowindex,value) {
									if (rowdata.subj_dire == 0) {
										return "借";
									} else {
										return "贷";
									}
								}
							},
							{
								display: '是否末级', name: 'is_last', align: 'left', hide: true
							},
							{
								display: '上级科目', name: 'super_code', align: 'left', hide: true
							},
							{
								display: '辅助核算', align: 'center', columns: [
									{
										display: '核算1', name: 'check1_name', align: 'left'
									},
									{
										display: '核算2', name: 'check2_name', align: 'left'
									},
									{
										display: '核算3', name: 'check3_name', align: 'left'
									},
									{
										display: '核算4', name: 'check4_name', align: 'left'
									}
									//{ display: '核算5', name: 'check5_name', align: 'center'
									//},
									//{ display: '核算6', name: 'check6_name', align: 'center'
									//}
									//{ display: '核算7', name: 'check7', align: 'center'
									//},
									//{ display: '核算8', name: 'check8', align: 'center'
									//},
									//{ display: '核算9', name: 'check9', align: 'center'
									//},
									//{ display: '核算10', name: 'check10', align: 'center'
									//}                             
								]
							}
						],
						dataAction: 'server', dataType: 'server', usePager: true, url: 'queryAccSubj.do?subj_type_code=' + subj_type,
						width: '100%', height: '100%', checkbox: true, rownumbers: true, delayLoad : true,//初始化不加载，默认false
						selectRowButtonOnly: true, columnWidth: 70, heightDiff: 0, allowUnSelectRow: true,
						/*  toolbar: { items: [
								{ text: '全部', click:querySubjByType,id:'all'  },
								{ line:true },
								{ text: '资产',  click:querySubjByType,id:'assets',icon:'assets'},
								{ line:true },
								{ text: '负债', click:querySubjByType,id:'liabi',icon:'liabi'  },
								{ line:true },
								{ text: '净资产', click:querySubjByType,id:'asset',icon:'asset'  },
								{ line:true },
								{ text: '收入', click:querySubjByType,id:'income',icon:'income'  },
								{ line:true },
								{ text: '费用', click:querySubjByType,id:'cost',icon:'cost'  },
								{ line:true }
						]}, */
						onCheckRow: function (checked, data, rowid, rowdata) {         // 复选框选择时存入数据，同时重复数据不存入
							var code = rowid + "@" + grid.options.page;
							if (checked) {
								selectArr.push(code);
							} else {
								var index = selectArr.indexOf(code);
								if (index > -1)
									selectArr.splice(index, 1);
							}
						},
						onDblClickRow: function (rowdata, rowindex, value) {
							openUpdate(
								rowdata.subj_id + "|" +
								rowdata.group_id + "|" +
								rowdata.hos_id + "|" +
								rowdata.copy_code + "|" +
								rowdata.acc_year + "|" +
								rowdata.subj_code + "|" +
								rowdata.__id                      //  添加参数：传入ID值
							);
						},
						onChangePage: function(page){           // 当点击换页按钮时，触发此事件，更新newpage,以免跳转不了
							 newpage = page ;
							 scroll_onoff = false;				//  当点解input翻页时，限制滚动条滚动，让滚动条重置到所翻页的启始处
						},
						onAfterShowData: function () {
							var pageNum = grid.options.page;
							grid.changePages(newpage);         
							grid.selectedDataRender(selectArr,oldPageSize, grid.options.pageSize, pageNum);
							var selects = selectArr;
							if(selects.length > 0)
							{
								var selectNow = selects[selects.length - 1].split('@')[0]; 
								if(oldPageSize != grid.options.pageSize){            // pagesize改变时触发changePages
									newpage = selects[selects.length - 1].split('@')[1];
									grid.changePages(newpage);
									oldPageSize=grid.options.pageSize;
									scroll_onoff = true;
									return false;
								}
								if(scroll_onoff)                                       // 防止每页的滚动条都在动  
								grid.gridbody.scrollTop(28 * (selectNow.replace("r1","")*1 -1));
							}
							oldPageSize=grid.options.pageSize;
							scroll_onoff = true;
						},	lodop:{
	    		    		title:"会计科目",
	    					fn:{
	    						subj_dire:function(value){//借方
	    							if (value == 0) {
										return "借";
									} else {
										return "贷";
									}
	        	    			}
	        	    		}
	    				}


					});

					gridManager = $("#maingrid").ligerGetGridManager();
				}
	


				// function selectedDataRender (selectArr, oldPageSize, newPageSize,pageNum) {
				// 	for (var item = 0; item < selectArr.length; item++) {
				// 		selectArr[item] = switchDataByPageSize( selectArr[item], oldPageSize, newPageSize);
				// 		var num = selectArr[item].split('@')[1];         // 获取相对应的页数
				// 		var value =selectArr[item].split('@')[0];       // 获取当前页的索引值
				// 		if (num == pageNum) {
				// 			grid.select(value);                         // 渲染
				// 		}
				// 	}

				// } 

				// function changePages(newpage){
				// 	$('.pcontrol input', grid.toolbar).val(newpage);
				// 	grid.changePage("input");       
				// }

				// function switchDataByPageSize(select,oldpage,newpage){
				// 	if(oldpage == newpage)
				// 			return select;
				// 	var num = select.split('@')[1];
				// 	var value = select.split('@')[0];
				// 	var sumIndex = (num - 1) * oldpage + value.substring(2)*1;
				// 	num = Math.ceil(sumIndex / newpage);
				// 	var numRow = sumIndex % newpage||newpage;
				// 	if(numRow　<= 9 ){
				// 		value = "r100"+ numRow;
				// 	}else if(numRow >9 && numRow <= 99){
				// 		value = "r10"+ numRow;
				// 	}else
				// 	   value = "r1" + numRow;
				// 	value += "@"+ num;
				// 	return value;
				// }
				function add_open() {
					if(isSubj==true){
						$.ligerDialog.warn("无功能权限！");
						return false;
					}else{
						$.ligerDialog.open({
							url: 'accSubjAddPage.do?isCheck=false&subj_type_code=' + subj_type + '&subj_type_name=' + subj_type_name, height: 550, width: 700, title: '添加会计科目', modal: true,
							showToggle: false, showMax: false, showMin: false, isResize: true, top: 0,
							buttons: [
								/* { text: '保存 & 继续', onclick: function (item, dialog) { dialog.frame.saveAccSubj(); },cls:'l-dialog-btn-highlight' },  */
								{ text: '保存', onclick: function (item, dialog) { dialog.frame.saveAccSubj(); }, cls: 'l-dialog-btn-highlight' },
								{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } }
							]
						});
					}

				}

				function delete_btn() {
					if(isSubj==true){
						$.ligerDialog.warn("无功能权限！");
						return false;
					}else{
						var data = grid.getSelectedRows();
						if (data.length == 0) {
							$.ligerDialog.error('请选择行');
						} else {
							var ParamVo = [];
							var isLastSubj = "";
							$(data).each(function () {
								/* if(this.is_last==0){
									isLastSubj+=this.subj_code+"，";
								} */
								ParamVo.push(
									//表的主键
									this.subj_id + "@" +
									this.group_id + "@" +
									this.hos_id + "@" +
									this.copy_code + "@" +
									this.acc_year + "@" +
									this.subj_code + "@" +
									this.super_code
								)
							});
							/* if(isLastSubj!=""){
								$.ligerDialog.error("以下科目不是末级科目：【"+isLastSubj.substring(0, isLastSubj.length-1)+"】。");
								return;
							} */
							$.ligerDialog.confirm('确定删除?', function (yes) {
								if (yes) {
									ajaxJsonObjectByUrl("deleteAccSubj.do", { ParamVo: ParamVo.toString() }, function (responseData) {
										if (responseData.state == "true") {
											query();
										}
									});
								}
							});
						}
					}
					

				}

				function imp() {
					var para = {
							url : 'hrp/acc/accsubj/accSubjImportPage.do?isCheck=false',
							title : '会计科目导入',
							width : 0,
							height : 0,
							isShowMin : false,
							isModal : true,
							data : {
								grid : grid
							}
						};
						parent.openDialog(para);
				}

				function itemclick(item) {
					if (item.id) {
						switch (item.id) {
							case "add":
								$.ligerDialog.open({
									url: 'accSubjAddPage.do', height: 485, width: 700, title: '添加会计科目', modal: true, showToggle: false, showMax: false, showMin: true, isResize: true,
									buttons: [
										/* { text: '保存 & 继续', onclick: function (item, dialog) { dialog.frame.saveAccSubj(); },cls:'l-dialog-btn-highlight' },  */
										{ text: '保存', onclick: function (item, dialog) { dialog.frame.saveAccSubj(); }, cls: 'l-dialog-btn-highlight' },
										{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } }
									]
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
									var isLastSubj = "";
									$(data).each(function () {
										if (this.is_last == 0) {
											isLastSubj += this.subj_code + "，";
										}
										ParamVo.push(
											//表的主键
											this.subj_id + "@" +
											this.group_id + "@" +
											this.hos_id + "@" +
											this.copy_code + "@" +
											this.acc_year + "@" +
											this.subj_code + "@" +
											this.super_code
										)
									});
									if (isLastSubj != "") {
										$.ligerDialog.error("以下科目不是末级科目：【" + isLastSubj.substring(0, isLastSubj.length - 1) + "】。");
										return;
									}
									$.ligerDialog.confirm('确定删除?', function (yes) {
										if (yes) {
											ajaxJsonObjectByUrl("deleteAccSubj.do", { ParamVo: ParamVo.toString() }, function (responseData) {
												if (responseData.state == "true") {
													query();
												}
											});
										}
									});
								}
								return;
							case "Excel":
							case "Word":
							case "PDF":
							case "TXT":
							case "import":
								$.ligerDialog.open({ url: 'accSubjkImportPage.do', height: 500, width: 1135, title: '导入', modal: true, showToggle: false, showMax: false, showMin: true, isResize: true });
							case "export":
								return;
						}
					}

				}

				function updateRow(obj) {
					var selectArr = grid.selected;
					var selected = selectArr[selectArr.length - 1];
					grid.updateRow(selected, obj);
				}


				function openUpdate(obj) {
					newpage = grid.options.page;
					var vo = obj.split("|");
					var parm = "subj_id=" +
						vo[0] + "&group_id=" +
						vo[1] + "&hos_id=" +
						vo[2] + "&copy_code=" +
						vo[3] + "&acc_year=" +
						vo[4] + "&subj_code=" +
						vo[5];

					var code = vo[vo.length -1] + "@" + grid.options.page;          // code为存入的选择数据
					var index = selectArr.indexOf(code);
					if (index > -1)              // 判断是否有重复数据，有的话先删除一个
						selectArr.splice(index, 1);
					selectArr.push(code);


					$.ligerDialog.open({
						url: 'accSubjUpdatePage.do?isCheck=false&' + parm, data: {}, height: 550, width: 700,
						title: '修改会计科目', modal: true, showToggle: false, showMax: false, showMin: false, isResize: false,
						buttons: [{
							text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccSubj(); },
							cls: 'l-dialog-btn-highlight'
						}, { text: '取消', onclick: function (item, dialog) { dialog.close(); } }]
					});

				}

				function extend(obj) {

					$.ligerDialog.open({ url: 'accSubjExtendPage.do', data: {}, height: 400, width: 500, title: '继承', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true, buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.save(); }, cls: 'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } }] });

				}
				
				function synSubj(){
					var formPara = {
					};
					$.ligerDialog.confirm('是否确认集团同步！', function(yes) {
						if(yes){
							ajaxJsonObjectByUrl("addSynGroupSubj.do?isCheck=false",formPara,function(responseData){
		    		            
		    		            if(responseData.state=="true"){
		    		                parent.query();
		    		                this_close();
		    		            }
		    		        });
						}
					});
				}

				function this_close() {
					frameElement.dialog.close();
				}
				
				function loadDict() {
					//字典下拉框
					$("#is_last").ligerComboBox({ data: [{ id: "0", text: "否" }, { id: "1", text: "是" }], width: 80, cancelable: true });
					$("#is_stop").ligerComboBox({ data: [{ id: "0", text: "否" }, { id: "1", text: "是" }], width: 80, cancelable: true, value: '0'});
					autocomplete("#subj_level", "../querySubjLevel.do?isCheck=false", "id", "text", true, true, '', false, '', 80);
				}
			</script>
			<script type="text/javascript">
				/* $(function ()
       {
           $("#toptoolbar").ligerToolBar({ items: [
               {text: '添加', id:'add', icon:'add', click: itemclick},
               { line:true },
               { text: '删除',id:'delete',icon:'delete', click: itemclick },
               { line:true },
               { text: '继承',id:'extend',icon:'', click: extend },
               { line:true },
               { text: '导入',id:'import',icon:'up', click: itemclick },
               { line:true },
               { text: '导出',id:'export',icon:'pager', click: exportExcel },
               { line:true },
           ]
           }); */
				/* 
				$("#toptoolbar1").ligerToolBar({ items: [
				 { text: '全部', click:querySubjByType  },
				 { line:true },
				 { text: '资产',  click:querySubjByType  },
				 { line:true },
				 { text: '负债', click:querySubjByType  },
				 { line:true },
				 { text: '净资产', click:querySubjByType  },
				 { line:true },
				 { text: '收入', click:querySubjByType  },
				 { line:true },
				 { text: '费用', click:querySubjByType  },
				 { line:true }
				  ]
				});
			}); */
				function querySubjByType(item) {

					grid.options.parms = [];

					grid.options.parms.push({ name: 'subj_code', value: $("#subj_code").val() });

					grid.options.parms.push({ name: 'subj_name', value: $("#subj_name").val() });

					grid.options.parms.push({ name: 'subj_level', value: liger.get("subj_level").getValue() });

					if ($("#is_last").val() != -1) {

						grid.options.parms.push({ name: 'is_last', value: $("#is_last").val() });

					}

					switch (item.text) {
						case "全部":
							//grid.options.parms=[];
							grid.options.newPage = 1;
							grid.loadData(grid.where);
							subj_type = "";
							return;
						case "资产":
							//grid.options.parms=[];
							grid.options.newPage = 1;
							grid.options.parms.push({ name: 'subj_type_code', value: '01' });
							grid.loadData(grid.where);
							subj_type = "01";
							return;
						case "负债":
							//grid.options.parms=[];
							grid.options.newPage = 1;
							grid.options.parms.push({ name: 'subj_type_code', value: '02' });
							grid.loadData(grid.where);
							subj_type = "02";
							return;
						case "净资产":
							//grid.options.parms=[];
							grid.options.newPage = 1;
							grid.options.parms.push({ name: 'subj_type_code', value: '03' });
							grid.loadData(grid.where);
							subj_type = "03";
							return;
						case "收入":
							//grid.options.parms=[];
							grid.options.newPage = 1;
							grid.options.parms.push({ name: 'subj_type_code', value: '04' });
							grid.loadData(grid.where);
							subj_type = "04";
							return;
						case "费用":
							//	grid.options.parms=[];
							grid.options.newPage = 1;
							grid.options.parms.push({ name: 'subj_type_code', value: '05' });
							grid.loadData(grid.where);
							subj_type = "05";
							return;
					}
				}

				//导出数据
				function exportExcel() {

					if (grid.getData().length == 0) {

						$.ligerDialog.error("请先查询数据！");

						return;
					}

					var selPara = {};

					$.each(grid.options.parms, function (i, obj) {

						selPara[obj.name] = obj.value;

					});

					var printPara = {
						headCount: 2,
						title: '会计科目',
						type: 3,
						columns: grid.getColumns(1)
					};

					ajaxJsonObjectByUrl("queryAccSubj.do?isCheck=false", selPara, function (responseData) {
						printGridView(responseData, printPara);
					});
				}
				
		function printDate(){
			 if(grid.getData().length==0){
			
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
		var heads={
		      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		      		  "rows": [
								//{"cell":0,"value":"购置日期："+$("#begin_date").val()+"至"+$("#end_date").val(),"colSpan":"5"},
								//{"cell":3,"value":"系统名称："+liger.get("mod_code").getText(),"from":"right","align":"right","colSpan":"4"}  
		      		  ]
		      	};
		   		
			var printPara={
				rowCount:1,
				title:'会计科目',
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.commonbuilder.AccSubjService",
				method_name: "queryAccSubjPrint",
				bean_name: "accSubjService",
				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
				};
		
			//执行方法的查询条件
			$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
			
		officeGridPrint(printPara); 
		
		}
			</script>
		</head>

		<body style="padding: 0px; overflow: hidden;">
			<div id="pageloading" class="l-loading" style="display: none"></div>

			<div id="toptoolbar"></div>
			<div id="toptoolbar1"></div>
			<table cellpadding="0" cellspacing="0" class="l-table-edit">

				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">科目编码：</td>
					<td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">科目名称：</td>
					<td align="left" class="l-table-edit-td"><input name="subj_name" type="text" id="subj_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">科目级次：</td>
					<td align="left" class="l-table-edit-td"><input name="subj_level" type="text" id="subj_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">是否末级：</td>
					<td align="left" class="l-table-edit-td"><input type="text" id="is_last" /></td>
					<td align="left"><input type="button" value=" 查询（Q）" onclick="query();" /></td>
				</tr>

			</table>

			<div style="width: 100%;height:100%;border:1px">
				<div style="border:1px">
					<input type="button" value=" 添加（A）" onclick="add_open();"  />
					<input type="button" value=" 删除（D）" onclick="delete_btn();" />
					<input type="button" value=" 继承" onclick="extend();" />
					<input type="button" value=" 导入" onclick="imp();" />
					<input type="button" value=" 导出Excel" onclick="exportExcel();" />
					<input  type="button" value=" 打 印" onclick="printDate();"/> 
					<input  type="button" value="集团同步" onclick="synSubj();"/>  
				</div>
				<div id="subj_tab" style="width: 100%;overflow:hidden; border:1px solid #A3C0E8; ">

					<div id="all" title="全部" style="height:100%">

						<div id="maingrid"></div>

					</div>

				</div>
			</div>

			<div id="resultPrint" style="display:none">
				<table width="100%">
					<thead>
						<tr>
							<th width="200">科目编码</th>
							<th width="200">科目名称</th>
							<th width="200">科目全称</th>
							<th width="200">级次</th>
							<th width="200">性质</th>
							<th width="200">方向</th>
							<th width="800">辅助核算
								<th width="200">核算1</th>
								<th width="200">核算2</th>
								<th width="200">核算3</th>
								<th width="200">核算4</th>
							</th>

						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>

		</body>

		</html>