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
    <script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
    <script type="text/javascript">
    var dataFormat;
    
    var inMainGrid;
    
    var gridManager;
    
    var data;
    
    var grid;
    
    var year;
    
    var month;
    
    var group_id = "${group_id}";
    var hos_id = "${hos_id}" ;
    var copy_code = "${copy_code}";
    var pre_pay_id = "${pre_pay_id}";
    var pre_pay_no = "${pre_pay_no}" ;
    var state = "${state}" ;
    
    var targetMap = new HashMap();//存放key 对应关系
    
    var detailMap = new HashMap();//存放 明细数据
    var init = '${is_init}' ;
     $(function (){
         loadDict()//加载下拉框
         loadForm();
         loadHead();
         
     });
     //查询
     function  query(){
    	inMainGrid.options.parms=[];
 		inMainGrid.options.newPage=1;
         //根据表字段进行添加查询条件
 		inMainGrid.options.parms.push({
 			name : 'sup_id',
 			value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]
 		});
     	//加载查询条件
     	inMainGrid.loadData(inMainGrid.where);
      }
     function loadHead(){
     	 inMainGrid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '入库单号', name: 'in_no', align: 'left',width:120
 					 		},
 					  { display: '摘要', name: 'brief', align: 'left',width:210
 					 		},
 					  { display: '仓库', name: 'store_name', align: 'left',width:100
 					 		},
 					  { display: '供应商', name: 'sup_name', align: 'left',width:150
 					 		},
 					  { display: '入库日期', name: 'in_date', align: 'left',width:80
 					 		},
 					  { display: '确认人', name: 'confirmer_name', align: 'left',width:100
 					 		},
 					  { display: '单据金额', name: 'payable_money', align: 'right',width:100,
 					 			render:function(rowdata,rowindex,value){
					 				return formatNumber(rowdata.payable_money ,'${p08005 }', 1);
					 			}
  					 		},
  					  { display: '已消耗金额', name: 'out_money', align: 'right',width:100,
  					 			render: function(rowdata,rowindex,value){
  	                    	  		if(rowdata.out_money == 0 ){
  										return formatNumber(rowdata.out_money ,'${p08005 }', 1);
  									}else{
  										return formatNumber(rowdata.out_money ,'${p08005 }', 1);
  									}
  	                    	  	}
  					 		},
  					  { display: '已付金额', name: 'payed_money', align: 'right',width:100,
  					 			render: function(rowdata,rowindex,value){
  					 				if(rowdata.payed_money == 0 ){
  					 					return formatNumber(rowdata.payed_money ,'${p08005 }', 1);
  					 				}else{
  					 					return formatNumber(rowdata.payed_money ,'${p08005 }', 1);
  					 				}
  					 			}
  					 		},
 				 	  { display: '本次付款金额', name: 'pre_pay_money', align: 'right',width:100,
  					 		 render: function(rowdata,rowindex,value){
									if(rowdata.pre_pay_money == 0 ){
										return formatNumber(rowdata.pre_pay_money ,'${p08005 }', 1);
									}else{
										return formatNumber(rowdata.pre_pay_money ,'${p08005 }', 1);
									}
								}
 				 	  		}
                      ],
                      dataAction: 'server',dataType: 'server',usePager:false,url:'queryMedPrePayIn.do?isCheck=false&pre_pay_id=${pre_pay_id}&init=${is_init}',  
                      width:'100%',height : '100%', checkbox: true,rownumbers:true,enabledEdit:true,
                      frozen : false ,isScroll :true , delayLoad: false,//初始化不加载，默认false
                      selectRowButtonOnly:true,heightDiff: 0,allowAdjustColWidth:false ,
                      onsuccess:function(){
          			
          				//is_addRow();
          			},
                      showTitle: true,detail: { onShowDetail: showDetail,height:'auto',reload: true, single: true },//入库单明细
                      onAfterAddRow  : updatePre_pay_money ,// 添加行后  自动更新 要生成的预付款单的  付款金额
                      onAfterShowData  : updateMoney ,//  显示完数据后   更新相应的 金额
                      toolbar:{items: [
              				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add',disabled:state ==1?false:true},
                   	        { line:true },
               	            { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete',disabled:state ==1?false:true },
              				{ line:true },
//                	            { text: '保存（<u>S</u>）', id:'save', click: saveMedPrePayMain,icon:'save',disabled:state ==1?false:true },
//               				{ line:true },
              				{ text: '审核（<u>Z</u>）', id:'audit', click: audit, icon:'bluebook',disabled:state ==1?false:true },
    						{ line:true },
    						{ text: '消审（<u>U</u>）', id:'unaudit', click: unAudit,icon:'bookpen' },
    						{ line:true }
    						]
                           } 
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
                      { display: '规格型号', name: 'inv_model',width:200},
                      { display: '批号', name: 'batch_no',width:100 },
                      { display: '条形码', name: 'sn' ,width:92},
                      { display: '单价', name: 'price',width:100 ,align: 'right',
	                    	  render:function(rowdata,rowindex,value){
	                    		  return formatNumber(rowdata.price ,'${p08006 }', 1);
	                    	  }
                    	  },
                      { display: '数量', name: 'amount',width:100 },
                      { display: '单据金额', name: 'payable_money',align: 'right',width:100,
	                    	  render:function(rowdata,rowindex,value){
	                    		  return formatNumber(rowdata.payable_money ,'${p08005 }', 1);
	                    	  }
                    	  },
                      { display: '已消耗金额', name: 'out_money', align: 'right',width:100,
                    	  	render: function(rowdata,rowindex,value){
                    	  		if(rowdata.out_money == 0 ){
									return formatNumber(0,'${p08005 }', 1);
								}else{
									return formatNumber(rowdata.out_money ,'${p08005 }', 1);
								}
                    	  	}
                      	},
					  { display: '已付金额', name: 'payed_money', align: 'right',width:100,
								render: function(rowdata,rowindex,value){
									if(rowdata.payed_money == 0 ){
										return formatNumber(0,'${p08005 }', 1);
									}else{
										return formatNumber(rowdata.payed_money ,'${p08005 }', 1);
									}
								}
						  },
				 	  { display: '本次付款金额', name: 'pre_pay_money', align: 'right',width:100,editor: { type: 'float'},
							  render: function(rowdata,rowindex,value){
									if(rowdata.pre_pay_money == 0 ){
										return formatNumber(0,'${p08005 }', 1);
									}else{
										return formatNumber(rowdata.pre_pay_money ,'${p08005 }', 1);
									}
								}
						  }
                      ], 
                 dataAction : 'server',dataType : 'server',usePager : false,selectRowButtonOnly:true,isScroll:true,
	 			 //url : 'queryMedInDetail.do?isCheck=false&in_id='+row.in_id+'&pre_pay_id='+row.pre_pay_id+'&init='+row.init,
	 			 width:'98%',data: row.detail ,enabledEdit : true,fixedCellHeight:true,isAddRow:false,
	 			 onAfterEdit : f_onAfterEdit
         });
     }
    
    // 编辑后 选择该行数据
     function f_onAfterEdit(e){
    	 var out_money = 0; //存储  主表格 已消耗金额
    	 var payed_money = 0;//存储  主表格 已付金额
    	 var pre_pay_money = 0;//存储  主表格 付款金额
    	 var  total = e.record.payed_money + e.record.pre_pay_money ;//存储 明细表格 已付金额 与付款金额 的和 ，与明细单据金额对比（不能大于单据金额）
    	 if( total > e.record.payable_money){
    		 $.ligerDialog.error('<span style="color: red">填写数据错误!该条数据的本次付款金额与已付金额之和大于单据金额.</span>(<span style="color:blue">请您重新填写本次付款金额</span>)');
    	 }
		 var detail =  grid.getData();
    	 if(detail.length > 0){
    		 $.each(detail,function(index,content){
    			 out_money += content.out_money;
    			 payed_money += content.payed_money;
    			 pre_pay_money += content.pre_pay_money;
    		 })
    	 }
   		 gridManager.updateCell('out_money',out_money,data);
       	 gridManager.updateCell('payed_money',payed_money,data);
       	 gridManager.updateCell('pre_pay_money',pre_pay_money,data);
       	 data.detail={"Rows":detail,"Total":detail.length};
       	 if(!gridManager.isSelected(data)){
       		 gridManager.select(data);
       	 }
       	 updatePre_pay_money();
    	 
     }
   //添加 行后  自动更新 要生成的预付款单的  付款金额
     function updatePre_pay_money(){
    	 var payable_money = 0; // 存储  应付金额
         var pre_pay_money =0;//存储  付款金额
    	 var dataMain =  inMainGrid.getData();
    	 if(dataMain.length > 0){
    		 $(dataMain).each(function(){
    			 payable_money += this.payable_money;
    			 pre_pay_money += this.pre_pay_money;
    		 })
    	 }
    	 $("#payable_money").val(payable_money);
    	 $("#pre_pay_money").val(pre_pay_money);
     }
   //删除 行后  更新 要生成的预付款单的  付款金额
     function updateDeleteMoney(){
    	 var payable_money = 0; // 存储  修改后应付金额
         var pre_pay_money =0;//存储  修改后付款金额
    	 var t = 0; // 存储  删除 应付金额
         var s =0;//存储  删除付款金额
    	 var dataMain =  inMainGrid.getCheckedRows();
    	 if(dataMain.length > 0){
    		 $(dataMain).each(function(){
    			 t += this.payable_money;
    			 s += this.pre_pay_money;
    		 })
    	 }
    	 payable_money = $("#payable_money").val() - t ;
    	 pre_pay_money = $("#pre_pay_money").val() - s ;
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
	 	   		mainPre_pay_money += this.pre_pay_money ;
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
    function setMedPrePayMainNo(){
    	var parm={
		 		 year:year,
		 		 month:month
		         };
    	 ajaxJsonObjectByUrl("setMedPrePayMainNo.do?isCheck=false",parm,function (responseData){
				$("#bill_code").val(responseData.prefixe+responseData.year+responseData.month+responseData.max_no);
    	 })
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
	  
	   return false;
}
     function add_open(){
    	 
    	 if( liger.get("sup_id").getValue().split(",")[0] == '' ){
    		 $.ligerDialog.error('请先选择供应后再添加入库单');
    	 }else{
    		 $.ligerDialog.open({url: 'inMainPage.do?isCheck=false&sup_id='+liger.get("sup_id").getValue().split(",")[0]
 		 		+'&sup_no='+liger.get("sup_id").getValue().split(",")[1]+
				"&sup_code="+liger.get("sup_id").getText().split(" ")[0]+"&sup_name="+liger.get("sup_id").getText().split(" ")[1],
    			height: 540,width: 1000,title:'选择 入库单',modal:true,showToggle:false,
    			showMax:true,showMin: false,isResize:true ,top:0,
 				});
    	 }
    	
     }
    function remove(){
		var data = inMainGrid.getCheckedRows();
		if(data.length == 0){
			$.ligerDialog.error('选择要删除的行!');
			return;
		}else{
			$.ligerDialog.confirm('确定删除?', function (yes){
			  	if(yes){
			 	 	for (var i = 0; i < data.length; i++){
			 	 		inMainGrid.remove(data[i]);
			 	 	}
			 	}
			}); 
		}
		//更新 相关金额
		updateDeleteMoney();
    }
    function  save(){
        var formPara={
				pre_pay_id:'${pre_pay_id}',
        		pre_pay_no:$("#pre_pay_no").val(),
        		pay_date:$("#pay_date").val(),
        		pay_bill_type:$("#pay_bill_type").val(),
   	            sup_id:liger.get("sup_id").getValue().split(",")[0],
   	            sup_no:liger.get("sup_id").getValue().split(",")[1],
   	            pay_code:liger.get("pay_code").getValue(),
   	            pre_pay_money : $("#pre_pay_money").val() ,
   	        	payable_money: $("#payable_money").val(),
   	            note:$("#note").val(),
   	            maker:'${maker}',
   	            make_date:'${make_date}',
   	            checker:'${checker}',
   	         	chk_date:'${chk_date}',
   	         	state:'${state}'
	         };
        var data = inMainGrid.getData();
        var ParamVo =[];
        var str = '';
        var error ='' ;
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
   	           				$("#pre_pay_id").val()  +"@"+ 
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
        if(str != ''){
    		$.ligerDialog.error('<span style="color: red">修改失败!入库单号:'+str+'本次付款金额为0,该条单据下的明细数据没有消耗,不允许添加.</span>(<span style="color:blue">如想提前付款，请您修改明细 本次付款金额后,再保存,否则请删除该单据</span>)');
    	}else{
    		if(error != ''){
        		$.ligerDialog.error('保存失败！入库单号:<span style="color: red">'+ error +'有明细数据的本次付款金额与已付款金额之和大于单据金额</span>,<span style="color: blue">不能保存.请您仔细核对数据</span>');
        	}else{
        		$.ligerDialog.confirm('确定要保存发票及发票明细信息吗?<span style= "color:red">(注意核对各个金额)</span>', function (yes){
                	if(yes){
                		 ajaxJsonObjectByUrl("updateMedPrePayMain.do?isCheck=false",formPara,function(responseData){
               	            if(responseData.state=="true"){
               	                parent.query();
               	                ajaxJsonObjectByUrl("updateMedPrePayDetail.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                               		if(responseData.state=="true"){
                               		}
                               	}); 
               	            }
               	        });
               		}
                }); 
        	}
    	}
    }
 // 审核
	function audit(){
		if (state != 1){
			$.ligerDialog.error('审核失败！'+pre_pay_no+'单据不是未审核状态,不能审核');
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
					group_id   +"@"+ 
					hos_id   +"@"+ 
					copy_code    +"@"+ 
					pre_pay_id  +"@"+ 
					pre_pay_no   +"@"+ 
					$("pay_date").val()   +"@"+ 2
				) 
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateMedPrePayMainState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							self.location.reload(); //  刷新本页
							parent.query();
						}
					});
				}
			}); 
		}
	}
	//消审
	function unAudit(){
		
		if (state != 2){
			$.ligerDialog.error('消审失败！'+pre_pay_no+'单据不是已审核状态,不能消审');
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
					group_id   +"@"+ 
					hos_id   +"@"+ 
					copy_code    +"@"+ 
					pre_pay_id  +"@"+ 
					pre_pay_no   +"@"+ 
					$("pay_date").val()   +"@"+ 1
				) 
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateMedPrePayMainState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							self.location.reload();  //刷新本页;
							parent.query();
						}
					});
				}
			}); 
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
	    // $("form").ligerForm();
	}  
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('S', saveMedPrePayMain);
		hotkeys('P', printDate);
		hotkeys('M', printSet);
		hotkeys('A', add_open);
  		hotkeys('D', remove);
		hotkeys('C', this_close);
	}
   
    function saveMedPrePayMain(){
        if($("form").valid()){
            save();
        }
	}
    function loadDict(){
            //字典下拉框
	    	//供应商下拉框
			autocomplete("#sup_id", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true,'',false,'',240);
            liger.get("sup_id").setValue('${sup_id},${sup_no}');
            liger.get("sup_id").setText('${sup_code} ${sup_name}');
			//付款条件
			autocomplete("#pay_code", "../../queryMedPayTerm.do?isCheck=false", "id", "text", true, true,'',false,'',240);
			liger.get("pay_code").setValue('${pay_code}');
	        liger.get("pay_code").setText('${pay_term_code} ${pay_term_name}');
	        $("#pay_bill_type").val("${pay_bill_type}");
	        
	        $("#pre_pay_no").ligerTextBox({width:200,disabled:true});
			$("#pay_date").ligerTextBox({width:200});
			$("#pay_bill_type").ligerTextBox({width:200});
			$("#sup_id").ligerTextBox({width:240});
			$("#pay_code").ligerTextBox({width:240});
			$("#pre_pay_money").ligerTextBox({width:200,disabled:true});
			$("#note").ligerTextBox({width:535});
	        
// 	        if("${state}" != 1 ){
// 	        	$("#toptoolbar").hide();
// 	        }
	        
	      //格式化按钮
	        if(state > 1){
	    		$("#save").ligerButton({click: saveMedPrePayMain, width:90, disabled:true});
	        }else{
	    		$("#save").ligerButton({click: saveMedPrePayMain, width:90});
	        }
			$("#print").ligerButton({click: printDate, width:90});
			$("#printSet").ligerButton({click: printSet, width:100});
			$("#close").ligerButton({click: this_close, width:90});
     } 
    
  //打印
	function printDate(){
		
		var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		 var para={
				    pre_pay_id:$("#pre_pay_id").val(),
					template_code:'08025',
					class_name:"com.chd.hrp.med.serviceImpl.medprepay.MedPrePayMainServiceImpl",
					method_name:"queryPrePayByPrintTemlateNew",
					isSetPrint:false,//是否套打，默认非套打
					isPreview:false,//是否预览，默认直接打印
					use_id:useId
			};
			
			officeFormPrint(para);
		
		//printTemplate("queryPrePayByPrintTemlate.do?isCheck=false",para);
	}
	
	//打印设置
	function printSet(){
		
		
		var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		officeFormTemplate({template_code:"08025",user_id:useId});
		/**
		parent.parent.$.ligerDialog.open({url : 'hrp/med/medprepay/medprepaymain/prePayPrintSetPage.do?isCheck=false&template_code=08025&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		});
		*/
	}
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
	
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
        	<div class="l-layout-content" position="top">
        	<input type="hidden" id="pre_pay_id" name="pre_pay_id"  value='${pre_pay_id}'/>
        	<input type="hidden" id="payable_money" name="payable_money" type="text"  ltype="text" validate="{required:true,maxlength:20}" />
        	<input type="hidden" id="curentDate" name="curentDate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
	      	 <form name="form1" method="post"  id="form1" >
			     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>付款单号<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="pre_pay_no" type="text" id="pre_pay_no" value='${pre_pay_no}' disabled="disabled" ltype="text"  validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供货单位<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>制单日期<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
			            	<input class="Wdate" name="pay_date" type="text" id="pay_date" value='${pay_date}'  ltype="text" style="width: 200px;" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预付类型<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
				            <select name="pay_bill_type" id="pay_bill_type" style="width: 135px;" >
		            			<option value="">请选择</option>
		                		<option value="1">预付款单</option>
		                		<!-- <option value="2">退款单</option> -->
		            		</select>
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>付款条件:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="pay_code" type="text" id="pay_code" ltype="text" validate="{maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>发票金额<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="pre_pay_money" value='${pre_pay_money}' type="text" id="pre_pay_money" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			         <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>摘要:</b></td>
			            <td align="left" class="l-table-edit-td" colspan="4">
			            	<input name="note" type="text" id="note" value='${note}' ltype="text" validate="{maxlength:100}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
			     </table>
		    </form>
        	</div>
        	
        	<div class="l-layout-header" id="toptoolbar" position="center"></div>
	        <div title="" class="l-layout-content" style="" position="centerbottom">
	            <div id="maingrid" ></div>
	        </div>
      </div>
      <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-button: 10px;">
<!-- 			<tr>	 -->
<!-- 				<td align="center" class="l-table-edit-td" width="32%" > -->
<%-- 					制单人：${medInMain.maker_name} --%>
<!-- 				</td> -->
<!-- 				<td align="center" class="l-table-edit-td" width="32%" > -->
<%-- 					审核人：${medInMain.checker_name} --%>
<!-- 				</td> -->
<!-- 				<td align="center" class="l-table-edit-td" width="32%" > -->
<%-- 					确认人：${medInMain.confirmer_name} --%>
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					<button id ="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
      
    </body>
</html>
