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
			name : 'store_id',
			value : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]
		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
			     {display: '单据号', name: 'special_no', align: 'left', width: 130,
						render : function(rowdata, rowindex, value) {
							return '<a href=javascript:update_open("' 
								+ rowdata.group_id 
								+ ',' + rowdata.hos_id 
								+ ',' + rowdata.copy_code 
								+ ',' + rowdata.special_id
								+ ',' + rowdata.special_no+'")>'+rowdata.special_no+'</a>';
						}
					},
				{display: '单据类型', name: 'bus_type_code', align: 'left',width: 80,
						render : function(rowdata, rowindex, value){
							if(rowdata.bus_type_code == '01'){
								return "正常";
							}else{
								return "冲账";
							}
						}
					},
				{display: '单据来源', name: 'come_from', align: 'left',width: 90,
		 			},
				{display: '仓库', name: 'store_name', align: 'left',width: 160,
		 			},
		 		{display: '供应商', name: 'sup_name', align: 'left',width: 200,
		 			},
		 		{display: '领料科室', name: 'dept_name', align: 'left',width: 150,
		 			},
		 		{display: '采购员', name: 'stocker_name', align: 'left',width: 80,
		 			},
		 		{display: '金额', name: 'amount_money', align: 'right',width: 90,
			 			render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005}', 1);
						}
		 			}, 
				{display: '入库单号', name: 'in_no', align: 'left', width: 120,
					},
				{display: '出库单号', name: 'out_no', align: 'left', width: 120,
					}, 
				{display: '编制日期', name: 'make_date', align: 'left',width: 90,
		 			}, 
		 		{display: '制单人', name: 'maker_name', align: 'left',width: 90,
		 			},
		 		{display: '审核日期', name: 'check_date', align: 'left',width: 90,
		 			},
		 		{display: '审核人', name: 'checker_name', align: 'left',width: 90,
		 			},
		 		{display: '确认日期', name: 'confirm_date', align: 'left',width: 90,
		 			},
		 		{display: '确认人', name: 'confirmer_name', align: 'left',width: 90,
		 			},
		 		{display: '状态', name: 'state', align: 'left',width: 80,
		 				render: function(rowdata,index,value){
		 					if(rowdata.state == 0){
		 						return "验收";
		 					}else if(rowdata.state == 1){
		 						return "未审核";
		 					}else if(rowdata.state == 2){
		 						return "审核";
		 					}else if(rowdata.state == 3){
		 						return "入库确认";
		 					}else{
		 						return "财务记账";
		 					}
		 				}
		 			}
		 		],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAffiSpecial.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			selectRowButtonOnly:true,//heightDiff: -10,
			delayLoad : true,//初始化明细数据
			toolbar: { items: [
				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit' },
				{ line:true }, 
				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'candle' },
				{ line:true },
				{ text: '确认（<u>F</u>）', id:'confirm', click: confirm, icon:'right' },
				{ line:true },
				//{ text: '模板打印（<u>P</u>）', id:'print', click: print, icon:'print' }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('F', confirm);
		hotkeys('P', print);
	}
    
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"special_id="+vo[3] +"&"+ 
			"special_no="+vo[4] ;
		$.ligerDialog.open({
			title: '专购品单修改',
			height: 550,
			width: 1150,
			url: 'updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 1,
		});   
    }
    
    
	// 审核
	function audit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 1){
					in_nos = in_nos + this.in_no + ",";
				}else{
					ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.special_id  +"@"+ 
							this.special_no  +"@"+ 
							this.state  +"@"+ 2
						) 
				}
			});
			if(in_nos != ""){
				$.ligerDialog.error("审核失败！"+in_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	//消审
	function unAudit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + ",";
				}else{
					ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.special_id  +"@"+ 
							this.special_no  +"@"+ 
							this.state  +"@"+ 1
						) 
				}
				
			});
			if(in_nos != ""){
				$.ligerDialog.error("消审失败！"+in_nos+"单据不是已审核状态");
				return;
			}
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    //确认	
    function confirm(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.special_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.special_id  +"@"+ 
					this.special_no  +"@"+ 
					this.year +"@"+ 
					this.month +"@"+ 
					this.store_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("确定失败！"+in_nos+"单据不是已审核状态");
				return;
			}
			$.ligerDialog.confirm('您确定要进行确认操作吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("confirmMatSpecial.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
  	//打印数据
    function print(){
   		//有数据直接打印
   		if($("#resultPrint > table > tbody").html()!=""){
   			lodopPrinterTable("resultPrint","开始打印","专购品",true);
   			return;
   		}
   		
   		//重新查询数据，避免分页导致打印数据不全
   		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

   		var printPara={
   			  usePager:false,
   			begin_in_date:$("#begin_in_date").val(),
   	    	end_in_date:$("#end_in_date").val(),
   	    	store_id:liger.get("store_id").getValue().split(",")[0],
   	    	state:$("#state").val(),
   	    	begin_confirm_date:$("#begin_confirm_date").val(),
   	    	end_confirm_date:$("#end_confirm_date").val(),
   	    	sup_id:liger.get("sup_id").getValue().split(",")[0],
   	    	pay_bill_type:$("#pay_bill_type").val(),
   	    	special_no : $("#special_no").val()
            };

   		ajaxJsonObjectByUrl("queryMatSpecial.do?isCheck=false",printPara,function (responseData){
   			 var trHtml='';
   			$.each(responseData.Rows,function(idx,item){ 
   				 	 trHtml+="<tr>";
   					 trHtml+="<td>"+item.special_no+"</td>";
   					 if(item.bus_type_code == '01'){
   						 trHtml+="<td>正常</td>"; 
   		 				}else{
   		 					trHtml+="<td>冲账</td>";
   		 				}
   					 trHtml+="<td>"+item.store_name+"</td>"; 
   					 trHtml+="<td>"+item.sup_name+"</td>"; 
   					 trHtml+="<td>"+item.dept_name+"</td>"; 
   					trHtml+="<td>"+item.stocker_name+"</td>"; 
   					trHtml+="<td>"+item.amount_money+"</td>"; 
   					trHtml+="<td>"+item.in_no+"</td>"; 
   					trHtml+="<td>"+item.out_no+"</td>"; 
		 			trHtml+="<td>"+item.make_date+"</td>";
   					trHtml+="<td>"+item.maker_name+"</td>";
   					trHtml+="<td>"+item.check_date+"</td>"; 
   					trHtml+="<td>"+item.checker_name+"</td>"; 
   					trHtml+="<td>"+item.confirm_date+"</td>"; 
   					trHtml+="<td>"+item.confirmer_name+"</td>";
   					if(item.state == 0){
   						trHtml+="<td>验收</td>"; 
 					}else if(item.state == 1){
 						trHtml+="<td>未审核</td>"; 
 					}else if(item.state == 2){
 						trHtml+="<td>审核</td>";
 					}else if(item.state == 3){
 						trHtml+="<td>入库确认</td>";
 						return "";
 					}else{
 						trHtml+="<td>财务记账</td>";
 					}
   				 trHtml+="</tr>";
   				$("#resultPrint > table > tbody").empty();
   				$("#resultPrint > table > tbody").append(trHtml);
   			});
   			manager.close();
   			lodopPrinterTable("resultPrint","开始打印","专购品",true);
   	    },true,manager);
   		return;
    }
   
    function loadDict(){
		//字典下拉框
		autocomplete("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true);
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
		autocomplete("#dept_id", "../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true);
		
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
       <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            
        </tr> 
       
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
				<tr>
	                <th width="200">单据号</th>
	                <th width="200">单据类型</th>	
	                <th width="200">仓库</th>	
	                <th width="200">供应商</th>
	                <th width="200">领料科室</th>	
	                <th width="200">采购员</th>
	                <th width="200">金额</th>
	                <th width="200">入库单号</th>
	                <th width="200">出库单号</th>
	                <th width="200">制单日期</th>
	                <th width="200">制单人</th>
	                <th width="200">审核日期</th>	
	                <th width="200">审核人</th>	
	                <th width="200">确认日期</th>	
	                <th width="200">确认人</th>
	                <th width="200">状态</th>	
				</tr>
			</thead>
			<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
