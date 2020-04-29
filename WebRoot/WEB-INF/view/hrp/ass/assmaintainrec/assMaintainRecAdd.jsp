<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
    var dataFormat;
    var grid; 
	var gridManager = null; 
	var ass_nature; 
	var gridItem;
	var gridManagerItem = null;
	var selectData = "";
	var clicked = 0;
	var ass_card_no = 0;
	var editor;
	var maintain_unit_dict = { 
			Rows : [ {
				"id" : 0,
				"text" : "年"
			}, {
				"id" : 1,
				"text" : "月"
			},{
				"id" : 2,
				"text" : "日"
			},{
				"id" : 3,
				"text" : "时"
			},{
				"id" : 4,
				"text" : "分"
			}    ],
			Total : 5
		};
     $(function (){
    	 $("#layout1").ligerLayout({
 			rightWidth : '600',
 			//heightDiff: -8,
 			//每展开左边树即刷新一次表格，以免结构混乱
 			onRightToggle : function() {
 				grid._onResize();
 			},
 			//每调整左边树宽度大小即刷新一次表格，以免结构混乱
 			onEndResize : function(a, b) {
 				grid._onResize();
 			}
 		});
        loadDict();//加载下拉框
        loadHead();
        loadHeadItem();
     });  
     
     function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		grid.options.parms.push({name : 'rec_id',value : $("#rec_id").val() == ""?"0":$("#rec_id").val()});
    		grid.loadData(grid.where);
     }
     
     function queryItem(rec_id, detail_id) {

 		gridItem.options.parms = [];
 		gridItem.options.newPage = 1;
 		gridItem.options.parms.push({
 			name : 'rec_id',
 			value : rec_id == "" || rec_id == null
 					|| rec_id == "undefined" ? "0" : rec_id
 		});

 		gridItem.options.parms.push({
 			name : 'detail_id',
 			value : detail_id == "" || detail_id == null
 					|| detail_id == "undefined" ? "0" : detail_id
 		});
 		gridItem.loadData(gridItem.where);
 	}
      
       function loadHead(){
       	grid = $("#maingrid").ligerGrid({
       		
              columns: [  

 						{
								display : '资产卡片号',
								name : 'ass_card_no',
								width: 150,
								align : 'left',
								textField : 'ass_card_no',
								editor : {
									type : 'select',
									valueField : 'ass_card_no',
									textField : 'ass_card_no',
									selectBoxWidth : 500,
									selectBoxHeight : 240,
									grid : {
										columns : [ {display : '资产卡片号',
													 name : 'ass_card_no',
													 align : 'left'
														},{
											display : '编码',
											name : 'ass_code',
											align : 'left'
										}, {
											display : '名称',
											name : 'ass_name',
											align : 'left'
										}, {
											display : '分类名称',
											name : 'ass_type_name',
											align : 'left'
										}, {
											display : '是否停用',
											name : 'is_stop',
											align : 'left'
										} ],
										switchPageSizeApplyComboBox : false,
										onSelectRow : f_onSelectRow_detail,
										url : '../assmaintainplan/choseAssCardNo.do?isCheck=false&use_state=1,2,3,4,5&ass_nature='+liger.get("ass_nature").getValue(),
										pageSize : 30
									},
									alwayShowInDown : true,
									keySupport : true,
									autocomplete : true,
									onSuccess : function() {
										this.parent("tr").next(
												".l-grid-row").find(
												"td:first").focus();
									}
								//
								},
								render : function(rowdata, rowindex,
										value) {
									return rowdata.ass_card_no;
								}

							},
							{ display: '资产编码', name: 'ass_code',width: 100, align: 'left' },
							{ display: '资产名称', name: 'ass_name',width: 100, align: 'left',
								 totalSummary:
				                    {
				                        render: function (suminf, column, cell)
				                        {
				                            return '<div>合计</div>';
				                        },
				                        align: 'center'
				                    }},
							{ display: '型号', name: 'ass_mondl',width: 100, align: 'left'},
							{ display: '规格', name: 'ass_spec', width: 100,align: 'left'},
							{ display: '品牌', name: 'ass_brand', width: 100,align: 'left'}, 
						    { display: '生产厂家', name: 'fac_name',width: 100, align: 'left'},
						    { display: '保养费用', name: 'maintain_money', width: 100,align: 'right', 
						    	editor : {
									type : 'numberbox',
									
									onChanged: function (){
										$("#maintain_money").val(grid.getTotalInfoSum('maintain_money')); 
									}
								},
						    	totalSummary : {
									render: function (suminf, column, cell)
			                        {
										/* $("#maintain_money").val(suminf.sum); */
			                            return '<div>' + formatNumber(suminf.sum,'${ass_05005}',1) + '</div>';
			                        }
								} ,
								render : function(item) {
									
									return formatNumber(
											item.maintain_money, '${ass_05005}', 1);
								}
						    
						    },
							{ display: '工时单位', name: 'maintain_unit',width: 100, align: 'left',
						    	editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									data : maintain_unit_dict.Rows,
									keySupport : true,
									autocomplete : true
								},
								render : function(item) {
									if (item.maintain_unit == 0) {
										return '年';
									} else if (item.maintain_unit == 1) {
										return '月';
									} else if(item.maintain_unit == 2){
										return '日';
									}else if(item.maintain_unit == 3){
										return '时';
									}else if(item.maintain_unit == 4){
										return '分';
									}
								}	
							},
						    { display: '保养工时', name: 'maintain_hours', width: 100,align: 'left',editor : { type : 'text',
						    	onChanged: function (){
								$("#maintain_hours").val(grid.getTotalInfoSum('maintain_hours')); 
							} }
							}
                        ],
                        dataAction: 'server',dataType: 'server',usePager:true,
                        url:'../assmaintainrecass/queryAssMaintainRecAss.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue(),
                        width : '100%',
  						height : '100%',	
  						checkbox : true,
  						enabledEdit : true,
  						alternatingRow : true,
  						onBeforeEdit : f_onBeforeEdit,
  						onBeforeSubmitEdit : f_onBeforeSubmitEdit,
  						onAfterEdit : f_onAfterEdit,
  						isScroll : true,
  						checkbox : true,
  						rownumbers : true,
  						delayLoad:true,
  						selectRowButtonOnly : true,//heightDiff: -10,
                      toolbar : {
 	 							items : [ {text : '保存',id : 'save',click : save,icon : 'save'}, {line : true}, 
 	 							          {text : '删除',id : 'delete',click : itemclick,icon : 'delete'}, {line : true}, 
 	 							          {text : '引入资产卡片',id : 'create',click : itemclick,icon : 'save'},{line : true}, 
 	 							          {text : '关闭',id : 'close',click : this_close,icon : 'candle'} ,{line : true}, 
 	 							          {text : '引入保养计划',id : 'refMaintainPlan',click : itemclick,icon : 'save'} 
  							        ]
  						},
                        onCheckRow : function(checked, data, rowid, rowdata) {
							queryItem(data.rec_id, data.detail_id);
							loadHeadItem();
							isItem_addRow();
						}
                      });

           gridManager = $("#maingrid").ligerGetGridManager();
           
        }
       
       function loadHeadItem() {
   		gridItem = $("#recItemGrid")
   				.ligerGrid(
   						{
   							columns : [
   									{
   										display : '项目编码',
   										name : 'item_code',
   										align : 'left',
   										width : 120
   									},
   									{
   										display : '项目名称',
   										name : 'item_name',
   										align : 'left',
   										textField : 'text',
   										width : 260,
   										editor : {
   											type : 'select',
   											valueField : 'id',
   											textField : 'text',
   											selectBoxWidth : 350,
   											selectBoxHeight : 240,
   											grid : {
   												columns : [ {
   													display : '项目编码',
   													name : 'id',
   													align : 'left'
   												}, {
   													display : '项目名称',
   													name : 'text',
   													align : 'left'
   												} ],
   												switchPageSizeApplyComboBox : false,
   												onSelectRow : f_onSelectRow_item,
   												url : '../queryAssMaintainItem.do?isCheck=false',
   												pageSize : 30
   											},
   											alwayShowInDown : true,
   											keySupport : true,
   											autocomplete : true,
   											onSuccess : function() {
   												this.parent("tr").next(
   														".l-grid-row").find(
   														"td:first").focus();

   											}
   										}
   									},
   									{
   										display : '巡检结果',
   										name : 'is_normal',
   										align : 'left',
   										width : 150,
   										type : 'text',
   										render : function(rowdata, index, value) {
   											var str1;
   											var str2;
   											var str3;

   											if (rowdata.is_normal == 1) {
   												str1 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='1' id='is_normal_yes"
   														+ index
   														+ "' name='is_normal"
   														+ index
   														+ "' checked='checked' />";
   												str2 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='2' id='is_normal_yno"
   														+ index
   														+ "' name='is_normal"
   														+ index + "'/>";
   												str3 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='9' id='is_normal_null"
   														+ index
   														+ "' name='is_normal"
   														+ index + "'/>";
   											} else if (rowdata.is_normal == 2) {
   												str1 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='1' id='is_normal_yes"
   														+ index
   														+ "' name='is_normal"
   														+ index + "'/>";
   												str2 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='2' id='is_normal_yno"
   														+ index
   														+ "' name='is_normal"
   														+ index
   														+ "' checked='checked' />";
   												str3 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='9' id='is_normal_null"
   														+ index
   														+ "' name='is_normal"
   														+ index + "'/>";
   											} else if (rowdata.is_normal == 9) {
   												str1 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='1' id='is_normal_yes"
   														+ index
   														+ "' name='is_normal"
   														+ index + "' />";
   												str2 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='2' id='is_normal_yno"
   														+ index
   														+ "' name='is_normal"
   														+ index + "' />";
   												str3 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='9' id='is_normal_null"
   														+ index
   														+ "' name='is_normal"
   														+ index
   														+ "' checked='checked'/>";
   											} else {
   												str1 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='1' id='is_normal_yes"
   														+ index
   														+ "' name='is_normal"
   														+ index + "' />";
   												str2 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='2' id='is_normal_yno"
   														+ index
   														+ "' name='is_normal"
   														+ index + "'/>";
   												str3 = "<input type='radio' onChange='getRadioValue("
   														+ index
   														+ ")' value='9' id='is_normal_null"
   														+ index
   														+ "' name='is_normal"
   														+ index + "'/>";
   											}
   											return ""
   													+ "<table class='l-table-edit'>"
   													+ "<tr>"
   													+ "<td style='padding-top: 6px;'>"
   													+ str1
   													+ "</td>"
   													+ "<td style='padding-top: 5px;'><label for='is_normal_yes"+index+"'>通过</label></td>"
   													+ "<td style='padding-top: 6px;'>"
   													+ str2
   													+ "</td>"
   													+ "<td style='padding-top: 5px;'><label for='is_normal_yno"+index+"'>未通过</label></td>"
   													+ "<td style='padding-top: 6px;'>"
   													+ str3
   													+ "</td>"
   													+ "<td style='padding-top: 5px;'><label for='is_normal_null"+index+"'>不适用</label></td>"
   													+ "</tr>" + "</table>" + "";
   										}

   									} ],
   							dataAction : 'server',
   							dataType : 'server',
   							usePager : false,
   							url : 'queryAssRecItem.do?isCheck=false',
   							width : '100%',
   							height : '100%',
   							checkbox : true,
   							enabledEdit : true,
   							alternatingRow : true,
   							//onBeforeEdit : f_onBeforeEditItem,
   							onBeforeSubmitEdit : f_onBeforeSubmitEditItem,
   							onAfterEdit : f_onAfterEditItem,
   							isScroll : true,
   							checkbox : true,
   							rownumbers : true,
   							delayLoad : true,
   							selectRowButtonOnly : true,//heightDiff: -10,
   							toolbar : {
   								items : [ {
   									text : '生成',
   									id : 'copy',
   									click : copy,
   									icon : 'copy'
   								}, {
   									line : true
   								}, {
   									text : '保存',
   									id : 'saveItem',
   									click : saveItem,
   									icon : 'save'
   								}, {
   									line : true
   								}, {
   									text : '删除',
   									id : 'deleteItem',
   									click : deleteItem,
   									icon : 'delete'
   								}, {
   									line : true
   								}, {
   									text : '全部通过',
   									id : 'allDdopt',
   									click : allDdopt,
   									icon : 'audit'
   								}, {
   									line : true
   								}, {
   									text : '全部取消',
   									id : 'allNotDdopt',
   									click : allNotDdopt,
   									icon : 'bcancle'
   								} ]
   							}
   						});

   		gridManagerItem = $("#recItemGrid").ligerGetGridManager();
   	}
       
       
       function f_onBeforeEditItem(e) {
   		rowindex_id_item = e.rowindex;
   		clicked = 0;
   		column_name_item = e.column.name;
   	}
       
       function allDdopt() {
   		var data = gridItem.getData();
   		$.each(data, function(i, v) {
   			var row = gridItem.getRow(i);
   			row.is_normal = 1;
   			$("input[name='is_normal" + i + "'][value=2]").attr('checked',
   					false);
   			$("input[name='is_normal" + i + "'][value=9]").attr('checked',
   					false);
   			$("input[name='is_normal" + i + "'][value=1]")
   					.attr('checked', true);
   		});
   		gridItem.reRender();
   	}
      
       
       function allNotDdopt() {
   		var data = gridItem.getData();
   		$.each(data, function(i, v) {
   			var row = gridItem.getRow(i);
   			row.is_normal = 0;
   			$("input[name='is_normal" + i + "']").attr('checked', false);

   		});
   	}
       
    // 编辑单元格提交编辑状态之前作判断限制
   	function f_onBeforeSubmitEditItem(e) {
   		return true;
   	}
   	// 跳转到下一个单元格之前事件
   	function f_onAfterEditItem(e) {
   		return true;
   	}
   	
   	
   	function validateGridItem() {
		var data = gridManagerItem.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data, function(i, v) {
			if (!isnull(v.item_code)) {
				var key = v.item_code;
				var value = "第" + (i + 1) + "行";
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = "1";
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});

		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
			$.ligerDialog.warn("无数据保存");
			return false;
		}
		return true;
	}
   	
   	
   	function getRadioValue(index) {
		var row = gridItem.getRow(index);
		row.is_normal = $("input[name='is_normal" + index + "']:checked").val();
	}
       
   	
   	function saveItem() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个资产操作');
			return false;
		} else {
			var rec_id = 0;
			var detail_id = 0;
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.error('所选卡片没有保存');
					flag = false;
				}
				rec_id = this.rec_id;
				detail_id = this.detail_id;
			});
			if (flag) {
				gridManagerItem.endEdit();
				var dataItem = gridManagerItem.getData();
				$.each(dataItem, function() {
					this.ass_card_no = ass_card_no;
				})
				
				if (validateGridItem()) {
					ajaxJsonObjectByUrl("saveAssRecItem.do?isCheck=false", {
						rec_item_data : JSON.stringify(dataItem)
					}, function(responseData) {
						if (responseData.state == "true") {
							queryItem(rec_id, detail_id);
						}
					});
				}
			}
		}

	}
   	
   	function deleteItem() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个资产操作');
			return false;
		} else {
			var ass_card_no = 0;
			var rec_id = 0;
			var detail_id = 0;
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.error('所选资产没有保存');
					flag = false;
				}
				ass_card_no = this.ass_card_no;
				rec_id = this.rec_id;
				detail_id = this.detail_id;
			});
			if (flag) {
				var dataItem = gridManagerItem.getCheckedRows();
				if (dataItem.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(dataItem).each(
							function() {
								if (isnull(this.group_id)) {
									gridManagerItem.deleteSelectedRow();
								} else {
									ParamVo.push(this.group_id + "@"
											+ this.hos_id + "@"
											+ this.copy_code + "@"
											+ this.rec_id + "@"
											+ this.detail_id + "@"
											+ this.item_code);
								}
							});
					if (ParamVo == "") {
						isItem_addRow();
						return false;
					}
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAssRecItem.do?isCheck=false", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									queryItem(rec_id, detail_id);
								}
							});
						}
					});
				}
			}
		}
	}
   	
   	
   	function copy() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.error('只能选择单个资产');
			return;
		} else {
			var ass_card_no = 0;
			var rec_id = 0;
			var detail_id = 0;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.error('所选卡片没有保存');
					return;
				}
				ass_card_no = this.ass_card_no;
				rec_id = this.rec_id;
				detail_id = this.detail_id;
			});
			var itemDataLength = gridItem.getData().length;//获取行数
			for (var i = itemDataLength; i >= 0; i--) {
				gridItem.deleteRow(i);//删除行
			}

			var formPara = {
				'ass_card_no' : ass_card_no
			};
			//gridItem.set("url","buildAssAcceptItem.do?ass_id="+ass_id.split("@")[0]);
			ajaxJsonObjectByUrl("buildAssRecItem.do?isCheck=false", formPara, function(
					responseData) {
				$.each(responseData.Rows, function(i, v) {
					gridItem.addRows({
						item_code : v.maintain_item_code,
						item_name : v.maintain_item_name,
						rec_id : rec_id,
						detail_id : detail_id,
						is_normal : 0
					});

				});

			}, false);
		}
	}
   	
   	function f_onSelectRow_item(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		if (column_name_item == "item_name") {
			if (selectData != "" || selectData != null) {
				var data1 = gridManager.getCheckedRows();
				if (data1.length == 0 || data1.length > 1) {
					$.ligerDialog.warn('选择单个资产操作');
					return false;
				} else {
					var rec_id = 0;
					var detail_id = 0;
					$.each(data1, function() {
						if (isnull(this.group_id)) {
							$.ligerDialog.error('所选资产没有保存');
							return;
						}
						rec_id = this.rec_id;
						detail_id = this.detail_id;
					});
					gridItem.updateRow(rowindex_id_item, {
						item_code : data.id,
						item_name : data.text,
						is_normal : 0,
						rec_id : rec_id,
						detail_id : detail_id
					});
				}

			}
		}
		return true;
	}
   	
       var rowindex_id = "";
       
     	var column_name="";
     	
     	function f_onBeforeEdit(e) {
     		
     		rowindex_id = e.rowindex;
     		
     		clicked = 0;
     		
     		column_name=e.column.name;
     		
     	}
     	
     	function this_close() {
    		frameElement.dialog.close();
    	}
     	//选中回充数据
      	function f_onSelectRow_detail(data, rowindex, rowobj) {
      		selectData = "";
      		selectData = data;
			if(column_name == "ass_card_no"){
     			if (selectData != "" || selectData != null) {
     				//回充数据 
     				grid.updateRow(rowindex_id, {
     					ass_card_no : data.ass_card_no,
     					ass_code : data.ass_code,
     					ass_name : data.ass_name,
     					fac_id : data.fac_id,
     					fac_no : data.fac_no,
     					fac_code : data.fac_code,
     					fac_name : data.fac_name,
     					ass_mondl : data.ass_mondl,
     					ass_spec : data.ass_spec
     				});
     			}
     		} 
			
		if(column_name == "maintain_item_name"){
     			if (selectData != "" || selectData != null) {
     				grid.updateRow(rowindex_id, {
     					maintain_item_code : data.maintain_item_code,
     					maintain_item_name : data.maintain_item_name,
     					
     				});

     			}
     		}
      		return true;
      	}
     	
       	function f_onSelectRow(data, rowindex, rowobj) {
       		return true;
       	}
       	
       	// 编辑单元格提交编辑状态之前作判断限制
       	function f_onBeforeSubmitEdit(e) {
       		return true;
       	}
       	
       	// 跳转到下一个单元格之前事件
       	function f_onAfterEdit(e) {
       		
       		return true;
       	}
       	
       	
    	function itemclick(item) {
   		if (item.id) {
   			switch (item.id) {
   			case "delete":
   				var data = gridManager.getCheckedRows();
   				if (data.length == 0) {
   					$.ligerDialog.error('请选择行');
   				} else { 
   					var ParamVo = [];
   					var i = 0;
   					$(data).each(
   							function() {
   								if(isnull(this.detail_id)){
   									gridManager.deleteSelectedRow();
								}else{
 			  								ParamVo.push(
 					  									this.group_id + "@" + 
 					  									this.hos_id + "@" + 
 					  									this.copy_code + "@"+ 
 					  									this.plan_id + "@" + 
 					  									this.ass_card_no+"@"+
 					  									this.detail_id
   									);
								}
   								i++;
   							});
   					if(ParamVo == ""){
   						is_addRow();
						return;
					}
   					$.ligerDialog.confirm('确定删除?', function(yes) {
   						
   						if (yes) {
   							
   							ajaxJsonObjectByUrl("../assmaintainrecitem/deleteAssMaintainRecItem.do",
   									
   									{ParamVo : ParamVo.toString()}, function(responseData) {
   										
   								if (responseData.state == "true") {
   									
   									query();
   									$("#maintain_money").val(responseData.maintain_money);
									is_addRow();
   									
   								}
   							});
   						}
   					});
   				}
   				return;
   			case "create":
				//grid.deleteSelectedRow();
					var dept_id = liger.get("dept_id").getValue().split("@")[0];
				     var  dept_no = liger.get("dept_id").getValue().split("@")[1];
				     if (dept_id == null || dept_no == null || dept_id == ""
							|| dept_no == "") {
				    	 dept_no = "";
				    	 dept_id = "";
					}
		            var fn = $.ligerui.getPopupFn({
		                top : 80,
		                onSelect: function (e) {
		                    grid.addRows(e.data);
		                },
		                grid: {
		                    columns: [

		      						{ display : '资产卡片号', name : 'ass_card_no', align : 'left', },
		      						
		      						{ display: '资产编码', name: 'ass_code', align: 'left', },
		      						
		      						{ display: '资产名称', name: 'ass_name', align: 'left', },
		      						 
		      						{ display: '型号', name: 'ass_mondl', align: 'left',  },
		      						
		      						{ display: '规格', name: 'ass_spec', align: 'left', },
		      						
		      						{ display: '品牌', name: 'ass_brand', align: 'left', },
		      						
		      						{ display: '生产厂家', name: 'fac_name', align: 'left', },
		      						
		                           ],
		                           dataAction: 'server',dataType: 'server',usePager:true,url:'../assmaintainplan/choseAssCardNo.do?isCheck=false&use_state=1,2,3,4,5&ass_nature='+liger.get("ass_nature").getValue()+'&ass_card_no='+liger.get("ass_card_no").getValue()+'&ass_name='+liger.get("ass_name").getValue()+'&dept_id='+dept_id+'&dept_no='+dept_no,
		                           width: '100%', height: '100%', checkbox: true,rownumbers:true,
		                } 
		            });

		            fn();
		        	return;
   	   		case "refMaintainPlan":
	   	   		if(liger.get("plan_id").getValue() == ""){
		  			$.ligerDialog.error('保养计划不能为空');
		  			return;
		  		}
		   	   	/* if(liger.get("ass_nature").getValue() == ""){
					$.ligerDialog.error('资产性质不能为空');
					return;
				} */
   	   			var fnn = $.ligerui.getPopupFn({
   	                top : 80,
   	                onSelect: function (e) {
   	                	//liger.get("ass_nature").setValue(e.data[0].ass_nature);
   	                	var itemDataLength = grid.getData().length;//获取行数
						for (var i = itemDataLength; i >= 0; i--) {
							grid.deleteRow(i);//删除行
						}
   	                	grid.addRows(e.data);
   	                	save();
   	                    
   	                },
   	                grid: {
   	                    columns: [{
									display : '资产卡片号',
									name : 'ass_card_no',
									align : 'left'
								},{
									display : '资产编码',
									name : 'ass_code',
									align : 'left'
								}, {
									display : '资产名称',
									name : 'ass_name',
									align : 'left'
								}, {
									display : '规格',
									name : 'ass_spec',
									align : 'left'
								}, {
									display : '型号',
									name : 'ass_model',
									align : 'left'
								}, {
									display : '品牌',
									name : 'ass_brand',
									align : 'left'
								},{
									display : '分类名称',
									name : 'ass_type_name',
									align : 'left'
								},{
									display : '是否停用',
									name : 'is_stop',
									align : 'left'
								},{ 
									display: '生产厂家',
									name: 'fac_name', 
									align: 'left' }
   	      						
   	                           ],
   	                           dataAction: 'server',dataType: 'server',usePager:true,url:'queryMaintainPlanRec.do?isCheck=false&plan_id='+liger.get("plan_id").getValue()+'&state=1',
   	                           width: '100%', height: '100%', checkbox: true,rownumbers:true,
   	                } 
   	            });

   	            fnn();
   			}
   			
   		}
   	}
    
     
     function  save(){
    	 gridManager.endEdit();
    	 /* if(liger.get("plan_id").getValue() == ""){
	  			$.ligerDialog.error('保养计划不能为空');
	  			return;
	  		} */
    	  if(liger.get("maintain_dept_id").getValue() == ""){
	  			$.ligerDialog.error('保养部门不能为空');
	  			return;	
	  		} 
	  		if(liger.get("ass_nature").getValue() == ""){
	  			$.ligerDialog.error('资产性质不能为空');
	  			return;
	  		}
	  		
	  		if(liger.get("fact_exec_emp").getValue() == ""){
	  			$.ligerDialog.error('执行人不能为空');
	  			return;
	  		} 
	  		/* if($("#maintain_hours").val() == ""){
	  			$.ligerDialog.error('保养工时不能为空');
	  			return;	
	  		} */ 
	  		/* if($("#maintain_money").val() == ""){
	  			$.ligerDialog.error('保养费用不能为空');
	  			return;	
	  		} */
	  		if($("#fact_exec_date").val() == ""){
	  			$.ligerDialog.error('实际执行日期不能为空');
	  			return;	
	  		}
	  		
	  		var data = gridManager.getData();	
	  	  var num= 0;
	  	  for(var i = 0;i < data.length; i++){
	  	   
	  	   if(data[i].ass_card_no){
	  	    num ++;
	  	   }
	  	    }
	  	  if(!num){
	  	   $.ligerDialog.error('明细数据不能为空');
	  	   return false;
	  	  }
        var formPara={ 
            
           rec_id : $("#rec_id").val() == ""?0:$("#rec_id").val(),
        		   
           rec_no:$("#rec_no").val(),
            
           ass_year:$("#ass_year").val(),
            
           ass_month:$("#ass_month").val(),
            
           plan_id: liger.get("plan_id").getValue(),
          
           ass_nature: liger.get("ass_nature").getValue(),
           
           fact_exec_emp: liger.get("fact_exec_emp").getValue(),
            
           plan_exec_date:$("#plan_exec_date").val(),
            
           fact_exec_date:$("#fact_exec_date").val(),
            
           maintain_hours:$("#maintain_hours").val(),
            
           maintain_money:$("#maintain_money").val(),
            
           maintain_desc:$("#maintain_desc").val(), 
           
           maintain_dept_id: liger.get("maintain_dept_id").getValue().split("@")[0],
            
           maintain_dept_no: liger.get("maintain_dept_id").getValue().split("@")[1],
           
		   ParamVo : JSON.stringify(data)
         }; 
        if(validateGrid()){
	        ajaxJsonObjectByUrl("addAssMaintainRec.do",formPara,function(responseData){
	            if(responseData.state=="true"){
	            	$("#rec_id").val(responseData.rec_id);
	            	$("#rec_no").val(responseData.rec_no);
	            	parentFrameUse().query();
	                query();
					$("#maintain_money").val(responseData.maintain_money);
					is_addRow();
	            }
	        });
        }
    }
     
     
     function validateGrid() {
    		var data = gridManager.getData();
   		var msg = "";
   		var targetMap = new HashMap();
   		var msgMap = new HashMap();
   		//删除空行
   		$.each(data, function(i, v) {
   			var key = v.ass_card_no + "|" +v.ass_code + "|" + v.ass_model + "|" + v.ass_spec;
   			var value = "第" + (i + 1) + "行";
   			if (isnull(v.ass_card_no)) {
   				//有漏洞 暂时先这么处理
   				gridManager.deleteRow(i);
   				return;
   			}
   			if (isnull(v.ass_card_no)) {
   				msg += "[卡片编号]、";
   			}
   		
   			/* if (isnull(v.maintain_item_code)) {
   				msg += "[保养项目]";
   			} */
   			/* if (isnull(v.maintain_hours)) {
   				msg += "[保养工时]";
   			} */
   			if (msg != "") {
   				msgMap.put(value+msg+"不能为空或不能为零！\n\r", "");
   			}
   			if (isnull(targetMap.get(key))) {
   				targetMap.put(key, value);
   			} else {
   				msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r", value);
   			}
   		});
   		if (msg != "") {
   			$.ligerDialog.warn(msgMap.keySet());
   			return false;
   		}
   		if (data.length == 0) {
   			$.ligerDialog.warn("无数据保存");
   			return false;
   		}
   		return true;
   	}
  
    function loadDict(){
    	
    	$("#rec_no").ligerTextBox({disabled:true,cancelable: false,width:160});
    	$("#maintain_money").ligerTextBox({disabled:true,cancelable: false,width:160});
    	$("#maintain_hours").ligerTextBox({disabled:true,cancelable: false,width:160});
        autodate("#ass_year","YYYY");
        
        autodate("#ass_month","MM");
        
            //字典下拉框
    	//计划执行人
    	autocomplete("#fact_exec_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true);
    	
    	//计划Id
    	autocomplete("#plan_id","../queryAssMaintainPlanDict.do?isCheck=false", "id","text", true, true);
    	
    	$("#plan_id").change(function(){
    		var formPara = {plan_id:liger.get("plan_id").getValue()};
    		ajaxJsonObjectByUrl("queryMaintainPlanRec.do?isCheck=false",formPara,function(responseData){
            	//console.log(responseData)
            	liger.get("ass_nature").setValue(responseData.Rows[0].ass_nature);
	        });
    	});
    	
    	//保养部门
    	autocomplete("#maintain_dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true);
    	
    	//资产性质
    	
    	  $("#ass_nature").ligerComboBox({
            	url: '../queryAssNaturs.do?isCheck=false',
            	valueField: 'id',
             	textField: 'text', 
             	selectBoxWidth: 160,
            	autocomplete: true,
            	width: 160,
            	onSelected :function(id,text){ 
            		loadHead();
            		is_addRow();
            		/* if(ass_nature != id){
            			liger.get('plan_id').setValue(null);
            			loadHead();
                		is_addRow();
            		}*/
            	} 
   		  });
    	
    	  $("#ass_card_no").ligerTextBox({cancelable: false,width:160}); 
  		
  		  $("#ass_name").ligerTextBox({width:160});
    	
    	  $("#ass_year").ligerTextBox({width:160});
    	  
    	  $("#ass_month").ligerTextBox({width:160});
    	  
    	  $("#plan_exec_date").ligerTextBox({width:160});
    	  
    	  $("#fact_exec_date").ligerTextBox({width:160});
    	  
    	/*   $("#maintain_hours").ligerTextBox({width:160});
    	  
    	  $("#maintain_money").ligerTextBox({width:160}); */
        
    	  
    	  $("#dept_id").ligerComboBox({
   			url : '../queryDeptDict.do?isCheck=false',
   			valueField : 'id',
   			textField : 'text',
   			selectBoxWidth : 160,
   			autocomplete : true,
   			width : 160,
   			onSelected : function(id, text) {
   				//loadHead();
   			}
   		});
          
     } 
    
    function isItem_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			gridItem.addRow();
		}, 1000);
	}
 
	function is_addRow() {
		 setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000); 

	}
    </script>
  
  </head>
  
   <body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <input type="hidden" id="rec_id" name="rec_id"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>保养序号：</td>
            <td align="left" class="l-table-edit-td"><input name="rec_no" type="text" id="rec_no"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计年度：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_year" type="text" id="ass_year"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计月份：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_month" type="text" id="ass_month"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">保养计划：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_id" type="text" id="plan_id"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>保养部门：</td>
            <td align="left" class="l-table-edit-td"><input name="maintain_dept_id" type="text" id="maintain_dept_id"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature" value="${ass_nature}"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>执行人：</td>
            <td align="left" class="l-table-edit-td"><input name="fact_exec_emp" type="text" id="fact_exec_emp"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划执行日期：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_exec_date" type="text" id="plan_exec_date"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>实际执行日期：</td>
            <td align="left" class="l-table-edit-td"><input name="fact_exec_date" type="text" id="fact_exec_date"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>保养工时：</td>
            <td align="left" class="l-table-edit-td"><input name="maintain_hours" type="text" id="maintain_hours"  value="0"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>保养费用：</td>
            <td align="left" class="l-table-edit-td"><input name="maintain_money" type="text" id="maintain_money"  value="0" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产卡片号：</td>
             <td align="left" class="l-table-edit-td"><input name="ass_card_no" disabled="disabled" type="text" id="ass_card_no"   /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_name" disabled="disabled" type="text" id="ass_name"   /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
			 <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" /></td>
        </tr>
         <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">保养说明：</td>
             <td align="left" class="l-table-edit-td" colspan="4">
            	<textarea rows="3" cols="70"  name="maintain_desc"  id="maintain_desc"  ></textarea>
             </td>
             <td align="left"></td>
        </tr>
      
    </table>
   <div id="layout1">
		<div position="center" title="资产列表">
			<div>
	<div id="maingrid"></div>
	</div>
		</div>
		<div position="right" title="保养项目列表">
			<div>
				<div id="recItemGrid"></div>
			</div>
		</div>
	</div>
    </body>
</html>
