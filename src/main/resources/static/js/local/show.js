new Vue({
    el: "#Senti",
    data: {
        Commitdates: [],
        highs: [],
        lows: [],
        commitMessage:[],

        selectClass:"",
        classes:"",
        dates:[],
        codeHighs:[],
        codeLows:[],
        codeComments:[],

        codes:[]
    },
    methods: {
        commitSenti: function () {
            $("#CodeSentis").hide();
            $("#loader").show();
            self = this;
            this.$http.get("http://localhost:8080/git/commitSenti").then(function (response) {
                var res = response.data;
                this.Commitdates = res.Commitdates;
                this.highs = res.highs;
                this.lows = res.lows;
                this.commitMessage=res.commitMessage;

                var posData=[];
                var negData=[];
                var comments=[];
                for(var i=0;i<this.Commitdates.length;i++){
                    posData[i]=[this.Commitdates[i],this.highs[i]];
                    negData[i]=[this.Commitdates[i],this.lows[i]];
                    comments[i]=this.commitMessage[i];
                }
                var myChart = echarts.init(document.getElementById('CommitMessage'));
                var option = {
                    title : {
                        text : 'Commit Message Sentiment',
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend : {
                        data : ['positive','negative']
                    },
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
                        type: 'time',
                        name: 'time',

                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: 'positive',
                        type: 'scatter',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:posData
                    },{
                        name: 'negative',
                        type: 'scatter',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:negData
                    }

                    ]
                };
                myChart.setOption(option);
                $("#loader").hide();
                $("#CommitMessage").show();

                myChart.on('click',function(params){
                    $('#commitModal').modal();
                    document.getElementById("commit-body").innerHTML=comments[params.dataIndex];
                });
            })
        },
        codeSenti: function () {
            $("#loader").show();
            $("#CommitMessage").hide();
            self = this;
            this.$http.get("http://localhost:8080/git/codeSenti").then(function (response) {
                var res = response.data;
                this.classes = res;
                $("#loader").hide();
                $("#CodeSentis").show();
            })
        },

        getValue: function () {

            String.prototype.replaceAll  = function(s1,s2){
                return this.replace(new RegExp(s1,"gm"),s2);
            }

            self = this;
            var selectClass=this.selectClass;
            selectClass=selectClass.replaceAll("/",".");
            this.$http.get("http://localhost:8080/git/getSingleSenti/"+selectClass).then(function (response) {
                var res = response.data;
                this.dates = res.dates;
                this.codeHighs = res.codeHighs;
                this.codeLows = res.codeLows;
                this.codeComments=res.codeComments;

                var posData=[];
                var negData=[];
                var comments=[];

                for(var i=0;i<this.dates.length;i++){
                    posData[i]=[this.dates[i],this.codeHighs[i]];
                    negData[i]=[this.dates[i],this.codeLows[i]];
                    comments[i]=this.codeComments[i];
                }

                this.codes=res.codes;
                console.log(this.codes);
                console.log(posData);
                console.log(negData);
                var code=this.codes.join("\n");

                document.getElementById("codes").innerHTML=code;

                var myChart = echarts.init(document.getElementById('CodeComment'));
                var option = {
                    title: {
                        text: 'Code Sentiment',
                        subtext:selectClass
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend : {
                        data : ['positive','negative']
                    },
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
                        type: 'time',
                        name: 'time'
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: 'positive',
                        type: 'scatter',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:posData
                    }, {
                        name: 'negative',
                        type: 'scatter',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:negData
                    }]
                };
                myChart.setOption(option);

                if(myChart._$handlers.click){
                    myChart._$handlers.click.length = 0;
                }
                myChart.on('click',function(params){
                    $('#codeModal').modal();
                    document.getElementById("code-body").innerHTML=comments[params.dataIndex];
                });
            })
        }
    }

})

