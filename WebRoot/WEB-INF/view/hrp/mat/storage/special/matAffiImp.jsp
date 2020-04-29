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
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
    var grid; 
    var gridManager = null;
    var userUpdateStr;
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
		grid.options.parms.push({name : 'begin_out_date',value : $("#begin_out_date").val()});
		grid.options.parms.push({name : 'end_out_date',value : $("#end_out_date").val()}); 
		grid.options.parms.push({name : 'store_id',value : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'dept_id',value : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]}); 
	 	grid.options.parms.push({name : 'maker',value : liger.get("maker").getValue()}); 
		grid.options.parms.push({name : 'brief',value : $("#brief").val()});
		grid.options.parms.push({name : 'inv_code', value : $("#inv_code").val()});
		grid.options.parms.push({name : 'by_inv_sup', value : $("#by_inv_sup").prop("checked") ? 1 : 0});
		//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
			    { 
		 			display: '供应商', name: 'sup_name', align: 'left',width:200,
		 			totalSummary:{ 
		        		render: function (suminf, column, cell){
	                            return '<div>合计</div>';
	                	}
		 			}
		 		},{
					display: '出库单号', name: 'out_no', align: 'left',width:150,
					/* render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.out_id
							+ '")>'+rowdata.out_no+'</a>';
					} */
				},{ 
		 			display: '摘要', name: 'brief', align: 'left',width:210
		 		},{ 
		 			display: '制单日期', name: 'out_date', align: 'left',width:90,
		 		}, { 
		 			display: '领料科室', name: 'dept_name', align: 'left',width:150,
		 		}, { 
		 			display: '材料编码', name: 'inv_code', align: 'left',width:120,
		 		}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left',width:220,
		 		}, { 
		 			display: '批号', name: 'batch_no', align: 'left',width:90,
		 		}, { 
		 			display: '条形码', name: 'bar_code', align: 'left',width:100,
		 		}, { 
		 			display: '单价', name: 'price', align: 'right',width:90,
		 			render : function(rowdata, rowindex, value) {
						return rowdata.price == null ? "" : formatNumber(rowdata.price, 2, 1);
					}
		 		}, { 
		 			display: '数量', name: 'amount', align: 'right',width:90,
		 			render : function(rowdata, rowindex, value) {
						return rowdata.amount == null ? "" : formatNumber(rowdata.amount, 2, 1);
					},
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
						}
					}
		 		}, { 
		 			display: '金额', name: 'amount_money', align: 'right',width:90,
		 			render : function(rowdata, rowindex, value) {
						return rowdata.amount_money == null ? "" : formatNumber(rowdata.amount_money, '${p04005}', 1);
					},
					totalSummary:{
						align: 'right', 
						render: function (suminf, column, cell){
                        	return '<div>'+formatNumber(suminf.sum, '${p04005}', 1)+'</div>';
                		}
					},
		 		}, { 
		 			display: '注册证号', name: 'cert_code', align: 'left',width:150
		 		},{ 
		 			display: '生产日期', name: 'fac_date', align: 'left',width:90
		 		},{ 
		 			display: '序列号', name: 'serial_no', align: 'left',width:90
		 		},{ 
		 			display: '制单人', name: 'maker_name', align: 'left',width:90
		 		},{
		 			display : '科室ID', name : 'dept_id', width : 80, hide:true
		 		},{
		 			display : '科室NO', name : 'dept_no', width : 80, hide:true
		 		},{
		 			display : '主表ID', name : 'out_id', width : 80, hide:true
		 		},{
		 			display : '明细ID', name : 'detail_id', width : 80, hide:true
		 		},{
		 			display : '注册证ID', name : 'cert_id', width : 80, hide:true
		 		}],
		 		dataAction: 'server',dataType: 'server', usePager: false,
		 		url:'queryAffiOut.do?isCheck=false',
				width: '99%', height: '97%', checkbox: true,rownumbers:true,frozen:false,
				delayLoad: true,//初始化不加载，默认false
				selectRowButtonOnly:true,heightDiff: 0,
				showTitle: true,
				checkBoxDisplay : isCheckDisplay,
				toolbar: { items: [
					{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
					{ line:true },
					{ text: '生成专购品（<u>S</u>）', id:'add', click: affiImp, icon:'add' },
					{ line:true },
					{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' },
					{ line:true },
    				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
				]},
				onDblClickRow : function (rowdata, rowindex, value){
						changeSupCode(rowdata);
					}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
	//复选框是否可用
	function isCheckDisplay(rowdata) {
		if (rowdata.out_id == null)
			return false;
		return true;
	}
	
	function remove(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				
				ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.out_id  +"@"+ 
						this.detail_id  +"@"+ 
						this.amount  
					) 
				
			});
			
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("addAffiRela.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('S', affiImp);
		hotkeys('C', this_close);
	}
	
	//改变供应商
	function changeSupCode(rowdata){		
		liger.get("sup_id").setValue(rowdata.sup_id+","+rowdata.sup_no);
		liger.get("sup_id").setText(rowdata.sup_name);
		//alert(rowdata.sup_code+" "+rowdata.sup_name);
		query();
    }
    
    //生成专购品 
  	function affiImp(){
		var data = gridManager.getCheckedRows();
		if(liger.get("in_store_id").getValue() == null || liger.get("in_store_id").getValue() == ""){
 			$.ligerDialog.warn("仓库不能为空");  
 			return false;  
 		}
		
		if(data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
			//var ParamVo =[];
			var paras;
			var out_ids;
			var detail_ids;
			$(data).each(function (index, vo){	
				if(index == 0){
					paras = "&group_id=" + vo.group_id
						+ "&hos_id=" + vo.hos_id
						+ "&copy_code=" + vo.copy_code
						+ "&store_id=" + vo.store_id
						+ "&in_store_id=" + (liger.get("in_store_id").getValue() == null ? "" : liger.get("in_store_id").getValue().split(",")[0])
						+ "&by_inv_sup=" + ($("#by_inv_sup").prop("checked") ? 1 : 0);
					out_ids = vo.out_id;
					detail_ids = vo.detail_id;
				}else{
					out_ids += "," + vo.out_id;
					detail_ids += "," + vo.detail_id;
				}
			});
			paras += "&out_ids=" + out_ids + "&detail_ids=" + detail_ids;
			/* $(data).each(function (){		
				ParamVo.push(
						this.group_id +"@"+
						this.hos_id +"@"+
						this.copy_code +"@"+
						this.out_id +"@"+
						this.out_no +"@"+
						this.detail_id +"@"+
						this.store_id +"@"+
						this.store_no +"@"+
						this.dept_id +"@"+
						this.dept_no +"@"+
						this.sup_id +"@"+ 
						this.sup_no 
				) 
			}); */
			
			$.ligerDialog.confirm('确定汇总生成专购品？', function (yes){
				var para = {
						 
				};
				if(yes){
			        //ajaxJsonObjectByUrl("affiDate.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
			        ajaxJsonObjectByUrl("affiDate.do?isCheck=false", paras, function(responseData){
			            if(responseData.state=="true"){
			            	//query();
			            	parentFrameUse().query();
			            	parentFrameUse().update_open(responseData.update_para);
			            	this_close();
			            }
			        });
				}
			});
		}
	}
    
	function this_close(){
		frameElement.dialog.close();
	}
   
    function loadDict(){
		//字典下拉框
		autocompleteAsync("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true, {"is_com" : 1}, true);
		
		//autocompleteAsync("#store_id", "../../queryMatStoreDictPro.do?isCheck=false", "id", "text", true, true, {"is_com" : 1}, true);
		//入库库房
		autocomplete("#in_store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true);
		//采购部门
    	autocomplete("#dept_id", "../../queryMatDeptDict.do?isCheck=false", "id", "text", true, true, {"is_last" : 1}, false);
    	//供应商
    	autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", false);

    	autocomplete("#maker", "../../../sys/queryUserDict.do?isCheck=false",  "id", "text", true, true);
    	
    	$("#sup_id").ligerTextBox({width:220});
    	$("#begin_out_date").ligerTextBox({width:90});
    	$("#end_out_date").ligerTextBox({width:90});
    	$("#brief").ligerTextBox({width:220});
    	 $("#inv_code").ligerTextBox({width:160});
	}  
	
	</script> 
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
        		<b><font color="red">*</font>使用仓库:</b>
        	</td>
            <td align="left" class="l-table-edit-td">
            	<input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  ><b>出库日期:</b></td>
            <td align="left" class="l-table-edit-td">
            	<table>
            		<tr>
            			<td>
            				<input class="Wdate" name="begin_out_date" id="begin_out_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
				        <td align="left"  class="l-table-edit-td">至：</td>
				        <td align="left"  class="l-table-edit-td">
				        	<input class="Wdate" name="end_out_date" id="end_out_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
				        </td>
            		</tr>
            	</table>
            </td>
	        
			<td align="right" class="l-table-edit-td"><b>领料科室:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="dept_id" type="text" id="dept_id" ltype="text"  validate="{required:false}" />
            </td>
		</tr>
		<tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
        		<b><font color="red">*</font>入库仓库:</b>
        	</td>
            <td align="left" class="l-table-edit-td">
            	<input name="in_store_id" type="text" id="in_store_id" ltype="text" validate="{required:true}" />
            </td>
            
            <td align="right" class="l-table-edit-td"><b>供应商:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_id" type="text" id="sup_id" ltype="text"  validate="{required:false}" />
            </td>
            
            <td align="right" class="l-table-edit-td"><b>摘要:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="brief" type="text" id="brief" ltype="text"  validate="{required:false}" />
            </td>
        </tr> 
        <tr>
       		<td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td"></td>
            <td align="left" class="l-table-edit-td">
            	<input name="by_inv_sup" type="checkbox" id="by_inv_sup" onclick="query();"/>&nbsp;&nbsp;按材料供应商&nbsp;&nbsp;
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td"><input name="maker" type="text" id="maker" ltype="text" validate="{maxlength:20}" /></td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
