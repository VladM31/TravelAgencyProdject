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
const classNameUser = '.item_name-user'; // 9

const classUsername = '.item_username';//2

const classIdUser = '.item_id-user';//3

const classPasswordUser = '.item_password-user';//4

const classEmailUser = '.item_email-user';//5

const classRoleUser = '.item_role-user';

const clasCountryName = '.item_country-name';

const classNumberPhoneUser = '.item_number-phone-user';

const classDateRegistration = '.item_date-registration';

const classActiveUser = '.item_active-user';

// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+

function getMethodBySelectedName(selectedName){
    //console.log(selectedName);
    if(selectedName == 'sortByNameUser'){
      sortDivs(classNameUser,how_sort_string);
      return;
    }
    if(selectedName == 'sortByUsername'){
      sortDivs(classUsername,how_sort_string);
      return;
    }
    if(selectedName == 'sortByIdUser'){
      sortDivs(classIdUser,how_sort_number);
      return;
    }
    if(selectedName == 'sortByPasswordUser'){
      sortDivs(classPasswordUser,how_sort_string);
      return;
    }
    if(selectedName == 'sortByEmailUser'){
      sortDivs(classEmailUser,how_sort_string);
      return;
    }
    if(selectedName == 'sortByRoleUser'){
      sortDivs(classRoleUser,how_sort_string);
      return;
    }
    if(selectedName == 'sortByCountryName'){
      sortDivs(clasCountryName,how_sort_string);
      return;
    }
    if(selectedName == 'sortByNumberPhoneUser'){
      sortDivs(classNumberPhoneUser,how_sort_number);
      return;
    }
    if(selectedName == 'sortByDateRegistration'){
      sortDivs(classDateRegistration,how_sort_string);
      return;
    }
    if(selectedName == 'sortByActiveUser'){
      sortDivs(classActiveUser,how_sort_string);
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