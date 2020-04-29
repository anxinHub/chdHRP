<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var store_id = null;
	var store_no;
	var data;
	
	var detailGrid = null;
	var collect_flag = 0;
	
	var manager;
	var flag = 0;
	$(function() {
		
		$("html body").height($(window).height());
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		//默认为汇总
		$("#is_check").val("3");
		//全部
		$("#is_all").change(function(){
			if($("#is_all").prop("checked") == true){
				$('#is_yes').prop('checked',false) ;
				$('#is_no').prop('checked',false) ;
	        	$("#is_check").val("1");
	        	
			}else{
	        	$("#is_check").val("0");
			} 
		}); 
		
		//已汇总
		$("#is_yes").change(function(){
			if($("#is_yes").prop("checked") == true){
				$('#is_all').prop('checked',false) ;
				$('#is_no').prop('checked',false) ;
	        	$("#is_check").val("2");
	        	
			}else{
	        	$("#is_check").val("0");
			} 
		}); 
		
		//未汇总
		$("#is_no").change(function(){
			if($("#is_no").prop("checked") == true){
				$('#is_all').prop('checked',false) ;
				$('#is_yes').prop('checked',false) ;
	        	$("#is_check").val("3");
	        	
			}else{
	        	$("#is_check").val("0");
			} 
		}); 
		
		//query();
		
	});
	
	//查询
	function query() {
		if(liger.get("store_code").getValue() =='' || liger.get("store_code").getValue() == null){
			$.ligerDialog.warn("请选择仓库！");	
			return;
		}
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
    	grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()}); 
    	grid.options.parms.push({name : 'end_date',value : $("#end_date").val()}); 
    	
    	grid.options.parms.push({name : 'acc_year', value : liger.get("year").getValue()});
    	grid.options.parms.push({name : 'acc_month',value : liger.get("month").getValue()});
    	
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]}); 
    	grid.options.parms.push({name : 'is_check',value : $("#is_check").val()}); 
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead() {
		var para = {
			store_id : liger.get("store_code").getValue().split(',')[0],
			store_no : liger.get("store_code").getValue().split(',')[1]
		};
		
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                  { display: '材料编码', name: 'inv_code', align: 'left',width :'10%',
		                	 render : function(rowdata, rowindex,value) {
									return "<a href=javascript:openUpdate('"
											+rowdata.inv_code   + "|" 
											+ rowdata.inv_id + "|" 
											+ rowdata.inv_no  + "|"
											+ rowdata.inv_name  + "|"
											+"')>"+rowdata.inv_code+"</a>"
								}		 
		                 },
						 { display: '材料名称(E)', name: 'inv_name', align: 'left' ,width :'15%'},
						 { display: '规格型号', name: 'inv_model', align: 'left',width :'10%'},
						 { display: '计量单位', name: 'unit_name', align: 'left'},
						 { display: '供应商', name: 'sup_name', align: 'left',width :'10%'},
						 { 
							 display: '单价', name: 'price', align: 'right' ,
							 render:function(rowdata){
				            		return formatNumber(rowdata.price ==null ? 0 : rowdata.price,'${p04006}',1);
				             }
						 },
						 /*{ display: '期初库存', name: 'begin_amount', align: 'right',
							 render:function(rowdata){
				            		return formatNumber(rowdata.begin_amount ==null ? 0 : rowdata.begin_amount,2,1);
				             }
						 },{ 
							 display: '当前库存', name: 'cur_amount', align: 'right',
							 render:function(rowdata){
				            		return formatNumber(rowdata.cur_amount ==null ? 0 : rowdata.cur_amount,2,1);
				             }	 
						 },*/
						 { 
							 display: '需求数量', name: 'req_amount', align: 'right' ,
							 render:function(rowdata){
				            		return formatNumber(rowdata.req_amount ==null ? 0 : rowdata.req_amount,2,1);
				             }
						 },{ 
							 display: '汇总数量', name: 'exec_amount', align: 'right' ,
							 render:function(rowdata){
				            		return formatNumber(rowdata.exec_amount ==null ? 0 : rowdata.exec_amount,2,1);
				             }
						 },{ 
							 display: '需求金额', name: 'req_money', align: 'right' ,
							 render:function(rowdata){
				            		return formatNumber(rowdata.req_money ==null ? 0 : rowdata.req_money,'${p04005}',1);
				             }	 
						 },{ 
							 display: '状态', name: 'state', align: 'left' ,
							 render : function(rowdata, rowindex,value) {
									if(rowdata.state == 1 ||rowdata.state == 2 ){
										return "未汇总";
									}
									if(rowdata.state == 3 ){
										return "已汇总";
									}
									
							 } 
						 },{
							 display: '明细数量', name: 'detail_data', align: 'left' ,width:'0'
						 }					
		                ],
		                checkbox: false,dataAction : 'server',dataType : 'server',usePager : true,
		                url:'queryMatDeptRequriedCollect.do?isCheck=true',
		                enabledEdit : true,fixedCellHeight:true,isScroll : false,selectRowButtonOnly:true,
		                showTitle: true , /* isSingleCheck : true , */ heightDiff: 10 , delayLoad:true,
	                    width: '100%', height: '100%',checkbox:true,
	                    frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
	                    detail: { onShowDetail: f_showInvDetail,height:'200'},parms :para,
		                toolbar: { items: 
		                	 	[
		                            {text: '查询', id:'query', click: query ,icon:'search' },
		                            { line:true },
		                            {text: '汇总', id:'saveSub', click: saveSub ,icon:'save' },
		                            { line:true },
		                            {text: '定向汇总', id:'dirSum', click: dirSum ,icon:'' },
		                            { line:true },
		                            {text: '删除', id:'delete', click: deleteRows ,icon:'delete' },
		                            { line:true }/* ,
		                            {text : '打印',id : 'print',click: print ,icon : 'print'} */
		                   		]
		                 }
		               });
		    	gridManager = $("#maingrid").ligerGetGridManager();
		    	grid.toggleCol("detail_data", false);
	}
	
	//明细
	var detailRowData;
	function f_showInvDetail(row, detailPanel,callback){
		detailRowData = row;
		 if(row.state ==2){
			 //未汇总的可以编辑
			var para = {
					inv_id : row.inv_id,
					inv_no : row.inv_no,
					store_id : liger.get("store_code").getValue().split(',')[0],
					store_no : liger.get("store_code").getValue().split(',')[1],
					acc_year : liger.get("year").getValue(),
					acc_month :liger.get("year").getValue(),
					begin_date : $("#begin_date").val(),
					end_date : $("#end_date").val(),
					is_check : $("#is_check").val(),
					state : row.state
			};
			 var invDetail = document.createElement('div'); 
	         $(detailPanel).append(invDetail);
	         detailGrid = $(invDetail).css('margin',1).ligerGrid({
	             columns:
	                         [
	                         { display: '需求计划单号', name: 'req_code',width: 180},
	                         { display: '申请部门', name: 'dept_name',width: 180},
	                         { display: '单价', name: 'price', align: 'right',width:80,
		                        	render : function(rowdata, rowindex, value) {
		             					return value == null ? "" : formatNumber(value, '${p04006}', 1);
		             				}	 
		                         },
	                         { display: '需求数量', name: 'req_amount',width: 100 },
	                         { display: '汇总数量', name: 'exec_amount' ,width: 100,
	                        	 editor : {type : 'number'}	 
	                         },
	                         { display: '需求金额', name: 'req_money' ,align: 'right',width:80,
	                        	render : function(rowdata, rowindex, value) {
	              					return value == null ? "" : formatNumber(value, '${p04005}', 1);
	              				}	 
	                         }
	                         ], 
							dataAction : 'server',dataType : 'server',usePager : false,checkbox: false, 
							parms :para, 
				 			url : 'queryMatCollectDetail.do?isCheck=false',
				 			enabledEdit : true,
				 			fixedCellHeight:true, 
				 			onBeforeEdit : f_detail_onBeforeEdit, 
				 			onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit,
				 			onAfterEdit : f_detail_AfterDetail,
				 			isScroll: false,  frozen:false,
				 			width: '800',height : "200"
	         });
	         
	         $(invDetail).bind('keydown.grid', function(event) {
	     		if (event.keyCode == 13) {// enter,也可以改成9:tab
	     			detailGrid.endEditToMatNext();
	     		}
	     	});
	     	
		 }else{
			 //已汇总的不可以编辑
			 var para = {
						inv_id : row.inv_id,
						inv_no : row.inv_no,
						store_id : liger.get("store_code").getValue().split(',')[0],
						store_no : liger.get("store_code").getValue().split(',')[1],
						acc_year : liger.get("year").getValue(),
						acc_month :liger.get("year").getValue(),
						begin_date : $("#begin_date").val(),
						end_date : $("#end_date").val(),
						is_check : $("#is_check").val(),
						state : row.state
				};
				 var invDetail = document.createElement('div'); 
		         $(detailPanel).append(invDetail);
		         detailGrid = $(invDetail).css('margin',1).ligerGrid({
		             columns:
		                         [
		                         { display: '需求计划单号', name: 'req_code',width: 180},
		                         { display: '申请部门', name: 'dept_name',width: 180},
		                         { display: '单价', name: 'price', align: 'right',width:80,
			                        	render : function(rowdata, rowindex, value) {
			             					return value == null ? "" : formatNumber(value, '${p04006}', 1);
			             				}
			                         },
		                         { display: '需求数量', name: 'req_amount',width: 100 },
		                         { display: '汇总数量', name: 'exec_amount' ,width: 100,
		                        	 editor : {type : 'int'}	 
		                         },
		                         { display: '需求金额', name: 'req_money' ,align: 'right',width:80,
		                        	render : function(rowdata, rowindex, value) {
		              					return value == null ? "" : formatNumber(value, '${p04005}', 1);
		              				}	 
		                         }
		                         ], 
								dataAction : 'server',dataType : 'server',usePager : false,checkbox: false, 
								parms :para, 
					 			url : 'queryMatCollectDetail.do?isCheck=false',
					 			enabledEdit : false,
					 			isScroll:true,
					 			width: '800',height : "200"
		         });  
		 }
	}
	var detail_id = "";
	var detail_name = "";
	//编辑之前
	function f_detail_onBeforeEdit(e) {
		detail_id = e.rowindex;
		detailClicked = 0;
		detail_name = e.column.name;		
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_detail_onBeforeSubmitEdit(e) {
		if (e.column.name == "exec_amount" && e.value == ""){
			return false;
		}
		return true;
	}
	//编辑之后动作
	function f_detail_AfterDetail(e){
		if("exec_amount" == detail_name){ 
			
			if(e.record.req_amount < e.record.exec_amount){
				$.ligerDialog.warn("汇总数量不能大于请求数量！");	
		 		return;
			}else{
				gridManager.updateCell('exec_amount',e.record.exec_amount,detailRowData);
				setDetailAmount();
			}    		
    	} 
	}
	
	
    //设置明细数量
    function setDetailAmount(){   	
    	var sumAmount =0;
		var dataDetail =  detailGrid.getData();
		if(dataDetail.length > 0){
			$(dataDetail).each(function(){
				if(validateStr(this.exec_amount)){
					sumAmount += this.exec_amount;
				}
			})
			
	    	if(dataDetail.length > 0){
	    		grid.updateCell('exec_amount', sumAmount, detailRowData); 
	    		grid.updateCell('detail_data', dataDetail, detailRowData); 
	    	}else{
	    		grid.updateCell('exec_amount', sumAmount, detailRowData); 
	    		grid.updateCell('detail_data', "", detailRowData); 
	    	}
		}
		//alert(JSON.stringify(dataDetail));
    }
    
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "inv_code="+vo[0]
		+"&inv_id="+vo[1]
		+"&inv_no="+vo[2]
		+"&inv_name="+vo[3]
		+"&store_id="+liger.get("store_code").getValue().split(',')[0]
		+"&store_no="+liger.get("store_code").getValue().split(',')[1]
		+"&acc_year="+liger.get("year").getValue()
		+"&acc_month="+liger.get("month").getValue()
		+"&begin_date="+$("#begin_date").val()
		+"&end_date="+$("#end_date").val();
		
		$.ligerDialog.open({ 
			url : 'matDeptCollectDetailPage.do?isCheck=false&'+parm,data:{}, 
			height: 550,width: 1000, 
			title:'查看' , modal:true , showToggle:false , showMax:true , showMin: false , isResize:true
		});
	}
	
	/* //汇总
	function summary(){
		 if(liger.get("store_code").getValue() =='' || liger.get("store_code").getValue() == null){
			$.ligerDialog.warn("请选择仓库！");	
			return;
		} 
		query();
	} */
	
	//保存提交页面
	function saveSub(){
		if($("#is_check").val() !='3'){
			$.ligerDialog.warn("请查询未汇总的单据！");	
			return;
		}
	    var allData = gridManager.getData();
	    //alert(JSON.stringify(allData))
	    if(JSON.stringify(allData).length == 2){
	    	$.ligerDialog.warn('没有汇总数据！');
	    	return;
	    }else{	
    	    $.ligerDialog.open({url: 'matDeptRequriedCollectSaveAndSub.do?isCheck=false',data:{}, //2017-1-12修改此处代码 解决点击确定按钮页面报错的问题
    	    height: 250,width:450,title:'汇总提交',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
      		buttons: [ 
      				   { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMatDeptCollectSaveAndSub();} }, 
      				   { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
      				 ] 
      		});
	    }	    
	}
	
	//保存汇总提交
	function save(){
		var allData = gridManager.getData();
		
		$.each(allData,function(i,v){
			delete v.inv_model;//暂时删除规格型号，因为其中有带有特殊字符的后台json没办法解析
			//delete v.detail_data;//暂时删除明细显示属性对象
		});
		//alert(JSON.stringify(allData));
		var parm = "store_id="+liger.get("store_code").getValue().split(',')[0]
			+"&store_no="+liger.get("store_code").getValue().split(',')[1]
    		+"&begin_date="+$("#begin_date").val()
			+"&end_date="+$("#end_date").val()
			+"&act_year="+liger.get("year").getValue()
			+"&act_month="+liger.get("month").getValue()
			+"&make_date="+$("#make_date").val()
			+"&allData="+JSON.stringify(allData);
		ajaxJsonObjectByUrl("saveMatDeptRequriedCollect.do", parm, 
				function(responseData) {
					 if (responseData.state == "true") {
						    $('#is_yes').prop('checked',true) ;
						    $('#is_all').prop('checked',false) ;
							$('#is_no').prop('checked',false) ;
				        	$("#is_check").val("2");
				  			query();
					}
				}); 
	}
	
	
	//定向汇总页面
	function dirSum(){
		var parm = '&store_id='+liger.get("store_code").getValue()
		+'&store_code='+liger.get("store_code").getText();
		
		parent.$.ligerDialog.open({ 
			url: 'hrp/mat/require/collect/matDeptRequriedDirCollectMainPage.do?isCheck=false'+parm, 
			height: $(window).height(),
			width: $(window).width(),
			title:'定向汇总',
			modal:true,showToggle:false,showMax:true ,showMin: false,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			buttons: [ 
						{ text: '查询', onclick: function (item, dialog) { 
						  	 dialog.frame.query();						  	 
							 },cls:'l-dialog-btn-highlight' 
					    },						
						{ text: '确定', onclick: function (item, dialog) { 
					   	 		dialog.frame.save();
					   	 	    $('#is_yes').prop('checked',true) ;
							    $('#is_all').prop('checked',false) ;
								$('#is_no').prop('checked',false) ;
					        	$("#is_check").val("2");
						  		query();	
						
				  	    },cls:'l-dialog-btn-highlight' },
				  	   /*  { text: '打印', onclick: function (item, dialog) { 
					  	 	dialog.frame.print();
					  		},cls:'l-dialog-btn-highlight' 
					    }, */
						{ text: '关闭', onclick: function (item, dialog) { 
							dialog.frame.this_close();
						  	},cls:'l-dialog-btn-highlight' 
						}
						 
		    ] 
			      			
		});
		
		
	}
	
	//打印
	/* function print(){
		
	} */
	
	//加载字典
	function loadDict() {
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		var date = getCurrentDate();
        var aa = date.split(';');
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
	    autodate("#end_date", "yyyy-mm-dd", "month_last");
       
		autocomplete("#year","../../queryMatYear.do?isCheck=false","id","text",true,true);
        liger.get("year").setValue(aa[0]);
        liger.get("year").setText(aa[0]);
        $("#year").ligerComboBox({cancelable: false});
        
        autocomplete("#month","../../queryMatMonth.do?isCheck=false","id","text",true,true);
        liger.get("month").setValue(aa[1]);
        liger.get("month").setText(aa[1]);
        $("#month").ligerComboBox({cancelable: false});
		
        autocomplete("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true,{is_purchase : '1'},true,false,'160');//仓库
        
        $("#year").ligerTextBox({width:100});
		$("#month").ligerTextBox({width:100});
		
        
	}
	
	//删除
	function deleteRows(){
		/* 
        if (json.length <= 0){
        	$.ligerDialog.error('请选择要删除的行！');
        	return;
        }else{
        	gridManager.deleteAllRows();
        } */
        
        var json = gridManager.getCheckedRows();
        
		 if (json.length <= 0){
	        	$.ligerDialog.error('请选择要删除的行！');
	        	return;
	     }
        
        gridManager.deleteSelectedRow2();
        
	}
	
	//年月改变
	function loadSelDate(){//年月的onchange方法
		if(year==null||year==''||month==null||month==''){
			return;
		}else{
			var str = getMonthDate(liger.get("year").getValue() , liger.get("month").getValue());
	        var p = str.split(';');
	        $("#begin_date").val(p[0]);
	        $("#end_date").val(p[1]);	      
		}
	}
	
	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}
	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; ">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
		<tr></tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><b>编制年月：</b></td>
			<td align="left" class="l-table-edit-td">
				<input name="year" type="text" id="year" requried="true"/>
			</td>
			<td align="center" class="l-table-edit-td" style="width: 15px;">至：</td>
			<td align="right" class="l-table-edit-td">
				<input name="month" type="text" id="month" requried="true" onchange="loadSelDate();"/>
			</td>
			<td align="left"></td>
			
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><b>响应库房：</b></td>
			<td align="left" class="l-table-edit-td">
				<input name="store_code" type="text" requried="true" id="store_code" />
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>查询范围:</b></td>
	        <td align="left" style="padding-left:20px;">
	          	<input type="checkbox" id="is_all" name="is_all" ltype="text"/>&nbsp;全部&nbsp;&nbsp;&nbsp;&nbsp;
	          	<input type="checkbox" id="is_yes" name="is_yes" ltype="text" />&nbsp;已汇总&nbsp;&nbsp;&nbsp;&nbsp;
	          	<input type="checkbox" id="is_no" name="is_no" ltype="text" checked/>&nbsp;未汇总
	        </td>
	        </td>
			<td align="left"></td>
			
			
			<td >
				<input name="begin_date" type="hidden" id="begin_date" requried="false"/>
				<input name="end_date" type="hidden" id="end_date" requried="false"/>
				<input name="is_check" type="hidden" id="is_check" requried="false"/>
				<input name="make_date" type="hidden" id="make_date" requried="false"/>
			</td>
		</tr>
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
