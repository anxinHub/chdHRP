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
    
    
    //页面初始化
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();//加载快捷键
    	loadHead(null);	//加载数据
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
	
    
    //加载grid
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '申请单号', name: 'apply_code', align: 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href=javascript:openUpdate('"
							+rowdata.apply_code + "')>"
							+ rowdata.apply_code + "</a>";
					},width:120,
					totalSummary:{render:
						function (suminf, column, cell){
							return '<div>合计</div>';
						},align: 'center'
		            }
				},
				{ display: '凭证号', name: 'vouch_no', align: 'left',width:100,render: 
			 		function (rowdata){
						if(rowdata.vouch_no){
							if(rowdata.vouch_no=='-'){
								return rowdata.vouch_no;
							}else{
								return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
							}
						}else{
							return '';
						}
					}
				},
				{ display: '申请日期', name: 'apply_date', align: 'left',width:120},	
				
				/* { display: '收款单位/个人', name: 'unit_name', align: 'left',width:120}, */
				
				{ display: '科室', name: 'dept_name', align: 'left',width:180},
				
				{ display: '项目', name: 'proj_name', align: 'left',width:220},
				
				{ display: '申请人', name: 'emp_name', align: 'left',width:120},
				
				{ display: '付款金额', name: 'payment_amount', align: 'left', align: 'right',render: 
					function(item){
						return formatNumber(item.payment_amount,2,1);
					},totalSummary:{render: 
						function (suminf, column, cell){
			            	return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
			        	}
					} ,width:80
				},
				
				{ display: '制单人', name: 'maker_name', align: 'left',width:120},
				
				{ display: '支付人', name: 'confirmer_name', align: 'left',width:120},
			],
    		dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgChargeApplyPayForAcc.do',
    		width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
    		selectRowButtonOnly:true,//heightDiff: -10,
            toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
				{ line:true },
				{ text : '生成凭证（<u>S</u>）',id : 'pay',click : pay,icon : 'ok'},
				{ line : true},
				{ text : '取消确认（<u>R</u>）',id : 'pay',click : unPay,icon : 'back'},
				{ line : true},
				{ text : '打印（<u>P</u>）',id : 'print',click :print ,icon : 'print'},
				{ line : true}
		    ]},
			
		    onDblClickRow : function (rowdata, rowindex, value){//双击行事件
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
    
    function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
    
    //打印
	function print(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.warn("请先查询数据！");
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
       			title: "费用支付",//标题
       			columns: JSON.stringify(grid.getPrintColumns()),//表头
       			class_name: "com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyService",
    			method_name: "queryBudgChargeApplyPrint",
    			bean_name: "budgChargeApplyService"/* ,
    			heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
    			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
       		};
    	
    	$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
    	
    	officeGridPrint(printPara);
   		
    }
    
    
    
    //修改
    function openUpdate(obj){
    	
    	var parm ="group_id=101&"+ "hos_id=105&"+ " copy_code='001'&"+ "apply_code="+obj;
    	
    	parent.$.ligerDialog.open({
			title: '费用支付',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/acc/payable/otherpay/payforacc/budgChargeApplyUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    
    }
    
    
    //加载字典
    function loadDict(){
    	
    	autocomplete("#dept_id", "../../../../sys/queryDeptDict.do?isCheck=false", "id","text", true, true,null,false);   
    	autocomplete("#proj_id", "../../../../sys/queryProjDictDict.do?isCheck=false", "id","text", true, true,null,false); 
    	autocomplete("#emp_id","../../../queryEmpDict.do?isCheck=false", "id","text", true, true,null,false	,null,"184");
        
    	$("#apply_date_b").ligerTextBox({width : 81});
    	$("#apply_date_e").ligerTextBox({width : 81});
    	
    	autodate("#apply_date_b", "yyyy-mm-dd", "month_first");
    	autodate("#apply_date_e", "yyyy-mm-dd", "month_last");
    	
    	//状态
    	$("#state").ligerComboBox({
	      	url: '../../../queryBudgSysDict.do?isCheck=false&f_code=PAYMENT_STATE',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 160,
	      	onSuccess: function (data) {
				this.setValue("04");
      		}
		});
    	
    	$.post("../../../queryPayType.do?isCheck=false",'',function(data){payData = data},"json");
    } 
    
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query); 
		hotkeys('S', pay);
		hotkeys('R', unPay);
	}
    
	
	
    //确认
	function pay(){
		 var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ;
		} 
		var busi_no="";//生成凭证使用
		
		var ParamVo = [];
		var msg = "";
		var falg = true;
		
		
		$(data).each(function() {
			//if($("#pay_way"+ this.apply_code+"").val() == "" || $("#pay_way"+ this.apply_code+"").val() == null || $("#pay_way"+ this.apply_code+"").val() == 0){
			//	msg = msg +"单据号为["+ this.apply_code + "]的支付方式还没有选择,请选择支付方式!<br/>";
			//	falg = false;
			//}
			
			//ParamVo.push(
			//	this.group_id + "@" + 
			//	this.hos_id + "@" + 
			//	this.copy_code + "@" + 
			//	this.apply_code + "@" + 
			//	this.state+"@" + 
			//	$("#pay_way"+ this.apply_code+"").val()
			//);
			
			busi_no=busi_no+this.apply_code+","
		});
		
		busi_no=busi_no.substring(0,busi_no.length-1);
		
		//if(falg == false){
		//	$.ligerDialog.warn(msg);
		//	return ;
		//}
		
		//$.ligerDialog.confirm('确定支付?', function (yes){
		//	if(yes){
		//		ajaxJsonObjectByUrl("confirmBudgChargeApplyForAcc.do", {ParamVo : ParamVo.toString()}, function(responseData) {
		//			if (responseData.state == "true") {
						
						var busi_type_code = '0205';
						var busi_log_table='ACC_BUSI_LOG_0205';
						loadHead(null);
						var para={
 	        				busi_date_b:'',
 	        				busi_date_e:'',
 	        				template_code:'01',
 	        				vouch_date:'${vouch_date}',
 	        				init_type:1,
 	        				mod_code:'02',
 	        				busi_type_code:busi_type_code,
 	        				busi_no:busi_no,
 	        				busi_log_table:busi_log_table,
 	        				huizong_sql:''
 	        			};
 	        					//console.log(para);
 	        			var loadIndex = layer.load(1);
 	        		    ajaxJsonObjectBylayer("/CHD-HRP/hrp/acc/autovouch/budgautovouch/queryVouchJsonByBusi.do",para,function(responseData){	
 	        		    			//console.log(responseData);
 	        		    	layer.close(loadIndex);
 	        		    	var para={
 	       	    				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
 	       	    				title:'会计凭证',
 	       	    				width:0,
 	       	    				height:0,
 	       	    				isShowMin:true,
 	       	    				isModal:true,
 	       	    				/* data:{vouch:responseData.vouch,busi_log_table:liger.get("acc_busi_type").getValue().split("@")[1],busi_type_code:liger.get("acc_busi_type").getValue().split("@")[0],
 	       	    					busi_no:responseData.busi_no,template_code:liger.get("template_code").getValue()} */
 	       	    				data:{auto_id:responseData.auto_id,
 	       	    					busi_log_table:busi_log_table,
 	       	    					busi_type_code:busi_type_code,
 	       	    					busi_no:responseData.busi_no,
 	       	    					template_code:'01'}
 	       	    			};

 	        		    	parent.parent.openDialog(para);
 	        		      },layer,loadIndex);
 	        		    
					// }
				//});
			//}
		//});
	}
	
	//确认
	function unPay(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ;
		} 
		
		var ParamVo = [];
		var msg = "";
		var falg = true;
		

		$(data).each(function() {
			if(this.state != '04'){
				msg = msg +"单据号为["+ this.apply_code + "] 为非确认状态 无法取消确认!<br/>";
				falg = false;
			}
			
			ParamVo.push(
				this.group_id + "@" + 
				this.hos_id + "@" + 
				this.copy_code + "@" + 
				this.apply_code + "@" + 
				this.state+"@" + 
				$("#pay_way"+ this.apply_code+"").val()+"@"+this.vouch_id+ "@" +"ACC_BUSI_LOG_0205"
			);

		});

		if(falg == false){
			$.ligerDialog.warn(msg);
			return ;
		}
		

		$.ligerDialog.confirm('确定取消支付?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("unConfirmBudgChargeApplyForAcc.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						
						loadHead(null);
						
					}
				});
			}
		});
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
