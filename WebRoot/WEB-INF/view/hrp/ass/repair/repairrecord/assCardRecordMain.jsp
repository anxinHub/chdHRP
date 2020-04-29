<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <style>
        </style>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="hr,dialog,select" name="plugins" />
        </jsp:include>
        <script>
            var ass_card_no, ass_name, ass_spec, ass_brand, fac_name, sup_name, contract_no, accept_date, run_date, usage_name, emp_name, emp_name, dept_name,
                ass_seq_no;

            function initDict() {
                // 卡片编号
                ass_card_no = $("#ass_card_no");
                // 资产名称
                ass_name = $("#ass_name");
                // 规格型号
                ass_spec = $('#ass_spec');
                // 品牌
                ass_brand = $('#ass_brand');                
                // 生产厂商
                fac_name = $('#fac_name');                
                // 供应商
                sup_name = $('#sup_name');                
                // 合同编号
                contract_no = $('#contract_no');                
                // 安装日期
                accept_date = $('#accept_date');                
                // 投入使用日期
                run_date = $('#run_date');                                
                // 资产用途
                usage_name = $('#usage_name');                                
                // 验收人
                emp_name = $('#emp_name');                
                // 验收日期
                emp_name = $('#emp_name');                                
                // 管理科室
                dept_name = $('#dept_name');                                
                // 序列号
                ass_seq_no = $('#ass_seq_no');   
                
                // 赋值代码 eg：
                /* ass_card_no.val(222);
                ass_name.val(3333);
                ass_spec.val(44444);
                ass_brand.val(55555);
                fac_name.val(55555);
                sup_name.val(55555);
                contract_no.val(55555);
                accept_date.val(55555);
                run_date.val(55555);
                usage_name.val(55555);
                emp_name.val(55555);
                emp_name.val(55555);
                dept_name.val(656565);
                ass_seq_no.val(99999); */
            }

            function saveData() {
                ajaxPostData({
                    url: "",
                    data: {},
                    delayCallback: true,
                    success: function (res) {
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        parent.search(); // 父页面查询                       
                    }
                })
            }
            $(function () {
                initDict()
            })
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label">卡片编号：</td>
                    <td class="ipt">
                        <input id="ass_card_no" type="text" value="${ass_card_no}" disabled />
                    </td>
                    <td class="label">资产名称：</td>
                    <td class="ipt">
                        <input id="ass_name" type="text" value="${ass_name}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">规格型号：</td>
                    <td class="ipt">
                        <input id="ass_spec" type="text" value="${ass_spec}" disabled/>
                    </td>
                    <td class="label">品牌：</td>
                    <td class="ipt">
                        <input id="ass_brand" type="text" value="${ass_brand}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">生产厂商：</td>
                    <td class="ipt">
                        <input id="fac_name" type="text" value="${fac_name}" disabled/>
                    </td>
                    <td class="label">供应商：</td>
                    <td class="ipt">
                        <input id="sup_name" type="text"  value="${sup_name}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">合同编号：</td>
                    <td class="ipt">
                        <input id="contract_no" type="text" value="${contract_no}" disabled/>
                    </td>
                    <td class="label">安装日期：</td>
                    <td class="ipt">
                        <input id="accept_date" type="text" value="${accept_date}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">投入使用日期：</td>
                    <td class="ipt">
                        <input id="run_date" type="text" value="${run_date}" disabled/>
                    </td>
                    <td class="label">资产用途：</td>
                    <td class="ipt">
                        <input id="usage_name" type="text" value="${usage_name}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">验收人：</td>
                    <td class="ipt">
                        <input id="emp_name" type="text"  value="${emp_name}" disabled/>
                    </td>
                    <td class="label">验收日期：</td>
                    <td class="ipt">
                        <input id="accept_date" type="text"  value="${accept_date}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">管理科室：</td>
                    <td class="ipt">
                        <input id="dept_name" type="text" value="${dept_name}" disabled/>
                    </td>
                    <td class="label">序列号：</td>
                    <td class="ipt">
                        <input id="ass_seq_no" type="text" value="${ass_seq_no}"  disabled/>
                    </td>
                </tr>
            </table>
        </div>
    </body>

    </html>