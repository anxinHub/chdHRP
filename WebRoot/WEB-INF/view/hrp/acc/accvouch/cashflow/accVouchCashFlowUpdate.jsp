<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

	var grid;

	var gridManager = null;

	var userUpdateStr;

	var cash_dire=0;
	
	$(function() {
		//alert('${cash_dire}');
		loadHead(null); //加载数据
		
		$(':button').ligerButton({width:90});
		
		is_addRow();
		$("#cash_dire").val('${cash_dire}');
	});
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '摘要',
				name:'summary',			
				align : 'left',
				editor : {						
						type : 'text'
				}
			},{
				display : '现金项目',
				name:'cash_item_id',
				textField: 'cash_item_name',
				align : 'left',
				editor : {
						type : 'select',
						valueField: 'id', 
						textField: 'text',
						url : '../../queryCashItemSelect.do?isCheck=false',
						parms:{cash_dire:$("#cash_dire").val()},		
						keySupport:true,
				      	autocomplete: true,
				      	delayLoad: false,
				      	triggerToLoad : false, //是否在点击下拉按钮时加载
				      	async:false,
						onBeforeOpen: function (selectValue){
							//this.options.parms={cash_dire:$("#cash_dire").val()};
    		    			this.setParm("cash_dire",$("#cash_dire").val());
    		    			this.reload();
    		    		}
    						
    			}
			},{
				display : '金额',
				name : 'cash_money',
				align : 'left',editor : {
					type : 'text'},
					render : function(rowdata, rowindex, value) {
      					return formatNumber(rowdata.cash_money, 2, 1);
      				}
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			//data:cashData,
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			enabledEdit : true,
			selectRowButtonOnly : true,
			url:"queryAccCashFlow.do?isCheck=false&vouch_id=${vouch_id}&vouch_detail_id=${vouch_detail_id}&cash_item_id=${cash_item_id}"
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	$(document).bind('keydown.grid', function(event) {
 		if (event.keyCode == 13) {// enter,也可以改成9:tab

 			grid.endEditToNextChd();

 	 	}
 	 });

	
	
	//自动添加行
	function is_addRow() {
		
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 100);

    }
	
	function myDelete(){
		
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			
			$.ligerDialog.error('请选择行');
			
		} else {
			
			$.ligerDialog.confirm('确定删除吗?', function(yes) {
				
				if (yes) {
					
					grid.deleteSelectedRow();
					
					setTimeout(function() {
						
						if(grid.getData().length==0){
							
							grid.addRow();
							
						}
					}, 1000);
				}
			});
				
			/*var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(
				this.cash_item_id)
			});
			$.ligerDialog.confirm('确定删除吗?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAccVouchCashFlow.do?isCheck=false", {
						ParamVo : ParamVo
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});*/
		}
	}
	
	function mySave(){

		var ParamVo = [];
		var data=grid.getData();
		var msg="";
		var moneySum=0;
		
		$(data).each(function() {
			if((this.cash_item_id==undefined || this.cash_item_id=="") && (this.summary==undefined || this.summary=="")){
				return true;
			}
			if(this.cash_item_id==undefined || this.cash_item_id==""){
				msg='现金项目不能为空！';
				return false;
			}else if(this.summary==undefined || this.summary==""){
				msg='摘要不能为空！';
				return false;
			}else if((isNaN(this.cash_money) || this.cash_money==0)){
				msg='金额不能为空！';
				return false;
			}
			if(this.cash_item_id!=null){
				
				ParamVo.push(
						this.cash_item_id+"@"+this.cash_money+"@"+this.summary
					);
					moneySum+=parseFloat(this.cash_money);
			}
			
		});
		
		if(msg!=""){
			$.ligerDialog.error(msg);
			return;
		}
		if(ParamVo.length==0){
			myUnbz();
			return;
		}
		//004:现金流量与辅助核算一起保存
		//if($("#para004",parent.document).val()=="1"){
			
		//}
	
		/* if((parseFloat($("#debit").val())!=0 && parseFloat($("#debit").val())!=moneySum)){
			$.ligerDialog.error("分录金额："+$("#debit").val()+"，与标注金额不相等！");
			return;
		}else if((parseFloat($("#credit").val())!=0 && parseFloat($("#credit").val())!=moneySum)){
			$.ligerDialog.error("分录金额："+$("#credit").val()+"，与标注金额不相等！");
			return;
		} */

		var formData="vouch_id="+'${vouch_id}'+"&vouch_detail_id="+'${vouch_detail_id}'+"&cash_item_id="+ParamVo;
				
				ajaxJsonObjectByUrl("saveAccVouchCashFlow.do?isCheck=false&"+formData, {
					
				}, function(responseData) {
					
					if (responseData.state == "true") {
						
						parent.query();
					
					}
				
				});
		
	}
	
	function myUnbz(){
		var ParamVo = [];
		ParamVo.push('${vouch_id}');
		ParamVo.push('${vouch_detail_id}');
		$.ligerDialog.confirm('确定取消标注?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("AccVouchCashFlowUnbatch.do", {
					ParamVo : ParamVo.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						//$("#maingrid").find("tbody").children().children().remove();
						
						var data=grid.getData();
						$(data).each(function(i,o) {
							grid.deleteRow(i);
						});
						grid.deleteRow(0);		
						setTimeout(function() {
							if(grid.getData().length==0){
								grid.addRow();
							}
						}, 1000);
						
						parent.query();
					}
				});
			}
		});
	}
</script>

</head>

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<!--div id="topmenu"></div-->
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方向：</td>
			<td align="left" class="l-table-edit-td">
				<select name="cash_dire" id="cash_dire" >
					<option value="0">流入</option>
					<option value="1">流出</option>
				</select>
			</td>
			<td align="left">
				<input type="button" accessKey="S"  value=" 删除（D）" onclick="myDelete();"/>
			</td>
		</tr>
	</table>
	<div id="maingrid"></div>

</body>
</html>
