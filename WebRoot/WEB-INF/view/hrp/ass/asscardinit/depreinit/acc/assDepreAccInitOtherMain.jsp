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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var ass_card_no = '${ass_card_no}';
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        query();
		
    });
    
    function setAssCardNo(no){
    	ass_card_no = no;
    	query();
    }
    
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		grid.options.parms.push({
    			name : 'ass_nature',
    			value : '${ass_nature}'
    		});
    		grid.options.parms.push({
    			name : 'ass_card_no',	
    			value : ass_card_no
    		});
    		grid.options.parms.push({
    			name : 'depre_year_month_beg',	
    			value : $("#depre_year_month_beg").val()
    		});
    		grid.options.parms.push({
    			name : 'depre_year_month_end',	
    			value : $("#depre_year_month_end").val()
    		});
    		grid.options.parms.push({
    			name : 'use_dept_id',	
    			value : liger.get("use_dept_id").getValue().split("@")[0]
    		});
    		grid.options.parms.push({
    			name : 'use_dept_no',	
    			value : liger.get("use_dept_id").getValue().split("@")[1]
    		});
    		
    		grid.options.parms.push({
    			name : 'source_id',	
    			value : liger.get("source_id").getValue()
    		});
    		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '折旧年月', name: 'depre_year', align: 'left',width:100
					 		},
                     { display: '折旧期间', name: 'depre_month', align: 'left',width:100
					 		},
                     { display: '资金来源', name: 'source_name', align: 'left',width:100
					 		},
                     { display: '使用科室', name: 'use_dept_name', align: 'left',width:150
					 		},
                     { display: '分摊比例', name: 'use_percent', align: 'left',width:100
					 		},
                     { display: '原值', name: 'prim_money', align: 'right',width:100,
								render : function(item) {
									return formatNumber(
											item.prim_money, '${ass_05006}', 1);
								}
					 		},
                     { display: '本期折旧', name: 'now_depre_amount', align: 'right',width:100,
								render : function(item) {
									return formatNumber(
											item.now_depre_amount, '${ass_05005}',1);
								}
					 		},
                     { display: '累计折旧', name: 'add_depre_amount', align: 'right',width:100,
								render : function(item) {
									return formatNumber(
											item.add_depre_amount, '${ass_05005}', 1);
								}
					 		},
                     { display: '累计折旧月份', name: 'add_depre_month', align: 'left',width:100
					 		},
                     { display: '资产净值', name: 'cur_money', align: 'right',width:100,
								render : function(item) {
									return formatNumber(
											item.cur_money, '${ass_05006}', 1);
								}
					 		},
                     { display: '预留残值', name: 'fore_money', align: 'right',width:100,
								render : function(item) {
									return formatNumber(
											item.fore_money, '${ass_05006}', 1);
								}
					 		},
                     { display: '操作员', name: 'operator', align: 'left',width:110
					 		},
                     { display: '处理日期', name: 'deal_date', align: 'left',width:140
					 		},
                     { display: '使用折旧方法', name: 'equi_depre_name', align: 'left',width:160
					 		},
                     { display: '备注', name: 'note', align: 'left',width:160
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAssDepreAccInit.do',
                     width: '100%', height: '90%', checkbox: true,rownumbers:true,delayLoad : true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     scroll: true,
                     frozen:true,
                     alternatingRow: false,
                     scrollToPage: true,
                     scrollToAppend: true,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    	{ text: '计算（<u>A</u>）', id:'settle', click: cacl, icon:'settle' }
				    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function cacl(){
    	
    	}
   
    function loadDict(){
    	$("#depre_year_month_beg").ligerTextBox({width:90});
    	$("#depre_year_month_end").ligerTextBox({width:90});
    	autodate("#depre_year_month_beg", "YYYYMM");
		autodate("#depre_year_month_end", "YYYYMM");
    	autocompleteAsync("#source_id", "../querySourceDict.do?isCheck=false", "id",
				"text", true, true, "", false,
				null, '160',true);
    	autocompleteAsync("#use_dept_id", "../queryDeptDict.do?isCheck=false", "id",
				"text", true, true, "", false,
				null, '160',true);
         }  
    function dateText() {
		WdatePicker({
			isShowClear : true,
			readOnly : false,
			dateFmt : 'yyyyMM'
		});
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >

        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年月：</td>
            <td align="left" class="l-table-edit-td"><input class='Wdate' onFocus='dateText()' name="depre_year_month_beg" type="text" id="depre_year_month_beg" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left">至：</td>
            <td align="right" class="l-table-edit-td"  ><input class='Wdate' onFocus='dateText()' name="depre_year_month_end" type="text" id="depre_year_month_end" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
            <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用科室：</td>
            <td align="left" class="l-table-edit-td"><input name="use_dept_id" type="text" id="use_dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>


	<div id="maingrid"></div>
	
</body>
</html>
