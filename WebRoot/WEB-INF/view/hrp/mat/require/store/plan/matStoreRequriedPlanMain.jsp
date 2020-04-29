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
		                 { display: '需求计划单号', name: 'req_code', align: 'left',minWidth:120,
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
						 { display: '计划来源', name: 'field_desc', align: 'left',minWidth:80 },
		                 { display: '响应仓库', name: 'store_name', align: 'left',minWidth:150 },
						 { display: '编制部门', name: 'dept_name', align: 'left',minWidth:150 },
						 { display: '摘要', name: 'brif', align: 'left',minWidth:180 },
						 { display: '编制人', name: 'maker', align: 'left',minWidth:100},
						 { display: '编制日期', name: 'make_date', align: 'left',minWidth:80},
						 { display: '审核人', name: 'checker', align: 'left' ,minWidth:80,hide:isHideCheck},
						 { display: '审核日期', name: 'check_date', align: 'left' ,minWidth:80,hide:isHideCheck},
						 { display: '状态', name: 'state', align: 'left',minWidth:80,
							 render : function(rowdata, rowindex,value) {
									if(rowdata.state == 0){
										return "中止计划";
									}else if(rowdata.state == 1){
										return "未提交";
									}else if(rowdata.state == 2){
										return "已提交";
									}else if(rowdata.state == 3){
										return "已审核";
									}
								}
						 },
						 { display: '其他需求物资', name: 'other_inv', align: 'left',minWidth:120 },
						 { display: '退回原因', name: 'return_reason', align: 'left' ,minWidth:120}
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStoreRequriedPlan.do?isCheck=true',
		                 width: '100%', height: '100%', checkbox: true, rownumbers : true,delayLoad:true,isScroll:true,
		                 selectRowButtonOnly:true,
		                 toolbar: { items: 
		                	 	[
		                          	{ text: '查询（<u>Q</u>）', id:'query', click: query ,icon:'search' },
		                            { line:true },
		                            {text: '添加（<u>O</u>）', id:'openAdd', click: openAdd ,icon:'add' },
		                            { line:true },
		                            {text: '删除（<u>D</u>）', id:'del', click: del ,icon:'delete' },
		                            { line:true },
		                            {text: '提交（<u>S</u>）', id:'submit', click: submit ,icon:'up' },
		                            { line:true },
		                            {text: '取消提交（<u>U</u>）', id:'submitCancle', click: submitCancle ,icon:'edit' },
		                            { line:true },
		                            {text: '审核（<u>A</u>）', id:'audit', click: audit ,icon:'audit' ,hide: isHideCheck},
		                            { line:true ,hide: isHideCheck},
		                            {text: '取消审核（<u>U</u>）', id:'unaudit', click: unaudit ,icon:'unaudit' ,hide: isHideCheck},
		                            { line:true ,hide: isHideCheck},
		                            /* {text: '退回仓库（<u>R</u>）', id:'ret', click: ret ,icon:'back' },
		                            { line:true }, */
		                            {text: '引入科室需求计划（<u>G</u>）', id:'dept_imp', click: dept_imp ,icon:'add' },
			        				{ line:true } ,
			        				{ text: '批量打印', id:'printDetail', click: printDetail, icon:'print' },
			        				{ line:true } ,
			                        { text: '模板设置', id:'printSetDetail', click: printSetDetail, icon:'settings' },
			        				{ line:true },
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
	
	function printSetDetail(){
		  
		var useId=0;//统一打印
			//按用户打印
		 
		officeFormTemplate({template_code:"04012",use_id:useId});
	}
	
	//打印
	function printDetail(){
		//按用户打印
		var  useId=0;
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
				class_name:"com.chd.hrp.mat.serviceImpl.requrie.store.MatStoreRequirePlanServiceImpl",
				method_name:"printMatStoreRequireMainData",
				req_code: req_code.substring(0,req_code.length-1) ,
				template_code:'04012',
				isPrintCount:false,//更新打印次数
				isPreview:true,//预览窗口，传绝对路径
				use_id:useId,
			}; 
			//printTemplate("hrp/mat/purchase/make/queryMatMakeByDetailPrintTemlate.do?isCheck=false",para);
			officeFormPrint(para);
		}
		 
	}
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);//查询
		hotkeys('O', openAdd);//添加
		hotkeys('D', del);//删除
		hotkeys('T', abort);//中止计划
		hotkeys('S', submit);//提交
		hotkeys('U', submitCancle);//取消提交
		hotkeys('C', copy);//复制
		hotkeys('A', audit);//审核
		hotkeys('U', unaudit);//销审
		hotkeys('R', ret);//退回科室
		//hotkeys('P', printData);//打印
	}
	
	function dept_imp(){
		parent.$.ligerDialog.open({
			title: '科室需求计划生成',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/require/store/plan/matStoreRequriedPlanDeptImpPage.do?isCheck=false', 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//打开添加页面
	function openAdd(){
		parent.$.ligerDialog.open({
			title: '仓库需求计划添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/require/store/plan/matStoreRequriedPlanAddPage.do?isCheck=false', 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 		
	}
	
	//修改页面
	function update_open(obj) {
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"req_id="+vo[3] +"&"+ 
			"req_code="+vo[4] ;
		
		var vo = obj.split(",");
		var parm = "group_id="+vo[0]+"&hos_id="+vo[1]+"&copy_code="+vo[2]+"&req_id="+vo[3]+"&req_code="+vo[4]; 
		parent.$.ligerDialog.open({ 
			title: '仓库需求计划修改',
			height: $(window).height(),
			width: $(window).width(),
			url : 'hrp/mat/require/store/plan/matStoreRequriedPlanUpdatePage.do?isCheck=false&' + parm, 
			modal:true,showToggle:false,showMax:true ,showMin: false,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});


	}
	
	//删除
	function del(){
		
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要删除的单据！');
        	return;
        }else{
            var ParamVo =[];
            var req_code = "";
            var req_nos = "";
            $(data).each(function (){	
            	if(this.state != 1  ){
            		req_nos = req_nos + this.req_code + ",";
            	}
				ParamVo.push(
					//表的主键
					this.group_id +"@"+ this.hos_id +"@"+ this.copy_code +"@"+ this.req_id
				)
            });
            
            if(req_nos != ""){
            	$.ligerDialog.error("删除失败！请选择新建单据！");
        		return;
            }
           
            $.ligerDialog.confirm('确定要删除吗?', 
            		function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("deleteMatStoreRequriedPlan.do?isCheck=true",{ParamVo : ParamVo.toString()},
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
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要中止的单据！');
        	return;
        }else{
            var ParamVo =[];
            var req_nos = "";
            $(data).each(function (){	
            	if(this.state != 1 ){
            		req_nos = req_nos + this.req_code + ",";
            	}
				ParamVo.push(
					this.req_id +"@"+ this.req_code  
				)
            });
            
            if(req_nos != ""){
            	$.ligerDialog.error("中止失败！请选择未提交的单据！");
        		return;
            }
            
            $.ligerDialog.confirm('确定要终止吗?', 
            		function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("abortMatStoreRequriedPlan.do?isCheck=true",{ParamVo : ParamVo.toString()},
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
            var req_code ="";
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
            	$.ligerDialog.error('提交失败！请选择未提交的单据！');
        		return;
            }
            
            $.ligerDialog.confirm('确定要提交吗?', 
            	function (yes){
                    if(yes){
                       	ajaxJsonObjectByUrl("submitMatStoreRequriedPlan.do?isCheck=true",{ParamVo : ParamVo.toString()},
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
		//取消提交新建状态（STATE=1）并且已提交的需求计划，修改IS_SUBMIT=0
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要取消提交的单据！');
        	return;
        }else{
            var ParamVo =[];
            var req_nos ="";
            var req_codes = "";
            $(data).each(function (){	
            	if(this.state != 2  ){
            		req_nos = req_nos + ","+ this.req_code;
            	}
            	
				ParamVo.push(
					this.req_id +"@"+ this.req_code  
				)
				
            });
           
            if(req_nos != ""){
            	$.ligerDialog.error("取消提交失败！请选择已提交的单据！");
				return;
            }
            
            $.ligerDialog.confirm('确定要取消提交吗?', 
            	function (yes){
                   	if(yes){
                       	ajaxJsonObjectByUrl("unSubmitMatStoreRequriedPlan.do?isCheck=true",{ParamVo : ParamVo.toString()},
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
				ParamVo.push(
					//表的主键
					this.group_id +"@"+ 
					this.hos_id +"@"+ 
					this.copy_code +"@"+ 
					this.req_id +"@"+ 
					this.req_code+"@"
				)
            });
           
            $.ligerDialog.confirm('确定要复制吗?', 
            	function (yes){
                   	if(yes){
                       	ajaxJsonObjectByUrl("copyMatStoreRequriedPlan.do?isCheck=true",{ParamVo : ParamVo.toString()},
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
            	$.ligerDialog.error("请选择已提交单据！");
				return;
            }
            
            $.ligerDialog.confirm('确定要审核吗?', 
            	function (yes){
                    if(yes){
                        ajaxJsonObjectByUrl("submitMatStoreRequriedConfirm.do?isCheck=true",{ParamVo : ParamVo.toString()},
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
					this.req_id +"@"+ this.req_code  
				)
            });
            
            if(req_codes != ""){
            	$.ligerDialog.error("请选择已审核单据！");
				return;
            }
            
            $.ligerDialog.confirm('确定要取消审核吗?', 
            	function (yes){
                   	if(yes){
                        ajaxJsonObjectByUrl("unSubmitMatStoreRequriedConfirm.do?isCheck=true",{ParamVo : ParamVo.toString()},
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
        		 if(this.state != 1  ){
        			 req_codes = req_codes + this.req_code +",";
              	 }else{
              		reqIds = reqIds + this.req_id +",";
              	 }	
             });
        	 
        	if(req_codes !=""){
        		$.ligerDialog.error("请选择未审核单据！");
            	return;
        	}
        	
         	$.ligerDialog.open({
         		url: 'matDeptRequriedConfirmReturnPage.do?isCheck=false&reqIds='+reqIds, 
         			height: 300,width:450, title:'退回科室',initShowMax:false,
         			modal:true, showToggle:false, showMax : true ,showMin: false, isResize:true,
       				buttons: [ 
       				           { text: '确定', onclick: function (item, dialog) { 
	          				        	   	dialog.frame.saveMatDepartRequriedConfirmReturn();	          				        	   	
	          				        	   	dialog.close(); 
	          				        	   	query();
       				        	    },cls:'l-dialog-btn-highlight' }, 
       				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
       				         ] 
       			});	
		
		}
	}
	
	//打印
	function printData(){
		
	}
	
	//加载字典
	function loadDict() {
			
		autocomplete("#store_code", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_purchase : '1',read_or_write:'1'},false,false,'160',"",200);//仓库
		autocomplete("#dept_code", "../../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true,{is_last : '1',read_or_write:'1'}, false,false,'160',"",200);//科室		
		
		if(isHideCheck == false){
			autoCompleteByData("#state", matRequireMain_state.Rows, "id", "text", true, true,'',false,false,'160');//状态		
		}else{
			autoCompleteByData("#state", matRequireMain_state2.Rows, "id", "text", true, true,'',false,false,'160');//状态	
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
		  
			<td align="right" class="l-table-edit-td" width="10%">响应库房：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="store_code" type="text" requried="true" id="store_code" />
			</td> 
			<td align="right" class="l-table-edit-td" width="10%">编制科室：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="dept_code" type="text" requried="true" id="dept_code" />
			</td> 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">摘　　要：</td>
			<td>
				<table>
					<tr>
						<td align="left" class="l-table-edit-td" >
							<input name="brif" type="text" requried="false" id="brif" />
						</td>
					</tr>
				</table>
			</td>

			<td align="right" class="l-table-edit-td"  width="10%" >状　　态：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="state" type="text" requried="true" id="state" />
			</td> 

			<td align="right" class="l-table-edit-td" width="10%">需求计划单号：</td>
			<td align="left" class="l-table-edit-td" width="20%" >
				<input name="req_code" type="text" requried="false" id="req_code" />
			</td> 
		</tr>
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
