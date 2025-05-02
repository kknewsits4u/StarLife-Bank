let sideBarSlider = document.querySelector(".sideBarSlider");
let leftSide = document.querySelector(".leftSide");


let sideBarFlag = 1;
sideBarSlider.addEventListener("click" , function(){
if(sideBarFlag == 1){
  leftSide.style.left="0%";
  sideBarSlider.style.zIndex="500"
  sideBarSlider.style.left="60%"
  sideBarSlider.style.top="6vw"
  sideBarSlider.style.rotate="180deg"
  sideBarFlag =0;
}
else{
  leftSide.style.left="-100%"; 
  sideBarSlider.style.zIndex="500"
  sideBarSlider.style.left="5vw"
  sideBarSlider.style.top="1vw"
  sideBarSlider.style.rotate="0deg"
  sideBarFlag =1;
}

})