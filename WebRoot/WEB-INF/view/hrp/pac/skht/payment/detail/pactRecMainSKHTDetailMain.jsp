<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script>
        var grid;
        var startpicker;
        var endpicker;
      //查询
        function  query(){
       		grid.options.parms=[];
        	grid.options.newPage=1;

            //根据表字段进行添加查询条件
            grid.options.parms.push({name:'pact_type_code',value: liger.get("pact_type_code").getValue()}); 
            grid.options.parms.push({name:'cus_no',value: liger.get("cus_no").getValue()}); 
            grid.options.parms.push({name:'dept_no',value: liger.get("dept_no").getValue()}); 
            grid.options.parms.push({name:'pact_nature',value: liger.get("pact_nature").getValue()}); 
            grid.options.parms.push({name:'pact_code',value:$("#pact_code").val()}); 
            grid.options.parms.push({name:'pact_name',value:$("#pact_name").val()}); 
            grid.options.parms.push({name:'start_date',value:$("#start_date").val()}); 
            grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 
            
        	//加载查询条件
        	grid.loadData(grid.where);
    	}
        
        
        var initSelect=  function(){
            //字典下拉框
            autocomplete("#pact_type_code",'../../../basicset/select/queryPactTypeSKHTSelect.do?isCheck=false',"id","text",true,true,'',false);
            autocomplete("#cus_no",'../../../basicset/select/queryHosCusDictSelect.do?isCheck=false',"id","text",true,true,'',false);
            autocomplete("#dept_no",'../../../basicset/select/queryDeptSelect.do?isCheck=false' ,"id","text",true,true,'',false);
            autocomplete("#pact_nature","../../../basicset/select/queryTypeSKHTNatureSelect.do?isCheck=false" ,"id","text",true,true,'',false);
            $("#pact_type_code").ligerComboBox({ width:180});
        	$("#cus_no").ligerComboBox({ width:180});
    		$("#dept_no").ligerComboBox({ width:180});
        	$("#pact_nature").ligerComboBox({width:180});
        	$("#pact_code").ligerTextBox({width:180});
        	$("#pact_name").ligerTextBox({width:180});
        	
        	$("#start_date").ligerTextBox({width : 100});
        	$("#end_date").ligerTextBox({width : 100});
        	autodate("#start_date", "yyyy-mm-dd", "month_first");
        	autodate("#end_date", "yyyy-mm-dd", "month_last");
        	
        }
        
        
        
        //加载grid
        function loadHead(){
    		grid = $("#mainGrid").ligerGrid({
    			columns: [ 
                	{ display: '合同编码', name: 'pact_code', align: 'left',width:100},
                    { display: '合同名称', name: 'pact_name', align: 'left',width:100},
                    { display: '客户', name: 'cus_name', align: 'left',width:100},
                    { display: '签订科室', name: 'dept_name', align: 'left',width:100},
                    { display: '签订日期', name: 'sign_date', align: 'left',width:100},
                    { display: '性质', name: 'nature_name', align: 'left',width:100},
                    { display: '计划收款',
                    	columns:[
                    		 { display: '期号', name: 'p_rec_id', align: 'left',width:100},
                    		 { display: '收款日期', name: 'p_rec_date', align: 'left',width:100},
                    		 //{ display: '资金来源', name: 'p_source_name', align: 'left',width:100},
                    		 { display: '收款方式', name: 'p_cond_name', align: 'left',width:100},
                    		 { display: '计划金额', name: 'p_plan_money', align: 'right',width:100},
                    		 { display: '实际收款', name: 'p_rec_money', align: 'right',width:100},
                    		 { display: '未收金额', name: 'p_unpay', align: 'right',width:100},
                    	]	
                    },
                    { display: '实际收款',
                    	columns:[
                    		 { display: '收款单号', name: 'r_rec_code', align: 'left',width:100},
                    		 { display: '收款日期', name: 'r_rec_date', align: 'left',width:100},
                    		// { display: '资金来源', name: 'r_source_name', align: 'left',width:100},
                    		 { display: '收款方式', name: 'r_rec_name', align: 'right',width:100},
                    		 { display: '收款金额', name: 'r_rec_money', align: 'right',width:100},
                    	]	
                    },
                    
                ],
                dataAction: 'server',dataType: 'server',usePager:false,url:'queryPactRecMainSKHTDetail.do?isCheck=false',/* enabledEdit : true, */
                width: '100%', height: '95%',heightDiff:30, rownumbers:true,checkbox:false,
                selectRowButtonOnly:true,delayLoad:true,isAddRow:false,onAfterShowData:onAfterShowData,
                 toolbar: {
                     items: [
                    	 { text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     ]
                 }
              });
    		
    		gridManager = $("#mainGrid").ligerGetGridManager();
        }
       
        function onAfterShowData(){
        	 $('#mainGrid .l-grid-body-table tbody').rowspan('pact_code', grid , 5)
        	 $('#mainGrid .l-grid-body-table tbody').rowspan('p_rec_id', grid , 6)
        }
        
        //合并单元格
        jQuery.fn.rowspan = function (colname , tableObj , rowNum){
        	var first;
        	var second;
        	var colIdx;
        	
        	for(var i = 0 , n = tableObj.columns.length; i<n;i++){
        		if(tableObj.columns[i]["columnname"] == colname ){
        			colIdx = i -1 < 1 ? 0 : i-1;
        			break;
        		}
        	}
        	
        	return this.each(function(){
        		var that;
        		$('tr' ,this).each(function(row){
        			$('td:eq(' + colIdx + ')', this).filter(':visible').each(function(col){
        				if(that != null && $(this).html() == $(that).html()){
        					rowspan = $(that).attr("rowSpan");
        					if(rowspan == undefined){
        						$(that).attr("rowSpan",1);
        						rowspan = $(that).attr("rowSpan");
        					}
        					rowspan = Number(rowspan) + 1;
        					$(that).attr("rowSpan",rowspan);
        					$(this).hide();
        					first = $(that);
        					second = $(this);
        					var id = $(that)[0].id;
        					var num = parseInt(id.substr(id.length -1 ,id.length));
        					for(var i = num ; i <num +rowNum ; i++){
        						first.next().attr("rowSpan" , rowspan);
        						second.next().hide();
        						first = first.next();
        						second = second.next();
        					}
        				}else{
        					that = this;
        				}
        			});
        		});
        	});
        } 
        
        $(function () {
        	loadHead();
            initSelect();
            query();
        })
        
    </script>
</head>

<body>
    <table style="margin-bottom: 12px">
        <tr>
            <td class="label" style="padding-left: 20px;padding-top: 20px">收款日期：</td>
            <td class="label" style="padding-top: 20px">
            	<table>
            		<tr>
           <td align="left" class="l-table-edit-td" ><input name="start_date" class="Wdate" type="text" id="start_date" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
           <td align="left">至</td>
           <td align="left" class="l-table-edit-td" ><input name="end_date" class="Wdate" type="text" id="end_date" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            		</tr>
            	</table>
            </td>
                
            <td class="label" style="padding-left: 30px;padding-top: 20px">合同类别：</td>
            <td class="ipt" style="padding-top: 20px"><input id="pact_type_code" ></td>
            <td class="label" style="padding-left: 30px;padding-top: 20px">客户：</td>
            <td class="ipt" style="padding-top: 20px"> <input id="cus_no" > </td>
        </tr>
        <tr>
            <td class="label" style="padding-left: 20px;padding-top: 12px">合同编号：</td>
            <td class="ipt" style="padding-top: 12px"><input id="pact_code" type="text" /> </td>
            <td class="label" style="padding-left: 30px;padding-top: 12px">合同名称：</td>
            <td class="ipt" style="padding-top: 12px"><input id="pact_name" type="text" /> </td>
            <td class="label" style="padding-left: 30px;padding-top: 12px">签订科室：</td>
			<td class="ipt" style="padding-top: 12px"><input id="dept_no"></td>
        </tr>
        <tr>
         	<td class="label" style="padding-left: 20px;padding-top: 12px">合同性质：</td>
            <td class="ipt" style="padding-top: 12px"><input id="pact_nature" /></td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>