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
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
    	 loadHead(null);
         loadDict();//加载下拉框
		// loadHotkeys();
		 var dataGrid = [
           //  { id: 1, name: '单张生成' },
             { id: 2, name: '按单据生成' },
             { id: 3, name: '按日期生成'}
             ]; 
         $("#initType").ligerRadioList({
             data: dataGrid,
             textField: 'name'
         });
         liger.get("initType").setValue("3");
         $(':button').ligerButton({width:70});
         //$("button[name='createVouchBtn']").ligerButton({width:100});
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
		var busi_type_code = liger.get("acc_busi_type").getValue();
		if(""==busi_type_code){
        	
        	$.ligerDialog.error('请选择业务类型');
        	return;
        }
		
		
		grid.options.parms.push({
			name : 'busi_type_code',
			value : busi_type_code.split("@")[0]
		});
		
		grid.options.parms.push({
			name : 'mod_code',
			value : '02'
		});
		
		grid.options.parms.push({
			name : 'busi_date_b',
			value : $("#busi_date_b").val()
		});
		grid.options.parms.push({
			name : 'busi_date_e',
			value : $("#busi_date_e").val()
		});
		grid.options.parms.push({
			name : 'business_no_b',
			value : $("#business_no_b").val()
		});
		grid.options.parms.push({
			name : 'business_no_e',
			value : $("#business_no_e").val()
		});
		
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'proj_id',
			value : liger.get("proj_id").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'emp_id',
			value : liger.get("emp_id").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'source_id',
			value : liger.get("source_id").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'payment_item_id',
			value : liger.get("payment_item_id").getValue().split("@")[0]
		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgAutoVouch.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,checkBoxDisplay : isCheckDisplay,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true,pageSize:500//,heightDiff: -10
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    var columnsData;
    function f_setColumns() {
    	
    	var mod_code = '02';
    	var acc_busi_type = liger.get("acc_busi_type").getValue().split("@")[0];
    	
		$.post("queryBudgAutoVouchHead.do?isCheck=false", {
			"mod_code" : mod_code,
			"busi_type_code":acc_busi_type
		}, function(data) {
			var columns = [];
			
			$.each(data.Rows, function(i, v) {
				var name_dis = v.head_name.split(",");
				var is_show = false;
				if(v.business_url != '' && v.business_url != null){
					is_show = true;
				}
				
				for(var i = 0 ; i < name_dis.length; i++){
					var value_name = name_dis[i].split("|")[0];
					var display_name = name_dis[i].split("|")[1];
					if(value_name == "AMOUNT_MONEY"){
						columns.push({display:display_name,name:value_name,width: 130,align : 'right',
							render:function(rowdata){
								 return formatNumber(rowdata.AMOUNT_MONEY ==null ? 0 : rowdata.AMOUNT_MONEY,2,1);
						 	 } });
					}else if(value_name == "OFFSET_AMOUNT"){
						columns.push({display:display_name,name:value_name,width: 130,align : 'right',
							render:function(rowdata){
								 return formatNumber(rowdata.OFFSET_AMOUNT ==null ? 0 : rowdata.OFFSET_AMOUNT,2,1);
						 	 } });
					}else if(value_name == "PAY_AMOUNT"){
						columns.push({display:display_name,name:value_name,width: 130,align : 'right',
							render:function(rowdata){
								 return formatNumber(rowdata.PAY_AMOUNT ==null ? 0 : rowdata.PAY_AMOUNT,2,1);
						 	 } });
					}else if(value_name == "BUSINESS_NO"){
						columns.push({display:display_name,name:value_name,width:130,align : 'left',frozen: true,render:function(rowdata){
							if(rowdata.BUSINESS_NO == '合计'){
								return '合计';
							}else{
								if(is_show){
									return "<a href=javascript:openMatBusiness('"+rowdata.BUSINESS_ID+"','"+v.business_url+"')>"+rowdata.BUSINESS_NO+"</a>";
								}else{
									return rowdata.BUSINESS_NO;
								}
							}
						}});
						columns.push({display:"凭证号",name:"VOUCH_NO",width:100,align : 'left',frozen: true,render:function(rowdata){
							if(rowdata.VOUCH_NO != null){
								if(rowdata.VOUCH_NO=='-'){
									return rowdata.VOUCH_NO;
								}else{
									return "<a href=javascript:openSuperVouch('"+rowdata.VOUCH_ID+"')>"+rowdata.VOUCH_NO+"</a>";
								}
							}else{
								return '';
							}
							
						}});
					}else if(value_name == "BUSINESS_ID"){
						columns.push({display:display_name,name:value_name,width:100,align : 'left',hide:true});
					}else{
						columns.push({display:display_name,name:value_name,width: 130,align : 'left'});
					}
					
				}
			});
			columnsData = data;
			grid.set('columns', columns);
			//grid.reRender();
		}, "json");

	}
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
	}
    
    function openMatBusiness(business_id,business_url){
    	var paras = business_id;
		parent.$.ligerDialog.open({
			title: '单据查看',
			height: $(window).height(),
			width: $(window).width(),
			url: business_url.replace("|","&") + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
    
    
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
  
   
    function loadDict(){
		$("#acc_busi_type").ligerComboBox({
			width : 160
		});  
		$("#template_code").ligerComboBox({
			width : 160
		}); 
		
        $("#acc_busi_type").ligerComboBox({
               url: '../../queryBusiType.do?isCheck=false&is_vouch=1&mod_code=02',
               valueField: 'id',
               textField: 'text', 
               selectBoxWidth: 160,
                  	autocomplete: true,
                  	width: 160,
					onSuccess : function(data) {
						this.setValue("0201@ACC_BUSI_LOG_0201");
					},
					onSelected:function(value){
						liger.get("template_code").setValue("");
						liger.get("template_code").setText("");
						autocomplete("#template_code","../../autovouch/his/charge/queryAutoBusiTemplate.do?isCheck=false&busi_type_code="+value.split("@")[0],"id","text",true,true,"",true);
						//$("#template_code").reload();
				         f_setColumns();
					}
        });
		autocomplete("#dept_id", "../../../sys/queryDeptDict.do?isCheck=false","id", "text", true, true,null,null,null);
		autocomplete("#proj_id", "../../../sys/queryProjDictDict.do?isCheck=false","id", "text", true, true,null,null,null,"220");
		autocomplete("#emp_id","../../queryEmpDict.do?isCheck=false", "id","text", true, true,null,false,null);
		autocomplete("#payment_item_id","../../queryBudgPaymentItemDict.do?isCheck=false", "id","text", true, true,null,false,null);
		autocomplete("#source_id","../../../sys/querySourceDict.do?isCheck=false", "id","text", true, true,null,false,null);
		
		
		$("#busi_date_b").ligerTextBox({width:100 });
		$("#busi_date_e").ligerTextBox({width:100 });
		
		autodate("#busi_date_b", "yyyy-mm-dd", "month_first");
    	autodate("#busi_date_e", "yyyy-mm-dd", "month_last");
		
		
		$("#business_no_b").ligerTextBox({width:100 });
		$("#business_no_e").ligerTextBox({width:100 });
		
		$("#vouch_date").ligerTextBox({width:90 });
		
		$("#vouch_date").val('${vouch_date}');
	}  
    
    function createVouch(flag){
    	/* if($("#busi_date_b").val().substring(0,7)!=$("#busi_date_e").val().substring(0,7)){
		$.ligerDialog.error("不能跨月生成凭证！");
		return;
		} */
		if(liger.get("template_code").getValue()==""){
			$.ligerDialog.error("请选择凭证模板！");
			return;
		}
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		
		
		var busi_no="";
		if(liger.get("initType").getValue()==1 || liger.get("initType").getValue()==2){
			//按单据生成
			var data = gridManager.getCheckedRows();
		    if (data.length>0){
		        $(data).each(function (){
		        	busi_no=busi_no+this.BUSINESS_ID+",";
				});
		        
		        busi_no=busi_no.substring(0,busi_no.length-1);
		    }else{
		    	$.ligerDialog.error("请选择单据！");
				return;
		    }
		   
		}
	   
		var para={
				busi_date_b:$("#busi_date_b").val(),
				busi_date_e:$("#busi_date_e").val(),
				template_code:liger.get("template_code").getValue(),
				vouch_date:$("#vouch_date").val(),
				init_type:liger.get("initType").getValue(),
				mod_code:'02',
				busi_type_code:liger.get("acc_busi_type").getValue().split("@")[0],
				busi_no:busi_no,
				busi_log_table:liger.get("acc_busi_type").getValue().split("@")[1]
		};
		
		if(flag==1){
			checkVouch(para);
		}else{
			$.ligerDialog.confirm('确定生成凭证?', function (yes){
		    	if(yes){
		    		var loadIndex = layer.load(1);
		    		ajaxJsonObjectBylayer("queryVouchJsonByBusi.do",para,function(responseData){	
		    			//console.log(responseData);
		    			layer.close(loadIndex);
		    			var para={
		    				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
		    				title:'会计凭证',
		    				width:0,
		    				height:0,
		    				isShowMin:true,
		    				isModal:true,
		    				/* data:{vouch:responseData.vouch,busi_log_table:liger.get("acc_busi_type").getValue().split("@")[1],busi_type_code:liger.get("acc_busi_type").getValue().split("@")[0],
		    					busi_no:responseData.busi_no,template_code:liger.get("template_code").getValue()} */
		    					data:{auto_id:responseData.auto_id,busi_log_table:liger.get("acc_busi_type").getValue().split("@")[1]}
		    			};
		    			//期末处理生成凭证格式：data:{vouch:responseData,busi_log_table:'ACC_VOUCH_SOURCE',busi_type_code:'0103'}
		    			parent.openDialog(para);
		      		},layer,loadIndex);
		    	}
		    }); 
		}

    }
	
    function isCheckDisplay(rowdata) {
		if (rowdata.BUSINESS_NO == "合计")
			return false;

		return true;

	}

    
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}	
	
	function printDate(){
		 if(grid.getData().length==0){
  		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
  	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"业务日期："+$("#busi_date_b").val()+"至"+$("#busi_date_e").val(),"colSpan":"5"},
	      		  ]
	      	};
	   		
 		var printPara={
 			rowCount:1,
 			title:'借款报销凭证',
 			columns: JSON.stringify(grid.getPrintColumns()),//表头
 			class_name: "com.chd.hrp.acc.service.autovouch.AccBudgAutoVouchService",
			method_name: "queryBudgAutoVouchPrint",
			bean_name: "accBudgAutoVouchService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 			};
  	
 		//执行方法的查询条件
 		$.each(grid.options.parms,function(i,obj){
 			printPara[obj.name]=obj.value;
  	});
 		
  	officeGridPrint(printPara); 
	
	}
	
	//检查凭证
	var checkJson=[];
	function checkVouch(para){
		var loadIndex = layer.load(1);
		ajaxJsonObjectBylayer("../checkAutoVouch.do?isCheck=false",para,function(res){
			if(res.Total==0){
				$.ligerDialog.success("检查成功，业务数据正常。");
				return;
			}
			
			checkJson=res;
			$.etDialog.open({
   				id: "vouchNotePage",
   				url: '../vouchNotePage.do?isCheck=false',
   				frameName : window.name,
   				width: 700,
   				height: $(window).height()-200,
   				shade: 0,
   				zIndex:0,
   				maxmin: true,
   				title: '凭证说明',
   				success: function(layero){
   				   	 layer.setTop(layero);
   				 },resizing: function(layero){
   					  
   				 },full: function(layero){
   					 
   				 },min: function(layero){
   					  
   				 },restore: function(layero){
   					
   				 }
   			});
			
			
  		},layer,loadIndex);
		
	}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
            <td align="right" class="l-table-edit-td" >业务日期：</td>
            <td align="left" class="l-table-edit-td" >
            	<input class="Wdate" id="busi_date_b" name="busi_date_b"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            </td>
            <td>至</td>
            <td align="left" class="l-table-edit-td" >
            	<input class="Wdate" id="busi_date_e" name="busi_date_e"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">业务类型：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="acc_busi_type" name="acc_busi_type" />
            </td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">项目：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
            	<input id="proj_id" name="proj_id" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">科室：</td>
            <td align="left" class="l-table-edit-td">
            	
            	<input id="dept_id" name="dept_id" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">申请人：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="emp_id" name="emp_id" />
            </td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:10px;">单据编号：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="business_no_b" name="business_no_b" />
            </td>
            <td>至</td>
            <td align="left" class="l-table-edit-td" >
            	<input id="business_no_e" name="business_no_e" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">资金来源：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="source_id" name="source_id" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">支出项目：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="payment_item_id" name="payment_item_id" />
            </td>
        </tr>
    </table>
    <hr/>
    <table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" >
    <tr>
    	<td align="right" class="l-table-edit-td"  style="width:65px;"><font color="red">*</font>凭证模板：</td>
		<td align="left" class="l-table-edit-td" style="width:120px;">
		    <input type="text" id="template_code"  />
		</td>
		<td align="right" class="l-table-edit-td" style="width:70px;"><font color="red">*</font>凭证日期：</td>
		<td align="left" class="l-table-edit-td" style="width:90px;">
			<input class="Wdate" name="vouch_date"  type="text"  id="vouch_date"   onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
		</td>
		<td align="right" class="l-table-edit-td" style="width:70px;"><font color="red">*</font>生成方式：</td>
		<td align="left" class="l-table-edit-td" style="width:250px;">
			<div id="initType"></div>
		</td>
    	<td align="right">
    		<button accessKey="Q" onclick="query();">查询 Q</button>
    		<button accessKey="C" name="createVouchBtn" onclick="createVouch(1);">检查凭证 C</button>
    		<button accessKey="S" name="createVouchBtn" onclick="createVouch(2);">生成凭证 S</button>
    		<button accessKey="P" onclick="printDate();">打 印 P</button>
    	</td>
    </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
