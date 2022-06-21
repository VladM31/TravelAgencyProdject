let nameBox = ".filter-window";
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

    if(btn.classList.contains(styleSelectSort)){
      event.preventDefault();
      getMethodBySelectedName(sb.options[sb.selectedIndex].id);
      btn.classList.remove(styleSelectSort);
    }else{
      btn.classList.add(styleSelectSort);
    }
    
};

// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+
const classDateNew = '.item_date-new';//1

const classNameTravelAgency = '.item_name-travel-agency';//1

const classRating0fTravelAgency = '.item_rating-travel-agency';//1

const classDateStart = '.item_date-start';//2

const classDateEnd = '.item_date-end';//3

const classTourAdForCustomerCost = '.item_cost-for-customer';//4

const classDiscountPercentage = '.item_discount-percentage';//5

const classDiscountSizePeople = '.text-discount-size-people';

const classCountry = '.item_name-country';

const classCity = '.item_name-city';

const classPlace = '.item_name-place';
// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+

function getMethodBySelectedName(selectedName){
    //console.log(selectedName);
    if(selectedName == 'sortByDateNew'){
        sortDivs(classDateNew,how_sort_string);
        return;
    }
    if(selectedName == 'sortByNameTravelAgency'){
      sortDivs(classNameTravelAgency,how_sort_string);
      return;
    }
    if(selectedName == 'sortByRating0fTravelAgency'){
      sortDivs(classRating0fTravelAgency,how_sort_string);
      return;
    }
    if(selectedName == 'sortByDateStart'){
      sortDivs(classDateStart,how_sort_string);
      return;
    }
    if(selectedName == 'sortByDateEnd'){
      sortDivs(classDateEnd,how_sort_string);
      return;
    }
    if(selectedName == 'sortByTourAdForCustomerCost'){
      sortDivs(classTourAdForCustomerCost,how_sort_string);
      return;
    }
    if(selectedName == 'sortByDiscountPercentage'){
      sortDivs(classDiscountPercentage,how_sort_string);
      return;
    }
    if(selectedName == 'sortByDiscountSizePeople'){
      sortDivs(classDiscountSizePeople,how_sort_string);
      return;
    }
    if(selectedName == 'sortByCountry'){
      sortDivs(classCountry,how_sort_string);
      return;
    }
    if(selectedName == 'sortByCity'){
        sortDivs(classCity,how_sort_string);
        return;
    }
    if(selectedName == 'sortByPlace'){
        sortDivs(classPlace,how_sort_string);
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