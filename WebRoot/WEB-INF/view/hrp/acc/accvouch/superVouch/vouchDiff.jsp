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
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<!-- Sensei Grid CSS -->
<link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-grid.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/jquery/smartMenu.css"/>
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery/jquery-smartMenu-min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/js/vouchDiff.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=path%>/lib/et_components/etCheck/css/icheck.css">
<script src="<%=path%>/lib/et_components/etCheck/js/icheck.js"></script>
<script src="<%=path%>/lib/et_components/etCheck/etCheck.js"></script>

<!-- CSS dependencies -->
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/hrp/acc/superVouch/grid/bootstrap/dist/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/themes/classic.css"/>
<link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/themes/classic.date.css"/>
<link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/fontawesome/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/summernote/dist/summernote.css"/>

<script src="<%=path%>/lib/hrp/acc/superVouch/grid/lodash/lodash.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/picker.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/picker.date.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/bootstrap/dist/js/bootstrap.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/summernote/dist/summernote.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/isInViewport/lib/isInViewport.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/typeahead.js/dist/typeahead.jquery.js"></script>

<!-- Sensei Grid JS -->
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-grid.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-editors.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-row-actions.js"></script>


<script type="text/javascript">
var pGrid=parent.frameObj.grid;
var isReadonly=parent.frames["vouchFrame"].grid.config["readonly"];
var is_diff_check;
var is_budg=1;
var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	$(function() {
		if(existsChromeVer42(44)){
			//谷歌43以下版本滚动条的宽度多1px
			$("#vouchCheckDiv").css("padding-right","1px");
		}
		
		var loadIndex = layer.load(1);
		is_budg=$("#is_budg_val",parent.document).val();
		//是否自动差异标注
		is_diff_check = $("#is_diff_check").etCheck({
			checked :$("#is_diff_check",parent.document).prop("checked"),
			ifChanged: function (status, checked, disabled) {
				if(checked){
					parent.is_diff_check.setCheck(checked);
				}else{
					parent.is_diff_check.setUncheck(checked);
				}
				
	        }
		});
		
		loadVouchCheckTable();
		$(':button').ligerButton({width:70});
		$("#vouchCheckDiv").css("height", $(window).height()-200); 
		//alert(JSON.stringify(parent.vouchCashMap));
		//index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		  //  console.log($("#vouchCheckDiv").width())
		  //  console.log($(window).width());
		
		//快捷键处理
		BindKeyBoard([
			{keyCode:112,fn:function(){}},//F1
		    {keyCode:113,fn:function(){myDel()}},//删除F2
		    {keyCode:114,fn:function(){mySave(2)}},//手工标注
		    {keyCode:115,fn:function(){}},//F4
		    {keyCode:116,fn:function(){}},//F5
		    {keyCode:117,fn:function(){}},//F6
		    {keyCode:118,fn:function(){}},//F7
		    {keyCode:119,fn:function(){}},//F8
		    {keyCode:120,fn:function(){}},//F9
		    {keyCode:121,fn:function(){}},//F10
		    {keyCode:122,fn:function(){}},//F11
		    {keyCode:123,fn:function(){mySave()}},//保存F12
		    {keyCode:27,fn:function(){myClose()}}//关闭ESC
		]);
		
		
		
		layer.close(loadIndex);
		$("body").scrollTop(0);
		
	});
	
	
	
	function myDel(){
				
		if(grid.getSelectedRows().length>1){
			$.ligerDialog.confirm("确定要删除吗？", function(yes) {
				if(yes) {
					grid.removeActiveRow();
					sumMoney();//合计金额
				}
			});
		}else{ 
			grid.removeActiveRow();
			sumMoney();//合计金额
		}
		
		if(grid.getRows().length==0){
			grid.assureEmptyRow();
		}
		
	}
	
	function mySave(flag){
		
		if(!$("input[name='saveDiffButton']").is(":visible") && $("input[name='controllerButton']").attr("disabled")!="disabled"){
			flag=1;
		}
		
		if(flag==1){
			
			if($("input[name='controllerButton']").attr("disabled")=="disabled"){		
				//按钮置灰状态下返回
				return;
			}
			
		}else{
			
			if($("#vouchId",parent.document).val()==""){
				$.ligerDialog.error("请先保存凭证！");
				return false;
			}
		}
		
		var rowData=[];
		var msg="";
		//grid.editorBlur(grid.getActiveCell());//光标离开保存数据
		grid.saveEditor(true);//光标离开保存数据
		$.each(grid.getGridData(),function (n,obj) {
			
			//金额都为空不处理 
			if(obj.money=="")obj.money=0;
			if(parseFloat(obj.money)==0){
				return true;
			}
			//验证不能为空。
			$.each(obj,function (col,val) {
			
				if(col=="summary" && val==""){
					msg="摘要，不能为空！";
					return false;
				}
				
				if(col=="money" && (isNaN(val.replace(/\,/g,"")) || val=="")){
					msg="金额，不能为空！";
					
					return false;
				}
				if(col=="diff_item_name" && val==""){
					msg="标注项目，不能为空！";
					return false;
				}
			});
			
			rowData.push(obj);
			
		 });

		
		if(msg!=""){
			$.ligerDialog.error(msg);
			return false;
		}
		
		if(rowData.length>0){
			var is_sz=false;
			var sum04=0;
			var sum05=0;
			var sum06=0;
			var sum07=0;
			
			$.each(pGrid.getGridData(),function (n,obj) {
				if(obj.subj_code.substring(0,1)=="4" || obj.subj_code.substring(0,1)=="5" || obj.budg_subj_code.substring(0,1)=="6" || obj.budg_subj_code.substring(0,1)=="7"){
					is_sz=true;
				}
				
				if(is_budg=1){
					if(obj.debit=="")obj.debit=0;
					if(obj.credit=="")obj.credit=0;
					if(obj.budg_debit=="")obj.budg_debit=0;
					if(obj.budg_credit=="")obj.budg_credit=0;
					if(obj.subj_code.substring(0,1)=="4"){
						sum04=sum04+obj.credit-obj.debit;
					}else if(obj.subj_code.substring(0,1)=="5"){
						sum05=sum05+obj.debit-obj.credit;
					}else if(obj.subj_code.substring(0,1)=="6"){
						sum06=sum06+obj.budg_credit-obj.budg_debit;
					}else if(obj.subj_code.substring(0,1)=="7"){
						sum07=sum07+obj.budg_debit-obj.budg_credit;
					}
				}else{
					if(obj.debit=="")obj.debit=0;
					if(obj.credit=="")obj.credit=0;
					if(obj.subj_code.substring(0,1)=="4"){
						sum04=sum04+obj.credit-obj.debit;
					}else if(obj.subj_code.substring(0,1)=="5"){
						sum05=sum05+obj.debit-obj.credit;
					}
				}
				
			});
			
			if(sum04==sum06 && sum05==sum07){
				$.ligerDialog.error("收支科目的金额相等不需要标注！");
				return false;
			}
			
			if(!is_sz){
				$.ligerDialog.error("没有收支科目不需要标标注！");
				return false;
			}
		}
		
		
		
		if(flag==1){
			parent.diffJson = rowData;
			setTimeout(function(){
				parent.layer.close(index);
			},50);
			
		}else{
			
			/* if(rowData.length==0){
				$.ligerDialog.error("没有需要保存的数据。");
				return false;
			} */
			
			$.ligerDialog.confirm("确定要调整提交吗？", function(yes) {
				if(yes) {
					var saveIndex = layer.load(1);
					Local.set("hrp[repeat[commit",true);
					param={
						vouch_id: $("#vouchId",parent.document).val(),
						vouch_date: $("#vouch_date",parent.document).val(),
						diff: JSON.stringify(rowData)
					}
					ajaxJsonObjectBylayer("updateAccVouchDiffSg.do?isCheck=false",param,function (responseData){
						parent.diffJson = rowData;
						is_diff_check.setUncheck(false);
						initRow=grid.getRows().length;
					    initMoneyText=$("#sum_money").text();
					},layer,saveIndex);
				}
			});
			
		}
		
	}
	
	function myClose(){
		//frameElement.dialog.close();
		//parent.layer.close(index);
		if((initRow==1 && initMoneyText!=$("#sum_money").text()) ||  (initRow>1 && (initRow!=grid.getRows().length || initMoneyText!=$("#sum_money").text()))){
			if(!confirm("是否关闭？")){
				return;
			}
		}
		parent.layer.close(index);
		
	}	

	function myImport(){
		
	}
	
	
	 
</script>
</head>

<body style="padding: 0px;overflow-x:auto; overflow-y:scroll;"  onload="">

	<div style="margin:0;position:fixed;z-index:3;top:0px;width:100%;background:white;padding:0px 0px;">
		<table id="headTable" cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="height:25px;">
			<tr>
				<td align="left">
					<input type="button" value=" 删除 F2" onclick="myDel();"/>
					<input type="button" name="saveDiffButton" value="手工调整 F3" onclick="mySave(2);"/>
					<span id="sum_money">0.00</span>
				</td>
				<td align="right"  width="260px">
					<input type="checkbox" id="is_diff_check"/>
					<label for="is_diff_check">自动差异标注</label>
					<input type="button" value=" 关闭 ESC" onclick="myClose();"/>
					<input type="button" name="controllerButton" value=" 确定 F12" onclick="mySave(1);"/>
				</td>
			</tr>
		</table>
	</div>
	<div id="vouchCheckDiv" class="sensei-grid sensei-grid-default" style="position:absolute;top:25px;"></div>
	
</body>
</html>
