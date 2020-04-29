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
 	var gridManager = null;    
	var ForR = [{ id: 0, text: '否' }, { id: 1, text: '是'}]; 
	var ForRs = [{ id: 0, text: '年度计划' }, { id: 1, text: '临时计划'}]; 
     $(function (){ 
         loadDict();//加载下拉框
         loadHead();
     });  
      
     function  query(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
 		grid.options.parms.push({name : 'plan_id',value : $("#plan_id").val() == ""?"0":$("#plan_id").val()});
 		grid.loadData(grid.where);
  }
     
     var editor;
     
     function cardSelect(){
  		var data = gridManager.getData();
  		var ass_card_nos = [];
  		$.each(data, function(i, v) {
  			if (!isnull(v.ass_card_no)) {
  				ass_card_nos.push("'"+v.ass_card_no+"'");
  			}
  		});
  		
  		
  		editor.grid.url = '../assmeasureplan/choseAssCardNo.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue()+'&ass_card_not_exists='+ass_card_nos.toString();
  	}
   
    function loadHead(){
    	editor = {
				type : 'select',
				valueField : 'ass_card_no',
				textField : 'ass_card_no',
				selectBoxWidth : 500,
				selectBoxHeight : 240,
				grid : {
					columns : [ {display : '资产卡片号',
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
					url : '../assmeasureplan/choseAssCardNo.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue(),
					pageSize : 30
				},
				alwayShowInDown : true,
				keySupport : true,
				autocomplete : true,
				onSuccess : function() {
					this.parent("tr").next(
							".l-grid-row").find(
							"td:first").focus();
				},
				onBeforeOpen: cardSelect
			};
    	
    	grid = $("#maingrid").ligerGrid({
    		
           columns: [  

						{
								display : '资产卡片号',
								name : 'ass_card_no',
								width: 150,
								align : 'left',
								textField : 'ass_card_no',
								editor : editor,
								render : function(rowdata, rowindex,
										value) {
									return rowdata.ass_card_no;
								}

							},
							{ display: '资产编码', name: 'ass_code',width: 120, align: 'left', },
	                        { display: '资产名称', name: 'ass_name',width: 160, align: 'left', },
	                        { display: '型号', name: 'ass_mondl', width: 120,align: 'left',  },
	                        { display: '规格', name: 'ass_spec', width: 120,align: 'left', },
	                        { display: '品牌', name: 'ass_brand', width: 120,align: 'left', },
	 					    { display: '生产厂家', name: 'fac_name', width: 160,align: 'left', },
	 					    { display: '管理科室', name: 'dept_name', width: 100,align: 'left', },
		             { display: '计量类别', name: 'measure_kind',width: 100, align: 'left',
                    	 editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								data : ForRs,
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
		                        if (parseInt(item.measure_kind) == 1) {
		                        	return '临时计划';
		                        }else if(parseInt(item.measure_kind) == 0){
		                        	return '年度计划';
		                        }else{
		                        	return "";
		                        }
		                    }
	 				 	    },
	 	             { display: '计划计量日期', name: 'plan_exec_date', width: 100,align: 'left',

	 				 	    	type: 'date', 
								format: 'yyyy-MM-dd',
								editor : {
									type : 'date'
								},
						    },
		             { display: '计量说明', name: 'measure_desc', align: 'left', width: 100,editor : { type : 'text'  }
		 				    },
			 	    /*  { display: '是否内部检测', name: 'is_inner', align: 'left',width: 100, 
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
			                        if (parseInt(item.is_inner) == 1) {
			                        	return '是';
			                        }else if(parseInt(item.is_inner) == 0){
			                        	return '否';
			                        }else{
			                        	return "";
			                        }
			                    }
		 				    	
						    },
			         { display: '外部计量单位', name: 'outer_measure_org', align: 'left', width: 100,editor : { type : 'text'  }
			 			    }, */
                     ],
                     
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../assmeasureplanass/queryAssMeasurePlanAss.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue(),
                     width : '100%',
						height : '95%',
						checkbox : true,
						enabledEdit : true,
						alternatingRow : true,
						onBeforeEdit : f_onBeforeEdit,
						onBeforeSubmitEdit : f_onBeforeSubmitEdit,
						onAfterEdit : f_onAfterEdit,
						isScroll : true,
						checkbox : true,
						rownumbers : true,
						//notCellEditByColName:"ass_card_no", //资产卡片是主键不能进行修改 （主键进行添加完成后，不能进行修改）
						delayLoad:true,
						selectRowButtonOnly : true,//heightDiff: -10,
                   toolbar : {
	 							items : [ {text : '保存',id : 'save',click : save,icon : 'save'}, {line : true}, 
	 							          {text : '删除',id : 'delete',click : itemclick,icon : 'delete'}, {line : true}, 
	 							          {text : '引入资产卡片',id : 'create',click : itemclick,icon : 'save'},{line : true},
	 							          {text : '关闭',id : 'close',click : this_close,icon : 'candle'}
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
 				//回充数据 
 				grid.updateRow(rowindex_id, {
 					ass_card_no : data.ass_card_no,
 					ass_code : data.ass_code,
 					ass_name : data.ass_name,
 					fac_id : data.fac_id,
 					fac_no : data.fac_no,
 					fac_code : data.fac_code,
 					fac_name : data.fac_name,
 					ass_mondl : data.ass_model,
 					ass_spec : data.ass_spec,
 					dept_name : data.dept_name
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
    	
 	function itemclick(item) {
 	    /* console.log(liger.get("ass_nature").getValue()), */
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
								if(isnull(this.ass_card_no)){
									gridManager.deleteSelectedRow();
								}else{
			  								ParamVo.push(this.ass_card_no
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
							
							ajaxJsonObjectByUrl("../assmeasureplanass/deleteAssMeasurePlanAss.do?isCheck=false ",
									
									{ParamVo : ParamVo.toString()}, function(responseData) {
										
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
				var dept_id = liger.get("dept_id").getValue().split("@")[0];
				var dept_no = liger.get("dept_id").getValue().split("@")[1];
				var ass_nature= liger.get("ass_nature").getValue();
				if(dept_id == null || dept_no == null || dept_id == "" ||
					dept_no == "") {
					dept_no = "";
					dept_id = "";
				}
 				$.ligerDialog.open({
 					top : 80,
					title : '引入资产卡片',
					height :600,
					width : 900,
					url : 'assMeasurePlanImportPage.do?isCheck=false&dept_id='+dept_id+'&dept_no='+dept_no+'&ass_nature='+ass_nature,
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : false,
					isResize : true,
					buttons: [{
		                    text: '选择',
		                    onclick: function (item, dialog) {dialog.frame.addRows();dialog.close();},
		                },
		                {
		                    text: '取消',
		                    onclick: function (item, dialog) {
		                        dialog.close();
		                    }
		                }
		                ]
					//parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
					});
				//grid.deleteSelectedRow();
//				     var dept_id = liger.get("dept_id").getValue().split("@")[0];
//				     var  dept_no = liger.get("dept_id").getValue().split("@")[1];
//				     if (dept_id == null || dept_no == null || dept_id == ""
//							|| dept_no == "") {
//				    	 dept_no = "";
//				    	 dept_id = "";
//					}
//					
//		            var fn = $.ligerui.getPopupFn({
//		                top : 80,
//		                onSelect: function (e) {
//		                    grid.addRows(e.data);
//		                    grid.moveRange(e.data,0);
//		                },
//		                
//		                grid: {
//		                    columns: [
//
//		      						{ display : '资产卡片号', name : 'ass_card_no', align : 'left', },
//		      						
//		      						{ display: '资产编码', name: 'ass_code', align: 'left', },
//		      						
//		      						{ display: '资产名称', name: 'ass_name', align: 'left', },
//		      						 
//		      						{ display: '型号', name: 'ass_mondl', align: 'left',  },
//		      						
//		      						{ display: '规格', name: 'ass_spec', align: 'left', },
//		      						
//		      						{ display: '品牌', name: 'ass_brand', align: 'left', },
//		      						
//		      						{ display: '生产厂家', name: 'fac_name', align: 'left', },
//		      						
//		      						{ display: '管理科室', name: 'dept_name', align: 'left', },
//		      						
//		      						{ display: '领用日期', name: 'in_date', align: 'left', },
//		      						
//		      						{ display: '是否计量', name: 'is_measure_name', align: 'left', }
//		                           ],
//		                       
//		                           dataAction: 'server',dataType: 'server',usePager:true,url:'../assmeasureplan/choseAssCardNo.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue()+'&ass_card_no='+liger.get("ass_card_no").getValue()+'&ass_name='+liger.get("ass_name").getValue()+'&dept_id='+dept_id+'&dept_no='+dept_no,
//		                           width: '100%', height: '100%', checkbox: true,rownumbers:true,
//		                           autoFilter: true
//
//		                } 
//		            });
//
//		            fn();
			}
		}
	}
 
     
     function  save(){
    	 gridManager.endEdit();
    	 if($("#plan_name").val() == ""){
   			$.ligerDialog.error('计量计划名称不能为空');
   			return;	
   		}
     	if(liger.get("ass_nature").getValue() == ""){
   			$.ligerDialog.error('资产性质不能为空');
   			return;
   		}
   		if(liger.get("measure_cycle").getValue() == ""){
   			$.ligerDialog.error('计量周期不能为空');
   			return;
   		}
   		if(!liger.get("is_inner").getValue()){
   			$.ligerDialog.error('是否内部检测不能为空'); 
   			return;
   		}
     	var data = gridManager.getData(); 
     	  var num= 0;
     	  for(var i = 0;i < data.length; i++){
     	   
     	   if(data[i].ass_card_no){
     	    num ++;
     	   }
     	    }
     	  if(!num){
     	   $.ligerDialog.error('明细数据不能为空');
     	   return false;
     	  }
        var formPara={
        		
           plan_id : $("#plan_id").val() == ""?0:$("#plan_id").val(),	
            
           plan_no:$("#plan_no").val(),
            
           plan_name:$("#plan_name").val(),
            
           plan_year:$("#plan_year").val(),
            
           ass_nature: liger.get("ass_nature").getValue(), 
            
           check_way:$("#check_way").val(),
           
           measure_cycle:$("#measure_cycle").val(),
            
           note:$("#note").val(),
           
           is_inner : liger.get("is_inner").getValue() ,
           
           outer_measure_org:$("#outer_measure_org").val(),
           
		   ParamVo : JSON.stringify(data)
            
         };
        if(validateGrid()){
	        ajaxJsonObjectByUrl("addAssMeasurePlan.do",formPara,function(responseData){
	            if(responseData.state=="true"){
	            	$("#plan_id").val(responseData.plan_id);
	            	$("#plan_no").val(responseData.plan_no);
	                //parent.query();
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
   			var key = v.ass_card_no + "|" +v.ass_code + "|" + v.ass_model + "|" + v.ass_spec;
   			var value = "第" + (i + 1) + "行";
   			if (isnull(v.ass_card_no)) {
   				gridManager.deleteRow(i);
   				return;
   			}
   			if (isnull(v.ass_card_no)) {
   				msg += "[卡片编号]、";
   			}
  			/* if (v.is_inner != 0 && v.is_inner != 1) {
  				msg += "[是否内部检测]";
  				
  			}  */
 
   			if (msg != "") {
   				msgMap.put(value+msg+"不能为空或不能为零！\n\r", "");
   			}
   			if (isnull(targetMap.get(key))) {
   				targetMap.put(key, value);
   			} else {
   				msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r", value);
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
            
        $("#dept_id").ligerComboBox({
 			url : '../queryDeptDict.do?isCheck=false',
 			valueField : 'id',
 			textField : 'text',
 			selectBoxWidth : 160,
 			autocomplete : true,
 			width : 160,
 			onSelected : function(id, text) {
 				//loadHead();
 			}
 		});
	
        $('#is_inner').ligerComboBox({
 			data:ForR,
 			valueField: 'id',
            textField: 'text',
 			cancelable:true
 		});
            
        $("#ass_card_no,#measure_cycle").ligerTextBox({cancelable: false,width:160}); 
        $("#ass_name").ligerTextBox({width:160});
    	$("#plan_no").ligerTextBox({disabled:true,cancelable: false,width:160});
    	
        autodate("#plan_year","YYYY");
      
    	 $("#plan_no").ligerTextBox({width:160});
         
         $("#plan_name").ligerTextBox({width:160});
         
		 $("#plan_year").ligerTextBox({width:160});
         
         $("#check_way").ligerTextBox({width:160});	
         
         $("#is_inner").ligerTextBox({width:160});
         
         $("#outer_measure_org").ligerTextBox({width:160});	
     } 
    </script>
  
  </head>
  
   <body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <input type="hidden" id="plan_id" name="plan_id"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>计量计划编号：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_no" type="text" id="plan_no"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>计量计划名称：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_name" type="text" id="plan_name"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量计划年份：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_year" type="text" id="plan_year"   class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"   /></td>
            <td align="left"></td> 
           
        </tr> 
        <tr>
        	
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>计量周期(月)：</td>
            <td align="left" class="l-table-edit-td"><input name="measure_cycle"  type="text" id="measure_cycle"  onkeyup='this.value=this.value.replace(/\D/gi,"")' /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产卡片号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_card_no" disabled="disabled" type="text" id="ass_card_no"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_name" disabled="disabled" type="text" id="ass_name"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">检查方式：</td>
            <td align="left" class="l-table-edit-td"><input name="check_way" type="text" id="check_way"   /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
			<td align="left" class="l-table-edit-td"><input	name="dept_id" type="text" id="dept_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>是否内部检测：</td>
			<td align="left" class="l-table-edit-td"><input	name="is_inner" type="text" id="is_inner" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">外部计量单位：</td>
			<td align="left" class="l-table-edit-td"><input	name="outer_measure_org" type="text" id="outer_measure_org" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td" colspan="4">
            	<textarea rows="3" cols="70"  name="note"  id="note"  ></textarea>
            </td>
            <td align="left"></td>
        
        <tr>
    </table>
   <div id="maingrid"></div>
 
    </body>
</html>
