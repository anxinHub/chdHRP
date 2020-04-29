<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<style>
    .setList {
        list-style: none;
        margin-top: 50px;
        margin-left: 50px;
        font-size: 14px;
    }
    .setList li {
        margin: 15px 0;
    }
    .setList li div {
        display: inline-block;
        vertical-align:middle;
    }
    .button-group {
    	margin-top: 40px;
    	padding-left: 200px;
    }
</style>
<script>

	var budg_year = "${budg_year}";
	
    $(function() {
        loadDict();
        $("#year").change(function(){
        	budg_year = $("#year").val(),
        	query();
        });
    })
	
    function query(){
    	budg_year = $("#year").val(),
    	data = {
    		budg_year:budg_year
    	}
    	
    	ajaxJsonObjectByUrl("queryBudgSysSet.do?isCheck=false", data, function (responseData){
    		if(responseData){
    			if (responseData.work_budg_mode === '01') {
    				$("#work_budg_mode_1").prop("checked", true);
    			} else if (responseData.work_budg_mode === '02') {
    				$("#work_budg_mode_2").prop("checked", true);
    			}
    			
    			if (responseData.income_budg_mode === '01') {
    				$("#income_budg_mode_1").prop("checked", true);
    			} else if (responseData.income_budg_mode === '02') {
    				$("#income_budg_mode_2").prop("checked", true);
    			}
    			if (responseData.state === 'false') {
    				$("#work_budg_mode_1").prop("checked", false);
    				$("#work_budg_mode_2").prop("checked", false);
    			}
    			
    			if (responseData.state === 'false') {
    				$("#income_budg_mode_1").prop("checked", false);
    				$("#income_budg_mode_2").prop("checked", false);
    			}
    		}
    	}); 
    }
    
    var save = function () {
    	var budg_year = $("#year").val(),
    		work_budg_mode,
    		income_budg_mode,
    		data;
    	if(!budg_year){
    		$.ligerDialog.error("预算年度不能为空!");
    		return;
    	}
    	
    	if ($("#work_budg_mode_1").prop("checked")) {
    		work_budg_mode = "01"
    	} else if ($("#work_budg_mode_2").prop("checked")) {
    		work_budg_mode = "02"
    	} else {
    		$.ligerDialog.error("请选择业务预算编制模式!");
    		return;
    	}
    	if ($("#income_budg_mode_1").prop("checked")) {
    		income_budg_mode = "01"
    	} else if ($("#income_budg_mode_2").prop("checked")) {
    		income_budg_mode = "02"
    	} else {
    		$.ligerDialog.error("请选择医疗收入预算编制模式!");
    		return;
    	}
    	
    	data = {
    		budg_year: budg_year,
    		work_budg_mode: work_budg_mode,
    		income_budg_mode: income_budg_mode,
    		risk_fund_subj: liger.get("risk_fund_subj").getValue(),
    		risk_fund_rate: $("#risk_fund_rate").val(),
    		workload_index_out: liger.get("workload_index_out").getValue(),
    		workload_index_in: liger.get("workload_index_in").getValue(),
    		workload_index_check: liger.get("workload_index_check").getValue()?liger.get("workload_index_check").getValue():""
    	}
    	ajaxJsonObjectByUrl("addBudgSysSet.do?isCheck=false", data, function (responseData){
    		if(responseData.state=="true"){
    			query()
    		}
    	});
    }
    
    function loadDict() {
    	//预算年度下拉框
    	if(budg_year){
    		autocomplete("#year","../../queryBudgYear.do?isCheck=false","id","text",true, true,"", false, "${budg_year}",200);
    	}else{
    		autocomplete("#year","../../queryBudgYear.do?isCheck=false","id","text",true, true,"", true,"",200);
    	}
		
    	autocomplete("#risk_fund_subj","../../queryBudgSubj.do?isCheck=false&subj_type="+"05"+"&budg_year="+budg_year,"id","text",true, true,{key:"${risk_fund_subj}"}, false,"${risk_fund_subj}",200);
    	autocomplete("#workload_index_out","../../queryBudgIndexDict.do?isCheck=false","id","text",true, true,"", false,"${workload_index_out}",200);
    	autocomplete("#workload_index_in","../../queryBudgIndexDict.do?isCheck=false","id","text",true, true,"", false,"${workload_index_in}",200);
    	autocomplete("#workload_index_check","../../queryBudgIndexDict.do?isCheck=false","id","text",true, true,"", false,"${workload_index_check}",200);
    	
		$("#year").ligerTextBox({width:200});
		$("#workload_index_out").ligerTextBox({width:200});
		$("#workload_index_in").ligerTextBox({width:200});
		$("#workload_index_check").ligerTextBox({width:200});
		
		$("#risk_fund_subj").ligerTextBox({width:200});
		$("#risk_fund_rate").ligerTextBox({width:150});
		
		$("#save").ligerButton({
			width: 100,
			click: save
		});
		
		if ('${work_budg_mode}' === '01') {
			$("#work_budg_mode_1").prop("checked", true);
		} else if ('${work_budg_mode}' === '02') {
			$("#work_budg_mode_2").prop("checked", true);
		}
		
		if ('${income_budg_mode}' === '01') {
			$("#income_budg_mode_1").prop("checked", true);
		} else if ('${income_budg_mode}' === '02') {
			$("#income_budg_mode_2").prop("checked", true);
		}
    }
</script>
</head>
  
<body>
    <ul class="setList">
        <li>
           	预算年度：
            <input id="year" type="text">
        </li>
        <li>
            1、业务预算编制模式：
            <input id="work_budg_mode_1" value="01" name="work_budg_mode"  type="radio"/>
            <label>自上而下</label>
            <input id="work_budg_mode_2" value="02" name="work_budg_mode"  type="radio"/>
            <label>自下而上</label>
        </li>
        <li>
            2、医疗收入预算编制模式：
            <input id="income_budg_mode_1" value="01" name="income_budg_mode" type="radio"/>
            <label>自上而下</label>
            <input id="income_budg_mode_2" value="02" name="income_budg_mode" type="radio"/>
            <label>自下而上</label>
        </li>
        <li>
            3、提取医疗风险基金预算支出科目：
            <input id="risk_fund_subj" type="text">
        </li>
        <li>
            4、医疗风险基金提取比例：
            <input id="risk_fund_rate" type="text" value="${risk_fund_rate}">‰
        </li>
        <li>
            5、门诊工作量指标：
            <input id="workload_index_out" type="text">
        </li>
        <li>
            6、住院工作量指标：
            <input id="workload_index_in" type="text">
        </li>
        <li>
            7、检查工作量指标：
            <input id="workload_index_check" type="text">
        </li>
    </ul>
    <div class="button-group">
    	<button id="save">保存</button>
    </div>
    <div id="pageloading" class="l-loading"></div>
</body>
</html>
