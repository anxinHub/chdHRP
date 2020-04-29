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
    
  //打印 单元格格式化 用
    var renderFunc = {};
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		//loadHotkeys();
		
/* 		$("#budg_year").change(function(){
			query() ;
		})
		$("#proj_id").change(function(){
			query() ;
		}) */
		
    });
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'duty_dept_id',value:liger.get("duty_dept_name").getValue()}); 
		grid.options.parms.push({name:'payment_item_id',value:liger.get("payment_item_name").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '归口科室编码', name: 'duty_dept_code', align: 'left',
                    	 render:function(rowdata,rowindex,value){
	                    	  return "<a href=javascript:openUpdate('"
								+ rowdata.dept_id + "|"
								+ rowdata.payment_item_id + "|"
								+ rowdata.duty_dept_id +"')>"+ rowdata.duty_dept_code +"</a>"; 
							}
					 	},
                     { display: '归口科室名称', name: 'duty_dept_name', align: 'left'
					 		},
					 { display: '支出项目编码', name: 'payment_item_code', align: 'left'
					 		},
                     { display: '支出项目名称', name: 'payment_item_name', align: 'left'
					 		},
					 { display: '科室编码', name: 'dept_code', align: 'left'
					 		},
                     { display: '科室名称', name: 'dept_name', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryExpenditureItemBelong.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     delayLoad:true ,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' },
		                { line:true }
    				]},onDblClickRow : function (rowdata, rowindex, value)
    				{
    					openUpdate(rowdata.dept_id + "|"
								+ rowdata.payment_item_id + "|"
								+ rowdata.duty_dept_id );
    				}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  //打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="项目与收入预算科目对应关系";
    }
    
    function add_open(){

		parent.$.ligerDialog.open({ url : 'hrp/budg/base/budgpayitem/expenditureItem/ExpenditureItemBelongAddPage.do?isCheck=false',data:{},
				height: 150,width:300,title:'添加支出项目归口',
				modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    	}); 
		
    }
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
    	//console.info(data);
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
	            var ParamVo =[];
	            $(data).each(function (){
					ParamVo.push(
					this.dept_id   +"@"+ 
					this.payment_item_id   +"@"+ 
					this.duty_dept_id
					) 
				});
	            $.ligerDialog.confirm('确定删除?', function (yes){
	            	if(yes){
	                	ajaxJsonObjectByUrl("deleteExpenditureItemBelong.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
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
			            "name": "dept_code",
			            "display": "科室代码",
			            "width": "200",
			            "require":true
			        },
			        {
			            "name": "dept_name",
			            "display": "科室名称",
			            "width": "200",
			            "require":true
			        },
			        {
			            "name": "payment_item_code",
			            "display": "支出项目编码",
			            "width": "200",
			            "require":true
			        },
			        {
			            "name": "payment_item_name",
			            "display": "支出项目名称",
			            "width": "200",
			            "require":true
			        },
			        {
			            "name": "duty_dept_code",
			            "display": "归口科室编码",
			            "width": "200",
			            "require":true
			        },
			        {
			            "name": "duty_dept_name",
			            "display": "归口科室名称",
			            "width": "200"
			        }
			    ]
			};
			importSpreadView("hrp/budg/base/budgpayitem/expenditureItem/budgExpenditureItemBelongImport.do?isCheck=false",para);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    }	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		//console.info(vo);
		var parm ="dept_id="+vo[0] +"&payment_item_id="+vo[1] +"&duty_dept_id="+vo[2]
		
		 parent.$.ligerDialog.open({ url : 'hrp/budg/base/budgpayitem/expenditureItem/ExpenditureItemBelongUpdatePage.do?isCheck=false&'+parm,data:{}, 
			height: 300,width: 800, title:'修改支出项目归口',modal:true,showToggle:true,
			showMax:true,showMin: false,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    	});
		 
    }
    function loadDict(){
        //归口科室名称
		autocomplete("#duty_dept_name","queryDutyDept.do?isCheck=false","id","text",true,true,'',null);
        //支出项目：
		autocomplete("#payment_item_name","queryPaymentItem.do?isCheck=false","id","text",true,true,'',null);
        
        //budg_year = liger.get("budg_year").getValue();
        
        //autocomplete("#proj_id","../../../queryProjName.do?isCheck=false","id","text",true,true); 
        //字典下拉框
        //$("#duty_dept_name").ligerTextBox({width:160});
        //$("#payment_item_name").ligerTextBox({width:160});

    }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		//hotkeys('B', downTemplate);

		hotkeys('I', imp);
		//hotkeys('E', copyData);

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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">归口科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="duty_dept_name" type="text" id="duty_dept_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出项目：</td>
            <td align="left" class="l-table-edit-td"><input name="payment_item_name" type="text" id="payment_item_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
