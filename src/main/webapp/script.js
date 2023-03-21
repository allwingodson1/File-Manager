/**
 * 
 */
function toggle(element){
    var signin = document.getElementById("signin");
    var signup = document.getElementById("signup");
    if(element.innerText == "Sign Up"){
        signin.className = 'hide';
        signup.className = '';
    }else{
        signup.className = 'hide';
        signin.className = '';
    }
}
function login(){
    var xhr = new XMLHttpRequest();
    var username = document.querySelector("#signin input[type='text']").value;
    var password = document.querySelector("#signin input[type='password']").value;
    xhr.open("POST","signinserv");
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhr.onreadystatechange = ()=>{
        if(xhr.readyState == 4){
            var res = JSON.parse(xhr.responseText);
            if(res.StatusCode == 200){
                home();
            }
        }
    }
    xhr.send("username="+username+"&password="+password);
}
function signUp(){
    var xhr = new XMLHttpRequest();
    var username = document.querySelector("#signup input[type='text']").value;
    var password = document.querySelector("#signup input[type='password']").value;
    var number = document.querySelector("#signup input[type='number']").value;
    xhr.open("POST","signup");
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhr.onreadystatechange = ()=>{
        if(xhr.readyState == 4){
            var res = JSON.parse(xhr.responseText);
            if(res.StatusCode == 200){
                home();
            }
        }
    }
    xhr.send("username="+username+"&password="+password+"&number="+number);
}

function home(){
    window.location.href = 'myPage.html';
    document.getElementById("login").className = 'hide';
    document.getElementById("home").classList.remove('hide');
}