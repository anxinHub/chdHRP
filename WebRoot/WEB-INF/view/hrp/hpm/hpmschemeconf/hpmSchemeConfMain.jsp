<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	var grid;
	var gridManager = null;
	
	
	//页面初始化
	$(function() {
		loadHead(null);//加载数据
		loadDict();//加载字典
		
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键
	});
	
	
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		grid.options.parms.push({name : 'year_month',value : $("#year_month").val()}); 
		grid.options.parms.push({name : 'scheme_seq_no',value :  liger.get("scheme_seq_no").getValue() });
		
		grid.loadData(grid.where);
	}
	
	//加载grid
	function loadHead() {
		grid = $("#grid").ligerGrid({
			columns : [ 
				{display : '核算年度',name : 'acct_year',align : 'left',width:'10%',render: 
					function (rowdata, rowindex, value){
						return '<a href=\'#\' onclick=openUpdate(\''
								+rowdata.acct_year+'\',\''
								+rowdata.acct_month+'\');>'
								+rowdata.acct_year+'</a>';
					}
				}, 
				
				{display : '核算月份',name : 'acct_month',align : 'left',width:'10%'}, 
				{display : '方案序号',name : 'scheme_seq_no',align : 'left',width:'10%'},
				{display : '方案说明',name : 'scheme_note',align : 'left',width:'70%'} 
			],
			dataAction : 'server',dataType : 'server',usePager : true,
			url : 'queryHpmSchemeConf.do',//delayLoad:true,
			width : '100%', height: '100%',checkbox : true,rownumbers : true,selectRowButtonOnly : true,
			onDblClickRow : 
				function(rowdata, rowindex, value) {
					openUpdate(rowdata.acct_year,rowdata.acct_month);
				}
		});
		gridManager = $("#grid").ligerGetGridManager();
	}
	
	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addSchemeConf, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteSchemeConf,icon:'delete' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	//键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('D',deleteSchemeConf);
		hotkeys('A',addIncomeItem);
	}
	
	//添加
	function addSchemeConf(){
  		$.ligerDialog.open({
  			url: 'addHpmSchemeConfPage.do?isCheck=false', 
  			title:'添加',height: 400,width: 600,modal:true,showToggle:false,
  			showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.saveSchemeApply(); 
  					},cls:'l-dialog-btn-highlight' 
  				}, 
  				{ text: '取消', onclick: 
  					function (item, dialog) { 
  						dialog.close(); 
  					} 
  				} 
  			] 
  		});
	}
	
	//生成
	function createSchemeConf(){
		
		var year_month = $("#year_month").val();
		var scheme_seq_no = liger.get("scheme_seq_no").getValue();
		
		if(year_month == ""){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		if(scheme_seq_no == ""){
			$.ligerDialog.warn('请选择方案序号！');
			return false;
		}
		
		$.ligerDialog.open({
			url: 'createHpmSchemeConf.do?isCheck=false&year_month='+year_month+'&scheme_seq_no='+scheme_seq_no, 
			height: 200,width: 450, title:'选择生成',modal:true,showToggle:false,
			showMax:false,showMin: true,isResize:true,
			buttons: [ 
				{ text: '确定', onclick: 
					function (item, dialog) { 
						dialog.frame.saveSchemeApply(); 
					},cls:'l-dialog-btn-highlight' 
				}, 
				{ text: '取消', onclick: 
					function (item, dialog) { 
						dialog.close(); 
					} 
				} 
			] 
		});
	}
	
	//删除
	function deleteSchemeConf(){
		
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
        	
        var checkIds =[];
        
        $(data).each(function (){checkIds.push(this.acct_year+";"+this.acct_month);});
        
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deleteHpmSchemeConf.do",{checkIds:checkIds.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
	}
	
	//修改页跳转
	function openUpdate(obj1,obj2){
		$.ligerDialog.open({ 
			url: 'updateHpmSchemeConfPage.do?isCheck=false&acct_year='+obj1+'&acct_month='+obj2,data:{}, 
			height: 400,width: 600, title:'修改',modal:true,showToggle:false,
			showMax:false,showMin: false,isResize:true,
			buttons: [ 
				{ text: '确定', onclick: 
					function (item, dialog) { 
						dialog.frame.updateItem(); 
					},cls:'l-dialog-btn-highlight' 
				}, 
				{ text: '取消', onclick: 
					function (item, dialog) { 
						dialog.close(); 
					} 
				} 
			] 
		});

	}
	
	//字典下拉框
	function loadDict() {
		autocomplete("#scheme_seq_no","../querySchemeSeq.do?isCheck=false", "id", "text", true,true);
		$("#year_month").ligerTextBox({width:160 });//autodate("#year_month","yyyyMM");
	}
	
	
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="year_month" type="text" id="year_month" ltype="text"
					validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" />
			</td>
			
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案序号：</td>
			<td align="left" class="l-table-edit-td"><input name="scheme_seq_no" type="text" id="scheme_seq_no" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="grid"></div>
</body>
</html>