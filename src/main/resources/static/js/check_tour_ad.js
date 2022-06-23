let nameBox = ".user";
const STYLE_SORT_BUTTON = "button-sort-used";

// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+
let sortDescendingBtn = document.getElementById('sortDescending');
let sortAscendingBtn = document.getElementById('sortAscending');

let how_sort_number = function(a,b) { return a-b};
let how_sort_string = function(a,b) {return (a > b) - (a < b)};

function sortDescending(){

  sortDescendingBtn.classList.add(STYLE_SORT_BUTTON);
  sortAscendingBtn.classList.remove(STYLE_SORT_BUTTON);

  how_sort_number = function(a,b) { return b-a};
  how_sort_string = function(a,b) {return (b > a) - (b < a)};

  getMethodBySelectedName(sb.options[sb.selectedIndex].id);
  btn.classList.remove(styleSelectSort);
}

function sortAscending(){

  sortAscendingBtn.classList.add(STYLE_SORT_BUTTON);
  sortDescendingBtn.classList.remove(STYLE_SORT_BUTTON);

  how_sort_number = function(a,b) { return a-b};
  how_sort_string = function(a,b) {return (a > b) - (a < b)};

  getMethodBySelectedName(sb.options[sb.selectedIndex].id);
  btn.classList.remove(styleSelectSort);
}

sortDescendingBtn.addEventListener('click', sortDescending);
sortAscendingBtn.addEventListener('click', sortAscending);

// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+
const btn = document.querySelector('#selectSort');
const sb = document.querySelector('#selectSort');
const styleSelectSort = "user-selected-button";
btn.onclick = (event) => {
 event.preventDefault();
    if(btn.classList.contains(styleSelectSort)){
      event.preventDefault();
      getMethodBySelectedName(sb.options[sb.selectedIndex].id);
      btn.classList.remove(styleSelectSort);
    }else{
      btn.classList.add(styleSelectSort);
    }
    
};

// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+
const classNameTravelAgency = '.item_name-travel-agency'; // 9

const classCost = '.item_cost';//2

const classStartDate = '.item_start-date';//3


const classCityName = '.item_city-name';//4

const classDiscountTourAd = '.item_discount-tour-ad';//5

const classEndDate = '.item_end-date';

const clasPlaceName = '.item_place-name';


const classNumberOfPeople = '.item_number-of-people';

const classCountryName = '.item_country-name';

const classDateRegistration = '.item_date-registration';

// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+

function getMethodBySelectedName(selectedName){
    console.log(selectedName);
    if(selectedName == 'sortByNameTravelAgency'){
      sortDivs(classNameTravelAgency,how_sort_string);
      return;
    }
    if(selectedName == 'sortByCost'){
      sortDivs(classCost,how_sort_number);
      return;
    }
    if(selectedName == 'sortByStartDate'){
      sortDivs(classStartDate,how_sort_string);
      return;
    }
    if(selectedName == 'sortByCityName'){
      sortDivs(classCityName,how_sort_string);
      return;
    }
    if(selectedName == 'sortByDiscountTourAd'){
      sortDivs(classDiscountTourAd,how_sort_number);
      return;
    }
    if(selectedName == 'sortByEndDate'){
      sortDivs(classEndDate,how_sort_string);
      return;
    }
    if(selectedName == 'sortByPlaceName'){
      sortDivs(classPlaceName,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNumberOfPeople'){
      sortDivs(classNumberOfPeople,how_sort_number);
      return;
    }
    if(selectedName == 'sortByCountryName'){
      sortDivs(classCountryName,how_sort_string);
      return;
    }
    if(selectedName == 'sortByDateRegistration'){
      sortDivs(classDateRegistration,how_sort_string);
      return;
    }
   
}


function sortDivs(fieldForSort,sortFunction){
  var items = document.querySelectorAll(nameBox);

  // get all items as an array and call the sort method
  Array.from(items).sort(function(a, b) {
    // get the text content
    a = a.querySelector(fieldForSort).innerText.toLowerCase()
    b = b.querySelector(fieldForSort).innerText.toLowerCase()
    return sortFunction(a,b);
  }).forEach(function(n, i) {
    n.style.order = i
  })
}

const valueCostInput = document.querySelectorAll("#value_cost");
const alertButton = document.querySelectorAll("#button_set_cost");
let variableForAlert = null;

console.log(alertButton);


alertButton.forEach((button)=>{button.addEventListener('click', alertForAdmin)});

function alertForAdmin(e){
console.log("test");

variableForAlert = prompt("Enter the cost of Tour Ad service","");
console.log(variableForAlert);

valueCostInput.forEach((value)=>{value.value=variableForAlert});
return variableForAlert;
}