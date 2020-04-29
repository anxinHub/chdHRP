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
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>

<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
	var para ="";
    
    var sumPara = "";
    
    var group_item="";

	var tree_wage_code ="";
    
    var tree_wage_name ="";
    
    var scheme_code ="";
    
    var scheme_name ="";
    
    var tree;
    
    var acc_times;
    
	var setting = {   
    		
    		data: {
    			simpleData: {
    				enable: true,
    				idKey: "id",
    				pIdKey: "pId",
    				rootPId: 0
    				
    			},
    		    key: {
    				children: "nodes"
    			}
    		},
    		check: {
    			enable: false
    		}, 
    		treeNode:{
    			open:true
    		},
    		callback:{
    			onClick: zTreeOnClick
    		}                

       }; 
    
    $(function ()
    {
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    	
    	loadTree(null);
    	
    	$("#layout1").ligerLayout({ 
    		leftWidth: 300,
    		centerWidth:800,
    		onLeftToggle: function (isColl){
    			grid._onResize();
   			},
   			onEndResize: function(isColl) {
                grid._onResize()
            }
 		}); 
    	
    	$("#bank_code").ligerTextBox({width:160});
    	
    	$("#is_number").ligerTextBox({width:160});
    	
    	//$("#acc_time").ligerTextBox({cancelable: false});

    	$("#wage_code").bind("change",function(){
    		
    		var fromData={
                		
                		wage_code:liger.get("wage_code").getValue()
                
               }

            	autocomplete("#scheme_name","../queryAccWageScheme.do?isCheck=false&scheme_type_code=05","id","text",true,true,fromData);
        })
        
    });
    
	function zTreeOnClick(event, treeId, treeNode) {
		setTimeout(function() {
			if(treeNode.pId==0){
	    		
	    		tree_wage_code = treeNode.id;
	    		
	    		scheme_code = "";
	    		
	    		scheme_name = "";
	    		
	    		tree_wage_name = treeNode.name;
	    		
	    	}else{
	    		
	    		tree_wage_code = treeNode.pId;
	    		
	    		scheme_code = treeNode.id;
	    		
	    		scheme_name= treeNode.name
	    
	    		tree_wage_name=treeNode.getParentNode().name.split(" ")[1];
	    	}
	    	
	    	if(grid.get("url"))     //检测它的URL值，若它为空则直接渲染(不含数据)，若有值则清空表格数据再重新渲染
	   	 	{
			 	grid.set("url","");
			 	$("#maingrid").find("tbody").children().remove();
			 	loadHead();
				query();
	   	
	   	 	}else {
				loadHead();
				query();
	   	 	}
	    	
	    	var fromData = {

                'wage_code': tree_wage_code,

                'acc_year': acc_times.getValue().split(".")[0],

                'scheme_id': scheme_code

            };
    		$(".l-grid1 .l-grid-header-inner").width("100%");
            autocomplete("#item_code", "../queryAccWageItemColumn.do?isCheck=false&is_stop=0", "id", "text", true, true, fromData);
	    	
			
		}, 0)
    };
    
    //查询
    function  query(){
	
    	grid.options.parms=[];
		
		grid.options.newPage=1;
		
      	var acc_time= acc_times.getValue();
      	
      	if(tree_wage_code == ""||acc_time ==""){
      		
      		$.ligerDialog.error('工资套和期间为必填项！');
    		
        	return;
      		
      	}
      	
      	 var acc_money = $("#money1").val();
      	 
      	 var item_code = liger.get("item_code").getValue();
      	
        grid.options.parms.push({name:'item_code',value:para}); 
        
        grid.options.parms.push({name:'sum_item',value:sumPara});
        
        grid.options.parms.push({name:'group_item',value:group_item});
        
    	grid.options.parms.push({name:'wage_code',value:tree_wage_code}); 
    	
    	grid.options.parms.push({name:'acc_year',value:acc_time.split(".")[0]}); 
    	
    	grid.options.parms.push({name:'acc_month',value:acc_time.split(".")[1]}); 
    	
        grid.options.parms.push({name:'scheme_id',value:scheme_code}); 
    	
    	grid.options.parms.push({name:'kind_code',value:liger.get("emp_kind").getValue()}); 
    	
    	grid.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue().split(".")[0]}); 
    	
    	grid.options.parms.push({name:'emp_code',value:$("#emp_code").val()}); 
    	
		grid.options.parms.push({name:'bank_code',value:$("#bank_code").val()}); 
    	
    	grid.options.parms.push({name:'is_number',value:$("#is_number").val()});
    	
    	grid.options.parms.push({name:'wage_type',value:liger.get("type_id").getValue()});
    	
    	grid.options.parms.push({name:'account_codes',value:liger.get("account_codes").getValue()});
    	
    	if(item_code!=""&&$("#wage_item").val()!=""&&acc_money!=""){
           	
           	grid.options.parms.push({
                   name: 'item_name',
                   value: "awp." + item_code+" "+$("#wage_item").val()+" "+acc_money
               });
           	
           }
    	
    	grid.set("url","queryAccBankIssuing.do");
    	
    	//加载查询条件
    	grid.loadData(grid.where);
    	grid._onResize();
    	
    }

    function loadHead(){
    	
    	var columns = "";

    	//if(scheme_code==""){
    		
    		columns = columns +"{ display: '职工编码', name: 'EMP_CODE', align: 'left',frozen: true,width:'10%'},"
    		
        	+"{ display: '职工名称', name: 'EMP_NAME', align: 'left',frozen: true,width:'10%'},"
    		
        	+"{ display: '职工分类', name: 'KIND_NAME', align: 'left',frozen: true,width:'10%'},"
        	
        	+"{ display: '部门编码', name: 'DEPT_CODE', align: 'left',frozen: true,width:'10%'},"
    		
        	+"{ display: '部门名称', name: 'DEPT_NAME', align: 'left',frozen: true,width:'10%'},"
        	
        	/* +"{ display: '发放方式', name: 'PAY_TYPE_NAME', align: 'left',frozen: true,width:'10%'},"
        	
        	+"{ display: '身份证号', name: 'ID_NUMBER', align: 'left',frozen: false,width:'10%'}," */
			
        	+"{ display: '银行账号', name: 'ACCOUNT_CODE', align: 'left',frozen: false,width:'10%'}";
    		
    		//} 
    	
    	para="";
        	
        sumPara="";
            
        group_item="";
   
    	$.ajax({
			
			type: "POST", 
			
            url: "../accwagepay/queryAccWagePayGrid.do?isCheck=false",
            
            data: {'scheme_id':scheme_code,"wage_code":tree_wage_code,"acc_year":acc_times.getValue().split(".")[0]},
            
            dataType: "json",
            
            async: false,
            
            success: function(data){
            	
            	if(data.Rows.length>0){
            		
            		$.each(data.Rows,function(i,v){
            			
            			if(v.ITEM_NAME.indexOf("实发")>-1){
            				
            				columns = columns + ",{ display: '"+v.ITEM_NAME+"', name: '"+v.COLUMN_ITEM+"',formatter: '###,##0.00', align: 'right',"+
            				
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
            		
            		columns = columns.substr(0,columns.length-1);
            		
            	}
            	
            }
            
		});
		
    	grid = $("#maingrid").ligerGrid({
    		
           columns:  eval("["+columns+"]"),
           
           dataAction: 'server',dataType: 'server',usePager:true,url:'',
                     
           width: '100%', height: '100%', checkbox: true,rownumbers:true,
           
           selectRowButtonOnly:true,delayLoad:true,columnWidth:'15%',minColumnWidth:70,
           
           toolbar: { items: [
						
						{ text: '查询', id:'search', click: query,icon:'search' },
						
						{ line:true } ,
						
						{ text: '打印', id:'print', click: print,icon:'print' },
                     	
						{ line:true }
						
    				]}
    	
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
    }
    
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
       	var printPara={
         		title: "银行代发",//标题
         		columns: JSON.stringify(grid.getPrintColumns()),//表头
         		class_name: "com.chd.hrp.acc.service.wagedata.AccBankIssuingService",
      			method_name: "queryAccBankIssuingPrint",
      			bean_name: "accBankIssuingService"
          	};
       	
       	//执行方法的查询条件
      	$.each(grid.options.parms,function(i,obj){
      		printPara[obj.name]=obj.value;
       	});
      		
       	officeGridPrint(printPara);
   		
    }
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	
                	var wage_code = liger.get("wage_code").getValue();
                	
                	if(wage_code ==""){
                		
                		$.ligerDialog.error('请选择工资套！');
                		
                    	return;
                		
                	}
                		
              		$.ligerDialog.open({url: 'accWagePayAddPage.do?isCheck=false&wage_code='+wage_code, height: 400,width: 590, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		
              		return;
                
            }   
        }
        
    }
    
    function openUpdate(obj,group_id,hos_id,copy_code){
    	
    	var vo = obj.split("|");
    	
		var parm = "type_id="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2]   +"&copy_code="+ 
			vo[3];

    	$.ligerDialog.open({ url : 'accWageTypeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccBank(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
	function loadTree(obj){
    	
    	ajaxJsonObjectByUrl("../accwagepay/queryAccWagePayTree.do?isCheck=false&scheme_type_code=05",obj,function (responseData){

    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	});
    }
    
	function btn_query(){
	   	
	   	var wage=liger.get("wage_code").getValue();
	   	
	   	var scheme = liger.get("scheme_name").getValue();
	   	
	   	var forData={
	   			
	   			wage_code:wage,
	   			
	   			scheme_code:scheme
	   	}
	   	
	   	loadTree(forData);
	   	
	   }
	
	// 维护方案
	function btn_add(){
    	var nodes= tree.getSelectedNodes();
    	var node_name;
    	var scheme_code;
    	var scheme_name;
    	var acc_year = acc_times.getValue().split(".")[0];
    	if(nodes ==""||nodes ==null){
    		$.ligerDialog.error('请选择工资套或方案进行维护！');
        	return;
    	}
    	
    	if(nodes[0].pId=="0"){
    		node_name = nodes[0].name;
    	}else{
    		node_name =nodes[0].getParentNode().name;
    		scheme_code=nodes[0].id;
    		scheme_name=nodes[0].name
    	}
    	
//     	parent.openDialog({ url : 'hrp/acc/accwagepay/accWageSchemeMainPage.do?isCheck=false&node='+node_name+'&scheme_code='+scheme_code+'&scheme_name='+scheme_name+'&acc_year='+acc_year+'&scheme_type_code='+'05',data:{}, height: 0,width: 0, title:'工资方案维护',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	
    	parent.$.ligerDialog.open({
            url : 'hrp/acc/accwagepay/accWageSchemeMainPage.do?isCheck=false&node='+node_name+'&scheme_code='+scheme_code+'&scheme_name='+scheme_name+'&acc_year='+acc_year+'&scheme_type_code='+'05',
            data : {},
            width :$(parent.window).width(),
            height : $(parent.window).height(),
            title : '工资方案维护',
            modal : true,
            top:0,
            showClose: true,
            showToggle : false,
            showMax : false,
            showMin : true,//是否最小化
            isResize : false,
            parentframename:window.name
        });
    }
	
		function btn_delete(){
	    	
	    	var formData={
	    		
	    			wage_code:tree_wage_code,
	    			
	    			scheme_code:scheme_code
	    			
	    	}
	    	
	    	if(scheme_code==""){
	    		
				$.ligerDialog.error('请选择要删除的方案！');
	    		
	        	return;
	    		
	    	}else{

	    		$.ligerDialog.confirm("该方案存在业务数据,是否确认删除?", function (yes){
	            	if(yes){
	                	ajaxJsonObjectByUrl("../accwagepay/deleteAccWageScheme.do?isCheck=false",formData,function (responseData){
	                		if(responseData.state=="true"){
	                			btn_query();
	                		}
	                	});
	            	}
	            });
	    		
	    		return;
	    		
	    	}
	    }
    
    function loadDict(){
    	
    	 $("#wage_item").ligerTextBox({
             width: 50
         });
    	 $("#money1").ligerTextBox({
             width: 100
         });
    	 
    	 $("#item_code").ligerComboBox({
             selectBoxWidth: 160,
             autocomplete: true,
             width: 160
         });
    	 
    	 $("#account_codes").ligerComboBox({
             selectBoxWidth: 160,
             autocomplete: true,
             width: 160,
             data: [
                    { text: '全部', id: 0 },
                    { text: '已录入', id: 1 },
                    { text: '未录入', id: 2 }
                ]
         });
        //字典下拉框
        var fromData={
        		
        		wage_code:'0000'
        
        }

    	autocomplete("#scheme_name","../queryAccWageScheme.do?isCheck=false","id","text",true,true,fromData);
		  
        autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true);
        
        autocomplete("#emp_kind","../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0","id","text",true,true);
        
        autocomplete("#dept_code","../queryDeptDictNo.do?isCheck=false&is_stop=0","id","text",true,true);
    
       // autocomplete("#emp_code","../../sys/queryEmpDict.do?isCheck=false&is_stop=0","id","text",true,true);
		
        $("#emp_code").ligerTextBox({width:160});
        
        autocomplete("#type_id","../queryWageType.do?isCheck=false","id","text",true,true);
        
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
			 
			 month =(month<10?"0"+month:month); 
			 
			 year_Month = (year.toString()+month.toString());
			
		}
		
	    acc_times.setValue(year_Month.substring(0,4)+"."+year_Month.substring(4,6).toString());
		 
     } 
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div id="layout1">
          <div position="left" title="  ">
	        <table cellpadding="0" cellspacing="0" class="l-table-edit">
		   	 	<tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套名称：</td>
		            <td align="left" class="l-table-edit-td" colspan="2"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
		        </tr>
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案名称：</td>
		            <td align="left" class="l-table-edit-td" colspan="2"><input name="scheme_name" type="text" id="scheme_name" ltype="text" validate="{required:true,maxlength:18}" /></td>
		        </tr>
		        <tr>
			        <td align="left"><input class="l-button" type="button" style="width: 80px;margin-left: 20px" id="query1" value="查询(Q)" onclick="btn_query();" /></td>
			        <td align="left"><input class="l-button" type="button" style="width: 80px;margin-left: 20px" id="query2" value="维护方案(A)" onclick="btn_add();" /></td>
			        <td align="left"><input class="l-button" type="button" style="width: 80px;margin-left: 20px" id="query3" value="删除方案(D)" onclick="btn_delete();" /></td>
		        </tr>
   			</table>
   			  <hr>
  			   <div style="width:97%; height:77%;overflow:auto;border: 1px solid #AECAF0;margin-left: 5px;margin-top: 5px" >
	      		<ul class="ztree" id="tree" ></ul>
		   </div>
        </div>
        <div position="center"  title="  ">
		    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		       <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
		            <td align="left" class="l-table-edit-td" ><input name="acc_time" type="text" id="acc_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		        
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
		            <td align="left" class="l-table-edit-td"><input name="emp_kind" type="text" id="emp_kind" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
		            <td align="left" class="l-table-edit-td" ><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		       </tr> 
		       <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
		            <td align="left" class="l-table-edit-td" ><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资账号：</td>
		            <td align="left" class="l-table-edit-td" ><input name="is_number" type="text" id="is_number" ltype="text" validate="{required:true,maxlength:50}" /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">账号类别：</td>
		            <td align="left" class="l-table-edit-td"><input name="type_id" type="text" id="type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		            
		        </tr>
		        <tr>
					   <td align="right" class="l-table-edit-td" >工资项：</td>
			            <td align="right" class="l-table-edit-td" ><input name="item_code" type="text" id="item_code" /></td>
			        	<td align="left" class="l-table-edit-td" ><select id="wage_item">
			        	<option value="<">&lt;</option>
			        	<option value="<=">&lt;=</option>
			        	<option value=">">&gt;</option>
			        	<option value=">=">&gt;=</option>
			        	<option value="=">=</option>
			        	<option value="!=">!=</option>
			        	</select></td>
						<td align="left"><input name="money1" type="text" id="money1"  ltype="text" validate="{required:true,maxlength:18}" />
					<td align="left"></td>
					<td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行账号：</td>
		            <td align="left" class="l-table-edit-td"><input name="account_codes" id="account_codes" ltype="text" validate="{required:true,maxlength:20}" /></td>
					   
		        </tr>
		    </table>
			<div id="maingrid"></div>
		</div>
	</div>
</body>
</html>
