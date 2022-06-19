

const prosentInfo = parseFloat(document.getElementById('prosent').value);
const costInfot = parseInt(document.getElementById('cost').value); 
const numberOfPeoplMaxInfot = parseInt(document.getElementById('numberOfPeoplMax').value); 

const START_DATE = document.getElementById('startDate');
const END_DATE = document.getElementById('endDate');


function getResult(inputNumberOfPeople) {
    
    if(inputNumberOfPeople < 1){
        document.getElementById('rezult').innerHTML = 'Error';
        return;
    }

    if(START_DATE.value.length == 0 && END_DATE.value.length == 0){
        document.getElementById('rezult').innerHTML = 'Date empty';
        return;
    }

    const DAY_DIFFERENT = getDiffetentDateDay(new Date(START_DATE.value),new Date(END_DATE.value));

    if(DAY_DIFFERENT < 1){
        alert("Введено не правильний порядок дат");
        document.getElementById('rezult').innerHTML = 'Error';
        return;
    }

    //console.log('Date diff ' + DAY_DIFFERENT);

    //console.log('Cost without ' +calculate(inputNumberOfPeople));

    const cost =  parseInt(calculate(inputNumberOfPeople) * DAY_DIFFERENT);

    document.getElementById('rezult').innerHTML = cost;
};

function calculate(inputNumberOfPeople){
    let numberPersons = ((inputNumberOfPeople < numberOfPeoplMaxInfot) ? inputNumberOfPeople :numberOfPeoplMaxInfot ) -1;
    
    let cost = parseFloat(costInfot * inputNumberOfPeople);
    let prosentNow = parseFloat(numberPersons * prosentInfo);

    let costDelete = parseFloat(cost/100*prosentNow);


    cost -= costDelete;

    return cost;
}


function getDiffetentDateDay(startDate,endDate){
    var timeDiff = endDate.getTime() - startDate.getTime();
    return (Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1); 
}

