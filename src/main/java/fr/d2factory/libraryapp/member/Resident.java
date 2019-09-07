package fr.d2factory.libraryapp.member;

import java.util.List;

import fr.d2factory.libraryapp.book.Book;

public class Resident extends Member{

	

	public Resident(long id, String fullName, float wallet) {
		super(id, fullName, wallet);
	}

	@Override
	public boolean payBook(int numberOfDays) {
		float amount = 0;
		if (numberOfDays <= 60) {
			amount = (numberOfDays * 0.10f);
		} else {
			amount = (60 * 0.10f) + ((numberOfDays - 60) * 0.20f);
		}
		if (this.getWallet() > amount) {

			this.setWallet(this.getWallet() - amount);
			return true;
		}
		return false;	
	}

}
