<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script>
    var grid;
      
    var gridManager = null;  
    
   var check_id =null;
   var check_type = null;
   var check_type_id = null; 
   var subj_dire = null;
   var check_code = null;
   var veri_check_id =null;
   var subj_code = null;
    $(function () {
    	check_id ="${check_id}";
    	check_type = "${check_type}";
    	check_type_id = "${check_type_id}";
    	subj_dire = "${subj_dire}";
    	check_code = "${check_code}";
    	veri_check_id = "${veri_check_id}";
    	subj_code = "${subj_code}";
		loadDict();
		loadForm();
    	loadHead(null);	//加载数据
    });
	
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'veri_check_id',value:veri_check_id});
    	grid.options.parms.push({name:'check_id',value : check_id});
    	grid.options.parms.push({name:'direct',value : direct});
    	grid.options.parms.push({name:'check_type',value : check_type});
    	grid.options.parms.push({name:'subj_code',value : subj_code});
    	grid.options.parms.push({name:'check_type_id',value : check_type_id});
    	//加载查询条件
    	grid.loadData(grid.where);
     }
  
    function loadHead(){
    	if(subj_dire=="0"){
    		grid = $("#maingrid").ligerGrid({
                columns: [ 
        					{ display: '凭证日期', name: 'vouch_date', align: 'left',width:100,
                				totalSummary:{
    		                        type: 'sum',render: function (suminf, column, cell){
    		                            return '<div>合计</div>';
    		                        }
    		                    }	
        					},
                            { display: '凭证号', name: 'vouch_no', align: 'left',width:100,
        						render : function(rowdata, rowindex, value) {
	       							 if(rowdata.vouch_no !=null){
	       								 return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no.split('-')[0]+"-"+rowdata.vouch_no.split('-')[1]+"</div></a>"; 
	       							 }
       						 	}		
                            },
                            { display: '往来项目', name: 'check_code', align: 'left',width:180},
        					{ display: '摘要', name: 'summary', align: 'left',width:150},
        					{ display: '贷方金额', name: 'debit', align: 'right',width:100,
        						totalSummary:{
    		                        type: 'sum',
    		                        render: function (suminf, column, cell){
    		                            return '<div>' + formatNumber(suminf.sum ,2,1) + '</div>';
    		                        }
    		                    },
        						render:function(rowdata){
        							return formatNumber(rowdata.debit ==null ? 0 : rowdata.debit,2,1);
        						 }
        					},
        					{ display: '已核销金额', name: 'ybal_amt', align: 'right',width:100,
        						totalSummary:{
    		                        type: 'sum',
    		                        render: function (suminf, column, cell){
    		                            return '<div>' + formatNumber(suminf.sum ,2,1) + '</div>';
    		                        }
    		                    },
        						render:function(rowdata){
        							return formatNumber(rowdata.ybal_amt ==null ? 0 : rowdata.ybal_amt,2,1);
        						}
        					},
        					{ display: '未核销金额', name: 'wbal_amt', align: 'right',width:100,
        						totalSummary:{
    		                        type: 'sum',
    		                        render: function (suminf, column, cell){
    		                            return '<div>' + formatNumber(suminf.sum ,2,1) + '</div>';
    		                        }
    		                    },
        						render:function(rowdata){
        							  return formatNumber(rowdata.wbal_amt ==null ? 0 : rowdata.wbal_amt,2,1);
        						 }
        					},
        					{ display: '核销人', name: 'ver_person', align: 'left',width:100},
        					{ display: '核销日期', name: 'ver_date', align: 'left',width:100},
        				    { display: '发生日期', name: 'occur_dated', align: 'left',width:100},
        					{ display: '到期日期', name: 'due_date', align: 'left',width:100},
        					{ display: '合同号', name: 'con_no', align: 'left',width:100},
        					{ display: '单据号', name: 'business_no', align: 'left',width:100}
                          ],
                          dataAction: 'server',dataType: 'server',usePager:true,
                          url:'accInventoryVerificationQ.do?isCheck=false&check_id='+check_id+'&check_type='+check_type+'&check_type_id='+check_type_id+'&subj_dire='+subj_dire+'&check_code='+check_code+'&veri_check_id='+veri_check_id+'&subj_code='+subj_code,
                          width: '100%', height: '100%', checkbox: false,rownumbers:true,isSingleCheck :false,isScroll:true,
                          selectRowButtonOnly:true
                           
        			});
    	}else{
    		grid = $("#maingrid").ligerGrid({
                columns: [ 
        					{ display: '凭证日期', name: 'vouch_date', align: 'left',width:100,
                				totalSummary:{
    		                        type: 'sum',render: function (suminf, column, cell){
    		                            return '<div>合计</div>';
    		                        }
    		                    }	
        					},
                            { display: '凭证号', name: 'vouch_no', align: 'left',width:100,
        						render : function(rowdata, rowindex, value) {
	       							 if(rowdata.vouch_no !=null){
	       								 return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no.split('-')[0]+"-"+rowdata.vouch_no.split('-')[1]+"</div></a>"; 
	       							 }
      						 	}	
                            },
                            { display: '往来项目', name: 'check_code', align: 'left' ,width:180},
        					{ display: '摘要', name: 'summary', align: 'left',width:150},
        					{ display: '借方金额', name: 'debit', align: 'right',width:100,
        						totalSummary:{
    		                        type: 'sum',
    		                        render: function (suminf, column, cell){
    		                            return '<div>' + formatNumber(suminf.sum ,2,1) + '</div>';
    		                        }
    		                    },
        						render:function(rowdata){
        							return formatNumber(rowdata.debit ==null ? 0 : rowdata.debit,2,1);
        						 }
        					},
        					{ display: '已核销金额', name: 'ybal_amt', align: 'right',width:100,
        						totalSummary:{
    		                        type: 'sum',
    		                        render: function (suminf, column, cell){
    		                            return '<div>' + formatNumber(suminf.sum ,2,1) + '</div>';
    		                        }
    		                    },
        						render:function(rowdata){
        							return formatNumber(rowdata.ybal_amt ==null ? 0 : rowdata.ybal_amt,2,1);
        						}
        					},
        					{ display: '未核销金额', name: 'wbal_amt', align: 'right',width:100,
        						totalSummary:{
    		                        type: 'sum',
    		                        render: function (suminf, column, cell){
    		                            return '<div>' + formatNumber(suminf.sum ,2,1) + '</div>';
    		                        }
    		                    },
        						render:function(rowdata){
        							  return formatNumber(rowdata.wbal_amt ==null ? 0 : rowdata.wbal_amt,2,1);
        						 }
        					},
        					{ display: '核销人', name: 'ver_person', align: 'left',width:100},
        					{ display: '核销日期', name: 'ver_date', align: 'left',width:100},
        				    { display: '发生日期', name: 'occur_dated', align: 'left',width:100},
        					{ display: '到期日期', name: 'due_date', align: 'left',width:100},
        					{ display: '合同号', name: 'con_no', align: 'left',width:100},
        					{ display: '单据号', name: 'business_no', align: 'left',width:100}
                          ],
                          dataAction: 'server',dataType: 'server',usePager:true,url:'accInventoryVerificationQ.do?isCheck=false&check_id='+check_id+'&check_type='+check_type+'&check_type_id='+check_type_id+'&subj_dire='+subj_dire+'&check_code='+check_code+'&veri_check_id='+veri_check_id+'&subj_code='+subj_code,
                          width: '100%', height: '100%', checkbox: false,rownumbers:true,isSingleCheck :false,isScroll:true,
                          selectRowButtonOnly:true
        			});
    	}
        gridManager1 = $("#maingrid").ligerGetGridManager();
	 	
    }
    
    function openSuperVouch(vouch_id){
		parent.parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
   
    function loadDict(){
    	
    }   
    function loadForm(){
    	
    }  
    
  </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="maingrid"></div>
</body>
</html>
