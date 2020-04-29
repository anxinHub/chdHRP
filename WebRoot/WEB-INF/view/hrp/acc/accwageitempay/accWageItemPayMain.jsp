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
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
	var para ="";
    
    var sumPara = "";
    
    var group_item = "";
    
	var tree_wage_code ="";
    
    var tree_wage_name ="";
    
    var scheme_code ="";
    
    var scheme_name ="";
    
    var tree;
    
    $(function ()
    {
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    	
    	//loadTree(null);
    	
    	//$("#layout1").ligerLayout({ leftWidth: 350,centerWidth:750,onLeftToggle: function (isColl){grid._onResize();},onRightToggle: function (isColl){}}); 
    	
        //$("#acc_time").ligerTextBox({cancelable: false});
    });
	
    //查询
    function  query(){
    	
		grid.toggleCol('EMP_NAME', true);
		
		grid.toggleCol('EMP_CODE', true);
		
		grid.toggleCol('DEPT_NAME', true);
		
		grid.toggleCol('EMP_COUNT', false);
		
		grid.toggleCol('EMP_SUM', false);
    	
		grid.options.parms=[];
		
		grid.options.newPage=1;
    	
      	var acc_time= acc_times.getValue();
      	
       if(liger.get("wage_code").getValue() == ""||acc_time ==""){
      		
      		$.ligerDialog.error('工资套和期间为必填项！');
    		
        	return;
      		
      	} 
        
        grid.options.parms.push({name:'item_code',value:para}); 
        
        grid.options.parms.push({name:'sum_item',value:sumPara});
        
        grid.options.parms.push({name:'group_item',value:group_item});
        
    	grid.options.parms.push({name:'wage_code',value:liger.get("wage_code").getValue()}); 
    	
    	grid.options.parms.push({name:'dept_nature',value:liger.get("dept_nature").getValue()}); 
    	
    	grid.options.parms.push({name:'acc_year',value:acc_time.split(".")[0]}); 
    	
    	grid.options.parms.push({name:'acc_month',value:acc_time.split(".")[1]}); 
    	
    	/* grid.options.parms.push({name:'kind_code',value:liger.get("emp_kind").getValue()}); 
    	
    	grid.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue()});  */
    	
    	grid.options.parms.push({name:'emp_code',value:$("#emp_code").val()}); 
    	
    	grid.options.parms.push({name:'query_para',value:"0"});
    	
    	grid.set("url","queryAccWageItemPay.do");
    	//加载查询条件
    	grid.loadData(grid.where);
     
    }
    
	function  query_sum(){
		
		grid.toggleCol('EMP_NAME', false);
		
		grid.toggleCol('EMP_CODE', false);
		
		grid.toggleCol('DEPT_NAME', false);
		
		grid.toggleCol('EMP_COUNT', true);
		
		grid.toggleCol('EMP_SUM', true);
    	
		grid.options.parms=[];
		
		grid.options.newPage=1;
    	
      	var acc_time= acc_times.getValue();
      	
      	 if(liger.get("wage_code").getValue() == ""||acc_time ==""){
      		
      		$.ligerDialog.error('工资套和期间为必填项！');
    		
        	return;
      		
      	} 
        
        grid.options.parms.push({name:'item_code',value:para}); 
        
        grid.options.parms.push({name:'sum_item',value:sumPara});
        
        grid.options.parms.push({name:'group_item',value:group_item});
        
    	grid.options.parms.push({name:'wage_code',value:liger.get("wage_code").getValue()}); 
    	
    	grid.options.parms.push({name:'acc_year',value:acc_time.split(".")[0]}); 
    	
    	grid.options.parms.push({name:'acc_month',value:acc_time.split(".")[1]}); 
    	
    	grid.options.parms.push({name:'dept_nature',value:liger.get("dept_nature").getValue()}); 
    	
    	grid.options.parms.push({name:'emp_code',value:$("#emp_code").val()});
    	
    	grid.options.parms.push({name:'query_para',value:"1"});
    	
    	grid.set("url","queryAccWageItemPay.do");
    	//加载查询条件
    	grid.loadData(grid.where);
     
    }

    function loadHead(){
    	
    	var columns = "";

    	var yf_columns = "";
    	
    	var kk_columns = "";
    	
    	var sf_columns = "";
    	//if(scheme_code==""){
    		
    		columns = columns +"{ display: '职工编码', name: 'EMP_CODE',width: '10%', align: 'left',frozen: true},"
    		
        	+"{ display: '职工名称', name: 'EMP_NAME',width: '10%', align: 'left',frozen: true}," 
        	
        	+"{ display: '专业类属', name: 'EMP_COUNT', align: 'left',frozen: true},"
    		
        	+"{ display: '人数', name: 'EMP_SUM', align: 'left',frozen: true},"
        	
        	+"{ display: '部门名称', name: 'DEPT_NAME', align: 'left',frozen: true},"
        	
        	+"{ display: '部门性质', name: 'OUT_NAME',width: '10%', align: 'left',frozen: true}" ;
        	
        	/*+"{ display: '发放方式', name: 'PAY_TYPE_NAME', align: 'left',frozen: true},"
    		
        	+"{ display: '备注', name: 'NOTE', align: 'left',frozen: true}"; */
        	
    		//}
    	
		para="";
    	
        sumPara="";
        
        group_item="";
    	
    	$.ajax({
			
			type: "POST", 
			
            url: "../accwagepay/queryAccWagePayGrid.do?isCheck=false",
            
            data: {"wage_code":liger.get("wage_code").getValue(),"acc_year":acc_times.getValue().split(".")[0]},
            
            dataType: "json",
            
            async: false,
            
            success: function(data){
            	
            	if(data.Rows.length>0){
            		
            		$.each(data.Rows,function(i,v){
            			
            			if(v.ITEM_NATURE=='06'){
            				
            				yf_columns = yf_columns + "{ display: '"+v.ITEM_NAME+"', name: '"+v.COLUMN_ITEM+"',formatter: '###,##0.00',width: '10%', align: 'right',"+
            				
                			"render:function(rowdata){ return rowdata."+v.COLUMN_ITEM+">0?formatNumber(rowdata."+v.COLUMN_ITEM+",2,1):formatNumber('0',2,1)}},";
    					
							para+= ",to_char(awp."+v.COLUMN_ITEM+") as "+v.COLUMN_ITEM;
            				
            				group_item+= ",awp."+v.COLUMN_ITEM;
            				
            				if(v.IS_SUM==1){
                				
                				sumPara+= ",to_char(sum(awp."+v.COLUMN_ITEM+")) as "+v.COLUMN_ITEM;
                				
                			}else{
                				
                				sumPara+=",'0' as "+v.COLUMN_ITEM
                			}
            				
            			}else if(v.ITEM_NATURE=='07'){
            				
            				kk_columns = kk_columns + "{ display: '"+v.ITEM_NAME+"', name: '"+v.COLUMN_ITEM+"',formatter: '###,##0.00',width: '10%', align: 'right',"+
            				
                			"render:function(rowdata){ return rowdata."+v.COLUMN_ITEM+">0?formatNumber(rowdata."+v.COLUMN_ITEM+",2,1):formatNumber('0',2,1)}},";
    					
							para+= ",to_char(awp."+v.COLUMN_ITEM+") as "+v.COLUMN_ITEM;
            				
            				group_item+= ",awp."+v.COLUMN_ITEM;
            				
            				if(v.IS_SUM==1){
                				
                				sumPara+= ",to_char(sum(awp."+v.COLUMN_ITEM+")) as "+v.COLUMN_ITEM;
                				
                			}else{
                				
                				sumPara+=",'0' as "+v.COLUMN_ITEM
                			}
            			
            			}else if(v.ITEM_NATURE=='08'){
            				
            				sf_columns = sf_columns + "{ display: '"+v.ITEM_NAME+"', name: '"+v.COLUMN_ITEM+"',formatter: '###,##0.00',width: '10%', align: 'right',"+
            				
                			"render:function(rowdata){ return rowdata."+v.COLUMN_ITEM+">0?formatNumber(rowdata."+v.COLUMN_ITEM+",2,1):formatNumber('0',2,1)}},";
    					
							para+= ",to_char(awp."+v.COLUMN_ITEM+") as "+v.COLUMN_ITEM;
            				
            				group_item+= ",awp."+v.COLUMN_ITEM;
            				
            				if(v.IS_SUM==1){
                				
                				sumPara+= ",to_char(sum(awp."+v.COLUMN_ITEM+")) as "+v.COLUMN_ITEM;
                				
                			}else{
                				
                				sumPara+=",'0' as "+v.COLUMN_ITEM
                			}
            			}
            			
            		});
            		
            		if(yf_columns.length>0){
            			
            			columns = columns +",{ display: '应发金额', align: 'center',columns:["+yf_columns.substr(0,yf_columns.length-1)+"]}"
            		}
            		
					if(kk_columns.length>0){
            			
            			columns = columns +",{ display: '扣款金额', align: 'center',columns:["+kk_columns.substr(0,kk_columns.length-1)+"]}"
            		}
					
					if(sf_columns.length>0){
            			
            			columns = columns +",{ display: '实发金额', align: 'center',columns:["+sf_columns.substr(0,sf_columns.length-1)+"]}"
            		}
            		
            	}
            	
            }
		});
    	
    	grid = $("#maingrid").ligerGrid({
    		
           columns:  eval("["+columns+"]"),
           
           dataAction: 'server',dataType: 'server',usePager:true,url:'',
                     
           width: '100%', height: '98%', checkbox: true,rownumbers:true,
           
           selectRowButtonOnly:true,delayLoad:true,columnWidth:'15%',minColumnWidth:100,
           
           toolbar: { items: [
                              
						{ text: '汇总查询', id:'search', click: query_sum,icon:'search' },
						
						{ line:true },    
						
						{ text: '明细查询', id:'search', click: query,icon:'search' },
						
						{ line:true },
                     	
						{ text: '打印', id:'print', click: myPrint,icon:'print' },
                     	 
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
		
        $("#emp_code").ligerTextBox({width:160});
        
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
    	
    	/* var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"职工编码: "+emp_code.split(" ")[0],"colspan":colspan_num,"br":true} ,
    				{"cell":0,"value":"职工名称: "+emp_code.split(" ")[1],"colspan":colspan_num,"br":true},
    				{"cell":0,"value":"部门名称: "+grid.rows[0].dept_name,"colspan":colspan_num,"br":true}
        	]}; */
        	
        	var printPara={
          		title:  acc_times.getValue().split(".")[0]+"年"+acc_times.getValue().split(".")[1]+"月"+liger.get("wage_code").getText()+"工资发放表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.acc.service.wagedata.AccWageItemPayService",
       			method_name: "queryAccWageItemPayPrint",
       			bean_name: "accWageItemPayService"/* ,
       			heads: JSON.stringify(heads), *///表头需要打印的查询条件,可以为空
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
			<div id="maingrid"></div>
		</div>
	<!-- </div> -->
</body>
</html>
