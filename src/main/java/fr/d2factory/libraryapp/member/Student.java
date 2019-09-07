package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.constant.AppConstant;

public class Student extends Member {
	
	private boolean firstYear;

	

	public Student(long id, String fullName, float wallet, boolean firstYear) {
		super(id, fullName, wallet);
		this.firstYear = firstYear;
	}

	@Override
	public boolean payBook(int numberOfDays) {
		float amount = 0;
		//Test if the period is less of equal than 30 days
		if (numberOfDays <= AppConstant.STUDENT_PERIOD) {
			//if it's less or equal we multiply the number of days to the amount per day
			amount = numberOfDays * AppConstant.AMOUNT_PER_DAY;
		} else { 
			//else we add the extra fees to the normal amount of 30 days
			amount = (AppConstant.STUDENT_PERIOD * AppConstant.AMOUNT_PER_DAY) + ((numberOfDays - AppConstant.STUDENT_PERIOD) * AppConstant.STUDENT_AMOUNT_LATE);
		}
		//Test if student is in the first year
		if (this.firstYear) {
			//If period is less than 15 days the amount will be 0.0
			if (numberOfDays <= AppConstant.STUDENT_FREE_PERIOD) {
				amount = 0f;
			} else { //Else we will make a reduction by removing the amount of 15 days
				amount = amount - (AppConstant.STUDENT_FREE_PERIOD * AppConstant.AMOUNT_PER_DAY);
			}
		}
		//Test if Student have enough money to pay
		if (this.getWallet() > amount) {

			this.setWallet(this.getWallet() - amount);
			return true;
		}
		//Else he can't return book until he will have enough money
		return false;
	}

	public boolean isFirstYear() {
		return firstYear;
	}

	public void setFirstYear(boolean firstYear) {
		this.firstYear = firstYear;
	}

}
