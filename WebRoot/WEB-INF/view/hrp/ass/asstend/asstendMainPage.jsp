<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
/* 	amount_money : function(value) {//金额
		return formatNumber(value,
				'${ass_05005}', 1);
	}, */
	$(function() {
		
		loadDict();//加载下拉框
		
		loadHotkeys();
		
		//加载数据
		loadHead(null); 
	
	});
	//查询
	function query(obj) {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件 
		grid.options.parms.push({ name : 'tend_code', value : $("#tend_code").val() });
		
		grid.options.parms.push({ name : 'tend_person', value : $("#tend_person").val() });
		
		grid.options.parms.push({ name : 'tend_method', value : liger.get("tend_method").getValue() });
			
		grid.options.parms.push({ name : 'start_date', value : $("#start_date").val() });
		
		grid.options.parms.push({ name : 'end_date', value : $("#end_date").val() });
		
		grid.options.parms.push({ name : 'state', value :  liger.get("state").getValue() });
		

		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function addNewRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		});
	}
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
                    {display : 'apply_id',
						name : 'apply_id',
						hide:true,
						align : 'left',
						width: '120'
					},{display : 'bid_id',
						name : 'bid_id',
						hide:true,
						align : 'left',
						width: '120'
					},
					{
						display : '招标编号',
						name : 'bid_code',
						editor:{type:'text'},
						align : 'left',width: '120',
						render : function(rowdata, rowindex,value) {
							if(rowdata){ 
								if(!value){
									return "";
								}else{
								return "<a href=javascript:openUpdate('"+rowdata.bid_id+"|"+rowdata.bid_state+"')><font><b>"+value+"</b></font></a>";
							}
							}
						}
					}, {
						display : '招标地址',
						name : 'bid_tenderaddress',
						editor:{type:'text'},
						align : 'left',width: '150',
					}, {
						display : '招标人',
						name : 'bid_tenderee',
						editor:{type:'text'},
						align : 'left',width: '80',
					}, {
						display : '申请金额',
						name : 'bid_value',
						editor:{type:'number'},
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.bid_value == null ? 0
												: rowdata.bid_value,
										'${ass_05005}',
										1);
						},
						align : 'right',width: '150'
					}, {
						display : '招标方式',
						name : 'bid_method',
						textField:'bid_method',
                    	render:function(rowdata,rowindex,value){
                       		if(value == '01'){
   			 					return "公开招标";
   			 				}else if(value == '02'){
   			 					return "邀请招标";
   			 				}
			 			},
						align : 'left',width: '120'
					}, {
						display : '公告媒介',
						name : 'bid_notice',
						editor:{type:'text'},
						align : 'left',width: '150'
					}, {
						display : '公告日期',
						name : 'bid_noticedate',
						type : 'date',
						format : 'yyyy-MM-dd',
						editor : {
							type : 'date',showSelectBox:false
						},
						align : 'left',width: '100'
					}, {
						display : '招标联系电话',
						name : 'bid_phone',
						editor:{type:'int'},
						align : 'left',width: '120',
			            
					}, {
						display : '一链网招标编码',
						name : 'bid_ylwcode',
						editor:{type:'text'},
						align : 'left',width: '90'
					},  {
						display : '状态',
						name : 'bid_state',
						render : function(rowdata, rowindex,value) {
							if(rowdata){ 
								if(value=="01"){
									return "新增";
								}else if(value=="02"){
								return "提交";
							}else if(value=="03"){
								return "审核";
							}else{
								return value;
							}
							}
						},
						align : 'center',width: '90'
					},{ display: '招标文件', 
						//name: 'file_path'
							render : function(rowdata, rowindex,value) {
								if(rowdata){
									return "<a href=javascript:openFlie("+rowdata.bid_id+")><font><b>招标文件</b></font></a>";
								}
							},
						width: '15%'
		            	  
		              }],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAsstendMain.do?isCheck=false',
					width : '100%',
					height : '100%',
					alternatingRow : true,
					isScroll : true,
					checkbox : true,
					rownumbers : true,
					delayLoad :true,
					enabledEdit : true,
					isAddRow:false,
					selectRowButtonOnly : true,
					onBeforeEdit :f_BeforeEdit,
					toolbar : {
						items : [ {
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						},{
							text : '采购申请单选择',
							id : 'checkApply',
							click : checkApply,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '新增',
							id : 'add',
							click : itemclick,
							icon : 'add'
						}, {
							line : true
						},  {
							text : '保存',
							id : 'save',
							click : save,
							icon : 'save'
						},{
							line : true
						},{
							text : '删除',
							id : 'delete',
							click : itemclick,
							icon : 'delete'
						},{
							line : true
						}, {
							text : '招标文件上传',
							id : 'uplode',
							click :uplode ,
							icon : 'uplode'
						}
						]
					}
				});
		
		gridManager = $("#maingrid").ligerGetGridManager();

	}
	
function f_BeforeEdit(e){
	if((e.record.bid_state!="01")&&(e.record.bid_state)){
		return false;
	}else{
		return true;
	}
}
///选择采购申请单
function checkApply(){	
	//var len=parseInt(gridManager.getData().length)-1;
	parent.$.ligerDialog.open({
		title: '购置申请单',
		height: $(window).height(),
		width: $(window).width(),
		url: 'hrp/ass/asstend/assTendApplyPage.do?isCheck=false&',
		buttons:[{text: '确定',onclick:function getInfo(item, dialog){
			//点击确定后，新增一行，并且回写金额和applID
			var result = dialog.frame.save(); 		
			var mount=result.split('@')[1];
			grid.addRow({bid_value:mount,
				apply_id:result.split('@')[0]
			});
			//grid.updateCell('bid_value',mount,len);
			//grid.updateCell('apply_id',result.split('@')[0],len);
		}},{text:'取消',onclick: function (item, dialog) {dialog.close(); }}],
		modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
		parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

	});

}
 function uplode(){
	 var data=grid.getSelectedRows();
	 var bid_id="";
	 if (data.length != 1) {
		$.ligerDialog.error('请选择一行');
		} else {
 $(data).each(
	function(){
  	    bid_id=this.bid_id;		 
		if(!bid_id){
			$.ligerDialog.error('请选保存招标单');	
		}else{
			 parent.$.ligerDialog.open({url:'hrp/ass/asstend/upLodePage.do?isCheck=false&bid_id='+bid_id,data:{},height: 500,width: 900,
     			 title:'上传',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,parentframename:window.name });
			
		}
				 
			 });
		}
 };
 
 function openFlie(bid_id){
		parent.$.ligerDialog.open({
			title: '招标文件',
			height: 600,
			width: 1000,
			url: 'hrp/ass/asstend/assTendFilePage.do?isCheck=false&bid_id='+bid_id,
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

		}); 
	 
 };
 function save(){
	 grid.endEdit();
	 var data=gridManager.getChanges();
	 if(validateGrid()){
		ajaxJsonObjectByUrl("savetendInfomation.do?isCheck=false",{"ParamVoDetail":JSON.stringify(data),"user_id":'${sessionScope.user_id }'}, function(responseData) {
			if (responseData.state == "true") {
				query();
			}
		});
	 }
 };
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				parent.$.ligerDialog.open({
					title: '设备招标单',
					height: $(window).height(),
					width: $(window).width(),
					url: 'hrp/ass/asstend/assTendAddPage.do?isCheck=false',
					modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

				});
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					
					var ParamVo = [];
					$(data).each(
							function() {
								if(isnull(this.bid_id)){
									gridManager.deleteSelectedRow();	
									
								}else{
								if(this.bid_state!="01"){
									$.ligerDialog.error('非新增单据不可删除');	
									return;
								}
								ParamVo.push(this.group_id + "@" + this.hos_id
										+ "@" + this.copy_code + "@"
										+ this.bid_id+"@"+this.bid_code);
								}
							});
					if (ParamVo == "") {
						return;
				} 	
					$.ligerDialog.confirm('确定删除?', function(yes) {
						
						if (yes) {
							ajaxJsonObjectByUrl("deleteTend.do", {
							Param : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			}
		}

	}
	function openUpdate(obj) {

	  var bid_id=obj.split("|")[0];
	  var bid_state=obj.split("|")[1];
		parent.$.ligerDialog.open({
			title: '设备招标单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asstend/assTendDetailPage.do?isCheck=false&bid_id=' + bid_id+"&bid_state="+bid_state,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

		});
		
	}
	function loadDict() {
		//字典下拉框
 
		var param = {
            	query_key:''
        }; 
		$("#start_date").ligerTextBox();
		$("#end_date").ligerTextBox();
		
		//autocomplete("#tend_method","queryTendMethod.do?isCheck=false","id","text",true,true,param,true);
		
		//$("#apply_month1").ligerTextBox({width : 120});
        $('#tend_method').ligerComboBox({
				data:[{id:'01',text:'公开招标'},{id:'02',text:'邀请招标'}],
				valueField: 'text',
	            textField: 'text',
				cancelable:true,
				width : 160
		})
		
		 $('#state').ligerComboBox({
				data:[{id:'01',text:'新建'},{id:'02',text:'提交'},{id:'03',text:'审核'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 160
		});
        
        

	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', itemclick, [ {
			id : 'add'
		} ]);

		hotkeys('D', itemclick, [ {
			id : 'delete'
		} ]);

	}
	
	function validateGrid() {

		var data = gridManager.getChanges();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data,
				function(i, v) {
			if (!isnull(v.bid_code)) {
					var key = v.bid_code;
					var value = "第" + (i + 1) + "行";
					
					if (isnull(v.bid_code)) {
						msg += "[招标编码]、";
					}
					
					if (isnull(v.bid_method)) {
						msg += "[招标方式]、";
					}
					if (isnull(v.bid_value)) {
						msg += "[申请金额]、";
					}
					 if (isnull(v.bid_tenderee)) {
						msg += "[招标人]、";
					} 
					 
					 if (isnull(v.bid_tenderaddress)) {
							msg += "[招标地址]、";
						} 
					/* if (isnull(v.bid_phone)) {
							msg += "[招标联系电话]、";
						} */
					 
					if (msg != "") {
						msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
					}
					if (isnull(targetMap.get(key))) {
						targetMap.put(key, value);
					} else {
						msg = targetMap.get(key) + "与" + value + "重复！\n\r",
						value;
						msgMap.put(
								targetMap.get(key) + "与" + value + "重复！\n\r",
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
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">招标编码</td>
			<td align="left" class="l-table-edit-td" ><input name="tend_code" type="text" id="tend_code" /></td>
			<td align="center" class="l-table-edit-td">招标人</td>
			
			<td align="left" class="l-table-edit-td"><input name="tend_person" type="text" id="tend_person" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;" width="60">招标方式</td>
			<td align="left" class="l-table-edit-td"><input name="tend_method" type="text" id="tend_method"   /></td>
			<td align="left" ></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期</td>
			<td align="left" class="l-table-edit-td"><input name="start_date" type="text" id="start_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			<td align="center" class="l-table-edit-td">&nbsp至</td>
			<td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">状&nbsp;&nbsp;态&nbsp;&nbsp;</td>
			<td align="left" class="l-table-edit-td"><input name="state" type="text" id="state"  /></td>
			<td align="left"></td>
		</tr>
		
	</table>
	<div id="maingrid"></div>
</body>
</html>
