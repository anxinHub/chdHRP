function loadDict(){
	
	var initKindCode=Local.get("acc[vouch[kind_code");
	if(!initKindCode){
		//取客户端的缓存
		initKindCode="02";
	}
	$("#kind_code").ligerComboBox({
		data:[{ id: '01', text: '财务会计'},{ id: '02', text: '预算会计' }],
		valueField: 'id',
     	textField: 'text', 
	    cancelable: false,
	    autocomplete: false,
	    width: 85,
	    value: initKindCode,
	    onBeforeSelect: function (newvalue){
	    	if(newvalue==liger.get("kind_code").getValue()){
        		return false;
        	}
	    	/* if(vouchJson["row_size"]!=frameObj.grid.getRows().length || vouchJson["debit_sum"]!=$("#debit_nameSum").find("div").text() || vouchJson["credit_sum"]!=$("#credit_nameSum").find("div").text()
	    			|| vouchJson["budg_debit_sum"]!=$("#budg_debit_nameSum").find("div").text() || vouchJson["budg_credit_sum"]!=$("#budg_credit_nameSum").find("div").text()){
        		if(!confirm("数据改变，是否切换？")){
        			return false;
        		}
        	} */
	    },onSelected: function (selectValue){
	    	if(selectValue=="01"){
	    		dialog._setTitle("财务会计");
	    	}else{
	    		dialog._setTitle("预算会计");
	    	}
        	Local.set("acc[vouch[kind_code",selectValue);
        	location.reload(true);
    	}
        
	});
}

//初始化表格
var grid;
var page="vouchKind";
var moenyWith=141;
var kindCode;//01：财务会计、02：预算会计
var isAccHide=true;
var isBudgHide=true;
function loadVouchTable(){
	
	if(kindCode=="01"){
		isAccHide=false;
		dialog._setTitle("财务会计");
	}else{
		isBudgHide=false;
		dialog._setTitle("预算会计");
	}
	
    //全屏显示表格，所以要计算科目的宽度
	var mul=1;	
	var moneyWidthAll=moenyWith*2*mul;//金额列
	var subjWidth=parseInt(($(window).width()-15-moneyWidthAll)/(mul+1)+20);//科目列取剩余宽度的百分比+20
	var summaryWidth=$(window).width()-15-moneyWidthAll-subjWidth*mul;
	
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
        editorProps: [],wrap:true,hide:isAccHide,
        display:"摘要",width:summaryWidth+'px',align : 'left'//3
    }, {
        name: "subj_name",
        type: "string",
        editor: "AutocompleteEditor",
        //editor: "DisabledEditor",
        editorProps: [],wrap:true,hide:isAccHide,
        display:"财务会计科目",width: subjWidth+'px',align : 'left'//4
    }, {
        name: "debit_name",
        type: "number",hide:isAccHide,
        display:'借方金额<br/><span>千百十亿千百十万千百十元角分</span>',
       // display:'<span class="l-acc_test" align="right">千百十亿千百十万千百十元角分</span>',
        width : moenyWith+'px',align : 'right',//editor: "DisabledEditor",
        allowHTML: false//5
    }, {
        name: "credit_name",
        type: "number",hide:isAccHide,
        display:'贷方金额<br/><span>千百十亿千百十万千百十元角分</span>',
       // display:'<span class="l-acc_test" align="right">千百十亿千百十万千百十元角分</span>',
        width : moenyWith+'px',align : 'right',//editor: "DisabledEditor",
        allowHTML: false//6
    },{
        name: "subj_code",
        type: "string",
        editor:"DisabledEditor",hide:true,
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
        name: "int_did",
        type: "string",
        editor:"DisabledEditor",
        display: "int_did",align : 'left',hide:true//15停用
    },{
        name: "budg_id",
        type: "int",
        editor:"DisabledEditor",
        display: "budg_id",align : 'left',hide:true//16唯一预算会计辅助核算key
    },{
        name: "budg_summary",
        type: "string",
        editor: "AutocompleteEditor",
        editorProps: [],wrap:true,hide:isBudgHide,
        display:"摘要",width:summaryWidth+'px',align : 'left'//17
    }, {
        name: "budg_subj_name",
        type: "string",
        editor: "AutocompleteEditor",
        //editor: "DisabledEditor",
        editorProps: [],wrap:true,
        display:"预算会计科目",width: subjWidth+'px',align : 'left',hide: isBudgHide//18
    }, {
        name: "budg_debit_name",
        type: "number",
        display:'借方金额<br/><span>千百十亿千百十万千百十元角分</span>',
       // display:'<span class="l-acc_test" align="right">千百十亿千百十万千百十元角分</span>',
        width : moenyWith+'px',align : 'right', //editor:"DisabledEditor",
        allowHTML: false,hide: isBudgHide//19
    }, {
        name: "budg_credit_name",
        type: "number",
        display:'贷方金额<br/><span>千百十亿千百十万千百十元角分</span>',
       // display:'<span class="l-acc_test" align="right">千百十亿千百十万千百十元角分</span>',
        width : moenyWith+'px',align : 'right', //editor:"DisabledEditor",
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
    grid = $(".sensei-grid-default").grid(parent.frameObj.grid.getGridData(), columns, options);
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
    		sumDebitCredit();//更新合计
    		
    	}else if(columnName=="subj_name" || columnName=="budg_subj_name"){
    		//科目校验、科目ID赋值
    		saveSubjId(isCheckVal,$row,$cell);
    		
    	}else if(columnName=="summary" || columnName=="budg_summary"){
    		
    		var summaryIndex=3;
    		if(columnName=="budg_summary"){
    			summaryIndex=17;
    		}
    	
    		var summaryText=$($row[0].cells[summaryIndex]).find("div").text();
    		if(parent.paraList["040"]==1 && summaryText!=""){
            	//040：分录辅助核算摘要同步
    			
    			//去掉辅助核算
    			if(summaryText.substring(0,1)=="[" && summaryText.indexOf("]")!=-1){
    				summaryText=summaryText.substring(summaryText.indexOf("]")+1,summaryText.length);
	    		}
    			
            	if(kindCode=="01"){
            		jsonKeyAcc=$($row[0].cells[2]).find("div").text();//did
            		if(!jsonKeyAcc){
    					jsonKeyAcc=$($row[0].cells[1]).find("div").text();//id
    				}
            	}else{
            		jsonKeyAcc=$($row[0].cells[24]).find("div").text();//budg_did
            		if(!jsonKeyAcc){
    					jsonKeyAcc=$($row[0].cells[16]).find("div").text();//budg_id
    				}
            	}
				
				//辅助核算
				if(parent.kindCheckJson[jsonKeyAcc]!=undefined && parent.kindCheckJson[jsonKeyAcc].length>0){
					$.each(parent.kindCheckJson[jsonKeyAcc],function(i,obj){
						parent.kindCheckJson[jsonKeyAcc][i].summary=summaryText;
					});
				}
				
				//现金流量
				if(parent.kindCashJson[jsonKeyAcc]!=undefined && parent.kindCashJson[jsonKeyAcc].length>0){
					$.each(parent.kindCashJson[jsonKeyAcc],function(i,obj){
						parent.kindCashJson[jsonKeyAcc][i].summary=summaryText;
					});
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
		
		if(columnName=="subj_name"){
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
    if(parent.isReadOnly){
    	$("input[name='controllerButton']").ligerButton({disabled: true});
    	$("input[name='controllerButton']").attr("disabled",true);
    }else{
    	//表格下拉框渲染
    	loadVouchDict();//加载凭证科目、摘要下拉列表
        if(kindCode=="01"){
        	grid.editorProps["subj_name"] = parent.subjDict;
        	grid.editorProps["summary"] = parent.summaryDict;
        }else{
        	grid.editorProps["budg_subj_name"] = parent.subjDict;
        	grid.editorProps["budg_summary"] = parent.summaryDict;
        }
        
    }
    rowIndex=parseInt(parent.frameObj.rowIndex);
    //更新合计，记录开始状态
    resetVouchStatus();
   
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
			setSubjFlag($cell[0],$($row[0].cells[7]).find("div").text());
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
			setSubjFlag($cell[0],$($row[0].cells[23]).find("div").text());
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
   		setSubjFlag($cell[0]);//清除标记
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
	
	if(kindCode=="01"){
		debitSum=toDecimal(_.sum(grid.getGridData(),"debit"));//格式化两位小数借方
		creditSum=toDecimal(_.sum(grid.getGridData(),"credit"));//格式化两位小数贷方
		//console.log("creditSum",creditSum);
		//isNaN(debitSum)//判断数字
		
		if(debitSum==0){
			$("#debit_nameSum").find("div").text("");//借方合计
		}else{
			$("#debit_nameSum").find("div").text(formatNumber(debitSum.toString().replace("-",""),2,1));//借方合计
		}
		
		if(creditSum==0){
			$("#credit_nameSum").find("div").text("");//贷方合计
		}else{
			$("#credit_nameSum").find("div").text(formatNumber(creditSum.toString().replace("-",""),2,1));//贷方合计
		}
		
		customStyle($("#debit_nameSum"),debitSum);
		customStyle($("#credit_nameSum"),creditSum);
		
		
		//金额转大写
		if(debitSum!=0){
			$("#capital").text(amountInWords(debitSum,2));
		}else if(creditSum!=0){
			$("#capital").text(amountInWords(creditSum,2));
		}else{
			$("#capital").text(amountInWords(0,0));
		}
		
		//显示借贷不平
		if(debitSum!=creditSum){
			
			if(debitSum>creditSum){
				$("#jdbpSpan").text(formatNumber((debitSum-creditSum), 2, 1));
			}else{
				$("#jdbpSpan").text(formatNumber((creditSum-debitSum), 2, 1));
			}
			
			$("#jdbpSpan").parent().show();
		}else{
			$("#jdbpSpan").parent().hide();
			$("#jdbpSpan").text("");
		}
	}else{
		
		budgDebitSum=toDecimal(_.sum(grid.getGridData(),"budg_debit"));//格式化两位小数借方
		budgCreditSum=toDecimal(_.sum(grid.getGridData(),"budg_credit"));//格式化两位小数贷方
		//isNaN(debitSum)//判断数字
		if(budgDebitSum==0){
			$("#debit_nameSum").find("div").text("");//借方合计
		}else{
			$("#debit_nameSum").find("div").text(formatNumber(budgDebitSum.toString().replace("-",""),2,1));//借方合计
		}
		
		if(budgCreditSum==0){
			$("#credit_nameSum").find("div").text("");//贷方合计
		}else{
			$("#credit_nameSum").find("div").text(formatNumber(budgCreditSum.toString().replace("-",""),2,1));//贷方合计
		}
		
		customStyle($("#debit_nameSum"),budgDebitSum);
		customStyle($("#credit_nameSum"),budgCreditSum);
		
		
		//金额转大写
		if(budgDebitSum!=0){
			$("#capital").text(amountInWords(budgDebitSum,2));
		}else if(budgCreditSum!=0){
			$("#capital").text(amountInWords(budgCreditSum,2));
		}else{
			$("#capital").text(amountInWords(0,0));
		}
		
		//显示借贷不平
		if(budgDebitSum!=budgCreditSum){
			
			if(debitSum>creditSum){
				$("#jdbpSpan").text(formatNumber((budgDebitSum-budgCreditSum), 2, 1));
			}else{
				$("#jdbpSpan").text(formatNumber((budgCreditSum-budgDebitSum), 2, 1));
			}
			
			$("#jdbpSpan").parent().show();
		}else{
			$("#jdbpSpan").parent().hide();
			$("#jdbpSpan").text("");
		}
	}
	
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
	
	if(parent.kindCashJson[jsonKey]!=undefined && parent.kindCashJson[jsonKey].length==1){
		parent.kindCashJson[jsonKey][0].money=money;
	}
	
	if(parent.kindCheckJson[jsonKey]!=undefined && parent.kindCheckJson[jsonKey].length==1){
		parent.kindCheckJson[jsonKey][0].money=money;
	}
	
}

//加载凭证科目、摘要下拉列表
function loadVouchDict(){
	
	//加载科目
	if(parent.subjDict.length==0){
		var vouchDate=$("#vouch_date",parent.document).val();
		vouchDate=vouchDate.substring(0,4);//获取凭证日期的年度
		if(vouchDate!=""){
			ajaxJsonObjectByUrl("accVouchDictList.do?isCheck=false",{flag:1,acc_year: vouchDate},function (responseData){
				parent.subjDict=responseData;
		    },false);
		}
	}
	
	//加载摘要
	if(parent.summaryDict.length==0){
		ajaxJsonObjectByUrl("accVouchDictList.do?isCheck=false",{flag:2},function (responseData){
			parent.summaryDict=responseData;
		 },false);
	}
	
}


//弹出辅助核算窗口
var loadIndex=0;
function openVouchCheck($activeCell,flag){

    var $row=grid.getCellRow($activeCell);
	var $rowData=grid.getRowData($row);
	if($rowData==null){
		return false;
	}
	var columnName=$activeCell.data("column");
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
	var summary=$rowData.summary;
	if(kindCode=="02"){
		subjCode=$rowData.budg_subj_code;
		detailId=$rowData.budg_did;
		debit=$rowData.budg_debit;
		credit=$rowData.budg_credit;
		id=$rowData.budg_id;
		summary=$rowData.budg_summary;
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
		if(parent.kindCheckJson[jsonKey]!=undefined && parent.kindCheckJson[jsonKey].length>1)isShowCheck=false;
		
		if(isShowCheck && (columnName=="subj_name" || columnName=="budg_subj_name")){
			showCheckDiv({
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
		
		loadIndex = layer.load(1);
		parent.$.ligerDialog.open({url : 'accVouchCheckPage.do?isCheck=false&id='+id+'&isReadOnly='+grid.config["readonly"]+
			'&rowNumber='+$row.index()+'&cellNumber='+$activeCell.index()+'&vouch_id='+vouch_id+'&detail_id='+detailId+
			'&state='+state+'&coulmn_name='+columnName+'&subj_code='+subjCode+'&debit='+debit+
			'&credit='+credit+'&acc_year='+acc_year+'&is_check='+subjAttr.is_check,
		data:{summary:summary,grid:grid,page: page}, height: $(parent.window).height()-100,width: $(parent.window).width()+10, 
		title:'辅助核算',modal:true,showToggle:false,showMax:false,showMin: false,isResize:false,isDrag:true
		,onClose(){
			layer.close(loadIndex); 
			loadIndex=0;
		}});
		parent.ktLayerObj.fn.close();
		return true;
	}else if(subjAttr.is_cash==1){
		//现金流量页面
		
		//现金流量大于两条记录，弹出窗口
		if(parent.kindCashJson[jsonKey]!=undefined && parent.kindCashJson[jsonKey].length>1)isShowCheck=false;
		
		if(isShowCheck && (columnName=="subj_name" || columnName=="budg_subj_name")){
			
			showCheckDiv({
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
	var subjCode=$rowData.subj_code;
	var detailId=$rowData.did;
	var debit=$rowData.debit;
	var credit=$rowData.credit;
	var id=$rowData.id;
	var summary=$rowData.summary;
	if(kindCode=="02"){
		subjCode=$rowData.budg_subj_code;
		detailId=$rowData.budg_did;
		debit=$rowData.budg_debit;
		credit=$rowData.budg_credit;
		id=$rowData.budg_id;
		summary=$rowData.budg_summary;
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
	
	loadIndex = layer.load(1);
	parent.$.ligerDialog.open({url : 'accVouchCashPage.do?isCheck=false&id='+id+'&isReadOnly='+grid.config["readonly"]+'&rowNumber='+$row.index()+
		'&cellNumber='+$activeCell.index()+'&vouch_id='+vouch_id+'&detail_id='+detailId+'&state='+state+'&coulmn_name='+$activeCell.data("column")+
		'&subj_code='+subjCode+'&debit='+debit+'&credit='+credit,
	data:{summary:summary,grid:grid,page: page}, height: $(parent.window).height()-100,width: $(parent.window).width()-10, 
	title:'现金流量',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,isDrag:false
	,onClose(){
		layer.close(loadIndex);
		loadIndex=0;
	}});
	parent.ktLayerObj.fn.close();
	return true;
}

//点击分录事件
var lastSubjId=-1;
function clickVouchDetailFun(flag,$activeCell){
	
	var $row=grid.getCellRow($activeCell);
	var $rowData=grid.getRowData($row);
	if($rowData==null){
		return;
	}
	
	var subjCode=$rowData.subj_code;
	if(kindCode=="02"){
		subjCode=$rowData.budg_subj_code;
	}
	
	if(!subjCode){
		return;
	}
	
	var subjAttr=getSubjAttr(subjCode);
	if(!subjAttr.id){
		return;
	}
	
	//判断科目是否发生变化
	if(lastSubjId!=subjAttr.id){
		//设置科目余额
		var para={
			vouch_date:parent.liger.get("vouch_date").getValue(),
			p_028:parent.paraList["028"],
			subj_code:subjAttr.id,
			subj_dire:subjAttr.subj_dire
		};
		ajaxJsonObjectByUrl("querySuperVouchSubjBalance.do?isCheck=false",para,function (responseData){
			
			var subj_dire="（借）";
			if(subjAttr.subj_dire=="1"){
				subj_dire="（贷）";
			}
			
			var title="财务会计";
			if(kindCode=="02"){
				title="预算会计";
			}
			title=title+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;【"+subjAttr.name.split(" ")[1]+subj_dire+"】";
			title=title+"期初余额："+responseData.bal_os+" 本期借方："+responseData.this_od+" 本期贷方："+responseData.this_oc+" 期末余额："+responseData.end_os;
			dialog._setTitle(title);
			lastSubjId=subjAttr.id;
		});
		
	}
	
}



//选择科目树
function openSubjTree($activeCell,actVal){
	
	$.ligerDialog.open({url : 'subjTreePage.do?isCheck=false',
		data:{grid:grid,activeCell:$activeCell,activeCellVal:actVal,kind_code:kindCode,vouch_date: $("#vouch_date",parent.document).val()}, 
		height: 600,width: 600, title:'会计科目',modal:true,showToggle:false,showMax:false,showMin: false,isResize:false});
		
}

//弹出科目窗口，赋值
function setActiveCellSubj(node,rowNumber,cellNumber,oldSubjCode){
	
	var $row=grid.getRowByIndex(rowNumber);
	//var $activeCell=$($row[0].cells[cellNumber]);
	
	if(cellNumber==4){
		//财务会计科目
		//$($row[0].cells[4]).find("div").text(node.subj_name_all);//科目全称
		subjDivView(node,$($row[0].cells[4]));//科目全称
		$($row[0].cells[7]).find("div").text(node.subj_code);//科目编码
	}else{
		//$($row[0].cells[18]).find("div").text(node.subj_name_all);//科目全称
		subjDivView(node,$($row[0].cells[18]));//科目全称
		$($row[0].cells[23]).find("div").text(node.subj_code);//科目编码
		
	}
	/*if(oldSubjCode!=node.subj_code){
   		if($($row[0].cells[2]).find("div").text()!=""){
   	   		//修改凭证，如果科目发生改变，分录ID清空，重新保存辅助核算
   	   	 	$($row[0].cells[15]).find("div").text($($row[0].cells[2]).find("div").text());//保存初始化分录ID
   	   		$($row[0].cells[2]).find("div").text("");//分录ID
   	   	}
   	}*/
}

//金额运算
var verlis = /^[\d\+\-\*\/\.{0,1}\,{0,1}\={0,1}]+$/;
function evelMoney(){
	$(".sensei-grid-basic-editor").find("input").keydown(function(event) {	
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
	})
};



//重置页面状态，关闭窗口提示，表格渲染后执行
function resetVouchStatus(){
	
	//重置借贷金额合计
	sumDebitCredit();
	 
	if(!grid.config["readonly"]){
    	//第一行摘要获取焦点
        var $row = grid.getRowByIndex(0);
        //$($row[0].cells[3]).focus();
        if(kindCode=="01"){
        	grid.setActiveCell($($row[0].cells[3]));
        }else{
        	grid.setActiveCell($($row[0].cells[17]));
        }
        //grid.$el.focus();
        grid.editCell(); 
	}
	initRowSize=grid.getRows().length;
}

//因为增加预算会计科目列，不再存表格里面了，改为按科目编码取科目属性
function getSubjAttr(subjCode){
	
	var attr={};
	if(parent.subjDict.length>0){
		$.each(parent.subjDict, function (i, obj) {
			if(subjCode==obj.id){
				attr=obj;
				return false;
			}
		});
	}
	return attr
}

//弹出辅助核算、现金流量窗口，保存后回调事件
function callSaveVouchCheck(parm){
	
	var $row=grid.getRowByIndex(parm.rowNumber);
	var $activeCell=$($row[0].cells[parm.cellNumber]);
	if(parm.flag=="save"){
    	
    	if(parm.cellNumber==4 || parm.cellNumber==5 || parm.cellNumber==6){
    		//全屏式或者财务会计科目列
    		$($row[0].cells[5]).find("div").text(parseFloat(parm.debit)==0?"":parm.debit);//借方金额
    		$($row[0].cells[6]).find("div").text(parseFloat(parm.credit)==0?"":parm.credit);//贷方金额
    		$($row[0].cells[13]).find("div").text(parm.debit);//借方金额
    		$($row[0].cells[14]).find("div").text(parm.credit);//贷方金额
    		customStyle($($row[0].cells[5]),parm.debit);//借方金额样式
    		customStyle($($row[0].cells[6]),parm.credit);//贷方金额样式
    		/* frameObj.grid.move("right");
    		frameObj.grid.move("right");
    		frameObj.grid.move("right");
    		frameObj.grid.hideMove("right"); */
    	}else{
    		$($row[0].cells[19]).find("div").text(parseFloat(parm.debit)==0?"":parm.debit);//借方金额
    		$($row[0].cells[20]).find("div").text(parseFloat(parm.credit)==0?"":parm.credit);//贷方金额
    		$($row[0].cells[21]).find("div").text(parm.debit);//借方金额
    		$($row[0].cells[22]).find("div").text(parm.credit);//贷方金额
    		customStyle($($row[0].cells[19]),parm.debit);//借方金额样式
    		customStyle($($row[0].cells[20]),parm.credit);//贷方金额样式
    	}
    	
    	//计算合计
    	sumDebitCredit();
    	
        if(parent.paraList["040"]==1 || parm.summary && $($row[0].cells[3]).find("div").text()==""){
        	//040：分录辅助核算摘要同步，或者分录没有摘要，辅助核算的摘要赋给分录
        	$($row[0].cells[3]).find("div").text(parm.summary);
        }
        
        //拼接分录的摘要：[辅助核算内容]摘要
    	if(parent.paraList["036"]==1 && parm.checkItemVal!=undefined && parm.checkItemVal!=""){
    		parm.checkItemVal=parm.checkItemVal.split(" ")[1];
    		var cellsText3=$($row[0].cells[3]).find("div").text();
    		if(cellsText3!="" && cellsText3.indexOf("[")!=-1 && cellsText3.indexOf("]")!=-1){
    			cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
    		}
    		$($row[0].cells[3]).find("div").text("["+parm.checkItemVal+"]"+cellsText3);//摘要+辅助核算页面传过来的摘要	
    	}
    	
    	var $nextRow = $activeCell.parent("tr").next();//下一行
    	var $nextCell=$($nextRow[0].cells[3]);
       	grid.setActiveCell($nextCell);//下一行焦点
       	
    	if(window.navigator.userAgent.indexOf("Chrome") >= 0){
    		grid.$el.focus();
    	}else{
    		$($nextRow[0].cells[3]).focus();
    	}
    	
    }else{
    	grid.setActiveCell($activeCell);//单元格获取焦点
		if(window.navigator.userAgent.indexOf("Chrome") >= 0){
			grid.$el.focus();
    	}else{
    		$activeCell.focus();
    	}
		
    }
	
	//frameObj.grid.exitEditor(true);
	setTimeout(function (){
		grid.editCell(); 
	},100);
	
}

//删除分录
function myDelDetail(){
	if($("input[name='controllerButton']").attr("disabled")=="disabled"){		
		//按钮置灰状态下返回
		return;
	}
	
	
	if(grid.getSelectedRows().length>1){
		$.ligerDialog.confirm("确定要删除吗？", function(yes) {
			if(yes) {
				grid.removeActiveRow();
				sumDebitCredit();//更新合计
			}
		});
	}else{
		grid.removeActiveRow();
		sumDebitCredit();//更新合计
		grid.assureEmptyRow();//插入空行
	
	}

}

//辅助核算
function myCheck(){
	//selectable
	var $activeCell =grid.getActiveCell();
	$("#checkDiv").hide();
	openVouchCheck($activeCell,2);
}

//现金流量
function myCash(){
	//selectable
	var $activeCell =grid.getActiveCell();
	$("#checkDiv").hide();
	if(parent.paraList["004"]==1){
		//现金流量与辅助核算一起保存
		openVouchCheck($activeCell,2);
	}else{
		openVouchCash($activeCell,2);	
	}
	
}

function mySave(){
	if($("input[name='controllerButton']").attr("disabled")=="disabled"){		
		//按钮置灰状态下返回
		return;
	} 
	grid.saveEditor(true);//光标离开保存数据
	var frameGrid=parent.frameObj.grid;
	$.each(grid.getGridData(),function (n,obj) {
		//借贷金额为空不处理
		if(obj.debit=="")obj.debit=0;
		if(obj.credit=="")obj.credit=0;
		if(obj.budg_debit=="")obj.budg_debit=0;
		if(obj.budg_credit=="")obj.budg_credit=0;
		
		if(obj.summary=="" && obj.subj_code=="" && obj.budg_subj_code==""){
			return true;
		}
		
	/*	if(obj.summary=="" && (obj.subj_code!="" || obj.budg_subj_code!="")){
			isSubmit=false;
			$.ligerDialog.error((obj.subj_code?obj.subj_code:obj.budg_subj_code)+"，摘要不能为空.");
			return false;
		}else if(obj.summary!="" && obj.subj_code=="" && obj.budg_subj_code==""){
			isSubmit=false;
			$.ligerDialog.error(obj.summary+"，科目不能为空.");
			return false;
		} */
		
		var $row=frameGrid.getRowByIndex(n);
		if(!$row || !$row[0]){
			//没有足够的空行
			frameGrid.assureEmptyRow();
			$row=frameGrid.getRowByIndex(n);
		}
		
		
		if(kindCode=="01"){
			//财务会计
			var subjName=obj.subj_name;
			if(subjName!=""){
				subjName=subjName.substring(0,subjName.length-1);//去掉科目标记
			}
			$($row[0].cells[1]).html($("<div>"));
			$($row[0].cells[1]).find("div").text(obj.id);
			$($row[0].cells[2]).find("div").text(obj.did);
			$($row[0].cells[3]).find("div").text(obj.summary);
			$($row[0].cells[4]).find("div").text(subjName);
			$($row[0].cells[5]).find("div").text(obj.debit_name);
			$($row[0].cells[6]).find("div").text(obj.credit_name);
			$($row[0].cells[7]).find("div").text(obj.subj_code);
			$($row[0].cells[13]).find("div").text(obj.debit);
			$($row[0].cells[14]).find("div").text(obj.credit);
			
		}else{
			//预算会计
			var subjName=obj.budg_subj_name;
			if(subjName!=""){
				subjName=subjName.substring(0,subjName.length-1);//去掉科目标记
			}
			if(obj.summary!="" && obj.summary!=obj.budg_summary){
				$($row[0].cells[17]).find("div").text(obj.budg_summary);
			}
			$($row[0].cells[16]).html($("<div>"));
			$($row[0].cells[16]).find("div").text(obj.budg_id);
			$($row[0].cells[18]).find("div").text(subjName);
			$($row[0].cells[19]).find("div").text(obj.budg_debit_name);
			$($row[0].cells[20]).find("div").text(obj.budg_credit_name);
			$($row[0].cells[21]).find("div").text(obj.budg_debit);
			$($row[0].cells[22]).find("div").text(obj.budg_credit);
			$($row[0].cells[23]).find("div").text(obj.budg_subj_code);
			$($row[0].cells[24]).find("div").text(obj.budg_did);
		}
		
		$row.find(">td.selectable").html($("<input type=checkbox>"));
		frameGrid.setRowSaved($row);
		/*rowData.push({id:obj.id,did:obj.did,summary:obj.summary,subj_code:obj.subj_code,subj_name:subjName,debit:obj.debit,credit:obj.credit,debit_name:obj.debit_name,credit_name:obj.credit_name,
			budg_did:obj.budg_did,budg_summary:summary,budg_subj_code:obj.budg_subj_code,budg_subj_name:budgSubjName,budg_debit:obj.budg_debit,budg_credit:obj.budg_credit,budg_debit_name:obj.budg_debit_name,budg_credit_name:obj.budg_credit_name});
		*/
	});
	
	
	//parent.vouchJson.Rows=JSON.parse(JSON.stringify(rowData));
	//表格渲染
	//parent.frameObj.grid.renderData(rowData);
	//辅助核算
	parent.vouchCheckJson=JSON.parse(JSON.stringify(parent.kindCheckJson));
	parent.vouchCashJson=JSON.parse(JSON.stringify(parent.kindCashJson));
	//重置行号
	parent.frameObj.rowIndex=rowIndex;
	
	//重置凭证状态，并且关闭窗口提示
	parent.frameObj.resetVouchStatus();
	parent.vouchJson["row_size"]=0;
	parent.frameObj.grid.bindEvents();
	parent.frameObj.window.scrollTo(0,0);
	parent.kindCheckJson=null;
	parent.kindCashJson=null;
 	setTimeout(function() {
		dialog.close();
	}, 50);
}

//复制
var copyRowIndex;
var copyColumnName="";
function copyData(){
	copyRowIndex=0;
	
	var $activeCell =grid.getActiveCell();
	if(!$activeCell){
		return;
	}
	
	var $row=grid.getCellRow($activeCell);
	copyRowIndex=$row.index();
	copyColumnName=$activeCell.data("column");
}


/**
 * 粘贴
 */
function pasteData(){
	var $activeCell =grid.getActiveCell();
	if(!$activeCell || copyColumnName==""){
		return;
	}
	
	var $row=grid.getCellRow($activeCell);
	var $rowDataNew=grid.getRowData($row);//新行
	var $rowDataOld=grid.getRowDataByIndex(copyRowIndex);//旧行
	if($row.index()==copyRowIndex){
		return;
	}
	
	if(kindCode=="01"){
		/**
		 * 财务会计
		 */
		var subj_name=$rowDataOld.subj_name;
		if(subj_name!=""){
			subj_name=subj_name.substring(0,subj_name.length-1);
			if($rowDataOld.subj_code!=""){
				//分屏式有预、财标记，需要加载样式
				setSubjFlag($row[0].cells[4],$rowDataOld.subj_code);
			}
		}
		$($row[0].cells[4]).find("div").text(subj_name);//科目名称
		$($row[0].cells[5]).find("div").text($rowDataOld.debit_name);//借
		$($row[0].cells[6]).find("div").text($rowDataOld.credit_name);//贷
		$($row[0].cells[7]).find("div").text($rowDataOld.subj_code);//科目编码
		$($row[0].cells[13]).find("div").text($rowDataOld.debit);//借
		$($row[0].cells[14]).find("div").text($rowDataOld.credit);//贷
		customStyle($($row[0].cells[5]),$rowDataOld.debit);//借方金额样式
		customStyle($($row[0].cells[6]),$rowDataOld.credit);//贷方金额样式
		
		var jsonKey=$rowDataOld.did;
		if(!jsonKey){
			jsonKey=$rowDataOld.id;
		}
		//辅助核算
		if(parent.kindCheckJson[jsonKey]){
			//复制数据需要转换，否则会影响vouch.js这个方法setCheckCashMoney
			parent.kindCheckJson[$rowDataNew.id] = JSON.parse(JSON.stringify(parent.kindCheckJson[jsonKey]));
		}else{
			delete parent.kindCheckJson[$rowDataNew.id];
		}

		//现金流量
		if(parent.kindCashJson[jsonKey]){
			parent.kindCashJson[$rowDataNew.id]= JSON.parse(JSON.stringify(parent.kindCashJson[jsonKey]));
		}else{
			delete parent.kindCashJson[$rowDataNew.id];
		}
		
		//摘要
		var summary=$rowDataOld.summary;
		if(summary!=""){
			
			if(summary.indexOf("[")!=-1 && summary.indexOf("]")!=-1 && summary.substring(0,1)=="["){
				summary=summary.substring(summary.indexOf("]")+1,summary.length);
			}
			$($row[0].cells[3]).find("div").text(summary);//摘要
		}
		
	}
	
	if(kindCode=="02"){
		/**
		 * 预算会计
		 */
		$($row[0].cells[17]).find("div").text($rowDataOld.budg_summary);//摘要
		$($row[0].cells[18]).find("div").text($rowDataOld.budg_subj_name);//科目名称
		$($row[0].cells[19]).find("div").text($rowDataOld.budg_debit_name);//借
		$($row[0].cells[20]).find("div").text($rowDataOld.budg_credit_name);//贷
		$($row[0].cells[21]).find("div").text($rowDataOld.budg_debit);//借
		$($row[0].cells[22]).find("div").text($rowDataOld.budg_credit);//贷
		$($row[0].cells[23]).find("div").text($rowDataOld.budg_subj_code);//科目编码
		customStyle($($row[0].cells[19]),$rowDataOld.budg_debit);//借方金额样式
		customStyle($($row[0].cells[20]),$rowDataOld.budg_credit);//贷方金额样式
		
		jsonKey=$rowDataOld.budg_did;
		if(!jsonKey){
			jsonKey=$rowDataOld.budg_id;
		}
		//辅助核算
		if(parent.kindCheckJson[jsonKey]){
			parent.kindCheckJson[$rowDataNew.budg_id] = JSON.parse(JSON.stringify(parent.kindCheckJson[jsonKey]));
		}else{
			delete parent.kindCheckJson[$rowDataNew.budg_id];
		}
	
		//现金流量
		if(parent.kindCashJson[jsonKey]){
			parent.kindCashJson[$rowDataNew.budg_id]= JSON.parse(JSON.stringify(parent.kindCashJson[jsonKey]));
		}else{
			delete parent.kindCashJson[$rowDataNew.budg_id];
		}
		
		//摘要
		var summary=$rowDataOld.budg_summary;
		if(summary!=""){
			
			if(summary.indexOf("[")!=-1 && summary.indexOf("]")!=-1 && summary.substring(0,1)=="["){
				summary=summary.substring(summary.indexOf("]")+1,summary.length);
			}
			$($row[0].cells[17]).find("div").text(summary);//摘要
		}
	}
		
	sumDebitCredit();//更新合计
	$row.find(">td.selectable").html($("<input type=checkbox>"));
}



//删除行数据
function myRemoveRow(){
	var $activeCell =grid.getActiveCell();
	if(!$activeCell){
		return;
	}
	
    var $row=grid.getCellRow($activeCell);
	var $rowData=grid.getRowData($row);
	var jsonKey;
	
	if(kindCode=="01"){
		//财务会计
		jsonKey=$rowData.did;
		if(!jsonKey){
			jsonKey=$rowData.id;
		}
		$($row[0].cells[2]).find("div").text("");//did
		$($row[0].cells[3]).find("div").text("");//财务会计摘要
		$($row[0].cells[4]).find("div").text("");//科目名称
		$($row[0].cells[5]).find("div").text("");//借
		$($row[0].cells[6]).find("div").text("");//贷
		$($row[0].cells[7]).find("div").text("");//科目编码
		$($row[0].cells[13]).find("div").text("0.00");//借
		$($row[0].cells[14]).find("div").text("0.00");//贷
		setSubjFlag($row[0].cells[4]);//清除标记
	}else{
		//预算会计
		jsonKey=$rowData.budg_did;
		if(!jsonKey){
			jsonKey=$rowData.budg_id;
		}
		$($row[0].cells[17]).find("div").text("");//预算会计摘要		
		$($row[0].cells[18]).find("div").text("");//科目名称
		$($row[0].cells[19]).find("div").text("");//借
		$($row[0].cells[20]).find("div").text("");//贷
		$($row[0].cells[21]).find("div").text("0.00");//借
		$($row[0].cells[22]).find("div").text("0.00");//贷
		$($row[0].cells[23]).find("div").text("");//科目编码
		$($row[0].cells[24]).find("div").text("");//budg_did
		setSubjFlag($row[0].cells[18]);//清除标记
	}
	
	//删除辅助核算
	if(parent.kindCheckJson[jsonKey]){
		delete parent.kindCheckJson[jsonKey];
	}
	
	//删除现金流量
	if(parent.kindCashJson[jsonKey]){
		delete parent.kindCashJson[jsonKey];
	}
	
	sumDebitCredit();//更新合计
	
}


function changeDire(){
		
	var kind_code = liger.get("kind_code").getValue();
	if(kind_code == "01"){
		liger.get("kind_code").setValue("02");
		liger.get("kind_code").setText("预算会计");
	}else{
		liger.get("kind_code").setValue("01");
		liger.get("kind_code").setText("财务会计");
	}
	
	//设置焦点
	var $activeCell=grid.getActiveCell();
	if(!$activeCell[0]){
		return;
	}
	
    grid.setActiveCell($activeCell);
    //grid.$el.focus();
    grid.editCell();
}


/**********************右键菜单处理********begin*****************************/
function initMenu(){
	
	var menuData=[];
	menuData.push([{
        text: "render",
        func: function () {}
    }]);
	
	//分屏式
	menuData.push([{
        text: "复制",
        func: function (event) {
        	copyData();
		 }
    },
    {
        text: "粘贴",
        func: function (event) {
        	pasteData();
        }
    }]);
	
	
	menuData.push([{
        text: "插入",
        func: function (event) {
       	 	grid.insertActiveRow();
       }
    },{
        text: "删除",
        data: [[{
	        text: "删除行",
	        func: function (event) {
	        	myDelDetail();
	       }
	    },{
	        text: "清除行数据",
	        func: function (event) {
	        	myRemoveRow();//删除数据不删除行
	       }
	    }]]
    }]);
	
	menuData.push([{
        text: "存摘要",
        func: function (event) {
       	 //var rownum=$(event.path[2]).find('li:first > a').text().replace("行号：","");
	       	var $activeCell =grid.getActiveCell();
	    	if(!$activeCell){
	    		return;
	    	}
	    	var $row=grid.getCellRow($activeCell);
	    	var summary;
	    	if(kindCode=="01"){
	    		summary=$($row[0].cells[3]).find("div").text();
	    	}else{
	    		summary=$($row[0].cells[17]).find("div").text();
	    	}
    	    
	    	parent.mySaveSummary(summary,{grid: grid,kindCode: kindCode});
	    	
       }
    }]);
	
	
	menuData.push([{
        text: "其他",
        data: [[{
            text: "摘要管理",
            func: function () {parent.myGetSummary();}
        },{
            text: "辅助核算",
            func: function () {parent.checkGrid();}
        },{
	        text: "快捷键说明",
	        func: function (event) {
	        	parent.myHelp();
	       }
	    }]]
    }]);

	$("#vouchDiv").smartMenu(menuData,{
        name:"rightMenu",
        offsetX:2,
        offsetY:2,
        textLimit:6,
        topsetX:0,
        beforeShow:function(e) {
        	$("#checkDiv").hide();
        	return readRowNum(e);
        },
        afterShow:function() {

        }
    });
	
}

//加载行号
function readRowNum(e){
	var textNew = "";
	var event = event||window.event;//iframe对象
	//  根据鼠标所点击的元素的父元素是否为thead来判断是否为表头
	if($(event.srcElement).parents('thead')[0]){   
		textNew = "行号：0";
	}else if($(event.srcElement).parents('tr','.sensei-grid-tbody')[0]){
		//   取出父元素tr的索引值
		var RowNum = $(event.srcElement).parents('tr','.sensei-grid-tbody').index() + 1;
		textNew = "行号：" + RowNum;
	}else if(grid.activeEditor){  //  若点击是编辑框，则取出.activeCell类td的父元素tr索引值
		var $activeCell =grid.getActiveCell();
		var RowNum = $activeCell.parents('tr','.sensei-grid-tbody').index() + 1;
		textNew = "行号：" + RowNum;
	}
	
	return textNew;
}

/**********************右键菜单处理********end*****************************/

/**********************参数044默认弹出辅助核算层********begin*****************************/
var columnCheckSel={};
function showCheckDiv(cellObj){
	
	var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
	//console.log(cellObj.scrollTop+"："+cellObj.activeCell.offset().top+"："+cellObj.activeCell.position().top)
	$("#checkTable").html("");
	$("#checkDiv").css({
		display: "block",
		left: cellObj.activeCell.offset().left-5,
		top: $("#topDiv").height()+cellObj.activeCell.offset().top-scrollTop-6
	});
	
	var defaultDate="";
	var defaultCash = "";
	var defaultMoney = 0.00;
	var subjAttr=cellObj.subjAttr;
	columnCheckSel={};
	var defaultCheckNo="";
	var $row=grid.getCellRow(cellObj.activeCell);
	var $rowData=grid.getRowData($row);
	var columnName=cellObj.activeCell.data("column");
	
	if(columnName=="subj_name"){
		if(parseFloat($rowData.debit)!=0){
			defaultMoney=$rowData.debit;
		}else if(parseFloat($rowData.credit)!=0){
			defaultMoney=$rowData.credit;
		}
	}else if(columnName=="budg_subj_name"){
		if(parseFloat($rowData.budg_debit)!=0){
			defaultMoney=$rowData.budg_debit;
		}else if(parseFloat($rowData.budg_credit)!=0){
			defaultMoney=$rowData.budg_credit;
		}
	}
	
	if(cellObj.type=="cash"){
		//现金流量
		$("#checkTable").append('<tr><td>现金流量：</td><td><input id="check_cash" style="width: 320px;"/></td><tr>');
		
		if(parent.kindCashJson[cellObj.jsonKey]!=undefined && parent.kindCashJson[cellObj.jsonKey].length==1){
			defaultCash=parent.kindCashJson[cellObj.jsonKey][0].cash_item_id;
			if(parseFloat(defaultMoney)==0)defaultMoney=parent.kindCashJson[cellObj.jsonKey][0].money;
		}
		
		columnCheckSel["check_cash"]=$("#check_cash").ligerComboBox({
			url: "accCheckDictList.do?isCheck=false",
			parms: {column_id: 'cash_item_id'},
			data: parent.checkDict["cash"],
			valueField: 'id',
	     	textField: 'name',
	     	initValue: defaultCash,
		    cancelable: true,
		    autocomplete: true,
		    autocompletelocal: true,
		    width: 320,
		    selectBoxWidth: 430,
		    selectBoxHeight: 200,
		    setTextBySource: true,
		    keySupport: true,
		    alwayShowInDown: true,
		    highLight: true,
		    async: false,
		    initIsTriggerEvent: false,
		    autocompleteAllowEmpty : true,
		    onSuccess :function (data){
		    	parent.checkDict["cash"]=data;
		    },
		    onBeforeSelect: function (newvalue){
		    	//console.log("onBeforeSelect")
		    	if(newvalue==liger.get("check_cash").getValue()){
		    		return false;
		    	}
		    },
		    onSelected: function (selectValue,selectText){
		    	//console.log("onSelected",selectValue)
		    	if(selectValue==null || selectValue==""){
		    		return;
		    	}
		    	updateVouchCashJson(selectValue,selectText);
	    	}
		});
		
		//加载默认值
		/*if(defaultCash && defaultCash!=""){
			$.each(columnCheckSel["check_cash"].data,function(i,o){
				if(o.id==defaultCash){
					liger.get("check_cash").setValue(defaultCash);
					return false;
				}
			});
		}*/
		
	}else{
		//辅助核算
		if(parent.kindCheckJson[cellObj.jsonKey]!=undefined && parent.kindCheckJson[cellObj.jsonKey].length==1){
			if(parseFloat(defaultMoney)==0)defaultMoney=parent.kindCheckJson[cellObj.jsonKey][0].money;
		}
		
		//现金流量
		if(subjAttr.is_cash=="1" && parent.paraList["004"]=="1"){
			
			$("#checkTable").append('<tr><td>现金流量：</td><td><input id="check_cash" style="width: 320px;"/></td></tr>');
			
			if(parent.kindCheckJson[cellObj.jsonKey]!=undefined && parent.kindCheckJson[cellObj.jsonKey].length==1){
				defaultCash=parent.kindCheckJson[cellObj.jsonKey][0].cash_item_id;
			}
			
			columnCheckSel["check_cash"]=$("#check_cash").ligerComboBox({
				url: "accCheckDictList.do?isCheck=false",
				parms: {column_id: 'cash_item_id'},
				data: parent.checkDict["cash"],
				valueField: 'id',
		     	textField: 'name',
		     	initValue: defaultCash,
			    cancelable: true,
			    autocomplete: true,
			    autocompletelocal: true,
			    width: 320,
			    selectBoxWidth: 430,
			    selectBoxHeight: 200,
			    setTextBySource: true,
			    keySupport: true,
			    alwayShowInDown: true,
			    highLight: true,
			    async: true,
			    initIsTriggerEvent: false,
			    autocompleteAllowEmpty : false,
			    onSuccess :function (data){
			    	parent.checkDict["cash"]=data;
			    },
			    onBeforeSelect: function (newvalue){
			    	//console.log("onBeforeSelect")
			    	if(newvalue==liger.get("check_cash").getValue()){
			    		return false;
			    	}
			    },
			    onSelected: function (selectValue,selectText){
			    	//console.log("onSelected",selectValue)
			    	if(selectValue==null || selectValue==""){
			    		return;
			    	}
			    	updateVouchCheckJson({
						kv: "cash_item_id",
						value: selectValue,
						kt: "cash_name",
						text: selectText
					});
		    	}
			});
			
			//加载默认值
			/*if(defaultCash && defaultCash!=""){
				$.each(columnCheckSel["check_cash"].data,function(i,o){
					if(o.id==defaultCash){
						liger.get("check_cash").setValue(defaultCash);
						return false;
					}
				});
			}*/
		}
		
		//科目性质=03银行科目
		if(subjAttr.subj_nature_code=="03"){
			
			$("#checkTable").append('<tr><td>结算方式：</td><td><input id="pay_type_code" style="width: 320px;"/><td></tr>');
			
			var defaultPayTypeCode="";
			if(parent.kindCheckJson[cellObj.jsonKey]!=undefined && parent.kindCheckJson[cellObj.jsonKey].length==1){
				defaultPayTypeCode=parent.kindCheckJson[cellObj.jsonKey][0].pay_type_code;
			}
			
			columnCheckSel["pay_type_code"]=$("#pay_type_code").ligerComboBox({
				url: "accCheckDictList.do?isCheck=false",
				parms: {column_id: 'pay_code'},
				data: parent.checkDict["payType"],
				valueField: 'id',
		     	textField: 'name',
		     	initValue: defaultPayTypeCode,
			    cancelable: true,
			    autocomplete: true,
			    autocompletelocal: true,
			    width: 320,
			    selectBoxWidth: 430,
			    selectBoxHeight: 200,
			    setTextBySource: true,
			    keySupport: true,
			    alwayShowInDown: true,
			    highLight: true,
			    async: true,
			    initIsTriggerEvent: false,
			    autocompleteAllowEmpty : false,
			    onSuccess :function (data){
			    	parent.checkDict["payType"]=data;
			    },
			    onBeforeSelect: function (newvalue){
			    	//console.log("onBeforeSelect")
			    	if(newvalue==liger.get("pay_type_code").getValue()){
			    		return false;
			    	}
			    },
			    onSelected: function (selectValue,selectText){
			    	//console.log("onSelected",selectValue)
			    	if(selectValue==null || selectValue==""){
			    		return;
			    	}
			    	updateVouchCheckJson({
						kv: "pay_type_code",
						value: selectValue,
						kt: "pay_name",
						text: selectText
					});
		    	}
			});
			
			//加载默认值
			/*if(defaultPayTypeCode && defaultPayTypeCode!=""){
				$.each(columnCheckSel["pay_type_code"].data,function(i,o){
					if(o.id==defaultPayTypeCode){
						liger.get("pay_type_code").setValue(defaultPayTypeCode);
						return false;
					}
				});
			}*/
		}
		
		//票据
		if(subjAttr.is_bill=="1"){
			
			$("#checkTable").append('<tr><td>票据类型：</td><td><input id="paper_type_code" style="width: 320px;"/></td></tr>');
			
			var defaultPaperTypeCode="";
			if(parent.kindCheckJson[cellObj.jsonKey]!=undefined && parent.kindCheckJson[cellObj.jsonKey].length==1){
				defaultPaperTypeCode=parent.kindCheckJson[cellObj.jsonKey][0].paper_type_code;
			}
			
			$("#checkTable").append('<tr><td>票&nbsp;据&nbsp;号&nbsp;：</td><td><input id="check_no" style="width: 320px;"/></td></tr>');
			
			if(parent.kindCheckJson[cellObj.jsonKey]!=undefined && parent.kindCheckJson[cellObj.jsonKey].length==1){
				defaultCheckNo=parent.kindCheckJson[cellObj.jsonKey][0].check_no;
			}
			
			//票据类型
			columnCheckSel["paper_type_code"]=$("#paper_type_code").ligerComboBox({
				url: "accCheckDictList.do?isCheck=false",
				parms: {column_id: 'paper_type_code'},
				data: parent.checkDict["paperType"],
				valueField: 'id',
		     	textField: 'name',
		     	initValue: defaultPaperTypeCode,
			    cancelable: true,
			    autocomplete: true,
			    autocompletelocal: true,
			    width: 320,
			    selectBoxWidth: 430,
			    selectBoxHeight: 200,
			    setTextBySource: true,
			    keySupport: true,
			    alwayShowInDown: true,
			    highLight: true,
			    async: true,
			    initIsTriggerEvent: true,
			    autocompleteAllowEmpty : false,
			    onSuccess :function (data){
			    	parent.checkDict["paperType"]=data;
			    	//加载票据号默认值
					setDefaultCheckNO(defaultCheckNo);
			    },
			    onBeforeSelect: function (newvalue){
			    	//console.log("onBeforeSelect")
			    	//reloadCheckNoSelect(newvalue);
			    	if(newvalue==liger.get("paper_type_code").getValue()){
			    		return false;
			    	}
			    },
			    onSelected: function (selectValue,selectText){
			    	//console.log("onSelected",selectValue)
		        	//票据号
					reloadCheckNoSelect(selectValue);
		        	
			    	if(selectValue==null || selectValue==""){
			    		return;
			    	}
			    	updateVouchCheckJson({
						kv: "paper_type_code",
						value: selectText
					});
		    	}
			});
			
			//加载默认值
			/*if(defaultPaperTypeCode && defaultPaperTypeCode!=""){
				$.each(columnCheckSel["paper_type_code"].data,function(i,o){
					if(o.name==defaultPaperTypeCode){
						liger.get("paper_type_code").setValue(defaultPaperTypeCode.split(" ")[0]);
						return false;
					}
				});
			}*/
			
	    }
		
		//处理辅助核算
		if(subjAttr.is_check=="1"){
			
			var acc_year=$("#vouch_date",parent.document).val().substring(0,4);
			var columnCheckSel=[];
			ajaxJsonObjectByUrl("queryVouchCheckType.do?isCheck=false",{subj_code: subjAttr.id,acc_year: acc_year},function (resData){
				var typeData=JSON.parse(resData.data);
				$.each(typeData,function(i,obj){
					if(!obj.column_no){
						obj.column_no="";
					}
					
					$("#checkTable").append('<tr><td>'+obj.check_type_name+'：</td><td><input id='+obj.column_check+' style="width: 320px;"/></td></tr>');
					
					var defaultCheck="";
					if(parent.kindCheckJson[cellObj.jsonKey]!=undefined && parent.kindCheckJson[cellObj.jsonKey].length==1){
						defaultCheck=parent.kindCheckJson[cellObj.jsonKey][0][obj.column_check];
					}
					
					//核算分类编码字段
					var check_type_code="";
					//console.log(JSON.stringify(checkJson[i]))
					if(obj.check_type_code!=undefined && obj.check_type_code!=""){
						//替换条件1;2;3--> '1','2','3'
						check_type_code="'"+obj.check_type_code.replace(/\;/g, "','")+"'";
					}
					
					//部门类型字段
					if(check_type_code!="" && obj.check_table=="hos_dept_dict"){
						check_type_code=" and a.dept_id in (select attr.dept_id from ACC_DEPT_ATTR attr where attr.group_id=a.group_id and " +
						" attr.hos_id=a.hos_id and attr.type_code in ("+check_type_code+")) ";
					}
					//职工分类字段
					else if(check_type_code!="" && obj.check_table=="hos_emp_dict"){
						check_type_code=" and a.kind_code in("+check_type_code+") ";
					}
//					//项目分类字段
//					else if(check_type_code!="" && checkJson[i].check_table=="hos_proj_dict"){
//						check_type_code_filed="type_code";
//					}
//					//库房分类字段
//					else if(check_type_code!="" && checkJson[i].check_table=="hos_store_dict"){
//						check_type_code_filed="type_code";
//					}
//					//客户分类字段
//					else if(check_type_code!="" && checkJson[i].check_table=="hos_cus_dict"){
//						check_type_code_filed="type_code";
//					}
//					//供应商分类字段
//					else if(check_type_code!="" && checkJson[i].check_table=="hos_sup_dict"){
//						check_type_code_filed="type_code";
//					}
					else if(check_type_code!="" && obj.check_table=="hos_source"){
						check_type_code=" and a.source_attr in("+check_type_code+") ";
					}else if(check_type_code!=""){
						//默认条件
						check_type_code=" and a.type_code in("+check_type_code+") ";
					}
					
					var para={						
							check_id: obj.check_id,
							column_id: obj.column_id,
							column_no: obj.column_no,
							column_code: obj.column_code,
							column_name: obj.column_name,
							check_table: obj.check_table,
							column_check: obj.column_check,
							check_type_code: check_type_code,
							//key: comInitId,
							out_code: subjAttr.out_code//支出性质
					};
					
					columnCheckSel[obj.column_check]=$("#"+obj.column_check).ligerComboBox({
						url: "accCheckDictList.do?isCheck=false",
						parms: para,
						data: parent.checkDict[obj.column_check],
						valueField: 'idno',
				     	textField: 'name',
				     	initValue: defaultCheck,
					    cancelable: true,
					    autocomplete: true,
					    autocompletelocal: true,
					    width: 320,
					    selectBoxWidth: 430,
					    selectBoxHeight: 200,
					    setTextBySource: true,
					    keySupport: true,
					    alwayShowInDown: true,
					    highLight: true,
					    async: true,
					    initIsTriggerEvent: false,
					    autocompleteAllowEmpty : false,
					    onSuccess: function (data){
					    	parent.checkDict[obj.column_check]=data;
					    },
					    onBeforeSelect: function (newvalue){
					    	//console.log("onBeforeSelect")
					    	if(newvalue==liger.get(obj.column_check).getValue()){
					    		return false;
					    	}
					    },
					    onSelected: function (selectValue,selectText){
					    	//console.log("onSelected",selectValue)
					    	if(selectValue==null || selectValue==""){
					    		return;
					    	}
					    	var checkItemVal="";
					    	if(i==0){
					    		//第一个辅助做为摘要
								checkItemVal= selectText;
							}
				        	updateVouchCheckJson({
								kv: obj.column_check,
								value: selectValue,
								kt: obj.column_check+"_name",
								text: selectText,
								checkItemVal: checkItemVal
							});
				    	}
					});
					
					//加载默认值
					/*if(defaultCheck && defaultCheck!=""){
						var isItem=false;
						$.each(columnCheckSel[obj.column_check].data,function(i,o){
							if(o.idno==defaultCheck){
								isItem=true;
								return false;
							}
						});
						if(!isItem){
							columnCheckSel[obj.column_check].addItem({idno: defaultCheck,name: defaultCheckText});
						}
						columnCheckSel[obj.column_check].setValue(defaultCheck);
						//columnCheckSel[obj.column_check].setText(defaultCheckText);
					}*/
					
				});
				
		    },false);
		}
		
		//科目性质=04、05应收、应付科目 
	    if(subjAttr.subj_nature_code=="04" || subjAttr.subj_nature_code=="05"){
	    	
	    	if(parent.paraList["049"]==1){
	    		var defaultBusinessNo="";
				if(parent.kindCheckJson[cellObj.jsonKey]!=undefined && parent.kindCheckJson[cellObj.jsonKey].length==1){
					if(parent.kindCheckJson[cellObj.jsonKey][0].business_no && parent.kindCheckJson[cellObj.jsonKey][0].business_no!=""){
						defaultBusinessNo=parent.kindCheckJson[cellObj.jsonKey][0].business_no;
					}
				}
		    	$("#checkTable").append('<tr><td>单&nbsp;据&nbsp;号&nbsp;：</td><td><input id="business_no" value="'+defaultBusinessNo+'" style="width: 318px;height:24px;border:1.3px solid #AECAF0"/></td></tr>');
		    	
		    	$("#business_no").on('blur', function() {
		    		updateVouchCheckJson({
		    			kv: "business_no",
		    			value: $("#business_no").val()
		    		});
		    	});
	    	}
	    	
	    	if(parent.paraList["035"]==1){
		    	var defaultConNo="";
				if(parent.kindCheckJson[cellObj.jsonKey]!=undefined && parent.kindCheckJson[cellObj.jsonKey].length==1){
					if(parent.kindCheckJson[cellObj.jsonKey][0].con_no && parent.kindCheckJson[cellObj.jsonKey][0].con_no!=""){
						defaultConNo=parent.kindCheckJson[cellObj.jsonKey][0].con_no;
					}
				}
		    	$("#checkTable").append('<tr><td>合同编号：</td><td><input id="con_no" value="'+defaultConNo+'" style="width: 318px;height:24px;border:1.3px solid #AECAF0"/></td></tr>');
		    	
		    	$("#checkTable").append('<tr><td>发生日期：</td><td><input id="occur_date" style="width: 120px;"/></td></tr>');
		    	
		    	var defaultOccurDate=$("#vouch_date",parent.document).val();
				if(parent.kindCheckJson[cellObj.jsonKey]!=undefined && parent.kindCheckJson[cellObj.jsonKey].length==1){
					if(parent.kindCheckJson[cellObj.jsonKey][0].occur_date && parent.kindCheckJson[cellObj.jsonKey][0].occur_date!=""){
						defaultOccurDate=parent.kindCheckJson[cellObj.jsonKey][0].occur_date;
					}
				}
		    	
		    	$("#checkTable").append('<tr><td>到期日期：</td><td><input id="due_date" style="width: 120px;"/></td></tr>');
		    	
		    	$("#business_no").on('blur', function() {
		    		updateVouchCheckJson({
		    			kv: "business_no",
		    			value: $("#business_no").val()
		    		});
		    	});
		    	$("#con_no").on('blur', function() {
		    		updateVouchCheckJson({
		    			kv: "con_no",
		    			value: $("#con_no").val()
		    		});
		    	});
		    	$("#occur_date").on('blur', function() {
		    		updateVouchCheckJson({
		    			kv: "occur_date",
		    			value: $("#occur_date").val()
		    		});
		    	});
		    	$("#due_date").on('blur', function() {
		    		updateVouchCheckJson({
		    			kv: "due_date",
		    			value: $("#due_date").val()
		    		});
		    	});
		    	
		    	var occurDate = $("#occur_date").etDatepicker({
					range: false,
					readonly: false,
					dateFormat: "yyyy-mm-dd",
					defaultDate: [defaultOccurDate],
					onChange: function (value) {
						updateVouchCheckJson({
			    			kv: "occur_date",
			    			value: value
			    		});
					}
				});
		    	
		    	var date = new Date();
		    	if(parent.paraList["027"]==0){
			    	//到期日期默认月底
			    	var lastDay=getLastDay(date.getFullYear(),date.getMonth() + 1);
			    	defaultDate=date.getFullYear()+"-"+(date.getMonth() + 1)+"-"+lastDay;
			    }else{
			    	//到期日期默认年底
			    	defaultDate=date.getFullYear()+"-12-31";
			    }
		    	
		    	var defaultDueDate=defaultDate;
				if(parent.kindCheckJson[cellObj.jsonKey]!=undefined && parent.kindCheckJson[cellObj.jsonKey].length==1){
					if(parent.kindCheckJson[cellObj.jsonKey][0].due_date && parent.kindCheckJson[cellObj.jsonKey][0].due_date!=""){
						defaultDueDate=parent.kindCheckJson[cellObj.jsonKey][0].due_date;
					}
				}
		    	
		    	var dueDate = $("#due_date").etDatepicker({
					range: false,
					readonly: false,
					dateFormat: "yyyy-mm-dd",
					defaultDate: [defaultDueDate],
					onChange: function (value) {
						updateVouchCheckJson({
			    			kv: "due_date",
			    			value: value
			    		});
					}
				});
		    }
	    }

	}
	
	$("#checkTable :input")[0].focus();
	if($($("#checkTable :input")[0]).val()!=""){
		$($("#checkTable :input")[0]).select();
	}
	$('#checkTable').off('keydown', 'input');
	$('#checkTable').on('keydown', 'input', function(e) {
	    var self = $(this)
	      , table = self.parents('table:eq(0)')
	      , focusable
	      , next;
	    if (e.keyCode == 13) {
	        focusable = table.find('input').filter(':visible');
	        //console.log(focusable.eq(focusable.index(this)))
	        next = focusable.eq(focusable.index(this)+1);
	        var comboboxid=$(this).attr("data-comboboxid");
	        if (next.length) {
	        	
	        	//下拉框
	        	if(comboboxid){
	        		var combobox=columnCheckSel[comboboxid];
	        		ligerToSelect(combobox);
	        		if(!ligerToDefault($(this),combobox)){
	        			return false;
	        		}
	        		
	        		if(comboboxid=="paper_type_code"){
	        			if($(this).val()==""){
	        				updateVouchCheckJson({
								kv: "paper_type_code",
								value: ''
							});
	        			}
	        		}else if(comboboxid=="pay_type_code"){
	        			if($(this).val()==""){
	        				updateVouchCheckJson({
								kv: "pay_type_code",
								value: '',
								kt: "pay_name",
								text: ''
							});
	        			}
	        		}else if(comboboxid=="check_no"){	        			
	        			if($("#paper_type_code").val()!="" && $(this).val()==""){
	        				return false;
	        			}
	        			
        				setDefaultCheckNO($(this).val());
        				updateVouchCheckJson({
        		    		kv: "check_no",
        					value: $(this).val(),
        				});
	        			
	        		}else if($(this).val()==""){
		        		return false;
		        	}
	        		
	        	}
	        	
	        	if($(next).val()!=""){
	        		$(next).select();
	        	}
	            next.focus();
	        } else {
	        	
	        	//下拉框
	        	if(comboboxid){
	        		var combobox=columnCheckSel[comboboxid];
	        		ligerToSelect(combobox);
	        		if(!ligerToDefault($(this),combobox)){
	        			return false;
	        		}
	        		
	        		if(comboboxid=="paper_type_code"){
	        			if($(this).val()==""){
	        				updateVouchCheckJson({
								kv: "paper_type_code",
								value: ''
							});
	        			}
	        		}else if(comboboxid=="pay_type_code"){
	        			if($(this).val()==""){
	        				updateVouchCheckJson({
								kv: "pay_type_code",
								value: '',
								kt: "pay_name",
								text: ''
							});
	        			}
	        		}else if(comboboxid=="check_no"){
	        			if($("#paper_type_code").val()!="" && $(this).val()==""){
	        				return false;
	        			}
	        			
        				setDefaultCheckNO($(this).val());
        				updateVouchCheckJson({
        		    		kv: "check_no",
        					value: $(this).val()
        				});
	        			
	        			//checkDivCallBack({activeCell: cellObj.activeCell,rowData: cellObj.rowData});
	        		}else if($(this).val()==""){
		        		return false;
		        	}
	        	}
	        	
	        	if($(next).val()!=""){
	        		$(next).select();
	        	}
	        
	        	checkDivCallBack({activeCell: cellObj.activeCell,rowData: cellObj.rowData});
	        }
	        return false;
         }
    });
	
	function ligerToSelect(combobox){
		
    	if (!combobox.selectBox.is(":visible")) return;
        var curTd = combobox.selectBox.table.find("td.l-over");
       
        if (curTd.length){
            var value = curTd.attr("value");
            if (combobox.enabledLoadDetail()){
                combobox.loadDetail(value, function (data){
                    var index = combobox.getRowIndex(value);
                    if (index == -1) return;
                    combobox.data = combobox.data || [];
                    combobox.data[index] = combobox.selected = data;
                    
                    combobox._changeValue(value, curTd.attr("text"), true);
                    combobox.selectBox.hide();
                    combobox.trigger('textBoxKeyEnter', [{
                        element: curTd.get(0)
                    }]);
                    
                });
            } else{
            	combobox._changeValue(value, curTd.attr("text"), true);
                combobox.selectBox.hide();
                combobox.trigger('textBoxKeyEnter', [{
                    element: curTd.get(0)
                }]);
            }
        }
	}
	
	function ligerToDefault(_this,combobox){
		
		if(_this.val()==""){
			combobox.selectBox.hide();
    		return true;
    	}
		
		var isItem=false;
		if(combobox && combobox.data.length>0){
			
			$.each(combobox.data,function(i,o){
				if(o.name==combobox.getText()){
					combobox.setValue(o.idno===undefined?o.id:o.idno);
					isItem=true;
					return false;
				}
			});
			/*if(!isItem && combobox.id!="check_no"){
				combobox.setValue(combobox.data[0].idno===undefined?combobox.data[0].id:combobox.data[0].idno);
			}*/
		}
		
		if(combobox.id=="check_no"){
			isItem=true;
		}
		combobox.selectBox.hide();
		return isItem;
		
	}
	
	function setDefaultCheckNO(checkNo){
		
		if(checkNo && checkNo!=""){
			var isItem=false;
			$.each(columnCheckSel["check_no"].data,function(i,o){
				if(o.id==checkNo){
					isItem=true;
					return false;
				}
			});
			if(!isItem){
				columnCheckSel["check_no"].addItem({id: checkNo,name: checkNo});
			}
			columnCheckSel["check_no"].setValue(checkNo);
		}
		
	}
	
	//更新VouchCashJson
	function updateVouchCashJson(selectValue,selectText){
		
		var rowData=[];
		if(parent.kindCashJson[cellObj.jsonKey]!=undefined && parent.kindCashJson[cellObj.jsonKey].length==1){
			rowData=parent.kindCashJson[cellObj.jsonKey];
			rowData[0]["cash_item_id"]=selectValue;
			rowData[0]["cash_name"]=selectText;
		}else{
			rowData=[{"id":1,"cid":"","summary":""+cellObj.rowData.summary+"","cash_name":""+selectText+"","cash_item_id":""+selectValue+"","money":""+defaultMoney+""}];
		}
		
		parent.kindCashJson[cellObj.jsonKey] = rowData;
		var sumaryIndex=3;
		if(kindCode=="02"){
			sumaryIndex=17;
		}
		
		//拼接分录的摘要：[辅助核算内容]摘要
		if(parent.paraList["050"]==1 && selectText!=undefined && selectText!=""){
			var $row=grid.getCellRow(cellObj.activeCell);
			var checkItemVal=selectText.split(" ")[1];
			var cellsText3=$($row[0].cells[3]).find("div").text();
			if(cellsText3!="" && cellsText3.indexOf("[")!=-1 && cellsText3.indexOf("]")!=-1){
				cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
			}
			$($row[0].cells[3]).find("div").text("["+checkItemVal+"]"+cellsText3);//摘要+现金流量
		}
	}
	
	//更新VouchCheckJson
	function updateVouchCheckJson(ele){
		
		var rowData=[];
		if(parent.kindCheckJson[cellObj.jsonKey]!=undefined && parent.kindCheckJson[cellObj.jsonKey].length==1){
			rowData=parent.kindCheckJson[cellObj.jsonKey];
		}else{
			rowData=[{"id":1,"cid":"","summary":""+cellObj.rowData.summary+"","money":""+defaultMoney+""}];
		}
		
		rowData[0][ele.kv]=ele.value;
		if(ele.kt){
			rowData[0][ele.kt]=ele.text;
		}
	
		parent.kindCheckJson[cellObj.jsonKey] = rowData;
		var sumaryIndex=3;
		if(kindCode=="02"){
			sumaryIndex=17;
		}
		
		//拼接分录的摘要：[辅助核算内容]摘要
		if(parent.paraList["036"]==1 && ele.checkItemVal!=undefined && ele.checkItemVal!=""){
			var $row=grid.getCellRow(cellObj.activeCell);
			ele.checkItemVal=ele.checkItemVal.split(" ")[1];
			var cellsText3=$($row[0].cells[sumaryIndex]).find("div").text();
			if(cellsText3!="" && cellsText3.indexOf("[")!=-1 && cellsText3.indexOf("]")!=-1){
				cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
			}
			$($row[0].cells[sumaryIndex]).find("div").text("["+ele.checkItemVal+"]"+cellsText3);//摘要+辅助核算页面传过来的摘要	
		}else if(parent.paraList["050"]==1 && ele.kv=="cash_item_id" && ele.selectText!=undefined && ele.selectText!=""){
			var $row=grid.getCellRow(cellObj.activeCell);
			ele.selectText=ele.selectText.split(" ")[1];
			var cellsText3=$($row[0].cells[3]).find("div").text();
			if(cellsText3!="" && cellsText3.indexOf("[")!=-1 && cellsText3.indexOf("]")!=-1){
				cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
			}
			$($row[0].cells[3]).find("div").text("["+ele.selectText+"]"+cellsText3);//摘要+现金流量
		}
			
	}
	
	//级联票据号
	function reloadCheckNoSelect(value) {
		/*if(value!=""){
		checkNoSel.setValue("");
		checkNoSel.clearOptions();
		checkNoSel.reload({
			url: "accCheckDictList.do?isCheck=false",
			para: {column_id: 'check_no',paper_type_code: value},
		});
		}*/
		/*liger.get("check_no").clear();
		liger.get("check_no").set("parms", {column_id: 'check_no',paper_type_code: value});
		liger.get("check_no").reload();*/
		if(liger.get("check_no")){
			liger.get("check_no").clear();
			liger.get("check_no").setValue("");
	    	liger.get("check_no").setText("");
		}
		
		//票据号
    	columnCheckSel["check_no"]=$("#check_no").ligerComboBox({
			url: "accCheckDictList.do?isCheck=false",
			parms: {column_id: 'check_no',paper_type_code: value},
			valueField: 'id',
	     	textField: 'name',
		    cancelable: true,
		    autocomplete: true,
		    autocompletelocal: true,
		    width: 320,
		    selectBoxWidth: 430,
		    selectBoxHeight: 200,
		    setTextBySource: true,
		    keySupport: true,
		    alwayShowInDown: true,
		    highLight: true,
		    async: true,
		    initIsTriggerEvent: false,
		    autocompleteAllowEmpty : false,
		    onSuccess :function (checkData){
		    	
		    },
		    onSelected: function (checkValue,checkText){
		    	//console.log("onSelected",selectValue)
		    	if(checkValue==null || checkValue==""){
		    		return;
		    	}
		    
		    	updateVouchCheckJson({
		    		kv: "check_no",
					value: checkValue,
				});
	    	}
		});
			
	}
}

//控制焦点
function checkDivCallBack(parm){

	$("#checkDiv").hide();
	if(columnCheckSel){
		for(var a in columnCheckSel){
			if(columnCheckSel[a])columnCheckSel[a].selectBox.hide();
		}
	}
	
	var $activeCell=parm.activeCell;
	var $row=grid.getCellRow($activeCell);
	var $rowData=parm.rowData;
	//frameObj.grid.move("right");
	//frameObj.grid.hideMove("right");
	
	if(parent.paraList["047"]==1){
    	
    	var subjCode=$rowData.subj_code;
    	if($activeCell.index()==18){
    		subjCode=$rowData.budg_subj_code;
    	}
    	if(subjCode!=""){
    		subjCode=subjCode.substring(0,1);
    	}
    	
    	if(subjCode=="4" && $rowData.debit_name==""){
    		grid.setActiveCell($($row[0].cells[$activeCell.index()+2]));
	    	grid.editCell(); 
    	}else if(subjCode=="6" && $rowData.budg_debit_name==""){
    		grid.setActiveCell($($row[0].cells[$activeCell.index()+2]));
	    	grid.editCell(); 
    	}else{
    		grid.setActiveCell($activeCell.next());//下一单元格焦点
	    	grid.editCell(); 
    	}
	}else{
		grid.setActiveCell($activeCell.next());
		grid.editCell(); 
	}
	
	setTimeout(function (){
		if(columnCheckSel){
			for(var a in columnCheckSel){
				if(columnCheckSel[a])columnCheckSel[a].selectBox.hide();
			}
		}
	},1000);
	
/*	grid.setActiveCell($activeCell.next());//下一单元格焦点
   	setTimeout(function (){
    	grid.editCell(); 
	},100);
   	
	if(window.navigator.userAgent.indexOf("Chrome") >= 0){
	   grid.$el.focus();
	}else{
		$($row[0].cells[5]).focus();
	}*/
	
}

/**********************参数044默认弹出辅助核算层********end*****************************/

