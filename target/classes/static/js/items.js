/* services card items .................*/

const serviceCard=[

	   {
		  "id":"01",
		  "url":"https://img.freepik.com/free-photo/mobile-banking-financial-technology-with-people-using-phones-background_53876-104205.jpg?t=st=1728633913~exp=1728634513~hmac=e27fd743e51cd5137aea6459ed1563f60583971e52cb51592c43251a7c5e2f5e",
		  "serviceType":"Mobile Banking",
		  "btnText":"Explore Now"
		},
		{
			"id":"02",
			"url":"https://img.freepik.com/free-photo/pay-goods-by-credit-card-through-smartphone-coffee-shop_1150-18770.jpg?w=1380&t=st=1728634138~exp=1728634738~hmac=ea4d4d2963b0aeb95f91bd846572a7681fb8ec5d0606dab3a870d36715c2e9cf",
			"serviceType":"Net Banking",
			"btnText":"Click here"
		},
	    {
			"id":"03",
			"url":"/image/atm.png",
			"serviceType":"Card Service",
			"btnText":"Order Now"	
		},
		{
		    "id":"04",
			"url":"/image/guideimage.png",
			"serviceType":"Services guidance",
			"btnText":"Take a tour"
		}		
]


function showAllServicesCard(){
	
	let servicesFeature = document.querySelector(".servicesFeature");
	
	serviceCard.forEach(item =>{
		
		servicesFeature.innerHTML +=

		`
		<div class="ServiceCard">
		    <div class="serviceCardImgDiv">
		       <img src="${item.url}" alt="" />
		    </div>
		    <div class="serviceCardDetails">
		        <h1>${item.serviceType}</h1>
		        <button>${item.btnText}</button>
		    </div>
		</div>
		`
	})	
}

showAllServicesCard();







/* services card items .................*/

const cardServiceee=[

	{
	 "id":"01",
	 "url":"/image/atm.png",
	 "p1":"",
	 "p2":"",
	 "p3":"",
	 "btnText":"Order Now"
 },
 {
	 "id":"02",
"url":"/image/atm.png",
    "p1":"",
	 "p2":"",
	 "p3":"",
	 "btnText":"Click here"
 },
	 {
	 "id":"03",
	 "url":"/image/atm.png",
	  "p1":"",
	 "p2":"",
	 "p3":"",
	 "btnText":"Get Details"	
 },
 {
		 "id":"04",
	 "url":"/image/atm.png",
	  "p1":"",
	 "p2":"",
	 "p3":"",
	 "btnText":"Order Now"
 }		
]


function showAllAtms(){

let cardContainer = document.querySelector(".cardContainer");

cardServiceee.forEach(itemm =>{
 
	cardContainer.innerHTML +=

 `
              <div class="atmCardParent">
            <div class="atmCardDiv">
              <img src="${itemm.url}" alt="atmImg">
            </div>
            <div class="atmCardDetails">
              <p>↬    ATM Facilities</p>
              <p>↬    Point of Sale</p>
              <p>↬    Online Shoping....</p>
            </div>
            <div class="atmCardButton">
              <a href="" style="width: 100%;"><div class="btn btn-primary btn-block atmCardOrderButton" style="height: 3vw;">${itemm.btnText}</div></a>
            </div>
         </div>
 `
})	
};

showAllAtms();