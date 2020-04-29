<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%-- <jsp:include page="${path}/inc.jsp"/> --%>
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
     var dataFormat;
     var clicked = 0;
     var selectData = "";
     var grid;
     var gridManager = null;
     $(function (){
        loadDict();//加载下拉框
        loadHead(null);//加载数据
      	
     }); 
     
   //查询
     function  query(){
     		grid.options.parms=[];
     		grid.options.newPage=1;
     		
     	//加载查询条件
     	grid.loadData(grid.where);
 		
      }
     
     function loadHead(){
    	 
    	 grid = $("#maingrid").ligerGrid({
             columns: [ 
                       { display: '药品编码', name: 'inv_code', align: 'left',minWidth : 100},
                       { display: '药品名称', name: 'inv_name', align: 'left',minWidth : 80},
  					   { display: '规格型号', name: 'inv_model', align: 'left',minWidth : 80},
					   { display: '计量单位', name: 'unit_name', align: 'left',minWidth : 80},
				  	   { display: '计划数量', name: 'amount', align: 'left',minWidth : 80,
						   render:function(rowdata){
								  return formatNumber(rowdata.amount ==null ? 0 : rowdata.amount,2,1);
				             }
				  	   },
					   { display: '单价', name: 'price', align: 'left',minWidth : 80,
				  		 	render:function(rowdata){
							  	return formatNumber(rowdata.price ==null ? 0 : rowdata.price,2,1);
			             	}		
					   },
					   { display: '金额', name: 'amount_money', align: 'left',minWidth : 80,
						   	render:function(rowdata){
								return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money,2,1);
				           	}
					   },
					   { display: '需求日期', name: 'rdate', align: 'left',minWidth : 80 }
                       ],
                       dataAction: 'server',dataType: 'server',usePager:true,
                       url:'queryMedReqByCode.do?isCheck=false&req_id='+'${req_id}',
                       width: '100%', height: '100%', checkbox: false,rownumbers:true,
                       enabledEdit : true,alternatingRow : true,
                       isScroll : true,
                       selectRowButtonOnly:true,
                       toolbar: { items: [
							{ text: '查询', id:'search', click: query,icon:'search' },
							{ line:true }	
  				       ]} 
                     });

          gridManager = $("#maingrid").ligerGetGridManager();
          
         
     }
     
     
    

		function save() {
			
	    	/* var allData = gridManager.getData();
	    	
	    	if(allData.length == 0){
	    		$.ligerDialog.error('请添加采购计划明细');
	    		return ; 
	    	}
	    	
	    	var flag = true;
	    	
	    	if(allData.length != 0){
	    		
		    	$(allData).each(function(){
		    		
		    		if(this.inv_name == undefined || this.inv_name == ''){
		    			
		    			$.ligerDialog.error('请选择药品名称');
		    			
		    			return flag = false;
		    		}
		    		
					 if(this.price == undefined || this.price == ''){
		    			
		    			$.ligerDialog.error('请填写单价');
		    			
		    			return flag = false;
		    		} 
		    	}); 
		    	
		    	if(!flag){
		    		return;
		    	}
	    	} */
	    	
	    	
			var formPara = {
				//pur_code : $('#pur_code').val(),//计划单号
				dept_id : liger.get("dept_id").getValue().split(",")[0],//编制科室
				dept_no:liger.get("dept_id").getValue().split(",")[1],
				make_date:$("#make_date").val(),//编制日期
				pur_type:$("input[type='radio']:checked").val(),//计划类型
				brif:$("#brif").val(),//摘要
				pur_hos_id:liger.get("pur_hos_id").getValue().split(",")[0],//采购单位
				req_hos_id:liger.get("req_hos_id").getValue().split(",")[0],//请购单位
				pay_hos_id:liger.get("pay_hos_id").getValue().split(",")[0],//付款单位
				allData : JSON.stringify(allData),//获取所有数据
				
			};
			
			ajaxJsonObjectByUrl("addMedPurMain.do?isCheck=true", formPara,function(responseData) {

				if (responseData.state == "true") {
						$("input[name='dept_id']").val('');
						$("input[name='make_date']").val('');
						$("input[name='brif']").val('');
						$("input[name='pur_hos_id']").val('');
						$("input[name='req_hos_id']").val('');
						$("input[name='pay_hos_id']").val('');
						parent.query();
				}
			});
		}

		//保存
		function saveMedPurMain() {
				
				save();
		}
		
		//加载字典下拉框
		function loadDict() {		
			autocomplete("#stock_id","../../queryMedStore.do?isCheck=false","id","text",true,true);//仓库信息
			$("#req_code").ligerTextBox({width:160,disabled:true});
	        $("#stock_id").ligerTextBox({width:160,disabled:true});
	        liger.get("stock_id").setValue("${stock_id},${stock_no}");
	        liger.get("stock_id").setText("${stock_code}"+" "+"${stock_name}");
		}
		
		</script>
  </head>
  
   <body >
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划单号：</td>
            <td align="left" class="l-table-edit-td"><input name="req_code" type="text" id="req_code" ltype="text"  validate="{required:true,maxlength:20}" value="${req_code}" disabled="disabled"/></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">汇总仓库：</td>
            <td align="left" class="l-table-edit-td"><input name="stock_id" type="text" id="stock_id" ltype="text"  validate="{required:true,maxlength:20}" disabled="disabled"/></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">编制日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="make_date" type="text" id="make_date" ltype="text"  
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" value="${make_date}" disabled="disabled"/></td>
            <td align="left"></td>
            
        </tr>
    </table>
    
    <div id="maingrid"></div>
    </form>
   
    </body>
</html>
