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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var renderFunc = {
			 
    		is_req:function(value){//是否已生成需求计划
				if(value == 1){
 					return "是";	
 				}else if(value == 0){
 					return "否";
 				} 
			} , 
			rela_state:function(value){//处理状态
				if(value == 1){
 					return "待处理";	
 				}else if(value == 2){
 					return "部分完成";
 				}else if(value == 3){
 					return "全部完成";
 				}else if(value == 0){
 					return "待处理+部分完成";
 				}
			} ,
			
			left_state:function(value){//处理状态
				if(value == 1){
 					return "库存满足";	
 				}else if(value == 2){
 					return "部分满足";
 				}else if(value == 3){
 					return "库存不足";
 				}
			} 
	}; 
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
		loadHotkeys();
		query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_app_date',
			value : $("#begin_app_date").val()
		});
		grid.options.parms.push({
			name : 'end_app_date',
			value : $("#end_app_date").val()
		}); 
		grid.options.parms.push({
			name : 'begin_send_date',
			value : $("#begin_send_date").val()
		});
		grid.options.parms.push({
			name : 'end_send_date',
			value : $("#end_send_date").val()
		}); 
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("apply_dept").getValue() == null ? "" : liger.get("apply_dept").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'app_emp',
			value : liger.get("app_emp").getValue() == null ? "" : liger.get("app_emp").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'response_emp',
			value : liger.get("response_emp").getValue() == null ? "" : liger.get("response_emp").getValue().split(",")[0]
		});
	
		grid.options.parms.push({
			name : 'brief',
			value : $("#brief").val()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'do_state',
			value : liger.get("do_state").getValue() == null ? "" : liger.get("do_state").getValue()
		}); 
		grid.options.parms.push({
			name : 'apply_no',
			value : $("#apply_no").val()
		}); 
		grid.options.parms.push({
			name : 'is_req',
			value : liger.get("is_req").getValue() == null ? "" : liger.get("is_req").getValue()
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
		 			display: '库存状态', name: 'left_state', align: 'left', width: '90',
		 			render: function(rowdata, rowindex, value){
		 				if(value == 1){
		 					return "库存满足";	
		 				}else if(value == 2){
		 					return "部分满足";
		 				}else if(value == 3){
		 					return "库存不足";
		 				}
		 			}
		 			
		 		}, { 
					display: '申请单号', name: 'apply_no', align: 'left', width: '150',
					render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.apply_id
							+ ',' + rowdata.rela_state
							+ '")>'+rowdata.apply_no+'</a>';
					}
				}, { 
		 			display: '申请日期', name: 'app_date', align: 'left', width: '130',
		 		}, { 
		 			display: '摘要', name: 'brief', align: 'left', minWidth: '150',
		 		}, { 
		 			display: '响应仓库', name: 'store_name', align: 'left', minWidth: '120',
		 		}, { 
		 			display: '申请部门', name: 'dept_name', align: 'left', minWidth: '120',
		 		}, {  
		 			display: '申请人', name: 'emp_name', align: 'left', width: '90',
		 		}, { 
		 			display: '发送日期', name: 'send_date', align: 'left', width: '90',
		 		}, { 
		 			display: '处理状态', name: 'rela_state', align: 'left', width: '90',
		 			render: function(rowdata, rowindex, value){
		 				if(value == 1){
		 					return "待处理";	
		 				}else if(value == 2){
		 					return "部分完成";
		 				}else if(value == 3){
		 					return "全部完成";
		 				}else if(value == 0){
		 					return "待处理+部分完成";
		 				}
		 			}
		 		}, { 
		 			display: '已生成单据', align: 'left', width: '90',
		 			render : function(rowdata, rowindex, value) {
						return '<a href=javascript:rela_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.apply_id
							+ ',' + rowdata.apply_no
							+ '")>查看</a>';
					}
		 		}, { 
		 			display: '是否已生成需求计划', name: 'is_req', align: 'left', minWidth: '140',
		 			render: function(rowdata, rowindex, value){
		 				if(value == 0){
		 					return "否";	
		 				}else if(value == 1){
		 					return "是";
		 				}
		 			}
		 		} ],
	 		rowAttrRender:function(rowdata,rowid){
	 			if(rowdata.left_state==3){
	 				return "style='background:rgba(255, 0, 0, 0.31)'"
	 			}else if(rowdata.left_state==1){
	 				return "style='background:#8BC34A'"
	 			}else if(rowdata.left_state==2){
	 				return "style='background:rgba(156, 39, 176, 0.69)'"
	 			}
 			},
 			alternatingRow:false,
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedCommonOutApplyCheck.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			enabledSort : true,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '批量生成出库单（<u>O</u>）', id:'createOut', click: createOut, icon:'settle' },
				{ line:true },
				{ text: '汇总生成出库单（<u>H</u>）', id:'createOutCollect', click: createOutCollect, icon:'settle' },
				{ line:true },  
				{ text: '退回科室（<u>B</u>）', id:'back', click: back, icon:'back' },
				{ line:true } ,
				{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
				{ line:true } ,
				{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
			]}, 
			onDblClickRow : function (rowdata, rowindex, value){
				update_open(
					rowdata.group_id + "," + 
					rowdata.hos_id + "," + 
					rowdata.copy_code + "," + 
					rowdata.apply_id + "," + 
					rowdata.rela_state
				);
			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>申请日期："+$("#begin_app_date").val() +" 至  "+ $("#end_app_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="科室申领审核";
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('O', createOut);
		hotkeys('H', createOutCollect);
		hotkeys('B', back);
	}
    
    //修改
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"apply_id="+vo[3] +"&"+ 
			"rela_state="+vo[4] ;
		
		parent.$.ligerDialog.open({
			title: '申请单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/storage/out/applycheck/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
    
    //修改
    function rela_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"apply_id="+vo[3] +"&"+ 
			"apply_no="+vo[4] ;
		
		parent.$.ligerDialog.open({
			title: '已生成单据列表',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/storage/out/applycheck/relaPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
    
    //批量生成出库单
	function createOut(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var apply_nos = "";
			$(data).each(function (){		
				if(this.rela_state != 1){
					apply_nos = apply_nos + this.apply_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.apply_id 
				) 
			});
			if(apply_nos != ""){
				$.ligerDialog.error("生成出库单失败！"+apply_nos+"单据不是未处理状态");
				return;
			}
			$.ligerDialog.confirm('确定生成出库单?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("addOutMedCommonOutApplyCheckBatch.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
    //批量生成出库单
	function createOutCollect(){
		var data = gridManager.getCheckedRows();//获取选中数据
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return ; 
		}
		
		var strs = '';//判断是否库存不足,用于存储申请单号
		var ParamVo =[];
		var apply_nos = "";
		var store_dept = "";
		var is_create = true;
		$(data).each(function (index){		
			if(this.left_state == 3){
				strs = strs + this.apply_no + ",";
			}
			
			if(this.rela_state != 3){
				apply_nos = apply_nos + this.apply_no + ",";
			}
			
			
			if(index == 0){
				store_dept = this.store_id+this.dept_id;
			}else{
				if(store_dept != this.store_id + this.dept_id){
					is_create = false;
					return false;
				}
			}
			ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+ 
				this.apply_id  +"@"+ 
				this.store_id 
			) 
		}); 
		
		if(strs != ''){
			$.ligerDialog.warn('单据[' + strs.substring(0,strs.length-1)+']库存不足,不能生成出库单');
			return ; 
		}
		if(!is_create){
			$.ligerDialog.warn("生成出库单失败！所选单据响应库房与领用科室需一致");
			return;
		}
		if(apply_nos == ""){
			$.ligerDialog.warn("生成出库单失败！所选单据都是全部完成状态");
			return;
		}
		
		parent.$.ligerDialog.open({
			title: '汇总生成出库单',
			height: $(window).height()-50,
			width: $(window).width()-100,
			url: 'hrp/med/storage/out/applycheck/addOutMedCommonOutApplyCheckCollect.do?isCheck=false&para=' + ParamVo.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
			
	}
	
    //退回
	function back(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var apply_nos = "";
			$(data).each(function (){		
				if(this.rela_state > 1){
					apply_nos = apply_nos + this.apply_no + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.apply_id 
				) 
			});
			if(apply_nos != ""){
				$.ligerDialog.error("操作失败！<br>以下单据存在已处理数据不允许退回：<br>"+apply_nos);
				return;
			}
			
			$.ligerDialog.prompt('退回原因', true, function (yes, value){
				if(yes){
					var paras = {
						back_reason : value,
						ids : ParamVo.toString()
					}
					ajaxJsonObjectByUrl("backMedCommonOutApplyCheck.do?",paras,function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	  //打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		//if('${sessionScope.med_para_map["08017"] }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		/* }else if('${sessionScope.med_para_map["08017"] }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		} */
		/* parent.$.ligerDialog.open({url : 'hrp/med/storage/out/applycheck/storageOutPrintSetPage.do?template_code=08013&use_id='+useId,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
    		}); */
    	officeFormTemplate({template_code:"08013",useId:useId});
    }

  //打印
    function print(){
    	
    	 var start_date = $("#begin_app_date").val() + " 00:00"; 
    	 var end_date = $("#end_app_date").val() + " 00:00"; 
    	 start_date = new Date(start_date.replace(/-/g, "/")); 
    	 end_date = new Date(end_date.replace(/-/g, "/")); 
    	 if(start_date.getMonth() != end_date.getMonth()) { 
    		  $.ligerDialog.error("不支持跨月打印！"); 
    	      return false;  
    	 } 
    	 
    	 var useId=0;//统一打印
 		//if('${sessionScope.med_para_map["08013"] }'==1){
 			//按用户打印
 			useId='${sessionScope.user_id }';
 		/* }else if('${sessionScope.med_para_map["08013"] }'==2){
 			//按仓库打印
 			if(liger.get("store_code").getValue()==""){
 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
 				return;
 			}
 			useId=liger.get("store_code").getValue().split(",")[0];
 		}
 */
    	//if($("#create_date_b").val())
 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var apply_id ="" ;
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				
				apply_id  += this.apply_id+","
					
			});
			
// 			if(in_nos != ""){
// 				$.ligerDialog.error("打印失败！<br>以下单据不是已审核状态：<br>"+in_nos);
// 				return;
// 			} 
			
			
			/*  var para={
	    			paraId :apply_id.substring(0,apply_id.length-1) ,
	    			
	    			template_code:'08013',
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	};  */
		 	
			//alert(JSON.stringify(para));
			
	    	//printTemplate("hrp/med/storage/out/applycheck/queryMedOutByPrintTemlate.do?isCheck=false",para);
	    	
	    	
	    	var para={
	    			template_code:'08013',
	    			class_name:"com.chd.hrp.med.serviceImpl.storage.out.MedCommonOutApplyCheckServiceImpl",
	    			method_name:"queryMedOutByPrintPage",
	    			//isSetPrint:flag,//是否套打，默认非套打
	    			isPreview:true,//是否预览，默认直接打印
					paraId :apply_id.substring(0,apply_id.length-1),
	    			use_id:useId,
	    			p_num:1
	    	};
	    	
	    	officeFormPrint(para);
	    	
		}
    	
    }
    
    function loadDict(){
		//字典下拉框
		autocomplete("#apply_dept", "../../../queryMedDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last: 1,read_or_write:'1'});
		autoCompleteByData("#do_state", medApplyMain_storeCheckState.Rows, "id", "text", true, true,null,null,'1');
		autocomplete("#store_code", "../../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_read:'1'});
		
		var para={dept_id : liger.get("apply_dept").getValue() == "" ? "" : liger.get("apply_dept").getValue().split(",")[0]};
		autocomplete("#app_emp", "../../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true, para, false);
		autocomplete("#response_emp", "../../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true, para, false);
 
		autoCompleteByData("#is_req", yes_or_no.Rows, "id", "text", true, true,null,null,'0');
        $("#begin_app_date").ligerTextBox({width:100});
        autodate("#begin_app_date", "yyyy-mm-dd", "month_first");
        $("#end_app_date").ligerTextBox({width:100});
        autodate("#end_app_date", "yyyy-mm-dd", "month_last");
        $("#begin_send_date").ligerTextBox({width:100});
        autodate("#begin_send_date", "yyyy-mm-dd", "month_first");
        $("#end_send_date").ligerTextBox({width:100});
        autodate("#end_send_date", "yyyy-mm-dd", "month_last");
        $("#brief").ligerTextBox({width:238});
        $("#apply_no").ligerTextBox({width : 238});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	申请日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="begin_app_date" id="begin_app_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_app_date" id="end_app_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				申请科室：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="apply_dept" type="text" id="apply_dept" ltype="text" validate="{required:false}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				申请人：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="app_emp" type="text" id="app_emp" ltype="text" validate="{required:false}" />
            </td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td"  width="10%">
            	发送日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="begin_send_date" id="begin_send_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_send_date" id="end_send_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
        	<td align="right" class="l-table-edit-td" >
            	响应库房：
            </td>
            <td align="left" class="l-table-edit-td" >
				<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
	        </td>
			<td align="right" class="l-table-edit-td" >
				状态：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="do_state" type="text" id="do_state" ltype="text" validate="{required:false}" />
            </td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td" >
				单据号：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="apply_no" type="text" id="apply_no" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td" >
				需求状态：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_req" type="text" id="is_req" ltype="text" validate="{required:false}" />
            </td>
            <td align="right" class="l-table-edit-td"  width="10%">
				响应人：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="response_emp" type="text" id="response_emp" ltype="text" validate="{required:false}" />
            </td>
        </tr>
        <tr>
			<td align="right" class="l-table-edit-td" >
				摘要：
			</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        	
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
