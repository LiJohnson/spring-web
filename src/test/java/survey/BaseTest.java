package survey;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class BaseTest implements ApplicationContextAware{
	public ApplicationContext ctxt;
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.ctxt = arg0;
	}	
}
