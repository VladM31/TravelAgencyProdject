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
const classDateMade = '.item_date-made'; // 9

const classNameTravelAgency = '.item_name-travel-agency';//2

const classNameCountry = '.item_name-country';//3

const classNameCity = '.item_name-city';//4

const classNameRest = '.item_name-rest-place';//5

const classCost = '.item_cost';

const clasNumberOfPeople = '.item_number-of-people';

const classId = '.item_id';

const classState = '.item_state';

const classStartDate = '.item_start-date';

const classEndDate = '.item_end-date';
// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+

function getMethodBySelectedName(selectedName){
    //console.log(selectedName);
    if(selectedName == 'sortByDateMade'){
      sortDivs(classDateMade,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNameTravelAgency'){
      sortDivs(classNameTravelAgency,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNameCountry'){
      sortDivs(classNameCountry,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNameCity'){
      sortDivs(classNameCity,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNameRest'){
      sortDivs(classNameRest,how_sort_string);
      return;
    }
    if(selectedName == 'sortByCost'){
      sortDivs(classCost,how_sort_number);
      return;
    }
    if(selectedName == 'sortByNumberOfPeople'){
      sortDivs(clasNumberOfPeople,how_sort_number);
      return;
    }
    if(selectedName == 'sortById'){
      sortDivs(classId,how_sort_number);
      return;
    }
    if(selectedName == 'sortByState'){
      sortDivs(classState,how_sort_string);
      return;
    }
    if(selectedName == 'sortByStartDate'){
      sortDivs(classStartDate,how_sort_string);
      return;
    }
    if(selectedName == 'sortByEndDate'){
      sortDivs(classEndDate,how_sort_string);
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