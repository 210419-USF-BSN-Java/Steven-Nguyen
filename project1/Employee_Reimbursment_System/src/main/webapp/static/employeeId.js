document.getElementById("submitId").onclick = ve;

async function ve(){
    
    apiURL ='http://localhost:8080/ERS/manager/info?';
    let response = await fetch(apiURL);

    if(response.status >= 200 && response.status <300){
        let daoData = await response.json();
        getE(daoData);
    }else{
        document.getElementById('fromjs').innerHTML=`<p>
        You suck at Javascript!</p>`
    }
}

async function getE(response){
    
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
                        Amount: ${x.amount}
                        Description: ${x.description}
                    
                        `;
        fromjs.appendChild(d);
              

    }
}