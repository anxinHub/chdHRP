<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid,state;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
  		loadDict();
		
    	loadHead(null);	//加载数据

    	dates();

    	query();
    });

	function dates(){

	    var today=new Date();
	    var h=today.getFullYear();
	    var m=today.getMonth()+1;
	    today.setDate(1); 
       $("#qf_date_state").val(h+"-"+m+"-"+1)
       	var endDate = new Date(today);
		endDate.setMonth(today.getMonth()+1);
		endDate.setDate(0);
       $("#qf_date_end").val(h+"-"+m+"-"+endDate.getDate())
       
	}
    
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;

		//查询必须输入日期
		if(!$("#qf_date_state").val() || !$("#qf_date_end").val()){
			$.ligerDialog.error("签发日期不可为空!");
			return false;
		}
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'qf_date_state',value:$("#qf_date_state").val()}); 
    	grid.options.parms.push({name:'qf_date_end',value:$("#qf_date_end").val()}); 
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
    	
    	grid.options.parms.push({name:'cw_date_state',value:$("#cw_date_state").val()}); 
    	grid.options.parms.push({name:'cw_date_end',value:$("#cw_date_end").val()}); 
    	grid.options.parms.push({name:'check_type_id',value:liger.get("check_type_id").getValue()}); 
    	grid.options.parms.push({name:'create_user',value:liger.get("create_user").getValue()}); 
    	
    	grid.options.parms.push({name:'paper_num_state',value:$("#paper_num_state").val()});
    	grid.options.parms.push({name:'paper_num_end',value:$("#paper_num_end").val()}); 
    	grid.options.parms.push({name:'check_item_no',value:liger.get("check_item_no").getValue()}); 
    	grid.options.parms.push({name:'audit_user',value:liger.get("audit_user").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '状态', name: 'VALUE_NAME', align: 'center',width:50},
                     { display: '票据类型', name: 'TYPE_NAME', align: 'center',width:150},
                     { display: '票据编号', name: 'PAPER_NUM', align: 'left',width:90},
                     { display: '币种', name: 'CUR_NAME', align: 'left',width:80},
                     { display: '签发日期', name: 'QF_DATE', align: 'center',width:80},
                     { display: '财务日期', name: 'CW_DATE', align: 'center',width:80},
                     { display: '到期日期', name: 'DQ_DATE', align: 'center',width:80},
                    { display: '票面金额', name: 'PM_MONEY', align: 'right',width:200,
     					render: function(item)
     		            {
     		                    return formatNumber(item.PM_MONEY,2,1);
     		            }
                    }, 
                    { display: '核算类', name: 'CHECK_TYPE_NAME', align: 'center',width:60},
					{ display: '核算项', name: 'SUP_NAME', align: 'left',width:180},
					{ display: '制单人', name: 'USER_NAME', align: 'left',width:100},
					{ display: '审核人', name: 'AUDIT_USER', align: 'left',width:100,
  					    render : function(rowdata, rowindex,value){
  	  					    if(value != null && value != ""){
  	  	  	  					return value;
  	  	  	  				}
								return "未审核";
						}	
                    }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperIncome.do?busi_type_code=1',
         			width: '100%',height: $(window).height()-$("#queryform").height() -$("#btn").height(), checkbox: true,rownumbers:true,
        			selectRowButtonOnly:true,delayLoad: true,
        			//heightDiff: -65,
        			/* inWindow:true, */
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(rowdata.STATE,rowdata.PAPER_NUM);
    				},
    				toolbar: { items: [

    				                	{ text: '查询',click:query, icon:'search' },
    				                	{ line:true },
    				                	{ text: '添加',click:add_open, icon:'add' },
    					                { line:true },
    					                { text: '删除',click:del_open, icon:'delete' },
    									{ line:true },
    									{ text: '审核',click:audit, icon:'cashier' },
    									{ line:true },
    									{ text: '消审',click:cancel, icon:'uncashier' },
    									{ line:true },
    									{ text: '退票',click:refund, icon:'bcancle' },
    									{ line:true },
    									{ text: '收款',click:put, icon:'blabel'},
    									{ line:true },
    									{ text: '作废',click:zuofei, icon:'candle'},
    				                	{ line:true },
    				                	{ text: '重置条件',click:resets, icon:'settings' }
    						       ]},
    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function add_open(){
    	
    	parent.$.ligerDialog.open({
			url : 'hrp/acc/accpaper/accpaperincome/accPaperIncomeAddPage.do?isCheck=false',
			height : $(window).height(),
			width : $(window).width(),
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			parentframename : window.name,
			buttons : [{
				text : '保存',
				onclick : function(item, dialog) {
					var forms = dialog.frame.$("#acc_paper_sf").serialize();
					dialog.frame.addPaperIncome(forms);
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
	function del_open() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var paramVo = [];
			$(data).each(
					function() {
						paramVo.push(
								{"type_code":this.TYPE_CODE,"paper_num":this.PAPER_NUM}
								)
					});
			var json = JSON.stringify(paramVo);
			$.ligerDialog.confirm('确定删除?只有新建状态的票据才能被删除!', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAccPaperIncome.do", {
						"paramVo" : json
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function openUpdate(obj,obj2) {
		if(obj != 1){
			parent.$.ligerDialog.open({
				url : 'hrp/acc/accpaper/accpaperincome/accPaperIncomeUpdatePage.do?isCheck=false&paper_num='+obj2,
				height : $(window).height(),
				width : $(window).width(),
				title : '修改',
				modal : true,
				showToggle : false,
				showMax : false,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				buttons : [{
					text : '关闭',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			});
			return false;
		}
		
		parent.$.ligerDialog.open({
			url : 'hrp/acc/accpaper/accpaperincome/accPaperIncomeUpdatePage.do?isCheck=false&paper_num='+obj2,
			height : $(window).height(),
			width : $(window).width(),
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			parentframename : window.name,
			buttons : [{
				text : '修改',
				onclick : function(item, dialog) {
					dialog.frame.huifu(false);
					var forms = dialog.frame.$("#acc_paper_sf").serialize();
					dialog.frame.huifu(true);
					dialog.frame.updatePaperIncome(forms);
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}

	function audit() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var paramVo = [];
			$(data).each(
					function() {
						paramVo.push(
								{"type_code":this.TYPE_CODE,"paper_num":this.PAPER_NUM}
								)
					});
			var json = JSON.stringify(paramVo);
			$.ligerDialog.confirm('确定审核?只有新建状态的票据才能被审核!', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateAuditAccPaperIncome.do", {
						"paramVo" : json
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}

	}
	function cancel() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var paramVo = [];
			$(data).each(
					function() {
						paramVo.push(
								{"type_code":this.TYPE_CODE,"paper_num":this.PAPER_NUM}
								)
					});
			var json = JSON.stringify(paramVo);
			$.ligerDialog.confirm('确定取消?只有审核完的票据才能被消审!', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateCancelAccPaperIncome.do", {
						"paramVo" : json
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}

	}
	
	function zuofei() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var paramVo = [];
			$(data).each(
					function() {
						if(this.STATE != 5){
							paramVo.push(
									{"type_code":this.TYPE_CODE,"paper_num":this.PAPER_NUM}
									)
						}
					});
			if(paramVo.length == 0){
				$.ligerDialog.error('作废失败,票据已经被作废!');
				return false;
			}
			var json = JSON.stringify(paramVo);
			$.ligerDialog.confirm('确定作废?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateZuofeiAccPaperIncome.do", {
						"paramVo" : json
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function refund(){

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		}else if(data.length > 1){
			$.ligerDialog.error('请选择单条数据');
		}else {
			if(data[0].STATE != 2){
				$.ligerDialog.error('此票已经无法退票');
				return false;
			}
			str = "&type_code_val="+data[0].TYPE_CODE+"&paper_num="+data[0].PAPER_NUM
			parent.$.ligerDialog.open({
				url : 'hrp/acc/accpaper/accpaperincome/accPaperIncomeRefundUpdatePage.do?isCheck=false&money='+data[0].PM_MONEY,
				height : 230,
				width : 350,
				title : '确定',
				modal : true,
				showToggle : false,
				showMax : false,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				buttons : [{
					text : '退票',
					onclick : function(item, dialog) {
						dialog.frame.updateRefund(str);
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			});
		}

	}
	
	function put(){

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		}else if(data.length > 1){
			$.ligerDialog.error('请选择单条数据');
		}else {
			if(data[0].STATE != 2){
				$.ligerDialog.error('此票已经无法收款');
				return false;
			}
			str = "&type_code_val="+data[0].TYPE_CODE+"&paper_num="+data[0].PAPER_NUM
			parent.$.ligerDialog.open({
				url : 'hrp/acc/accpaper/accpaperincome/accPaperIncomePutUpdatePage.do?isCheck=false&money='+data[0].PM_MONEY,
				height : 260,
				width : 350,
				title : '确定',
				modal : true,
				showToggle : false,
				showMax : false,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				buttons : [{
					text : '收款',
					onclick : function(item, dialog) {
						dialog.frame.updatePut(str);
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			});
		}

	}
	
/* 	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);

		hotkeys('D', del_open);

	} */

	function loadDict() {
		//字典下拉框
/* 		$(':button').ligerButton({
			width : 80
		}); */

		//字典下拉框
			$("#type_code").ligerComboBox({
	    		url: "queryAccPaperIncomeType_code.do?isCheck=false",
	    		valueField: "id",
	    		textField: "text",
	    		selectBoxWidth: '156',
	    		selectBoxHeight:'260',
	    		setTextBySource: true,
	    		width: '156',
	    		autocomplete: true,
	    		highLight: true,
	    		keySupport: true,
	    		async: true,
	    		alwayShowInDown: true, 
	    		parms: {pageSize: 100},
	    		onChangeValue: function (value) {
	    			
	    		}
	    	});

			//状态下拉框
			state = $("#state").ligerComboBox({
		     	data:
					[ {
						id : 1,
						text : '新建'
					}, {
						id : 2,
						text : '审核'
					}, {
						id : 3,
						text : '退票'
					}, {
						id : 4,
						text : '收款'
					}, {
						id : 5,
						text : '作废'
					} ],
			valueField : 'id',
			textField : 'text',
			initValue:1,
			selectBoxWidth : 156,
			autocomplete : true,
			width : 156
		});
//liger.get("state").setValue(5);

			//核算类下拉加载
			$("#check_type_id").ligerComboBox({
	    		url: "queryAccPaperIncomeCheckTypeId.do?isCheck=false",
	    		valueField: "id",
	    		textField: "text",
	    		selectBoxWidth: '156',
	    		selectBoxHeight:'260',
	    		setTextBySource: true,
	    		width: '156',
	    		autocomplete: true,
	    		highLight: true,
	    		keySupport: true,
	    		async: true,
	    		alwayShowInDown: true, 
	    		parms: {pageSize: 100}
	    	});

	 		//核算项下拉加载
			$("#check_item_no").ligerComboBox({
	    		url: "queryAccPaperIncomeCheckNo.do?isCheck=false",
	    		valueField: "id",
	    		textField: "text",
	    		selectBoxWidth: '156',
	    		selectBoxHeight:'260',
	    		setTextBySource: true,
	    		width: '156',
	    		autocomplete: true,
	    		highLight: true,
	    		keySupport: true,
	    		async: true,
	    		alwayShowInDown: true, 
	    		parms: {pageSize: 100}
	    	});

	 		//制单人
			$("#create_user").ligerComboBox({
	    		url: "queryAccPaperIncomeOpCreateuser.do?isCheck=false",
	    		valueField: "id",
	    		textField: "text",
	    		selectBoxWidth: '156',
	    		selectBoxHeight:'260',
	    		setTextBySource: true,
	    		width: '156',
	    		autocomplete: true,
	    		highLight: true,
	    		keySupport: true,
	    		async: true,
	    		alwayShowInDown: true, 
	    		parms: {pageSize: 100}
	    	});
	    	
	 		//制单人
			$("#audit_user").ligerComboBox({
	    		url: "queryAccPaperIncomeOpAudituser.do?isCheck=false",
	    		valueField: "id",
	    		textField: "text",
	    		selectBoxWidth: '156',
	    		selectBoxHeight:'260',
	    		setTextBySource: true,
	    		width: '156',
	    		autocomplete: true,
	    		highLight: true,
	    		keySupport: true,
	    		async: true,
	    		alwayShowInDown: true, 
	    		parms: {pageSize: 100}
	    	});
	}

		function resets(){
			$("#queryform")[0].reset()
			liger.get("type_code").setValue(null);
			liger.get("state").setValue(null);
			liger.get("check_type_id").setValue(null);
			liger.get("create_user").setValue(null);
			liger.get("check_item_no").setValue(null);
			liger.get("audit_user").setValue(null);
			dates()
		}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<form id="queryform">
     <table cellpadding="0" align="center"  cellspacing="0" class="l-table-edit">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">签发日期:</td>
            <td align="left" class="l-table-edit-td"><input name="qf_date_state" type="text" placeholder="必填" style="width: 158;height: 27" id="qf_date_state" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',maxDate:qf_date_end.value})" /></td>
            <td align="left" class="l-table-edit-td"  style="padding-left:20px;">至：</td>
            <td align="left" class="l-table-edit-td"><input name="qf_date_end" type="text" placeholder="必填" style="width: 158;height: 27" id="qf_date_end" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',minDate:qf_date_state.value})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据类型:</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" class="l-table-edit-td" id="type_code" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态:</td>
            <td align="left" class="l-table-edit-td"><input name="state" class="l-table-edit-td" id="state" ltype="text" /></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">财务日期:</td>
            <td align="left" class="l-table-edit-td"><input name="cw_date_state" type="text" style="width: 158;height: 27" id="cw_date_state" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',maxDate:cw_date_end.valu})" /></td>
            <td align="left" class="l-table-edit-td"  style="padding-left:20px;">至：</td>
            <td align="left" class="l-table-edit-td"><input name="cw_date_end" type="text" style="width: 158;height: 27" id="cw_date_end" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',minDate:cw_date_state.value})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算类:</td>
            <td align="left" class="l-table-edit-td"><input name="check_type_id" class="l-table-edit-td" id="check_type_id" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人:</td>
            <td align="left" class="l-table-edit-td"><input name="create_user" class="l-table-edit-td" id="create_user" ltype="text" /></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据编号:</td>
            <td align="right" class="l-table-edit-td"><input name="paper_num_state" type="text" style="width: 158;height: 27" id="paper_num_state" class="l-table-edit-td" id="" ltype="text" /></td>
            <td align="left" class="l-table-edit-td"  style="padding-left:20px;">至：</td>
            <td align="left" class="l-table-edit-td"><input name="paper_num_end" type="text" style="width: 158;height: 27" id="paper_num_end" class="l-table-edit-td" id="" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算项:</td>
            <td align="left" class="l-table-edit-td"><input name="check_item_no" class="l-table-edit-td" id="check_item_no" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人:</td>
            <td align="left" class="l-table-edit-td"><input name="audit_user" class="l-table-edit-td" id="audit_user" ltype="text" /></td>
        </tr> 
    </table>
   </form>
	<center>
    <div style="border:1px" id="btn">
<!--     	<input  type="button" value=" 重置" onclick="resets();"/>
	    <input  type="button" value=" 查询 " onclick="query();"/>
		<input  type="button" value=" 添加 " onclick="add_open();"/>
		<input  type="button" value=" 删除 " onclick="del_open();"/>
	    <input  type="button" value=" 审核 " onclick="audit()"/>
		<input  type="button" value=" 消审 " onclick="cancel()"/>
		<input  type="button" value=" 退票 " onclick="refund()"/>
		<input  type="button" value=" 收款 " onclick="put()"/>
		<input  type="button" value=" 作废 " onclick="zuofei()"/> -->
	</div>
	</center>
	<div id="maingrid"></div>

</body>
</html>
