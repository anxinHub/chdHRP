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
    var price = '${price}';
    var proj = '${proj_id}'; 
    $(function ()
    {
    	loadHead(null);	
        query();
    });
    
    
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    
                     { display: '资金来源', name: 'source_name', align: 'left'
					 },
                     { display: '金额(原值)', name: 'point', align: 'right',
								editor : {
									type : 'float'
								},
								render : function(item) {
									if(item.point){
									return formatNumber(
											item.point, '${ass_05006}', 1);
									}
								}
					 }
                     
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../../asssource/queryAssSource.do?isCheck=false',
                     width: '100%', height: '90%',enabledEdit : true,delayLoad : true,checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     scroll: true,
                     frozen:true,
                     alternatingRow: false,
                     scrollToPage: true,
                     scrollToAppend: true,
                     onAfterEdit : f_onAfterEdit
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function f_onAfterEdit(e) {
		grid.updateTotalSummary();
		return true;
	}
    
    function validateGrid() {
    	gridManager.endEdit();
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		var points = 0;
		var vProj = [];
		//删除空行
		$.each(data, function(i, v) {
			if (!isnull(v.source_id)) {
				var key = v.source_id;
				var value = "第" + (i + 1) + "行";

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
				
				if(v.point != null && v.point != ""){
					points = points + v.point;
					if(v.source_id != 1){
						vProj.push("false");
					}else{
						vProj.push("true");
					}
				}
				
			}
		});
		if(points != price){
			parent.$.ligerDialog.warn("总金额必须和资产单价或入库金额相等！");
			return false;
		}
		
		for(var i = 0; i < vProj.length; i++){
			if(vProj[i] == "false"){
				if(proj == "" || proj == null){
					parent.$.ligerDialog.warn("项目不能为空！");
					return false;
				}
			}
		}

		if (msg != "") {
			parent.$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
			parent.$.ligerDialog.warn("无数据保存");
			return false;
		}
		return data;
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

	<div id="maingrid"></div>
	
</body>
</html>
