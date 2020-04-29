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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var ass_card_no = '${ass_card_no}';
    
    $(function ()
    {
    	$("#ass_year").ligerTextBox({
			width : 160
		});
		$("#ass_month").ligerTextBox({
			width : 160
		});
		//默认年
		$("#ass_year").val("${ass_year}");
		//默认月
		$("#ass_month").val("${ass_month}");
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        query();
    });
    
    //查询
    function  query(){
    		if($("#ass_year").val() == ""){
    			parent.$.ligerDialog.warn("请选择统计年度");
    			return;
    		}
    		if($("#ass_month").val() == ""){
    			parent.$.ligerDialog.warn("请选择统计月份");
    			return;
    		}
    	
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		grid.options.parms.push({
    			name : 'ass_nature',
    			value : '${ass_nature}'
    		});
    		grid.options.parms.push({
    			name : 'ass_card_no',	
    			value : ass_card_no
    		});
    		grid.options.parms.push({
    			name : 'ass_year',
    			value : $("#ass_year").val()
    		});
    		grid.options.parms.push({
    			name : 'ass_month',	
    			value : $("#ass_month").val()
    		});
    		grid.options.parms.push({
    			name : 'dept_id',
    			value : liger.get("dept_id").getValue().split("@")[0] == "true"?"":liger.get("dept_id").getValue().split("@")[0]
    		});
    		grid.options.parms.push({
    			name : 'dept_no',	
    			value : liger.get("dept_id").getValue().split("@")[1]
    		});
    		
    	//加载查询条件
    	grid.loadData(grid.where);
    	is_addRow();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科室', name: 'dept_id', align: 'left',textField : 'dept_name',
							editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								url : '../queryDeptDict.do?isCheck=false',
								keySupport : true,
								autocomplete : true,
								onSuccess : function(data) {
									if (initvalue != undefined
											&& initvalue != "") {
										this.setValue(initvalue);
										initvalue = "";
									}
								}
							}
					 		},
                     { display: '分摊当量', name: 'area', align: 'left',
								editor : {
									type : 'float',
									precision : 6
								}
					 		},
                     { display: '备注', name: 'note', align: 'left',
								editor : {
									type : 'text'
								}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAssShareDeptR.do',
                     width: '100%', height: '95%', checkbox: true,rownumbers:true,delayLoad : true,enabledEdit : true,
                     selectRowButtonOnly:true,//heightDiff: -10,,
                     scroll: true,
                     frozen:true,
                     alternatingRow: false,
                     scrollToPage: true,
                     scrollToAppend: true,
                     toolbar: { items: [
			                    	 	{ text: '查询', id:'search', click: query, icon:'search' },
				    	                { line:true },
				    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
				    	                { line:true },
				    	                { text: '保存', id:'save', click: save, icon:'save' }, {
											line : true
										}, {
											text : '关闭',
											id : 'close',
											click : this_close,
											icon : 'candle'
										}
				    	]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function this_close() {
		frameElement.dialog.close();
	}
    
    
    function save(){
    	gridManager.endEdit();
    	if($("#ass_year").val() == ""){
    		parent.$.ligerDialog.warn("请选择统计年度");
			return;
		}
		if($("#ass_month").val() == ""){
			parent.$.ligerDialog.warn("请选择统计月份");
			return;
		}
    	var data = grid.getData();
    	if(ass_card_no == '0'){
    		parent.$.ligerDialog.warn("请先保存卡片");
    		return;
    	}
    	
		var formPara = {
				ass_nature:'${ass_nature}',
			ass_card_no : ass_card_no,
			ass_year :$("#ass_year").val(),
			ass_month:$("#ass_month").val(),
			ParamVo : JSON.stringify(data)

		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssCardShareDeptR.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					query();
				}
			}, false);
		}
    	}
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	parent.$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){	
                        	if (isnull(this.group_id)) {
								gridManager.deleteSelectedRow();
							} else {
								ParamVo.push(
								this.group_id   +"@"+ 
								this.hos_id   +"@"+ 
								this.copy_code   +"@"+ 
								this.ass_card_no   +"@"+ 
								this.dept_id.split("@")[0]   +"@"+ 
								this.dept_no   +"@"+
								this.ass_year +"@"+
								this.ass_month
								);
							}
							});
                        if(ParamVo == ""){
                			return;
                		}
                        parent.$.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssShareDeptR.do",{ParamVo : ParamVo.toString(),ass_nature:'${ass_nature}'},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    
    function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data, function(i, v) {
			if (!isnull(v.dept_id)) {
				var key = v.dept_id;
				var value = "第" + (i + 1) + "行";

				if (v.dept_id == '@' || isnull(v.dept_id)) {
					msg += "[科室]、";
				}
				
				if(isnull(v.area) || v.area == 0 || v.area == 0.0){
					msg += "[分摊当量]";
				}

				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = targetMap.get(key) + "与" + value + "重复！\n\r",
					value;
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});

		if (msg != "") {
			parent.$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
			parent.$.ligerDialog.warn("无数据保存");
			return false;
		}
		return true;
	}
    
    function loadDict(){
            //字典下拉框
    	autocomplete("#dept_id",
				"../queryDeptDict.do?isCheck=false", "id",
				"text", true, true, null, null,true,"160");
         }  
    function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">统计年度：</td>
			<td align="left" class="l-table-edit-td"><input
				name="ass_year" type="text" id="ass_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">统计月份：</td>
			<td align="left" class="l-table-edit-td"><input
				name="ass_month" type="text" id="ass_month" class="Wdate"
				onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'MM'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
			<td align="left" class="l-table-edit-td"><input
				name="dept_id" type="text" id="dept_id"  /></td>
		</tr>
	</table>
	<div id="maingrid"></div>
	
</body>
</html>
