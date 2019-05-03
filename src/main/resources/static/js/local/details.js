$(document).ready(function(){
    $("#showYear").click(function(){
        $("#yeartable").show();
        $("#monthtable").hide();
        $("#monthcharts").hide();
    });
    $("#showMonth").click(function(){
        $("#yeartable").hide();
        $("#monthtable").show();
        $("#monthcharts").hide();
    });
    $("#showMonthChart").click(function(){
        $("#yeartable").hide();
        $("#monthtable").hide();
        $("#monthcharts").show();
    });

});
new Vue({
    el: "#Senti",
    data: {
        year2019dataP:[],
        year2018dataP:[],
        year2017dataP:[],
        month2019dataP:[],
        month2018dataP:[],
        month2017dataP:[],
        year2019dataN:[],
        year2018dataN:[],
        year2017dataN:[],
        month2019dataN:[],
        month2018dataN:[],
        month2017dataN:[]
    },
    mounted:function(){
        this.yeartop();

    },
    methods:{
        yeartop: function () {
            self = this;
            this.$http.get("http://localhost:8080/search/details").then(function (response) {
                var res = response.data;
                var y_7_p=[];
                var y_8_p=[];
                var y_9_p=[];
                var y_7_n=[];
                var y_8_n=[];
                var y_9_n=[];
                for (var j = 0; j <10; j++) {
                    if(j<5){
                        y_7_p[j]=res[0][j];
                        y_8_p[j]=res[1][j];
                        y_9_p[j]=res[2][j];
                    }
                    if(j>=5){
                        y_7_n[j-5]=res[0][j];
                        y_8_n[j-5]=res[1][j];
                        y_9_n[j-5]=res[2][j];
                    }
                }
                this.year2017dataP=y_7_p;
                this.year2018dataP=y_8_p;
                this.year2019dataP=y_9_p;
                this.year2017dataN=y_7_n;
                this.year2018dataN=y_8_n;
                this.year2019dataN=y_9_n;
                console.log(this.year2019dataP);
            })
        },
        monthtop:function(){
            self = this;
            this.$http.get("http://localhost:8080/search/details_month").then(function (response) {
                var res = response.data;
                var m_7_p=[];
                var m_8_p=[];
                var m_9_p=[];
                var m_7_n=[];
                var m_8_n=[];
                var m_9_n=[];
                for (var j = 0; j < 24; j++) {
                    if(j<12){
                        m_7_p[j]=res[0][j];
                        m_8_p[j]=res[1][j];
                        m_9_p[j]=res[2][j];
                    }
                    if(j>=12){
                        m_7_n[j-12]=res[0][j];
                        m_8_n[j-12]=res[1][j];
                        m_9_n[j-12]=res[2][j];
                    }
                }
                this.month2017dataP=m_7_p;
                this.month2018dataP=m_8_p;
                this.month2019dataP=m_9_p;
                this.month2017dataN=m_7_n;
                this.month2018dataN=m_8_n;
                this.month2019dataN=m_9_n;
                console.log(this.month2019dataP);

            })
        },
        monthchart:function(){
            self=this;
            this.$http.get("http://localhost:8080/search/month_chart").then(function (response) {
                var res = response.data;
                var posData=[];
                var negData=[];
                var datetime=[];
                for(var i=0;i<27;i++){
                    posData[i]=res[0][i];
                    negData[i]=res[1][i];
                }
                console.log(posData);
                var myChart = echarts.init(document.getElementById('monthcharts'));
                var option = {
                    title : {
                        text : 'Issue Sentiment Monthly',
                        subtext: '注：数值为0表示该月没有评论'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend : {
                        data : ['positive','negative']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    dataZoom: {
                        show: true,
                        realtime: true,
                        start: 0,
                        end: 100
                    },
                    grid: {
                        y2: 80
                    },
                    xAxis: {
                        data:["17-01","17-02","17-03","17-04","17-05","17-06","17-07","17-08","17-09","17-10","17-11","17-02","17-12"
                            ,"18-01","18-02","18-03","18-04","18-05","18-06","18-07","18-08","18-09","18-10","18-11","18-12","19-01","19-02","19-03"]

                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: 'positive',
                        type: 'line',
                        smooth:'true',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:posData,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }

                    },{
                        name: 'negative',
                        type: 'line',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:negData,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    },

                    ]
                };
                myChart.setOption(option);
            })
        },
        sendurl:function(e){
            var issueweb=e.substring(19);
            console.log(issueweb);
            window.location.href="/search/issueComments?issueweb="+issueweb;
        }


    }
});
