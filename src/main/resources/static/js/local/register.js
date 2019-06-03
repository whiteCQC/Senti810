$(document).ready(function() {
    var vue=new Vue({
        el:"#toRegister",
        data:{
            errorMsg:"error!",
            register:{
                account:'',
                password:'',
                passwordConfirm:''
            }
        },
        methods:{
            registerSubmit:function () {
                self=this;
                var register=this.register;

                if(register.account==""){
                    alert("Please enter the account!");
                }
                else if (register.password==""){
                    alert("Please enter the password!");
                }else if(register.passwordConfirm==""){
                    alert("Please confirm the password!");
                }else if(register.password!=register.passwordConfirm){
                    alert("Confirmation password is not identical!");
                }
                else{
                    var registerInfo=[register.account,register.password];
                    this.$http.get("http://localhost:8080/user/register/"+registerInfo).then(function (response) {
                        var res=response.bodyText;
                        if(res=="Exist"){
                            alert("account already exist!");
                        }else if(res=="success"){
                            alert("register success!");
                            window.location.href="/"
                        }
                    })
                }

            }
        }
    })


})