/**
 * 凭证现金流量标注
 */ 
//var oldValue ='流入';
var page="vouchCash";
function loadDict() {
	//var subj_dire = $("#subj_dire_text").val();
	$("#subj_dire").ligerComboBox({
		width:60,
		cancelable: false,
        data: [
            { text: '流入', id: '0' },
            { text: '流出', id: '1' }
        ],  
        value: 0 ,
        disabled:true
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
	//$("#subj_dire").focus();
}


var vouchCheckData = [];
var grid;
var initRow=0;
var initMoney=0;
//初始化辅助核算表格
function loadVouchCheckTable(){
	// define select editor properties
  
   /* var summaryList = ["Engineer", "Programmer", "Designer",
            "Administrator", "Manager", "Director",
            "Accountant"];
    */
	//console.log(parent.vouchCashJson)
	var vouchCheckData;
	if(openPage=="vouchKind"){
		vouchCheckData=parent.kindCashJson[jsonKey];
	}else{
		vouchCheckData=parent.vouchCashJson[jsonKey];
	}
	rowIndex=0;
	var	isReadonly=parent.frames["vouchFrame"].grid.config["readonly"];
	
	//根据004参数控制，两个json的数据是通用的
	if(!vouchCheckData){
		if(openPage=="vouchKind"){
			vouchCheckData=parent.kindCheckJson[jsonKey];
		}else{
			vouchCheckData=parent.vouchCheckJson[jsonKey];
		}
	}
	rowIndex=0;
    //弹出窗口需要赋值，点击分录加载页面不需要赋值
    if(dialog!=null){
	    //根据分录金额设置辅助科目方向
	    if($("#debit").val()!="" && parseFloat($("#debit").val())!=0){
			liger.get("subj_dire").setValue(0);//流入
			//oldValue = '流入'
			
		}else if($("#credit").val()!="" && parseFloat($("#credit").val())!=0){
			liger.get("subj_dire").setValue(1);//流出
			//oldValue = '流出'
		}else{
			//根据分录科目方向设置辅助科目方向
			liger.get("subj_dire").setValue(subjAttr.subj_dire);
			
		}
    }
   // console.log("vouchCheckData：",JSON.stringify(vouchCheckData)); 
    // generate data
  /*  vouchCheckData.push({
            "id": getRowIndex(),
            "summary":$("#summary").val(),
            "dept_id":"",
            "check_money": ''
     });*/
    // define columns
    var columns=[];
    columns.push({name: "id",type: "int",editor:"DisabledEditor",hide:true,display: "id",align : 'left'});
    columns.push({name: "cid",type: "string",editor:"DisabledEditor",hide:true,display: "cid",align : 'left'});
    columns.push({name: "summary",type: "string",editor: "AutocompleteEditor",editorProps: parent.frames["vouchFrame"].grid.editorProps["summary"],wrap:true,display:"摘要",width:'200px',align : 'left'});
   
    //现金流量标注    
    columns.push({name: "cash_name",display:"现金流量",wrap:true,width:'350px',type: "string",editor: "AutocompleteEditor",editorProps: [],align : 'left'});
    columns.push({name: "cash_item_id",display:"cash_item_id",hide:true,type: "string",editor:"DisabledEditor",align : 'left'});
    columns.push({name: "money",type: "number",display:'金额',width : '100px',align : 'right'});
   
	/*
	 * 已改为一次性加载，不需要单独查了
	 * if(!vouchCheckData && $("#detail_id").val()!=""){	
		
		var param={vouch_id:$("#vouch_id").val(),vouch_detail_id:$("#detail_id").val(),is_new:1,para004:parent.paraList["004"]};
		//console.log("param:",param);
		ajaxJsonObjectByUrl("querySuperVouchByCash.do?isCheck=false",param,function (responseData){
			vouchCheckData=responseData.Rows;//辅助核算
			//parent.vouchCashJson[$("#uniqueNumber").val()]=vouchCheckData;
			rowIndex=parseInt(responseData.Rows.length);
			isLoadSql=true;
			//liger.get("subj_dire").setValue(responseData.dire);//方向，从父页面取，现金流量没有方向
			//存内存里面，下次不要从数据库里面查了
			if(isReadonly){
				parent.vouchCashJson[$("#uniqueNumber").val()]=vouchCheckData;
				
			}
		},false);
		
    }*/
    
    
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
		//分录页面加载页面
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
        
         var columnName=$cell.data("column");
         if(columnName=="check_no" || columnName=="con_no"){
        	 //合同编号、票据号不检查
        	 return;
         }
         
         if(isCheckVal && $cell.find("div").text()!="" && columnName=="money"){
        	 
        	 var reMoney=0;
        	 if($cell.find("div").text()=="="){
        		 
        		 //取借贷平衡
        		 var rowDebit=parseFloat($("#debit").val()==""?0:$("#debit").val());//当前借方分录金额
        		 var rowCredit=parseFloat($("#credit").val()==""?0:$("#credit").val());//当前贷方分录金额
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
         if(columnName=="cash_name" || columnName=="pay_name" || columnName.indexOf("check")!=-1){
        	// console.log(this.grid.editorProps[columnName]);
        	 saveCheckId(isCheckVal,$row,$cell,this.grid.editorProps[columnName],columnName);
        	
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
      //  console.info("column sort:", col, order, $el);
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
    	loadCheckDict();//加载现金流量下拉列表
    	initCheckGridValue(isReadonly);
    }
    
   
    initRow=grid.getRows().length;
    
    /*已改为一次性加载，不需要单独查了
    //如果内存中没有数据需要查询数据库（只有一条时），修改了分录，需要更新表格数据
    if(!isReadonly && initRow==2 && isLoadSql &&  ((parseFloat($("#credit").val())==0 && parseFloat($("#debit").val())!=parseFloat($("#sum_money").text())) || (parseFloat($("#debit").val())==0 && parseFloat($("#credit").val())!=parseFloat($("#sum_money").text())))){
    	var $row=grid.getRowByIndex(0);
    	var $money = grid.getCellFromRowByKey($row, "money");
    	var money=parseFloat($("#debit").val())!=0?parseFloat($("#debit").val()):parseFloat($("#credit").val());
    	$money.find("div").text(toDecimal(money));
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
	/*var sumMoney=0.00;
	$.each(grid.getRows(),function (n,value) {		
   	     if(grid.getCellDataByKey(n, "money")!="" && !isNaN(grid.getCellDataByKey(n, "money"))){
   	    	sumMoney+=parseFloat(grid.getCellDataByKey(n, "money"));
   	     }
	});
	$("#sum_money").text(toDecimal(sumMoney));
	*/
	
	$("#sum_money").text(formatNumber(_.sum(grid.getGridData(),"money"),2,1));
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
};

//选择下拉框，保存ID列
function saveCheckId(isCheckVal,$row,$cell,checkist,columnName){
	var isSelCheck=false;
	if(isCheckVal){
		$.each(checkist, function (i, str) {
			 
	 		//科目：如果输入编码匹配的话，自动选中
	 		if($cell.find("div").text()==str.name.split(" ")[0]){
	 			$cell.find("div").text(str.name);
	 		}
	 		
	        if ($cell.find("div").text()==str.name) {
	         	isSelCheck=true;
	         	if(str.no!=undefined){
	         		$($row[0].cells[$cell.index()+1]).find("div").text(str.id+"@"+str.no);//保存ID,no
	         	}else{
	         		$($row[0].cells[$cell.index()+1]).find("div").text(str.id);//保存ID
	         	}
	         	
	         	if(columnName=="cash_name"){ 
	         		//更改现金流量方向
	         		changeSubjDire(str.type,$row);
		        }
	         	
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
		liger.get("subj_dire").setText("流入");
	}else if(type===1){
		liger.get("subj_dire").setText("流出");
	} 
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
    	
    	grid.setRowSaved($row);//保存，改变编辑表格状态
    	if(!isReadonly){
    		grid.assureEmptyRow();
    	}
    	grid.editCell(); 
    }
    
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
}

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

//取现金流量属性
function getCashAttr(cash_item_id){
	var attr={};
	$.each(grid.editorProps["cash_name"], function (i, obj) {
		 if(cash_item_id==obj.id){
			 attr=obj;
			 return false;
		 }
	 });
	return attr
}

