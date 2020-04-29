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
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	$("#year_month1").ligerTextBox({width:130});
    	$("#year_month2").ligerTextBox({width:132});
		var acc_month=getMonthDate('${acc_year}','${acc_month}');
		$("#year_month1").val(acc_month.split(";")[0]);
		$("#year_month2").val(acc_month.split(";")[1]);
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
         var year_month1 = $("#year_month1").val();
         var year_month2 = $("#year_month2").val()
        //根据表字段进行添加查询条件
        
        if(year_month1.split("-")[0] != year_month2.split("-")[0]){
            
        	   $.ligerDialog.error('不支持跨年查询!')

        	   return false;
            }
        grid.options.parms.push({name:'year_month1',value:year_month1}); 
        grid.options.parms.push({name:'year_month2',value:year_month2}); 
    	grid.options.parms.push({name:'level_code',value:liger.get("level_code").getValue()}); 
    	grid.options.parms.push({name:'proj_id',value:liger.get("proj_id").getValue()}); 
    	grid.options.parms.push({name:'con_emp_id',value:liger.get("con_emp_id").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '项目编码', name: 'proj_code', align: 'left'
					 },
                     { display: '项目名称', name: 'proj_name', align: 'left'
					 },
                     { display: '负责人', name: 'emp_name', align: 'left'
					 },
                     { display: '合计',  align: 'center',
						 columns:[
                                    { display: '期初余额', name: 'total_os', align: 'right',formatter:'###,##0.00',
            							render : function(rowdata, rowindex,value) {
                                             return formatNumber(rowdata.bal_os+rowdata.match_os, 2, 1)
            							}
                                    },
                                    { display: '本期收入', name: 'total_sr', align: 'right',formatter:'###,##0.00',
               							render : function(rowdata, rowindex,value) {
               								 return "<a href=javascript:openUpdate('" + 
               										 rowdata.proj_id + "|"+ 
               										 rowdata.proj_no + "|" + 
               										 "financialeducation_total_sr"+ "|"
               		      	                       + "04"+"')>" + formatNumber(rowdata.bal_sr+rowdata.match_sr, 2, 1) + "</a>"; 
               							}
                                    },
                                    { display: '本期支出', name: 'total_ot', align: 'right',formatter:'###,##0.00',
               							render : function(rowdata, rowindex,value) {
		                  				 	return "<a href=javascript:openUpdate('" + 
		                  				 			rowdata.proj_id + "|"+
		                  				 			rowdata.proj_no + "|" + 
		                  				 			"financialeducation_total_ot"+ "|"
		      		      	                       + "05"+"')>" + formatNumber(rowdata.bal_ot+rowdata.match_ot, 2, 1) + "</a>";
		                  					        return formatNumber(rowdata.bal_ot+rowdata.match_ot, 2, 1)
               							}
                                    },
                                    { display: '期末余额', name: 'total_od', align: 'right',formatter:'###,##0.00',
               							render : function(rowdata, rowindex,value) {
               								return formatNumber(rowdata.bal_od+rowdata.match_od, 2, 1)
               							}
                                    }
                                  ]
					 },
					 { display: '外拨资金',  align: 'center',
						 columns:[
                                  { display: '期初余额', name: 'bal_os', align: 'right',
                                	  render : function(rowdata, rowindex,value) {
                                		  return formatNumber(rowdata.bal_os, 2, 1)
                                	  },
                                	  formatter:'###,##0.00'
                               	  },
                                  { display: '本期收入', name: 'bal_sr', align: 'right',
                               		  render : function(rowdata, rowindex,value) {         									
				                           return "<a href=javascript:openUpdate('" + 
				                        		   rowdata.proj_id + "|"+ 
				                        		   rowdata.proj_no + "|" + 
				                        		   "financialeducation_bal_sr"+ "|"
			 		      	                       + "04"+"')>" + formatNumber(rowdata.bal_sr, 2, 1) + "</a>"; 
                                   	  },
                                	  formatter:'###,##0.00'
                           		  },
                                  { display: '本期支出', name: 'bal_ot', align: 'right',
                           			  render : function(rowdata, rowindex,value) {
				                             return "<a href=javascript:openUpdate('" + 
				                            		 rowdata.proj_id + "|"+ 
				                            		 rowdata.proj_no + "|" + 
				                            		 "financialeducation_bal_ot"+ "|"
				 		      	                       + "05"+"')>" + formatNumber(rowdata.bal_ot, 2, 1) + "</a>";
                                   	  }, 
                           			  formatter:'###,##0.00'
                       			  },
                                  { display: '期末余额', name: 'bal_od', align: 'right',
                               	     render : function(rowdata, rowindex, value) {
                           		   		return formatNumber(rowdata.bal_od, 2, 1)
                           	 		 },
                           	 		 formatter:'###,##0.00'
                            	  }
                                ]
					 },
					 { display: '配套资金',  align: 'center',
						 columns:[
                                  { display: '期初余额', name: 'match_os', align: 'right',
                        			  render : function(rowdata, rowindex, value) {
                                  		  return formatNumber(rowdata.match_os, 2, 1)
                                  	  },
                                  	  formatter:'###,##0.00'
                               	  },
                                  { display: '本期收入', name: 'match_sr', align: 'right',
                           			  render : function(rowdata, rowindex, value) {
		                           		 return "<a href=javascript:openUpdate('"  + 
		                           				 rowdata.proj_id + "|"+ 
		                           				 rowdata.proj_no + "|" + 
		                           				 "financialeducation_match_sr"+ "|"
		   		      	                       + "04"+"')>" + formatNumber(rowdata.match_sr, 2, 1) + "</a>";
		               							
                                   	  }, 
                                   	  formatter:'###,##0.00'
                               	  },
                                  { display: '本期支出', name: 'match_ot', align: 'right',
                           			  render : function(rowdata, rowindex, value) {
			                           	 return "<a href=javascript:openUpdate('" + 
			                           			 rowdata.proj_id + "|"+ 
			                           			 rowdata.proj_no + "|" + 
			                           			 "financialeducation_match_ot"+ "|"
			 		      	                       + "05"+"')>" + formatNumber(rowdata.match_ot, 2, 1) + "</a>"; 
           	                                	 return formatNumber(rowdata.match_ot, 2, 1);
                                   	  },
                                   	  formatter:'###,##0.00'
                               	  },
                                  { display: '期末余额', name: 'match_od', align: 'right',
                           			  render : function(rowdata, rowindex, value) {
                                     		  return formatNumber(rowdata.match_od, 2, 1)
                                   	  },
                                   	  formatter:'###,##0.00'
                               	  }
                                ]
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccFinancialEducation.do?attr_code='+'${attr_code}',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,heightDiff: -30,
                     delayLoad:true,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true } , 
						{ text: '打印', id:'print', click: printDate,icon:'print' },
						{ line:true } 
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function loadDict(){
            //字典下拉框
    	 /* $("#proj_id").ligerComboBox({
		      	url: '../../sys/queryProjDictDict.do?isCheck=false&attr_code='+'${attr_code}',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 280,
		      	autocomplete: true,
		      	width: 280
			 });
    	autocomplete("#con_emp_id","../../sys/queryEmp.do?isCheck=false","id","text",true,true);  */ 
    	$("#proj_id").ligerTextBox({width:275});
    	$("#con_emp_id").ligerTextBox({width:160});
    	autocomplete("#level_code","../../sys/queryProjLevelDict.do?isCheck=false","id","text",true,true);  
    }
    
    function printDate(){
  	   
    	if(grid.getData().length==0){
    		$.ligerDialog.error("请先查询数据！");
    		return;
    	}

      	var selPara={usePager:false};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});

    		//console.log(grid)
    		var printPara={
    			headCount:2,
    			title:'财政项目备查簿',
    			type:3,
    			columns:grid.getColumns(1),
    			autoFile:true

    		};
    		
    		ajaxJsonObjectByUrl("queryAccFinancialEducation.do?isCheck=false", selPara, function(responseData) {
    			printGridView(responseData,printPara);
    	});
    	 
    }

    function openUpdate(obj){
    	
    	var vo = obj.split("|");
    	
    	if(vo[0] =="null" && vo[1]=="null"){
    		
    		return ;
    	}
    	var parm = "proj_id=" +
		vo[0] + "&proj_no=" +
		vo[1] + "&identification=" + 
		vo[2] + "&subj_type_code=" + 
		vo[3] +"&year_month1=" +
		$("#year_month1").val() + "&year_month2=" +
		$("#year_month2").val()
		
		parent.$.ligerDialog.open({
			url: 'hrp/acc/accfinancialeducation/accFinancialEducationDataMiningPage.do?isCheck=false&'+parm, data: {parm:parm},
			height: $(window).height(),
			width: $(window).width(),
			title: '', modal: true, showToggle: false, showMax: true, showMin: false, isResize: false,slide:false
			/* buttons: [{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }] */
		});

    }
        
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0"  class="l-table-edit" >
	
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">日期范围：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month1" type="text" id="year_month1" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text"/></td>
            <td align="left" >-</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month2" type="text" id="year_month2" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text"/></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="con_emp_id" type="text" id="con_emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项&nbsp;目&nbsp;级&nbsp;别：</td>
            <td align="left" class="l-table-edit-td"><input name="level_code" type="text" id="level_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
