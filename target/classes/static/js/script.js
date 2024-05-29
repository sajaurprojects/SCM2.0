console.log("Script loading...........");

// let currentTheme="light";
let currentTheme= getTheme();

document.addEventListener('DOMContentLoaded',()=>{
    changeTheme();
});



function changeTheme(){
  //set currenttheme to web page

  document.querySelector('html').classList.add(currentTheme);

  //apply listner to change theme of button
 const changeThemeBtn = document.querySelector("#theme_change_btn");
changeThemeBtn.addEventListener("click",()=>{
   
    //change the text of button
    changeThemeBtn.querySelector('span').textContent=currentTheme == 'light'?'dark':'light';
  console.log("Btn clicked theme")

  const oldTheme = currentTheme;
  

  if(currentTheme=== "dark"){
    //do theme light
    currentTheme="light";
  }
  else{
    //do theme dark
    currentTheme="dark";
  }

  //if themes on web page and localstorage is same then update theme
  setTheme(currentTheme);
     //remove the current theme secondly
     document.querySelector('html').classList.remove(oldTheme);
  //set the current theme firstly
  document.querySelector('html').classList.add(currentTheme);

  //change the text of button

//   changeThemeBtn.querySelector('span').textContent=currentTheme == 'light'?'dark':'light';

});


}

//set theme to local storage
 function setTheme(theme){
    localStorage.setItem("theme",theme);
 }

 //get theme
 function getTheme(){
    let theme= localStorage.getItem("theme");

    // if(theme){
    //     return theme;
    // }
    // else{
    //     return "light";
    // } OR

    return theme ? theme : "light";

 }