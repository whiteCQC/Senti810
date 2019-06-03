$(document).ready(function() {
    var vue=new Vue({
        el:"#toLogin",
        data:{
            errorMsg:"error!",
            login:{
                account:'',
                password:''
            }
        },
        methods:{
            loginSubmit:function () {
                self=this;
                var login=this.login;

                if(login.account==""){
                    alert("Please enter the account!");
                }
                else if (login.password==""){
                    alert("Please enter the password!");
                }
                else{
                    var loginInfo=[login.account,login.password];
                    this.$http.get("http://localhost:8080/user/login/"+loginInfo).then(function (response) {
                        var res=response.bodyText;
                        if(res=="not Exist"){
                            alert("account not exist or password wrong");
                        }else if(res=="success"){
                            window.location.href="/search/input"
                        }
                    })
                }

            },
            toRegister:function () {
                window.location.href="/view/register.html"
            }
        }
    })


})