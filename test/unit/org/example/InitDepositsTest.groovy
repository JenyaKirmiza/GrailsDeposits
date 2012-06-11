package org.example

import grails.test.*

class InitDepositsTest extends GrailsUnitTestCase {
	def fileName = "file:///D:/Projects/Grails/WS/deposits/test/unit/org/example/finance.ua.pageSample.htm"
	def initDeposits
	
    protected void setUp() {
        super.setUp()
		initDeposits = new InitDeposits()
		initDeposits.setUrl(fileName);
    }

	
    void testSomething() {
		initDeposits.start()
		
		initDeposits.getDeposits().each {
			assert it.getBankName() instanceof String
			assert it.getBankUrl() instanceof String
			assert it.getBankPhone() instanceof String
			assert it.getBankRegion() instanceof String
			assert it.getCurrency()  instanceof String
			assert it.getPayConditions() instanceof String
			assert it.getAmountLimits() instanceof String
			assert it.getPeriod() instanceof String
			assert it.getRate() instanceof String
			assert it.getDate() instanceof Date
		}
    }
}
