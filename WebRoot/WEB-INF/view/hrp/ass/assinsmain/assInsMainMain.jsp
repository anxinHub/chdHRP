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
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
        
		 loadHotkeys();
		 $("#ven_id").ligerTextBox({width:160});
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件 
          grid.options.parms.push({name:'ins_date1',value:$("#ins_date_begin").val()}); 
        
    	  grid.options.parms.push({name:'ins_date2',value:$("#ins_date_end").val()});
    	   
    	  grid.options.parms.push({name:'ins_no',value:$("#ins_no").val()}); 
    	  
    	  grid.options.parms.push({name:'ass_month',value:$("#ass_month").val()}); 
    	  
    	  grid.options.parms.push({name:'ass_month1',value:$("#ass_month1").val()}); 
    	  
    	  grid.options.parms.push({name:'pact_code',value:liger.get("pact_code").getValue().split("@")[0]}); 
    	  
    	  grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]}); 
    	  
    	  grid.options.parms.push({name:'audit_date',value:$("#audit_date").val()}); 
    	  
    	  grid.options.parms.push({name:'audit_date1',value:$("#audit_date1").val()}); 
    
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]}); 
    	  
    	  grid.options.parms.push({name:'audit_emp',value:liger.get("audit_emp").getValue()}); 
    	    
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where); 
     }
    

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    
                     { display: '安装单号', name: 'ins_no', align: 'left',width:100,frozen: true,
					 			render : function(rowdata, rowindex,
										value) {
						 				if(rowdata.accept_desc == "合计"){
											return '';
										}
										return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|" + rowdata.ins_id  + "|" + rowdata.ins_no +"')>"+rowdata.ins_no+"</a>";
								   }
					 		},
					 { display: '摘要', name: 'accept_desc', align: 'left',width:150,frozen: true
					 		},		
                     { display: '购置年度', name: 'ass_year', align: 'left',width:80,frozen: true
					 		},
                     { display: '购置月份', name: 'ass_month', align: 'left',width:80,frozen: true
					 		},
                     { display: '安装日期', name: 'ins_date', align: 'left',width:130
					 		},
                     { display: '合同名称', name: 'contract_name', align: 'left',width:190
					 		},
                     { display: '供应商', name: 'sup_name', align: 'left',width:190
					 		},
					 { display: '安装费用', name: 'ins_money', align: 'right',width:120,
								render : function(item) {
									return formatNumber(item.ins_money,
											'${ass_05005}', 1);
								}
					 		},
                     { display: '安装科室', name: 'dept_name', align: 'left',width:100
					 		},
                     { display: '制单人', name: 'create_emp_name', align: 'left',width:100
					 		},
                     { display: '制单日期', name: 'create_date', align: 'left',width:130
					 		},
                     { display: '审核人', name: 'audit_emp_name', align: 'left',width:100
					 		},
                     { display: '审核日期', name: 'audit_date', align: 'left',width:130
					 		},
                     { display: '状态', name: 'state_name', align: 'left',width:100/* ,
								render : function(rowdata, rowindex,
										value) {
										if(rowdata.state==0){
											return "新建";
										}
										else{
											return "审核";
										}
								   } */
					 		}
                     ],
                     dataAction: 'server',
                     dataType: 'server',
                     usePager:true,
                     url:'queryAssInsMain.do',
                     width: '100%', 
                     height: '100%', 
                     checkbox: true,
                     rownumbers:true,
                     delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     checkBoxDisplay : isCheckDisplay,
                     toolbar: { items : [ {
							text : '查询（<u>Q</u>）',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						},
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
											{ line:true }, 
						                {
											text : '审核（<u>S</u>）',
											id : 'toExamine',
											click : toExamine,
											icon : 'ok'
										},{
											line : true
										}, {
											text : '销审（<u>X</u>）',
											id : 'notToExamine',
											click :notToExamine ,
											icon : 'right'
										},{
											line : true
										/* }, {
											text : '按合同生成单据（<u>h</u>）',
											id : 'initContract',
											click :initContract ,
											icon : 'refresh'
										},{
											line : true */
										},{
											text :'模板设置',
											id   :'printSet',
											click:printSet,
											icon : 'print'
										},{ line:true },
										{ text: '批量打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.ins_id +"|"+
								rowdata.ins_no
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function isCheckDisplay(rowdata) {
		if (rowdata.accept_desc == "合计")
			return false;
		return true;
	}
    
    function add_open(){
				parent.$.ligerDialog.open({
					title: '资产安装添加',
					height: $(window).height(),
					width: $(window).width(),
					url: 'hrp/ass/assinsmain/assInsMainAddPage.do?isCheck=false&',
					modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				});		
    	}
    function initContract(){
		
		parent.$.ligerDialog.open({
			title: '按合同生成',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assinsmain/assInsMainInitPage.do?isCheck=false&',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
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
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.ins_id  
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssInsMain.do",
                            			{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function toExamine(){
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
							ParamVo.push(this.group_id + "@" + this.hos_id + "@"
									+ this.copy_code + "@" + this.ins_id  );
						
					});
			$.ligerDialog.confirm('确定审核?', function(yes) {

				if (yes) {
					ajaxJsonObjectByUrl("updateToExamine.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    function notToExamine(){
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
							ParamVo.push(this.group_id + "@" + this.hos_id + "@"
									+ this.copy_code + "@" + this.ins_id );
						
					});
			$.ligerDialog.confirm('确定销审?', function(yes) {

				if (yes) {
					ajaxJsonObjectByUrl("updateNotToExamine.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "&group_id="+ 
			 vo[0] +"&hos_id="+ 
			 vo[1] +"& copy_code="+ 
			 vo[2] +"&ins_id="+
			 vo[3] +"&ins_no="+
			 vo[4];
		parent.$.ligerDialog.open({
			title: '资产安装修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assinsmain/assInsMainUpdatePage.do?isCheck=false&'+ parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});	
    }
    function loadDict(){
    	
		var param = {
            	query_key:''
        }; 
		
            //字典下拉框
    	autocomplete("#audit_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true,param,true);
		
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true,param,true);
		
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false","id", "text",true,true,param,true,null,"400");
		
		autocomplete("#pact_code", "../queryContractMain.do?isCheck=false", "id", "text", true, true,param,true); 

		autodate("#ass_month","YYYYmm");

		autodate("#ass_month1","YYYYmm");
		
		 
        $("#ins_no").ligerTextBox({width:160});
        
        $("#ass_month").ligerTextBox({width:120});
        
        $("#ass_month1").ligerTextBox({width:120});
        
        $("#ins_date_begin").ligerTextBox({width:120});
        
        $("#ins_date_end").ligerTextBox({width:120});
          
        $("#create_date").ligerTextBox({width:120}); 
        
        $("#audit_date").ligerTextBox({width:120});
        
        $("#audit_date1").ligerTextBox({width:120});
        
       /*  $("#state").ligerComboBox({width:160}); */
        $('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
        
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('S', toExamine);
		hotkeys('X', notToExamine);
		hotkeys('h', initContract);
		hotkeys('P', printDate);
	 }
	  //打印回调方法
	    function lodopPrint(){
	    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
	 		head=head+"<tr><td>制单日期："+$("#create_date_start").val() +" 至  "+ $("#create_date_end").val()+"</td></tr>";
	 		head=head+"</table>";
	 		grid.options.lodop.head=head; 
	 		grid.options.lodop.fn=renderFunc;
	 		grid.options.lodop.title="资产安装";
	    }
    
	//模板设置
	    function printSet(){
			  
			  var useId=0;//统一打印
				if('${ass_05012}'==1){
					//按用户打印
					useId='${user_id }';
				}
				
		//POBrowser.openWindow('../../../../PageOffice/printFormSet.do?isCheck=false&template_code=01001&userid=${user_id}', 'fullscreen=yes');
		officeFormTemplate({template_code:"05012",use_id:useId});

	    }
    
 	 //打印
	function printDate() {
		 var useId=0;//统一打印
	 		if('${ass_05012}'==1){
	 			//按用户打印
	 			useId='${user_id }';
	 		}
	 		var data = gridManager.getCheckedRows();
			if (data.length == 0){
				$.ligerDialog.error('请选择行');
			}else{
				
				var ins_id ="" ;
				$(data).each(function (){		
					
					ins_id  += "'"+this.ins_id+"',"
						
				});
	    	var para={
	    		
	       
	    			template_code:'05012',
	    			class_name:"com.chd.hrp.ass.serviceImpl.ins.AssInsMainServiceImpl",
	    			method_name:"queryAssInsMainDY",
					
	    			paraId :ins_id.substring(0,ins_id.length-1),
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    	};
	    	ajaxJsonObjectByUrl("queryInsMainState.do?isCheck=false",{paraId :ins_id.substring(0,ins_id.length-1),state:1},function(responseData){
				if (responseData.state == "true") {
					officeFormPrint(para);
				}
			});
 	 }
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%;" border="0">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">购置年月：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="ass_month" type="text" id="ass_month" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" /></td>
            <td width="2%">&nbsp;至：</td>
            <td><input name="ass_month1" type="text" id="ass_month1" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">安装单号：</td>
            <td class="l-table-edit-td"><input name="ins_no" type="text" id="ins_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
            <td align="left" class="l-table-edit-td">
            <!-- <select id="state" name="state">
						<option value="">全部</option>
                		<option value="0">新建</option>
                		<option value="1">审核</option>
                	</select> -->
                <input  name="state" type="text" id="state"/>
           	</td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">安装日期：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="ins_date_begin" type="text" id="ins_date_begin" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'ins_date_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td>&nbsp;至：</td>
			<td><input name="ins_date_end" type="text"
				id="ins_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'ins_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同名称：</td>
            <td align="left" class="l-table-edit-td"><input name="pact_code" type="text" id="pact_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核日期：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_date" type="text" id="audit_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td>&nbsp;至：</td>
            <td><input name="audit_date1" type="text" id="audit_date1" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
       
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_emp" type="text" id="audit_emp" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
         </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
