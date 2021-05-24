window.onload = vp;
//document.getElementById("view").onclick = vp;

async function vp(){
    
    apiURL ='http://localhost:8080/ERS/manager/viewPending?';
    let response = await fetch(apiURL);

    if(response.status >= 200 && response.status <300){
        let daoData = await response.json();
        getP(daoData);
    }else{
        document.getElementById('fromjs').innerHTML=`<p>
        You suck at Javascript!</p>`
    }
}

async function getP(response){
    
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
                        Description: ${x.description}
                        Date Submitted: ${x.submitted.toString()}
                        `;
        fromjs.appendChild(d);
              

    }
}