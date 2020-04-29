<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;

    var gridManager1 = null;
    
    var userUpdateStr;

    var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)

    var data=dialog!=null?dialog.get("data").parm:"";

    var identification = "${identification}";
    
    $(function ()
    {
        
		loadDict();
    	
    	loadHead(null);	//加载数据

    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){

    if(identification=="financialeducation_total_sr"){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '凭证号', name: 'vouch_no', align: 'left',width:'10%',
                         render:function(rowdata){
                             if(rowdata.vouch_id !=null){
                           return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"</div></a>";
                           }
         			   }
					 },
                     { display: '摘要', name: 'summary', align: 'left',width:'30%'
					 },
                     { display: '科目', name: 'subj_name', align: 'left',width:'30%'
					 },
					 { display: '借方', name: 'debit', align: 'right',width:'15%',
						 render : function(rowdata, rowindex,
									value) {
                              return formatNumber(rowdata.debit, 2, 1)
								 
							}
					 },
					 { display: '贷方', name: 'credit', align: 'right',width:'15%',
						 render : function(rowdata, rowindex,
									value) {
                           return formatNumber(rowdata.credit, 2, 1)
								 
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccFinancialEducationDataMining.do?isCheck=false&'+data.replace("financialeducation_total_sr","financialeducation_bal_sr"),
                     width: '100%', height: '50%', checkbox: false,rownumbers:true,delayLoad:false,
                     selectRowButtonOnly:true,//heightDiff: -10,
                   });

    	   gridManager = $("#maingrid").ligerGetGridManager();
    	 
        	grid = $("#maingrid1").ligerGrid({
                columns: [ 
                          { display: '单据编号', name: 'business_no', align: 'left',width:'20%'},
                          { display: '批复文号', name: 'reply_no', align: 'left',width:'10%'},
                          { display: '批复日期', name: 'reply_date', align: 'left',width:'15%'},
     					  { display: '项目编码', name: 'proj_code', align: 'right',width:'15%'},
     					  { display: '项目名称', name: 'proj_name', align: 'right',width:'15%'},
     					  { display: '负责人', name: 'emp_name', align: 'right',width:'15%'},
     					  { display: '批复金额', name: 'reply_money', align: 'right',width:'10%',
         					  render : function(rowdata, rowindex,
									value) {
                      		  return formatNumber(rowdata.reply_money, 2, 1)
                      	  }

         					  },
     					  { display: '批复说明', name: 'note', align: 'right',width:'10%'}
                          ],
                          dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccFinancialEducationDataMining.do?isCheck=false&'+data.replace("financialeducation_total_sr","financialeducation_match_sr"),
                          width: '100%', height: '50%', checkbox: false,rownumbers:true,delayLoad:false,
                          selectRowButtonOnly:true,//heightDiff: -10,
                        });

        	gridManager1 = $("#maingrid1").ligerGetGridManager();
    	 }else if(identification=="financialeducation_total_ot"){
    		 grid = $("#maingrid").ligerGrid({
    	           columns: [ 
    	                     { display: '凭证号', name: 'vouch_no', align: 'left',width:'10%',
    	                         render:function(rowdata){
    	                             if(rowdata.vouch_id !=null){
    	                           return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"</div></a>";
    	                           }
    	         			   }
    						 },
    	                     { display: '摘要', name: 'summary', align: 'left',width:'30%'
    						 },
    	                     { display: '科目', name: 'subj_name', align: 'left',width:'30%'
    						 },
    						 { display: '借方', name: 'debit', align: 'right',width:'10%',
    							 render : function(rowdata, rowindex,
    										value) {
    	                              return formatNumber(rowdata.debit, 2, 1)
    									 
    								}
    						 },
    						 { display: '贷方', name: 'credit', align: 'right',width:'10%',
    							 render : function(rowdata, rowindex,
    										value) {
    	                           return formatNumber(rowdata.credit, 2, 1)
    									 
    								}
    						 }
    	                     ],
    	                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccFinancialEducationDataMining.do?isCheck=false&'+data.replace("financialeducation_total_ot","financialeducation_bal_ot"),
    	                     width: '100%', height: '50%', checkbox: false,rownumbers:true,delayLoad:false,
    	                     selectRowButtonOnly:true,//heightDiff: -10,
    	                   });

    	    	   gridManager = $("#maingrid").ligerGetGridManager();
    	    	 
    	    	   grid = $("#maingrid1").ligerGrid({
    	                 columns: [ 
    	                            { display: '发生日期', name: 'occur_date', align: 'left',width:'20%'},
    	                            { display: '项目编码', name: 'proj_code', align: 'right',width:'10%'},
    	       					    { display: '项目名称', name: 'proj_name', align: 'right',width:'10%'},
    	                            { display: '报销事由', name: 'summary', align: 'left',width:'10%'},
    	                            { display: '科目', name: 'subj_name', align: 'left',width:'10%'},
    	                            { display: '凭证号', name: 'vouch_no', align: 'left',width:'10%'},
    	                            { display: '报销单据', name: 'business_no', align: 'left',width:'10%'},
    	                            { display: '支出金额', name: 'debit', align: 'left',width:'10%',
    	           					  render : function(rowdata, rowindex,
    	  									value) {
    	                        		  return formatNumber(rowdata.debit, 2, 1)
    	                        	  }}
    	                           ],
    	                           dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccFinancialEducationDataMining.do?isCheck=false&'+data.replace("financialeducation_total_ot","financialeducation_match_ot"),
    	                           width: '100%', height: '50%', checkbox: false,rownumbers:true,delayLoad:false,
    	                           selectRowButtonOnly:true,//heightDiff: -10,
    	                         });

    	        	gridManager1 = $("#maingrid1").ligerGetGridManager();
        	 }
    }

    function loadDict(){}

    function openSuperVouch(vouch_id){
    	parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
    }
        
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0"  class="l-table-edit" >
    </table>

	<div id="maingrid"></div>
	<br />
	<div id="maingrid1"></div>

</body>
</html>
