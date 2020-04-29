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
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridManager = null
     $(function (){
         loadDict();//加载下拉框
         loadHead(null);
         
     });  
     
     function  query(){
  		grid.options.parms=[];
  		grid.options.newPage=1;
  		grid.options.parms.push({name : 'repair_rec_id',value : $("#repair_rec_id").val() == ""?"0":$("#repair_rec_id").val()});
  		grid.loadData(grid.where);
   }
     function loadHead(){
     	grid = $("#maingrid").ligerGrid({
            columns: [  
                      { display: '材料编码', name: 'inv_code', align: 'left',width:200, editor : { type : 'text' }
 					 		},
                      { display: '材料名称', name: 'inv_name', align: 'left', editor : { type : 'text' },width:200
 					 		},
 		              { display: '材料单价', name: 'inv_price', align: 'left', width: 200,editor : { type : 'numberbox',precision : 2  }
 	 				 	    },
 	                  { display: '材料数量', name: 'inv_num', align: 'left', width: 200,editor : { type : 'int' }
 	 				 	    }, 	
 	 				  { display: '费用', name: 'sutff_money', align: 'right',width: 200,
 	 				 	    	
					 			render : function(rowdata, rowindex, value) {
									rowdata.stuff_money = formatNumber(rowdata.inv_price * rowdata.inv_num);
										return formatNumber(
												rowdata.stuff_money== null ? 0
														: rowdata.stuff_money,
												'${ass_05005}',
												1);
									
									}
	 				 	    }, 
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'../assrepairrecdetail/queryAssRepairRecDetail.do',
                      width : '100%',
						height : '100%',
						checkbox : true,
						enabledEdit : true,
						alternatingRow : true,
						onBeforeEdit : f_onBeforeEdit,
						onBeforeSubmitEdit : f_onBeforeSubmitEdit,
						onAfterEdit : f_onAfterEdit,
						isScroll : true,
						checkbox : true,
						rownumbers : true,
						delayLoad:true,
						selectRowButtonOnly : true,//heightDiff: -10,
                    toolbar : {
							items : [ 
							          { text : '保存', id : 'save', click : save, icon : 'save' }, { line : true },
									  { text : '删除', id : 'delete', click : itemclick, icon : 'delete' }, { line : true },
									  {text : '引入资产卡片',id : 'create',click : itemclick,icon : 'save'}, {line : true}, 
									
							
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
   	
 // 编辑单元格提交编辑状态之前作判断限制
  	function f_onBeforeSubmitEdit(e) {
  		return true;
  	}
  	// 跳转到下一个单元格之前事件
  	function f_onAfterEdit(e) {
  		gridManager.updateCell('sutff_money', e.record.inv_price * e.record.inv_num, e.record); 
  	}
  	function itemclick(item) {
 		if (item.id) {
 			switch (item.id) {
 			case "delete":
 				var data = gridManager.getCheckedRows();
 				if (data.length == 0) {
 					$.ligerDialog.error('请选择行');
 				} else {
 					var ParamVo = [];
 					var i = 0;
 					$(data).each(
 							function() {
 								if(isnull(this.rec_detail_id)){
 									gridManager.deleteSelectedRow();
								}else{
 									ParamVo.push(
 											this.group_id + "@" + 
 											this.hos_id + "@" + 
 											this.copy_code + "@"+
 											this.repair_rec_id + "@" + 
 											this.rec_detail_id 
 										);
								}
 								i++;
 							});
 					if(ParamVo == ""){
 						is_addRow();
						return;
					}
 					$.ligerDialog.confirm('确定删除?', function(yes) {
 						if (yes) {
 							ajaxJsonObjectByUrl("../assrepairrecdetail/deleteAssRepairRecDetail.do", {
 								ParamVo : ParamVo.toString()
 							}, function(responseData) {
 								if (responseData.state == "true") {
 									query();
 									is_addRow();
 								}
 							});
 						}
 					});
 				}
 				return;
 			case "create":
				//grid.deleteSelectedRow();
		            var fn = $.ligerui.getPopupFn({
		            	 onSelect: function (e) {
		            		 
			                    grid.addRows(e.data);
			                    grid.moveRange(e.data,0)
			                },
		                grid: {
		                    columns: [

		      						{ display : '资产卡片号', name : 'ass_card_no', align : 'left', },
		      						
		      						{ display: '资产编码', name: 'ass_code', align: 'left', },
		      						
		      						{ display: '资产名称', name: 'ass_name', align: 'left', },
		      						 
		      						{ display: '型号', name: 'ass_mondl', align: 'left',  },
		      						
		      						{ display: '规格', name: 'ass_spec', align: 'left', },
		      						
		      						{ display: '品牌', name: 'ass_brand', align: 'left', },
		      						
		      						{ display: '生产厂家', name: 'fac_name', align: 'left', },
		      						
		                           ],
		                           dataAction: 'server',dataType: 'server',usePager:true,url:'../assmaintainplan/choseAssCardNo.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue(),
		                           width: '100%', height: '100%', checkbox: true,rownumbers:true,
		                } 
		            });

		            fn();
 			}
 		}
 	}
  	
  	function is_addRow() {
  		
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);

	}
  	
     function  save(){
    	 
		if ($("#ass_year").val() == "" || $("#ass_year").val() == "undefined"){
	    		 $.ligerDialog.error('统计年度不能为空');
	    		 return ;
	    		 
    	 }
    	 
    	 if ($("#ass_month").val() == "" || $("#ass_month").val() == "undefined"){
	    		 $.ligerDialog.error('统计月份不能为空');
	    		 return ;
    	 }
    	 
    	 if (liger.get("is_contract").getValue() == "" || liger.get("is_contract").getValue() == "undefined"){
    		 $.ligerDialog.error('是否合同不能为空');
    		 return ;
	       }
    	 
    	 if (liger.get("fixed_dept_id").getValue() == "" || liger.get("fixed_dept_id").getValue() == "undefined"){
	    		 $.ligerDialog.error('维修部门不能为空');
	    		 return ;
    	 }
    	 
    	 if (liger.get("ass_nature").getValue() == "" || liger.get("ass_nature").getValue() == "undefined"){
	    		 $.ligerDialog.error('资产性质不能为空');
	    		 return ;
    	 }
    	 
    	 if (liger.get("ass_card_no").getValue() == "" || liger.get("ass_card_no").getValue() == "undefined"){
	    		 $.ligerDialog.error('资产卡片号不能为空');
	    		 return ;
    	 }
    	 
    	 if (liger.get("is_inner").getValue() == "" || liger.get("is_inner").getValue() == "undefined"){
	    		 $.ligerDialog.error('是否内部维修不能为空');
	    		 return ;
	    		 
    	 }
    	 
 		if (liger.get("trouble_level").getValue() == "" || liger.get("trouble_level").getValue() == "undefined"){
		    		 $.ligerDialog.error('故障级别不能为空');
		    		 return ;
		    		 
    	 }
    	 
    	 if (liger.get("repair_level").getValue() == "" || liger.get("repair_level").getValue() == "undefined"){
	    		 $.ligerDialog.error('维修级别不能为空');
	    		 return ;
	    		 
    	 }
    	 
 		if ($("#repair_date").val() == "" || $("#repair_date").val() == "undefined"){
		   		 $.ligerDialog.error('维修日期不能为空');
		   		 return ;
		   		 
   		 }
  	 	 
  	 	 if ($("#repair_engr").val() == "" || $("#repair_engr").val() == "undefined"){
		   		 $.ligerDialog.error('维修工程师不能为空');
		   		 return ;
   		 }
  	 	 
  	 	if ($("#repair_hours").val() == "" || $("#repair_hours").val() == "undefined"){
	   		 $.ligerDialog.error('维修工时不能为空');
	   		 return ;
	   		 
  		 }
  	 	
  	 	var data = gridManager.getData();
  	  var num= 0;
  	  for(var i = 0;i < data.length; i++){
  	   
  	   if(data[i].inv_code){
  	    num ++;
  	   }
  	    }
  	  if(!num){
  	   $.ligerDialog.error('明细数据不能为空');
  	   return false;
  	  }
        var formPara={
        		
           repair_rec_id : $("#repair_rec_id").val() == ""?0:$("#repair_rec_id").val(),
        		
           repair_rec_no : $("#repair_rec_no").val(),
           
           ass_year : $("#ass_year").val(),
           
           ass_month : $("#ass_month").val(),
            
           fixed_dept_id : liger.get("fixed_dept_id").getValue().split("@")[0],
            
           fixed_dept_no : liger.get("fixed_dept_id").getValue().split("@")[1],
            
           ass_nature : liger.get("ass_nature").getValue(),
            
           ass_card_no : liger.get("ass_card_no").getValue(),
           
           apply_no : liger.get("apply_no").getValue(),
            
           is_inner : liger.get("is_inner").getValue(),
            
           trouble_level : liger.get("trouble_level").getValue(),  
           
           repair_level : liger.get("repair_level").getValue(),
            
           repair_date : $("#repair_date").val(),
            
           receive_date : $("#receive_date").val(),
            
           repair_engr : $("#repair_engr").val(),
            
           repair_hours : $("#repair_hours").val(),
            
           is_contract : liger.get("is_contract").getValue(),
            
           contract_no :$("#contract_no").val(),
            
           repair_money :$("#repair_money").val(),
            
           other_money : $("#other_money").val(),
            
           repair_desc :$("#repair_desc").val(), 
           ParamVo : JSON.stringify(data)
            
         };
        if(validateGrid()){
        	ajaxJsonObjectByUrl("addAssRepairRec.do",formPara,function(responseData){
                if(responseData.state=="true"){
                	$("#repair_rec_id").val(responseData.repair_rec_id);
    				$("#repair_rec_no").val(responseData.repair_rec_no);
                    parent.query();
                    query();
    				is_addRow();
                }
            });
        }
    }
     function validateGrid() {
    		var data = gridManager.getData();
   		var msg = "";
   		var targetMap = new HashMap();
   		var msgMap = new HashMap();
   		$.each(data, function(i, v) {
   			var value = "第" + (i + 1) + "行";
   			if (isnull(v.inv_code)) {
   				gridManager.deleteRow(i);
   				return;
   			}
   			if (isnull(v.inv_code)) {
   				msg += "[材料编码]、";
   			}
   			if (isnull(v.inv_name)) {
   				msg += "[材料名称]、";
   			}
   			if (isnull(v.inv_price)) {
   				msg += "[材料单价]、";
   			}
   			if (isnull(v.inv_num)) {
   				msg += "[材料数量]";
   			}
   			if (msg != "") {
   				msgMap.put(msg+"不能为空或不能为零！\n\r", "");
   			}
   		});
   		if (msg != "") {
   			$.ligerDialog.warn(msgMap.keySet());
   			return false;
   		}
   		if (data.length == 0) {
   			$.ligerDialog.warn("无数据保存");
   			return false;
   		}
   		return true;
   	} 
 
    function loadDict(){
            //字典下拉框
    	//资产性质
    	//autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false", "id","text", true, true);
           
           $("#ass_nature").ligerComboBox({
          	url: '../queryAssNaturs.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	onSelected :function(value,text){
          		 
          		var param = {'ass_nature':liger.get("ass_nature").getValue()};
          		 
          		autocomplete("#ass_card_no",
        				"../queryAssCardNoDict.do?isCheck=false","id",
        			    "text",true,true,param,false,false,"160px");
          		 
          	}
 		  }); 
            
            //审核人
    	autocomplete("#audit_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true);
    	//制单人
    	autocomplete("#create_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true);
    	//维修部门
    	autocomplete("#fixed_dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true);
    	//资产卡片号
    	//autocomplete("#ass_card_no", "../queryAssCardNoDict.do?isCheck=false", "id","text", true, true);
    	//申请单号
    	autocomplete("#apply_no", "../queryAssApplyNoDict.do?isCheck=false", "id","text", true, true);
    	//是否内部维修
    	/* $("#is_inner").ligerComboBox({
			width : 160
		}); */
		$('#is_inner').ligerComboBox({
			data:[{id:0,text:'否'},{id:1,text:'是'}],
			valueField: 'id',
	        textField: 'text',
			cancelable:true,
			width:160
		});
    	//故障级别
    	/* $("#trouble_level").ligerComboBox({
			width : 160
		}); */
		$('#trouble_level').ligerComboBox({
			data:[{id:0,text:'一级维修'},{id:1,text:'二级维修'},{id:2,text:'三级维修'},{id:3,text:'四级维修'}],
			valueField: 'id',
	        textField: 'text',
			cancelable:true,
			width:160
		});
    	//维修级别
    	/* $("#repair_level").ligerComboBox({
			width : 160
		}); */
		$('#repair_level').ligerComboBox({
			data:[{id:0,text:'一级维修'},{id:1,text:'二级维修'},{id:2,text:'三级维修'},{id:3,text:'四级维修'}],
			valueField: 'id',
	        textField: 'text',
			cancelable:true,
			width:160
		});
    	//是否合同
    	/* $("#is_contract").ligerComboBox({
			width : 160
		}); */
		$('#is_contract').ligerComboBox({
			data:[{id:0,text:'否'},{id:1,text:'是'}],
			valueField: 'id',
	        textField: 'text',
			cancelable:true,
			width:160
		}) 
    	 
        autodate("#ass_year","YYYY");
        
        autodate("#ass_month","MM");
        

	     $("#repair_rec_no").ligerTextBox({disabled:true,cancelable: false,width : 160});
	     $("#contract_no").ligerTextBox({disabled:true,cancelable: false,width : 160});
	     $("#ass_card_no").ligerComboBox({
	   			width : 160
	   			});
	     $("#ass_year").ligerTextBox({width : 160});
	     $("#ass_month").ligerTextBox({width : 160});
	     $("#repair_date").ligerTextBox({width : 160});
	     $("#receive_date").ligerTextBox({width : 160});
	     $("#repair_engr").ligerTextBox({width : 160});
	     $("#repair_hours").ligerTextBox({width : 160});
	     $("#repair_money").ligerTextBox({width : 160});
	     $("#other_money").ligerTextBox({width : 160});
     } 
    
   function changeContract(){
	  
	  if ($("#is_contract").val() == 0){
		  $("#contract_no").ligerTextBox({disabled:true,cancelable: false,width : 160});
	
		  
	  }else if ($("#is_contract").val() == 1){
		  $("#contract_no").ligerTextBox({disabled:false,cancelable: true,width : 160});
	  }
	  
   }
    </script>
  
  </head>
  
   <body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
      <input type="hidden" id="repair_rec_id" name="repair_rec_id"/>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		 
		 <tr>
		 
		 <td align="right" class="l-table-edit-td"  style="padding-left:20px;" ><b><font color="red">*</font></b>维修记录号：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="repair_rec_no" type="text" id="repair_rec_no"  />
            </td>
            <td align="left"></td> 
		  <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计年度：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="ass_year" type="text" id="ass_year"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计月份：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="ass_month" type="text" id="ass_month"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>是否合同：</td>
            <td align="left" class="l-table-edit-td">
            	<!-- <select id="is_contract" name="is_contract" onchange="changeContract()"  > 
            			<option value=""></option>
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select> -->
                <input name="is_contract" type="text" id="is_contract"/>	
            </td>
            <td align="left"></td>
		 </tr>
        <tr>  
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">合同号：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="contract_no" type="text" id="contract_no"  />
            </td>
            <td align="left" id ="contract_no"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" ><b><font color="red">*</font></b>维修部门：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="fixed_dept_id" type="text" id="fixed_dept_id"  />
            </td>
            <td align="left"></td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>资产性质：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="ass_nature" type="text" id="ass_nature"  />
            </td>
            <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>资产卡片号：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="ass_card_no" type="text" id="ass_card_no"  />
            </td>
            <td align="left"></td>
        </tr> 
        <tr>  
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请单号：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="apply_no" type="text" id="apply_no"  />
            </td>
            <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>是否内部维修：</td>
            <td align="left" class="l-table-edit-td">
            	<!-- <select id="is_inner" name="is_inner"> 
            			<option value=""></option>
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select> -->
                	<input name="is_inner" type="text" id="is_inner"/>
            </td>
            <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>故障级别：</td>
            <td align="left" class="l-table-edit-td">
            	<!-- <select id="trouble_level" name="trouble_level"> 
            			<option value=""></option>
                		<option value="0">一级故障</option>
                		<option value="1">二级故障</option>
                		<option value="2">三级故障</option>
                		<option value="3">四级故障</option>
                	</select> -->
                	<input name="trouble_level" type="text" id="trouble_level"/>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>维修级别：</td>
            <td align="left" class="l-table-edit-td">
            	<!-- <select id="repair_level" name="repair_level"> 
            			<option value=""></option>
                		<option value="0">一级维修</option>
                		<option value="1">二级维修</option>
                		<option value="2">三级维修</option>
                		<option value="3">四级维修</option>
                	</select> -->
                	<input name="repair_level" type="text" id="repair_level"/>
            </td>
            <td align="left"></td>
        </tr> 
        <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>维修日期：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="repair_date" type="text" id="repair_date"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </td>
            <td align="left"></td>
          	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">取件日期：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="receive_date" type="text" id="receive_date"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </td>
             <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>维修工程师：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="repair_engr" type="text" id="repair_engr"  />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>维修工时：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="repair_hours" type="text" id="repair_hours"  />
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
         	
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维修费用：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="repair_money" type="text" id="repair_money"  />
            </td>
            <td align="left"></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">其他费用：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="other_money" type="text" id="other_money"  />
            </td>
             <td align="left"></td>
        </tr>  
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">检修说明：</td>
            <td align="left" class="l-table-edit-td" colspan="4"> 
            	<textarea rows="3" cols="70" name="repair_desc" id="repair_desc"></textarea>
            </td>
            <td align="left"></td>
          </tr> 
    </table>
    </form>
    <div id="maingrid"></div>
    </body>
</html>
