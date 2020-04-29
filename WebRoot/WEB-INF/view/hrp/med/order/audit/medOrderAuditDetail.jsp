<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridManager;
     var state = '${medOrderMain.state}';
     $(function (){
    	
    	loadDict()//加载下拉框  
 		loadHead();
 		queryDetail();
 		if('${medOrderMain.pur_type}'=='1'){
 			$('input:radio:first').attr('checked', 'checked');
 			$('#hos_name').hide();
 		}else{
 			$('input:radio:last').attr('checked', 'checked');
 			$('#hos_name').show();
 		}
 		
 		queryDetail();
/*  		var path = window.location.pathname+"/maingrid";
		var url = '../../../sys/querySysTableStyle.do?isCheck=false';
		loadColHeader({
			grid:grid,
			path:path,
			url:url
		}) */
     });
     
     function singleSel(){
    	 if($("input[type='radio']:checked").val() == '1'){
    		 $(".demo").attr("style","visibility:hidden");
    		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,"0",'160');//是否定向
			 changeDir();
    	 }else{
    		 $(".demo").attr("style","visibility:true");
    		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,"1",'160');//是否定向
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
 		//根据表字段进行添加查询条件		
     	grid.options.parms.push({name : 'order_id',value : $("#order_id").val()});      	
 		//加载查询条件
 		grid.loadData(grid.where);
     }
   //隐藏或显示  采购单位、请购单位、付款单位
     function change(){
     	singleSel();
     }  
  
    function loadDict(){
    	//字典下拉框
    	autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", false, false);//供应商
    	liger.get("sup_code").setValue('${medOrderMain.sup_id},${medOrderMain.sup_no}');
		liger.get("sup_code").setText('${medOrderMain.sup_code} ${medOrderMain.sup_name}');
		
    	autocomplete("#take_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.take_hos_id}');//收货单位
		autocomplete("#pay_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.pay_hos_id}');//付款单位
		autoCompleteByData("#order_type", medOrderMain_orderType.Rows, "id", "text", true, true, "", false, '${medOrderMain.order_type}');//订单类型
    	autocomplete("#stock_type_code", "../../queryMedStockType.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.stock_type_code}');//采购类型
    	autocomplete("#pay_code", "../../queryMedPayTerm.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.pay_code}');//付款方式
		autocomplete("#dept_code", "../../queryPurDept.do?isCheck=false", "id", "text", true, true, "", false, false);//采购部门
		liger.get("dept_code").setValue('${medOrderMain.dept_id},${medOrderMain.dept_no}');
		liger.get("dept_code").setText('${medOrderMain.dept_code} ${medOrderMain.dept_name}');
		
		autocomplete("#stocker", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.stocker}');//采购人
		autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,'',false,'${medOrderMain.is_dir}','160');//是否定向
		autocomplete("#dir_dept_id","../../queryMedDeptDict.do?isCheck=false","id","text",true,true);//定向科室	
		liger.get("dir_dept_id").setValue('${medOrderMain.dir_dept_id},${medOrderMain.dir_dept_no}');
		liger.get("dir_dept_id").setText('${medOrderMain.dir_dept_code} ${medOrderMain.dir_dept_name}');
		
        $("#order_code").ligerTextBox({width:160, disabled:true});
        $("#order_date").ligerTextBox({width:160});
        $("#arrive_date").ligerTextBox({width:160});//计划到货日期
        $("#sup_code").ligerTextBox({width:160});
        
        $("#order_type").ligerTextBox({width:160});
        $("#take_hos_id").ligerTextBox({width:160});
        $("#pay_hos_id").ligerTextBox({width:160});
        $("#dir_dept_id").ligerTextBox({width:160});
        $("#stock_type_code").ligerTextBox({width:160});
        $("#pay_code").ligerTextBox({width:160});
        
        $("#dept_code").ligerTextBox({width:160});
        $("#stocker").ligerTextBox({width:160});
        
        $("#arr_address").ligerTextBox({width:500});
        $("#note").ligerTextBox({width:160});
        
        if(state == 1){
    		$("#unAudit").ligerButton({click: unAudit, width:90, disabled:true});
        }else if(state ==2 ){
        	$("#audit").ligerButton({click: audit, width:90, disabled:true});
        }else{
        	$("#unAudit").ligerButton({click: unAudit, width:90, disabled:true});
        	$("#audit").ligerButton({click: audit, width:90, disabled:true});
        }
        
        $("#audit").ligerButton({click: audit, width:90});
		$("#unAudit").ligerButton({click: unAudit, width:90});
		$("#print").ligerButton({click: printDate, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
     } 
	
    function loadHead() {
    	grid = $("#maingrid").ligerGrid({
			columns : [{ display : '药品编码',name : 'inv_code',width : 150,align : 'left',
				},{display : '药品名称(E)',name : 'inv_name',width : 150,align : 'left'
				}, {display : '规格型号', name : 'inv_model', width : 150, align : 'left'
				}, {display : '计量单位', name : 'unit_name', width : 100, align : 'left'
				},{ display : '包装单位(E)', name : 'pack_code', textField : 'pack_name', width : 100, align : 'left',
					editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						url : '../../queryMedHosPackage.do?isCheck=false',
						keySupport : true,
						autocomplete : true,
					},
					render : function(rowdata, rowindex, value) {
						return rowdata.pack_name;
					} 
			 }, {display : '转换量(E)', name : 'num_exchange', width : 100, type : 'int',
					editor : { type : 'int'}, align : 'left',
					render : function(rowdata, rowindex, value) {
						return rowdata.num_exchange == null ? "" : formatNumber(rowdata.num_exchange, 2, 1);
					}
				}, {display : '包装数量(E)', name : 'num', width : 100, type : 'number',
					editor : {
						type : 'number',
					},
					align : 'right'
				}, {display : '采购数量(E)', name : 'amount',    width : 100, type : 'number',
					align : 'right',
					editor : { type : 'number', },
					render : function(rowdata, rowindex, value) {
						return rowdata.amount == null ? "" : formatNumber(rowdata.amount, 2, 1);
					}
				}, {display : '单价(E)', name : 'price', width : 100, align : 'right',
					editor : {
						type : 'numberbox',
						precision : '${p08006 }'
					},
					render : function(rowdata, rowindex, value) {
						rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
					}
				}, {display : '金额', name : 'amount_money', width : 100, type : 'number', align : 'right',
					render : function(rowdata, rowindex, value) {
						rowdata.amount_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
						return value == null ? "" : formatNumber(value, '${p08005 }', 1);
					},
				},{ display : '生产厂商', name : 'fac_name', align : 'left',width : 200
					
				}, { display : '备注', name : 'memo', align : 'left',width : 200,
					 editor : {
							type : 'text'
					 }
				}
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryMedOrderAuditDetail.do?isCheck=false',
			width : '100%',
			height : '95%',
			checkbox : false,
			enabledEdit : false,
			alternatingRow : true,
			isScroll : true,
			rownumbers : true,
			onsuccess:function(){
			
				//is_addRow();
			},
			delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ 
				          {text : '删除（<u>D</u>）',id : 'delete', click : remove, icon : 'delete'}
				        ]
			        }
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
    function btn_saveColumn(){
 		
		   var path = window.location.pathname+"/maingrid";
		   var url = '../../../sys/addBatchSysTableStyle.do?isCheck=false';
		   saveColHeader({
			   grid:grid,
			   path:path,
			   url:url,
			   callback:function(data){
				   $.ligerDialog.success("保存成功");
			   }
		   });
	  
	   return false;
 }
	//审核
    function audit(){
    	var ParamVo =[];
    	ParamVo.push(
    			'${medOrderMain.group_id}'   +"@"+ 
    			'${medOrderMain.hos_id}'   +"@"+ 
    			'${medOrderMain.copy_code}'   +"@"+ 
    			'${medOrderMain.order_id}' 
			) 
		$.ligerDialog.confirm('确定要审核?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("auditMedOrderAudit.do",{ParamVo : ParamVo.toString()},function (responseData){
					if(responseData.state=="true"){
						parent.query();
						$("#audit").ligerButton({click: audit, width:90, disabled:true});
						$("#unAudit").ligerButton({click: audit, width:90, disabled:false});
					}
				});
			}
		});	
    }
    //取消审核
    function unAudit(){
    	var ParamVo =[];
    	ParamVo.push(
    			'${medOrderMain.group_id}'   +"@"+ 
    			'${medOrderMain.hos_id}'   +"@"+ 
    			'${medOrderMain.copy_code}'   +"@"+ 
    			'${medOrderMain.order_id}' 
			) 
		$.ligerDialog.confirm('确定要销审?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("unAuditMedOrderAudit.do",{ParamVo : ParamVo.toString()},function (responseData){
					if(responseData.state=="true"){
						parent.query();
						$("#unAudit").ligerButton({click: unAudit, width:90, disabled:true});
						$("audit").ligerButton({click: unAudit, width:90, disabled:false});
					}
				});
			}
		});	
    }
	
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('A', audit);//审核
		hotkeys('U', unAudit);//取消审核
		//hotkeys('P', printDate);//打印
		hotkeys('C', this_close);//关闭
		hotkeys('D', remove);//删除
	}
	
	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	
	

	//打印
	function printDate(){
		
	var useId=0;//统一打印
		
		var useId=0;//统一打印
		if('${p08023 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08023 }'==2){
			//按仓库打印
			 if(liger.get("out_store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按调出仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("out_store_id").getValue().split(",")[0];
		}
		var para={
				order_id:$("#order_id").val(),
				template_code:'08006',
				use_id:useId
			};
			printTemplate("queryMedOrderAuditByPrintTemlate.do",para);
	}
	
	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		
		var useId=0;//统一打印
		if('${p08023 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08023 }'==2){
			//按仓库打印
			if(liger.get("out_store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按调出仓库打印，请选择仓库！');
				return;
			} 
			
			useId=liger.get("out_store_id").getValue().split(",")[0];
		}
		parent.parent.$.ligerDialog.open({url : 'hrp/med/order/audit/medOrderAuditPrintSetPage.do?template_code=08006&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		});
	}
	
    </script>
  
</head>
  
<body >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0">
	        <tr>
	            <td align="right" class="l-table-edit-td" >订单编号：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="order_id" type="hidden" id="order_id" value="${medOrderMain.order_id}" ltype="text" />
	            	<input name="order_code" type="text" id="order_code" readOnly value="${medOrderMain.order_code}" ltype="text" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>编制日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="order_date" id="order_date" required="true" value="${medOrderMain.order_date}" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td"><font color="red">*</font>供应商：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_code" type="text" id="sup_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>采购方式：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input id="typeA" name="pur_type" type="radio" value="1" onChange="change();"/>自购订单
	            	<input id="typeB" name="pur_type" type="radio" value="2" onChange="change();"/>统购订单
           		</td>
           		
           		<td align="right" class="l-table-edit-td demo"><font color="red">*</font>收货单位：</td>
				<td align="left" class="l-table-edit-td demo">
					<input name="take_hos_id" type="text" id="take_hos_id" required="false" ltype="text" />
				</td>	
								            
				<td align="right" class="l-table-edit-td demo" ><font color="red">*</font>付款单位：</td>
				<td align="left" class="l-table-edit-td demo">
					<input name="pay_hos_id" type="text" id="pay_hos_id" required="false" ltype="text"  />
				</td>
	        </tr> 
	        <tr>
	        	<td align="right" class="l-table-edit-td" ><font color="red">*</font>订单类型：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="order_type" type="text" id="order_type"  ltype="text" required="true" validate="{required:true,maxlength:20}"/>
	            </td>
	        	
	            <td align="right" class="l-table-edit-td">采购类型：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stock_type_code" type="text" id="stock_type_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	            
	        	<td align="right" class="l-table-edit-td" >付款条件：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="pay_code" type="text" id="pay_code"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	           
	        </tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td" >采购部门：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="dept_code" type="text" id="dept_code"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	            
	        	<td align="right" class="l-table-edit-td" >采购员：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stocker" type="text" id="stocker"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	            
	             <td align="right" class="l-table-edit-td" ><font color="red">*</font>计划到货日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="arrive_date" id="arrive_date" required="true" value="${medOrderMain.arrive_date}" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	     
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" >到货地址：</td>
	            <td align="left" class="l-table-edit-td" colspan="3">
	            	<input name="arr_address" type="text" id="arr_address" ltype="text"  value="${medOrderMain.arr_address}" validate="{required:true,maxlength:50}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >备注：</td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="note" type="text" id="note" ltype="text" value="${medOrderMain.note}" validate="{required:true,maxlength:50}" />
	            </td>
			</tr>
			<tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否定向：</td>
            	<td align="left" class="l-table-edit-td">
            		<input name="is_dir" type="text" id="is_dir" ltype="text" onChange="changeDir();"/>
            	</td>
            	
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
					<button id ="audit" accessKey="A"><b>审核（<u>A</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="unAudit" accessKey="U"><b>取消审核（<u>U</u>）</b></button>
					&nbsp;&nbsp;
				    <button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>模板打印（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
