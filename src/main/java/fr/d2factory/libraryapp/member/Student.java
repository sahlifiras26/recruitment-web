package fr.d2factory.libraryapp.member;

import java.util.List;

import fr.d2factory.libraryapp.book.Book;
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
		if (numberOfDays <= AppConstant.STUDENT_PERIOD) {
			amount = numberOfDays * AppConstant.AMOUNT_PER_DAY;
		} else {
			amount = (AppConstant.STUDENT_PERIOD * AppConstant.AMOUNT_PER_DAY) + ((numberOfDays - AppConstant.STUDENT_PERIOD) * AppConstant.STUDENT_AMOUNT_LATE);
		}
		if (this.firstYear) {
			if (numberOfDays <= AppConstant.STUDENT_FREE_PERIOD) {
				amount = 0f;
			} else {
				amount = amount - (AppConstant.STUDENT_FREE_PERIOD * AppConstant.AMOUNT_PER_DAY);
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
