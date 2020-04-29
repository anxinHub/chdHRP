<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
	
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		grid.options.parms.push({name : 'store_id',value : liger.get("store_id").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'create_dateB',value : $("#create_dateB").val()}); 
    	grid.options.parms.push({name : 'create_dateE',value : $("#create_dateE").val() }); 
		
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                 { display: '仓库编码', name: 'store_code', align: 'left'},
						 { display: '仓库名称', name: 'store_name', align: 'left' },
						 { display: '库房分类', name: 'type_code', align: 'left'},
						 { display: '排序号', name: 'sort_code', align: 'left'},
						 { display: '备注', name: 'note', align: 'left' },
						 { display: '是否停用', name: 'is_stop', align: 'left',
							 render : function(rowdata, rowindex,value) {
									if(rowdata.is_stop == 0){
										return "否";
									}else{
										return "是"
									}
								}
						 },
						 { display: '操作人', name: 'user_name', align: 'left' },
						 { display: '变更时间', name: 'create_time', align: 'left' },
						 { display: '变更原因', name: 'dlog', align: 'left' }
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStoreDict.do?',
		                 width: '100%', height: '100%', checkbox: false,rownumbers:false,delayLoad:true,
		                 selectRowButtonOnly:true,heightDiff: -30,
		                 toolbar: { items: 
		                	 	[
		                          { text: '查询（<u>Q</u>）', id:'query', click: itemclick ,icon:'search' },
		                            { line:true }/* ,
		                            {text : '打印（<u>P</u>）',id : 'print',click : itemclick ,icon : 'print'}, 
		      						{line : true} */
		                   		]
		                 }
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "query":
                	
              		query();
              		return;
                case "modify":
                    return;
                case "delete":
                    
                    return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function ()
                    {
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.error('导出失败');
                    }, 1000);
                    return;
            }   
        }
        
    }
	
	function loadDict() {
    	$("#create_dateB").ligerTextBox({width:160});
    	$("#create_dateE").ligerTextBox({width:160});

		
		$("#store_id").ligerComboBox({
           	url: '../../../queryMatStore.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 160,
           	autocomplete: true,
           	initValue : 0,
           	width: 160
  		  });
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		var date = getCurrentDate();
        var aa = date.split(';');
        
		$("#create_dateB").val(aa[3]);
   		$("#create_dateE").val(aa[4]);
		
		
	}
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		
		hotkeys('P', printDate);
		hotkeys('I', imp);

	}
	//打印数据
	function printDate() {
		
	}
	function imp(){
		
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr></tr>
		<tr>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">变更日期：</td>
	            <td align="left" class="l-table-edit-td"  >
	            	<input class="Wdate" name="create_dateB" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="create_dateB"   />
	            </td>
	            <td align="center" class="l-table-edit-td" style="width:15px;">至：</td>
	            <td align="right" class="l-table-edit-td">
	            	<input class="Wdate" name="create_dateE" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="create_dateE"   />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">库房信息：</td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="store_id" type="text" requried="true"  id="store_id"  />
	            </td>
		</tr>
		
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
