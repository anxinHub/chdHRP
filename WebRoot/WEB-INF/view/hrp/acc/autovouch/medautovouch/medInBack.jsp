<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">

    var grid;
    var gridManager = null;
    var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
    var vouch_id=dialog.get("data").vouch_id;
    var busi_date_b=dialog.get("data").busi_date_b;
    var busi_date_e=dialog.get("data").busi_date_e;
    var business_no=dialog.get("data").business_no;
    var bus_type_code = dialog.get("data").bus_type_code;
    var store_id=dialog.get("data").store_id;
    var sup_id=dialog.get("data").sup_id;
    var bill_state=dialog.get("data").bill_state;
    
    $(function ()
    {
    	$(':button').ligerButton({width:80});
    	$("#busi_date_b").ligerTextBox({width:90 });
		$("#busi_date_e").ligerTextBox({width:90 });
    	$("#busi_date_b").val(dialog.get("data").busi_date_b);
    	$("#busi_date_e").val(dialog.get("data").busi_date_e);
    	$("#sup_his").ligerTextBox({width : 205});
    	$("#sup_hrp").ligerTextBox({width : 205});
    	
    	loadHead(null);	
    	
    	$("#busi_date_b").attr("disabled",true);
		$("#busi_date_e").attr("disabled",true);
		$("#busi_date_b").ligerTextBox({ disabled: true });
		$("#busi_date_e").ligerTextBox({ disabled: true });
    	/* if(vouch_id!="0"){
    		
    	}else{
    		grid.toggleCol('busi_date', false);
    		grid.toggleCol('hrp_store_name', false);
    		grid.toggleCol('med_type_name', false);
    		grid.toggleCol('business_no', false);
    	} */
    	query();
    });
    //查询
    function  query(){
    	
		grid.options.parms = [];
		var is_sup_ref=0;
    	if($("#is_sup_ref").attr("checked")){
    		is_sup_ref=1;
      	}

		grid.options.parms.push({name : 'vouch_id',value : vouch_id});
		grid.options.parms.push({name : 'sup_his',value : $("#sup_his").val()});
		grid.options.parms.push({name : 'sup_hrp',value : $("#sup_hrp").val()});
		grid.options.parms.push({name : 'is_sup_ref',value : is_sup_ref});
		grid.options.parms.push({name : 'busi_date_b',value : $("#busi_date_b").val()});
		grid.options.parms.push({name : 'busi_date_e',value : $("#busi_date_e").val()});
		grid.options.parms.push({name : 'bus_type_code',value :bus_type_code});
		grid.options.parms.push({name : 'store_id',value :store_id});
		grid.options.parms.push({name : 'sup_id',value :sup_id});
		if(bus_type_code=="080101" || bus_type_code=="080102"){
			grid.options.parms.push({name : 'bill_state',value :bill_state});
		}
		if(vouch_id!="0"){
			grid.options.parms.push({name : 'business_no',value :business_no});
		}
		
		grid.loadData(grid.where);//加载查询条件
		
     }
    
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
					columns : [
								{display : '单据号',id:'business_no',name : 'business_no',width: 150,align : 'left'},
								{display : '组合单号',id:'pay_no',name : 'pay_no',width: 150,align : 'left'},
								{display : '发票日期',name : 'bill_date',width: 80,align : 'left'},
								{display : '发票号',name : 'bill_no',width: 100,align : 'left'},
								{display : '药品进价',name : 'amount_money',width: 120,align : 'right',
									render:function(rowdata){
										return formatNumber(rowdata.amount_money,2,1);
							 	 	},totalSummary:{render:function(suminf, column, cell){
									return formatNumber(suminf.sum,2,1);
									
							 	 }}},
								{display : '业务日期',id:'busi_date',name : 'busi_date',width: 80,align : 'left'}, 
								{display : '库房名称',id:'hrp_store_name',name : 'hrp_store_name',width: 100,align : 'left'}, 
								{display : '药品类别',id:'med_type_name',name : 'med_type_name',width: 100,align : 'left'}, 
								{display : 'HIS供应商',name : 'his_sup_name',width: 250,align : 'left'}, 
								{display : 'HRP供应商',name : 'hrp_sup_name',width: 250,align : 'left'}
								
								
							],
					dataAction: 'server',dataType: 'server',usePager:false,url:'queryMedInBackDetail.do?isCheck=false',width: '100%', height: '100%', checkbox: false,
					rownumbers:true,delayLoad:true,selectRowButtonOnly:true//heightDiff: -10,
					
				});
	
		gridManager = $("#maingrid").ligerGetGridManager();
	}

    function myClose(){
		frameElement.dialog.close();
	}	
</script>

</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit">
   		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">业务日期：</td>
            <td align="left" class="l-table-edit-td">
	            <table style="font-size:15px">
					<tr>
						<td>
							<input class="Wdate" id="busi_date_b" name="busi_date_b"  onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input class="Wdate" id="busi_date_e" name="busi_date_e"  onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" />
						</td>
            		</tr>
				</table>
			</td>
			<td colspan="3"></td>
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">HIS供应商：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_his" type="text" id="sup_his" ltype="text" />
            </td>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">HRP供应商：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_hrp" type="text" id="sup_hrp" ltype="text" />
            </td>
            <td>
            	<input type="checkbox" id="is_sup_ref"/>没有对应供应商
	            <button accessKey="Q" onclick="query();"><b>查询（<u>Q</u>）</b></button>
	            <button accessKey="C" onclick="myClose();"><b>关闭（<u>C</u>）</b></button>
            </td>
        </tr> 
    </table>
    <div id="maingrid"></div>

</body>
</html>
