import org.example.Book
import org.example.Deposit
import org.example.InitDeposits
import groovyx.net.http.AsyncHTTPBuilder
import static groovyx.net.http.ContentType.HTML

class BootStrap {

	def init = { servletContext ->
		// Check whether the test data already exists.
		if (!Book.count()) {
			new Book(author: "Stephen King", title: "The Shining").save(failOnError: true)
			new Book(author: "James Patterson", title: "Along Came a Spider").save(failOnError: true)
		}
		if(!Deposit.count()){
			def finance = new InitDeposits();
			
			finance.start();
			def deposits = finance.getDeposits();
			/*deposits.each {
				  new Deposit(bankName: it.getBankName(), bankPhone:it.getBankPhone(), bankUrl:it.getBankUrl(), bankRegion:it.getBankRegion(), currency:it.getCurrency(), payConditions: it.getPayConditions(), amountLimits:it.getAmountLimits(), period:it.getPeriod(), rate:it.getRate(), date:it.getDate()).save(failOnError: true)
				}
			*/
		}
	}


	def destroy = {
	}

}
