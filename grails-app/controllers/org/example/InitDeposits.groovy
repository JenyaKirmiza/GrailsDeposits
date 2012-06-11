package org.example

import org.ccil.cowan.tagsoup.*
import org.example.Deposit
import groovy.xml.*
import groovy.util.XmlSlurper

class InitDeposits {
	def ENCODING='CP1251'
	def url = 'http://tables.finance.ua/ru/credit_deposit/deposit_nat/~/ua/xxx/0/0/0'
	def headers = []
	def deposits= []
	def deposit

	def start ={
		print "Get deposit data from internet: "
		def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
		def slurper = new XmlSlurper(tagsoupParser)

		try{

			def pageText =  new URL(url).getText(ENCODING)
			def htmlParser = slurper.parseText(pageText)

			deposit = new Deposit()
			deposit.date = new Date()

			htmlParser.'**'.findAll{ it.@id == 'deposit-grid' }.each {
				def nodes = it.children()


				nodes[0..-1].each {
					
					def currencyCountDown
					def payConditionCountDown
					def amountLimitsCountDown

					it.childNodes().each{

						if(it.name.equalsIgnoreCase("th")) {
							headers.add(it.text())
						}
						else if("td".equals(it.name)){
							def aClass = it.attributes().getAt("class")
							def rowspan = it.attributes().getAt("rowspan")

							if("first".equals(aClass)){
								initBankInfo(it, deposit)
							}else if("empty".equals(it.name)){
								//nothing to do
							}
							else //initDepositDetails
							if (it.text().matches("^[A-Z]{3}")){
								//assume 3 symbol currency code
								deposit.setCurrency(it.text())
							}else if(it.text().matches("^[\\u0400-\\u04f9]{1,}")){//Cyrillic word
								//assume payment conditions
								deposit.setPayConditions(it.text())
							}
							else if(it.text().matches("^\\d{3,}(\\s).*")){
								//assume amount limits
								deposit.setAmountLimits(it.text())
								amountLimitsCountDown = rowspan
							}
							else if (it.text().matches("^\\d{1,2}")){
								//assume period
								deposit.setPeriod(it.text())
							}else if(it.text().indexOf("%")!=-1){
								//assume rate
								deposit.setRate(it.text())
								deposits.add(deposit)
								//FIXME don't need to create a deposit when next row contains <td class="empty"...
								deposit = new Deposit(deposit)
							}
						}
					}
				}
				headers.each{ print "|$it "} println ""
				deposits.each { println "$it.bankName $it.bankPhone $it.currency $it.payConditions $it.amountLimits $it.period $it.rate " }
				println "done!"
			}
			//TODO save collected info into DB
			saveToDB()
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
	}

	def initBankInfo(it, deposit) {
		def nodeList = it.children()

		nodeList.each{

			if(it instanceof groovy.util.slurpersupport.Node) {

				if(it.name.equals("a")){
					def href = it.attributes().getAt("href");

					deposit.setBankName(it.text());
					deposit.setBankUrl(href);
				}
			}
			else if(it instanceof String){

				if (it.matches( "^[\\d]{7,}")) {
					//assusme it's a phone number
					deposit.setBankPhone(it)
				}
				else {
					//so it's a bank Region
					deposit.setBankRegion(it)
				}
			}
		}
	}

	def saveToDB={
		//TODO not implemented yet
	}
}
