/**
 * 
 */
class PlanList {
   
    template(plans) {
	return `<h2>일정 목록</h2>
	    <tbody>
	    	${plans.map((plan, index) => `
	    	    <tr id=${index}>
	    	    	<td><a class="show" href="#">${plan.title}</a></td>
	    	    	<td><a class="delete" href="#">삭제</td>
	    	    	<td><a href="#">리뷰 쓰기</td>
	    	    </tr>
	    	    `).join('')}
	    </tbody>
	    `
    }
}

export default new PlanList();