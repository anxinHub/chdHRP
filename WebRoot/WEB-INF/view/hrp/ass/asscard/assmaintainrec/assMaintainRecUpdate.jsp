<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    	<style type="text/css">
		html {
			overflow: auto !important;
		}
  		.divedline {
  			margin: 20px 0;
  		}
  	</style>
    <script type="text/javascript">
    var dataFormat;
    var grid;
	var gridManager = null;
    $(function (){
        loadDict();
        loadHead();
        
          if ('${state}' !=0) { 
          	toobarmanage = gridManager.toolbarManager; 
          	toobarmanage.setDisabled('saveDetail');
          	toobarmanage.setDisabled('save');
          	toobarmanage.setDisabled('delete');
          	toobarmanage.setDisabled('create');
  		}
        query ();
    });  
     
    
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		grid.options.parms.push({name : 'rec_id',value : $("#rec_id").val()});
		grid.loadData(grid.where);
 	}
  
   function loadHead(){
	   
   	grid = $("#maingrid").ligerGrid({
   		
          columns: [  
						{
							display : '资产卡片号',
							width: 150,
							name : 'ass_card_no',
							align : 'left',
							textField : 'ass_card_no',
							editor : {
								type : 'select',
								valueField : 'ass_card_no',
								textField : 'ass_card_no',
								selectBoxWidth : 500,
								selectBoxHeight : 240,
								grid : {
									columns : [ 
									     {
											 display : '资产卡片号',
											 name : 'ass_card_no',
											 align : 'left'
												},{
										display : '编码',
										name : 'ass_code',
										align : 'left'
									}, {
										display : '名称',
										name : 'ass_name',
										align : 'left'
									}, {
										display : '分类名称',
										name : 'ass_type_name',
										align : 'left'
									}, {
										display : '是否停用',
										name : 'is_stop',
										align : 'left'
									} ],
									switchPageSizeApplyComboBox : false,
									onSelectRow : f_onSelectRow_detail,
									url : '../assmaintainplan/choseAssCardNo.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue(),
									pageSize : 30
								},
								alwayShowInDown : true,
								keySupport : true,
								autocomplete : true,
								onSuccess : function() {
									this.parent("tr").next(
											".l-grid-row").find(
											"td:first").focus();
								}
							//
							},
							render : function(rowdata, rowindex,
									value) {
								return rowdata.ass_card_no;
							}
						
						},
						{ display: '资产编码', name: 'ass_code', width: 100,align: 'left' ,},
						{ display: '资产名称', name: 'ass_name',width: 100, align: 'left', },
						{ display: '型号', name: 'ass_mondl', width: 100,align: 'left' ,},
						{ display: '规格', name: 'ass_spec',width: 100, align: 'left' ,},
						{ display: '品牌', name: 'ass_brand',width: 100, align: 'left', }, 
					    { display: '生产厂家', name: 'fac_name', width: 100,align: 'left', },
					    { display: '保养费用', name: 'maintain_money', width: 100,align: 'right',editor : { type : 'text' },
					    	render: function(item)
				            {
				                    return formatNumber(item.maintain_money,'${ass_05005}',1);
				            } 
	
					    },
						{ display: '工时单位', name: 'maintain_unit', width: 100,align: 'left',editor : { type : 'text' }, },
					    { display: '保养工时', name: 'maintain_hours',width: 100, align: 'left',editor : { type : 'text' } ,},
	 				 	{ display: '保养项目', name: 'maintain_item_name',width: 100,

	                    	   align : 'left',
								textField : 'maintain_item_name',
								editor : {
									type : 'select',
									valueField : 'maintain_item_name',
									textField : 'maintain_item_code',
									selectBoxWidth : 300,
									selectBoxHeight : 240,
									grid : {
										columns : [ {
											display : '编码',
											name : 'maintain_item_code',
											align : 'left'
										}, {
											display : '名称',
											name : 'maintain_item_name',
											align : 'left'
										},  ],
										switchPageSizeApplyComboBox : false,
										onSelectRow : f_onSelectRow_detail,
										url : '../queryAssMaintainItemTable.do?isCheck=false',
										pageSize : 30,
										checkbox: true
									},
									alwayShowInDown : true,
									keySupport : true,
									autocomplete : true,
									onSuccess : function() {
										this.parent("tr").next(".l-grid-row").find("td:first").focus();
									}
								//
								},
								render : function(rowdata, rowindex,
										value) {
									return rowdata.maintain_item_name;
								}
	  					 		}			
						],
						dataAction: 'server',dataType: 'server',usePager:true,url:'../assmaintainrecitem/queryAssMaintainRecItem.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue(),
						width : '100%',
						height : '100%',
						checkbox : true,
						enabledEdit :'${state}'!=0?false:true,
						alternatingRow : true,
						onBeforeEdit : f_onBeforeEdit,
						onBeforeSubmitEdit : f_onBeforeSubmitEdit,
						onAfterEdit : f_onAfterEdit,
						isScroll : true,
						checkbox : true,
						rownumbers : true,
						delayLoad:true,
						//notCellEditByColName:"ass_card_no", //资产卡片是主键不能进行修改
						selectRowButtonOnly : true,//heightDiff: -10,
                  toolbar : {
	 							items : [ 	
			{
										text : '关闭',
										id : 'close',
										click : this_close,
										icon : 'candle'
									} 
							        ]
						}
                  });

       gridManager = $("#maingrid").ligerGetGridManager();
       
    }
   
   var rowindex_id = "";
   
 	var column_name="";
 	
 	function f_onBeforeEdit(e) {
 		
 		rowindex_id = e.rowindex;
 		
 		clicked = 0;
 		
 		column_name=e.column.name;
 		
 	}
 	
 	//选中回充数据
  	function f_onSelectRow_detail(data, rowindex, rowobj) {
  		
  		selectData = "";
  		
  		selectData = data;
  		
  		//alert(JSON.stringify(data)); 
  		if(column_name == "ass_card_no"){
 			
 			if (selectData != "" || selectData != null) {
 				
 				//回充数据 
 				//grid.updateCell('apply_emp', 100, e.record);
 				grid.updateRow(rowindex_id, {
 					
 					ass_card_no : data.ass_card_no,
 					
 					ass_code : data.ass_code,
 					
 					ass_name : data.ass_name,
 					
 					fac_id : data.fac_id,
 					
 					fac_no : data.fac_no,
 					
 					fac_code : data.fac_code,
 					
 					fac_name : data.fac_name,
 					
 					ass_mondl : data.ass_mondl,
 					
 					ass_spec : data.ass_spec
 					
 				});

 			}
 		} 
		
	if(column_name == "maintain_item_name"){
 			
 			
 			if (selectData != "" || selectData != null) {
 				
 				//回充数据 
 				//grid.updateCell('apply_emp', 100, e.record);
 				grid.updateRow(rowindex_id, {
 					
 					maintain_item_code : data.maintain_item_code,
 					
 					maintain_item_name : data.maintain_item_name,
 				});

 			}
 		}
  		return true;
  	}
 	
   	function f_onSelectRow(data, rowindex, rowobj) {
   		return true;
   	}
   	
   	// 编辑单元格提交编辑状态之前作判断限制
   	function f_onBeforeSubmitEdit(e) {
   		return true;
   	}
   	
   	// 跳转到下一个单元格之前事件
   	function f_onAfterEdit(e) {
   		return true;
   	}
   	
   	function is_addRow() {
  		setTimeout(function() { //当数据为空时 默认新增一行
  			grid.addRow();
  		}, 1000);

  	}
	function this_close() {
		frameElement.dialog.close();
	}


  
    
    
   
    function loadDict(){
        //字典下拉框
        
        //资产性质
    	 $("#ass_nature").ligerComboBox({
            	url: '../queryAssNaturs.do?isCheck=false',
            	valueField: 'id',
             	textField: 'text', 
             	selectBoxWidth: 160,
            	autocomplete: true,
            	width: 160,
            	onSelected :function(id,text){ 
            		loadHead();
            		is_addRow();
            	}
   		  });
        
    	liger.get("ass_nature").setValue("${ass_nature}");
    	
    	liger.get("ass_nature").setText("${nature_name}");
    	
    	//维修部门
    	autocomplete("#maintain_dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true,"",false,"${maintain_dept_id}@${maintain_dept_no}");
    	
    	liger.get("maintain_dept_id").setValue("${maintain_dept_id}@${maintain_dept_no}");
    	
    	liger.get("maintain_dept_id").setText("${maintain_dept_code} ${maintain_dept_name}");
    	      
     	//计划执行人
		autocomplete("#fact_exec_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,"",false,"${fact_exec_emp}");
    	
    	liger.get("fact_exec_emp").setValue("${fact_exec_emp}");
    	
    	liger.get("fact_exec_emp").setText("${fact_exec_emp_name}"); 
    	
    	//计划Id
    	autocomplete("#plan_id","../queryAssMaintainPlanDict.do?isCheck=false", "id","text", true, true,"",false,"${plan_id}");
    	
		liger.get("plan_id").setValue("${plan_id}");
    	
    	liger.get("plan_id").setText("${plan_no} ${plan_name}"); 
    	  
    	$("#rec_no").ligerTextBox({disabled:true,cancelable: false,width:160});
    	
    	$("#audit_date").ligerTextBox({disabled:true,cancelable: false,width:160});
        
        $("#ass_year").ligerTextBox({width:160});
        
        $("#ass_month").ligerTextBox({width:160});
        
        $("#plan_exec_date").ligerTextBox({width:160});
        
        $("#fact_exec_date").ligerTextBox({width:160});
        
        $("#maintain_hours").ligerTextBox({width:160});
        
        $("#maintain_money").ligerTextBox({width:160});
        
     }   
    </script>
  
  </head>
  
   <body onload="is_addRow()" >
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <input type="hidden" id="rec_id" name="rec_id" value="${rec_id }"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>保养序号：</td>
            <td align="left" class="l-table-edit-td"><input name="rec_no" disabled="disabled" type="text" id="rec_no"  value="${rec_no}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计年度：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_year" type="text" id="ass_year"  value="${ass_year}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计月份：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_month" type="text" id="ass_month"  value="${ass_month}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>保养计划：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_id" type="text" id="plan_id"  value="${plan_id}"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>保养部门：</td>
            <td align="left" class="l-table-edit-td"><input name="maintain_dept_id" type="text" id="maintain_dept_id"  value="${maintain_dept_id}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"  value="${ass_nature}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>执行人：</td>
            <td align="left" class="l-table-edit-td"><input name="fact_exec_emp" type="text" id="fact_exec_emp"  value="${fact_exec_emp}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划执行日期：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_exec_date" type="text" id="plan_exec_date"  value="${plan_exec_date}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>实际执行日期：</td>
            <td align="left" class="l-table-edit-td"><input name="fact_exec_date" type="text" id="fact_exec_date"  value="${fact_exec_date}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>保养工时：</td>
            <td align="left" class="l-table-edit-td"><input name="maintain_hours" type="text" id="maintain_hours"  value="${maintain_hours}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>保养费用：</td>
            <td align="left" class="l-table-edit-td"><input name="maintain_money" type="text" id="maintain_money"  value="${maintain_money}"  /></td>
            <td align="left"></td>
        </tr>
       <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">保养说明：</td>
             <td align="left" class="l-table-edit-td" colspan="4">
            	<textarea rows="3" cols="70"  name="maintain_desc"  id="maintain_desc"  >${maintain_desc}</textarea>
            </td>
             <td align="left"></td>
        </tr>
			
        </table>
    <div id="maingrid"></div>
    </body>
</html>
