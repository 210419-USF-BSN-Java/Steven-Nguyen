document.getElementById("RR").onclick = gr;
document.getElementById("Employees").onclick = ge;

async function ge(){
    
    apiURL ='http://localhost:8080/ERS/manager/employees?';
    let response = await fetch(apiURL);

    if(response.status >= 200 && response.status <300){
        let daoData = await response.json();
        retrieveE(daoData);
    }else{
        document.getElementById('fromjs').innerHTML=`<p>
        You suck at Javascript!</p>`
    }
}

async function gr(){
    apiURL ='http://localhost:8080/ERS/manager/resolved?';
    let response = await fetch(apiURL);

    if(response.status >= 200 && response.status <300){
        let daoData = await response.json();
        getR(daoData);
    }else{
        document.getElementById('fromjs').innerHTML=`<p>
        You suck at Javascript!</p>`
    }
}

async function retrieveE(response){
    
    let addInput = document.getElementById('fromjs');

    while(addInput.firstChild){
        addInput.removeChild(addInput.firstChild);
    }

    for(var i=0; i < response.length; i++){

        var x = response[i];
        console.log(x);
        var d = document.createElement('div');
        d.textContent = `
                        Employee ID: ${x.id}
                        First Name: ${x.fname}
                        Last Name: ${x.lname}
                        Role: ${x.roles.userRole}
                        `;
        fromjs.appendChild(d);
              

    }
}

async function getR(response){
    let addInput = document.getElementById('fromjs');

    while(addInput.firstChild){
        addInput.removeChild(addInput.firstChild);
    }

    for(var i=0; i < response.length; i++){

        var x = response[i];
        console.log(x);
        var d = document.createElement('div');
        d.textContent = `
                        Reimbursement ID: ${x.reimbId}
                        Type: ${x.types.types}
                        Cost: ${x.amount}
                        Date Submitted: ${x.submitted}
                        Date Resolved: ${x.resolved}
                        Description: ${x.description}
                        Manager: ${x.resolver.fname}
                        `;
        fromjs.appendChild(d);
              

    }
}