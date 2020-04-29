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
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>

<script src="/CHD-HRP/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var acc_times;
    
    $(function ()
    {
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    	
    });
	
    //查询
    function  query(){
    	
		grid.options.parms=[];
		
		grid.options.newPage=1;
    	
      	var acc_time= acc_times.getValue();
      	
       if(liger.get("wage_code").getValue() == ""||acc_time ==""){
      		
      		$.ligerDialog.error('工资套和期间为必填项！');
    		
        	return;
      		
      	} 
        
    	grid.options.parms.push({name:'wage_code',value:liger.get("wage_code").getValue()}); 
    	
    	grid.options.parms.push({name:'emp_code',value:liger.get("emp_code").getValue()}); 
    	
    	grid.options.parms.push({name:'acc_year',value:acc_time.split(".")[0]}); 
    	
    	grid.options.parms.push({name:'acc_month',value:acc_time.split(".")[1]}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     
    }
    
    function loadHead(){
    	
    	grid = $("#grid").ligerGrid({
            columns: [ 
                      { display: '时间', name: 'wage_date', align: 'left'},
                      { display: '发放名称', name: 'wage_name', align: 'left'},
                      { display: '应发合计', name: 'yf_item', align: 'center'},
                      { display: '扣款合计', name: 'kk_item', align: 'center'},
                      { display: '实发合计', name: 'sf_item', align: 'center'},
                      { display: '发放形式', name: 'pay_type', align: 'center'},
                      { display: '备注', name: 'note', align: 'center'}
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccBankNetWageMain.do',
                      width: '100%', height: '100%', checkbox: false,rownumbers:true,
                      selectRowButtonOnly:true,delayLoad:true,
                      toolbar: { items: [
                      	{ text: '查询', id:'search', click: query,icon:'search' },
                      	{ line:true }
     				]}
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
        
    }
    
    function loadDict(){
        //字典下拉框
		var fromData={
        		
        		wage_code:'0000'
        
        }

		autocompleteObj({
    		id:"#wage_code",
    		urlStr:"../queryAccWage.do?isCheck=false",
    		valueField:"id",
    		textField:"text",
    		autocomplete:true,
    		highLight:true,
    		parmsStr:null,
    		defaultSelect:false,
    		initvalue:null,
    		initWidth:"160",initHeight:null,boxwidth:null,alwayShowInDown:null,selectEvent:function (value){
    			
    			loadHead();

			}
    	});
        
        autocomplete("#dept_nature","../queryDeptOut.do?isCheck=false","id","text",true,true);
		
        autocomplete("#emp_code","../../sys/queryEmpDict.do?isCheck=false&is_stop=0","id","text",true,true);
        
		acc_times = $("#acc_time").etDatepicker({
		    view: "months",
		    minView: "months",
		    dateFormat: "yyyy.mm",
		    width:'162',
		    defaultDate: true,
		});
		
		var year_Month = '${wage_year_month}';
		
		if(year_Month.toString()=="000000"){
			
			var date=new Date;
			
			 var year=date.getFullYear();
			 
			 var month=date.getMonth()+1;
			 
			 month =(month<10 ? "0"+month:month); 
			 
			 year_Month = (year.toString()+month.toString());
			
		}
		
	    acc_times.setValue(year_Month.substring(0,4)+"."+year_Month.substring(4,6).toString());
		 
     } 
    
	function myPrint(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
        	var printPara={
          		title: "工资发放表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.acc.service.wagedata.AccWageItemPayService",
       			method_name: "queryAccWageItemPayPrint",
       			bean_name: "accWageItemPayService"
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
   		<div position="center"  title="  ">
		    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		       <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
		            <td align="left" class="l-table-edit-td"><input name="acc_time" type="text" id="acc_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套名称：</td>
		            <td align="left" class="l-table-edit-td" colspan="2"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
		            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门性质：</td>
		            <td align="left" class="l-table-edit-td"><input name="dept_nature" type="text" id="dept_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		        </tr>  
		    </table>
			<div id="grid"></div>
		</div>
	<!-- </div> -->
</body>
</html>
