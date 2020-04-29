<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var state="${bid_state}";

	$(function() {
		
		loadDict();//加载下拉框

		//加载数据
		loadHead(null); 
		loadForm()
		query();
	});
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件 
		grid.options.parms.push({ name : 'bid_id', value : $("#bid_id").val() });
				
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function addNewRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		});
	}
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
                    {display : 'detail_id',
						name : 'detail_id',
						hide:true,
						align : 'left',
						width: '120'
					},{display : 'bid_id',
						name : 'bid_id',
						hide:true,
						align : 'left',
						width: '120'
					},{display : 'ass_id',
						name : 'ass_id',
						hide:true,
						align : 'left',
						width: '120'
					},
					{
						display : '资产名称',
						name : 'ass_name',
						editor:{type:'text'},
						align : 'left',width: '120',
						/*render : function(rowdata, rowindex,value) {
							if(rowdata){ 
								if(!value){
									return "";
								}else{
								return "<a href=javascript:openUpdate("+rowdata.ass_id+")><b>"+value+"</b></font></a>";
							}
							}
						}*/
					}, {
						display : '单价',
						name : 'bidd_price',
						editor:{type:'text'},
						align : 'left',width: '150',
					}, {
						display : '数量',
						name : 'bidd_no',
						editor:{type:'text'},
						align : 'left',width: '80',
					}, {
						display : '金额',
						name : 'bidd_value',
						editor:{type:'number'},
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.bidd_value == null ? 0
												: rowdata.bidd_value,
										'${ass_05005}',
										1);
						},
						align : 'left',width: '150'
					}, {
						display : '品牌要求',
						name : 'ass_brand',
						
						align : 'left',width: '120'
					}, {
						display : '功能要求',
						name : 'bidd_budgfunction',
						editor:{type:'text'},
						align : 'left',width: '150'
					}, {
						display : '项目',
						name : 'prj_name',						
						align : 'left',width: '100'
					}, {
						display : '生产厂家',
						name : 'fac_name',
						align : 'right',width: '120',
			            
					}, {
						display : '预算数量',
						name : 'bidd_budgno',
						editor:{type:'text'},
						align : 'left',width: '90'
					},  {
						display : '预算单价',
						name : 'bidd_budgprice',
						align : 'left',width: '90'
					},{ 
						display: '预算金额', 
						name: 'bidd_budgvalue', 
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.bidd_budgprice*rowdata.bidd_budgno == null ? 0
												: rowdata.bidd_budgprice*rowdata.bidd_budgno,
										'${ass_05005}',
										1);
						},
						align : 'left',width: '90'
		              },
		              { display: '预计到货日期', 
		            	  name: 'bidd_budgreachdate', 
		            	  width: '90',editable:false
		              },{
		            	  display: '评估信息', 
		            	  name: 'bidd_budgevaluation', 
		            	  width: '90',editable:false 
		              }],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAsstendDetail.do?isCheck=false',
					width : '100%',
					height : '100%',
					alternatingRow : true,
					isScroll : true,
					checkbox : true,
					rownumbers : true,
					delayLoad :true,
					enabledEdit : false,
					isAddRow:false,
					selectRowButtonOnly : true,
					toolbar : {
						items : [ {
							text : '新增',
							id : 'add',
							click : itemclick,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : itemclick,
							icon : 'delete'
						}, {
							line : true
						}, {
							text : '关闭',
							id : 'close',
							click :  this_close,
							icon : 'candle'
						}
						]
					},
					onDblClickRow : function (rowdata, rowindex, value)
					{
						openUpdate(
								
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.detail_id  + "|" + 
								rowdata.bid_id
							);
					} 
				});
		
		gridManager = $("#maingrid").ligerGetGridManager();

	}
	
 function save(){
	 
	 if(state!="01"){
		 $.ligerDialog.error('非新增状态不可修改!');
		 return;
	 };
	prama={
		bid_id:$("#bid_id").val(),
		bid_code:$("#bid_code").val(),
		bid_method:liger.get("bid_method").getValue(),
		bid_tenderee:$("#bid_tenderee").val(),
		bid_tenderaddress:$("#bid_tenderaddress").val(),
		bid_purchasemode:liger.get("bid_purchasemode").getValue(),
		filed_price:$("#filed_price").val(),
		bid_bond:$("#bid_bond").val(),
		bid_condition:$("#bid_condition").val(),
		bid_purstart:$("#bid_purstart").val(),
		bid_purend:$("#bid_purend").val(),
		bid_puraddr:$("#bid_puraddr").val(),
		bid_notice:$("#bid_notice").val(),
		bid_agent:$("#bid_agent").val(),
		bid_agenter:$("#bid_agenter").val(),
		bid_agentphone:$("#bid_agentphone").val(),
		bid_openplace:$("#bid_openplace").val(),
		bid_answerdate:$("#bid_answerdate").val(),
		bid_calibratedate:$("#bid_calibratedate").val(),
		bid_noticedate:$("#bid_noticedate").val(),
		bid_start:$("#bid_start").val(),
		bid_end:$("#bid_end").val(),
		bid_committee:$("#bid_committee").val(),
		bid_remark:$("#bid_remark").val()
	};
	
	$.ligerDialog.confirm('确定更新?', function(yes) {
		
		if (yes) {
			ajaxJsonObjectByUrl("updateAssTendMain.do", prama, function(responseData) {
				if (responseData.state == "true") {
					 parentFrameUse().query();
					 parentFrameUse().grid.reload() ;
				}
			});
		}
	});
 };
  //提交
  function submitTend(){
	  if(state!="01"){
			 $.ligerDialog.error('非新增状态不可提交!');
			 return;
		 };
	 prama={
				bid_id:$("#bid_id").val()
	 };
		$.ligerDialog.confirm('确定提交?', function(yes) {
			
			if (yes) {
				ajaxJsonObjectByUrl("submitAssTend.do", prama, function(responseData) {
					if (responseData.state == "true") {
						 parentFrameUse().query();
						 parentFrameUse().grid.reload() ;
					}
				});
			}
		});
 };
	function itemclick(item) {
		if (item.id) {
			 if(state!="01"){
				 $.ligerDialog.error('非新增状态不可操作!');
				 return;
			 };
			 
			switch (item.id) {
			
			case "add":
				if(!$("#bid_id").val()){
					
					$.ligerDialog.error('请先保存主表!');
				}else{
				parent.$.ligerDialog.open({
					title: '设备招标单',
					height: 500,
					width: $(window).width(),
					url: 'hrp/ass/asstend/assTendDetailAddPage.do?bid_id='+$("#bid_id").val(),
					modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
					buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveValid(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] 

				});
			}
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					
					var ParamVo = [];
					$(data).each(
							function() {
								if(isnull(this.detail_id)){
									gridManager.deleteSelectedRow();	
									
								}else{
								ParamVo.push(this.group_id + "@" + this.hos_id
										+ "@" + this.copy_code + "@"
										+ this.bid_id+"@"+this.detail_id);
								}
							});
					if (ParamVo == "") {
						return;
				}		
					$.ligerDialog.confirm('确定删除?', function(yes) {
						
						if (yes) {
							ajaxJsonObjectByUrl("deleteTendDetail.do", {
							Param : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			}
		}

	}
	
	function openUpdate(obj) {

		if(state!="01"){
			 $.ligerDialog.error('非新增状态不可操作!');
			 return;
		 };
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		};
		var parm ="&group_id="+ 
		  vo[0] + "&hos_id=" + 
		  vo[1] + "&copy_code=" + 
		  vo[2] + "&detail_id=" + 
		  vo[3] +"&bid_id="+
		  vo[4];
		
		parent.$.ligerDialog.open({
			title: '修改',
			height: 500,
			width: $(window).width(),
			url: 'hrp/ass/asstend/assTendDetailUpdatePage.do?isCheck=true&' + parm,
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveValid(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] 

		});
		
	}
	
	function this_close() {
		 parentFrameUse().query();
		 parentFrameUse().grid.reload() ;
		frameElement.dialog.close();
		
	}
	
	function saveValid(){
        if($("form").valid()){
            save();
        }
   };
   
	function loadDict() {
		//字典下拉框
 
		var param = {
            	query_key:''
        }; 
		//$("#start_date").ligerTextBox();
		//$("#end_date").ligerTextBox();
		$("#bid_purstart").ligerTextBox();
		$("#bid_purend").ligerTextBox();
		$("#bid_answerdate").ligerTextBox();
		$("#bid_calibratedate").ligerTextBox();
		$("#bid_noticedate").ligerTextBox();
		$("#bid_start").ligerTextBox();
		$("#bid_end").ligerTextBox();
		
		autocomplete("#bid_purchasemode","queryTendMethod.do?isCheck=false","id","text",true,true,param,false);
		
		//$("#apply_month1").ligerTextBox({width : 120});

		 $('#bid_method').ligerComboBox({
				data:[{id:'01',text:'公开招标'},{id:'02',text:'邀请招标'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 160
		});
		 
		 liger.get("bid_purchasemode").setValue("${bid_purchasemode}");
		 liger.get("bid_purchasemode").setText("${value_name}");
		 liger.get("bid_method").setValue("${bid_method}");
	}

function loadForm(){
	    
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
	     $("form").ligerForm();
	 }  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	 <form name="form1" method="post"  id="form1" >
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">招标编码</td>
			<td align="left" class="l-table-edit-td" ><input name="bid_code" type="text" id="bid_code" value="${bid_code }"  validate="{required:true}"/></td>
	        
	        <td align="right" class="l-table-edit-td" >招标方式</td>
			<td align="left" class="l-table-edit-td"><input name="bid_method" type="text" id="bid_method"  value="${bid_method }"  validate="{required:true}"/></td>
			<td align="left" ></td>
			
			<td align="right" class="l-table-edit-td">招标人</td>			
			<td align="left" class="l-table-edit-td"><input name="bid_tenderee" type="text" id="bid_tenderee"  value="${bid_tenderee }"  validate="{required:true}" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" >招标地址</td>
			<td align="left" class="l-table-edit-td"><input name="bid_tenderaddress" type="text" id="bid_tenderaddress"   value="${bid_tenderaddress }"/></td>
			
			<td><input type="button" id="save" value="保存" style="width: 80px" onclick="saveValid();" /></td>
		</tr>
		 <tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">采购方式</td>
			<td align="left" class="l-table-edit-td"><input name="bid_purchasemode" type="text" id="bid_purchasemode"  value="${bid_purchasemode }"  /></td>
			
			<td align="right" class="l-table-edit-td">采购文件售价</td>
			<td align="left" class="l-table-edit-td"><input name="filed_price" type="text" id="filed_price"  /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" >保证金</td>
			<td align="left" class="l-table-edit-td"><input name="bid_bond" type="number" id="bid_bond" value="${bid_bond }" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" >招标条件</td>
			<td align="left" class="l-table-edit-td"><input name="bid_condition" type="text" id="bid_condition" value="${bid_condition }" /></td>
			<td><input type="button" id="submit" value="提交" style="width: 80px"  onclick="submitTend();" /></td>
		</tr>
		<tr>
		    <td align="right" class="l-table-edit-td" style="padding-left: 10px;">购买标书开始时间</td>
			<td align="left" class="l-table-edit-td"><input name="bid_purstart" type="text" id="bid_purstart" class="Wdate" value="${bid_purstart }" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			
			<td align="right" class="l-table-edit-td"> 购买标书结束时间</td>
			<td align="left" class="l-table-edit-td"><input name="bid_purend" type="text" id="bid_purend"  value="${bid_purend }" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td">买标书地点</td>
			<td align="left" class="l-table-edit-td"><input name="bid_puraddr" type="text" id="bid_puraddr"  value="${bid_puraddr }" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td">公告媒介</td>
			<td align="left" class="l-table-edit-td"><input name="bid_notice" type="text" id="bid_notice"  value="${bid_notice }" /></td>
			
		</tr>
		<tr>
		
		   <td align="right" class="l-table-edit-td" style="padding-left: 10px;">招标代理</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_agent" type="text" id="bid_agent" value="${bid_agente }" /></td>
		   
		   <td align="right" class="l-table-edit-td">招标代理联系人</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_agenter" type="text" id="bid_agenter" value="${bid_agenter }" /></td>
		   <td align="left"></td>
		   
		   <td align="right" class="l-table-edit-td">招标代理电话</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_agentphone" type="number" id="bid_agentphone" value="${bid_agentphone }" /></td>
		   <td align="left"></td>
		   
		   <td align="right" class="l-table-edit-td">开标地点</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_openplace" type="text" id="bid_openplace" value="${bid_openplace }" s/></td>
		   <td align="left"></td>
		</tr>
		
		<tr>
		   <td align="right" class="l-table-edit-td" style="padding-left: 10px;">答疑日期</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_answerdate" type="text" id="bid_answerdate" class="Wdate" value="${bid_answerdate }" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			
		   <td align="right" class="l-table-edit-td">定标日期</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_calibratedate" type="text" id="bid_calibratedate"  value="${bid_calibratedate }" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
		   <td align="left"></td>

		   <td align="right" class="l-table-edit-td">公告日期</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_noticedate" type="text" id="bid_noticedate"  value="${bid_noticedate }" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
		   <td align="left"></td>

		   <td align="right" class="l-table-edit-td">开标日期</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_start" type="text" id="bid_start"  value="${bid_start }" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"   /></td>
		   <td align="left"></td>		   		
		</tr>
		
		<tr>
		   <td align="right" class="l-table-edit-td" style="padding-left: 10px;">投标截止日期</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_end" type="text" id="bid_end" class="Wdate" value="${bid_end }" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
		  
		   <td align="right" class="l-table-edit-td">评委委员会人员</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_committee" type="text" id="bid_committee" value="${bid_committee }" /></td>
		   <td align="left"></td>
		   
		   <td align="right" class="l-table-edit-td">备注</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_remark" type="text" id="bid_remark" value="${bid_remark }" /></td>
		   <td align="left"></td>
		   <td align="right" class="l-table-edit-td" style="display: none">bid_id</td>
		   <td align="left" class="l-table-edit-td"><input name="bid_id" type="text" id="bid_id" value="${bid_id }" style="display: none" /></td>
		   	
		</tr> 
	</table>
	</form>
	<div id="maingrid"></div>
</body>
</html>
