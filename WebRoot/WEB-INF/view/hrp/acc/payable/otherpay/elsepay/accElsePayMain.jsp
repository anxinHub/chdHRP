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
    	
   	  	grid.options.parms.push({name:'start_date',value:$("#start_date").val()}); 
   	  	
   	 	grid.options.parms.push({name:'end_date',value:$("#end_date").val()});
   	 	
   	  	grid.options.parms.push({name:'maker',value:liger.get("maker").getValue().split(".")[0]}); 
   	  	
   	 	grid.options.parms.push({name:'payee',value:liger.get("payee").getValue().split(".")[0]});
   	 	
   	 	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(".")[0]}); 
		
    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    
    //加载grid
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '付款凭证号', name: 'pay_code', align: 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href=javascript:openUpdate('"
							+ rowdata.group_id   + "|" + 
							rowdata.hos_id   + "|" + 
							rowdata.copy_code   + "|" + 
							rowdata.pay_code + "')>"
							+ rowdata.pay_code + "</a>";
					},width:120
			 	},
			 	{ display: '制单人', name: 'maker_name', align: 'left',width:120},
	 		 	{ display: '制单日期', name: 'make_date', align: 'left',width:120},	
                { display: '科室', name: 'dept_name', align: 'left',width:120},
                { display: '领款人', name: 'payee_name', align: 'left',width:120},
                { display: '付款金额', name: 'pay_money', align: 'right',width:100,
                	render: function(item){
						return formatNumber(item.pay_money,2,1);
					} 
			    	
				},
				{ display: '付款事由', name: 'remark', align: 'left'}
			],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccElsePay.do?isCheck=false',
            width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :false,
            selectRowButtonOnly:true,
            toolbar: { 
            	items: [
                	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                   	{ line:true },
			    	{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
			    	{ line:true },
			    	{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
					{ line:true }, 
					{ text : '模版设置',id : 'printSet',click :printSet ,icon : 'settings'},
					{ line : true},
					{ text : '模版打印（<u>P</u>）',id : 'print',click :print ,icon : 'print'},
					{ line : true}
			]}, 
    		onDblClickRow : 
    			function (rowdata, rowindex, value){//双击行事件
					openUpdate(
						rowdata.group_id   + "|" + 
						rowdata.hos_id   + "|" + 
						rowdata.copy_code   + "|" + 
						rowdata.pay_code
					);
    			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //添加
    function add_open(){
    	$.ligerDialog.open({
			title: '其他费用——付款凭证添加',
			height: 360,
			width: 700,
			url: 'accElsePayAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: false,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccElsePay(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close();}}]
		});
	}
    
    
    //删除
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
        
        var ParamVo =[];
        
		$(data).each(function() {
			ParamVo.push(this.group_id + "@" + this.hos_id + "@" + this.copy_code + "@" + this.pay_code);
		});
        
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deleteAccELsePay.do?isCheck=false",{ParamVo:ParamVo.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
	}
    
    
    //修改
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		
    	var parm ="group_id="+vo[0] +"&"+ 
					"hos_id="+vo[1] +"&"+ 
					"copy_code="+vo[2] +"&"+ 
    				"pay_code="+vo[3];
    	
    	$.ligerDialog.open({
			title: '其他费用——付款凭证修改', 
			height:360,
			width: 700,
			url: 'accElesePayUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: false,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccElsePay(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close();}}]
		});
    
    }
    
    //模版设置
    function printSet(){
    	var useId = '${sessionScope.user_id }'; //按用户打印
    	
    	officeFormTemplate({template_code:"01038",use_id:useId});
		/* parent.$.ligerDialog.open({
			url : 'hrp/acc/payable/otherpay/elsepay/printSetPage.do?template_code=01038&use_id='+ useId,
			data : {},
			height : $(parent).height(),
			width : $(parent).width(),
			title : '打印模板设置',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true
		}); */
    }
    
    
  //模版打印 
    function print(){
    	var useId = '${sessionScope.user_id }'; //按用户打印

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {

			var pay_code = "";
			$(data).each(function() {
				pay_code += "'"+this.pay_code + "',"

			});
 
			var para={
					template_code:'01038',
					class_name:"com.chd.hrp.acc.serviceImpl.payable.otherpay.AccElsePayServiceImpl",
					method_name:"queryAccElsePayPrint",
					isSetPrint:false,//是否套打，默认非套打
					isPreview:true,//是否预览，默认直接打印
					
					paraId : pay_code.substring(0, pay_code.length - 1),
					use_id : useId,
					p_num : 1
			}; 
			officeFormPrint(para);
			
		}
    }
    
    //加载字典
    function loadDict(){
    	
    	
    	autocomplete("#maker","../../../queryEmpDict.do?isCheck=false", "id","text", true, true,null,false,null,"120");
    	
    	autocomplete("#payee","../../../queryEmpDict.do?isCheck=false", "id","text", true, true,null,false,null,"120");
    	
		autocomplete("#dept_id", "../../../../sys/queryDeptDict.do?isCheck=false", "id","text", true, true,null,false);
    	
        
		autodate("#start_date", "yyyy-mm-dd", 'month_first');
		autodate("#end_date", "yyyy-mm-dd", 'month_last');
		
    	$("#start_date").ligerTextBox({width :90});
    	$("#end_date").ligerTextBox({width : 90});
    	$("#payee").ligerTextBox({width :120});
    	$("#maker").ligerTextBox({width :120});
    	
    	
    	
    } 
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('D', remove);
		hotkeys('P', print);
	 }
    
    
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">制单日期：</td>
            <td align="left" class="l-table-edit-td" ><input name="start_date" class="Wdate" type="text" id="start_date" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left">至</td>
            <td align="left" class="l-table-edit-td" ><input name="end_date" class="Wdate" type="text" id="end_date" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td" ><input name="maker" type="text" id="maker"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">领款人：</td>
            <td align="left" class="l-table-edit-td" ><input name="payee" type="text" id="payee"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
        </tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>
