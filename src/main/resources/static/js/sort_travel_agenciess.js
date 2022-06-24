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
const classNameTravelAgency = '.item_name-agency'; // 9

const classUsername = '.item_username';//2

const classNameAdress = '.item_name-adress';//3

const classNameKVED = '.item_name-kved';//4

const classEmailAgency = '.item_email-agency';//5

const classNumberAgency = '.item_number-agency';

const classCodeValue = '.item_code-value';

const classNameDirector = '.item_name-director';

const clasCountryName = '.item_country-name';

const classDateRegistration = '.item_date-registration';

const classTypeCode = '.item_type_code';


// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+

function getMethodBySelectedName(selectedName){
    //console.log(selectedName);
    if(selectedName == 'sortByDateRegistration'){
      sortDivs(classDateRegistration,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNameTravelAgency'){
      sortDivs(classNameTravelAgency,how_sort_string);
      return;
    }
    if(selectedName == 'sortByUsername'){
      sortDivs(classUsername,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNameAdress'){
      sortDivs(classNameAdress,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNameKVED'){
      sortDivs(classNameKVED,how_sort_string);
      return;
    }
    if(selectedName == 'sortByEmailAgency'){
      sortDivs(classEmailAgency,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNumberAgency'){
      sortDivs(classNumberAgency,how_sort_number);
      return;
    }
    if(selectedName == 'sortByCodeValue'){
      sortDivs(classCodeValue,how_sort_number);
      return;
    }
    if(selectedName == 'sortByNameDirector'){
      sortDivs(classNameDirector,how_sort_string);
      return;
    }

    if(selectedName == 'sortByCountryName'){
      sortDivs(clasCountryName,how_sort_string);
      return;
    }
    if(selectedName == 'sortByTypeCode'){
      sortDivs(classTypeCode,how_sort_string);
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