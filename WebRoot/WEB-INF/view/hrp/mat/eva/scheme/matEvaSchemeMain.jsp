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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var grid; 
	var gridManager = null;
	
	$(function() {
		
		loadDict();//加载下拉框
		loadHead();
	});
	//查询
	function query() {
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead(){
		
		grid = $("#maingrid").ligerGrid({
    		columns: [
    			{display: '指标维度', name: 'target_type_name', align: 'left', width: '20%'},
    			{display: '指标编码', name: 'target_code', align: 'left', width: '20%', hide:true},
    			{display: '指标名称', name: 'target_name', align: 'left', width: '20%'},
    			{display: '指标标度', name: 'targer_score', align: 'center', width: '20%',
    				render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("'
								+ rowdata.group_id
								+ ','
								+ rowdata.hos_id
								+ ','
								+ rowdata.copy_code
								+ ','
								+ rowdata.target_code
								+ '")>查看</a>';
					}	
    			}, 
    			{display: '分值', name: 'target_full_score', align: 'right', width: '20%', type: 'float',
    				editor: {
    					type: 'number',
    					precision: 2
    				},
					render: function (rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, '2', 1);
					},
					totalSummary: {
                        render: function (suminf, column, cell)
                        {
                            return '<div>分值合计 = ' + suminf.sum + '</div>';
                        }
                    }
    			},
    			{display: '权重', name: 'weight', align: 'right',width: '20%', type: 'float',
    				editor: {
    					type: 'number',
    					precision: 2
    				},
					render: function (rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, '2', 1);
					},
					totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '<div>权重合计 = ' + formatNumber(suminf.sum, '2', 1) + '</div>';
                        }
                    }
    			}
    		],
    		dataAction: 'server',dataType: 'server',usePager:false,url:'queryMatEvaSchemeDetail.do?isCheck=true',
    		width: '100%', height: '100%', checkbox: false, rownumbers:true,
    		enabledEdit: true, delayLoad : false,//初始化不加载，默认false
    		selectRowButtonOnly:true,//heightDiff: -10,
    		isAddRow:false, onAfterEdit: f_onAfterEdit, 
    		toolbar: { items: [
    			{ text: '查询', id:'search', click: query, icon:'search' },
    			{ line:true },
    			{ text: '保存', id:'save', click: save, icon:'save' },
    			{ line:true },
    			{ text: '引入指标', id:'add', click: add, icon:'add' }
    		]}
    	});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//验证
    function validateGrid() { 
		
    	//主表
 		if(!$("#full_score").val() || $("#full_score").val() == 0){
 			$.ligerDialog.warn("请先维护满分标准并保存\n 满分标准不能为空且需大于0");  
 			return false;  
 		}
		
 		var full_score = $("#full_score").val();
    	//明细
    	var weightSum = 0;
    	var scoreSum = 0;
  		var msg="";
  		var rows = 0;
  		var data = gridManager.getData();
  		//判断grid 中的数据是否重复或者为空
  		var targetMap = new HashMap();
  		$.each(data,function(i, v){
  			if(v.target_code){
	  			rows += 1;
	  			if(v.target_full_score == 0 && v.weight == 0){
	  				return;
	  			}
	  			if(v.target_full_score/v.weight != full_score){
	  				msg += "["+v.target_name+"]" + "\n\r";
	  			}
	  			weightSum += v.weight;
	  			scoreSum += v.target_full_score;
  			}
  		});
  		if(msg != ""){
  			msg += "请重新维护上述指标！规则：<font color='green'>分值=权重*满分</font>";
  		}else{
	  		if(weightSum != 1 || scoreSum != full_score){
	  			msg += "规则: <font color='green'>所有指标分值 = 满分值  且  所有指标权重 = 1！</font>不满足上述规则";
	  		}
  		}
  		if(msg != ""){
 			$.ligerDialog.warn(msg);  
			return false;  
 		} 	
  		return true;	
	}

	//保存
    function  save(){
		if(validateGrid()){
			var detailData = gridManager.getData();

            var formPara={
           		scheme_code : 'sys01',
           		scheme_name : '默认供应商评价方案',
           		scheme_desc : '',
            	full_score : $("#full_score").val(),
            	is_stop : 0,
            	is_defaut : 1,
            	note : '',
       			detailData : JSON.stringify(detailData)
   			};
           
			ajaxJsonObjectByUrl("saveMatEvaScheme.do?isCheck=true", formPara,
					function(responseData){
               			if(responseData.state=="true"){
	               	 		query();
               			}
					}	
            );
   	 	} 
   	}

	function add(){
		
		//主表
 		if(!$("#full_score").val() || $("#full_score").val() == 0){
 			$.ligerDialog.warn("请先维护满分标准并保存\n 满分标准不能为空且需大于0");  
 			return false;  
 		}
		
		ajaxJsonObjectByUrl("addMatEvaSchemeDetail.do?isCheck=true", "",
			function(responseData){
           		if(responseData.state=="true"){
					query();
           		}
			}	
        );
	}
	
	function loadDict() {
		$("#full_score").ligerTextBox({width:160});
		
		$("#save").ligerButton({ click: saveMain, width: 60 });
	}
	
	function saveMain(){
    	//主表
 		if(!$("#full_score").val() || $("#full_score").val() == 0){
 			$.ligerDialog.warn("满分标准不能为空且需大于0");  
 			return false;  
 		}
    	
 		var formPara={
           	scheme_code : 'sys01',
           	scheme_name : '默认供应商评价方案',
           	scheme_desc : '',
            full_score : $("#full_score").val(),
            is_stop : 0,
            is_defaut : 1,
            note : ''
   		};
           
		ajaxJsonObjectByUrl("saveMatEvaScheme.do?isCheck=true", formPara,
			function(responseData){
               	if(responseData.state=="true"){
	               	 query();
               	}
			}	
        );
	}
	
	function update_open(obj) {
		var vo = obj.split(",");
		var paras = "target_code=" + vo[3] ;
		
		parent.$.ligerDialog.open({
			title : '指标标度查看',
			height : $(window).height() - 50,
			width :  $(window).width() - 100,
			url : 'hrp/mat/eva/scheme/matEvaTargetScaleBatchPage.do?isCheck=false&'
					+ paras.toString(),
			modal:true,showToggle:false,showMax:false,
			isResize:true, showMin: true,showMax: false, //开启最大化最小化按钮
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function f_onAfterEdit(rowData){
		
		var full_score = $("#full_score").val();
		if(rowData.column.columnname == 'target_full_score'){
			grid.updateCell('weight', parseFloat(rowData.record.target_full_score/full_score), rowData.record);
		}else if(rowData.column.columnname == 'weight'){
			grid.updateCell('target_full_score', parseFloat(rowData.record.weight*full_score), rowData.record);
		}
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="30%">
		<tr>
			<td align="right" class="l-table-edit-td" ><font color="red">*</font>满分标准：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="full_score" type="text" id="full_score" value="${full_score}" oninput="value=value.replace(/[^\d]/g,'')"/>
			</td>
			<td align="right" class="l-table-edit-td" >
				<button id="save" >保存</button>
			</td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
