<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
        $("#set_code").bind("change",function(){
	    	if(liger.get("set_code").getValue()){
	    		liger.get("store_code").setValue("");
				liger.get("store_code").setText("");
	   	 		$("#store_code").ligerComboBox({disabled:true});
	   	 		grid.toggleCol('02', true);
	    	}else{
	    		$("#store_code").ligerComboBox({disabled:false});
	    		grid.toggleCol('02', false);
	    	}
	    	
		});
    	$("#store_code").bind("change",function(){
	    	if(liger.get("store_code").getValue()){
	    		liger.get("set_code").setValue("");
				liger.get("set_code").setText("");
	   	 		$("#set_code").ligerComboBox({disabled:true});
	   	 		grid.toggleCol('03', true);
	    	}else{
	    		$("#set_code").ligerComboBox({disabled:false});
	    		grid.toggleCol('03', false);
	    	}
	    	
		});
        query();
		loadHotkeys();
		
		$("#check_column1").change(function () { 
			
			query();
			
		});

		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    		if(check_column1_manager.getValue()){
    			
    			grid.options.parms.push({name:'result_state',value:'6'});
    			
    		}

          grid.options.parms.push({name:'beginDate',value:$("#beginDate").val()});
    	  grid.options.parms.push({name:'endDate',value:$("#endDate").val()});
    	  grid.options.parms.push({name:'makeBeginDate',value:$("#makeBeginDate").val()});
    	  grid.options.parms.push({name:'makeEndDate',value:$("#makeEndDate").val()});
    	  grid.options.parms.push({name:'pay_bill_type',value:$("#pay_bill_type").val()}); 
    	  grid.options.parms.push({name:'state',value:$("#state").val()}); 
    	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue().split(",")[0]});
    	  grid.options.parms.push({name:'pay_bill_no',value:$("#pay_bill_no").val()});
    	  grid.options.parms.push({name:'maker',value:liger.get("maker").getValue()});
    	  grid.options.parms.push({name : 'set_id',value : liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()});
    	  grid.options.parms.push({
  			name : 'store_id',
  			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
  		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '付款单号', name: 'pay_bill_no', align: 'left',width:120,
					 			render:function(rowdata,index,value){
					 				if(rowdata.pay_bill_type == 0){
					 					return "<a href=javascript:openUpdate('"+
				 						rowdata.group_id+"|"+
				 						rowdata.hos_id+"|"+
				 						rowdata.copy_code+"|"+
				 						rowdata.pay_id+"|"+
				 						rowdata.state+"|"+
				 						rowdata.vouch_id+"')>"+rowdata.pay_bill_no+"</a>";
					 				}else if(rowdata.pay_bill_type != 0 && rowdata.pay_bill_no !="合计"){
					 					return "<a href=javascript:openUpdate('"+
				 						rowdata.group_id+"|"+
				 						rowdata.hos_id+"|"+
				 						rowdata.copy_code+"|"+
				 						rowdata.pay_id+"|"+
				 						rowdata.state+"|"+
				 						rowdata.vouch_id+"')>"+rowdata.pay_bill_no+"<font color='red'>(退)</font></a>";
					 				}else if(rowdata.pay_bill_no =="合计"){
					 					return "合计";
					 				}
					 			}
					 		},
					 		{ display: '凭证号', name: 'vouch_no', align: 'left',width:100,
								render: function (rowdata){
									if(rowdata.vouch_no){
										if(rowdata.vouch_no=='-'){
											return rowdata.vouch_no;
										}else{
											return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
										}
									}else{
										return '';
									}
								}
						 	},

					 { display: '单据类型', name: 'pay_bill_type', align: 'left',width:100,
					 			render : function(rowdata,rowindex,value){
					 				if(rowdata.pay_bill_type == 0){
					 					return "付款单";
					 				}else if(rowdata.pay_bill_type == null){
					 					return "";
					 				}else{
					 					return "退款单";
					 				}
					 			}
					 		},
                     { display: '凭证日期', name: 'vouch_date', align: 'left',width:100
					 		},
                     { display: '供应商', name: 'sup_name', align: 'left',width:200
					 		},
				 	 { display: '付款金额', name: 'pay_money', align: 'right',width:100,
					 			render:function(rowdata,rowindex,value){
					 				return formatNumber(rowdata.pay_money ,2, 1);
					 			}
					 		},
					 { display: '制单人', name: 'maker_name', align: 'left',width:96
					 		},
					 { display: '制单日期', name: 'make_date', align: 'left',width:100
					 		},
                     { display: '审核人', name: 'checker_name', align: 'left',width:96
					 		},
                     { display: '审核日期', name: 'chk_date', align: 'left',width:100
					 		},
					 { display: '单据状态', name: 'state',align: 'left',width:100,hide:true
						 		},
                     { display: '状态', name: 'state_name', align: 'left',width:80,
					 			/* render:function(rowdata,index,value){
					 				if(rowdata.state == 1){
					 					return "未审核";
					 				}else if (rowdata.state == 2){
					 					return "已审核";
					 				}else if (rowdata.state == null){
					 					return "";
					 				}else {
					 					return "记账";
					 				}
					 			} */
					 		},
					  { display: '支付状态', name: 'result_state', align: 'left',width:80}
						 		
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatPayForAccMain.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: 0,
                     delayLoad: true,//初始化不加载，默认false
                     checkBoxDisplay:isCheckDisplay,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
		                { text: '审核（<u>Z</u>）', id:'audit', click: audit, icon:'bluebook' },
						{ line:true },
						{ text: '消审（<u>U</u>）', id:'unaudit', click: unaudit,icon:'bookpen' },
						{ line:true },
						{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
						{ line:true } ,
						{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.pay_id  + "|" + 
								rowdata.state + "|" + 
								rowdata.vouch_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}

  //是否显示复选框
    function isCheckDisplay(rowdata){
       	if(rowdata.pay_id == null) return false;
         return true;
    }
    function add_open(){
    	parent.$.ligerDialog.open({
    		url : 'hrp/mat/matpay/matpayforaccmain/matPayForAccMainAddPage.do?isCheck=false',
			height: 580,
			width: 1000,
			title:'添加付款单',
			modal:true,
			showToggle:false,
			showMax:true,
			showMin: true,
			isResize:true,
			parentFrameUse:window.name
    	});
    }
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){	
                        	if(this.state ==1){
                        		ParamVo.push(
        								this.group_id   +"@"+ 
        								this.hos_id   +"@"+ 
        								this.copy_code   +"@"+ 
        								this.pay_id  +"@"+ 
        								this.pay_bill_no 
        								) 
                        	}else{
                        		$.ligerDialog.error('只有未审核状态的发票允许删除');
                        	}
                        })
						if(ParamVo.length >0){
							$.ligerDialog.confirm('确定删除?', function (yes){
	                        	if(yes){
	                            	ajaxJsonObjectByUrl("deleteMatPayForAccMain.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
	                            		if(responseData.state=="true"){
	                            			query();
	                            		}
	                            	});
	                        	}
	                        }); 
						}
                    }
    	}
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'matBillMainImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){

		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"pay_id="+vo[3]+"&"+ 
			"state="+vo[4]+"&"+ 
			"vouch_id="+vo[5]
			/*$.ligerDialog.open({ url : 'matPayForAccMainUpdatePage.do?isCheck=false&'+parm,data:{}, top: 0,
				height: 580,width: 1000, title:'修改',modal:true,showToggle:false,showMax:true,
				showMin: true,isResize:true,
				}); */
		parent.$.ligerDialog.open({
    		url : 'hrp/mat/matpay/matpayforaccmain/matPayForAccMainUpdatePage.do?isCheck=false&'+parm,
			height: 580,
			width: 1000,
			title:'修改付款单',
			modal:true,
			showToggle:false,
			showMax:true,
			showMin: true,
			isResize:true,
			parentFrameUse:window.name
    	});

    }
    
    //审核
    function audit(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str = '';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	var busi_no="";//生成凭证使用

        	 $(data).each(function (){
        		 if(this.state != 1){
        			 str += this.pay_bill_no +",";
         		 }else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code +"@"+ this.pay_id +"@"+ this.pay_bill_no +"@"+this.state +"@"+2+"@"+this.vouch_id
					)}
        		 busi_no=busi_no+this.pay_id+","
        	 })
        	 busi_no=busi_no.substring(0,busi_no.length-1);

        	 if(str != ''){
             	$.ligerDialog.error('付款单号：<span style="color:red">'+str+'不是未审核状态</span>（只有未审核状态的付款单允许审核） ');
             }else{
             	 $.ligerDialog.confirm('确定审核所选付款单吗?', function (yes){
     	             	if(yes){
     	                 	ajaxJsonObjectByUrl("updatePayStateForAcc.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
     	                 		if(responseData.state=="true"){
     	                 			loadHead(null);
     	                 			
     	                 			var para={
     	        							busi_date_b:'',
     	        							busi_date_e:'',
     	        							template_code:'001',
     	        							vouch_date:'${vouch_date}',
     	        							init_type:1,
     	        							mod_code:'04',
     	        							busi_type_code:'040703',
     	        							busi_no:busi_no,
     	        							busi_log_table:'ACC_BUSI_LOG_0408',
     	        							huizong_sql:''
     	        					};
     	        					//console.log(para);
     	        					var loadIndex = layer.load(1);
     	        		    		ajaxJsonObjectBylayer("/CHD-HRP/hrp/acc/autovouch/matautovouch/queryVouchJsonByBusi.do",para,function(responseData){	
     	        		    			//console.log(responseData);
     	        		    			layer.close(loadIndex);
     	        		    			var para={
     	        		    				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
     	        		    				title:'会计凭证',
     	        		    				width:0,
     	        		    				height:0,
     	        		    				isShowMin:true,
     	        		    				isModal:true,
     	        		    				data:{vouch:responseData.vouch,
     	        		    					busi_log_table:'ACC_BUSI_LOG_0408',
     	        		    					busi_type_code:'040703',
     	        		    					busi_no:responseData.busi_no,
     	        		    					template_code:'001'}
     	        		    			};
     	        		    			//期末处理生成凭证格式：data:{vouch:responseData,busi_log_table:'ACC_VOUCH_SOURCE',busi_type_code:'0103'}
     	        		    			//console.log(para);
     	        		    			parent.parent.openDialog(para);
     	        		      		},layer,loadIndex);

     	                 		}
     	                 	});
     	             	}
     	          }); 
             }
        }	
    }
    //消审
    function unaudit(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str = '' ;
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	 $(data).each(function (){
        		 if(this.state != 2){
        			 str += this.pay_bill_no +",";
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.pay_id +"@"+ this.pay_bill_no +"@"+this.state +"@"+ 1 +"@"+this.vouch_id
					)};
        	 })
        	 if(str != ''){
              	$.ligerDialog.error('付款单号：<span style="color:red">'+str+'不是已审核状态</span>（只有已审核状态的付款单允许消核） ');
             }else{
             	 $.ligerDialog.confirm('确定消审所选付款单吗?', function (yes){
     	             	if(yes){
     	                 	ajaxJsonObjectByUrl("updatePayStateForAcc.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
     	                 		if(responseData.state=="true"){
     	                 			loadHead(null);
     	                 		}
     	                 	});
     	             	}
     	          }); 
             }
        }	
       
    }
   /*  //确认
    function confirm(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var flag = false;
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	 $(data).each(function (){
        		 if(this.state != 2){
        			 flag = true;
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.pay_id +"@"+this.state +"@"+ 3+"@"+this.checker +"@"+ this.chk_date
					)};
        	 })
        }	
        if(flag){
        	$.ligerDialog.error('只允许审核状态的付款单确认');
        }else{
        	 $.ligerDialog.confirm('确定确认所选付款单吗?', function (yes){
	             	if(yes){
	                 	ajaxJsonObjectByUrl("updatePayState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
	                 		if(responseData.state=="true"){
	                 			loadHead(null);
	                 		}
	                 	});
	             	}
	             }); 
        }
    }
    //取消确认
    function unconfirm(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var flag = false;
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	 $(data).each(function (){
        		 if(this.state != 3){
        			 flag = true;
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.pay_id +"@"+this.state +"@"+ 2+"@"+this.checker +"@"+ this.chk_date
					)};
        	 })
        }	
        if(flag){
        	$.ligerDialog.error('只允许确认状态的付款单取消确认');
        }else{
        	 $.ligerDialog.confirm('确定取消确认所选付款单吗?', function (yes){
	             	if(yes){
	                 	ajaxJsonObjectByUrl("updatePayState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
	                 		if(responseData.state=="true"){
	                 			loadHead(null);
	                 		}
	                 	});
	             	}
	             }); 
        }
    } */
    function loadDict(){
            //字典下拉框
	    	//供应商下拉框
			autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',240);
            
			autocomplete("#set_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true,'',false,'',240);
/* 			autocomplete("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true, '',false,'',240); */
			autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:1},false,'',240);
			//科室：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
			//autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true);
			//采购员下拉框
			//autocomplete("#stocker", "../../queryMatStoctEmpDict.do?isCheck=false", "id", "text", true, true);
			autocomplete("#maker", "../../querySysUser.do?isCheck=false", "id", "text", true, true,'',false,'',240);
			//autodate("#beginDate", "yyyy-mm-dd", "month_first");
	    	//autodate("#endDate", "yyyy-mm-dd", "month_last");
			$("#beginDate").ligerTextBox({width:110});
	        $("#endDate").ligerTextBox({width:110});
	        
	        autodate("#makeBeginDate", "yyyy-mm-dd", "month_first");
	    	autodate("#makeEndDate", "yyyy-mm-dd", "month_last");
			$("#makeBeginDate").ligerTextBox({width:110});
	        $("#makeEndDate").ligerTextBox({width:110});
	        
	        $("#state").ligerComboBox({width:240});
			$("#sup_id").ligerTextBox({width:240});
	        $("#pay_bill_type").ligerComboBox({width:240});
	        $("#pay_bill_no").ligerTextBox({width:240});
	        
	        check_column1_manager = $("#check_column1").ligerCheckBox();check_column1_manager.setValue(false);

         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		//hotkeys('B', downTemplate);

		//hotkeys('E', exportExcel);

		// hotkeys('P', printDate);
		
		hotkeys('Z', audit);

		hotkeys('U', unaudit);
		
		//hotkeys('I', imp);
		

	 }
	  //打印设置
		function printSet(){
			var useId=0;//统一打印
			if('${p04028 }'==1){
				//按用户打印
				useId='${sessionScope.user_id }';
			}
			officeFormTemplate({template_code:"04026",user_id:useId});
			
			/**
			
			parent.parent.$.ligerDialog.open({url : 'hrp/mat/matpay/matpaymain/matPayMainPrintSetPage.do?template_code=04026&use_id='+useId,
				data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			});*/
		}

		  //打印
		    function print(){
		    	 var useId=0;//统一打印
		 		if('${p04028 }'==1){
		 			//按用户打印
		 			useId='${sessionScope.user_id }';
		 		}
		    	 /*
		    	//if($("#create_date_b").val())
		 		var data = gridManager.getCheckedRows();
				if (data.length == 0){
					$.ligerDialog.error('请选择行');
				}else{
					
					var pay_id ="" ;
					var in_nos = "";
					$(data).each(function (){		
						if(this.state != 2){
							in_nos = in_nos + this.in_no + "<br>";
						}
						
						pay_id  += this.pay_id+","
							
					});
					 var para={
			    			paraId :pay_id.substring(0,pay_id.length-1) ,
			    			
			    			template_code:'04026',
			    			isPrintCount:false,//更新打印次数
			    			isPreview:true,//预览窗口，传绝对路径
			    			use_id:useId,
			    			p_num:1
			    			
			    	}; 
				 	
			    	printTemplate("hrp/mat/matpay/matpaymain/queryMatPayMainByPrintTemlate.do?isCheck=false",para);
			    	
				}*/
		 		var data = gridManager.getCheckedRows();
				if (data.length == 0){
					$.ligerDialog.error('请选择行');
				}else{
					var pay_id ="" ;
					var in_nos = "";
					$(data).each(function (){		
						if(this.state != 2){
							in_nos = in_nos + this.in_no + "<br>";
						}
						
						pay_id  += this.pay_id+","
							
					});
					var para={
							paraId :pay_id.substring(0,pay_id.length-1) ,
							template_code:'04026',
							class_name:"com.chd.hrp.mat.serviceImpl.matpay.MatPayMainServiceImpl",
							method_name:"queryMatPayMainByPrintTemlateNew",
							isSetPrint:false,//是否套打，默认非套打
							isPreview:true,//是否预览，默认直接打印
							use_id:useId,
							p_num:1
					};
					
					officeFormPrint(para);
				}
		    	
		    }
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    	<tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款日期：</td>
        	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="beginDate" id="beginDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
           				</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="endDate" id="endDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
            		</tr>
				</table>
	        </td>  
            <!-- <td align="left" class="l-table-edit-td" style="width: 160">
            	<input class="Wdate" name="beginDate" id="beginDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left" style="width: 15">至：</td>
            <td align="left" style="width: 160">
            	<input class="Wdate" name="endDate" id="endDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            </td> -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款单号：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_bill_no" type="text" id="pay_bill_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
             <td align="left" class="l-table-edit-td">
            	<select name="state" id="state">
            			<option value="">请选择</option>
                		<option value="1">未审核</option>
                		<option value="2">审核</option>
                		<option value="3">已确认</option>
            	</select>
            	
            </td>
            <td align="left"></td>
            <td><input name="check_column1" type="checkbox" id="check_column1" ltype="text"  />显示支付失败</td>
        </tr> 
        <tr> 
        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
        	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="makeBeginDate" id="makeBeginDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
           				</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="makeEndDate" id="makeEndDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
            		</tr>
				</table>
	        </td>  
        	<td align="right" class="l-table-edit-td" style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td" ><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单据类型：</td>
            <td align="left" class="l-table-edit-td">
	            <select name="pay_bill_type" id="pay_bill_type"style="width: 135px;" >
	            			<option value="">请选择</option>
	                		<option value="0">付款单</option>
	                		<option value="1">退款单</option>
	            </select>
	            </td>
            <td align="left"></td>
           	
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td" style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td" ><input name="maker" type="text" id="maker" ltype="text" validate="{required:true,maxlength:20}" /></td>
       
        <td align="right" class="l-table-edit-td" width="10%">虚&nbsp;&nbsp;仓：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            	<td align="right" class="l-table-edit-td" >
				仓库：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
       
        </tr>
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
				<tr>
	                <th width="200">付款单号</th>
	                <th width="200">单据类型</th>	
	                <th width="200">付款日期</th>	
	                <th width="200">供应商</th>	
	                <th width="200">付款金额</th>
	                <th width="200">制单人</th>	
	                <th width="200">制单日期</th>
	                <th width="200">审核人</th>	
	                <th width="200">审核日期</th>
	                <th width="200">状态</th>	
				</tr>
			</thead>
			<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
