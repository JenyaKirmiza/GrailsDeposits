package org.example

class Deposit {

	def bankName
	def bankUrl
	def bankPhone
	def bankRegion
	def currency
	def payConditions
	def amountLimits
	def period
	def rate
	def date

	static constraints = {
		bankName(blank: false)
		bankUrl(blank: false)
		bankPhone(blank: false)
		bankRegion(blank: false)
		currency(blank: false)
		payConditions(blank: false)
		amountLimits(blank: false)
		period(blank: false)
		rate(blank: false)
	}

	Deposit(){
	}

	Deposit(Deposit ancestor){
		bankName=ancestor.getBankName()
	    bankUrl=ancestor.getBankUrl()
	    bankPhone=ancestor.getBankPhone()
	    bankRegion=ancestor.getBankRegion()
		currency = ancestor.getCurrency()
		payConditions = ancestor.getPayConditions()
		amountLimits = ancestor.getAmountLimits()
		period = ancestor.getPeriod()
		rate = ancestor.getRate()
		date = ancestor.getDate()
	}
}
