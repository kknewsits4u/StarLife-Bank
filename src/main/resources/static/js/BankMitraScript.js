
let BankMitraspinnerDiv = document.querySelector(".BankMitraspinnerDiv");
let bank_mitra_sidebar = document.querySelector(".bank_mitra_sidebar");


let BankMitraspinnerDivFlag = 1;
BankMitraspinnerDiv.addEventListener("click" , function(){
if(BankMitraspinnerDivFlag == 1){
  bank_mitra_sidebar.style.left="0%";
  BankMitraspinnerDivFlag =0;
}
else{
  bank_mitra_sidebar.style.left="-100%"; 
  BankMitraspinnerDivFlag = 1;
}

})