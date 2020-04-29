<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%-- <jsp:include page="${path}/inc.jsp"/> --%>
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script src="<%=path%>/lib/stringbuffer.js"></script>
    <script type="text/javascript">
     var dataFormat;
     var clicked = 0;
     var selectData = "";
     var grid;
     var gridManager = null;
     var is_quote = 0;
     var is_dir = 0;

     $(function (){
        loadDict();//加载下拉框
        loadHead(null);//加载数据
     });

   	 //查询
     function  query(){
     		grid.options.parms=[];
     		grid.options.newPage=1;
     		grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()});
     		grid.options.parms.push({name:'end_date',value:$("#end_date").val()});
     		grid.options.parms.push({name:'req_code',value : $("#req_code").val()});
       	    grid.options.parms.push({name:'stock_id',value:liger.get("stock_id").getValue() == ''?'':liger.get("stock_id").getValue().split(",")[0]});//编制部门
    		grid.options.parms.push({name:'is_dir',value : is_dir});
    		
    		if('${p08031}' == 0){
    			//是：提交状态; 否：审核状态
    			grid.options.parms.push({name : 'state',value : 2 });
    		}else{
    			grid.options.parms.push({name : 'state',value : 3 });
    		}
     		//加载查询条件
     		grid.loadData(grid.where);
      }

     function loadHead(){

    	 grid = $("#maingrid").ligerGrid({
             columns: [
                       { display: '需求计划编号', name: 'req_code', align: 'left',minWidth : 150,render:
                      	 	function(rowdata,rowindex,value){
	                    	 	return '<a href=javascript:openUpdate("'
									+ rowdata.req_id
									+ '")>'+rowdata.req_code+'</a>';
                   	 		}
  					 	},
  					   { display: '需求来源', name: 'come_from', align: 'left',minWidth : 120},
                       { display: '编制日期', name: 'make_date', align: 'left',minWidth : 120},
  					   { display: '申请科室', name: 'dept_name', align: 'left',minWidth : 120},
  					   { display: '仓库',    name: 'store_name', align: 'left',minWidth : 120},
					   { display: '药品编码', name: 'inv_code', align: 'left',minWidth : 120},
				  	   { display: '药品名称', name: 'inv_name', align: 'left',minWidth : 120},
					   { display: '规格型号', name: 'inv_model', align: 'left',minWidth : 120},
					   { display: '计量单位', name: 'unit_name', align: 'left',minWidth : 120},
					   { display: '需求数量', name: 'req_amount', align: 'right',minWidth : 120,
						   render:function(rowdata){
				            	return formatNumber(rowdata.req_amount ==null ? 0 : rowdata.req_amount,2,1);
				           }   
					   },
					   { display: '采购数量', name: 'pur_amount', align: 'right',minWidth : 120,
						   render:function(rowdata){
				            	return formatNumber(rowdata.pur_amount ==null ? 0 : rowdata.pur_amount,2,1);
				           },editor : {type : 'number'}   
					   }
               		],
                         dataAction: 'server',dataType: 'server',usePager:true,
                         url:'queryMedReqDeptMain.do?isCheck=false',
                         width: '100%', height: '100%', checkbox: true, 
		                 rownumbers:false,delayLoad:true,
		                 enabledEdit : true,
				 		 fixedCellHeight:true, 
				 		 onBeforeEdit : f_onBeforeEdit, 
				 		 onBeforeSubmitEdit : f_onBeforeSubmitEdit,
				 		 onAfterEdit : f_onAfterEdit,
		                 selectRowButtonOnly:true, isScroll :true ,
		                 toolbar: { items:[
		                            { text: '查询（<u>Q</u>）', id:'query', click: query ,icon:'search' },
		                            { line:true },
		                            { text: '删除（<u>D</u>）', id:'delete', click: deleteRows ,icon:'delete' },
		                            { line:true },
		                            { text: '生成采购计划（<u>G</u>）', id:'add', click: addNew ,icon:'add' },
		                            { line:true },
		                            { text: '关闭（<u>L</u>）', id:'close', click: this_close ,icon:'close' }
		                   		]
		                 },
		                 onDblClickRow : function (rowdata, rowindex, value){
		                	 changeStore(rowdata);
		            	 }
                     });

          gridManager = $("#maingrid").ligerGetGridManager();
         
     }
     
	 //双击行重新查询
     function changeStore(rowdata){
    	liger.get("stock_id").setValue(rowdata.store_id+","+rowdata.store_no);
 		liger.get("stock_id").setText(rowdata.store_code+" "+rowdata.store_name);	
 		query();
     }
     
   //编辑之前
 	function f_onBeforeEdit(e) {
 		detail_id = e.rowindex;
 		detailClicked = 0;
 		detail_name = e.column.name;		
 	}
 	
 	// 编辑单元格提交编辑状态之前作判断限制
 	function f_onBeforeSubmitEdit(e) {
 		if (e.column.name == "pur_amount" && e.value != ""){
			if(e.record.req_amount < e.value){
				return false;
			} 	
		}
 		return true;
 	}
 	
 	//编辑之后动作
 	function f_onAfterEdit(e){
 		if(e.value != "" && e.value != 0){
			if (e.column.name == "pur_amount"){
				if(e.record.req_amount < e.value){
					$.ligerDialog.warn("采购数量不能大于需求数量！");	
				 	return false;
				} 		
			}
		}
 		
 		return true;
 	}
 	
 	//删除
 	function deleteRows(){
 		var json = gridManager.getCheckedRows();
         if (json.length <= 0){
         	$.ligerDialog.error('请选择要删除的行！');
         	return;
         }else{
         	gridManager.deleteSelectedRow();
         }
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
				detail_rows.append('"inv_no":').append(data.inv_no).append(','); 
				detail_rows.append('"req_id":').append(data.req_id).append(',');
				detail_rows.append('"dept_id":').append(data.dept_id).append(',');
				detail_rows.append('"dept_no":').append(data.dept_no).append(',');
				detail_rows.append('"req_detail_id":').append(data.req_detail_id).append(',');
				detail_rows.append('"req_amount":').append(data.req_amount).append(',');
				detail_rows.append('"pur_amount":').append(data.pur_amount).append('}');
			});
			detail_rows.append("]");
			
			var formPara = {
				store_id :  liger.get("stock_id").getValue().split(",")[0],
				store_no :  liger.get("stock_id").getValue().split(",")[1],
				store_text : liger.get("stock_id").getText(),
				allData : detail_rows.toString()
			};
			
			ajaxJsonObjectByUrl("queryDeptCollectData.do?isCheck=false", formPara, function(responseData) {
				var detailData = responseData;
				var paras ="store_id="+liger.get("stock_id").getValue().split(",")[0] + 
					"&store_no="+liger.get("stock_id").getValue().split(",")[1] + 
					"&store_text="+liger.get("stock_id").getText()+"&is_dir="+is_dir+"&is_dept=1";		
				
				parent.$.ligerDialog.open({ 
					title: '采购需求计划添加',
					height: $(window).height(),
					width: $(window).width(),
					url : 'hrp/med/purchase/make/medRequireProdAddPage.do?isCheck=false&'+paras, 
					data : detailData,
					modal:true,showToggle:false,showMax:true ,showMin: false,isResize:true,
					parentframename: window.name  //用于parent弹出层调用本页面的方法或变量
				}); 
			});	
			parentFrameUse().query();
			
		}
	}
	
		//关闭
		function this_close(){
			frameElement.dialog.close();
		}
		

		//打开科室需求计划单明细页面
		function openUpdate(obj){
			$.ligerDialog.open({
				url : 'medRequireDetail.do?isCheck=false&req_id='+obj,data:{}, height: 420,width: 950,top:1,
				title:'仓库需求计划单明细',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true ,
				buttons: [ { text: '关闭', onclick: function (item, dialog) { dialog.close(); } }]
			});
		}

		//加载字典下拉框
		function loadDict() {
			autodate("#begin_date", "yyyy-mm-dd", "month_first");
			autodate("#end_date", "yyyy-mm-dd", "month_last");
			autocomplete("#stock_id","../../queryMedStore.do?isCheck=false","id","text",true,true,"",false,false,'160');//仓库信息

			$("#stock_id").ligerTextBox({width:160});
		    $("#req_code").ligerTextBox({width:160});
		    
		    $("#begin_date").ligerTextBox({width:160});
		    $("#end_date").ligerTextBox({width:160});
		}

		function singleSel(){
			if($("input[type='radio']:checked").val() == '0'){
				is_dir = 0;
	    	 }else{
	    		is_dir = 1;
	    	 }
		}

	</script>
</head>

   <body >
	   <div id="pageloading" class="l-loading" style="display: none"></div>
	   <form name="form1" method="post"  id="form1" >
	        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border='0'>
		        <tr>
		        	<td align="right" class="l-table-edit-td"  ><font color="red" size="2">*</font>编制日期： </td>
					<td align="left" class="l-table-edit-td" width='100px;'>
						<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
					</td>
					<td align="right" class="l-table-edit-td"  width="10px;"> 至： </td>
					<td align="left" class="l-table-edit-td">
						<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
					</td>
					
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
		            <td align="left" class="l-table-edit-td"><input name="stock_id" type="text" id="stock_id" ltype="text"  validate="{required:true,maxlength:20}"/></td>
		            <td align="left"></td>

		            <td align="right" class="l-table-edit-td" style="padding-left: 10px;">单据号：</td>
					<td align="left" class="l-table-edit-td" colspan="3">
						<input name="req_code" type="text" requried="false" id="req_code" />
					</td>
					<td align="left"></td>
		        </tr>
		        <tr>
		          	<td align="left"></td>
		            <td align="left" class="l-table-edit-td" colspan="3">
		            	<!-- <input type="checkbox" id="is_quote" name="is_quote" />包含已引用&nbsp;&nbsp;&nbsp; -->
		            	<input id="dirN" name="is_dir" type="radio" value="0" checked="checked" onclick="singleSel()" />&nbsp;非定向 &nbsp;&nbsp;&nbsp;
		            	<input id="dirY" name="is_dir" type="radio" value="1" onclick="singleSel()" />&nbsp;定向

		            </td>
		            <td align="left"></td>
		        </tr>
	    	</table>
	    	<div id="maingrid"></div>
	    </form>
    </body>
</html>
