package com.auction.web.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.auction.commons.util.AuctionLogger;
import com.auction.component.AuctionRunningPhase;
import com.auction.component.AuctionSettlementPhase;
import com.auction.component.AuctionStatusHelper;
import com.auction.component.LogHelper;
import com.auction.dao.GenericDaoImpl;
import com.auction.service.AgentOwnerService;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;
import com.auction.service.DailyAuctionFinishedJob;
import com.auction.service.DailyAuctionStartJob;
import com.auction.service.DailyAuctionsService;
import com.auction.service.DayBegingJob;
import com.auction.web.intercepter.AuctionInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.auction" })
@EnableJpaRepositories(basePackages = "com.auction.dao", repositoryBaseClass = GenericDaoImpl.class)
// entityManagerFactoryRef="LocalContainerEntityManagerFactoryBean"
@EnableTransactionManagement
@PropertySources({ @PropertySource("classpath:properties/hibernate.properties"), @PropertySource("classpath:properties/auction.properties") })
@EnableAsync
public class AuctionWebConfig extends WebMvcConfigurerAdapter {

	public static final long MAX_UPLOAD_SIZE = 26214400;
	private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
	private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

	// Local Database Configuration URL
	private static final String LOCAL_DATABASE_URL = "database.url";

	private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String HIBERNATE_CONNECTION_CHARACTERENCODING = "hibernate.connection.characterEncoding";
	private static final String HIBERNATE_CONNECTION_USEUNICODE = "hibernate.connection.useUnicode";
	private static final String DATABASE_PASSWORD = "database.password";
	private static final String DATABASE_USERNAME = "database.username";
	private static final String DATABASE_DRIVER_CLASS = "database.driverClass";
	private static final String ACTIVE_PROFILE = "active.profile";

	private static final String MAIL_SMTP_HOST = "mail.smtp.host";
	private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	private static final String MAIL_SMTP_PORT = "mail.smtp.port";
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	private static final String MAIL_SMTP_FROM = "mail.smtp.from";
	private static final String MAIL_SMTP_PASSWORD = "mail.smtp.password";
	private static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
	private static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
	private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
	private static final String MAIL_DEBUG = "mail.debug";
	

	private final String APP_DEFAULT_TIMEZONE = "app.default.timezone";
	private final String APP_DEFAULT_LOCALE = "app.default.locale";
	private final String APP_DEFAULT_LOCALE_COOKIE_MAX_AGE = "app.default.locale.cookie.maxage";

	// E-Mail Task Executor
	private final String MAIL_TASK_CORE_POOL_SIZE = "mail.task.core.pool.size";
	private final String MAIL_TASK_MAX_POOL_SIZE = "mail.task.max.pool.size";
	private final String MAIL_TASK_QUEUE_CAPACITY = "mail.task.queue.capacity";

	// Balance Manager Task Executor
	private final String BALANCE_TASK_CORE_POOL_SIZE = "balance.task.core.pool.size";
	private final String BALANCE_TASK_MAX_POOL_SIZE = "balance.task.max.pool.size";
	private final String BALANCE_TASK_QUEUE_CAPACITY = "balance.task.queue.capacity";
	private final String AUCTION_DEFAULT_DURATION = "auction.default.duration";

	private final Logger LOGGER = Logger.getLogger(getClass());

	public AuctionWebConfig() {
	}

	@Autowired
	private Environment environment;

	@Autowired
	private DailyAuctionsService dailyAuctionsService;

	@Autowired
	private AuctionSellersService auctionSellersService;

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	/*
	 * @Autowired private AcceptBuyersBidHelper acceptBuyersBidHelper;
	 */

	@Autowired
	private AuctionSettlementPhase auctionSettlementPhase;

	@Autowired
	private AuctionRunningPhase auctionRunningPhase;

	@Autowired
	private AuctionStatusHelper auctionStatusHelper;

	@Autowired
	private LogHelper logHelper;
	
	@Autowired
	private AgentOwnerService agentOwnerService;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("redirect:home");
	}

	private AuctionLogger logger = new AuctionLogger(getClass());

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		TimeZone.setDefault(TimeZone.getTimeZone(environment.getProperty(APP_DEFAULT_TIMEZONE)));

		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		registry.addInterceptor(interceptor);

		OpenEntityManagerInViewInterceptor oInterceptor = new OpenEntityManagerInViewInterceptor();
		oInterceptor.setEntityManagerFactory(entityManagerFactory().getObject());
		registry.addWebRequestInterceptor(oInterceptor);
		registry.addInterceptor(new AuctionInterceptor());
	}

	@Bean
	public DataSource getLocaDataSource() {
	/*	System.out.println("Environment Specified: " + environment.getProperty(ACTIVE_PROFILE));*/
		/*
		 * logger.trace("Environment Specified: " + environment.getProperty(ACTIVE_PROFILE));
		 */
		LOGGER.info("Environment Specified: " + environment.getProperty(ACTIVE_PROFILE));
		LOGGER.info("Thread Info: " + Thread.currentThread().getName() + " and id : " + Thread.currentThread().getId());
		logger.trace("Thread Info: " + Thread.currentThread().getName() + " and id : " + Thread.currentThread().getId());
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty(DATABASE_DRIVER_CLASS));
		dataSource.setUrl(environment.getProperty(LOCAL_DATABASE_URL));
		dataSource.setUsername(environment.getProperty(DATABASE_USERNAME));
		dataSource.setPassword(environment.getProperty(DATABASE_PASSWORD));
		return dataSource;
	}

	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
	    List<Resource> resources = new LinkedList<Resource>();
	    resources.add(new ClassPathResource("properties/hibernate.properties"));
	    configurer.setLocations(resources.toArray(new Resource[0]));
	    configurer.setIgnoreUnresolvablePlaceholders(true);
	    return configurer;

	}
	
	@Bean("entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(getLocaDataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan("com.auction.model.entity");
		entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setContentType("text/html; charset=UTF-8");
		viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
		viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);
		viewResolver.setOrder(2);
		return viewResolver;
	}

	@Bean
	TilesViewResolver tilesViewResolver() {
		TilesViewResolver viewResolver = new TilesViewResolver();
		viewResolver.setContentType("text/html; charset=UTF-8");
		viewResolver.setOrder(1);
		return viewResolver;
	}

	@Bean
	TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("WEB-INF/tiles/tiles-definition.xml", "WEB-INF/tiles/tiles-base-definition.xml");
		tilesConfigurer.setPreparerFactoryClass(org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory.class);
		return tilesConfigurer;
	}

	@Bean("mailSession")
	public Session mailSession() {
		Properties javaMailProperties = new Properties();
		javaMailProperties.put(MAIL_SMTP_STARTTLS_ENABLE, environment.getProperty(MAIL_SMTP_STARTTLS_ENABLE));
		javaMailProperties.put(MAIL_SMTP_HOST, environment.getProperty(MAIL_SMTP_HOST));
		javaMailProperties.put(MAIL_SMTP_PORT, environment.getProperty(MAIL_SMTP_PORT));
		javaMailProperties.put(MAIL_SMTP_AUTH, environment.getProperty(MAIL_SMTP_AUTH));
		javaMailProperties.put(MAIL_SMTP_FROM, environment.getProperty(MAIL_SMTP_FROM));
		javaMailProperties.put(MAIL_SMTP_PASSWORD, environment.getProperty(MAIL_SMTP_PASSWORD));
		javaMailProperties.put(MAIL_SMTP_SSL_TRUST, environment.getProperty(MAIL_SMTP_SSL_TRUST));
		javaMailProperties.put(MAIL_SMTP_SOCKET_FACTORY_CLASS, environment.getProperty(MAIL_SMTP_SOCKET_FACTORY_CLASS));
		javaMailProperties.put(MAIL_TRANSPORT_PROTOCOL, environment.getProperty(MAIL_TRANSPORT_PROTOCOL));
		javaMailProperties.put(MAIL_DEBUG, environment.getProperty(MAIL_DEBUG));
		
		Session session = Session.getInstance(javaMailProperties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(environment.getProperty(MAIL_SMTP_FROM), environment.getProperty(MAIL_SMTP_PASSWORD));
			}
		});
		
		return session;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
		multipartResolver.setDefaultEncoding("UTF-8");
		return multipartResolver;
	}

	@Bean(name = "EmailTaskExecutor")
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(Integer.parseInt(environment.getProperty(MAIL_TASK_CORE_POOL_SIZE)));
		taskExecutor.setMaxPoolSize(Integer.parseInt(environment.getProperty(MAIL_TASK_MAX_POOL_SIZE)));
		taskExecutor.setQueueCapacity(Integer.parseInt(environment.getProperty(MAIL_TASK_QUEUE_CAPACITY)));
		taskExecutor.setThreadNamePrefix("EmailTaskExecutor-");
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Bean(name = "BalanceTaskExecutor")
	public TaskExecutor balanceManager() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(Integer.parseInt(environment.getProperty(BALANCE_TASK_CORE_POOL_SIZE)));
		taskExecutor.setMaxPoolSize(Integer.parseInt(environment.getProperty(BALANCE_TASK_MAX_POOL_SIZE)));
		taskExecutor.setQueueCapacity(Integer.parseInt(environment.getProperty(BALANCE_TASK_QUEUE_CAPACITY)));
		taskExecutor.setThreadNamePrefix("BalanceTaskExecutor-");
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.setAwaitTerminationSeconds(5);
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Bean(name="messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/i18/msg");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean(name="localeResolver")
	public LocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale(environment.getProperty(APP_DEFAULT_LOCALE)));
		resolver.setCookieName("myLocaleCookie");
		resolver.setCookieMaxAge(Integer.parseInt(environment.getProperty(APP_DEFAULT_LOCALE_COOKIE_MAX_AGE)));
		return resolver;
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.setProperty(HIBERNATE_DIALECT, environment.getProperty(HIBERNATE_DIALECT));
		properties.setProperty(HIBERNATE_HBM2DDL_AUTO, environment.getProperty(HIBERNATE_HBM2DDL_AUTO));
		properties.setProperty(HIBERNATE_SHOW_SQL, environment.getProperty(HIBERNATE_SHOW_SQL));
		properties.setProperty(HIBERNATE_FORMAT_SQL, environment.getProperty(HIBERNATE_FORMAT_SQL));
		properties.setProperty(HIBERNATE_CONNECTION_CHARACTERENCODING, environment.getProperty(HIBERNATE_CONNECTION_CHARACTERENCODING));
		properties.setProperty(HIBERNATE_CONNECTION_USEUNICODE, environment.getProperty(HIBERNATE_CONNECTION_USEUNICODE));
		return properties;
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	// Scheduling
	// ScheduleFactoryBean for JobScheduler
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		Map<String, Object> schedulerContextAsMap = new HashMap<String, Object>();
		schedulerContextAsMap.put("dailyAuctionsService", dailyAuctionsService);
		schedulerContextAsMap.put("auctionSellersService", auctionSellersService);
		schedulerContextAsMap.put("auctionBuyersService", auctionBuyersService);
		schedulerContextAsMap.put("simpMessagingTemplate", simpMessagingTemplate);
		schedulerContextAsMap.put("auctionSettlementPhase", auctionSettlementPhase);
		schedulerContextAsMap.put("auctionRunningPhase", auctionRunningPhase);
		schedulerContextAsMap.put("auctionStatusHelper", auctionStatusHelper);
		schedulerContextAsMap.put("logHelper", logHelper);
		schedulerContextAsMap.put("agentOwnerService", agentOwnerService);
		scheduler.setSchedulerContextAsMap(schedulerContextAsMap);
		scheduler.setTriggers(cronTriggerFactoryBean().getObject(), CronTriggerFactoryBean1().getObject(),cronTriggerFactoryBean2().getObject());
		// scheduler.setTriggers(cronTriggerFactoryBean().getObject());
		return scheduler;
	}

	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean() {
		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
		stFactory.setJobDetail(jobDetailFactoryBean().getObject());
		stFactory.setCronExpression("0 0 0/1 1/1 * ? *");
		return stFactory;
	}

	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean() {
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(DailyAuctionStartJob.class);
		return factory;
	}
	
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean2() {
		CronTriggerFactoryBean stFactory3 = new CronTriggerFactoryBean();
		stFactory3.setJobDetail(jobDetailFactoryBean2().getObject());
		stFactory3.setCronExpression("0 0 0 * * ? *");
		return stFactory3;
	}

	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean2() {
		JobDetailFactoryBean factory3 = new JobDetailFactoryBean();
		factory3.setJobClass(DayBegingJob.class);
		return factory3;
	}
	
	@Bean
	public CronTriggerFactoryBean CronTriggerFactoryBean1() {

		CronTriggerFactoryBean cronTriggerFactoryBean1 = new CronTriggerFactoryBean();
		cronTriggerFactoryBean1.setJobDetail(jobDetailFactoryBean1().getObject());
		String auctionDuration = environment.getProperty(AUCTION_DEFAULT_DURATION);
		String expression = "0 " + auctionDuration + " * * * ? *";
		cronTriggerFactoryBean1.setCronExpression(expression);
		return cronTriggerFactoryBean1;
	}

	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean1() {
		JobDetailFactoryBean factory2 = new JobDetailFactoryBean();
		factory2.setJobClass(DailyAuctionFinishedJob.class);
		return factory2;
	}
	
}