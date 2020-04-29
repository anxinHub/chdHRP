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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var store_id = '';
	var store_no = '';
	var store_name ;
	var isFlag = 0;
	var isHideCheck = '${p04031 }' == 1 ? false : true;
	 var renderFunc = {
			 state:function(value){
				if(value== 0){
					return "已中止";
				}else if(value == 1){
					return "未提交";
				}else if(value == 2){
					return "已提交";
				}else if(value == 3){
					return "已审核";
				} 
			 }
	 };
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		query();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
    	grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()}); 
    	grid.options.parms.push({name : 'end_date',value : $("#end_date").val()}); 
    	grid.options.parms.push({name : 'dept_id',value : liger.get("dept_code").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'brif',value : $("#brif").val()});
    	grid.options.parms.push({name : 'req_code',value : $("#req_code").val()});
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'state',value : liger.get("state").getValue()});
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                 { display: '需求计划单号', name: 'req_code', align: 'left',width:'120',
		                	 render : function(rowdata, rowindex,value) {
		                		
									return "<a href=javascript:update_open('"
									+ rowdata.group_id   + "," 
									+ rowdata.hos_id + "," 
									+ rowdata.copy_code  + ","
									+ rowdata.req_id  + ","
									+ rowdata.req_code  + ","
									+"')>"+rowdata.req_code+"</a>"
								}	 
		                 },
		                 { display: '需求来源', name: 'field_desc', align: 'left',width:'120'},
		                 { display: '响应仓库', name: 'store_name', align: 'left',width:'120' },
						 { display: '编制部门', name: 'dept_name', align: 'left',width:'140' },
						 { display: '摘要', name: 'brif', align: 'left',width:'180' },
						 { display: '编制人', name: 'maker', align: 'left',width:'90'},
						 { display: '编制日期', name: 'make_date', align: 'left',width:'90'},
						 { display: '审核人', name: 'checker', align: 'left',hide:isHideCheck, width:'90'},
						 { display: '审核日期', name: 'check_date', align: 'left' ,width:'90',hide:isHideCheck},
						 { 
		    		 			display: '状态', name: 'state', align: 'right', width: '80',hide:true
		    		 	},{ 
		    		 			display: '状态', name: 'state_name', align: 'right', width: '80'
		    		 		},
						 { display: '其他需求物资', name: 'other_inv', align: 'left',width:'140' },
						 { display: '退回原因', name: 'return_reason', align: 'left', width: '120' }
					
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatDeptRequriedPlan.do?isCheck=true',
		                 width: '100%', height: '100%', checkbox: true, rownumbers : true,delayLoad:true,
		                 selectRowButtonOnly:true,
		                 toolbar: { items: 
		                	 	[
		                          	{ text: '查询（<u>Q</u>）', id:'query', click: query ,icon:'search' },
		                            { line:true },
		                            {text: '添加（<u>O</u>）', id:'add_openStore', click: add_deptRequried ,icon:'add' },
		                            { line:true },
		                            {text: '删除（<u>D</u>）', id:'del', click: del ,icon:'delete' },
		                            { line:true },
		                            {text: '提交（<u>S</u>）', id:'submit', click: submit ,icon:'up' },
		                            { line:true },
		                            {text: '取消提交（<u>U</u>）', id:'submitCancle', click: submitCancle ,icon:'edit' },
		                            { line:true },
		                            {text: '审核（<u>A</u>）', id:'audit', click: audit ,icon:'audit',hide:isHideCheck},
		                            { line:true,hide:isHideCheck},
		                            {text: '取消审核（<u>U</u>）', id:'unaudit', click: unaudit ,icon:'unaudit',hide:isHideCheck},
		                            { line:true,hide:isHideCheck},
		                            {text: '退回科室（<u>R</u>）', id:'ret', click: ret ,icon:'back' },
		                            { line:true } ,
			        				{ text: '批量打印', id:'printDetail', click: printDetail, icon:'print' },
		                            { line:true },
		                            { text: '模板设置', id:'printSetDetail', click: printSetDetail, icon:'settings' },
			        				{ line:true } ,
		                            {text: '中止计划（<u>T</u>）', id:'abort', click: abort ,icon:'cut' },
		                            { line:true },
		                            {text: '复制（<u>C</u>）', id:'copy', click: copy ,icon:'copy' },

		                   		]
		                 },
						onDblClickRow : function (rowdata, rowindex, value){
							update_open(		
								rowdata.group_id   + "," 
								+ rowdata.hos_id + "," 
								+ rowdata.copy_code  + ","
								+ rowdata.req_id  + ","
								+ rowdata.req_code  
								
							);
						} 

		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);//查询
		hotkeys('O', add_openStore);//添加
		hotkeys('D', del);//删除
		hotkeys('T', abort);//中止计划
		hotkeys('S', submit);//提交
		hotkeys('U', submitCancle);//取消提交
		hotkeys('C', copy);//复制
		hotkeys('A', audit);//审核
		hotkeys('U', unaudit);//销审
		hotkeys('R', ret);//退回科室
	}
	
	
	function add_deptRequried(){
		//科室需求是否区分响应库房参数
		var para_value = '${p04032 }'
		if(para_value == 1 ){
			//跳转到选择仓库页
			add_openStore();
		}else{
			//直接跳转到添加页
			openAdd();
		}
	}
	
	//选择仓库再跳至添加页面
	function add_openStore(){
		
		$.ligerDialog.open({url: 'matDeptRequriedPlanStorePage.do?isCheck=false', height: 250,width:450, 
				title:'响应库房',modal: true, showToggle: false,initShowMax:true, showMin: false, isResize: true,
				buttons: [ 
				           { text: '确定', onclick: function (item, dialog) { 
  				        	   	dialog.frame.saveMatDepartRequriedPlanStore();
  				        	   	
  				        	   	if( isFlag == 0){
  				        	   		dialog.close(); 
  				        	   		openAdd();
  				        	   	}
  				        	    
				        	    },cls:'l-dialog-btn-highlight' }, 
				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
				         ] 
			});
	}
	
	//打开添加页面
	function openAdd(){
		parent.$.ligerDialog.open({
			title: '科室需求计划添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/require/dept/plan/matDeptRequriedPlanAddPage.do?isCheck=false&store_id='+store_id+'&store_no='+store_no+'&store_name='+store_name, 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 		
	}
	
	//修改页面
	function update_open(obj) {
		
		var vo = obj.split(",");
		var parm = "group_id="+vo[0]+"&hos_id="+vo[1]+"&copy_code="+vo[2]+"&req_id="+vo[3]+"&req_code="+vo[4]; 
		parent.$.ligerDialog.open({ 
			title: '科室需求计划修改',
			height: $(window).height(),
			width: $(window).width(),
			url : 'hrp/mat/require/dept/plan/matDeptRequriedPlanUpdatePage.do?isCheck=false&' + parm, 
			modal:true,showToggle:false,showMax:true ,showMin: false,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});


	}
	
	
	//删除
	function del(){
		//只有新建状态（STATE=1）的数据允许删除
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要删除的单据！');
        	return;
        }else{
            var ParamVo =[];
            var req_nos = "";
            $(data).each(function (){	
            	if(this.state != 1  ){
            		req_nos = req_nos + this.req_code + ",";
            	}
				ParamVo.push(
					//表的主键
					this.req_id +"@"+ this.req_code  
				)
            });
            
            if(req_nos != ""){
            	$.ligerDialog.error("删除失败！请选择未提交状态的单据！");
        		return;
            }
            $.ligerDialog.confirm('确定要删除吗?', 
            		function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("deleteMatDeptRequriedPlan.do?isCheck=true",{ParamVo : ParamVo.toString()},
                        			function (responseData){
	                            		if(responseData.state=="true"){
	                            			query();
	                            		}
                        			});
                    	}else{
                    		return;
                    	}
            		}); 
        }
	}
	
	//终止计划
	function abort(){
		// 对于非执行状态（STATE=1 OR STATE=2）的需求计划可以被中止，修改STATE=0
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要中止的单据！');
        	return;
        }else{
            var ParamVo =[];
            var req_nos = "";
            $(data).each(function (){	
            	
            	if(this.state == 0 ){ 
            		
            		req_nos = req_nos + this.req_code + ",";
            	} 
            	
				ParamVo.push(
					//表的主键
					this.req_id +"@"+ this.req_code  
				)
				
            });
            
            if(req_nos != ""){
            	$.ligerDialog.warn("请选择未中止的单据！");
        		return;
            }
            
            $.ligerDialog.confirm('确定要中止吗?', 
            		function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("abortMatDeptRequriedPlan.do?isCheck=true",{ParamVo : ParamVo.toString()},
                        			function (responseData){
	                            		if(responseData.state=="true"){
	                            			query();
	                            		}
                        			});
                    	}else{
                    		return;
                    	}
            		}); 
        }
	}
	//提交
	function submit(){
		//提交新建状态（STATE=1）并且未提交的需求计划，修改IS_SUBMIT=1；
		var data = gridManager.getCheckedRows();
		
        if (data.length == 0){
        	$.ligerDialog.error('请选择要提交的单据！');
        	return;
        }else{
            var ParamVo =[];
            var req_nos = "";
            $(data).each(function (){	           	
            	if(this.state != 1  ){
            		req_nos = req_nos + this.req_code + ",";
            	}
            	
				ParamVo.push(
					//表的主键
					this.req_id +"@"+ this.req_code  
				)				
            });
            
            if(req_nos !=""){
            	$.ligerDialog.error('提交失败！请选择状态为未提交的单据！');
        		return;
            }
            
            $.ligerDialog.confirm('确定要提交吗?', 
            	function (yes){
                    if(yes){
                       	ajaxJsonObjectByUrl("submitMatDeptRequriedPlan.do?isCheck=true",{ParamVo : ParamVo.toString()},
                        	function (responseData){
	                           	if(responseData.state=="true"){
	                            	query();
	                           	}
                        	});
                    }else{
                    	return;
                    }
            	}); 
        }
	}
	//取消提交
	function submitCancle(){
		//取消提交已提交状态（STATE=2）并且已提交的需求计划，修改STATE=1
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要取消提交的单据！');
        	return;
        }else{
            var ParamVo =[];
            var req_nos ="";
            $(data).each(function (){	
            	if(this.state != 2  ){
            		req_nos = req_nos + ","+ this.req_code;
            	}
				ParamVo.push(
					//表的主键
					this.req_id +"@"+ this.req_code  
				)
				
            });
            
            if(req_nos != ""){
            	$.ligerDialog.error("取消提交失败！请选择状态为已提交的单据！");
				return;
            }
            
            $.ligerDialog.confirm('确定要取消提交吗?', 
            	function (yes){
                   	if(yes){
                       	ajaxJsonObjectByUrl("unSubmitMatDeptRequriedPlan.do?isCheck=true",{ParamVo : ParamVo.toString()},
                        	function (responseData){
	                           	if(responseData.state=="true"){
	                            	query();
	                           	}
                        	});
                    }else{
                    	return;
                    }
            	}); 
        	}
	}
	//复制
	function copy(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要复制的单据！');
        	return;
        }else{
        	var ParamVo =[];
            var req_nos ="";
            var req_codes = "";
            $(data).each(function (){	
            	/* if(this.state < 3  ){
            		req_nos = req_nos + ","+ this.req_code;
            	}
            	if(this.is_submit != 1){
            		req_codes = req_codes + ","+ this.req_code;
            	} */
				ParamVo.push(
					//表的主键
					this.group_id +"@"+ 
					this.hos_id +"@"+ 
					this.copy_code +"@"+ 
					this.req_id +"@"+ 
					this.req_code  
				)
            });
            
           /*  if(req_codes != ""){
            	$.ligerDialog.error("复制失败！请选择已提交的单据！");
				return;
            }
            
            if(req_nos != ""){
            	$.ligerDialog.error("复制失败！请选择已审核或已汇总的单据！");
				return;
            } */
            
            $.ligerDialog.confirm('确定要复制吗?', 
            	function (yes){
                   	if(yes){
                       	ajaxJsonObjectByUrl("copyMatDeptRequriedPlan.do?isCheck=true",{ParamVo : ParamVo.toString()},
                        	function (responseData){
	                           	if(responseData.state=="true"){
	                            	query();
	                           	}
                        	});
                    }else{
                    	return;
                    }
             }); 
        }
	}
	//审核
	function audit(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要审核的单据！');
        	return;
        }else{
            var ParamVo = [];
            var req_codes = "";
            $(data).each(function (){	
            	if(this.state != 2  ){
            		req_codes = req_codes + this.req_code+",";
            	}
            	
				ParamVo.push(
					//表的主键
					this.req_id +"@"+ this.req_code  
				)				
            });
            
            if(req_codes != ""){
            	$.ligerDialog.error("请选择状态为已提交的单据！");
				return;
            }
            
            $.ligerDialog.confirm('确定要审核吗?', 
            	function (yes){
                    if(yes){
                        ajaxJsonObjectByUrl("submitMatDeptRequriedConfirms.do?isCheck=true",{ParamVo : ParamVo.toString()},
                        	function (responseData){
	                            if(responseData.state=="true"){
	                            	query();
	                            }
                        	});
                    }else{
                    	return;
                    }
            	}); 
        }
	}
	//销审
	function unaudit(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要销审的单据！');
        	return;
        }else{
            var ParamVo =[];
            var req_codes = "";
            $(data).each(function (){	
            	if(this.state != 3  ){
            		req_codes = req_codes + this.req_code+",";
            	}
            	
				ParamVo.push(
					//表的主键
					this.req_id +"@"+ this.req_code  
				)
				
            });
            
            if(req_codes != ""){
            	$.ligerDialog.error("请选择状态为已审核的单据！");
				return;
            }
            
            $.ligerDialog.confirm('确定要取消审核吗?', 
            	function (yes){
                   	if(yes){
                        ajaxJsonObjectByUrl("unSubmitMatDeptRequriedConfirms.do?isCheck=true",{ParamVo : ParamVo.toString()},
                        	function (responseData){
	                           	if(responseData.state=="true"){
	                           		query();
	                          		}
                        	});
                    }else{
                    	return;
                   	}
            	}); 
        }
	}
	
	//退回科室
	function ret(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要退回的的单据！');
        	return;
        }else{
        	 var reqIds ="" ;
        	 var req_codes = "";
        	 $(data).each(function (){	
        		 //isHideCheck:04031系统参数
        		 if(isHideCheck){//0 true 
        			 if(this.state != 2){
            			 req_codes = req_codes + this.req_code +",";
                  	 }else{
                  		reqIds = reqIds + this.req_id +",";
                  	 }	
        		 }else{//1 false
        			 
        			 if(this.state != 3){
            			 req_codes = req_codes + this.req_code +",";
                  	 }else{
                  		reqIds = reqIds + this.req_id +",";
                  	 }	
        		 }
        		 
             });
        	
        	if(isHideCheck){//0 true 
        		if(req_codes !=""){
            		$.ligerDialog.error("请选择状态为已提交的单据！");
                	return;
            	}
        	}else{//1 false
        		if(req_codes !=""){
            		$.ligerDialog.error("请选择状态为已审核的单据！");
                	return;
            	}
        	} 
        	
         	$.ligerDialog.open({
         		url: 'matDeptRequriedConfirmReturnPage.do?isCheck=false&reqIds='+reqIds, 
         			height: 300,width:450, title:'退回科室',initShowMax:false,
         			modal:true, showToggle:false, showMax : true ,showMin: false, isResize:true,
       				buttons: [ 
       				           { text: '确定', onclick: function (item, dialog) { 
	          				        	   	dialog.frame.saveMatDepartRequriedConfirmReturn();	
	          				        	   	query();
       				        	    },cls:'l-dialog-btn-highlight' }, 
       				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
       				         ] 
       			});	
		
		}
	}
	
	//打印回调方法
	function lodopPrint() {
		var head = "<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
		head = head + "<tr><td>制单日期：" + $("#begin_date").val() + " 至  "
				+ $("#end_date").val() + "</td></tr>";
		head = head + "</table>";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		
	}
	//打印模板设置
	function printSetDetail(){
		  
		var useId=0;//统一打印
		
		officeFormTemplate({template_code:"04016",use_id:useId});
	}
	
	//打印
	function printDetail(){
		var useId=0;//统一打印
		if('${p04031 }'==1){
			//按用户打印
		}
		//if($("#create_date_b").val())
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var req_code ="" ;
			$(data).each(function (){		
				req_code  += "'"+this.req_code+"',"
			});
			var para={
				class_name:"com.chd.hrp.mat.serviceImpl.requrie.dept.MatRequirePlanServiceImpl",
				method_name:"printMatRequireMainData",
				req_code: req_code.substring(0,req_code.length-1) ,
				template_code:'04016',
				isPrintCount:false,//更新打印次数
				isPreview:true,//预览窗口，传绝对路径
				use_id:useId,
			}; 
			//printTemplate("hrp/mat/purchase/make/queryMatMakeByDetailPrintTemlate.do?isCheck=false",para);
			officeFormPrint(para);
		}
		 
	}
	//加载字典
	function loadDict() {
			
		autocomplete("#dept_code", "../../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true,{is_last : '1',read_or_write:'1'}, true, false,'160',"",200);//科室		
		autocomplete("#store_code", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_purchase : '1',read_or_write:'1'}, false, false,'160',"",200);//仓库
		/* if(isHideCheck){
			autoCompleteByData("#state", matRequireMain_state2.Rows, "id", "text", true, true,'',false,false,'160');//状态	
		}else{
			autoCompleteByData("#state", matRequireMain_state.Rows, "id", "text", true, true,'',false,false,'160');//状态		
		} */
		if(isHideCheck){
			$("#state").ligerComboBox({isShowCheckBox:true,isMultiSelect:true,
				data:[
				      {"id" : '0',"text" : '已中止'},
				      {"id" : '1',"text" : '未提交'},
				      {"id" : '2',"text" : '已提交'}
				      ],valueFieldID:'state',width:160
			});
		}else{
			$("#state").ligerComboBox({isShowCheckBox:true,isMultiSelect:true,
				data:[
				      {"id" : '0',"text" : '已中止'},
				      {"id" : '1',"text" : '未提交'},
				      {"id" : '2',"text" : '已提交'},
				      {"id" : '3',"text" : '已审核'}
				      ],valueFieldID:'state',width:160
			});
		}
		
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
	    autodate("#end_date", "yyyy-mm-dd", "month_last");
		
   		$("#begin_date").ligerTextBox({width:100});
		$("#end_date").ligerTextBox({width:100});
		$("#brif").ligerTextBox({width:250});
		$("#req_code").ligerTextBox({width:160});
	}
	

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
		<tr> 
			<td align="right" class="l-table-edit-td"  width="10%">编制日期：</td>
		 	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="begin_date" />
						</td>
						<td align="right" class="l-table-edit-td" style="width: 10px;">至：</td>
						<td >
							<input class="Wdate" name="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="end_date" />
						</td>
					</tr>
				</table>
			</td>
			 
			<td align="right" class="l-table-edit-td"  width="10%" >编制科室：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="dept_code" type="text" requried="true" id="dept_code" />
			</td>
			<td align="right" class="l-table-edit-td"  width="10%">响应库房：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="store_code" type="text" requried="true" id="store_code" />
			</td>
			  
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">摘　　要：</td>
			<td  align="left" class="l-table-edit-td"  width="20%">
				<input name="brif" type="text" requried="false" id="brif" />
			</td>
			 
			<td align="right" class="l-table-edit-td"  width="10%" >状　　态：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="state" type="text"  id="state" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%" >需求计划单号：</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<input name="req_code" type="text" requried="false" id="req_code" />
			</td> 
		</tr>
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
