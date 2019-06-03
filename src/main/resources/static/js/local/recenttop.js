new Vue({
    el: "#Senti",
    data: {
        variancesP:[],
        variancesN:[],
        variancesPTitle:[],
        variancesNTitle:[],
        date:[],
        sentiOfPos:[],
        sentiOfNeg:[],
        issueWeb:"",
        issueData:[],
        usedData:[]
    },
    mounted:function(){
        $("#loader").show();
        this.recenttop();

    },
    methods:{
        recenttop: function () {
            self = this;
            this.$http.get("http://localhost:8080/search/recentdata").then(function (response) {
                var res = response.data;
                var variance=res[0];
                var changes=res[1];
                var posV=[];
                var negV=[];
                var dealposV=[];
                var dealnegV=[];
                var posVTitle=variance[2];
                var negVTitle=variance[3];
                this.variancesPTitle=posVTitle;
                this.variancesNTitle=negVTitle;
                var index=variance[0][0].lastIndexOf("/");
                var issueWeb=variance[0][0].substring(0,index+1);
                this.issueWeb=issueWeb;
                for(var i=0;i<variance[0].length;i++){
                    posV[i]=variance[0][i].substring(variance[0][i].lastIndexOf("/")+1);
                    negV[i]=variance[1][i].substring(variance[1][i].lastIndexOf("/")+1);
                }
                if(posV.length>5){//如果大于5，则只截取前五,否则全部保留
                    for(var i=0;i<5;i++){
                        dealposV[i]=posV[i];
                        dealnegV[i]=negV[i];
                    }
                }else{
                    dealposV=posV;
                    dealnegV=negV;
                }

                var dates=changes[0];
                var sentiP=changes[1];
                var sentiN=changes[2];
                var relative=changes[3];
                for(var i=0;i<relative.length;i++){
                    for(var j=0;j<relative[i].length;j++){
                        var ind=relative[i][j].lastIndexOf("/");
                        relative[i][j]=relative[i][j].substring(ind+1);
                    }
                }
                this.issueData=relative;
                this.usedData=relative[0];
                this.variancesP=dealposV;
                this.variancesN=dealnegV;
                this.date=dates;
                this.sentiOfPos=sentiP;
                this.sentiOfNeg=sentiN;
                console.log(this.date);
                var myChart = echarts.init(document.getElementById('monthcharts'));
                var option = {
                    title : {
                        text : 'Senti Change',
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
                        data:dates
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
                        data:sentiP,
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
                        data:sentiN,
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
            $("#loader").hide();
        },
        mousedown:function(){

            //          console.log(Xindex);
            for(var i=0;i<this.issueData.length;i++) {
                if (Xindex == i) {
                    this.usedData = this.issueData[i];
                }
            }



        },
        sendurl:function(e){
            var issueweb=this.issueWeb.substring(19)+e;
            console.log(issueweb);
            window.location.href="/search/issueComments?issueweb="+issueweb;
        },


    }
});