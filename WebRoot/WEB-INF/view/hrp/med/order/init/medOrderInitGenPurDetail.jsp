<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jsp:include page="${path}/inc.jsp"/-->
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridManager;
     
     $(function (){
    	 loadDict();//加载下拉框
         //loadForm();
         loadHead(null);//加载数据
         
         if('${pur_type}'=='1'){
 			$('input:radio:first').attr('checked', 'checked');
 			$('#hos_name').hide();
 		}else{
 			$('input:radio:last').attr('checked', 'checked');
 			$('#hos_name').show();
 		}
		 queryDetail();
     });  
     
     function singleSel(){
       	 if($("input[type='radio']:checked").val() == '1'){
       		 $('#hos_name').hide();
       		 //统购计划中，请购单位为当前单位，并且不可编辑
       		 
       		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"${is_dir}",false,"0",'160');//是否定向
    			 changeDir();
       	 }else{
       		 $('#hos_name').show();
       		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"${is_dir}",false,"1",'160');//是否定向
       		 $("#req_hos_id").ligerComboBox({disabled:true,cancelable: false});
       		 liger.get("req_hos_id").setValue("${req_hos_id}");
      		 liger.get("req_hos_id").setText("${req_hos_code} ${req_hos_name}");
       		 changeDir();
       	 }
        }
    	//选择定向与非定向是否显示定向科室
        function changeDir(){
    		 if(liger.get("is_dir").getValue()=='1'){
    			 $(".dept").attr("style","visibility:true");
    		 }else{
    			 $(".dept").attr("style","visibility:hidden");
    		 }
    	 }
   
     function queryDetail(){
    	
        	 grid.options.parms = [];
     		 grid.options.newPage = 1;
     			
     		 grid.options.parms.push({name : 'pur_id',value : '${pur_id}'}); 
     		 //加载查询条件
     		 grid.loadData(grid.where);
        
     }
     
    function loadDict(){
    	//字典下拉框
		autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true);//编制科室下拉框	
		liger.get("dept_id").setValue("${dept_id},${dept_no}");
		liger.get("dept_id").setText("${dept_code} ${dept_name}");
		
		autocomplete("#pur_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true,"",false,"${pur_hos_id}");//采购单位下拉框 
		liger.get("pur_hos_id").setValue("${pur_hos_id}");
		liger.get("pur_hos_id").setText("${pur_hos_name}");
		
		autocomplete("#req_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true,"",true,"${req_hos_id}");//请购单位下拉框 
		liger.get("req_hos_id").setValue("${req_hos_id}");
		liger.get("req_hos_id").setText("${req_hos_name}");
		
		autocomplete("#pay_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true,"",false,"${pay_hos_id}");//付款单位下拉框 
		liger.get("pay_hos_id").setValue("${pay_hos_id}");
		liger.get("pay_hos_id").setText("${pay_hos_name}");
		
		autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,"${is_dir}",'160');//是否定向
		$("#req_hos_id").ligerComboBox({disabled:true,cancelable: false});
		autocomplete("#dir_dept_id","../../queryMedDeptDict.do?isCheck=false","id","text",true,true,"",false,false);//定向科室	
		liger.get("dir_dept_id").setValue("${dir_dept_id},${dir_dept_no}");
		liger.get("dir_dept_id").setText("${dir_dept_code} ${dir_dept_name}");
		
		$("#pur_code").ligerTextBox({width:160});
		$("#dept_id").ligerTextBox({width:160});
		$("#make_date").ligerTextBox({width:160});
		$("#arrive_date").ligerTextBox({width:160});
		$("#brif").ligerTextBox({width:160});
		
		//$("#print").ligerButton({click: printDate, width:90});
		$("#close").ligerButton({click: this_close, width:90});
		
		
     } 
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{display: '药品编码', name: 'inv_code', align: 'left', width: '120',
							totalSummary: {
			                    type: 'sum',
			                    render: function (suminf, column, cell) {
			                        return '<div>合计</div>';
			                    }
			                }	
						}, 
					  {display: '药品名称', name: 'inv_name', align: 'left',width: '120'}, 
					  {display: '规格型号', name: 'inv_model', align: 'left',width: '120'}, 
					  {display: '计量单位', name: 'unit_name', align: 'left', width: '80'}, 
					  {display: '包装单位(E)', name: 'pack_name', align: 'left', width: '80'}, 
					  {display: '当前库存', name: 'cur_amount', align: 'right', width: '80',
						  render : function(rowdata, rowindex, value) {
							  return formatNumber(rowdata.cur_amount ==null ? 0 : rowdata.cur_amount,2,1);
							}  
					  },
					  {display: '计划数量', name: 'req_amount', align: 'right', width: '80',
						  render : function(rowdata, rowindex, value) {
							  return formatNumber(rowdata.req_amount ==null ? 0 : rowdata.req_amount,2,1);
							}   
					  },
					  {display: '包装数量(E)', name: 'num', align: 'right', width: '80',
						  render : function(rowdata, rowindex, value) {
							  return formatNumber(rowdata.num ==null ? 0 : rowdata.num,2,1);
							}   
					  },
					  {display: '采购数量(E)', name: 'amount', align: 'right', width: '80',
						  render : function(rowdata, rowindex, value) {
							  return formatNumber(rowdata.amount ==null ? 0 : rowdata.amount,2,1);
							}   
					  },
					  {display: '单价(E)', name: 'price', align: 'right', width: '80',
						  render : function(rowdata, rowindex, value) {
							  return formatNumber(rowdata.price ==null ? 0 : rowdata.price,'${p08006 }',1);
							}   
					  }, 
					  {display: '金额', name: 'amount_money', align: 'right', width: '80',
						  render:function(rowdata){
							  return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money,'${p08005 }',1);
			             },
			             totalSummary: {
		                        type: 'sum',
		                        render: function (suminf, column, cell) {
		                            return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,'${p08005 }',1)+ '</div>';
		                        }
		                    }  
					  }, 
					  {display: '供应商', name: 'sup_name', align: 'left',width: '150'}, 
					  {display: '生产厂商', name: 'fac_name', align: 'left'	,width: '120'}, 
					  {display: '备注', name: 'memo', align: 'left',width: '120'}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryOrderInitGenPurDetail.do?isCheck=false',
			width: '95%', height: '67%', checkbox: false, rownumbers:true,isScroll : true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
	function printDate(){
	}

	function this_close(){
		frameElement.dialog.close();
	}
	
    </script>
  
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>计划单号：</td>
				<td align="left" class="l-table-edit-td">
					<input name="pur_code" type="text" id="pur_code" ltype="text" validate="{required:true,maxlength:20}" value="${pur_code}" disabled="disabled" /></td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>编制科室：</td>
				<td align="left" class="l-table-edit-td">
					<input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>编制日期：</td>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="make_date" type="text" id="make_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
					validate="{required:true,maxlength:20}" value="${make_date}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>计划类型：</td>
				<td align="left" class="l-table-edit-td">
					<input id="planA" name="planType" type="radio" value="1" onclick="singleSel()">自购计划
					<input id="planB" name="planType" type="radio" value="2" onclick="singleSel()">统购计划</td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘要：</td>
				<td align="left" class="l-table-edit-td" colspan="2">
					<input name="brif" type="text" id="brif" ltype="text" value="${brif}" />
				</td>
					
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>计划到货日期：</td>
            	<td align="left" class="l-table-edit-td">
            		<input class="Wdate" name="arrive_date" type="text" id="arrive_date" ltype="text"  value="${arrive_date}"
            		onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}"/>
            	</td>
            	<td align="left"></td>
			</tr>

			<tr id="hos_name">
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>采购单位：</td>
				<td align="left" class="l-table-edit-td">
					<input name="pur_hos_id" type="text" id="pur_hos_id" ltype="text" />
				</td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>请购单位：</td>
				<td align="left" class="l-table-edit-td">
					<input name="req_hos_id" type="text" id="req_hos_id" ltype="text" readonly="readonly" />
				</td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><font color="red" size="2">*</font>付款单位：</td>
				<td align="left" class="l-table-edit-td">
					<input name="pay_hos_id" type="text" id="pay_hos_id" ltype="text" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否定向：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="is_dir" type="text" id="is_dir" ltype="text" onChange="changeDir();"/>
	            </td>
	            <td align="left"></td>
	            
	            <td align="right" class="l-table-edit-td dept"  style="padding-left:20px;">定向科室：</td>
	            <td align="left" class="l-table-edit-td dept" >
	            	<input name="dir_dept_id" type="text" id="dir_dept_id" ltype="text" />
	            </td>
	            <td align="left"></td>
        	</tr>
		</table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<!-- <button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; -->
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
