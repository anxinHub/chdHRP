/**
 * 凭证现金流量标注、辅助核算
 */ 
//var oldValue ='流入';
var page="vouchCheck";
var is_cash_show=0;
function loadDict() {  
	 
	//var subj_dire = $("#subj_dire_text").val();
	$("#subj_dire").ligerComboBox({
		width:60,
		cancelable: false,
        data: [
            { text: '借', id: '0' },
            { text: '贷', id: '1' }
        ],  
        value: 0  
       /* onChangeValue:function(value){  
        	if(oldValue == value){
        		return;
        	}
        	loadCheckDict();
        	if(!grid) return;
        	$(grid.getRows()).each(function(){
        		$(this).find('td').each(function(){
        			if('cash_name' !== grid.getCellColumn($(this))) return;
        			var oldValue = grid.getCellData($(this));
        			$(">div", $(this)).empty();
        			grid.events.trigger("cell:clear", oldValue, $(this));
        		})
        	});
        	oldValue = value;
        }*/
	}); 

	if(subjAttr.is_cash=="1" && parent.paraList["004"]=="1"){
		is_cash_show=1;
		$("#subj_dire").ligerComboBox({disabled:true});
	 }else if(subjAttr.subj_type_code=="04"||subjAttr.subj_type_code=="05" || subjAttr.subj_type_code=="06"||subjAttr.subj_type_code=="07"){
		 
		 $("#subj_dire").ligerComboBox({disabled:true});
	 }
	//$("#subj_dire").focus();
}


var grid;
var initRow=0;
var initMoney=0;
var columns=[];
//初始化辅助核算表格
function loadVouchCheckTable(){
	// define select editor properties
  
   /* var summaryList = ["Engineer", "Programmer", "Designer",
            "Administrator", "Manager", "Director",
            "Accountant"];
    */
	
	var isReadonly=parent.frames["vouchFrame"].grid.config["readonly"];
	var vouchCheckData;
	if(openPage=="vouchKind"){
		vouchCheckData=parent.kindCheckJson[jsonKey];
	}else{
		vouchCheckData=parent.vouchCheckJson[jsonKey];
	}
	rowIndex=0;   
    //弹出窗口需要赋值，点击分录加载页面不需要赋值
    if(dialog){
    	
    	//根据分录金额设置辅助科目方向
    	if($("#debit").val()!="" && parseFloat($("#debit").val())!=0){
    		liger.get("subj_dire").setValue(0);//借方
    		//oldValue = '借'
    	}else if($("#credit").val()!="" && parseFloat($("#credit").val())!=0){
    		liger.get("subj_dire").setValue(1);//贷方
    		//oldValue = '贷'
    		
    	}else{
    		//根据分录科目方向设置辅助科目方向
    		liger.get("subj_dire").setValue(subjAttr.subj_dire);
    	}
		
    }
    
    //由JSON字符串转换为JSON对象
    checkJson=JSON.parse(checkJson);
    
    // define columns
    columns.push({name: "id",type: "int",editor:"DisabledEditor",hide:true,display: "id",align : 'left'});
    columns.push({name: "cid",type: "string",editor:"DisabledEditor",hide:true,display: "cid",align : 'left'});
    columns.push({name: "summary",type: "string",editor: "AutocompleteEditor",editorProps: parent.frames["vouchFrame"].grid.editorProps["summary"],wrap:true,display:"摘要",width:'200px',align : 'left'});
    
    var checkIndex=2;
    //现金流量标注
    //现金流量与辅助核算一起保存
    if(is_cash_show==1){
    	columns.push({name: "cash_name",display:"现金流量",wrap:true,width:'250px',type: "string",editor: "AutocompleteEditor",editorProps: [],align : 'left'});
    	columns.push({name: "cash_item_id",display:"cash_item_id",hide:true,type: "string",editor:"DisabledEditor",align : 'left'});
    	checkIndex=checkIndex+2;
    }
    
    //辅助核算1
    if(checkJson[0]){
    	checkIndex=checkIndex+1;
    	columns.push({name: checkJson[0]["column_check"]+"_name",display:checkJson[0]["check_type_name"],wrap:true,width:getCheckWidth(checkJson[0]["check_table"]),type: "string",editor: "AutocompleteEditor",editorProps: [],align : 'left'});
    	columns.push({name: checkJson[0]["column_check"],display:checkJson[0]["column_check"],hide:true,type: "string",editor:"DisabledEditor",align : 'left'});
    }
    //辅助核算2
    if(checkJson[1]){
    	columns.push({name: checkJson[1]["column_check"]+"_name",display:checkJson[1]["check_type_name"],wrap:true,width:getCheckWidth(checkJson[1]["check_table"]),type: "string",editor: "AutocompleteEditor",editorProps: [],align : 'left'});
    	columns.push({name: checkJson[1]["column_check"],display:checkJson[1]["column_check"],hide:true,type: "string",editor:"DisabledEditor",align : 'left'});
    }
    //辅助核算3
    if(checkJson[2]){
    	columns.push({name: checkJson[2]["column_check"]+"_name",display:checkJson[2]["check_type_name"],wrap:true,width:getCheckWidth(checkJson[2]["check_table"]),type: "string",editor: "AutocompleteEditor",editorProps: [],align : 'left'});
    	columns.push({name: checkJson[2]["column_check"],display:checkJson[2]["column_check"],hide:true,type: "string",editor:"DisabledEditor",align : 'left'});
    }
    //辅助核算4
    if(checkJson[3]){
    	columns.push({name: checkJson[3]["column_check"]+"_name",display:checkJson[3]["check_type_name"],wrap:true,width:getCheckWidth(checkJson[3]["check_table"]),type: "string",editor: "AutocompleteEditor",editorProps: [],align : 'left'});
    	columns.push({name: checkJson[3]["column_check"],display:checkJson[3]["column_check"],hide:true,type: "string",editor:"DisabledEditor",align : 'left'});
    }

       
    //科目性质=03银行科目
    if(subjAttr.subj_nature_code=="03"){
    	columns.push({name: "pay_name",display:"结算方式",width:'100px',wrap:true,type: "string",editor: "AutocompleteEditor",editorProps: [],align : 'left'});
    	columns.push({name: "pay_type_code",display:"pay_type_code",hide:true,type: "string",editor:"DisabledEditor",align : 'left'});
    }
    if(subjAttr.is_bill=="1"){
		columns.push({name: "paper_type_code",display:"票据类型",width:'130px',wrap:true,type: "string",editor: "AutocompleteEditor",editorProps: [],align : 'left'});
		columns.push({name: "check_no",display:"票据号",width:'120px',wrap:true,type: "string",editor: "AutocompleteEditor",editorProps: [],align : 'left'});
    }
   
    //var cc = JSON.stringify(checkJson[0]);
    //科目性质=04、05应收、应付科目 
    if((subjAttr.subj_nature_code=="04" || subjAttr.subj_nature_code=="05")){
    	if(parent.paraList["049"]==1){
    		columns.push({name: "business_no",display:"单据号",width:'130px',wrap:true,type: "string",align : 'left'});
    	}
    	
    	if(parent.paraList["035"]==1){
        	columns.push({name: "con_no",display:"合同编号",width:'130px',wrap:true,type: "string",align : 'left'});
        	columns.push({name: "occur_date",display:"发生日期",width:'81px',type: "string",editor:function (grid){
        	//	var $td = grid.getActiveCell();
        	//	$td.find("div").text(new Date().toDateString());
        	//	var $row=grid.getCellRow($td);
        	//	console.log("========",grid.getCellDataByKey($row.index(), "occur_date")); 
        	return "DateEditor";
        	},align : 'left'});
        	columns.push({name: "due_date",display:"到期日期",width:'81px',type: "string",editor:"DateEditor",align : 'left'});
    	}
		
    }
    columns.push({name: "money",type: "number",display:'金额',width : '100px',align : 'right'});
    
    
   /* var deptWidth=$(window).width()-13-31-320-13-120-12.5;
    var columns = [{name: "id",type: "int",editor:"DisabledEditor",hide:true,display: "ID",align : 'left'},
                   {name: "cid",type: "int",editor:"DisabledEditor",hide:true,display: "cid",align : 'left'}, 
                   {name: "summary",type: "string",editor: "AutocompleteEditor",editorProps: occupations,wrap:true,display:"摘要",width:'320px',align : 'left'}, 
                   {name: "dept_id",type: "string",editor: "AutocompleteEditor",editorProps: occupations,display:"部门",width:deptWidth+'px',align : 'left'},                  
                   {name: "money",type: "number",display:'金额',width : '120px',align : 'right'}];
*/
    // initialize grid
	
	var isSelectable=true;//复选框
	var isSortable=false;//排序
	var isEmptyRow=true;
	if(!dialog){
		//点击分录加载
		isReadonly=true;
		$("#headTable").css("display","none");
		$("#vouchCheckDiv").css("top","18px");
	}else{
		//右键菜单
	 	initMenu();
	}
	
	if(isReadonly){
		isSelectable=false;
		isSortable=true;
		isEmptyRow=false;
	}
	
    var options = {
        // add an empty row at the end of grid
        emptyRow: isEmptyRow,
        // enable sortable callbacks
        sortable: isSortable,
        // disable specific keys
        disableKeys: [],
        // move active cell when a row is removed
        moveOnRowRemove: true,
        // skip these cells on duplicate action
        skipOnDuplicate: ["id"],
        // set the initial order of table
        initialSort: {col: "id", order: "asc"},
        selectable: isSelectable,
        readonly : isReadonly
    };

    //console.log("columns",columns);
    // initialize grid with data, column mapping and options
    grid = $(".sensei-grid-default").grid(vouchCheckData, columns, options);
    
    // register editors that are bundled with sensei grid
    grid.registerEditor(BasicEditor);
   // grid.registerEditor(BooleanEditor);
   // grid.registerEditor(TextareaEditor);
  //  grid.registerEditor(SelectEditor);    
    grid.registerEditor(AutocompleteEditor);
   // grid.registerEditor(DisabledEditor);
    grid.registerEditor(DateEditor);

    // register row actions
    // grid.registerRowAction(BasicRowActions);

    // example listeners on grid events
    grid.events.on("editor:save", function (data, $cell) {
    
    	var $row=grid.getCellRow($cell);
    	var isCheckVal=true;
    	//检查空字符串
         var regu = "^[ ]+$";
         var re = new RegExp(regu);
         if(re.test($cell.find("div").text())){
        	 isCheckVal=false;
             $cell.find("div").text("");
         }
         //定义columnName数组
         var columnName=$cell.data("column");
         if(columnName=="check_no" || columnName=="con_no"){
        	 //票据号、合同编号不检查
        	 return;
         }
         
         if(isCheckVal && $cell.find("div").text()!="" && columnName=="money"){
        
        	 var reMoney=0;
        	 if($cell.find("div").text()=="="){
        		 
        		 //取借贷平衡
        		 var rowDebit=parseFloat($("#debit").val()==""?0:$("#debit").val());//当前借方分录金额
        		 var rowCredit=parseFloat($("#credit").val()==""?0:$("#credit").val());//当前贷方分录金额
        		 var debitSum=0.00;
        		 var creditSum=0.00;
        		 if($("#cellNumber").val()==4 || $("#cellNumber").val()==5 || $("#cellNumber").val()==6){
        			 //财务会计
        			 debitSum=parseFloat(_.sum(pGrid.getGridData(),"debit"))-rowDebit;//取分录借方合计
        			 creditSum=parseFloat(_.sum(pGrid.getGridData(),"credit"))-rowCredit;//取分录贷方合计
        		 }else{
        			 //预算会计
        			 debitSum=parseFloat(_.sum(pGrid.getGridData(),"budg_debit"))-rowDebit;//取分录借方合计
        			 creditSum=parseFloat(_.sum(pGrid.getGridData(),"budg_credit"))-rowCredit;//取分录贷方合计
        		 }
        		 
        		 if(liger.get("subj_dire").getValue()==0){
        			 //借方
        			 reMoney=creditSum-debitSum;
        		 }else{
        			 //贷方
        			 reMoney=debitSum-creditSum;
        		 }
        		 
        		 reMoney=reMoney-parseFloat(_.sum(grid.getGridData(),"money"));
        		 reMoney=toDecimal(reMoney);//格式化两位小数
        		 
        	 }else{
        		//金额运算
        		reMoney=evelMoney($cell.find("div").text());
         	    if(reMoney!=null){
         	    	reMoney=toDecimal(reMoney);//格式化两位小数
              		
         	    }else{
         	    	isCheckVal=false;
         	    	reMoney="";
//                  console.info("isNaN","不是数字");
         	    }
        	}
        	$cell.find("div").text(formatNumber(reMoney,2,1));
    	    sumMoney();//合计金额
         }
         
         //字典下拉框保存事件
         if(columnName && (columnName=="cash_name" || columnName=="pay_name" || columnName=="paper_type_code" || columnName.indexOf("check")!=-1)){
        	// console.log(this.grid.editorProps[columnName]);
        	 saveCheckId(isCheckVal,$row,$cell,this.grid.editorProps[columnName],columnName);
        	 if(columnName=="paper_type_code"){
				//根据票据类型加载票据号
	          	var para={
	          		column_id:"check_no",
	          		paper_type_code:$cell.find("div").text().split(" ")[0]
	          	};
	          	ajaxJsonObjectByUrl("accCheckDictList.do?isCheck=false",para,function (responseData){
	          		grid.editorProps["check_no"] = responseData;
	          		grid.showEditor();
	          	},false);
 			
        	 }
         }
         
    	
    });
    grid.events.on("editor:load", function (data, $cell) {
        //console.info("set value in editor:", data, $cell);
    });
    grid.events.on("cell:select", function ($cell) {
       // console.info("active cell:", $cell);
    });
    grid.events.on("cell:clear", function (oldValue, $cell) {
       // console.info("clear cell:", oldValue, $cell);
    });
    grid.events.on("cell:deactivate", function ($cell) {
      //  console.info("cell deactivate:", $cell);
    });
    grid.events.on("row:select", function ($row) {
      //  console.info("row select:", $row);
    });
    grid.events.on("row:remove", function (data, row, $row) {
       // console.info("row remove:", data, row, $row);
    });
    grid.events.on("row:mark", function ($row) {
      //  console.info("row mark:", $row);
    });
    grid.events.on("row:unmark", function ($row) {
      //  console.info("row unmark:", $row);
    });
    grid.events.on("row:save", function (data, $row, source) {
     //   console.info("row save:", source, data);
        // save row via ajax or any other way
        // simulate delay caused by ajax and set row as saved
       setTimeout(function () {
            grid.setRowSaved($row);
          //  $('#vouchCheckDiv').scrollTop( $('#vouchCheckDiv')[0].scrollHeight );
        });
    });

    // implement basic sorting
    grid.events.on("column:sort", function (col, order, $el) {
       // console.info("column sort:", col, order, $el);
        var sorted = _.sortBy(vouchCheckData, col);
        if (order === "desc") {
            sorted = sorted.reverse();
        }
        grid.updateData(sorted);
    });

    // render grid
    grid.render();
    if(isReadonly){
    	$("input[name='controllerButton']").ligerButton({disabled: true});
    	$("input[name='controllerButton']").attr("disabled",true);
    }else{
    	loadCheckDict();//加载辅助核算下拉列表
    	initCheckGridValue(isReadonly);
    }
   
    initRow=grid.getRows().length;
    
    /*已改为一次性加载，不需要单独查了
    //如果内存中没有数据需要查询数据库（只有一条时），修改了分录，需要更新表格数据
    if(!isReadonly && initRow==2 && isLoadSql &&  ((parseFloat($("#credit").val())==0 && parseFloat($("#debit").val())!=parseFloat($("#sum_money").text())) || (parseFloat($("#debit").val())==0 && parseFloat($("#credit").val())!=parseFloat($("#sum_money").text())))){
    	var $row=grid.getRowByIndex(0);
    	var $money = grid.getCellFromRowByKey($row, "money");
    	var momey=parseFloat($("#debit").val())!=0?parseFloat($("#debit").val()):parseFloat($("#credit").val());
    	$money.find("div").text(toDecimal(momey));
    	isSaveCheck=true;
	}*/
    
    sumMoney();//合计金额
    initMoney=parseFloat($("#sum_money").text());
    
 //   delInvalidData();//删除无效的数据
    // api examples
  //  window.scrollTo(0,0); 
  /*  console.group("data api examples");
    console.log("grid.getRowDataByIndex(0):", grid.getRowDataByIndex(0));
    console.log("grid.getRowData($row):", grid.getRowData($row));
    console.log("grid.getCellDataByIndex(0, 1):", grid.getCellDataByIndex(0, 1));
    console.log("grid.getGridData():", grid.getGridData());
    console.groupEnd();*/

	if(checkJson[0] && !dialog){
		//默认点击第一行第一个辅助核算
    	var $row=grid.getRowByIndex(0);
    	if(!$row || !$row[0]){
    		return;
    	}
    	clickCheckDetailFun("clickCell",$($row[0].cells[checkIndex]));
    	$("#checkBlance").show();
	}
   
}


function delInvalidData(){
	 $.each(grid.getRows(),function (n,value) {
			
	     if(grid.getCellDataByKey(n, "money")=="" || isNaN(grid.getCellDataByKey(n, "money"))){
	       	 grid.removeRow(grid.getRowByIndex(n));
	     }
	 });
}

//合计金额
function sumMoney(){
/*	var sumMoney=0.00;
	$.each(grid.getRows(),function (n,value) {		
   	     if(grid.getCellDataByKey(n, "money")!="" && !isNaN(grid.getCellDataByKey(n, "money"))){
   	    	sumMoney+=parseFloat(grid.getCellDataByKey(n, "money"));
   	     }
	});
	*/
	$("#sum_money").text(formatNumber(_.sum(grid.getGridData(),"money"),2,1));
}

//选择下拉框，保存ID列
function saveCheckId(isCheckVal,$row,$cell,checkist,columnName){
	var isSelCheck=false;
	
	if(isCheckVal){
		$.each(checkist, function (i, str) {
			
	 		if($cell.find("div").text()==str.name.split(" ")[0]){
		       $cell.find("div").text(str.name);
	 		}
	 	
	        if ($cell.find("div").text()==str.name) {
	         	isSelCheck=true;
	         	if(columnName=="paper_type_code"){
	         		//票据类型
	         		$($row[0].cells[$cell.index()]).find("div").text(str.name);//保存
	         		$($row[0].cells[$cell.index()+1]).find("div").text("");//票据号清空
	         	}else{
	         		if(str.no!=undefined){
		         		$($row[0].cells[$cell.index()+1]).find("div").text(str.id+"@"+str.no);//保存ID,no
		         	}else{
		         		$($row[0].cells[$cell.index()+1]).find("div").text(str.id);//保存ID
		         	}
	         	}
	         	 
	         	if(columnName=="cash_name"){
	         		//更改现金流量方向
	         		changeSubjDire(str.type,$row);
		         } 
	         	
	 			/*不能写死，单元格显示也没有什么问题就一个text
	 			 * if(columnName=="check2_name"){
	 				//职工辅助核算(显示部门名称)，显示部门名称，需要与controller方法accCheckDictList匹配
	 				$cell.find("div").text(str.name.substring(0,str.name.indexOf("(")));//截取左括号前面的字符串
		        }
	 			
	 			if(columnName=="check3_name"){
	 				//项目辅助核算(显示职工名称)，需要与controller方法accCheckDictList匹配
	 				$cell.find("div").text(str.name.substring(0,str.name.indexOf("(")));//截取左括号前面的字符串
		        }*/
	         	
	            return false;
	        }
		});
	}
	
	//输入的值在下拉框里面不存在，清空
	if(!isSelCheck){
		$cell.find("div").text("");
		$($row[0].cells[$cell.index()+1]).find("div").text("");//ID,no
     	
	}
	
}

//编辑现金流量项目更改方向下拉框的值
function changeSubjDire(type,$row){
	liger.get("subj_dire").setValue(type);
	if(type===0){
		liger.get("subj_dire").setText("借");
	}else if(type===1){
		liger.get("subj_dire").setText("贷");
	} 
}

/*function aa(columnName){
	console.log(columnName)
	if(columnName=="cash_name" ){
		$("#subj_dire").ligerComboBox({disabled:true});
	} 
}*/
//调整辅助核算宽度
function getCheckWidth(checkTable){
	var checkWidth="180px";
    if(checkTable=="hos_dept_dict" || checkTable=="hos_sup_dict" || checkTable=="hos_proj_dict"){
    	checkWidth="300px";
    	return checkWidth;
    }else if(checkTable=="hos_source"){
    	checkWidth="100px";
		return checkWidth;
    }
    return checkWidth;
}

//表格加载完，初始化值
function initCheckGridValue(isReadonly){
	//第一行摘要获取焦点
    var $row = grid.getRowByIndex(0);
    //$row[0].cells[3].innerText=$("#summary").val();
    grid.setActiveCell($($row[0].cells[3]));
    grid.$el.focus();
    //grid.exitEditor(true);
    
    if($($row[0].cells[3]).find("div").text()!=""){
    	grid.editCell(); 
    	return;
    }
    
    var isSave=false;
    if($($row[0].cells[3]).find("div").text()==""){
    	if(summary!="" && summary.indexOf("[")!=-1 && summary.indexOf("]")!=-1){
    		summary=summary.substring(summary.indexOf("]")+1,summary.length);
    	}
    	$($row[0].cells[3]).find("div").text(summary);//摘要赋值
    	
    	//分录有金额默认带分录的金额
    	var $moneyCell= grid.getCellFromRowByKey($row, "money");
    	if(parseFloat($("#debit").val())!=0){
    		$moneyCell.find("div").text($("#debit").val());
    	}else if(parseFloat($("#credit").val())!=0){
    		$moneyCell.find("div").text($("#credit").val());
    	}
    	
    	isSave=true;
    }
    
    
    if(subjAttr.subj_nature_code=="04" || subjAttr.subj_nature_code=="05"){
    	var date = new Date();
	    var $dueDateCell = grid.getCellFromRowByKey($row, "due_date");
	    if(parent.paraList["027"]==0){
	    	//到期日期默认月底
	    	var lastDay=getLastDay(date.getFullYear(),date.getMonth() + 1);
	 	    $dueDateCell.find("div").text(date.getFullYear()+"-"+(date.getMonth() + 1)+"-"+lastDay);
	    }else{
	    	//到期日期默认年底
	 	    $dueDateCell.find("div").text(date.getFullYear()+"-12-31");
	    }
	   
	    
	    var $occurDateCell = grid.getCellFromRowByKey($row, "occur_date");
	    $occurDateCell.find("div").text($("#vouch_date",parent.document).val());
	    isSave=true;
    }
    
    if(isSave){
    	grid.setRowSaved($row);//保存，改变编辑表格状态
    	if(!isReadonly){
    		grid.assureEmptyRow();
    	}
    	
    }
    grid.editCell(); 
}

function loadCheckDict(){
	
	/*var value = liger.get("subj_dire").getValue();
	if(!grid){
		return;
	}*/
	//加载现金流量下拉字典
	if(grid.editorProps["cash_name"] && grid.editorProps["cash_name"].length==0){
		
		if(parent.checkDict["cash"] && parent.checkDict["cash"].length>0){
			grid.editorProps["cash_name"] = parent.checkDict["cash"];
		}else{
			var para={						
					column_id:"cash_item_id"
					//cash_dire: value
			};
			ajaxJsonObjectByUrl("accCheckDictList.do?isCheck=false",para,function (responseData){
		    	grid.editorProps["cash_name"] = responseData;
		    	parent.checkDict["cash"]=responseData;
		    });
		}
		
	}

	//加载辅助核算下拉字典
	if(checkJson.length>0){
		$.each(checkJson,function (i,obj){
			if(checkJson[i] && grid.editorProps[checkJson[i]["column_check"]+"_name"].length==0){
				
				if(parent.checkDict[checkJson[i]["column_check"]] && parent.checkDict[checkJson[i]["column_check"]].length>0){
					grid.editorProps[checkJson[i]["column_check"]+"_name"] = parent.checkDict[checkJson[i]["column_check"]];
				}else{
				
					//核算分类编码字段
					var check_type_code="";
					//console.log(JSON.stringify(checkJson[i]))
					if(checkJson[i].check_type_code!=undefined && checkJson[i].check_type_code!=""){
						//替换条件1;2;3--> '1','2','3'
						check_type_code="'"+checkJson[i].check_type_code.replace(/\;/g, "','")+"'";
					}
					
					//部门类型字段
					if(check_type_code!="" && checkJson[i].check_table=="hos_dept_dict"){
						check_type_code=" and a.dept_id in (select attr.dept_id from ACC_DEPT_ATTR attr where attr.group_id=a.group_id and " +
						" attr.hos_id=a.hos_id and attr.type_code in ("+check_type_code+")) ";
					}
					//职工分类字段
					else if(check_type_code!="" && checkJson[i].check_table=="hos_emp_dict"){
						check_type_code=" and a.kind_code in("+check_type_code+") ";
					}
	//				//项目分类字段
	//				else if(check_type_code!="" && checkJson[i].check_table=="hos_proj_dict"){
	//					check_type_code_filed="type_code";
	//				}
	//				//库房分类字段
	//				else if(check_type_code!="" && checkJson[i].check_table=="hos_store_dict"){
	//					check_type_code_filed="type_code";
	//				}
	//				//客户分类字段
	//				else if(check_type_code!="" && checkJson[i].check_table=="hos_cus_dict"){
	//					check_type_code_filed="type_code";
	//				}
	//				//供应商分类字段
	//				else if(check_type_code!="" && checkJson[i].check_table=="hos_sup_dict"){
	//					check_type_code_filed="type_code";
	//				}
					else if(check_type_code!="" && checkJson[i].check_table=="hos_source"){
						check_type_code=" and a.source_attr in("+check_type_code+") ";
					}else if(check_type_code!=""){
						//默认条件
						check_type_code=" and a.type_code in("+check_type_code+") ";
					}
					
					var para={						
							check_id:checkJson[i].check_id,
							column_id:checkJson[i].column_id,
							column_no:checkJson[i].column_no,
							column_code:checkJson[i].column_code,
							column_name:checkJson[i].column_name,
							check_table:checkJson[i].check_table,
							column_check:checkJson[i].column_check,
							check_type_code:check_type_code,
							out_code:subjAttr.out_code//支出性质
					};
					
					ajaxJsonObjectByUrl("accCheckDictList.do?isCheck=false",para,function (responseData){
				    	grid.editorProps[checkJson[i]["column_check"]+"_name"] = responseData;
				    	if(checkJson[i].column_id!="dept_id" && check_type_code==""){
				    		parent.checkDict[checkJson[i]["column_check"]]=responseData;
				    	}
				    });
				}
			}
		});
	}
	
	//加载结算方式
	if(grid.editorProps["pay_name"] && grid.editorProps["pay_name"].length==0){
		
		if(parent.checkDict["payType"] && parent.checkDict["payType"].length>0){
			grid.editorProps["pay_name"] = parent.checkDict["payType"];
		}else{
			var para={						
				column_id:"pay_code"
			};
			ajaxJsonObjectByUrl("accCheckDictList.do?isCheck=false",para,function (responseData){
		    	grid.editorProps["pay_name"] = responseData;
		    	parent.checkDict["payType"]=responseData;
		    });
		}
		
	}
	
	
	//加载票据类型
	if(grid.editorProps["paper_type_code"] && grid.editorProps["paper_type_code"].length==0){
		
		if(parent.checkDict["paperType"] && parent.checkDict["paperType"].length>0){
			grid.editorProps["paper_type_code"] = parent.checkDict["paperType"];
		}else{
			var para={						
				column_id:"paper_type_code"
			};
			ajaxJsonObjectByUrl("accCheckDictList.do?isCheck=false",para,function (responseData){
				//console.log(responseData)
		    	grid.editorProps["paper_type_code"] = responseData;
		    	parent.checkDict["paperType"]=responseData;
		    });
		}
	}
	
	/*//加载票据号
	if(grid.editorProps["check_no"] && grid.editorProps["check_no"].length==0){
		
	}*/
} 

//金额运算
var verlis = /^[\d\+\-\*\/\.{0,1}\,{0,1}\={0,1}]+$/;
function evelMoney(a){
	
	if(verlis.test(a)){
		var sum = a.replace(/,/g,"");//替换千分符号
		try{
			return eval(sum);	
		}catch(e){
			
		}
		
	}
	return null;
	
	/*$(".sensei-grid-basic-editor").find("input").keydown(function(event) {
		
		if (event.keyCode == '13') {
			var a = $(".sensei-grid-basic-editor input").val();
			if(a != " " && a != "=" && a.length != 0){
				if(verlis.test(a)){
					var sum = a.replace(",","");
					try{
						var num = eval(sum);	
					}catch(e){
						if(e != ""){
							//alert("错误返回值："+e);
							$(".sensei-grid-basic-editor input").val("");
							return false;
						}
					}
					$(".sensei-grid-basic-editor input").val(num);
				}else{
					$(".sensei-grid-basic-editor input").val("");
					return false;
					//$.ligerDialog.error("请输入合法数字！");
				}
			}
		}
	})*/
};


/**********************右键菜单处理********begin*****************************/
function initMenu(){
	var menuData=[
         [{
             text: "render",
             func: function () {}
         }],
         [{
             text: "插入",
             func: function (event) {
            	 grid.insertActiveRow();
            }
         }],
         [{
             text: "存摘要",
             func: function (event) {
            	 //var rownum=$(event.path[2]).find('li:first > a').text().replace("行号：","");
            	var $activeCell =grid.getActiveCell();
 		    	if(!$activeCell){
 		    		return;
 		    	}
 		    	var $row=grid.getCellRow($activeCell);
 		    	var summary=$($row[0].cells[3]).find("div").text();
 		    	parent.mySaveSummary(summary);
            }
         },{
             text: "摘要管理",
             func: function () {
            	parent.myGetSummary();
            }
         }],
         [{
             text: "快捷键说明",
             func: function () {
            	parent.myHelp();
             }
         }]
	 ];
 	$("#vouchCheckDiv").smartMenu(menuData,{
        name:"rightMenu",
        offsetX:2,
        offsetY:2,
        textLimit:6,
        topsetX:0,
        beforeShow:function(e) {
        	return readRowNum(e);
        },
        afterShow:function() {

        }
    });
	
}

//加载行号
function readRowNum(e){
	var textNew = "";
	var event = event || window.event;
	//  根据鼠标所点击的元素的父元素是否为thead来判断是否为表头
	if($(event.srcElement).parents('thead')[0]){   
		textNew = "行号：0";
	} else if($(event.srcElement).parents('tr','.sensei-grid-tbody')[0]){
		//   取出父元素tr的索引值
		var RowNum = $(event.srcElement).parents('tr','.sensei-grid-tbody').index() + 1;
		textNew = "行号：" + RowNum;
	}else if(grid.activeEditor){  //  若点击是编辑框，则取出.activeCell类td的父元素tr索引值
		var RowNum = $('.activeCell').parents('tr','.sensei-grid-tbody').index() + 1;
		textNew = "行号：" + RowNum;
	}

	return textNew;
}
/**********************右键菜单处理********end*****************************/


//辅助核算点击事件
function clickCheckDetailFun(flag,$activeCell){
	
	//dialog._setTitle(title);
	var curColumn=$activeCell.data("column");
	if(!curColumn){
		return;
	}
	
	if(curColumn=="check_no" || curColumn.indexOf("check")==-1){
		//不是辅助核算字段
		return;
	}
	
	var $row=grid.getCellRow($activeCell);
	var $rowData=grid.getRowData($row);
	var cellText=$($row[0].cells[$activeCell.index()]).find("div").text();
	if(cellText.replace(/\s+/g,"")==""){
		return;
	}
	
	//不是往来科目
	/*if($("#subj_nature_code").val()!="04" && $("#subj_nature_code").val()!="05"){
		return
	}*/
	
	var checkSql="";
	$.each(grid.columns, function (i,column) {
		//不是辅助核算字段
		var columnName=column.name;
		if(!columnName){
			return true;
		}
		
		if(columnName=="check_no" || columnName.indexOf("check")==-1 || columnName.indexOf("_name")!=-1){
			return true;
	    }
		
		var columnCell=grid.getCellFromRowByKey($row, columnName);
		var columnVal=columnCell.find("div").text();
		if(columnVal!=""){
			if(columnVal.indexOf("@")!=-1){
				columnVal=columnVal.split("@")[0];//取ID
			}
			if(columnName.indexOf("zcheck")==-1){
				columnName=columnName+"_id";
			}
			checkSql=checkSql+" and "+columnName+" = '"+columnVal+"'";
		}
    	
	});
	if(checkSql==""){
		return;
	}
	
	//查询辅助核算余额
	var para={
		vouch_date:parent.liger.get("vouch_date").getValue(),
		p_028:parent.paraList["028"],
		subj_code:$("#subj_code").val(),
		subj_dire:subjAttr.subj_dire,
		checkSql:checkSql
	};
	
	ajaxJsonObjectByUrl("querySuperVouchCheckBalance.do?isCheck=false",para,function (responseData){
		if(dialog){
			dialog._setTitle(title+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;【" +
					cellText+"】期初余额："+responseData.bal_os+" 本期借方："+responseData.this_od+" 本期贷方："+responseData.this_oc+" 期末余额："+responseData.end_os);
		}else{
			$("#checkBlance").html("辅助核算&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;【" +cellText+"】期初余额："+responseData.bal_os+" 本期借方："+responseData.this_od+" 本期贷方："+responseData.this_oc+" 期末余额："+responseData.end_os);
		}
		
	});
	

}

function getRowDataMoney(){
	var rowCount=0;
	$.each(grid.getGridData(),function (n,obj) {
		if(obj.money=="")obj.money=0;
		if(parseFloat(obj.money)!=0){
			rowCount++;
		}
	});
}

//取现金流量属性
function getCashAttr(cash_item_id){
	var attr={};
	$.each(grid.editorProps["cash_name"], function (i, obj) {
		 if(cash_item_id==obj.id){
			 attr=obj;
			 return false;
		 }
	 });
	return attr;
}