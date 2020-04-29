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
    var isHideCheck = '${p04015 }' == 1 ? false : true;
    var show_detail=0;
    var renderFunc = {
    		state : function (value){
				 if(value == 1){
					return "未审核";
				}else if(value == 2){
					return "已审核";
				}else if(value == 3){
					return "已确认";
				}
			}
    		
    }
    $(function (){
    	loadDict()//加载下拉框
   	 	//加载数据
   	 	//loadHead(null);	
		loadHotkeys();
		showDetail();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0 ;
		//query();
    });
    //查询
    function  query(){
    	grid.options.parms=[];
		grid.options.newPage=1;
    	//根据表字段进行添加查询条件
	  	grid.options.parms.push({name:'out_dateB',value:$("#out_dateB").val()}); 
	  	grid.options.parms.push({name:'out_dateE',value:$("#out_dateE").val()}); 
	  	
	  	grid.options.parms.push({name:'bus_type_code',value:liger.get("bus_type_code").getValue()}); //== "" ? 28: liger.get("bus_type_code").getValue()
	  	grid.options.parms.push({name:'confirm_dateB',value:$("#confirm_dateB").val()}); 
	  	grid.options.parms.push({name:'confirm_dateE',value:$("#confirm_dateE").val()}); 
	  	
    	grid.options.parms.push({name:'store_id',value : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'dept_id',value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'state',value : liger.get("state").getValue() == null ? "" : liger.get("state").getValue()}); 

		grid.options.parms.push({name : 'out_no',value : $("#out_no").val()}); 
		grid.options.parms.push({name:'brief',value:$("#brief").val()});
		if(show_detail == 1){
/* 	  		grid.options.parms.push({name:'inv_code',value:liger.get("inv_code").getText().split(" ")[0]}); */
            grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()});
	  		grid.options.parms.push({name:'batch_no',value:$("#batch_no").val()});
	  		grid.options.parms.push({name:'bar_code',value:$("#bar_code").val()});
	  		grid.options.parms.push({name:'sup_id',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]}); 
	  		grid.options.parms.push({name:'sup_no',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1]}); 
		}
		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    //是否显示复选框
    function isCheckDisplay(rowdata){
       	if(rowdata.out_id == null) return false;
         return true;
    }
    
    function loadHead(){
    	if(show_detail=="1"){
    		grid = $("#maingrid").ligerGrid({
    			columns: [{
    					display: '出库单号', name: 'out_no', align: 'left', width: 130,
    					render : function(rowdata, rowindex, value) {
    						if(value == '合计'){
    							return value ; 
    						}
    						return '<a href=javascript:openUpdate("' 
       							+ rowdata.group_id
       							+ ',' + rowdata.hos_id 
       							+ ',' + rowdata.copy_code
       							+ ',' + rowdata.out_id
       							+ ',' + rowdata.store_id
       							+ '")>'+rowdata.out_no+'</a>';
       					}
    				}, 
    				{ display: '摘要', name: 'brief', align: 'left',minWidth:200},
                    { display: '仓库', name: 'store_name', align: 'left', width: '120'},
                    { display: '领用科室', name: 'dept_name', align: 'left',minWidth: 120},
                    { display: '材料编码', name: 'inv_code', align: 'left', width: '120'},
     		 		{ display: '材料名称', name: 'inv_name', align: 'left', width: '120'},
     		 		{ display: '计量单位', name: 'unit_name', align: 'left', width: '60'},
     		 		{ display: '规格型号', name: 'inv_model', align: 'left', width: '120'},
     		 		{ display: '单价', name: 'price', align: 'right', width: '80',
     		 			render: function (rowdata, rowindex, value) {
 							return formatNumber(value, '${p04006 }', 1);
 						}
     		 		},
     		 		{ display: '数量', name: 'amount', align: 'right', width: '80'},
     		 		{ display: '金额', name: 'amount_money', align: 'right', width: '100',
     		 			render : function(rowdata, rowindex, value) {
     						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
     					}
     		 		},
     		 		{ display: '批号', name: 'batch_no', align: 'left', width: '80'},
     		 		{ display: '条形码', name: 'bar_code', align: 'left', width: '80'},
     		 		{ display: '供应商', name: 'sup_name', align: 'left', width: '120'},
     		 		{ display: '生产厂商', name: 'fac_name', align: 'left', width: '80'},
                  
     		 	   	{ display: '状态', name: 'state', align: 'left',width: '80',editor: { type: 'select',data: matOutMain_state.Rows},render: fieldTypeRender},
                    { display: '业务类型', name: 'bus_type_name', align: 'left',minWidth: 80},
                    
                    { display: '领料员', name: 'emp_name', align: 'left',minWidth: 80},
                    { display: '编制日期', name: 'out_date', align: 'left',minWidth: '90'},
                    { display: '制单人', name: 'user_name_make', align: 'left',minWidth: '80'},
                    { display: '确认日期', name: 'confirm_date', align: 'left',minWidth: '90'},	
                    { display: '出库确认人', name: 'user_name_confirmer', align: 'left',minWidth: '80'}
                    /* { display: '状态', name: 'state', align: 'left',minWidth: '80',editor: { type: 'select',data: matOutMain_state.Rows},render: fieldTypeRender} */
    		 	],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAffiOutBackCommon.do?isCheck=true&show_detail=1',
    			width: '100%', height: '100%', checkbox: false,rownumbers:true,
    			delayLoad: true,//初始化不加载，默认false
    			checkBoxDisplay:isCheckDisplay,
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'query', click: query,icon:'search' },
    				{ line:true },
    				{ text: '添加（<u>I</u>）', id:'add', click: add_open, icon:'add' },
    				{ line:true },
    				{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
    				{ line:true }, 
    				{ text: '审核（<u>A</u>）', id:'audit', click: audit, icon:'audit', hide: isHideCheck },
    				{ line:true, hide: isHideCheck },
    				{ text: '销审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit', hide: isHideCheck },
    				{ line:true, hide: isHideCheck },
    				{ text: '复制（<u>C</u>）', id:'copy', click: copy, icon:'copy' },
    				{ line:true }, 
    				{ text: '退库确认（<u>R</u>）', id:'confirm', click: confirmData,icon:'account' },
    				{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
    				{ line:true } ,
    				{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
    			]}
    		});
    	}else{
    		grid = $("#maingrid").ligerGrid({
    			columns: [{
    					display: '出库单号', name: 'out_no', align: 'left', width: 130,
    					render : function(rowdata, rowindex, value) {
    						if(value == '合计'){
    							return value ; 
    						}
       						return '<a href=javascript:openUpdate("' 
       							+ rowdata.group_id
       							+ ',' + rowdata.hos_id 
       							+ ',' + rowdata.copy_code
       							+ ',' + rowdata.out_id
       							+ ',' + rowdata.store_id
       							+ '")>'+rowdata.out_no+'</a>';
       					}
    				}, 
    				{ display: '摘要', name: 'brief', align: 'left',minWidth:200},
                    { display: '仓库', name: 'store_name', align: 'left'},
                    { display: '业务类型', name: 'bus_type_name', align: 'left',minWidth: 80},
                    { display: '领用科室', name: 'dept_name', align: 'left',minWidth: 120},
                    { display: '领料员', name: 'dept_emp_name', align: 'left',minWidth: 80},
                    { display: '金额', name: 'amount_money', align: 'right',minWidth:100,
                   	 render : function(rowdata, rowindex, value) {
         					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
         				}
                    },
                    { display: '编制日期', name: 'out_date', align: 'left',minWidth: '90'},
                    { display: '制单人', name: 'maker_name', align: 'left',minWidth: '80'},
                    { display: '确认日期', name: 'confirm_date', align: 'left',minWidth: '90'},	
                    { display: '出库确认人', name: 'confirmer_name', align: 'left',minWidth: '80'},
                   	{ display: '状态', name: 'state', align: 'left',width: '80',editor: { type: 'select',data: matOutMain_state.Rows},render: fieldTypeRender},
    		 	],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAffiOutBackCommon.do?isCheck=true&show_detail=0',
    			width: '100%', height: '100%', checkbox: true,rownumbers:true,
    			delayLoad: true,//初始化不加载，默认false
    			checkBoxDisplay:isCheckDisplay,
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'query', click: query,icon:'search' },
    				{ line:true },
    				{ text: '添加（<u>I</u>）', id:'add', click: add_open, icon:'add' },
    				{ line:true },
    				{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
    				{ line:true }, 
    				{ text: '审核（<u>A</u>）', id:'audit', click: audit, icon:'audit', hide: isHideCheck },
    				{ line:true, hide: isHideCheck },
    				{ text: '销审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit', hide: isHideCheck },
    				{ line:true, hide: isHideCheck },
    				{ text: '复制（<u>C</u>）', id:'copy', click: copy, icon:'copy' },
    				{ line:true },
    				/* { text: '冲销（<u>V</u>）', id:'offSet', click: offSet, icon:'offset' },
    				{ line:true }, */
    				{ text: '退库确认（<u>R</u>）', id:'confirm', click: confirmData,icon:'account' },
    				{ line:true },
    				{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
    				{ line:true } ,
    				{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
    			]}
    		});
    	}
    	

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function fieldTypeRender(r, i, value){
        for (var i = 0, l = matOutMain_state.Rows.length; i < l; i++){
            var o = matOutMain_state.Rows[i];
            if (o.id == value) return o.text;
        }
        return "未审核";
    }
  	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);//查询
		hotkeys('I', add);//添加
		hotkeys('D', remove);//删除
		hotkeys('A', audit);//审核
		hotkeys('U', unAudit);//取消审核
		hotkeys('C', copy);//复制
		//hotkeys('V', offSet);//冲销
		hotkeys('R', confirmData);//出库确认
		//hotkeys('P', printDate);//模板打印
	}
    function loadDict(){
		//字典下拉框
		//autocomplete("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true,{is_com : '1'},false,null,"180");
		autocomplete("#store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_com : '1',read_or_write:1},false,null,"180");
		//autocomplete("#dept_code", "../../queryMatDept.do?isCheck=false", "id", "text", true, true,{is_last : '1'}, false,null,"200");
		autocomplete("#dept_code", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true,{is_last : '1',read_or_write:1}, false,null,"200");
		autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes:'30'},true,'28',"180");
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);//供应商

		autoCompleteByData("#state", matOutMain_state.Rows, "id", "text", true, true,null,null,null,"180");
		autodate("#out_dateB", "yyyy-mm-dd", "month_first");
        autodate("#out_dateE", "yyyy-mm-dd", "month_last");
        
        $("#out_dateB").ligerTextBox({width:90});
        $("#out_dateE").ligerTextBox({width:90});
        $("#confirm_dateB").ligerTextBox({width:90});
        $("#confirm_dateE").ligerTextBox({width:90});
        $("#store_code").ligerTextBox({width:180});
        $("#dept_code").ligerTextBox({width:180});
        $("#bus_type_code").ligerTextBox({width:180});
        $("#state").ligerTextBox({width:180});
        $("#out_no").ligerTextBox({width : 219});
        $("#batch_no").ligerTextBox({width : 219});
      /*   autocomplete("#inv_code", "../../queryMatInv.do?isCheck=false", "id", "text", true, true, "", false, false, "", "", "180"); */
        $("#inv_code").ligerTextBox({width:180});
        $("#sup_code").ligerTextBox({width:180});
        $("#brief").ligerTextBox({width:180});
        $("#bar_code").ligerTextBox({width:220});
	}  
    
    //添加
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '代销材料退库单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/affi/outback/matAffiOutBackCommonAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true,initShowMax:true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    	 
	}
    //修改
    function openUpdate(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"out_id="+vo[3] +"&"+ 
			"store_id="+vo[4];
		parent.$.ligerDialog.open({
			title: '代销材料退库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/affi/outback/matAffiOutBackCommonUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, initShowMax:true,isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
    }
    
    //删除	
    function remove(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
			var ParamVo =[];
			var out_nos = "";
			$(data).each(function (){		
				if(this.state > 1){
					out_nos = out_nos + this.out_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.out_id 
				) 
			});
			if(out_nos != ""){
				$.ligerDialog.error("删除失败！请选择未审核的单据！");
				//$.ligerDialog.error("删除失败！"+out_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMatAffiOutBackCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
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
			var ParamVo =[];
			var out_nos = "";
			$(data).each(function (){		
				if(this.state > 1){
					out_nos = out_nos + this.out_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.out_id 
				) 
			});
			if(out_nos != ""){
				$.ligerDialog.error("审核失败！请选择未审核的单据！");
				//$.ligerDialog.error("审核失败！"+out_nos+"单据不是未审核状态!");
				return;
			}
			$.ligerDialog.confirm('确定审核吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMatAffiOutBackCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    //取消审核
    function unAudit(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要取消审核的单据！');
			return;
		}else{
			var ParamVo =[];
			var out_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					out_nos = out_nos + this.out_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.out_id 
				) 
			});
			if(out_nos != ""){
				$.ligerDialog.error("销审失败！请选择已审核单据！");
				//$.ligerDialog.error("销审失败！"+out_nos+"单据不是审核状态!");
				return;
			}
			$.ligerDialog.confirm('确定销审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("unAuditMatAffiOutBackCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
    }
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
 		head=head+"<tr><td>编制日期："+$("#out_dateB").val() +" 至  "+ $("#out_dateE").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		
    }
    //复制
    function copy(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要复制的行!');
			return;
		}else{
			var ParamVo =[];
			var out_nos = "";
			$(data).each(function (){		
				/* if(this.state != 3){
					out_nos = out_nos + this.out_no + ",";
				} */
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.out_id 
				) 
			});
			//if(out_nos != ""){
				//$.ligerDialog.error("复制失败！请选择出库确认状态单据！");
				//$.ligerDialog.error("复制失败！"+out_nos+"单据不是出库确认状态!");
				//return;
			//}
			$.ligerDialog.confirm('确定要复制单据吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("copyMatAffiOutBackCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
    }
    //冲销
  /*   function offSet(){
    	var data = gridManager.getCheckedRows();
    	if (data.length == 0){
			$.ligerDialog.error('请选择要冲销的单据！');
			return ;
		}else{
			if(data.length > 1){
				$.ligerDialog.error('只能单张冲账,请重新选择！');
				return ;
			}else{
				var ParamVo = [];
				var out_nos = "";
				var out_noM = "";
				$(data).each(function(){	
					if(this.state != 3){
						out_nos = this.out_no +",";
					}
					if(this.bus_type_code != 28 && this.bus_type_code != 30 && this.bus_type_code != 33 && this.bus_type_code != 34 ){
						out_noM = this.out_no +",";
					} 
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.out_id 
					) 
				});
				//判断是否是出库单据
				if(out_nos != ""){
					$.ligerDialog.error("冲销失败！请选择出库确认状态单据！");
					//$.ligerDialog.error("冲销失败！"+out_nos+"单据不是出库确认状态");
					return;
				}
				//判断单据类型是否能冲销
				if(out_noM !="" ){
					$.ligerDialog.error("冲销失败！"+out_noM+"单据的业务类型不能冲销！");
					return;
				}
				$.ligerDialog.confirm('确定要冲销吗?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("offsetMatAffiOutCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				});
			}
				
		}
    } */
    //出库确认	
    function confirm(){
    	var is_store='${p04045 }';
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要出库确认的单据!');
			return;
		}else{
			var todayDate = new Date();
			var todayYear = todayDate.getFullYear();
			var todayMonth = todayDate.getMonth() + 1;
			var todayDate = todayDate.getDate();
			todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
			todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
			var today = todayYear + '-' + todayMonth + '-' + todayDate;
			if('${p04047 }'==0){
				confirmDate(today);
			}else{
				$.ligerDialog.open({
					content: "确认日期:<input id='confirmDate' value=" + today + " style='text-align:center; border: 1px solid blue; height: 18px;' onFocus='WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd\"})' />",
					width: 300,
					height: 150,
					buttons: [
						{ text: '确定', onclick: function (item, dialog) {
							var dd = $("#confirmDate").val();
							if (dd) {
								dialog.close();
								confirmDate(dd);
							}
						}},
		                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
				]
				})
				
			}
		}
	}
    function confirmData(){
    	
    	var is_store='${p04045 }';
    	var data = gridManager.getCheckedRows();
    	if (data.length == 0){
			$.ligerDialog.error('请选择要出库确认的单据!');
			return;
		}
		var ParamVo =[];
		var out_nos = "";
		
		/* 1.开始   说明：后期可用于维护用户输入日期操作 */
		var todayDate = new Date();
		var todayYear = todayDate.getFullYear();
		var todayMonth = todayDate.getMonth() + 1;
		var todayDate = todayDate.getDate();
		todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
		todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
		var confirmDate = todayYear + '-' + todayMonth + '-' + todayDate; 
		/* 1.结束 */
		
		$(data).each(function (){		
			if(isHideCheck){
           		//不使用审核功能需要判断是否未审核
           		if(this.state != 1){out_nos = out_nos + this.out_no + ",";}
           	}else{
           		//使用审核功能需要判断是否已经审核
           		if(this.state != 2){out_nos = out_nos + this.out_no + ",";}
           	}
			
			ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+ 
				this.out_id  +"@"+ 
				this.out_date + "@" + //confirmDate+ "@" +
				is_store+ "@" +this.store_id+"@"+this.out_no
			) 
		});
		
		if(isHideCheck){
            if(out_nos != ""){
				$.ligerDialog.error("确认出库失败！"+out_nos+"单据不是未审核状态");
				return;
			}
           }else{
            if(out_nos != ""){
				$.ligerDialog.error("确认出库失败！"+out_nos+"单据不是审核状态");
				return;
			}
           }
		
		$.ligerDialog.confirm('确定出库确认?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("/CHD-HRP/hrp/mat/storage/in/verifyMatClosingDate.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
					if (responseData.state == "true") {
						ajaxJsonObjectByUrl("confirmMatAffiOutBackCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				},false);
			}
		}); 
		
	}
    //打印设置
	function printSet(){
		
		var useId=0;//统一打印
		
		var useId=0;//统一打印
		if('${p04024 }'==1){
			//按用户打印
			useId='${user_id }';
		}
		
		officeFormTemplate({template_code:"04020",use_id:useId});
		
		/* parent.parent.$.ligerDialog.open({url : 'hrp/mat/affi/out/affiOutPrintSetPage.do?template_code=04020&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		}); */
	}
	 
    //打印
    function print(){
    	
    	 var useId=0;//统一打印
 		if('${p04024 }'==1){
 			//按用户打印
 			useId='${user_id }';
 		}

    	//if($("#create_date_b").val())
 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var out_id ="" ;
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				
				out_id  += this.out_id+","
					
			});
			 /* var para={
	    			paraId :out_id.substring(0,out_id.length-1) ,
	    			
	    			template_code:'04020',
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	}; 
		 	
			//alert(JSON.stringify(para));
			
	    	printTemplate("hrp/mat/affi/out/queryMatAffiOutByPrintTemlate.do?isCheck=false",para); */
	    	
			 var para={
	    				paraId :out_id.substring(0,out_id.length-1) ,
		    			template_code:'04020',
		    			class_name:"com.chd.hrp.mat.serviceImpl.affi.out.MatAffiOutBackCommonServiceImpl",
		    			method_name:"matAffiOutCommonTemplate",
		    			isPrintCount:false,//更新打印次数
		    			isPreview:true,//预览窗口，传绝对路径
		    			use_id:useId,
		    			p_num:1
		    	}; 
			 	
			officeFormPrint(para);
		}
    	
    }
    
    function showDetail(){
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if(show_detail==0){
			$("#batch_no").val();
		}
		loadHead();
		query();
	 }
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border=0>
		 
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">编制日期：</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="out_dateB" id="out_dateB" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="out_dateE" id="out_dateE" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
				
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%" style="padding-left: 10px;">业务类型：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input	name="bus_type_code" type="text" id="bus_type_code" required="false" ltype="text"	validate="{required:false,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td" width="10%" style="padding-left: 10px;">状态：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input	name="state" type="text" id="state" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">出库日期：</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="confirm_dateB" id="confirm_dateB" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="confirm_dateE" id="confirm_dateE" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%" style="padding-left: 10px;">仓库：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input	name="store_id" type="text" id="store_id" required="false" ltype="text"	validate="{required:false,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td" width="10%" style="padding-left: 10px;">领料科室：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input	name="dept_code" type="text" id="dept_code" required="false" ltype="text"	validate="{required:false,maxlength:100}" />
			</td>
			
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">单据号：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="out_no" type="text" id="out_no" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
			<td align="right" class="l-table-edit-td" width="10%">摘要：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td  demo" width="10%">批号：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<input name="batch_no" type="text" id="batch_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td  demo" width="10%">供应商：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%">
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
            </td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td  demo" width="10%">条形码：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<input name="bar_code" type="text" id="bar_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
		</tr>
	</table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>

				<tr>
					<th width="200">单据ID</th>
					<th width="200">出库单号</th>
					<th width="200">年份</th>
					<th width="200">月份</th>
					
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
