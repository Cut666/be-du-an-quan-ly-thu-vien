// const register = document.getElementById("register-form")
// const username = document.getElementById("username")
// const password = document.getElementById("password")
// const email = document.getElementById("email")
//
// register.addEventListener("submit", function (e) {
//     e.preventDefault();
//
//     const usernameValue = username.value;
//     const passwordValue = password.value;
//     const emailValue = email.value;
//
//     fetch("http://localhost:8080/api/auth/signup",
//         {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify({
//                 username: usernameValue,
//                 password: passwordValue,
//                 email: emailValue
//             })
//         }).then((data) => {
//         return data.json()
//
//     }).then((data) => {alert(data.message)
//         // window.location.href='/view/signin'
//         })
//         .catch(error => console.log(error))
// })