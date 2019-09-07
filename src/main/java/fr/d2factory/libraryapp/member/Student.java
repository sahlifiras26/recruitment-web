package fr.d2factory.libraryapp.member;

import java.util.List;

import fr.d2factory.libraryapp.book.Book;

public class Student extends Member {
	
	private boolean firstYear;

	

	public Student(long id, String fullName, float wallet, boolean firstYear) {
		super(id, fullName, wallet);
		this.firstYear = firstYear;
	}

	@Override
	public boolean payBook(int numberOfDays) {
		float amount = 0;
		if (numberOfDays <= 30) {
			amount = numberOfDays * 0.10f;
		} else {
			amount = (30 * 0.10f) + ((numberOfDays - 30) * 0.15f);
		}
		if (this.firstYear) {
			if (numberOfDays <= 15) {
				amount = 0f;
			} else {
				amount = amount - (15 * 0.10f);
			}
		}
		if (this.getWallet() > amount) {

			this.setWallet(this.getWallet() - amount);
			return true;
		}
		return false;
	}

	public boolean isFirstYear() {
		return firstYear;
	}

	public void setFirstYear(boolean firstYear) {
		this.firstYear = firstYear;
	}

}
