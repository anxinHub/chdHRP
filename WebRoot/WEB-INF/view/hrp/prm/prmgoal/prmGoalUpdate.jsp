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

var loadCenterCount = 0;//判断 centerData onAfterShowData 事件只执行一次

var dialog = frameElement.dialog;

var centerData;

var kpiNum = 1;

function f_onBeforeEdit(e) {clicked = 0;}

	$(function() {
		
		$("#goalLayout").ligerLayout({
			topHeight:93,
			bottomHeight:250,
			height:'100%',
		}); 

		loadDict();//加载下拉框
		loadCenterHead(null); //加载子目标grid
		loadbottomHead(null);//加载指标grid
		
		centertoolbal();
		bottomtoolbar();
		loadHotkeys();
		
	});
	
	//加载子目标grid
	function loadCenterHead() {
		
		centerData = ${goalDetail};
		$.each(centerData.Rows,function(t_index, t_content){
			t_content.goal_detail_id = kpiNum;
			kpiNum++;
		});
		
		centergrid = $("#centergrid").ligerGrid(
			{columns : [
				{ display: '子目标编码', name: 'child_goal_code', align: 'left',width:'20%',editor: { type: 'text' }},
				{ display: '子目标名称', name: 'child_goal_name', align: 'left',width:'40%',editor: { type: 'text' }},
				{ display: '设立目的', name: 'child_goal_note', align: 'left',width:'40%',editor: { type: 'text' }}
			],
			usePager : false,width : '100%',height : '100%',enabledEdit : true,fixedCellHeight:true,isAddRow:false,
			isScroll : true,selectRowButtonOnly:true,isSingleCheck:true,heightDiff: 10,data:centerData,
			onAfterAddRow:function(rowdata){//为每一行数据增加属性goal_detail_id,增加唯一性字段
				rowdata.goal_detail_id = kpiNum;
				kpiNum++;
			},
			onAfterShowData:function(currentData){
				if(loadCenterCount == 0){//第一次加载执行取KPI指标方法
					var count = 0;
					$.each(currentData.Rows, function(t_index, t_content){
						if(t_content.goal_code != undefined && t_content.child_goal_code != undefined){
						var kpiPara={
							usePager:false,
							group_id:t_content.group_id,
							hos_id:t_content.hos_id,
							copy_code:t_content.copy_code,
							acc_year:t_content.acc_year,
							goal_code:t_content.goal_code,
							child_goal_code:t_content.child_goal_code
							};
						}else{
							count++;
						}
												
						if(count){
							return ;
						}
								
						ajaxJsonObjectByUrl("queryPrmGoalDetailKpiByGoal.do?isCheck=false",kpiPara,function (responseData){
							if(responseData.Rows !=null){
								loadTargetMap(t_content.goal_detail_id,responseData.Rows);
							}
						});
					}); 
												
					loadCenterCount++;
				}},
				onCheckRow  : function (checked,data,rowid,rowdata){
					if(checked){
						bottomgrid.reload();
						var targetData = targetMap.get(data.goal_detail_id);//点击 将KPI数据回冲到表格
						if(targetData != null){
							bottomgrid.addRows(targetData,data.goal_detail_id);
						}
					}else{
						bottomgrid.reload();//置空底部表格
					}
				},checkbox: true}
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
	
	
	//子目标工具栏
	function centertoolbal(){
		var obj = [];
		
		obj.push({ text: '添加行（<u>C</u>）', id:'add', click: addCenterRow,icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteCenterRow,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '保存（<u>S</u>）', id:'save', click: saveCenterRow,icon:'save' });
       	obj.push({ line:true });
       	
       	
       	$("#centertoolbal").ligerToolBar({ items: obj});
	}
	
	
	
	
	
	//加载指标gird
	function loadbottomHead() {
		bottomgrid = $("#bottomgrid").ligerGrid(
			{columns : [
				{ display: '指标代码', name: 'kpi_code', align: 'left',width:'20%'},
				{ display: '指标名称', name: 'kpi_name', align: 'left',width:'20%'},
				{ display: '目标值', name: 'goal_value', align: 'left',width:'30%',editor: { type: 'text' }},
				{ display: '行动要点', name: 'action_note', align: 'left',width:'30%',editor: { type: 'text' }}
			],
			usePager : false,width : '100%',height :'100%',enabledEdit : true,isAddRow:false,isScroll : true,heightDiff:23,
			/* toolbar: { items: [
				{ text: '选择（<u>A</u>）', id:'add', click: bottomCheckClick, icon:'outbox' },{ line:true },
				{ text: '删除（<u>B</u>）', id:'delete', click: deleteBottomRow,icon:'delete' },{ line:true },
				{ text: '保存（<u>K</u>）', id:'save', click: saveCheckClick, icon:'save' },{ line:true },
			]}, */
			checkbox: true
			}
		);

		gridBottomManager = $("#bottomgrid").ligerGetGridManager();
	}
	
	
	//指标工具栏
	function bottomtoolbar(){
		var obj = [];
		
		obj.push({ text: '选择（<u>A</u>）', id:'select', click: bottomCheckClick,icon:'outbox' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>B</u>）', id:'delete', click: deleteBottomRow, icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '保存（<u>K</u>）', id:'save', click: saveCheckClick,icon:'save' });
       	obj.push({ line:true });
       	
       	
       	$("#bottomtoolbar").ligerToolBar({ items: obj});
	}
	
	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('C',addCenterRow);
		hotkeys('D',deleteCenterRow);
		hotkeys('S',saveCenterRow);
		hotkeys('B',deleteBottomRow);
		hotkeys('A',bottomCheckClick);
	}
	
	//增加行
	function addCenterRow(){ 
		centergrid.addRow();
	}
	
	//保存子目标
	function saveCenterRow () {
		
		var goal_detail_add_data = centergrid.getAdded();
		var validate_detail_buf = new StringBuffer();
		var msg = '';
		
		if(JSON.stringify(goal_detail_add_data) != '[{}]'){
			var targetMap = new HashMap();
			
			$.each(goal_detail_add_data, function(d_index, d_content){ 
				
				 if(typeof(d_content.child_goal_code) != "undefined" && d_content.child_goal_code != ""){ //过滤空行
					 
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
		
		var goal_detail_update_data = centergrid.getUpdated(); 
		var formPara = {
				acc_year :  $("#acc_year").val(),
				goal_code : $("#goal_code").val(),
				goal_detail_add_data : JSON.stringify(goal_detail_add_data),
				goal_detail_update_data : JSON.stringify(goal_detail_update_data),
		}; 
 
        $.ligerDialog.confirm('确定保存?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("updateBatchPrmGoalDetail.do?isCheck=false",formPara,function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
	}
	
	//删除子目标
	function deleteCenterRow(){ 
		
		var row = centergrid.getSelectedRow();
		
        if (!row) { $.ligerDialog.warn('请选择要删除的子目标'); return; }
        $.ligerDialog.confirm('确定要删除吗?',function(yes){
        	
        	if(!yes){
        		return ; 
        	}
        	
        	targetMap.remove(row.__id);
			centergrid.deleteSelectedRow();
			bottomgrid.reload();
		 	var ParamVo =[];
	        $(row).each(function (){
	        	if(this.goal_code != undefined && this.child_goal_code != undefined){
		        	ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.acc_year   +"@"+ 
						this.goal_code  +"@"+
						this.child_goal_code
					) 
	        	}
			});
	        
	        if(ParamVo.length == 0){
	        	$.ligerDialog.warn('请选择要删除的子目标 ');
	        	return ; 
	        }
			ajaxJsonObjectByUrl("deletePrmGoalDetail.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
				
	    		if(responseData.state=="true"){
	    			query();
	    		}
	    	});
        });
    }
	
	//保存指标
	function saveCheckClick(){ 
		
		var goal_detail_kpi_data = bottomgrid.rows; 
		if(goal_detail_kpi_data.length == 0){
			$.ligerDialog.warn('无数据保存 ');
			return ; 
		}
		
		var row = centergrid.getSelectedRow();
		
		var formPara = {
				acc_year : row.acc_year,
				child_goal_code : row.child_goal_code,
				goal_code : row.goal_code,
				goal_detail_kpi_data : JSON.stringify(goal_detail_kpi_data)
		}; 
		
        $.ligerDialog.confirm('确定保存?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("updateBatchPrmGoalDetailKpi.do?isCheck=false",formPara,function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
	}
	
	//删除指标
	function deleteBottomRow(){ 
		
		var row = centergrid.getSelectedRow();//获取子目标的行数据
		var deleteArr = bottomgrid.getSelectedRows();
		var bottomaArr = targetMap.get(row.goal_detail_id);//获取指定行的map
		
        if (!deleteArr) { $.ligerDialog.warn('请选择要删除的指标'); return; }
        
        $.ligerDialog.confirm('确定要删除吗?',function(yes){
        	
			var ParamVo =[];
			$(deleteArr).each(function (){					
				if(this.goal_code != undefined && this.child_goal_code != undefined && this.kpi_code != undefined){
			    	ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.acc_year   +"@"+ 
						this.goal_code  +"@"+
						this.child_goal_code +"@"+
						this.kpi_code
					);
				}
			}); 
		    
			 if(ParamVo.length == 0){
				 $.ligerDialog.warn('请选择要删除的指标 ');
				 return ;
			 }
			ajaxJsonObjectByUrl("deletePrmGoalDetailKpi.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
				
	    		if(responseData.state=="true"){
	    			
	    			for(var i = 0; i < deleteArr.length ; i++){
	        			var index = deleteArr[i]._index;
	        			bottomaArr.splice(index,1);
	        		}
	    			
	        		bottomgrid.deleteSelectedRow2();//删除选择的行
	        		loadTargetMap(row.goal_detail_id,bottomaArr);
	    			
	    		}
	    	});
        });
    }
	
	//选择页跳转
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
		$.ligerDialog.open({url: '../prmKpiLibPage.do?isCheck=false', 
				height:$(window).height(),width: $(window).width(), title:'选择',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			data: {
                __id: row.goal_detail_id,
                targetData:targetData
            },buttons: [ 
            	{ text: '确定', onclick: function (item, dialog) { dialog.frame.confirmPrmKpiLib();dialog.close();saveCheckClick(); },cls:'l-dialog-btn-highlight' }, 
			    { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
            ] 
        });
    }
	
	//选择页数据回显
	function addRows(target_data,__id){
		
		var map_data = targetMap.get(__id);
		if(map_data != null){
			$.each(target_data, function(t_index, t_content){
				var flag = 0;
				$.each(map_data, function(index, content){ 
	    			if(t_content.kpi_code == content.kpi_code){
	    				flag = 1;
						return false;
	    			}else{ flag = 0;}
	    		 }); 
				
				if(flag == 0){
					bottomgrid.addRow(t_content);
				}
	    	}); 
		} else{
			bottomgrid.addRows(target_data);
		}
    	
    	loadTargetMap(__id,target_data);
	}
	
	//关闭窗口
	function closeDialog(){ 
		dialog.close(); 
    }
	
	//键盘事件
	function loadTargetMap(goalKey,goalValue){
		targetMap.put(goalKey ,goalValue);
	}
	
	//保存
	function save() { 
		var formPara = {
			acc_year : $("#acc_year").val(),
			start_month : $("#start_month").val(),
			goal_code : $("#goal_code").val(),
			goal_name : $("#goal_name").val(),
			goal_note : $("#goal_note").val(),
		}; 

		ajaxJsonObjectByUrl("updatePrmGoal.do", formPara, function(responseData) {
			if (responseData.state == "true") {
				parentFrameUse().query();
			}
		});
	}
	
	//添加新行
	function is_addRow(){ 
		setTimeout(function () { 
			//当数据为空时 默认新增一行
			var data = centergrid.getData();
			if (data.length==0)
				centergrid.addRow();
			centergrid.rows[0].goal_detail_id = kpiNum;
		}, 500);
	}
	
	//确定保存
	function savePrmGoal() { 
		save();
	}
	
	
	//字典下拉框
	function loadDict() {
		$("#acc_year").ligerTextBox({width : 160});
		$("#start_month").ligerTextBox({width : 160});
		$("#goal_code").ligerTextBox({width : 160,disabled: true});
		$("#goal_name").ligerTextBox({width : 288});
		$("#goal_note").ligerTextBox({width :680});
		$("#acc_year").ligerTextBox({width : 160,disabled: true});
		
		 autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,'',false,'${hos_id}');
		 $("#hos_id").ligerTextBox({disabled: true,cancelable:true}); 
	}
</script>
<style type="text/css">
body{ padding:3px; margin:0;}
/* #goalLayout{  width:100%; margin:10px;  height:430px;margin:0; padding:0;} */
</style>

</head>

<body  style="padding:3px" >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="goalLayout">
			<div position="top">
				<form name="form1" method="post" id="form1">
					<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
						<tr>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">单位信息：</td>
							<td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" disabled="true" ltype="text" validate="{required:true,maxlength:20}" /></td>
							<td align="left"></td>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">绩效年度：</td>
							<td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text"  validate="{required:true,maxlength:20}" disabled="disabled"  class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" value="${acc_year}"/></td>
							<td align="left"></td>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">开始月份：</td>
							<td align="left" class="l-table-edit-td"><input name="start_month" type="text" id="start_month"  ltype="text" validate="{required:true,maxlength:20}"    class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" value="${start_month}"/></td>
							<td align="left"></td>
						</tr>
						<tr>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标编码：</td>
							<td align="left" class="l-table-edit-td"><input name="goal_code" type="text" id="goal_code" ltype="text" validate="{required:true,maxlength:20}"  value="${goal_code}" disabled="disabled"/></td>
							<td align="left"></td>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标名称：</td>
							<td align="left" class="l-table-edit-td"><input name="goal_name" type="text" id="goal_name" ltype="text" validate="{required:true,maxlength:20}"  value="${goal_name}"/></td>
							<td align="left"></td>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;"></td>
							<td align="left" class="l-table-edit-td"></td>
							<td align="left"></td>	
						</tr>
						<tr>
							<td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标描述：</td>
							<td align="left" class="l-table-edit-td" colspan="6"><input name="goal_note" type="text" id="goal_note" ltype="text" validate="{required:true,maxlength:20}"  value="${goal_note}"/></td>
							<td align="left"></td>
						</tr>
					</table>
				</form>
			</div>
			
            <div position="center">
            <div id="centertoolbal"></div>
				<div id="centergrid"></div>
            </div>
            
            
            <div position="bottom" style="height:100%">
	            <div id="bottomtoolbar" ></div>
            	<div id="bottomgrid"></div>
            </div>
	</div> 

</body>
</html>
