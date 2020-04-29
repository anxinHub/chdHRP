<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>

<script type="text/javascript">
	var wbal_amt_check = 0;
	var check_code_check = 0;
	var con_no_check = 0;
	var business_no_check = 0;
	var occur_date_check = 0;
	var due_date_check = 0;
	var summary_check = 0; 

     var dataFormat;  
     var subj_code;
     var check_type;
     var acc_monthB;
     var acc_monthE;
     var proj1;
     var proj2;
     
     var grid1;
     var gridManager1 = null;    
  	 var grid2;    
     var gridManager2 = null; 
     
     $(function (){ 
    	
    	 subj_code = "${subj_code}";
         check_type = "${check_type}";
         proj1 = "${proj1}";
         proj2 = "${proj2}";
         acc_monthB = "${acc_monthB}";
         acc_monthE = "${acc_monthE}"; 
        loadDict();//加载下拉框        
        loadForm();
        loadHead(null);	//加载数据
       
     });  
     
   function loadHead(){
	   grid1 = $("#maingrid1").ligerGrid({
	        columns: [ 
	        			{ display: '核算ID', name: 'check_id', hide:true },
						{ display: '凭证日期', name: 'vouch_date', align: 'left'},
	                    { display: '凭证号', name: 'vouch_no', align: 'left'},
	                    { display: '往来项目', name: 'check_name', align: 'left'
	                    },
						{ display: '摘要', name: 'summary', align: 'left'},
						{ display: '借方金额', name: 'debit', align: 'right',
							render:function(rowdata){
								return formatNumber(rowdata.debit ==null ? 0 : rowdata.debit,2,1);
							 }
						},
						{ display: '未核销金额', name: 'wbal_amt', align: 'right',
							render:function(rowdata){
								  return formatNumber(rowdata.wbal_amt ==null ? 0 : rowdata.wbal_amt,2,1);
							 }
						},
						
					    { display: '发生日期', name: 'occur_dated', align: 'left'},
						{ display: '到期日期', name: 'due_date', align: 'left'},
						{ display: '合同号', name: 'con_no', align: 'left'},
						{ display: '单据号', name: 'business_no', align: 'left'}
						
	                  ],
	                  dataAction: 'server',dataType: 'server',usePager:true,url:'.do',
	                  width: '100%', height: 200, checkbox: true,rownumbers:true,
	                  selectRowButtonOnly:true,//heightDiff: -10,
	                 
	                  toolbar: { 
	                	  items: [
	                     	
	                     	{ text: '批量核销', id:'batchVer', click : itemclick ,icon:'' },
	                     	{ line:true },
	                     	{ text: '查询', id:'search', click : itemclick,icon:'search' },
	                     	{ line:true },
	                     	{ text: '关闭', id:'close', onclick: function (item, dialog) { dialog.close(); },icon:'' },
	                     	{ line:true }
	    				]}
	                   });
	        gridManager1 = $("#maingrid1").ligerGetGridManager();
	 
	        grid2 = $("#maingrid2").ligerGrid({
	            columns: [ 
						 { display: '核算ID', name: 'check_id', hide:true },
	 					 { display: '凭证日期', name: 'vouch_date', align: 'left'},
	                     { display: '凭证号', name: 'vouch_no', align: 'left'},
	                     { display: '往来项目', name: 'check_name', align: 'left'},
	 					 { display: '摘要', name: 'summary', align: 'left'},
	 					 { display: '贷方金额', name: 'credit', align: 'right',
							render:function(rowdata){
								  return formatNumber(rowdata.credit ==null ? 0 : rowdata.credit,2,1);
							 }
						},
						
						{ display: '未核销金额', name: 'wbal_amt', align: 'right',
							render:function(rowdata){
								  return formatNumber(rowdata.wbal_amt ==null ? 0 : rowdata.wbal_amt,2,1);
							 }
						},
	 					 { display: '发生日期', name: 'occur_date', align: 'left'},
	 					 { display: '到期日期', name: 'due_date', align: 'left'},
	 					 { display: '合同号', name: 'con_no', align: 'left'},
	 					 { display: '单据号', name: 'business_no', align: 'left'}
	            ],
	           dataAction: 'server',dataType: 'server',usePager:true,url:'.do',
	           width: '100%', height: 200, checkbox: true,rownumbers:true,
	           selectRowButtonOnly:true
	       });

	         gridManager2 = $("#maingrid2").ligerGetGridManager();
   }
    function itemclick(item){
    	if($("#wbal_amt_check").attr("checked") == true){
    		wbal_amt_check = 1;
    	}
    	if($("#check_code_check").attr("checked") == true){
    		check_code_check = 1;
    	}
    	if($("#con_no_check").attr("checked") == true){
    		con_no_check = 1;
    	}
    	if($("#business_no_check").attr("checked") == true){
    		business_no_check = 1;
    	}
    	if($("#occur_date_check").attr("checked") == true){
    		occur_date_check = 1;
    	}
    	if($("#due_date_check").attr("checked") == true){
    		due_date_check = 1;
    	}
    	if($("#summary_check").attr("checked") == true){
    		summary_check = 1;
    	}
    	 
        var formPara={
        
        	subj_code : subj_code,
	        check_type : check_type,
	        proj1 : proj1,
	        proj2 : proj2,
	        acc_monthB : acc_monthB,
	        acc_monthE : acc_monthE,
	        wbal_amt_check : wbal_amt_check,
	        check_code_check : check_code_check ,
	        con_no_check : con_no_check,
	        business_no_check : business_no_check,
	        occur_date_check : occur_date_check,
	        due_date_check : due_date_check,
	        summary_check : summary_check
 
         };
        
    	if(item.id){
            switch (item.id){
            	case "search":
            		ajaxJsonObjectByUrl("accVerification.do",formPara,
    		    			function (responseData){
    		    				if(responseData.state=="true"){
    		    					query();
    		    				}
    		    			}
    		    		);
            		return;
            	case "batchVer":
            	
            	return;
            }
        }
    }
   
    function AddAccBatchVerification(){
    	
    	
    	
    	if($("#wbal_amt_check").attr("checked") == true){
    		wbal_amt_check = 1;
    	}
    	if($("#check_code_check").attr("checked") == true){
    		check_code_check = 1;
    	}
    	if($("#con_no_check").attr("checked") == true){
    		con_no_check = 1;
    	}
    	if($("#business_no_check").attr("checked") == true){
    		business_no_check = 1;
    	}
    	if($("#occur_date_check").attr("checked") == true){
    		occur_date_check = 1;
    	}
    	if($("#due_date_check").attr("checked") == true){
    		due_date_check = 1;
    	}
    	if($("#summary_check").attr("checked") == true){
    		summary_check = 1;
    	}
    	 
        var formPara={
        
        	subj_code : subj_code,
	        check_type : check_type,
	        proj1 : proj1,
	        proj2 : proj2,
	        acc_monthB : acc_monthB,
	        acc_monthE : acc_monthE,
	        wbal_amt_check : wbal_amt_check,
	        check_code_check : check_code_check ,
	        con_no_check : con_no_check,
	        business_no_check : business_no_check,
	        occur_date_check : occur_date_check,
	        due_date_check : due_date_check,
	        summary_check : summary_check
 
         };
        
        ajaxJsonObjectByUrl("accBatchAddVerification.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
    function loadDict(){
         
    } 
    function loadForm(){
   	 	$("form").ligerForm();
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form" method="post"  id="form" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        	<tr>
        		<td colspan="15">
        			<left><h3>说明：根据选的核销方式判断借贷方相等之后核销数据</h3></left>
        		</td>
        	</tr>
        	 
			<tr>
				<td align="right" style="padding-left:20px;">核销方式:</td>
				
                <td style="padding-left:20px;" align="right" class="l-table-edit-td">
                	<input name="wbal_amt_check" type="checkbox" id="wbal_amt_check"  checked="true" readonly disabled/>
                </td>
            	<td align="left" >未核销金额</td>
            	<td style="padding-left:20px;" align="right" class="l-table-edit-td">
            		<input name="check_code_check" type="checkbox" id="check_code_check" checked="true" readonly  disabled/>
            	</td>
            	<td align="left">往来项目</td>
            	<td style="padding-left:20px;" align="right" class="l-table-edit-td">
            		<input name="con_no_check" type="checkbox" id="con_no_check"   />
            	</td>
            	<td align="left">合同号</td>
            	<td style="padding-left:20px;" align="right" class="l-table-edit-td">
            		<input name="business_no_check" type="checkbox" id="business_no_check"   />
            	</td>
            	<td align="left">单据号</td>
            	<td style="padding-left:20px;" align="right" class="l-table-edit-td">
            		<input name="occur_date_check" type="checkbox" id="occur_date_check"   />
            	</td>
            	<td align="left">发生日期</td>
            	<td style="padding-left:20px;" align="right" class="l-table-edit-td">
            		<input name="due_date_check" type="checkbox" id="due_date_check"   />
            	</td>
            	<td align="left">到期日期</td>
            	<td style="padding-left:20px;" align="right" class="l-table-edit-td">
            		<input name="summary_check" type="checkbox" id="summary_check"   />
            	</td>
            	<td align="left">摘要</td>
            </tr> 
            
        </table>
      </form>  
	  <div id="maingrid1"></div>
	  <div id="maingrid2"></div>
</body>
</html>
