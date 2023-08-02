const library1 = document.getElementById("library1")
const tax = document.getElementById("1")
const name = document.getElementById("2")
const phone = document.getElementById("3")
const address = document.getElementById("4")
const email = document.getElementById("5")
library1.addEventListener("submit",function (e){
    e.preventDefault();
    const taxValue = tax.value
    const nameValue = name.value
    const phoneValue = phone.value
    const addressValue = address.value
    const emailValue = email.value
    fetch("http://localhost:8080/library/updatelibrary",
        {
            method: 'POST',
            headers:{
                'Content-Type': 'application/json'
            },
            body:JSON.stringify({
                tax: taxValue,
                name: nameValue,
                phone: phoneValue,
                address: addressValue,
                email: emailValue
            })
        }).then((data)=>{
            return data.json()
    }).then((data)=>{
        alert("cap nhat thanh cong")
    }).catch(error => console.log(error))

})