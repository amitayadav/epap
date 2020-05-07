package com.auction.web.startup;

public class ApplicationStartup {}/*implements ApplicationListener<ContextRefreshedEvent> {
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext applicationContext = event.getApplicationContext();
		Map<String, DataSource> dataSourceMap = applicationContext.getBeansOfType(DataSource.class);
		if (dataSourceMap.isEmpty()) {
			((ConfigurableApplicationContext) applicationContext).close();
			throw new IllegalStateException("DataSource was not instantiated during startup.");
		}
		System.out.println("Auction Application Started Successfully!!");
	}
}*/