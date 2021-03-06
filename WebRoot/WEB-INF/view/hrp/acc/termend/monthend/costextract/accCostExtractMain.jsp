<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var subjGrid;
	var gridManager = null;
	var detailManager = null;
	var year_month = '${acc_year_month}';

	var userUpdateStr;

	$(function() {
		$("#layout1").ligerLayout({ leftWidth: 265, centerBottomHeight: $(window).height()-230});
		$("#navtab1").ligerTab({contextmenu: false});
		loadForm();
		loadDict();
		loadHead(null); //加载数据
		loadSubjGrid(null);
		query();
	});
	
	//查询模板信息
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acc_year',
			value : liger.get("acc_year_month").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'acc_month',
			value : liger.get("acc_year_month").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'template_type_code',
			value : "Z004"
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//加载模板主表信息
	function queryMain(rowdata) {
		rowdata = JSON.parse(rowdata);
		$("#template_id").val(rowdata.template_id);
		$("#template_name").val(rowdata.template_name);
		if(rowdata.vouch_type_code){
			liger.get("vouch_type_code").setValue(rowdata.vouch_type_code);
			liger.get("vouch_type_code").setText(rowdata.vouch_type_name);
		}
		$("#summary").val(rowdata.summary);
		$("#rate").val(rowdata.rate);
		if(rowdata.debit_subj_code1){
			liger.get("debit_subj_code1").setValue(rowdata.debit_subj_code1);
			liger.get("debit_subj_code1").setText(rowdata.debit_subj_code1+" "+rowdata.debit_subj_name1);
		}else{
			liger.get("debit_subj_code1").setValue("");
			liger.get("debit_subj_code1").setText("");
		}
		if(rowdata.debit_subj_code2){
			liger.get("debit_subj_code2").setValue(rowdata.debit_subj_code2);
			liger.get("debit_subj_code2").setText(rowdata.debit_subj_code2+" "+rowdata.debit_subj_name2);
		}else{
			liger.get("debit_subj_code2").setValue("");
			liger.get("debit_subj_code2").setText("");
		}
		if(rowdata.credit_subj_code1){
			liger.get("credit_subj_code1").setValue(rowdata.credit_subj_code1);
			liger.get("credit_subj_code1").setText(rowdata.credit_subj_code1+" "+rowdata.credit_subj_name1);
		}else{
			liger.get("credit_subj_code1").setValue("");
			liger.get("credit_subj_code1").setText("");
		}

		queryDetail(rowdata.template_id);
	}
	
	//查询模板明细信息
	function queryDetail(template_id) {
		subjGrid.options.parms = [];
		subjGrid.options.newPage = 1;
		//根据表字段进行添加查询条件
		subjGrid.options.parms.push({
			name : 'template_id',
			value : template_id
		});
		//加载查询条件
		subjGrid.loadData(subjGrid.where);
	}
	
	function loadForm(){
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element)
	         {
	             if (element.hasClass("l-textarea"))
	             {
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }
	             else if (element.hasClass("l-text-field"))
	             {
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else
	             {
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
		//$("form").ligerForm();
	}  
	//模板Grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '凭证模板', name : 'template_name', align : 'left', width: "140",  
				render : function(rowdata, rowindex,value) {
					return '<a href=javascript:queryMain(\''+ JSON.stringify(rowdata) +'\')>'+rowdata.template_name+'</a>';
				}
			}, {
				display : '凭证类型', name : 'vouch_type_name', align : 'left', width: "60" 
			}],
			dataAction : 'server', dataType : 'server', width : '100%', height : $(window).height()-1,
			usePager : false, url : 'queryAccCostExtract.do', checkbox : true, isSingleCheck: true,  
			rownumbers : true, delayLoad: true, selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '模板添加', id : 'add', click : itemclick, icon : 'add'
				}, {
					line : true
				}, {
					text : '模板删除', id : 'delete', click : itemclick, icon : 'delete'
				}]
			},
			onDblClickRow : function(rowdata, rowindex, value) {
				queryMain(rowdata);
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				$("#template_id").val("");
				$("#template_name").val("");
				$("#summary").val("");
				$("#rate").val("");
				liger.get("debit_subj_code1").clear();
				liger.get("debit_subj_code2").clear();
				liger.get("credit_subj_code1").clear();
				subjGrid.deleteAllRows();
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.warn('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						ParamVo.push(
						//表的主键
						this.template_id)
					});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccCostExtract.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
									$("#template_id").val("");
								}
							});
						}
					});
				}
				return;
			}
		}
	}	
	
	//科目Grid
	function loadSubjGrid() {
		//subjGrid = $("#subjgrid").css({"margin-left" : 40}).ligerGrid({
		subjGrid = $("#subjgrid").ligerGrid({
			columns : [ {
				display : '科目编码', name : 'subj_code', align : 'left', width: "160" 
			}, {
				display : '科目名称', name : 'subj_name_all', align : 'left'
			}],
			dataAction : 'server', dataType : 'server', width : '100%', height : '100%', 
			usePager : false, url : '../../queryAccTermendTemplateDetail.do?isCheck=false',
			checkbox : true, rownumbers : false, delayLoad: true, selectRowButtonOnly : false,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '科目设置', id : 'add', click : setIncomSubj, icon : 'add'
				}, {
					line : true
				}, {
					text : '科目删除', id : 'delete', click : removeSubj, icon : 'delete'
				}]
			},
		});

		detailManager = $("#subjgrid").ligerGetGridManager();
	}
	
	function removeSubj(){
		subjGrid.deleteSelectedRow();
	}

	//字典下拉框
	function loadDict() {
		$("#acc_year_month").ligerComboBox({
	      	url: '../../../queryYearMonth.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 90,
	      	autocomplete: true,
	      	width: 90, 
	      	onChangeValue: function(value){
	      		if(grid){
		      		query();
	      		}
	      	}
		});
		liger.get("acc_year_month").setValue(year_month.substring(0,4)+"."+year_month.substring(4,6).toString());
		liger.get("acc_year_month").setText(year_month.substring(0,4)+"."+year_month.substring(4,6).toString());
		
		var paras ={
			code_like : '5001',
			is_last : "1"
		};
		autocomplete("#debit_subj_code1", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);	
		paras ={
			code_like : '5101',
			is_last : "1"
		};
		autocomplete("#debit_subj_code2", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);	
		paras ={
			subj_type_code : '02',
		 	subj_type_code1 : '03',
		 	is_last : "1"
		};
		autocomplete("#credit_subj_code1", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);	
		autocomplete("#vouch_type_code", "../../../queryVouchType.do?isCheck=false", "id", "text", true, true, "", true);
		
		$("#template_name").ligerTextBox({width:180,disabled: false }); 
		$("#vouch_type_code").ligerTextBox({width:85,disabled: false });
		$("#summary").ligerTextBox({width:180,disabled: false });  
		$("#rate").ligerTextBox({width:85,number:true,disabled: false });
		
		$("#but_add").ligerButton({click: createVouch, width:90});
		$("#but_vouch").ligerButton({click: showVhouchList, width:90});
		$("#but_save").ligerButton({click: saveTemplate, width:90});
	}
	
	//保存模板
	function saveTemplate(){
		if($("form").valid()){
			if(getSubjData() == "[]"){
				$.ligerDialog.warn('请选择科目');
				return false;
			}
			var formPara ={
				template_id : $("#template_id").val(),
				template_name : $("#template_name").val(),
				template_type_code : "Z004",
				template_type_name : "支出费用提取",
				vouch_type_code : liger.get("vouch_type_code").getValue(),
				summary : $("#summary").val(),
				rate : $("#rate").val(),
				debit_subj_code1 : liger.get("debit_subj_code1").getValue(),
				debit_subj_code2 : liger.get("debit_subj_code2").getValue(),
				credit_subj_code1 : liger.get("credit_subj_code1").getValue(),
				detailData : getSubjData()
			};
			ajaxJsonObjectByUrl("saveAccCostExtract.do",formPara,function (responseData){
        		if(responseData.state=="true"){
        			query();
        		}
        	});
		}
	}
	
	//设置科目
	var subjList;
	function setIncomSubj(){
		parent.$.ligerDialog.open({ 
			title: '收入科目设置', 
			width: $(window).width() - 20, 
			height: $(window).height() - 50, 
			url: 'hrp/acc/termend/accTermendTemplateSubjPage.do?isCheck=false&subj_type_code=05', 
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			buttons: [
				{ text: '确定', onclick: writeSubj, cls:'l-dialog-btn-highlight' },
				{ text: '取消', onclick: closeDialog }
			]
		}); 
	}
	//写入选择的科目
	function writeSubj(item, dialog){ 
		var rows = dialog.frame.getSelectRows();
        if (!rows){
            alert('请选择行!');
            return;
        } 
		//清除已有数据
		subjGrid.deleteAllRows();
		//写入数据
        subjGrid.addRows(rows);
        dialog.close();
    }	
	//关闭选择科目窗口
    function closeDialog(item, dialog){ 
        dialog.close();
    }
	//获取科目数据
    function getSubjData(){
        var manager = $("#subjgrid").ligerGetGridManager();
        var data = manager.getData();
        return JSON.stringify(data);
    }

	//生成凭证
	function createVouch(){
		var rows = gridManager.getCheckedRows();
		if (rows.length == 0) {
			$.ligerDialog.warn('请选择行');
            return;
        } 
		//如选多个模板则循环生成凭证
		$.ligerDialog.confirm('确定生成凭证?', function (yes){
	    	if(yes){
	    		var para;
        		for (var i = 0; i < rows.length; i++){
		        	//template_ids = rows[i].template_id + ",";
		        	para={
		    			acc_year : liger.get("acc_year_month").getValue().split(".")[0],
		    			acc_month : liger.get("acc_year_month").getValue().split(".")[1],
		        		vouch_type_code : rows[i].vouch_type_code,
		        		template_id : rows[i].template_id
		        	}
			
	        		var loadIndex = layer.load(1);
	        		ajaxJsonObjectBylayer("addAccCostExtractVouch.do",para,function(responseData){	
	        			//console.log(responseData);
	        			layer.close(loadIndex);
	        			var paraVouch={
	        				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
	        				title:'会计凭证',
	        				width:0,
	        				height:0,
	        				isShowMin:true,
	        				isModal:true,
	        				data:{auto_id: responseData.vouch_id, busi_log_table:'ACC_BUSI_LOG_ZZ', busi_type_code:'Z004',busi_no:para.template_id}
	        			};
	        			parent.openDialog(paraVouch);
	          		},layer,loadIndex);
	        	}
	         
        	} 
		});
	}
	//凭证维护
	function showVhouchList(){
		parent.$.ligerDialog.open({ 
			title: '凭证维护', 
			width: $(window).width(), 
			height: $(window).height(), 
			url: 'hrp/acc/termend/accTermendTemplateVouchPage.do?isCheck=false&template_type_code=Z004&acc_year='+liger.get("acc_year_month").getValue().split(".")[0]+'&acc_month='+liger.get("acc_year_month").getValue().split(".")[1],
			model:true, showMax: false, showToggle: false, showMin: false, isResize: true
		}); 
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<div id="layout1" style="width:100%;margin:0; padding:0;">
		<div id="maingrid" position="left" title="模板列表"></div>
		<div position="center" title="模板信息">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">会计期间：</td>
					<td align="left" class="l-table-edit-td"><input name="acc_year_month"
						type="text" id="acc_year_month" ltype="text"
						validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						<input type="button" id="but_add" accessKey="T"  value="费用提取(T)"/>
						&nbsp;&nbsp;
						<input type="button" id="but_vouch" accessKey="Z"  value="凭证维护(Z)"/>
						&nbsp;&nbsp;
						<input type="button" id="but_save" accessKey="S"  value="模板保存(S)"/>
					</td>
				</tr>
			</table>
			<hr style="border:1px solid #A3C0E8; "/>
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit">
					<tr>
						<td align="right" class="l-table-edit-td" > 
							<span style="color:red">*</span>模板名称：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="template_id" type="hidden" id="template_id" ltype="text" validate="{required:false}" />
							<input name="template_name" type="text" id="template_name" ltype="text" required="true" validate="{required:true,maxlength:40}" />
						</td>
						<td align="right" class="l-table-edit-td" style="padding-left: 15px;">
							<span style="color:red">*</span>凭证类型：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text" required="true" validate="{required:true}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" >
							<span style="color:red">*</span>摘要：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="summary" type="text" id="summary" ltype="text" required="true" validate="{required:true,maxlength:80}" />
						</td>
						<td align="right" class="l-table-edit-td" style="padding-left: 15px;">
							<span style="color:red">*</span>提取比例：
						</td>
						<td align="left" class="l-table-edit-td">
							<table>
								<tr>
									<td>
										<input name="rate" type="text" id="rate" ltype="text" required="true" validate="{required:true,maxlength:20}" />
									</td>
									<td align="left">%</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" >
							<span style="color:red">*</span>借：提取支出费用(医疗)：
						</td>
						<td align="left" colspan="3" class="l-table-edit-td" >
							<input name="debit_subj_code1" type="text" id="debit_subj_code1" ltype="text" required="true" validate="{required:true}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" >
							<span style="color:red">*</span>借：提取支出费用(管理)：
						</td>
						<td align="left" colspan="3" class="l-table-edit-td" >
							<input name="debit_subj_code2" type="text" id="debit_subj_code2" ltype="text" required="true" validate="{required:true}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" >
							<span style="color:red">*</span>贷：　　　　&nbsp;&nbsp;支出费用：<!-- 注含全角空格 -->
						</td>
						<td align="left" colspan="3" class="l-table-edit-td" >
							<input name="credit_subj_code1" type="text" id="credit_subj_code1" ltype="text" required="true" validate="{required:true}" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div position="centerbottom" id="navtab1" style="border:1px solid #A3C0E8; ">
			<div tabid="subjDiv" title="支出科目" lselected="true" style="width: 100%">
				<div id="subjgrid" style="margin-top: 5px"></div>
			</div>
		</div>
	</div>
</body>
</html>
