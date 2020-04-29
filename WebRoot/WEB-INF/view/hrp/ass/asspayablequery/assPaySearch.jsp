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
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        $("#dept_id").ligerTextBox({width:160});
        $("#in_date_beg").ligerTextBox({width:90});
        $("#in_date_end").ligerTextBox({width:90});
        $("#ven_id").ligerTextBox({width:160});
        $("#store_id").ligerComboBox({width:160});
        $("#state").ligerComboBox({width:160});
        $("#ass_card_no").ligerTextBox({width:160});
        $("#run_date_beg").ligerTextBox({width:90});
        $("#run_date_end").ligerTextBox({width:90});
        autodate("#in_date_beg","YYYY-mm-dd","month_first");

		autodate("#in_date_end","YYYY-mm-dd","month_last");
		
	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split("@")[1]}); 
    	  grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'ven_no',value:liger.get("ven_id").getValue().split("@")[1]});
    	  grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'store_no',value:liger.get("store_id").getValue().split("@")[1]});
    	  grid.options.parms.push({name:'ass_id',value:liger.get("ass_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'ass_no',value:liger.get("ass_id").getValue().split("@")[1]});
    	  grid.options.parms.push({name:'in_date_beg',value:$("#in_date_beg").val()}); 
    	  grid.options.parms.push({name:'in_date_end',value:$("#in_date_end").val()});
    	  grid.options.parms.push({name:'run_date_beg',value:$("#run_date_beg").val()}); 
    	  grid.options.parms.push({name:'run_date_end',value:$("#run_date_end").val()}); 
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
    	  grid.options.parms.push({name:'ass_type_id',value:liger.get("ass_type_id").getValue()}); 
    	  grid.options.parms.push({name:'ass_card_no',value:$("#ass_card_no").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '卡片编码', name: 'ass_card_no', align: 'left',width : 120
					 		},
					 { display: '资产名称', name: 'ass_name', align: 'left',width : 180
					 		},
					 { display: '使用科室', name: 'dept_name', align: 'left',width : 160
							},
					 { display: '所在仓库', name: 'store_name', align: 'left',width : 180
							},		
					 { display: '资产类别', name: 'ass_type_name', align: 'left',width : 120
					 		},		
                     { display: '入库日期', name: 'in_date', align: 'left',width : 120
					 		},
                     { display: '规格', name: 'ass_spec', align: 'left',width : 120
					 		},
                     { display: '型号', name: 'ass_mondl', align: 'right',width : 120
					 		},
                     { display: '品牌', name: 'ass_brand', align: 'left',width : 120
					 		},
                     { display: '原值', name: 'price', align: 'right',width : 190,
								render: function(item)
					            {
					                    return formatNumber(item.price,'${ass_05006}',1);
					            }
					 		},
                     { display: '已付金额', name: 'pay_money', align: 'right',width : 120,
								render: function(item)
					            {
					                    return formatNumber(item.pay_money,'${ass_05005}',1);
					            }
					 		},
					 { display: '未付金额', name: 'unpay_money', align: 'right',width : 120,
								render: function(item)
					            {
					                    return formatNumber(item.unpay_money,'${ass_05005}',1);
					            }
					 		}		
					 
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryPaySearch.do',delayLoad :true,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    	{ text: '打印', id:'print', click: print,icon:'print' }
				    ]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    	
  
    function loadDict(){
    	
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true, null, false);
		
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false","id", "text",true,true,null,false,null,"350");
		
		autocomplete("#store_id", "../queryHosStoreDict.do?isCheck=false","id", "text",true,true,null,false,null,"350");
		
		autocomplete("#state", "../queryAssCardUseStateDict.do?isCheck=false","id", "text",true,true,null,false,null);
            
		autocomplete("#ass_id", "../queryAssNoDict.do?isCheck=false", "id","text", true, false, null,null,null,"240");
				
    	autocomplete("#ass_type_id","../queryAssTypeDictIsLast.do?isCheck=false","id","text",true,true,null,null,null,"160");
         
    	
    }  
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库日期：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="in_date_beg" type="text" id="in_date_beg"  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left">至：</td>
            <td align="left" class="l-table-edit-td"><input name="in_date_end" type="text" id="in_date_end"  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'in_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"   /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">投入使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="run_date_beg" type="text" id="run_date_beg"  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left">至：</td>
            <td align="left" class="l-table-edit-td"><input name="run_date_end" type="text" id="run_date_end"  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'run_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="state" type="text" id="state"   />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"   /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="ass_id" type="text" id="ass_id"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">卡片编码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="ass_card_no" type="text" id="ass_card_no"   />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_type_id" type="text" id="ass_type_id"   /></td>
            <td align="left"></td>
        </tr>
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
