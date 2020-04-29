<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var by_sum_sup = '${p08040 }';
    var useAudit = '${p08046 }';
    var is_dir = 0;
    var show_detail = 1;
	var renderFunc = {
			pur_type : function(value){
    			if(value == 1){
					return "自购订单";
				}else if(value == 2){
					return "统购订单";
				}
    		},
    		state : function(value){
    				if(value == '0'){
						return "已中止";
					}
    				if(useAudit==0){
	                    if(value == '2'){
			                return "未执行";
			            }
	                }
    				
	                if(useAudit==1){
	                    if(value == '1'){
			                return "未审核";
			            }
	                    if(value == '2'){
			                return "已审核";
			            }
	                }
	                    
					if(value == '3'){
					 	return "已执行";
					}
					if(value == '4'){
					 	return "部分执行";
					}
    				
    			},
    			is_dir:function(value){
    				if(value == 0){
						 return "否";
					}else if (value ==1){
						 return "是";
					}
    			}
			
	};
    
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		//query();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0 ;
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()});//日期范围：开始时间 
    	  grid.options.parms.push({name:'end_date',value:$("#end_date").val()});//日期范围：结束时间
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]});//编制部门
    	  grid.options.parms.push({name:'state',value:liger.get("planState").getValue()});//状态
    	  grid.options.parms.push({name:'brif',value:$("#brif").val()});//摘要
    	  grid.options.parms.push({name:'pur_type',value:liger.get("pur_type").getValue()});//计划类型
    	  if(liger.get("pur_type").getValue()=='2'){
    		  grid.options.parms.push({name:'req_hos_id',value:liger.get("req_hos_id").getValue().split(",")[0]});//请购单位
        	  grid.options.parms.push({name:'pur_hos_id',value:liger.get("pur_hos_id").getValue().split(",")[0]});//请购单位
    	  }
    	  grid.options.parms.push({name:'pur_code',value:$("#pur_code").val()});
    	  grid.options.parms.push({name:'is_dir',value:liger.get("is_dir").getValue()});//是否定向
    	  if(liger.get("is_dir").getValue()=='1'){//定向部门
    		  grid.options.parms.push({name:'dir_dept_id',value:liger.get("dir_dept_id").getValue().split(",")[0]});//定向科室
    	  }
    	  if(show_detail == 1){
    		  grid.options.parms.push({name:'sup_id',value:liger.get("sup_code").getValue().split(",")[0]});//供应商
    	  }
    	  grid.options.parms.push({name:'app_dept_id',value:liger.get("app_dept").getValue().split(",")[0]});//申请科室/部门id
    	  grid.options.parms.push({name:'app_dept_no',value:liger.get("app_dept").getValue().split(",")[1]});//申请科室/部门no
    	  grid.options.parms.push({name:'app_dept_code',value:liger.get("app_dept").getText().split(" ")[0]});//申请科室/部门code
    	  grid.options.parms.push({name:'app_dept_name',value:liger.get("app_dept").getText().split(" ")[1]});//申请科室/部门name
    	  grid.options.parms.push({name:'inv_msg',value:$("#inv_msg").val()});//药品信息
    	
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    
    function loadHead(){
    	if(show_detail=="1"){
    		grid = $("#maingrid").ligerGrid({
 	           columns: [ 
						 { display: '供应商', name: 'sup_name', align: 'left',width:200/* ,
							 render : function(rowdata,rowindex,value){
 		                    	 return '<a href=javascript:openSupUpdate("' 
 									+ rowdata.sup_id +','+ rowdata.sup_no
 									+ '")>'+rowdata.sup_name+'</a>';
 	                     	 } */
						 },
						 { display: '单据来源', name: 'field_desc',width:100, align: 'left'},
						 { display: '制单人', name: 'maker', align: 'left',width:100},
						 { display: '申请部门/仓库', name: 'dept_names', align: 'left',width:120},
						 { display: '药品编码', name: 'inv_code', align: 'left',width:110},
 						 { display: '药品名称', name: 'inv_name', align: 'left',width:240},
 						 { display: '规格型号', name: 'inv_model', align: 'left',width:160},
 						 { display: '计量单位', name: 'unit_name', align: 'left',width:80},
 						 { display: '单价', name: 'price', align: 'right',width:80},
 						 { display: '数量', name: 'amount', align: 'right',width:80},
 	                     { display: '计划编号', name: 'pur_code', align: 'left',width:120,
 	                    	 render : function(rowdata,rowindex,value){
 		                    	 return '<a href=javascript:openUpdate("' 
 									+ rowdata.pur_id +','
 									+ rowdata.order_state
 									+ '")>'+rowdata.pur_code+'</a>';
 	                     	 }
 						 },
 						 { display: '交易编码', name: 'bid_code', align: 'left',width:110},
 						 { display: '生产厂商', name: 'fac_name', align: 'left',width:200},
						 { display: '编制日期', name: 'make_date', align: 'left' ,width:100},
						 { display: '药品类别', name: 'med_type_name', align: 'left' ,width:110},
 	                     { display: '编制部门', name: 'dept_name', align: 'left',width:100},
 						 { display: '摘要', name: 'brif', align: 'left',width:100},
 						 { display: '计划类型', name: 'pur_type', align: 'left',width:100,
 			                  render : function(rowdata){
 			                      if(rowdata.pur_type == '1'){
 			                        return "自购计划";
 			                      }
 			                      if(rowdata.pur_type == '2'){
 			                        return "统购计划";
 			                      }
 			                    }   
 			                },
 						 { display: '请购单位', name: 'req_hos_name', align: 'left',width:120},
 						 { display: '付款单位', name: 'pay_hos_name', align: 'left',width:120},
 						 { display: '状态', name: 'state', align: 'left',width:100,
 			                  render : function(rowdata){
 			                    if(rowdata.state == '0'){
 			                      	return "已中止";
 			                    }
 			                    if(useAudit==0){
 			                    	if(rowdata.state == '2'){
 		 			                	return "未执行";
 		 			                }
 			                    }
 			                    if(useAudit==1){
 			                    	if(rowdata.state == '1'){
 		 			                	return "未审核";
 		 			                }
 			                    	if(rowdata.state == '2'){
 		 			                	return "已审核";
 		 			                }
 			                    }
 			                    if(rowdata.state == '3'){
 			                      return "已执行";
 			                    }
 			                    if(rowdata.state == '4'){
 			                      return "部分执行";
 			                    }
 			                  }
 			              },
 			             { display: '定向计划', name: 'is_dir', align: 'left',width:100,
 			                  render : function(rowdata){
	 			                    if(rowdata.is_dir == '1'){
	 			                      	return "是";
	 			                    }
	 			                    if(rowdata.is_dir =='0'){
	 			                      	return "否";
	 			                    }
 			                  }
 			             },
 						 { display: '定向科室', name: 'dir_dept_name', align: 'left',width:100 },
 						 { display: '计划到货日期', name: 'arrive_date',width:100, align: 'left'},
 						 { display: '生成订单', name: 'order_state',width:100, align: 'left',hide:true}
 	                     ],
 	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedPurMain.do?isCheck=true&show_detail=1',
 	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,isScroll:true,
 	                     selectRowButtonOnly:true,//heightDiff: -10,
 	                     toolbar: { items: [
 	                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
 	                     	{ line:true },
 	        				{ text: '添加（<u>N</u>）', id:'addNew', click: addNew, icon:'add' },
 	        				{ line:true },
 	        				{ text: '删除明细（<u>D</u>）', id:'delete', click: deleteDetail,icon:'delete'},
 	        				{ line:true },
 	        				/*{ text: '审核', id:'audit', click: audit, icon:'bluebook',hide:true },
 	        				{ line:true ,hide:true},
 	        				{ text: '取消审核', id:'unaudit', click: unaudit,icon:'bookpen' ,hide:true},
 	        				{ line:true ,hide:true},
 	        				{ text: '中止计划（<u>M</u>）', id:'suspend', click: suspend,icon:'cut' ,hide:true}, 
 	        				{ line:true ,hide:true}, */
 	        				{ text: '按基数生成（<u>G</u>）', id:'secuLimitGen', click: secuLimitGen, icon:'settle'},
	       				    { line:true},
 	        			    { text: '生成订单（<u>G</u>）', id:'purImp', click: purImp, icon:'settle' ,hide: by_sum_sup == 1 ? true : false},
 	       				    { line:true, hide: by_sum_sup == 1 ? true : false}, 
 	        				{ text: '生成订单（<u>I</u>）', id:'purImpN', click: purImpN, icon:'settle' ,hide: by_sum_sup == 0 ? true : false},
 	       				    { line:true ,hide: by_sum_sup == 0 ? true : false},
 	        				{ text: '导入科室需求计划（<u>T</u>）', id:'produceDept', click: produceDept,icon:'copy' },
 	        				{ line:true },
 	        				{ text: '导入仓库需求计划（<u>R</u>）', id:'produceStore', click: produceStore,icon:'copy' },
	        				{ line:true } ,
	        				{ text: '模板设置', id:'printSetDetail', click: printSetDetail, icon:'printSetDetail' },
	        				{ line:true } ,
	        				{ text: '批量打印', id:'printDetail', click: printDetail, icon:'print' },
	        				{ line:true } ,
	        				{ text: '关闭药品', id:'closeInv', click: closeInv, icon:'close' },
	        				{ line:true } ,
	        				{ text: '关闭药品列表', id:'closeInvInfo', click: closeInvInfo, icon:'search' },
	        				{ line:true } 
 	                     	]},  
 	       				onDblClickRow : function (rowdata, rowindex, value){
 	   						changeSupCode(rowdata);
 	   					}
 	            });
    	}else{
    		grid = $("#maingrid").ligerGrid({
    	           columns: [ 
    	                     { display: '计划编号', name: 'pur_code', align: 'left',width:120,
    	                    	 render: function(rowdata,rowindex,value){
    		                    	 return '<a href=javascript:openUpdate("' 
    									+ rowdata.pur_id +','
    									+ rowdata.state
    									+ '")>'+rowdata.pur_code+'</a>';
    	                     	 }
    						 },
							 { display: '编制日期', name: 'make_date', align: 'left' ,width:100},
    						 { display: '单据来源', name: 'field_desc',width:100, align: 'left'},
    	                     { display: '编制部门', name: 'dept_name', align: 'left',width:100},
    						 { display: '摘要', name: 'brif', align: 'left',width:100},
    						 { display: '计划类型', name: 'pur_type', align: 'left',width:100,
    		                     render:
    		                       function(rowdata){
    		                         if(rowdata.pur_type == '1'){
    		                           return "自购计划";
    		                         }
    		                         if(rowdata.pur_type == '2'){
    		                           return "统购计划";
    		                         }
    		                         
    		                       }   
    		                 },
    	                     { display: '采购单位', name: 'pur_hos_name', align: 'left',width:120},
    						 { display: '请购单位', name: 'req_hos_name', align: 'left',width:120},
    						 { display: '付款单位', name: 'pay_hos_name', align: 'left',width:120},
    						 { display: '制单人', name: 'maker', align: 'left',width:100},
    						  { display: '审核人', name: 'checker', align: 'left',width:100,hide : useAudit == 0 ? true : false},
    						 { display: '审核日期', name: 'check_date', align: 'left',width:100,hide : useAudit == 0 ? true : false}, 
    						 { display: '状态', name: 'state', align: 'left',width:100,render:
    		                     function(rowdata){
    		                       if(rowdata.state == '0'){
    		                         return "已中止";
    		                       }
    		                       if(useAudit==0){
    			                    	if(rowdata.state == '2'){
    		 			                	return "未执行";
    		 			                }
    			                    }
    			                    if(useAudit==1){
    			                    	if(rowdata.state == '1'){
    		 			                	return "未审核";
    		 			                }
    			                    	if(rowdata.state == '2'){
    		 			                	return "已审核";
    		 			                }
    			                    }
    		                       if(rowdata.state == '3'){
    		                         return "已执行";
    		                       }
    		                       if(rowdata.state == '4'){
    		                          return "部分执行";
    		                        }
    		                     }
    		                   },
    		                   { display: '定向计划', name: 'is_dir', align: 'left',width:100,
    		                     render:function(rowdata){
    		                       if(rowdata.is_dir == '1'){
    		                         return "是";
    		                       }
    		                       if(rowdata.is_dir =='0'){
    		                         return "否";
    		                       }
    		                    
    		                     }
    		                   },
    						 { display: '定向科室', name: 'dir_dept_name', align: 'left',width:100 },
    						 { display: '计划到货日期', name: 'arrive_date',width:100, align: 'left'}
    	                     ],
    	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedPurMain.do?isCheck=true&show_detail=0',
    	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,isScroll:true,
    	                     selectRowButtonOnly:true,//heightDiff: -10,
    	                     toolbar: { items: [
    	                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
    	                     	{ line:true },
    	        				{ text: '添加（<u>N</u>）', id:'addNew', click: addNew, icon:'add' },
    	        				{ line:true },
    	        				{ text: '删除（<u>D</u>）', id:'delete', click: del,icon:'delete' },
    	        				{ line:true },
    	        				{ text: '审核', id:'audit', click: audit, icon:'bluebook' ,hide: useAudit == 0 ? true : false},
    	        				{ line:true  ,hide: useAudit == 0 ? true : false},
    	        				{ text: '取消审核', id:'unaudit', click: unaudit,icon:'bookpen'  ,hide: useAudit == 0 ? true : false},
    	        				{ line:true  ,hide: useAudit == 0 ? true : false}, 
    	        				{ text: '按基数生成（<u>G</u>）', id:'secuLimitGen', click: secuLimitGen, icon:'settle'},
    	       				    { line:true},
    	        				{ text: '生成订单（<u>I</u>）', id:'purImp', click: purImp, icon:'settle' },
    	        				{ line:true }, 
    	        				{ text: '导入科室需求计划（<u>T</u>）', id:'produceDept', click: produceDept,icon:'copy' },
    	        				{ line:true },
    	        				{ text: '导入仓库需求计划（<u>R</u>）', id:'produceStore', click: produceStore,icon:'copy' },
    	        				{ line:true },
    	        				{ text: '中止计划（<u>M</u>）', id:'suspend', click: suspend,icon:'cut' },
    	        				{ line:true },
    	        				{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
    	        				{ line:true } ,
    	        				{ text: '批量打印', id:'print', click: print, icon:'print' },
    	        				{ line:true }
    	        			
    	                     	]}
    	            });
    	}
    	
        gridManager = $("#maingrid").ligerGetGridManager();
    }

    /* 删除明细 */
    function deleteDetail(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
	 		var ParamVo =[];
	 		var msgInv="";
	 		$.each(data,function(i, v){
				if(useAudit == 0){
					if(v.state != 2 ){
						msgInv = msgInv + v.inv_code+",";
						return;
					}
				}
				if(useAudit == 1){
					if(v.state != 1 ){
						msgInv = msgInv + v.inv_code+",";
						return;
					}
				}
	 			
	 		});
	 		
	 		if (msgInv != "") {
	 			if(useAudit == 0){
	 				$.ligerDialog.warn( msgInv +"  所在单据不是未执行状态！");
	 			}else{
	 				$.ligerDialog.warn( msgInv +"  所在单据不是未审核状态！");
	 			}
				return false;
			} 
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.pur_id +"@"+ 
					this.pur_detail_id
				) 
			});
			$.ligerDialog.confirm('确定要删除选中的材料？', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMedPurDetailInv.do", {ParamVo : ParamVo.toString()}, function(responseData){
			            if(responseData.state=="true"){
							query();
			            }
					});
				}
			});
		}
    }
    
    //关闭药品
    function closeInv(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
	 		var ParamVo =[];
	 		var msgInv="";
	 		$.each(data,function(i, v){
				if(useAudit == 0){
					if(v.state != 2 ){
						msgInv = msgInv + v.inv_code+",";
						return;
					}
				}
				if(useAudit == 1){
					if(v.state != 1 ){
						msgInv = msgInv + v.inv_code+",";
						return;
					}
				}
	 			
	 		});
	 		
	 		if (msgInv != "") {
	 			if(useAudit == 0){
	 				$.ligerDialog.warn( msgInv +"  所在单据不是未执行状态！");
	 			}else{
	 				$.ligerDialog.warn( msgInv +"  所在单据不是未审核状态！");
	 			}
				return false;
			} 
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.pur_id +"@"+ 
					this.pur_detail_id
				) 
			});
			$.ligerDialog.confirm('确定要关闭选中的药品？', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateMedPurCloseInv.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData){
			            if(responseData.state=="true"){
							query();
			            }
					});
				}
			});
		}
    }
    //查看关闭药品列表
    function closeInvInfo(){
		$.ligerDialog.open({
			url: "medPurMainCloseInvInfoPage.do?isCheck=false", 
			height: 500,width: 900, title:'关闭药品列表',
			modal:true,showToggle:false,showMax:true,showMin: false,isResize:true
		});
    }
    
    //改变供应商
	function changeSupCode(rowdata){		
		liger.get("sup_code").setValue(rowdata.sup_id+","+rowdata.sup_no);
		liger.get("sup_code").setText(rowdata.sup_code+" "+rowdata.sup_name);
		//alert(rowdata.sup_code+" "+rowdata.sup_name);
		query();
    }
  
    //查看供应商信息
    function openSupUpdate(obj){
    	var vo = obj.split(",");
		var paras = "sup_id="+vo[0]+"&sup_no="+vo[1];
		$.ligerDialog.open({
			title: '供应商信息',
			height: 450,
			width: 1000,
			url: 'medPurSupMainPage.do?isCheck=false&' + paras,
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true, top: 1,
		});
	
    }
    
  	 //审核
    function audit(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	 var Param =[];
        	 var flag;
             $(data).each(function (){
            	if(this.state != '1'){
            		flag = false;
            	}
				Param.push(
					//表的主键
					this.group_id+"@"+
					this.hos_id+"@"+
					this.copy_code+"@"+
					this.pur_id+"@"+
					this.state
				)
             });
             if(flag == false){
            	 $.ligerDialog.error('只能审核状态为未审核的数据');
            	  return ;
             }
           $.ligerDialog.confirm('确定审核?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("checkMedPurMain.do?isCheck=flase&paramVo="+Param,{},
                			function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            });  
        }
    }
    //取消审核
    function unaudit(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	 var Param =[];
        	 var flag;
             $(data).each(function (){
            	if(this.state != '2'){
            		flag = false;
            	}
				Param.push(
					//表的主键
					this.group_id+"@"+
					this.hos_id+"@"+
					this.copy_code+"@"+
					this.pur_id+"@"+
					this.state
				)
             });
             if(flag == false){
            	 $.ligerDialog.error('只能取消已审核的数据');
            	 return ;
             }
           $.ligerDialog.confirm('确定取消审核?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("cancelCheckMedPurMain.do?isCheck=flase&paramVo="+Param,{},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            });  
        }
    } 
    
    //按需求计划生成 生成完跳转添加页面
    function pur_add(para){
    	parent.$.ligerDialog.open({
    		url: 'hrp/med/purchase/make/medRequireProdAddPage.do?isCheck=false&para='+para+'&is_dir='+is_dir,
			height: $(window).height(),
			width: $(window).width(),
    		title:'添加',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,top:1,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    		buttons: [ 
    			{ text: '保存', onclick: function (item, dialog) { dialog.frame.saveMedPurMain(); },cls:'l-dialog-btn-highlight' }, 
    			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    		] 
    	});
    }
    
    function addNew(){
    	parent.$.ligerDialog.open({
  			url: 'hrp/med/purchase/make/addMedPurMainPage.do?isCheck=false',
			height: $(window).height(), 
			width: $(window).width(), 
  			title:'添加',modal:true,showToggle:false,showMax:true,showMin: true,isResize:true,top:1,
			parentframename: window.name  //用于parent弹出层调用本页面的方法或变量
  			
  		});
    }
    //删除
    function del(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	
        	var Param =[];
        	 var flag;
        	 var msgInv="";
             $(data).each(function (){
            	if(useAudit == 0){
 					if(this.state != 2 ){
 						flag = false;
 					}
 				}
 				if(useAudit == 1){
 					if(this.state != 1 ){
 						flag = false;
 					}
 				}
 				
				Param.push(
					//表的主键
					this.group_id+"@"+
					this.hos_id+"@"+
					this.copy_code+"@"+
					this.pur_id+"@"+
					this.come_from
				)
             });
             if(flag == false){
            	 if(useAudit == 0){
            		 $.ligerDialog.error('只允许删除未执行的单据！');
            	 }else{
            		 $.ligerDialog.error('只允许删除未审核的单据！');
            	 }
            	 return ;
             }
             
             $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteMedPurMain.do?isCheck=false&paramVo="+Param,{},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            });  
        }
    }
    //中止
    function suspend(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	 var Param =[];
        	 var flag;
             $(data).each(function (){
            	if(useAudit == 0){
  					if(this.state != 2 ){
  						flag = false;
  					}
  				}
  				if(useAudit == 1){
  					if(this.state != 1 ){
  						flag = false;
  					}
  				}
  				
				Param.push(
					//表的主键
					this.group_id+"@"+
					this.hos_id+"@"+
					this.copy_code+"@"+
					this.pur_id+"@"+
					this.state
				)
             });
             if(flag == false){
            	 if(useAudit == 0){
            		 $.ligerDialog.error('只允许中止未执行的单据！');
            	 }else{
            		 $.ligerDialog.error('只允许中止未审核的单据！');
            	 }
            	 return ;
             }
           $.ligerDialog.confirm('确定中止计划?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("updateMedPurPlanState.do?isCheck=false&paramVo="+Param,{},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            });  
        }
    	
    }
     //按基数生成采购计划
    function secuLimitGen(){
    	$.ligerDialog.open({url: 'medPurPlanGenBySecuLimitPage.do?isCheck=false', height: 300,width: 500, 
			title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
			buttons: [ 
			           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedLocationType(); },cls:'l-dialog-btn-highlight' }, 
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			         ] 
		});
    }
    //科室需求计划生成
    function produceDept(){
    	parent.$.ligerDialog.open({
			title: '引入科室需求计划',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/purchase/make/medDeptDetailImpPage.do?isCheck=false', 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
    //仓库需求计划生成
    function produceStore(){
    	parent.$.ligerDialog.open({
			title: '引入仓库需求计划',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/purchase/make/medStoreDetailImpPage.do?isCheck=false', 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
    
   
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";   
    }
    
    function openUpdate(obj){ 	
    	var vo = obj.split(",");
		var paras = 
			"pur_id="+vo[0] +"&"+ 
			"state="+vo[1] ;
		parent.$.ligerDialog.open({
			title: '编制采购计划单',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/purchase/make/updateMedPurMainPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    
    }
    //生成订单
    function  purImpN(){
    	var paras ="sup_id="+liger.get("sup_code").getValue().split(",")[0] + 
		"&sup_no="+(liger.get("sup_code").getValue().split(",")==""?"":liger.get("sup_code").getValue().split(",")[1]) + 
		"&sup_text="+liger.get("sup_code").getText();
    	
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	var Param =[];
       	 	var flag;
            $(data).each(function(index,vo){
            	if(useAudit == 0){
  					if(this.state != 2 ){
  						flag = false;
  					}
  				}
  				if(useAudit == 1){
  					if(this.state != 1 ){
  						flag = false;
  					}
  				}
  				
            });
            
            if(flag == false){
            	if(useAudit == 0){
	           		$.ligerDialog.error('请选择未执行的单据！');
	           	}else{
	           		$.ligerDialog.error('请选择未审核的单据！');
	           	}
           	 	return ;
            }
            
            var formPara = {
    			allData : JSON.stringify(gridManager.getCheckedRows())
    		};
            
            ajaxJsonObjectByUrl("queryPurDetailCollectData.do?isCheck=false", formPara, function(responseData) {
				//console.log(responseData)
				var detailData = responseData;
					
				parent.$.ligerDialog.open({ 
					title: '生成订单',
					height: $(window).height(),
					width: $(window).width(),
					url : 'hrp/med/purchase/make/addOrderPage.do?isCheck=false&'+paras,
					data : detailData,
					modal:true,showToggle:false,showMax:true ,showMin: false,isResize:true,
					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				});
			});
            
            liger.get("sup_code").setValue("");
    		liger.get("sup_code").setText("");
        }
        
        
	}
    
	//采购计划生成订单
	function  purImp(){
		parent.$.ligerDialog.open({
			title: '生成订单',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/order/init/medOrderInitPurImpPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
    //键盘事件
	  function loadHotkeys() {	
		hotkeys('Q', query);
		hotkeys('N', addNew);
		hotkeys('D', del);
		hotkeys('M', suspend);
		hotkeys('I', purImp);
		hotkeys('T', produceDept);
		hotkeys('R', produceStore);
		hotkeys('B', downTemplate);
		hotkeys('E', exportExcel);
		//hotkeys('P', printDate);
	 }
    
    
	  //打印回调方法
	    function lodopPrint(){
	    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
	 		head=head+"<tr><td>制单日期："+$("#begin_date").val() +" 至  "+ $("#end_date").val()+"</td></tr>";
	 		head=head+"</table>";
	 		grid.options.lodop.head=head; 
	 		grid.options.lodop.fn=renderFunc;
	 		grid.options.lodop.title="采购计划";
	    }
	  
     //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","08113 物资仓库配套表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val()
         };
		ajaxJsonObjectByUrl("queryMedStoreMatch.do?isCheck=false",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.type_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopPrinterTable("resultPrint","开始打印","08113 物资仓库配套表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","08113 物资仓库配套表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val()
         };
		ajaxJsonObjectByUrl("queryMedStoreMatch.do?isCheck=false",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.type_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","08113 物资仓库配套表.xls",true);
	    },true,manager);
		return;
	 }
	 
	 function loadDict(){
         //字典下拉框
         if(useAudit=="0"){
        	 autoCompleteByData("#planState", medPurMain_state0.Rows, "id", "text", true, true,"",false);
         }
         if(useAudit=="1"){
        	 autoCompleteByData("#planState", medPurMain_state1.Rows, "id", "text", true, true,"",false);
         }
		 
		 autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true);//编制部门下拉框		 
		 autocomplete("#pur_type", "../../queryMedPlanType.do?isCheck=false", "id", "text", true, true);//计划类型下拉框
		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false);//是否定向
		 autocomplete("#pur_hos_id","../../queryMedHosInfoDict.do?isCheck=false","id","text",true,true);//采购单位下拉框 
		 autocomplete("#req_hos_id","../../queryMedHosInfoDict.do?isCheck=false","id","text",true,true);//请购单位下拉框 
		 autocomplete("#dir_dept_id","../../queryMedDeptDict.do?isCheck=false","id","text",true,true,{is_last:'1'},false);//定向科室
		 //autocomplete("#app_dept","../../queryPermDeptAndStoreDict.do?isCheck=false","id","text",true,true);//申请科室
		 loadComboBox({id:"#app_dept",url:"../../queryPermDeptAndStoreDict.do?isCheck=false",value:"id",text:"text",autocomplete:true,hightLight:true,selectBoxWidth:'247',maxWidth:'250',defaultSelect:false,async:false})
		 loadComboBox({id:"#sup_code",url:"../../queryHosSup.do?isCheck=false",value:"id",text:"text",autocomplete:true,hightLight:true,selectBoxWidth:'247',maxWidth:'250',defaultSelect:false,async:false})

		 //autocomplete("#sup_code","../../queryHosSup.do?isCheck=false","id","text",true,true);//供应商
		 autodate("#begin_date", "yyyy-mm-dd", "month_first");
		 autodate("#end_date", "yyyy-mm-dd", "month_last");
	   		
	   	 $("#stateSelect").ligerTextBox({width:160});
	     $("#planState").ligerTextBox({width:160});
	     $("#store_id").ligerTextBox({width:160});
	     $("#brif").ligerTextBox({width:247});
	     $("#sup_code").ligerTextBox({width:247});
	     $("#app_dept").ligerTextBox({width:247});
	     $("#dir_dept_id").ligerTextBox({width:160});
	     $("#pur_code").ligerTextBox({width:160});
	     $("#begin_date").ligerTextBox({width:100});
	     $("#end_date").ligerTextBox({width:100});
	     $("#inv_msg").ligerTextBox({width:160});
	  
      }
	 
	 function change(){	//采购类型变化,是否定向改变，显示定向部门，显示采购单位、请购单位
		 var para = "";
		 if(liger.get("pur_type").getValue()=='2'){
			 $(".demo").attr("style","visibility:true");
			 para = 1;
		 }else if(liger.get("pur_type").getValue()=='1'){		
			 $(".demo").attr("style","visibility:hidden");
			 para = 0;
		 }else{
			 $(".demo").attr("style","visibility:hidden");
		 }
		 
		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,para,'160');//是否定向
		 changeDir();
	 }
	 //定向改变实现定向科室
	 function changeDir(){
		 if(liger.get("is_dir").getValue()=='1'){ 
			 $("#dir_dept_id").attr("type","hidden");
			 $(".dept").attr("style","display:table-cell");
			 //loadHead();
		 }else{
			 $(".dept").attr("style","display:none");
			// loadHead();
		 }
	 }
	  //打印模板设置
	    function printSet(){
		  
	    	var useId=0;//统一打印
			officeFormTemplate({template_code:"08003",use_id:useId});
	    }

	  //打印
	    function print(){
	    	var useId=0;//统一打印
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
				
			var pur_id ="" ;
			var in_nos = "";
			$(data).each(function (){	
				
					in_nos = in_nos + this.in_no + "<br>";
					pur_id  += this.pur_id+","
			});
			
			var para={
					template_code: '08003',
					class_name: "com.chd.hrp.med.serviceImpl.purchase.make.MedPurMainServiceImpl",
					method_name: "queryMedPurMain",
					isPreview: true,//是否预览，默认直接打印
					paraId : pur_id.substring(0,pur_id.length-1),
					use_id: useId
			};
			officeFormPrint(para);
		}
	    	
	    }
	  
	//打印模板设置
	function printSetDetail(){
		  
		var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}
		 
		officeFormTemplate({template_code:"08003",use_id:useId});
	}
	
	//打印
	function printDetail(){
		var useId=0;//统一打印
		/* if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		} */

		//if($("#create_date_b").val())
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var pur_detail_ids ="" ;
			var pur_codes = "";
			$(data).each(function (){	
					pur_codes = pur_codes + this.pur_code + "<br>";
					pur_detail_ids  += this.pur_detail_id+","
			});
			
			var para={
				class_name:"com.chd.hrp.med.serviceImpl.purchase.make.MedPurMainServiceImpl",
				method_name:"queryMedPurMainDetail",
				bean_name:"medPurMainService",
				paraId: pur_detail_ids.substring(0,pur_detail_ids.length-1) ,
				template_code:'08003',
				isPreview:true,//预览窗口，传绝对路径
				use_id:useId
			}; 
			//printTemplate("hrp/med/purchase/make/queryMedMakeByDetailPrintTemlate.do?isCheck=false",para);
			officeTablePrint(para);
		}
	}
	 function showDetail(){
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if(show_detail == 1){ 
			 $("#sup_code").attr("type","hidden");
			 $(".sup").attr("style","display:table-cell");
		 }else{
			 $(".sup").attr("style","display:none");
		 }
		loadHead();
		query();
	 }
	 
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"> 编制日期： </td>
			<td align="left" class="l-table-edit-td">
                <table>
                    <tr>
                        <td align="right" class="l-table-edit-td">
                            <input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
                        </td>
                        <td align="right" class="l-table-edit-td"> 至： </td>
                        <td align="left" class="l-table-edit-td">
                            <input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
                        </td>
                    </tr>
                </table>
			</td>
			
            <td align="right" class="l-table-edit-td" >编制部门：</td>
            <td align="left" class="l-table-edit-td">
                <input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            
            <td align="right" class="l-table-edit-td" >状态：</td>
            <td align="left" class="l-table-edit-td">
        		<input name="planState" type="text" id="planState" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
        </tr>
        
        <tr>
        	<td align="right" class="l-table-edit-td"  >摘要：</td>
			<td align="left" class="l-table-edit-td">
                <input name="brif" id="brif" type="text"/>
            </td>
			
            <td align="right" class="l-table-edit-td" >计划类型：</td>
            <td align="left" class="l-table-edit-td">
                <input name="pur_type" type="text" id="pur_type" ltype="text" onChange="change();" validate="{required:true,maxlength:20}" />
             </td>
            
            <td align="right" class="l-table-edit-td">单据号：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="pur_code" type="text" requried="false" id="pur_code" />
			</td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td" >申请部门/仓库：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="app_dept" type="text" id="app_dept" ltype="text" />
            </td>
            
            <td align="right" class="l-table-edit-td">是否定向：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_dir" type="text" id="is_dir" ltype="text" onChange="changeDir()"/>
            </td>
            
            <td align="right" class="l-table-edit-td" >药品信息：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="inv_msg" type="text" id="inv_msg" ltype="text" />
            </td>
        </tr> 
        <tr >
        	
			<td align="right" class="l-table-edit-td sup" >供应商：</td>
            <td align="left" class="l-table-edit-td sup">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" />
            </td>

            <td align="right" class="l-table-edit-td dept" >定向科室：</td>
            <td align="left" class="l-table-edit-td dept" >
                <input name="dir_dept_id" type="text" id="dir_dept_id" ltype="text"  />
            </td>
            
             <td align="right" class="l-table-edit-td"></td>
            <td align="left" class="l-table-edit-td">
                <input name="show_detail" type="checkbox" id="show_detail" checked="checked" onclick="showDetail();" />&nbsp;显示明细
            </td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td demo" >采购单位：</td>
            <td align="left" class="l-table-edit-td demo">
                <input name="pur_hos_id" type="text" id="pur_hos_id" ltype="text" />
            </td>
                            
            <td align="right" class="l-table-edit-td demo" >请购单位：</td>
            <td align="left" class="l-table-edit-td demo">
                <input name="req_hos_id" type="text" id="req_hos_id" ltype="text"  />
            </td>
           
        </tr>
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
				<tr>
	                <th width="200">配套表ID</th>	
	                <th width="200">配套表名称</th>	
	                <th width="200">仓库ID</th>	
	                <th width="200">科室ID</th>	
				</tr>
			 </thead>
			<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
