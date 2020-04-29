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
	var toolBra;
	var parent_node_id;
	var state_name;
	var diffData = {
    		Rows : [{
 				"id" : "0",
 				"text" : "未标注" 
 			},{
 				"id" : "1",
 				"text" : "已标注" 
 			},{
 				"id" : "2",
 				"text" : "不需要标注" 
 			}],
 			Total : 3
    };
	
	$(function() {
		
		ajaxJsonObjectByUrl("../queryVouchState.do?isCheck=false&state="+2,{}, function(data) {
			
			if(data.Rows.length==0){
				toolBra={};
				$.ligerDialog.error('没有出纳签字的功能！');
				
			}else{
				parent_node_id=data.Rows[0].PARENT_NODE_ID;
				state_name = data.Rows[0].STATE_NAME;
				toolBra={ items: [
				              	{ text: '查询', id:'search', click: query, icon:'search' },
				            	{ line:true },
				            	{ text: '签字', id:'sign', click: itemclick, icon:'cashier' },
				                { line:true },
				                { text: '取消签字', id:'unsign', click: itemclick,icon:'uncashier' },
				    			{ line:true },
								{ text: '打印', id:'print', click: printDate,icon:'print'}
				            ]};
			}
			
			
		},false);
		
		if(${copy_nature} == "02"){
			copyNa = true;
		}else{
			copyNa = false;
		}
		
		loadDict();

		loadHead(null); //加载数据
		
		$("#vouch_no_b").ligerTextBox({width:160 });
		
		$("#vouch_no_e").ligerTextBox({width:160 });
		
		$("#summary").ligerTextBox({width:160 });
		
		$("#att_num").ligerTextBox({width:160 });
		
		$("#money").ligerTextBox({width:160 });
		
		$("#sumMoney").ligerTextBox({width:160 });
		
		$("#business_no").ligerTextBox({width:160 });
		
		$("#occur_date").ligerTextBox({width:160 });
		
		$("#money").ligerTextBox({width:160 });
		
		$("#sumMoney").ligerTextBox({width:160 });
		
		$("#summary").ligerTextBox({width:160 });

		/* $("#topmenu").ligerMenuBar({
			items : [ {
				text : '签字',
				id : 'sign',
				click : itemclick
			}, {
				text : '取消签字',
				id : 'unsign',
				click : itemclick
			} , {
				text : '打印',
				menu : menu_print
			}, {
				text : '导出',
				id : 'export',
				click : itemclick
			} ]
		}); */
		
		//设置凭证起止日期
		var acc_month=getMonthDate('${acc_year}','${acc_month}');
		$("#create_date_b").val(acc_month.split(";")[0]);
		$("#create_date_e").val(acc_month.split(";")[1]);
		
		$('input:radio[name="pay_type_code"]').bind("change",function(){
			hideSubj();
		})
		
		hideSubj();
		
		/* ajaxJsonObjectByUrl("../queryAccVouchFlowByNodeId.do?isCheck=false",{},function (responseData){
	    	
			if(responseData.result=="true"){
    			
				grid.toggleCol('cash_name', true);
				
    		}else{
    			
    			grid.toggleCol('cash_name', false);
    		}
    	}); */

	});
	
	 function isCheckDisplay(rowdata) {
			if (rowdata.summary == "合计")
				return false;

			return true;

		}
	
	function hideSubj(){
		
		var pay_type=$("input[name='pay_type_code']:checked").attr('value');
		
		if(pay_type=="1"){
			
			grid.toggleCol('subj_name', false);
			grid.toggleCol('budg_debit', true);
			grid.toggleCol('budg_credit', true);
			grid.changeHeaderText('budg', '财务会计')
		}else{
			
			grid.toggleCol('subj_name', true);
			grid.toggleCol('budg_debit', false);
			grid.toggleCol('budg_credit', false);
			grid.changeHeaderText('budg', '会计金额')
		}
		
		if(${copy_nature} == "02"){
			grid.toggleCol('budg_debit', false);
			grid.toggleCol('budg_credit', false);
		}
		
	}
	
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({name : 'changepage',value : 1});
		var pay_type=$("input[name='pay_type_code']:checked").attr('value');
 
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'create_date_b',
			value : $("#create_date_b").val()
		});
		grid.options.parms.push({
			name : 'create_date_e',
			value : $("#create_date_e").val()
		});
		grid.options.parms.push({
			name : 'vouch_type_code',
			value : liger.get("vouch_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue()
		});
		grid.options.parms.push({
			name : 'vouch_no_b',
			value : $("#vouch_no_b").val()
		});
		grid.options.parms.push({
			name : 'vouch_no_e',
			value : $("#vouch_no_e").val()
		});
		grid.options.parms.push({
			name : 'money',
			value : $("#money").val()
		});
		grid.options.parms.push({
			name : 'sumMoney',
			value : $("#sumMoney").val()
		});
		grid.options.parms.push({
			name : 'subj_code_b',
			value : liger.get("subj_code_b").getValue()
		});
		grid.options.parms.push({
			name : 'subj_code_e',
			value : liger.get("subj_code_e").getValue()
		});
		grid.options.parms.push({
			name : 'create_user',
			value : liger.get("create_user").getValue()
		});
		grid.options.parms.push({
			name : 'cash_user',
			value : liger.get("cash_user").getValue()
		});
		grid.options.parms.push({
			name : 'acc_user',
			value : liger.get("acc_user").getValue()
		});
		grid.options.parms.push({
			name : 'audit_user',
			value : liger.get("audit_user").getValue()
		});
		grid.options.parms.push({
			name : 'summary',
			value : $("#summary").val()
		});
		grid.options.parms.push({
			name : 'busi_type_code',
			value : liger.get("busi_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'pay_type_code',
			value : pay_type
		});
		grid.options.parms.push({
			name : 'note',
			value : $("#note").is(':checked')==false?0:1
		});
		grid.options.parms.push({
			name : 'sign_flag',
			value : liger.get("sign_flag").getValue()
		});
		
		if(pay_type=="1"){
			
			grid.toggleCol('subj_name', false);

		}else{
			
			grid.toggleCol('subj_name', true);
			
		}
		
		grid.options.parms.push({
			name : 'page_url',
			value : 'cash'
		});
		
		//parent_node_id关系到未签字、已签字状态的条件
		grid.options.parms.push({
			name : 'parent_node_id',
			value : parent_node_id
		});
		
		
		
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '凭证编号',
				name : 'vouch_no',width:80,
				align : 'left',render:function(rowdata){
			          if(rowdata.state==0){
			            return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"<span style='color:red;font-weight: bold;'> 废</span></div></a>";
			          }else if(rowdata.state==-1){
			            return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"<span style='color:red;font-weight: bold;'> 稿</span></div></a>";
			          }else{
			            return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
			          }
			        }
			}, {
				display : '凭证日期',
				name : 'vouch_date',
				align : 'left',width:80,formatter: "yyyy-MM-dd",
			}, {
				display : '科目',
				name : 'subj_name',
				align : 'left',width:200
			}, {
				display : '摘要',
				name : 'summary',
				align : 'left',width:200
			}, {
				display : '财务会计',
				align : 'center',
				name:'budg',
  				columns:[{
					display : '借方金额',
					name : 'debit',
					align : 'right',width:105,formatter: "###,##0.00",
					render : function(rowdata, rowindex, value) {
	  					return formatNumber(rowdata.debit, 2, 1);
	  				},
				}, {
					display : '贷方金额',
					name : 'credit',
					align : 'right',width:105,formatter: "###,##0.00",
					render : function(rowdata, rowindex, value) {
	  					return formatNumber(rowdata.credit, 2, 1);
	  				},
				}]
			}, {
				display : '预算会计',
				align : 'center',
  				columns:[{
					display : '借方金额',
					name : 'budg_debit',
					align : 'right',width:105,formatter: "###,##0.00",
					render : function(rowdata, rowindex, value) {
	  					return formatNumber(rowdata.budg_debit, 2, 1);
	  				},
				}, {
					display : '贷方金额',
					name : 'budg_credit',
					align : 'right',width:105,formatter: "###,##0.00",
					render : function(rowdata, rowindex, value) {
	  					return formatNumber(rowdata.budg_credit, 2, 1);
	  				},
				}]
			}, {
				display : '制单人',
				name : 'create_name',width:80,
				align : 'left'
			}, {
				display : '出纳签字人',
				name : 'cash_name',width:80,
				align : 'left'
			}, {
				display : '审核人',
				name : 'audit_name',width:80,
				align : 'left'
			}, {
				display : '记账人',
				name : 'acc_name',width:80,
				align : 'left'
			}, {
				display : '凭证来源',
				name : 'busi_type_name',width:80,
				align : 'left'
			}, {
				display : '差异标注',
				name : 'sign_flag',width:80,
				align : 'left',
				hide:copyNa
			}, {
				display : '备注',
				name : 'note',width:100,
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryAccVouchCashier.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad:true,
			selectRowButtonOnly : true ,//heightDiff: -10,
			toolbar: toolBra,
			checkBoxDisplay : isCheckDisplay,
			lodop:{
			   title:"会计凭证",
	    	   fn:{
	    		  debit:function(value){
	    			   return formatNumber(value, 2, 1);
	    		   }
	    		   ,credit:function(value){
	    			   return formatNumber(value, 2, 1);
	    		   }
	    	   }
		     },
		     scroll: true,
			 scrollToPage: true,
	         scrollToAppend: true,
		     pagerRender: function (){
		    	  
                 var html = [];
                 html.push('<div style="line-height:32px;padding-right:10px;float:right;">');
                 if(this.get('newPage')!=undefined){
                     grid.options.parms.push({name : 'newPage',value : this.get('newPage')});
                 }
                
                 if (this.get('newPage') == this.get('pageCount')){
                     html.push('<span>总共：' + this.get('pageCount') + '页，'+this.get('total')+'条</span> ');
                 }else{
                     html.push('<span>已加载：' + this.get('newPage') + '页</span> ');
                     html.push('<span>，</span> ');
                     html.push('<span>总共：' + this.get('pageCount') + '页，'+this.get('total')+'条</span> ');
                
                 } 
             
                 html.push('</div>');
                 return html.join('');
             }
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
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
			case "search":
				$.ligerDialog.open({url: 'accVouchCashierSearchPage.do?isCheck=false', height: 420,width: 650, title:'高级查询',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.searchAccVouch(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
				return;
			case "sign"://出纳签字
				
				var data = gridManager.getCheckedRows();
				
				var vouchData="";
				
				if (data.length == 0) {
					
					$.ligerDialog.error('请选择行');
					
				} else {
					
					var ParamVo = [];
					
					var result = 0;
					
					$(data).each(function() {
						
						if(this.vouch_no != null){
							if(this.create_user=='${sessionScope.user_id }'){
								
								result+=1;
								
							}
							ParamVo.push(this.vouch_id+"@"+this.state);
							
						}
						
					});
					
					if(ParamVo.length==0){
						
						$.ligerDialog.error("请选择有效凭证进行签字操作！");
						return;
					}
					
					
					if(result>0){
						
						$.ligerDialog.error('当前用户不能操作自己制作的凭证！');
						
						return;
					}
					
					$.ligerDialog.confirm('确定签字?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("signAccVouchCashire.do", {
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
			case "unsign":
				
			var data = gridManager.getCheckedRows();
			
			if (data.length == 0) {
				
				$.ligerDialog.error('请选择行');
				
			} else {
				
				var ParamVo = [];
				
				var vouchData="";
				
				var count=0;
				
				$(data).each(function() {
					
					if(this.vouch_no != null){
						
					ParamVo.push(this.vouch_id+"@"+this.state+"@"+parent_node_id);
						
					if(this.cash_user != '${sessionScope.user_id }'){
						count+=1;
					}
				  }
					
				});
				
				if(count> 0){
					
					$.ligerDialog.error("请选择当前用户签字的凭证进行取消操作！");
					
					return;
					
				}
				if(ParamVo.length==0){
					
					$.ligerDialog.error("请选择有效凭证进行取消签字操作！");
					return;
				}
				
				$.ligerDialog.confirm('确定取消签字?', function(yes) {
					if (yes) {
						ajaxJsonObjectByUrl("unsignAccVouchCashire.do", {
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
	
	function printDate(){
		if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
		var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	  	          {"cell":0,"value":"凭证日期："+$("#create_date_b").val()+"至"+$("#create_date_e").val(),"colSpan":"5"}
	      		  ]
	      	};
	   		
	   		var printPara={
	   			rowCount:1,
	   			title:'出纳签字查询',
	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
	   			class_name: "com.chd.hrp.acc.service.vouch.AccVouchService",
				method_name: "queryAccVouchPrint",
				bean_name: "accVouchService",
				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	   			};
	    	
	   		//执行方法的查询条件
	   		$.each(grid.options.parms,function(i,obj){
	   			printPara[obj.name]=obj.value;
	    	});
	   		
	    	officeGridPrint(printPara);
	}
	
	function show(){
		if($("#vouch_table").is(":hidden")){
			$("#vouch_table").show();
		}else{
			$("#vouch_table").hide();
		}
		grid._onResize();
		//$(".l-bar-btnload").click();
	}
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
	//获取系统默认日期
	var acc_month=getMonthDate('${acc_year}','${acc_month}');
	var date_b = acc_month.split(";")[0];
	var date_e = acc_month.split(";")[1];
	//字典下拉框
	function loadDict() {
		
		
		$("#create_date_b").ligerTextBox({width:160});
		$("#create_date_e").ligerTextBox({width:160});
		
		autocomplete("#vouch_type_code", "../../queryVouchType.do?isCheck=false","id", "text", true, true);
		//autocomplete("#state", "../../queryAccVouchState.do?isCheck=false","id", "text", true, true);
		autoCompleteByData("#state", [{"id": "1", "text": "未签字"}, {"id": "2", "text": "已签字"}], "id", "text", false, true, "", false,1);
		//autocomplete("#subj_code_b","../../querySubj.do?isCheck=false","id","text",true,true);
		$("#subj_code_b").ligerComboBox({
    		url: "../../querySubj.do?isCheck=false",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: subjWidth,
    		selectBoxHeight:'260',
    		setTextBySource: true,
    		width: '160',
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: true,
    		alwayShowInDown: true,
    	});
    	//autocomplete("#subj_code_e","../../querySubj.do?isCheck=false","id","text",true,true);
    	$("#subj_code_e").ligerComboBox({
    		url: "../../querySubj.do?isCheck=false",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: subjWidth,
    		selectBoxHeight:'260',
    		setTextBySource: true,
    		width: '160',
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: true,
    		alwayShowInDown: true,
    	});
    	autocomplete("#acc_user", "../../../sys/queryAccUserDict.do?isCheck=false&create_date_b='"+date_b+"'&create_date_e= '"+date_e+"' ","id", "text", true, true);
    	autocomplete("#audit_user", "../../../sys/queryAuditUserDict.do?isCheck=false&create_date_b='"+date_b+"'&create_date_e= '"+date_e+"' ","id", "text", true, true);
    	autocomplete("#create_user", "../../../sys/queryCreateUserDict.do?isCheck=false&create_date_b='"+date_b+"'&create_date_e='"+date_e+"' ","id", "text", true, true);
    	autocomplete("#cash_user", "../../../sys/queryCashUserDict.do?isCheck=false&create_date_b='"+date_b+"'&create_date_e= '"+date_e+"' ","id", "text", true, true);
    	//autocomplete("#cur_code", "../../queryCur.do?isCheck=false","id", "text", true, true);
    	autocomplete("#busi_type_code", "../../queryBusiTypeByVouch.do?isCheck=false&begin_date="+date_b+"&end_date="+date_e+"","id", "text", true, true);
    	autoCompleteByData("#sign_flag", diffData.Rows, "id", "text", false, true,null,false,null);
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
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">凭证日期：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="create_date_b" type="text" id="create_date_b" ltype="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
				style="width: 90px;" /></td>
			<td align="left" class="l-table-edit-td">至</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="create_date_e"
				type="text" id="create_date_e" ltype="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
				style="width: 90px;" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">凭证类型：</td>
			<td align="left" class="l-table-edit-td"><input
				name="vouch_type_code" type="text" id="vouch_type_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 25px;">状&nbsp;&nbsp;&nbsp;&nbsp;态：&nbsp;&nbsp;&nbsp;</td>
			<td align="left" class="l-table-edit-td"><input name="state"
				type="text" id="state" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<!-- <td align="right" class="l-table-edit-td" style="padding-left: 20px;"> <input class="liger-button" type="button" value="查询" onclick="query();"/></td> -->
		<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><a href="javascript:show()">高级查询</a></td>
		</tr>
	</table>
	<table id="vouch_table" cellpadding="0" cellspacing="0" class="l-table-edit" style="display: none">
		 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭 证 号 ：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_no_b" type="text" id="vouch_no_b" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left" >&nbsp;至&nbsp;</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_no_e" type="text" id="vouch_no_e" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" class="l-table-edit-td" style="padding-left:25px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
                <td align="left" class="l-table-edit-td" ><input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" class="l-table-edit-td" style="padding-left:25px;" >凭证来源：</td>
                <td align="left" class="l-table-edit-td"><input name="busi_type_code" type="hidden" id="busi_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计科目：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code_b" type="hidden" id="subj_code_b" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" >&nbsp;至&nbsp;</td>
                <td align="left" class="l-table-edit-td" ><input name="subj_code_e" type="hidden" id="subj_code_e" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" class="l-table-edit-td" style="padding-left:25px;">制 单 人：</td>
                <td align="left" class="l-table-edit-td"><input name="create_user" type="hidden" id="create_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left" class="l-table-edit-td" style="padding-left:25px;">出纳人 ：</td>
                <td align="left" class="l-table-edit-td"><input name="cash_user" type="hidden" id="cash_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="left" class="l-table-edit-td"  style="padding-left:15px;">金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额：</td>
                <td align="left" class="l-table-edit-td" ><input name="money" type="text" id="money" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" >&nbsp;至&nbsp;</td>
                <td align="left" class="l-table-edit-td" ><input name="sumMoney" type="text" id="sumMoney" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:25px;">审 核 人：</td>
                <td align="left" class="l-table-edit-td"><input name="audit_user" type="hidden" id="audit_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left" class="l-table-edit-td" style="padding-left:25px;">记账人 ：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_user" type="hidden" id="acc_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">展现方式：</td>
                <td align="left" class="l-table-edit-td" colspan="3">
            	 <input name="pay_type_code" type="radio" id="pay_type_code"  checked="checked" value="1"/>按凭证展示
                <input name="pay_type_code" type="radio" id="pay_type_id" value="2"/>按凭证分录展示
                 &nbsp;&nbsp;&nbsp;&nbsp;
                <input name="note" type="checkbox" id="note" ltype="text" value="1" validate="{required:true,maxlength:20}" />批注</td>
                <td align="right" class="l-table-edit-td" style="padding-left: 25px;">差异标注：</td>
				<td align="left" class="l-table-edit-td"><input name="sign_flag" type="hidden" id="sign_flag"  validate="{required:true,maxlength:20}" /></td>
            </tr>
	</table>
	<div id="maingrid"></div>

</body>
</html>
