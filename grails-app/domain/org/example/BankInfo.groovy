package org.example

class BankInfo {

	def name=''
	def url=''
	def phone=''
	def region=''

	static constraints = {
		name(blank: false)
		url(blank: false)
		phone(blank: false)
		region(blank: false)
	}

	BankInfo(){}
	
	BankInfo(BankInfo ancestor){
		name = ancestor.getName()
		url = ancestor.getUrl()
		phone = ancestor.getPhone()
		region = ancestor.getRegion()
    }
}
