const deleteButton=document.getElementById('delete-btn');

if(deleteButton){
    deleteButton.addEventListener('click', event=>{
        let id=document.getElementById('plan_id').value;
        fetch(`/api/plans/${id}`,{
            method:'DELETE'
        })
        .then(()=>{
            alert('삭제가 완료되었습니다.');
            location.replace('/plans');
        })
    })
}

const modifyButton= document.getElementById('modify-btn');

if(modifyButton) {
    modifyButton.addEventListener('click', event=>{
        let params=new URLSearchParams(location.search);
        let id=params.get('id');

        fetch(`/api/plans/${id}`, {
            method:'PUT',
            headers: {
                "Content-Type":"application/json",
            },
            body:JSON.stringify({
                planTitle: document.getElementById('title').value,
                planContent: document.getElementById('content').value
            })
        })
        .then(()=>{
            alert('수정이 완료되었습니다.');
            location.replace(`/plans/${id}`);
        })
    })
}

const createButton= document.getElementById("create-btn");

if (createButton) {
    createButton.addEventListener("click", (event) => {
        fetch("/api/plans", {
            method:"POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                planTitle: document.getElementById("title").value,
                planContent: document.getElementById("content").value,
            }),
        }).then(()=> {
            alert("등록 완료되었습니다.");
            location.replace("/plans");
        })
    })
}