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
    var payData;
    $(function ()
    {
        loadDict();//加载下拉框
        
        loadHotkeys();
        
    	//加载数据
    	loadHead(null);	
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    	  
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(".")[0]}); 
    	  grid.options.parms.push({name:'proj_id',value:liger.get("proj_id").getValue().split(".")[0]}); 
    	  grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue().split(".")[0]}); 
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
    	  grid.options.parms.push({name:'apply_date_b',value:$("#apply_date_b").val()}); 
    	  grid.options.parms.push({name:'apply_date_e',value:$("#apply_date_e").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	        	     { display: '借款单号', name: 'apply_code', align: 'left',
							render : function(rowdata, rowindex, value) {
								return "<a href=javascript:openUpdate('"
										+ rowdata.group_id   + "|" + 
										rowdata.hos_id   + "|" + 
										rowdata.copy_code   + "|" + 
										rowdata.apply_code + "')>"
										+ rowdata.apply_code + "</a>";
							},width:120,
		                    totalSummary:
		                    {
		                        render: function (suminf, column, cell)
		                        {
		                            return '<div>合计</div>';
		                        },
		                        align: 'center'
		                    }
			 		 		},
			 		 { display: '申请日期', name: 'apply_date', align: 'left'
					 		},		
                     { display: '科室', name: 'dept_name', align: 'left'
					 		},
                     { display: '项目', name: 'proj_name', align: 'left'
					 		},
                     { display: '借款人', name: 'emp_name', align: 'left'
					 		},
                     { display: '借款金额', name: 'borrow_amount', align: 'right', 
								render: function(item)
					            {
					                    return formatNumber(item.borrow_amount,2,1);
					            } ,
			                    totalSummary:
			                    {
			                        render: function (suminf, column, cell)
			                        {
			                            return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
			                        }
			                    } 
					 		},
					 { display: '制单人', name: 'maker_name', align: 'left'
						 	},
	                 { display: '制单日期', name: 'make_date', align: 'left'
						 	},		
                     { display: '审核人', name: 'checker_name', align: 'left'
					 		},
                     { display: '审核日期', name: 'check_date', align: 'left'
					 		},
					 { display: '支付人', name: 'payer_name', align: 'left'
					 		},
                     { display: '支付日期', name: 'pay_date', align: 'left'
					 		},
					 { display: '支付方式', name: 'pay_way', align: 'center',
								render : function(rowdata, rowindex, value) {
									var str = "";
									if(rowdata.state == "04"){
										str = str + "<select disabled='disabled' id='pay_way"+rowdata.apply_code+"' style='margin-top:5px;' name='pay_way"+rowdata.apply_code+"'>";
										
									}else{
										str = str + "<select id='pay_way"+rowdata.apply_code+"' style='margin-top:5px;' name='pay_way"+rowdata.apply_code+"'>";
									}
									str = str + "<option value='0'></option>";
									$.each(payData,function(i,v){
										if(rowdata.pay_way == v.id){
											str = str + "<option value='"+v.id+"' selected='selected'>"+v.text+"</option>";
										}else{
											str = str + "<option value='"+v.id+"'>"+v.text+"</option>";
										}
									})
									str = str+ "</select>";
									return str;
								}
						 	},		
                     { display: '状态', name: 'state_name', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgBorrPay.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
						{ text : '确认（<u>S</u>）',id : 'pay',click : pay,icon : 'ok'},
						{ line : true},
						{ text : '打印',id : 'print',click :print ,icon : 'print'}
						                
				    ]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{//双击行事件
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.apply_code
							);
						
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
	function print(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	/* var heads={
      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
      		  "rows": [
  	          {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
  	          {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
      		  ]
      	}; */
           	
    	var printPara={
       			title: "借款支付",//标题
       			columns: JSON.stringify(grid.getPrintColumns()),//表头
       			class_name: "com.chd.hrp.acc.service.payable.loanmt.BudgBorrApplyService",
      			method_name: "queryBudgBorrApplyPrint",
      			bean_name: "budgBorrApplyService"/* ,
    			heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
    			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
       		};
    	
    	$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
    	
    	officeGridPrint(printPara);
   		
    }
    	
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		
    	var parm ="group_id="+vo[0] +"&"+ 
		"hos_id="+vo[1] +"&"+ 
		" copy_code="+vo[2] +"&"+ 
    		"apply_code="+vo[3];
    	parent.$.ligerDialog.open({
			title: '借款支付',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/acc/payable/loanmt/pay/budgBorrApplyUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    
    }
    function loadDict(){
    	
		
    	
    	autocomplete("#dept_id", "../../../../sys/queryDeptDict.do?isCheck=false", "id","text", true, true,null,false);   
    	 
    	
    	autocomplete("#proj_id", "../../../../sys/queryProjDictDict.do?isCheck=false", "id","text", true, true,null,false); 
    	
    	
    	autocomplete("#emp_id","../../../queryEmpDict.do?isCheck=false", "id","text", true, true,null,false,null,"184");
        
    	$("#apply_date_b").ligerTextBox({width : 81});
    	
    	$("#apply_date_e").ligerTextBox({width : 81});
    	
    	autodate("#apply_date_b", "yyyy-mm-dd", "month_first");
    	autodate("#apply_date_e", "yyyy-mm-dd", "month_last");
    	
    	//状态
    	
    	autocomplete("#state","../../../queryBudgSysDict.do?isCheck=false&f_code=BORROW_STATE", "id","text", true, true,null,false,'03',"160");
    	
    	$.post("../../../queryPayType.do?isCheck=false",'',function(data){payData = data},"json");
    } 
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query); 

		hotkeys('S', pay);

	 }
    
   
	  function pay(){
			var ParamVo = [];
			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				var msg = "";
				var falg = true;
				$(data).each(
						function() {
							if($("#pay_way"+ this.apply_code+"").val() == "" || $("#pay_way"+ this.apply_code+"").val() == null || $("#pay_way"+ this.apply_code+"").val() == 0){
								msg = msg +"单据号为["+ this.apply_code + "]的支付方式还没有选择,请选择支付方式!<br/>";
								falg = false;
							}
								ParamVo.push(this.group_id + "@" + this.hos_id + "@"
										+ this.copy_code + "@" + this.apply_code + "@" + this.state+"@"+$("#pay_way"+ this.apply_code+"").val());
							
						});
				if(falg){
					$.ligerDialog.confirm('确定支付?', function (yes){
	                	if(yes){
							ajaxJsonObjectByUrl("confirmPay.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
	                	}
					});
				}else{
					$.ligerDialog.error(msg);
				}
			}
		}
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">申请日期：</td>
            <td align="left" class="l-table-edit-td" ><input name="apply_date_b" class="Wdate" type="text" id="apply_date_b" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left">至</td>
            <td align="left" class="l-table-edit-td" ><input name="apply_date_e" class="Wdate" type="text" id="apply_date_e" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请人：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
            	<input name="emp_id" type="text" id="emp_id"  />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="state" type="text" id="state"  />
            </td>
            <td align="left"></td>
        </tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>
