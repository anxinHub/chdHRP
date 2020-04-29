<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">

	var errorMsg = '${errorMsg}';

	var grid;

	var gridManager = null;

	$(function() {

		loadHead(null);
		
		$("#dept_kind_name").ligerTextBox({width : 377,disabled:true});
		$("#dept_name").ligerTextBox({width : 377,disabled:true});
		$("#item_name").ligerTextBox({width : 160,disabled:true});
		$("#formula_method_chs").ligerTextBox({width : 850,disabled:true});
		$("#bonus_money").ligerTextBox({width : 160,disabled:true});
		
		if(!errorMsg){
			
			grid.loadData();
			
		}else{
			
			$.ligerDialog.error(errorMsg);
		}

	});

	function loadHead() {//获取查询条件的数值

		grid = $("#maingrid").ligerGrid({
			columns : [ 
						{display : '指标编码',name : 'target_code',align : 'left',width:'15%'}, 
						
						{display : '指标名称',name : 'target_name',align : 'left',width:'15%'}, 
						
						{display : '指标性质',name : 'nature_name',align : 'left',width:'10%'},
						
						{display : '取值方法',name : 'method_name',align : 'left',width:'40%',render:
							function(rowdata , rowindex , value){
								if(rowdata.method_name == null){
									return '';
								}
								
								if(rowdata.method_name == 'null'){
									return '';
								}
								
								if(rowdata.method_name == ''){
									return '';
								}
								
								return rowdata.method_name;
							}
						
						},
						
						{display : '指标值',name : 'target_value',align : 'right',width:'20%',
							render: function (rowdata , rowindex , value){
								if(rowdata.method_code == '02'){
										var obj1 = rowdata.target_value;
										obj1 = encodeURIComponent(obj1).replace(/\+/g,'%2B').replace(/\&/g,'%26').replace(/\?/g,'%3F').replace(/\#/g,'%23').replace(/\=/g,'%3D').replace(/\"/g,'%22').replace(/\'/g, '%27');
										var obj2 = rowdata.formula_code;
										obj2 = encodeURIComponent(obj2).replace(/\+/g,'%2B').replace(/\&/g,'%26').replace(/\?/g,'%3F').replace(/\#/g,'%23').replace(/\=/g,'%3D').replace(/\"/g,'%22').replace(/\'/g, '%27');
										var obj3 = rowdata.method_name;
										obj3 = encodeURIComponent(obj3).replace(/\+/g,'%2B').replace(/\&/g,'%26').replace(/\?/g,'%3F').replace(/\#/g,'%23').replace(/\=/g,'%3D').replace(/\"/g,'%22').replace(/\'/g, '%27');
return "<a href='#' onclick=\"openFormula('" + obj1 + "','" + obj2 + "','" + obj3 + "');\" >" +formatNumber(rowdata.target_value ==null ? 0 : rowdata.target_value,4,1);+ "</a>";

								}else{
									
									return formatNumber(rowdata.target_value ==null ? 0 : rowdata.target_value,4,1);
								}
								
							}
						},
					],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryHpmDeptBonusDataForFormula.do?isCheck=false&dept_id='+'${dept_id}'+'&dept_no='+'${dept_no}'+'&dept_kind_code='+'${dept_kind_code}'+'&formula_code='+'${formula_code}'+'&acct_yearm='+'${acct_yearm}',
			width : '100%',
			height : '100%',
			checkbox : false,
			delayLoad : true,
			rownumbers : true
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function openFormula(obj1,obj2,obj3){
		
		//obj3 = encodeURI(obj3).replace(/\+/g,'%2B').replace(/\&/g,'%26').replace(/\?/g,'%3F').replace(/\#/g,'%23').replace(/\=/g,'%3D');
		//obj3 = encodeURIComponent(obj3).replace(/\+/g,'%2B').replace(/\&/g,'%26').replace(/\?/g,'%3F').replace(/\#/g,'%23').replace(/\=/g,'%3D').replace(/\"/g,'%22').replace(/\'/g, '%27');
		
		var para = "?isCheck=false&viewFlag=2&dept_id=${dept_id}&dept_no=${dept_no}&dept_code=${dept_code}&dept_name=${dept_name}&dept_kind_name=${dept_kind_name}&item_code=${item_code}&item_name=${item_name}&dept_kind_code=${dept_kind_code}&acct_yearm=${acct_yearm}&bonus_money="+obj1+"&formula_code="+obj2+"&formula_method_chs="+obj3;

		parent.$.ligerDialog.open({ 
    		url: 'hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForFormulaMainPage.do'+para,data:{}, 
    		height:$(window).height()-30,width: $(window).width()-10, title:'修改',modal:true,showToggle:false,
    		showMax:true,showMin: false,isResize:true,
    		parentframename: window.name,
    		buttons: [ 
    			{ text: '关闭', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		]
    	});
    	
    }
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_kind_name" type="text" id="dept_kind_name" ltype="text" value="${dept_kind_name}"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_name" type="text" id="dept_name" ltype="text" value="${dept_name}"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">奖金项目：</td>
			<td align="left" class="l-table-edit-td"><input name="item_name" type="text" id="item_name" ltype="text" value="${item_name}"/></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计算公式：</td>
			<td align="left" class="l-table-edit-td" colspan="4"><input name="formula_method_chs" type="text" id="formula_method_chs" value="${formula_method_chs}" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">奖金金额：</td>
			<td align="left" class="l-table-edit-td"><input name="bonus_money" type="text" id="bonus_money" value="${bonus_money}" ltype="text"/></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
