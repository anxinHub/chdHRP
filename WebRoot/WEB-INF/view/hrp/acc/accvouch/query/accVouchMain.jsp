<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/map.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script type="text/javascript">
	var grid;

	var gridManager = null;

	var userUpdateStr;
	
	var copyNa;
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
      
		/*  $("#topmenu").ligerMenuBar({
			items : [ {
				text : '冲销',
				menu : menu_write_off
			},{
				text : '作废',
				menu : menu_cancle
			} , {
				text : '打印',
				menu : menu_print
			}, {
				text : '导入导出',
				menu : menu_export
			}   ]
		}); */
		//设置凭证起止日期
		var acc_month=getMonthDate('${acc_year}','${acc_month}');
		$("#create_date_b").val(acc_month.split(";")[0]);
		$("#create_date_e").val(acc_month.split(";")[1]);
		
		$('input:radio[name="pay_type_code"]').bind("change",function(){
			hideSubj();
		})
		
		hideSubj();
		
		ajaxJsonObjectByUrl("../queryAccVouchFlowByNodeId.do?isCheck=false",{},function (responseData){
    	
			if(responseData.result=="true"){
    			
				grid.toggleCol('cash_name', true);
				
    		}else{
    			
    			grid.toggleCol('cash_name', false);
    		}
    	});

	});
	
	function hideSubj(){
		
		var pay_type=$("input[name='pay_type_code']:checked").attr('value');
		//console.log()
		
		if(pay_type=="1"){
			
			grid.toggleCol('subj_name', false);
			grid.toggleCol('budg_debit', true);
			grid.toggleCol('budg_credit', true);
			grid.changeHeaderText('budg', '财务会计');
			grid.usePager = false;
		}else{
			grid.toggleCol('subj_name', true);
			grid.toggleCol('budg_debit', false);
			grid.toggleCol('budg_credit', false);
			grid.changeHeaderText('budg', '会计金额');
			grid.usePager = true;
		}
		
		if(${copy_nature} == "02"){
			grid.toggleCol('budg_debit', false);
			grid.toggleCol('budg_credit', false);
		}
		
		//query();
	}
	
	//查询
	function query() {
		
		grid.options.parms = [];
		/* grid.options.newPage = 1;
		grid.options.parms.push({name : 'changepage',value : 1}); */
		//根据表字段进行添加查询条件
		var pay_type=$("input[name='pay_type_code']:checked").attr('value');
		
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
		
		//加载查询条件
		grid.loadData(grid.where);
		
		//console.log(1,grid.options.parms);

	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [  {
				display : '凭证编号',
				name : 'vouch_no',
				align : 'left',width:80,render:function(rowdata){
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
			},{
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
			url : 'queryAccVouch.do',
			width : '100%',
			height : '100%',
			checkbox :true,
			rownumbers : true,
			delayLoad:true,
			selectRowButtonOnly : true ,heightDiff: 30,
			checkBoxDisplay : isCheckDisplay,
			toolbar: { items: [
                	{ text: '查询', id:'search', click: query, icon:'search' },
                	{ line:true },
                	{ text: '添加', id:'add', click: itemclick, icon:'add' },
	                { line:true },
	                { text: '删除', id:'del', click: itemclick,icon:'delete' },
					{ line:true },
					{ text: '作废', id:'cancle', click: itemclick,icon:'candle' },
					{ line:true },
					{ text: '恢复', id:'recover', click: itemclick,icon:'prev' },
					{ line:true },
					{ text: '冲销', id:'redVouch', click: itemclick,icon:'bcancle' },
					{ line:true },
					{ text: '差异标注', id:'redVouch', click: myDiff,icon:'blabel', hide:copyNa },
					{ line:true,hide:copyNa },
					{ text: '打印', id:'print', click: printDate,icon:'print'}
		       ]},
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
		       }
		       /* pagerRender: function (){
		    	  
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
               }*/
		}); 

		gridManager = $("#maingrid").ligerGetGridManager();
		/* $("#create_date_b").onChangeDate('changeValue', function (value) { 
			alert(1,value); 
		});  */
	}
	
	function isCheckDisplay(rowdata) {
		if (rowdata.summary == "合计")
			return false;

		return true;

	}
	
	function printDate(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	/* var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	}); */
    	var heads={
      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
      		  "rows": [
  	          {"cell":0,"value":"凭证日期："+$("#create_date_b").val()+"至"+$("#create_date_e").val(),"colSpan":"5"}
      		  ]
      	};
   		
   		var printPara={
   			rowCount:1,
   			title:'凭证查询',
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
   		/* ajaxJsonObjectByUrl("queryAccVouch.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		}); */
    }
	
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				openVouchNew();
				return;
			case "modify":
				return;
			case "del":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					var isDel=true;
					$(data).each(function() {
						if(this.vouch_id!="0"){			
							
							if(this.busi_type_name==null || this.busi_type_name=="" 
									|| this.busi_type_name=="期末处理" || this.busi_type_name=="工资转账" || this.busi_type_name=="出纳凭证"){
								
							}else{
								isDel=false
								return true;
							}
							ParamVo.push(this.vouch_id);
						}
					});
					
					if(!isDel){
						$.ligerDialog.error('自动凭证请在凭证维护页面删除！');
						return;
					}
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccVouch.do", {
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
			case "search":
				//$.ligerDialog.open({url: 'accVouchSearchPage.do?isCheck=false', height: 420,width: 650, title:'高级查询',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.searchAccVouch(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
				return;
			case "cancle":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					var vouchData="";
					$(data).each(function() {
						
						if(this.state != '1'){
							vouchData+=this.vouch_no+"、";
						}else{
							
							ParamVo.push(this.vouch_id+"@"+this.state)
							
						}
						
					});
					if(vouchData.length>0){
						
						$.ligerDialog.error('凭证号:'+vouchData.substring(0, vouchData.length-1)+"不是新建状态,不能进行作废操作！");
						
						return;
					}
					$.ligerDialog.confirm('确定作废?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("cancleAccVouch.do", {
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
			case "recover":
			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				var ParamVo = []
				var vouchData="";
				$(data).each(function() {
					if(this.state != '0'){
						vouchData+=this.vouch_no+"、";
					}else{
						
						ParamVo.push(
								this.vouch_id+"@"+this.state)
						
					}
					
				});
				if(vouchData.length>0){
					
					$.ligerDialog.error('凭证号:'+vouchData.substring(0, vouchData.length-1)+"未进行作废操作,不能取消作废！");
					
					return;
				}
				
				$.ligerDialog.confirm('确定恢复?', function(yes) {
					if (yes) {
						ajaxJsonObjectByUrl("recoverAccVouch.do", {
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
			case "redVouch":
				var data = gridManager.getCheckedRows();
				
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					if(data.length>1){
						$.ligerDialog.error("只能单张红字冲销");
						return;
					}
					
					var fromData ={};
					var isRes=true;
					$(data).each(function() {
						if(this.state =='-1' || this.state =='0'){
							$.ligerDialog.error("作废、草稿凭证不能红字冲销");
							isRes=false;
							return false;
						}
						fromData={
								copy_type:"1",//红字冲销
								vouch_id:this.vouch_id,
								vouch_date : this.vouch_date
						};
						
					});
					if(!isRes){
						return;
					}
					$.ligerDialog.confirm('确定红字冲销?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("../superVouch/copySuperVouch.do", fromData, function(responseData) {
								if (responseData.state == "true") {
									query();
									openSuperVouch(responseData.vouchId);
								}
							});
						}
					});
				}
				return;
			/* case "blueVouch":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					var vouchData="";
					$(data).each(function() {
						
						if(this.state != '1'){
							vouchData+=this.vouch_type_short+""+this.vouch_no+"、";
						}else{
							
							ParamVo.push(this.vouch_id+"@"+this.state)
							
						}
						
					});
					if(vouchData.length>0){
						
						$.ligerDialog.error('凭证号:'+vouchData.substring(0, vouchData.length-1)+"不是新建状态,不能进行作废操作！");
						
						return;
					}
					$.ligerDialog.confirm('确定蓝字冲销?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("blueWriteoffAccVouch.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return; */
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
	//获取系统默认日期
	var acc_month=getMonthDate('${acc_year}','${acc_month}');
	var date_b = acc_month.split(";")[0];
	var date_e = acc_month.split(";")[1];
	//字典下拉框
	function loadDict() {
		
		
		$("#create_date_b").ligerTextBox({width:160});
		$("#create_date_e").ligerTextBox({width:160});
		
		autocomplete("#vouch_type_code", "../../queryVouchType.do?isCheck=false","id", "text", true, true);
		autocomplete("#state", "../../queryAccVouchState.do?isCheck=false","id", "text", true, true);
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
    	autocomplete("#busi_type_code", "../../queryBusiTypeByVouch.do?isCheck=false&begin_date="+date_b+"&end_date="+date_e+"","id", "text", true, true);
    	autoCompleteByData("#sign_flag", diffData.Rows, "id", "text", false, true,null,false,null);
	}

	/**
	 * 打印 预览 设置
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
	
	var menu_cancle = {
		width : 120,
		items : [ {
			text : '作废',
			id : 'cancle',
			click : itemclick
		}, {
			text : '恢复',
			id : 'recover',
			click : itemclick
		} ]
	};

	
	var menu_export = {
		width : 120,
		items : [ {
			text : '导入',
			id : 'inport',
			click : itemclick
		}, {
			text : '导出',
			id : 'export',
			click : itemclick
		}, {
			text : '模版下载',
			id : 'download',
			click : itemclick
		} ]
	};

	
	var menu_write_off = {
		width : 120,
		items : [ {
			text : '红字冲销',
			id : 'redVouch',
			click : itemclick
		}, {
			line : true
		}, {
			text : '蓝字冲销',
			id : 'blueVouch',
			click : itemclick
		} ]
	};
	
	function show(){
		if($("#vouch_table").is(":hidden")){
			$("#vouch_table").show();
		}else{
			$("#vouch_table").hide();
			
		}
		grid._onResize();
		//$(".l-bar-btnload").click();
	}
	
function openVouchNew(){
	parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do','会计凭证',0,0,true,true);
}

function openSuperVouch(vouch_id){
	parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
}

function exportxls() {
	var jatoolsPrinter = getJatoolsPrinter();

	// chrome 45+, 用新插件api,异步调用
	//console.log(jatoolsPrinter)
	jatoolsPrinter.isExcelInstalled(function(res) {
		doExport(res);
	});
}

function myDiff(){
	
	var data = gridManager.getCheckedRows();
	if (data.length == 0) {
		$.ligerDialog.error('请选择行');
		return;
	} 
	
	var ParamVo = [];
	$(data).each(function() {
		if(this.vouch_id!="0"){	
			ParamVo.push(this.vouch_id);
		}
	});
	
	if(ParamVo.length>1000){
		$.ligerDialog.error('不能超过1000张证');
		return;
	}
	
	if(ParamVo.length>0){
		$.ligerDialog.confirm('确定差异标注?', function(yes) {
			if (yes) {
				var loadIndex = layer.load(1);
				ajaxJsonObjectBylayer("../superVouch/updateAccVouchDiffAuto.do", {
					vouch_id : ParamVo.toString()
				}, function(res) {
					
					if(res.Total==0){
						$.ligerDialog.success("操作成功。");
						return;
					}
					
					parent.$.ligerDialog.open({url : 'hrp/acc/accvouch/superVouch/vouchDiffNotePage.do?isCheck=false',
						data:{
							diffNoteJson: res
						}, height: $(parent.window).height()-200,width: 600, title:'差异标注',
						modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,isDrag: false,
						buttons: [{ text: '关闭', onclick: function (item, dialog) { dialog.hide(); } } ]});
					
					if (res.state == "true") {
						query();
					}
				},layer,loadIndex);
			}
		});
	}
	
}

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
	<table id="vouch_table" cellpadding="0" cellspacing="0" class="l-table-edit" style="display: none" border=0>
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
	<div id="maingrid" ></div>
	
<script type="text/javascript">

//改变时间日期时触发后台查询项目相关负责人 
var inputDateB = document.getElementById("create_date_b");
var inputDateE = document.getElementById("create_date_e");
inputDateB.onfocus = function(){
	 WdatePicker({
		isShowClear:true,
		readOnly:false,
		dateFmt:'yyyy-MM-dd',
		onpicked:function(dp){
			date_b = dp.cal.getNewDateStr();
	 		//发生日期不触发 
		}
	}) 
}
inputDateE.onfocus = function(){
	 WdatePicker({
		isShowClear:true,
		readOnly:false,
		dateFmt:'yyyy-MM-dd',
		onpicked:function(dp){
			date_e = dp.cal.getNewDateStr();
			//结束日期，触发重新加载
	 		loadDict();
		}
	}) 
}
 
</script>

</body>
</html>

