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
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		query();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{display: '排序号', name: 'sort_code',id:'sort_code', align: 'left',hide:true},
									{display: 'HIS视图编码', name: 'his_log_code',id:'his_log_name', align: 'left',width: 200},
  									{display: 'HIS视图名称', name: 'his_log_name', align: 'left',width: 150 },
  									{ display: '是否启用', name: 'is_stop', align: 'center',width: 100,render:checkboxRender}, 
  									{ display: '默认月数', name: 'g_day', align: 'left',type: 'text',editor : {type : 'text'},width: 100, 
  										render : function(rowdata, rowindex,value) {
  											if(rowdata.g_day==null){
  												return "1";
  											}else{
  												return rowdata.g_day;
  											}
  										  }
										},
  									
  									{ display: '操作', name: 'set_date', align: 'center',width: 130, 
  										render : function(rowdata, rowindex,value) {
  		  									return "<div class='l-button' style='width: 80px;margin-top:1px;margin-left: 22px;' ligeruiid='Button1004' onclick=onSave('"+rowindex+"','"+rowdata.his_log_code+"');>"
  		  				       					+"<span>设置取数时间</span></div>";
  		  									}
  								 	} 
							           ],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : 'queryCostIncomeHisViewSetting.do?isCheck=false',
							width : '100%',
							height : '95%',
							checkbox : false,
							rownumbers : false,
							enabledEdit : true,
							isAddRow:false,
							alternatingRow: true,
							delayLoad : true,//初始化不加载，默认false
							selectRowButtonOnly : true//heightDiff: -10,
							
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('C', this_close);
	}
	
	function checkboxRender(rowdata, rowindex, value, column){
			 var iconHtml = '<div class="chk-icon';
		        if (value==0) iconHtml += " chk-icon-selected";
		        iconHtml += '"';
		        iconHtml += ' rowid = "' + rowdata['__id'] + '"';
		        iconHtml += ' gridid = "' + this.id + '"';
		        iconHtml += ' columnname = "' + column.name + '"';
		        iconHtml += '></div>';
		        return iconHtml;
    }
	
	  //是否启用的模拟复选框的点击事件
    $(document).on('click','.chk-icon', function (){
    	
        var gridChk = $.ligerui.get($(this).attr("gridid"));
        
        var rowdata = gridChk.getRow($(this).attr("rowid"));
        
        var columnname = $(this).attr("columnname");
        
        var checked = rowdata[columnname];

        grid.updateCell(columnname, !checked, rowdata);
        
        
    });


	function save() {
		var data = gridManager.getData();
		
		var isDataCheck=true;
		$.each(data,function(i,obj){
			if(parseInt(obj.g_day)<0){
				$.ligerDialog.error("【"+obj.his_log_name+"】的默认天数，不能小于0！");
				isDataCheck=false;
				return true;
			}
		});
		if(!isDataCheck){
			return;
		}
	
		var formPara = {
			detailData : JSON.stringify(data)
		};
		
		
		$.ligerDialog.confirm('确定保存?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("updateOrAddCostIncomeHisViewSetting.do?isCheck=false",
						formPara, function(responseData) {
					      query();
				});
			}
		});
		
	}
	
	function onSave(rowindex,his_log_code){
	  	var formPara = "his_log_code="+his_log_code;
	  	 $.ligerDialog.open({ 
			url : 'costGetDateCostIncomeHisViewPage.do?isCheck=false&' + formPara,data:{}, 
	  		height: 300,width: 400, title:'取数时间', top: 100, 
	  		modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [
				{ text: '保存', onclick: function (item, dialog) {dialog.frame.saveCostAutoHisDate(); },cls:'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			]
		})
	
    }


	function this_close() {
		frameElement.dialog.close();
	}

	function loadDict() {

		
		//格式化按钮
		$("#save").ligerButton({
			click : save,
			width : 90
		});
		$("#close").ligerButton({
			click : this_close,
			width : 90
		}); 
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div style="width: 100%; height: 100%;">
		<div style="font-size:14px">说明：1)、此页面的操作配合ETL使用，请参考ETL使用手册。<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2)、是否启用：如果不启用ETL将不会添加取数时间；默认月数：只能>=0，1代表ETL默认取上个月的数据，以此类推。<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3)、设置取数时间：ETL下次执行时，读取数据会包含里面设置的时间。
		</div>
		<br/>
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit"
			width="100%" style="margin-top: 5px;">
			<tr>
				<td align="center" class="l-table-edit-td">
					
					<button id="save" accessKey="B">
						<b>保存（<u>B</u>）
						</b>
					</button> &nbsp;&nbsp;
					<button id="close" accessKey="C">
						<b>取消（<u>C</u>）
						</b>
					</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
