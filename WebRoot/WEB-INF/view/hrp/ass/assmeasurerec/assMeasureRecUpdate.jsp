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
 	var ForR = [{ id: 0, text: '不合格' }, { id: 1, text: '合格'}, { id: 2, text: '修复后合格'}];
     $(function (){ 
         loadDict();//加载下拉框
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
  		grid.options.parms.push({name : 'rec_id',value : '${rec_id}'});
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
 		 var dept_id = liger.get("dept_id").getValue().split("@")[0];
	     var  dept_no = liger.get("dept_id").getValue().split("@")[1];
 		 if (dept_id == null || dept_no == null || dept_id == ""
 			|| dept_no == "") {
     	 dept_no = "";
     	 dept_id = "";
 		 }
 		 
 		var str = "select * from ASS_MEASURE_REC_DETAIL p "+
		  " left join ASS_MEASURE_REC pp "+
		  "  on pp.group_id = p.group_id "+
		  " and pp.hos_id = p.hos_id "+
		  " and pp.copy_code = p.copy_code "+
		  " and pp.rec_id = p.rec_id "+
		  " where p.group_id = a.group_id "+
		  " and p.hos_id = a.hos_id "+
		  " and p.ass_card_no = a.ass_card_no "+
		  " and pp.state = 0 ";
 		 
 		editor.grid.url = '../assmeasureplan/choseAssCardNo.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue()+'&ass_card_not_exists='+ass_card_nos.toString()+'&dept_id='+dept_id+'&dept_no='+dept_no+'&sql='+str;
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
								if(rowdata.ass_card_no == "" || rowdata.ass_card_no == null){
									return "";
								}
								return "<a href=javascript:openCardUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.ass_card_no  + "|"
								+ liger.get("ass_nature").getValue() +"')>"+rowdata.ass_card_no+"</a>";
							}

						},
						{ display: '资产编码', name: 'ass_code', width: 100,align: 'left'
					 		},
                        { display: '资产名称', name: 'ass_name', width: 100,align: 'left'
					 		},
                        { display: '型号', name: 'ass_mondl',width: 100, align: 'left'
					 		},
                        { display: '规格', name: 'ass_spec', width: 100,align: 'left'
					 		},
                        { display: '品牌', name: 'ass_brand', width: 100,align: 'left'
					 		},
					 	{ display: '序列号', name: 'ass_seq_no', width: 100,align: 'left'
					 		},	
					    { display: '生产厂家', name: 'fac_name',width: 140, align: 'left'
					 		},
					 	{ display: '管理科室', name: 'dept_name', width: 130,align: 'left'
					 		},	
                        { display: '检定结果', name: 'measure_result',width: 120,
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
			                        	return '合格';
			                        }else if(parseInt(item.measure_result) == 0){
			                        	return '不合格';
			                        }else if(parseInt(item.measure_result) == 2){
			                        	return '修复后合格';
			                        }else{
			                        	return "";
			                        }
			                    },
					 			align: 'left'
 					 		},
 		                { display: '检定结论', name: 'measure_memo',width: 130, align: 'left', editor : { type : 'text'  }
 	 				 	    },
 	 	                { display: '处理意见', name: 'measure_idea', width: 130,align: 'left', editor : { type : 'text' }
 						    },
						{ display: '有效期', name: 'pre_next_date', align: 'left',type : 'date', width: 100,format : 'yyyy-MM-dd',
										editor : {type : 'date'}
		 				    },
 						{ display: '证书号', name: 'cert_no', width: 260,align: 'left', editor : { type : 'text' }
						   },
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true, url:'../assmeasurerecdetail/queryAssMeasureRecDetail.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue(), 
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
 						//notCellEditByColName:"ass_card_no", //资产卡片是主键不能进行修改 （主键进行添加完成后，不能进行修改）
 						selectRowButtonOnly : true,//heightDiff: -10,
                    toolbar : {
 	 							items : [ {text : '保存',id : 'save',click : save,icon : 'save'}, {line : true}, 
 	 							          {text : '删除',id : 'delete',click : itemclick,icon : 'delete'}, {line : true},  
 	 							          {text : '引入资产卡片',id : 'create',click : itemclick,icon : 'save'},{line : true},
 	 							          {text : '关闭',id : 'close',click : this_close,icon : 'candle'},{line : true}
 	 							        ]
 						}
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
         
      }
     
     var rowindex_id = "";
     
   	var column_name="";
   	
   	
   	function this_close() {
		frameElement.dialog.close();
	}
   	function f_onBeforeEdit(e) {
   		rowindex_id = e.rowindex;
   		clicked = 0;
   		column_name=e.column.name;
   	}
   	
   	function openCardUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
				+ "ass_nature="+vo[4];
		parent.$.ligerDialog.open({
			title: '资产卡片维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscard/assInCardUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
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
     					ass_spec : data.ass_spec,
     					dept_name : data.dept_name,
     					ass_seq_no : data.ass_seq_no
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
 			  								ParamVo.push(
 			  										this.ass_card_no
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
 							
 							ajaxJsonObjectByUrl("../assmeasurerecdetail/deleteAssMeasureRecDetail.do?isCheck=false",
 									
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
					url : 'assMeasureRecImportPage.do?isCheck=false&dept_id='+dept_id+'&dept_no='+dept_no+'&ass_nature='+ass_nature,
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
// 				var str = "select * from ASS_MEASURE_REC_DETAIL p "+
//				  " left join ASS_MEASURE_REC pp "+
//				  "  on pp.group_id = p.group_id "+
//				  " and pp.hos_id = p.hos_id "+
//				  " and pp.copy_code = p.copy_code "+
//				  " and pp.rec_id = p.rec_id "+
//				  " where p.group_id = a.group_id "+
//				  " and p.hos_id = a.hos_id "+
//				  " and p.ass_card_no = a.ass_card_no "+
//				  " and pp.state = 0 ";
// 				
//				//grid.deleteSelectedRow();
//				     var dept_id = liger.get("dept_id").getValue().split("@")[0];
//				     var  dept_no = liger.get("dept_id").getValue().split("@")[1];
//				     if (dept_id == null || dept_no == null || dept_id == ""
//							|| dept_no == "") {
//				    	 dept_no = "";
//				    	 dept_id = "";
//					}
//		            var fn = $.ligerui.getPopupFn({
//		                top : 80,
//		                onSelect: function (e) {
//		                    grid.addRows(e.data);
//		                    grid.moveRange(e.data,0);
//		                },
//		                grid: {
//		                    columns: [
//
//		      						{ display : '资产卡片号', name : 'ass_card_no', align : 'left',
//		    							render : function(rowdata, rowindex,
//		    									value) {
//		    								if(rowdata.ass_card_no == "" || rowdata.ass_card_no == null){
//		    									return "";
//		    								}
//		    								return "<a href=javascript:openCardUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
//		    								+ "|" + rowdata.copy_code + "|"
//		    								+ rowdata.ass_card_no  + "|"
//		    								+ liger.get("ass_nature").getValue() +"')>"+rowdata.ass_card_no+"</a>";
//		    							} },
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
//		                           ],
//		                           dataAction: 'server',dataType: 'server',usePager:true,url:'../assmeasureplan/choseAssCardNo.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue()+'&dept_id='+dept_id+'&dept_no='+dept_no+'&sql='+str,
//		                           width: '100%', height: '100%', checkbox: true,rownumbers:true,
//		                } 
//		            });
//
//		            fn();
		            return;
 			}
 		}
 	}
  	
     function  save(){
    	    
    	 if(liger.get("ass_nature").getValue() == ""){
   			$.ligerDialog.error('资产性质不能为空');
   			return;
   		}
    	 
     	/* if(liger.get("plan_id").getValue() == ""){
   			$.ligerDialog.error('计量计划不能为空');
   			return;
   		} */
     	 
     	if(liger.get("measure_kind").getValue() == ""){
   			$.ligerDialog.error('检测方式不能为空');
   			return;
   		}
     	if(liger.get("measure_result").getValue() == ""){
   			$.ligerDialog.error('检定结果能为空');
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
            
           rec_id : '${rec_id}',		
        		
           seq_no:$("#seq_no").val(),
            
           plan_id: liger.get("plan_id").getValue(),
            
           ass_nature: liger.get("ass_nature").getValue(),
            
           outer_measure_engr: $("#outer_measure_engr").val(),
            
           inner_measure_emp: liger.get("inner_measure_emp").getValue(),
            
           plan_measure_date:$("#plan_measure_date").val(),
            
           fact_measure_date:$("#fact_measure_date").val(),
            
           pre_next_date:$("#pre_next_date").val(),
            
           measure_kind: liger.get("measure_kind").getValue(),
            
           outer_measure_org:$("#outer_measure_org").val(),
            
           measure_result: liger.get("measure_result").getValue(),
            
           measure_idea:$("#measure_idea").val(),
           
           entrust_no:$("#entrust_no").val(),
           
           firm_basis:$("#firm_basis").val(),
           
		   ParamVo : JSON.stringify(data)
            
         };
        if(validateGrid()){ 
	        ajaxJsonObjectByUrl("addAssMeasureRec.do",formPara,function(responseData){
	            
	            if(responseData.state=="true"){
	            	parentFrameUse().query();
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
			if(!isnull(v.ass_card_no)){
				var key = v.ass_card_no;
				var value = "第" + (i + 1) + "行";
				if (isnull(v.ass_card_no)) {
					msg += "[卡片编号]、";
				}
	  			if (v.measure_result == null || v.measure_result == "") {
	  				msg += "[检定结果]";
	  			}  
				if (msg != "") {
					msgMap.put(value+msg+"不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r", value);
				}
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
            	width: 160
   		  });
            
       liger.get("ass_nature").setValue("${ass_nature}");
          	
       liger.get("ass_nature").setText("${naturs_name}");
       liger.get("ass_nature").setDisabled();
            
      //计量计划ID
      	autocomplete("#plan_id", "../queryAssMeasurePlanDict.do?isCheck=false", "id","text", true, true);
   
	    liger.get("plan_id").setValue("${plan_id}");
    	
    	liger.get("plan_id").setText("${plan_name}");
        liger.get("plan_id").setDisabled();
        
   /*      //外部计量员
    	autocomplete("#outer_measure_engr","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true);
         */
        //内部计量员
    	autocomplete("#inner_measure_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true);
         
         liger.get("inner_measure_emp").setValue("${inner_measure_emp}");
     	
     	liger.get("inner_measure_emp").setText("${inner_measure_emp_name}");
        
    	$("#seq_no").ligerTextBox({disabled:true,cancelable: false,width:160});
    	
	   	 $("#plan_measure_date").ligerTextBox({width:160});
	     
	     $("#fact_measure_date").ligerTextBox({width:160});
	     
		 $("#pre_next_date").ligerTextBox({width:160});
	     
	     $("#outer_measure_engr").ligerTextBox({width:160});	
	     
	     /* $("#measure_kind").ligerTextBox({width:160});	 */
	     $('#measure_kind').ligerComboBox({
 			data:[{id:1,text:'自检'},{id:2,text:'送检'},{id:3,text:'抽检'},{id:4,text:'现检'}],
 			valueField: 'id',
             textField: 'text',
 			cancelable:true,
 			width:160
 		});
	     liger.get("measure_kind").setValue("${measure_kind}");
	     
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
	  		
	     
	   	$("#outer_measure_org").ligerTextBox({width:160});
	   	 
		$("#entrust_no").ligerTextBox({width:160});
	   	
	   	$("#firm_basis").ligerTextBox({width:160});
	     
		 /* $("#measure_result").ligerTextBox({width:160}); */
		 $('#measure_result').ligerComboBox({
	 			data:[{id:1,text:'未完成'},{id:0,text:'完成'}],
	 			valueField: 'id',
	             textField: 'text',
	 			cancelable:true,
	 			width:160
	 		});
	 		liger.get("measure_result").setValue("${measure_result}");
	    } 
	    
	    
    </script>
  
  </head>
  
   <body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <input type="hidden" id="rec_id" name="rec_id"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>计量记录编号：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="seq_no" type="text" id="seq_no" disabled="disabled" value="${seq_no }" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_id" type="text" id="plan_id"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部计量员：</td>
            <td align="left" class="l-table-edit-td"><input name="outer_measure_engr" type="text" id="outer_measure_engr" value="${outer_measure_engr }" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">内部计量员：</td>
            <td align="left" class="l-table-edit-td"><input name="inner_measure_emp" type="text" id="inner_measure_emp"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划计量日期：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_measure_date" type="text" value="${plan_measure_date }" id="plan_measure_date"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">实际计量日期：</td>
            <td align="left" class="l-table-edit-td"><input name="fact_measure_date" type="text" value="${fact_measure_date }" id="fact_measure_date"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">有效期至：</td>
            <td align="left" class="l-table-edit-td"><input name="pre_next_date" type="text" value="${pre_next_date }" id="pre_next_date"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">检测单位：</td>
            <td align="left" class="l-table-edit-td"><input name="outer_measure_org" type="text" id="outer_measure_org" value="${outer_measure_org }" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>检定结果：</td>
            <td align="left" class="l-table-edit-td">
            			<!-- <select id="measure_result" name="measure_result"> 
			            			<option value=""></option>
			                		<option value="0">完成</option>
			                		<option value="1">未完成</option>
			                	</select> -->
			               <input name="measure_result" type="text" id="measure_result" /> 	 	
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
			<td align="left" class="l-table-edit-td"><input
				name="dept_id" type="text" id="dept_id" /></td>
       </tr>
       <tr>
       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">委托单号：</td>
            <td align="left" class="l-table-edit-td">
            	 <input name="entrust_no" type="text" id="entrust_no" value="${entrust_no }"/> 	 	
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">鉴定依据：</td>
            <td align="left" class="l-table-edit-td">
            	 <input name="firm_basis" type="text" id="firm_basis" value="${firm_basis }"/> 	 	
            </td>
            <td align="left"></td>
       </tr>
       <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">检验说明：</td>
            <td align="left" class="l-table-edit-td" colspan="4">
            		<textarea rows="3" cols="70"  name="measure_idea" id="measure_idea"  >${measure_idea }</textarea>
            </td>
            <td align="left"></td>
        </tr> 
    </table>
      <div id="maingrid"></div>
 
    </body>
</html>
