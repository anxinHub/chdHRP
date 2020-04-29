<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    var is_stop = 0;
   
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	query();
    	loadHotkeys();
    	
    	$("#is_stop").change(function () {
    		if ($(this).val() === '1') {
    			is_stop = 1;
    		} else {
    			is_stop = 0;
    		}
    	})
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		 if(liger.get("is_stop").getValue()){
     			is_stop=liger.get("is_stop").getValue()
     		}
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'emp_id',value:liger.get("emp_code").getValue()});
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue()}); 
    	grid.options.parms.push({name:'kind_code',value:liger.get("kind_code").getValue()}); 
    	grid.options.parms.push({name:'pay_type_code',value:liger.get("pay_type_code").getValue()}); 
    	grid.options.parms.push({name:'is_stop',value:is_stop}); 
    	grid.options.parms.push({name:'is_pay',value:$("#is_pay").val()}); 
    	grid.options.parms.push({name:'id_number',value:$("#id_number").val()}); 
		
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     }
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '职工编码', name: 'emp_code', align: 'left',width:70,
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.emp_id   + "|" + 
									rowdata.emp_no +"')>"+rowdata.emp_code+"</a>";
							}
					 },
                     { display: '职工名称', name: 'emp_name', align: 'left'
					 },
					 { display: '职工分类', name: 'kind_name', align: 'left'
					 },
					 { display: '部门', name: 'dept_name', align: 'left',width:100
					 },
					 { display: '国籍', name: 'countries_name', align: 'left',width:100,
					 },
					 { display: '是否发放工资', name: 'pay', align: 'left',width:100
					 },
					 { display: '发放方式', name: 'pay_type_name', align: 'left'
					 },
					 { display: '岗位', name: 'station_name', align: 'left'
					 },
					 { display: '职务', name: 'duty_name', align: 'left'
					 },
					 { display: '身份证号', name: 'id_number', align: 'left',width:150,
					 },
                     { display: '是否停用', name: 'is_stop', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_stop == 0){
									return "启用";
								}else{
									return "停用";
								}
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,
                     url: '../../sys/empdict/queryEmpDict.do',
                     delayLoad: true,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     /* toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true }
    				]}, */
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.emp_id   + "|" + 
								rowdata.emp_no 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1]+"&emp_id="+ vo[2]+"&emp_no="+ vo[3];
		
		parent.$.ligerDialog.open({ url : 'accCheckEmpUpdatePage.do?isCheck=false&' + parm,data:{}, height: 400,width: 930, 
			title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			parentframename:window.name,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccEmpAttr(); },
			cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    function add_btn(){
    	
    var ParamVo ="";
    	
	var data = gridManager.getCheckedRows();
    	
        $(data).each(function (){	

		ParamVo+=this.emp_id+",";  
		
        });
         
        parent.$.ligerDialog.open({ url : '../baseData/initAccEmpAttrMainPage.do?isCheck=false&param='+ParamVo,
    		data:{}, height: 480,width: 500, title:'批量修改',modal:true,showToggle:false,showMax:false,
    		showMin: false,isResize:true, parentframename:window.name,
    		buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccDeptAttr(); },
    		cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    	
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
/* 
		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('K', printBarcode); */

	}
    
    function loadDict(){
            //字典下拉框
            
    	autocomplete("#emp_code","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true,'',false,'',120);
    	autocomplete("#kind_code","../../sys/queryEmpKindDict.do?isCheck=false","id","text",true,true,'',false,'',120);
    	autocomplete("#pay_type_code","../../sys/queryEmpPay.do?isCheck=false","id","text",true,true,'',false,'',120);
    	autocomplete("#dept_id","../../sys/queryHosDept.do?isCheck=false","id","text",true,true,'',false,'',120);
    	$("#emp_code").ligerTextBox({width:120});
   	 	$("#kind_code").ligerTextBox({width:120}); 
   	 	$("#pay_type_code").ligerTextBox({width:120});
   		$("#dept_id").ligerTextBox({width:120});
   	 	$("#is_stop").ligerComboBox({width:120}); 
   	 	$("#is_pay").ligerComboBox({width:120}); 
   	 	$("#id_number").ligerTextBox({width:120}); 
    }  
    
    function printDate(){
		 if(grid.getData().length==0){
		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'职工',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.sys.service.EmpDictService",
			method_name: "queryEmpDictPrint",
			bean_name: "empDictService",
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
            <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发放方式：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_type_code" type="text" id="pay_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
              		<select id="is_stop" name="is_stop" style="width: 135px;">
            		   	<option value="">&nbsp;</option>
	                	<option value="0">启用</option>
	                	<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否发放工资：</td>
            <td align="left" class="l-table-edit-td">
            		<select id="is_pay" name="is_pay" >
          		   		<option value="">&nbsp;</option>
               			<option value="0">否</option>
               			<option value="1">是</option>
                	</select>
            </td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">身份证号：</td>
            <td align="left" class="l-table-edit-td"><input name="id_number" type="text" id="id_number" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td>
            	<input class="l-button l-button-test"  type="button" value="查询(Q)" onclick="query();"/>
            </td>
            <td>
            	<input class="l-button l-button-test"  type="button" value="批量修改" onclick="add_btn();"/>
            	<input class="l-button l-button-test"  type="button" value="打 印" onclick="printDate();"/>
            </td>
            
        </tr>
    </table>

	<div id="maingrid"></div>

</body>
</html>
