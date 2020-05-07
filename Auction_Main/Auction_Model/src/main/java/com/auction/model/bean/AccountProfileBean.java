package com.auction.model.bean;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.auction.model.entity.AccountProfile;

public class AccountProfileBean {
	private Integer accountId;
	private LandingPagesBean landingPagesBean;
	private String publicName;
	private NotificationCodesBean notificationCodesBean;
	private RoyaltyCodesBean royaltyCodesBean;
	private String governmentId;
	private String FName;
	private String MName;
	private String LName;
	private String fullName;
	private String profileImage;
	private String businessName;
	private String emailAddress2;
	private String postalAddress;
	private String phoneNumber1;
	private String phoneNumber2;
	private Float rating;
	private Integer numOfRatings;
	private Integer ratingTotal;
	private String preferredLanguage;
	private Integer noOfCancellations;
	private Integer offerOrBidCount;
	private Integer executedOfferOrBidCount;
	private Float cancelPercentage;
	private String contactUs;
	private Integer reserved1;
	private String reserved2;
	private SellerInfoBean sellerInfoBean;
	private EventsProfileBean eventsProfileBean;
	private BankDetailsBean bankDetailsBean;
	private ShippersRegistrationInfoBean shippersRegistrationInfoBean;
	private List<AccountLocationsBean> existingAccountLocationBean = new ArrayList<AccountLocationsBean>(0);
	private List<AccountLocationsBean> newAccountLocation = new ArrayList<AccountLocationsBean>(0);
	private Blob contents;
	private Integer otp;
	private Date otpExpiredDate;
	private Integer otpCount;

	public AccountProfileBean() {
	}

	public AccountProfileBean(Integer accountId) {
		this.accountId = accountId;
	}

	public AccountProfileBean(AccountProfile entity) {
		if (null != entity) {
			this.accountId = entity.getAccountId();
			this.governmentId = entity.getGovernmentId();
			this.FName = entity.getFName();
			this.MName = entity.getMName();
			this.LName = entity.getLName();
			this.fullName = (this.FName + " " + this.LName);
			this.publicName = entity.getPublicName();
			this.profileImage = entity.getProfileImage();
			this.businessName = entity.getBusinessName();
			this.emailAddress2 = entity.getEmailAddress2();
			this.postalAddress = entity.getPostalAddress();
			this.phoneNumber1 = entity.getPhoneNumber1();
			this.phoneNumber2 = entity.getPhoneNumber2();
			this.rating = entity.getRating();
			this.numOfRatings = entity.getNumOfRatings();
			this.ratingTotal = entity.getRatingTotal();
			this.preferredLanguage = entity.getPreferredLanguage();
			this.noOfCancellations = entity.getNoOfCancellations();
			this.offerOrBidCount = entity.getOfferOrBidCount();
			this.executedOfferOrBidCount = entity.getExecutedOfferOrBidCount();
			this.cancelPercentage = new Float((entity.getNoOfCancellations().floatValue() / entity.getOfferOrBidCount().floatValue()) * 100);
			// this.cancelPercentage = entity.getRating();
			this.contactUs = entity.getContactUs();
			this.reserved1 = entity.getReserved1();
			this.reserved2 = entity.getReserved2();
			this.bankDetailsBean = (null != entity.getBankDetails() ? new BankDetailsBean(entity.getBankDetails()) : null);
			this.shippersRegistrationInfoBean = (null != entity.getShippersRegistrationInfo() ? new ShippersRegistrationInfoBean(entity.getShippersRegistrationInfo()) : null);
			this.landingPagesBean = (null != entity.getLandingPages() ? new LandingPagesBean(entity.getLandingPages()) : null);
			this.notificationCodesBean = new NotificationCodesBean(entity.getNotificationCodes());
			this.royaltyCodesBean = new RoyaltyCodesBean(entity.getRoyaltyCodes());
			this.contents = entity.getContents();
			this.otp = entity.getOtp();
			this.otpExpiredDate = entity.getOtpExpiredDate();
			this.otpCount = entity.getOtpCount();
		}

	}

	/*
	 * public AccountProfileBean(Integer accountId, String publicName, String
	 * governmentId, String fName, String mName, String lName, String profileImage,
	 * String businessName, String emailAddress2, String postalAddress, String
	 * phoneNumber1, String phoneNumber2, Float rating, Integer numOfRatings,
	 * Integer ratingTotal, String preferredLanguage, Integer noOfCancellations,
	 * Integer offerOrBidCount, Float cancelPercentage, String contactUs, Integer
	 * reserved1, String reserved2) { this.accountId = accountId; this.publicName =
	 * publicName; this.governmentId = governmentId; this.FName = fName; this.MName
	 * = mName; this.LName = lName; this.fullName = (this.FName + " " + this.LName);
	 * this.profileImage = profileImage; this.businessName = businessName;
	 * this.emailAddress2 = emailAddress2; this.postalAddress = postalAddress;
	 * this.phoneNumber1 = phoneNumber1; this.phoneNumber2 = phoneNumber2;
	 * this.rating = rating; this.numOfRatings = numOfRatings; this.ratingTotal =
	 * ratingTotal; this.preferredLanguage = preferredLanguage;
	 * this.noOfCancellations = noOfCancellations; this.offerOrBidCount =
	 * offerOrBidCount; this.cancelPercentage = cancelPercentage; this.contactUs =
	 * contactUs; this.reserved1 = reserved1; this.reserved2 = reserved2; }
	 */

	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public LandingPagesBean getLandingPagesBean() {
		return this.landingPagesBean;
	}

	public void setLandingPagesBean(LandingPagesBean landingPagesBean) {
		this.landingPagesBean = landingPagesBean;
	}

	public String getPublicName() {
		return publicName;
	}

	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}

	public NotificationCodesBean getNotificationCodesBean() {
		return this.notificationCodesBean;
	}

	public void setNotificationCodesBean(NotificationCodesBean notificationCodesBean) {
		this.notificationCodesBean = notificationCodesBean;
	}

	public RoyaltyCodesBean getRoyaltyCodesBean() {
		return this.royaltyCodesBean;
	}

	public void setRoyaltyCodesBean(RoyaltyCodesBean royaltyCodesBean) {
		this.royaltyCodesBean = royaltyCodesBean;
	}

	public String getGovernmentId() {
		return this.governmentId;
	}

	public void setGovernmentId(String governmentId) {
		this.governmentId = governmentId;
	}

	public String getFName() {
		return this.FName;
	}

	public void setFName(String fName) {
		this.FName = fName;
	}

	public String getMName() {
		return this.MName;
	}

	public void setMName(String mName) {
		this.MName = mName;
	}

	public String getLName() {
		return this.LName;
	}

	public void setLName(String lName) {
		this.LName = lName;
	}

	public String getFullName() {
		this.fullName = (this.FName + " " + this.LName);
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProfileImage() {
		return this.profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getEmailAddress2() {
		return this.emailAddress2;
	}

	public void setEmailAddress2(String emailAddress2) {
		this.emailAddress2 = emailAddress2;
	}

	public String getPostalAddress() {
		return this.postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getPhoneNumber1() {
		return this.phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPhoneNumber2() {
		return this.phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public Float getRating() {
		return this.rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Integer getNumOfRatings() {
		return this.numOfRatings;
	}

	public void setNumOfRatings(Integer numOfRatings) {
		this.numOfRatings = numOfRatings;
	}

	public Integer getRatingTotal() {
		return this.ratingTotal;
	}

	public void setRatingTotal(Integer ratingTotal) {
		this.ratingTotal = ratingTotal;
	}

	public String getPreferredLanguage() {
		return this.preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public Integer getNoOfCancellations() {
		return this.noOfCancellations;
	}

	public void setNoOfCancellations(Integer noOfCancellations) {
		this.noOfCancellations = noOfCancellations;
	}

	public Integer getOfferOrBidCount() {
		return this.offerOrBidCount;
	}

	public void setOfferOrBidCount(Integer offerOrBidCount) {
		this.offerOrBidCount = offerOrBidCount;
	}

	public Integer getExecutedOfferOrBidCount() {
		return executedOfferOrBidCount;
	}

	public void setExecutedOfferOrBidCount(Integer executedOfferOrBidCount) {
		this.executedOfferOrBidCount = executedOfferOrBidCount;
	}

	public Float getCancelPercentage() {
		return this.cancelPercentage;
	}

	public void setCancelPercentage(Float cancelPercentage) {
		this.cancelPercentage = cancelPercentage;
	}

	public String getContactUs() {
		return contactUs;
	}

	public void setContactUs(String contactUs) {
		this.contactUs = contactUs;
	}

	public Integer getReserved1() {
		return this.reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return this.reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public SellerInfoBean getSellerInfoBean() {
		return this.sellerInfoBean;
	}

	public void setSellerInfoBean(SellerInfoBean sellerInfoBean) {
		this.sellerInfoBean = sellerInfoBean;
	}

	public EventsProfileBean getEventsProfileBean() {
		return this.eventsProfileBean;
	}

	public void setEventsProfileBean(EventsProfileBean eventsProfileBean) {
		this.eventsProfileBean = eventsProfileBean;
	}

	public BankDetailsBean getBankDetailsBean() {
		return this.bankDetailsBean;
	}

	public void setBankDetailsBean(BankDetailsBean bankDetailsBean) {
		this.bankDetailsBean = bankDetailsBean;
	}

	public List<AccountLocationsBean> getExistingAccountLocationBean() {
		return this.existingAccountLocationBean;
	}

	public void setExistingAccountLocationBean(List<AccountLocationsBean> existingAccountLocationBean) {
		this.existingAccountLocationBean = existingAccountLocationBean;
	}

	public List<AccountLocationsBean> getNewAccountLocation() {
		return this.newAccountLocation;
	}

	public void setNewAccountLocation(List<AccountLocationsBean> newAccountLocation) {
		this.newAccountLocation = newAccountLocation;
	}

	public ShippersRegistrationInfoBean getShippersRegistrationInfoBean() {
		return shippersRegistrationInfoBean;
	}

	public void setShippersRegistrationInfoBean(ShippersRegistrationInfoBean shippersRegistrationInfoBean) {
		this.shippersRegistrationInfoBean = shippersRegistrationInfoBean;
	}

	public Blob getContents() {
		return contents;
	}

	public void setContents(Blob contents) {
		this.contents = contents;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public Date getOtpExpiredDate() {
		return otpExpiredDate;
	}

	public void setOtpExpiredDate(Date otpExpiredDate) {
		this.otpExpiredDate = otpExpiredDate;
	}

	public Integer getOtpCount() {
		return otpCount;
	}

	public void setOtpCount(Integer otpCount) {
		this.otpCount = otpCount;
	}

}