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
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var is_warn = 1;
	var is_store = 0;
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
        //query();
		$("#is_warn").bind('change', function (){
			if($('#is_warn').is(':checked')) {
	        	is_warn = 1;
			}else{
				is_warn = 0;
			} 
		}); 
		
		$("#is_store").bind('change', function (){
			if($('#is_store').is(':checked')) {
				is_store = 1;
			}else{
				is_store = 0;
			} 
		}); 
		
	});
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		grid.options.parms.push({name : 'store_id',value : '${store_id}'});
	
		grid.options.parms.push({name : 'med_type_code',value : liger.get("med_type_id").getText() == null ? "" : liger.get("med_type_id").getText().split(" ")[0] });
		grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val() });
		grid.options.parms.push({name:'is_warn',value : is_warn});
		grid.options.parms.push({name:'is_store',value : is_store});
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
                            {display : '药品编码',name : 'inv_code',align : 'left',width:'90'}, 
                            {display : '药品名称',name : 'inv_name',align : 'left',width:'100'}, 
                            {display : '规格型号',name : 'inv_model',align : 'left',width:'100'}, 
                            {display : '计量单位',name : 'unit_name',align : 'left'},
                            {display : '包装单位',name : 'pack_name',align : 'left'}, 
                            {display : '转换量',name : 'num_exchange',align : 'right',
                            	render:function(rowdata){
				            		return formatNumber(rowdata.num_exchange ==null ? 0 : rowdata.num_exchange,2,1);
				             	}	
                            }, 
                            {display : '预警周期',name : 'period_num',align : 'right',
                            	render:function(rowdata){
				            		return formatNumber(rowdata.period_num ==null ? 0 : rowdata.period_num,2,1);
				             	}	
                            }, 
				            {display : '周期单位',name : 'period',align : 'left',
                            	render : function(rowdata, rowindex, value) {
		     						if(rowdata.period == 1){
		     							return "年";
		     						}else if(rowdata.period == 2){
		     							return "季";
		     						}else if(rowdata.period == 3){
		     							return "月";
		     						}else if(rowdata.period == 4){
		     							return "天";
		     						}else{
		     							return "";
		     						}
		     					}	
				            }, 
				            {display : '临近预警量',name : 'warn_amount',align : 'right',
				            	render:function(rowdata){
				            		return formatNumber(rowdata.warn_amount ==null ? 0 : rowdata.warn_amount,2,1);
				             	}	
				            },
				            {display : '结存数量',name : 'cur_amount',align : 'right',
				            	render:function(rowdata){
				            		return formatNumber(rowdata.cur_amount ==null ? 0 : rowdata.cur_amount,2,1);
				             	}	
				            },
				            {display : '安全库存量',name : 'secu_limit',align : 'right',
				            	render:function(rowdata){
				            		return formatNumber(rowdata.secu_limit ==null ? 0 : rowdata.secu_limit,2,1);
				             	}	
				            },
				            {display : '差量',name : 'differ_amount',align : 'right',
				            	render:function(rowdata){
				            		return formatNumber(rowdata.differ_amount ==null ? 0 : rowdata.differ_amount,2,1);
				             	}	
				            },
				            {display : '包装差量',name : 'differ_num',align : 'right',
				            	render:function(rowdata){
				            		return formatNumber(rowdata.differ_num ==null ? 0 : rowdata.differ_num,2,1);
				             	}	
				            }
		                ],
		                 dataAction: 'server',dataType: 'server',usePager:true,
		                 url:'queryMedDeptRequeriedPlanSafe.do?isCheck=false',
		                 width: '95%', height: '75%', checkbox: true, 
		                 rownumbers:false,delayLoad:true,
		                 selectRowButtonOnly:true, isScroll :true ,
		                 toolbar: { items: 
		                	 	[
		                          { text: '查询（<u>Q</u>）', id:'query', click: query ,icon:'search' },
		                            { line:true },
		                            {text: '添加（<u>A</u>）', id:'add', click: addNew ,icon:'add' },
		                            { line:true },
		                            {text: '关闭（<u>C</u>）', id:'close', click: this_close ,icon:'colse' }
		                   		]
		                 }
		               });

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	//获得选中行数
	function getSelectRows() {
		var rows = grid.getCheckedRows();
		return rows;
	}
	//确定添加
	function addNew(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择药品！');
			return;
		}else{
			var detail_rows = new StringBuffer();
			detail_rows.append("[");
			$.each(data, function(index, data){
				if(index != 0){
					detail_rows.append(",");
				}
				//amount = 差量的绝对值; num= 包装差量的绝对值
				detail_rows.append('{"inv_id":').append(data.inv_id).append(',');
				detail_rows.append('"inv_code":"').append(data.inv_code).append('",');
				detail_rows.append('"inv_name":"').append(data.inv_name).append('",');
				detail_rows.append('"inv_no":').append(data.inv_no).append(','); 
				detail_rows.append('"inv_model":"').append(data.inv_model).append('",');
				detail_rows.append('"unit_code":"').append(data.unit_code).append('",');
				detail_rows.append('"unit_name":"').append(data.unit_name).append('",');
				detail_rows.append('"pack_code":"').append(data.pack_code).append('",');
				detail_rows.append('"pack_name":"').append(data.pack_name).append('",');
				detail_rows.append('"fac_id":').append(data.fac_id).append(','); 
				detail_rows.append('"fac_no":').append(data.fac_no).append(','); 
				detail_rows.append('"fac_name":"').append(data.fac_name).append('",');
				//detail_rows.append('"num_exchange":').append(data.num_exchange).append(',');
				detail_rows.append('"sup_id":').append(data.sup_id).append(','); 
				detail_rows.append('"sup_no":').append(data.sup_no).append(','); 
				detail_rows.append('"sup_name":"').append(data.sup_name).append('",');
				detail_rows.append('"num":').append(data.abs_num).append(',');
				detail_rows.append('"amount":').append(data.abs_differ).append(',');
				detail_rows.append('"price":').append(data.price).append(',');
				detail_rows.append('"sum_money":').append(data.price*data.abs_differ).append('}');
			});
			detail_rows.append("]");
			var p_data = parent.gridManager.rows;//获取父页面数据
    		parent.deleteRange(p_data);//删除父页面数据
    		//alert(detail_rows.toString());
			//添加药品
			parent.add_rows(eval(detail_rows.toString()));
			this_close();
		}
		
	}
	
	
	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('D', remove);//删除
		hotkeys('N', addNew);//确定添加
		hotkeys('C', this_close);//关闭
	}
	
	//字典加载
	function loadDict() {
		autocomplete("#store_code","../../../queryMedStore.do?isCheck=false","id","text",true,true);//仓库
        liger.get("store_code").setValue("${store_id}");
        liger.get("store_code").setText("${store_name}");
        $("#store_code").ligerComboBox({disabled:true,cancelable: false});
        
        autocomplete("#med_type_id","../../../queryMedTypeDict.do?isCheck=false","id","text",true,true,"",true);//仓库
        $("#med_type_id").ligerTextBox({width:160});
		$("#store_code").ligerTextBox({width:160});
		$("#inv_code").ligerTextBox({width:160});
	}
	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		<tr>
		  	<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b><font color="red">*</font>仓库：</b></td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="store_code" type="text" requried="true"  id="store_code"  />
	        </td>
	        <td align="left"></td>
	        
           	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font>药品分类:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="med_type_id" type="text" id="med_type_id" ltype="text" validate="{maxlength:20}" />
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td" style="padding-left: 10px;">药品信息:</td>
			<td align="left" class="l-table-edit-td" colspan="3">
				<input name="inv_code" type="text" requried="false" id="inv_code" />
			</td>
			<td align="left"></td>
			
	    
		</tr>
		<tr>
			<td class="l-table-edit-td" style="padding-left: 10px;" align="right">
				<input type="checkbox" id="is_warn" checked/>&nbsp;&nbsp;只显示预警范围内药品
			</td>
		    <td class="l-table-edit-td" style="padding-left: 10px;" align="left">
		    	<input type="checkbox" id="is_store" />&nbsp;&nbsp;显示所有仓库药品
		    </td>
		</tr>
	</table>
	<div id="maingrid"></div>	
</body>
</html>
