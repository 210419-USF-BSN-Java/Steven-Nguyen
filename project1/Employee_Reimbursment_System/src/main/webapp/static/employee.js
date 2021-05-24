document.getElementById('pending').onclick = gp;
document.getElementById('resolved').onclick = gr;
document.getElementById('info').onclick = gi;

async function gi(){
    apiURL ='http://localhost:8080/ERS/employee/info?';
    let response = await fetch(apiURL);

    if(response.status >= 200 && response.status <300){
        let daoData = await response.json();
        retrieveUserInfo(daoData);
    }else{
        document.getElementById('fromjs').innerHTML=`<p>
        You suck at Javascript!</p>`
    }
}

async function gp(){
    apiURL ='http://localhost:8080/ERS/employee/pending?';
    let response = await fetch(apiURL);

    if(response.status >= 200 && response.status <300){
        let daoData = await response.json();
        retrieveData(daoData);
    }else{
        document.getElementById('fromjs').innerHTML=`<p>
        You suck at Javascript!</p>`
    }
}

async function gr(){
    apiURL ='http://localhost:8080/ERS/employee/resolved?';
    let response = await fetch(apiURL);

    if(response.status >= 200 && response.status <300){
        let daoData = await response.json();
        retrieveData(daoData);
    }else{
        document.getElementById('fromjs').innerHTML=`<p>
        You suck at Javascript!</p>`
    }
}

function retrieveUserInfo(response){
    let x = response;
    let element = document.getElementById("fromjs");
    let p = document.createElement('p');

    p.innerHTML=`
                First Name: ${x.fname}

                Last Name: ${x.lname}
                
                Email: ${x.email}`;
                
    element.parentNode.replaceChild(p,element);
    
    
   
}

function retrieveData(response){
    let addInput = document.getElementById('fromjs');

    while(addInput.firstChild){
        addInput.removeChild(addInput.firstChild);
    }

    for(var i=0; i < response.length; i++){

        var x = response[i];
        console.log(x);
        var p = document.createElement('p');
        p.textContent = `
                        Reimbursement ID: ${x.reimbId}
                        Type: ${x.types.types}
                        Cost: ${x.amount}
                        Date Submitted: ${x.submitted}
                        Description: ${x.description}
                        `;
        fromjs.appendChild(p);
              

    }
}