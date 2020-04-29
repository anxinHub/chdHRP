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
var dialog = frameElement.dialog;
var call=dialog.get("data")!=null?dialog.get("data").call:null;
var deptgrid,deptgridManager,deptgrid2,initvalue;
var page_type = "${page_type}";
var state = "${state}";
var deleteshow=false;
var collectshow=false;
var auditshow=false;
var saveshow=false;
	$(function() {
		if(call == 'vouch'){
			 deleteshow=true;
			 collectshow=true;
			 auditshow=true;
			 saveshow=true;
			 state = 2;
		}
		
		loadDict();
		loadTopBar();
		loadHead(true);
		loadToolBar();
		
		deptgrid.addRow();
	});
	
	//查询
	function query() {
		if(!$("#create_date").val()){
			$.ligerDialog.error("制单日期不能为空！");
			return;
		}
		if(!liger.get("proj_code").getValue()){
			$.ligerDialog.error("项目不能为空！");
			return;
		}
		deptgrid.options.parms = [];
		deptgrid.options.newPage = 1;
		deptgrid.options.parms.push({name : 'proj_code',value : liger.get("proj_code").getValue()});
		deptgrid.options.parms.push({name : 'create_date',value : $("#create_date").val()});
		deptgrid.options.parms.push({name : 'ser_num',value : liger.get("ser_num").getValue()});
		deptgridManager.loadData(deptgrid.where);
		
		var coop_type = liger.get("coop_type").getValue();
		if(coop_type == 2){
			deptgrid2.options.parms = [];
			deptgrid2.options.newPage = 1;
			deptgrid2.options.parms.push({name : 'proj_code',value : liger.get("proj_code").getValue()});
			deptgrid2.options.parms.push({name : 'create_date',value : $("#create_date").val()});
			deptgrid2.options.parms.push({name : 'ser_num',value : liger.get("ser_num").getValue()});
			deptgridManager2.loadData(deptgrid2.where);
		}
	}
	var directionData = { Rows :[{ direction: 0, text: '借' }, { direction: 1, text: '贷'}]};
	
	function loadHead(bool){
		var enabledEdit = true;
		if(state == 2){
			enabledEdit = false;
		}
    	deptgrid = $("#leftgrid").ligerGrid({
			columns: [
				{display: '院内科室', name: 'dept_name', align: 'left',width:'30%',textField : 'text',
					editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						cancelable:false,
						url : '../../../../sys/queryDeptDict.do?isCheck=false&is_last=1',
						keySupport : true,
						autocomplete : true,
						selectBoxWidth:300,
						 onChanged:function(data){
							if(data.selected){
								data.record.dept_name = data.selected.text;
								data.record.dept_id = data.selected.id.split(".")[0];
								data.record.dept_no = data.selected.id.split(".")[1];
							}else{
								data.record.dept_name = '';
								data.record.dept_id = '';
								data.record.dept_no = '';
							}
						}  
					}
				},
				{display: '分摊比例(%)', name: 'ft_bl', align: 'right' ,width:'20%'},
				{display: '分摊金额', name: 'ft_my', align: 'right' ,width:'20%', editor:{type : 'float'}},
				{display: '备注', name: 'note', align: 'left' ,width:'29.99%', editor:{type : 'text'}}
			],
			dataAction: 'server',usePager:false, url:'queryAccCoopCostDept.do?isCheck=false',
			width: '50%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true,enabledEdit: enabledEdit,
			onLoaded : function(){
				if(state == 1 && bool){
					deptgrid.addRow();
				}
			}
		});
    	deptgridManager = $("#leftgrid").ligerGetGridManager();
    	
    }
	function loadHead2(bool){
		var enabledEdit = true;
		if(state == 2){
			enabledEdit = false;
		}
    	deptgrid2 = $("#rightgrid").ligerGrid({
			columns: [
				{display: '合作对象', name: 'coop_obj_name', align: 'left',width:'15%',textField : 'coop_obj_name',
					editor : {
						type : 'select',
						valueField : 'id',
						textField : 'coop_obj_name',
						cancelable:false,
						data:[
							{ id: 1, coop_obj_name: '客户' },
							{ id: 2, coop_obj_name: '供应商'}
						],
						onChanged :function(data){
							data.record.obj_name = "";
							deptgrid2.updateCell("obj_name","",data.rowindex);
							if(data.selected){
								data.record.coop_obj = data.selected.id;
								data.record.coop_obj_name = data.selected.coop_obj_name;
							}else{
								data.record.coop_obj = '';
								data.record.coop_obj_name = '';
							}	
						}
					}
				},
				{display: '外单位', name: 'obj_name', align: 'left',width:'30%',textField : 'text',
					editor :  {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						keySupport : true,
						autocomplete : true,
						selectBoxWidth:300,
						cancelable:false,
						onChanged:function(this_data){
							if(this_data.selected){
								if(this_data.record.coop_obj == 1){
									this_data.record.cus_id = this_data.selected.id.split(".")[0];
									this_data.record.cus_no = this_data.selected.id.split(".")[1];
								}
								if(this_data.record.coop_obj == 2){
									this_data.record.sup_id = this_data.selected.id.split(".")[0];
									this_data.record.sup_no = this_data.selected.id.split(".")[1];
								}
								this_data.record.obj_name = this_data.selected.text;
							}else{
								this_data.record.cus_id = '';
								this_data.record.cus_no = '';
								this_data.record.sup_id = '';
								this_data.record.sup_no = '';
							}
					}	
				}	
				},
				{display: '分摊比例(%)', name: 'ft_bl', align: 'right' ,width:'15%'},
				{display: '分摊金额', name: 'ft_my', align: 'right' ,width:'15%',editor:{type : 'float'}},
				{display: '备注', name: 'note', align: 'left' ,width:'24.99%', editor:{type : 'text'}}
			],
			dataAction: 'server',usePager:false, url:'queryAccCoopObj.do?isCheck=false', 
			width: '49.6%', height: '100%', rownumbers:true,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true,enabledEdit: enabledEdit,checkbox:true,
			onLoaded : function(){
				if(state == 1 && bool){
					deptgrid2.addRow();
				}
			},
			onBeforeEdit :function(rowData){
				if(rowData.column.name == 'obj_name'){
					if(rowData.record.coop_obj == 1){
						rowData.column.editor.url = '../../../../sys/queryCusDict.do?isCheck=false';
					}
					if(rowData.record.coop_obj == 2){
						rowData.column.editor.url = '../../../../sys/querySupDictDict.do?isCheck=false';
					}
				}
			}
		});
    	deptgridManager2 = $("#rightgrid").ligerGetGridManager();
    }
	
	function audit(){
		if(!liger.get("ser_num").getValue()){
			$.ligerDialog.error("未保存单据不能审核！");
			return;
		}
		var para={
			ser_num : $("#ser_num").val(),
			type : 'audit'
		};
		ajaxJsonObjectByUrl("auditAccCoopCost.do?",para,function(responseData){
            if(responseData.state=="true"){
            	state = 2;
            	loadToolBar();
            	parentFrameUse().query();
            	loadHead(false);
            	if(liger.get("coop_type").getValue() == 2){
            		loadHead2(false);
            	}
            }
        });
	}
	function unaudit(){
		if(!liger.get("ser_num").getValue()){
			$.ligerDialog.error("未保存单据不能取消审核！");
			return;
		}
		var para={
			ser_num : $("#ser_num").val(),
			type : 'unaudit'
		};
		ajaxJsonObjectByUrl("auditAccCoopCost.do?",para,function(responseData){
            if(responseData.state=="true"){
            	state = 1;
            	loadToolBar();
            	parentFrameUse().query();
            	loadHead(false);
            	if(liger.get("coop_type").getValue() == 2){
            		loadHead2(false);
            	}
            }
        });
	}
	
	function remove(){
		if(state == 2){
			$.ligerDialog.error("已审核数据不能删除！");
			return;
		}
		var data = deptgridManager.getSelectedRows();
		var data2 =[];
		if(liger.get("coop_type").getValue() == 2){
			 data2 = deptgridManager2.getSelectedRows();
		}
		if(data.length > 0 || data2.length > 0){
			$.ligerDialog.confirm('是否删除数据?', function (yes){
				if(yes){
					deptgridManager.deleteSelectedRow();
					if(liger.get("coop_type").getValue() == 2){
						deptgridManager2.deleteSelectedRow();
					}
				}
			})
		}
	}
	
	function collect(){
		if(state == 2){
			$.ligerDialog.error("已审核数据不能计算！");
			return;
		}
		if(!liger.get("sm_my").getValue()){
			$.ligerDialog.error("总费用不能为空！");
			return;
		}
		
		var param = {
			sm_my : liger.get("sm_my").getValue(),
			ser_num : liger.get("ser_num").getValue(),
			proj_code : liger.get("proj_code").getValue(),
		}
		var depts = liger.get("leftgrid").rows;
		var bool = false;
		$(depts).each(function(){
			if(this.dept_id && (this.__status == 'add' || this.ft_my)){
				bool = true;
			}	
		})
		var depts2 = [];
		if(liger.get("coop_type").getValue() == 2){
			 depts2 = liger.get("rightgrid").rows;
			 $(depts2).each(function(){
				if(this.dept_id && (this.__status == 'add' || this.ft_my)){
					bool = true;
				}	
			})
		}
		if(bool){
			$.ligerDialog.confirm('已存在数据是否需要重新计算?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("collectAccCoopCost.do",param,function(responseData){
						coll(responseData);
				    });
				}
			})
		}else{
			ajaxJsonObjectByUrl("collectAccCoopCost.do",param,function(responseData){
				coll(responseData);
		    });
		}
	}
	
	function coll(responseData){
		var left = responseData.left;
		if(left.length > 0 ){
        	var depts = liger.get("leftgrid").rows;
        	$(depts).each(function(){
        		deptgrid.deleteRow(this.__index);
        	})
        	$(left).each(function(){
        		deptgrid.addRow(this);
        	})
        	deptgrid.addRow();
		}
		
		if(liger.get("coop_type").getValue() == 2){
			var right = responseData.right;
			if(right.length > 0 ){
				var depts = liger.get("rightgrid").rows;
	        	$(depts).each(function(){
	        		deptgrid2.deleteRow(this.__index);
	        	})
        		$(right).each(function(){
	        		deptgrid2.addRow(this);
	        	})
	        	deptgrid2.addRow();
			}
		}
	}
	
	function printDate() {
		if (deptgrid.getData().length == 0) {
			$.ligerDialog.error("请先查询数据！");
			return;
		}

		var para={
				template_code:'COOP01',
				class_name:"com.chd.hrp.acc.serviceImpl.autovouch.acccoodeptcost.AccCoopCostServiceImpl",
				method_name:"queryAccCoopCostDetailPrint",
				isSetPrint:false,//是否套打，默认非套打
				isPreview:true,//是否预览，默认直接打印
				print_title: "合作费用单据",
				ser_num : liger.get("ser_num").getValue()
		}; 
		officeFormPrint(para);
	}
	
	function loadTopBar(){
		$("#topbar").ligerToolBar({ 
			items: [
                /*  { text: '添加',  click: add , icon:'add'},
                 { line:true }, */
                 { text: '删除',  click: remove  , icon:'delete',hide: deleteshow},
                 { line:true ,hide: deleteshow},
                 { text: '计算',  click: collect  , icon:'collect',hide: collectshow},
                 { line:true ,hide: collectshow},
                 { text: '保存',  click: save  , icon:'save',hide:saveshow},
                 { line:true ,hide:saveshow},
                 { text: '审核',  click: audit  , icon:'ok',hide:auditshow},
                 { text: '取消审核',  click: unaudit  , icon:'candle',hide:auditshow},
                 { line:true ,hide:auditshow},
                 { text: '打印',  click: printDate  , icon:'print'},
                 { line:true },
                 { text: '打印模版',  click: printMode  , icon:'print'},
                 { line:true },
                 { text: '关闭',  click: cancle , icon:'close'}
                 
			]
		});
	}
	
	function printMode(){
		officeFormTemplate({template_code : 'COOP01'});
	}
	
	function loadToolBar(){
		if("update" == page_type){
           $("#proj_code").ligerComboBox({disabled:true,cancelable : false});
		}
        if(state == 1){
           $('div.l-toolbar-item').eq(3).show();
           $('div.l-toolbar-item').eq(4).hide();
           $("#create_date").ligerComboBox({disabled:false,cancelable : true});
           $("#sm_my").ligerComboBox({disabled:false,cancelable : true});
           $("#note").ligerComboBox({disabled:false,cancelable : true});
           $("#coop_type").ligerComboBox({disabled:false,cancelable : true});
        }
        if(state == 2){
           $('div.l-toolbar-item').eq(3).hide();
           $('div.l-toolbar-item').eq(4).show();
           $("#create_date").ligerComboBox({disabled:true,cancelable : false});
           $("#sm_my").ligerComboBox({disabled:true,cancelable : false});
           $("#note").ligerComboBox({disabled:true,cancelable : false});
           $("#coop_type").ligerComboBox({disabled:true,cancelable : false});
        }
        if("vouch" == call){
        	$("#create_date").ligerComboBox({disabled:true,cancelable : false});
            $("#sm_my").ligerComboBox({disabled:true,cancelable : false});
            $("#note").ligerComboBox({disabled:true,cancelable : false});
            $("#coop_type").ligerComboBox({disabled:true,cancelable : false});
		}
	}
	
	function loadDict() {
		autocompleteObj({
			  id: '#proj_code',
			  urlStr: "queryAccCoopProjDict.do?isCheck=false",
			  valueField: 'id',
			  textField: 'text',
			  autocomplete: true,//可输入
			  initWidth: '260',
			  autocompletelocal: true,//本地检索
			  initvalue: '${proj_code}',
			  selectEvent : function(value){
				  if(liger.get("proj_code").selected){
					  liger.get("coop_type").selectValue(liger.get("proj_code").selected.coop_type);
					  liger.get("note").setValue(liger.get("proj_code").selected.note);
				  }
				query();
			  },
			  pageSize: 99999//下拉框默认显示条数
		});
		
		$("#ser_num").ligerTextBox({width : 180,disabled:true,cancelable : false});
		$("#sm_my").ligerTextBox({width : 180});
		$("#create_date").ligerTextBox({width : 180});
		$("#note").ligerTextBox({width :260});
		
		$("#coop_type").ligerComboBox({ 
			data: [
				{ id: 1, text: '院内' },
				{ id: 2, text: '院外'}
			], 
			isMultiSelect: false,
			value : 1,
			width : '180',
			onSelected:function(value){
				if(value == 2){
					if($("#rightgrid")[0].style.display == 'none'){
						$("#rightgrid").show();
						if(!deptgrid2){
						 	loadHead2(true);
						 	deptgrid2.addRow();
						}
					}
				}else{
					if($("#rightgrid")[0].style.display != 'none'){
						 $("#rightgrid").toggle();
					}
				}
			}
		});
		
		var today=new Date();
	    var h=today.getFullYear();
	    var m=today.getMonth()+1;
	    var d=today.getDate();
	    if(m<10){
	    	m = "0" +m
	    }
	    if(d<10){
	    	d = "0" + d
	    }
		$("#create_date").val(h + "-" + m + "-" + d);
		
		if(page_type == 'update'){
			liger.get("ser_num").setValue("${ser_num}");
			liger.get("sm_my").setValue("${sm_my}");
			liger.get("note").setValue("${note}");
			liger.get("coop_type").selectValue("${coop_type}");
			$("#create_date").val("${create_date}");
		}
		
	}
	
	function cancle(){
		 dialog.close();
	}
	
	function save(){
		if(state == 2){
			$.ligerDialog.error("已审核数据不能进行保存！");
			return;
		}
		if(!$("#create_date").val()){
			$.ligerDialog.error("制单日期不能为空！");
			return;
		}
		if(!liger.get("proj_code").getValue()){
			$.ligerDialog.error("合作项目不能为空！");
			return;
		}
		if(!liger.get("sm_my").getValue()){
			$.ligerDialog.error("总费用不能为空！");
			return;
		}
		if(!liger.get("coop_type").getValue()){
			$.ligerDialog.error("合作类型不能为空！");
			return;
		}
		var leftRow = liger.get("leftgrid").rows;
		var rightRow ;
		if(liger.get("coop_type").getValue() == 2){
			rightRow = liger.get("rightgrid").rows;
		}
		
		var para={
				note :liger.get("note").getValue(),
				coop_type :liger.get("coop_type").getValue(),
				sm_my :liger.get("sm_my").getValue(),
				proj_code :liger.get("proj_code").getValue(),
				ser_num :liger.get("ser_num").getValue(),
				create_date :$("#create_date").val(),
				page_type : page_type,
				leftRow : JSON.stringify(leftRow),
				rightRow : JSON.stringify(rightRow),
		};
		
		ajaxJsonObjectByUrl("saveAccCoopCost.do",para,function(responseData){
            if(responseData.state=="true"){
            	parentFrameUse().query();
            	if(responseData.ser_num){
	                liger.get("ser_num").setValue(responseData.ser_num);
            	}
            	page_type = "update";
            	loadToolBar();
            }
        });
	}
	
	function num(obj){
	    obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	    obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
	    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
	    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">流水号：</td>
			<td align="left" class="l-table-edit-td"><input id="ser_num" name="ser_num" placeholder="系统自动生成"/></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="create_date" type="text" id="create_date" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"
				style="width: 100px;" />
			</td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>合作项目：</td>
			<td align="left" class="l-table-edit-td"><input id="proj_code" name="proj_code" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>总费用：</td>
			<td align="left" class="l-table-edit-td"><input id="sm_my" name="sm_my" oninput="num(this)"/></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>合作类型：</td>
			<td align="left" class="l-table-edit-td"><input id="coop_type" name="coop_type" /></td>	
			<td align="right" class="l-table-edit-td">备注：</td>
			<td align="left" class="l-table-edit-td"><input id="note" name="note" /></td>	
		</tr>
	</table>
	<div id="topbar"></div>
	<div>
		<div id="leftgrid" style="float: left; display: inline;" ></div>
		<div id="rightgrid" style="float: right; display: none;" ></div>
	</div>
</body>
</html>
