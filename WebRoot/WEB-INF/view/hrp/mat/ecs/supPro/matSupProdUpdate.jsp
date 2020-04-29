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
            function initDict() {
            	  
                  check_state = $("#check_state").etSelect({
                      options: [{
                          id: 1,
                          text: '未审核'
                      }, 
                      {
                          id: 2,
                          text: '已完成'
                      }, 
                      {
                          id: 3,
                          text: '未通过'
                      }
                      ],
                      defaultValue: "${check_state}"
                  });
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
                    <td class="label">供应商：</td>
                    <td class="ipt">
                        <input id="sup_name" type="text" value="${sup_name}" disabled />
                    </td>
                    <td class="label">材料名称：</td>
                    <td class="ipt">
                        <input id="inv_name" type="text" value="${inv_name}" disabled/>
                    </td>
                    <td class="label">厂商名称：</td>
                    <td class="ipt">
                        <input id="fac_name" type="text" value="${fac_name}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">产品小类：</td>
                    <td class="ipt">
                        <input id="prod_type_name2" type="text" value="${prod_type_name2}" disabled/>
                    </td>
                    <td class="label">68分类：</td>
                    <td class="ipt">
                        <input id="prod_type_name3" type="text"  value="${prod_type_name3}" disabled/>
                    </td>
                    <td class="label">试剂分类：</td>
                    <td class="ipt">
                        <input id="prod_type_name4" type="text" value="${prod_type_name4}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">注册证号：</td>
                    <td class="ipt">
                        <input id="cert_code" type="text" value="${cert_code}" disabled/>
                    </td>
                    <td class="label">批件号：</td>
                    <td class="ipt">
                        <input id="cert_batch" type="text" value="${cert_batch}" disabled/>
                    </td>
                    <td class="label">证件开始日期：</td>
                    <td class="ipt">
                        <input id="start_date" type="text" value="${start_date}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">证件结束日期：</td>
                    <td class="ipt">
                        <input id="end_date" type="text"  value="${end_date}" disabled/>
                    </td>
                    <td class="label">是否长期：</td>
                    <td class="ipt">
                        <input id="is_long" type="text"  value="${is_long}" disabled/>
                    </td>
                     <td class="label">审核状态：</td>
                    <td class="ipt">
                        <input id="check_state" type="text" value="${check_state}" style="width: 180px" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">产品别名1：</td>
                    <td class="ipt">
                        <input id="alias1" type="text" value="${alias1}" disabled/>
                    </td>
                    <td class="label">产地类型：</td>
                    <td class="ipt">
                        <input id="origin_type" type="text" value="${origin_type}"  disabled/>
                    </td>
                    <td class="label">品牌名称：</td>
                    <td class="ipt">
                        <input id="brand_name" type="text" value="${brand_name}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">产地名称：</td>
                    <td class="ipt">
                        <input id="origin_name" type="text" value="${origin_name}"  disabled/>
                    </td>
                    <td class="label">是否需要冷链：</td>
                    <td class="ipt">
                        <input id="is_cold" type="text" value="${is_cold}" disabled/>
                    </td>
                    <td class="label">储存条件：</td>
                    <td class="ipt">
                        <input id="stora_term" type="text" value="${stora_term}"  disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">规格名称：</td>
                    <td class="ipt">
                        <input id="spec_name" type="text" value="${spec_name}" disabled/>
                    </td>
                    <td class="label">组件名称：</td>
                    <td class="ipt">
                        <input id="term_name" type="text" value="${term_name}"  disabled/>
                    </td>
                    <td class="label">规格别称：</td>
                    <td class="ipt">
                        <input id="spec_name_sec" type="text" value="${spec_name_sec}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">包装：</td>
                    <td class="ipt">
                        <input id="packages" type="text" value="${packages}"  disabled/>
                    </td>
                    <td class="label">是否植入：</td>
                    <td class="ipt">
                        <input id="is_impl" type="text" value="${is_impl}" disabled/>
                    </td>
                    <td class="label">主条码：</td>
                    <td class="ipt">
                        <input id="bar_code" type="text" value="${bar_code}"  disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">招标名称：</td>
                    <td class="ipt">
                        <input id="tender_name" type="text" value="${tender_name}" disabled/>
                    </td>
                    <td class="label">单位：</td> 
                    <td class="ipt">
                        <input id="unit_name" type="text" value="${unit_name}"  disabled/>
                    </td>
                    <td class="label">单价：</td>
                    <td class="ipt">
                        <input id="price" type="text" value="${price}" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="label">产品类型：</td>
                    <td class="ipt">
                        <input id="prod_type_name1" type="text" value="${prod_type_name1}" disabled/>
                    </td>
                    <td class="label">交易编码：</td>
                    <td class="ipt">
                        <input id="bid_code" type="text" value="${bid_code}"  disabled/>
                    </td>
                </tr>
                <tr>
                   <td class="label">产品性能结构及组成：</td>
                    <td class="ipt"colspan="2">
                        <textarea rows="10" cols="50" id="contents"  disabled="disabled">${contents}</textarea>
                    </td>
                      <td class="label" >产品标准：</td>
                    <td class="ipt" colspan="3">
                        <textarea rows="10" cols="50" id="standard"  disabled="disabled">${standard}</textarea>
                    </td>
                </tr>
                <tr>
                     <td class="label">禁忌症：</td>
                    <td class="ipt"colspan="2">
                        <textarea rows="10" cols="50"  id="taboo" disabled>${taboo}</textarea>
                    </td>
                   <td class="label">产品使用范围：</td>
                    <td class="ipt"  colspan="3">
                        <textarea rows="10" cols="50" id="indications"  disabled="disabled">${indications}</textarea>
                    </td>
                </tr>
            </table>
        </div>
    </body>

    </html>