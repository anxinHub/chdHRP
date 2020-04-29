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
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;

	var gridManager = null;

	var userUpdateStr;

	$(function() {
		loadDict();

		loadHead(null); //加载数据
		
		loadHotkeys();

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		var year_month1= liger.get("acc_year_month1").getValue();
        
        var vouch_type_code = liger.get("vouch_type_code").getValue();
        
        if(year_month1==""){
        	
        	$.ligerDialog.error('会计期间为必填项，不能为空！');
        	
        	return;
        }
        
		 if(vouch_type_code==""){
        	
        	$.ligerDialog.error('凭证类别为必填项，不能为空！');
        	
        	return;
        	
        } 
		
    	grid.options.parms.push({name:'acc_year',value:year_month1.split(".")[0]}); 
    	
        grid.options.parms.push({name:'acc_month',value:year_month1.split(".")[1]}); 
        
        grid.options.parms.push({name:'vouch_type_code',value:vouch_type_code});
    	
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '凭证日期',
				name : 'vouch_date',formatter: "yyyy-MM-dd",
				align : 'left',width:80
			}, {
				display : '凭证编号',
				name : 'vouch_no',width:80,
				align : 'left',render:function(rowdata){
					return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
				}
			}, {
				display : '说明',
				name : 'summary',
				align : 'left',width:300
			}, {
				display : '附件',
				name : 'vouch_att_num',
				align : 'left',width:60
			}, {
				display : '制单人',
				name : 'create_name',
				align : 'left'
			}, {
				display : '出纳签字人',
				name : 'cash_name',
				align : 'left'
			}, {
				display : '审核人',
				name : 'audit_name',
				align : 'left'
			}, {
				display : '记账人',
				name : 'acc_name',
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAccVouchNeaten.do',
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			delayLoad:true,
			selectRowButtonOnly : true/* ,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				} ]
			},
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.vouch_id);
			} */
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function zhengli(){
		var year_month= liger.get("acc_year_month1").getValue();
        
        var vouch_type_code = liger.get("vouch_type_code").getValue();
        
        if(year_month==""){
        	
        	$.ligerDialog.error('会计期间为必填项，不能为空！');
        	
        	return;
        	
        }
        
		if(vouch_type_code==""){
        	
        	$.ligerDialog.error('凭证类别为必填项，不能为空！');
        	
        	return;
        	
        } 
		
		var data = gridManager.getData();
		
		if (data.length == 0) {
			
			$.ligerDialog.error('没有断号凭证！');
			
			return;
			
		} else {
			
			var ParamVo = [];
			
			$(data).each(function() {
				ParamVo.push(

				year_month+"@"+vouch_type_code+"@"+this.group_id+"@"+this.hos_id
				+"@"+this.copy_code+"@"+this.vouch_no.split("-")[1]);
				
			});
			
			$.ligerDialog.confirm('确定处理断号凭证?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateVouchNeaten.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	
	function itemclick(item) {
		
		if (item.id) {
			
			switch (item.id) {
			
			case "charge_duanhao":
				
				var year_month= liger.get("acc_year_month1").getValue();
		        
		        var vouch_type_code = liger.get("vouch_type_code").getValue();
		        
		        if(year_month==""){
		        	
		        	$.ligerDialog.error('会计期间为必填项，不能为空！');
		        	
		        	return;
		        	
		        }
		        
				if(vouch_type_code==""){
		        	
		        	$.ligerDialog.error('凭证类别为必填项，不能为空！');
		        	
		        	return;
		        	
		        } 
				
				var data = gridManager.getData();
				
				if (data.length == 0) {
					
					$.ligerDialog.error('没有断号凭证！');
					
					return;
					
				} else {
					
					var ParamVo = [];
					
					$(data).each(function() {
						ParamVo.push(

						year_month+"@"+vouch_type_code+"@"+this.group_id+"@"+this.hos_id
						+"@"+this.copy_code+"@"+this.vouch_no.split("-")[1]);
						
					});
					
					$.ligerDialog.confirm('确定处理断号凭证?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("updateVouchNeaten.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				
				return;
			case "modify":
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						ParamVo.push(
						//表的主键
						this.vouch_id)
					});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccVouch.do", {
								ParamVo : ParamVo
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			case "Excel":
			case "Word":
			case "PDF":
			case "TXT":
			case "XML":
				$.ligerDialog.waitting('导出中，请稍候...');
				setTimeout(function() {
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
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
/* 
		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('K', printBarcode); */

	}
	
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
	function loadDict() {
		//字典下拉框
		
		autocomplete("#vouch_type_code", "../../queryVouchType.do?isCheck=false",
				"id", "text", true, true,'',true,'',100);
		
		$("#acc_year_month1").ligerComboBox({
           	url: '../../queryYearMonth.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 100,
           	autocomplete: true,
           	width: 100/* ,
           	onSuccess:function(data){
           		for(var i in data){
           			if(data[i].text =='${yearMonth}'){
           				liger.get("acc_year_month1").setValue(data[i].id.split(".")[0]+"."+data[i].id.split(".")[1]);
           				liger.get("acc_year_month1").setText(data[i].text);
           			}
           		}
           	} */
  		  });
		
		 var year_Month = '${yearMonth}';

    	 liger.get("acc_year_month1").setValue(year_Month);
		 
    	 liger.get("acc_year_month1").setText(year_Month);
    	 
	}

	/**
	 * 打印 打印 预览 设置
	 */
	var menu_print = {
		width : 120,
		items : [ {
			text : '打印',
			id : 'print',
			click : itemclick
		}, {
			text : '预览',
			id : 'view',
			click : itemclick
		}, {
			text : '设置',
			id : 'set',
			click : itemclick
		} ]
	};
	
	function printDate(){
		if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
		var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	  	          {"cell":0,"value":"会计期间："+$("#acc_year_month1").val(),"colSpan":"5"},
	  	          {"cell":3,"value":"凭证类型："+liger.get("vouch_type_code").getText(),"from":"right","align":"right","colSpan":"4"}
	      		  ]
	      	};
	   		
	   		var printPara={
	   			rowCount:1,
	   			title:'凭证整理',
	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
	   			class_name: "com.chd.hrp.acc.service.vouch.AccVouchService",
				method_name: "queryAccVouchNeatenPrint",
				bean_name: "accVouchService",
				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	   			};
	    	
	   		//执行方法的查询条件
	   		$.each(grid.options.parms,function(i,obj){
	   			printPara[obj.name]=obj.value;
	    	});
	   		
	    	officeGridPrint(printPara);
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		 <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>会计期间：</td>
            <td align="right" class="l-table-edit-td" ><input name="acc_year_month1" type="text" id="acc_year_month1"   /></td>
            <td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>凭证类型：</td>
			<td align="left" class="l-table-edit-td"><input
				name="vouch_type_code" type="text" id="vouch_type_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
			<input class="l-button"  style="width: 90px;" type="button" value="检查断号(Q)" onclick="query();"/>
			<input class="l-button"  style="width: 90px;" type="button" value="整理断号(Z)" onclick="zhengli();"/>
			<input class="l-button"  style="width: 50px;" type="button" value="打 印" onclick="printDate();"/>
			</td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
