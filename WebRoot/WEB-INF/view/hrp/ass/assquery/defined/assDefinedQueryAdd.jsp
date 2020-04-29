<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	
	$(function() {
		loadForm();
		loadHead(null);	//加载数据
		
	});
	
	function loadHead(){
		var field_type = [
			{ id: 'text', text: '文本' },
			{ id: 'number', text: '数值' },
			{ id: 'date', text: '日期'}
		];
		var where_operator = [
        {id:"equal", text:"相等"},
        /* {id:"notequal", text:"不相等"},
        {id:"startwith", text:"以..开始"},
        {id:"endwith", text:"以..结束"}, */
        {id:"like", text:"模糊"},
        /* {id:"greater", text:"大于"},
        {id:"greaterorequal", text:"大于或等于"},
        {id:"less", text:"小于"},
        {id:"lessorequal", text:"小于或等于"},
        {id:"in", text:"包括在..."},
        {id:"notin", text:"不包括..."} */
        ];
        var where_and_or= [
        {id:"and", text:"并且"},
        {id:"or", text:"或者"}
        ];
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	        	     { display: '字段名称', name: 'field_name', align: 'left',width: '120',
	        	    	 editor: { type: 'text' }
	                 },
                     { display: '字段文本', name: 'field_text', align: 'left',width: '120',
	                	 editor: { type: 'text' }
                     },
                     { display: '类型', name: 'field_type', align: 'left',width: '120',
                    	 editor: { type: 'select', data: field_type, valueField: 'id',textField:'text' },
                    	 render: function(rowdata, rowindex,value){
                    		 if (value == 'text') return '文本';
                    		 if (value == 'number') return '数值';
                    		 if (value == 'date') return '日期';
                    	 }
					 },
					 { display: '是否显示', name: 'is_show', align: 'left',width: '120',
						 editor: { type: 'select', data: [{ id: 1, text: '显示' }, { id: 2, text: '隐藏'}], valueField: 'id',textField:'text' },
						 render: function(rowdata, rowindex,value){
                    		 if (parseInt(value) == 1) return '显示';
                             return '隐藏';
                    	 }
					 },
					 { display: '查询方式', name: 's_mode', align: 'left',width: '120',
						 editor: { type: 'select', data: [{ id: 1, text: '单条件' }/* , { id: 2, text: '范围'} */], valueField: 'id',textField:'text' },
						 render: function(rowdata, rowindex,value){
                    		 if (parseInt(value) == 1) return '单条件';
                             //return '范围';
                    	 }
					 },
					 { display: '条件运算符', name: 'where_operator', align: 'left',width: '120',
						 editor: { type: 'select', data: where_operator, valueField: 'id',textField:'text' },
						 render: function(rowdata, rowindex,value){
                    		 if (value == 'like') return '模糊';
                             return '相等';
                    	 }
					 },
					 /* { display: '列关系', name: 'where_and_or', align: 'left',width: '120',
						 editor: { type: 'select', data: where_and_or, valueField: 'id',textField:'text' },
					 }, 
					 { display: '取值', name: 'replace_va', align: 'left',width: '120',
						 editor: { type: 'text' }
					 },*/
					 { display: '是否查询', name: 's_flag', align: 'left',width: '120',
						 editor: { type: 'select', data: [{ id: 1, text: '是' }, { id: 2, text: '否'}], valueField: 'id',textField:'text' },
						 render: function(rowdata, rowindex,value){
                    		 if (parseInt(value) == 1) return '是';
                             return '否';
                    	 }
					 },
					 { display: '排序', name: 'order_num', align: 'left',width: '120',
						 editor: { type: 'number' }
					 },
                     ],
                     //dataAction: 'server',dataType: 'server',usePager:false,url:'.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,enabledEdit: true,//heightDiff: -10,
                     toolbar: { 
                    	 items: [
                    		{ text : 'SQL解析',	id : 'getfields',click : getfields,icon : 'add'}, 
                      		{line : true},
                     		{ text : '添加行',	id : 'add',click : itemclick,icon : 'add'}, 
                     		{line : true},
                     		{ text : '删除所选行',	id : 'delete',click : itemclick,icon : 'delete'}, 
                     		{line : true}
                         ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
	//获取字段
	function getfields(){
		var sqlStr = $('#r_sql').val();
		ajaxJsonObjectByUrl("getFields.do?isCheck=false",{sqlStr : sqlStr}, function(responseData) {
			if(!isnull(grid) && !isnull(grid.rows)){
				//console.log(grid.rows);
				grid.removeRange(grid.rows);
			}
			grid.addRows(responseData.fields);
		}); 
	}
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				gridManager.addEditRow();
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					gridManager.deleteSelectedRow();
				}
				return;
			}
		}

	}
	
	function saveAssDefinedQuery(){
		gridManager.endEdit();
		if($("form").valid()){
			
			var data = gridManager.getData();
			
			var formPara = {
				rhead_name : $("#rhead_name").val(),
				rhead_code : $("#rhead_code").val(),
				r_sql : $("#r_sql").val(),
				note : $("#note").val(),
				item : JSON.stringify(data)
			};
			
			//console.log(formPara)
			
			if (validateGrid(data)) {
				ajaxJsonObjectByUrl("addAssDefinedQuery.do?isCheck=false", formPara, function(
						responseData) {
					if (responseData.state == "true") {
						parent.query();
						//parent.$.ligerDialog.close();
					}
				});
			}
		}
	}
	function loadForm() {
		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			//debug: true,
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		//$("form").ligerForm();
	}
	
	function validateGrid(data) {
        if (data.length === 0) {
            $.ligerDialog.warn("请添加明细数据！");
            return false;
        }

        //明细
       /*  var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();

        $.each(data, function(i, v) {
            var rowm = "";
            if (v) {
            	var key = v.field_name;
				var value = "第" + (i + 1) + "行";
				if (isnull(v.field_name)) {
					msg += "[字段名称]、";
				}
				if (isnull(v.field_text)) {
					msg += "[字段文本]";
				}
				
				if (msg != "") {
					msgMap.put(value + msg + "不能为空！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",value);
				}
            }
        });
        if (msgMap) {
        	$.ligerDialog.warn(msgMap.keySet());
            return false;
        } */
        
        return true;
    }
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
	    	<tbody>
	    		<tr>
	                <td align="right" class="l-table-edit-td">报表名称:</td>
	                <td align="left" class="l-table-edit-td">
	                	<div class="l-text" style="width: 178px;"><input name="rhead_name" type="text" id="rhead_name" ltype="text" validate="{required:true}" class="l-text-field" ligeruiid="rhead_name" style="width: 174px;"><div class="l-text-l"></div><div class="l-text-r"></div></div>
	                </td>
	                <td align="right" class="l-table-edit-td">报表编码:</td>
	                <td align="left" class="l-table-edit-td">
	                	<div class="l-text" style="width: 178px;"><input name="rhead_code" type="text" id="rhead_code" ltype="text" validate="{required:true}" class="l-text-field" ligeruiid="rhead_code" style="width: 174px;"><div class="l-text-l"></div><div class="l-text-r"></div></div>
	                </td>
	            </tr>
	            <tr>
	                <td align="right" class="l-table-edit-td">说明:</td>
	                <td align="left" class="l-table-edit-td" colspan="3">
	                	<div class="l-text" style="width: 600px;"><input name="note" value="${note}" type="text" id="note" ltype="text" class="l-text-field" ligeruiid="note" style="width: 600px;"><div class="l-text-l"></div><div class="l-text-r"></div></div>
	                </td>
	            </tr>
	            <tr>
	            	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		            	<tr>
			                <td align="right" class="l-table-edit-td">查询SQL:</td>
			                <td align="left" class="l-table-edit-td"> 
			                	<textarea cols="100" rows="4" class="l-textarea" id="r_sql" style="width:600px" validate="{required:true}"></textarea>
			                </td>
			                <!-- <td align="left"><input type="button" value="SQL解析" id="getfields" class="l-button"></td> -->
		                </tr>
	                </table>
	            </tr>
	        </tbody>
	    </table>
	</form>
	<div id="maingrid"></div>
</body>
</html>
