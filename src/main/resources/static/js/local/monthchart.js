new Vue({
    el: "#Senti",
    data: {
        issueData:[],
        usedData:[],
        issueWeb:"",
        yourComment:""
    },
    mounted:function(){
        this.monthchart();
        this.getComment();
    },
    methods:{
        monthchart:function(){
            $("#mainall").hide();
            $("#loader").show();
            self=this;
            this.$http.get("http://localhost:8080/search/month_chart").then(function (response) {
                var res = response.data;
                var posData=[];
                var negData=[];
                var datetime=[];
                var data1=res[0];//double[][]
                var data2=res[1];//ArrayList<ArrayList<String>>
                for(var i=0;i<data2.length;i++){
                    for(var j=0;j<data2[i].length;j++){
                        var index=data2[i][j].lastIndexOf("/");
                        data2[i][j]=data2[i][j].substring(index+1);
                    }
                }
                this.issueData=data2;
                this.usedData=data2[0];
                this.issueWeb=res[2];
                for(var i=0;i<27;i++){
                    posData[i]=data1[0][i];
                    negData[i]=data1[1][i];
                }
                console.log(posData);
                var myChart = echarts.init(document.getElementById('monthcharts'));
                var option = {
                    title : {
                        text : 'Issue Sentiment Monthly',
                        subtext: '注：数值为0表示该月没有评论'
                    },
                    tooltip: {
                        trigger: 'axis',
                        formatter:function(params){
                            var html = '';
                            if(params.length>0){
                                Xindex = params[0].dataIndex;
                                for ( var int = 0; int < params.length; int++) {
                                    html+=params[int].seriesName+':'+params[int].data+'<br>';
                                }
                            }
                            return html;

                        }
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
            $("#mainall").show();
            $("#loader").hide();
        },
        mousedown:function(){

  //          console.log(Xindex);
            for(var i=0;i<27;i++) {
                if (Xindex == i) {
                    this.usedData = this.issueData[i];
                }
            }



        },
        sendurl:function(e){
            var issueweb=this.issueWeb.substring(19)+"/issues/"+e;
            console.log(issueweb);
            window.location.href="/search/issueComments?issueweb="+issueweb;
        },
        getComment:function(){
            self=this;
            this.$http.get("http://localhost:8080/search/historyComment").then(function (response) {
                var res=response.data;
                this.yourComment=res[0];
            })
        }


    }
});