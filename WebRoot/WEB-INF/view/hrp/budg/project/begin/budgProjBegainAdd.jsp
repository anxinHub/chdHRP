<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
			    var dataFormat,grid,budg_year,proj_id,con_emp_id,state;
			    var source_id,payment_item_id,payment_item_no;
			    var gridManager=null;
			    
			    $(function (){
					   	   loadHead() ;
					       loadDict()//加载下拉框
					       loadForm();
					       //获取下拉框的值
			        	   budg_year = liger.get("budg_year").getValue();
			        	   proj_id=liger.get("proj_id").getValue();
			        	   con_emp_id = liger.get("con_emp_id").getValue();
			        	   state=liger.get("state").getValue();
			       $("body").keydown(function() {
			            if (event.keyCode == "9") {//keyCode=9是Tab
			             grid.addRowEdited({
			               	source_id: '' ,
			                budg_amount:0,
			                in_amount:0,
			                cost_amount:0,
			                remain_amount:0
			         		});
			             }          
			        });
			        
			        $("#budg_year").change(function(){
			         	  
			        	 budg_year = liger.get("budg_year").getValue();
			         	  
			         	  loadHead();
			        })
			         $("#proj_id").change(function(){
			         	  
			        	 proj_id = liger.get("proj_id").getValue();
			         	  
			         	  loadHead();
			        })
			         $("#con_emp_id").change(function(){
			         	  
			        	 con_emp_id = liger.get("con_emp_id").getValue();
			         	  
			         	  loadHead();
			        })
			         $("#state").change(function(){
			         	  
			        	 state=liger.get("state").getValue();
			         	  
			         	  loadHead();
			        })
			        
			    });   
			    function loadHead(){
			    	grid = $("#maingrid").ligerGrid({
				           columns: [ 
				        	   { display: '来源ID', name: 'source_id', align: 'left',valueField:'id',textField:'text',
				        		   editor : {
										type : 'select',
										valueField : 'id',
										textField : 'text',
										url : 'queryBudgSourceId.do?isCheck=false&source_id='+source_id,
										keySupport : true,
										autocomplete : true,
									} 
						 						},
					 		   { display: '支出项目ID', name: 'payment_item_id', align: 'left',valueField:'itemid',textField:'itemtext',
						 							editor : {
														type : 'select',
														valueField : 'itemid',
														textField : 'itemtext',
														url : 'queryBudgPaymentItemId.do?isCheck=false&payment_item_id='+payment_item_id,
														keySupport : true,
														autocomplete : true,
													} 
					 		 					},
						 		 { display: '支出项目变更号', name: 'payment_item_no', align: 'left',valueField:'idno',textField:'textno',
					 		 						editor : {
														type : 'select',
														valueField : 'idno',
														textField : 'textno',
														url : 'queryBudgPaymentItemNo.do?isCheck=false&payment_item_no='+payment_item_no,
														keySupport : true,
														autocomplete : true,
													} 
							 					},
				 				{ display: '期初预算金额', name: 'b_budg_amount', align: 'left',editor : {type : 'float'},
										render:function(rowdata,rowindex,value){
											if(value){
												return formatNumber(value,2,1);
											}
										}						 			
							 		},
							 	{ display: '期初到账金额', name: 'b_in_amount', align: 'left',editor : {type : 'float'},
										render:function(rowdata,rowindex,value){
											if(value){
												return formatNumber(value,2,1);
											}
										}							 			
							 		},
							 		
							 	 { display: '期初支出金额', name: 'b_cost_amount', align: 'left',editor : {type : 'float'},
										render:function(rowdata,rowindex,value){
											if(value){
												return formatNumber(value,2,1);
											}
										}							 			
							 		},	
							 		 { display: '期初预算金额', name: 'b_remain_amount', align: 'left',editor : {type : 'float'},
										render:function(rowdata,rowindex,value){
											if(value){
												return formatNumber(value,2,1);
											}
										}							 			
							 		},
							 		
				                     { display: '预算金额', name: 'budg_amount', align: 'left',editor : {type : 'float'},
												render:function(rowdata,rowindex,value){
													if(value){
														return formatNumber(value,2,1);
													}
												}							 			
									 		},
									 		 { display: '预算余额', name: 'remain_amount', align: 'left',editor : {type : 'float'},
												render:function(rowdata,rowindex,value){
													if(value){
														return formatNumber(value,2,1);
													}
												}							 			
									 		},
				                     { display: '到账金额', name: 'in_amount', align: 'left',editor : {type : 'float'},
									 			render:function(rowdata,rowindex,value){
													if(value){
														return formatNumber(value,2,1);
													}
									 			}
									 		},
				                     { display: '支出金额', name: 'cost_amount', align: 'left',editor : {type : 'float'},
									 			render:function(rowdata,rowindex,value){
													if(value){
														return formatNumber(value,2,1);
													}
									 			}
									 		},
							 		  { display: '预算金额累计', name: 't_budg_amount', align: 'left',editor : {type : 'float'},
							 			render:function(rowdata,rowindex,value){
											if(value){
												return formatNumber(value,2,1);
											}
							 			}
									 		},

							 		{ display: '到账金额累计', name: 't_in_amount', align: 'left',editor : {type : 'float'},
							 			render:function(rowdata,rowindex,value){
											if(value){
												return formatNumber(value,2,1);
											}
							 			}
									 		},
							 		{ display: '支出金额累计', name: 't_cost_amount', align: 'left',editor : {type : 'float'},
							 			render:function(rowdata,rowindex,value){
											if(value){
												return formatNumber(value,2,1);
											}
							 			}
									 		},
									 		
							 		{ display: '可用金额', name: 't_cost_amount', align: 'left',editor : {type : 'float'},
							 			render:function(rowdata,rowindex,value){
											if(value){
												return formatNumber(value,2,1);
											}
							 			}
											 		},
								 		{ display: '可用金额', name: 'usable_amount', align: 'left',editor : {type : 'float'},
								 			render:function(rowdata,rowindex,value){
												if(value){
													return formatNumber(value,2,1);
												}
								 			}
													},
							 		{ display: '预算金额累计', name: 'y_budg_amount', align: 'left',editor : {type : 'float'},
							 			render:function(rowdata,rowindex,value){
											if(value){
												return formatNumber(value,2,1);
											}
							 			}
															 		},
							 		{ display: '支出金额累计', name: 'y_cost_amount', align: 'left',editor : {type : 'float'},
							 			render:function(rowdata,rowindex,value){
											if(value){
												return formatNumber(value,2,1);
											}
							 			}
																	},
				                     { display: '审核日期', name: 'check_date', align: 'left',type:'date',format:"yyyy-MM-dd" ,align: 'left',
												editor : {type:'date'}

									 		},
							 	    { display: '执行进度', name: 'rate', align: 'left',editor : {type : 'float'},
									 			render:function(rowdata,rowindex,value){
													if(value){
														return formatNumber(value,2,1);
													}
									 			}

									 		},
									 	  
				                     ],	                    
			                  dataAction: 'server',dataType: 'server',checkbox: true,usePager:true,
			                   width: '100%', height: '100%',isAddRow:false,rownumbers:true,enabledEdit:true,
			                   selectRowButtonOnly:true,//heightDiff: -10,
			                   toolbar: { 
			                   	items: [
			          						{ text: '保存', id:'add', click: save , icon:'add' },
			          	                	{ line:true },
			          	                	{ text: '删除', id:'delete', click: del ,icon:'delete' },
			          	               		{ line:true }
			          				]},
						});
					gridManager = $("#maingrid").ligerGetGridManager();
			}      
   
			    function  save(){   	 
			   	  var data = gridManager.getData();
			       budg_year = liger.get("budg_year").getValue();
				   proj_id=liger.get("proj_id").getValue();
				   con_emp_id = liger.get("con_emp_id").getValue();
				   state=liger.get("state").getValue();		   	 
			   	 if(!budg_year){
			   		 $.ligerDialog.error('预算年度不能为空');
			   	 }
			   	 if(!proj_id){
			   		 $.ligerDialog.error('项目名称不能为空');
			   	 }
			   	 if(!con_emp_id){
			   		 $.ligerDialog.error('项目负责人不能为空');
			   	 }
			   	
			   	 if (data.length == 0){
			        	$.ligerDialog.error('请添加行数据');
			        }else{
			       	 if(!validateGrid(data)){
			       		 return false;
			       	 }
				    	 var ParamVo =[];
				         $(data).each(function (){					
				        	 ParamVo.push(					
				        		budg_year   +"@"+ 
				        		proj_id +"@"+
				        		con_emp_id +"@"+
				        		state +"@"+
				        		this.source_id +"@"+
				        		this.payment_item_id +"@"+
				        		this.payment_item_no +"@"+
				        		this.budg_amount +"@"+
				        		this.in_amount +"@"+
				        		this.cost_amount +"@"+
				        		this.check_date +"@"+
								this.b_budg_amount+"@"+
								this.b_in_amount+"@"+
								this.b_cost_amount+"@"+
								this.b_remain_amount+"@"+
								this.remain_amount+"@"+
								this.rate+"@"+
								this.t_budg_amount+"@"+
								this.t_in_amount+"@"+
								this.t_cost_amount+"@"+
								this.y_budg_amount+"@"+
								this.y_cost_amount+"@"+
								this.usable_amount
								)
				         });
				        ajaxJsonObjectByUrl(" addBudgProjBegain.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
				            
				            if(responseData.state=="true"){
				                parent.query();
				                grid.deleteAllRows();
				                
				            }
				        });
				        
			       }
			   }
    function del(){
   	 var data = grid.getCheckedRows();
  		 if(data.length == 0){
  				$.ligerDialog.error('请选择行');
               return;
           }else{
           	 for (var i = 0; i < data.length; i++){
           		 grid.remove(data[i]);
                } 
           }
   }
   function validateGrid(data) {  
		var msg="";
		var rowm = "";
		//判断grid 中的数据是否重复或者为空
		var targetMap = new HashMap();
		$.each(data,function(i, v){
			rowm = "";
			if (v.source_id == "" || v.source_id == null || v.source_id == 'undefined') {
				rowm+="[资金来源]、";
			} 
			if(rowm != ""){
 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
 			}
 			msg += rowm;
 			var key=v.source_id 
 			var value="第"+(i+1)+"行";
 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
 				targetMap.put(key ,value);
 			}else{
 				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
 			}
 		});
 		if(msg != ""){
 			$.ligerDialog.warn(msg);  
 			return false;  
 		}else{
 			return true;  
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
  
   function saveBudgDeptBasicIndexData(){
       if($("form").valid()){
           save();
       }
  }
   function loadDict(){
           //字典下拉框
   	//加载年度   
       autocomplete("#budg_year","../../queryBudgYear.do?isCheck=false","id","text",true,true,'',true);
       //加载项目名称
       autocomplete("#proj_id","../../queryProjName.do?isCheck=false","id","text",true,true,'',true); 
       //加载项目负责人
       autocomplete("#con_emp_id","../../queryConEmpId.do?isCheck=false","id","text",true,true,'',true);     
 	   //加载状态
       autocomplete("#state","../../queryBudgState.do?isCheck=false","id","text",true,true,'',true);  
    } 
    </script>
  
  </head>
  
		<body>
		   <div id="pageloading" class="l-loading" style="display: none"></div>
		   <form name="form1" method="post"  id="form1" >
       	<table cellpadding="0" cellspacing="0" class="l-table-edit" >		
	         <tr>
					            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度：</td>
					            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
					            <td align="left"></td>
					            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目名称：</td>
					            <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:true,maxlength:50}" /></td>
					            <td align="left"></td>
					            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人：</td>
					            <td align="left" class="l-table-edit-td"><input name="con_emp_id" type="text" id="con_emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
					            <td align="left"></td>
	        </tr> 
	        <tr>
					            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
					            <td align="left" class="l-table-edit-td"><input name="state" type="text" id="state" ltype="text" validate="{required:true,maxlength:20}" /></td>
					            <td align="left"></td>
	        </tr>   
	    </table>
	    </form>
    <div id="toptoolbar" ></div>
   	<div id="maingrid"></div> 
    </body>
</html>
