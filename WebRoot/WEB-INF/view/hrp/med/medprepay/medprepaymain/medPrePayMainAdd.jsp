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
    <script src="<%=path%>/lib/map.js"></script>
    <script type="text/javascript">
     var dataFormat;
     var inMainGrid;
     var detailGrid;
     var gridManager;
     var detailManager;
     var data;
     var grid;
     var year;
     var month;
     var init = "0" ;
     var targetMap = new HashMap();//存放key 对应关系
     
     $(function (){
    	 
         loadDict();
         
         loadHead(null);
         
         loadForm();
         
         loadHotkeys();
         
		 $("#sup_id").change(function(){
			 query();
		 })
		 
     });  
     
     //查询
     function  query(){
    	if(!liger.get("sup_id").getValue()){
   			$.ligerDialog.error("请先选择供应商");
		}else{
			inMainGrid.options.parms=[];
			inMainGrid.options.newPage=1;
			      //根据表字段进行添加查询条件
			inMainGrid.options.parms.push({
				name : 'sup_id',
				value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]
			});
			inMainGrid.options.parms.push({
				name : 'init',	value : init
			});
			inMainGrid.options.parms.push({
				name : 'pay_bill_type',	value : $("#pay_bill_type").val()
			});
			//加载查询条件
			inMainGrid.loadData(inMainGrid.where);
		}
     }
     function loadHead(){
     	 inMainGrid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '入库单号', name: 'in_no', align: 'left',width:140
 					 		},
 					  { display: '摘要', name: 'brief', align: 'left',width:180
 					 		},
                      { display: '仓库', name: 'store_name', align: 'left',width:100
 					 		},
 					  { display: '供应商', name: 'sup_name', align: 'left',width:150
 					 		},
 					  { display: '入库日期', name: 'confirm_date', align: 'left',width:80
 					 		},
 					  { display: '确认人', name: 'confirmer_name', align: 'left',width:90
 					 		},
 					  { display: '单据金额', name: 'payable_money', align: 'right',width:105,
 					 			render: function(rowdata,rowindex,value){
 					 				return formatNumber(rowdata.payable_money ,'${p08005 }', 1);
 					 			}
  					 		},
  					  { display: '已消耗金额', name: 'out_money', align: 'right',width:105,
  					 			render: function(rowdata,rowindex,value){
  	                    	  		if(rowdata.out_money == 0 |rowdata.out_money == null | rowdata.out_money == "" | rowdata.out_money == "undefined"){
  										rowdata.out_money = 0 ;
  										return formatNumber(rowdata.out_money ,'${p08005 }', 1);
  									}else{
  										return formatNumber(rowdata.out_money ,'${p08005 }', 1);
  									}
  	                    	  	}
  					 		},
  					  { display: '已付金额', name: 'payed_money', align: 'right',width:105,
  					 			render: function(rowdata,rowindex,value){
  					 				if(rowdata.payed_money == 0 |rowdata.payed_money == null | rowdata.payed_money == "" | rowdata.payed_money == "undefined"){
  					 					rowdata.payed_money = 0 ;
  					 					return formatNumber(rowdata.payed_money ,'${p08005 }', 1);
  					 				}else{
  					 					return formatNumber(rowdata.payed_money ,'${p08005 }', 1);
  					 				}
  					 			}
  					 		},
 				 	  { display: '本次付款金额', name: 'pre_pay_money', align: 'right',width:105,
  					 			render:function(rowdata,rowindex,value){
					 				if(rowdata.pre_pay_money == 0 | rowdata.pre_pay_money == null |rowdata.pre_pay_money == '' |rowdata.pre_pay_money == "undefined"){
					 					rowdata.pre_pay_money = 0 ;
					 					return formatNumber(rowdata.pre_pay_money ,'${p08005 }', 1);
					 				}else{
					 					return formatNumber(rowdata.pre_pay_money ,'${p08005 }', 1);
					 				}
  					 			}
 				 	  		}
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedCommonIn.do?isCheck=false',  
                      width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit:true,frozen : false , 
                      delayLoad: true,//初始化不加载，默认false
                      selectRowButtonOnly:true,heightDiff: 0, allowAdjustColWidth : false ,
                      showTitle: true,detail: { onShowDetail: showDetail ,height:'auto',reload: true, single: true },//入库单明细
                      onSelectRow  : updatePre_pay_money ,// 勾选入库单后    更新相应的 金额
                      onUnSelectRow   : updatePre_pay_money ,// 取消勾选入库单后    更新相应的 金额
                      onAfterShowData  : updateMoney ,//  显示完数据后   更新相应的 金额
                      toolbar: { items: [
     					{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
     	                { line:true },
     	               /*  { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
 						{ line:true }, */
     	                { text: '保存（<u>S</u>）', id:'save', click: saveMedPrePayMain,icon:'save' },
						{ line:true },
						{ text: '<input type="checkbox" id="initOnly" />只显示期初送货单', id:'', click: setInit,icon:'' },
     				]},
                  });

         gridManager = $("#maingrid").ligerGetGridManager();
     }
     function showDetail(row, detailPanel,callback)
     {	
    	 data= row;
         detailGrid = document.createElement('div'); 
         $(detailPanel).append(detailGrid);
       	 grid =$(detailGrid).css('margin',10).ligerGrid({
             columns:
                     [
                      { display: '药品编码', name: 'inv_code',width:110},
                      { display: '药品名称', name: 'inv_name',width:180},
                      { display: '计量单位', name: 'unit_name',width:80 },
                      { display: '规格型号', name: 'inv_model',width:200 ,align: 'left'},
                      { display: '批号', name: 'batch_no',width:100,align: 'left' },
                      { display: '条形码', name: 'sn' ,width:92,align: 'left'},
                      { display: '单价', name: 'price',width:100 ,align: 'right',
	                    	  render: function(rowdata,rowindex,value){
	                  	  		return formatNumber(rowdata.price ,'${p08006 }', 1);
	                  	  	  }
                    	  },
                      { display: '数量', name: 'amount',width:100 },
                      { display: '单据金额', name: 'payable_money' ,align: 'right',width:100 ,
                    	  	render: function(rowdata,rowindex,value){
                    	  		return formatNumber(rowdata.payable_money ,'${p08005 }', 1);
                    	  	}
                    	  },
                      { display: '已消耗金额', name: 'out_money', align: 'right',width:100,
                    	  	render: function(rowdata,rowindex,value){
                    	  		if(rowdata.out_money == 0 |rowdata.out_money == null | rowdata.out_money == "" | rowdata.out_money == "undefined"){
									rowdata.out_money = 0 ;
									return formatNumber(rowdata.out_money ,'${p08005 }', 1);
								}else{
									return formatNumber(rowdata.out_money ,'${p08005 }', 1);
								}
                    	  	}
                      	},
					  { display: '已付金额', name: 'payed_money', align: 'right',width:100,
								render: function(rowdata,rowindex,value){
									if(rowdata.payed_money == 0 |rowdata.payed_money == null | rowdata.payed_money == "" | rowdata.payed_money == "undefined"){
										rowdata.payed_money = 0 ;
										return formatNumber(rowdata.payed_money ,'${p08005 }', 1);
									}else{
										return formatNumber(rowdata.payed_money ,'${p08005 }', 1);
									}
								}
						  },
				 	  { display: '本次付款金额', name: 'pre_pay_money', align: 'right',width:100,editor: { type: 'float'},
					 			render:function(rowdata,rowindex,value){
					 				if(rowdata.pre_pay_money == 0){
					 					return formatNumber(rowdata.pre_pay_money ,'${p08005 }', 1);
					 				}else if(rowdata.pre_pay_money < 0){
					 					rowdata.pre_pay_money = 0 ;
					 					return formatNumber(0 ,'${p08005 }', 1);
					 				}else{
					 					return formatNumber(rowdata.pre_pay_money ,'${p08005 }', 1);
					 				}
					 			}
				 	  		}
                      ], 
                      
                      dataAction : 'server',dataType : 'server', usePager : false,checkbox: true,selectRowButtonOnly:true,
                      delayLoad: false,//初始化不加载，默认false
                      width:'98%',height:'auto',isScoll:true ,allowAdjustColWidth : false , frozen : false ,isAddRow:false,
                     //url : 'queryMedInDetail.do?isCheck=false&in_id='+row.in_id+'&pre_pay_id='+row.pre_pay_id+'&init='+row.init,columnWidth:80,
		 			 data:row.detail ,enabledEdit : true,fixedCellHeight:true,onAfterEdit: getPre_pay_money,
		 			 onSelectRow  : detailData , onUnSelectRow :removeDetail 
		 			 
         });
     }
     
     //勾选明细后  存储数据  更新 主表格 已消耗金额、已付金额 、本次付款金额
     function detailData(rowdata,rowid,rowobj){
    	 var out_money = 0; //存储  主表格 已消耗金额
    	 var payed_money = 0;//存储  主表格 已付金额
    	 var pre_pay_money = 0;//存储  主表格 付款金额
    	 var detailDate =  grid.getSelectedRows();
    	 if(detailDate.length > 0){
    		 $.each(detailDate,function(index,content){
    			 out_money += content.out_money;
    			 payed_money += content.payed_money;
    			 pre_pay_money += content.pre_pay_money;
    		 })
    	 }
    	 gridManager.updateCell('out_money',out_money,data);
    	 gridManager.updateCell('payed_money',payed_money,data);
    	 gridManager.updateCell('pre_pay_money',pre_pay_money,data);
    	 targetMap.put(data.__id+'|'+rowid,rowdata);
    	 if(!gridManager.isSelected(data)){
    		 gridManager.select(data);
    	 }
    	 updatePre_pay_money();
     }
     // 取消勾选明细后  移除存储的数据  更新 主表格    已消耗金额、已付金额 、本次付款金额
     function removeDetail(rowdata,rowid,rowobj){
    	 var out_money = 0; //存储  主表格 已消耗金额
    	 var payed_money = 0;//存储  主表格 已付金额
    	 var pre_pay_money = 0;//存储  主表格 付款金额
    	 var detailDate =  grid.getSelectedRows();
    	 
    	 if(detailDate.length > 0){
    		 $.each(detailDate,function(index,content){
    			 out_money += content.out_money;
    			 payed_money += content.payed_money;
    			 pre_pay_money += content.pre_pay_money;
    		 })
    	 }
    	 gridManager.updateCell('out_money',out_money,data);
    	 gridManager.updateCell('payed_money',payed_money,data);
    	 gridManager.updateCell('pre_pay_money',pre_pay_money,data);
    	 targetMap.remove(data.__id+'|'+rowid,rowdata);
    	 var selectRow = grid.getSelectedRows();
    	 if(selectRow.length == 0){
    		 gridManager.unselect(data);
    	 }
    	 updatePre_pay_money();
     }
     //编辑完明细表格单元格后   选择该行数据
     function getPre_pay_money(e){
    	 var out_money = 0; //存储  主表格 已消耗金额
    	 var payed_money = 0;//存储  主表格 已付金额
    	 var pre_pay_money = 0;//存储  主表格 付款金额 
    	 var  total = e.record.payed_money + e.record.pre_pay_money ;//存储 明细表格 已付金额 与付款金额 的和 ，与明细单据金额对比（不能大于单据金额）
    	 if( total > e.record.payable_money){
    		 $.ligerDialog.error('<span style="color: red">填写数据错误!该条数据的本次付款金额与已付金额之和大于单据金额.</span>(<span style="color:blue">请您重新填写本次付款金额</span>)');
    	 }else{
    		 var detailDate =  grid.getData();
        	 if(detailDate.length > 0){
        		 $.each(detailDate,function(index,content){
        			 out_money += content.out_money;
        			 payed_money += content.payed_money;
        			 pre_pay_money += content.pre_pay_money;
        		 })
        	 }
        	 gridManager.updateCell('out_money',out_money,data);
        	 gridManager.updateCell('payed_money',payed_money,data);
        	 gridManager.updateCell('pre_pay_money',pre_pay_money,data);
        	 if(!gridManager.isSelected(data)){
        		 gridManager.select(data);
        	 }
        	 data.detail={"Rows":detailDate,"Total":detailDate.length};
        	 updatePre_pay_money();
    	 }
    	 
     } 
     function btn_saveColumn(){
  		
		   var path = window.location.pathname+"/maingrid";
		   var url = '../../../sys/addBatchSysTableStyle.do?isCheck=false';
		   saveColHeader({
			   grid:inMainGrid,
			   path:path,
			   url:url,
			   callback:function(data){
				   $.ligerDialog.success("保存成功");
			   }
		   });
}
   //  勾选、取消勾选入库单后  自动填写 要生成的预付款单的  付款金额
     function updatePre_pay_money(rowdata,rowid,rowobj){
    	var payable_money = 0; // 存储  应付金额
        var pre_pay_money =0;//存储  付款金额
    	var dataMain =  inMainGrid.getSelectedRows();
    	if(dataMain.length > 0){
    		$(dataMain).each(function(){
    			payable_money += this.payable_money;
    			pre_pay_money += this.pre_pay_money;
    		})
    	}
    	$("#payable_money").val(payable_money);
    	$("#pre_pay_money").val(pre_pay_money);
     }
   	// 显示完数据 后  更新相应的 金额
     function updateMoney(){
 	   	// 
 	   	var dataMain =  inMainGrid.getData();
 	   	$.each(dataMain,function(index,content){
	 	   	var mainOut_money = 0 ; //存储 该条入库单 所有药品的  已消耗金额 总额
	 	   	var mainPayed_money = 0 ; //存储 该条入库单 所有药品的  已付金额 总额
	 	   	var mainPre_pay_money = 0 ; //存储 该条入库单 所有药品的  本次付款金额 总额
	 	   	var detail = content.detail ;
	 	   	$(detail.Rows).each(function (){
	 	   		mainOut_money += this.out_money ;
	 	   		mainPayed_money += this.payed_money ;
	 	   	 if(this.pre_pay_money <= 0){
	 	   		mainPre_pay_money += 0;
			 }else{
				 mainPre_pay_money += this.pre_pay_money ;
			 }
	 	   		
	 		})
	 	   	gridManager.updateCell('out_money',mainOut_money,index);
	     	gridManager.updateCell('payed_money',mainPayed_money,index);
	     	gridManager.updateCell('pre_pay_money',mainPre_pay_money,index);
 	   	})
      }
    //生成流水号
     var curDate=new Date();
	 year = curDate.getFullYear();
	 month = curDate.getMonth();
	 day = curDate.getDate();
    function setMedPrePayMainNo(){
    	var pay_bill_type = $("#pay_bill_type").val()
    	var parm={
		 		 year:year,
		 		 month:month,
		 		 day:day,
		 		pay_bill_type:pay_bill_type
		         };
    	 ajaxJsonObjectByUrl("setMedPrePayMainNo.do?isCheck=false",parm,function (responseData){
				$("#pre_pay_no").val(responseData.prefixe+responseData.year.substring(2)+responseData.month+responseData.day+responseData.max_no);
    	 })
    }
    
    function setInit(){
    	if($("#initOnly").prop("checked") == true){
    		init = "1";
    	}else{
    		init = "0" ;
    	}
    	query();
    }
     //添加
   /*   function add_open(){
    	 
    	 if( liger.get("sup_id").getValue().split(",")[0] == '' ){
    		 $.ligerDialog.error('请先选择供应商后再添加预付款单');
    	 }else{
    		 $.ligerDialog.open({url: 'inMainPage.do?isCheck=false',height: 500,width: 1000, top:0,
 				title:'选择 入库单',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true
 				});
    	 }
    	
     } */
     function  save(){
    	
        var formPara={
        		pre_pay_id:'',
        		pre_pay_no:$("#pre_pay_no").val(),
        		pay_date:$("#pay_date").val(),
        		pay_bill_type:$("#pay_bill_type").val(),
   	            sup_id:liger.get("sup_id").getValue().split(",")[0],
   	            sup_no:liger.get("sup_id").getValue().split(",")[1],
   	            pay_code:liger.get("pay_code").getValue(),
   	            pre_pay_money : $("#pre_pay_money").val() ,
   	        	payable_money: $("#payable_money").val(),
   	            note:$("#note").val(),
   	            init : init
	         };
        var data = inMainGrid.getSelectedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择入库单再保存');
        }else{
       		var ParamVo =[];
       		var str = '' ; //  记录 入库单本次付款金额 为0 的 入库单号 
       		var error ='' ;
       		if(JSON.stringify(targetMap.map) != "{}"){
       			$.each(data,function(index,content){
	             	var flag = false; // 判断该条数据  是否有明细数据 标志 （false：没有(整单保存)   true： 有（保存选择的明细）
	            	var key = targetMap.keySet() ;
	            	if(content.pre_pay_money == 0 ){
	            		str+= content.in_no+",";
	            	}else{
	            		for(var i=0;i< key.length;i++){
		         			var data_detail = null;
		         			if(content.__id == key[i].split("|")[0]){
		         				if(!flag ){
		         					flag = true ;
		         				}
   			    					
		         				data_detail= targetMap.get(key[i]);
		 	        			$(data_detail).each(function (){
		 	        				if( this.pre_pay_money > 0){
		 	        					if(this.pre_pay_money + this.payed_money >this.payable_money){
	   			    						error += content.in_no + "," ;
		 	        					}
		 	        					ParamVo.push(
		     		           				content.group_id   +"@"+ 
		     		           				content.hos_id   +"@"+ 
		     		           				content.copy_code   +"@"+ 
		     		           				content.pre_pay_id   +"@"+ 
		     		           				content.in_id  +"@"+ 
		     		           				this.in_detail_id  +"@"+ 
		     		           				this.payable_money +"@"+ 
		     		           				this.pre_pay_money +"@"+ 
		     		       					$("#pre_pay_no").val() +"@"+ 
		     		       					content.pre_detail_id
		     		        			)
		 	        				}
		 		           		   	
		 	        			})
		         			}
		         		}
		         		if(!flag){
		         			var data_detail= content.detail ;
	            			$(data_detail.Rows).each(function (){
	            				if( this.pre_pay_money > 0){
	            					if(this.pre_pay_money + this.payed_money >this.payable_money){
   			    						error += content.in_no + "," ;
	            					}
		                		   	ParamVo.push(
		                				content.group_id   +"@"+ 
		                				content.hos_id   +"@"+ 
		                				content.copy_code   +"@"+ 
		                				content.pre_pay_id  +"@"+ 
		                				content.in_id  +"@"+ 
		                				this.in_detail_id  +"@"+ 
		                				this.payable_money +"@"+ 
		                				this.pre_pay_money +"@"+ 
		            					$("#pre_pay_no").val() +"@"+ 
		            					content.pre_detail_id
		             				)
	            				}
	            			})
		         		}
	            	}
	         	})
       		}else{
       			$.each(data,function(index,content){
	             	if(content.pre_pay_money == 0 ){
	             		str+= content.in_no+",";
	             	}else{
	             		var data_detail= content.detail ;
            			$(data_detail.Rows).each(function (){
            				if( this.pre_pay_money > 0){
            					if(this.pre_pay_money + this.payed_money >this.payable_money){
			    					error += content.in_no + "," ;
            					}
	                		   	ParamVo.push(
	                				content.group_id   +"@"+ 
	                				content.hos_id   +"@"+ 
	                				content.copy_code   +"@"+ 
	                				content.pre_pay_id  +"@"+ 
	                				content.in_id  +"@"+ 
	                				this.in_detail_id  +"@"+ 
	                				this.payable_money +"@"+ 
	                				this.pre_pay_money +"@"+ 
	            					$("#pre_pay_no").val() +"@"+ 
	            					content.pre_detail_id
	             				)
            				}
	            		})
	             	}
	             })
       		}
			if(str != ''){
 				$.ligerDialog.error('<span style="color: red">数据错误,入库单号:'+str+'本次付款金额为0,该条单据下的明细数据没有消耗,不允许添加.</span>(如想提前付款，请您修改明细 本次付款金额后,再保存,否则请删除该单据)');
			}else{
				 if(error != ''){
		          	$.ligerDialog.error('保存失败！入库单号:<span style="color: red">'+ error +'有明细数据的本次付款金额与已付款金额之和大于单据金额</span>,<span style="color: blue">不能保存.请您仔细核对数据</span>');
		         }else{
		        	 $.ligerDialog.confirm('确定要保存预付款单及预付款单明细信息吗?', function (yes){
				           	if(yes){
				           		 ajaxJsonObjectByUrl("addMedPrePayMain.do?isCheck=false",formPara,function(responseData){
			          	            if(responseData.state=="true"){
			          	                parent.query();
			          	                ajaxJsonObjectByUrl("addMedPrePayDetail.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
			                          		if(responseData.state=="true"){
			                          		}
			                          	});
			          	            }
			          	        });
			          		}
			           });  
		         }
			}
        };	
    }
    function loadHotkeys() {

 		hotkeys('S', saveMedPrePayMain);
		
 		hotkeys('Q', query);
 		
 		/* hotkeys('A', add_open); */
 		
 		hotkeys('D', remove);
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
	    // $("form").ligerForm();
	 }       
	   
    function saveMedPrePayMain(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
	    	//供应商下拉框
			autocomplete("#sup_id", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true,'',true,'',240);
			//科室：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
			//autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true,'',false,'',200);
			//采购员下拉框
			//autocomplete("#stocker", "../../queryMedStoctEmpDict.do?isCheck=false", "id", "text", true, true,'',false,'',200);
			//付款条件
			autocomplete("#pay_code", "../../queryMedPayTerm.do?isCheck=false", "id", "text", true, true,'',false,'',240);
			
			//autodate("#pay_date","yyyy-MM-dd");

	         $("#pre_pay_no").ligerTextBox({width:200,disabled:true});
	         $("#pay_date").ligerTextBox({width:200});
	         $("#pay_bill_type").ligerTextBox({width:200});
	         $("#sup_id").ligerTextBox({width:240});
	         $("#pay_code").ligerTextBox({width:240});
	 		 $("#pre_pay_money").ligerTextBox({width:200,disabled:true});
	         $("#note").ligerTextBox({width:535});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
        	<div class="l-layout-content" position="top">
        	<input type="hidden" id="payable_money" name="payable_money" type="text"  ltype="text" validate="{required:true,maxlength:20}" />
        	<input type="hidden" id="curentDate" name="curentDate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
	      	 <form name="form1" method="post"  id="form1" >
			     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>单据号<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="pre_pay_no" type="text" id="pre_pay_no" disabled="disabled" ltype="text"  validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应单位<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>制单日期<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
			            	<input class="Wdate" name="pay_date" type="text" id="pay_date" ltype="text" onChange="setMedPrePayMainNo()" style="width: 200px;" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预付款类别<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
				            <select name="pay_bill_type" id="pay_bill_type" style="width: 135px;" onChange="setMedPrePayMainNo()">
		                		<option value="1">预付款单</option>
		                		<!-- <option value="2">退款单</option> -->
		            		</select>
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>付款条件:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="pay_code" type="text" id="pay_code" ltype="text" validate="{maxlength:20}" /></td>
			            <td align="left"></td>
						<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>付款金额<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="pre_pay_money" type="text" id="pre_pay_money" ltype="text" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>摘要:</b></td>
			            <td align="left" class="l-table-edit-td" colspan="4">
			            	<input name="note" type="text" id="note" ltype="text" validate="{maxlength:100}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
			        
			     </table>
		    </form>
        	</div>
	        <div title="" class="l-layout-content" style="" position="center">
	            <div id="maingrid" ></div>
	        </div>
      </div>  
    </body>
</html>
