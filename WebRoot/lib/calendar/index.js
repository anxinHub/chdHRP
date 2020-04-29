
$(function() {
    $("#calendar").calendar({
        date: new Date(),
        width: 500,
        height: 300,
        /*
         * 休假和加班设置
         * JSON格式：Y加年-M加月-D加日
         * 0表示休假 1表示加班
         */
        week: false, // 是否开启单双休
        isclick: true,
        configDay: {}, // 系统配置
    });
});
