//指令状态
var resultData = [
					{ id: 0, text: '提交成功,等待银行处理' },
                    { id: 1, text: '授权成功, 等待银行处理' }, 
                    { id: 2, text: '等待授权' },
                    { id: 3, text: '等待二次授权' },
                    { id: 4, text: '等待银行答复' },
                    { id: 5, text: '主机返回待处理' },
                    { id: 6, text: '被银行拒绝' },
                    { id: 7, text: '处理成功' },
                    { id: 8, text: '指令被拒绝授权' },
                    { id: 9, text: '银行正在处理' },
                    { id: 10, text: '预约指令' },
                    { id: 11, text: '预约取消' },
                    { id: 86, text: '等待电话核实' },
                    { id: 95, text: '待核查' },
                    { id: 95, text: '区域中心通讯可疑' },
                    ];

//入账方式
var settleModeData = [
                  { id: 0, text: '逐笔记账' },
                  { id: 2, text: '并笔入账' }
                  ];
//入账方式
var payTypeData = [
                  { text: '加急', id: '1' },
                  { text: '普通', id: '2' }
                  ];
//记账处理方式
var bankSettleData=[
                    { text: '个人账户', id: '1' },
                    { text: '对公账户', id: '0' }
                ];
//币种
var curCodeData=[
                 { text: '人民币', id: '001' }
                 ];

//同城异城
var isSameCityData=[
                 { text: '同城', id: '1' },
                 { text: '异地', id: '2' }
                 ];

//同城异城
var is_bank_same_data=[
                 { text: '否', id: '0' },
                 { text: '是', id: '1' }
                 ];
//同城异城
var is_city_same_data=[
                 { text: '否', id: '0' },
                 { text: '是', id: '1' }
                 ];

//币种
var sysIOFlgData=[
                 { text: '系统内', id: '1' },
                 { text: '系统外', id: '2' }
                 ];
