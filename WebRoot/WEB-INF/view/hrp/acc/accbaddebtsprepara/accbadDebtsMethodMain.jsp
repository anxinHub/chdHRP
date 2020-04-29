<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
<script>
	var grid;

	var gridManager = null;

	var userUpdateStr;
	var acc_year;
	var acc_month;
	var range_pro;
	var range_type;
	var acc_flag;     //当前会计期间是否结账标志
	var subj_accFlag; //应收科目是否已经记账
	var subj_badCode; //应收科目是否已经计提过坏账
	var subj_endOs;   //应收科目期末余额是否 <=0
	var subj_code ;   //应收科目
	
	
	$(function() {

		acc_year = "${acc_year}";
		acc_month = "${acc_month}";
		subj_code ="${subj_code}";
		
		range_type = "${range_type}";
		range_pro = "${range_pro}";
		acc_flag = "${flag}";
		subj_endOs = "${subj_endOs}";
		subj_badCode = "${subj_badCode}";
		subj_accFlag = "${subj_accFlag}";
		
		
		if(range_type == 0){	
			$("#ye").attr("checked",true);
			$("#zl").attr("checked",false);
			if(range_pro ==''){
				$("#fix_rate").val("1.00");
			}else{
				$("#fix_rate").val(range_pro);
			}
		}else{
			$("#zl").attr("checked",true);
			$("#ye").attr("checked",false);	
			$("#fix_rate").val("1.00");
		}
		loadDict();
		loadForm();
		show();
	});
	
	function show(){
		var value = $('input[name="auto"]:checked').val();
		//若是余额分析法 则可以编辑固定比例 ;否则不可编辑
		if(value == 0 ){
			$("#show_table").show();
		}else{
			$("#show_table").hide();
		}
	}

	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		grid.options.parms.push({ name : 'subj_code', value : subj_code });  //应收科目
		grid.options.parms.push({ name : 'acc_year', value : acc_year });	 //计提年
		grid.options.parms.push({ name : 'acc_month', value : acc_month });  //计提月
		grid.options.parms.push({ name : 'bad_subj_code', value : liger.get("bad_subj_code").getValue() }); //坏账科目
		grid.options.parms.push({ name : 'fy_subj_code', value : liger.get("fy_subj_code").getValue() });   //管理科目
		grid.options.parms.push({ name : 'range_type', value : $('input[name="auto"]:checked').val() });	 //计提方式
		grid.options.parms.push({ name : 'fix_rate', value : $("#fix_rate").val() });	 //固定计提比例
		grid.options.parms.push({ name : 'subj_endOs', value : subj_endOs });	 //应收科目余额
		
		//根据表字段进行添加查询条件
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadDict() {
		//字典下拉框
		var param = {
			SUBJ_NATURE_CODE : '01',
			acc_year : "${acc_year}",
			acc_month : "${acc_month}"
		};

		$("#bad_subj_code").ligerComboBox({
			parms : param,
			url : 'queryBadSubj.do?isCheck=false',
			valueField : 'subj_code_hz',
			textField : 'subj_code_gl',
			selectBoxWidth : 350,
			autocomplete : true,
			width : 350
		});

		$("#fy_subj_code").ligerComboBox({
			parms : param,
			url : 'queryManageSubj.do?isCheck=false',
			valueField : 'subj_code_hz',
			textField : 'subj_code_gl',
			selectBoxWidth : 350,
			autocomplete : true,
			width : 350
		});
	}
	
	//确定按钮
	function saveBadDetsPrepara(){
	
		if(acc_flag == 1){
			$.ligerDialog.error("本会计期间已经结账，不能计提坏账！");
			return;
		}else{
			if(subj_endOs <= 0){
				$.ligerDialog.error("应收科目在当前的年月没有余额，不能进行坏账计提！");
				return;
			}else{
				//应收科目当前会计期间已经计提过坏账了，不能进行计提
				if(subj_badCode != null && subj_badCode !=''){
					$.ligerDialog.error("应收科目  "+ subj_badCode+" 在当前年月已经提取坏账！");
					return;
				}else{
					//查看当前会计期间应收科目是否已经记账
					if(subj_accFlag == 1){
						$.ligerDialog.error("选择的应收科目所在凭证没有全部记账，请记账后再执行本功能！");
						return;
					}else{
						//计提方式  0 为余额百分比法
						if($('input[name="auto"]:checked').val() == 0){
							//余额分析法
							if($("#fix_rate").val()<=0){
								$.ligerDialog.error('固定计提比例不能小于等于0！');
					    		return;
							}else{
								$.ligerDialog.confirm('确定要计提吗?', 
							    		function (yes){
							    			if(yes){
							    				var formPara={
							    						subj_code : subj_code , //应收科目
							    						acc_year : acc_year ,	 //计提年
							    						acc_month : acc_month ,  //计提月
							    						bad_subj_code : liger.get("bad_subj_code").getValue().split(".")[1] , //坏账科目
							    						fy_subj_code : liger.get("fy_subj_code").getValue().split(".")[1] ,   //管理科目
							    						range_type : $('input[name="auto"]:checked').val() ,	 //计提方式
							    						fix_rate : $("#fix_rate").val() ,	 //固定计提比例
							    						subj_endOs : subj_endOs 	 //应收科目余额
		          			    				};
							    				ajaxJsonObjectByUrl("addAccBadDebtsExtract.do?isCheck=true",formPara,
							    		    			function (responseData){
							    		    				if(responseData.state=="true"){
							    		    					alert("凭证ID："+responseData.vouch_id);
							    		    				}else{
							    		    					alert(responseData.msg);
							    		    				}
							    		    			}
							    		    		);
							    			}						
							    		}				
							    	);

							}
						}else{
							$.ligerDialog.confirm('确定要计提吗?', 
						    		function (yes){
						    			if(yes){
						    				var formPara={
						    						
						    						subj_code : subj_code , //应收科目
						    						acc_year : acc_year ,	 //计提年
						    						acc_month : acc_month ,  //计提月
						    						bad_subj_code : liger.get("bad_subj_code").getValue().split(".")[1] , //坏账科目
						    						fy_subj_code : liger.get("fy_subj_code").getValue().split(".")[1] ,   //管理科目
						    						range_type : $('input[name="auto"]:checked').val() ,	 //计提方式
						    						fix_rate : $("#fix_rate").val() ,	 //固定计提比例
						    						subj_endOs : subj_endOs 	 //应收科目余额
						    						
	          			    				};
						    				ajaxJsonObjectByUrl("addAccBadDebtsExtract.do?isCheck=true",formPara,
						    		    			function (responseData){
						    		    				if(responseData.state=="true"){
						    		    					alert("凭证ID："+responseData.vouch_id);
						    		    				}else{
						    		    					alert(responseData.msg);
						    		    				}
						    		    			}
						    		    		);
						    			}						
						    		}				
						    	);

						}
					}
					
				}
				
			}
			
		}
		
		
		//判断计提比例范围不能小于0
		
			
		
	}
	
	function loadForm() {
		$("form").ligerForm();

	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">坏账提取科目：</td>
				<td align="left" class="l-table-edit-td" colspan="4">
					<input name="bad_subj_code" type="text" id="bad_subj_code" ltype="text" />
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">管理费用科目：</td>
				<td align="left" class="l-table-edit-td" colspan="4">
					<input name="fy_subj_code" type="text" id="fy_subj_code" ltype="text" />
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计提方式：</td>
				<td colspan="4">
					<input type="radio" id="ye" value="0" name="auto" style="padding-left: 10px;"  onchange="show();" /> 余额百分比法 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" id="zl" value="1" name="auto" style="padding-left: 10px;" onchange="show();" /> 账龄分析法
				</td>
				
			</tr>
			<tr id="show_table">
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">固定计提比例：</td>
				<td >
					<input name="fix_rate" type="text" id="fix_rate" style="width: 50px;" />
				</td>
				<td align="left" class="l-table-edit-td" >%</td>
				<td style="width: 60px;"></td>
				<td style="width: 60px;"></td>
			</tr>
		</table>
	</form>

	<div id="maingrid"></div>

</body>
</html>
