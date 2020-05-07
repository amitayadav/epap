package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "account_profile")
public class AccountProfile implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer accountId;
	private LandingPages landingPages;
	private String publicName;
	private NotificationCodes notificationCodes;
	private RoyaltyCodes royaltyCodes;
	private String governmentId;
	private String FName;
	private String MName;
	private String LName;
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
	private String contactUs;
	private Integer reserved1;
	private String reserved2;
	private SellerInfo sellerInfo;
	private BankDetails bankDetails;
	private EventsProfile eventsProfile;
	private ShippersRegistrationInfo shippersRegistrationInfo;
	private Blob contents;
	private Integer otp;
	private Date otpExpiredDate;
	private Integer otpCount;

	public AccountProfile() {
	}

	public AccountProfile(Integer accountId) {
		this.accountId = accountId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "account_id", unique = true, nullable = false)
	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "landing_page_id")
	public LandingPages getLandingPages() {
		return this.landingPages;
	}

	public void setLandingPages(LandingPages landingPages) {
		this.landingPages = landingPages;
	}

	@Column(name = "public_name", nullable = false, length = 20)
	public String getPublicName() {
		return this.publicName;
	}

	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notification_code")
	public NotificationCodes getNotificationCodes() {
		return this.notificationCodes;
	}

	public void setNotificationCodes(NotificationCodes notificationCodes) {
		this.notificationCodes = notificationCodes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "royalty_code", nullable = false)
	public RoyaltyCodes getRoyaltyCodes() {
		return this.royaltyCodes;
	}

	public void setRoyaltyCodes(RoyaltyCodes royaltyCodes) {
		this.royaltyCodes = royaltyCodes;
	}

	@Column(name = "government_id", nullable = false, length = 10)
	public String getGovernmentId() {
		return this.governmentId;
	}

	public void setGovernmentId(String governmentId) {
		this.governmentId = governmentId;
	}

	@Column(name = "f_name", nullable = false, length = 20)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "m_name", length = 20)
	public String getMName() {
		return this.MName;
	}

	public void setMName(String MName) {
		this.MName = MName;
	}

	@Column(name = "l_name", nullable = false, length = 20)
	public String getLName() {
		return this.LName;
	}

	public void setLName(String LName) {
		this.LName = LName;
	}

	@Column(name = "profile_image")
	public String getProfileImage() {
		return this.profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@Column(name = "business_name", nullable = false, length = 50)
	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Column(name = "email_address2", length = 40)
	public String getEmailAddress2() {
		return this.emailAddress2;
	}

	public void setEmailAddress2(String emailAddress2) {
		this.emailAddress2 = emailAddress2;
	}

	@Column(name = "postal_address", length = 50)
	public String getPostalAddress() {
		return this.postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	@Column(name = "phone_number1", nullable = false, length = 16)
	public String getPhoneNumber1() {
		return this.phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	@Column(name = "phone_number2", length = 16)
	public String getPhoneNumber2() {
		return this.phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	@Column(name = "rating", nullable = false, precision = 3)
	public Float getRating() {
		return this.rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	@Column(name = "num_of_ratings", nullable = false)
	public Integer getNumOfRatings() {
		return this.numOfRatings;
	}

	public void setNumOfRatings(Integer numOfRatings) {
		this.numOfRatings = numOfRatings;
	}

	@Column(name = "rating_total", nullable = false)
	public Integer getRatingTotal() {
		return this.ratingTotal;
	}

	public void setRatingTotal(Integer ratingTotal) {
		this.ratingTotal = ratingTotal;
	}

	@Column(name = "preferred_language", nullable = false, length = 3)
	public String getPreferredLanguage() {
		return this.preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	@Column(name = "no_of_cancellations", nullable = false)
	public Integer getNoOfCancellations() {
		return this.noOfCancellations;
	}

	public void setNoOfCancellations(Integer noOfCancellations) {
		this.noOfCancellations = noOfCancellations;
	}

	@Column(name = "offer_or_bid_count", nullable = false)
	public Integer getOfferOrBidCount() {
		return this.offerOrBidCount;
	}

	public void setOfferOrBidCount(Integer offerOrBidCount) {
		this.offerOrBidCount = offerOrBidCount;
	}

	@Column(name = "executed_offer_or_bid_count", nullable = false)
	public Integer getExecutedOfferOrBidCount() {
		return this.executedOfferOrBidCount;
	}

	public void setExecutedOfferOrBidCount(Integer executedOfferOrBidCount) {
		this.executedOfferOrBidCount = executedOfferOrBidCount;
	}

	@Column(name = "contact_us", length = 100)
	public String getContactUs() {
		return this.contactUs;
	}

	public void setContactUs(String contactUs) {
		this.contactUs = contactUs;
	}

	@Column(name = "reserved1")
	public Integer getReserved1() {
		return this.reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	@Column(name = "reserved2", length = 30)
	public String getReserved2() {
		return this.reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "accountProfile")
	public SellerInfo getSellerInfo() {
		return this.sellerInfo;
	}

	public void setSellerInfo(SellerInfo sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "accountProfile", cascade = CascadeType.ALL, orphanRemoval = true)
	public BankDetails getBankDetails() {
		return this.bankDetails;
	}

	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "accountProfile")
	public EventsProfile getEventsProfile() {
		return this.eventsProfile;
	}

	public void setEventsProfile(EventsProfile eventsProfile) {
		this.eventsProfile = eventsProfile;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "accountProfile", cascade = CascadeType.ALL, orphanRemoval = true)
	public ShippersRegistrationInfo getShippersRegistrationInfo() {
		return shippersRegistrationInfo;
	}

	public void setShippersRegistrationInfo(ShippersRegistrationInfo shippersRegistrationInfo) {
		this.shippersRegistrationInfo = shippersRegistrationInfo;
	}

	@Column(name = "contents")
	@Lob
	public Blob getContents() {
		return contents;
	}

	public void setContents(Blob contents) {
		this.contents = contents;
	}

	@Column(name = "otp")
	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "otp_expired_date", length = 19)
	public Date getOtpExpiredDate() {
		return otpExpiredDate;
	}

	public void setOtpExpiredDate(Date otpExpiredDate) {
		this.otpExpiredDate = otpExpiredDate;
	}

	@Column(name = "otp_count")
	public Integer getOtpCount() {
		return otpCount;
	}

	public void setOtpCount(Integer otpCount) {
		this.otpCount = otpCount;
	}

}