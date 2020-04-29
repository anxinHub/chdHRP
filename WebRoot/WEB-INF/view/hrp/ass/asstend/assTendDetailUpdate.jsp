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
/* 	amount_money : function(value) {//金额
		return formatNumber(value,
				'${ass_05005}', 1);
	}, */
	$(function() {
		
		loadDict();//加载下拉框
		loadForm();
	});


 function save(){

			prama={
					bid_id:$("#bid_id").val(),
					detail_id:$("#detail_id").val(),
					ass_id:liger.get("ass_name").getValue(),
					ass_name:liger.get("ass_name").getText(),
					bid_budgchose:liger.get("bid_budgchose").getValue(),
					prj_name:$("#proj_name").val(),
					bidd_budgno:$("#bidd_budgno").val(),
					bidd_budgprice:$("#bidd_budgprice").val(),
					bidd_budemoney:$("#bidd_budemoney").val(),
					ass_brand:$("#ass_brand").val(),
					bidd_budgfunction:$("#bidd_budgfunction").val(),
					bidd_budgreachdate:$("#bidd_budgreachdate").val(),
					bidd_price:$("#bidd_price").val(),
					bidd_no:$("#bidd_no").val(),
					bidd_value:$("#bidd_value").val(),
					bidd_budgevaluation:$("#bidd_budgevaluation").val(),
					fac_id:liger.get("fac_name").getValue()
				};
				
	   //alert(liger.get("bid_budgchose").getValue());
	$.ligerDialog.confirm('确定更新?', function(yes) {
		
		if (yes) {
			ajaxJsonObjectByUrl("updateAssTendDetail.do?isCheck=false", prama, function(responseData) {
				if (responseData.state == "true") {

					
					parentFrameUse().query();
					//this_close();
				}
			});
		}
	});
 };
	
	
	function this_close() {
		frameElement.dialog.close();
	}
	
	
	function loadDict() {
		//字典下拉框
 
		var param = {
            	query_key:''
        }; 
		
		$("#bidd_budgreachdate").ligerTextBox();
		autocomplete("#ass_name","queryAssDict.do?isCheck=false","id","text",true,true,param,false);
		
		//autocomplete("#bid_purchasemode","queryTendMethod.do?isCheck=false","id","text",true,true,param,false);
		autocomplete("#fac_name","queryAssFac.do?isCheck=false","id","text",true,true,param,false);
		 
		
		$('#bid_budgchose').ligerComboBox({
			    width : 160,
			    cancelable:false,
			    valueField : 'row_id',
				textField : 'ass_item_name',
				selectBoxWidth : 800,
				selectBoxHeight : 240,
				grid : {
					columns : [ {
						display : '项目编码',
						name : 'ass_item',
						align : 'left',
						width:'100'
					}, {
						display : '项目名称',
						name : 'ass_item_name',
						align : 'left',
						width:'120'
					}, {
						display : '预算单价',
						name : 'budg_price',
						type : 'int',
						align : 'right',
						width:'90',
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.budg_price == null ? 0
												: rowdata.budg_price,
										'2',
										1);
						}
					}, {
						display : '预算数量',
						name : 'budg_amount',
						align : 'left',
						width:'90'
					}, {
						display : '预计费用',
						name : 'budg_money',
						type : 'int',
						align : 'right',
						width:'90',
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.budg_money == null ? 0
												: rowdata.budg_money,
										'2',
										1);
						}
					}, {
						display : '预算科室',
						name : 'budg_dept_name',
						align : 'left',
						width:'100'
					}],
					switchPageSizeApplyComboBox : false,
					onSelectRow   : f_onSelectRow_detail_budg,
					url : '../queryAssBudgTable.do?isCheck=false',
					
					pageSize : 30
					//checkbox: true
				}
					
		});
		
		liger.get("fac_name").setValue("${fac_id}")
	    liger.get("fac_name").setText("${fac_name}")
	    liger.get("ass_name").setValue("${ass_id}")
    	liger.get("ass_name").setText("${ass_name}")
    	liger.get("bid_budgchose").setValue("${bidd_budgid}");
	    liger.get("bid_budgchose").setText("${bidd_budgname}");

	}

	//选中回充数据
	function f_onSelectRow_detail_budg(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
			if (selectData != "" || selectData != null) {
				//回充数据 
				  $("input[name='proj_name']").val(data.ass_item_name);
				  $("input[name='bidd_budgno']").val(data.budg_amount);
				  $("input[name='bidd_budgprice']").val(data.budg_price);
				  $("input[name='bidd_budemoney']").val(data.budg_money);
			
		}
		return true;
	}
	
	//金额=数量*单价
	function change(thi){
		var price=$("#bidd_price").val();
		if(!price){
			
			$.ligerDialog.error('请输入单价');
			return;
		};
		
		var num=$("#bidd_no").val();
		var value=parseFloat(price)*parseFloat(num)
		 $("input[name='bidd_value']").val(value);
	}

	//金额=数量*单价
	function changebudg(thi){
		var num=$("#bidd_budgno").val();
		if(!num){
			
			$.ligerDialog.error('请输入数量');
			return;
		};
		
		var price=$("#bidd_budgprice").val();
		var value=parseFloat(price)*parseFloat(num)
		 $("input[name='bidd_budemoney']").val(value);
	}
	

	function saveValid(){
	    if($("form").valid()){
	        save();
	    }
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
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">资产名称</td>
			<td align="left" class="l-table-edit-td" ><input name="ass_name" type="text" id="ass_name" validate="{required:true}"/></td>
	        
	        <td align="right" class="l-table-edit-td" >采购预算选择</td>
			<td align="left" class="l-table-edit-td"><input name="bid_budgchose" type="text" id="bid_budgchose"  value="${bid_budgchose }" validate="{required:true}"/></td>
			<td align="left" ></td>
			
			<td align="right" class="l-table-edit-td">项目</td>			
			<td align="left" class="l-table-edit-td"><input name="proj_name" type="text" id="proj_name"  value="${proj_name }" validate="{required:true}"/></td>
			<td align="left"></td>
			
			
		</tr>
		 <tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">预算数量</td>
			<td align="left" class="l-table-edit-td"><input name="bidd_budgno" type="text" id="bidd_budgno"  value="${bidd_budgno }"  onchange="changebudg(this);" validate="{required:true}"/></td>
			
			<td align="right" class="l-table-edit-td">预算单价</td>
			<td align="left" class="l-table-edit-td"><input name="bidd_budgprice" type="number" id="bidd_budgprice" value="${bidd_budgprice }"  onchange="changebudg(this);" validate="{required:true}"/></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" >预算金额</td>
			<td align="left" class="l-table-edit-td"><input name="bidd_budemoney" type="number" id="bidd_budemoney" value="${bidd_budgprice }"  disabled="disabled"/></td>
			<td align="left"></td>
						
		</tr>
		<tr>
		    <td align="right" class="l-table-edit-td" style="padding-left: 10px;">品牌要求</td>
			<td align="left" class="l-table-edit-td"><input name="ass_brand" type="text" id="ass_brand"  value="${ass_brand }"  /></td>
			
			<td align="right" class="l-table-edit-td"> 功能要求</td>
			<td align="left" class="l-table-edit-td"><input name="bidd_budgfunction" type="text" id="bidd_budgfunction"  value="${bidd_budgfunction }"  /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td">预计到货日期</td>
			<td align="left" class="l-table-edit-td"><input name="bidd_budgreachdate" type="text" id="bidd_budgreachdate"  value="${bidd_budgreachdate }"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			<td align="left"></td>
			
		</tr>
		<tr>
		
		   <td align="right" class="l-table-edit-td" style="padding-left: 10px;">单价</td>
		   <td align="left" class="l-table-edit-td"><input name="bidd_price" type="number" id="bidd_price" value="${bidd_price }"  onchange="change(this);" validate="{required:true}"/></td>
		   
		   <td align="right" class="l-table-edit-td">数量</td>
		   <td align="left" class="l-table-edit-td"><input name="bidd_no" type="number" id="bidd_no" value="${bidd_no }"  onchange="change(this);" validate="{required:true}"/></td>
		   <td align="left"></td>
		   
		   <td align="right" class="l-table-edit-td">金额</td>
		   <td align="left" class="l-table-edit-td"><input name="bidd_value" type="number" id="bidd_value" value="${bidd_value }" disabled="disabled"/></td>
		   <td align="left"></td>

		</tr>
		
		<tr>
		   <td align="right" class="l-table-edit-td" style="padding-left: 10px;">评估信息</td>
		   <td align="left" class="l-table-edit-td"><input name="bidd_budgevaluation" type="text" id="bidd_budgevaluation"  value="${bidd_budgevaluation }"  /></td>
			
		   <td align="right" class="l-table-edit-td">生产厂家</td>
		   <td align="left" class="l-table-edit-td"><input name="fac_name" type="text" id="fac_name"  validate="{required:true}"   /></td>
		   <td align="left"></td>
            <td align="right" class="l-table-edit-td"><input name="detail_id" type="text" id="detail_id" value="${detail_id }" style="display: none"/></td>
		   <td align="left" class="l-table-edit-td"><input name="bid_id" type="text" id="bid_id" value="${bid_id }" style="display: none"/></td>
		
		</tr>
		
	</table>
</form>
</body>
</html>
