let nameBox = ".box-for-keep-information";
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
const classNumbeOfFlyers = '.item_number-of-flyers';//1

const classCity = '.item_city';//2

const classEmailCourier = '.item_email-courier';//3

const classNameCourier = '.item_name-courier';//4

const classNameGiveTask = '.item_name-give-task';//5

const classDescribe = '.text-describe';

const classDateMade = '.item_date-made';

const classState = '.item_state';
// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+

function getMethodBySelectedName(selectedName){
    //console.log(selectedName);
    if(selectedName == 'sortByNumberOfFlyers'){
      sortDivs(classNumbeOfFlyers,how_sort_number);
      return;
    }
    if(selectedName == 'sortByCity'){
      sortDivs(classCity,how_sort_string);
      return;
    }
    if(selectedName == 'sortByEmailCourier'){
      sortDivs(classEmailCourier,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNameCourier'){
      sortDivs(classNameCourier,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNameGiveTask'){
      sortDivs(classNameGiveTask,how_sort_string);
      return;
    }
    if(selectedName == 'sortByDescribe'){
      sortDivs(classDescribe,how_sort_string);
      return;
    }
    if(selectedName == 'sortByDateMade'){
      sortDivs(classDateMade,how_sort_string);
      return;
    }
    if(selectedName == 'sortByState'){
      sortDivs(classState,how_sort_string);
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