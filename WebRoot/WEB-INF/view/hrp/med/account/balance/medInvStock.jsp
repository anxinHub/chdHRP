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
			name : 'begin_in_date',
			value : $("#begin_in_date").val()
		});
		grid.options.parms.push({
			name : 'end_in_date',
			value : $("#end_in_date").val()
		}); 
		grid.options.parms.push({
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue() == null ? "2" : liger.get("bus_type_code").getValue() == "" ? "2" : liger.get("bus_type_code").getValue()
		}); 
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue() == null ? "" : liger.get("state").getValue()
		}); 
		grid.options.parms.push({
			name : 'begin_confirm_date',
			value : $("#begin_confirm_date").val()
		});
		grid.options.parms.push({
			name : 'end_confirm_date',
			value : $("#end_confirm_date").val()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '入库单号', name: 'in_no', align: 'left', width: '140',
					render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.in_id
							+ '")>'+rowdata.in_no+'</a>';
					}
				}, { 
		 			display: '摘要', name: 'brief', align: 'left', width: '200'
		 		}, { 
		 			display: '仓库', name: 'store_name', align: 'left', width: '150'
		 		}, { 
		 			display: '业务类型', name: 'bus_type_name', align: 'left', width: '80'
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'left', width: '150'
		 		}, { 
		 			display: '采购员', name: 'stocker_name', align: 'left', width: '80'
		 		}, { 
		 			display: '金额', name: 'amount_money', align: 'right', width: '100',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005 }', 1);
					}
		 		}, { 
		 			display: '编制日期', name: 'in_date', align: 'left', width: '80'
		 		}, { 
		 			display: '制单人', name: 'maker_name', align: 'left', width: '80'
		 		}, { 
		 			display: '入库日期', name: 'confirm_date', align: 'left', width: '80'
		 		}, { 
		 			display: '库管员', name: 'confirmer_name', align: 'left', width: '80'
		 		}, { 
		 			display: '状态', name: 'state_name', align: 'right', width: '80'
		 		}, { 
		 			display: '发票号', name: 'invoive_no', align: 'right', width: '100'
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedCommonIn.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				{ line:true },
				{ text: '复制（<u>C</u>）', id:'copy', click: copy_no, icon:'copy' },
				{ line:true },
				{ text: '冲账（<u>O</u>）', id:'offset', click: offset, icon:'offset' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
				{ line:true }, 
				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit' },
				{ line:true }, 
				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit' },
				{ line:true }, 
				{ text: '入库确认（<u>C</u>）', id:'confirm', click: confirm, icon:'account' },
				{ line:true },
				{ text: '模板打印（<u>P</u>）', id:'print', click: print, icon:'print' }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('C', copy_no);
		hotkeys('O', offset);
		hotkeys('D', remove);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('F', confirm);
		hotkeys('P', print);
	}
    
    //打开添加页面
    function add_open(){
    	
    	$.ligerDialog.open({
			title: '入库单添加',
			height: 550,
			width: 1000,
			url: 'addPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
		});   
	}
    
    //打开修改页面
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"in_id="+vo[3] ;
		$.ligerDialog.open({
			title: '入库单修改',
			height: 500,
			width: 1000,
			url: 'updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 1,
		});   
    }
   	
    //批量删除
    function remove(){
    	var is_delete = "${p08011 }";//是否可删除他人单据
    	var user_id = "${sessionScope.user_id}";//当前操作用户
    
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){	
				//is_delete：1不能删除他人单据
				if(is_delete == 1){
					//判断是否本人制单
					if(user_id != this.maker){
						in_nos = in_nos + this.in_no + "<br>";
					}else{
						//判断单据状态
						if(this.state > 1){
							in_nos = in_nos + this.in_no + "<br>";
						}
					}
				}else {
					//判断单据状态
					if(this.state > 1){
						in_nos = in_nos + this.in_no + "<br>";
					}
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				if(is_delete == 1){
					$.ligerDialog.error("删除失败！<br>以下单据不是未审核状态或为他人制单：<br>"+in_nos);
					return;
				}else{
					$.ligerDialog.error("删除失败！<br>以下单据不是未审核状态：<br>"+in_nos);
					return;
				}
			}
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMedCommonIn.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
  //批量复制
	function copy_no(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			$.ligerDialog.confirm('确定复制?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("copyMedCommonIn.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
	//批量冲销
	function offset(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 3){
					in_nos = in_nos + this.in_no + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("冲账失败！<br>以下单据不是已确认状态：<br>"+in_nos);
				return;
			}
			$.ligerDialog.confirm('确定冲账?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("offsetMedCommonIn.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
	//批量审核
	function audit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 1){
					in_nos = in_nos + this.in_no + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("审核失败！<br>以下单据不是未审核状态：<br>"+in_nos);
				return;
			}
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMedCommonInBatch.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
	//批量消审
	function unAudit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("消审失败！<br>以下单据不是已审核状态：<br>"+in_nos);
				return;
			}
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("unAuditMedCommonInBatch.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
   	
	//批量入库确认
    function confirm(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("入库确认失败！<br>以下单据不是已审核状态：<br>"+in_nos);
				return;
			}
			$.ligerDialog.confirm('确定入库确认?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("confirmMedCommonInBatch.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
	//批量打印
    function print(){
    	return ;
    }
   
    function loadDict(){
		//字典下拉框
		autoCompleteByData("#state", medInMain_state.Rows, "id", "text", true, true);
		//alert(liger.get("bus_type_code").getValue());
		autocomplete("#bus_type_code", "../../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {sel_flag : 'in'}, true);
		autocomplete("#store_code", "../../../queryMedStore.do?isCheck=false", "id", "text", true, true);
		autocomplete("#sup_code", "../../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
        $("#begin_in_date").ligerTextBox({width:120});
        autodate("#begin_in_date", "yyyy-mm-dd", "month_first");
        $("#end_in_date").ligerTextBox({width:120});
        autodate("#end_in_date", "yyyy-mm-dd", "month_last");
        $("#begin_confirm_date").ligerTextBox({width:120});
        $("#end_confirm_date").ligerTextBox({width:120});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	入库日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				业务类型：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:false}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				状态：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="state" type="text" id="state" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	确认日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				供应商：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
