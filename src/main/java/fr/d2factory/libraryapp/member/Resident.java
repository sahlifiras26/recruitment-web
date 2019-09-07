package fr.d2factory.libraryapp.member;

import java.util.List;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.constant.AppConstant;

public class Resident extends Member{

	

	public Resident(long id, String fullName, float wallet) {
		super(id, fullName, wallet);
	}

	@Override
	public boolean payBook(int numberOfDays) {
		float amount = 0;
		if (numberOfDays <= AppConstant.RESIDENT_PERIOD) {
			amount = (numberOfDays * AppConstant.AMOUNT_PER_DAY);
		} else {
			amount = (AppConstant.RESIDENT_PERIOD * AppConstant.AMOUNT_PER_DAY) + ((numberOfDays - AppConstant.RESIDENT_PERIOD) * AppConstant.RESIDENT_AMOUNT_LATE);
		}
		if (this.getWallet() > amount) {

			this.setWallet(this.getWallet() - amount);
			return true;
		}
		return false;	
	}

}
