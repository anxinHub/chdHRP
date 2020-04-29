<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var show_detail = 0;
    var renderFunc = {
			  
			amount_money:function(value){//金额
				return formatNumber(value, '${p04005 }', 1);
			},
			 state:function(value){//数量
				 
				if(value==1){
					return "未确认";
				}else if (value == 2){
					return "调出确认";
				}else if (value == 3){
					return "调入确认";
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
    });
    //查询
    function  query(){
		grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'begin_tran_date',value:$("#begin_tran_date").val()}); 
		grid.options.parms.push({name:'end_tran_date',value:$("#end_tran_date").val()}); 
		
		grid.options.parms.push({name:'tran_type',value:liger.get("tran_type").getValue()}); 
		grid.options.parms.push({name:'bus_type',value:liger.get("bus_type").getValue()}); 
		grid.options.parms.push({name:'tran_type',value:liger.get("tran_type").getValue()}); 
		
		grid.options.parms.push({name:'out_hos_id',value:liger.get("out_hos_id").getValue()}); 
		grid.options.parms.push({name:'out_store_id',value:liger.get("out_store_id").getValue().split(",")[0]}); 
		grid.options.parms.push({name:'in_hos_id',value:liger.get("in_hos_id").getValue()}); 
		grid.options.parms.push({name:'in_store_id',value:liger.get("in_store_id").getValue().split(",")[0]}); 
		
		grid.options.parms.push({name:'begin_check_date',value:$("#begin_check_date").val()}); 
		grid.options.parms.push({name:'end_check_date',value:$("#end_check_date").val()}); 
		grid.options.parms.push({name:'begin_confirm_date',value:$("#begin_confirm_date").val()}); 
		grid.options.parms.push({name:'end_confirm_date',value:$("#end_confirm_date").val()}); 

		grid.options.parms.push({name:'tran_no',value:$("#tran_no").val()}); 
		grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
		grid.options.parms.push({
			name : 'brief',
			value : $("#brief").val()
		});
		 grid.options.parms.push({name:'maker',value:liger.get("maker").getValue()});
		 grid.options.parms.push({name:'in_confirmer',value:liger.get("in_confirmer").getValue()});
		 grid.options.parms.push({name:'out_confirmer',value:liger.get("out_confirmer").getValue()});
		  grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
		grid.options.parms.push({name : 'print_state',value : $("#print_state").val()}); 
		
		grid.options.parms.push({name:'inv_info',value:$("#inv_info").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	
    	if(show_detail=="0"){
    		grid = $("#maingrid").ligerGrid({
    	           columns: [ 
    	                     { display: '单据号', name: 'tran_no', width : 130,  align: 'left',
    	                    	 render : function(rowdata, rowindex, value) {
    	                    		if(value == '合计'){
    	                    			return value;
    	                    		}
    	      						return '<a href=javascript:openUpdate("' 
    	      							+ rowdata.group_id
    	      							+ ',' + rowdata.hos_id 
    	      							+ ',' + rowdata.copy_code
    	      							+ ',' + rowdata.tran_id
    	      							+ ',' + rowdata.out_store_id
    	      							+ ',' + rowdata.in_store_id
    	      							+ '")>'+rowdata.tran_no+'</a>';
    	      					}		 
    	                     },
    	                     { display: '摘要', name: 'brief', width : 200, align: 'left'},
    	                     { display: '编制日期', name: 'tran_date', width : 90, align: 'left'},
    	                     { display: '调出单位', name: 'out_hos_name', width : 100, align: 'left'},
    	                     { display: '调出仓库', name: 'out_store_name', width : 120, align: 'left'},
    	                     { display: '调拨出库单', name: 'out_no', width : 120, align: 'left',
    	                    	 render : function(rowdata, rowindex, value) {
    	                    		 	if(value == null){
    	                    		 		return "";
    	                    		 	}
    	        						return '<a href=javascript:openOut("' 
    	        							+ rowdata.group_id
    	        							+ ',' + rowdata.hos_id 
    	        							+ ',' + rowdata.copy_code
    	        							+ ',' + rowdata.out_id
    	        							+ ',' + rowdata.out_store_id
    	        							+ '")>'+rowdata.out_no+'</a>';
    	        					}	
    	                     },
    	                     { display: '调入单位', name: 'in_hos_name', width : 100, align: 'left'},
    	                     { display: '调入仓库', name: 'in_store_name', width : 120, align: 'left'},
    	                     { display: '调拨入库单', name: 'in_no', width : 120, align: 'left',
    	                    	 render : function(rowdata, rowindex, value) {
    	                    		if(value == null){
    	                    			return "";
    	                    		}
    	       						return '<a href=javascript:openIn("' 
    	       							+ rowdata.group_id
    	       							+ ',' + rowdata.hos_id 
    	       							+ ',' + rowdata.copy_code
    	       							+ ',' + rowdata.in_id
    	       							+ '")>'+rowdata.in_no+'</a>';
    	       					}	
    	                     },
    	                     { display: '金额', name: 'amount_money', width : 90, align: 'right',
    	                    	 render : function(rowdata, rowindex, value) {
    	           					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    	           				}
    	                     },
    	                     { display: '制单人', name: 'maker_name', width : 80, align: 'left'},
    	                     { display: '调出确认人', name: 'checker_name', width : 80, align: 'left'},
    	                     { display: '调出日期', name: 'check_date', width : 90, align: 'left'},
    	                     { display: '调入确认人', name: 'confirmer_name', width : 80, align: 'left'},
    	                     { display: '调入日期', name: 'confirm_date', width : 90, align: 'left'},
    	                     { display: '状态', name: 'state_name', width : 100, align: 'left'
    	                    	 },
    	                     { display: '是否打印', name: 'print_state_name', width : 100, align: 'left'
    	                    	 }/* ,
    	                    	  {display: '是否打印', name: 'print_state', align: 'left',width: 100,
        	                    	 render : function(rowdata, rowindex, value) {
        	     		 				if(rowdata.print_state>0){
        	     		 					return '是';
        	     		 				}else{
        	     		 					return '否';
        	     		 				}
        	     		 			}	 
        	                     } */
    	                     ],
    	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatTranMain.do?show_detail=0',
    	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
    	                     checkBoxDisplay:isCheckDisplay,
    	                     selectRowButtonOnly:true,//heightDiff: -10,
    	                     onsuccess:function(){
    	         			
    	         				//is_addRow();
    	         			},
    	                     toolbar: { items: [
    	                     					{ text: '查询', id:'search', click: query,icon:'search' },
    	                     					{ line:true },
    					    					{ text: '添加', id:'add', click: add_open, icon:'add' },
    					    					{ line:true },
    											{ text: '冲账', id:'offset', click: balanceConfirm,icon:'offset' },
    											{ line:true }, 
    											{ text: '删除', id:'delete', click: remove,icon:'delete' },
    					    	                { line:true },
    					    	                { text: '调出确认', id:'outConfirm', click: outConfirm,icon:'audit' },
    											{ line:true },
    											{ text: '取消调出确认', id:'unOutConfirm', click: unOutConfirm,icon:'unaudit' },
    											{ line:true },
    											{ text: '调入确认', id:'inConfirm', click: confirmData,icon:'account' },
    											{ line:true },
    											
    											{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' },
    											
    											
    					    				]},
    	    				onDblClickRow : function (rowdata, rowindex, value)
    	    				{
    	    					if(rowdata.tran_id == null){
    	    						$.ligerDialog.warn('请选择数据 ');
    	    						return ; 
    	    					}
    							openUpdate(
    									rowdata.group_id   + "," + 
    									rowdata.hos_id  + "," + 
    									rowdata.copy_code   + "," + 
    									rowdata.tran_id    + "," + 
    									rowdata.out_store_id  + 
    									rowdata.in_store_id 
    								);
    	    				} 
    	                   });
    	}else{
    		grid = $("#maingrid").ligerGrid({
 	           columns: [ 
 	                     { display: '单据号', name: 'tran_no', width : 130,  align: 'left',
 	                    	 render : function(rowdata, rowindex, value) {
 	                    		if(value == '合计'){
 	                    			return value;
 	                    		}
 	      						return '<a href=javascript:openUpdate("' 
 	      							+ rowdata.group_id
 	      							+ ',' + rowdata.hos_id 
 	      							+ ',' + rowdata.copy_code
 	      							+ ',' + rowdata.tran_id
 	      							+ ',' + rowdata.out_store_id
 	      							+ ',' + rowdata.in_store_id
 	      							+ '")>'+rowdata.tran_no+'</a>';
 	      					}		 
 	                     },
 	                     { display: '材料编码', name: 'inv_code', width : 90, align: 'left'},
 	                     { display: '材料名称', name: 'inv_name', width : 90, align: 'left'},
 	                    { display: '物资类别', name: 'mat_type_name', width : 90, align: 'left'},
 	                     { display: '计量单位', name: 'unit_name', width : 90, align: 'left'},
 	                     { display: '规格型号', name: 'inv_model', width : 90, align: 'left'},
 	                     { display: '数量', name: 'amount', width : 80, align: 'right'},
 	                     { display: '单价', name: 'price', width : 90, align: 'right'},
 	                     { display: '金额', name: 'amount_money', width : 90, align: 'right',
 	                    	 render : function(rowdata, rowindex, value) {
 	           					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
 	           				}
 	                     },
 	                     { display: '批号', name: 'batch_no', width : 90, align: 'left'},
 	                     { display: '条形码', name: 'bar_code', width : 90, align: 'left'},
 	                     { display: '生产厂商', name: 'fac_name', width : 90, align: 'left'},
 	                     { display: '编制日期', name: 'tran_date', width : 90, align: 'left'},
 	                     { display: '调出单位', name: 'out_hos_name', width : 100, align: 'left'},
 	                     { display: '调出仓库', name: 'out_store_name', width : 120, align: 'left'},
 	                     { display: '调拨出库单', name: 'out_no', width : 120, align: 'left',
 	                    	 render : function(rowdata, rowindex, value) {
 	                    		 	if(value == null){
 	                    		 		return "";
 	                    		 	}
 	        						return '<a href=javascript:openOut("' 
 	        							+ rowdata.group_id
 	        							+ ',' + rowdata.hos_id 
 	        							+ ',' + rowdata.copy_code
 	        							+ ',' + rowdata.out_id
 	        							+ ',' + rowdata.out_store_id
 	        							+ '")>'+rowdata.out_no+'</a>';
 	        					}	
 	                     },
 	                     { display: '调入单位', name: 'in_hos_name', width : 100, align: 'left'},
 	                     { display: '调入仓库', name: 'in_store_name', width : 120, align: 'left'},
 	                     { display: '调拨入库单', name: 'in_no', width : 120, align: 'left',
 	                    	 render : function(rowdata, rowindex, value) {
 	                    		if(value == null){
 	                    			return "";
 	                    		}
 	       						return '<a href=javascript:openIn("' 
 	       							+ rowdata.group_id
 	       							+ ',' + rowdata.hos_id 
 	       							+ ',' + rowdata.copy_code
 	       							+ ',' + rowdata.in_id
 	       							+ '")>'+rowdata.in_no+'</a>';
 	       					}	
 	                     }, 
 	                     { display: '制单人', name: 'maker_name', width : 80, align: 'left'},
 	                     { display: '调出确认人', name: 'checker_name', width : 80, align: 'left'},
 	                     { display: '调出日期', name: 'confirm_date', width : 90, align: 'left'},
 	                     { display: '调入确认人', name: 'confirmer_name', width : 80, align: 'left'},
 	                     { display: '调入日期', name: 'confirm_date', width : 90, align: 'left'},
 	                     { display: '状态', name: 'state_name', width : 100, align: 'left'},
    	                 { display: '是否打印', name: 'print_state_name', width : 100, align: 'left'
    	                   }/* ,
 	                    {display: '是否打印', name: 'print_state', align: 'left',width: 100,
	                    	 render : function(rowdata, rowindex, value) {
	     		 				if(rowdata.print_state>0){
	     		 					return '是';
	     		 				}else{
	     		 					return '否';
	     		 				}
	     		 			}	 
	                     } */
 	                    
 	                     ],
 	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatTranMain.do?show_detail=1',
 	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
 	                     checkBoxDisplay:isCheckDisplay, gid: 'detailGrid', 
 	                     selectRowButtonOnly:true,//heightDiff: -10,
 	                     onsuccess:function(){
 	         			
 	         				//is_addRow();
 	         			},
 	                     toolbar: { items: [
 	                                   	{ text: '查询', id:'search', click: query,icon:'search' },
                     					{ line:true },
				    					{ text: '添加', id:'add', click: add_open, icon:'add' },
										{ line:true },
										{ text: '冲账', id:'offset', click: balanceConfirm,icon:'offset' },
										{ line:true }, 
										{ text: '删除', id:'delete', click: remove,icon:'delete' },
				    	                { line:true },
				    	                { text: '调出确认', id:'outConfirm', click: outConfirm,icon:'audit' },
										{ line:true },
										{ text: '取消调出确认', id:'unOutConfirm', click: unOutConfirm,icon:'unaudit' },
										{ line:true },
										{ text: '调入确认', id:'inConfirm', click: confirmData,icon:'account' },
										{ line:true },
									
										{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' },
 											
 					    				]},
 	    				onDblClickRow : function (rowdata, rowindex, value)
 	    				{
 	    					if(rowdata.tran_id == null){
 	    						$.ligerDialog.warn('请选择数据 ');
 	    						return ; 
 	    					}
 							openUpdate(
 									rowdata.group_id   + "," + 
 									rowdata.hos_id  + "," + 
 									rowdata.copy_code   + "," + 
 									rowdata.tran_id    + "," + 
 									rowdata.out_store_id + 
 									rowdata.in_store_id
 								);
 	    				} 
 	                   });
    	}

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>制单日期："+$("#begin_tran_date").val() +" 至  "+ $("#end_tran_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="材料调拨";
    }
    
    
    //保存列
    function btn_saveColumn(){
		
		   var path = window.location.pathname+"/maingrid";
		   var url = '../../../sys/addBatchSysTableStyle.do?isCheck=false';
		   saveColHeader({
			   grid:grid,
			   path:path,
			   url:url,
			   callback:function(data){
				   $.ligerDialog.success("保存成功");
			   }
		   });
	  
	   return false;
}
    //字段类型渲染器
    function fieldTypeRender(r, i, value){
        for (var i = 0, l = matTranMain_state.Rows.length; i < l; i++){
            var o = matTranMain_state.Rows[i];
            if (o.id == value) return o.text;
        }
        if(value == null){
        	return "";
        }
        return "未确认";
    }
    
    //是否显示复选框
    function isCheckDisplay(rowdata){
       	if(rowdata.out_id == null) return false;
         return true;
    }
    
    function add_open(){
    	
    	parent.$.ligerDialog.open({
			title: '调拨单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/tran/matTranMainAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
    	
    function remove(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要删除的数据');
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						if(this.state > 1){nos = nos + this.tran_no + ",";}
						ParamVo.push(
								this.group_id + "@" + 
								this.hos_id + "@"+ 
								this.copy_code + "@" +
								this.tran_id 
								)
			});
			 if(nos != ""){
 				$.ligerDialog.error("删除失败！"+nos+"单据不是未确认状态");
 				return;
 			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatTranMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    
    function outConfirm(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要调出确认的数据');
		} else {
			var ParamVo = [];
			var tran_nos = "";
			$(data).each(
					function() {
						if(this.state != 1){
							tran_nos = tran_nos + this.tran_no + ",";
						}
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.tran_id
								)
			});
			if(tran_nos != ""){
				$.ligerDialog.warn("调出确认失败！"+tran_nos+"单据不是未确认状态");
				return;
			}
			$.ligerDialog.confirm('确定调出确认?', function(yes) {
				if (yes) {
				
				if (yes) {
					ajaxJsonObjectByUrl("outConfirmMatTranMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
				}
				}
			});
		}
	}
    
    function unOutConfirm(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要取消调出确认的数据');
		} else {
			var ParamVo = [];
			var tran_nos = "";
			$(data).each(
					function() {
						if(this.state != 2){
							tran_nos = tran_nos + this.tran_no + ",";
						}
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.tran_id
								)
			});
			if(tran_nos != ""){
				$.ligerDialog.warn("取消调出确认失败！"+tran_nos+"单据不是调出确认状态");
				return;
			}
			$.ligerDialog.confirm('确定取消调出确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("unOutConfirmMatTranMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    
    function inConfirm(){
    	var is_store='${p04045 }';
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要调入确认的数据');
		} else {
			
			var todayDate = new Date();
			var todayYear = todayDate.getFullYear();
			var todayMonth = todayDate.getMonth() + 1;
			var todayDate = todayDate.getDate();
			todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
			todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
			var today = todayYear + '-' + todayMonth + '-' + todayDate;
			if('${p04047 }'==0){
				confirmData(today);
			}else{
				$.ligerDialog.open({
					content: "确认日期:<input id='confirmDate' value=" + today + " style='text-align:center; border: 1px solid blue; height: 18px;' onFocus='WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd\"})' />",
					width: 300,
					height: 150,
					buttons: [
						{ text: '确定', onclick: function (item, dialog) {
							aa = $("#confirmDate").val();
							if (aa) {
								dialog.close();
								confirmData(aa);
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
    	var ParamVo = [];
		
		var ParamVo1 = [];
		
		/* 1.开始   说明：后期可用于维护用户输入日期操作 */
		var todayDate = new Date();
		var todayYear = todayDate.getFullYear();
		var todayMonth = todayDate.getMonth() + 1;
		var todayDate = todayDate.getDate();
		todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
		todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
		var confirmDate = todayYear + '-' + todayMonth + '-' + todayDate; 
		/* 1.结束 */
		
		var tran_nos = "";
		var checker="";
		$(data).each(
				function() {
					if(this.checker=='${user_id}'){
						checker=checker+this.tran_no+",";
					}
					if(this.state != 2){
						tran_nos = tran_nos + this.tran_no + ",";
					}
					ParamVo.push(
							this.group_id + "@"+ 
							this.hos_id + "@" + 
							this.copy_code + "@" +
							this.tran_id+ "@" +
							this.tran_date + "@" + //confirmDate+ "@" +
							is_store+ "@" +this.in_store_id+"@"+this.tran_no
							);
					ParamVo1.push(
							this.group_id + "@"+ 
							this.hos_id + "@" + 
							this.copy_code + "@" +
							this.tran_id+ "@" +
							confirmDate+ "@" +is_store+ "@" +this.out_store_id+"@"+this.tran_no
							);
					
		});
		
		
		if(tran_nos != ""){
			$.ligerDialog.warn("调入确认失败！"+tran_nos+"单据不是调出确认状态");
			return;
		}
		$.ligerDialog.confirm('确定调入确认?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("../in/verifyMatClosingDate.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
					if (responseData.state == "true") {
						
						ajaxJsonObjectByUrl("../in/verifyMatClosingDate.do?isCheck=false", {ParamVo : ParamVo1.toString()}, function(responseData){
							if (responseData.state == "true") {
								
						ajaxJsonObjectByUrl("inConfirmMatTranMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
							}
						},false);
					}
				},false);
			}
		});
		
		
    }
    
    
    function balanceConfirm(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要冲账的数据');
		} else {
			var ParamVo = [];
			var tran_nos = "";
			$(data).each(function() {
				if(this.state != 3){
					tran_nos = tran_nos + this.tran_no + ",";
				}
				ParamVo.push(
					this.hos_id + "@" + 
					this.group_id + "@"+ 
					this.copy_code + "@" +
					this.tran_id
				)
			});
			if(tran_nos != ""){
				$.ligerDialog.warn("冲账失败！"+tran_nos+"单据不是调入确认状态");
				return;
			}
			$.ligerDialog.confirm('确定冲账?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("balanceConfirmMatTranMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function openUpdate(obj) {

		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ "copy_code=" + voStr[2].toString() + "&" 
			+ "tran_id=" + voStr[3].toString() + "&" 
			+ "store_id=" + voStr[4].toString()+ "&" 
			+ "in_store_id=" + voStr[5].toString();;
		
		parent.$.ligerDialog.open({
			title: '调拨单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/tran/matTranMainUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}

	function openOut(obj) {

		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ "copy_code=" + voStr[2].toString() + "&" 
			+ "out_id=" + voStr[3].toString() + "&" 
			+ "store_id=" + voStr[4].toString();

		parent.$.ligerDialog.open({
			title: '出库单查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/tran/matTranMainUpdateOutPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}

	function openIn(obj) {

		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ "copy_code=" + voStr[2].toString() + "&" 
			+ "in_id=" + voStr[3].toString();

		parent.$.ligerDialog.open({
			title: '入库单查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/tran/matTranMainUpdateInPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
	function loadDict() {
		//字典下拉框
		autoCompleteByData("#tran_type", matTranMain_tranType.Rows, "id", "text",true, true,'',false,false,'180');
		autoCompleteByData("#bus_type", matTranMain_busType.Rows, "id", "text",true, true,'',false,false,'180');
		//联动事件
		$("#tran_type").bind("change", function(){changeTranType()});
		
		autocomplete("#in_store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id","text", true, true, {is_read:'1'}, false, false, '180');
		autocomplete("#out_store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id","text", true, true, {is_write:'1'}, false, false, '180');
		
		autocomplete("#in_hos_id", "../../queryMatHosInfo.do?isCheck=false", "id","text", true, true, "", false, false, '180');
		autocomplete("#out_hos_id", "../../queryMatHosInfo.do?isCheck=false", "id","text", true, true, "", false, false, '180');
		autocomplete("#maker", "../../../sys/queryUserDict.do?isCheck=false",  "id", "text", true, true, "", false, false, '180');
		autocomplete("#in_confirmer", "../../../sys/queryUserDict.do?isCheck=false",  "id", "text", true, true, "", false, false, '180');
		autocomplete("#out_confirmer", "../../../sys/queryUserDict.do?isCheck=false",  "id", "text", true, true, "", false, false, '235');
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},false, false, '180');
		autoCompleteByData("#state", matTranMain_state.Rows, "id", "text",true, true,'',false,false,'180');
 
		$("#begin_tran_date").ligerTextBox({width : 100});
        autodate("#begin_tran_date", "yyyy-mm-dd", "month_first");
		$("#end_tran_date").ligerTextBox({width : 100});
        autodate("#end_tran_date", "yyyy-mm-dd", "month_last");
		
		$("#begin_check_date").ligerTextBox({width : 100});
		$("#end_check_date").ligerTextBox({width : 100});
		
		$("#begin_confirm_date").ligerTextBox({width : 100});
		$("#end_confirm_date").ligerTextBox({width : 100});
		
		$("#tran_no").ligerTextBox({width : 238});
		$("#brief").ligerTextBox({width : 238});
		$("#inv_info").ligerTextBox({width :180});
		/*   $("#print_state").ligerTextBox({width:160}); */
		//材料信息,默认不显示
		$(".inv_msg").hide();
	}
	
    function changeTranType(){
		if(liger.get("tran_type").getValue() == 1){
			$("#bus_type").ligerComboBox({width:180,disabled:true,cancelable: false});
	        liger.get("bus_type").setValue("");
			liger.get("bus_type").setText("");
		}else{
			$("#bus_type").ligerComboBox({width:180,disabled:false});
		}
    }
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('P', print);
	}
	
	  //打印模板设置
    function printSet(){
	  
    	 var useId=0;//统一打印
		if('${p04020 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p04020 }'==2){
  			//按库房打印
  			if(!liger.get("out_store_id").getValue()){
  				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
  			}
  			
			useId=liger.get("out_store_id").getValue().split(",")[0];
  		}/*
    	parent.$.ligerDialog.open({url : 'hrp/mat/storage/tran/matTranPrintSetPage.do?template_code=04015&use_id='+useId,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
    		}); */
    	officeFormTemplate({template_code:"04015",useId:useId});
    }

  //打印
    function print(flag){
    	 var useId=0;//统一打印
  		if('${p04020 }'==1){
  			//按用户打印
  			useId='${sessionScope.user_id }';
  		}else if('${p04020 }'==2){
  			//按库房打印
  			if(!liger.get("out_store_id").getValue()){
  				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
  			}
  			
			useId=liger.get("out_store_id").getValue().split(",")[0];
  		}
  	

     	//if($("#create_date_b").val())
  		var data = gridManager.getCheckedRows();
 		if (data.length == 0){
 			$.ligerDialog.error('请选择行');
 		}else{
 			
 			var tran_id ="" ;
 			var in_nos = "";
 			$(data).each(function (){		
 				if(this.state != 2){
 					in_nos = in_nos + this.in_no + "<br>";
 				}
 				
 				tran_id  += this.tran_id+","
 					
 			});
 		}
			 var para={
					 template_code:'04015',
						class_name:"com.chd.hrp.mat.serviceImpl.storage.tran.MatTranMainServiceImpl",
						method_name:"queryMatTranByPrintTemlateMain",
						 sSetPrint:flag,//是否套打，默认非套打 
						 isPreview:true,//是否预览，默认直接打印 
						paraId :tran_id.substring(0,tran_id.length-1) ,
			    		use_id:useId,
			    		p_num:1
						
	    	}; 
		 	
			console.log(para)
			
			 officeFormPrint(para);
	    	
		
    	
    }
  	
  	//显示明细
    function showDetail(){
    	show_detail = $("#show_detail").is(":checked") ? 1 : 0;
    	if(show_detail =='1'){ 
			 $(".inv_msg").show();
		 }else{
			 $(".inv_msg").hide();
		 }
    	if (grid) {
    		//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind(); 
		}
		loadHead();
		query();
	}

	function show(){
		if($("#tran_div").css("height")=="0px"){
			$("#tran_div").css("height","auto")
		}else{
			$("#tran_div").css("height","0px")
		}
		grid._onResize();
		//$(".l-bar-btnload").click();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">制单日期：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="left"><input name="begin_tran_date" type="text" id="begin_tran_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">至：</td>
			            <td align="left" class="l-table-edit-td"><input name="end_tran_date" type="text" id="end_tran_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
				</table>
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">调拨类型：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="tran_type" type="text" id="tran_type" ltype="text" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left:20px;" width="10%">业务类型：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="bus_type" type="text" id="bus_type" ltype="text" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">调出日期：</td>
			<td align="left" class="l-table-edit-td">
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="left"><input name="begin_check_date" type="text" id="begin_check_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">至：</td>
			            <td align="left" class="l-table-edit-td"><input name="end_check_date" type="text" id="end_check_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
				</table>
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">调出单位：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="out_hos_id" type="text" id="out_hos_id" ltype="text" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">调出仓库：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="out_store_id" type="text" id="out_store_id" ltype="text" /></td>
			<td align="left"></td>
		</tr>  
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">调入日期：</td>
			<td align="left" class="l-table-edit-td">
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="left"><input name="begin_confirm_date" type="text" id="begin_confirm_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">至：</td>
			            <td align="left" class="l-table-edit-td"><input name="end_confirm_date" type="text" id="end_confirm_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
				</table>
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">调入单位：</td>
			<td align="left" class="l-table-edit-td"><input name="in_hos_id" type="text" id="in_hos_id" ltype="text" /></td>
			<td align="left"></td>
			            
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">调入仓库：</td>
			<td align="left" class="l-table-edit-td">
            	<table>
            		<tr>
            			<td>
							<input name="in_store_id" type="text" id="in_store_id" ltype="text" />
            			</td>
            			<td align="left" class="l-table-edit-td" style="padding-left: 20px;">
            				<a href="javascript:show()">高级查询</a>
            			</td>
            		</tr>
            	</table>
			</td>
			<td align="left"></td>
		</tr> 
    </table>
	<div id="tran_div"  style="height:0px; overflow:hidden" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">单据号：</td>
			<td align="left" class="l-table-edit-td"  width="20%"><input name="tran_no" type="text" id="tran_no" ltype="text" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">状   态：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="state" type="text" id="state" ltype="text" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left:20px;" width="10%">物资类别：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td" >
				摘要：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td"><input name="maker" type="text" id="maker" ltype="text" validate="{maxlength:20}" /></td>
	 		<td align="left"></td>
			   <td align="right" class="l-table-edit-td  demo" width="10%">是否打印：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<select name="print_state" id="print_state"style="width: 135px;" >
            			<option value="">请选择</option>
                		<option value="0">否</option>
                		<option value="1">是</option>
            	</select>
            </td> 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">调出确认人：</td>
            <td align="left" class="l-table-edit-td"><input name="out_confirmer" type="text" id="out_confirmer" ltype="text" validate="{maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">调入确认人：</td>
            <td align="left" class="l-table-edit-td"><input name="in_confirmer" type="text" id="in_confirmer" ltype="text" validate="{maxlength:20}" /></td>
     
			
			
	<!-- 		<td align="left"></td>
			   <td align="right" class="l-table-edit-td  demo" width="10%">是否已经打印：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<select name="print_state" id="print_state"style="width: 135px;" >
            			<option value="">请选择</option>
                		<option value="0">否</option>
                		<option value="1">是</option>
            	</select>
            	
            </td> -->
			
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" >
				查询：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="show_detail" type="checkbox" id="show_detail" ltype="text" onclick="showDetail()"/>显示明细
			</td>
			<td align="left"></td>
		   <td align="right" class="l-table-edit-td inv_msg">
				材料信息：
			</td>
			<td align="left" class="l-table-edit-td inv_msg">
				<input name="inv_info" type="text" id="inv_info" ltype="text" />
			</td>
		
		</tr>
    </table>
    </div>

	<div id="maingrid"></div>
</body>
</html>
