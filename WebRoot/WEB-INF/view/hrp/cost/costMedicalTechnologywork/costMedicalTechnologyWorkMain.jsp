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
        grid.options.parms.push({name:'b_year_month',value:$("#b_year_month").val()}); 
    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: '', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
                        	 if(rowdata.acc_year+rowdata.acc_month!="合计"){
    							return "<a href=javascript:openUpdate('"
    							+ rowdata.group_id  +"|"
    							+ rowdata.hos_id    + "|" 
    							+ rowdata.copy_code + "|"
    							+ rowdata.acc_year  + "|"
    							+ rowdata.acc_month + "|"
    						    + rowdata.dept_id   + "|"
    							+ rowdata.dept_no   + "')>"+rowdata.acc_year +rowdata.acc_month+"</a>";
                        	 }else{
                        		 return rowdata.acc_year+rowdata.acc_month;
                        	  }
                   			}
					 },
					 { display: '科室编码', name: 'dept_code', align: 'left'},
					 { display: '科室名称', name: 'dept_name', align: 'left'},
                     { display: '工作量', name: 'medical_num', align: 'left'}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostMedicalTechnologyWork.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     checkBoxDisplay : f_checkBoxDisplay,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: remove,icon:'delete'},
		                { line:true },
		                { text: '导入', id:'import', click: imp,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
        				if(rowdata.acc_year + rowdata.acc_month != "合计")
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.acc_year   + "|" + 
								rowdata.acc_month   + "|" + 
								rowdata.dept_id   + "|" + 
								rowdata.dept_no 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function f_checkBoxDisplay(rowdata){
   	     if (rowdata.acc_year+rowdata.acc_month == "合计")
			    return false;
	   }

    
    function add_open(){
	    	$.ligerDialog.open({
	    	    url: 'costMedicalTechnologyWorkAddPage.do?isCheck=false',
	    	    height: 300,
	    	    width: 400,
	    	    title: '添加',
	    	    modal: true,
	    	    showToggle: false,
	    	    showMax: false,
	    	    showMin: true,
	    	    isResize: true,
	    	    buttons: [{
	    	        text: '确定',
	    	        onclick: function(item, dialog) {
	    	            dialog.frame.saveCostMedicalTechnologyWork();
	    	        },
	    	        cls: 'l-dialog-btn-highlight'
	    	    },
	    	    {
	    	        text: '取消',
	    	        onclick: function(item, dialog) {
	    	            dialog.close();
	    	        }
	    	    }]
	    	});
        }

        function remove(){
	    	var data = gridManager.getCheckedRows();
	        if (data.length == 0){
	        	$.ligerDialog.error('请选择行');
	        }else{
	            var ParamVo =[];
	            $(data).each(function (){					
					ParamVo.push(
					//表的主键
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.acc_year   +"@"+ 
							this.acc_month   +"@"+ 
							this.dept_id   +"@"+ 
							this.dept_no
					)
	            });
	            $.ligerDialog.confirm('确定删除?', function (yes){
	            	if(yes){
	                	ajaxJsonObjectByUrl("deleteCostMedicalTechnologyWork.do",{ParamVo : ParamVo.toString()},function (responseData){
	                		if(responseData.state=="true"){
	                			query();
	                		}
	                	});
	            	}
	            }); 
	        }
        }

    function imp(){

    	var para={
			    "column": [
			        {
			            "name": "acc_year",
			            "display": "统计年度",
			            "width": "200",
			            "require":true
			        },{
			            "name": "acc_month",
			            "display": "统计月份",
			            "width": "200",
			            "require":true
			        },{
			            "name": "dept_code",
			            "display": "科室编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "dept_name",
			            "display": "科室名称",
			            "width": "200",
			            "require":true
			        },{
			            "name": "medical_num",
			            "display": "工作量",
			            "width": "200",
			            "require":true
			        },
			    ]
			};

    	importSpreadView("hrp/cost/costMedicalTechnologywork/costMedicalTechnologyWorkImportPage.do?isCheck=false",para); 
        
     }

    
    function openUpdate(obj){
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"acc_year="+vo[3]   +"&"+ 
			"acc_month="+vo[4]   +"&"+ 
			"dept_id="+vo[5]   +"&"+ 
			"dept_no="+vo[6]  
		
		$.ligerDialog.open({
		    url: 'costMedicalTechnologyWorkUpdatePage.do?isCheck=false&' + parm,
		    data: {},
		    height: 300,
		    width: 500,
		    title: '修改',
		    modal: true,
		    showToggle: false,
		    showMax: false,
		    showMin: false,
		    isResize: true,
		    buttons: [{
		        text: '确定',
		        onclick: function(item, dialog) {
		            dialog.frame.saveCostMedicalTechnologyWork();
		        },
		        cls: 'l-dialog-btn-highlight'
		    },
		    {
		        text: '取消',
		        onclick: function(item, dialog) {
		            dialog.close();
		        }
		    }]
		});

    }
    function loadDict(){
            //字典下拉框
            var dept_para = {type_code : "('01','02','03','04')"};
            
            autocomplete("#dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true,dept_para);
            
            $("#b_year_month").ligerTextBox({ width:160 });
   	        $("#e_year_month").ligerTextBox({ width:160});
   	 		 
   			 autodate("#b_year_month","yyyyMM");
   			 autodate("#e_year_month","yyyyMM");
         }  
    
     
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="b_year_month" type="text" id="b_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
