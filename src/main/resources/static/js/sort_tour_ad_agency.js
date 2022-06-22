let nameBox = ".order_block";
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
const classCountOrder = '.item_count-order'; //

const classCostTourAd = '.item_cost-tour-ad';//

const classProsent = '.item_prosent';

const classNumberOfPeolpeMax = '.item_number-of-people';

const classCountryName = '.item_country-name';//

const classDateRegistration = '.item_date-registration';//

const clasCityName = '.item_city-name';//

const classStartDateTourAd = '.item_start-date-tour-ad';//

const classEndDateTourAd = '.item_end-date-tour-ad';//

const classPlaceName = '.item_place-name';//

const classStateTourAd = '.item_state-tour-ad';//

// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+

function getMethodBySelectedName(selectedName){
    //console.log(selectedName);
    if(selectedName == 'sortByCountOrder'){
      sortDivs(classCountOrder,how_sort_number);
      return;
    }
    if(selectedName == 'sortByCostTourAd'){
      sortDivs(classCostTourAd,how_sort_string);
      return;
    }
    if(selectedName == 'sortByProsent'){
      sortDivs(classProsent,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNumberOfPeolpeMax'){
      sortDivs(classNumberOfPeolpeMax,how_sort_string);
      return;
    }
    if(selectedName == 'sortByCountryName'){
      sortDivs(classCountryName,how_sort_string);
      return;
    }
    if(selectedName == 'sortByCityName'){
      sortDivs(classCityName,how_sort_number);
      return;
    }
    if(selectedName == 'sortByStartDateTourAd'){
      sortDivs(classStartDateTourAd,how_sort_number);
      return;
    }
    if(selectedName == 'sortByEndDateTourAd'){
      sortDivs(classEndDateTourAd,how_sort_number);
      return;
    }
    if(selectedName == 'sortByPlaceName'){
      sortDivs(classPlaceName,how_sort_string);
      return;
    }
    if(selectedName == 'sortByStateTourAd'){
      sortDivs(classStateTourAd,how_sort_string);
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