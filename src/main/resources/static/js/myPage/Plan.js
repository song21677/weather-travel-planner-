/**
 * 
 */
class Plan {
    
    template(plan) {
	return `<header> 
		  <div>${plan[0].title}</div>
		  <div>${plan[0].startDate} ~ ${plan[0].endDate}</div>
		</header>
	
		<main>
		  ${plan[1].map(plan => `
		     <div>${plan.startHour} ~ ${plan.endHour}</div>
		  `).join('')}
		</main>
		`
    }
}

export default new Plan();