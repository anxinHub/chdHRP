<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	var is_grant_show = ${is_grant_show};
	var is_audit_show = ${is_audit_show};
	var is_create_show = ${is_create_show};
	var is_update = ${is_update};
    var grid;
	
    
    //页面初始化
    $(function (){
    	
		$("#acct_yearm").ligerTextBox({ width:160 });autodate("#acct_yearm","yyyymm");
		
        loadDict();//加载下拉框
    	loadHead(null);	//加载数据
    	changeDate();//核算年月事件
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
		
	});
    
    
    //查询
    function  query(){//根据表字段进行添加查询条件
    	
		grid.options.parms=[];
	   	grid.options.newPage=1;
	       
	   	grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
	   	
	   	var dept_id = liger.get("dept_id").getValue();
		if (dept_id) {
			grid.options.parms.push({name : 'dept_id',value : dept_id.split(",")[0]});
			grid.options.parms.push({name : 'dept_no',value : dept_id.split(",")[1]});
		}
		
		grid.options.parms.push({name : 'item_code',value : liger.get("item_code").getValue()});
	   	
	   	grid.loadData(grid.where);//加载查询条件
    	
    }
    
    
    function  changeDate(){

    	ajaxJsonObjectByUrl("../hpmdeptbonusaudit/querydataAudita.do?isCheck=false", {acct_yearm:$("#acct_yearm").val()},function(responseData) {

    		if (responseData.is_audit==0) {
				$("#is_audit").text("未审核");
			}else if (responseData.is_audit==1){
				$("#is_audit").text("已审核");
			}else{
				$("#is_audit").text("  ");
			};
	
			if (responseData.is_grant==0){
				$("#is_grant").text("未发放");
			}else if (responseData.is_grant==1){
				$("#is_grant").text("已发放");
			}else{
				$("#is_grant").text("  ");
			}
		});
    }
    
    
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '核算年月', name: 'acct_yearm', align: 'left',render: 
					function (rowdata, rowindex, value){
						return rowdata.acct_year + rowdata.acct_month;
					}
				},
				
				{ display: '科室编码', name: 'dept_code', align: 'left'},
				
				{ display: '科室名称', name: 'dept_name', align: 'left'},
				
				{ display: '项目编码', name: 'item_code', align: 'left'},
				
				{ display: '项目名称', name: 'item_name', align: 'left'},
				
				{ display: '核算奖金', name: 'bonus_money', align: 'right',render:
					function(rowdata, rowindex, value){
						var col = arguments[arguments.length - 1];if (rowdata.is_audit == 1) {rowdata.notEidtColNames.push(col.columnname);}
						return formatNumber(rowdata.bonus_money ==null ? 0 : rowdata.bonus_money,2,1);
					}
				},
				
				{ display: '预留比例', name: 'activity_percent', align: 'right', editor: { type: 'float', onChange: function (editValue){editValue.value = editValue.value/100;} },
				 	render: function (rowdata, rowindex, value){
				 			var col = arguments[arguments.length - 1];if (rowdata.is_audit == 1 || is_update == false) {rowdata.notEidtColNames.push(col.columnname);}
				 			var activity_percent = formatNumber(rowdata.activity_percent ==null ? 0 : rowdata.activity_percent,2,1);
				 			return activity_percent*100+"%";
				 		}
				},
				{ display: '预留奖金', name: 'activity_money', align: 'right', editor: { type: 'float' },
					render:function(rowdata, rowindex, value){
						var col = arguments[arguments.length - 1];if (rowdata.is_audit == 1 || is_update == false) {rowdata.notEidtColNames.push(col.columnname);}
						return formatNumber(rowdata.activity_money ==null ? 0 : rowdata.activity_money,2,1);
					}
				},
				{ display: '职工奖金', name: 'grant_money', align: 'right',editor: { type: 'float' },
					render:function(rowdata, rowindex, value){
						var col = arguments[arguments.length - 1];if (rowdata.is_audit == 1 || is_update == false) {rowdata.notEidtColNames.push(col.columnname);}
						return formatNumber(rowdata.grant_money ==null ? 0 : rowdata.grant_money,2,1);
					},
                    totalSummary:
                    {
                    	render: function (suminf, column, cell)
                        {
                            return '<div>合计:' + formatNumber(suminf.sum,2,1) + '</div>';
                        },
                        align: 'left'
                    }
				},
				{ display: '是否审核', name: 'is_audit', align: 'left',render:
					function(rowdata, rowindex, value){
						if(rowdata.is_audit == 1){
							return "是";
						}else{
							return "否";
						}
					}
				}
			],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmDeptBonusGrantItem.do',
            width: '100%',height: '100%',checkbox: false,rownumbers:true,enabledEdit: true,delayLoad:true,
            selectRowButtonOnly:true,onBeforeSubmitEdit:f_onBeforeEdit,onAfterEdit:f_onAfterEdit,isAddRow:false, 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'create', click: create, icon:'bookpen',hide:is_create_show });
       	obj.push({ line:true,hide:is_create_show });
       	
       	obj.push({ text: '审核（<u>C</u>）', id:'audit', click: audit,icon:'ok',hide:is_audit_show});
       	obj.push({ line:true,hide:is_audit_show});
       	
       	obj.push({ text: '消审（<u>U</u>）', id:'reAudit', click: reAudit,icon:'delete',hide:is_audit_show });
       	obj.push({ line:true,hide:is_audit_show });
       	
       	obj.push({ text: '发放（<u>F</u>）', id:'grant', click: grant,icon:'ok',hide:is_grant_show });
       	obj.push({ line:true,hide:is_grant_show });
       	
       	obj.push({ text: '取消发放（<u>D</u>）', id:'reGrant', click: reGrant,icon:'delete',hide:is_grant_show });
       	obj.push({ line:true,hide:is_grant_show });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('G',create);
		
		hotkeys('C',audit);
		
		hotkeys('U',reAudit);
		
		hotkeys('F',grant);
		
		hotkeys('D',reGrant);
		
		hotkeys('P',print);
	}
    
	
	//打印
    function print() {

		if (grid.getData().length == 0) {
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
				title: "绩效工资发放",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiDeptBonusGrantItemService",
				method_name: "queryDeptBonusGrantPrint",
				bean_name: "aphiDeptBonusGrantService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
    
    //预留比例
    var activity_percent1;
    //预留奖金
    var activity_money1;
    //职工奖金
    var grant_money1;
    function f_onBeforeEdit(){
    	 var data = gridManager.getCheckedRows();
    	 $(data).each(function (){
    		 activity_percent1 = this.activity_percent;
    		 activity_money1 = this.activity_money;
    		 grant_money1 = this.grant_money;
		  });
    }
    
	/*
	    预留比例 = (核算奖金 - 职工奖金)/核算奖金
	    职工奖金 = 核算奖金 - 预留奖金
	    预留奖金 =  核算奖金 - 职工奖金 
	 */
	 function f_onAfterEdit(e){
		 var data = gridManager.getCheckedRows();
		 var ParamVo =[];
		 var res= 0;
		  $(data).each(function (){
			  //预留比例不等于编辑前
			  if(activity_percent1 != this.activity_percent && res == 0){
				  this.activity_money =this.activity_percent * this.bonus_money;//预留奖金 = 预留比例 * 核算奖金
				  this.grant_money = this.bonus_money - this.activity_money;//职工奖金 = 核算奖金 - 预留奖金
				  res =1;
			 //预留奖金不等于编辑前
			  }else if(activity_money1 != this.activity_money && res == 0){
				  this.grant_money = this.bonus_money - this.activity_money;//职工奖金 = 核算奖金 - 预留奖金
				  this.activity_percent = this.activity_money / this.bonus_money;//预留比例 = 预留奖金 / 核算奖金
				  res =1;
			 //职工奖金不等于编辑前
			  }else if(grant_money1 != this.grant_money && res == 0){
				  this.activity_percent = (this.bonus_money - this.grant_money) / this.bonus_money;//预留比例 = (核算奖金 - 职工奖金)/核算奖金
				  this.activity_money =this.activity_percent * this.bonus_money;//预留奖金 = 预留比例 * 核算奖金
				  res =1;
			  }
	      	ParamVo.push(this.acct_year+"#"+this.acct_month+"#"+this.dept_id+"#"+this.activity_money+"#"+this.activity_percent+"#"+this.grant_money+"#"+this.bonus_money);
		  });
		  if(e.value == e.oldvalue){
				return false;
			}

		  //$.ligerDialog.confirm('确定修改？',"",function(yes) {
				//if (yes){
	          	ajaxJsonObjectByUrl("updateHpmDeptBonusGrantItem.do",{ParamVo : ParamVo.toString()},function (responseData){
	          		if(responseData.state=="true"){
	          			query();
	        		}
	          	});
			//}
		  //},{closeWhenEnter:false});
	}
	
	
	//审核
	function audit() {

		var acct_yearm = $("#acct_yearm").val();
		if(!acct_yearm){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var dept_id = liger.get("dept_id").getValue();
		var param = {
			acct_yearm : acct_yearm,
			isAudit:'1',
			dept_id:dept_id ? dept_id : ''
		};
		
		ajaxJsonObjectByUrl("auditHpmDeptBonusGrantItem.do", param,function(responseData){
    		if(responseData.state=="true"){
    			query();
    			changeDate();
    		}
    	});
	}
	
	
	//反审
    function reAudit() {

		var acct_yearm = $("#acct_yearm").val();
		if(!acct_yearm){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var dept_id = liger.get("dept_id").getValue();

		var param = {
			acct_yearm : acct_yearm,
			isAudit:'0',
			dept_id:dept_id ? dept_id : ''
		};
		
		ajaxJsonObjectByUrl("reAuditHpmDeptBonusGrantItem.do", param,function(responseData){
    		if(responseData.state=="true"){
    			query();
    			changeDate();
    		}
    	});

	}
	
	
	//发放
    function grant() {

		var acct_yearm = $("#acct_yearm").val();
		if(!acct_yearm){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var param = {
			acct_yearm : acct_yearm,
			grant_state:'1'
		};
		
		ajaxJsonObjectByUrl("grantHpmDeptBonusGrantItem.do", param,function(responseData){
    		if(responseData.state=="true"){
    			query();
    			changeDate();
    		}
    	});

	}
   	
	
	//取消发放
    function reGrant() {

		var acct_yearm = $("#acct_yearm").val();
		if(!acct_yearm){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var param = {
			acct_yearm : acct_yearm,
			grant_state:'0'
		};
		
		ajaxJsonObjectByUrl("grantHpmDeptBonusGrantItem.do", param,function(responseData){
    		if(responseData.state=="true"){
    			query();
    			changeDate();
    		}
    	});
	}
    
	
	//生成
    function create() {

		var acct_yearm = $("#acct_yearm").val();
		
		if(!acct_yearm){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var param = {
			acct_yearm : acct_yearm
		};
		
		
		ajaxJsonObjectByUrl("createHpmDeptBonusGrantItem.do", param, function(responseData) {
			if (responseData.state == "true") {
				query();
			}
		});

	}
	
  	//字典下拉框
    function loadDict(){
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?&isCheck=false","id","text",true,true);
    	autocomplete("#item_code","../queryItemAllDict.do?&isCheck=false","id","text",true,true);
    	
	}   
  	
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text" id="acct_yearm" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM',onpicked:changeDate()})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td></td>
            <td></td>
            <td align="right" class="l-table-edit-td">
                	当前科室绩效核算审核状态:
            </td>
            <td align="left" class="l-table-edit-td">
                <span id="is_audit" style="color: red"></span>
            </td>
            <td align="right" class="l-table-edit-td">
                	发放状态：
            </td>
            <td align="left" class="l-table-edit-td">
                <span id="is_grant" style="color: red"></span>
            </td>
        </tr>
    </table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
	
</body>
</html>
