$(document).ready(function(){
    $("#History").click(function(){
        $("#searchdiv").hide();
        $("#historySearch").show();
    });

});
new Vue({
    el: "#Senti",
    data: {
        beforeurl:[],
        time:[]
    },
    mounted:function(){
        $("#historySearch").hide();
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
        }


    }
});