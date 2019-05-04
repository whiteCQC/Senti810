$(document).ready(function(){
    $("#History").click(function(){
        $("#searchdiv").hide();
        $("#historySearch").show();
        $("#RecentDiv").hide();
    });
    $("#Recent").click(function(){
        $("#searchdiv").hide();
        $("#historySearch").hide();
        $("#RecentDiv").show();
    });

});
new Vue({
    el: "#Senti",
    data: {
        beforeurl:[],//历史记录那边的url
        time:[],//历史记录那边的time
        recenturl:[],//最近项目那边的url
        recenttime:[],//最近项目那边的修改项目的时间
        realtime:[]////最近项目创立的时间
    },
    mounted:function(){
        $("#historySearch").hide();
        $("#RecentDiv").hide();
    },
    methods:{
        history:function(){
            self = this;
            this.$http.get("http://localhost:8080/search/hisSearch").then(function (response) {
                var res=response.data;
                var urls=[];
                var times=[];
                for(var i=0;i<res.length;i++){
                    urls[i]=res[i][1];
                    times[i]=res[i][2];
                }
                this.beforeurl=urls;
                this.time=times;
                console.log(this.beforeurl);
                console.log(this.time);
            })
        },
        sendurl:function(url, time){
            var issueweb=url;
            var searchtime=time;
            console.log(issueweb);
            window.location.href="/search/more?issueweb="+issueweb+"&searchtime="+searchtime;
        },
        recent:function(){
            self = this;
            this.$http.get("http://localhost:8080/search/Recent").then(function (response) {
                var res=response.data;
                var urls=[];
                var times=[];
                var realtimes=[];
                for(var i=0;i<res.length;i++){
                    urls[i]=res[i][1];
                    times[i]=res[i][3];
                    realtimes[i]=res[i][2];
                }
                this.recenturl=urls;
                this.recenttime=times;
                this.realtime=realtimes;
                console.log(this.recenturl);
                console.log(this.recenttime);
            })
        },
        wait:function(){
            $("#searchdiv").hide();
            $("#loader").show();


        }


    }
});