<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    	grid.options.parms.push({name:'year_month',value:$("#year_month").val()}); 
    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()}); 
    	grid.options.parms.push({name:'appl_dept_id',value:liger.get("appl_dept").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'appl_dept_no',value:liger.get("appl_dept").getValue().split(".")[1]}); 
    	grid.options.parms.push({name:'exec_dept_id',value:liger.get("exec_dept").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'exec_dept_no',value:liger.get("exec_dept").getValue().split(".")[1]}); 
    	grid.options.parms.push({name:'charge_kind_id',value:liger.get("charge_kind_id").getValue()}); 
		 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',render : function(rowdata, rowindex,value) {
                    		return rowdata.acc_year+rowdata.acc_month;
            			}
					 },
                     { display: '开单科室编码', name: 'appl_dept_code', align: 'left'
					 },
                     { display: '开单科室名称', name: 'appl_dept_name', align: 'left'
					 },
                     { display: '执行科室编码', name: 'exec_dept_code', align: 'left'
					 },
                     { display: '执行科室名称', name: 'exec_dept_name', align: 'left'
					 },
                     { display: '收费类别编码', name: 'charge_kind_code', align: 'left'
					 },
                     { display: '收费类别名称', name: 'charge_kind_name', align: 'left'
					 },
                     { display: '金额', name: 'money', align: 'right',
							render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.money,2,1);
							}
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryIncomeCollection.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,checkBoxDisplay : f_checkBoxDisplay,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '校验', id:'check', click: check,icon:'add' },
                     	{ line:true },
                    	{ text: '归集', id:'incomeCollection', click: incomeCollection,icon:'back' },
                     	{ line:true },
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true },
    				]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function f_checkBoxDisplay(rowdata){
        
   	 if (rowdata.acc_year == "总" && rowdata.acc_month == "计")
			    return false;
		      return true;
	       }
   
    function loadDict(){
    	
    	 $("#year_month").ligerTextBox({ width:160 });
	     $("#e_year_month").ligerTextBox({ width:160});
	 		 
			 autodate("#year_month","yyyyMM");
			 autodate("#e_year_month","yyyyMM");
            //字典下拉框
            var param = {
            	  type_code:"('01','02')"
               };
            //字典下拉框
            var exec_param = {
      			  type_code:"('01','02')"
               };
            //字典下拉框
		 autocomplete("#appl_dept","../queryDeptDictNo.do?isCheck=false","id","text",true,true,param);
		 autocomplete("#exec_dept","../queryDeptDictNo.do?isCheck=false","id","text",true,true,exec_param); 
    	 autocomplete("#charge_kind_id","../queryChargeKindArrt.do?isCheck=false","id","text",true,true);
         }  


    //校验
    function check(){
    	parent.$.ligerDialog.open({
			title: '校验',
			height: 550,
			width: 1000,
			url: 'hrp/cost/costincomecollection/costCollectionCheckPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin:false, isResize: true, top: 10,
			parentframename: window.name,
		});
      }
    //归集 
    function incomeCollection(){
    	
    	if($("#year_month").val()==''){
    		
    		$.ligerDialog.error("统计年月不能为空");
    		
    		return false
    	}
    	
    	 var formPara={
    	            
    	          year_month : $("#year_month").val()

    	         };
        $.ligerDialog.confirm('是否归集?', function (yes){
        	           if(yes){
        	               ajaxJsonObjectByUrl("addIncomeCollection.do",formPara,function(responseData){      	               
        	                   if(responseData.state=="true"){
        	                	   query();
        	                   }
        	               });
        	               }
            	});
    }
    

        function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"统计日期："+$("#b_year_month").val()+"至"+$("#e_year_month").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "收入归集",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostInComeCollectionService",
	 	   			method_name: "queryCostInComeCollectionPrint",
	 	   			bean_name: "costInComeCollectionService",
	 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	 	   			
	 	       	};
	 	      //执行方法的查询条件
	 		  $.each(grid.options.parms,function(i,obj){
	 			printPara[obj.name]=obj.value;
	  	      });
	 		
	  	     officeGridPrint(printPara);	

	   		
	    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        </tr> 
	
        <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开单科室：</td>
            <td align="left" class="l-table-edit-td"><input name="appl_dept" type="text" id="appl_dept" /></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行科室：</td>
            <td align="left" class="l-table-edit-td"><input name="exec_dept" type="text" id="exec_dept" /></td>
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费类别：</td>
          <td align="left" class="l-table-edit-td"><input name="charge_kind_id" type="text" id="charge_kind_id" /></td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
