<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	 
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	$("#year_month1").ligerTextBox({width:110});
    	$("#year_month2").ligerTextBox({width:110});
		var acc_month=getMonthDate('${acc_year}','${acc_month}');
		$("#year_month1").val(acc_month.split(";")[0]);
		$("#year_month2").val(acc_month.split(";")[1]);
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		 if($("#year_month1").val().split("-")[0] != $("#year_month2").val().split("-")[0]){
    	            
          	   $.ligerDialog.error('不支持跨年查询!')

          	   return false;
              }
    		 
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'year_month1',value:$("#year_month1").val()}); 
        grid.options.parms.push({name:'year_month2',value:$("#year_month2").val()}); 
    	grid.options.parms.push({name:'level_code',value:liger.get("level_code").getValue()}); 
    	grid.options.parms.push({name:'proj_id',value:liger.get("proj_id").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'con_emp_id',value:liger.get("con_emp_id").getValue().split(".")[0]}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '项目编码', name: 'proj_code', align: 'left',width:200
					 },
                     { display: '项目名称', name: 'proj_name', align: 'left' ,width:200
					 },
                     { display: '负责人', name: 'emp_name', align: 'left',width:100
					 },
                     { display: '合计',  align: 'center',
						 columns:[
                                    { display: '期初余额', name: 'total_os', align: 'right',formatter:'###,##0.00',width:120,
            							 render : function(rowdata, rowindex, value) {
            								return formatNumber(rowdata.total_os, 2, 1)
            							} 
                                    },
                                    { display: '本期收入', name: 'total_sr', align: 'right',formatter:'###,##0.00',width:120,
               							 render : function(rowdata, rowindex,value) {
		       								 return "<a href=javascript:openUpdate('" + rowdata.proj_id + "|"+ rowdata.proj_no + "|" + "Education_total_sr_dy"+"')>" 
		       								 +  formatNumber(rowdata.total_sr, 2, 1) + "</a>"; 
               							} 
                                    },
                                    { display: '本期支出', name: 'total_ot', align: 'right',formatter:'###,##0.00',width:120,
               							  render : function(rowdata, rowindex,value) {
	               								return "<a href=javascript:openUpdate('" + rowdata.proj_id + "|"+ rowdata.proj_no + "|" + "Education_total_ot_dy"+"')>"
	               								+formatNumber(rowdata.total_ot, 2, 1) + "</a>"; 
               							} 
                                    },
                                    { display: '期末余额', name: 'total_od', align: 'right',formatter:'###,##0.00',width:120,
                   							 render : function(rowdata, rowindex,value) {
                   								return formatNumber(rowdata.total_od, 2, 1)
                   							}  
                                    }
                                  ]
					 },
					 { display: '财政外拨资金',  align: 'center',
						 columns:[
                                  { display: '期初余额', name: 'cz_bal_os', align: 'right',formatter:'###,##0.00',width:120,
                                	  render : function(rowdata, rowindex, value) {
          								return formatNumber(rowdata.cz_bal_os, 2, 1)
          						      } 
                                  },
                                  { display: '本期收入', name: 'cz_bal_sr', align: 'right',formatter:'###,##0.00',width:120,
                            		  render : function(rowdata, rowindex,value) {         									
					                       return "<a href=javascript:openUpdate('" + rowdata.proj_id + "|"+ rowdata.proj_no + "|" + "Education_bal_sr_cz_dy"+"')>" 
					                       + formatNumber(rowdata.cz_bal_sr, 2, 1) + "</a>"; 
                           			  }
                                  },
                                  { display: '本期支出', name: 'cz_bal_ot', align: 'right',formatter:'###,##0.00',width:120,
                               		  render : function(rowdata, rowindex,value) {         									
			      	                       return "<a href=javascript:openUpdate('" + rowdata.proj_id + "|"+ rowdata.proj_no + "|" + "Education_bal_ot_cz_dy"+"')>" 
			      	                       + formatNumber(rowdata.cz_bal_ot, 2, 1) + "</a>"; 
                                      }
                                  },
                                  { display: '期末余额', name: 'cz_bal_od', align: 'right',formatter:'###,##0.00',width:120,
                                	  render : function(rowdata, rowindex, value) {
          								return formatNumber(rowdata.cz_bal_od, 2, 1)
          							  } 
                                  }
                                ]
					 },
					 { display: '科教外拨资金',  align: 'center',
						 columns:[
                                  { display: '期初余额', name: 'kj_bal_os', align: 'right',formatter:'###,##0.00',width:120,
                                	  render : function(rowdata, rowindex, value) {
          								return formatNumber(rowdata.kj_bal_os, 2, 1)
          							  }   
                                  },
                                  { display: '本期收入', name: 'kj_bal_sr', align: 'right',formatter:'###,##0.00',width:120,
                            		  render : function(rowdata, rowindex,value) {         									
					                       return "<a href=javascript:openUpdate('" + rowdata.proj_id + "|"+ rowdata.proj_no + "|" + "Education_bal_sr_kj_dy"+"')>" 
					                       + formatNumber(rowdata.kj_bal_sr, 2, 1) + "</a>"; 
                           			  }
                                  },
                                  { display: '本期支出', name: 'kj_bal_ot', align: 'right',formatter:'###,##0.00',width:120,
                               		  render : function(rowdata, rowindex,value) {         									
			      	                       return "<a href=javascript:openUpdate('" + rowdata.proj_id + "|"+ rowdata.proj_no + "|" + "Education_bal_ot_kj_dy"+"')>" 
			      	                       + formatNumber(rowdata.kj_bal_ot, 2, 1) + "</a>"; 
                                	  }
                                  },
                                  { display: '期末余额', name: 'kj_bal_od', align: 'right',formatter:'###,##0.00',width:120,
                           				render : function(rowdata, rowindex, value) {
              								return formatNumber(rowdata.kj_bal_od, 2, 1)
              							} 				
                                  }
                                ]
					 },
					 { display: '配套资金',  align: 'center',
						 columns:[
                                  { display: '期初余额', name: 'match_os', align: 'right',formatter:'###,##0.00',width:120,
                                	  render : function(rowdata, rowindex, value) {
          								return formatNumber(rowdata.match_os, 2, 1)
          							  }   
                                  },
                                  { display: '本期收入', name: 'match_sr', align: 'right',formatter:'###,##0.00',width:120,
                            		  render : function(rowdata, rowindex,value) {         									
	   		      	                       return "<a href=javascript:openUpdate('" + rowdata.proj_id + "|"+ rowdata.proj_no + "|" + "Education_match_sr"+"')>" 
	   		      	                       + formatNumber(rowdata.match_sr, 2, 1) + "</a>"; 
                                      }
                                  },
                                  { display: '本期支出', name: 'match_ot', align: 'right',formatter:'###,##0.00',width:120,
                       					render : function(rowdata, rowindex,value) {         									
       		      	                       return "<a href=javascript:openUpdate('" + rowdata.proj_id + "|"+ rowdata.proj_no + "|" + "Education_match_ot"+"')>" 
       		      	                       + formatNumber(rowdata.match_ot, 2, 1) + "</a>"; 
                                        }
                                  },
                                  { display: '期末余额', name: 'match_od', align: 'right',formatter:'###,##0.00',width:120,
                           				render : function(rowdata, rowindex, value) {
               								return formatNumber(rowdata.match_od, 2, 1)
               							}       				
                                  }
                                ]
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccEducationBySplit.do',
                     width: '100%', height: '98%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,heightDiff: -20,
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

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		return;
                case "modify":
                    return;
                case "delete":
                    
                    return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
            }   
        }
        
    } 
    function loadDict(){
            //字典下拉框
    	 $("#proj_id").ligerComboBox({
		      	url: '../../sys/queryProjDictDict.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 280,
		      	autocomplete: true,
		      	width: 240
			 });
    	autocomplete("#con_emp_id","../../sys/queryEmp.do?isCheck=false","id","text",true,true);  
    	autocomplete("#level_code","../../sys/queryProjLevelDict.do?isCheck=false","id","text",true,true);  
    }
    
    function printDate(){
   	
    	if(grid.getData().length==0){
    		$.ligerDialog.error("请先查询数据！");
    		return;
    	}

    	var heads={
      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
      		  "rows": [
  	          {"cell":0,"value":"日期范围："+$("#year_month1").val()+"至"+$("#year_month2").val(),"colSpan":"5"}
      		  ]
      	};
      	
     		var printPara={
     			title: "科目项目备查簿",//标题
     			columns: JSON.stringify(grid.getPrintColumns()),//表头
     			class_name: "com.chd.hrp.acc.service.books.memorandumbook.AccEducationService",
	  			method_name: "queryAccEducationBySplitPrint",
	  			bean_name: "accEducationService",
	  			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
  			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
     		};
      	
     		//执行方法的查询条件
     		$.each(grid.options.parms,function(i,obj){
     			printPara[obj.name]=obj.value;
      	});
     		
      	officeGridPrint(printPara);
    }

    function openUpdate(obj){
    	var vo = obj.split("|");
    	 var parm = "proj_id=" +
		vo[0] + "&proj_no=" +
		vo[1] + "&identification=" + 
		vo[2] + "&year_month1=" +
		$("#year_month1").val() + "&year_month2=" +
		$("#year_month2").val()
		parent.$.ligerDialog.open({
			url: 'hrp/acc/acceducation/acceducationDataMiningByDyPage.do?isCheck=false&'+parm, data: {parm:parm},
			height: $(window).height(),
			width: $(window).width(),
			title: '', modal: false, showToggle: false, showMax: true, showMin: false, isResize: false,slide:false
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
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目级别：</td>
            <td align="left" class="l-table-edit-td"><input name="level_code" type="text" id="level_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
       
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
