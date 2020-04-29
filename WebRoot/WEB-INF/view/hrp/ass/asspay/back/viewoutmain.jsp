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
       query();
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({
			name : 'pay_no',
			value : '${pay_no}'
		});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '发票流水号', name: 'bill_no', align: 'left', frozen: true,width : 120,
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.note == "合计"){
								return '';
							}
							return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
							+ "|" + rowdata.copy_code + "|"
							+ rowdata.bill_no  +"')>"+rowdata.bill_no+"</a>";
						}
					 		},
					 { display: '摘要', name: 'note', align: 'left', frozen: true,width : 120
					 		},
					 { display: '供应商', name: 'ven_name', align: 'left', frozen: true,width : 220
					 		},
					 { display: '仓库', name: 'ven_name', align: 'left', frozen: true,width : 220
					 		},		
                     { display: '发票号', name: 'invoice_no', align: 'left', frozen: true,width : 120
					 		},
                     { display: '开票日期', name: 'bill_date', align: 'left',width : 120
					 		},
                     { display: '合同', name: 'pact_code', align: 'left',width : 120
					 		},
                     { display: '发票金额', name: 'bill_money', align: 'right',width : 120,
								render: function(item)
					            {
					                    return formatNumber(item.bill_money,'${ass_05005}',1);
					            }
					 		},
                     { display: '制单人', name: 'create_emp_name', align: 'left',width : 120
					 		},
                     { display: '制单时间', name: 'create_date', align: 'left',width : 120
					 		},
                     { display: '确认人', name: 'audit_emp_name', align: 'left',width : 120
					 		},
                     { display: '确认时间', name: 'audit_date', align: 'left',width : 120
					 		},
					 { display: '状态', name: 'state_name', align: 'left',width : 120
						 	}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssBillBillMain.do?isCheck=false',delayLoad :true,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,checkBoxDisplay : isCheckDisplay,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
									{
										text : '关闭',
										id : 'close',
										click : this_close,
										icon : 'candle'
									}
				    	
				    ]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.bill_no 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    function this_close() {
		frameElement.dialog.close();
	}
    
   
    
    function isCheckDisplay(rowdata) {
		if (rowdata.note == "合计")
			return false;
		return true;
	}
    
    function add_open(){
    	
    	parent.$.ligerDialog.open({
			title: '付款发票登记添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assbill/main/assBillMainAddPage.do?isCheck=false&',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
    	}
    	
    
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			" copy_code="+vo[2]   +"&"+ 
			"bill_no="+vo[3] 

		parent.$.ligerDialog.open({
			title: '退款款发票登记修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asspay/back/assBackBillMainUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
    function loadDict(){
		var param = {query_key:''};
    	
		autocomplete("#create_emp", "../../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true, param, true);
    	
		autocomplete("#audit_emp", "../../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true, param, true);
		
		autocomplete("#ven_id", "../../queryHosSupDict.do?isCheck=false","id", "text",true,true,param,true,null,"350");
		
		autocomplete("#pact_code", "../../queryContractMain.do?isCheck=false","id", "text",true,true,param,true,null,"200");
		$('#state').ligerComboBox({
			data:[{id:2,text:'确认'},{id:1,text:'审核'},{id:0,text:'新建'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		})  ;
		autodate("#bill_date_beg","YYYY-mm-dd","month_first");

		autodate("#bill_date_end","YYYY-mm-dd","month_last");
		
         }  
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<div id="toptoolbar" ></div>

	<div id="maingrid"></div>
	
</body>
</html>
