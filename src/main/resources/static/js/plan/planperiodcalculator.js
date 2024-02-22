$('input[name="daterange"]').daterangepicker({
        locale: {
            "separator": " ~ ",                     //  시작일시와 종료일시 구분자
            "format": 'YYYY-MM-DD',    //  일시 노출 포맷
            "applyLabel": "확인",//  확인 버튼 텍스트
            "cancelLabel": "취소",                   //  취소 버튼 텍스트
            "daysOfWeek": ["일", "월", "화", "수", "목", "금", "토"],
            "monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
        },
        showDropdowns: true,                     //  년월 수동 설정 여부
        autoApply: false                         //  확인/취소 버튼 사용여부
    });
    
$('input[name="daterange"]').on('change', function () {
	if (!isInitial) {
	    const title = document.querySelector(".title").value;
	    const startDate = moment($('input[name="daterange"]').data('daterangepicker').startDate._d).format('YYYYMMDD');
	    const endDate = moment($('input[name="daterange"]').data('daterangepicker').endDate._d).format('YYYYMMDD');
	    
	    if (sessionStorage.getItem('plan') == null) {			        
		 sessionStorage.setItem('plan', JSON.stringify(plan.init(title, startDate, endDate)));
	    } else {
		plan.set(JSON.parse(sessionStorage.getItem('plan')));
		plan.setStartDate(startDate);
		plan.setEndDate(endDate);
		sessionStorage.setItem('plan', JSON.stringify(plan.get()));
	    }   
	} else {
	    cnt++;
	    if (cnt == 2) {
		isInitial = false;
		cnt = 0;
		return;
	    }
	    
	    var endDate = moment(plan.getEndDate(), 'YYYYMMDD').format('YYYY-MM-DD');
	    $('input[name="daterange"]').data('daterangepicker').setEndDate(endDate);
	}
	
	let div = document.querySelector('.div');
	
	if (!div) {
	    makeDayScheduleSection();
	}
	else {
	    editDateInScheduleSection();
	}
	
})
    
var { setItem, getItem, removeItem, clear, length, key } = sessionStorage;
    
var plan = (function() {
	var plan = {
		title: "",
		startDate: "",
		endDate: "",
		detailPlans: []
	};
	
	function init(title, startDate, endDate) {
	    plan.title = title;	// title도 이때 같이 해줘야 할까?
	    plan.startDate = startDate;
	    plan.endDate = endDate;
	    plan.detailPlans = [];
	    return plan;
	}
	
	function get() {
	    return plan;
	}
	
	function set(newPlan) {
	   plan.title = newPlan.title;
	   plan.startDate = newPlan.startDate;
	   plan.endDate = newPlan.endDate;
	   plan.detailPlans = newPlan.detailPlans;
	   return plan;
	}
	
	function getTitle() {
	    return plan.title;
	}
	
	function getStartDate() {
	    return plan.startDate;
	}
	
	function getEndDate() {
	    return plan.endDate;
	}
	
	function addDetailPlan(detailPlan) {
	    plan.detailPlans.push(detailPlan);
	}
	
	function setTitle(title) {
	    plan.title = title;
	}
	
	function setStartDate(startDate) {
	    plan.startDate = startDate;
	}
	
	function setEndDate(endDate) {
	    plan.endDate = endDate;
	}
	
	return {
	        init: init,
	        get: get,
	        set: set,
	        getTitle: getTitle,
	        getStartDate : getStartDate,
	        getEndDate : getEndDate,
	        addDetailPlan: addDetailPlan,
	        setTitle: setTitle,
	        setStartDate : setStartDate,
	        setEndDate : setEndDate
	};
})();

var DetailPlan = (function() {
    function DetailPlan(date, startHour, endHour, placeId, placeColor) {
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.placeId = placeId;
        this.placeColor = placeColor;
    };

    DetailPlan.prototype.get = function() {
        return {
            date: this.date,
            startHour: this.startHour,
            endHour: this.endHour,
            placeId: this.placeId,
            placeColor: this.placeColor
        };
    };

    return DetailPlan;
})();

var isInitial = false;
var isHandlerRegistered = false;

var cnt = 0;
if (sessionStorage.getItem('plan') != null) {
	// startDate, endDate 모두 있는지 확인해줘야 하나..?
	isInitial = true;
	plan.set(JSON.parse(sessionStorage.getItem('plan')));
	var startDate = moment(plan.getStartDate(), 'YYYYMMDD').format('YYYY-MM-DD');
	$('input[name="daterange"]').data('daterangepicker').setStartDate(startDate);
}

if (!isHandlerRegistered) {
    isHandlerRegistered = true;
    
    class DefaultResponse extends Error {
        constructor(statusCode, message, data) {
    	super(message);
    	this.statusCode = statusCode;
    	this.data = data;
        }
    }
    
    const saveBtn = document.querySelector('.saveBtn');
    saveBtn.addEventListener("click", function() {
	event.preventDefault();
	
	const plans = sessionStorage.getItem('plan');
	if (plans === null) {
	    alert('여행 기간을 설정해주세요.');
	    return;
	}

	plan.detailPlans = [];
	plans.detailPlans.foreach(function (e) {
	   let detailPlan = new DetailPlan(e.date, e.startHour, e.endHour, e.placeNo, e.color);
	   plan.detailPlans.push(detailPlan);
	});
	
	const fetchPlans = async() => { 
	    try {
        	    const response = await fetch('/plans', {
                	    method: 'POST',
                	    headers: {
                		'Content-Type': 'application/json',
                		'Accept': 'application/json'
                	    },
                	    body: plans
                	});
        	    
        	    let responseBody;
        	    if (response.ok) {
        		responseBody = response.json();
        		return await responseBody;
        	    } else {
        		throw new DefaultResponse(responseBody.statusCode, response.message, null); 
        	    }
	    } catch (error) {
		console.log(error);
		throw error;
	    }
	};
	
	const renderPlans = async() => {
	    try {
		const plans = await fetchPlans();
		sessionStorage.removeItem('plan');
		alert('저장되었습니다.')
	    } catch (e) {
		if (e instanceof DefaultResponse) {
		    if (e.statusCode == 401) {
			alert('로그아웃 되었습니다. 다시 인증해주세요.')
		    } else {
			console.log(e.message);
		    }
		}
	    }
	};
	
	renderPlans();
    });
}
function makeDayScheduleSection() {
    const startDate = $('input[name="daterange"]').data('daterangepicker').startDate._d;
    const endDate = $('input[name="daterange"]').data('daterangepicker').endDate._d;
    const timeDiff = Math.abs(endDate - startDate);
    const daysDiff = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
    dayDiffBeforeChange = daysDiff;
    
    const detailsElement = document.querySelector('.details');

    const childElements = Array.from(detailsElement.children);

    childElements.forEach((element) => {
        element.remove();
    });
    
    //const date = $('input[name="daterange"]').data('daterangepicker').startDate._d;
    const nextDate = new Date(startDate);

    for (let i = 1; i <= daysDiff; i++) {
        let daySchedule = document.createElement("div");
        daySchedule.setAttribute("class", "form-control2");

        detailsElement.appendChild(daySchedule);

        let div = document.createElement("div");
        div.setAttribute("class", "div");
        div.style = 'padding:10px 10px 10px 30px;';

        let h2 = document.createElement("h2");
        h2.setAttribute("class", "h2");
        h2.style = 'padding:10px 10px 10px 10px; display:inline-block;';
        h2.innerText = "DAY " + i;

        div.appendChild(h2);

        let span = document.createElement("span");

        let nextDateMoment = moment(nextDate).add(i - 1, 'days');
        span.setAttribute("class", "span");
        span.innerText = nextDateMoment.format('YYYYMMDD');
        span.style = 'display:inline-block';

        div.appendChild(span);

        daySchedule.appendChild(div);

        let button = document.createElement("button");
        button.setAttribute("class", "form-control");
        button.setAttribute("id", "add-schedule-button");
        button.innerText = "일정 추가";
        button.style = "border: none";
        button.type = "button";
        button.value = i - 1;

        addEventScheduleAddButton(button);
        
        daySchedule.appendChild(button);
    }
}

function addEventScheduleAddButton(button) {
    const startDate = $('input[name="daterange"]').data('daterangepicker').startDate._d;
    const endDate = $('input[name="daterange"]').data('daterangepicker').endDate._d;
    
    // 일정 추가 버튼 눌렀을 시
    button.addEventListener("click", function () {
	let title = document.querySelector('.title').value;
        let startDateString = moment(startDate).format('YYYYMMDD');
        let endDateString = moment(endDate).format('YYYYMMDD');
        let date = button.value;
        console.log(title);
        var givemedate = document.querySelector('.span').textContent;
        localStorage.setItem('givemedate', givemedate);

        //fetch(`/search?title=${title}&startDate=${startDateString}&endDate=${endDateString}&date=${date}`)
        fetch(`/search?date=${date}`)
            .then(response => response.text())
            .then(html => {
                // searchForm.html
                document.querySelector('.frame').innerHTML = html;

                var categories = document.querySelector('.categories');

                if (!categories) {
                    makeCategoryButton();
                    resizeMap();
                    deleteMapScript();
                    loadMapScript();
                    loadScript('js/plan/search.js', 'test3', 'module');
//                    loadScript('js/plan/clickcategoryandselectandcancelandapply.js', 'test3');
//                    loadScript('js/plan/search.js', 'test3');
                }
            });
    });
}

function makeCategoryButton() { // 지도 위에 카테고리 만들기
    let div2 = document.createElement('div');
    div2.setAttribute("class", "row categories");
    div2.setAttribute("style", "padding: 0px 10px 10px 10px;")

    let div3 = document.createElement('div');
    div3.setAttribute("class", "col-md-4");

    let div = document.createElement('div');
    div.setAttribute("class", "category row");

    // 음식점
    let button = document.createElement('button');
    button.type = 'button';
    button.setAttribute("class", "form-control cat");
    button.setAttribute("name", "category");
    button.setAttribute("id", "category");
    button.setAttribute("value", "음식점");
    button.style = "border: dotted; border-color: #E5F2FF; margin:5px;";
    button.textContent = "음식점";

    div3.appendChild(div);
    div.appendChild(button);

    // 관광지
    let div4 = document.createElement('div');
    div4.setAttribute("class", "col-md-4");

    let div5 = document.createElement('div');
    div5.setAttribute("class", "category row");

    let button2 = document.createElement('button');
    button2.setAttribute("class", "form-control cat");
    button2.setAttribute("name", "category");
    button2.setAttribute("value", "관광지");
    button2.textContent = "관광지";
    button2.style = "border: dotted; border-color: #E5F2FF; margin:5px;";

    div4.appendChild(div5);
    div5.appendChild(button2);

    // 행사/공연/축제
    let div6 = document.createElement('div');
    div6.setAttribute("class", "col-md-4");

    let div7 = document.createElement('div');
    div7.setAttribute("class", "category row");

    let button3 = document.createElement('button');
    button3.setAttribute("class", "form-control cat");
    button3.setAttribute("name", "category");
    button3.setAttribute("value", "행사/공연/축제");
    button3.textContent = "행사/공연/축제";
    button3.style = "border: dotted; border-color: #E5F2FF; margin:5px;";

    div6.appendChild(div7);
    div7.appendChild(button3);

    div2.appendChild(div3);
    div2.appendChild(div4);
    div2.appendChild(div6);
    
    let map = document.querySelector('.map');
    map.insertBefore(div2, map.firstChild);
}

// 존재하던 스크립트 지우고 다시 실행 -> 지우지 않으면 에러 발생
//function loadScript(src, callback) {
//    let script = document.createElement('script');
//    script.src = src;
//    script.setAttribute("class", "test3");
//    script.addEventListener('load', callback);
//    document.body.appendChild(script);
//}

function resizeMap() {
    let map2 = document.getElementById('map');
    map2.style='height: 400px;';
}

function deleteMapScript() {
    var remove = document.querySelectorAll('.test3');

    remove.forEach(function(element) {
        element.remove();
    });
}

function loadMapScript() {
    loadScript("//dapi.kakao.com/v2/maps/sdk.js?appkey=0a4e5f604e58c301ddedd0a6790ec3bf", 'test3', 'text/javascript');        
    loadScript('js/plan/map.js', 'test3', 'text/javascript');
}

function loadScript(url, className, type) {
    return new Promise((resolve, reject) => {
      const script = document.createElement('script');
      script.type = type;
      script.setAttribute("class", className);
      script.src = url;
      script.onload = resolve;
      script.onerror = reject;
      document.body.appendChild(script);
    });
  }

function editDateInScheduleSection() {
    const startDate = $('input[name="daterange"]').data('daterangepicker').startDate._d;
    const endDate = $('input[name="daterange"]').data('daterangepicker').endDate._d;
    const timeDiff = Math.abs(endDate - startDate);
    const daysDiff = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
    const nextDate = new Date(startDate);
    
    if (dayDiffBeforeChange < daysDiff) {
	editDate(nextDate, dayDiffBeforeChange)
	makeDaySchedule(nextDate, dayDiffBeforeChange, daysDiff);
    } else if (dayDiffBeforeChange > daysDiff) {
	removeDaySchedule(dayDiffBeforeChange, daysDiff);
	editDate(nextDate, daysDiff);
    } else {
	editDate(nextDate, daysDiff);
    }
    
    dayDiffBeforeChange = daysDiff;
    
    var buttons = document.querySelectorAll('#add-schedule-button');
    
    buttons.forEach(function(button) {
	addEventScheduleAddButton(button);
    });
  
}

function editDate(nextDate, dayDiff) {
    var buttons = document.querySelectorAll('#add-schedule-button');
    
    for (let i = 0; i < dayDiff; i++) {
	let nextDateMoment = moment(nextDate).add(i, 'days');
	        
	let div = buttons[i].parentElement;
	let firstChild = div.firstElementChild;
	let span = firstChild.querySelector('.span');
	span.innerText = nextDateMoment.format('YYYYMMDD');
    }
}

function removeDaySchedule(dayDiffBeforeChange, daysDiff) {
    
    let cnt = dayDiffBeforeChange - daysDiff;
    
    for (let i=1; i<=cnt; i++) {
        let detailsElement = document.querySelector('.details');
        
        let preLastChild = detailsElement.lastElementChild;
        /*
        let weatherPlaces = preLastChild.querySelectorAll('.weatherplace');
        weatherPlaces.forEach(function(weatherPlace) {
            let currLastChild = detailsElement.children[daysDiff - 1];
            currLastChild.insertBefore(weatherPlace, currLastChild.querySelector('button'));
        })
        */
        
        detailsElement.removeChild(preLastChild);
    }
}

function makeDaySchedule(startDate, startDaysDiff, daysDiff) {
    dayDiffBeforeChange = daysDiff;
    
    const detailsElement = document.querySelector('.details');

//    const childElements = Array.from(detailsElement.children);
//
//    childElements.forEach((element) => {
//        element.remove();
//    });
    
    //const date = $('input[name="daterange"]').data('daterangepicker').startDate._d;
    const nextDate = new Date(startDate);

    for (let i = startDaysDiff; i < daysDiff; i++) {
        let daySchedule = document.createElement("div");
        daySchedule.setAttribute("class", "form-control2");

        detailsElement.appendChild(daySchedule);

        let div = document.createElement("div");
        div.setAttribute("class", "div");
        div.style = 'padding:10px 10px 10px 30px;';

        let h2 = document.createElement("h2");
        h2.setAttribute("class", "h2");
        h2.style = 'padding:10px 10px 10px 10px; display:inline-block;';
        h2.innerText = "DAY " + startDaysDiff;

        div.appendChild(h2);

        let span = document.createElement("span");

        let nextDateMoment = moment(nextDate).add(i, 'days');
        span.setAttribute("class", "span");
        span.innerText = nextDateMoment.format('YYYYMMDD');
        span.style = 'display:inline-block';

        div.appendChild(span);

        daySchedule.appendChild(div);

        let button = document.createElement("button");
        button.setAttribute("class", "form-control");
        button.setAttribute("id", "add-schedule-button");
        button.innerText = "일정 추가";
        button.style = "border: none";
        button.type = "button";
        button.value = i;

        addEventScheduleAddButton(button);
        
        daySchedule.appendChild(button);
    }
}