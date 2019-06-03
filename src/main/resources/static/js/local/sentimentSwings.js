$(document).ready(function () {
    new Vue({
        el: "#Senti",
        data: {
            topHigh:[],
            topLow:[],
            topHighScore:[],
            topLowScore:[],

            times:{
                startTime:"",
                endTime:""
            },
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
            SentimentSwings:function(){
                self=this;
                this.$http.get("http://localhost:8080/git/projectExist").then(function (response) {
                    var res=response.bodyText;
                    if(res=="no")
                        alert("please search project first");
                    else
                        window.location.href="/view/sentimentSwings.html"
                })
            },

            returnback:function () {
                window.location.href="SearchView"
            },

            SwingbyTime:function () {
                self=this;

                if(this.times.startTime==""||this.times.endTime==""){
                    alert("please input the time");
                }else{
                    var timeInfo=[this.times.startTime,this.times.endTime]
                    this.$http.get("http://localhost:8080/git/SwingbyTime/"+timeInfo).then(function (response) {
                        var res = response.data;

                        this.topHigh=res.topHigh;
                        this.topLow=res.topLow;
                        this.topHighScore=res.topHighScore;
                        this.topLowScore=res.topLowScore;
                    })
                }


            }


        },
        mounted(){

        },

        created(){
            this.$http.get("http://localhost:8080/git/Swing").then(function (response) {
                var res = response.data;

                this.topHigh=res.topHigh;
                this.topLow=res.topLow;
                this.topHighScore=res.topHighScore;
                this.topLowScore=res.topLowScore;
            })
        }
    })

})
