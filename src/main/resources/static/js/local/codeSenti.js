$(document).ready(function () {
    new Vue({
        el: "#Senti",
        data: {
            selectClass: "",//当前选择查看的类
            classes: "",//文件结构的树形结构
            dates: [],//修改日期
            codeHighs: [],//正面情绪
            codeLows: [],//负面
            codeComments: [],//注释

            notes:[],//留言
            noteTimes:[],//留言时间

            codes: [],//当前代码

            toSearch: "",

            CommitClasses:[],//每次Commit涉及的类
            CommitMessages:[]//commit的message
        },
        methods: {
            commitSenti:function () {
                self=this;
                this.$http.get("http://localhost:8080/git/projectExist").then(function (response) {
                    var res=response.bodyText;
                    if(res=="no")
                        alert("please search project first");
                    else
                        window.location.href="/view/showCommit.html"
                })
            },
            codeSenti:function () {
                self=this;
                this.$http.get("http://localhost:8080/git/projectExist").then(function (response) {
                    var res=response.bodyText;
                    if(res=="no")
                        alert("please search project first");
                    else
                        window.location.href="/view/showCode.html"
                })
            },

            returnback:function () {
                window.location.href="SearchView"
            },
            SubmitNote:function () {
                var oWrap=document.getElementsByClassName("wrap")[0];
                var oTextarea=oWrap.getElementsByTagName("textarea")[0];
                var oBtn=oWrap.getElementsByTagName("input")[0];
                var oUl=oWrap.getElementsByTagName("ul")[0];
                var errMsg=oWrap.getElementsByClassName("errmsg")[0];

                var t=new Date();
                var Year=t.getFullYear();
                var Month=t.getMonth()+1;
                var Day=t.getDate();
                var Hours=t.getHours();
                var Minutes=t.getMinutes();
                var Scondes=t.getSeconds();
                var timers=toString(Year)+"年"+toString(Month)+"月"+toString(Day)+"日"+toString(Hours)+":"+toString(Minutes)+":"+toString(Scondes);//将获取到的日期对象拼接。

                function toString(n)//数字转字符串
                {
                    if(n<9)
                    {
                        n="0"+n;
                    }
                    else
                    {
                        n=""+n;
                    }
                    return n;
                };
                var self=this

                oBtn.onclick=function()
                {
                    if(oTextarea.value=="")
                    {
                        startMove(errMsg,{opacity:100});
                        //console.log(errMsg.style.opacity)
                        oTextarea.select();
                    }
                    else
                    {
                        var noteInfo=[oTextarea.value,timers,self.selectClass];
                        self.$http.get("http://localhost:8080/git/addNote/"+timers+"?selectClass="+self.selectClass+"&&note="+oTextarea.value).then(function (response) {

                        })

                        var aLi=document.createElement("li");
                        var aSpan=document.createElement("span");
                        aLi.innerHTML=oTextarea.value;
                        aSpan.innerHTML=timers;
                        if(oUl.children.length>0)
                        {
                            oUl.insertBefore(aLi,oUl.children[0]);
                            aLi.appendChild(aSpan);
                            oTextarea.value="";
                        }
                        else
                        {
                            oUl.appendChild(aLi);
                            aLi.appendChild(aSpan);
                            oTextarea.value="";
                        };
                        var aLiHeight=parseInt(getStyle(aLi,"height"));

                        aLi.style.height="0";
                        startMove(aLi,{height:aLiHeight},function(){
                            startMove(aLi,{opacity:100});
                        });

                        aLi.style.opacity="1";
                    }
                };


            }

        },
        mounted(){

        },

        created() {
            $("#loader").show();
            $("#CommitMessage").hide();

            String.prototype.replaceAll  = function(s1,s2){
                return this.replace(new RegExp(s1,"gm"),s2);
            }

            var sclass=window.location.href.split("?selectClass=");
            if(sclass.length==2){
                var temp=sclass[1].split("/")
                this.selectClass=temp[temp.length-1];

                this.$http.get("http://localhost:8080/git/getSingleSenti/" + sclass[1].replaceAll("/",".")).then(function (response) {

                    var res = response.data;
                    this.dates = res.dates;
                    this.codeHighs = res.codeHighs;
                    this.codeLows = res.codeLows;
                    this.codeComments = res.codeComments;

                    this.notes=res.notes;
                    this.noteTimes=res.noteTimes;

                    this.CommitClasses=res.CommitClasses;
                    this.CommitMessages=res.CommitMessages;

                    var oWrap=document.getElementsByClassName("wrap")[0];
                    var oUl=oWrap.getElementsByTagName("ul")[0];

                    oUl.innerHTML="";
                    for(var i=0;i<this.notes.length;i++){
                        var aLi=document.createElement("li");
                        var aSpan=document.createElement("span");
                        aLi.innerHTML=this.notes[i];
                        aSpan.innerHTML=this.noteTimes[i];
                        if(oUl.children.length>0)
                        {
                            oUl.insertBefore(aLi,oUl.children[0]);
                            aLi.appendChild(aSpan);
                        }
                        else
                        {
                            oUl.appendChild(aLi);
                            aLi.appendChild(aSpan);
                        };
                        var aLiHeight=parseInt(getStyle(aLi,"height"));

                        aLi.style.height="0";
                        startMove(aLi,{height:aLiHeight},function(){
                            startMove(aLi,{opacity:100});
                        });

                    }
                    var lis=oWrap.getElementsByTagName("li");
                    for(var i=0;i<lis.length;i++){
                        lis[i].style.opacity=1;
                    }


                    var posData = [];
                    var negData = [];
                    var comments = [];
                    var messages=[];
                    var rclasses=[];
                    console.log(this.dates)
                    console.log(this.CommitMessages)
                    console.log(this.CommitClasses)

                    for (var i = 0; i < this.dates.length; i++) {
                        posData[i] = [this.dates[i], this.codeHighs[i]];
                        negData[i] = [this.dates[i], this.codeLows[i]];
                        comments[i] = this.codeComments[i];
                        messages[i]=this.CommitMessages[i];
                        rclasses[i]=this.CommitClasses[i];
                    }

                    this.codes = res.codes;
                    var code = this.codes.join("\n");

                    document.getElementById("codes").innerHTML = code;

                    var myChart = echarts.init(document.getElementById('CodeComment'), 'macarons');
                    var option = {
                        title: {
                            text: '代码情绪变化',
                            subtext: this.selectClass
                        },
                        tooltip: {
                            trigger: 'item'
                        },
                        legend: {
                            data: ['positive', 'negative']
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
                            type: 'line',
                            showAllSymbol: true,
                            symbolSize: 10,
                            data: posData
                        }, {
                            name: 'negative',
                            type: 'line',
                            showAllSymbol: true,
                            symbolSize: 10,
                            data: negData
                        }]
                    };
                    myChart.setOption(option);

                    if (myChart._$handlers.click) {
                        myChart._$handlers.click.length = 0;
                    }
                    myChart.on('click', function (params) {
                        document.getElementById("commentVary").innerHTML = comments[params.dataIndex];
                        document.getElementById("Commit_Message").innerHTML = messages[params.dataIndex];
                        document.getElementById("CommitClasses").innerHTML = rclasses[params.dataIndex];
                    });
                })
            }


            var sc="nothing"
            if(sclass.length==2)
                sc=sclass[1].replaceAll('/','.')

            var self=this;
            this.$http.get("http://localhost:8080/git/codeSenti/"+sc).then(function (response) {
                var res = response.data;
                this.classes = res;

                $(function () {
                    $('#allClasses').jstree({
                        'core': {
                            'data': res
                        },
                    });

                    var tree = $('#allClasses').jstree();

                    $('#allClasses').on('changed.jstree', function (e, data) {//点击展开
                        $('#allClasses').jstree(true).toggle_node(data.selected);
                    })


                    $('#allClasses').on("changed.jstree", function (e, data) {
                        var id = data.instance.get_node(data.selected).id;
                        if (tree.is_leaf(id)) {
                            self.selectClass = id;

                            self.$http.get("http://localhost:8080/git/getSingleSenti/" + self.selectClass).then(function (response) {
                                var res = response.data;
                                self.dates = res.dates;
                                self.codeHighs = res.codeHighs;
                                self.codeLows = res.codeLows;
                                self.codeComments = res.codeComments;

                                self.notes=res.notes;
                                self.noteTimes=res.noteTimes;
                                self.CommitClasses=res.CommitClasses;
                                self.CommitMessages=res.CommitMessages;

                                var oWrap=document.getElementsByClassName("wrap")[0];
                                var oUl=oWrap.getElementsByTagName("ul")[0];

                                oUl.innerHTML="";
                                for(var i=0;i<self.notes.length;i++){
                                    var aLi=document.createElement("li");
                                    var aSpan=document.createElement("span");
                                    aLi.innerHTML=self.notes[i];
                                    aSpan.innerHTML=self.noteTimes[i];
                                    if(oUl.children.length>0)
                                    {
                                        oUl.insertBefore(aLi,oUl.children[0]);
                                        aLi.appendChild(aSpan);
                                    }
                                    else
                                    {
                                        oUl.appendChild(aLi);
                                        aLi.appendChild(aSpan);
                                    };
                                    var aLiHeight=parseInt(getStyle(aLi,"height"));

                                    aLi.style.height="0";
                                    startMove(aLi,{height:aLiHeight},function(){
                                        startMove(aLi,{opacity:100});
                                    });
                                }
                                var lis=oWrap.getElementsByTagName("li");
                                for(var i=0;i<lis.length;i++){
                                    lis[i].style.opacity=1;
                                }

                                var posData = [];
                                var negData = [];
                                var comments = [];
                                var messages=[];
                                var rclasses=[];

                                for (var i = 0; i < this.dates.length; i++) {
                                    posData[i] = [self.dates[i], self.codeHighs[i]];
                                    negData[i] = [self.dates[i], self.codeLows[i]];
                                    comments[i] = self.codeComments[i];
                                    messages[i]=self.CommitMessages[i];
                                    rclasses[i]=self.CommitClasses[i];
                                }

                                self.codes = res.codes;
                                var code = self.codes.join("\n");

                                document.getElementById("codes").innerHTML = code;

                                var myChart = echarts.init(document.getElementById('CodeComment'), 'macarons');
                                var option = {
                                    title: {
                                        text: '代码情绪变化',
                                        subtext: self.selectClass
                                    },
                                    tooltip: {
                                        trigger: 'item'
                                    },
                                    legend: {
                                        data: ['positive', 'negative']
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
                                        type: 'line',
                                        showAllSymbol: true,
                                        symbolSize: 10,
                                        data: posData
                                    }, {
                                        name: 'negative',
                                        type: 'line',
                                        showAllSymbol: true,
                                        symbolSize: 10,
                                        data: negData
                                    }]
                                };
                                myChart.setOption(option);

                                if (myChart._$handlers.click) {
                                    myChart._$handlers.click.length = 0;
                                }
                                myChart.on('click', function (params) {
                                    document.getElementById("commentVary").innerHTML = comments[params.dataIndex];
                                    document.getElementById("Commit_Message").innerHTML = messages[params.dataIndex];
                                    document.getElementById("CommitClasses").innerHTML = rclasses[params.dataIndex];
                                });
                            })
                        }

                    });
                })


                $("#loader").hide();
                $("#CodeSentis").show();
            })
        }
    })

})

//运动框架
function startMove(obj,json,endFn){
    clearInterval(obj.timer);
    obj.timer=setInterval(
        function(){

            var iStop=true;
            for(var curr in json)
            {
                var oNumber=0;
                if(curr=="opacity")
                {
                    oNumber=Math.round(parseFloat(getStyle(obj,curr))*100);
                }
                else
                {
                    oNumber=parseInt(getStyle(obj,curr));
                }
                var speed=(json[curr]-oNumber)/6;
                speed=speed>0?Math.ceil(speed):Math.floor(speed);
                if(oNumber!=json[curr])
                    iStop=false;
                if(curr=="opacity")
                {
                    obj.style.filter="alpha(opacity:"+(oNumber+speed)+")";
                    obj.style.opacity=(oNumber+speed)/100;
                }
                else
                {
                    obj.style[curr]=oNumber+speed+"px";
                }
            };
            if(iStop)
            {
                clearInterval(obj.timer);
                if(endFn)endFn();
            }
        },30);
};

//获取非行间样式
function getStyle(obj,name)
{
    if(obj.currentStyle)
    {
        obj=currentStyle[name];
    }
    else
    {
        obj=getComputedStyle(obj,false)[name];
    }
    return obj;
};