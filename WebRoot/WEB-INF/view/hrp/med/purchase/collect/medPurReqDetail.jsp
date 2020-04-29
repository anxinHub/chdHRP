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
        
        liger.get("dept_id").setValue("${dept_id},${dept_no}");
        liger.get("dept_id").setText("${dept_no}"+" "+"${dept_name}");
        
        liger.get("pur_hos_id").setValue("${pur_hos_id}");
        liger.get("pur_hos_id").setText("${pur_hos_name}");
        
        liger.get("req_hos_id").setValue("${req_hos_id}");
        liger.get("req_hos_id").setText("${req_hos_name}");
        
        liger.get("pay_hos_id").setValue("${pay_hos_id}");
        liger.get("pay_hos_id").setText("${pay_hos_name}");
        
        liger.get("dept_id").setValue("${dept_id}");
        liger.get("dept_id").setText("${dept_name}")
        
        $("#pur_code").ligerTextBox({width:160,disabled:true});
        $("#pur_hos_id").ligerTextBox({width:160,disabled:true});
        $("#req_hos_id").ligerTextBox({width:160,disabled:true});
        $("#pay_hos_id").ligerTextBox({width:160,disabled:true});
        $("#dept_id").ligerTextBox({width:160,disabled:true});
       
     });  
    
     function loadHead(){
    	 
    	 grid = $("#maingrid").ligerGrid({
             columns: [ 
                       { display: '药品编码', name: 'inv_code', align: 'left'
  					 		},
                       { display: '药品名称',
  					     name: 'inv_name',
  					     align: 'left'
  					   },
                       { display: '规格型号', name: 'inv_model', align: 'left'
  					 		},
  					   { display: '计量单位', name: 'unit_name', align: 'left'
					 		},
					   { display: '计划数量', name: 'amount', align: 'left'
							},
					 /*   { display: '包装数量(E)', name: 'num', align: 'left'
							}, */
				       { display: '单价(E)', name: 'price', align: 'right',
								render:function(rowdata){
									return formatNumber(rowdata.price,2,1);
								}
							},
					   { display: '金额', name: 'amount_money', align: 'right',
								render:function(rowdata){
									return formatNumber(rowdata.amount_money,2,1);								
								}
							},
					 /*   { display: '计划到货日期(E)', name: 'arrive_date',align: 'left'
						    }
					 */
                       ],
                       dataAction: 'server',dataType: 'server',usePager:true,
                       width: '100%', height: '100%', rownumbers:true,
                       alternatingRow : true,
                       url:'queryMedPurDetail.do?pur_id='+'${pur_id}',
                       isScroll : true,
                       selectRowButtonOnly:true
                     });

          gridManager = $("#maingrid").ligerGetGridManager();
          
     }
     	//加载下拉列表
		function loadDict() {
						//字典下拉框
			autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true);//编制科室下拉框
						
			autocomplete("#pur_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true);//采购单位下拉框 
						
			autocomplete("#req_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true);//请购单位下拉框 
			
			autocomplete("#pay_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true);//付款单位下拉框 

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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>计划单号：</td>
            <td align="left" class="l-table-edit-td"><input name="pur_code" type="text" id="pur_code" ltype="text" validate="{required:true,maxlength:20}" value="${pur_code}" disabled="disabled"/></td>
            <td align="left"></td>
            
             
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>编制医院：</td>
            <td align="left" class="l-table-edit-td"><input name="req_hos_id" type="text" id="req_hos_id" ltype="text"  /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>编制科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text"  validate="{required:true,maxlength:20}"/></td>
            <td align="left"></td>
           
        </tr>
        
         <tr id="hos_name">
         	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>编制日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="make_date" type="text" id="make_date" ltype="text"  
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" value="${make_date}"/></td>
            <td align="left"></td>
         
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>采购单位：</td>
            <td align="left" class="l-table-edit-td"><input name="pur_hos_id" type="text" id="pur_hos_id" ltype="text" /></td>
            <td align="left"></td>
           
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>付款单位：</td>
	        <td align="left" class="l-table-edit-td"><input name="pay_hos_id" type="text" id="pay_hos_id" ltype="text"  /></td>  
            <td align="left"></td>
        </tr> 
    </table>
    
    <div id="maingrid"></div>
    </form>
   
    </body>
</html>
