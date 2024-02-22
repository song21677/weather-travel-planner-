/**
 * 
 */
import PlanList from "./PlanList.js"
import Plan from "./Plan.js"

class DefaultResponse extends Error {
    constructor(statusCode, message, data) {
	super(message);
	this.statusCode = statusCode;
	this.data = data;
    }
}

const fetchUrl = async(url, method) => {
    try {
      const response = await fetch(url, {
	  method: method,
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          }
      });
      
      let responseBody = await response.json();
      if (responseBody.statusCode == 200) {
	  return responseBody.data;
      } else {
         throw new DefaultResponse(responseBody.statusCode, response.message, null);
       }
    } catch (e) {
      if (e instanceof DefaultResponse) {
          if (e.statusCode == 401) {
            alert('로그아웃 되었습니다. 다시 인증해주세요.')
          } else {
            console.log(e.message);
          }
      }
    }
}

const $my = document.querySelector(".My");
const routes = {
	"/" : PlanList,
	"/detail/:planId" : Plan,
};

export const changeUrl = (requestedUrl, ...data) => {
    history.pushState(null, null, requestedUrl);
    
    // 동적인 부분을 정규 표현식으로 추출
    const match = requestedUrl.match(/^\/detail\/(\d+)$/);

    if (match) {
        // 동적인 부분이 있는 경우, 해당 부분을 변수에 저장
        const planId = match[1];
        $my.innerHTML = routes["/detail/:planId"].template(data);
    } else {
        // 동적인 부분이 없는 경우 그대로 템플릿 적용
        $my.innerHTML = routes[requestedUrl].template(data);
    }
}

const findUrl = (id, method, rel) => {    
    let url = null;
    
    for (const link of plans[id].links) {
	if (link.action == method && link.rel == rel) {
	    url = link.href;
	    break;
	}
    };
    
    return url;
}

const plans = await fetchUrl("/plans", "GET");

$my.innerHTML = routes["/"].template(plans);

const show = document.querySelector(".show");
show.addEventListener("click", async (e) => {
    e.preventDefault();
    const planId = e.target.closest('tr').id;
    
    if (e.target.classList.contains("show")) {
	
	// 큰 일정 찾기
	let target = findUrl(planId, "GET", "self");
	const plan = await fetchUrl(target, "GET");
	
	// 세부 일정 찾기
	target = findUrl(planId, "GET", "detailPlan")
	const detail = await fetchUrl(target, "GET");
	
	// url 바꾸기
	changeUrl(`/detail/${planId}`, plan, detail);
    } else if (e.target.classList.contains("delete")) {
	fetchUrl(findUrl(e.target, "DELETE"), "DELETE");
    }
    
});