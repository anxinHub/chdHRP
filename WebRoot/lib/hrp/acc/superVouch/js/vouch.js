
//初始化表格
var grid;

var page="vouch";
var moenyWith=141;
var is_budg=1;//{ id: 1, text: '分栏式'},{ id: 2, text: '分屏式' }
function loadVouchTable(){
	
	//加载凭证明细 
	queryVouchDetail();
    // generate data
 /*   for (var i = 0; i < 6; ++i) {
    	vouchDetailData.push({
    		"id": getRowIndex(),
    		"did": '',
            "summary": "",
            "subj_code": "",
            "debit_name": '',
            "credit_name": '',
            "debit": '10.00',
            "credit": '0.00'
        });
    }*/
    //全屏显示表格，所以要计算科目的宽度
	var mul=1;
	var subjName="科目";
	var isBudgHide=true;
	if(is_budg==1){
		//预算会计,科目、金额列翻倍
		mul=2;
		isBudgHide=false;
		subjName="财务会计科目";
	}
	var moneyWidthAll=moenyWith*2*mul;//金额列
	var subjWidth=parseInt(($(window).width()-15-moneyWidthAll)/(mul+1)+20);//科目列取剩余宽度的百分比+20
	var summaryWidth=$(window).width()-15-moneyWidthAll-subjWidth*mul;
	
	//父页面表尾宽度
	$("#vouchFootDiv",parent.document).css("width",$(window).width());//div宽度
	$("#debit_nameSum",parent.document).css("width",moenyWith-2);
	$("#credit_nameSum",parent.document).css("width",moenyWith-1);
	if(is_budg==1){
		$("#budg_subj_nameSum",parent.document).css("width",subjWidth-1);
		$("#budg_debit_nameSum",parent.document).css("width",moenyWith-2);
		$("#budg_credit_nameSum",parent.document).css("width",moenyWith-1);
	}
	
	
    // define columns
    var columns = [{
        name: "id",
        type: "int",
        editor:"DisabledEditor",
        display: "id",align : 'left',hide:true//1唯一辅助核算key
    },{
        name: "did",
        type: "string",
        editor:"DisabledEditor",
        display: "did",align : 'left',hide:true//2
    }, {
        name: "summary",
        type: "string",
        editor: "AutocompleteEditor",
        editorProps: [],wrap:true,hide:false,
        display:"摘要",width:summaryWidth+'px',align : 'left'//3
    }, {
        name: "subj_name",
        type: "string",
        editor: "AutocompleteEditor",
        editorProps: [],wrap:true,
        display:subjName,width: subjWidth+'px',align : 'left'//4
    }, {
        name: "debit_name",
        type: "number",
        display:'借方金额<br/><span>千百十亿千百十万千百十元角分</span>',
       // display:'<span class="l-acc_test" align="right">千百十亿千百十万千百十元角分</span>',
        width : moenyWith+'px',align : 'right',
        allowHTML: false//5
    }, {
        name: "credit_name",
        type: "number",
        display:'贷方金额<br/><span>千百十亿千百十万千百十元角分</span>',
       // display:'<span class="l-acc_test" align="right">千百十亿千百十万千百十元角分</span>',
        width : moenyWith+'px',align : 'right',
        allowHTML: false//6
    },{
        name: "subj_code",
        type: "string",
        editor:"DisabledEditor",
        display: "subj_code",align : 'left',hide:true//7
    },{
        name: "is_check",
        type: "string",
        editor:"DisabledEditor",
        display: "is_check",align : 'left',hide:true//8,停用（用的时候单独根据科目编码取科目属性getSubjAttr）
    },{
        name: "is_cash",
        type: "string",
        editor:"DisabledEditor",
        display: "is_cash",align : 'left',hide:true//9,停用（用的时候单独根据科目编码取科目属性getSubjAttr）
    },{
        name: "subj_nature_code",
        type: "string",
        editor:"DisabledEditor",
        display: "subj_nature_code",align : 'left',hide:true//10,停用（用的时候单独根据科目编码取科目属性getSubjAttr）
    },{
        name: "subj_type_code",
        type: "string",
        editor:"DisabledEditor",
        display: "subj_type_code",align : 'left',hide:true//11,停用（用的时候单独根据科目编码取科目属性getSubjAttr）
    },{
        name: "subj_dire",
        type: "string",
        editor:"DisabledEditor",
        display: "subj_dire",align : 'left',hide:true//12,停用（用的时候单独根据科目编码取科目属性getSubjAttr）
    },{
        name: "debit",
        type: "string",
        editor:"DisabledEditor",
        display: "debit",align : 'left',hide:true//13
    },{
        name: "credit",
        type: "string",
        editor:"DisabledEditor",
        display: "credit",align : 'left',hide:true//14
    },{
        name: "init_did",
        type: "string",
        editor:"DisabledEditor",
        display: "init_did",align : 'left',hide:true//15停用
    },{
        name: "budg_id",
        type: "init",
        editor:"DisabledEditor",
        display: "budg_id",align : 'left',hide:true//16唯一预算会计辅助核算key
    },{
        name: "budg_summary",
        type: "string",
        editor: "DisabledEditor",
        //editorProps: [],wrap:true,
        display:"预算摘要",align : 'left',hide:true//17
    }, {
        name: "budg_subj_name",
        type: "string",
        editor: "AutocompleteEditor",
        editorProps: [],wrap:true,
        display:"预算会计科目",width: subjWidth+'px',align : 'left',hide: isBudgHide//18
    }, {
        name: "budg_debit_name",
        type: "number",
        display:'借方金额<br/><span>千百十亿千百十万千百十元角分</span>',
       // display:'<span class="l-acc_test" align="right">千百十亿千百十万千百十元角分</span>',
        width : moenyWith+'px',align : 'right',
        allowHTML: false,hide: isBudgHide//19
    }, {
        name: "budg_credit_name",
        type: "number",
        display:'贷方金额<br/><span>千百十亿千百十万千百十元角分</span>',
       // display:'<span class="l-acc_test" align="right">千百十亿千百十万千百十元角分</span>',
        width : moenyWith+'px',align : 'right',
        allowHTML: false,hide: isBudgHide//20
    },{
        name: "budg_debit",
        type: "string",
        editor:"DisabledEditor",
        display: "budg_debit",align : 'left',hide:true//21
    },{
        name: "budg_credit",
        type: "string",
        editor:"DisabledEditor",
        display: "budg_credit",align : 'left',hide:true//22
    },{
        name: "budg_subj_code",
        type: "string",
        editor:"DisabledEditor",
        display: "budg_subj_code",align : 'left',hide:true//23
    },{
        name: "budg_did",
        type: "string",
        editor:"DisabledEditor",
        display: "budg_did",align : 'left',hide:true//24
    }];

    // initialize grid
   // var vhReadOnly= true;
   // if($("#vouchId",parent.document).val()=="" || (($("#state",parent.document).val()=="-1" || $("#state",parent.document).val()=="1") && (parent.parent.sessionJson["user_id"]==$("#createUserId",parent.document).val() || parent.paraList["005"]=="1"))){
   // 	vhReadOnly=false;
   // }
    var options = {
        // add an empty row at the end of grid
        emptyRow: true,
        // enable sortable callbacks
        sortable: false,
        // disable specific keys
        disableKeys: [],
        // move active cell when a row is removed
        moveOnRowRemove: true,
        // skip these cells on duplicate action
        skipOnDuplicate: ["id"],
        // set the initial order of table
        initialSort: {col: "id", order: "asc"},
        selectable: true,
        readonly :parent.isReadOnly
    };
    // initialize grid with data, column mapping and options
    grid = $(".sensei-grid-default").grid(parent.vouchJson.Rows, columns, options);
    // register editors that are bundled with sensei grid
    grid.registerEditor(BasicEditor);
 //   grid.registerEditor(BooleanEditor);
  //  grid.registerEditor(TextareaEditor);
  //  grid.registerEditor(SelectEditor);   
    grid.registerEditor(AutocompleteEditor);
    grid.registerEditor(DisabledEditor);
    // register row actions
    // grid.registerRowAction(BasicRowActions);

    // example listeners on grid events  保存时执行
    grid.events.on("editor:save", function (data, $cell) {
        //console.log("save cell:", data,$cell);
    	var $row=grid.getCellRow($cell);
    	//var $rowData=grid.getRowData($row);
    	//var columnName=$activeCell.data("column");
    	//var val=plugin.getCellDataByKey($row.index(), columnName);
    	//var val=plugin.getCellDataByIndex($row.index(),$activeCell.index());//获取当前单元格的值
    	var isCheckVal=true;
    	var $rowData=grid.getRowData($row);        
        var columnName=$cell.data("column");
       
    	//检查空字符串
        var regu = "^[ ]+$";
        var re = new RegExp(regu);
        if(re.test($cell.find("div").text())){
        	 
        	 if(columnName=="debit_name" && $cell.find("div").text()==" " && parseFloat($($row[0].cells[14]).find("div").text())!=0){
        		 //贷方有数据，借方输入空格，取贷方数据
        		 $cell.find("div").text($($row[0].cells[14]).find("div").text());
        	 }else if(columnName=="credit_name" && $cell.find("div").text()==" " && parseFloat($($row[0].cells[13]).find("div").text())!=0){
        		 //借方有数据，贷方输入空格，取借方数据
        		 $cell.find("div").text($($row[0].cells[13]).find("div").text());
        	 }else if(columnName=="budg_debit_name" && $cell.find("div").text()==" " && parseFloat($($row[0].cells[22]).find("div").text())!=0){
        		 //贷方有数据，借方输入空格，取贷方数据
        		 $cell.find("div").text($($row[0].cells[22]).find("div").text());
        	 }else if(columnName=="budg_credit_name" && $cell.find("div").text()==" " && parseFloat($($row[0].cells[21]).find("div").text())!=0){
        		 //借方有数据，贷方输入空格，取借方数据
        		 $cell.find("div").text($($row[0].cells[21]).find("div").text());
        	 }else{
        		 isCheckVal=false;
        		 $cell.find("div").text("");
        	 }
        	 //plugin.editCell();
          	 //plugin.$el.focus();
           	 //window.scroll(0,top);
        }
        
        //格式化金额线//cells[5]:借方金额显示列，cells[13]:借方金额保存列//cells[6]:贷方金额显示列，cells[14]:贷方金额保存列
    	if(columnName=="debit_name" || columnName=="credit_name" || columnName=="budg_debit_name" || columnName=="budg_credit_name"){
    		if(columnName=="debit_name"){
    			//如果借方没有输入，并且贷方有值，返回
    			if($($row[0].cells[6]).find("div").text()!="" && $cell.find("div").text()==""){
    				return;
    			}
    		}
    		if(columnName=="credit_name"){
    			//如果贷方没有输入，并且借方有值，返回
    			if($($row[0].cells[5]).find("div").text()!="" && $cell.find("div").text()=="" ){
    				return;
    			}
    		}
    		
    		if(columnName=="budg_debit_name"){
    			//如果借方没有输入，并且贷方有值，返回
    			if($($row[0].cells[20]).find("div").text()!="" && $cell.find("div").text()==""){
    				return;
    			}
    		}
    		if(columnName=="budg_credit_name"){
    			//如果贷方没有输入，并且借方有值，返回
    			if($($row[0].cells[19]).find("div").text()!="" && $cell.find("div").text()=="" ){
    				return;
    			}
    		}
    		    		
    		//检查是否数字
    		var fmtrMoney=0;//借贷方最终金额
    		if(isCheckVal){
    			if($cell.find("div").text()=="="){
        			//=号取差额
    				fmtrMoney=getDiffMoney(columnName);
        		}else{
        			fmtrMoney=toDecimal($cell.find("div").text());//格式化两位小数
        			if(fmtrMoney.toString().replace(".","").replace("-","").length>14){
        				fmtrMoney=0;
        			}
        		}
    			
    		}
    		
    		//金额验证错误
    		if(!fmtrMoney || isNaN(fmtrMoney) || parseFloat(fmtrMoney)==0){
    			isCheckVal=false;
    			$cell.find("div").text("");
    			if(columnName=="debit_name"){
	    			$($row[0].cells[13]).find("div").text("0.00");//借方金额
	    			
	    		}else if(columnName=="credit_name"){
	    			$($row[0].cells[14]).find("div").text("0.00");//贷方金额
	    			
	    		}else if(columnName=="budg_debit_name"){
	    			$($row[0].cells[21]).find("div").text("0.00");//借方金额
	    			
	    		}else if(columnName=="budg_credit_name"){
	    			$($row[0].cells[22]).find("div").text("0.00");//贷方金额
	    			
	    		}
    			
    		}else{
    			$cell.find("div").text(fmtrMoney.toString().replace(".","").replace("-",""));//金额显示列，去掉小数点，负号
    			if(columnName=="debit_name"){
    				
    				$($row[0].cells[6]).find("div").text("");//贷方金额
	    			$($row[0].cells[13]).find("div").text(fmtrMoney);//借方金额
	    			$($row[0].cells[14]).find("div").text("0.00");//贷方金额
	    			
	    		}else if(columnName=="credit_name"){
	    			
	    			$($row[0].cells[5]).find("div").text("");//借方金额
	    			$($row[0].cells[13]).find("div").text("0.00");//借方金额
	    			$($row[0].cells[14]).find("div").text(fmtrMoney);//贷方金额
	    			
	    		}else if(columnName=="budg_debit_name"){
	    			
	    			$($row[0].cells[20]).find("div").text("");//贷方金额
	    			$($row[0].cells[21]).find("div").text(fmtrMoney);//借方金额
	    			$($row[0].cells[22]).find("div").text("0.00");//贷方金额
	    			
	    		}else if(columnName=="budg_credit_name"){
	    			
	    			$($row[0].cells[19]).find("div").text("");//借方金额
	    			$($row[0].cells[21]).find("div").text("0.00");//借方金额
	    			$($row[0].cells[22]).find("div").text(fmtrMoney);//贷方金额
	    			
	    		}
    			setCheckCashMoney($rowData,fmtrMoney,columnName);
	    		customStyle($cell,fmtrMoney);
	    		
    		}
    		
    		if(is_budg==1 && $("#is_budg_check",parent.document).prop("checked") && $($row[0].cells[7]).find("div").text()!=""){
    			//分栏式，实时生成预算会计
    			var parm={
    				rowNumber: $row.index(),
    				subj_code: $($row[0].cells[7]).find("div").text()
    			};
    			parent.updateBudgData(parm);
    			
        	}
    		
    		sumDebitCredit();//更新合计
    		
    		
    	}else if(columnName=="subj_name" || columnName=="budg_subj_name"){
    		//科目校验、科目ID赋值
    		saveSubjId(isCheckVal,$row,$cell);
    		
    	}else if(columnName=="summary"){
    		
    		var summaryText=$($row[0].cells[3]).find("div").text();
    		if(parent.paraList["040"]==1 && summaryText!=""){
            	//040：分录辅助核算摘要同步
    			
    			//去掉辅助核算
    			if(summaryText.substring(0,1)=="[" && summaryText.indexOf("]")!=-1){
    				summaryText=summaryText.substring(summaryText.indexOf("]")+1,summaryText.length);
	    		}
    			
            	var jsonKeyAcc=$($row[0].cells[2]).find("div").text();//did
            	if(!jsonKeyAcc){
					jsonKeyAcc=$($row[0].cells[1]).find("div").text();//id
				}
            	
            	//财务会计辅助核算
				if(parent.vouchCheckJson[jsonKeyAcc]!=undefined && parent.vouchCheckJson[jsonKeyAcc].length>0){
					$.each(parent.vouchCheckJson[jsonKeyAcc],function(i,obj){
						parent.vouchCheckJson[jsonKeyAcc][i].summary=summaryText;
					});
				}
				
				//财务会计现金流量
				if(parent.vouchCashJson[jsonKeyAcc]!=undefined && parent.vouchCashJson[jsonKeyAcc].length>0){
					$.each(parent.vouchCashJson[jsonKeyAcc],function(i,obj){
						parent.vouchCashJson[jsonKeyAcc][i].summary=summaryText;
					});
				}
            	
            	//分栏式,预算摘要有数据不同步，说明财务会计与预算会计的业务不一样，所以摘要也不一样
				if(is_budg==1 && !$($row[0].cells[17]).find("div").text()){
					
					var jsonKeyBudg=$($row[0].cells[24]).find("div").text();//budg_did
					if(!jsonKeyBudg){
						jsonKeyBudg=$($row[0].cells[16]).find("div").text();//budg_id
					}
					
					//预算会计辅助核算
					if(parent.vouchCheckJson[jsonKeyBudg]!=undefined && parent.vouchCheckJson[jsonKeyBudg].length>0){
						$.each(parent.vouchCheckJson[jsonKeyBudg],function(i,obj){
							parent.vouchCheckJson[jsonKeyBudg][i].summary=summaryText;
						});
					}
					
					//预算会计现金流量
					if(parent.vouchCashJson[jsonKeyBudg]!=undefined && parent.vouchCashJson[jsonKeyBudg].length>0){
						$.each(parent.vouchCashJson[jsonKeyBudg],function(i,obj){
							parent.vouchCashJson[jsonKeyBudg][i].summary=summaryText;
						});
					}
					
				}
				
            }
    	}
	});
    
	//加载时执行方法
	grid.events.on("editor:load", function (data, $cell) {
		
		var $row=grid.getCellRow($cell);
		var columnName=$cell.data("column");
		if(columnName=="debit_name" || columnName=="credit_name" || columnName=="budg_debit_name" || columnName=="budg_credit_name"){
			//输入=号取差额，剔除自己
		//	console.log("editor:load-debit",$($row[0].cells[13]).find("div").text());
		//	console.log("editor:load-credit",$($row[0].cells[14]).find("div").text());
			if(columnName=="debit_name"){
				if(parseFloat($($row[0].cells[13]).find("div").text())!=0){
					preDiffMoney=parseFloat($($row[0].cells[13]).find("div").text());//借方金额
				}else{
					preDiffMoney=-parseFloat($($row[0].cells[14]).find("div").text());//贷方金额
				}
			}else if(columnName=="credit_name"){
				if(parseFloat($($row[0].cells[13]).find("div").text())!=0){
					preDiffMoney=-parseFloat($($row[0].cells[13]).find("div").text());//借方金额
				}else{
					preDiffMoney=parseFloat($($row[0].cells[14]).find("div").text());//贷方金额
				}
			}else if(columnName=="budg_debit_name"){
				if(parseFloat($($row[0].cells[21]).find("div").text())!=0){
					preDiffMoney=parseFloat($($row[0].cells[21]).find("div").text());//借方金额
				}else{
					preDiffMoney=-parseFloat($($row[0].cells[22]).find("div").text());//贷方金额
				}
			}else if(columnName=="budg_credit_name"){
				if(parseFloat($($row[0].cells[21]).find("div").text())!=0){
					preDiffMoney=-parseFloat($($row[0].cells[21]).find("div").text());//借方金额
				}else{
					preDiffMoney=parseFloat($($row[0].cells[22]).find("div").text());//贷方金额
				}
			}
		}
		
		if(is_budg==2 && columnName=="subj_name"){
			setSubjFlag($cell[0],$($row[0].cells[7]).find("div").text());
		}
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
	       // $('#vouchDiv').scrollTop( $('#vouchDiv')[0].scrollHeight );
	    });
	});

    // implement basic sorting
    grid.events.on("column:sort", function (col, order, $el) {
      //  console.info("column sort:", col, order, $el);
        var sorted = _.sortBy(parent.vouchJson.Rows, col);
        if (order === "desc") {
            sorted = sorted.reverse();
        }
        grid.updateData(sorted);
    });
    // render grid
    grid.render();
    
    //重置凭证状态，关闭窗口提示
    resetVouchStatus();
    parent.loadVouchDict();//加载凭证科目、摘要下拉列表
	
   
    //console.log("grid-html",grid);
  //  $("#test",parent.document).val(grid.html());
    
    // api examples
/*    var $row = grid.getRowByIndex(5);
    console.group("data api examples");
    console.log("grid.getRowDataByIndex(0):", grid.getRowDataByIndex(0));
    console.log("grid.getRowData($row):", grid.getRowData($row));
    console.log("grid.getCellDataByIndex(0, 1):", grid.getCellDataByIndex(0, 1));
    console.log("grid.getGridData():", grid.getGridData());
    console.groupEnd();*/
    
}


//负数红色字体、金额分隔符
function customStyle($td,fmtrMoney,div){
	if(parseFloat(fmtrMoney)<0){
		if(!$td.hasClass("redMoneyLine19")){
			$td.addClass("redMoneyLine19");//金额分隔符、红色字体
		}
	}else{
		if($td.hasClass("redMoneyLine19")){
			$td.removeClass("redMoneyLine19");//移除红色字体
		}
		if(!$td.hasClass("moneyLine19")){
			$td.addClass("moneyLine19");//金额分隔符
		}
	}
	
	if(fmtrMoney && parseFloat(fmtrMoney)!=0){
		var fmtrMoneyDis=fmtrMoney.replace("-","");
		if(parseFloat(fmtrMoneyDis)>0 && parseFloat(fmtrMoneyDis)<1){
			//金额大于0小于1
			if(div){
				//表格初始化，td还没有添加div
				div.text(fmtrMoneyDis.replace("0.",""));
			}else{
				$td.find("div").text(fmtrMoneyDis.replace("0.",""));
			}
		}else{
			if(div){
				div.text(fmtrMoneyDis.replace(".",""));
			}else{
				$td.find("div").text(fmtrMoneyDis.replace(".",""));
			}
		}
	}
	
}

//选择科目，保存ID列
function saveSubjId(isCheckVal,$row,$cell){
//	var oldSubjId=$($row[0].cells[7]).find("div").text();//记录老的科目ID
	var isSubjCheck=false;
	if(isCheckVal){
		
		if($cell.data("column")=="subj_name"){
			//var subjList=JSON.parse($("#subjList").text());
			if(is_budg==1){
				//分栏式
				//这里好像只能分开加if判断，然后再循环，grid.editorProps["subj_name"]好像有索引，多条件判断影响速度
				$.each(grid.editorProps["subj_name"], function (i, str) {
		   	 		if(str.kind_code=="01" && $cell.find("div").text()==str.id){
						$cell.find("div").text(str.name);
					}
					
		            if ($cell.find("div").text()==str.name) {
		            	isSubjCheck=true;
		            	$($row[0].cells[7]).find("div").text(str.id);//财务会计科目编码
		            	subjDivView(str,$cell);//科目根据规则显示优化
		            	return false;
		            }
		        });
			}else{
				//分屏式
				$.each(grid.editorProps["subj_name"], function (i, str) {
		   	 		if($cell.find("div").text()==str.id){
						$cell.find("div").text(str.name);
					}
					
		            if ($cell.find("div").text()==str.name) {
		            	isSubjCheck=true;
		            	$($row[0].cells[7]).find("div").text(str.id);//财务会计科目编码
		            	subjDivView(str,$cell);//科目根据规则显示优化
		            	return false;
		            }
		        });
			}
		}else{
			$.each(grid.editorProps["budg_subj_name"], function (i, str) {
	   	 		//科目：如果输入编码匹配的话，自动选中
	   	 		if(str.kind_code=='02' && $cell.find("div").text()==str.id){
	   	 			$cell.find("div").text(str.name);
	   	 		}
	   	 		
	            if ($cell.find("div").text()==str.name) {
	            	isSubjCheck=true;
	            	$($row[0].cells[23]).find("div").text(str.id);//科目编码
	            	subjDivView(str,$cell);//科目根据规则显示优化
	            	return false;
	            }
	        });
		}
		
		if(is_budg==2){
			setSubjFlag($cell[0],$($row[0].cells[7]).find("div").text());
		}
		
	}
	
	//输入的科目在下拉框里面不存在，清空
   	if(!isSubjCheck){
   		if($cell.data("column")=="subj_name"){
   			$($row[0].cells[4]).find("div").text("");//财务会计科目名称
   			$($row[0].cells[7]).find("div").text("");//财务会计科目编码
   		}else{
   			$($row[0].cells[18]).find("div").text("");//预算会计科目名称
   			$($row[0].cells[23]).find("div").text("");//预算会计科目编码
   		}
  
   	 	$cell.find("div").text("");
	   	if(is_budg==2){
	   		setSubjFlag($cell[0]);//清除标记
	   	}
   	}
   
}

/**
 * 科目显示优化，注意科目名称关键字‘-’
 * 039：科目显示规则：科目大于5级时倒数第2-3级中间以...显示
 * 如：6级：5级显示...，7级：5、6级显示...依次类推
 */
function subjDivView(subj,$cell){
	var subj_name=subj.subj_name_all?subj.subj_name_all:subj.name;
	if(parent.paraList["039"]==0){
		$cell.find("div").text(subj_name);
		return;
	}
	
	var show_level=5;
	if(parseInt(subj.subj_level)<=show_level){
		$cell.find("div").text(subj_name);
		return;
	}
	
	
	subj_name=subj_name.split(" ")[1].split("-");
	var subj_name_n="";
	var isDD=false;
	$.each(subj_name,function(s,n){
		if(s>show_level-2 && s<subj.subj_level-1){
			if(!isDD){
				isDD=true;//只拼一次
				subj_name_n=subj_name_n+"-...";
			}
		}else{
			subj_name_n=subj_name_n+"-"+n;
		}
		
	});
	
	$cell.find("div").text(subj.id+" "+subj_name_n.substring(1,subj_name_n.length));
}

//统计借方、贷方金额
var debitSum=0;
var creditSum=0;
var budgDebitSum=0;
var budgCreditSum=0;
function sumDebitCredit(){
	debitSum=0;
	creditSum=0;
	budgDebitSum=0;
	budgCreditSum=0;
	/*$.each(grid.getGridData(), function (i, str) {
		if(str.debit!=0 || str.credit!=0){
			debitSum+=parseFloat(str.debit);
			creditSum+=parseFloat(str.credit);
		}
	});*/
	
	debitSum=toDecimal(_.sum(grid.getGridData(),"debit"));//格式化两位小数借方
	creditSum=toDecimal(_.sum(grid.getGridData(),"credit"));//格式化两位小数贷方
	//console.log("creditSum",creditSum);
	//isNaN(debitSum)//判断数字
	
	if(debitSum==0){
		$("#debit_nameSum",parent.document).find("div").text("");//借方合计
	}else{
		$("#debit_nameSum",parent.document).find("div").text(formatNumber(debitSum.toString().replace("-",""),2,1));//借方合计
	}
	
	if(creditSum==0){
		$("#credit_nameSum",parent.document).find("div").text("");//贷方合计
	}else{
		$("#credit_nameSum",parent.document).find("div").text(formatNumber(creditSum.toString().replace("-",""),2,1));//贷方合计
	}
	
	customStyle($("#debit_nameSum",parent.document),debitSum);
	customStyle($("#credit_nameSum",parent.document),creditSum);
	
	
	//金额转大写
	if(debitSum!=0){
		$("#capital",parent.document).text(amountInWords(debitSum,2));
	}else if(creditSum!=0){
		$("#capital",parent.document).text(amountInWords(creditSum,2));
	}else{
		$("#capital",parent.document).text(amountInWords(0,0));
	}
	
	//显示借贷不平
	if(debitSum!=creditSum){
		
		if(debitSum>creditSum){
			$("#jdbpSpan",parent.document).text(formatNumber((debitSum-creditSum), 2, 1));
		}else{
			$("#jdbpSpan",parent.document).text(formatNumber((creditSum-debitSum), 2, 1));
		}
		
		$("#jdbpSpan",parent.document).parent().show();
	}else{
		$("#jdbpSpan",parent.document).parent().hide();
		$("#jdbpSpan",parent.document).text("");
	}
	
	if(is_budg==1){
		//预算会计科目
		budgDebitSum=toDecimal(_.sum(grid.getGridData(),"budg_debit"));//格式化两位小数借方
		budgCreditSum=toDecimal(_.sum(grid.getGridData(),"budg_credit"));//格式化两位小数贷方
		//console.log("creditSum",creditSum);
		//isNaN(debitSum)//判断数字
		
		if(budgDebitSum==0){
			$("#budg_debit_nameSum",parent.document).find("div").text("");//借方合计
		}else{
			$("#budg_debit_nameSum",parent.document).find("div").text(formatNumber(debitSum.toString().replace("-",""),2,1));//借方合计
		}
		
		if(budgCreditSum==0){
			$("#budg_credit_nameSum",parent.document).find("div").text("");//贷方合计
		}else{
			$("#budg_credit_nameSum",parent.document).find("div").text(formatNumber(creditSum.toString().replace("-",""),2,1));//贷方合计
		}
		
		customStyle($("#budg_debit_nameSum",parent.document),budgDebitSum);
		customStyle($("#budg_credit_nameSum",parent.document),budgCreditSum);
		
		//金额转大写
		if(budgDebitSum!=0){
			$("#budg_capital",parent.document).text(amountInWords(budgDebitSum,2));
		}else if(budgCreditSum!=0){
			$("#budg_capital",parent.document).text(amountInWords(budgCreditSum,2));
		}else{
			$("#budg_capital",parent.document).text(amountInWords(0,0));
		}
		
		
		//显示借贷不平
		if(budgDebitSum!=budgCreditSum){
			if(budgDebitSum>budgCreditSum){
				$("#budg_jdbpSpan",parent.document).text(formatNumber((budgDebitSum-budgCreditSum), 2, 1));
			}else{
				$("#budg_jdbpSpan",parent.document).text(formatNumber((budgCreditSum-budgDebitSum), 2, 1));
			}
			
			$("#budg_jdbpSpan",parent.document).parent().show();
		}else{
			$("#budg_jdbpSpan",parent.document).parent().hide();
			$("#budg_jdbpSpan",parent.document).text("");
		}
	}
	
	setSubjJsEndOs();//更新科目及余额
}

//计算即时余额
function setSubjJsEndOs(){
	
	$("#subjEndOsDIv",parent.document).text("");
	if(grid.config["readonly"] || jsSubjCode==null || $("#subjActiveCell",parent.document).text()==""){
		return;
	}
	
	var jsSubjEndOsNew=parseFloat(jsSubjEndOs);
	
	$.each(grid.getGridData(),function (n,obj) {
		
		if(isJsAccBudg){
			if(obj.budg_subj_code!=jsSubjCode){
				return true;
			}
			
			if(obj.budg_debit=="")obj.budg_debit=0;
			if(obj.budg_credit=="")obj.budg_credit=0;
			
			if($("#subjActiveCell",parent.document).text().indexOf("（借）：")!=-1){
				jsSubjEndOsNew=jsSubjEndOsNew+parseFloat(obj.budg_debit)-parseFloat(obj.budg_credit);
			}else{
				jsSubjEndOsNew=jsSubjEndOsNew-parseFloat(obj.budg_debit)+parseFloat(obj.budg_credit);
			}
		}else{
			if(obj.subj_code!=jsSubjCode){
				return true;
			}
			
			if(obj.debit=="")obj.debit=0;
			if(obj.credit=="")obj.credit=0;
			
			if($("#subjActiveCell",parent.document).text().indexOf("（借）：")!=-1){
				jsSubjEndOsNew=jsSubjEndOsNew+parseFloat(obj.debit)-parseFloat(obj.credit);
			}else{
				jsSubjEndOsNew=jsSubjEndOsNew-parseFloat(obj.debit)+parseFloat(obj.credit);
			}
		}
		
	});
	
	$("#subjEndOsDIv",parent.document).text("即时余额："+formatNumber(jsSubjEndOsNew,2,1));
}

//计算余额，刨掉当前凭证，为计算即时余额做铺垫
function initSubjJsEndOs(end_os,subj_code,subj_dire,isBudg){
	
	$("#subjEndOsDIv",parent.document).text("");
	if(parent.paraList["048"]==0 || grid.config["readonly"]){
		return;
	}
	
	jsSubjEndOs=parseFloat(end_os.replace(/\,/g,""));
	jsSubjCode=subj_code;
	isJsAccBudg=isBudg;
	
	
	if($("#vouchId",parent.document).val()==""){
		return;
	}
	
	$.each(grid.getGridData(),function (n,obj) {
		
		if(isJsAccBudg){
			
			if(obj.budg_subj_code!=subj_code || obj.budg_did==""){
				return true;
			}
			
			if(obj.budg_debit=="")obj.budg_debit=0;
			if(obj.budg_credit=="")obj.budg_credit=0;
			
			if(subj_dire=="（借）："){
				jsSubjEndOs=jsSubjEndOs-parseFloat(obj.budg_debit)+parseFloat(obj.budg_credit);
			}else{
				jsSubjEndOs=jsSubjEndOs+parseFloat(obj.budg_debit)-parseFloat(obj.budg_credit);
			}
		}else{
			
			if(obj.subj_code!=subj_code  || obj.did==""){
				return true;
			}
			
			if(obj.debit=="")obj.debit=0;
			if(obj.credit=="")obj.credit=0;
			
			if(subj_dire=="（借）："){
				jsSubjEndOs=jsSubjEndOs-parseFloat(obj.debit)+parseFloat(obj.credit);
			}else{
				jsSubjEndOs=jsSubjEndOs+parseFloat(obj.debit)-parseFloat(obj.credit);
			}
		}
		
	});
	
}

//=号取差额
var preDiffMoney=0;
function getDiffMoney(columnName){
	//console.log("dire",dire);
	if(isNaN(preDiffMoney)){
		preDiffMoney=0;
	}
	//console.log("preDiffMoney",preDiffMoney);
	//console.log("creditSum",creditSum);
	//console.log("debitSum",debitSum);
	if(columnName=="debit_name"){
		//借方差额
		return toDecimal(creditSum-debitSum+preDiffMoney);//格式化两位小数
	}else if(columnName=="credit_name"){
		//贷方差额
		return toDecimal(debitSum-creditSum+preDiffMoney);//格式化两位小数
	}else if(columnName=="budg_debit_name"){
		//借方差额
		return toDecimal(budgCreditSum-budgDebitSum+preDiffMoney);//格式化两位小数
	}else if(columnName=="budg_credit_name"){
		//借方差额
		return toDecimal(budgDebitSum-budgCreditSum+preDiffMoney);//格式化两位小数
	}
	
}

//如果辅助核算只有一条记录，根据分录金额修改辅助核算金额
function setCheckCashMoney($rowData,money,columnName){
	
	var jsonKey;
	if(columnName=="budg_debit_name" || columnName=="budg_credit_name"){
		jsonKey=$rowData.budg_did;
		if(!jsonKey){
			jsonKey=$rowData.budg_id;
		}
	}else if(columnName=="debit_name" || columnName=="credit_name"){
		jsonKey=$rowData.did;
		if(!jsonKey){
			jsonKey=$rowData.id;
		}
	}
	
	if(parent.vouchCashJson[jsonKey]!=undefined && parent.vouchCashJson[jsonKey].length==1){
		parent.vouchCashJson[jsonKey][0].money=money;
	}
	
	if(parent.vouchCheckJson[jsonKey]!=undefined && parent.vouchCheckJson[jsonKey].length==1){
		parent.vouchCheckJson[jsonKey][0].money=money;
	}
	
}


//查询分录
function queryVouchDetail(){
	
	//新建凭证
	if(parent.openType=="newVouch" && $("#vouchId",parent.document).val()!=""){

		//修改凭证，返回分录明细
		var acc_year=$("#vouch_date",parent.document).val().substring(0,4);
		if($("#vouch_date",parent.document).val())
		var param={vouch_id:$("#vouchId",parent.document).val(),acc_year:acc_year,is_budg:is_budg};
		ajaxJsonObjectByUrl("querySuperVouchByDetail.do?isCheck=false",param,function (responseData){
			if(responseData.state==false){
				parent.$.ligerDialog.error(responseData.msg);
				return;
			}
			parent.vouchJson.Rows=responseData.Rows;
			parent.vouchCheckJson=responseData.check==undefined?{}:responseData.check;
			parent.vouchCashJson=responseData.cash==undefined?{}:responseData.cash;
			parent.diffJson=responseData.diff==undefined?[]:responseData.diff;
			parent.setDiffCheck(parent.diffJson);
			
		},false);
		
	}else if(parent.openType=="autoVouch" && parent.vouchJson["auto_id"]===undefined){
		
		//老的自动凭证处理方式
		
	}else if(parent.openType=="autoVouch" && parent.vouchJson["auto_id"] && parent.vouchJson["auto_id"]!=""){
		//新的自动凭证处理方式
		var acc_year=$("#vouch_date",parent.document).val().substring(0,4);
		var param={auto_id: parent.vouchJson["auto_id"],
				busi_log_table: parent.vouchJson["busi_log_table"],
				busi_type_code : parent.vouchJson["busi_log_table"].substring(parent.vouchJson["busi_log_table"].length-6,parent.vouchJson["busi_log_table"].length),
				busi_no: parent.vouchJson["busi_no"],
				acc_year:acc_year,
				is_budg:is_budg};
		ajaxJsonObjectByUrl("queryAutoVouch.do?isCheck=false",param, function(responseData) {
			//parent.myGetTemplateVouchJson(null,responseData,"","");
			parent.vouchJson.Rows=responseData.Rows;
			parent.vouchCheckJson=responseData.check==undefined?{}:responseData.check;
			parent.vouchCashJson=responseData.cash==undefined?{}:responseData.cash;
			parent.setVouchTypeValue(responseData.vouch_type_code);
			parent.diffJson=responseData.diff==undefined?[]:responseData.diff;
			parent.setDiffCheck(parent.diffJson);
			//凭证主表
			$("#vouchId",parent.document).val("");
			$("#state",parent.document).val("1");
			$("#vouch_no",parent.document).val(responseData.vouch_no);
			$("#vouch_date",parent.document).val(responseData.vouch_date);
			$("#file_num",parent.document).val(responseData.att_num);
			$("#createUserId",parent.document).val(responseData.create_user);
			$("#create_name",parent.document).text("制单："+responseData.create_name);
			$("#audit_name",parent.document).text("审核："+responseData.audit_name);
			$("#cash_name",parent.document).text("出纳："+responseData.cash_name);
			$("#acc_name",parent.document).text("记账："+responseData.acc_name);
			parent.vouchJson["vouch_no"]=responseData.vouch_no;
			parent.vouchJson["busi_type_code"]=responseData.busi_type_code;
			parent.vouchJson["template_code"]=responseData.template_code;
			parent.vouchJson["auto_id"]=responseData.auto_id;
			parent.vouchJson["busi_no"]=responseData.busi_no;
			parent.vouchJson["busi_log_table"]=responseData.busi_log_table;
			parent.vouchJson["vouch_type_code"]=responseData.vouch_type_code;//记录凭证类型初始值，值改变需要变更凭证号
			parent.openType="newVouch";
		},false);
	}
	
}


//弹出辅助核算窗口
function openVouchCheck($activeCell,flag){

    var $row=grid.getCellRow($activeCell);
	var $rowData=grid.getRowData($row);
	if($rowData==null){
		return false;
	}
	var columnName=$activeCell.data("column");
	
	//按键弹出
	/*if(flag==1 && $activeCell.data("column")=="summary"){
		grid.setActiveCell($activeCell);
    	if(window.navigator.userAgent.indexOf("Chrome") >= 0){
    		grid.$el.focus();
        }else{
        	$activeCell.focus();
        }
    	grid.exitEditor(true);
		return false;//光标向右移动
	}else */if(columnName.indexOf("subj_name")==-1 && columnName.indexOf("debit_name")==-1 && columnName.indexOf("credit_name")==-1){
		return false;
	}
	
	/*
	 * 004:现金流量与辅助核算一起保存
	 * 032:凭证制单，银行科目默认弹出辅助核算窗口
	 * 026:凭证制单，默认弹出现金流量标注窗口
	 */
	var subjCode=$rowData.subj_code;
	var detailId=$rowData.did;
	var debit=$rowData.debit;
	var credit=$rowData.credit;
	var id=$rowData.id;
	if(columnName=="budg_subj_name" || columnName=="budg_debit_name" || columnName=="budg_credit_name"){
		subjCode=$rowData.budg_subj_code;
		detailId=$rowData.budg_did;
		debit=$rowData.budg_debit;
		credit=$rowData.budg_credit;
		id=$rowData.budg_id;
	}
	
	if(!subjCode){
		return false;
	}
	
	var subjAttr=getSubjAttr(subjCode);
	if(!subjAttr.id){
		return false;
	}
	
	var isCheck=subjAttr.is_check;
	if(subjAttr.is_bill==1){
		//是否票据核销，也弹出辅助核算窗口
		isCheck=1;
	}
	
	if(subjAttr.subj_nature_code=='03' &&  parent.paraList["032"]==1){
		//银行科目默认弹出辅助核算窗口
		isCheck=1;
	}
	var isShowCheck=false;
	if(parent.paraList["044"]==1 && flag==1){
		isShowCheck=true;
	}
	var jsonKey=detailId;
	if(!jsonKey){
		jsonKey=id;
	}
	
	if(isCheck==1){
		//辅助核算页面
		/*var title=subjAttr.name.split(" ")[1]+"（借）";
		if(subjAttr.subj_dire=="1"){
			title=subjAttr.name.split(" ")[1]+"（贷）";
		}*/
		//辅助核算大于两条记录，弹出窗口
		if(parent.vouchCheckJson[jsonKey]!=undefined && parent.vouchCheckJson[jsonKey].length>1)isShowCheck=false;
		
		if(isShowCheck && (columnName=="subj_name" || columnName=="budg_subj_name")){
			
			parent.showCheckDiv({
				rowData: $rowData,
				activeCell: $activeCell,
				jsonKey: jsonKey,
				subjAttr: subjAttr,
				type: 'check'
			});
			return true;
		}else if(isShowCheck){
			return false;
		}
		
		var vouch_id=$("#vouchId",parent.document).val();
		var state=$("#state",parent.document).val();
		var acc_year=$("#vouch_date",parent.document).val().substring(0,4);
		parent.$.ligerDialog.open({url : 'accVouchCheckPage.do?isCheck=false&id='+id+'&isReadOnly='+grid.config["readonly"]+
			'&rowNumber='+$row.index()+'&cellNumber='+$activeCell.index()+'&vouch_id='+vouch_id+'&detail_id='+detailId+
			'&state='+state+'&coulmn_name='+columnName+'&subj_code='+subjCode+'&debit='+debit+
			'&credit='+credit+'&acc_year='+acc_year+'&is_check='+subjAttr.is_check,
		data:{summary:$rowData.summary,grid:grid}, 
		//top: $(parent.window).height()-$(window).height()+40,
		height: $(parent.window).height()-100,width: $(parent.window).width()+10, title:'辅助核算',
		modal:true,showToggle:false,showMax:false,showMin: false,isResize:false,isDrag: false});
		parent.ktLayerObj.fn.close();
		return true;
	}else if(subjAttr.is_cash==1){
		//现金流量页面
		
		//现金流量大于两条记录，弹出窗口
		if(parent.paraList["026"]==0 || parent.vouchCashJson[jsonKey]!=undefined && parent.vouchCashJson[jsonKey].length>1)isShowCheck=false;
		
		if(isShowCheck && (columnName=="subj_name" || columnName=="budg_subj_name")){
			parent.showCheckDiv({
				rowData: $rowData,
				activeCell: $activeCell,
				jsonKey: jsonKey,
				subjAttr: subjAttr,
				type: 'cash'
			});
			return true;
		}else if(isShowCheck){
			return false;
		}
		
		if(flag==1  && parent.paraList["026"]==0)return false;
		openVouchCash($activeCell,flag,subjAttr);
		return true;
	}

	return false;//光标向右移动
}

//弹出现金流量标注窗口
function openVouchCash($activeCell,flag,subjAttr){
	var $row=grid.getCellRow($activeCell);
	var $rowData=grid.getRowData($row);
	if($rowData==null){
		return false;
	}
	
	var columnName=$activeCell.data("column");
	if(columnName.indexOf("subj_name")==-1 && columnName.indexOf("debit_name")==-1 && columnName.indexOf("credit_name")==-1){
		return false;
	}
	
	var subjCode=$rowData.subj_code;
	var detailId=$rowData.did;
	var debit=$rowData.debit;
	var credit=$rowData.credit;
	var id=$rowData.id;
	if(columnName=="budg_subj_name" || columnName=="budg_debit_name" || columnName=="budg_credit_name"){
		subjCode=$rowData.budg_subj_code;
		detailId=$rowData.budg_did;
		debit=$rowData.budg_debit;
		credit=$rowData.budg_credit;
		id=$rowData.budg_id;
	}
	
	if(!subjCode){
		return false;
	}
	
	if(!subjAttr){
		subjAttr=getSubjAttr(subjCode);
	}
	
	if(!subjAttr.id){
		return false;
	}
	
	if(subjAttr.is_cash==0){
		return false;
	}
	
	//按键，026=1凭证制单，默认弹出现金流量标注窗口
	if(flag==1 && parent.paraList["026"]=="0"){
		return false;
	}
	
	/*var title=subjAttr.name.split(" ")[1]+"（借）";
	if(subjAttr.subj_dire=="1"){
		title=subjAttr.name.split(" ")[1]+"（贷）";
	}*/
	
	var vouch_id=$("#vouchId",parent.document).val();
	var state=$("#state",parent.document).val();
	parent.$.ligerDialog.open({url : 'accVouchCashPage.do?isCheck=false&id='+id+'&isReadOnly='+grid.config["readonly"]+'&rowNumber='+$row.index()+
		'&cellNumber='+$activeCell.index()+'&vouch_id='+vouch_id+'&detail_id='+detailId+'&state='+state+'&coulmn_name='+$activeCell.data("column")+
		'&subj_code='+subjCode+'&debit='+debit+'&credit='+credit,
	data:{summary:$rowData.summary,grid:grid}, height: $(parent.window).height()-100,width: $(parent.window).width()+10, title:'现金流量',
	modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,isDrag: false});
	
	parent.ktLayerObj.fn.close();
	
	return true;
}

//点击分录事件
var lastDid=-1;
var jsSubjEndOs=0;
var jsSubjCode=null;
var isJsAccBudg=false;
function clickVouchDetailFun(flag,$activeCell){
	
	//console.log(flag,$($activeCell[0]).attr("class"));
	if($($activeCell[0]).attr("class")!="activeCell"){
		return;
	}
	
	var $row=grid.getCellRow($activeCell);
	var $rowData=grid.getRowData($row);
	var vouchCheckCashFrame=$('#vouchCheckCashFrame', parent.document);
	if($rowData==null){
		vouchCheckCashFrame.css("display","none");
		lastDid=-1;
		return;
	}
	
	var columnName=$activeCell.data("column");
	if(!columnName || (columnName.indexOf("subj_name")==-1 && columnName.indexOf("debit_name")==-1 && columnName.indexOf("credit_name")==-1)){
		vouchCheckCashFrame.css("display","none");
		lastDid=-1;
		return;
	}
	
	var subjCode=$rowData.subj_code;
	var detailId=$rowData.did;
	var debit=$rowData.debit;
	var credit=$rowData.credit;
	var id=$rowData.id;
	var isBudg=false;
	if(columnName=="budg_subj_name" || columnName=="budg_debit_name" || columnName=="budg_credit_name"){
		subjCode=$rowData.budg_subj_code;
		detailId=$rowData.budg_did;
		debit=$rowData.budg_debit;
		credit=$rowData.budg_credit;
		id=$rowData.budg_id;
		isBudg=true;
	}
	
	if(!subjCode || subjCode==""){
		vouchCheckCashFrame.css("display","none");
		lastDid=-1;
		return;
	}
	
	var subjAttr=getSubjAttr(subjCode);
	if(!subjAttr.id){
		return;
	}
	
	var subj_dire="（借）：";
	if(subjAttr.subj_dire=="1"){
		subj_dire="（贷）：";
	}
	
	//判断科目是否发生变化
	if($("#subjActiveCell",parent.document).text()!=subjAttr.name.split(" ")[1]+subj_dire){
		
		$("#subjActiveCell",parent.document).text(subjAttr.name.split(" ")[1]+subj_dire);
		//console.log($("#subjActiveCell",parent.document).text())
		//设置科目余额
		var para={
			vouch_date:parent.liger.get("vouch_date").getValue(),
			p_028:parent.paraList["028"],
			subj_code:subjAttr.id,
			subj_dire:subjAttr.subj_dire
		};
		ajaxJsonObjectByUrl("querySuperVouchSubjBalance.do?isCheck=false",para,function (responseData){
		    $("#subjBalanceDIv",parent.document).text("期初余额："+responseData.bal_os+" 本期借方："+responseData.this_od+" 本期贷方："+responseData.this_oc+" 期末余额："+responseData.end_os);
		    initSubjJsEndOs(responseData.end_os,subjCode,subj_dire,isBudg);
		    setSubjJsEndOs();//更新科目及余额
		});
		
	}
	//console.log(vouchCheckCashFrame.grid)
	
	//判断行是否发生变化
	if(lastDid!=detailId){
		//辅助核算frame
		vouchCheckCashFrame.css("display","block");
		var vouch_id=$("#vouchId",parent.document).val();
		var state=$("#state",parent.document).val();
		//$(window.parent.document).find("#vouchCheckCashFrame").attr("src","accVouchCheckPage.do?isCheck=false&id="+$rowData.id+"&isReadOnly=false");
		vouchCheckCashFrame.css("height",$(parent.window).height()/3);
		
		var isCheck=subjAttr.is_check;
		//票据号核销
		if(subjAttr.is_bill==1){
			isCheck=1;
		}
		
		if(isCheck==0 && subjAttr.subj_nature_code=='03' &&  parent.paraList["032"]==1){
			//银行科目默认弹出辅助核算窗口
			isCheck=1;
		}
		
		if(isCheck==1){
			//辅助核算页面
			if(parent.isOpenktLayer){
				parent.ktLayerObj.fn.open();
				parent.isOpenktLayer=false;
			}
			var acc_year=$("#vouch_date",parent.document).val().substring(0,4);
			vouchCheckCashFrame.attr("src",'accVouchCheckPage.do?isCheck=false&id='+id+'&isReadOnly=true&rowNumber='+$row.index()+
					'&cellNumber='+$activeCell.index()+'&vouch_id='+vouch_id+'&detail_id='+detailId+'&state='+state+'&acc_year='+acc_year+
					'&coulmn_name='+columnName+'&subj_code='+subjCode+'&debit='+debit+'&credit='+credit+'&is_check='+subjAttr.is_check);

		}else if(subjAttr.is_cash==1){
			//现金流量页面
			if(parent.isOpenktLayer){
				parent.ktLayerObj.fn.open();
				parent.isOpenktLayer=false;
			}
			vouchCheckCashFrame.attr("src",'accVouchCashPage.do?isCheck=false&id='+id+'&isReadOnly=true&rowNumber='+$row.index()+
					'&cellNumber='+$activeCell.index()+'&vouch_id='+vouch_id+'&detail_id='+detailId+'&state='+state+
					'&coulmn_name='+columnName+'&subj_code='+subjCode+'&debit='+debit+'&credit='+credit);
		}else{
			vouchCheckCashFrame.css("display","none");
			lastDid=-1;
		}
	}
	
	if(detailId){
		lastDid=detailId;
	}else{
		//新的分录没有ID,用行号+科目类别属性
		if(is_budg==1){
			//分栏式
			lastDid=$rowData.id+"-"+subjAttr.kind_code;
		}else{
			//全屏式
			lastDid=$rowData.id;
		}
	}
	
}

//重置页面状态，关闭窗口提示，表格渲染后执行
function resetVouchStatus(){
	lastDid=-1;
	
	//重置借贷金额合计
	sumDebitCredit();
	
	//重置按钮状态
	parent.setMyDisabled(getVouchFlowByData());
	
	//记录初始状态，关闭窗口提示
	 parent.vouchJson["row_size"]=grid.getRows().length;
	 //借贷方合计格式化
	 parent.vouchJson["debit_sum"]=$("#debit_nameSum",parent.document).find("div").text();
	 parent.vouchJson["credit_sum"]=$("#credit_nameSum",parent.document).find("div").text();
	 if(is_budg==1){
		 parent.vouchJson["budg_debit_sum"]=$("#budg_debit_nameSum",parent.document).find("div").text();
		 parent.vouchJson["budg_credit_sum"]=$("#budg_credit_nameSum",parent.document).find("div").text();
	 }
	 
	if(!grid.config["readonly"]){
    	//第一行摘要获取焦点
        var $row = grid.getRowByIndex(0);
        //$($row[0].cells[3]).focus();
        grid.setActiveCell($($row[0].cells[3]));
        //grid.$el.focus();
        grid.editCell(); 
	 }

}

//根据表格数据获取新的凭证审批流程
function getVouchFlowByData(){
	//判断列表是否包含现金、银行科目，重置审批流程
	var isCashBank=false;
	$.each(grid.getGridData(), function (i, obj) {
		var subjAttr=getSubjAttr(obj.subj_code);
		if(subjAttr.subj_nature_code=="02" || subjAttr.subj_nature_code=="03"){
			isCashBank=true;
			return false;
		}
	});
	
	//取出纳签字父节点
	var cashParentId=-1;
	$.each(parent.vouchFlow, function (i, flow) {
		if(flow.NODE_ID==2){
			cashParentId=flow.PARENT_NODE_ID;
			return false;
		}
	});
	
	//不是现金、银行科目或者没有出纳签字功能
	if(isCashBank || cashParentId==-1){
		return parent.vouchFlow;
	}
	
	var vouchFlowJson=[];
	$.each(parent.vouchFlow, function (i, flow) {
		//出纳签字节点
		if(flow.NODE_ID==2){
			return true;
		}
			
		//父节点=出纳签字
		if(flow.PARENT_NODE_ID==2){
			vouchFlowJson.push({"NODE_ID":flow.NODE_ID,"PARENT_NODE_ID":cashParentId});//取出纳签字的父节点
		}else{
			vouchFlowJson.push({"NODE_ID":flow.NODE_ID,"PARENT_NODE_ID":flow.PARENT_NODE_ID});
		}
			
	});
	
	return vouchFlowJson;
}


//选择科目树
function openSubjTree($activeCell,actVal){
	
	var kind_code="";
	if(is_budg==1){
		//分栏式
		var columnName=$activeCell.data("column");
		if(columnName=="subj_name"){
			kind_code="01";
		}else if(columnName=="budg_subj_name"){
			kind_code="02";
		}
	}
	
	parent.$.ligerDialog.open({url : 'subjTreePage.do?isCheck=false',
		data:{grid:grid,activeCell:$activeCell,activeCellVal:actVal,kind_code:kind_code,vouch_date: $("#vouch_date",parent.document).val()}, 
		height: 600,width: 600, title:'会计科目',modal:true,showToggle:false,showMax:false,showMin: false,isResize:false});
		
}

//金额运算
var verlis = /^[\d\+\-\*\/\.{0,1}\,{0,1}\={0,1}]+$/;
function evelMoney(){
	$(".sensei-grid-basic-editor").find("input").keydown(function(event) {	
		if (event.keyCode == '13') {
			var a = $(".sensei-grid-basic-editor input").val();
			if(a != " " && a != "=" && a.length != 0){
				if(verlis.test(a)){
					var sum = a.replace(/\,/g,"");
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
	})
};

//因为增加预算会计科目列，不再存表格里面了，改为按科目编码取科目属性
function getSubjAttr(subjCode){

	var attr={};
	if(parent.subjDict.length>0 && subjCode!=""){
		$.each(parent.subjDict, function (i, obj) {
			if(subjCode==obj.id){
				attr=obj;
				if(!attr.out_code)attr.out_code="";
				return false;
			}
		});
	}
	return attr
}