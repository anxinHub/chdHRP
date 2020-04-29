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
	var ForR = [{ id: 0, text: '未完成' }, { id: 1, text: '已完成'}];
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
        query();
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
							name : 'ass_card_no',
							width: 150,
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
										display : '编码',
										name : 'ass_card_no',
										align : 'left',width:'150'
									},  {
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
							},
							render : function(rowdata, rowindex,
									value) {
								return rowdata.ass_card_no;
							}

						},
						{ display: '资产编码', name: 'ass_code', width: 100,align: 'left', editor : { type : 'text' }
					 		},
                        { display: '资产名称', name: 'ass_name',width: 100, align: 'left', editor : { type : 'text' }
					 		},
                        { display: '型号', name: 'ass_mondl',width: 100, align: 'left', editor : { type : 'text' }
					 		},
                        { display: '规格', name: 'ass_spec', width: 100,align: 'left', editor : { type : 'text' }
					 		},
                        { display: '品牌', name: 'ass_brand', width: 100,align: 'left', editor : { type : 'text' } 
					 		},
					    { display: '生产厂家', name: 'fac_name', width: 100,align: 'left', editor : { type : 'text' }
					 	    },
                       /*  { display: '计量结果', name: 'measure_result', width: 100,
					 	    	editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									data : ForR,
									keySupport : true,
									autocomplete : true,
									onSuccess : function(data) {
										if (initvalue != undefined
												&& initvalue != "") {
											this.setValue(initvalue);
											initvalue = "";
										}
									}
								},
			                    render: function (item)
			                    {
			                        if (parseInt(item.measure_result) == 1) {
			                        	return '已完成';
			                        }else if(parseInt(item.measure_result) == 0){
			                        	return '未完成';
			                        }else{
			                        	return "";
			                        }
			                    },
					 	    	align: 'left'
 					 		}, */
 		                { display: '计量说明', name: 'measure_memo',width: 100, align: 'left', editor : { type : 'text'  }
 	 				 	    },
 	 	                { display: '处理意见', name: 'measure_idea', width: 100,align: 'left', editor : { type : 'text' }
 						    },
 		             
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'../assmeasurerecdetail/queryAssMeasureRecDetail.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue(),
                      width : '100%',
 						height : '100%',
 						checkbox : true,
 						enabledEdit : '${state}' !=0?false:true,
 						alternatingRow : true,
 						onBeforeEdit : f_onBeforeEdit,
 						onBeforeSubmitEdit : f_onBeforeSubmitEdit,
 						onAfterEdit : f_onAfterEdit,
 						isScroll : true,
 						checkbox : true,
 						rownumbers : true,
 						delayLoad:true,
 						//notCellEditByColName:"ass_card_no", //资产卡片是主键不能进行修改 （主键进行添加完成后，不能进行修改）
 						selectRowButtonOnly : true,//heightDiff: -10,
                    toolbar : {
 	 							items : [ {
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
	function this_close() {
		frameElement.dialog.close();
	}
	
   	//选中回充数据
    	function f_onSelectRow_detail(data, rowindex, rowobj) {
    		
    		selectData = "";
    		
    		selectData = data;
    		
    		if(column_name == "ass_card_no"){
     			
     			if (selectData != "" || selectData != null) {
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
   
    
	    function loadDict(){
            //字典下拉框
    	//资产性质
      	autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false", "id","text", true, true,"",false,"${ass_nature}");
            
    	liger.get("ass_nature").setValue("${ass_nature}");
    	
    	liger.get("ass_nature").setText("${nature_name}");
    	liger.get("ass_nature").setDisabled();
            
      //计量计划ID
      	autocomplete("#plan_id", "../queryAssMeasurePlanDict.do?isCheck=false", "id","text", true, true,"",false,"${plan_id}");
      
    	liger.get("plan_id").setValue("${plan_id}");
    	
    	liger.get("plan_id").setText("${plan_name}");
        liger.get("plan_id").setDisabled();
        //内部计量部门
        autocomplete("#inner_measure_dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true,"",false,"${inner_measure_dept_id}@${inner_measure_dept_no}");
        
        //外部计量员
    	autocomplete("#outer_measure_engr","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,"",false,"${outer_measure_engr}");
        
        //内部计量员
    	autocomplete("#inner_measure_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,"",false,"${inner_measure_emp}");
        
        //经办人
    	autocomplete("#deal_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,"",false,"${deal_emp}");
        
    	//检测方式
    	/* $("#measure_kind").ligerComboBox({
    		
    		width : 160
    		
    	}); */
    	$('#measure_kind').ligerComboBox({
 			data:[{id:1,text:'手工检测'},{id:0,text:'机器检测'}],
 			valueField: 'id',
             textField: 'text',
 			cancelable:true,
 			width:160
 		});
    	
    	liger.get("measure_kind").setValue("${measure_kind}");
    	
    	//计量类别
    	/* $("#measure_type").ligerComboBox({
    		
    		width : 160
    		
    	}); */
    	 $('#measure_type').ligerComboBox({
  			data:[{id:0,text:'一级类别'},{id:1,text:'二级类别'}],
  			valueField: 'id',
              textField: 'text',
  			cancelable:true,
  			width:160
  		});
    	
    	liger.get("measure_type").setValue("${measure_type}");
    	
    	//处理结果
    	/* $("#measure_result").ligerComboBox({
    		
    		width : 160
    		
    	}); */
    	 $('#measure_result').ligerComboBox({
	 			data:[{id:1,text:'未完成'},{id:0,text:'完成'}],
	 			valueField: 'id',
	             textField: 'text',
	 			cancelable:true,
	 			width:160
	 		});
    	
    	liger.get("measure_result").setValue("${measure_result}");
    	
    	 $("#seq_no").ligerTextBox({disabled:true,cancelable: false,width:160});
    	 
         $("#cert_no").ligerTextBox({width:160});
         
         $("#plan_measure_date").ligerTextBox({width:160});
         
         $("#fact_measure_date").ligerTextBox({width:160});
         
         $("#pre_next_date").ligerTextBox({width:160});
         
         $("#measure_hours").ligerTextBox({width:160});
         
         $("#other_money").ligerTextBox({width:160});
         
         $("#measure_money").ligerTextBox({width:160});
         
         $("#measure_memo").ligerTextBox({width:160});
         
         $("#outer_measure_org").ligerTextBox({width:160});
    	
	    }  
    </script>
  
  </head>
  
   <body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
   
        <input type="hidden" id="rec_id" name="rec_id" value="${rec_id}"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>计量记录编号：</td>
            <td align="left" class="l-table-edit-td"><input name="seq_no" type="text" id="seq_no"  value="${seq_no}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"  value="${ass_nature}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>计划：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_id" type="text" id="plan_id"  value="${plan_id}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>内部计量部门：</td>
            <td align="left" class="l-table-edit-td"><input name="inner_measure_dept_id" type="text" id="inner_measure_dept_id"  value="${inner_measure_dept_id}"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部计量员：</td>
            <td align="left" class="l-table-edit-td"><input name="outer_measure_engr" type="text" id="outer_measure_engr"  value="${outer_measure_engr}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">内部计量员：</td>
            <td align="left" class="l-table-edit-td"><input name="inner_measure_emp" type="text" id="inner_measure_emp"  value="${inner_measure_emp}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划计量日期：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_measure_date" type="text" id="plan_measure_date"  value="${plan_measure_date}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">实际计量日期：</td>
            <td align="left" class="l-table-edit-td"><input name="fact_measure_date" type="text" id="fact_measure_date"  value="${fact_measure_date}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">预计下次日期：</td>
            <td align="left" class="l-table-edit-td"><input name="pre_next_date" type="text" id="pre_next_date"  value="${pre_next_date}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>计量工时：</td>
            <td align="left" class="l-table-edit-td"><input name="measure_hours" type="text" id="measure_hours"  value="${measure_hours}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>计量费用：</td>
            <td align="left" class="l-table-edit-td"><input name="measure_money" type="text" id="measure_money"  value="${measure_money}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>其他费用：</td>
            <td align="left" class="l-table-edit-td"><input name="other_money" type="text" id="other_money"  value="${other_money}"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量证书号：</td>
            <td align="left" class="l-table-edit-td"><input name="cert_no" type="text" id="cert_no"  value="${cert_no}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量说明：</td>
            <td align="left" class="l-table-edit-td"><input name="measure_memo" type="text" id="measure_memo"  value="${measure_memo}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>检测方式：</td>
            <td align="left" class="l-table-edit-td">
            			<!-- <select id="measure_kind" name="measure_kind"> 
			            			<option value=""></option>
			                		<option value="0">机器检测</option>
			                		<option value="1">手工检测</option>
			                	</select> -->
			 <input name="measure_kind" type="text" id="measure_kind" />	                	
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部计量单位：</td>
            <td align="left" class="l-table-edit-td"><input name="outer_measure_org" type="text" id="outer_measure_org"  value="${outer_measure_org}"  /></td>
            <td align="left"></td>
        </tr> 
      <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">经办人：</td>
            <td align="left" class="l-table-edit-td"><input name="deal_emp" type="text" id="deal_emp"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>计量类别：</td>
            <td align="left" class="l-table-edit-td">
            			 <!-- <select id="measure_type" name="measure_type"> 
			            			<option value=""></option>
			                		<option value="0">一级类别</option>
			                		<option value="1">二级类别</option>
			                	</select> -->
			              <input name="measure_type" type="text" id="measure_type" />      	
            </td>
            <td align="left"></td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>计量结果：</td>
            <td align="left" class="l-table-edit-td">
            			<!-- <select id="measure_result" name="measure_result"> 
			            			<option value=""></option>
			                		<option value="0">完成</option>
			                		<option value="1">未完成</option>
			                	</select> -->
			              <input name="measure_result" type="text" id="measure_result" /> 	   	
            </td>
            <td align="left"></td>
       </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">处理意见：</td>
            <td align="right" class="l-table-edit-td" colspan="5">
            		<textarea rows="3" cols="70"  name="measure_idea"  id="measure_idea"  >${measure_idea }</textarea>
            </td><td align="left"></td>
        </tr> 
			
        </table>
    
       <div id="maingrid"></div>
    </body>
</html>
