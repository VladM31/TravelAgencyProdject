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

    if(btn.classList.contains(styleSelectSort)){
      event.preventDefault();
      getMethodBySelectedName(sb.options[sb.selectedIndex].id);
      btn.classList.remove(styleSelectSort);
    }else{
      btn.classList.add(styleSelectSort);
    }
    
};

// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+
const classFirstNameCustomer = '.item_first-name-customer'; //++

const classLastNameCustomer = '.item_last-name-customer';//++

const classNameMale = '.item_name-male';//++

const classDateStart = '.item_start-date';//++



const classNumberCustomer = '.item_number-customer';//++

const classEmailCustomer = '.item_email-customer';//++

const classNameCountry = '.item_name-country';//++

const classEndDate = '.item_end-date';//++



const classCost = '.item_cost-order';//++

const classNumberOfPeople = '.item_number-of-people';//++

const classPlaceStart = '.item_place-start';//++

const classDateCreate = '.item_date-create';//++

const classStateOrder = '.item_state-order';//++
// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+

function getMethodBySelectedName(selectedName){
    //console.log(selectedName);
    if(selectedName == 'sortByDateCreate'){
      sortDivs(classDateCreate,how_sort_string);
      return;
    }
    if(selectedName == 'sortByFirstNameCustomer'){
      sortDivs(classFirstNameCustomer,how_sort_string);
      return;
    }
    if(selectedName == 'sortByLastNameCustomer'){
      sortDivs(classLastNameCustomer,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNameMale'){
      sortDivs(classNameMale,how_sort_string);
      return;
    }
    if(selectedName == 'sortByDateStart'){
      sortDivs(classDateStart,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNumberCustomer'){
      sortDivs(classNumberCustomer,how_sort_number);
      return;
    }
    if(selectedName == 'sortByEmailCustomer'){
      sortDivs(classEmailCustomer,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNameCountry'){
      sortDivs(classNameCountry,how_sort_string);
      return;
    }
    if(selectedName == 'sortByEndDate'){
      sortDivs(classEndDate,how_sort_string);
      return;
    }
    if(selectedName == 'sortByCost'){
      sortDivs(classCost,how_sort_number);
      return;
    }
    if(selectedName == 'sortByNumberOfPeople'){
      sortDivs(classNumberOfPeople,how_sort_number);
      return;
    }
    if(selectedName == 'sortByPlaceStart'){
      sortDivs(classPlaceStart,how_sort_string);
      return;
    }
    if(selectedName == 'sortByStateOrder'){
      sortDivs(classStateOrder,how_sort_string);
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