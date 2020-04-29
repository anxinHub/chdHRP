<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var year_month = '${acc_year_month}';
	var userUpdateStr;

	$(function() {
		$("#layout1").ligerLayout({ leftWidth: 320});
		loadForm();
		loadDict();
		loadHead(null); //加载数据
		loadButton();
		query();
		changeCur();
	});
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : "acc_year",
			value : liger.get("acc_year_month").getValue().split(".")[0],
		});
		grid.options.parms.push({
			name : "acc_month",
			value : liger.get("acc_year_month").getValue().split(".")[1],
		});
		grid.options.parms.push({
			name : "template_type_code",
			value : "Z002",
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function del(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
				//表的主键
				this.vouch_id 
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteAccAdjustCollect.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
        return;

	}
	
	function loadForm(){
		//设置验证
	    $.metadata.setType("attr", "validate");
		var v = $("form").validate({
	         errorPlacement: function (lable, element)
	         {
	             if (element.hasClass("l-textarea"))
	             {
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }
	             else if (element.hasClass("l-text-field"))
	             {
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else
	             {
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
	     //$("form").ligerForm();
	 }  

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '凭证类型', name : 'vouch_type_name', align : 'left'
			}, {
				display : '凭证编号', name : 'vouch_no', align : 'left',
				render : function(rowdata, rowindex, value) {
					return '<a href=javascript:openSuperVouch("'+ rowdata.vouch_id + '")>'+rowdata.vouch_no+'</a>';
				}
			}, {
				display : '状态', name : 'state', align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAccAdjustCollect.do',
			width : '100%',
			height : $(window).height()-1,
			checkbox : true,
			rownumbers : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			delayLoad: true,//初始化不加载，默认false
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '删除',
					id : 'delete',
					click : del,
					icon : 'delete'
				}, {
					line : true
				} ]
			},
			/* onDblClickRow : function(rowdata, rowindex, value) {
				alert(rowdata.vouch_id);
				openUpdate(rowdata.vouch_id);
			} */
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	function loadDict() {
		//字典下拉框
		$("#acc_year_month").ligerComboBox({
	      	url: '../../../queryYearMonth.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 90,
	      	autocomplete: true,
	      	width: 90,
	      	onChangeValue: function(value){
	      		if(grid){
		      		query();
	      		}
	      	}
		});
		liger.get("acc_year_month").setValue(year_month.substring(0,4)+"."+year_month.substring(4,6).toString());
		liger.get("acc_year_month").setText(year_month.substring(0,4)+"."+year_month.substring(4,6).toString());

		var paras ={
			subj_type_code : '04',
			subj_type_code1 : '05',
			is_last : '1'
		}; 
		autocomplete("#subj_code", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "235", "", subjWidth);	
		autocomplete("#cur_code", "../../../queryCur.do?isCheck=false", "id", "text", true, true, "", true);
		autocomplete("#vouch_type_code", "../../../queryVouchType.do?isCheck=false", "id", "text", true, true, "", true);
		
		$("#start_rate").ligerTextBox({width:160,number:true,disabled: false });
		$("#avg_rate").ligerTextBox({width:160,number:true,disabled: false });
		$("#end_rate").ligerTextBox({width:160,number:true,disabled: false });
		$("#summary").ligerTextBox({width:160,disabled: false });
	}
	
	function loadButton(){
		$("#but_vouch").ligerButton({click: createVouch, width:90});
		$("#but_cur").ligerButton({click: openCur, width:70});
	}
	
	function createVouch(){
		if($("form").valid()){
			var para={
				acc_year : liger.get("acc_year_month").getValue().split(".")[0],
				acc_month : liger.get("acc_year_month").getValue().split(".")[1],
				cur_code : liger.get("cur_code").getValue(),
				start_rate : $("#start_rate").val(),
				avg_rate : $("#avg_rate").val(),
				end_rate : $("#end_rate").val(),
				summary : $("#summary").val(),
				vouch_type_code : liger.get("vouch_type_code").getValue(),
				subj_code : liger.get("subj_code").getValue()
			};
			
			$.ligerDialog.confirm('确定生成凭证?', function (yes){
	        	if(yes){
	        		var loadIndex = layer.load(1);
	        		ajaxJsonObjectBylayer("addAccAdjustCollectVouch.do",para,function(responseData){	
	        			//console.log(responseData);
	        			layer.close(loadIndex);
	        			var paraVouch={
	        				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
	        				title:'会计凭证',
	        				width:0,
	        				height:0,
	        				isShowMin:true,
	        				isModal:true,
	        				data:{auto_id:responseData.vouch_id, busi_log_table:'ACC_BUSI_LOG_ZZ', busi_type_code:'Z002'}
	        			};
	        			parent.openDialog(paraVouch);
	          		},layer,loadIndex);
	        	}
	        }); 
        }
	}
	
	function openCur(){
		var parm = "cur_code="+liger.get("cur_code").getValue();
		
    	$.ligerDialog.open({ 
    		url: 'accAdjustCollectCurPage.do?isCheck=false&' + parm,
    		data: {}, height: 300,width: 500, title : '修改', 
    		modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
    		buttons: [ 
    			{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCur(); },cls:'l-dialog-btn-highlight' }, 
    			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    		] 
    	});
	}
	
	function changeCur(){
		if(liger.get("acc_year_month").getValue() == ""){
			return
		}
		if(liger.get("cur_code").getValue() == ""){
			$("#start_rate").val("");
			$("#avg_rate").val("");
			$("#end_rate").val("");
			$("#summary").val("");
		}
		var formPara={
			cur_code : liger.get("cur_code").getValue(),
			acc_year : liger.get("acc_year_month").getValue().split(".")[0],
			acc_month : liger.get("acc_year_month").getValue().split(".")[1]
		};
		ajaxJsonObjectByUrl("queryAccAdjustCollectCurRateByCode.do?isCheck=false",formPara,function(responseData){
			if(responseData.change=="true"){
				$("#start_rate").val(responseDate.start_rate);
				$("#avg_rate").val(responseDate.avg_rate);
				$("#end_rate").val(responseDate.end_rate);
			}
		});
	}
	
	//打开凭证
	function openSuperVouch(vouch_id){
		parent.parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">会计期间：</td>
			<td align="left" class="l-table-edit-td"><input name="acc_year_month"
				type="text" id="acc_year_month" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<input type="button" id="but_vouch" accessKey="T"  value="生成凭证(T)"/>
			</td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="layout1" style="width:100%;margin:0; padding:0;">
		<div id="maingrid"  position="left" title="凭证列表"></div>
		<div position="center" title="货币信息" >
			<form name="form1" method="post"  id="form1" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr><td width="120"><br/></td></tr>
				<tr><td><br/></td></tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						<span style="color:red">*</span>调汇货币：
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="cur_code" type="text" id="cur_code" ltype="text" required="true" validate="{required:true}" onchange="changeCur()" />
					</td>
					<td align="left" class="l-table-edit-td" >
						<input type="button" id="but_cur" accessKey="B"  value="币种(B)"/>
					</td>
				</tr>
				<tr><td><br/></td></tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						<span style="color:red">*</span>期初汇率：
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="start_rate" type="text" id="start_rate" ltype="text" required="true" validate="{required:true,maxlength:20}" />
					</td>
					<td align="left"></td>
				</tr>
				<tr><td><br/></td></tr>
				<tr>
					<td align="right" class="l-table-edit-td" >
						<span style="color:red">*</span>平均汇率：
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="avg_rate" type="text" id="avg_rate" ltype="text" required="true" validate="{required:true,maxlength:20}" />
					</td>
					<td align="left"></td>
				</tr>
				<tr><td><br/></td></tr>
				<tr>
					<td align="right" class="l-table-edit-td" >
						<span style="color:red">*</span>期末汇率：
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="end_rate" type="text" id="end_rate" ltype="text" required="true" validate="{required:true,maxlength:20}" />
					</td>
					<td align="left"></td>
				</tr>
				<tr><td><br/></td></tr>
				<tr>
					<td align="right" class="l-table-edit-td" >
						<span style="color:red">*</span>摘要：
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="summary" type="text" id="summary" ltype="text" required="true" validate="{required:true,maxlength:80}" />
					</td>
					<td align="left"></td>
				</tr>
				<tr><td><br/></td></tr>
				<tr>
					<td align="right" class="l-table-edit-td" ><span style="color:red">*</span>凭证类型：</td>
					<td align="left" class="l-table-edit-td">
						<input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text" required="true" validate="{required:true}" />
					</td>
					<td align="left"></td>
				</tr>
				<tr><td><br/></td></tr>
				<tr>
					<td align="right" class="l-table-edit-td" ><span style="color:red">*</span>汇兑损益科目：</td>
					<td align="left" colspan="2" class="l-table-edit-td">
						<input name="subj_code"  type="text" id="subj_code" ltype="text" required="true" validate="{required:true}" />
					</td>
				</tr>
			</table>
			</form>
		</div>  
	</div> 
</body>
</html>
