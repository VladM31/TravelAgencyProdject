const nameBox = ".message_block";
const STYLE_SORT_BUTTON = "button-sort-used";

// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+
const sortDescendingBtn = document.getElementById('sortDescending');
const sortAscendingBtn = document.getElementById('sortAscending');

let how_sort_number = function(a,b) { return a - b};
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
const classNameMessage = '.item_name-message';//1

const classNameSendler = '.item_name-sendler';//2

const classRole = '.item_name-role';//3

const classDateSend = '.item_date-send';//4

const classStateMessage = '.item_state-message';//5

// -+-+---+-+-+-+-+-+--+-++--+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-++--+-+-+-+-+-+-+

function getMethodBySelectedName(selectedName){
    //console.log(selectedName);
    if(selectedName == 'sortByNameMessage'){
        sortDivs(classNameMessage,how_sort_string);
        return;
    }
    if(selectedName == 'sortByNameSendler'){
        sortDivs(classNameSendler,how_sort_string);
        return;
    }
    if(selectedName == 'sortByRole'){
        sortDivs(classRole,how_sort_string);
        return;
    }
    if(selectedName == 'sortByDateSend'){
        sortDivs(classDateSend,how_sort_string);
        return;
    }
    if(selectedName == 'sortByStateMessage'){
        sortDivs(classStateMessage,how_sort_string);
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