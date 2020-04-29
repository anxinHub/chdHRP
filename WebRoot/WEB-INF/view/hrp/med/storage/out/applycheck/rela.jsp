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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
		loadHotkeys();
		query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'group_id',
			value : "${medApplyRela.group_id}"
		});
		grid.options.parms.push({
			name : 'hos_id',
			value : "${medApplyRela.hos_id}"
		}); 
		grid.options.parms.push({
			name : 'copy_code',
			value : "${medApplyRela.copy_code}"
		}); 
		grid.options.parms.push({
			name : 'apply_id',
			value : "${medApplyRela.apply_id}"
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '单据类型', name: 'rela_type', align: 'left', minWidth: '150',
					render : function(rowdata, rowindex, value) {
						var type = "";
						switch(parseInt(value)){
							case 1 :
								type = "出库单";
								break;
							case 2 :
								type = "调拨单";
								break;
							case 3 :
								type = "代销出库单";
								break;
							case 4 :
								type = "代销调拨单";
								break;
							case 5 :
								type = "科室需求计划";
								break;
						}
						return type;
					}
				}, { 
					display: '单据号', name: 'rela_no', align: 'left', minWidth: '150',
					render : function(rowdata, rowindex, value) {
						return '<a href=javascript:rela_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.rela_type 
							+ ',' + rowdata.rela_id 
							+ ',' + rowdata.store_id 
							+ '")>'+rowdata.rela_no+'</a>';
					}
				}, { 
		 			display: '制单人', name: 'maker_name', align: 'left', minWidth: '80',
		 		}, { 
		 			display: '编制日期', name: 'rela_date', align: 'left', minWidth: '90',
		 		}, { 
		 			display: '状态', name: 'state', align: 'left', minWidth: '80',
		 			render: function(rowdata, rowindex, value){
		 				return getStateName(rowdata.rela_type, value);
		 			}
		 		} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedApplyRela.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: deleteRela, icon:'delete' },
				{ line:true },
				{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' }
			]}, 
			onDblClickRow : function (rowdata, rowindex, value){
				rela_open(
						rowdata.group_id 
						+ ',' + rowdata.hos_id 
						+ ',' + rowdata.copy_code 
						+ ',' + rowdata.rela_type 
						+ ',' + rowdata.rela_id 
						+ ',' + rowdata.store_id 
				);
			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //返回单据状态
    function getStateName(rela_flag, rela_state){
    	var state_name = "";
    	var flag = parseInt(rela_flag);
    	var state = parseInt(rela_state);
    	switch(flag){
    		case 1 :
    			switch(state){
    				case 1 :
    					state_name = "未审核";
    					break;
    				case 2 :
    					state_name = "已审核";
    					break;
    				case 3 :
    					state_name = "已确认";
    					break;
    			}
    			break;
    		case 2 : 
    			switch(state){
					case 1 :
						state_name = "未确认";
						break;
					case 2 :
						state_name = "调出确认";
						break;
					case 3 :
						state_name = "调入确认";
						break;
    			}
    			break;
    		case 3 : 
    			switch(state){
					case 1 :
						state_name = "未审核";
						break;
					case 2 :
						state_name = "已审核";
						break;
					case 3 :
						state_name = "已确认";
						break;
    			}
    			break;
    		case 4 : 
    			switch(state){
					case 1 :
						state_name = "未确认";
						break;
					case 2 :
						state_name = "调出确认";
						break;
					case 3 :
						state_name = "调入确认";
						break;
    			}
    			break;
    		case 5 : 
    			switch(state){
					case 0 :
						state_name = "中止计划";
						break;
					case 1 :
						state_name = "未审核";
						break;
					case 2 :
						state_name = "已审核";
						break;
					case 3 :
						state_name = "汇总执行";
						break;
    			}
    			break;
    	}
    	return state_name;
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('D', deleteRela);
	}
    
    //修改
    function rela_open(obj){		
		var vo = obj.split(",");
		var flag = vo[3];

		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"rela_type="+vo[3] +"&"+ 
			"rela_id="+vo[4] +"&"+ 
			"store_id="+vo[5] ;
		
		$.ligerDialog.open({
			title: '查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'relaOutPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true
		});   
    }
	
    //删除
	function deleteRela(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var rela_nos = "";
			$(data).each(function (){	
				if(parseInt(this.state) > 1){
					rela_nos = rela_nos + this.rela_no + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					$("#apply_id").val() + "@" +
					this.rela_type + "@" +
					this.rela_id
				) 
			});
			if(rela_nos != ""){
				$.ligerDialog.error("操作失败！<br>以下单据不允许删除：<br>"+rela_nos);
				return;
			}
			ajaxJsonObjectByUrl("deleteMedApplyRela.do?",{ParamVo : ParamVo.toString()},function (responseData){
				if(responseData.state=="true"){
					query();
	                parentFrameUse().query();
				}
			});
		}
	}
    
    function loadDict(){
        $("#apply_no").ligerTextBox({width:160, disabled:true, readonly: true});
	}  
    
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td">
            	申请单号：
            </td>
            <td align="left" class="l-table-edit-td">
            	<input name="apply_no" type="text" id="apply_no"  value= "${medApplyRela.apply_no}" ltype="text" disabled="disabled" validate="{required:false}" />
            </td>
            <td align="left" class="l-table-edit-td">
            	<input name="apply_no" type="hidden" id="apply_id"  value= "${medApplyRela.apply_id}" ltype="text" validate="{required:false}" />
            </td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
