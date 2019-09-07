package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.constant.AppConstant;

public class Resident extends Member{

	public Resident(long id, String fullName, float wallet) {
		super(id, fullName, wallet);
	}

	@Override
	public boolean payBook(int numberOfDays) {
		float amount = 0;
		//Test if the period of borrow is less than 60 days
		if (numberOfDays <= AppConstant.RESIDENT_PERIOD) {
			//the amount will be the number of days multiplied by 0.10
			amount = (numberOfDays * AppConstant.AMOUNT_PER_DAY);
		} else {
			//else we add the extra fees to the normal amount of 60 days 
			amount = (AppConstant.RESIDENT_PERIOD * AppConstant.AMOUNT_PER_DAY) + ((numberOfDays - AppConstant.RESIDENT_PERIOD) * AppConstant.RESIDENT_AMOUNT_LATE);
		}
		//Test if Resident don't have enough money to pay
		if (this.getWallet() > amount) {

			this.setWallet(this.getWallet() - amount);
			return true;
		}
		//Else he can't return book until he will have enough money
		return false;	
	}

}
