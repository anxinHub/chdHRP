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
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var selectData = "";
    var ass_card_no = '${ass_card_no}';
  
    var is_well_dict = {
    		Rows : [ {
    			"id" : 0,
    			"text" : "否"
    		}, {
    			"id" : 1,
    			"text" : "是"
    		} ],
    		Total : 2
    	};
    $(function ()
    {
        loadDict()//加载下拉框
    	loadHead(null);	
        query();
    });
    function setAssCardNo(no){
    	ass_card_no = no;
    	query();
    }
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		grid.options.parms.push({
    			name : 'ass_nature',
    			value : '${ass_nature}'
    		});
    		grid.options.parms.push({
    			name : 'ass_card_no',	
    			value : ass_card_no
    		});
    		grid.loadData(grid.where);
    		is_addRow();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '附件编码', name: 'accessory_code', align: 'left',
							editor : {
								type : 'text'
							}
					 		},
                     { display: '附件名称', name: 'accessory_name', align: 'left',
								totalSummary : {
									render : function(suminf, column,
											cell) {
										return '<div>合计</div>';
									},
									align : 'center'
								},
								editor : {
									type : 'text'
								}
					 		},
                     { display: '附件数量', name: 'accessory_amount', align: 'left',
								editor : {
									type : 'int'
								}
					 		},
                     { display: '附件单价', name: 'accessory_price', align: 'right',
								editor : {
									type : 'float',
									precision : 2
								},
								render : function(item) {
									return formatNumber(
											item.accessory_price, '${ass_05006}', 1);

								}
					 		},
                     { display: '附件金额', name: 'accessory_money', align: 'right',
								totalSummary : {
									render : function(suminf, column,
											cell) {
										return '<div>'
												+ formatNumber(
														suminf.sum, '${ass_05005}',
														1) + '</div>';
									}
								},
								render : function(item) {
									item.accessory_money = item.accessory_amount
											* item.accessory_price;
									return formatNumber(
											item.accessory_money, '${ass_05005}', 1);
								}
					 		},
                     { display: '资产性质', name: 'naturs_code', align: 'left',
								textField : 'ass_nature',
								valueField : 'ass_nature',
								editor : {
									type : 'select',
									valueField : 'ass_nature',
									textField : 'ass_nature',
									selectBoxWidth : 500,
									selectBoxHeight : 240,
									grid : {
										columns : [ {
											display : '资产性质',
											name : 'ass_nature',
											align : 'left'
										}, {
											display : '卡片编码',
											name : 'ass_card_no',
											align : 'left'
										}, {
											display : '资产编码',
											name : 'ass_code',
											align : 'left'
										}, {
											display : '资产名称',
											name : 'ass_name',
											align : 'left'
										}, {
											display : '资产规格',
											name : 'ass_spec',
											align : 'left'
										}, {
											display : '资产型号',
											name : 'ass_model',
											align : 'left'
										} , {
											display : '资产品牌',
											name : 'ass_brand',
											align : 'left'
										}, {
											display : '计量单位',
											name : 'ass_unit_name',
											align : 'left'
										} , {
											display : '生产厂商',
											name : 'fac_name',
											align : 'left'
										} , {
											display : '供应商',
											name : 'ven_name',
											align : 'left'
										}  ],
										switchPageSizeApplyComboBox : false,
										onSelectRow : f_onSelectRow_detail,
										url : '../queryAssCardTable.do?isCheck=false',
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
                     { display: '附卡', name: 'accessory_card_no', align: 'left'
					 		},
                     { display: '备注', name: 'note', align: 'left',
								editor : {
									type : 'text'
								}
					 		},
                     { display: '是否停用', name: 'is_stop', align: 'left',
								editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									data : is_well_dict.Rows,
									keySupport : true,
									autocomplete : true
								},
								render : function(item) {
									if (item.is_stop == 1) {
										return '是';
									} else if (item.is_stop == 0) {
										return '否';
									} else {
										return "";
									}
								}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssAccessoryInit.do',
                     width: '100%', height: '90%', checkbox: true,rownumbers:true,delayLoad : true,enabledEdit : true,
                     selectRowButtonOnly:true,//heightDiff: -10,,
                     scroll: true,
                     frozen:true,
                     alternatingRow: false,
                     onAfterEdit : f_onAfterEdit,
                     onBeforeEdit : f_onBeforeEdit,
                     scrollToPage: true,
                     scrollToAppend: true,
                     toolbar: { items: [
			                    	 	{ text: '查询', id:'search', click: query, icon:'search' },
				    	                { line:true },
				    					{ text: '保存', id:'save', click: save, icon:'save' },
				    	                { line:true },
				    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
						                { line:true },
						                { text: '导入', id:'import', click: imp,icon:'up' }
				    	]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function f_onAfterEdit(e) {
		grid.updateCell('accessory_money', e.record.accessory_price
				* e.record.accessory_amount, e.record);
		grid.updateTotalSummary();
		return true;
	}
    var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
    function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		if (column_name == "naturs_code") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					naturs_code : data.ass_nature,
					accessory_card_no : data.ass_card_no
				});

			}
		}
		return true;
	}
    
    function save(){
    	gridManager.endEdit();
    	var data = grid.getData();
    	if(ass_card_no == '0'){
    		parent.$.ligerDialog.warn("请先保存卡片");
    		return;
    	}
    	
		var formPara = {
				ass_nature:'${ass_nature}',
			ass_card_no : ass_card_no,

			ParamVo : JSON.stringify(data)

		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssCardAccessoryInit.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					query();
				}
			}, false);
		}
    }
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	parent.$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){	
                        	if (isnull(this.group_id)) {
								gridManager.deleteSelectedRow();
							} else {
								ParamVo.push(
								this.group_id   +"@"+ 
								this.hos_id   +"@"+ 
								this.copy_code   +"@"+ 
								this.ass_card_no   +"@"+ 
								this.accessory_code 
								);
							}
							});
                        if(ParamVo == ""){
                        	return;
                        }
                        parent.$.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssAccessoryInit.do",{ParamVo : ParamVo.toString(),ass_nature:'${ass_nature}'},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function imp(){
    	
    	if(ass_card_no == '0'){
    		parent.$.ligerDialog.warn("请先保存卡片");
    		return;
    	}
    	
    	var para={
    		    "column": [
    		        {
    		            "name": "accessory_code",
    		            "display": "附件编码",
    		            "width": "200",
    		            "require":true
    		        },
    		        {
    		            "name": "accessory_name",
    		            "display": "附件名称",
    		            "width": "200",
    		            "require":true
    		        },
    		        {
    		            "name": "accessory_amount",
    		            "display": "附件数量",
    		            "width": "200",
    		            "require":false
    		           
    		        },
    		        {
    		            "name": "accessory_price",
    		            "display": "附件单价",
    		            "width": "200",
    		            "require":false
    		        },
    		        {
    		            "name": "naturs_code",
    		            "display": "资产性质编码",
    		            "width": "200",
    		            "require":false
    		        },
    		        {
    		            "name": "naturs_name",
    		            "display": "资产性质名称",
    		            "width": "200",
    		            "require":false
    		        },
    		        {
    		            "name": "accessory_card_no",
    		            "display": "附卡",
    		            "width": "200",
    		            "require":false
    		        },
    		        {
    		            "name": "note",
    		            "display": "备注",
    		            "width": "200",
    		            "require":false
    		        },
    		        {
    		            "name": "is_stop",
    		            "display": "是否停用",
    		            "width": "200",
    		            "require":false
    		        }
    		    ]
    		};
    		parent.importSpreadView("hrp/ass/asscardinit/importAssAccessoryInit.do?isCheck=false&ass_naturs=${ass_nature}&ass_card_no="+ass_card_no,para);
			
    	
    }	
    function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data, function(i, v) {
			if (!isnull(v.accessory_code)) {
				var key = v.accessory_code;
				var value = "第" + (i + 1) + "行";

				if (v.accessory_name == '@' || isnull(v.accessory_name)) {
					msg += "[附件名称]、";
				}

				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = targetMap.get(key) + "与" + value + "重复！\n\r",
					value;
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});

		if (msg != "") {
			parent.$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
			parent.$.ligerDialog.warn("无数据保存");
			return false;
		}
		return true;
	}
    function loadDict(){
            //字典下拉框
            
         }  
    function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>

	<div id="maingrid"></div>
	
</body>
</html>
