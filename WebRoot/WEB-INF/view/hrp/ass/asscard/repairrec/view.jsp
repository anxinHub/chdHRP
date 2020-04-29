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
    	  loadForm();
    	  query();
          if ('${state}' !=0) { 
          	toobarmanage = gridManager.toolbarManager; 
          	toobarmanage.setDisabled('saveDetail');
          	toobarmanage.setDisabled('save');
          	toobarmanage.setDisabled('delete'); 
  		}
          
        $(".repair_confirm").click(function () {
	       	 $(this).blur();
	       	 if($("form").valid()){
                save();
            }
        })
    });  
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
    function  query(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
		grid.options.parms.push({name : 'repair_rec_no',value : $("#repair_rec_no").val()});
 		grid.loadData(grid.where);
  }
     
     function loadHead(){
     	grid = $("#maingrid").ligerGrid({
            columns: [  
                { display: '维修记录编码', name: 'repair_rec_no', align: 'left', hide : true
			 		},
                { display: '材料id', name: 'inv_id', align: 'left', hide : true
			 		},
                { display: '材料编码', name: 'inv_code', align: 'left', editor : { type : 'text' }
			 		},
	          { display: '材料名称', name: 'inv_name', align: 'left', textField : 'text', editor : {
					type : 'select',
					valueField : 'text',
					textField : 'text',
					url : '../queryAssInvArrt.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
					onChange: function (item) {
						console.log(item)
						if (item.selected) {      
							item.record.inv_id = item.selected.id.split(" ")[0];
							item.record.inv_code = item.selected.id.split(" ")[1];
							
						} else {
							item.record.inv_code = '';
						}
					}
				},
			 		},
           { display: '材料单价', name: 'inv_price', align: 'left',editor : { type : 'numberbox',precision : 2  }
			 	    },
           { display: '材料数量', name: 'inv_num', align: 'left',editor : { type : 'int' }
			 	    }, 	
			  { display: '费用', name: 'stuff_money', align: 'right',
			 	    	
		 			render : function(rowdata, rowindex, value) {
		 				var stuff_money = rowdata.inv_price * rowdata.inv_num;
		 				rowdata.stuff_money = stuff_money;
						return  stuff_money;
						
						}
			 	    }, 
          ],
                    dataAction: 'server',usePager:false,url:'queryAssRepairRecDetail.do?isCheck=false',
                    width : '100%',
						//height : '100%',
						checkbox : true,
						alternatingRow : true,
						enabledEdit : true,
						onBeforeEdit : f_onBeforeEdit,
						onBeforeSubmitEdit : f_onBeforeSubmitEdit,
						onAfterEdit : f_onAfterEdit,
						isScroll : true,
						checkbox : true,
						rownumbers : true,
						delayLoad:true,
						selectRowButtonOnly : true
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
         is_addRow();
         
     }


     var rowindex_id = "";
     
  	var column_name="";
  	
  	function f_onBeforeEdit(e) {
  		
  		rowindex_id = e.rowindex;
  		
  		clicked = 0;
  		
  		column_name=e.column.name;
  		
  	}
  	function f_onSelectRow(data, rowindex, rowobj) {
  		
  		return true;
  	}
  	// 编辑单元格提交编辑状态之前作判断限制
  	function f_onBeforeSubmitEdit(e) {
  		
  		return true;
  	}
  	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
  	// 跳转到下一个单元格之前事件
  	function f_onAfterEdit(e) {
  		if (e.record.inv_num && e.record.inv_price) {
  			var data = grid.getData();
  	  		var sum_money = 0;
  	  		
  	  		data.forEach(function (item, index) {
  	  			if (item.stuff_money) {
  	  				sum_money += Number(item.stuff_money);
  	  			}
  	  		})
  	  		$("#repair_money").val(sum_money)
  		}
  		
  		
  		//
  		
  	}
  	function itemclick(item) {
 		if (item.id) {
 			switch (item.id) {
 			case "delete":
 				var data = gridManager.getCheckedRows();
 				if (data.length == 0) {
 					parent.$.ligerDialog.error('请选择行');
 				} else {
 					var ParamVo = [];
 					var i = 0;
 					$(data).each(
 							function() {
 								if(isnull(this.repair_rec_no)){
 									gridManager.deleteSelectedRow();
								}else{
 									ParamVo.push(
		 										this.group_id + "@" + 
		 										this.hos_id + "@" + 
		 										this.copy_code + "@"+
		 										this.repair_rec_no + "@"+
		 										this.inv_code
 										);
								}
 								i++;
 							});
 					if(ParamVo == ""){
 						is_addRow();
						return;
					}
 					parent.$.ligerDialog.confirm('确定删除?', function(yes) {
 						if (yes) {
 							ajaxJsonObjectByUrl("deleteAssRepairRecDetail.do?isCheck=false", {
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
 			}
 		}
 	}
     
    function save(){ 
			 	 
		var data = gridManager.getData();
			 	  
        var formPara={
        		 
        repair_rec_id:$("#repair_rec_id").val(),
        
        repair_rec_no:$("#repair_rec_no").val(),
        
        apply_no : liger.get("apply_no").getValue(),
        
        is_inner : liger.get("is_inner").getValue(),
        
        trouble_level : liger.get("trouble_level").getValue(),
        
        repair_level : liger.get("repair_level").getValue(),
        
        repair_date:$("#repair_date").val(),
        
        repair_hours:$("#repair_hours").val(),
        
        is_contract : liger.get("is_contract").getValue(),
        
        repair_engr : liger.get("repair_engr").getValue(),
        
        contract_no:$("#contract_no").val(),
        
        repair_money:$("#repair_money").val(),
        
        other_money:$("#other_money").val(),
        
        repair_desc:$("#repair_desc").val(), 

        state:$("#state").val(), 
        ParamVo : JSON.stringify(data)
        
        }; 
        console.log(formPara);
        if(validateGrid()){
	        ajaxJsonObjectByUrl("updateAssRepairRec.do",formPara,function(responseData){
	            
	            if(responseData.state=="true"){
	            	parent.query();
                    query();
    				is_addRow();
	                
	            }
	        });
        }
    }
    function saveInv(){ 
			 	 
		var data = gridManager.getData();
			 	  
		    	 
        var formPara={
        		 
        
        repair_rec_no:$("#repair_rec_no").val(),
        
        apply_no : liger.get("apply_no").getValue(),
        
        ParamVo : JSON.stringify(data)
        
        }; 
        if(validateGrid()){
	        ajaxJsonObjectByUrl("seveAssRepairRec.do",formPara,function(responseData){
	            
	            if(responseData.state=="true"){
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
			parent.$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
			parent.$.ligerDialog.warn("无数据保存");
			return false;
		}
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
        
   		//维修班组
        autocomplete("#rep_team_code","../queryRepTeam.do?isCheck=false", "id","text", true, true, null, null,'${rep_team_code}');
        autocomplete("#repair_engr","../queryHeadEmp.do?isCheck=false", "id","text", true, true, null, null,'${repair_engr}');

     
    	//维修部门
    	autocomplete("#repair_dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true,"",false,"${repair_dept_id}@${repair_dept_no}");
    	
    	
    	//资产卡片号
    	autocomplete("#ass_card_no", "../queryAssCardNoDict.do?isCheck=false", "id","text", true, true,{'ass_nature':'${ass_nature}'});
    	
    	liger.get("ass_card_no").setValue("${ass_card_no}");
    	
    	liger.get("ass_card_no").setText("${ass_card_no}");
    	autocomplete("#apply_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true, null, null,'${apply_emp}');

    	//申请单号
    	autocomplete("#apply_no", "../queryAssApplyNoDict.do?isCheck=false", "id","text", true, true);
    	
    	liger.get("apply_no").setValue("${apply_no}");
    	
    	liger.get("apply_no").setText("${apply_no}");
    	
    	//是否内部维修
    	
    	$("#is_inner").ligerComboBox({
    		
			width : 160,
			onSelected: function (value, text) {
				var repair_engr_wrap = $(".repair_engr_wrap");
				repair_engr_wrap.children().remove();
				
				if (text === '是') {
					var repair_engr_html = 
						'<input name="repair_engr" validate="{required:true,maxlength:20}" type="text" id="repair_engr" /> ';
					repair_engr_wrap.html(repair_engr_html);
					
					if (Number('${repair_engr}') > 0) {
						autocomplete("#repair_engr","../queryHeadEmp.do?isCheck=false", "id", "text", true, true, null, null,'${repair_engr}');
					} else {
						autocomplete("#repair_engr","../queryHeadEmp.do?isCheck=false", "id","text", true, true, null, null);
					}
				} else {
					var repair_engr_html = 
						'<input name="repair_engr" type="text" id="repair_engr" /> ';
					repair_engr_wrap.html(repair_engr_html);
					
					var repair_engr = '${repair_engr}';
					
					if(repair_engr && !Number(repair_engr)) {
						$(document).find('#repair_engr').ligerTextBox({
							width: 160,
							value: repair_engr
						});
					} else {
						$(document).find('#repair_engr').ligerTextBox({width: 160});
					}
					
				}
			}
		});
    	
    	liger.get("is_inner").setValue("${is_inner}"); 
    	
    	//故障级别
    	$("#trouble_level").ligerComboBox({
    		
			width : 160
			
		});
    	
    	liger.get("trouble_level").setValue("${trouble_level}"); 
    	
    	//维修级别
    	$("#repair_level").ligerComboBox({
    		
			width : 160
			
		});
    	
    	liger.get("repair_level").setValue("${repair_level}"); 
    	
    	$("#contract_no").ligerTextBox({width:160});
    	//是否合同
    	$("#is_contract").ligerComboBox({
    		
			width : 160,
			onSelected: function (value, text) {
				var ligertipid = $("#contract_no").attr("ligertipid");
				if (text === "是"){
					 $("#contract_no").ligerTextBox({disabled:false });
					 $("#contract_no")
					 	.removeAttr("disabled")
					 	.attr("validate", '{required:true,maxlength:20}');
					 $(document).find("#"+ligertipid).show();
		  	    } else {
		  	    	 $("#contract_no").ligerTextBox({disabled:true });
		  	    	 $("#contract_no").removeAttr("validate").val('');
		  	    	 $(document).find("#"+ligertipid).hide();
		  	    }
			}
		});
    	
    	liger.get("is_contract").setValue("${is_contract}"); 
    	
    	
		$("#state").ligerComboBox({
		    		
					width : 160
					
				});
    	
    	liger.get("state").setValue("${state}"); 
     
    	
    	 $("#repair_rec_no").ligerTextBox({disabled:true,cancelable: false,width:160});
    	 
    	 $("#create_date").ligerTextBox({disabled:true,cancelable: false,width:160});
    	 
    	 $("#repair_dept_id").ligerTextBox({disabled:true,cancelable: false,width:160});
    	 
    	 $("#rep_team_code").ligerTextBox({disabled:true,cancelable: false,width:160});
    	 $("#apply_emp").ligerTextBox({disabled:true,cancelable: false,width:160});
    	 $("#rep_phone").ligerTextBox({disabled:true,cancelable: false,width:160});
    	 $("#apply_no").ligerTextBox({disabled:true,cancelable: false,width:160});
    	 $("#ass_card_no").ligerTextBox({disabled:true,cancelable: false,width:160});
    	 $("#ass_name").ligerTextBox({disabled:true,cancelable: false,width:160});
    	
         
         
         $("#repair_date").ligerTextBox({width:160});
         
         $("#repair_engr").ligerTextBox({width:160});
         
         $("#repair_hours").ligerTextBox({width:160});
         
         $("#repair_money").ligerTextBox({width:160});
         
         $("#other_money").ligerTextBox({width:160});
         
     }   

    </script>
  
  </head>
  
   <body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
    <input name="repair_rec_id"  type="hidden" id="repair_rec_id"  value="${repair_rec_id}"  />
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">维修记录号：</td>
            <td align="left" class="l-table-edit-td"><input name="repair_rec_no" type="text" id="repair_rec_no"  value="${repair_rec_no}"  /></td>
            <td align="left"></td>
       
             </tr> 
        <tr>
         <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date"  value="${create_date}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维修部门：</td>
            <td align="left" class="l-table-edit-td"><input name="repair_dept_id" type="text" id="repair_dept_id"    /></td>
            <td align="left"></td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维修班组：</td>
            <td align="left" class="l-table-edit-td"><input name="rep_team_code" type="text" id="rep_team_code"    /></td>
            <td align="left"></td> 
            </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请人：</td>
            <td align="left" class="l-table-edit-td"><input name="apply_emp" type="text" id="apply_emp"  value="${apply_emp}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请人联系方式：</td>
            <td align="left" class="l-table-edit-td"><input name="rep_phone" type="text" id="rep_phone"  value="${rep_phone}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维修申请单号：</td>
            <td align="left" class="l-table-edit-td"><input name="apply_no"  type="text" id="apply_no"  value="${apply_no}"  /></td>
            <td align="left"></td>
         </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产卡片号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_card_no" type="text" id="ass_card_no"  value="${ass_card_no}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>资产名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_name" type="text" id="ass_name"  value="${ass_name}"  /></td>
            <td align="left"></td>
        </tr>
    </table>
    <hr class="divedline"/>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>是否内部维修：</td>
            <td align="left" class="l-table-edit-td">
           		 <select id="is_inner" name="is_inner" value="${is_inner}"   validate="{required:true,maxlength:20}"> 
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>维修日期：</td>
            <td align="left" class="l-table-edit-td"><input name="repair_date"  validate="{required:true,maxlength:20}" type="text" id="repair_date"  value="${repair_date}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>维修工程师：</td>
            <td align="left" class="l-table-edit-td repair_engr_wrap">
            	<%-- <input name="repair_engr" validate="{required:true,maxlength:20}" type="text" id="repair_engr"  value="${repair_engr}"  /> --%>
            </td>
            <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left"></td>
                  
        </tr>

        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">故障级别：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="trouble_level" name="trouble_level"> 
            			<option value=""></option>
                		<option value="0">一般</option>
                		<option value="1">严重</option>
                	</select>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维修级别：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="repair_level" name="repair_level"> 
            			<option value=""></option>
                		<option value="0">小修</option>
                		<option value="1">中修</option>
                		<option value="2">大修</option>
                	</select>
            </td>
             <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维修工时：</td>
            <td align="left" class="l-table-edit-td"><input name="repair_hours" type="text" id="repair_hours"  value="${repair_hours}"  /></td>
            <td align="left"></td>
             
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否合同：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="is_contract" name="is_contract">
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select>
            </td>
            <td align="left"></td>
       
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同号：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_no" type="text" id="contract_no"  value="${contract_no}"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料费用：</td>
            <td align="left" class="l-table-edit-td"><input name="repair_money" type="text" id="repair_money"  value="${repair_money}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">其他费用：</td>
            <td align="left" class="l-table-edit-td"><input name="other_money" type="text" id="other_money"  value="${other_money}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td" >
            	<select  name="state"  id="state"  validate="{required:true,maxlength:20}">
            		<option value="5">待维修</option>
            		<option value="6">维修中</option>
            		<option value="7">维修完成</option>
            	</select>
            </td>
            <td align="left"></td>
        </tr> 
		<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">检修说明：</td>
            <td align="left" class="l-table-edit-td" colspan="7"> 
            	<textarea rows="3" cols="70" name="repair_desc" id="repair_desc">${repair_desc}</textarea>
            </td>
            <td align="left"></td>
          </tr> 
	</table>
   <hr class="divedline"/>
     <div id="maingrid"></div>
     </form>
    </body>
</html>
