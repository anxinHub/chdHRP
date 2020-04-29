<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hotkeys/jquery.hotkeys.js" type="text/javascript"></script>
<script src="<%=path%>/lib/map.js"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">

var targetMap = new HashMap();//存放弹窗窗口数据

var centergrid;

var gridCenterManager = null;

var bottomgrid;

var gridBottomManager = null;

var clicked = 0;

var dialog = frameElement.dialog;

var kpi_arr = [];//用于存储保存时的数据

var kpiNum = 1;

function f_onBeforeEdit(e) {clicked = 0;}

	$(function() {
		
		$("#goalLayout").ligerLayout({
			topHeight:93,
			bottomHeight:150
		}); 

		loadDict();//加载下拉框
		loadCenterHead(null); //加载数据
		loadbottomHead(null);
		loadHotkeys();
		
	});
	
	//加载子目标grid
	function loadCenterHead() {
		centergrid = $("#centergrid").ligerGrid(
			{columns : [
				{ display: '子目标编码', name: 'child_goal_code', align: 'left',width:'20%',editor: { type: 'text' }},
				{ display: '子目标名称', name: 'child_goal_name', align: 'left',width:'40%',editor: { type: 'text' }},
				{ display: '设立目的', name: 'child_goal_note', align: 'left',width:'40%',editor: { type: 'text' }}
			],
			usePager : false,width : '100%',height : '250',enabledEdit : true,fixedCellHeight:true,isAddRow:false,
			isScroll : true,selectRowButtonOnly:true,isSingleCheck:true,heightDiff: 10,toolbar: 
				{ items: [
					{ text: '添加行（<u>C</u>）', id:'add', click: addCenterRow, icon:'add' },{ line:true },
					{ text: '删除（<u>D</u>）', id:'delete', click: deleteCenterRow,icon:'delete' },{ line:true }
				]},
			onCheckRow  : function (checked,data,rowid,rowdata){
				if(checked){
					bottomgrid.reload();
					var targetData = targetMap.get(data.goal_detail_id);
					if(targetData != null){
						bottomgrid.addRows(targetData);
					}
				}else{
					bottomgrid.reload();
				}
			},
			checkbox: true}
		);

		gridCenterManager = $("#centergrid").ligerGetGridManager();

		$(document).bind('keydown.centergrid', function(event) {
			if (event.keyCode == 13) {// enter,也可以改成9:tab
				centergrid.endEditToPrmNext();
			}
		});
		
		$("#centergrid").on('focus', 'input', function() {
			if (clicked != 0)
				return;
			var curdom = $(this).parent();
			if (curdom.hasClass('l-text-combobox') && !$(this).attr('readonly')) {
				var clkbutton = curdom.find('.l-trigger-icon');
				clicked = 2;
				clkbutton[0].click();
			}
		});

	}
	
	//加载指标grid
	function loadbottomHead() {
		bottomgrid = $("#bottomgrid").ligerGrid(
			{columns : [
				{ display: '指标代码', name: 'kpi_code', align: 'left',width:'15%'},
				{ display: '指标名称', name: 'kpi_name', align: 'left',width:'35%'},
				{ display: '目标值', name: 'goal_value', align: 'left',width:'15%',editor: { type: 'text' }},
				{ display: '行动要点', name: 'kpi_act_note', align: 'left',width:'35%',editor: { type: 'text' }}
			],
			usePager : false,width : '100%',height : '200',enabledEdit : true,isAddRow:false,
			isScroll : true,toolbar: { 
				items: [
					{ text: '选择（<u>A</u>）', id:'add', click: bottomCheckClick, icon:'outbox' },{ line:true },
					{ text: '删除（<u>B</u>）', id:'delete', click: deleteBottomRow,icon:'delete' },{ line:true }
				]},
			checkbox: true}
		);

		gridBottomManager = $("#bottomgrid").ligerGetGridManager();
	}
	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('C',addCenterRow);
		hotkeys('D',deleteCenterRow);
		hotkeys('B',deleteBottomRow);
		hotkeys('A',bottomCheckClick);
	}
	
	//删除子目标行
	function deleteCenterRow(){ 
		var row = centergrid.getSelectedRow();
		
        if (!row) { $.ligerDialog.warn('请选择要删除的子目标'); return;} 
			kpi_arr = kpi_arr.filter(function(element, index, array){
			return (element.goal_detail_id != row.goal_detail_id);
		}) 
        
        targetMap.remove(row.goal_detail_id);
		centergrid.deleteSelectedRow();
		bottomgrid.reload();
    }
	
	//添加行初始化goal_detail_id字段值
	function addCenterRow(){ 
		
		kpiNum++;
		centergrid.addRow();
		var length = centergrid.rows.length;
		centergrid.rows[length - 1].goal_detail_id = kpiNum;
		
    }
	
	//删除指标行
	function deleteBottomRow(){ 
		var row = centergrid.getSelectedRow();//获取子目标的行数据
		var deleteArr = bottomgrid.getSelectedRows();//获取选中的指标
		var bottomaArr = targetMap.get(row.goal_detail_id);//获取指定行的map
		
		for(var i = 0; i < deleteArr.length ; i++){
			var index = deleteArr[i]._index;
			bottomaArr.splice(index,1);
		}
		
		bottomgrid.deleteSelectedRow2();//删除选择的行
		loadTargetMap(row.goal_detail_id,bottomaArr);
    }
	
	//选择页面跳转
	function bottomCheckClick(){ 
		
		var row = centergrid.getSelectedRow();
        if (!row) { 
        	$.ligerDialog.warn('请选择子目标');
        	return; 
        }

        var child_goal_code = row.child_goal_code;
        
        if(typeof(child_goal_code) == "undefined"){
			$.ligerDialog.warn('请输入子目标编码');
        	return; 
        }
        
        var targetData = targetMap.get(row.goal_detail_id);

		$.ligerDialog.open({url: '../prmKpiLibPage.do?isCheck=false',top:1,height: 400,width: 810,title:'选择',modal:true,showToggle:false,showMax:true,showMin: true,isResize:true,
			data: {
                __id: row.goal_detail_id,
                targetData:targetData
            },
            buttons: [ 
				{ text: '确定', onclick: function (item, dialog) { dialog.frame.confirmPrmKpiLib(); dialog.close();},cls:'l-dialog-btn-highlight' }, 
			    { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
		
    }
	
	//关闭窗口
	function closeDialog(){ 
		dialog.close(); 
    }
	
	//键盘事件
	function loadTargetMap(goalKey,goalValue){
		targetMap.put(goalKey ,goalValue);
	}
	
	//选择页面回显数据
	function addRows(target_data,__id){
		
		var map_data = targetMap.get(__id);//获取添加到行中的数据
		
		var checkedDetail = centergrid.getSelectedRow();//获取选中的子目标
		
		if(map_data == null){
			
			 map_data = [];
			$.each(target_data, function(t_index, t_content){
				t_content['child_goal_code'] = checkedDetail.child_goal_code;
				t_content['goal_detail_id'] = checkedDetail.goal_detail_id;
				kpi_arr.push(t_content);
				map_data.push(t_content);
				loadTargetMap(__id,map_data);
				bottomgrid.addRow(t_content);
				
			});
		}else{
			$.each(target_data, function(t_index, t_content){
				t_content['child_goal_code'] = checkedDetail.child_goal_code;
				t_content['goal_detail_id'] = checkedDetail.goal_detail_id;
				var count= 0;
				$.each(map_data, function(index, content){ 
					
	    			if(t_content.kpi_code == content.kpi_code){
	    				count++;
	    				return false;
	    			}
	    		});
				
				if(count == 0){
					bottomgrid.addRow(t_content);
					kpi_arr.push(t_content);
					map_data.push(t_content)
					loadTargetMap(__id,map_data);
				}
			});
		}
	}
	
	//保存
	function save() {
		if($('#acc_year').val() == ''){
			$.ligerDialog.warn('绩效年度不能为空 ');
			return ; 
		}
		
		if($("#start_month").val() == ''){
			$.ligerDialog.warn('开始月份不能为空 ');
			return ; 
		}
		
		if($("#goal_code").val() == ''){
			$.ligerDialog.warn('目标编码不能为空 ');
			return ; 
		}
		
		if($("#goal_name").val() == ''){
			$.ligerDialog.warn('目标名称不能为空 ');
			return ; 
		}
		//验证子目标区域
		var goal_detail_data = gridCenterManager.getData();
		//var kpi_target_data = gridBottomManager.getData();
			
		var validate_detail_buf = new StringBuffer();
		
		var msg = '';
		
		if(JSON.stringify(goal_detail_data) != '[{}]'){
			
			var targetMap = new HashMap();
			
			$.each(goal_detail_data, function(d_index, d_content){ 
				 //过滤空行
				 if(typeof(d_content.child_goal_code) != "undefined" && d_content.child_goal_code != ""){
					 
				 	var key = d_content.child_goal_code;
					var value = '第'+(d_index+1)+'行'; 
					
					//判断子目标编码是否重复
				 	if (!targetMap.get(key)) {
						targetMap.put(key, value);
					}else{
						msg += value + '与' + targetMap.get(key) + '子目标编码重复\n ';
					}
					
					//判断子目标名称是否为空
					if(typeof(d_content.child_goal_name) == "undefined" || d_content.child_goal_name == ""){
		      			validate_detail_buf.append("子目标编码：["+d_content.child_goal_code+"]对应子目标名称为空 请输入\n");
	      		  	}
				 }
			});   
		}
			
		if(msg != ''){
			$.ligerDialog.warn(msg);
			return ; 
		}

		if(validate_detail_buf.toString()  != ""){
			
			$.ligerDialog.warn(validate_detail_buf.toString());
			
			return ;
		}

		var formPara = {
				
			hos_id : liger.get("hos_id").getValue(),
			acc_year : $("#acc_year").val(),
			start_month : $("#start_month").val(),
			goal_code : $("#goal_code").val(),
			goal_name : $("#goal_name").val(),
			goal_note : $("#goal_note").val(),
			
			//以上保存目标基本表
			
			goal_detail_data : JSON.stringify(goal_detail_data),//子目标数据
			kpi_target_data : JSON.stringify(kpi_arr)//KPI指标数据

		};
		
		ajaxJsonObjectByUrl("addPrmGoal.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
				$("#goal_code").val('');
				$("#goal_name").val('');
				$("#goal_note").val('');
				
				targetMap = {};
				kpi_arr = [];
				centergrid.reload();
				bottomgrid.reload();
				
				parentFrameUse().query();
			}
		});
	}
	
	//添加行
	function is_addRow(){
		setTimeout(function () { 
			//当数据为空时 默认新增一行
			var data = centergrid.getData();
			if (data.length==0)
				centergrid.addRow();
			centergrid.rows[0].goal_detail_id = kpiNum;
			}, 500);
		
	}
	
	//保存
	function savePrmGoal() {
		save();
	}
	
	function loadDict() {
		//字典下拉框
		$("#acc_year").ligerTextBox({width : 160});
		$("#start_month").ligerTextBox({width : 160});
		$("#goal_code").ligerTextBox({width : 160});
		$("#goal_name").ligerTextBox({width : 288});
		$("#goal_note").ligerTextBox({width : 740});
        
		autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",true);
    	autodate("#acc_year","yyyy");
    	autodate("#start_month","mm");

	}
</script>
<style type="text/css">
body{ padding:3px; margin:0;}
#goalLayout{  width:100%; margin:10px;  height:430px;margin:0; padding:0;}
</style>

</head>

<body  style="padding:3px" onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="goalLayout">
			<div position="top">
				<form name="form1" method="post" id="form1">
					<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
						<tr>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">单位信息：</td>
							<td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
							<td align="left"></td>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">绩效年度：</td>
							<td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
							<td align="left"></td>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">开始月份：</td>
							<td align="left" class="l-table-edit-td"><input name="start_month" type="text" id="start_month" ltype="text" validate="{required:true,maxlength:20}"    class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})"/></td>
							<td align="left"></td>
						</tr>
						<tr>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标编码：</td>
							<td align="left" class="l-table-edit-td"><input name="goal_code" type="text" id="goal_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
							<td align="left"></td>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标名称：</td>
							<td align="left" class="l-table-edit-td" colspan="4"><input name="goal_name" type="text" id="goal_name" ltype="text" validate="{required:true}" /></td>
							
						</tr>
						<tr>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标描述：</td>
							<td align="left" class="l-table-edit-td" colspan="7"><input name="goal_note" type="text" id="goal_note" ltype="text" validate="{required:true,maxlength:20}" /></td>
						</tr>
					</table>
				</form>
			</div>
			
            <div position="center">
				<div id="centergrid"></div>
            </div>
            
            <div position="bottom">
            	<div id="bottomgrid"></div>
            </div>
	</div> 

</body>
</html>
